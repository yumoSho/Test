/**
 * update 20150425
 * <script type="text/javascript">
 *       var scanner = ScanView(document, function(barcode) {
 *              var me = this, $tr, $sum, goods, shelf;
 *              $tr = $('#product-list tr[data-barcode="' + barcode + '"]');
 *              if ($tr.length > 1) {
 *                  me.sendError('条码存在多个货架, 请手动选择货架后重新扫描')
 *              } else if ($tr.length < 1) {
 *                  me.sendError('警告:没有找到相应商品')
 *              } else {
 *                  $tr.siblings().removeClass('scan-item').end().addClass('scan-item');
 *                  $sum = $tr.find('input[name$=tempNum]');
 *                  $sum.val((parseInt($sum.val())|| 0) + 1)
 *
 *                  goods = $tr.find('td:nth-child(2) span').text();
 *              }
 *              $('#barcode').text(barcode || '');
 *              $('#goods').text(goods || '');
 *          $('#shelf').text(shelf || '');
 *      });
 *   scanner.pause();
 *   </script>
 *   ======================================================
 * 基于键盘的扫描枪工具类
 *  var scanner = BarcodeScanner({
 *      el: document,
 *      success: function(barcode) {
 *          $('#product-list tr').each(function(i, el) {
 *              var $el = $(el), $countEl, count;
 *
 *              $el.removeClass("scan-item");
 *              if(barcode == $el.attr('data-barcode')) {
 *                  $el.addClass("scan-item");
 *                  $countEl = $el.find('input:last');
 *                  count = parseInt($countEl.val()) || 0;
 *                  $countEl.val(count + 1);
 *              }
 *          });
 *      }
 *  });
 * scanner.resume();
 * TODO 状态区域
 */
