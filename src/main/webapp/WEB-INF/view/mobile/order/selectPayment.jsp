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
    <title>选择支付</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/cart.css" rel="stylesheet"/>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%--head--%>
    <c:set value="选择支付" var="common"/>
    <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/head.jspf" %>
    <div class="main">
        <input type="hidden" name="isMobile" id="isMobile" value="${isMobile}">
        <form action="${ctx}/payment/pay" method="get" id="payForm">
            <div class="payList">
        <c:if test="${!rechargePay}">
                <a href="javascript:void(0)" class="on changeL"><span class="hideWidth">账户余额 <span class="money"> ￥${balance}</span></span><input type="radio" name="channel" value="balance" style="display: none" <c:if test="${!rechargePay}">checked="checked"</c:if>/></a>
        </c:if>
                <a href="javascript:void(0);" class="<c:if test="${rechargePay}">on</c:if> /> alipay" style="display: none"><img src="${ctx}/images/mobile/iconPay1.png" />支付宝支付<input type="radio" name="channel" value="alipay" style="display: none" <c:if test="${rechargePay}">checked="checked"</c:if> /></a>
                <a href="javascript:void(0);" class="wxpay" style="display: none"><img src="${ctx}/images/mobile/iconPay2.png" />微信支付<input type="radio" name="channel" value="wxpay" style="display: none" /></a>
                <%--<a href="javascript:void(0);"><img src="${ctx}/images/mobile/iconPay3.png" />银联支付<input type="radio" name="channel" value="union_pay" style="display: none" /></a>--%>
            </div>
            <div class="personlBut">
                <input type="hidden" name="orderNo" value="${orderGroupCode}">
                <input type="hidden"  name="payType" value="${payType}">
                <a href="javascript:" onclick="$('#payForm').submit()" class="cart_pay_btn">前往支付</a>
            </div>
        </form>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
</div><!-- //wrap -->
<!--左侧导航-->
<!--左侧导航-->
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>

<script>
    $(function () {
        $(".payList a").click(function () {
            $("input:radio").removeAttr("checked");
            $(this).find("input:radio").attr("checked","checked");
            $(this).addClass("on").siblings().removeClass("on");
        });
    });
</script>

<script>
    $(function(){
        var isMobile = $("#isMobile").val();
        if(isMobile && (isMobile != 'false')){
            if(is_weixn()){
                $(".wxpay").show();
            } else {
                $(".alipay").show();
            }
        }
    });
    function is_weixn() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {

            return true;
        } else {
            return false;
        }
    }

</script>
</body>
</html>
