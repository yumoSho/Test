<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
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
        .table-module01 td{position: relative;}
    </style>

</m:h>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
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
	        <a href="${ctx}/admin/user/index" title="数据字典管理">字典管理</a> &gt;
	                        编辑
	    </div>
        <!-- //End 当前位置 -->
        <div class="easyui-layout" style="width:100%;height:500px;">
             <div data-options="region:'center',split:true" title="字典信息" style="border:1px solid #ccc;width: 80%;border-right:none">
                <form id="fromSave" action="${ctx}/admin/dictionary/update" method="post">
                    <table style='border:none;' class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                        <tbody>
	                        <tr>
	                            <th width="130" align="right">字典编码：<b class="color-red">*</b></th>
	                            <td>
	                                <input type="hidden" id="dictionaryId" name="id" value="${dictionary.id}">
	                                <input type="text" id="dicCode" name="dicCode" maxlength="20" value="${dictionary.dicCode}" style="width: 200px;height: 24px;"/>
	                                <span class="errorTip"></span>
	                            </td>
	                        </tr>
	                  		<tr>
		                        <th width="130" align="right">字典名称：<b class="color-red">*</b></th>
		                        <td>
	                                <input type="text" id="dicName" name="dicName" maxlength="20" value="${dictionary.dicName}" style="width: 200px;height: 24px;"/>
		                        </td>
	                   		</tr>
	                        <tr>
	                            <th width="130" align="right">排列序号：<b class="color-red">*</b></th>
	                            <td>
	                                <input type="text" id="sortNum" name="sortNum" value="${dictionary.sortNum}" style="width: 200px;height: 24px;"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th align="right">备注：</th>
	                            <td>
	                                <input type="text" name="remark" value="${dictionary.remark}">
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
    $(function(){       	
        $.validator.addMethod("isCodeExists", function(value, element,params){
     		var superId = '${dictionary.superId}';
     		var id = '${dictionary.id}';
            var exists = 0;
            $.ajax({
                url: '${ctx}/admin/dictionary/ajaxCheckIsCodeExists',
                type: 'post',
                async: false,
                data: {'dicCode': value , 'superId' : superId, 'id' : id},
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