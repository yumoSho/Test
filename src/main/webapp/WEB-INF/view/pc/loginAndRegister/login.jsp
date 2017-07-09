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
    <title>登录-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/login.css" rel="stylesheet" />
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
    <div class="loginMain clearfix">
        <div class="loginBanner">
            <img src="${ctx}/${simpleWebsite.defaultImage}_607x534" alt="loginBanner" width="607" height="534" class="fl" />
            <div class="info fl">
                <div class="infoHead"></div>
                <input type="hidden" name="redirectURL" value="${param.redirectURL}" id="redirectURL">
                <form id="signupForm" class="loginForm">
                    <p class="userInfo">
                        <b class="fl">用户登录</b><span class="fr">没有账号？<a href="${ctx}/reg">立即注册</a></span>
                    </p>
                    <p class="clearfix">账号</p>
                    <div class="locat">
                        <input type="text" id="name" name="loginKey" autofocus tabindex="1" class="name" placeholder="请输入您的手机号/用户名">
                    </div>
                    <p class="userPass">
                        <span class="fl">密码</span>
                        <a href="${ctx}/retrieve" class="fr">忘记密码？</a>
                    </p>
                    <div class="locat">
                        <input class="clearfix" type="password" id="password" tabindex="2" autofocus name="password" class="password" placeholder="请输入密码">
                    </div>
                    <label class="clearfix">
                        <%--<input type="checkbox" />--%>
                        <input type="checkbox" name="remember" tabindex="3" value="1" class="checkRadio fl">
                        <span class="fl remCode">自动登录</span>
                    </label> <br />
                    <input type="button" id="submit" value="登&ensp;录" class="btn" />
                    <p class="thirdLogin">使用第三方登录</p>
                    <div class="friendLogin">
                        <a href="#"><img src="${ctx}/images/pc/wx.png" alt="微信" width="71" height="51" /></a>
                        <a href="#"><img src="${ctx}/images/pc/sina.png" alt="新浪" width="71" height="51" /></a>
                        <a href="${ctx}/oauth/qq"><img src="${ctx}/images/pc/qq.png" alt="QQ" width="71" height="51" /></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- //main -->
    <div class="foot">
        <%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %>
    </div>
    <!-- //foot -->
</div>
    <script src="${ctx}/js/pc/loginAndRegister/login.js"></script>
</body>
</html>
