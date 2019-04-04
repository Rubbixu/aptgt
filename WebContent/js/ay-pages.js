var Pages = function(options) {
  var defaultOptions = {
    pagesSelector: ".pages",
    pageSelector: ".page",
    containerSelector: ".page-holder",
    ignoreMeta: ["viewport"],
    animation: 55,
    prefetch: true,
    componentPercentage: 20,
    cacheParams: false
  };

  /**
   * Options object
   * @type {{}}
   */
  this.options = Object.assign({}, defaultOptions, options);

  /**
   * Stores loaded pages and their elements in DOM.
   * @type {{}}
   */
  this.cache = new PageCache(this);


  /**
   * Page wrapper that contains page elements.
   * @type {Element|null}
   */
  this.wrapper = null;

  /**
   * Page wrapper that contains page elements.
   * @type {Element|null}
   */
  this.container = null;

  /**
   * Order counter to make animations logical.
   * @type {number}
   */
  this.counter = 0;

  /**
   * List of page request workers.
   * @type {Object}
   */
  this.working = {};

  /*
  anchor: "#"
  origin: "localhost"
  params: {test: "1"}
  pathname: "/index.html"
  */
  this.location = new Location(window.location.href);
  this.port = window.location.port; // null when none

  this.loadListeners = [];

  // animate
  var animationEndEventNames = {
    'WebkitAnimation' : 'webkitAnimationEnd',
    'OAnimation' : 'oAnimationEnd',
    'msAnimation' : 'MSAnimationEnd',
    'animation' : 'animationend'
  };
  this.endPage = [ false, false ];
  this.isAnimating = false;
  this.support = Modernizr.cssanimations;

  this.animEndEventName = animationEndEventNames[ Modernizr.prefixed( 'animation' ) ];

  this.init();
  this.register(this.attach, this);
};

/**
 * Loads ay-pages to html.
 */
Pages.prototype.init = function() {
  var self = this;
  this.wrapper = document.querySelector(this.options.pagesSelector);
  this.container = document.querySelector(this.options.containerSelector);

  var currentPage = this.wrapper.querySelector(".ay-current");
  var loadedPages = this.wrapper.querySelectorAll(this.options.pageSelector);
  loadedPages.forEach(function(page, index) {
    var itemLocation = page.getAttribute('data-pos');
    if((index === 0 && currentPage === null) || currentPage === page) {
      self.extractData(document, page);
      addClass(page, "ay-current");
      itemLocation = self.location;
      page.setAttribute('data-pos', self.location.toID());
    }
    if(page.getAttribute('data-order') === null) {
      page.setAttribute('data-order', self.counter);
      self.counter++;
    }
    self.cache.set(itemLocation, page);
  });
};

/**
 * Attach link triggers to DOM container.
 *
 * @param element container that will be searched
 */
Pages.prototype.attach = function(element) {
  var self = this;
  var links = element.querySelectorAll("a:not([data-page-disabled='true'])");
  console.log("attach", links);

  links.forEach(function(link) {
    if(self.options.prefetch) {
        link.onmouseover = function() {
          var link = this.href;
          var info = new Location(link);
          if (!self.isTrigger(info))
              return true;

          if (!self.cache.isset(info))
            self.requestPage(info);
        }
    }
    link.onclick = function(e) {
      var link = this.href;
      var info = new Location(link);
      console.log("info", info);
      
      /* Not use loader when logging out */
      if(info.pathname.match(/.*?\/logout/)!==null){
          return true;
      }
      /* */
      if(!self.isTrigger(info)) {
          e.preventDefault();
          return true;
      }

      e.preventDefault();
      self.loadPage(info);
      return true;
    };
  });
};

Pages.prototype.isTrigger = function(info) {
  return info.origin === this.location.origin &&
      (this.location.toID() !== info.toID() ||
          (this.location.toID() === info.toID() && info.anchor !== "#"))
};

/**
 * Reloads loaded pages to the cache.
 */
Pages.prototype.reloadPages = function() {
  var self = this;
  var loadedPages = this.wrapper.querySelectorAll(this.options.pageSelector);
  loadedPages.forEach(function(item) {
    var itemLocation = item.getAttribute('data-pos');
    if(!self.cache.isset(itemLocation))
      self.cache.set(itemLocation, item);
  });
};

