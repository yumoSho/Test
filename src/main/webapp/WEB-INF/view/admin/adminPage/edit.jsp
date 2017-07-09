<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<%--<%@include file="${ctx}/include/elibs.jspf" %>--%>
<%--<%@include file="${ctx}/include/admin/support.jspf" %>--%>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }
        .layout-split-east  {
            border-left: #f5f5f5;
        }
    </style>

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
            <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
            <a href="${ctx}/admin/page/index" title="页面管理">页面管理</a> &gt;
            编辑页面
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <%-- <div data-options="region:'east',split:true" title="包含页面" style="height: 800px; width: 350px;"></div>--%>
            <div <%--class="editPage"--%> data-options="region:'center',split:true" title="基础信息">
                <form id="fromSave" action="${ctx}/admin/page/update" method="post">
                    <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                        <tbody>
                        <tr>
                            <th width="130" align="right">页面名称：<b class="color-red">*</b></th>
                            <td>
                                <input type="text" name="name" maxlength="20" value="${Page.name}"/>
                                <input type="hidden" name="id" value="${Page.id}"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">所属模块：<b class="color-red">*</b></th>
                            <%--<td>
                                <select name="moduleId"  style="width: 145">
                                    <option value="" selected="selected">请选择</option>
                                    <c:forEach items="${mlist.data}" var="module">
                                        <option <c:if test="${Page.moduleId==module.id}">selected="selected" </c:if> value="${module.id}">${module.name}</option>
                                    </c:forEach>
                                </select>
--%>

                            <td valign="top">
                                <select name="moduleId" class="select chosen-select" data-placeholder="请选择模块">
                                    <c:forEach var="module" items="${mlist.data}">
                                        <option <c:if test="${Page.moduleId==module.id}">selected="selected" </c:if> value="${module.id}">${module.name}</option>
                                    </c:forEach>
                                </select>
                            <span class="errorTip"></span>
                            </td>

                        </tr>
                        <tr>
                            <th width="130" align="right">访问URL：<b class="color-red">*</b></th>
                            <td>
                                <input type="text" name="url" value="${Page.url}">
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th align="right">备注：</th>
                            <td>
                                <input type="text" name="remark" value="${Page.remark}">
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th align="right">排序：<b class="color-red">*</b></th>
                            <td valign="top">
                                <span id="sort-spinner" class="spinner">
                                    <input id="sort" name="sort" class="input" value="${Page.sort}">
                                    <span class="spin-buttons">
                                        <span class="btn-up"></span>
                                        <span class="btn-down"></span>
                                    </span>
                                </span>
                                <span class="errorTip"></span>
                                <script>
                                    $('#sort-spinner').spinner({
                                        max: 9999
                                        , min: 0
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


    <m:f/>

    <script type="text/javascript">

        $(function(){
            $('.chosen-select').chosen({});
        	var pageIdAndName = []
            $.validator.addMethod("isPageExists", function(value, element){
                var exists = 0;
                pageIdAndName = [$("#pageId").val(), $("#pageName").val()];
                $.ajax({
                    url: '/admin/page/checkIsPageExists',
                    type: 'post',
                    async: false,
                    data: {'pageIdAndName': pageIdAndName},
                    dataType: 'json',
                    success: function(data) {
                        exists = data.isExists;
                    }
                });
                return this.optional(element) || !exists;
            },"该模块名已存在");

            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/page/index";
            });

            //验证
            $("#fromSave").validate({
                rules:{
                    name:{
                        required: true,
                        maxlength: 25,
                        isPageExists: true
                    },
                    "moduleId":{
                        required: true
//                    minlength: 31
                    },
                    url:{
                        required: true,
                        maxlength: 100
                    },
                    remark: {
                        maxlength: 200
                    },
                    sort: {
                    	required: true,
                    	digits: true,
                        maxlength: 8
                    }
                },
                messages:{
                    name:{
                        required: "页面名称不能为空",
                        maxlength: "页面名称不能超过25个字符"
                    },
                    "moduleId":{
                        required: "请选择所属模块"
                    },
                    url:{
                        required: "访问URL不能为空",
                        maxlength: "访问URL不能超过100个字符"
                    },
                    remark: {
                        maxlength: "备注长度不能超过200个字符"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.siblings("span:.errorTip"));
                    if(element.attr("name")=="sort"){
                        error.appendTo(element.parent().siblings("span:.errorTip"));
                    }
                },
                submitHandler: function (form) {
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }
            });
        })

    </script>