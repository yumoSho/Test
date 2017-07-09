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
    <title>订单提交成功</title>
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
                <a href="../indexIndex.html">首页</a><span class="forGt"> &gt;</span><span>购物车</span>
            </div>
        </div>
        <div class=" clearfix">
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
                <div class=" flow">
                    <div class="flowBgWrap">
                        <div class="flowBg"></div>
                        <span class="flowNum">4</span>
                    </div>
                    <div class="flowDes">4、订单支付</div>
                </div>
            </div>
            <!-- 信息提示-->
            <div class="submitsuccNotice fleft">
                <div class="title">订单提交成功，请尽快完成支付</div>
                <p>请您在提交订单后 30分钟 内完成支付，否则订单会自动取消</p>
                <div class="payNums">
                    订单金额：<i>￥${payTotalPrice} </i>  |  应付金额：<span>￥${payTotalPrice} </span>
                </div>
                <form action="${ctx}/order/selectPayment" method="post" id="payForm">
                    <input type="hidden" name="orderGroupCode" value="${orderGroupCode}">
                    <input type="hidden" name="payTotalPrice" value="${payTotalPrice}">
                    <a href="javascript:" onclick="$('#payForm').submit()" class="payForOrder btn" >去支付</a>
                </form>
            </div>
        </div>
    </div>><!-- //main -->
    <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
    <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
</div><!-- //wrap -->
<script>
</script>
</body>
</html>
