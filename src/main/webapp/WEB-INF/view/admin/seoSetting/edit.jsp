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
            <a href="${ctx}/admin/seoSetting/index" ; title="">SEO配置</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/seoSetting/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${seoSetting.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">页面名称：<b class="color-red">*</b></th>
                    <td >
                       <%-- <select id="dicId" name="pageDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择</option>
                            <c:forEach items="${dictionaries_page}" var="pg" >
                                <option value="${pg.id }" <c:if test="${seoSetting.pageDictionary.id==pg.id }">selected="selected"</c:if>>${pg.dicName }</option>
                            </c:forEach>
                        </select>--%>
                           <input type="text" name="url" value="${seoSetting.url}">
                           <span class="color:red">如: /index</span>

                    </td>
                </tr>
                <%--<tr>
                    <th width="130" align="right">平台模块：<b class="color-red">*</b></th>
                    <td >
                        <select id="pdId" name="platformDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择</option>
                            <c:forEach items="${dictionaries_plat}" var="pt" >
                                <option value="${pt.id }" <c:if test="${seoSetting.platformDictionary.id==pt.id }">selected="selected"</c:if>>${pt.dicName }</option>
                            </c:forEach>
                        </select>
                        <label for="pdId" generated="true" class="error"></label>
                    </td>
                </tr>--%>

                
                <tr>
                    <th width="130" align="right">SEO标题：<b class="color-red">*</b></th>
                    <td >
                           <input  name="title"  class="text_sketch" type="text" value="${seoSetting.title }">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">SEO关键字：<b class="color-red">*</b></th>
                    <td >
                           <input  name="keyWords"  class="text_sketch" type="text" value="${seoSetting.keyWords }">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">SEO描述：<b class="color-red">*</b></th>
                    <td >
                               <textarea  name="content" cols="50" rows="7">${seoSetting.content }</textarea>
                    </td>
                </tr>
                <%-- <tr>
                    <th width="130" align="right">属性：<b class="color-red">*</b></th>
                    <td >
                         <input id="u1157_input" name="_isEnable" value="checkbox" <c:if test="${advertisement.isEnable==true}">checked=""</c:if> type="checkbox">启用
                        
                    </td>
                </tr>
                
                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td >
                         <input id="u1156_input" name="sortNum" value="${advertisement.sortNum}" class="text_sketch" type="text">
                        
                    </td>
                </tr> --%>
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
        window.location.href = "${ctx}/admin/seoSetting/index";
    });
    $('.chosen-select').chosen({});
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
            sortNum:{
            	required: true,
            	digits:true,
            	max: 999
            },
            'pageDictionary.id':{
                required:true
            },
            'platformDictionary.id' : {
                required:true
            }
                             
        },
    submitHandler: function(form){
        $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


