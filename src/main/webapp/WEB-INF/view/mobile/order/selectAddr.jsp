<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>收货地址</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />

</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <div class="head">
            <div class="pageTitle writeWrap samePadding clearfix headTop">
                <a href="javascript:void(0)" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <span>收货地址</span>
            </div>
        </div><!-- //head -->
        <div class="main">
            <form>
                <div class="receipAddress writeWrap padding10">
                    <c:forEach items="${addrList}" var="address">
                        <div class="receipCent <c:if test="${address.id == addressId}">on</c:if>">
                            <input type="hidden" class="id" name="addrId" value="${address.id}">
                            <div class="receipName clearfix">
                                <span class="receipL fleft">${address.consignee}</span>
                                <span class="receipR fright">${address.consigneePhone}</span>
                            </div>
                            <div class="receipText samePadding">
                                <span class="manDefault"><c:if test="${address.isDefault == true}">【默认】</c:if></span>${address.fieldOne}${address.fieldTwo}${address.fieldThree}${address.address}
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="redBut"><a href="${ctx}/orderTemp/addrAdd">添加收货地址</a></div>
            </form>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <!--左侧导航-->
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script type="text/javascript">
        $(".leftNav").hide();
        $(document).ready(function () {
            $(".receipCent").click(function () {
                $(this).addClass("on").siblings().removeClass("on");
                var uName = $(this).find(".receipL").text();
                var uTell = $(this).find(".receipR").text();
                var uAddr = $(this).find(".receipText").text();
                var addrId = $(this).find("input[name=addrId]").val();
                parent.$(".topMessage .fleft span").text(uName);
                parent.$(".topMessage .consigneePhone").text(uTell);
                parent.$(".addrShow .detailAddr span").text(uAddr);
                parent.$(".wrap").show().siblings(".addrIframe").hide();
                parent.$("#addressId").val(addrId).trigger("change");
            });
            $(".head .pageTitle .back").click(function () {
                var checkedAddr = $("div.receipCent.on");
                var uName = checkedAddr.find(".receipL").text();
                var uTell = checkedAddr.find(".receipR").text();
                var uAddr = checkedAddr.find(".receipText").text();
                var addrId = checkedAddr.find("input[name=addrId]").val();
                if(addrId){
                    parent.$(".topMessage .fleft span").text(uName);
                    parent.$(".topMessage .consigneePhone").text(uTell);
                    parent.$(".addrShow .detailAddr span").text(uAddr);
                    parent.$("#addressId").val(addrId);
                }
                    parent.$(".wrap").show().siblings(".addrIframe").hide();
                parent.$('a.selectAdd').show();
            });
        });
    </script>
</body>
</html>
