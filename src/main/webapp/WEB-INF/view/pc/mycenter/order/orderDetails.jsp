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
              
                <div class="rightWrap fr">
                    <div class="sameTitle color666">我的订单</div>
                    <p class="orderStatus">当前订单状态：
					    <c:choose>
	                        <c:when test="${order.status == 1}">待付款</c:when>
	                        <c:when test="${order.status == 2}">已付款</c:when>
	                        <c:when test="${order.status == 3}">待发货</c:when>
	                        <c:when test="${order.status == 4}">待收货</c:when>
	                        <c:when test="${order.status == 5}">已确认收货</c:when>
	                        <c:when test="${order.status == 6}">交易完成</c:when>
	                        <c:when test="${order.status == 7}">交易取消</c:when>
	                        <c:when test="${order.status == 8}">退换货处理中</c:when>
	                        <c:when test="${order.status == 9}">问题/缺货</c:when>
	                        <c:otherwise> </c:otherwise>
	                    </c:choose>
				
					</p>
                    <!-- 物流信息 -->
                    <c:if test="${!empty order.deliverCode}">
                <div class="orderMsgItem megItem">
                    <h5>物流信息</h5>
                    <table cellpadding="0" cellspacing="0" class="logisticsTable">
                        <tr>
                            <td class="msgTitle">物流单号</td>
                            <td class="msgContent">${order.deliverCode}</td>
                        </tr>
                        <tr>
                            <td class="msgTitle">物流跟踪</td>
                            <td class="msgContent">
                                 <ul class="logisticsMsg">
                                     <c:forEach var="info" items="${routeVOList}">
                                     <li class="clearfix">
                                         <span class="date fleft">${info.time}</span>
                                         <p class="logisticsDetail fleft">${info.context}</p>
                                     </li>
                                     </c:forEach>
                                 </ul>
                            </td>
                        </tr>
                    </table>
                </div>
                </c:if>
                    <!-- 订单信息 -->
                    <div class="orderMsgItem megItem">
                    <h5>订单信息</h5>
                    <table class="orderMsgTable">
                        <tr>
                            <td class="orderMsgTitle">订单编号</td>
                            <td class="orderMsgContent">${order.code}</td>
                            <td class="orderMsgTitle">支付时间</td>
                            <td class="orderMsgContent"><fmt:formatDate value="${order.payDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td class="orderMsgTitle">发货时间</td>
                            <td class="orderMsgContent"><fmt:formatDate value="${order.deliveryDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td class="orderMsgTitle">收货时间</td>
                            <td class="orderMsgContent"><fmt:formatDate value="${order.receiveDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td class="orderMsgTitle">收件地址</td>
                            <td class="orderMsgContent"
                                colspan="3">${order.receiver}，${order.contact}，${order.address}</td>
                        </tr>
                    </table>
                </div>
                    <!-- 商品信息 -->
                    <table class="proMsgTabel">
                    <tr>
                        <th width="430">商品信息</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>金额</th>
                        <th>交易状态</th>
                    </tr>
                    <c:forEach var="orderDetail" items="${order.orderDetails}">
                        <tr>
                            <td class="proDet">
                                <a href="${ctx}/detail/${orderDetail.goodsId}" class="proDetimg fleft">
                                    <input type="hidden" value="${orderDetail.goodsId}" class="goodId"/>
                                    <img src="${ctx}/${orderDetail.goodsImage}_78x86" width="78" height="86">
                                </a>
                                <a href="${ctx}/detail/${orderDetail.goodsId}" class="fleft myCentershaoppingTitle overflow">${orderDetail.goodsName}</a>
                            </td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.sellPrice}" type="currency" pattern="￥.00"/></td>
                            <td>${orderDetail.goodsNum}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.sellPrice*orderDetail.goodsNum}" type="currency" pattern="￥.00"/>
                                </td>
                            <td><c:choose>
                                <c:when test="${order.status == 1}">待付款</c:when>
                                <c:when test="${order.status == 2}">已付款</c:when>
                                <c:when test="${order.status == 3}">待发货</c:when>
                                <c:when test="${order.status == 4}">待收货</c:when>
                                <c:when test="${order.status == 5}">已确认收货</c:when>
                                <c:when test="${order.status == 6}">交易完成</c:when>
                                <c:when test="${order.status == 7}">交易取消</c:when>
                                <c:when test="${order.status == 8}">退换货处理中</c:when>
                                <c:when test="${order.status == 9}">问题/缺货</c:when>
                                <c:otherwise> </c:otherwise>
                            </c:choose></td>
                        </tr>
                    </c:forEach>
                </table>
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

    $(function(){
        $("a.pay").on('click',function () {
            payOrder(this);
        });
    });
    //删除订单
    function deleteOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        layer.confirm("是否删除此订单？", {
            btn: ["确定", "取消"]
        }, function () {
            window.location.href = contextPath +'/personalcenter/deleteOrder/'+orderId;
            layer.msg("删除成功！", {icon: 1});
        });
    }
    //订单支付
    function payOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath +'/order/selectPayment?id='+orderId;
    }
    //取消订单
    function cancleOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        layer.confirm("是否取消此订单？", {
            btn: ["确定", "取消"]
        }, function () {
            window.location.href = contextPath +'/personalcenter/cancleOrder/'+orderId;
            layer.msg("取消成功！", {icon: 1});
        });
    }
    //申请退换货
    function returnOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath +'/personalcenter/returnApply/'+orderId;
    }
    //确认收货
    function receiveOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath +'/personalcenter/receive/'+orderId;
    }
    //交易完成
    function finishOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath +'/personalcenter/finish/'+orderId;
    }
    //订单详情
    function orderDetails(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId=$this.closest("table").find("tr:eq(0)").find(".orderId").val();
        window.location.href = contextPath +'/personalcenter/orderDetails/'+orderId;
    }
    //根据商品ID跳转至商品详情
    function toDetailsByGoodsId(obj) {
        var $this = $(obj);
        var goodsId=$this.parent().find('.goodId').val()
        window.location.href = contextPath +'/detail/'+goodsId;
    }

</script>
   
</body>
</html>
