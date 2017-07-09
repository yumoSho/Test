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
            <a href="${ctx}/admin/supportContent/index" title="">帮助中心内容管理</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                
                <tr>
                    <th width="130" align="right">类别：<b class="color-red">*</b></th>
                    <td >
                        <select id="supportCategory_category" name="supportCategory.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${typeList}" var="t">
                                <option value="${t.id}">${t.name}</option>
                            </c:forEach>
                        </select>
                        <label for="supportCategory_category" generated="true" class="error"></label>
                        <script>
                            $('.chosen-select').chosen({});
                        </script>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">标题：<b class="color-red">*</b></th>
                    <td >
                      <input name="title" class="text_sketch" type="text">
                        
                    </td>
                </tr>
                
                
                <tr>
                    <th width="130" align="right">内容：<b class="color-red">*</b></th>
                    <td >
                           <textarea id="editor_" name="content" style="height: 350px"></textarea>

                        
                    </td>
                </tr>

                 <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                        <span id="sort-spinner" class="spinner">
<input id="sortNum" name="sortNum" class="input" value="1">
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
        </div>
        <!-- //End 主内容区 -->
    </div>
  </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>

    <script type="text/javascript">

        $(function(){
            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/supportContent/index";
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