(function ($, exports) {
    var DEFAULT = {
        scanTimeout: 2E2, /* 扫描超时时间(扫描枪两次按键最大时间间隔, 超时后清空当前扫描内容) */
        el: window.document, /* 监控元素 */
        success: $.loop, /* 扫描成功回调 */
        error: $.loop           /* 扫描失败回调 */
    };

    function BarcodeScanner(config) {
        var me = this;

        if (!(me instanceof BarcodeScanner)) {
            return new BarcodeScanner(config);
        }

        me.config = $.extend({}, DEFAULT, config || {});
        me._lastInputTime = new Date().getTime();
        me._barcode = '';
    }

    $.extend(true, BarcodeScanner.prototype, {
        resume: function () {
            var me = this;

            me.stop();
            $(me.config.el).on('keypress', function (e) {
                var now = new Date().getTime(),
                    code = e.keyCode,
                    ch = String.fromCharCode(code),
                    activeEl = document.activeElement,
                    timeout = false;

                timeout = now - me._lastInputTime > me.config.scanTimeout;
                me._lastInputTime = now;

                // 当前激活的是输入控件但不是目标 (手动输入)
                if (activeEl && me.config.el != activeEl && ('INPUT' == activeEl.nodeName || 'TEXTAREA' == activeEl.nodeName || 'SELECT' == activeEl.nodeName)) {
                    return;
                }

                // console.log(String.fromCharCode(e.charCode));
                // 如果超时, 清空当前扫描内容
                me._barcode = timeout ? ch : me._barcode + ch;

                // 如果回车 (扫描枪扫描结束)
                if (13 == code) {
                    if ('' === me._barcode) {
                        return;
                    }

                    me.config.success(me._barcode && me._barcode.trim().replace('\n', ''));
                    me._barcode = '';
                    e.stopPropagation();
                    return false;
                }
            });
        },
        stop: function () {
            $(this.config.el).off('keypress');
        }
    });

    exports.BarcodeScanner = BarcodeScanner;

    // update 20150425
    var STYLE = '' +
            '<style type="text/css">' +
            '.scan-tips {' +
            '   position: absolute;' +
            '   width: 700px;' +
            '   background: #FFF;' +
            '   top: 100px;' +
            '   left: 50%;' +
            '   margin-left: -350px;' +
            '   border: 1px solid #aaa;' +
            '   box-shadow: 0 5px 5px #aaa;' +
            '   border-bottom-left-radius: 5px;' +
            '   border-bottom-right-radius: 5px;' +
            '}' +
            '.scan-tips .tips-content {' +
            '   margin: 5px 10px 10px;' +
            '   position: relative;' +
            '}' +
            '.tips-content table { width: 100%; }' +
            '.tips-content table tr { line-height: 23px; border-bottom: 1px dashed #efefef; }' +
            '.tips-content .tips-pause, .tips-content .tips-scaning, .tips-content .tips-ambiguous { margin-top: 5px; background-color: #fef9f2; border: 1px solid #ccc; padding: 5px; font-size: 12px; text-align: center; color: #a0b937; display: none; } .tips-content .tips-pause { color: #c41411; } .scan-tips.scan-paused .tips-pause { display: block;; } .tips-content .tips-scaning { display: block; } .scan-tips.scan-paused .tips-scaning { display: none; }  .scan-tips.scan-error .tips-ambiguous { color: #f00; border-color: red; display: block; } ' +
            '</style>',
        A ='' +
            '<div class="scan-tips">' +
            '    <div class="dragger" style="position:absolute;top:0;left:0;width:100%;height:100%;"></div>' +
            '    <div class="tips-content">' +
            '        <table class="module-01 mobule-02">' +
            '              <tr>' +
            '                   <th width="10%"></th> ' +
            '                    <th width="30%">当前条码</th> ' +
            '                    <th width="30%">匹配商品</th> ' +
            '                    <th width="30%">匹配货架</th> ' +
            '              </tr>';
    B = '              <tr>' +
        '                    <td width="10%"> ' +
        '                        <input type="checkbox"> ' +
        '                    </td> ' +
        '                    <td id="barcode"></td> ' +
        '                    <td id="goods"></td> ' +
        '                    <td id="shelf"></td> ' +
        '              </tr>' ;
    C = '       </table>' +
        '        <div class="tips-ambiguous">条码存在多个货架, 请手动选择货架后重新扫描</div>' +
        '        <div class="tips-pause">暂停扫描, 点击开始/继续扫描</div>' +
        '        <div class="tips-scaning">正在扫描..</div>'+
        '    </div>' +
        '</div>';
    var TEMPLATE = A + B + C; /*'' +
     '<div class="scan-tips">' +
     '    <div class="dragger" style="position:absolute;top:0;left:0;width:100%;height:100%;"></div>' +
     '    <div class="tips-content">' +
     '        <table class="module-01 mobule-02">' +
     '              <thead>' +
     '              <tr>' +
     '                   <th width="10%"></th> ' +
     '                    <th width="30%">当前条码</th> ' +
     '                    <th width="30%">匹配商品</th> ' +
     '                    <th width="30%">匹配货架</th> ' +
     '              </tr>' +
     '              </thead>' +
     '              <tbody>' +
     '              <tr>' +
     '                    <td width="10%"> ' +
     '                        <input type="checkbox"> ' +
     '                    </td> ' +
     '                    <td id="barcode"></td> ' +
     '                    <td id="goods"></td> ' +
     '                    <td id="shelf"></td> ' +
     '              </tr>' +
     '              <tbody>' +
     '         </table>' +
     '        <div class="tips-ambiguous">条码存在多个货架, 请手动选择货架后重新扫描</div>' +
     '        <div class="tips-pause">暂停扫描, 点击开始/继续扫描</div>' +
     '        <div class="tips-scaning">正在扫描..</div>'+
     '    </div>' +
     '</div>';*/

    function ScanView(el, callback) {
        var me = this;

        if (!(me instanceof ScanView)) {
            return new ScanView(el, callback);
        }

        el = me.el = el || document;
        me.scanner = BarcodeScanner({
            el: el,
            success: function(barcode) {
                typeof callback == 'function' && callback.apply(me, arguments);
            }
        });

        if ($('#scan-style').length < 1) {
            $(STYLE).attr('id', 'scan-style').appendTo('head');
        }
        $(TEMPLATE).appendTo(document.body);

        $(window).on('focus', function() {
            if (!me.hasError()) {
                me.resume();
            }
        }).on('blur', function(evt) {
            evt = evt || window.event;
            if (window.ActiveXObject && /MSIE/.test(navigator.userAgent)) {  //IE
                //如果 blur 事件是窗口内部的点击所产生，返回 false, 也就是说这是一个假的 blur
                var x = evt.clientX;
                var y = evt.clientY;
                var w = document.body.clientWidth;
                var h = document.body.clientHeight;

                if (x >= 0 && x <= w && y >= 0 && y <= h) {
                    window.focus && window.focus();
                    return false;
                }
            }
            me.pause();
        });
        $(document).on('click', function() {
            if (!me.hasError()) {
                me.resume();
            }
        });
        me.pause(); // 初始暂停
        var $tips = $('.scan-tips');
        if ($tips.draggable) {
            $tips.draggable({
                containment: "window",
                handle: '.dragger'
            });
        }
        $tips.find('.tips-pause').css('cursor', 'pointer').click(function() {
            me.clearError();
            me.resume();
        })
    }

    ScanView.prototype = {
        constructor: ScanView,
        pause: function() {
            $('.scan-tips').addClass('scan-paused');
            this.scanner.stop();
        },
        resume: function() {
            var me = this;
            if (!me.hasError()) {
                $('.scan-tips').removeClass('scan-paused');
                this.scanner.resume();
                return true;
            }
            return false;
        },
        sendError: function(msg) {
            var me = this,
                i = 0,
                $tips = $('.scan-tips'),
                offset = $tips.offset();

            msg = msg || '未知错误';
            $tips.addClass('scan-error');
            $tips.find('.tips-ambiguous').html(msg);
            me.pause();
            if (me._timer) {
                me._timer && clearInterval(me._timer);
                delete me._timer;
                me._pos && $tips.offset(me._pos);
                offset = me._pos = $tips.offset();
            } else {
                me._pos = offset;
            }

            me._timer = setInterval(function () {
                i++;
                // 左右 10 个像素
                $tips.offset({
                    left: offset.left + ((i % 2) > 0 ? -10: 10),
                    top: offset.tip
                });
                if (i >= 10) {
                    clearInterval(me._timer);
                    delete me._timer;
                    $tips.offset(offset);
                }
            }, 30);
            //
        },
        hasError: function() {
            return $('.scan-tips').hasClass('scan-error');
        },
        clearError: function() {
            $('.scan-tips').removeClass('scan-error');
            this.pause();
        }
    };

    exports.ScanView = ScanView;
})(jQuery, window);