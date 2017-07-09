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
    <title>关于商城-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="${ctx}/css/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/base.css">
    <link href="${ctx}/css/index.css" rel="stylesheet" />
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
        <div class="main">
            
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>关于商城</span>
                </div>
            </div>
            <div class="contactWrap sameWidth clearfix">
               <div class="about" style="width: 1200px; overflow: hidden; margin: 28px auto; font-size: 15px; color: #666666; line-height: 25px;margin-bottom:51px"">
                <p>睢宁农商行电子商务平台于2016年XX月XX日正式营业。平台秉承"名商、名品、名店"的定位，以特色性、便利性、专业性、安全性为原则，有机整合客户与商户，有机连接支付与融资，有机统一物流、资金流与信息流，努力打造客户喜爱的消费和采购平台、客户倚重的销售和推广平台、支付融资一体化的金融服务平台、"三流合一"的数据管理平台。</p></br>
                <p>目前，睢宁商城已汇集数码家电、贵金属投资、金融产品、服装鞋帽、食品饮料、珠宝礼品等商品分类。作为银行系电商，睢宁行商城既把握电子商务发展规律和趋势，致力于提升客户体验。</p></br>
                <p>未来，睢宁农商行商城将不断优化用户体验，丰富商品的品类和数量，深化电商与金融的融合创新，为用户打造放心、便捷、质优、廉价的消费平台。</p>
                <img src="${ctx}/images/img_01.jpg" alt="aboutImg" style="margin-top:45px; margin-bottom:51px" />
            </div>
               
               
            </div>
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/rightFloat.jspf"%>
    </div>
    <!-- //wrap -->
    <script src="${ctx}/js/jquery.slides.min.js"></script>
    <script src="${ctx}/js/common.js"></script>
    <script src="${ctx}/js/Tab.js"></script>
    
</body>
</html>
