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
    <title>邮箱注册成功</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/login.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <div class="head">
        <div class="pageTitle writeWrap samePadding clearfix headTop">
            <a href="${ctx}/mobile/" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
            <span>注册成功</span>
        </div>
    </div><!-- //head -->
    <div class="main">
        <div class="login samePadding findPwdEmail">
            <img src="${ctx}/images/pc/succ.png" alt="注册成功" />
            <p>
                激活链接已发送至您的登录邮箱：<a href="javascript:;" id="user-email">${param.mail}</a>
                请您在24小时内登录邮箱，按邮件中的指示完成激活操作<%--如果没有收到邮件，请点击<a href="#">重新发送</a>--%>
            </p>
        </div>
    </div><!-- //main -->
</div><!-- //wrap -->
</body>
<script>
    $(function(){
        //点击登陆邮箱
        $("#user-email").click(function(){
            var $email=$("#user-email").text(),
                    $sEmail=$email.split("@"),
                    length=$sEmail.length,
                    $suffix=$sEmail[length-1];
            var $loginUrl="http://mail."+$suffix;
            location.href = $loginUrl;
        });
    });
</script>
</html>
