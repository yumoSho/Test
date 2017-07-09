/**
 * Created by Administrator on 2016/4/6.
 */
$(function () {
    var $g = $('#datagrid').jqGrid({
            url: 'list',
            datatype: 'json',
            colNames: ['订单编号', 'orderId', '商品名称',  '数量', '销售价格', '折扣金额', '折后金额', '含税结算价', '销售日期', '支付日期', '发货日期', '收货日期'],
            colModel: [
                {
                    name: 'code',
                    index: 'code',
                    template: 'text',
                    sortable: false,
                    search: false,
                    formatter: function (cell, op, row) {
                        if (row.oId == null) {
                            return cell;
                        }
                        return '<a href="' + contextPath + '/admin/adminOrder/edit/' + row.oId + '" style="text-decoration: none;color: blue" target="_blank">' + cell + '</a>'
                    }
                }
                , {name: 'oId', index: 'oId', hidden: true, key: true}
                , {name: 'title', index: 'title', template: 'text', sortable: false, search: false}
                , {name: 'goodsNum', index: 'goodsNum', template: 'text', sortable: false, search: false}
                , {name: 'goodsprice', index: 'goodsprice', template: 'text', sortable: false, search: false}
                , {name: 'discount', index: 'discount', template: 'text', sortable: false, search: false}
                , {name: 'sellprice', index: 'sellprice', template: 'text', sortable: false, search: false}
                , {name: 'bankTaxPrice', index: 'bankTaxPrice', template: 'text', sortable: false, search: false}
                , {name: 'createdDate', index: 'marketDate', template: 'date', sortable: false, search: false}
                , {name: 'payDate', index: 'payDate', template: 'date', sortable: false, search: false}
                , {name: 'deliverydate', index: 'receivedate', template: 'date', sortable: false, search: false}
                , {name: 'receivedate', index: 'receivedate', template: 'date', sortable: false, search: false}
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
                    "search_bankId_eq_L": $("#bankId").val(),
                    "search_supplierId_eq_L": $("#supplierId").val(),
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
        var url = "/admin/receivableSupplier/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });

});
