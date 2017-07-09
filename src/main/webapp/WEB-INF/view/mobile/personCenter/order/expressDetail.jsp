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
    <title>物流详情</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/myOrder.css" rel="stylesheet" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="isReg"/>
        <c:set value="物流详情" var="title"/>
            <c:set value="true" var="notShowTop"/>
        <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
        <div class="main">
            <div class="orderDetail font24">
                <div class="logStore bgblack">
                    <%--<p>店铺：Calbebe</p>--%>
                    <p>订单编号: <a href="${ctx}/mobile/myCenter/order/orderDetails/${order.id}">${order.code}</a></p>
                    <p>承运公司：${order.deliverCompany}</p>
                    <p>运单编号：${order.deliverCode}</p>
                </div>
                <p class="track samePadding bgblack">物流跟踪</p>
                <div class="sign bgblack">

                    <c:forEach var="info" items="${routeVOList}" varStatus="index">
                        <c:choose>
                            <c:when test="${index.first}">
                                <div class="signTop">
                                <img src="${ctx}/images/mobile/sign1.png" />
                                <p class="signRed">${info.context}</p>
                            </c:when>
                                    <c:otherwise>
                                    <div class="noSign">
                                    <img src="${ctx}/images/mobile/sign2.png" />
                                        <p class="noSignBrown">${info.context}</p>
                                    </c:otherwise>
                        </c:choose>
                                        <p class="signBrown">${info.time}</p>
                                        </div>
                    </c:forEach>
                    </td>
                </div>
            </div><!-- //main -->
            <div class="leftNavCover"></div>
            <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        </div>
    </div><!-- //wrap -->
</body>
</html>