/**
 * Request or switch to page.
 *
 * @param url {Location}
 * @returns {boolean}
 */
Pages.prototype.loadPage = function(url) {
  if(this.location.toID() === url.toID()) {
    if(this.anchorHandler(url, true)) {
      if(this.location.anchor !== url.anchor) {
        history.pushState({}, document.title, url.toString() );
        this.location.anchor = url.anchor;
      }
    }
    return false;
  }

  if(this.cache.isset(url)) {
    return this.switchPage(url);
  }

  return this.requestPage(url, true);
};

/**
 * Handles anchor events, scroll to view.
 *
 * @param url {Location}
 * @param allowNull
 * @returns {boolean}
 */
Pages.prototype.anchorHandler = function(url, allowNull) {
  console.log(url);
  if(!allowNull && url.anchor === null) return false;
  console.log("-- ", url.anchor);
  if(url.anchor === "#") {
    return false;
  }
  var pos = 0;
  if(url.anchor !== null) {
    console.log(this.getCurrentPage());
    var target = this.getCurrentPage().querySelector(url.anchor);
    if(target === null)
      return false;
    console.log(target.getBoundingClientRect(),"ses",this.container.scrollTop);
    pos = target.getBoundingClientRect().top+this.container.scrollTop;
  }
  $(this.container).animate({ scrollTop: pos });
  return true;
};

/**
 * Generates placeholder for page while it's loading.
 *
 * @param url {Location}
 * @returns {*|jQuery}
 */
Pages.prototype.generatePlaceholder = function(url) {
  var loaders = "<div class=\"spinner\">";
  for(var i = 0; i < 12; i++) {
    loaders += '<div class="loader'+(i+1)+' loader"></div>';
  }
  loaders += '</div>';

  var element = document.createElement("div");
  element.setAttribute("class", "page loading");
  element.setAttribute("data-page", "{}");
  element.setAttribute("id", "ap-"+url.pathname);
  element.setAttribute("data-pos", url.toID());

  element.innerHTML = "<div class='loader-wrapper'>" +
    loaders +
    '<div class="loader-section section-left"></div>'+
    '<div class="loader-section section-right"></div>'+
    "</div>";

  return element;
};

/**
 * Make a page request. Load it result into DOM and Cache.
 *
 * @param url {Location}
 * @param switchPage {boolean|null}
 * @returns {boolean}
 */
Pages.prototype.requestPage = function(url, switchPage) {
  var self = this;

  if(this.working[url.toID()] !== undefined) // Check if this page already requested
    return false;

  // Add page to working list
  this.working[url.toID()] = new XMLHttpRequest();
  var placeholder = this.generatePlaceholder(url);

  self.wrapper.appendChild(placeholder);
  self.cache.set(url, placeholder);
  if(switchPage)
    self.switchPage(url);

  var request = this.working[url.toID()];
  request.open('GET', url.toString(), true);

  request.onload = function() {
    if (request.status >= 200 && request.status < 400) {
      // Success!
      var data = request.responseText;
      var page = self.processPage(data, url);
      self._loaded(placeholder, page);

      $(self.wrapper).trigger("PageData", {
          data: data,
          page: placeholder
      });

      if(self.working[url.toID()] !== undefined)
        self.working[url.toID()] = undefined;

      if(switchPage)
        self.switchPage(url);
      else if(self.location.toID() !== url.toID()) {
        removeClass(placeholder, "loading");
      }
    } else {
      console.log("Request error:", request.error);
    }
  };
  setTimeout(function() {
    request.send();
}, 0);
};

/**
 * Page load event handler.
 *
 * @param placeholder
 * @param page
 * @private
 */
