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
	        <a href="${ctx}/admin/dictionary/index" title="系统管理">系统管理</a> &gt;
	        <a href="${ctx}/admin/dictionary/index" title="数据字典管理">字典管理</a> &gt;
	                        列表
	    </div>        <!-- //End 当前位置 -->
        <div class="listPage"  style="width:75%;float: left;">
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
                        <a class="operate-add" href="${ctx}/admin/dictionary/add">
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
    	<div id="treeDiv" style="width:22%;top:60px; z-index: 800;float: left;">
	    		<span></span>
	    		<ul id="tree" class="ztree" style="width:260px; overflow:auto;">
	    		</ul>
	    </div>
    </div>
    <!-- //End 主内容区 -->
</div>

<m:message message="${message}" error="${error}"/>
<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
	$(function(){
		onLoadZTree();
	});
	
	var setting = {
			callback: {
				//点击刷新
				onClick: reloadGridByTreeId
			},
			check:{
    			enable:false,
    			//chkStyle: "checkbox"
				//chkStyle: 'radio',
				radioType: 'all'
    		},
    		data:{
    			simpleData:{
    				enable: true,
		 			idkey : "id",          //在isSimpleData格式下，当前节点id属性  
		 			pIdkey : "superId",        //在isSimpleData格式下，当前节点的父节点id属性  
		 			rootPId: null 
    			}
    		},
	    };
	
	/**
	 * 加载树形结构数据
	 */
	function onLoadZTree(){
		var treeNodes;
		$.ajax({
			async:false,//是否异步
			cache:false,//是否使用缓存
			type:'POST',//请求方式：post
			dataType:'json',//数据传输格式：json
			url:"${ctx}/admin/dictionary/ajaxDictionaryTree",//请求的action路径
			error:function(){
				alert('请求失败！');
			},
			success:function(data){
				treeNodes = data;
			}
		});	 
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, treeNodes);
	}

	/**
	 * 加载树列表
	 */
	  var $g = $('#datagrid').jqGrid({
	        url: 'list',
	        datatype: 'json',
	        colNames: ['操作', 'id', '编码','字典名称','备注', '创建时间'],
	        colModel: [
	            {template: 'actions0', width: 120},
	            {name: 'id', index: 'id', key: true ,hidden: true,},  /* hidden: true,  */ 
	            {name: 'dicCode', index: 'tr.dicCode',template: 'text', search:true ,sortable:false},
	            {name: 'dicName', index: 'tu.dicName',template: 'text', search:true,sortable:false},
	            {name: 'remark',  template: 'text', search:false,sortable:false,width:200 },
	            {name: 'createdDate', template: 'date',search:false,sortable:false,width:180}
	        ],
	        multiselect: true,
	        autowidth: true,
	        height: 'auto',
	        pager: '#pagination',
	        sortname: 'sortNum',
	        sortorder: 'desc'
	    });
	
	/**
	* 查找该字典的子字典，刷新列表
	*/
    function reloadGridByTreeId(event, treeId, treeNode) {
    	var id = treeNode.id;
    	//添加值并刷新
    	$g.jqGrid("setGridParam", {
    		postData:{ 'superId': id }
    	}).trigger("reloadGrid");
    };
    
    //批量删除操作
    $('.operateBar .operate-delete').click(function () {
        var keys = $g.jqGrid('getGridParam', 'selarrrow');
        1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("警告", "字典数据非常重要，你确定要删除选择的" + keys.length + "行记录吗？", function (r) {
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
