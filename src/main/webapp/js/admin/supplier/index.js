/**
 * Created by Administrator on 2016/4/7.
 */
var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '供应商编号', '供应商名称', '联系人','电话', '邮箱', '创建时间'],
    colModel: [
        {template: 'actions2', width: 80},
        {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'supplierNum', index: 'supplierNum', template: 'text', sortable: false, search:true}
        , {name: 'supplierName', index: 'supplierName', template: 'text', sortable: false, searchoptions: {sopt: ["cn"]}}
        , {name: 'contact', index: 'contact', template: 'text', sortable: false, searchoptions: {sopt: ["cn"]}}
        , {name: 'phone', index: 'phone', template: 'text', sortable: false, search:true}
        , {name: 'email', index: 'email', template: 'text', sortable: false, search:true}
        , {name: 'createdDate', index: 'createdDate', template: 'date', sortable: true, search: false}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit: true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'createdDate',
    sortorder: 'desc'
});
