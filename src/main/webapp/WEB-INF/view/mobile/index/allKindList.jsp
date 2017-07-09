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
    <title>全部分类选择</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class=" wrap writeWrap">
        <div class="head">
            <div class="headTop samePadding clearfix">
                <a href="${ctx}/mobile/" class="menuBtn fleft">
                    <img src="${ctx}/images/mobile/icon14.png" />
                </a>
                <div class="headSearch fleft">
                    <a href="${ctx}/mobile/input"><img src="${ctx}/images/mobile/icon2.png" class="searchBtn" /></a>
                    <input type="text" placeholder="请输入关键字进行搜索" class="searchInput" />
                </div>
                <a href="${ctx}/cart" class="headCart fright"><img src="${ctx}/images/mobile/icon3.png" /></a>
            </div>
        </div><!-- //head -->
        <div class="main ">
            <ul class="kinds">
                <c:forEach items="${categories}" var="c" varStatus="status">
                <li  id="c_${status.index}">
                    <a href="javascript:void(0);"  onclick="noOew(${status.index})">${c.name}</a>
                    <ul class="subKinds" >
                        <c:forEach items="${c.children}" var="s">
                        <li>
                            <a href="${ctx}/mobile/search?cat=${s.id}">${s.name}</a>
                            <div class="thirdKinds">
                                <c:forEach items="${s.children}" var="child">
                                <a href="${ctx}/mobile/search?cat=${child.id}">
                                    <img src="${ctx}/${child.picture}" />
                                    <p>${child.name}</p>
                                </a>
                                </c:forEach>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
                </li>
                </c:forEach>
            </ul>
        </div><!-- //main -->
    </div><!-- //wrap -->
    <script>
        $(function () {

            var liNum = $(".kinds >li").size() * $(".kinds >li").height();
            $(".subKinds").css("height", liNum/46+"rem");

            $("#c_0 a").css("background-color","#fff");
            $("#c_0 .subKinds").css("display","block");

        });
        function noOew(Object){
            if(Object != 0){
                $("#c_0 a").css("background-color","#f0f0f0");
                $("#c_0 .subKinds").css("display","none");
            }else{
                $("#c_0 a").css("background-color","#fff");
                $("#c_0 .subKinds").css("display","block");
            }
        }
    </script>
</body>
</html>
