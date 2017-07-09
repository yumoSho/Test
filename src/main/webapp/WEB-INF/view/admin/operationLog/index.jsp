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
            <a href="javascript:void(0)"; title="">系统管理</a> &gt;
            <a href="${ctx}/admin/operationLog/index"; title="">日志管理</a> &gt;
            列表
        </div>
        <!-- //End 当前位置 -->
        <div class="listPage">
            <!-- Begin 操作条 -->
            <div class="operateBar">
                <ul>

                </ul>
            </div>
            <!-- //End 操作条 -->
            <div class="table-module03">
                <table id="datagrid"></table>
                <div id="pagination" style="height: 50px"></div>
            </div>
        </div>
    </div>
    <!-- //End 主内容区 -->
</div>

<m:message message="${message}" error="${error}"/>
<%--批量删除js--%>
<script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
<script type="text/javascript">
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['id','登录名','行为','操作时间','IP' ],
        colModel: [
    		    	{name: 'id', index: 'id', hidden: true, key: true}
            		        ,{name: 'otherFiled', index: 'otherFiled', template: 'text', sortable:false,searchoptions: {sopt: ["cn"]}}
	        		        ,{name: 'action', index: 'action', template: 'text', sortable:false,search:false}
	        		            ,{name: 'operateDate', index: 'operateDate', template: 'date', sortable:true,search:false}
            ,{name: 'operateIp', index: 'operateIp', template: 'text', sortable:true,search:false}

	                ],
        multiselect: false,
        autowidth: true,
        shrinkToFit:true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'operateDate',
            sortorder: 'desc'
    });
</script>
<m:f/>

