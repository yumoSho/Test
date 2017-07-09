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
    <title>个人信息</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
     <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/pc/head.jspf"%>
        <!-- //head -->
       <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx }/">首页</a><span class="forGt"> &gt;</span><span>会员中心</span><span class="forGt"> &gt;</span><span>个人信息</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                 <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>

                <div class="rightWrap fr myOrder">
                <c:choose>
                <c:when test="${isHave}">
                    <!-- 内页右侧内容 -->
                    <div class="rightWrap fr myOrder">
                        <!-- 内页公共标题 -->
                        <div class="sameTitle">
                            我的订单
                        </div>
                        <!-- 我的订单切换 -->
                        <div class="myOrderTab tabBtn clearfix">
                            <p href="javascript:;" status="-1" class="tab on" id="all" onclick="searchOrderByStatus(this)">
                                全部</p>
                            <p href="javascript:;" status="1" class="tab" id="dfk" onclick="searchOrderByStatus(this)">待付款</p>
                            <p href="javascript:;" status="3" class="tab" id="dfh" onclick="searchOrderByStatus(this)">待发货</p>
                            <p href="javascript:;" status="4" class="tab" id="dsh" onclick="searchOrderByStatus(this)">待收货</p>
                            <p href="javascript:;" status="6" class="tab" id="ywc" onclick="searchOrderByStatus(this)">已完成 </p>
                        </div>
                        <c:if test="${not empty orderList.data}">
                        <table class="centerTable">
                            <tr>
                                <th width="430">商品</th>
                                <th width="85">单价</th>
                                <th width="71">数量</th>
                                <th width="122">合计</th>
                                <th width="139">交易状态</th>
                                <th width="150">交易操作</th>
                            </tr>
                        </table>
                        </c:if>
                        <!-- 我的订单 -->
                        <c:if test="${empty orderList.data}">
                            <div align="center" style="margin-top: 100px;font-weight: bold;font-size: 14px">
                                没有该状态的订单
                            </div>
                        </c:if>
                        <c:if test="${not empty orderList.data}">
                            <c:forEach items="${orderList.data}" var="order">
                                <table class="centerTable" border="1">
                                    <tr>
                                        <input type="hidden" class="orderId" value="${order.id}">
                                        <td width="430"><span><fmt:formatDate value="${order.createdDate}"
                                                                              pattern="yyyy.MM.dd"/></span>
                                            （订单编号：<span>${order.code}</span>）
                                        </td>
                                        <td width="85"></td>
                                        <td width="71"></td>
                                        <td width="122"></td>
                                        <td width="139"></td>
                                        <td width="150"><%-- <a href="${ctx}/store/${order.storeId}">店铺：${order.storeName}</a> --%></td>
                                    </tr>
                                    <c:forEach items="${order.orderDetails}" var="detail" varStatus="index">
                                        <tr>
                                            <td width="430">
                                                <a href="${ctx}/detail/${detail.goodsId}" class="myCentershaoppingImg">
                                                    <img src="${ctx}/${detail.goodsImage}_78x86" width="78" height="86"/>
                                                </a>
                                                <a href="${ctx}/detail/${detail.goodsId}"
                                                   class="myCentershaoppingTitle overflow">${detail.goodsName}</a>
                                            </td>
                                            <td width="85">￥<span>${detail.goodsPrice}</span></td>
                                            <td width="71"><span>${detail.goodsNum}</span></td>
                                            <c:if test="${index.first}">
                                                <td width="122" rowspan="${fn:length(order.orderDetails)}">
                                                    ￥<span>${order.price}</span></td>
                                                <td width="139" class="lineLtem" rowspan="${fn:length(order.orderDetails)}">
                                                    <c:choose>
                                                        <c:when test="${order.status == 1}"><a href="javascript:void(0)"
                                                                                               class="on">待付款</a></c:when>
                                                        <c:when test="${order.status == 2}"><a href="javascript:void(0)"
                                                                                               class="on">已付款</a></c:when>
                                                        <c:when test="${order.status == 3}"><a href="javascript:void(0)"
                                                                                               class="on">待发货</a></c:when>
                                                        <c:when test="${order.status == 4}"><a href="javascript:void(0)"
                                                                                               class="on">待收货</a></c:when>
                                                        <c:when test="${order.status == 5}"><a href="javascript:void(0)"
                                                                                               class="on">已确认收货</a></c:when>
                                                        <c:when test="${order.status == 6}"><a href="javascript:void(0)"
                                                                                               class="on">交易完成</a></c:when>
                                                        <c:when test="${order.status == 7}"><a href="javascript:void(0)"
                                                                                               class="on">交易取消</a></c:when>
                                                        <c:when test="${order.status == 8}"><a href="javascript:void(0)"
                                                                                               class="on">退换货处理中</a></c:when>
                                                        <c:when test="${order.status == 9}"><a href="javascript:void(0)"
                                                                                               class="on">缺货</a>问题/</c:when>
                                                        <c:otherwise> </c:otherwise>
                                                    </c:choose>
                                                    <a href="${ctx}/personalcenter/orderDetails/${order.id}">订单详情</a>
                                                </td>
                                                <td width="150" rowspan="${fn:length(order.orderDetails)}">
                                                    <c:choose>
                                                        <c:when test="${order.status == 1}">
                                                            <a href="javascript:void(0);" target="_blank"
                                                               class="operatebtn pay receiving">立即付款</a>
                                                            <a href="javascript:;" onclick="cancleOrder(this)"
                                                               class="receivingBottom">取消订单</a>
                                                        </c:when>
                                                        <c:when test="${order.status == 2}"></c:when>
                                                        <c:when test="${order.status == 3}"></c:when>
                                                        <c:when test="${order.status == 4}">
                                                            <a href="javascript:;" onclick="receiveOrder(this)"
                                                               class="receiving">确认收货</a>
                                                        </c:when>
                                                        <c:when test="${order.status == 5}">
                                                            <a href="javascript:;" onclick="finishOrder(this)"
                                                               class="receiving">交易完成</a>
                                                        </c:when>
                                                        <c:when test="${order.status == 6 || order.status == 7}">
                                                            <a href="javascript:;" onclick="deleteOrder(this)"
                                                               class="receivingBottom deleBtn">删除</a>
                                                        </c:when>
                                                        <c:otherwise> </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>

                                </table>
                            </c:forEach>
                        </c:if>
                        <!--分页-->
                        <form id="pagination-form" class="pagination-form">
	                		<m:pagination totalPages="${orderList.totalPages}" pageParam="page" skip="false"/>
	                	</form>
                          <%--   <div class="samePage">
                        <table class="pageList">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="activeProPage">
                                        <form id="pagination-form">
                                            <m:pagination totalPages="${orderList.totalPages}" pageParam="page" skip="false"/>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div> --%>
                    </div>
                </c:when>
                <c:otherwise>
                    <!--我的订单(空)-->
                    <div class="emptyList clearfix">
                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
                        <ul class="fl emptyUl">
                            <li>您没有下过单~</li>
                            <li><a href="${ctx}/search">去购物>></a></li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
                    <!-- <div class="sameTitle">我的订单</div>
                    if 没有订单
                    <div class="emptyList clearfix">
                        <img src="../../../images/emptyImg.png" width="160" height="160" class="fleft" />
                        <ul class="fleft emptyUl">
                            <li>您还没有任何订单，快去选购全球好货吧~</li>
                            <li><a href="#">去购物>></a></li>
                        </ul>
                    </div>
                    -- end if
                    <div class="tabBtn clearfix">
                        <p class="on">全部</p>
                        <p>待付款</p>
                        <p>待发货</p>
                        <p>待收货</p>
                        <p>待评价</p>
                    </div>
                    <table class="centerTable">
                        <tr>
                            <th width="430">商品</th>
                            <th width="85">单价</th>
                            <th width="71">数量</th>
                            <th width="122">合计</th>
                            <th width="139">交易状态</th>
                            <th width="150">交易操作</th>
                        </tr>
                    </table>
                    <div class="tabDiv">
                        全部
                        <div class="myOrderList">
                        	<table class="centerTable" border="1">
                        	    <tr>
                        	        <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                        	        <td width="85"></td>
                        	        <td width="71"></td>
                        	        <td width="122"></td>
                        	        <td width="139"></td>
                        	        <td width="150"></td>
                        	    </tr>
                        	    <tr>
                        	        <td width="430">
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                        	                <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                        	            </a>
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                        	        </td>
                        	        <td width="85">￥<span>45.00</span></td>
                        	        <td width="71"><span>2</span></td>
                        	        <td width="122" rowspan="3">￥<span>270.00</span></td>
                        	        <td width="139" class="lineLtem" rowspan="3">
                        	            <a href="#" class="on">待付款</a>
                        	            <a href="../../myCenter/myTrade/orderDetail.html">订单详情</a>
                        	        </td>
                        	        <td width="150" rowspan="3">
                        	            <a href="#" class="receiving">立即付款</a>
                        	            <a href="javascript:void(0);" class="receivingBottom" onclick="delatePop();">取消订单</a>
                        	        </td>
                        	    </tr>
                        	    <tr>
                        	        <td width="430">
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                        	                <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                        	            </a>
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                        	        </td>
                        	        <td width="85">￥<span>45.00</span></td>
                        	        <td width="71"><span>2</span></td>
                        	    </tr>
                        	    <tr>
                        	        <td width="430">
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                        	                <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                        	            </a>
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                        	        </td>
                        	        <td width="85">￥<span>45.00</span></td>
                        	        <td width="71"><span>2</span></td>
                        	    </tr>
                        	</table>
                        	<table class="centerTable" border="1">
                        	    <tr>
                        	        <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                        	        <td width="85"></td>
                        	        <td width="71"></td>
                        	        <td width="122"></td>
                        	        <td width="139"></td>
                        	        <td width="150"></td>
                        	    </tr>
                        	    <tr>
                        	        <td width="430">
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                        	                <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                        	            </a>
                        	            <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                        	        </td>
                        	        <td width="85">￥<span>45.00</span></td>
                        	        <td width="71"><span>2</span></td>
                        	        <td width="122">￥<span>90.00</span></td>
                        	        <td width="139" class="lineLtem">
                        	            <a href="#" class="on">待付款</a>
                        	            <a href="../myDeal/orderDetails.html">订单详情</a>
                        	        </td>
                        	        <td width="150">
                        	            <a href="#" class="receiving">确认收货</a>
                        	        </td>
                        	    </tr>
                        	</table>
                        	<div class="samePage">
                        	    <table class="pageList ">
                        	        <tbody>
                        	            <tr>
                        	                <td>
                        	                    <div>
                        	                        <a class="prev" href="">&lt;</a>
                        	                        <a href="">1</a>
                        	                        <span>2</span>
                        	                        <a href="">3</a>
                        	                        <a href="">4</a>
                        	                        <a href="">5</a>
                        	                        <a class="next" href="">&gt;</a>
                        	                    </div>
                        	                </td>
                        	            </tr>
                        	        </tbody>
                        	    </table>
                        	</div>
                        </div>
                        待付款
                        <div class="myOrderList">
                            <table class="centerTable" border="1">
                                <tr>
                                    <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                                    <td width="85"></td>
                                    <td width="71"></td>
                                    <td width="122"></td>
                                    <td width="139"></td>
                                    <td width="150"></td>
                                </tr>
                                <tr>
                                    <td width="430">
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                            <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                        </a>
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                                    </td>
                                    <td width="85">￥<span>45.00</span></td>
                                    <td width="71"><span>2</span></td>
                                    <td width="122">￥<span>90.00</span></td>
                                    <td width="139" class="lineLtem">
                                        <a href="#" class="on">待付款</a>
                                        <a href="../myDeal/orderDetails.html">订单详情</a>
                                    </td>
                                    <td width="150">
                                        <a href="#" class="receiving">立即付款</a>
                                        <a href="javascript:void(0);" class="receivingBottom" onclick="delatePop();">取消订单</a>
                                    </td>
                                </tr>
                            </table>
                            <div class="samePage">
                                <table class="pageList ">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div>
                                                    <a class="prev" href="">&lt;</a>
                                                    <a href="">1</a>
                                                    <span>2</span>
                                                    <a href="">3</a>
                                                    <a href="">4</a>
                                                    <a href="">5</a>
                                                    <a class="next" href="">&gt;</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        待发货
                        <div class="myOrderList">
                            <table class="centerTable" border="1">
                                <tr>
                                    <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                                    <td width="85"></td>
                                    <td width="71"></td>
                                    <td width="122"></td>
                                    <td width="139"></td>
                                    <td width="150"></td>
                                </tr>
                                <tr>
                                    <td width="430">
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                            <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                        </a>
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                                    </td>
                                    <td width="85">￥<span>45.00</span></td>
                                    <td width="71"><span>2</span></td>
                                    <td width="122">￥<span>90.00</span></td>
                                    <td width="139" class="lineLtem">
                                        <a href="#" class="on">待发货</a>
                                        <a href="../myDeal/orderDetails.html">订单详情</a>
                                    </td>
                                    <td width="150">
                                        <a href="#" class="receiving">确认收货</a>
                                    </td>
                                </tr>
                            </table>
                            <div class="samePage">
                                <table class="pageList ">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div>
                                                    <a class="prev" href="">&lt;</a>
                                                    <a href="">1</a>
                                                    <span>2</span>
                                                    <a href="">3</a>
                                                    <a href="">4</a>
                                                    <a href="">5</a>
                                                    <a class="next" href="">&gt;</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        待收货
                        <div class="myOrderList">
                            <table class="centerTable" border="1">
                                <tr>
                                    <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                                    <td width="85"></td>
                                    <td width="71"></td>
                                    <td width="122"></td>
                                    <td width="139"></td>
                                    <td width="150"></td>
                                </tr>
                                <tr>
                                    <td width="430">
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                            <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                        </a>
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                                    </td>
                                    <td width="85">￥<span>45.00</span></td>
                                    <td width="71"><span>2</span></td>
                                    <td width="122">￥<span>90.00</span></td>
                                    <td width="139" class="lineLtem">
                                        <a href="#" class="on">待收货</a>
                                        <a href="../myDeal/orderDetails.html">订单详情</a>
                                    </td>
                                    <td width="150">
                                        <a href="#" class="receiving">确认收货</a>
                                    </td>
                                </tr>
                            </table>
                            <div class="samePage">
                                <table class="pageList ">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div>
                                                    <a class="prev" href="">&lt;</a>
                                                    <a href="">1</a>
                                                    <span>2</span>
                                                    <a href="">3</a>
                                                    <a href="">4</a>
                                                    <a href="">5</a>
                                                    <a class="next" href="">&gt;</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        待评价
                        <div class="myOrderList">
                            <table class="centerTable" border="1">
                                <tr>
                                    <td width="430"><span>2015.06.04</span> （订单编号：<span>20150615101456</span>）</td>
                                    <td width="85"></td>
                                    <td width="71"></td>
                                    <td width="122"></td>
                                    <td width="139"></td>
                                    <td width="150"></td>
                                </tr>
                                <tr>
                                    <td width="430">
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                            <img src="../../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                        </a>
                                        <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                                    </td>
                                    <td width="85">￥<span>45.00</span></td>
                                    <td width="71"><span>2</span></td>
                                    <td width="122">￥<span>90.00</span></td>
                                    <td width="139" class="lineLtem">
                                        <a href="#" class="on">待评价</a>
                                        <a href="../myDeal/orderDetails.html">订单详情</a>
                                    </td>
                                    <td width="150">
                                        <a href="#" class="receiving">去评价</a>
                                    </td>
                                </tr>
                            </table>
                            <div class="samePage">
                                <table class="pageList ">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div>
                                                    <a class="prev" href="">&lt;</a>
                                                    <a href="">1</a>
                                                    <span>2</span>
                                                    <a href="">3</a>
                                                    <a href="">4</a>
                                                    <a href="">5</a>
                                                    <a class="next" href="">&gt;</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    分页
                     -->
                </div>

            </div>
        </div>
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>

   </div>
    <!-- //wrap -->
     <script src="${ctx}/js/lib/layer/layer.js"></script>
    <script type="text/javascript">

    $(function () {
        var onId = "${param.onId}";
        var beginDate = "${param.beginDate}";
        var endDate = "${param.endDate}";
        var para = "${param.para}";
        $('#beginDate').val(beginDate);
        $('#endDate').val(endDate);
        $('#keywordId').val(para);
        if (onId) {
            $("div.myOrderTab .tab").removeClass("on");

            $("#" + onId).addClass("on");
        }
        $("a.pay").on('click', function () {
            payOrder(this);
        });
    });
    function getStatusDesc(val) {
        var status = "";
        switch (val) {
            case 1:
                status = "待支付";
                break;
            case 2:
                status = "已支付";
                break;
            case 3:
                status = "待发货";
                break;
            case 4:
                status = "待收货";
                break;
            case 5:
                status = "已确认收货";
                break;
            case 6:
                status = "交易完成";
                break;
            case 7:
                status = "交易取消";
                break;
            case 8:
                status = "退换货处理中";
                break;
            case 9:
                status = "问题/缺货";
                break;
            default:
                status = "";
        }
        return status;

    }
    ;
    //按条件搜索订单
    function searchOrder(type) {
        var para = "";
        var beginDate;
        var endDate;
        if (1 == type) {
            para = $("#keywordId").val();
        } else if (2 == type) {
            beginDate = $("#beginDate").val();
            endDate = $("#endDate").val();
        }
        var status = $('.myOrderTab').find('.on').attr('status');
        var onId = $('.myOrderTab').find('.on').attr('id')
        window.location.href = contextPath + '/personalcenter/orderList?type=' + (!type ? -1 : type) + "&onId=" + onId + '&status=' + (!status ? -1 : status) + '&para=' + (!para ? "" : para) + '&beginDate=' + (!beginDate ? "" : beginDate) + '&endDate=' + (!endDate ? "" : endDate);
    }
    ;
    //按状态搜索订单
    function searchOrderByStatus(obj) {
        var $on = $('.myOrderTab').find('.on');
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var status = $this.attr('status');
        window.location.href = contextPath + '/personalcenter/orderList?status=' + (!status ? -1 : status) + "&onId=" + $this.attr('id');

    }
    //删除订单
    function deleteOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        layer.confirm("是否删除此订单？", {
            btn: ["确定", "取消"]
        }, function () {
            window.location.href = contextPath + '/personalcenter/deleteOrder/' + orderId;
            layer.msg("删除成功！", {icon: 1});
        });
    }
    //订单支付
    function payOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath + '/order/selectPayment?id=' + orderId;
    }
    //取消订单
    function cancleOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        layer.confirm("是否取消此订单？", {
            btn: ["确定", "取消"]
        }, function () {
            window.location.href = contextPath + '/personalcenter/cancleOrder/' + orderId;
            layer.msg("取消成功！", {icon: 1});
        });
    }
    //申请退换货
    function returnOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath + '/personalcenter/returnApply/' + orderId;
    }
    //确认收货
    function receiveOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath + '/personalcenter/receive/' + orderId;
    }
    //交易完成
    function finishOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath + '/personalcenter/finish/' + orderId;
    }
    //订单详情
    function orderDetails(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath + '/personalcenter/orderDetails/' + orderId;
    }

</script>

</body>
</html>
