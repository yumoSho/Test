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
    <title>找回密码3-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/login.css" rel="stylesheet" />
    <link href="${ctx}/css/pc/backPwd.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <div class="loginHead">
        <div class="samewidth">
            <a href="${ctx}/">
                <img src="${ctx}/${simpleWebsite.logo}_176x96" alt="logo" width="176" height="96" />
            </a>
        </div>
    </div>
    <!-- //head -->
    <div class="backPwdMain">
        <p>
            <span class="fl">找回密码</span>
            <span class="fr">已有账号，<a href="${ctx}/login">去登录</a></span>
        </p>
        <div class="phoneBackPwd1">
            <p>
                <a class="fl phoneBack changeBorC">手机找回</a>
                <a class="fr emailBack">邮箱找回</a>
            </p>
            <form id="phoneBack3" class="setPasswordBox " method="post" action="${ctx}/retrieve/change">
                <div>
                    <p>新密码</p>
                    <div class="locat">
                        <input type="password" name="password" class="password" placeholder="请输入新密码">
                        <input type="hidden" name="key" value="${key}">
                    </div>
                    <p>确认密码</p>
                    <div class="locat">
                        <input type="password" autocomplete="off" name="confirm_password" placeholder="再次输入密码">
                    </div>
                    <div class="addTop">
                        <input type="submit" value="下一步" class="nextStep nextBtn3">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- //main -->
    <div class="foot"><%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %></div>
    <!-- //foot -->
</div>
<!-- //wrap -->
<script src="${ctx}/js/pc/findPassword/findPWDstep3.js"></script>

</body>
</html>
