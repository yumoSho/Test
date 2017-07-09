﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<c:set var="com.glanway.jty.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>帮助中心</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/mobile/helpcenter.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main">

        <div class="newsContent samePadding">
            <%--<p class="contentTitle overflow">${news.title }</p>--%>
            <div class="listContent" style="font-size: 0.24rem;line-height: 0.48rem;letter-spacing: 0.01rem;">
                ${supportContent.content }
            </div>
        </div>

      <%--  <div class="shopGuide">
            <p>购物指南</p>
            <ul class="clearfix">
                <li><a href="#">导购演示</a></li>
                <li><a href="../login/regist.html">免费注册</a></li>
            </ul>
        </div>
        <div class="shopGuide">
            <p>支付方式</p>
            <ul class="clearfix">
                <li><a href="../cart/payOrder.html">支付方式</a></li>
                <li><a href="../login/regist.html">充值说明</a></li>
                <li><a href="#">礼品卡说明</a></li>
                <li><a href="#">优惠券使用</a></li>
                <li><a href="#">发票说明</a></li>
            </ul>
        </div>
        <div class="shopGuide">
            <p>物流配送</p>
            <ul class="clearfix">
                <li><a href="#">运费收取说明</a></li>
                <li><a href="../login/regist.html">免费注册</a></li>
                <li><a href="#">配送范围</a></li>
                <li><a href="#">物理查询</a></li>
            </ul>
        </div>
        <div class="shopGuide">
            <p>售后服务</p>
            <ul class="clearfix">
                <li><a href="#">退换货政策</a></li>
                <li><a href="#">退换货流程</a></li>
                <li><a href="helpCenter.html">常见问题</a></li>
            </ul>
        </div>
        <div class="shopGuide">
            <p>会员中心</p>
            <ul class="clearfix">
                <li><a href="#">会员权利与义务</a></li>
                <li><a href="#">积分说明</a></li>
                <li><a href="#">会员等级说明</a></li>
            </ul>
        </div>
        <div class="shopGuide">
            <p>合作入驻</p>
            <ul class="clearfix">
                <li><a href="#">入驻说明</a></li>
            </ul>
        </div>
    </div>--%><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
</body>
</html>
