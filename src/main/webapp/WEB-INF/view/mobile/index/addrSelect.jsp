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
    <title>地址选择</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <style>
        .addrList a.on{color: #19aa4b}
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class=" wrap">
        <div class="head">
            <div class="pageTitle samePadding headTop clearfix">
                <a href="javascript:void(0)" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <span>区域选择</span>
            </div>
        </div><!-- //head -->
        <div class="main writeWrap">
           <div class="addrList">
               <a href="javascript:void(0);" data-id="0" data-code=""  <c:if test="${ empty sessionScope.provinceId or '0' eq sessionScope.provinceId }">class="on"</c:if>>不限</a>
               <c:forEach items="${allProvince}" var="p">
               <a href="javascript:void(0);" <c:if test="${p.id eq sessionScope.provinceId}">class="on"</c:if> data-id="${p.id}" data-code="${p.provinceCode}">${p.remark}</a>
               </c:forEach>
           </div>
        </div><!-- //main -->
    </div><!-- //wrap -->
    <script>
        $(function () {
            $(".addrList a").click(function () {
                var thisText = $(this).text();
                parent.$(".wrap").show(); 

                if($(this).data("id")){
                    parent.$(".menuBtn").text(thisText);
                }
                parent.window.document.getElementById("addrIframe").style.display = "none";
            });
            if($(".addrList a.on").data("id")){
                parent.$(".menuBtn").text($(".addrList a.on").text());
            }
            $(".back").click(function () {
                parent.$(".wrap").show();
                parent.window.document.getElementById("addrIframe").style.display = "none";
            });
        });
    </script>
</body>
</html>
