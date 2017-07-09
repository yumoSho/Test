$(function(){
    var link = document.createElement('link');link.rel='shortcut icon';link.type='image/x-icon';link.href=contextPath + '/images/admin/favicon.ico';link.media='screen';document.head.appendChild(link);
});
(function ($, window) {
    window.Besture = window.Besture || {};
    window.Besture.Messager = {
        alert: function (title, msg) {
            var msg = $.isArray(msg) ? msg : [msg],
                $title = $('<div>').addClass('cms-pop-title').append($('<b>').html(title)),
                $close = $('<a>').addClass('icon-close').attr('href', 'javascript:;').click(function () {
                    $mask.hide();
                    $dialog.hide().remove();
                }),
                $btns = $('<div>').addClass('cms-pop-btns'),
                $content = $('<div>').addClass('cms-pop-content')
                    // .css({ margin: 'auto auto 40px' })
                    .css({ margin: 'auto auto 20px' })
                    .append($('<s class="icon-edit-alert"></s>'))
                    .append($('<h5>').html(msg[0]))
                    .append($('<p>').html(msg[1])),
                $body = $('<div>').addClass('cms-pop-body')
                    // .css({padding: '60px 110px'})
                    .css({padding: '25px 65px'})
                    .append($content)
                    .append($btns),
                $dialog = $('<div>').addClass('cms-pop')
                    .css({
                        width: 'auto',
                        height: 'auto',
                        display: 'none'
                    })
                    .append($('<div>').addClass('cms-pop-t').append($title).append($close))
                    .append($body),
                $mask = $('.pop-mask');


            $('<span>').addClass('btn-sure-wrap').append($('<button>').addClass('btn-sure').html('确定').click(function () {
                $mask.hide();
                $dialog.hide().remove();
            })).appendTo($btns);

            $dialog.appendTo(document.body);
            if ($mask.length < 1) {
                $mask = $('<div class="pop-mask">').appendTo(document.body);
            }
            $mask.height(document.height());
            $mask.show();
            $dialog.show();
        },

        confirm: function (title, msg, callback) {
            var msg = $.isArray(msg) ? msg : [msg],
                $title = $('<div>').addClass('cms-pop-title').append($('<b>').html(title)),
                $close = $('<a>').addClass('icon-close').attr('href', 'javascript:;').click(function () {
                    $.isFunction(callback) && callback(false);
                    $mask.hide();
                    $dialog.hide().remove();
                }),
                $btns = $('<div>').addClass('cms-pop-btns'),
                $content = $('<div>').addClass('cms-pop-content')
                    // .css({ margin: 'auto auto 40px' })
                    .css({ margin: 'auto auto 20px' })
                    .append($('<s class="icon-edit-alert"></s>'))
                    .append($('<h5>').html(msg[0]))
                    .append($('<p>').html(msg[1])),
                $body = $('<div>').addClass('cms-pop-body')
                    // .css({padding: '60px 110px'})
                    .css({padding: '25px 65px'})
                    .append($content)
                    .append($btns),
                $dialog = $('<div>').addClass('cms-pop')
                    .css({
                        width: 'auto',
                        height: 'auto',
                        display: 'none'
                    })
                    .append($('<div>').addClass('cms-pop-t').append($title).append($close))
                    .append($body),
                $mask = $('.pop-mask');


            $('<span>').addClass('btn-sure-wrap').append($('<button>').addClass('btn-sure').html('确定').click(function () {
                $.isFunction(callback) && callback(true);
                $mask.hide();
                $dialog.hide().remove();
            })).appendTo($btns);

            $('<span>').addClass('btn-cancel-wrap').append($('<button>').addClass('btn-cancel').html('取消').click(function () {
                $.isFunction(callback) && callback(false);
                $mask.hide();
                $dialog.hide().remove();
            })).appendTo($btns);

            $dialog.appendTo(document.body);
            if ($mask.length < 1) {
                $mask = $('<div class="pop-mask">').appendTo(document.body);
            }

            $mask.show();
            $dialog.show();
        },
        tips: function (title, msg) {
            var msg = $.isArray(msg) ? msg : [msg],
                $title = $('<div>').addClass('cms-pop-title').append($('<b>').html(title)),
                $close = $('<a>').addClass('icon-close').attr('href', 'javascript:;').click(function () {
                    $mask.hide();
                    $dialog.hide().remove();
                }),
                $btns = $('<div>').addClass('cms-pop-btns'),
                $content = $('<div>').addClass('cms-pop-content')
                    // .css({ margin: 'auto auto 40px' })
                    .css({ margin: 'auto auto 20px', padding: '0', width: '400px' })
                    .append($('<h5>').html(msg[0]))
                    .append($('<p>').html(msg[1])),
                $body = $('<div>').addClass('cms-pop-body')
                    // .css({padding: '60px 110px'})
                    .css({padding: '25px 65px'})
                    .append($content)
                    .append($btns),
                $dialog = $('<div>').addClass('cms-pop')
                    .css({
                        width: 'auto',
                        height: 'auto',
                        display: 'none',
                        margin: '-15% 0 0 -20%'
                    })
                    .append($('<div>').addClass('cms-pop-t').append($title).append($close))
                    .append($body),
                $mask = $('.pop-mask');


            $('<span>').addClass('btn-sure-wrap').append($('<button>').addClass('btn-sure').html('确定').click(function () {
                $mask.hide();
                $dialog.hide().remove();
            })).appendTo($btns);

            $dialog.appendTo(document.body);
            if ($mask.length < 1) {
                $mask = $('<div class="pop-mask">').appendTo(document.body);
            }

            $mask.show();
            $dialog.show();
        }
    };

})(jQuery, window);
//
(function ($, cP) {
    // warn: read config from cookie first
    $.extend(cP, {
        mode: 'B',
        size: 2,
        allowResize: false,
        orientation: ['bottom', 'left'],
        offsetX: 120,
        offsetY: -160
    });
    $(function () {
        $('body').on('dblclick', '.cPSkin.S span.cPSL4', function () {
            $(cP.cP).hide();
        });
    });
})(jQuery, colorPicker);

