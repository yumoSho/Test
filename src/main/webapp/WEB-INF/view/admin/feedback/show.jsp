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
            <a href="${ctx}/admin/feedback/index" title="">用户反馈查看</a> &gt;
            查看
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/commonPro/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${supportContent.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">姓名：<b class="color-red">*</b></th>
                    <td >
                     ${feedback.memberName }
                        
                    </td>
                </tr>
                
                
                <tr>
                    <th width="130" align="right">电子邮箱：<b class="color-red">*</b></th>
                    <td >
                        ${feedback.email }
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">手机：<b class="color-red">*</b></th>
                    <td >
                        ${feedback.phone }

                    </td>
                </tr>

                 <tr>
                    <th width="130" align="right">内容：<b class="color-red">*</b></th>
                    <td>
                        <p style="width: 700px;">${feedback.content} </p>
                    </td>
                </tr> 
                 <tr>
                    <td></td>
                    <td align="left">

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
<script type="text/javascript" src="${ctx}/js/admin/supportCategory/bankChanged.js"></script>
<script type="text/javascript">

$(function(){
    $(".btn-cancel-wrap input").click(function() {
        window.location.href = "${ctx}/admin/feedback/index";
    });

    //验证
    $("#form").validate({
        rules:{
            'supportCategory.id':{
                  required: true,
                     
                   },
             title:{
                  required: true,
                  maxlength: 50
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
    	   	
        $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


