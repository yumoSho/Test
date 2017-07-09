﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<c:set var="com.glanway.jty.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />

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
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>帮助中心</span><span class="forGt"> &gt;</span><span>常见问题详情</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- //左侧导航 -->
                <%@ include file="/WEB-INF/view/include/pc/leftNav.jspf" %>
                <div class="rightWrap fr userIndex">
                    <span class="comPro">常见问题</span>
                    <div>
                    	${commonPro.content }
                       <!--  <p style="color:#666;font-size:12px;line-height:20px;padding-top:34px;letter-spacing:1px;">
                            
                        </p> -->
                    </div>
                </div>
                </div>
            </div><!-- //main -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %><!-- //foot -->
    </div><!-- //wrap -->
    <script>
     
    </script>
</body>

</html>