(function ($) {
    $.fn.tabs && $.extend($.fn.tabs.defaults, {
        onBeforeClose: function (title, index) {
            var tab = $(this).tabs('getTab', index),
                destroyIds = tab['destroyIds'] || [];
            $.each(destroyIds, function (i, id) {
                $('#' + id).remove();
            });
        }
    });

    $.fn.dialog && $.extend($.fn.dialog.defaults, {
        title: '提示'
    });
    $.fn.panel && $.extend($.fn.panel.defaults, {

        onBeforeDestroy: function () {
            $(this).html('').remove();
        }
    });
    $.fn.datagrid && $.extend($.fn.datagrid.defaults, {
        // fit : true,
        border: false,
        fitColumns: true,
        striped: true,
        rownumbers: true,
        pagination: true,
        pageSize: 15,
        pageList: [5, 10, 15, 20, 30, 40, 50],
        singleSelect: false,
        remoteFilter: true,
        remoteSort: true,
        multiSort: true,

        onLoadSuccess: function (data) {
            var me = this,
                $grid = $(me);

            // fix easyui don't clean selection on loaded
            $grid.datagrid('clearSelections');
            if (!data.rows || data.rows.length < 1) {
                var datagrid = $(this);
                datagrid.prev(".datagrid-view2").children(".datagrid-body").html(
                        "<div style='width:" + datagrid.prev(".datagrid-view2").find(".datagrid-header-row").width()
                        + "px;border:solid 0px;height:1px;'></div>");
            }

            // fix 存在图片时造成无法对齐
            $grid.datagrid('resize');
        }
    });

    // extention for remote validation
    $.fn.validatebox && $.extend($.fn.validatebox.defaults.rules, {
        ajax: {
            validator: function (value, param) {
                var me = this,
                    $form = $(me.form),
                    holderRe = /(?:{)([^{]+)(?=})/g,
                    holder, url, t;

                if ($.isArray(param)) {
                    param = param[0];
                }
                while (holder = holderRe.exec(param)) {
                    holder = holder[1];
                    t = '[name=' + holder + ']';
                    t = $('input' + t + ',textarea' + t + ',select' + t, $form);
                    if (t.length > 0) {
                        param = param.replace('{' + holder + '}', t.val());
                    }
                }
                t = param.indexOf('?');
                if (-1 < t) {
                    url = param.substring(0, t);
                    param = param.substring(t + 1);
                } else {
                    url = param;
                    param = {};
                }

                return "true" == $.ajax({
                    url: url,
                    dataType: 'json',
                    async: false,
                    cache: false,
                    type: 'post',
                    data: param
                }).responseText;
            },
            message: 'Invalid'
        }
    });

    // add getRecord method for easyui form
    $.fn.form && $.extend($.fn.form.methods, {
        getRecord: function (jq) {
            var record = {},
                $comps = jq.find('input:text:enabled[name],' +
                    'input:password:enabled[name],' +
                    // 'input:hidden:enabled[name],' + // 包含了不可见元素和 type=hidden
                    'input[type=hidden]:enabled[name],' +
                    'input:file:enabled[name],' +
                    'input:checked:enabled[name],' +
                    'select:enabled[name],' +
                    'textarea:enabled[name]');
            $comps.each(function (i, el) {
                // jq.find('input:enabled[name],textarea:enabled[name],select:enabled[name]').each(function
                // (i, el) {
                if (record[el.name]) {
                    if ($.isArray(record[el.name])) {
                        record[el.name].push(el.value);
                    } else {
                        record[el.name] = [ record[el.name], el.value ];
                    }
                } else {
                    record[el.name] = el.value;
                }
            });
            return record;
        }
    });

    if (!$.fn.form || !$.fn.form.methods) {
        return;
    }
    // fix radio load
    var oldLoad = $.fn.form.methods.load;
    $.fn.form.methods.load = function (jq, data) {
        var ret = oldLoad.apply(this, arguments);
        jq.find('input:enabled[type=radio]').each(function (i, e) {
            var checked = 'Y' == e.value || '1' == e.value;
            if (checked == data[e.name]) {
                $(e).attr('checked', true);
            } else {
                $(e).removeAttr('checked');
            }
        });
        return ret;
    };
})(jQuery);

var util = util || {};
$.extend(util, {
    formatDate: function (date) {
        if (!date) {
            return '';
        }

        if (typeof date === 'number') {
            date = new Date(date);
        }

        var y = date.getFullYear(),
            m = date.getMonth() + 1,
            d = date.getDate(),
            value = y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
        return value;
    },

    parseDate: function (value) {
        if (!value)
            return null;
        if (typeof value === 'string') {
            var s = value.split('-'),
                y = parseInt(s[0], 10),
                m = parseInt(s[1], 10),
                d = parseInt(s[2], 10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
                return new Date(y, m - 1, d);
            }
        }
        value = +value;
        if (!isNaN(value)) {
            return new Date(value);
        }
        return null;
    },

    /**
     * 解析后使用当前 formatter 格式化
     */
    fmtDate: function (v) {
        v = util.parseDate(v);
        return !v ? '' : $.fn.datebox.defaults.formatter(v);
    },

    fmtBoolean: function (v) {
        return v === null || v === undefined || v === '' ? v : (v === false ? 'N' : 'Y');
    }
});

$(function () {
    var url = window.location.href;
    var arr = url.split("/");
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == "admin") {
            var action = arr[i + 1];
            $('#' + action).addClass("on");
            //$('#'+action).attr("class","on");
        }
    }
});

