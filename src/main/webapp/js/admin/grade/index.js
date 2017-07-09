/**
 * Created by Administrator on 2016/4/12.
 */
var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '名称','添加时间'],
    colModel: [
        {template: 'actions2', width: 120},
        {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'name', index: 'name', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'createdDate',width:150, index: 'createdDate', template: 'date', sortable: true, search: false}
    ],
    multiselect: true,
    autowidth: false,
    shrinkToFit: true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'createdDate',
    sortorder: 'desc'
});
