/**
 * Created by Administrator on 2016/4/6.
 */

var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '优惠券模板名称', '优惠额度','订单最小金额', '是否自动发放','自动发放最低金额','自动发放最大金额'],
    colModel: [
        {template: 'actions2', width: 80}
        , {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'name', index: 'name', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'discount', index: 'discount', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
        , {name: 'minPrice', index: 'minPrice', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
        , {
            name: 'autoSend',
            index: 'autoSend',
            template: 'select',
            stype: "select",
            sortable:false,
            search: true,
            formatter: "select",
            searchoptions: {sopt: ["eq", "ne"], value: ":全部;1:是;0:否"},
            editoptions: {value: "0:否;1:是"},
            formatter: function (val) {
                return val ? '是' : '否';
            },
            type:'B',
            align: 'center'
        }
        , {name: 'autoMinPrice', index: 'autoMinPrice', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
        , {name: 'autoMaxPrice', index: 'autoMaxPrice', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
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