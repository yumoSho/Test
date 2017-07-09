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
    <title>注册_手机</title>
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
            <span>注册</span>
        </div>
    </div><!-- //head -->
    <div class="main">
        <div id="regiseDealDiv">
            <div class="samePadding" id="samePadding">
                <p class="registTitle">
                    <a href="#" class="fleft on">手机注册</a>
                    <a href="${ctx}/reg/toRegisterByMail" class="fleft">邮箱注册</a>
                </p>
                <form class="formDiv registForm">
                    <input type="hidden" name="regType" value="1">
                    <input type="hidden" name="recommendedCode" value="${recommendedCode }">
                    <ul class="displayFrame">
                        <li>
                            <span class="spanWidth"><i class="redStar">*</i>用户名：</span>
                            <input type="text" placeholder="4-20位字符组合" name="memberName" maxlength="20"/>
                        </li>
                        <li>
                            <span class="spanWidth"><i class="redStar">*</i>手机号：</span>
                            <input type="text" placeholder="请输入手机号" name="phone" id="phone" maxlength="11"/>
                        </li>
                        <li class="heightBtn">
                            <span class="spanWidth"><i class="redStar">*</i>验证码：</span>
                            <input type="text" class="verificationCode codeInput" id="codes" name="iCodes" placeholder="请输入验证码" maxlength="4"/>
                            <a href="javascript:void(0);"><img src="${ctx}/captcha/captchaImage" class="fright codeImg change" id="kaptchaImage"/></a>
                        </li>
                        <li class="heightBtn">
                            <span class="spanWidth upgrade"><i class="redStar">*</i>短信验证码：</span>
                            <input type="text" class="verificationCode codeInput" id="pCodes" name="pCodes" placeholder="请输入短信验证码" maxlength="6"/>
                            <input type="button" value="获取短信验证码" class="getCodes" id="noteBtn" />
                        </li>
                        <li>
                            <span class="spanWidth"><i class="redStar">*</i>密码：</span>
                            <input type="password" name="password" id="password" class="password" placeholder="请输入密码" maxlength="20"/>
                        </li>
                        <li>
                            <span class="spanWidth"><i class="redStar">*</i>确认密码：</span>
                            <input type="password" name="confirmPassword" placeholder="再次输入密码" maxlength="20"/>
                        </li>
                    </ul>
                    <div class="agreement clearfix">
                        <label>
                            <input type="checkbox" checked="checked" name="sure" id="sure"/><span class="fleft">我已阅读并同意</span>
                            <p class="agreementP"><a href="javascript:void(0);">《用户注册协议》</a></p>
                        </label>
                    </div>
                    <input type="button" value="注册" class="sameBtn" id="submit" />
                </form>
                <div class="loginLink clearfix">
                    <a href="${ctx}/login" class="fleft">去登录</a>
                    <a href="${ctx}/retrieve/main" class="fright">忘记密码？</a>
                </div>
            </div>
        </div>

    </div><!-- //main -->

    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    <div class="errorLabel"></div>
</div><!-- //wrap -->
<div class="iframeDiv samePadding" id="iframeDiv">
    <iframe src="${ctx}/reg/registDeal" class="iframeTwo" id="iframeTwo"></iframe>
</div>
</body>
<script src="${ctx}/js/mobile/loginAndRegister/register.js"></script>
</html>
