<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>找回密码3</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/login.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <div class="head">
        <div class="pageTitle writeWrap samePadding clearfix headTop">
            <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
            <span>找回密码</span>
        </div>
    </div><!-- //head -->
    <div class="main">
        <div class="samePadding">
            <form class="setPasswordBox formDiv " method="post" action="${ctx}/retrieve/change">
                <ul class="displayFrame">
                    <li>
                        <span class="spanWidth">密码：</span>
                        <input type="password" name="password" class="password" placeholder="请输入新密码">
                        <input type="hidden" name="key" value="${key}">
                    </li>
                    <li>
                        <span class="spanWidth">确认密码：</span>
                        <input type="password" name="confirm_password" placeholder="再次输入密码">
                    </li>
                </ul>
                <input type="submit" value="下一步" class="nextStep PWDnextStep sameBtn">
            </form>
        </div>
    </div><!-- //main -->
    <div class="errorLabel"></div>
</div><!-- //wrap -->
</body>
<script>
    $(function () {
        //设置新密码
        $(".setPasswordBox").validate({
            errorLabelContainer: $(".errorLabel"),
            rules: {
                password: {
                    required: true,
                    rangelength: [6, 20]
                },
                confirm_password: {
                    required: true,
                    rangelength: [6, 20],
                    equalTo: ".password"
                }
            },
            messages: {
                password: {
                    required: "请输入密码",
                    rangelength: $.validator.format("请输入长度6-20位字符")
                },
                confirm_password: {
                    required: "请输入密码",
                    rangelength: $.validator.format("请输入长度6-20位字符"),
                    equalTo: "两次密码输入不一致"
                }
            },
            highlight: function (element, errorClass) {
                $(element).parents(".inputBox").siblings(".correct").remove();
            },
            unhighlight: function (element, errorClass) {
                $(element).parents("td").append("<span class='correct'></span>");
            }
        });

    });
</script>
</html>
