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
    <title>找回密码1-${seo.title}</title>
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
            <form id="phoneBack1" class="mobileBox">
                <table>
                    <tr>
                        <td><span class="redStar">*</span><span> 手 机 号：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" maxlength="11" class="txtWidth" placeholder="请输入手机号码" name="mobile" id="mobile">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span> 验 证 码：</span></td>
                        <td>
                            <div class="locat changeL1">
                                <input type="text" maxlength="4" placeholder="输入验证码" name="vcodes" id="vcodes">
                            </div>
                        </td>
                        <td>
                            <a href="javascript:void(0);">
                                <img src="${ctx}/captcha/captchaImage"
                                     style="cursor: pointer;"
                                     class="change kaptchaImage" id="kaptchaImage2" alt="验证码" width="98" height="38" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="button" value="下一步" class="nextStep nextBtn1" id="nextStepByPhone">
                        </td>
                    </tr>
                </table>
            </form>
            <form id="emailBack1" class="mailBox">
                <table>
                    <tr>
                        <td><span class="redStar">*</span><span> 邮 箱 号：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" maxlength="25" class="txtWidth" autofocus placeholder="请输入邮箱地址" id="mail" name="mail">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span> 验 证 码：</span></td>
                        <td>
                            <div class="locat changeL1">
                                <input type="text" maxlength="4" placeholder="输入验证码" name="vcodes" id="codes">
                            </div>
                        </td>
                        <td>
                            <a href="javascript:void(0)">
                                <img src="${ctx}/captcha/captchaImage"
                                     style="cursor: pointer;"
                                     class="change kaptchaImage" id="kaptchaImage" alt="验证码" width="98" height="38" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="button" value="下一步" class="nextStep nextBtn1" id="nextStepByMail">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <!-- //main -->
    <div class="foot">
        <%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %>
    </div>
    <!-- //foot -->
</div>
<!-- //wrap -->
<script src="${ctx}/js/pc/findPassword/findPWDstep1.js"></script>
</body>
</html>
