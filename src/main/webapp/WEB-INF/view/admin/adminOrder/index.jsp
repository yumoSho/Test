<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/admin/support.jspf" %>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
</m:h>
<%@ include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@ include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>

    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/adminOrder/index" title="订单管理">订单管理</a> &gt;
            列表
        </div>
        <!-- //End 当前位置 -->
        <div class="listPage">
            <!-- Begin 操作条 -->
            <table class="table-module01" width="100%">
                <tbody>
                <tr>
                    <td width="8%">建单日期:</td>
                    <td valign="top" width=35%" style="line-height: 30px;">
                        <input type="text" readonly id="beginDate"
                               style="border:1px solid #d4d4d4;width: 145px;height:25px;" class="Wdate valid"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00', maxDate:'#F{$dp.$D(\'endDate\')||\'2099-12-31\'}'})">
                        <b class="color-red">-</b>
                        <input type="text" readonly id="endDate"
                               style="border:1px solid #d4d4d4;width: 145px;height: 25px;" class="Wdate valid"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59', minDate:'#F{$dp.$D(\'beginDate\')}',maxDate:'2099-12-31'})">

                    </td>
                    <td width="25%" colspan="2">
                        <span class="btn-sure-wrap">
                           <input class="btn-sure" type="button" value="查询" id="search"/>
                              </span>
                       <span>
                           <input class="btn-sure" type="button" value="导出" id="export"/>
                       </span>
                    </td>
                    <td>
                        <div style="background:#F5F5F5;color:red" id="countByStatus">您有${DFH}个待发货订单，${DSH}个待收货订单</div>

                    </td>
                </tr>
                </tbody>
            </table>
            <!-- //End 操作条 -->
            <div class="table-module03">
                <table id="datagrid"></table>
                <div id="pagination"></div>
            </div>
        </div>
    </div>
    <!-- //End 主内容区 -->
</div>
<m:message message="${message}" error="${error}"/>
<script type="text/javascript" src="${ctx}/js/glanway.js"></script>
<script type="text/javascript">

    //订单状态
    var orderStatus = ':全部;'
            + '1:待支付;'
            + '2:已支付;'
            + '3:待发货;'
            + '4:待收货;'
            + '5:已确认收货;'
            + '6:交易完成;'
            + '7:交易取消';

    //根据订单状态获得状态描述
    function getStatusDesc(val) {
        var status = "";
        switch (val) {
            case 1:
                status = "待支付";
                break;
            case 2:
                status = "已支付";
                break;
            case 3:
                status = "待发货";
                break;
            case 4:
                status = "待收货";
                break;
            case 5:
                status = "已确认收货";
                break;
            case 6:
                status = "交易完成";
                break;
            case 7:
                status = "交易取消";
                break;
            case 8:
                status = "退换货处理中";
                break;
            case 9:
                status = "问题/缺货";
                break;
            default:
                status = "";
        }
        return status;

    }
    ;

    //jqgrid初始化
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', '订单ID','订单编号', '订单状态', '订单金额',  '订单来源',  '会员名', '手机',  '配送时间','下单时间'],
        colModel: [
            {template: 'orderz', width: 80},
            {name: 'id', index: 'O.id', hidden: true, key: true},
            {name: 'code', index: 'code', template: 'text', width: 220, searchoptions: {sopt: ["cn"]}},
            {
                name: 'status', index: 'status',
                template: 'select',
                stype: 'select',
                searchoptions: {sopt: ["eq", "ne"], value: orderStatus},
                formatter: function (val, row) {
                    return getStatusDesc(val);
                },
                width: 120
            },
            {name: 'price', index: 'price', template: 'int', width: 100, align: 'left'},
            {name: 'source', index: 'source', template: 'text', width: 100, align: 'left', searchoptions: {sopt: ["cn"]}},
            {name: 'member.memberName', index: 'm_memberName', template: 'text', width: 140, align: 'left', searchoptions: {sopt: ["cn"]}},
            {name: 'member.phone', index: 'm_phone', template: 'text', width: 120, align: 'left', searchoptions: {sopt: ["cn"]}},
            {name: 'psDate', index: 'psDate', template: 'text', width: 150, align: 'left', search: false},
            {name: 'createdDate', index: 'createdDate', template: 'date', width: 150, align: 'left', search: false}
        ],
        multiselect: true,
        autowidth: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc',
        beforeRequest: function () {
            var postData = $("#datagrid").jqGrid("getGridParam", "postData");
            $.extend(postData, {
                "beginDate": $("#beginDate").val(),
                "endDate": $("#endDate").val()
            });
        },
    });

    //查询按钮点击方法
    $("#search").on('click', function () {
        $("#datagrid").trigger("triggerToolbar");
    });

    //导出按钮点击方法
    $("#export").on('click', function () {
        var url = "/admin/adminOrder/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });
</script>
