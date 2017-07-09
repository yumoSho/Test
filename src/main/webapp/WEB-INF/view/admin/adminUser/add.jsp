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
	        <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
	        <a href="${ctx}/admin/user/index" title="后台用户管理">用户管理</a> &gt;
	                        新增
	    </div>
	    <!-- //End 当前位置 -->
	    <div style="width:100%;height:600px;"> 
	    	<div id="treeDiv" style="width:150px;position: absolute;left:500px;top:30px; display:none;z-index: 800;">
	    		<span>选择角色：</span>
	    		<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
	    	</div>
	        <div data-options="region:'center',split:true" title="基础信息" style="height: 600px; width: 80%">
	            <form id="fromSave" action="${ctx}/admin/user/save" method="post">
	                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
	                    <tbody>
	                    <tr>
	                        <th width="130" align="right">用户名：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="text" name="userName" maxlength="20" value="" style="width: 200px;height: 24px;"/>
	                         <%--   <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <th width="130" align="right">密码：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="password" name="password"  value="" style="width: 200px;height: 24px;">
	                           <%-- <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                     
	                    <tr>
	                        <th width="130" align="right">角色：<b class="color-red">*</b></th>
	                        <td>
	                        	<input id="roleIds"  name="roleIds" type="hidden"/>
	                        	 <input class="btn-sure" type="button" value="选择该用户拥有角色" onclick="showTree()"/>
	                        </td>
	                    </tr>
	                    
	                    <!-- 选择user的角色，下拉列表只能实现一对一，但user可以有多个角色！
	                    <tr>
	                    	<th width="130" align="right">角色：</th>
	                    	<td>
	                    		<select name="roleId"  style="width: 145">
 	                            	<option value="" selected="selected">请选择</option>
	                                <c:forEach items="${rlist}" var="role">
	                                    <option value="${role.id}">${role.name}</option>
 	                                </c:forEach>
 	                        	</select>
	                    	</td>
	                    </tr>
	                     -->
						<%--<tr>
							<th width="130" align="right">用户类型：<b class="color-red">*</b></th>
							<td>
								<input type="radio" disabled name="userType"  value="1" style="width:13px;" checked="checked"/> 平台
								<span class="devider"></span>
								<input type="radio" disabled name="userType"  value="2" style="width:13px;"/> 银行
								<span class="devider"></span>
								<input type="radio" disabled name="userType" value="3" style="width:13px;"  /> 供应商
								<span class="errorTip"></span>
							</td>
						</tr>--%>
	                    <tr>
	                        <th width="130" align="right">是否冻结：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="radio" name="isFreeze"  value="true" style="width:13px;"/> 是
	                            <span class="devider"></span>
	                            <input type="radio" name="isFreeze" value="false" style="width:13px;"  checked="checked"/> 否
	                            <span class="errorTip"></span>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th align="right">备注：</th>
	                        <td>
	                            <input type="text" name="remark" >
	                            <span class="errorTip"></span>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td></td>
	                        <td align="left" >
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存" />
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消" onclick=""/>
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
	});
	var zTree;
	var setting = {
			
			check:{
    			enable:true,
    			// chkStyle: "checkbox"
				chkStyle: 'radio',
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
			url:"${ctx}/admin/user/roleTree",//请求的action路径
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
	         div.style.display="block"; 	    	 
	     } else {
	         div.style.display="none"; 	    	 
	     }
	     flag =! flag; 	 
	}
</script>

<script type="text/javascript">
    $(function(){
        $.validator.addMethod("isUserExists", function(value, element,params){
            var exists = 0;
            $.ajax({
                url: '${ctx}/admin/user/checkIsUserExists',
                type: 'post',
                async: false,
                data: {'username': value},
                dataType: 'json',
                success: function(data) {
                    exists = data.isExists;
                }
            });
            return this.optional(element) || !exists;//表单不为空时才触发验证？
        },"该登录名已存在");

        $(".btn-cancel-wrap input").click(function() {
            window.location.href = "${ctx}/admin/user/index";
        });

        $.validator.addMethod("checkPwd", function(value, element,params){
            return this.optional(element) || /^[a-zA-Z]{1}([a-zA-Z0-9]|[._#^~]){5,15}$/.test(value);
        }, "密码由以字母开头，可包含._#^~等特殊字符的6~16位字符组成");
        
        //验证
        $("#fromSave").validate({
            rules:{
                userName:{
                    required: true,
                    maxlength: 20,
                    isUserExists: true
                },
                password:{
                    required: true,
                    checkPwd: true
                },
                remark: {
                    maxlength: 200
                }
            },
            messages:{
                username:{
                    required: "登录名不能为空",
                    maxlength: "登录名不能超过20个字符"
                },
                password:{
                    required: "密码不能为空"
                },
                remark: {
                    maxlength: "备注不能超过200个字符"
                }
            },
          /*  errorPlacement: function (error, element) {
                error.appendTo(element.siblings("span:.errorTip"));
            },*/
            submitHandler: function(form){
            	var treeObj = $.fn.zTree.getZTreeObj("tree");
            	var checkedNodes = treeObj.getCheckedNodes(true);
                var checkedSize = checkedNodes.length;
                var roleIds = '';
                for(var i=0; i<checkedSize; i++){
                    roleIds += (checkedNodes[i].id + ",");
                }
                $("input[name=roleIds]").val(roleIds);
				$(form).find(":submit").attr("disabled", true).attr("value",
						"保存中...").css("letter-spacing", "0");
                form.submit();
            }
        });
    })
</script>