Pages.prototype._loaded = function(placeholder, page) {
  var self = this;

  var loadWrapper = placeholder.querySelector('.loader-wrapper');

  placeholder.appendChild(page);
  // move all children out of the page, before loadWrapper
  while (page.firstChild) placeholder.insertBefore(page.firstChild, loadWrapper);
  // remove the empty page element
  placeholder.removeChild(page);

  self._runListeners(placeholder);

  // Get last item that finishes the transition
  var counter = 0;

  // Transition event handler
  var transitionend = function() {
    // Clear event listener
    this.removeEventListener("transitionend", transitionend);
    // Section Left-Right transaction counter
    counter++;
    if(counter >= 2) {
        // Page loader cleanup
        removeClass(placeholder, 'loading');
        placeholder.removeChild(loadWrapper);

        self._eFirstVisit(placeholder, true);
        // Wait for page to be ready to move to the anchor
        requestAnimationFrame(function() {
            self.anchorHandler(self.location, false);
        });
    }
  };

  // Register transitionend event.
    placeholder.querySelector(".section-right").addEventListener("transitionend", transitionend);
    placeholder.querySelector(".section-left").addEventListener("transitionend", transitionend);

  // We are ready, start the transition.
  addClass(placeholder, 'loaded');

  var pageData = page.getAttribute('data-page');
  // Update placeholder page
  placeholder.setAttribute("data-page", pageData);
  // Trigger loaded event, and attach link triggers in loaded page
  //this.wrapper.trigger("PageLoad", placeholder);

    var placeHolderPosition =  placeholder.getAttribute('data-pos');
    // Check if we are on this page and trigger first visit.
    if(this.location.toID() === placeHolderPosition) {
        this.updateHeader(this.location, JSON.parse(pageData));
        if(placeholder._cacheObject.getAttribute('visited') !== true) {
            placeholder._cacheObject.setAttribute('visited', true);
            self._eFirstVisit(placeholder);
        }
    }
};

/**
 * Process page meta data and position.
 *
 * @param data HTML response data
 * @param url {Location}
 * @returns {Element}
 */
Pages.prototype.processPage = function(html, url) {
  var data = document.createElement('html');
  data.innerHTML = html;

  var page = data.querySelector(this.options.pageSelector);

  this.extractData(data, page);

  if(url !== null)
    page.setAttribute("data-pos", url.toID());

  return page;
};

Pages.prototype.extractData = function(container, page) {
    var title = container.getElementsByTagName("title")[0].innerText;

  var pageData = { title: title };

  var items = container.getElementsByTagName("meta");
  for(var i = 0; i < items.length; i++) {
    if(items[i].name === "" || items[i].name === 'viewport') continue;
    pageData[items[i].name] = items[i].getAttribute('content');
  }

  page.setAttribute("data-page", JSON.stringify(pageData));

  return page;
};

/**
 * Start page change transition.
 *
 * @param url {Location}
 * @returns {boolean}
 */
Pages.prototype.switchPage = function(url) {
  if(this.location.toID() === url.toID()) return true;
  if(!this.cache.isset(url)) return false;
  var page = this.cache.get(url);

  // Set page order for transactions
  if(page.getAttribute('data-order') === null) {
    page.setAttribute('data-order', this.counter);
    this.counter++;
  }

  if(this.working[url.toID()] !== undefined) {
      this.working[url.toID()].addEventListener("progress", function  (oEvent) {
          if (oEvent.lengthComputable) {
              var percentComplete = oEvent.loaded / oEvent.total * 100;
              NProgress.set(percentComplete);
          } else {
            NProgress.inc();
          }
      });

      NProgress.start();
  }

  this.startAnimation(url);
};

/**
 * Update header information with next pages data.
 *
 * @param url {Location}
 * @param pageData
 */
Pages.prototype.updateHeader = function(url, pageData) {
  if(pageData === undefined)
    return;

  if(pageData.title !== undefined)
    document.title = pageData.title;

  console.log(url, url.toString(), pageData);

  history.pushState({}, pageData.title || "", url.toString());

  var keys = Object.keys(pageData);
  for(var i = 0; i < keys.length; i++) {
    var data = keys[i];
    if(data === "title" || this.options.ignoreMeta.includes(data)) continue;
    $("meta[name="+data+"]").replaceWith( pageData[data] );
  }
};

/**
 * Start page change animation.
 *
 * @param url {Location}
 */
