<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
	<%@ include file="/WEB-INF/view/include/elibs.jspf" %>
	<link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }
        .layout-split-east  {
            border-left: #f5f5f5;
        }
        .table-module01 td{position: relative}

    </style>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
	<!-- Begin 主内容区 -->
	<div class="content">
	    <!-- Begin 当前位置 -->
	   	<div class="position">
	        <span>当前位置：</span>
	        <a href="${ctx}/admin/homePage" title="首页">首页</a> &gt;
	        <a href="${ctx}/admin/module/index" title="系统管理">系统管理</a> &gt;
	        <a href="${ctx}/admin/dictionary/index" title="数据字典管理">字典管理</a> &gt;
	                       新增
	    </div> 
	    <!-- //End 当前位置 -->
	    <div style="width:100%;height:600px;"> 
	    	 <div id="treeDiv" style="width:150px;position: absolute;left:500px;top:30px; display:none;z-index: 800;">
	    		<span>选择上级字典：</span>
	    		<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
	    	</div>
	        <div data-options="region:'center',split:true" title="字典信息" style="height: 600px; width: 80%">
	            <form id="fromSave" action="${ctx}/admin/dictionary/save" method="post">
	                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
	                    <tbody>
	                     <tr>
	                        <th width="130" align="right">所属字典：<b class="color-red">*</b></th>
	                        <td>
	                        	<input id="superId"  name="superId" type="hidden"/>
	                        	<input class="btn-sure" type="button" value="请选择上级字典" onclick="showTree()"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th width="130" align="right">字典编码：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="text" class="inputText" name="dicCode" maxlength="20" style="width: 200px;height: 24px;" disabled="disabled"/>
	                         <%--   <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <th width="130" align="right">字典名称：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="text" class="inputText" name="dicName"  style="width: 200px;height: 24px;" disabled="disabled"/>
	                           <%-- <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                     
	                    <tr>
	                        <th width="130" align="right">排列序号：<b class="color-red">*</b></th>
	                        <td>
	                        	<input type="text" class="inputText" name="sortNum"  style="width: 200px;height: 24px;" disabled="disabled"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th align="right">备注：</th>
	                        <td>
	                            <input type="text" class="inputText" name="remark" disabled="disabled">
	                            <span class="errorTip"></span>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td></td>
	                        <td align="left" >
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存" disabled="disabled" id="saveButton"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消"/>
	                            </span>
	                        </td>
	                    </tr>
	                    </tbody>
	                </table>
	            </form>
	        </div>
	    </div>
	    <!-- //End 主内容区 -->
	</div>
</div>
<m:f/>
<script type="text/javascript">
	$(document).ready(function(){
		onLoadZTree();
		$(".inputText").css("background","#eee");
	});
	
	var zTree;
	var setting = {
			callback: {
				onClick: setSuperId
			},
			check:{
    			enable:false,
    			// chkStyle: "checkbox"
				//chkStyle: 'radio',
				radioType: 'all'
    		},
    		data:{
    			simpleData:{
    				enable: true,
		 			idkey : "id",          //在isSimpleData格式下，当前节点id属性  
		 			pIdkey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性  
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
	
	// 显示、隐藏树结构
	 var flag = true; 
	 function showTree(){
		 var div = document.getElementById("treeDiv"); 
	     if(flag) {
	         div.style.display = "block"; 	    	 
	     } else {
	         div.style.display = "none"; 	    	 
	     }
	     flag = !flag; 	 
	}
	 
	/**
	* 查找该字典的子字典，刷新列表
	*/
	function setSuperId(event, treeId, treeNode) {
		var id = treeNode.id;
		$("#superId").val(id);
		//解除锁定
		$(".inputText").attr("disabled",false).css("background","#fff");
		$("#saveButton").attr("disabled",false);
	 };
</script>

<script type="text/javascript">
    $(function(){
        $.validator.addMethod("isCodeExists", function(value, element,params){
     		var superId = $("#superId").val();
            var exists = 0;
            $.ajax({
                url: '${ctx}/admin/dictionary/ajaxCheckIsCodeExists',
                type: 'post',
                async: false,
                data: {'dicCode': value , 'superId' : superId, 'id' : ""},
                dataType: 'json',
                success: function(data) {
                    exists = data.isExists;
                }
            });
            return this.optional(element) || !exists;//表单不为空时才触发验证？
        },"该字典下已存在此编码，请更换！"); 
        
        //验证
        $("#fromSave").validate({
            rules:{
            	dicCode:{
                    required: true,
                    maxlength: 20,
                    isCodeExists: true
                },
                dicName:{
                    required: true,
                    maxlength: 20
                    //checkPwd: true
                },
                sortNum: {
                	required: true,
                	number: true
                },
                remark: {
                    maxlength: 200
                }
            },
            messages:{
            	dicCode:{
                    required: "字典编号不能为空",
                    maxlength: "字典编号不能超过20个字符"
                },
                dicName:{
                    required: "字典名称不能为空"
                },
                sortNum:{
                    required: "排列顺序不能为空",
                    number: "请输入一个有效数字"
                },
                remark: {
                    maxlength: "备注不能超过200个字符"
                }
            },
            submitHandler: function(form){
                var superId = $("#superId").val();
                if('' == superId){
                	Glanway.Messager.alert("提示", "请选择所属上级字典！")
                	return false;
                }
                $("#superId").val(superId);
				$(form).find(":submit").attr("disabled", true).attr("value",
						"保存中...").css("letter-spacing", "0");
                form.submit();
            }
        });
        
        $(".btn-cancel-wrap input").click(function() {
            window.location.href = "${ctx}/admin/dictionary/index";
        });
        
    });
</script>
