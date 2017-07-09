(function($) {
    var DOC = document,
        DEFAULT = {
            gallery: null,
            bigImgWidth: 800,
            bigImgHeight: 800
        };

    function ImageZoom(opt) {
        var me = this;

        if (!(me instanceof ImageZoom)) {
            return new ImageZoom(opt);
        }
        opt = $.extend({}, DEFAULT, opt);

        var $gallery = me.$gallery = $(opt.gallery),
            bigImgWidth = me.bigImgWidth = opt.bigImgWidth,
            bigImgHeight = me.bigImgHeight = opt.bigImgHeight,
            $img = $gallery.find('img');

        if ($img.length < 1) {
            throw new Error("can\'t find image");
        }

        me._createLens($img[0]);
//        $gallery.on('mousemove mouseleave', $.proxy(me._onMouseMove, me));
        $(DOC).on('mousemove mouseleave', $.proxy(me._onMouseMove, me));
    }

    ImageZoom.prototype = {
        _createLens: function (img) {
            var me = this,
                $wrap = $('<span class="imagezoom-wrap"></span>').insertBefore(img),
                $icon = $('<span class="imagezoom-icon"></span>'),
                $lens = me.$lens = $('<span class="imagezoom-lens"></span>');
            $wrap.prepend(img).append($icon).append($lens);
        },
        _onMouseMove: function onMouseMove(e) {
            var me = this,
                $lens = me.$lens,
                x = e.pageX,
                y = e.pageY,

                bigImgWidth = me.bigImgWidth,
                bigImgHeight = me.bigImgHeight,
            // 视口大小
                $gallery = me.$gallery, //$('.gallery .gallery-inner a'),
                offset = $gallery.offset(),
                top = offset.top,
                left = offset.left,
                width = $gallery.width(),
                height = $gallery.height(),
            // 图片大小
                $img = $gallery.find('img'),
                imgOffset = $img.offset(),
                imgTop = imgOffset.top,
                imgLeft = imgOffset.left,
                imgWidth = $img.width(),
                imgHeight = $img.height(),
            // 缩放比例
                scaleH = bigImgWidth / imgWidth,
                scaleW = bigImgHeight / imgHeight,
            // 计算镜片大小: 视口 / 镜片 = 大图 / 小图
                lensWidth = imgWidth / scaleW,
                lensHeight = imgHeight / scaleH,
            // 镜片移动范围
                minLensTop = imgTop,
                maxLensTop = imgTop + imgHeight - lensHeight,
                minLensLeft = imgLeft,
                maxLensLeft = imgLeft + imgWidth - lensWidth,
            // 大图移动范围
                minBigImgTop = height - bigImgHeight,
                maxBigImgTop = 0,
                minBigImgLeft = width - bigImgWidth,
                maxBigImgLeft = 0,
            // 镜片位置
                lensOffsetTop,
                lensOffsetLeft,
            // 大图位置
                bigImageOffsetTop,
                bigImageOffsetLeft,
                src = $img.attr('jqimg'),
                $bigImg;

            // 在视口范围内
            // if (x > left && x < (left + width) && y > top && y < (top + height)) {
            if (src && x > imgLeft && x < (imgLeft + imgWidth) && y > imgTop && y < (imgTop + imgHeight)) {
                if (!me.$viewer) {
                    me.$viewer = $('<div class="imagezoom-viewer">'
                        + '<div>'
                        + '<img src="http://img.alicdn.com/bao/uploaded/i4/896588234/TB2AfNrcVXXXXbWXpXXXXXXXXXX_!!896588234.jpg">'
                        + '</div>' +
                        '</div>').appendTo(document.body);
                } else {
                }
                $bigImg = me.$viewer.find('img');
                $bigImg.attr('src', src);


                // 镜片位置, 鼠标位置为中心, 中心位置 - 高度/2 = 距离顶部位置
                lensOffsetTop = constrain(y - lensHeight / 2, minLensTop, maxLensTop);
                lensOffsetLeft = constrain(x - lensWidth / 2, minLensLeft, maxLensLeft);
                $lens.width(lensWidth).height(lensHeight).offset({ left: lensOffsetLeft, top: lensOffsetTop }).css({visibility: 'visible'});

                // 大图位置
                bigImageOffsetTop = constrain(-(y - imgTop) * scaleH + height / 2, minBigImgTop, maxBigImgTop);
                bigImageOffsetLeft = constrain(-(x - imgLeft) * scaleW + width / 2, minBigImgLeft, maxBigImgLeft);
                $bigImg.css({ left: bigImageOffsetLeft, top: bigImageOffsetTop });
                me.$viewer.width(width).height(height).offset({ left: left + width + 20, top: top }).css({visibility: 'visible'});
            } else {
                // 隐藏镜片和视口
                $lens.css({visibility: 'hidden' });
                me.$viewer && me.$viewer.css({visibility: 'hidden'});
            }
        }
    };

    /**
     * 获取某值在给定范围内的合法值
     * @param value 给定值
     * @param min   允许的最小值
     * @param max   允许的最大值
     * @returns {number}
     */
    function constrain(value, min, max) {
        return Math.min(Math.max(value, min), max);
    }

    $.fn.extend({
        'imagezoom': function(opt) {
            this.each(function(i, el) {
                var zoomer = $.data(el, 'zoomer');
                if (!zoomer) {
                    zoomer = new ImageZoom($.extend({gallery: this}, opt));
                    $.data(el, 'zoomer', zoomer);
                }
                return zoomer;
            });
        }
    });
})(jQuery);

