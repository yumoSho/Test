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
            <a href="${ctx}/admin/seoSetting/index" title="">SEO配置</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">页面路径：<b class="color-red">*</b></th>
                    <td >
                       <%-- <select id="dicId" name="pageDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${dictionaries_page}" var="pg" >
                                <option value="${pg.id }">${pg.dicName }</option>
                            </c:forEach>
                        </select>--%>
                           <input type="text" name="url" >
                          <span class="color:red">如: /index</span>
                    </td>
                </tr>
                <%--<tr>
                    <th width="130" align="right">平台模块：<b class="color-red">*</b></th>
                    <td >
                        <select id="pdId" name="platformDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${dictionaries_plat}" var="pt" >
                                <option value="${pt.id }">${pt.dicName }</option>

                            </c:forEach>
                        </select>
                        <label for="pdId" generated="true" class="error"></label>
                    </td>
                </tr>--%>

                
                <tr>
                    <th width="130" align="right">SEO标题：<b class="color-red">*</b></th>
                    <td >
                           <input  name="title"  class="text_sketch" type="text">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">SEO关键字：<b class="color-red">*</b></th>
                    <td >
                           <input  name="keyWords"  class="text_sketch" type="text">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">SEO描述：<b class="color-red">*</b></th>
                    <td >
                           <textarea  name="content" cols="50" rows="7"></textarea>
                        
                    </td>
                </tr>
               <!--  <tr>
                    <th width="130" align="right">图片链接：<b class="color-red">*</b></th>
                    <td >
                           <input id="u1161_input" name="image" value="http://" class="text_sketch" type="text">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">属性：<b class="color-red">*</b></th>
                    <td >
                         <input id="u1157_input" name="_isEnable" value="checkbox" checked="" type="checkbox">启用
                        
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
                window.location.href = "${ctx}/admin/seoSetting/index";
            });
            $('.chosen-select').chosen({});
            //验证
            $("#form").validate({
                rules:{
                    url:{
                        required: true,
                        maxlength: 300
                    },
                    title:{
                    	 required: true,
                         maxlength: 20
                          },
                    image:{
                    	required: true,
                        maxlength: 500
                          },
                    keyWords:{
                        required: true,
                        maxlength:100
                        },
                    content:{
                        required: true
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
            submitHandlerf: function(form){
                $("#form").find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                form.submit();
            }

        });
        });

    </script>

