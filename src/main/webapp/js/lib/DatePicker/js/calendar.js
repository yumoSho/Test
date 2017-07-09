/**
 * 仿百度统计的日期区间选择插件，经测试，存在 bug
 * 已知bug:
 * 1. 会出现选择日期后，日期标签并未加上背景样式，但文本框内的数据正常，预计是 css 未能正确渲染；
 * 2. 选择整月时，获取数据失败，产生 undefined/NaN
 *
 * 待优化：
 * 1. 无法对无效数据进行过滤；
 * 2. 没有错误提示。
 */
(function ($) {
    $.calendarpanel = {
        _op: {
            calendarCount: 3,
            format: 'YYYY-MM-DD'
        },
        from: null,
        to: null,
        // Non-Leap year Month days..
        DOMonth: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
        // Leap year Month days..
        lDOMonth: [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
        date_from: null,    //json
        date_to: null,      //json
        gNow: null,
        gMonth: null,
        gYear: null,
        $source: null,
        op: null,     //new parameter
        initParam: function () {//initialize all parameter for calendar panel
            $.calendarpanel.date_from = null;
            $.calendarpanel.date_to = null;
            $.calendarpanel.from = null;
            $.calendarpanel.to = null;
            $("#sunlyBDaDateStart").val("");
            $("#sunlyBDaDateEnd").val("");
            this.gNow = new Date();
            if (!$.calendarpanel.$source.data("gYear")) {
                this.gMonth = new String(this.gNow.getMonth());
                this.gYear = new String(this.gNow.getFullYear().toString());
            } else {
                this.gMonth = $.calendarpanel.$source.data("gMonth");
                this.gYear = $.calendarpanel.$source.data("gYear");
                if ($.calendarpanel.$source.val() != '') {
                    var date = $.calendarpanel.$source.val().split("至");
                    $.calendarpanel.date_from = $.calendarpanel.data_format(date[0]);
                    $.calendarpanel.date_to = $.calendarpanel.data_format(date[1]);
                    $.calendarpanel.from = $.calendarpanel.getClickDate($.calendarpanel.date_from);
                    if ($.calendarpanel.date_to != null) {
                        $.calendarpanel.to = $.calendarpanel.getClickDate($.calendarpanel.date_to);
                    }
                    $.calendarpanel.setDateSection();
                }
            }
        },
        open: function (source, options) {
            $.calendarpanel.$source = $(source);
            $.calendarpanel.op = $.extend({}, $.calendarpanel._op, options);
            this.initParam();
            this.createCalendar(this.gYear, this.gMonth);
            this.bindingHandle();
            this.bindingDayClick();
            var offset = $.calendarpanel.$source.offset();
            var height = $.calendarpanel.$source.outerHeight();
            var clientB = (parseInt(document.body.clientHeight) - parseInt(offset.top + height));
            var panelDiv = $("#calendarContainerOOCYoZxq");
            var divH = (panelDiv.height());
            panelDiv.offset();
            panelDiv.show();
            if (clientB <= divH) {
                if (offset.top - divH < 0) {
                    panelDiv.offset({top: 0, left: offset.left});
                } else {
                    panelDiv.offset({top: offset.top - divH, left: offset.left});
                }
            } else {
                panelDiv.offset({top: offset.top + height, left: offset.left});
            }
        },
        setInputValue: function () {
            if (Number($.calendarpanel.from == null)) {
                return;
            }
            if (Number($.calendarpanel.from) == Number($.calendarpanel.to) || $.calendarpanel.to == null) {//from == to
                $.calendarpanel.$source.val($.calendarpanel.format_data($.calendarpanel.date_from.y, $.calendarpanel.date_from.m, $.calendarpanel.date_from.d));
            } else {
                $.calendarpanel.$source.val($.calendarpanel.format_data($.calendarpanel.date_from.y, $.calendarpanel.date_from.m, $.calendarpanel.date_from.d)
                    + "至" + $.calendarpanel.format_data($.calendarpanel.date_to.y, $.calendarpanel.date_to.m, $.calendarpanel.date_to.d));
            }
            //set current panel year and month
            $.calendarpanel.$source.data("gYear", $.calendarpanel.gYear);
            $.calendarpanel.$source.data("gMonth", $.calendarpanel.gMonth);
        },
        bindingHandle: function () {
            $("#DSPrevMonth").unbind("click").click(function () {
                $.calendarpanel.gMonth--;
                if ($.calendarpanel.gMonth < 0) {
                    $.calendarpanel.gMonth = 11;
                    $.calendarpanel.gYear--;
                }
                $.calendarpanel.renderContent();
            });
            $("#DSNextMonth").unbind("click").click(function () {
                $.calendarpanel.gMonth++;
                if ($.calendarpanel.gMonth > 11) {
                    $.calendarpanel.gMonth = 0;
                    $.calendarpanel.gYear++;
                }
                $.calendarpanel.renderContent();
            });
            $("#DSPrevYear").unbind("click").click(function () {
                $.calendarpanel.gYear--;
                $.calendarpanel.renderContent();
            });
            $("#DSNextYear").unbind("click").click(function () {
                $.calendarpanel.gYear++;
                $.calendarpanel.renderContent();
            });
            $("#CancelChangeDate").unbind("click").click(function () {
                $("#calendarContainerOOCYoZxq").hide();
            });
            $("#ChangeDate").unbind("click").click(function () {
                $.calendarpanel.setInputValue();
                $("#calendarContainerOOCYoZxq").hide();
            });
        },
        //this can repeat bind
        bindingDayClick: function () {
            $("#calengar-body td[sign=data]").unbind("click").click(function () {
                var _$ = $(this);
                if ($.calendarpanel.from == null) {
                    $.calendarpanel.date_from = {'d': _$.attr('d'), 'm': _$.attr('m'), 'y': _$.attr('y')};
                    $.calendarpanel.from = $.calendarpanel.getClickDate($.calendarpanel.date_from);
                    _$.removeClass().addClass('ds-date-selected');
                } else if ($.calendarpanel.to == null) {
                    $.calendarpanel.date_to = {'d': _$.attr('d'), 'm': _$.attr('m'), 'y': _$.attr('y')};
                    $.calendarpanel.to = $.calendarpanel.getClickDate($.calendarpanel.date_to);
                    //change from and to
                    if (Number($.calendarpanel.to) < Number($.calendarpanel.from)) {
                        $.calendarpanel.to = $.calendarpanel.from;
                        $.calendarpanel.date_to = $.calendarpanel.date_from;
                        $.calendarpanel.date_from = {'d': _$.attr('d'), 'm': _$.attr('m'), 'y': _$.attr('y')};
                        $.calendarpanel.from = $.calendarpanel.getClickDate($.calendarpanel.date_from);
                    }
                    $.calendarpanel.renderContent();
                } else {
                    $.calendarpanel.date_from = {'d': _$.attr('d'), 'm': _$.attr('m'), 'y': _$.attr('y')};
                    $.calendarpanel.from = $.calendarpanel.getClickDate($.calendarpanel.date_from);
                    $.calendarpanel.to = null;
                    $.calendarpanel.date_to = null;
                    $("#calengar-body .ds-date-selected").removeClass().addClass('ds-date-thismonth');
                    _$.removeClass().addClass('ds-date-selected');
                }
                $.calendarpanel.setDateSection();
            });
            //check one month
            $("#calengar-body div[sign=month]").unbind("click").click(function () {
                $("#calengar-body .ds-date-selected").removeClass().addClass('ds-date-thismonth');
                var $_c = $(this).next();
                $_c.find("[sign=data]").removeClass().addClass('ds-date-selected');
                var _$ = $_c.find(":last[sign=data]");
                $.calendarpanel.date_from = {'d': 1, 'm': _$.attr('m'), 'y': _$.attr('y')};
                $.calendarpanel.date_to = {'d': _$.attr('d'), 'm': _$.attr('m'), 'y': _$.attr('y')};
                $.calendarpanel.to = $.calendarpanel.getClickDate($.calendarpanel.date_to);
                $.calendarpanel.from = $.calendarpanel.getClickDate($.calendarpanel.date_from);
                $.calendarpanel.setDateSection();
            });
        },
        setDateSection: function () {
            if ($.calendarpanel.from != null) {
                $("#sunlyBDaDateStart").val($.calendarpanel.format_data($.calendarpanel.date_from.y, $.calendarpanel.date_from.m, $.calendarpanel.date_from.d));
            }
            if ($.calendarpanel.to != null) {
                $("#sunlyBDaDateEnd").val($.calendarpanel.format_data($.calendarpanel.date_to.y, $.calendarpanel.date_to.m, $.calendarpanel.date_to.d));
            } else {
                $("#sunlyBDaDateEnd").val($("#sunlyBDaDateStart").val());
            }
        },
        getClickDate: function (_$) {
            return '' + _$.y
                + (_$.m < 10 ? '0' + _$.m : _$.m)
                + (_$.d < 10 ? '0' + _$.d : _$.d);
        },
        renderContent: function () {
            m = $.calendarpanel.gMonth;
            y = $.calendarpanel.gYear;
            if (m >= $.calendarpanel.op.calendarCount) {
                m -= $.calendarpanel.op.calendarCount;
            } else {
                m = m - $.calendarpanel.op.calendarCount + 12;
                y--;
            }
            $("#calengar-body td[id^='UMOuyHSpDSCalendarWrap']").each(function () {
                $(this).html($.calendarpanel.getCalendarHtml(y, m));
                m++;
                if (m > 11) {
                    m = 0;
                    y++;
                }
            });
            $.calendarpanel.bindingDayClick();
        },
        createCalendar: function (y, m) {
            var t = ['<table class="ds-wrap">' +
                '<tbody><tr>' +
                '<td valign="top" class="ds-prev">' +
                '<div class="ds-prevyear" id="DSPrevYear" sign="prevyear"></div>' +
                '<div class="ds-prevmonth" id="DSPrevMonth" sign="prevmonth"></div>' +
                '</td>'];
            if (m > this.op.calendarCount) {
                m -= this.op.calendarCount;
            } else {
                m = m - this.op.calendarCount + 11;
                y--;
            }
            for (var i = 0; i < this.op.calendarCount; i++) {
                t.push('<td valign="top" id="UMOuyHSpDSCalendarWrap' + i);
                if (i == (this.op.calendarCount - 1)) {
                    t.push('" class="ds-cal-wrap ds-cal-wrap-last">');
                } else {
                    t.push('" class="ds-cal-wrap">');
                }
                t.push(this.getCalendarHtml(y, m));
                t.push('</td>');
                m++;
                if (m > 11) {
                    m = 1;
                    y++;
                }
            }
            t.push('<td valign="top" class="ds-next">' +
                '<div class="ds-nextyear" id="DSNextYear" sign="nextyear"></div>' +
                '<div class="ds-nextmonth" id="DSNextMonth" sign="nextmonth"></div>' +
                '</td>' +
                '</tr></tbody></table>');
            $("#calengar-body").html(t.join(""));
        },
        getCalendarHtml: function (y, m) {
            var e = '<div class="ds-cal-head" sign="month" y="' + y + '" m="' + m + '">' + y + '年' + (m + 1) + '月</div>';
            var i = '<table cellpadding="0" cellspacing="0" border="0">';
            var d = [];
            d.push(e, i, '<thead>' + this.getCalHeadHtml() + '</thead>', '<tbody>' + this.getCalBodyHtml(y, m) + '</tbody></table>');
            return d.join("");
        },
        getCalHeadHtml: function () {
            var d = ["日", "一", "二", "三", "四", "五", "六"];
            var c = ['<tr>'];//['<tr><td style="background:#fff;" class="'+this.op.weekClass+'"></td>'];
            for (var b = 0; b < 7; b++) {
                c.push('<td class="ds-week-color">' + d[b] + '</td>');
            }
            c.push("</tr>");
            return c.join("");
        },
        getCalBodyHtml: function (y, m) {
            var vDate = new Date();
            vDate.setDate(1);
            vDate.setMonth(m);
            vDate.setFullYear(y);
            var vFirstDay = vDate.getDay();
            var vDay = 1;
            var vLastDay = this.get_daysofmonth(m, y);
            var vOnLastDay = 0;
            var vCode = "";
            /*
             Get day for the 1st of the requested month/year..
             Place as many blank cells before the 1st day of the month as necessary.
             */
            vCode = vCode + "<TR style='cursor:hand'>";
            for (i = 0; i < vFirstDay; i++) {
                vCode = vCode + "<TD class=ds-date-othermonth>&nbsp;</TD>";
            }
            // Write rest of the 1st week
            for (j = vFirstDay; j < 7; j++) {
                vCode = vCode + "<TD class=" + this.getDayClass(y, m, vDay) + " sign=data y=" + y + " m=" + m + " d=" + vDay + " >" + this.format_day(vDay, m, y) + "</TD>";
                vDay = vDay + 1;
            }
            vCode = vCode + "</TR>";
            // Write the rest of the weeks
            for (k = 2; k < 7; k++) {
                vCode = vCode + "<TR>";
                for (j = 0; j < 7; j++) {
                    vCode = vCode + "<TD class=" + this.getDayClass(y, m, vDay) + " sign=data y=" + y + " m=" + m + " d=" + vDay + " >" + this.format_day(vDay, m, y) + "</TD>";
                    vDay = vDay + 1;
                    if (vDay > vLastDay) {
                        vOnLastDay = 1;
                        break;
                    }
                }
                if (j == 6)
                    vCode = vCode + "</TR>";
                if (vOnLastDay == 1)
                    break;
            }

            // Fill up the rest of last week with proper blanks, so that we get proper square blocks
            for (m = 1; m < (7 - j); m++) {
                vCode = vCode + "<TD class=ds-date-othermonth>&nbsp;</TD>";
            }

            return vCode;
        },
        getDayClass: function (y, m, d) {
            if ($.calendarpanel.to == null) {
                if ($.calendarpanel.from != null && y == $.calendarpanel.date_from.y && m == $.calendarpanel.date_from.m && d == $.calendarpanel.date_from.d) {
                    return 'ds-date-selected';
                }
            } else {
                var ymd = y + (m < 10 ? '0' + m : m) + (d < 10 ? '0' + d : d);
                if (Number($.calendarpanel.from) <= Number(ymd) && Number(ymd) <= Number($.calendarpanel.to)) {
                    return 'ds-date-selected';
                }
            }
            return 'ds-date-thismonth';
        },
        get_daysofmonth: function (monthNo, p_year) {
            if ((p_year % 4) == 0) {
                if ((p_year % 100) == 0 && (p_year % 400) != 0) {
                    return Calendar.DOMonth[monthNo];
                }
                return this.lDOMonth[monthNo];
            } else {
                return this.DOMonth[monthNo];
            }
        },
        //reverse date to json
        data_format: function (date) {
            if (!date) {
                return null;
            }
            var dateArr;
            var v;
            switch (this.op.format) {
                case "YYYY-MM-DD" :
                    dateArr = date.split('-');
                    v = {'y': Number(dateArr[0]), 'm': Number(dateArr[1]) - 1, 'd': Number(dateArr[2])};
                    break;
                case "YYYY/MM/DD" :
                    dateArr = date.split('/');
                    v = {'y': Number(dateArr[0]), 'm': Number(dateArr[1]) - 1, 'd': Number(dateArr[2])};
                    break;
                case "DD-MM-YYYY" :
                    dateArr = date.split('-');
                    v = {'y': Number(dateArr[2]), 'm': Number(dateArr[1]) - 1, 'd': Number(dateArr[0])};
                    break;
                default :
                    dateArr = date.split('.');
                    v = {'y': Number(dateArr[0]), 'm': Number(dateArr[1]) - 1, 'd': Number(dateArr[2])};
            }
            return v;
        },
        format_data: function (y, m, d) {
            var vData;
            var vMonth = 1 + Number(m);
            vMonth = (String(vMonth).length < 2) ? "0" + vMonth : vMonth;
            var vDD = (String(d).length < 2) ? "0" + d : d;
            switch (this.op.format) {
                case "YYYY-MM-DD" :
                    vData = y + "-" + vMonth + "-" + vDD;
                    break;
                case "YYYY/MM/DD" :
                    vData = y + "/" + vMonth + "/" + vDD;
                    break;
                case "DD-MM-YYYY" :
                    vData = vDD + "-" + vMonth + "-" + y;
                    break;
                default :
                    vData = y + "." + vMonth + "." + vDD;
            }
            return vData;
        },
        format_day: function (vday, vmonth, vyear) {
            var vNowDay = this.gNow.getDate();
            var vNowMonth = this.gNow.getMonth();
            var vNowYear = this.gNow.getFullYear();
            if (vday == vNowDay && vmonth == vNowMonth && vyear == vNowYear)
                return ("<FONT COLOR=\"8B0000\"><B>" + vday + "</B></FONT>");
            else
                return (vday);
        }
    }
})(jQuery);

//点击其他地方，关闭树形

/*$(document).click(function(event){
 if($("#calendarContainerOOCYoZxq").is(":visible")){
 //Firefox的事件目标为：event.target
 if($.calendarpanel.$source[0] != (event.srcElement ? event.srcElement : event.target)
 && !getParent((event.srcElement ? event.srcElement : event.target))){
 closePanel();
 return false;
 }
 }
 });*/

//递归判断父对象 是否id=treediv
function getParent(srcobj) {
    if (!srcobj) {
        return false;
    }
    if (srcobj.id) {
        if (srcobj.id.indexOf('calendarContainerOOCYoZxq') != -1) {
            return true;
        }
    }
    return getParent(srcobj.parentNode);
}

//panel
function getpanelDiv() {
    return  '<div class="calendar-box" id="calendarContainerOOCYoZxq">' +
        '<div id="calengar-body"></div>' +
        '<div class="date-select-tip"></div>' +
        '<div class="btn-bar clearfix">' +
        '<div class="date-range">' +
        '<span>日期区间</span>' +
        '<input class="date-start" id="sunlyBDaDateStart" style="-ms-ime-mode: disabled;" type="text" maxlength="10" placeholder="起始日期" >' +
        '<span> - </span>' +
        '<input class="date-end" id="sunlyBDaDateEnd" style="-ms-ime-mode: disabled;" type="text" maxlength="10" placeholder="终止日期" >' +
        '</div>' +
        '<div class="date-change">' +
        '<input id="ChangeDate" type="button" value="确定">&nbsp;&nbsp;&nbsp;<input id="CancelChangeDate" type="button" value="取消"></div>' +
        '</div></div>';
}

document.writeln(getpanelDiv());

