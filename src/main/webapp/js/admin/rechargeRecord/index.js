/**
 * Created by Administrator on 2016/4/12.
 */
var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['会员编号', 'id', '会员名','邮箱','手机','充值金额','充值时间'],
    colModel: [
         {name: 'member.memberCode', index: 'memberCode', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}},
        {name: 'id', index: 'id', hidden: true, key: true}
        , {name: 'member.memberName', index: 'memberName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'member.email', index: 'memberMail', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'member.phone', index: 'memberPhone', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
        , {name: 'money', index: 'money', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
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
