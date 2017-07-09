(function ($, Mustache) {
    var TEMPLATE = '<div>' +
            '<div class="wl-module wl-stream loading">' +
            '<div class="wl-module-inner" id="wl-trace">'
            +
            '<div class="loader">查询中...</div>'
            +
            '<ul></ul>' +
            '</div>' +
            '<div class="message" id="J-Msg"><p class="tips">以上部分信息来自第三方，您可以到官网查询最新物流信息</p></div>' +
            '</div>' +
            '</div>',
        ITEM_TEMPLATE = '{{#.}}<li style="line-height: 30px;"><span class="wl-stream-time">{{ftime}}</span><span class="wl-stream-text">{{context}}</span></li>{{/.}}\n',
    // 以下几个API 参数并不一样
    // URL = "http://www.kuaidi100.com/openapi/api_post.shtml", // 运单 API, 需要 KEY
        URL = 'http://www.kuaidi100.com/query',      // 首页使用的API, ?type=快递公司代码&postid=快递单号&id=1
    // URL = 'http://m.kuaidi100.com/query',// 手机API, 晕不支持JSONP ?type=快递公司代码&postid=快递单号&id=1
    // 状态, http://www.kuaidi100.com/openapi/api_post.shtml
        DEPARTURE = "0",   // 在途
        GOT = "1",         // 已收件
    // ISSUES = "2",      // 疑难件
        SIGNED = "3",      // 已签收
    // RETURN = "4",      // 退签
        SENT_SCAN = "5",     // 到达, 正在派件
        RETURNED = "6",    // 退回中
        ARRIVAL,           // 到达城市
        SENT_CITY,         // 到达转发/发送城市
        OTHER,             // 其他

        KEY = '1';


// ----------------

    function KD100(opts) {
        this.opts = opts || {};
    }

    $.extend(KD100.prototype, {
        fetchData: function (opts, callback) {
            var me = this,
                data = $.extend({
                    id: KEY/*,
                    type: 'quanfengkuaidi',
                    postid: '123456'
                    */
                }, me.opts, opts || {});
            debugger;

            $.ajax({
                url: URL,
                method: 'get',
                dataType: 'jsonp',
                data: data
            }).done(function (data) {
                callback(null, data.data);
            });
        }
    });

    function WlView($dom, adapter) {
        var me = this;
        me.$view = $dom.find('.wl-module');
        me.adapter = me.adapter;
    }

    $.extend(WlView.prototype, {
        setLoading: function (loading) {
            var $view = this.$view;
            loading = undefined === loading ? 1 : loading;
            if (loading && !$view.hasClass('loading')) {
                $view.addClass('loading')
            } else {
                $view.removeClass('loading');
            }
        },
        showWl: function (data) {
            var me = this,
                $container = me.$view.find('ul');

            me.setLoading(0);

            data = data || [];
            data.sort(function (a, b) {
                return a.time === b.time ? 0 : a.time < b.time ? -1 : 1
            });
            if (data.length) {
                $container.html(Mustache.render(ITEM_TEMPLATE, data))
                    .children('li:last')
                    .addClass('last active');
            } else {
                $container.html(Mustache.render(ITEM_TEMPLATE, {
                    // ftime: '额,没有查询到相关记录'
                    ftime: '暂无物流信息'
                }));
            }
        }
    });

    $('.js-wl-mod[data-postno]').each(function (i, item) {
        var $self = $(item);

        var adapter = new KD100({
                type: $self.data('cp'),
                postid: $self[0].getAttribute('data-postno')//$self.data('postno')
            }),
            $dom = $(TEMPLATE),
            $wlMod = $dom.find('.wl-module'),
            wlView = new WlView($dom, null);

        $wlMod.appendTo($self);
        adapter.fetchData({}, function (err, data) {
            // 只取最新10条 (默认时间倒序)
            // data = data.length > 10 ? data.slice(0, 10) : data;
            wlView.showWl(data);
        });
    });
})(jQuery, Mustache);
