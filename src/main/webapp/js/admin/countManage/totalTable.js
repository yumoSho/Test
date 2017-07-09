/**
 * Created by Administrator on 2016/4/6.
 */
$(function () {
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作','订单编号', 'orderId', '商品名称', '商品类别', '供应商名称', '数量', '支付方式', '银行卡号', '销售价格', '折扣金额', '折后金额', '成本价', '银行结算金额', '佣金', '销售费用', '毛利', '销售日期', '支付日期', '发货日期', '收货日期'],
        colModel: [
            {
                align: 'center'
                , sortable: false
                , search: false
                ,width:50
                , formatter: function (cell, op, row) {
                    return '<img class="codeImg" orderId="'+row.oId+'" style="cursor:pointer" title="小计" src="' + contextPath + '/images/admin/icon-menubar-01.png" width="20" height="20">';
                }
            },
            {
                name: 'code',
                index: 'code',
                template: 'text',
                sortable: false,
                search: false,
                formatter: function (cell, op, row) {
                    if(row.oId == null){
                        return cell;
                    }
                    return '<a href="'+contextPath+'/admin/adminOrder/edit/'+row.oId+'" style="text-decoration: none;color: blue" target="_blank">'+cell+'</a>'
                }
            }
            , {name: 'oId', index: 'oId', hidden: true, key: true}
            , {name: 'title', index: 'title', template: 'text', sortable: false, search: false}
            , {name: 'product.category.name', index: 'categoryName', template: 'text', sortable: false, search: true}
            , {
                name: 'product.supplier', index: 'supplierName', template: 'text',
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.supplierName;
                }, sortable: false, searchoptions: {sopt: ["cn"]}
            }
            , {name: 'goodsNum', index: 'goodsNum', template: 'text', sortable: false, search: false}
            , {name: 'payment', index: 'payment', template: 'text', sortable: false, search: true, align: 'center'}
            , {name: 'bank', index: 'bank', template: 'text', sortable: false, search: false}
            , {name: 'goodsprice', index: 'goodsprice', template: 'text', sortable: false, search: false}
            , {name: 'discount', index: 'discount', template: 'text', sortable: false, search: false}
            , {name: 'sellprice', index: 'sellprice', template: 'text', sortable: false, search: false}
            , {name: 'ocostPrice', index: 'ocostPrice', template: 'text', sortable: false, search: false}
            , {name: 'bankTaxPrice', index: 'bankTaxPrice', template: 'text', sortable: false, search: false}
            , {name: 'commission', index: 'commission', template: 'text', sortable: false, search: false}
            , {name: 'marketCost', index: 'marketCost', template: 'text', sortable: false, search: false}
            , {name: 'maoLi', index: 'maoLi', template: 'text', sortable: true, search: false}
            , {name: 'createdDate', index: 'marketDate', template: 'date', sortable: false, search: false}
            , {name: 'payDate', index: 'payDate', template: 'date', sortable: false, search: false}
            , {name: 'deliverydate', index: 'deliverydate', template: 'date', sortable: false, search: false}
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
                            layerTips = layer.tips('运费：￥ '+data.deliverPrice+'<br/>毛利：￥ '+data.maoLi, $img, {
                                area: ['130px', '60px'],
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
        var url = "/admin/totalTable/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });

    $("body").click(function(){
        layer.close(layerTips);
    });
    $(".content").scroll(function(){
        layer.close(layerTips);
    });
});