Pages.prototype.startAnimation = function(url) {
  var animation = this.options.animation,
    self = this, currPage = this.cache.get(this.location), $currPage = $(currPage),
      endOptions = {};

  // If anchor is not set, move to top.
  if(url.anchor === null)
    $(this.container).animate({ scrollTop: 0 });

  if(currPage.getAttribute('data-animation') !== null) {
    animation = currPage.getAttribute('animation');
  }

  var nextPage = this.cache.get(url), $nextPage = $(nextPage).addClass( 'ay-current' ),
    outClass = 'ay-page-', inClass = 'ay-page-';

  $(this.wrapper).trigger("PageChangeStart", {
      currentPage: currPage,
      nextPage: nextPage
  });


  if( this.isAnimating !== false) {
    $currPage.off( self.animEndEventName ).stop(true, true);
    $nextPage.off( self.animEndEventName ).stop(true, true);
    self.onEndAnimation(this.isAnimating[0], this.isAnimating[1], { reanimate: url });
  }
  addClassEx(document.body, "page-changing");
  addClassEx(nextPage, "ay-changing-to");

  this.isAnimating = [currPage, nextPage];

  if(!hasClass(nextPage, "loading")) {
      if(nextPage._cacheObject.getAttribute('visited') !== true) {
          nextPage._cacheObject.setAttribute('visited', true);
          self._eFirstVisit(nextPage);
          endOptions.firstVisit = true;
      }
  }

  this.location = url;
  $(this.wrapper).trigger("LocationChange", this.location);
  var isGoingBack = currPage.getAttribute('data-order') > nextPage.getAttribute('data-order');
  if(isGoingBack) {
    if(animation === 27 || animation === 36 || animation === 37 || animation === 66 || animation === 67) {
      // NOOP
    } else if((animation < 27 && animation%2 === 1) || (animation < 36 && animation%2 === 0) || (animation < 66 && animation%2 === 0) ) {
      animation++;
    } else {
      animation--;
    }
  }


  switch( animation ) {
    case 1:
      outClass += 'moveToLeft';
      inClass += 'moveFromRight';
      break;
    case 2:
      outClass += 'moveToRight';
      inClass += 'moveFromLeft';
      break;
    case 3:
      outClass += 'moveToTop';
      inClass += 'moveFromBottom';
      break;
    case 4:
      outClass += 'moveToBottom';
      inClass += 'moveFromTop';
      break;
    case 5:
      outClass += 'fade';
      inClass += 'moveFromRight ay-page-ontop';
      break;
    case 6:
      outClass += 'fade';
      inClass += 'moveFromLeft ay-page-ontop';
      break;
    case 7:
      outClass += 'fade';
      inClass += 'moveFromBottom ay-page-ontop';
      break;
    case 8:
      outClass += 'fade';
      inClass += 'moveFromTop ay-page-ontop';
      break;
    case 9:
      outClass += 'moveToLeftFade';
      inClass += 'moveFromRightFade';
      break;
    case 10:
      outClass += 'moveToRightFade';
      inClass += 'moveFromLeftFade';
      break;
    case 11:
      outClass += 'moveToTopFade';
      inClass += 'moveFromBottomFade';
      break;
    case 12:
      outClass += 'moveToBottomFade';
      inClass += 'moveFromTopFade';
      break;
    case 13:
      outClass += 'moveToLeftEasing ay-page-ontop';
      inClass += 'moveFromRight';
      break;
    case 14:
      outClass += 'moveToRightEasing ay-page-ontop';
      inClass += 'moveFromLeft';
      break;
    case 15:
      outClass += 'moveToTopEasing ay-page-ontop';
      inClass += 'moveFromBottom';
      break;
    case 16:
      outClass += 'moveToBottomEasing ay-page-ontop';
      inClass += 'moveFromTop';
      break;
    case 17:
      outClass += 'scaleDown';
      inClass += 'moveFromRight ay-page-ontop';
      break;
    case 18:
      outClass += 'scaleDown';
      inClass += 'moveFromLeft ay-page-ontop';
      break;
    case 19:
      outClass += 'scaleDown';
      inClass += 'moveFromBottom ay-page-ontop';
      break;
    case 20:
      outClass += 'scaleDown';
      inClass += 'moveFromTop ay-page-ontop';
      break;
    case 21:
      outClass += 'scaleDown';
      inClass += 'scaleUpDown ay-page-delay300';
      break;
    case 22:
      outClass += 'scaleDownUp';
      inClass += 'scaleUp ay-page-delay300';
      break;
    case 23:
      outClass += 'moveToLeft ay-page-ontop';
      inClass += 'scaleUp';
      break;
    case 24:
      outClass += 'moveToRight ay-page-ontop';
      inClass += 'scaleUp';
      break;
    case 25:
      outClass += 'moveToTop ay-page-ontop';
      inClass += 'scaleUp';
      break;
    case 26:
      outClass += 'moveToBottom ay-page-ontop';
      inClass += 'scaleUp';
      break;
    case 27:
      outClass += 'scaleDownCenter';
      inClass += 'scaleUpCenter ay-page-delay400';
      break;
    case 28:
      outClass += 'rotateRightSideFirst';
      inClass += 'moveFromRight ay-page-delay200 ay-page-ontop';
      break;
    case 29:
      outClass += 'rotateLeftSideFirst';
      inClass += 'moveFromLeft ay-page-delay200 ay-page-ontop';
      break;
    case 30:
      outClass += 'rotateTopSideFirst';
      inClass += 'moveFromTop ay-page-delay200 ay-page-ontop';
      break;
    case 31:
      outClass += 'rotateBottomSideFirst';
      inClass += 'moveFromBottom ay-page-delay200 ay-page-ontop';
      break;
    case 32:
      outClass += 'flipOutRight';
      inClass += 'flipInLeft ay-page-delay500';
      break;
    case 33:
      outClass += 'flipOutLeft';
      inClass += 'flipInRight ay-page-delay500';
      break;
    case 34:
      outClass += 'flipOutTop';
      inClass += 'flipInBottom ay-page-delay500';
      break;
    case 35:
      outClass += 'flipOutBottom';
      inClass += 'flipInTop ay-page-delay500';
      break;
    case 36:
      outClass += 'rotateFall ay-page-ontop';
      inClass += 'scaleUp';
      break;
    case 37:
      outClass += 'rotateOutNewspaper';
      inClass += 'rotateInNewspaper ay-page-delay500';
      break;
    case 38:
      outClass += 'rotatePushLeft';
      inClass += 'moveFromRight';
      break;
    case 39:
      outClass += 'rotatePushRight';
      inClass += 'moveFromLeft';
      break;
    case 40:
      outClass += 'rotatePushTop';
      inClass += 'moveFromBottom';
      break;
    case 41:
      outClass += 'rotatePushBottom';
      inClass += 'moveFromTop';
      break;
    case 42:
      outClass += 'rotatePushLeft';
      inClass += 'rotatePullRight ay-page-delay180';
      break;
    case 43:
      outClass += 'rotatePushRight';
      inClass += 'rotatePullLeft ay-page-delay180';
      break;
    case 44:
      outClass += 'rotatePushTop';
      inClass += 'rotatePullBottom ay-page-delay180';
      break;
    case 45:
      outClass += 'rotatePushBottom';
      inClass += 'rotatePullTop ay-page-delay180';
      break;
    case 46:
      outClass += 'rotateFoldLeft';
      inClass += 'moveFromRightFade';
      break;
    case 47:
      outClass += 'rotateFoldRight';
      inClass += 'moveFromLeftFade';
      break;
    case 48:
      outClass += 'rotateFoldTop';
      inClass += 'moveFromBottomFade';
      break;
    case 49:
      outClass += 'rotateFoldBottom';
      inClass += 'moveFromTopFade';
      break;
    case 50:
      outClass += 'moveToRightFade';
      inClass += 'rotateUnfoldLeft';
      break;
    case 51:
      outClass += 'moveToLeftFade';
      inClass += 'rotateUnfoldRight';
      break;
    case 52:
      outClass += 'moveToBottomFade';
      inClass += 'rotateUnfoldTop';
      break;
    case 53:
      outClass += 'moveToTopFade';
      inClass += 'rotateUnfoldBottom';
      break;
    case 54:
      outClass += 'rotateRoomLeftOut ay-page-ontop';
      inClass += 'rotateRoomLeftIn';
      break;
    case 55:
      outClass += 'rotateRoomRightOut ay-page-ontop';
      inClass += 'rotateRoomRightIn';
      break;
    case 56:
      outClass += 'rotateRoomTopOut ay-page-ontop';
      inClass += 'rotateRoomTopIn';
      break;
    case 57:
      outClass += 'rotateRoomBottomOut ay-page-ontop';
      inClass += 'rotateRoomBottomIn';
      break;
    case 58:
      outClass += 'rotateCubeLeftOut ay-page-ontop';
      inClass += 'rotateCubeLeftIn';
      break;
    case 59:
      outClass += 'rotateCubeRightOut ay-page-ontop';
      inClass += 'rotateCubeRightIn';
      break;
    case 60:
      outClass += 'rotateCubeTopOut ay-page-ontop';
      inClass += 'rotateCubeTopIn';
      break;
    case 61:
      outClass += 'rotateCubeBottomOut ay-page-ontop';
      inClass += 'rotateCubeBottomIn';
      break;
    case 62:
      outClass += 'rotateCarouselLeftOut ay-page-ontop';
      inClass += 'rotateCarouselLeftIn';
      break;
    case 63:
      outClass += 'rotateCarouselRightOut ay-page-ontop';
      inClass += 'rotateCarouselRightIn';
      break;
    case 64:
      outClass += 'rotateCarouselTopOut ay-page-ontop';
      inClass += 'rotateCarouselTopIn';
      break;
    case 65:
      outClass += 'rotateCarouselBottomOut ay-page-ontop';
      inClass += 'rotateCarouselBottomIn';
      break;
    case 66:
      outClass += 'rotateSidesOut';
      inClass += 'rotateSidesIn ay-page-delay200';
      break;
    case 67:
      outClass += 'rotateSlideOut';
      inClass += 'rotateSlideIn';
      break;
  }


  $currPage.one( self.animEndEventName, function() {
    self.endPage[0] = true;
    if( self.endPage[1] ) {
        self.onEndAnimation( currPage, nextPage, endOptions );
    }
  });

  $nextPage.one( self.animEndEventName, function() {
    self.endPage[1] = true;
    if( self.endPage[0] ) {
      self.onEndAnimation( currPage, nextPage, endOptions );
    }
  });

  currPage.setAttribute('data-animation-playing', outClass);
  nextPage.setAttribute('data-animation-playing', inClass);
  addClassEx(currPage, outClass);
  addClassEx(nextPage, inClass);

  if( !this.support ) {
      self.onEndAnimation( currPage, nextPage, endOptions );
  }
};

