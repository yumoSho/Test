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
    <title>常见问题-${title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx }/css/pc/userCenter.css" rel="stylesheet" />
    <link href="${ctx }/css/pc/helpCenter.css" rel="stylesheet" />
    <link href="${ctx }/css/pc/flashSale.css" rel="stylesheet" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %><!-- //head -->
        <div class="main samewidth">
             <!-- //面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>帮助中心</span><span class="forGt"> &gt;</span><span>常见问题</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- //左侧导航 -->
                <%@ include file="/WEB-INF/view/include/pc/leftNav.jspf" %>
                <div class="rightWrap fr userIndex">
                    <span class="comPro">常见问题</span>
                    <ul class="comProList">
                    	<c:forEach items="${commonPros.data }" var="commonPro">
                    		<li><a href="${ctx }/contentManagement/commonProDetail?id=${commonPro.id }">${commonPro.title }</a></li>
                    	</c:forEach>
                   <!--        <li><a href="helpCenter.html">如何退换货？</a></li>
                      <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li>
                        <li><a href="helpCenter.html">如何退换货？</a></li> -->
                    </ul>
                    <!-- // 分页 -->
                    <form id="pagination-form" class="pagination-form">
	                	<m:pagination totalPages="${commonPros.totalPages}" pageParam="page" skip="false"/>
	            	</form>
                </div>
                </div>
            </div><!-- //main -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
        <!--//协议弹出层-->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %><!-- //foot -->
    </div><!-- //wrap -->
    <script>
        $(function () {
            $(".tabBtn").find("a").click(function () {
                var _this = $(this),
                    newLists = $(".newList");
                idx = _this.index();
                _this.addClass("on").siblings().removeClass("on");
                //$(newLists[idx]).removeClass("hide").siblings().addClass("hide");
            });
        });
    </script>
</body>

</html>
