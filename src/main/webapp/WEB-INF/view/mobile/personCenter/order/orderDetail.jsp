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
    <title>订单详情</title>
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
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/myOrder.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <c:set value="true" var="isReg"/>
    <c:set value="订单详情" var="title"/>
    <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
    <div class="main">
        <div class="orderDetail font24 padding10">
            <div class="pOrder">
                <c:if test="${order.status == 4}">${simpleWebsite.name}已经发货了!</c:if>
            </div>
            <div class="orderAdd writeWrap">
                <div class="clearfix">
                    <span class="receipL fleft">收货人：${order.receiver}</span><span class="fright">${order.contact}</span>
                </div>
                <p class="orderText">收货地址：${order.address}</p>
            </div>
            <div class="orderStore bgblack">
                <%--<p>店铺：${order.storeName}</p>--%>
                <p>订单编号: ${order.code}</p>
            </div>
<c:forEach var="orderDetail" items="${order.orderDetails}">
    <dl class="store writeWrap">
        <dt>
            <a href="${ctx}/mobile/detail/${orderDetail.goodsId}" title="${orderDetail.goodsName}" rel="nofollow"><img src="${ctx}/${orderDetail.goodsImage}" /></a>
        </dt>
        <dd>
            <a href="${ctx}/mobile/detail/${orderDetail.goodsId}" class="overflow">${orderDetail.goodsName}</a>
            <p>数量：${orderDetail.goodsNum}</p>
            <p>价格：<span class="storeRed">¥${orderDetail.sellPrice * orderDetail.goodsNum}</span></p>
        </dd>
    </dl>
</c:forEach>

            <div class="orderPrice samePadding writeWrap clearfix">
                <span class="fleft">实付款（含运费）</span><span class="fright storeRed">¥${order.price}</span>
            </div>
            <div class="remarks writeWrap">
                <p>
                    <span class="fleft">备&emsp;&emsp;注：</span>
                    <span class="fright">${order.remark}</span>
                </p>
                <p class="orderRe">下单时间：<fmt:formatDate value="${order.createdDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></p>
                <p class="orderRe">付款时间：<fmt:formatDate value="${order.payDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></p>
                <p class="orderRe">发货时间：<fmt:formatDate value="${order.deliveryDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></p>
            </div>
        </div>
        <c:if test="${order.status == 4 || order.status == 6 || order.status == 5}">
            <div class="redBut"><a href="${ctx}/mobile/myCenter/order/expressDetail/${order.id}">查看物流</a></div>
        </c:if>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
<script src="${ctx}/js/mobile/common.js"></script>
</body>
</html>
