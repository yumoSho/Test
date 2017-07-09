<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../../include/taglibs.jspf"%>
<m:h>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/icon.css">
    <%@include file="../../include/elibs.jspf" %>
</m:h>
<%@include file="../../include/admin/support.jspf" %>
<%@include file="../../include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>

	<!-- Begin 主内容区 -->
	<div class="content">
		<!-- Begin 当前位置 -->
		<div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/activity/index" title="营销管理">营销管理</a> &gt;
            <a href="${ctx}/admin/accessory/index" title="商品配件">商品配件</a>&gt;
            配件列表
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
<%--<%@include file="../../include/admin/support.jspf" %>--%>
<%--<%@include file="../../include/elibs.jspf" %>--%>
<script>
	var $g = $('#datagrid').jqGrid({
		url: 'list' ,
		datatype: 'json' ,
		colNames: [
			'操作', 'ID', '配件名称','主商品名称'
		]
		, colModel: [
			{template: 'actions2',width:100}
			, {name: 'id', index: 'id', template: "text",hidden: true }
			, {name: 'name', index: 'name', template: "text",width:200,sortable:false }
			, {name: 'primaryGoods.title', index: 'title', template: "text",width:330,sortable:false}
		],
		multiselect: true,
		autowidth: true,
		shrinkToFit: true,
		pager: '#pagination',
		sortname: 'createdDate',
		height: 'auto',
		sortorder: 'desc'
	});


	$('.operateBar .operate-delete').click(function () {
		var keys = $g.jqGrid('getGridParam', 'selarrrow');
		1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("警告", "您确定要删除选择的" + keys.length + "行记录吗？", function (r) {
			r && $.ajax({
				url: 'delete',
				type: 'post',
				traditional: true,
				data: { id: keys }
			}).done(function (data) {
				var removed;
				if (data.success) {
					removed = data.success || [];
					$g.trigger("reloadGrid");
					layer.alert('操作成功', {
						skin: 'layer-ext-message' //样式类名
						,closeBtn:1
						,time:3000
						,title:'提示 [3秒后消失]'
						,shade: 0
						,offset:'rb'
						,btn:''
					});
				}else{
					layer.alert(data.message, {
						skin: 'layer-ext-message' //样式类名
						,closeBtn:1
						,time:3000
						,title:'提示 [3秒后消失]'
						,shade: 0
						,offset:'rb'
						,btn:''
					});
				}
			}).fail(function () {
				layer.alert('操作失败', {
					skin: 'layer-ext-message' //样式类名
					,closeBtn:1
					,time:3000
					,title:'提示 [3秒后消失]'
					,shade: 0
					,offset:'rb'
					,btn:''
				});
			});
		});
	});

</script>
<m:message message="${message}" error="${error}"/>
<script type="text/javascript" src="${ctx}/js/admin/common.js"></script>

<m:f />

