<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/admin/support.jspf" %>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
	<style type="text/css">
		.cms-pop-body{padding:0px 30px;line-height: 25px;font-size: 14px}
		#goodsAnswer{margin: 5px 0 0 -6px;}
		#consultVisible{margin-top:2px}
		.table-module03 .operateBox a{display: none }
	</style>
</m:h>
<%@ include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@ include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
		<!-- 弹窗 -->
			<div class="comment" style="display: none">
				<input id="commentId" type="hidden" value="">
				<p>
					评价内容：<span id="content"></span>
				</p>
				<div>
					<span>回复内容：</span>
					<textarea id="commentReply" rows="5" cols="40"></textarea>
				</div>
				<span>&nbsp;&nbsp;属性：</span><input id="commentVisible" type="checkbox" value="true"><label>显示</label>
			</div>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/adminOrder/index" title="订单管理">订单管理</a> &gt;
            <a href="${ctx}/admin/comment/index" title="商品咨询管理">商品评价管理</a> &gt;
            列表
        </div>
		<div class="operateBar">
			<ul>
				<li>
					<a class="operate-delete" href="javascript:void(0);" class="">
						<s class="icon-delete"></s>删除
					</a>
				</li>
			</ul>
		</div>
        <!-- //End 当前位置 -->
        <div class="listPage" style="margin-top: 20px;">
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
        colNames: ['操作', 'id', '商品名称', '评价内容', '是否显示', '状态', '回复内容', '评论时间'],
        colModel: [
            {template: 'actionsComment', width: 70},
            {name: 'id', index: 'id', hidden: true, key: true},
            {name: 'goods.title', index: 'gTitle', template: 'text'},
            {name: 'content', index: 'content', template: 'text'},
            {name: 'visible', index: 'visible', template: 'select', align: 'left',
            	formatter:function(va){if(va) return "是"; else return "否";}, stype: 'select', searchoptions:{value:':全部;1:是;0:否'}},
            {name: 'reply', index: 'reply', template: 'select', align: 'left',
            	formatter:function(va){if(null != va) return "已回复"; else return "未回复";}, stype: 'select',type:'B', searchoptions:{value:':全部;!null:已回复;null:未回复'}},
            {name: 'reply', index:'reply', template: 'text',search:false,},
            {name: 'commentTime', index: 'commentTime', template: 'date', align: 'left',search:false}
        ],
        multiselect: true,
        autowidth: true,
        shrinkToFit: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'commentTime',
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
    
    
	function showComment(){
		var id = $g.jqGrid('getGridParam','selrow');
		var rowData = $g.jqGrid('getRowData',id);
		var title = rowData["orderItemId"];
		var reply = rowData["reply"];
		var content = rowData["content"];
		var visible = rowData["visible"];
		
		
		$("#commentId").val(id);
		$("#goodsTitle").text("评价商品："+title);
		$("#content").text(content);
		if (visible == "是") {
			$("#commentVisible").attr("checked",true);
		}
		$("#commentReply").val(reply);
		layer.open({
			zIndex: 10,
			title: '回复评论',
			type: 1,
			skin: 'layer-ext-alertpop', //加上边框
			area: ['600px', '400px'], //宽高
			shadeClose: true,
			content: $('.comment'),
			btn: ['确定', '取消'],
			cancel: function (index) {
				layer.close(index);
			},
			yes: function (index) {
				var id = $("#commentId").val();
				var reply = $("#commentReply").val();
				var visible = $("#commentVisible").attr("checked") == "checked" ? true : false;
				$.ajax({
					url:"${ctx}/admin/comment/update",
					type:"post",
					dataType:"json",
					data:{"id":id,"reply":reply,"visible":visible},
					success:function(data){
						if (data.success == true) {
							window.location.href = "${ctx}/admin/comment/index";
						}
					}
				});
			}
		});
	}
	

</script>
<script type="text/javascript" src="${ctx}/js/glanway.js"></script>
