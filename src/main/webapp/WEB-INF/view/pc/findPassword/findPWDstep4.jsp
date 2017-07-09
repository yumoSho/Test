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
    <title>找回密码4-${seo.title}</title>
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
            <div id="phoneBack4">
                <div class="clearfix succTxt">
                    <img src="${ctx}/images/pc/succ.png" alt="成功" class="fl" width="22" height="22" />
                    <span class="fr">新密码设置成功，请您牢记新密码！</span>
                </div>
                <div class="backCenter">
                    <a href="${ctx}/" class="fl">返回首页</a>
                    <a href="${ctx}/person-center/index" class="fr">个人中心</a>
                </div>
            </div>
        </div>
    </div>
    <!-- //main -->
    <div class="foot">
        <%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %>
    </div>
    <!-- //foot -->
</div>
<!-- //wrap -->
<script src="../../js/jquery-1.7.2.min.js"></script>
<script src="../../js/common.js"></script>
<script>
    $(function () {

    });
</script>
</body>
</html>
