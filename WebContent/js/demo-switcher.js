var AYKENTECH = AYKENTECH || {};

(function($) {
    "use strict";

    AYKENTECH.demoSwitcher = {
        selectedTheme: "chiller",
        selectedBG: 1,
        menuItemBG: false,
        themes: {
            chiller:
                "#sidebar{background-color:#1d1d1d}.sidebar-footer,#sidebar .sidebar-menu > ul > li.active div,#sidebar .sidebar-menu ul li a i,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{background-color:#2b2b2b}#sidebar .sidebar-header,#sidebar .sidebar-menu,#sidebar .sidebar-search{border-top:1px solid #2b2b2b}#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{border-color:#2b2b2b;box-shadow:none}.sidebar-footer>div>a,#sidebar .sidebar-brand>a,#sidebar .sidebar-header .user-info .user-role,#sidebar .sidebar-header .user-info .user-status,#sidebar .sidebar-menu ul li a,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{color:#bdbdbd}.sidebar-footer>div>a:hover i,#sidebar .sidebar-brand>a:hover,#sidebar .sidebar-header .user-info,#sidebar .sidebar-menu > ul > li.active.active>a,#sidebar .sidebar-menu ul li:hover>a{color:#fff}.page-wrapper #close-sidebar{color:#bdbdbd}.page-wrapper #close-sidebar:hover,#sidebar > ul > li.active .sidebar-submenu li a:hover:before,#sidebar .sidebar-menu > ul > li.active.active a i,#sidebar .sidebar-search input.search-menu:focus+span,#sidebar ul li:hover a i{color:#fff}#sidebar .sidebar-menu .header-menu span{color:#6c7b88}.sidebar-footer{box-shadow:0 -1px 5px #131212;border-top:1px solid #3a3a3a}.sidebar-footer>div:first-child{border-left:none}.sidebar-footer>div:last-child{border-right:none}.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger.mCSB_dragger_onDrag .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger:hover .mCSB_dragger_bar{background-color:#636363}.mCSB_scrollTools .mCSB_draggerRail{background-color:transparent}.sidebar-bg#sidebar::before{background-color:rgba(29,29,29,.8)}.sidebar-bg#sidebar .sidebar-header,.sidebar-bg#sidebar .sidebar-menu,.sidebar-bg#sidebar .sidebar-search{border-top:1px solid rgba(255,255,255,.1)}.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{border-color:rgba(255,255,255,.2);box-shadow:none}.sidebar-bg#sidebar .sidebar-menu > ul > li.active div,.sidebar-bg#sidebar .sidebar-menu ul li a i,.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{background-color:rgba(255,255,255,.1)}.sidebar-bg.sidebar-footer{background-color:rgba(43,43,43,.5);box-shadow:0 -1px 5px rgba(0,0,0,.28);border-top:1px solid rgba(255,255,255,.058823529411764705)}",
            ice:
                "#sidebar{background-color:#2B3A42}.sidebar-footer,#sidebar .sidebar-menu > ul > li.active div,#sidebar .sidebar-menu ul li a i,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{background-color:#3a4d56}#sidebar .sidebar-header,#sidebar .sidebar-menu,#sidebar .sidebar-search{border-top:1px solid #3a4d56}#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{border-color:#3a4d56;box-shadow:none}.sidebar-footer>div>a,#sidebar .sidebar-brand>a,#sidebar .sidebar-header .user-info .user-role,#sidebar .sidebar-header .user-info .user-status,#sidebar .sidebar-menu ul li a,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{color:#bdd4de}.sidebar-footer>div>a:hover i,#sidebar .sidebar-brand>a:hover,#sidebar .sidebar-header .user-info,#sidebar .sidebar-menu > ul > li.active.active>a,#sidebar .sidebar-menu ul li:hover>a{color:#EFEFEF}.page-wrapper #close-sidebar{color:#bdd4de}.page-wrapper #close-sidebar:hover,#sidebar > ul > li.active .sidebar-submenu li a:hover:before,#sidebar .sidebar-menu > ul > li.active.active a i,#sidebar .sidebar-search input.search-menu:focus+span,#sidebar ul li:hover a i{color:#EFEFEF}.sidebar-bg#sidebar .sidebar-menu .header-menu span,#sidebar .sidebar-menu .header-menu span{color:rgba(189,212,222,.5)}.sidebar-footer{border-top:1px solid #495d67}.sidebar-footer>div:first-child{border-left:none}.sidebar-footer>div:last-child{border-right:none}.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger.mCSB_dragger_onDrag .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger:hover .mCSB_dragger_bar{background-color:#8998a5}.mCSB_scrollTools .mCSB_draggerRail{background-color:transparent}.sidebar-bg#sidebar::before{background-color:rgba(2,19,25,.8)}.sidebar-bg#sidebar .sidebar-header,.sidebar-bg#sidebar .sidebar-menu,.sidebar-bg#sidebar .sidebar-search{border-top:1px solid rgba(58,77,86,.5)}.sidebar-bg#sidebar .sidebar-menu > ul > li.active div,.sidebar-bg#sidebar .sidebar-menu ul li a i,.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{background-color:rgba(58,77,86,.4)}.sidebar-bg.sidebar-footer{background-color:rgba(58,77,86,.3);border-top:1px solid rgba(73,93,103,.3)}",
            cool:
                "#sidebar{background-color:#38373D}.sidebar-footer,#sidebar .sidebar-menu > ul > li.active div,#sidebar .sidebar-menu ul li a i,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{background-color:#504E57}#sidebar .sidebar-header,#sidebar .sidebar-menu,#sidebar .sidebar-search{border-top:1px solid #504E57}#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{border-color:#504E57;box-shadow:none}.sidebar-footer>div>a,#sidebar .sidebar-brand>a,#sidebar .sidebar-header .user-info .user-role,#sidebar .sidebar-header .user-info .user-status,#sidebar .sidebar-menu ul li a,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{color:#918F9E}.sidebar-footer>div>a:hover i,#sidebar .sidebar-brand>a:hover,#sidebar .sidebar-header .user-info,#sidebar .sidebar-menu > ul > li.active.active>a,#sidebar .sidebar-menu ul li:hover>a{color:#b3b8c1}.page-wrapper #close-sidebar{color:#918F9E}.page-wrapper #close-sidebar:hover{color:#b3b8c1}#sidebar > ul > li.active .sidebar-submenu li a:hover:before,#sidebar .sidebar-menu > ul > li.active.active a i,#sidebar .sidebar-search input.search-menu:focus+span,#sidebar ul li:hover a i{color:#EFEFEF}#sidebar .sidebar-menu .header-menu span{color:#5B5963}.sidebar-footer{box-shadow:0 -1px 5px #2a292d;border-top:1px solid #5B5963}.sidebar-footer>div{border-left:1px solid #38373D;border-right:1px solid #5B5963}.sidebar-footer>div:first-child{border-left:none}.sidebar-footer>div:last-child{border-right:none}.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger.mCSB_dragger_onDrag .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger:hover .mCSB_dragger_bar{background-color:#918F9E}.sidebar-bg#sidebar::before{background-color:rgba(47,47,47,.8)}.sidebar-bg#sidebar .sidebar-header,.sidebar-bg#sidebar .sidebar-menu,.sidebar-bg#sidebar .sidebar-search{border-top:1px solid rgba(128,128,128,.5)}.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{border-color:rgba(138,137,144,.6)}.sidebar-bg#sidebar .sidebar-menu > ul > li.active div,.sidebar-bg#sidebar .sidebar-menu ul li a i,.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{background-color:rgba(105,105,105,.5)}.sidebar-bg#sidebar .sidebar-menu .header-menu span{color:#8e8d90}.sidebar-bg.sidebar-footer{background-color:rgba(80,78,87,.5)}",
            light:
                "#sidebar{background-color:#F1F0ED;border-right:1px solid #A4A29E}#sidebar .sidebar-brand,#sidebar .sidebar-header,#sidebar .sidebar-search{border-bottom:1px solid #A4A29E}#sidebar .sidebar-header,#sidebar .sidebar-menu,#sidebar .sidebar-search{border-top:1px solid #fff}#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{border-color:#c1c1c1;box-shadow:none}.sidebar-footer>div>a,#sidebar .sidebar-brand>a,#sidebar .sidebar-header .user-info .user-role,#sidebar .sidebar-header .user-info .user-status,#sidebar .sidebar-menu ul li a,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{color:#74726E}.sidebar-footer>div>a:hover i,#sidebar .sidebar-brand>a:hover,#sidebar .sidebar-header .user-info,#sidebar .sidebar-menu > ul > li.active.active>a,#sidebar .sidebar-menu ul li:hover>a{color:#302F2C}.page-wrapper #close-sidebar{color:#74726E}.page-wrapper #close-sidebar:hover{color:#302F2C}#sidebar > ul > li.active .sidebar-submenu li a:hover:before,#sidebar .sidebar-menu > ul > li.active.active a i,#sidebar .sidebar-search input.search-menu:focus+span,#sidebar ul li:hover a i{color:#00a9fd}#sidebar .sidebar-menu > ul > li.active div,#sidebar .sidebar-menu ul li a i,#sidebar .sidebar-search .input-group-text,#sidebar .sidebar-search input.search-menu{background-color:#fff}#sidebar .sidebar-menu .header-menu span{color:#A4A29E}.sidebar-footer{background-color:#f1f0ed;border-top:1px solid #A4A29E}.sidebar-footer>div{border-left:1px solid #A4A29E;border-right:1px solid #fff}.sidebar-footer>div:first-child{border-left:none}.sidebar-footer>div:last-child{border-right:none}.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger.mCSB_dragger_onDrag .mCSB_dragger_bar,.mCSB_scrollTools .mCSB_dragger:hover .mCSB_dragger_bar{background-color:#c1c1c1}.mCSB_scrollTools .mCSB_draggerRail{background-color:transparent}.sidebar-bg#sidebar::before{background-color:rgba(241,240,237,.8)}.sidebar-bg#sidebar .sidebar-header,.sidebar-bg#sidebar .sidebar-menu,.sidebar-bg#sidebar .sidebar-search{border-top:1px solid #fff}.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{border-color:#c1c1c1}.sidebar-bg#sidebar .sidebar-menu > ul > li.active div,.sidebar-bg#sidebar .sidebar-menu ul li a i,.sidebar-bg#sidebar .sidebar-search .input-group-text,.sidebar-bg#sidebar .sidebar-search input.search-menu{background-color:rgba(255,255,255,.5)}.sidebar-bg#sidebar .sidebar-menu .header-menu span{color:#696969}.sidebar-bg.sidebar-footer{background-color:rgba(241,240,237,.8);border-top:1px solid #A4A29E}"
        },
        sidebarBG: [
            "", // no bg,
            "img/sidebar/bg1.jpg",
            "img/sidebar/bg2.jpg",
            "img/sidebar/bg3.jpg",
            "img/sidebar/bg4.jpg"
        ],
        noMenuBackground: ".sidebar-bg#sidebar .sidebar-menu ul li a i { background:none; }",
        loader: function() {
            if ($container.length === 0) return false;
            self.loadEvents();
            self.updateTheme();
        },

        loadEvents: function() {
            $("#styleSwitcherOpen").on("click", function(b) {
                b.preventDefault();
                $container.toggleClass("active");
            });

            $themes.on("click", function() {
                var result = self.updateTheme($(this).attr("data-theme"));

                $themes.removeClass("selected");
                if (result) {
                    $(this).addClass("selected");
                }
            });

            // switch between background images
            $bgImages.on("click", function() {
                $bgImages.removeClass("selected");
                $(this).addClass("selected");
                self.updateTheme(null, $(this).attr("data-bg"));
            });

            // toggle background image
            $("#toggle-bg").change(function(e) {
                e.preventDefault();
                console.log(this.value);
                $sidebarWrapper.toggleClass("sidebar-bg");
            });
            // toggle background image
            $("#menu-background").change(function(e) {
                e.preventDefault();
                self.menuItemBG = $(this).is(":checked");
                console.log(self.menuItemBG);
                self.updateTheme();
            });

            var pageClasses = $pages.attr("class").split(' ');
            for(var i = 0; i < pageClasses.length; i++) {
                var c = pageClasses[i];

                if(c.substr(0, spinnerPrefix.length) === spinnerPrefix) {
                    var num = parseInt(c.substr(spinnerPrefix.length));
                    console.log("num",num);
                    $preloader.removeClass('selected');
                    $('[data-loader="'+num+'"]').addClass('selected');
                }
            }


            $preloader.on("click", function() {
                var $this = $(this);
                for(var i = 0; i <= 9; i++) {
                  $pages.removeClass(spinnerPrefix+(i));
                }
                $pages.addClass(spinnerPrefix+$this.data('loader'));
                $preloader.removeClass("selected");
                $this.addClass('selected');
            });
            $(".getCSS").on("click", function() {
                $("#demoCSS").val($themeCSS.html());
            });

          AYKENTECH.Pager.reloadPages();
        },

        generateBackgroundCSS: function(bg) {
            if (this.sidebarBG[bg] === undefined || this.sidebarBG[bg] === "")
                return "";

            return (
                ".sidebar-bg#sidebar { background-image: url(" +
                this.sidebarBG[bg] +
                "); }"
            );
        },

        updateTheme: function(theme, bg) {
            if (theme === null || theme === undefined)
                theme = self.selectedTheme;
            else self.selectedTheme = theme;
            if (bg === null || bg === undefined) bg = self.selectedBG;
            else self.selectedBG = bg;

            if (self.themes[theme] === undefined) return false;
            if ($themeCSS === null) {
                $themeCSS = $("<style id='themeCSS''></style>");
                $head.append($themeCSS);
            }
            console.log(theme, bg);
            $themeCSS.html(
                this.themes[theme] +
                    this.generateBackgroundCSS(bg) +
                    (this.menuItemBG ? "" : this.noMenuBackground)
            );
            return true;
        }
    };

    $(window).on("ay-loaded", function () {
        AYKENTECH.Pager.register( function (container) {
            var $container = $(container);
            var loadingPage = $container.find("#loading-page");
            if(loadingPage.length > 0) {
                var maxTries = 100;
                var interval = setInterval(function () {
                    var wrappers = loadingPage.parent().find(".loader-wrapper");
                    if(wrappers.length > 1) {
                        loadingPage.parent().addClass("loading").removeClass("loaded");
                        wrappers.first().remove();
                        clearInterval(interval);
                    } else if(loadingPage.parent().hasClass("loaded")) {
                        loadingPage.parent().addClass("loading").removeClass("loaded");
                        clearInterval(interval);
                    } else if(maxTries <= 0) {
                        clearInterval(interval);
                    }
                    maxTries--;
                }, 100);
            }
        });
    });

    var $sidebarWrapper = $("#sidebar"),
        $pages = $(".pages"),
        $head = $("head"),
        $container = $("#demo-switcher"),
        $themeCSS = null,
        $themes = $("[data-theme]"),
        $bgImages = $("[data-bg]"),
        $preloader = $(".page-loader"),
        spinnerPrefix = 'ay-spinner',
        self = AYKENTECH.demoSwitcher;
})(jQuery);
$(function() {
  AYKENTECH.demoSwitcher.loader();
});


//$("#demoCSS").val(themeData[selectedTheme] + generateBackgroundCSS(selectedBG));
