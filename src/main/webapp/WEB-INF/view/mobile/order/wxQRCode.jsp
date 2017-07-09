<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
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
    <%@ include file="/WEB-INF/view/include/common.jspf" %>
    <link href="${ctx}/css/cart.css" rel="stylesheet"/>
    <%--标识购物车图标是否显示--%>
    <% request.setAttribute("isOrderpage", true);%>
    <script src="${ctx}/js/lib/qrcode/jquery.qrcode.min.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <!-- //head -->
    <%@include file="/WEB-INF/view/include/head.jspf" %>
    <div class="main">
        <div class="sameWidth clearfix">
            <!-- 购物车公用流程-->
            <div class="cartFlow fright clearfix">
                <!-- 第一步-->
                <div class=" flow flow1">
                    <div class="flowBgWrap">
                        <div class="flowBg" style="background-color: #e60019; border: 1px solid #e60019;  border-right-color: #e60019;border-left-color: #e60019;"></div>
                        <span class="flowNum" style="background-color: #e60019">1</span>
                    </div>
                    <div class="flowDes" style="color: #e60019">1、我的购物车</div>
                </div>
                <!-- 第二步-->
                <div class=" flow flow1">
                    <div class="flowBgWrap">
                        <div class="flowBg" style="background-color: #e60019; border: 1px solid #e60019;  border-right-color: #e60019;border-left-color: #e60019;"></div>
                        <span class="flowNum" style="background-color: #e60019">2</span>
                    </div>
                    <div class="flowDes" style="color: #e60019">2、填写核对订单信息</div>
                </div>
                <!-- 第三步-->
                <div class=" flow flow1">
                    <div class="flowBgWrap">
                        <div class="flowBg" style="background-color: #e60019; border: 1px solid #e60019;  border-right-color: #e60019;border-left-color: #e60019;"></div>
                        <span class="flowNum"style="background-color: #e60019">3</span>
                    </div>
                    <div class="flowDes" style="color: #e60019">3、成功提交订单</div>
                </div>
                <!-- 第四步-->
                <div class=" flow flow1">
                    <div class="flowBgWrap">
                        <div class="flowBg"></div>
                        <span class="flowNum">4</span>
                    </div>
                    <div class="flowDes">4、订单支付</div>
                </div>
            </div>
            <!-- 选择支付方式-->
            <div class="submitsuccNotice fleft">
                <div style="width: 300px;margin: 20px auto">
                    <div style="width:200px;margin:0 auto">
                        <img id="code" />
                    </div>
                    <br>
                    <p style="text-align: center">支付金额：${fee}￥,扫描二维码完成支付</p>
                </div>
            </div>
        </div>
    </div>
    <!-- //main -->
    <!-- //foot -->
    <%@include file="/WEB-INF/view/include/foot.jspf" %>
    <!-- 右侧悬浮框-->
    <%@include file="/WEB-INF/view/include/rightFloat.jspf" %>
</div>
</body>
</html>
<script>
    $(function () {
        $("#code").attr("src","${ctx}/payment/createImage?qrcode=${qrCode}");

        var TIME = setInterval(OrderPaySuccess,5000);

        function OrderPaySuccess(){
            var num=0;
            $.post("${ctx}/payment/OrderPaySuccess",{mergeCode:'${mergeCode}',memberId:'${memberId}'} ,function (date) {
                if(date=="success"){
                    clearInterval(TIME);
                    window.location.href ="${ctx}/order/paySuccess";
                }
            });
        }
    });
</script>
