<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>选择支付</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<%--
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
--%>
    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/cart.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="writeWrap wrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
    <div class="main">
        <div class="payList">
            <a href="javascript:void(0);" payName="alipay" class="on"><img src="${ctx}/images/mobile/iconPay1.png" />支付宝支付</a>
            <a href="javascript:void(0);" payName="wxpay"><img src="${ctx}/images/mobile/iconPay2.png" />微信支付</a>
            <a href="javascript:void(0);" payName="unionpay"><img src="${ctx}/images/mobile/iconPay3.png" />银联支付</a>
        </div>
        <div class="personlBut changeBgc1"><a href="javascript:void(0)" onclick="$('#payForm').submit()">前往支付</a></div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<form action="${ctx}/payment/pay" method="post" id="payForm">
    <input type="hidden" id="payCode" name="channel" value="alipay">
</form>
<script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
<script src="${ctx}/js/mobile/common.js"></script>
<script>

    $(function () {
        $(".payList a").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
            $("#payCode").val($(this).attr("payName"));
        });
    });
</script>
</body>
</html>
