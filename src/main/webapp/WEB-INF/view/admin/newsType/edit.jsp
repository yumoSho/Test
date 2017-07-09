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
            <a href="${ctx}/admin/news/index" ; title="">资讯管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/newsType/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${newsType.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">名称：<b class="color-red">*</b></th>
                    <td >
                      <input name="name" class="text_sketch" type="text" value="${newsType.name }">
                        
                    </td>
                </tr>

                <tr>
                    <th width="130" align="right">是否启用：<b class="color-red">*</b></th>
                    <td >
                        <input name="activated" type="radio" value="1" <c:if test="${newsType.activated}">checked</c:if> > 是
                        <input name="activated" type="radio" value="0" <c:if test="${!newsType.activated}">checked</c:if> > 否
                    </td>
                </tr>
               <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <%--<input name="sortNum" value="${news.sortNum }" class="text_sketch" type="text">--%>
                    <span id="sort-spinner" class="spinner">
<input id="sortNum" name="sortNum" class="input" value="${newsType.sortNum }">
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
        window.location.href = "${ctx}/admin/newsType/index";
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
    	   	
        $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


