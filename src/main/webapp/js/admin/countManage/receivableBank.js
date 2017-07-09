/**
 * Created by Administrator on 2016/4/6.
 */
$(function () {
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['订单编号', 'orderId', '商品名称', '供应商名称', '支付方式', '银行卡号', '数量', '销售价格', '折扣金额', '折后金额', '不含税价', '银行含税结算价', '税额', '中间业务收入', '收货人', '联系方式', '收货地址', '销售日期', '支付日期', '发货日期', '收货日期'],
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
            , {
                name: 'product.supplier', index: 'supplierName', template: 'text',
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.supplierName;
                }, sortable: false, searchoptions: {sopt: ["cn"]}
            }
            , {name: 'payment', index: 'payment', template: 'text', sortable: false, search: true, align: 'center'}
            , {name: 'bank', index: 'bank', template: 'text', sortable: false, search: false}
            , {name: 'goodsNum', index: 'goodsNum', template: 'text', sortable: false, search: false}
            , {name: 'goodsprice', index: 'goodsprice', template: 'text', sortable: false, search: false}
            , {name: 'discount', index: 'discount', template: 'text', sortable: false, search: false}
            , {name: 'sellprice', index: 'sellprice', template: 'text', sortable: false, search: false}
            , {name: 'taxPrice', index: 'taxPrice', template: 'text', sortable: false, search: false}
            , {name: 'bankTaxPrice', index: 'bankTaxPrice', template: 'text', sortable: false, search: false}
            , {name: 'taxLimit', index: 'commission', template: 'text', sortable: false, search: false}
            , {
                name: 'bankIntermediaryBusinessIncome',
                index: 'marketCost',
                template: 'text',
                sortable: false,
                search: false
            }
            , {name: 'receiver', index: 'receiver', template: 'text', sortable: true, search: false}
            , {name: 'contact', index: 'contact', template: 'text', sortable: true, search: false}
            , {name: 'address', index: 'address', template: 'text', sortable: true, search: false}
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
                "search_finishdate_gt_D": $("#beginDate").val(),
                "search_finishdate_lt_D": $("#endDate").val()
            });
        },
    loadComplete: function () {
        var bankName = $("#bankId option:selected").text();
        $("caption").text(bankName);
        $(".codeImg").click(
            function () {
            var $img = $(this);
                $.ajax({
                    url: contextPath + "/admin/totalTable/findTotalByOrderId",
                    dataType:"json",
                    async: true,
                    data: {orderId:$(this).attr("orderId")},
                    success: function(msg){
                        if(msg.success){
                            var data = msg.data;
                            layerTips = layer.tips('运费：￥ '+data.deliverPrice, $img, {
                                area: ['130px', '40px'],
                                offset: ['-2px', '-2px'],
                                time: 0,
                                tips: [2, '#339999'],
                                fix:true
                            });
                        }
                    }
                });
                return false;
            }
        );
    }
})
    ;

    /*查询事件*/
    $("#search").click(function () {
        $("#datagrid").trigger("triggerToolbar");
    });

    /*导出事件*/
    $("#excelExport").click(function () {
        var url = "/admin/receivableBank/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });

    $("body").click(function(){
        layer.close(layerTips);
    });
    $(".content").scroll(function(){
        layer.close(layerTips);
    });
});
