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
    <title>公告列表-${title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="${ctx}/css/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/base.css">
    <link rel="stylesheet" href="${ctx}/css/contentManage.css"  />
    <script type="text/javascript" src="${ctx}/js/jquery-1.7.1.min.js"></script>
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/head.jspf"%>
        <!-- //head -->
        <div class="main sameWidth">
            
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>公告列表</span>
                </div>
            </div>
            <div class="contentWrap sameWidth clearfix">
                <!-- 内页左侧导航 -->
                <%@include file="/WEB-INF/view/include/leftNav.jspf"%>
                <div class="innerRightContent fright">
                    <!-- 内页公共标题 -->
                    <div class="innerTitle clearfix">
                        <span class="title">公告</span>
                    </div>
                <!--     <div class="annoceType">
                        <a href="javascript:void(0);">全部</a>
                        <a href="javascript:void(0);" class="on">系统公告</a>
                    </div> -->
                    <table class="annoceTable">
                        <tr>
                           
                            <th>标题</th>
                            <th>浏览数</th>
                            <th>发布时间</th>
                        </tr>
                        <c:forEach items="${announces.data}" var="announce">
                        <tr>
                            <td><a href="${ctx}/contentManagement/announceDetail?id=${announce.id}">${announce.title }</a></td>
                            <td>
                                <img src="${ctx}/images/eyes.png" width="17" height="12" />
                                <span>${announce.hitNum }</span></td>
                            <td><fmt:formatDate value="${announce.createdDate}" type="both"/></td>
                        </tr>
                       </c:forEach>
                    </table>
                    <c:if test="${not empty announces.data}">
	                    <div class="activePage">
	                    <form id="pagination-form">
	                        <m:pagination totalPages="${announces.totalPages}" pageParam="page" skip="false"/>
	                    </form>
	                	</div>
                	</c:if>
                </div>
            </div>
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/rightFloat.jspf"%>
    </div>
   </div>
    <!-- //wrap -->
    <script src="${ctx}/js/jquery.slides.min.js"></script>
    <script src="${ctx}/js/common.js"></script>
    <script src="${ctx}/js/Tab.js"></script>
    
</body>
</html>
