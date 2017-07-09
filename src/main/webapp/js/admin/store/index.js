/**
 * Created by Administrator on 2016/4/6.
 */

var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '店铺编号', '店铺名称','电话', '注册时间'],
    colModel: [
        {template: 'actions2', width: 80}
        , {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'code', index: 'bankCode', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'name', index: 'bankName', template: 'text', sortable: false, search: false}
        , {name: 'phone', index: 'phone', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'createdDate', index: 'createdDate', template: 'date', sortable: true,search:false}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit: true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'createdDate',
    sortorder: 'desc'
});

/*导出事件*/
$("#excelExport").click(function () {
    var url = "/admin/store/export";
    window.HgUtil.exportExcel($("#datagrid"), url);
});