/**
 * Page change animation callback.
 *
 * @param currentPage
 * @param nextPage
 * @param reAnimate
 */
Pages.prototype.onEndAnimation = function( currentPage, nextPage, options ) {
  this.endPage[0] = this.endPage[1] = false;
  this.resetPage( currentPage, nextPage );
  this.isAnimating = false;

  if(options.reanimate) // Go for next animation
    this.startAnimation(options.reanimate);
  else {
      removeClassEx(document.body, "page-changing");

      $(this.wrapper).trigger("PageChangeEnd", {
          currentPage: currentPage,
          nextPage: nextPage
      });

      if(options.firstVisit)
          this._eFirstVisit(nextPage, true);

      if(!hasClass(nextPage, "loading")) {
          this.anchorHandler(this.location, false);
          var pageData = JSON.parse(nextPage.getAttribute('data-page'));
          this.updateHeader(this.location, pageData);
      }
  }
};

/**
 * Switch current page to next page.
 *
 * @param currentPage
 * @param nextPage
 */
Pages.prototype.resetPage = function( currentPage, nextPage ) {
  removeClass(currentPage, 'ay-current');
  removeClassEx(currentPage, currentPage.getAttribute('data-animation-playing'));
  removeClass(nextPage, 'ay-changing');
  addClass(nextPage, 'ay-current');
  removeClassEx(nextPage, nextPage.getAttribute('data-animation-playing'));
};

