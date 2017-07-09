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
            <a href="${ctx}/admin/website/index" title="平台管理">平台管理</a> &gt;
            <a href="${ctx}/admin/link/index" ; title="">友情链接管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/link/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${link.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">链接名称：<b class="color-red">*</b></th>
                    <td >
                      <input type="text" name="title" value="${link.title}"/>
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">链接地址：<b class="color-red">*</b></th>
                    <td >
                           <input name="url"  class="text_sketch" value="${link.url}" type="text">
                        
                    </td>
                </tr>
                

                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <input name="sortNum"  class="text_sketch" value="${link.sortNum}" type="text">
                        
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
        </div>
        
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
        window.location.href = "${ctx}/admin/link/index";
    });

    //验证
    $("#form").validate({
        rules:{
            title:{
            	 required: true,
                 maxlength: 20
                  },
            image:{
            	required: true,
                maxlength: 500
                  },
            url:{
                required: true,
                url:true
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


