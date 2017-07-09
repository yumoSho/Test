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
                    <div class="sameTitle color666">充值记录</div>

                    <div class="rechangeHistoryContent">
                        <table class="rechangeHistoryTable">
                            <tr>
                                <th>充值金额</th>
                                <th>赠送金额</th>
                                <th>充值时间</th>
                            </tr>
                            <c:forEach items="${balancePaymentDetails.data }" var="balancePaymentDetail">
                            <tr>
                                <td>￥${balancePaymentDetail.amount }</td>
                                <td>￥<c:if test="${balancePaymentDetail.giftAmount==null }">0.00</c:if><c:if test="${balancePaymentDetail.giftAmount!=null }">${balancePaymentDetail.giftAmount }</c:if></td>
                                <td><fmt:formatDate value="${balancePaymentDetail.startTime}" type="both"/></td>
                            </tr>
                            </c:forEach>
                           <!--  <tr>
                                <td>￥20000</td>
                                <td>￥200</td>
                                <td>2015/9/11 14:20</td>
                            </tr>
                            <tr>
                                <td>￥1000</td>
                                <td>￥10</td>
                                <td>2015/9/11 14:20</td>
                            </tr>
                            <tr>
                                <td>￥20000</td>
                                <td>￥200</td>
                                <td>2015/9/11 14:20</td>
                            </tr>
                            <tr>
                                <td>￥1000</td>
                                <td>￥10</td>
                                <td>2015/9/11 14:20</td>
                            </tr> -->
                        </table>
                        <!-- 分页 -->
                        <c:if test="${not empty balancePaymentDetails.data}">
		                    <form id="pagination-form" class="pagination-form">
		                		<m:pagination totalPages="${balancePaymentDetails.totalPages}" pageParam="page" skip="false"/>
		            		</form>
            			</c:if>    

                    </div>
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
        $(".saleCard .tabBtn p").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
        });
    })
</script>
   
</body>
</html>
