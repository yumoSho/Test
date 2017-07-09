<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/view/include/taglibs.jspf"%>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>

	<!-- Begin 主内容区 -->
	<div class="content">
		<!-- Begin 当前位置 -->
		<div class="position">
			<span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
            <a href="${ctx}/admin/spec/index" title="商品规格">商品规格</a> &gt;
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
                        <a class="operate-add" href="add">
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

	<script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
	<script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/admin/product/spec.js"></script>
    <m:message message="${message}" error="${error}" />
<m:f />