/**
 * Animation setter.
 *
 * @param number
 * @returns {*}
 */
Pages.prototype.setAnimation = function(number) {
  if(number <= 1 || number > 67)
    return console.error("Invalid animation number "+number);

  this.options.animation = number;
  return this;
};

//-----------------Events-----------------//
/**
 * Triggered just before/after page transaction completed in the first visit.
 *
 * @param page
 * @param after
 * @private
 * @fires Pages#FirstVisitAfter
 * @fires Pages#FirstVisitBefore
 */
Pages.prototype._eFirstVisit = function(page, after) {
    /**
     * @event Pages#FirstVisitAfter
     * @event Pages#FirstVisitBefore
     * @type Element
     */
    $(this.wrapper).trigger("FirstVisit" + (after ? 'After' : 'Before'), page);
};

Pages.prototype.getCurrentPage = function() {
    return this.cache.get(this.location);
};

//-----------------Page-Plugins-----------------//
Pages.prototype.register = function(callback, context) {
  callback.call(context, document);
  this.loadListeners.push([callback, context]);
};

Pages.prototype._runListeners = function(placeholder) {
  console.log(placeholder);
  console.log(this.loadListeners);
  this.loadListeners.forEach(function(item) {
    item[0].call(item[1], placeholder);
  })
};

