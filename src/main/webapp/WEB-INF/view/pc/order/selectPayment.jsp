﻿<!DOCTYPE html>
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
    <title>订单支付</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/cart.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
    <div class="main samewidth">
        <!-- 面包屑 -->
        <div class="positionWrap">
            <div class="position">
                <span>您所在当前位置：</span>
                <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>购物车</span>
            </div>
        </div>
        <!-- 购物车公用流程-->
        <div class="cartFlow clearfix">
            <!-- 第一步-->
            <div class=" flow on">
                <div class="flowBgWrap">
                    <div class="flowBg"></div>
                    <span class="flowNum">1</span>
                </div>
                <div class="flowDes">1、我的购物车</div>
            </div>
            <!-- 第二步-->
            <div class=" flow on">
                <div class="flowBgWrap">
                    <div class="flowBg"></div>
                    <span class="flowNum">2</span>
                </div>
                <div class="flowDes">2、填写核对订单信息</div>
            </div>
            <!-- 第三步-->
            <div class=" flow  on">
                <div class="flowBgWrap">
                    <div class="flowBg"></div>
                    <span class="flowNum">3</span>
                </div>
                <div class="flowDes">3、成功提交订单</div>
            </div>
            <!-- 第四步-->
            <div class=" flow  on">
                <div class="flowBgWrap">
                    <div class="flowBg"></div>
                    <span class="flowNum">4</span>
                </div>
                <div class="flowDes">4、订单支付</div>
            </div>
        </div>
        <!-- 选择支付方式-->
        <div class="payWays checkOrderMessage fleft">
            <div class="noticeTitle">选择支付方式</div>
            <form action="${ctx}/payment/pay" method="post" id="payForm">
                <c:if test="${!rechargePay}">
                    <div class="choiceWay">
                        <p class="payTitle">余额支付：</p>
                        <label><input type="radio" name="channel" value="balance" <c:if test="${!rechargePay}"> checked="checked" </c:if> /> &nbsp;账户余额</label>
                        <span class="hasMoney">￥<span>${balance}</span></span>
                        <c:if test="${balance < payTotalPrice}">
                            <span class="moneyUnAble">余额不足</span>
                        </c:if>
                        <a href="${ctx}/person-center/recharge" class="interMoney">立即充值</a>
                    </div>
                </c:if>

            <div class="choiceWay">
                <p class="payTitle">快捷支付：</p>

                <label><input type="radio" name="channel" value="alipay" <c:if test="${rechargePay}">checked="checked"</c:if> /> &nbsp;<img
                        src="${ctx}/images/pc/payWay1.png" width="100" height="42"/></label>
                <label><input type="radio" name="channel" value="wxpay" /> &nbsp;<img src="${ctx}/images/pc/payWay2.png" width="54"
                                                                                      height="56"/></label>
                <label><input type="radio" name="channel" value="unionpay"/> &nbsp;<img src="${ctx}/images/pc/payWay3.png" width="65"
                                                                                        height="48"/></label>

            </div>
            <input type="hidden"  name="orderNo" value="${orderGroupCode}">
            <input type="hidden"  name="payType" value="${payType}">
            <a href="javascript:void(0)" onclick="$('#payForm').submit()" class="ordePay btn">立即支付</a>
                </form>
        </div>
    </div><!-- //main -->
    <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
    <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
</div><!-- //wrap -->

</body>
</html>
