﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>活动列表-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/active.css" rel="stylesheet" />
    <script src="${ctx}/js/pc/modernizr-2.6.2.min.js"></script>
    <style>
        .proNullNotice {
            width: 410px;
            margin: 30px auto;
            background: url(/images/pc/searchResultImg.png) left center no-repeat;
            font-size: 18px;
            font-weight: bold;
            line-height: 30px;
            padding: 100px 0 100px 100px;
        }
    </style>
    <c:set var="nav" value="2"/>
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
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>活动专区</span>
                </div>
            </div>
            <!--活动列表-->
            <c:if test="${fn:length(activities.data)>0}">
            <div class="activeList clearfix">
                <c:forEach items="${activities.data}" var="a">
                <div class="activeItem fl">
                    <img src="${ctx}/${a.activityImgPath}" width="586" height="245" />
                    <a href="${ctx}/activity/detail/${a.id}" title="${a.activityName}">入场疯抢</a>
                </div>
                </c:forEach>
            </div>
            <!-- 分页 -->
            <form id="pagination-form" class="pagination-form">
                <m:pagination totalPages="${activities.totalPages}" pageParam="page" skip="false"/>
            </form>
            </c:if>
            <c:if test="${fn:length(activities.data) eq 0}">
                <div class="proNullNotice">
                    没有找到相关的商品！<br />建议您按照以下提示调整关键字
                    <p>
                        1、您输入的关键字是否有错别字，或者更换区域试试；<br />
                        2、如果您使用自然语言进行搜索，请提炼出关键字再搜索试试！
                    </p>
                </div>
            </c:if>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script>
        $(function () {

        });
    </script>
</body>
</html>
