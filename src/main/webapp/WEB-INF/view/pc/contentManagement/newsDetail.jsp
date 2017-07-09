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
    <title>资讯列表-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx }/css/pc/news.css" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %><!-- //head -->
        <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>新闻资讯</span>
                </div>
            </div>
            <!--详情标题部分-->
            <div class="detailTitleWrap">
                <h1>${news.title }</h1>
                <div class="detailDate">发表时间：<span><fmt:formatDate value="${news.createdDate }" type="date"/></span></div>
            </div>
            <!--详情内容部分-->
            <div class="detailContent" style="line-height:24px;color:#333;">
                ${news.content }
            </div>

        </div><!-- //main -->
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
