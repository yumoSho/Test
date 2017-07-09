/**
 * Created by Administrator on 2016/4/12.
 */
var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id', '会员编号', '用户名','等级','邮箱', '手机号', '注册平台', '状态', '注册时间'],
    colModel: [
        {template: 'actions2', width: 120},
        {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'memberCode', index: 'memberCode', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'memberName', index: 'memberName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'gradeName', index: 'gradeName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'email', index: 'email', template: 'text', sortable: false, search: true}
        , {name: 'phone', index: 'phone', template: 'text', sortable: false, search: true}
        , {
            name: 'registerFrom', index: 'registerFrom', template: 'select', stype: 'select',
            width: 150,
            searchoptions: {sopt: ["eq", "ne"], value: getSearchoptions(froms)},
            formatter: function (val) {
                return formatByDictionary(val, froms);
            }
        }
        ,{
            name: 'status', index: 'status', template: 'select', stype: 'select',
            width: 150,
            searchoptions: {sopt: ["eq", "ne"], value: getSearchoptions(memberStatus)},
            formatter: function (val) {
                return formatByDictionary(val, memberStatus);
            }
        }
        , {name: 'registerDate',width:150, index: 'registerDate', template: 'date', sortable: false, search: false}
    ],
    multiselect: true,
    autowidth: false,
    shrinkToFit: true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'registerDate',
    sortorder: 'desc'
});

/*导出事件*/
$("#excelExport").click(function () {
    var url = "/admin/member/export";
    window.HgUtil.exportExcel($("#datagrid"), url);
});