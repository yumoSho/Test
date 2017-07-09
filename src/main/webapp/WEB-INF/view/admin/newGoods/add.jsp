<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/view/include/taglibs.jspf"%>
<m:h>
	<%@include file="/WEB-INF/view/include/elibs.jspf"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/admin/uploader.css">
	<style type="text/css">
		/*.panel-header {
			background: #f5f5f5;
		}
		
		.layout-split-east {
			border-left: #f5f5f5;
		}
		
		.table-module01 td {
			position: relative;
		}
		
		.table-module01 td .errorTip {
			position: absolute;
			left: 12px;
			top: 35px;
		}
		
		.table-module01 td .errorTip.errcon {
			top: 112px;
		}
		
		.upload-entry-icon img {
			width: 110px;
			height: 110px;
		}
        #pagination_center .ui-pg-input{width: 45px}
        .table-module01 label.error{position: inherit;}
        #light{height: 593px !important;}
        .suits{float: none !important;}*/
</style>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf"%>
<div class="main">
	<%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf"%>
	<!-- Begin 主内容区 -->
	<div class="content">
		<!-- Begin 当前位置 -->
		<div class="position">
			<span>您当前的位置：</span> <a href="${ctx}/admin/homePage" title="首页">首页</a> &gt;
			<a href="${ctx}/admin/activity/index" title="营销管理">营销管理</a> &gt;
			<a href="${ctx}/admin/newgoods/index" title="爆品推荐">爆品推荐</a> &gt;
			 编辑
		</div>
		<!-- //End 当前位置 -->

		<div class="editPage">
			<div class="tab-ct-wrap">
				<form action="${ctx}/admin/newgoods/save" method="post"
					id="cat-form">
					<table class="table-module01 table-module02" cellpadding="0" cellspacing="0">
						<thead class="tab1">
							<tr>
	                            <td colspan="4">
	                            	<input class="btn-sure " id="add-goods" type="button" value="选择商品" />
									<input type="hidden" name="TOKEN" value="${token}"/>
	                            </td>
	                        </tr>
							<tr>
								<th width="25%">商品名称</th>
								<th width="25%">类别</th>
								<th width="25%">序号</th>
								<th width="25%">操作</th>
							</tr>

						</thead>
						<tbody id="goods-list">
							<%--此处加入模板--%>
							<c:if test="${not empty newGoods}">
								<c:forEach items="${newGoods}" var="ng" varStatus="curr">
							<tr>
								<th width="25%">
									<input name="newGoods[${curr.index}].id" type="hidden" value="${ng.id}">
									<input name="newGoods[${curr.index}].goods.id" type="hidden" value="${ng.goods.id}">
									<input name="" type="text" disabled value="${ng.goods.title}">
								</th>
								<th width="25%" >
									<input name=""  type="text" disabled value="${ng.goods.product.category.name}">
								</th>
								<th width="25%">
									<input name="newGoods[${curr.index}].sort" type="text" class="digits required" value="${ng.sort}">
								</th>
								<th width="25%">
									<div class="operateBox" style="padding-top:4px;">
										<img src="${ctx}/images/admin/icon-delete01.png" onclick="removeTr(this);" width="15" height="15" title="Delete" alt="Delete">
									</div>
								</th>
							</tr>
							</c:forEach>
							</c:if>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="2" align="right">
									<span class="btn-sure-wrap">
										<input class="btn-sure btn-edit" onclick="sub();" type="button" value="保存" />
									</span>
								</td>
								<td colspan="2">
									<span class="btn-cancel-wrap">
										<input class="btn-cancel" type="button" value="取消" onclick="" />
									</span>
								</td>
						</tr>
						</tfoot>
					</table>
				</form>
			</div>
			<%-- TAB CONTENT END --%>
		</div>
		<!-- //End 主内容区 -->
	</div>

		<div class="sel-goods" style="display: none ;width: 598px">
			<table id="datagrid" style="width: 400px"></table>
			<div id="pagination" style="width: 400px"></div>
		</div>
</div>

	<%@include file="goodsTpl.jspf"%>
	<script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/admin/mustache.js"></script>
	<script type="text/javascript" src="${ctx}/js/admin/marketing/newGoods-edit.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#datagrid").setGridWidth(580);

		});
    </script>
<m:f />