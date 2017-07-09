<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<%--<%@include file="${ctx}/include/elibs.jspf" %>--%>
<%--<%@include file="${ctx}/include/admin/support.jspf" %>--%>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <style type="text/css">
        .table-module01 td{position: relative;padding:8px;}
        .editPage .table-module01 input[type="text"]{line-height: 25px;height: 25px;}
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
            <a href="${ctx}/admin/module/index" title="模块管理">模块管理</a> &gt;
            新增模块
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <%-- <div data-options="region:'east',split:true" title="包含页面" style="height: 800px; width: 350px;"></div>--%>
            <div <%--class="editPage"--%> data-options="region:'center',split:true" title="基础信息">
                <form id="fromSave" action="${ctx}/admin/module/save" method="post">
                    <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                        <tbody>
                        <tr>
                            <th width="130" align="right">模块名：<b class="color-red">*</b></th>
                            <td>
                                <input type="hidden" name="id" >
                                <input type="text" name="name" maxlength="20" class="moduleName"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">排序：<b class="color-red">*</b></th>
                            <%--<td>
                                <input type="text" name="sort" >
                                <span class="errorTip" id="nameSpan"></span>
                            </td>--%>

                            <td valign="top">
                            <span id="sort-spinner" class="spinner">
                                <input id="sort" name="sort" class="input" value="">
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
                            <th align="right">备注：</th>
                            <td>
                                <input type="text" name="remark" >
                                <span class="errorTip"></span>
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
            $.validator.addMethod("isModuleExists", function(value, element){
                var exists = 0;
                $.ajax({
                    url: '${ctx}/admin/module/checkIsModuleExists',
                    type: 'post',
                    async: false,
                    data: {name: value},
                    dataType: 'json',
                    success: function(data) {
                        exists = data.isExists;
                    }
                });
                return this.optional(element) || !exists;
            },"该模块名已存在");

            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/module/index";
            });

            //验证
            $("#fromSave").validate({
                rules:{
                    name:{
                        required: true,
                        maxlength: 25,
                        isModuleExists: true
                    },
                    sort:{
                        required: true,
                        digits: true,
                        maxlength: 3
                    },
                    remark: {
                        maxlength: 200
                    }
                },
                messages:{
                    name:{
                        required: "模块名不能为空",
                        maxlength: "模块名不能超过25个字符"
                    },
                    sort:{
                        required: "排序不能为空",
                        digits: "排序只能为正整数",
                        maxlength: "排序长度不能超过3位"
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
                submitHandler: function(form){
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing","0");
                    form.submit();
                }

            });
        })
    </script>