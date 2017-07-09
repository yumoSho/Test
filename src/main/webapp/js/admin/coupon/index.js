/**
 * Created by Administrator on 2016/4/6.
 */

var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '优惠券码', '优惠券名称','优惠额度','订单最小金额','状态','发放用户','生成时间','发放时间'],
    colModel: [
        {template: 'actions4', width: 80}
        , {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'code', index: 'code', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'couponName', index: 'couponName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'discount', index: 'discount', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
        , {name: 'minPrice', index: 'minPrice', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
        , {
            name: 'status', index: 'status', template: 'select', stype: 'select',
            width: 150,
            searchoptions: {sopt: ["eq", "ne"], value: ":全部;1:未发放;2:已发放;3:已使用"},
            formatter: function (val) {
                debugger;
                switch (val){
                    case 1: return "未发放";
                    case 2: return "已发放";
                    case 3: return "已使用";
                    default : return "";
                }
            }
        }
        , {name: 'memberName', index: 'memberName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'createdDate', index: 'createdDate', template: 'date', sortable: false,search:false}
        , {name: 'grantDate', index: 'grantDate', template: 'date', sortable: false,search:false}

    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit: true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'id',
    sortorder: 'desc'
});

/*导出事件*/
$("#excelExport").click(function () {
    var url = "/admin/store/export";
    window.HgUtil.exportExcel($("#datagrid"), url);
});