var AYKENTECH = AYKENTECH || {};

function fadeIn(el) {
  el.style.opacity = 0;

  var last = +new Date();
  var tick = function() {
    el.style.opacity = +el.style.opacity + (new Date() - last) / 400;
    last = +new Date();

    if (+el.style.opacity < 1) {
      (window.requestAnimationFrame && requestAnimationFrame(tick)) || setTimeout(tick, 16);
    }
  };

  tick();
}


(function($) {
    "use strict";

    AYKENTECH.loader = {
        initialize: function() {
            this.pager();
            this.sidebar();
            this.timeline();
            this.studentCards();
            this.footer();
            $window.trigger("ay-loaded");
        },

        footer: function() {
            $pagesWrapper.on("PageChangeStart", function(e, data) {
                $footer.removeClass("visible").addClass("hidden");
            });
            $pagesWrapper.on("PageChangeEnd", function(e, data) {
                $footer.removeClass("hidden").addClass("visible");
            });
        },

        studentCards: function() {
            AYKENTECH.Pager.register(function(container) {
              var $container = $(container);
              AYKENTECH.studentCards = { sliders: $container.find(".student-slider") };

              var cardItem = AYKENTECH.studentCards.sliders.find(".student-card-1");
              cardItem.on("click", function () {
                  $(this).toggleClass("active");
                  $(this).find(".details").collapse($(this).hasClass("active") ? 'show' : 'hide');
              }).find(".details").collapse({
                    toggle: false
                });
              if(AYKENTECH.studentCards.sliders.length === 0)
                return;
              AYKENTECH.studentCards.sliders.on("init", function() {
                var that = this;
                setTimeout(function() {
                  fadeIn(that);
                });
              }).slick({
                dots: false,
                infinite: true,
                speed: 300,
                slidesToShow: 5,
                adaptiveHeight: true,
                pauseOnHover:true,
                responsive: [
                  {
                    breakpoint: 1921,
                    settings: { slidesToShow: 4 }
                  },
                  {
                    breakpoint: 1200,
                    settings: { slidesToShow: 3 }
                  },
                  {
                    breakpoint: 992,
                    settings: { slidesToShow: 2 }
                  },
                  {
                    breakpoint: 768,
                    settings: { slidesToShow: 1 }
                  }
                ]
              });

            });
            $pagesWrapper.on("FirstVisitBefore FirstVisitAfter", function(e, data) {
                $(data).find(".student-slider").slick('setPosition');
            });
        },

        /**
         * Timeline events using bootstrap collapse.
         */
        timeline: function() {
          AYKENTECH.Timeline = {
            elements: [],
            attach: function(container) {
              var $container = $(container);
              console.log($container);
              var elements = $container.find(".timeline.both-side, .timeline.single-side");
              elements.each(function() {
                  $(this).find("li").on("click", function () {
                    $(this).find('.desc').collapse('toggle');
                  }).find('.desc').attr('data-parent', ".timeline").collapse({
                    toggle: false
                  }).on('hidden.bs.collapse', function () {
                    $(this).parent().removeClass("active");
                  }).on('shown.bs.collapse', function () {
                    $(this).parent().addClass("active");
                  });
              });
              this.elements.concat(elements);
            }
          };

          // Register page change events
          AYKENTECH.Pager.register( AYKENTECH.Timeline.attach, AYKENTECH.Timeline );
        },

        pager: function() {
          AYKENTECH.Pager = new Pages();
          var animation = $pages.data('animation');
          if(animation !== undefined) {
              AYKENTECH.Pager.setAnimation(animation);
          }
        },

        sidebar: function() {
            $sidebarDropdown.find("a").on('click', function() {
                console.log("test clicked");
                if ( $(this).parent().hasClass("active")) {
                    $(this)
                        .next(".sidebar-submenu")
                        .slideUp(200);
                    $(this)
                        .parent()
                        .removeClass("active");
                } else {
                    $(".sidebar-submenu")
                        .slideUp(200).parent().removeClass('active');
                    $(this)
                        .next(".sidebar-submenu")
                        .slideDown(200);
                    $(this)
                        .parent()
                        .addClass("active");
                }
            });

            // close sidebar
          $sidebarWrapper.find("#close-sidebar").on("click", function() {
                $pageWrapper.toggleClass("toggled");
            });

            //show sidebar
          $sidebarWrapper.find("#show-sidebar").on("click", function() {
                $pageWrapper.toggleClass("toggled");
            });

          var linkObjects = $sidebarWrapper.find(".sidebar-menu ul").find("a"),
            links = [];
          for(var i = 0; i < linkObjects.length; i++) {
              links[i] = linkObjects[i].getAttribute('href');
          }
          $pages.on("LocationChange", function (e, location) {
              var lid = location.toID();
              linkObjects.parent().removeClass("active");
              for(var i = 0; i < links.length; i++) {
                  if(links[i] === lid){
                      console.log("activated..");
                      $(linkObjects[i]).parent().addClass("active");
                  }else {
                      $(linkObjects[i])
                          .next(".sidebar-submenu")
                          // .slideUp(200);
                  }

              }
          });

          new SimpleBar($sidebarContent[0]);
            if (screen.width >= 992) {
                $pages.on("click", function() {
                    if(!$pageWrapper.hasClass('toggled')) {
                      $pageWrapper.addClass("toggled");
                    }
                });
                $pagesWrapper.on("PageChangeStart", function(e, data) {
                    if(!$pageWrapper.hasClass('toggled')) {
                        $pageWrapper.addClass("toggled");
                    }
                });
            }
        }
    };

    var $window = $(window),
        body = $("body"),
        $pageWrapper = $(".page-wrapper"),
        $pages = $(".pages"),
        $pagesWrapper = $('.pages'),
        $sidebarWrapper = $(".sidebar-wrapper"),
        $sidebarContent = $(".sidebar-content"),
        $sidebarDropdown = $(".sidebar-dropdown"),
        $footer = $("footer");

    NProgress.configure({ parent: '.pages' });
})(jQuery);

$(function() {
  AYKENTECH.loader.initialize();
});
