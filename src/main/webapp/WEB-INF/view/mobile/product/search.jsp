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
    <title>搜索</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/product.css" rel="stylesheet" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="writeWrap wrap">
        <div class="head">
            <div class="headTop searchTop samePadding clearfix">
                <a href="javascript:history.go(-1);" class="searchBack fleft"><img src="${ctx}/images/mobile/icon25.png" /></a>
                <div class="searchPart fleft clearfix">
                    <div class="selectPart fleft">
                        <a class="selectModel"  data-type="0">商品</a>
                        <div class="selectOptions">
                            <a href="javascript:void(0);"  data-type="1">文章</a>
                            <img src="${ctx}/images/mobile/icon27.png" class="bgTop" />
                        </div>
                    </div>
                    <input type="text" placeholder="请输入关键字进行搜索" class="searchText fleft" />
                </div>
                <a href="javascript:void(0);" class="headSearchBtn fright">搜索</a>
            </div>
        </div><!-- //head -->
        <c:if test="${ show != 0}">
            <div class="main ">
                <div class="searchNull"><img src="${ctx}/images/mobile/icon28.png" />没有找到相关的商品！</div>
            </div><!-- //main -->
        </c:if>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <script>
        $(function () {
            //倒瓶子
            $(".searchPart .selectPart > a").click(function () {
                $(this).siblings(".selectOptions").toggle();
            });
            $(".selectPart .selectOptions a").click(function () {
                var thisText = $(this).text();
                var type = $(this).data("type");

                var parentText = $(".selectModel").text();
                var changType = $(".selectModel").data("type");

                $(".selectPart > a").text(thisText);
                $(".selectPart > a").data("type",type);

                $(this).data("type",changType);
                $(this).text(parentText);
                $(this).parents(".selectOptions").hide();;
            });

            $(".headSearchBtn").click(function () {
                var kw = $(".searchText").val();
                var type = $(".selectModel").data("type");
                if(type=="0"){
                    window.location.href =  contextPath+"/mobile/search?kw=" + kw;
                }else{
                    window.location.href =  contextPath+"/mobile/contentManagement/newsList?newskeyword=" + kw;
                }
            });
            $(".headSearchBtn ").keydown(function (event) {
                if (event.keyCode == 13) {
                    var kw = $(".searchText").val();
                    var type = $(".selectModel").data("type");
                    if(type=="0"){
                        window.location.href = contextPath+"/mobile/search?kw=" + kw;
                    }else{
                        window.location.href = contextPath+"/mobile/contentManagement/newsList?newskeyword=" + kw;
                    }
                }
            });
        });
    </script>
</body>
</html>