/* utility js */
$(function () {
    // 表格列删除
    $(document).off('click', 'tr .js-del-row').on('click', 'tr .js-del-row', function (e) {
        var $self = $(this);
        Besture.Messager.confirm('提示', '您确定删除这行记录吗？', function (r) {
            r && $self.closest('tr').remove();
        });
    });

    // 表格 - 列统计
    $('tr .js-sum').each(function (i, el) {
        var me = this,
            $self = $(me),
            selector = $self.data('selector');

        if (!selector) return;

        $(document).off('blur', selector).on('blur', selector, function (e) {
            var count = 0;
            $(selector).each(function (i, el) {
                var $el = $(el),
                    tag = el.tagName.toLowerCase(),
                    val = '';

                if ('input' == tag || 'textarea' == tag || 'select' == tag) {
                    val = $el.val();
                } else {
                    val = $el.text();
                }
                count += Number(val) || 0;
            });
            $self.text(count);
        });
    });

});
//js工具类
(function($){
    function HgUtil(){
    }
    HgUtil.prototype = {
        numberFormat:function(number,len){
            return isNaN(number) ? 0 : Number(number).toFixed(len);
        },
        /*导出excel*/
        exportExcel:function($grid,url){
           $grid.jqGrid().trigger("triggerToolbar");
            var rowLen = $grid.getGridParam("reccount");
            if(rowLen > 0){
                var postData = $grid.jqGrid("getGridParam", "postData");
                var params = "";
                for (var p in postData) {
                    if (!(/__proto__/.test(p)) && postData.hasOwnProperty(p)) {
                        params += p + "=" + postData[p] + "&";
                    }
                }
                var prefix = url.indexOf('?') == -1 ? "?" : "&";
                params = prefix + params;
                window.open ( contextPath + url + params, "_blank");
            }else{
                layer.alert("当前没有数据可以导出", {
                    skin: 'layer-ext-message' //样式类名
                    , closeBtn: 1
                    , time: 3000
                    , title: '提示 [3秒后关闭]'
                    , shade: 0
                    , offset: 'rb'
                    , btn: ''
                });
            }
        }
    }
    window.HgUtil = new HgUtil();
})(jQuery);
