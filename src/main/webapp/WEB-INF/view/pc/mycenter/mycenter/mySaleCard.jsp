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
    <script src="${ctx}/js/pc/modernizr-2.6.2.min.js"></script>
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
     <style>
        .tabBtn a{
            height: 30px;
            text-align: center;
            line-height: 30px;
            display: block;
            float: left;
            border: 1px solid #ccc;
            color: #666;
            width: 198px;
            border-left: none;
        }
        .tabBtn a.on, .tabBtn a:hover {
            background: #19aa4b;
            color: #fff;
            border: 1px solid #19aa4b;
            cursor: pointer;
            border-left: none;
        }
        .saleCard .tabBtn a {
            width: 33.2%;
        }
    </style>
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
                    <div class="sameTitle color666">我的优惠券</div>
                    <!-- 空优惠券提示 -->
                    <!--<div class="noCrad">
                        您暂时还没有优惠券！
                    </div>-->
                    <div class="saleCard">
                        <div class="tabBtn clearfix">
                         <!--  <p>有效期内（4）</p>
                            <p>已过期（4）</p>
                            <p>使用记录</p>  -->
                            <a href="${ctx}/person-center/coupon?type=0" <c:if test="${type eq 0}">class="on"</c:if>>有效期内（${notUsed}）</a>
                            <a href="${ctx}/person-center/coupon?type=1" <c:if test="${type eq 1}">class="on"</c:if>>已过期（${overdue}）</a>
                            <a href="${ctx}/person-center/coupon?type=2" <c:if test="${type eq 2}">class="on"</c:if>>使用记录</a>
                        </div>
                        <div class="saleCartList">
                        	<div class="saleCardBox">
                        	 <c:if test="${fn:length(coupons.data) > 0}">
                                <table class="userPublicTable">
                                    <tr>
                                        <th width="17%">优惠券编码</th>
                                        <th width="20%">优惠券名称</th>
                                        <th width="10%">优惠方案</th>
                                        <th width="19%">规则</th>
                                        <th width="17%">起始日期</th>
                                        <th width="17%">到期日期</th>
                                    </tr>
                                    <c:forEach items="${coupons.data}" var="coupon">
			                            <tr>
			                                <td>${coupon.code}</td>
			                                <td class="name">${coupon.couponName}</td>
			                                <td><span>${coupon.discount}</span>元</td>
			                                <td>满${coupon.minPrice}减${coupon.discount}</td>
			                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.beginDate}" type="both"/></td>
			                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.endDate}" type="both"/></td>
			                            </tr>
		                            </c:forEach>
                                    <!-- <tr>
                                        <td>PDFA8B5F7S92B85</td>
                                        <td class="name">新注册会员优惠券</td>
                                        <td><span>20</span>元</td>
                                        <td>满100减20</td>
                                        <td>2015/9/11  14:20</td>
                                        <td>2015/9/11  14:20</td>
                                    </tr>
                                    <tr>
                                        <td>PDFA8B5F7S92B85</td>
                                        <td class="name">国庆节优惠券</td>
                                        <td><span>20</span>元</td>
                                        <td>满100减20</td>
                                        <td>2015/9/11  14:20</td>
                                        <td>2015/9/11  14:20</td>
                                    </tr>
                                    <tr>
                                        <td>PDFA8B5F7S92B85</td>
                                        <td class="name">等级会员特别优惠券</td>
                                        <td><span>20</span>元</td>
                                        <td>满100减20</td>
                                        <td>2015/9/11  14:20</td>
                                        <td>2015/9/11  14:20</td>
                                    </tr> -->
                                </table>
                                
                        	    <!-- 分页 -->
                        	    <form id="pagination-form" class="pagination-form">
	                				<m:pagination totalPages="${coupons.totalPages}" pageParam="page" skip="false"/>
	                			</form>
                        	    </c:if>
                        	    <!-- 空优惠券提示 -->
                        <c:if test="${fn:length(coupons.data) eq 0}">
                            <div class="noCrad">
                              	 您暂时还没有优惠券！
                            </div>
                        </c:if>
                        	</div>
                        	
                        	
                        </div>
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
