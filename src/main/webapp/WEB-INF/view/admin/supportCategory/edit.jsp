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
            <a href="${ctx}/admin/supportCategory/index"; title="">内容管理</a> &gt;
            <a href="${ctx}/admin/supportCategory/index" title="">帮助中心分类管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/supportCategory/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${supportCategory.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <c:choose>
                <c:when test="${ supportCategory.parent!=null }">
                <tr>
                    <th width="130" align="right">所属分类：<b class="color-red">*</b></th>
                    <td>

                   				 <input type="text" name="show_parent" value="${supportCategory.parent.name }"  readonly="readonly"/>
                   				 <input type="hidden" name="parent.id" value="${supportCategory.parent.id}">

                    </td>
                </tr>
                </c:when>
                </c:choose>
                <tr>
                    <th width="130" align="right">分类名称：<b class="color-red">*</b></th>
                    <td >
                           <input type="text"  name="name" value="${supportCategory.name }"/>

                        
                    </td>
                </tr>

                 <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <%--<input name="sortNum" value="${supportCategory.sortNum }" class="text_sketch" type="text">--%>
<span id="sort-spinner" class="spinner">
<input id="sortNum" name="sortNum" class="input" value="${supportCategory.sortNum }">
<span class="spin-buttons">
<span class="btn-up"></span>
<span class="btn-down"></span>
</span>
</span>
                        <script>
                            $('#sort-spinner').spinner({
                                min: 0
                                , step: 1
                                , allowEmpty: true
                                , minusBtn: '.btn-down'
                                , plusBtn: '.btn-up'
                            });
                        </script>
                    </td>
                </tr> 
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
        window.location.href = "${ctx}/admin/supportCategory/index";
    });

    //验证
    $("#form").validate({
        rules:{
            name:{
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
                "保存中...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


