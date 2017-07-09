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
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/supportCategory/index"; title="内容管理">内容管理</a> &gt;
            <a href="${ctx}/admin/contentManagement/index" ; title="">内容管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/contentManagement/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${contentManagement.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">名称：<b class="color-red">*</b></th>
                    <td >
                      <select class="text_sketch" name="dictionary.id">
                      <c:forEach items="${dictionaries_pt}" var="dictionary" >
				          <option value="${dictionary.id }" <c:if test="${contentManagement.dictionary.id==dictionary.id}">selected="selected"</c:if>>${dictionary.dicName }</option>
				        			          
				      </c:forEach>
			        </select>
                        
                    </td>
                </tr>
                 <tr>
                            <th width="130" align="right" valign="top">图片：<b class="color-red"></b></th>
                            <td>
                                <div class="uploader-1" id="single-drop-zone">
                                    <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传300*89像素的图片</span></div>
                                    <div class="upload-single">
                                        <div class="actions">
                                            <span id="image" name="image" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                            <script type="text/javascript">
                                                $(function () {
                                                Uploader2({
                                                    file_data_name: 'file',
                                                    browse_button: 'image',
                                                    url:'${ctx}/storage/images/preupload',
                                                    policy: true,
                                                    download:false,
                                                    name: 'image',
                                                    list: 'upload-queue',
                                                    filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                    mode: 't',
                                                    max_file_count: '1',
                                                    max_file_size: '1m'
                                                });
                                                });
                                            </script>
                                        </div>
                                        <div class="upload-input">请选择...</div>
                                        <ul class="upload-queue" id="upload-queue">
                                            <li>
                                                <c:if test="${not empty contentManagement.image}">
                                                    <input type="hidden" name="image" value="${contentManagement.image}" data-saved-url="${ctx}/${contentManagement.image}"/>
                                                </c:if>

                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                <tr>
                    <th width="130" align="right">内容：<b class="color-red">*</b></th>
                    <td >
                           <textarea id="editor_" name="content" rows="" cols="">${contentManagement.content}</textarea>

                        
                    </td>
                </tr>
           
                <!-- <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <input name="sortNum" value="1" class="text_sketch" type="text">
                        
                    </td>
                </tr> -->
                 <tr>
                    <td></td>
                    <td align="left">
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消" onclick=""/>
	                            </span>
                    </td>
                </tr>
                </tbody>
            </table>
            </form>
            <%--
       
        
        --%>
    </div>
    <!-- //End 主内容区 -->
</div>
</div>
<m:f/>
<-- 图片上传校验 -->
<script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
<--富文本初始化js-->
<script type="text/javascript" src="${ctx}/js/admin/initeditor.js"></script>
<script type="text/javascript">

$(function(){
    $(".btn-cancel-wrap input").click(function() {
        window.location.href = "${ctx}/admin/contentManagement/index";
    });

    //验证
    $("#form").validate({
        rules:{
            title:{
            	 required: true,
                 maxlength: 20
                  },
            content:{
            	required: true,
                maxlength: 10000
                  },
            sortNum:{
            	required: true,
            	digits:true,
            	max: 999
            },
                             
        },
    submitHandler: function(form){
    	
    	$.ajax({
            url: contextPath + '/admin/contentManagement/checkIsTitleAndBankExists',
            type: 'post',
            async: false,
            data: $("#form").serialize(),
            dataType: 'json',
            success: function(data) {
            	isExist = data.isExist;
            }
        });
    	if(isExist){
    		Glanway.Messager.alert("提示", "您选择的标题和其对应的银行已存在")
    		return;
    	}
    	
        $("#form").find(":submit").attr("disabled", true).attr("value",
                "Submitting...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


