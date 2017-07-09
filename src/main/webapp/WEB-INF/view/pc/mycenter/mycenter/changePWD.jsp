﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人信息</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
 	<%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/pc/head.jspf"%>
        <!-- //head -->
       <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>会员中心</span><span class="forGt"> &gt;</span><span>个人信息</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                 <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>
                <div class="rightWrap fr">
                    <div class="sameTitle">修改密码</div>
                    <div class="pwdContent clearfix">
                        <div class="rightNotice color666">
                            <p>小贴士</p>
                            将登录密码设置为8位以上，数字、大小写字母、特殊字符混合型是相对安全的，但通常不方便记忆，在这里我们教您一个小窍门：尝试用字母"O"代替数字零等等。同时，我们建议您每隔半年修改一次登录密码。
                        </div>
                        <div class="pwdTableWrap">
                            <form id="changePWDForm">
                                <table class="changePwdTable">
                                	<tr>
                                        <td colspan="2" style="text-align: center;color: red" id="textMsg"></td>
                                    </tr>
                                    <tr>
                                        <td width="80" class="wordSpace">原密码：</td>
                                        <td width="280"><input type="password" class="pwdInput" name="oldPwd" /></td>
                                    </tr>
                                    <tr>
                                        <td class="wordSpace">新密码：</td>
                                        <td><input type="password" class="pwdInput password" name="newPwd" id="newPwd" /></td>
                                    </tr>
                                    <tr>
                                        <td>确认密码：</td>
                                        <td><input type="password" class="pwdInput" name="confirmPsd" /></td>
                                    </tr>
                                </table>
                                <input type="submit" value="提交" class="submitBtn" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   </div>
    <!-- //wrap -->
    <script type="text/javascript">

    $(function () {
        //表单验证
        $("#changePWDForm").validate({
            rules: {
                oldPwd: "required",
                newPwd:{
                    required: true,
                    rangelength: [6, 20]
                },
                confirmPsd: {
                    required: true,
                    equalTo: ".password"
                }
            },
            messages: {
                oldPwd: "请输入原密码",
                newPwd: {
                    required: "请输入密码",
                    rangelength: $.validator.format("请输入长度6-20位字符")
                },
                confirmPsd: {
                    required: "请再次输入新密码",
                    equalTo: "两次密码不一致"
                }
            },
            submitHandler:function(form){
                $(form).find(":submit").attr("disabled", true).attr("value",
                        "Submitting...")
                $.ajax({
                    type: "POST",
                    url: "${ctx}/person-center/update-pwd",
                    data: $("form").serialize(),
                }).done(function(data){
                    if(data.success){
                        $(form).find(":submit").attr("disabled", false).attr("value",
                                "保存")
                        layer.msg('修改成功！',{ time: 1500}, function(){
                            location.href="${ctx}/login";
                        });
                    }else{
                        $(form).find(":submit").attr("disabled", false).attr("value",
                                "保存")
                        if(data.message){
                            $("#textMsg").text(data.message);
                        }else{
                            location.href="${ctx}/login";
                        }
                    }
                }).fail(function(data){
                    $(form).find(":submit").attr("disabled", false).attr("value",
                            "保存")
                    layer.msg('修改失败！',{ time: 1500}, function(){
                        location.href="${ctx}/login";
                    });
                });
            }
        })
    })
</script>
   
</body>
</html>
