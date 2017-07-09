<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@ include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@ include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>

    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>当前位置：</span>
            <a href="${ctx}/admin/homePage" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
            <a href="${ctx}/admin/user/index" title="后台用户管理">用户管理</a> &gt;
            列表
        </div>        <!-- //End 当前位置 -->
        <div class="listPage">
            <!-- Begin 操作条 -->
            <div class="operateBar">
                <ul>
                    <li>
                        <a class="operate-delete" href="javascript:void(0);" class="">
                            <s class="icon-delete"></s>
                            删除
                        </a>
                    </li>
                    <li>
                        <a class="operate-add" href="${ctx}/admin/user/add">
                            <s class="icon-add-green"></s>
                            新增
                        </a>
                    </li>
                </ul>
            </div>
            <!-- //End 操作条 -->
            <div class="table-module03">
                <table id="datagrid"></table>
                <div id="pagination"></div>
            </div>
        </div>
    </div>
    <!-- //End 主内容区 -->
</div>

<m:message message="${message}" error="${error}"/>
<script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
<script type="text/javascript">
    var userTypes = toObject("${userTypes}");
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id', '登录名', '用户类型', '备注', '是否冻结', '上次登录时间', '上次修改时间'],
        colModel: [
            {
                align: 'center',
                sortable: false,
                search: false,
                formatter: function (cell, options, row) {
                    var userType = row.userType;
                    var html = '<div class="operateBox">'
                            + '<img onclick="customEdit(this);" src="' + contextPath + '/images/admin/icon-edit01.png" width="15" height="15" title="Edit" alt="Edit">';
                    if (userType == 1) {
                        html += '<img onclick="jQuery.fn.fmatter.rowactions2.call(this, \'del\');" src="' + contextPath + '/images/admin/icon-delete01.png" width="15" height="15" title="Delete" alt="Delete">';
                    }
                    return html + '</div>';
                }
                , width: 70
            },
            {name: 'id', index: 'id', hidden: true, key: true},
            {name: 'userName', index: 'tu.userName', template: 'text', width: 120, searchoptions: {sopt: ["cn"]}},
            {
                name: 'userType', index: 'tu.userType', template: 'select', stype: 'select',
                width: 150,
                searchoptions: {sopt: ["eq", "ne"], value: getSearchoptions(userTypes)},
                formatter: function (val) {
                    return formatByDictionary(val, userTypes);
                }
            },
            {name: 'remark', index: 'tu.remark', template: 'text', width: 180},
            {
                name: 'isFreeze',
                index: 'tu.isFreeze',
                template: 'text',
                width: 125,
                stype: "select",
                search: true,
                formatter: "select",
                searchoptions: {sopt: ["eq", "ne"], value: ":全部;false:未冻结;true:已冻结"},
                editoptions: {value: "true:已冻结;false:未冻结"},
                type: 'B',
                align: 'center'
            },
            {name: 'lastLoginTime', index: 'tu.lastLoginTime', template: 'date', width: 150, search: false},
            {name: 'lastModifiedDate', index: 'tu.lastModifiedDate', template: 'date', width: 140, search: false}
        ],
        multiselect: true,
        autowidth: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc'
    });
</script>

<m:f/>
