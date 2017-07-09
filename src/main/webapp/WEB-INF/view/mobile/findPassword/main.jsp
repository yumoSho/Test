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
    <title>找回密码首页</title>
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
        <div class="main login samePadding">
                <ul class="findPwdIndex">
                    <li class="fleft"><a href="${ctx}/retrieve/findByPhone">手机找回</a></li>
                    <li class="fright"><a href="${ctx}/retrieve/findByMail">邮箱找回</a></li>
                </ul>
        </div><!-- //main -->
    </div><!-- //wrap -->
</body>
</html>
