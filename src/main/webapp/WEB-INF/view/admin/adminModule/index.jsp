<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/admin/support.jspf" %>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@ include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@ include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>

    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
            <a href="${ctx}/admin/module/index" title="模块管理">模块管理</a> &gt;
            列表
        </div>
        <!-- //End 当前位置 -->
        <div class="listPage">
            <!-- Begin 操作条 -->
            <div class="operateBar">
                <ul>
                    <li>
                        <a class="operate-delete" href="javascript:void(0);" class="">
                            <s class="icon-delete"></s>删除
                        </a>
                    </li>
                    <li>
                        <a class="operate-add" href="${ctx}/admin/module/add">
                            <s class="icon-add-green"></s>新增
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

<%-- <script type="text/javascript" src="${ctx}/js/admin/common.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/admin/adminUser.js"></script> --%>
<m:message message="${message}" error="${error}"/>
<script type="text/javascript">
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id', '模块名称', '排序', '备注', '上次修改时间', '创建时间'],
        colModel: [
            {template: 'actions2', width: 70},
            {name: 'id', index: 'id', hidden: true, key: true},
            {name: 'name', index: 'name', template: 'text', width: 120, searchoptions: {sopt: ["cn"]}},
            {name: 'sort', index: 'sort', template: 'int', width: 100, align: 'center'},
            {name: 'remark', index: 'remark', template: 'text', width: 210},
            {name: 'lastModifiedDate', index: 'lastModifiedDate', template: 'date', width: 220, align: 'center',search:false},
            {name: 'createdDate', index: 'createdDate', template: 'date', width: 220, align: 'center',search:false}
        ],
        multiselect: true,
        autowidth: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc'
    });
</script>
<script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
