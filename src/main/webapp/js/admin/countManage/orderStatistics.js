/**
 * Created by Administrator on 2016/4/6.
 */

 //订单状态
    var orderStatus = ':全部;'
            + '1:待支付;'
            + '2:已支付;'
            + '3:待发货;'
            + '4:待收货;'
            + '5:已确认收货;'
            + '6:交易完成;'
            + '7:交易取消;'
            + '8:退换货处理中;'
            + '8:问题/缺货';

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

$(function () {
    var $g = $('#datagrid').jqGrid({
            url: 'list',
            datatype: 'json',
            colNames: ['订单编号', 'orderId', '商品名称',  '数量', '供应商名称', '订单状态'/*, '销售日期', '支付日期'*/,'下单日期', '发货日期', '收货日期'],
            colModel: [
                {
                    name: 'code',
                    index: 'code',
                    template: 'text',
                    sortable: false,
                    search: false,
                    formatter: function (cell, op, row) {//debugger;
                        if (row.id == null) {
                            return cell;
                        }
                        return '<a href="' + contextPath + '/admin/adminOrder/edit/' + row.id + '" style="text-decoration: none;color: blue" target="_blank">' + cell + '</a>'
                    }
                }
                , {name: 'oId', index: 'oId', hidden: true, key: true}
                , {name: 'orderDetail.goodsName', index: 'D.goodsname', template: 'text', sortable: false, search: false}
                , {name: 'orderDetail.goodsNum', index: 'D.goodsnum', template: 'text', sortable: false, search: false}
                /*, {name: 'discount', index: 'discount', template: 'text', sortable: false, search: false}
                , {name: 'sellprice', index: 'sellprice', template: 'text', sortable: false, search: false}
                , {name: 'bankTaxPrice', index: 'bankTaxPrice', template: 'text', sortable: false, search: false}
                , {name: 'createdDate', index: 'marketDate', template: 'date', sortable: false, search: false}*/
                /* , {name: 'payDate', index: 'payDate', template: 'date', sortable: false, search: false}*/
                ,{name: 'supplyName', index: 'O.supplyname', template: 'text', width: 100, sortable: false, search: false}
               ,{
                    name: 'status', index: 'O.status',
                    template: 'select',
                    stype: 'select',
                    searchoptions: {sopt: ["eq", "ne"], value: orderStatus},
                    formatter: function (val, row) {
                        return getStatusDesc(val);
                    },
                    width: 120
                }
                , {name: 'createdDate', index: 'createdDate', template: 'date', sortable: false, search: false}
                , {name: 'deliveryDate', index: 'deliverydate', template: 'date', sortable: false, search: false}
                , {name: 'receiveDate', index: 'receivedate', template: 'date', sortable: false, search: false}
            ],
            multiselect: false,
            autowidth: true,
            shrinkToFit: false,
            height: 'auto',
            pager: '#pagination',
            sortname: 'id',
            footerrow: true,
            userDataOnFooter: true,
            altRows: true,
            sortorder: 'desc',
            beforeRequest: function () {
                var postData = $("#datagrid").jqGrid("getGridParam", "postData");
                $.extend(postData, {
                    "search_source_eq_S": $("#source").val(),
                    "search_bankid_eq_L": $("#bankId").val(),
                    "search_supplyid_eq_L": $("#supplierId").val(),
                    "search_finishdate_gt_D": $("#beginDate").val(),
                    "search_finishdate_lt_D": $("#endDate").val()
                    /*"bankName":$("#bankId option:selected").text(),
                    "supplierName":$("#supplierId option:selected").text(),
                    "beginDate":$("#beginDate").val(),
                    "endDate":$("#endDate").val()*/
                });
            },
            loadComplete: function () {
                var bankName = $("#bankId option:selected").text();
                var supplierName = $("#supplierId option:selected").text();
               $("caption .bankName").text(bankName);
               $("caption .supplierName").text(supplierName);
                var beginDate = $("#beginDate").val();
                var endDate = $("#endDate").val();
                $("caption .searchDate").text(beginDate.split(" ")[0] + " — " + endDate.split(" ")[0] );
            }
        })
        ;

    /*查询事件*/
    $("#search").click(function () {
        $("#datagrid").trigger("triggerToolbar");
    });

    /*导出事件*/
    $("#excelExport").click(function () {
        var url = "/admin/orderStatistics/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });

});
