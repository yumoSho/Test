<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录</title>
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
            <span>登录</span>
        </div>
    </div><!-- //head -->
    <div class="main">
        <div class="login samePadding">
            <input type="hidden" name="redirectURL" value="${param.redirectURL}" id="redirectURL">
                <form id="loginForm" class="loginForm formDiv">
                <ul class="displayFrame">
                    <li>
                        <span class="spanWidthsperd">账号</span>
                        <input type="text" placeholder="用户名/手机/邮箱" id="name" name="loginKey" autofocus />
                    </li>
                    <li>
                        <span class="spanWidthsperd">密码</span>
                        <input type="password" placeholder="密 码" name="password"  id="password" />
                    </li>
                </ul>
                <input type="button" value="登录" class="sameBtn" id="submit" />
            </form>
            <div class="loginLink">
                <a href="${ctx}/reg" class="fleft">立即注册</a>
                <a href="${ctx}/retrieve/main" class="fright">忘记密码？</a>
            </div>
        </div>
        <div class="loginBy clearfix">
            <p>使用第三方登录</p>
            <a href="#">
                <img src="${ctx}/images/mobile/login1.jpg" alt="微信"  />
            </a>
            <a href="#" class="sina">
                <img src="${ctx}/images/mobile/login2.jpg" alt="微博" />
            </a>
            <a href="#">
                <img src="${ctx}/images/mobile/login3.jpg" alt="QQ" />
            </a>
        </div>
    </div>
    <!-- //main -->
    <div class="errorLabel"></div>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/loginAndRegister/login.js"></script>
</body>
</html>
