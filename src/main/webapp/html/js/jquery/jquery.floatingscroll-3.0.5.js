/*!
floating-scroll v3.0.5
https://amphiluke.github.io/floating-scroll/
(c) 2019 Amphiluke
*/
!function(t, i) {
    "object" == typeof exports && "undefined" != typeof module ? i(require("jquery")) : "function" == typeof define && define.amd ? define(["jquery"], i) : i((t = t || self).jQuery)
}(this, function(t) {
    "use strict";
    var i = {
        init: function(t) {
            var i = t.closest(".fl-scrolls-body");
            i.length && (this.scrollBody = i),
            this.container = t[0],
            this.visible = !0,
            this.initWidget(),
            this.updateAPI(),
            this.syncWidget(),
            this.addEventHandlers()
        },
        initWidget: function() {
            var i = this.widget = t("<div class='fl-scrolls'></div>");
            t("<div></div>").appendTo(i).css({
                width: this.container.scrollWidth + "px"
            }),
            i.appendTo(this.container)
        },
        addEventHandlers: function() {
            var i = this;
            (i.eventHandlers = [{
                $el: i.scrollBody || t(window),
                handlers: {
                    scroll: function() {
                        i.checkVisibility()
                    },
                    resize: function() {
                        i.updateAPI()
                    }
                }
            }, {
                $el: i.widget,
                handlers: {
                    scroll: function() {
                        i.visible && i.syncContainer(!0)
                    }
                }
            }, {
                $el: t(i.container),
                handlers: {
                    scroll: function() {
                        i.syncWidget(!0)
                    },
                    focusin: function() {
                        setTimeout(function() {
                            return i.syncWidget()
                        }, 0)
                    },
                    "update.fscroll": function(t) {
                        "fscroll" === t.namespace && i.updateAPI()
                    },
                    "destroy.fscroll": function(t) {
                        "fscroll" === t.namespace && i.destroyAPI()
                    }
                }
            }]).forEach(function(t) {
                var i = t.$el
                  , n = t.handlers;
                return i.bind(n)
            })
        },
        checkVisibility: function() {
            var t = this.widget
              , i = this.container
              , n = this.scrollBody
              , e = t[0].scrollWidth <= t[0].offsetWidth;
            if (!e) {
                var s = i.getBoundingClientRect()
                  , o = n ? n[0].getBoundingClientRect().bottom : window.innerHeight || document.documentElement.clientHeight;
                e = s.bottom <= o || s.top > o
            }
            this.visible === e && (this.visible = !e,
            t.toggleClass("fl-scrolls-hidden"))
        },
        syncContainer: function(t) {
            void 0 === t && (t = !1);
            !0 !== this.skipSyncContainer ? (this.skipSyncWidget = t,
            this.container.scrollLeft = this.widget[0].scrollLeft) : this.skipSyncContainer = !1
        },
        syncWidget: function(t) {
            void 0 === t && (t = !1);
            !0 !== this.skipSyncWidget ? (this.skipSyncContainer = t,
            this.widget[0].scrollLeft = this.container.scrollLeft) : this.skipSyncWidget = !1
        },
        updateAPI: function() {
            var i = this.widget
              , n = this.container
              , e = this.scrollBody
              , s = n.clientWidth
              , o = n.scrollWidth;
            i.width(s),
            e || i.css("left", n.getBoundingClientRect().left + "px"),
            t("div", i).width(o),
            o > s && i.height(i[0].offsetHeight - i[0].clientHeight + 1),
            this.syncWidget(),
            this.checkVisibility()
        },
        destroyAPI: function() {
            this.eventHandlers.forEach(function(t) {
                var i = t.$el
                  , n = t.handlers;
                return i.unbind(n)
            }),
            this.widget.remove(),
            this.eventHandlers = this.widget = this.container = this.scrollBody = null
        }
    };
    t.fn.floatingScroll = function(n) {
        return void 0 === n && (n = "init"),
        "init" === n ? this.each(function(n, e) {
            return Object.create(i).init(t(e))
        }) : i.hasOwnProperty(n + "API") && this.trigger(n + ".fscroll"),
        this
    }
    ,
    t(document).ready(function() {
        t("body [data-fl-scrolls]").floatingScroll()
    })
});