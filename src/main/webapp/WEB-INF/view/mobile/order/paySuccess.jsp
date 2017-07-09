<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>支付成功</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/cart.css" rel="stylesheet"/>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%--head--%>
    <c:set value="支付成功" var="common"/>
        <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/head.jspf" %>
    <div class="main">
        <div class="payNotice">
            <img src="${ctx}/images/mobile/paySucc.png" />
            <p>订单支付成功！</p>
        </div>
        <div class="personlBut"><a href="${ctx}/mobile/search">继续购物</a></div>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
</div><!-- //wrap -->
<!--左侧导航-->
<script src="${ctx}/js/mobile/swiper.min.js"></script>
</body>
</html>
