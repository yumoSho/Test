<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@ include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
            <a href="${ctx}/admin/page/index" title="页面管理">页面管理</a> &gt;
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
                        <a class="operate-add" href="${ctx}/admin/page/add">
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
        colNames: ['操作', 'id', '页面名称', '所属模块', '访问url', '备注', '上次修改时间', '创建时间', '排序'],
        colModel: [
            {template: 'actions2', width: 70},
            {name: 'id', index: 'page.id', hidden: true, key: true},
            {name: 'name', index: 'page.name', template: 'text', width: 90, searchoptions: {sopt: ["cn"]}},
            {name: 'module.name', index: 'module.name', template: 'text', width: 120, searchoptions: {sopt: ["cn"]}},
            {name: 'url', index: 'page.url', template: 'text', width: 180, searchoptions: {sopt: ["cn"]}},
            {name: 'remark', index: 'page.remark', template: 'text', width: 120, searchoptions: {sopt: ["cn"]}},
            {name: 'lastModifiedDate', index: 'last_Modified_Date', template: 'date', width: 140, align: 'center',search:false},
            {name: 'createdDate', index: 'created_Date', template: 'date', width: 140, align: 'center',search:false},
            {name: 'sort', index: 'sort', template: 'text', width: 70, align: 'center'}
            ],
        multiselect: true,
        autowidth: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'CREATED_DATE',
        sortorder: 'desc'
    });

</script>
<script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
<m:f/>

