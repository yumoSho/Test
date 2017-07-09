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
    <script type="text/javascript" src="${ctx }/js/lib/ueditor/third-party/zeroclipboard/ZeroClipboard.js"></script>
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
              
                <div class="rightWrap fr userIndex">
                    <div class="sameTitle noBorder">个人信息</div>
                    <div class="mycenterDiv clearfix">
                        <div class="fl">
                        	<a href="${ctx}/person-center/info" class="headImg fl">
                                <c:if test="${empty member.headImage}">
                                    <img src="${ctx}/images/pc/headPortraitImg.png" width="108" height="108">
                                </c:if>
                                <c:if test="${not empty member.headImage}">
                                    <img src="${ctx}/${member.headImage}_108x108.jpg" width="108" height="108" />
                                </c:if>

                            </a>
                        	<ul class="mycenterDivUl">
                        	    <li>${currentTimeInterval }，<span>${member.memberName }</span>！</li>
                        	    <li>会员编号：<span>${member.memberCode }</span></li>
                        	    <li>会员等级：<span>${member.gradeName }</span></li>
                        	</ul>
                            <ul class="mycenterDivUl userIndexInfo">
                            
                                <li>我的余额：<span>&yen;<c:if test="${not empty member.balance }">${member.balance }</c:if><c:if test="${empty member.balance }">0.00</c:if></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx }/person-center/recharge">充值</a></li>
                                <li>我的充值记录：&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx}/person-center/myRechargeRecord">查看</a></li>
                            </ul>
                        </div>
                        <ul class="listUl fr">
                            <li>
                                <img src="${ctx}/images/pc/listUl01.png" width="33" height="31" />
                                <a href="${ctx}/personalcenter/orderList?status=1&onId=dfk">待付款（<span>${orderCount.COUNT1}</span>）</a>
                            </li>
                            <li>
                                <img src="${ctx}/images/pc/listUl02.png" width="33" height="31" />
                                <a href="${ctx}/personalcenter/orderList?status=3&onId=dfh">待发货（<span>${orderCount.COUNT3}</span>）</a>
                            </li>
                            <li>
                                <img src="${ctx}/images/pc/listUl03.png" width="33" height="31" />
                                <a href="${ctx}/personalcenter/orderList?status=4&onId=dsh">待收货（<span>${orderCount.COUNT2}</span>）</a>
                            </li>
                            <li>
                                <img src="${ctx}/images/pc/listUl04.png" width="33" height="31" />
                                <a href="${ctx}/person-center/comment">待评价（<span>${orderCount.COUNT4}</span>）</a>
                            </li>
                        </ul>
                    </div>
                    <div class="sameTitle">我的订单<a href="${ctx}/personalcenter/orderList" class="fr">查看全部订单 ></a></div>
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
                    <c:forEach items="${orderList}" var="order">
                            <table class="centerTable" border="1">
                                <tr class="tableTitle">
                                    <input type="hidden" class="orderId" value="${order.id}">
                                    <td width="430"><span><fmt:formatDate value="${order.createdDate}" pattern="yyyy.MM.dd"/></span> （订单编号：<span>${order.code}</span>）</td>
                                    <td width="85"></td>
                                    <td width="71"></td>
                                    <td width="122"></td>
                                    <td width="139"></td>
                                    <td width="150"><%-- <a href="${ctx}/store/${order.storeId}">店铺：${order.storeName}</a> --%></td>
                                </tr>
                                <c:forEach items="${order.orderDetails}" var="detail" varStatus="index">
                                    <tr>
                                        <td width="430">
                                            <a href="${ctx}/detail/${order.id}" class="myCentershaoppingImg">
                                                <img src="${ctx}/${detail.goodsImage}_78x86" width="78" height="86" />
                                            </a>
                                            <a href="${ctx}/detail/${order.id}" class="myCentershaoppingTitle overflow">${detail.goodsName}</a>
                                        </td>
                                        <td width="85">￥<span>${detail.goodsPrice}</span></td>
                                        <td width="71"><span>${detail.goodsNum}</span></td>
                                        <c:if test="${index.first}">
                                            <td width="122" rowspan="${fn:length(order.orderDetails)}">￥<span>${order.price}</span></td>
                                            <td width="139" class="lineLtem" rowspan="${fn:length(order.orderDetails)}">
                                                <c:choose>
                                                    <c:when test="${order.status == 1}"><a href="javascript:void(0)" class="on">待付款</a></c:when>
                                                    <c:when test="${order.status == 2}"><a href="javascript:void(0)" class="on">已付款</a></c:when>
                                                    <c:when test="${order.status == 3}"><a href="javascript:void(0)" class="on">待发货</a></c:when>
                                                    <c:when test="${order.status == 4}"><a href="javascript:void(0)" class="on">待收货</a></c:when>
                                                    <c:when test="${order.status == 5}"><a href="javascript:void(0)" class="on">已确认收货</a></c:when>
                                                    <c:when test="${order.status == 6}"><a href="javascript:void(0)" class="on">交易完成</a></c:when>
                                                    <c:when test="${order.status == 7}"><a href="javascript:void(0)" class="on">交易取消</a></c:when>
                                                    <c:when test="${order.status == 8}"><a href="javascript:void(0)" class="on">退换货处理中</a></c:when>
                                                    <c:when test="${order.status == 9}"><a href="javascript:void(0)" class="on">缺货</a>问题/</c:when>
                                                    <c:otherwise> </c:otherwise>
                                                </c:choose>
                                                <a href="${ctx}/personalcenter/orderDetails/${order.id}">订单详情</a>
                                            </td>
                                            <td width="150" rowspan="${fn:length(order.orderDetails)}">
                                                <c:choose>
                                                    <c:when test="${order.status == 1}">
                                                        <a href="javascript:void(0);"  target="_blank" class="operatebtn pay receiving">立即付款</a>
                                                        <a href="javascript:;" onclick="cancleOrder(this)" class="receivingBottom">取消订单</a>
                                                    </c:when>
                                                    <c:when test="${order.status == 2}"></c:when>
                                                    <c:when test="${order.status == 3}"></c:when>
                                                    <c:when test="${order.status == 4}">
                                                        <a href="javascript:;" onclick="receiveOrder(this)" class="receiving">确认收货</a>
                                                    </c:when>
                                                    <c:when test="${order.status == 5}">
                                                        <a href="javascript:;" onclick="finishOrder(this)" class="receiving">交易完成</a>
                                                    </c:when>
                                                    <c:when test="${order.status == 6}">
                                                        <a href="javascript:;" onclick="deleteOrder(this)" class="receivingBottom deleBtn">删除</a>
                                                    </c:when>
                                                    <c:otherwise> </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>

                            </table>
                        </c:forEach>
                   <!--  <table class="centerTable" border="1">
                        <tr class="tableTitle">
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
                                    <img src="../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                </a>
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">西兰花，西餐料理好方便；西兰花，西餐料理好方便；西兰花，西餐...</a>
                            </td>
                            <td width="85">￥<span>45.00</span></td>
                            <td width="71"><span>2</span></td>
                            <td width="122" rowspan="3">￥<span>270.00</span></td>
                            <td width="139" class="lineLtem" rowspan="3">
                                <a href="userDeal/myOrder.html">订单详情</a>
                            </td>
                            <td width="150" rowspan="3">
                                <a href="userAccount/payWay.html" class="receiving">立即付款</a>
                                <a href="#" class="receivingBottom">取消订单</a>
                            </td>
                        </tr>
                        <tr>
                            <td width="430">
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                    <img src="../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                </a>
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">西兰花，西餐料理好方便；西兰花，西餐料理好方便；西兰花，西餐...</a>
                            </td>
                            <td width="85">￥<span>45.00</span></td>
                            <td width="71"><span>2</span></td>
                        </tr>
                        <tr>
                            <td width="430">
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingImg">
                                    <img src="../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                </a>
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">西兰花，西餐料理好方便；西兰花，西餐料理好方便；西兰花，西餐...</a>
                            </td>
                            <td width="85">￥<span>45.00</span></td>
                            <td width="71"><span>2</span></td>
                        </tr>
                    </table>
                    <table class="centerTable" border="1">
                        <tr class="tableTitle">
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
                                    <img src="../../images/myCentershaoppingImg.jpg" width="78" height="86" />
                                </a>
                                <a href="../../html/product/productDetail.html" class="myCentershaoppingTitle overflow">西兰花，西餐料理好方便；西兰花，西餐料理好方便；西兰花，西餐...</a>
                            </td>
                            <td width="85">￥<span>45.00</span></td>
                            <td width="71"><span>2</span></td>
                            <td width="122">￥<span>90.00</span></td>
                            <td width="139" class="lineLtem" rowspan="3">
                                <a href="#" class="on">待付款</a>
                                <a href="userDeal/orderDetail.html">订单详情</a>
                            </td>
                            <td width="150">
                                <a href="userDeal/orderDetail.html" class="receiving">确认收货</a>
                            </td>
                        </tr>
                    </table> -->
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
