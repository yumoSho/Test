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
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/supportCategory/save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="parent.id" value="${supportCategory.id}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">所属分类：<b class="color-red">*</b></th>
                    <td >
                   
                       <input type="text" name="show_parent" value="${supportCategory.name }"/>
                        
                      <%--   <select class="text_sketch" name="parent.id">
	                      <c:forEach items="${supportCategories}" var="supportCategory" >
		                      <c:if test="${empty supportCategory.parent }">
						          <option value="${supportCategory.id }">${supportCategory.name }</option>
						      </c:if>  			          
					      </c:forEach>
			        	</select> --%>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">分类名称：<b class="color-red">*</b></th>
                    <td >
                           <input type="text"  name="name" />

                        
                    </td>
                </tr>

                 <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <input name="sortNum" value="1" class="text_sketch" type="text">
                        
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
        </div>
        <!-- //End 主内容区 -->
    </div>
  </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>

    <script type="text/javascript" src="${ctx}/js/admin/initeditor.js"></script>
    <script type="text/javascript">

        $(function(){
            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/supportCategory/index";
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

