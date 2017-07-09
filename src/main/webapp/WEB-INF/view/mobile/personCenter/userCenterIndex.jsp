﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人中心</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/mobile/personalCenter.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <div class="head">
        <div class="pageTitle headTop samePadding clearfix">
            <a href="${ctx}/mobile/person-center/setting" class="hSett fleft"><img src="${ctx}/images/mobile/icon50.png" /></a>
            <span>个人中心</span>
            <a href="${ctx}/mobile/contentManagement/helpCenter" class="hSett fright"><img src="${ctx}/images/mobile/helpIcon.png" /></a>
        </div>
    </div><!-- //head -->
    <div class="main personalMargin">
        <div class="personalHome padding10">
            <!--未登录
        <div class="personImg writeWrap clearfix">
            <span class="homeImg fleft"><img src="${ctx}/images/mobile/personal1.png" /></span>
            <div class="spanName">
                <a href="../login/login.html">登录/注册</a>
            </div>
        </div>
        -->
            <!--登录状态-->
            <div class="personImg writeWrap clearfix">
                <div>
                    <a href="${ctx}/mobile/person-center/userMessages/userMessage" class="homeImg fleft"><c:if test="${empty member.headImage}"><img src="${ctx}/images/mobile/personal.png" /></c:if><c:if test="${not empty member.headImage}"><img src="${ctx}${member.headImage}" /></c:if></a>
                    <div class="userName fleft">
                        <span>${currentTimeInterval}，${member.memberName}！<a href="javaScript:;" class="userLevel">${member.gradeName}</a></span>
                        <p class="font24">我的余额：<span style="color: #ff0000;width: 1.24rem;">&yen;<c:if test="${not empty member.balance }">${member.balance }</c:if><c:if test="${empty member.balance }">0.00</c:if></span><a href="${ctx}/mobile/person-center/recharge" class="rechLook">充值</a></p>
                        <p class="font24">我的充值记录：<a href="${ctx}/mobile/person-center/rechangeHistory" class="rechHisLook">查看</a></p>
                    </div>
                    <span class="settImg fright"><a href="${ctx}/mobile/person-center/userMessages/userMessage"><img src="${ctx}/images/mobile/icon16.png"></a></span>
                </div>
            </div>
            <!--结束-->
            <div class="homeMass writeWrap clearfix">
                <a href="${ctx}/mobile/myCenter/order/orderList?status=1&onId=dfk">
                    <img src="${ctx}/images/mobile/icon46.png" />
                    <p>待付款</p>
                </a>
                <a href="${ctx}/mobile/myCenter/order/orderList?status=3&onId=dfh">
                    <img src="${ctx}/images/mobile/icon47.png" />
                    <p>待发货</p>
                </a>
                <a href="${ctx}/mobile/myCenter/order/orderList?status=4&onId=dsh">
                    <img src="${ctx}/images/mobile/icon48.png" />
                    <p>待收货</p>
                </a>
                <a href="${ctx}/mobile/myCenter/order/orderList?status=6&onId=ywc">
                    <img src="${ctx}/images/mobile/icon49.png" />
                    <p>已完成</p>
                </a>
            </div>
            <div class="HomeMassage bgblack">
                <a href="${ctx}/mobile/myCenter/order/orderList">
                    <img src="${ctx}/images/mobile/icon40.png" class="lfImg" />
                    <span>我的订单</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/comment">
                    <img src="${ctx}/images/mobile/icon41.png" class="lfImg" />
                    <span>我的评价</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/userMessages/saleCoupon">
                    <img src="${ctx}/images/mobile/icon43.png" class="lfImg" />
                    <span>我的优惠券</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/myInvate">
                    <img src="${ctx}/images/mobile/icon42.png" class="lfImg" />
                    <span>我的邀请</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/myBackMoney">
                    <img src="${ctx}/images/mobile/assetIcon.png" class="lfImg" />
                    <span>我的提佣</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/userMessages/chuiceAddr">
                    <img src="${ctx}/images/mobile/icon44.png" class="lfImg" />
                    <span>收货地址</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
                <a href="${ctx}/mobile/person-center/collected">
                    <img src="${ctx}/images/mobile/icon45.png" class="lfImg" />
                    <span>商品收藏</span>
                    <img src="${ctx}/images/mobile/icon16.png" class="rImg" />
                </a>
            </div>
        </div>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <%@ include file="/WEB-INF/view/include/mobile/foot.jspf" %>
    <div class="errorLabel"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
</body>



</html>
