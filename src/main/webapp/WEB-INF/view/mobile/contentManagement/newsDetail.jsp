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
    <title>资讯详情</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/mobile/aboutus.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main">
        <div class="newsLi samePadding">
           <%-- <a href="#">全部</a>
            <a href="#" class="on">直播农村</a>
            <a href="#">菜谱专区</a>
            <a href="#">新闻</a>
            <a href="#">银行专区</a>--%>
            <a href="${ctx }/mobile/contentManagement/newsList" <c:if test="${_newsType == null }">class="on"</c:if>>全部</a>
               <c:forEach items="${newsTypes }" var="newsType">
                   <a href="${ctx }/mobile/contentManagement/newsList?type=${newsType.id }" <c:if test="${_newsType == newsType.id }">class="on"</c:if>>${newsType.name }</a>
               </c:forEach>

        </div>
        <div class="newsContent samePadding">
            <p class="contentTitle overflow">${news.title }</p>
            <div class="infoTime">发表时间:<fmt:formatDate value="${news.createdDate }" type="date"/></div>
            <div class="listContent" style="font-size: 0.24rem;line-height: 0.48rem;letter-spacing: 0.01rem;">
                ${news.content }
            </div>
        </div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
</body>
</html>
