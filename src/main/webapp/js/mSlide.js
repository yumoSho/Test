(function ($) {
    /*
       <div >
            <ul>
                <li></li>
                 <li></li>
                 <li></li>
                 <li></li>
            </ul>
       </div>
     */
    function mSlide(opts) {
        var me = this;

        var $container = me.$container = $(opts), //$('.JQ-slide:first'),
            $wrap = me.$wrap = $container.find('ul'),
            $elements = me.$elements = $wrap.children('li'),
            pos;

        me.index = 0;
        me.running = false;
        // 设置ul 宽度 为 li 总宽
        $wrap.width($elements.outerWidth(true) * $elements.length);
        pos = $wrap.css('position');

        if (!pos || 'static' == pos) {
            $wrap.css('position', 'relative');
        }

        me._();
        $container.siblings('.next').click($.proxy(me.next, me));
        $container.siblings('.prev').click($.proxy(me.prev, me));
    }

    $.extend(mSlide.prototype, {
        next: function next() {
            var me = this,
                $container = me.$container,
                $wrap = me.$wrap,
                $elements = me.$elements,
                pos;
            if (!me.running && me.hasNext()) {
                me.running = true;
                pos = $elements.eq(++me.index).position();
                me.$container.stop().animate({
                    scrollLeft: pos.left
                }, 200, function () {
                    me.running = false;
                    me._();
                })
            }
        },
        prev: function prev() {
            var me = this,
                $container = me.$container,
                $elements = me.$elements,
                pos;
            if (!me.running && me.hasPrev()) {
                me.running = true;
                pos = $elements.eq(--me.index).position();
                $container.stop().animate({
                    scrollLeft: pos.left
                }, 200, function () {
                    me.running = false;
                    me._();
                })
            }
        },
        hasPrev: function hasPrev() {
            var me = this,
                $container = me.$container,
                $elements = me.$elements;
            return $elements.length > 0 && $elements.first().position().left < $container.scrollLeft();
        },
        hasNext: function hasNext() {
            var me = this,
                $container = me.$container,
                $elements = me.$elements;
            return $elements.length > 0 && $elements.last().position().left >= $container.width() + $container.scrollLeft();
        },
        _: function v() {
            var me = this,
                $container = me.$container;
            if (me.hasPrev()) {
                $container.siblings('.prev').removeClass('disabled');
            } else {
                $container.siblings('.prev').addClass('disabled');
            }
            if (me.hasNext()) {
                $container.siblings('.next').removeClass('disabled');
            } else {
                $container.siblings('.next').addClass('disabled');
            }
        }
    });

    $.extend($.fn, {
        mSlide: function (opts) {
            var a = [];
            $.each(this, function (i, el) {
                var slider = $.data(el, 'mSlider');

                if (false === opts) {
                    a.push(slider);
                    return slider;
                }
                if (!slider) {
                    slider = new mSlide(el);
                    $.data(el, 'mSlider', slider);
                }
                a.push(slider);
                return slider;
            });
            return $(a);
        }
    })
})(jQuery);
