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
    <title>分享链接</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/product.css" rel="stylesheet" />
<%--
    <script type="text/javascript" src="${ctx }/js/lib/ueditor/third-party/zeroclipboard/ZeroClipboard.js"></script>
--%>
    <script type="text/javascript" src="${ctx }/js/pc/zero/ZeroClipboard.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-1.7.1.min.js"></script>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!--筛选页-->
    <div id="show2" style="display:block;">
        <div class="wrap">
            <div class="head">
                <div class="pageTitle headTop samePadding clearfix">
                    <a href="javascript:history.go(-1);" class="back back1 fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                    <span>分享链接</span>
                </div>
            </div><!-- //head -->
            <div class="main writeWrap">
                <div class="shareLinkContent">
                    <c:if test="${not empty sessionScope.member}">
                        <a id="m_rightFloat_a" href="${shareUrl}" class="jd on">${shareUrl}</a>
                        <p  style="display: block;">请长按以上链接，选择复制</p>
                        <p>在分享网站链接后，通过此链接注册的好友，购买指定类型的商品后，您将会获得一定比例的余额返现。</p>
                        <%--<input type="button" class="copy" id="m_clip_button" data-clipboard-target="m_rightFloat_a" value="复制"/>--%>
                    </c:if>
                    <c:if test="${empty sessionScope.member}">
                        <p id="d_clip_p_content" style="display: block;font-size: 20px;color: #19aa4b;margin-top: 50%">登录后才能拥有专属分享链接，请先登录</p>
                        <a class="copy" href="${ctx}/login">登录</a>
                    </c:if>
                </div>
            </div><!-- //main -->
        </div><!-- //wrap -->
    </div>

</body>

<script>
   /* var clip = new ZeroClipboard( document.getElementById("m_clip_button"), {
        moviePath: "${ctx }/js/lib/ueditor/third-party/zeroclipboard/ZeroClipboard.swf"
    } );
    // 复制内容到剪贴板成功后的操作
    clip.on( 'complete', function(client, args) {
        alert("复制成功，复制内容为："+ args.text);
    } );*/
   /* var clip = new ZeroClipboard( document.getElementById("m_clip_button"), {
       // moviePath: "${ctx }/js/zero/ZeroClipboard.swf"
        moviePath: "${ctx }/js/pc/zero/ZeroClipboard.swf"
    } );


    // 复制内容到剪贴板成功后的操作
    clip.on( 'complete', function(client, args) {
        alert("复制成功，复制内容为："+ args.text);
    } );*/

</script>
</html>