//-----------------Cache-----------------//
function CacheObject(page, attributes) {
  this.page = page;
  if(!attributes) attributes = {};
  this.attributes = attributes;
  if(this.attributes.visited === undefined) {
    this.attributes.visited = false;
  }
}

CacheObject.prototype.getAttribute = function (id) {
  return this.attributes[id];
};

CacheObject.prototype.setAttribute = function (id, value) {
    this.attributes[id] = value;
    return this;
};

function PageCache(parent) {
  this.parent = parent;
  this._data = {};
}

PageCache.prototype.set = function(id, value) {
  var location = undefined;
  if(typeof id === "object" && id.constructor.name === "Location") {
    location = id;
    id = id.toID();
  }

  if(this._data[id] !== undefined) {
    this._data[id].page = value;
      value._cacheObject = this.data[id];
    return this;
  }

   value._cacheObject = this._data[id] = new CacheObject(value, {location: location});

  return this._data[id];
};

PageCache.prototype.get = function(id) {
  if(typeof id === "object" && id.constructor.name  === "Location")
    id = id.toID();

  console.log("get", this._data);

  if(this._data[id] === undefined)
    return undefined;

  return this._data[id].page;
};


PageCache.prototype.isset = function(id) {
  if(typeof id === "object" && id.constructor.name  === "Location")
    id = id.toID();

  return this._data[id] !== undefined;
};

//----------------Location-----------------

/**
 * Get origin of an URL
 *
 * @arg    {string} url — URL to match
 * @return {string} Origin of URL or `null`
 * @static
 */
function getOrigin(url) {
  var match = url.match(/(https?:\/\/[\w\-.]+)/);
  return match ? match[1].replace(/https?:\/\//, '') : null;
}

/**
 * Get pathname of an URL
 *
 * @arg    {string} url — URL to match
 * @return {string} Pathname of URL or `null`
 * @static
 */
function getPathname(url) {
  var match = url.match(/https?:\/\/.*?(\/[\w_\-./]+)/);
  return match ? match[1] : '/';
}

/**
 * Get anchor in an URL
 *
 * @arg    {string} url — URL to match
 * @return {string} Anchor in URL or `null`
 * @static
 */
function getAnchor(url) {
  var match = url.match(/(#.*)$/);
  return match ? match[1] : null;
}

/**
 * Get search in URL.
 *
 * @arg    {string} url — URL to match
 * @return {object} Search in URL formatted as an object or `null`
 * @static
 */
function getParams(url) {
  var match = url.match(/\?([\w_\-.=&]+)/);

  if (!match) {
    return null;
  }

  var search = match[1].split('&');
  var object = {};

  for (var i = 0; i < search.length; i++) {
    var part = search[i].split('=');
    var key = part[0];

    object[key] = part[1];
  }

  return object;
}

/**
 * Get information about the url.
 *
 * @param url {string}
 * @returns {{origin: string, pathname: string, anchor: string, params: Object}}
 */
function Location(url) {
  this.origin = getOrigin(url);
  this.pathname = getPathname(url);
  this.anchor = getAnchor(url);
  this.params = getParams(url);
  this.origin = getOrigin(url);
}


/**
 * Generate URL from location
 *
 * @see new Location
 * @returns {string}
 */
Location.prototype.toString = function() {
  var params = "";

  if(this.params !== null) {
    var paramKeys = Object.keys(this.params);
    if (paramKeys.length > 0) {
      params = "?";
      for (var k = 0; k < paramKeys.length; k++) {
        if (k !== 0) params += "&";
        params += paramKeys[k] + '=' + this.params[paramKeys[k]];
      }
    }
  }
  return this.pathname+params+(this.anchor || '');
};


/**
 * Get part of the url that will be used for caching and retrieval.
 * @see switchPage
 * @returns {string}
 */
Location.prototype.toID = function() {
  var params = "";

  if(this.params !== null) {
    var paramKeys = Object.keys(this.params);
    if (paramKeys.length > 0) {
      params = "?";
      for (var k = 0; k < paramKeys.length; k++) {
        if (k !== 0) params += "&";
        params += paramKeys[k] + '=' + this.params[paramKeys[k]];
      }
    }
  }

  return this.pathname+params;
};
