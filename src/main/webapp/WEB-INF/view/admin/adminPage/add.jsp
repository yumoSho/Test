<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<%--<%@include file="${ctx}/include/elibs.jspf" %>--%>
<%--<%@include file="${ctx}/include/admin/support.jspf" %>--%>
<m:h>

    <link href="http://cdn.bootcss.com/chosen/1.4.2/chosen.css" rel="stylesheet">
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/default/easyui.css">--%>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/admin/uploader.css">
    <link rel="stylesheet" href="${ctx}/css/admin/admin.css">
    <script type="text/javascript" src="${ctx}/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/chosen/1.4.2/chosen.jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/spinner/spinner.js"></script>
    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }
        .layout-split-east  {
            border-left: #f5f5f5;
        }
        .table-module01 label.error{top: 42px;}
         .table-module01 td{position: relative;padding:8px;}
        .editPage .table-module01 input[type="text"]{line-height: 25px;height: 25px;}

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
            新增页面
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <%-- <div data-options="region:'east',split:true" title="包含页面" style="height: 800px; width: 350px;"></div>--%>
            <div <%--class="editPage"--%> data-options="region:'center',split:true" title="基础信息">
                <form id="fromSave" action="${ctx}/admin/page/save" method="post">
                    <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                        <tbody>
                        <tr>
                            <th width="130" align="right">页面名称：<b class="color-red">*</b></th>
                            <td>
                                <input type="text" name="name" maxlength="20"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">所属模块：<b class="color-red">*</b></th>
                            <td>
                                <%--<select name="moduleId"  style="width: 145px">
                                    <option value="" selected="selected">请选择</option>
                                    <c:forEach items="${mlist.data}" var="module">
                                        <option value="${module.id}">${module.name}</option>
                                    </c:forEach>
                                </select>--%>


                                <select name="moduleId" data-placeholder="请选择模块" class="required select chosen-select brand-chosen ni">
                                    <option></option>
                                    <c:forEach items="${mlist.data}" var="module">
                                        <option value="${module.id}">${module.name}</option>
                                    </c:forEach>
                                </select>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">访问URL：<b class="color-red">*</b></th>
                            <td>
                                <input type="text" name="url" >
                                <span class="errorTip"></span>
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
                            <th align="right">排序：<b class="color-red">*</b></th>
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
            $.validator.addMethod("isPageExists", function(value, element){
                var exists = 0;
                $.ajax({
                    url: '/admin/page/checkIsPageExists',
                    type: 'post',
                    async: false,
                    data: {'name': value},
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
                    moduleId:{
                        required: true
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
                    "module.id":{
                    	required: "请选择所属模块"
                    },
                    url:{
                        required: "访问URL不能为空",
                        maxlength: "访问URL不能超过100个字符"
                    },
                    
                    createdBy:{
                        required: "添加人不能为空",
                        maxlength: "添加人不能超过100个字符"
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