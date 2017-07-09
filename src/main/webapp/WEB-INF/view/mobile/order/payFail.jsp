<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>订单支付失败</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/cart.css" rel="stylesheet"/>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="writeWrap wrap">
        <div class="head">
            <div class="pageTitle writeWrap samePadding clearfix headTop">
                <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <span>订单支付</span>
            </div>
        </div><!-- //head -->
        <div class="main">
            <div class="payNotice">
                <img src="${ctx}/images/mobile/payFail.png" />
                <p>订单支付失败！</p>
            </div>
            <form action="${ctx}/order/selectPayment" method="post" id="payForm">
                <input type="hidden" name="orderGroupCode" value="${param.orderGroupCode}">
                <input type="hidden" name="payTotalPrice" value="${param.payTotalPrice}">
                <div class="personlBut changeBgc1"><a href="javascript:" onclick="$('#payForm').submit()" >重新支付</a></div>
            </form>
        </div><!-- //main -->
    </div><!-- //wrap -->
</body>
</html>
