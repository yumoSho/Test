<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/admin/support.jspf" %>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
	        <a href="${ctx}/admin/supplierArea/index" title="物流管理">物流管理</a> &gt;
	        <a href="${ctx}/admin/supplierArea/index" title="配送区域设置">配送区域设置</a> &gt;
	                        列表
	    </div>        <!-- //End 当前位置 -->
        <div class="listPage"  style="width:78%;float: left;">
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
                        <a class="operate-add" href="${ctx}/admin/supplierArea/add">
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
<script type="text/javascript">
	  var $g = $('#datagrid').jqGrid({
	        url: 'list',
	        datatype: 'json',
	        colNames: ['操作', 'id', '邮寄区域名称', '价格', '创建时间', '最新修改时间'],
	        colModel: [
	            {template: 'actions2', width: 100},
	            {name: 'id', index: 'id', key: true ,hidden:true},
	            {name: 'areaName', index: 'tu.areaName',template: 'text', search:true, sortable:false},
	            {name: 'price', template: 'text', search:false, sortable:false},
	            {name: 'createdDate',  template: 'date',search:false, formatter:"date", formatoptions: {newformat:'Y-m-d H:m:s'}},
	            {name: 'lastModifiedDate',  template: 'date', search:false, formatter:"date", formatoptions: {newformat:'Y-m-d H:m:s'},align:"center"}
	        ],
	        multiselect: true,
	        autowidth: true,
          shrinkToFit: true,
	        height: 'auto',
	        pager: '#pagination',
	        sortname: 'createdDate',
	        sortorder: 'desc'
	    });
	
    //批量删除操作
    $('.operateBar .operate-delete').click(function () {
        var keys = $g.jqGrid('getGridParam', 'selarrrow');
        1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("提示", "你确定要删除选择的" + keys.length + "行记录吗？", function (r) {
            r && $.ajax({
                url: 'delete',
                type: 'post',
                traditional: true,
                data: {"id": keys}
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
                }
            }).fail(function () {
            	 layer.alert(data.message, {
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
<m:f/>
