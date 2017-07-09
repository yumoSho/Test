<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>填写订单</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width"
          name="viewport">
    <meta name="format-detection" content="telephone=no"/>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/cart.css" rel="stylesheet"/>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <%--head--%>
    <c:set value="填写订单" var="common"/>
        <c:set value="true" var="notShowTop"/>
        <div class="head">
            <c:if test="${!notShowTop}">
                <div class="headTop samePadding clearfix">
                    <a href="javascript:void(0);" class="menuBtn fleft">
                        <img src="${ctx}/images/mobile/icon1.png" />
                        <!--上海市-->
                    </a>
                    <div class="headSearch fleft">
                        <a href="${ctx}/mobile/input"><img src="${ctx}/images/mobile/icon2.png" class="searchBtn" /></a>
                        <input type="text" placeholder="请输入关键字进行搜索" class="searchInput" />
                    </div>
                    <a href="${ctx}/cart" class="headCart fright"><img src="${ctx}/images/mobile/icon3.png" /></a>
                </div>
            </c:if>

            <%--购物车--%>
            <c:if test="${isCart}">
                <div class="pageTitle writeWrap samePadding clearfix headTop">
                    <a href="${ctx}/cart" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                    <span>购物车(<span class="cartNum" id="cartNum">0</span>)</span>
                    <a href="javascript:void(0);" class="editCart fright">编辑</a>
                </div>
            </c:if>
            <c:if test="${not empty common}">
                <div class="pageTitle writeWrap samePadding clearfix headTop">
                    <a href="
                <c:choose>
                    <c:when test="${not empty href}">${ctx}/${href}</c:when>
                    <c:otherwise>${ctx}/cart</c:otherwise>
                </c:choose>

            " class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                    <span>${common}</span>
                </div>
            </c:if>
        </div><!-- //head -->
    <div class="main" <%--style="height: 9.6rem;"--%>>
        <div class="defaultShow">
            <input type="hidden" name="addressId" id="addressId" value="${defaultAddressId}">
            <input type="hidden" name="couponId" id="couponId">
            <input type="hidden" name="orderFrom" value="${orderFrom}" id="orderFrom">
            <c:choose>
                <c:when test="${fn:length(addressList) > 0 }">
                    <c:forEach items="${addressList}" var="address" end="0" varStatus="index">
                        <!--地址信息-->
                        <a href="javascript:void(0);" class="addrShow" style="display: block">
                            <p class="topMessage clearfix">
                                <span class="fleft">收货人：<span>${address.consignee}</span></span>
                                <span class="fright consigneePhone">${address.consigneePhone}</span>
                            </p>
                            <p class="detailAddr">
                                收货地址：<span> ${address.fieldOne} ${address.fieldTwo} ${address.fieldThree}
                                    ${address.address}</span>
                            </p>
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!--为选择收货地址提示-->
                    <a href="javascript:void(0)" class="selectAdd writeWrap clearfix">
                        <span class="fleft">请选择收货地址</span>
                        <img src="${ctx}/images/mobile/icon37.png" class="fright"/>
                    </a>
                </c:otherwise>
            </c:choose>




            <!--订单产品信息-->
            <div class="orderProMessage">
                <div class="storePart">
                    <div class="orderPart ">
                        <div class="orderPro">
                        <c:forEach items="${otv.goodsList}" var="tmp">
                            <div class="proPart writeWrap clearfix">
                                <input type="hidden" name="id" value="${tmp.id}">
                                <a href="${ctx}/detail/${tmp.goodsId}" class="proImg fleft"><img src="${ctx}/${tmp.goodsImg}_92x101"/></a>
                                <div class="rightProDes fright">
                                    <a href="${ctx}/detail/${tmp.goodsId}" class="overflow">${tmp.goodsName}</a>
                                    <div class="numbers">数量：<span>${tmp.buyCount}</span></div>
                                    <div class="proPrice">价格：<span class="proAddNomey">￥<span>${tmp.totalPrice}</span></span></div>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="storeSale writeWrap">
                            <div class="saleType font24 clearfix">
                                <span class="fleft" >优惠券</span>
                                <span class="fright" id="couponName">不使用优惠券</span>
                            </div>
                            <div class="logisticsTime font24 clearfix">
                                <span class="fleft">选择配送时间</span>
                                <span class="fright" id="sendTime_text">全天</span>
                              <%--  <input id="sendTime" type="hidden" name="timeSelect" value="全部">--%>
                              <%--  <c:forEach items="${pssj}" var="sj" varStatus="index" begin="0" end="0">
                                    <span class="fright"> ${sj.remark}</span>
                                </c:forEach>--%>
                            </div>
                            <div class="saleMoney font24 clearfix">
                                <span class="fleft">优惠金额</span>
                                <span class="fright">￥<span id="couponPrice">0.00</span></span>
                            </div>
                            <div class="saleMoney font24 clearfix">
                                <span class="fleft">运费</span>
                                <c:choose>
                                    <c:when test="${!otv.canDelivery}">
                                        <span class="fright totalFreight" style="color:red">不支持该区域配送</span>
                                    </c:when>
                                    <c:when test="${otv.canDelivery && otv.baoYou}">
                                        <span class="fright totalFreight" style="color:red">包邮</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="fright totalFreight">￥${otv.totalFreight}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="remarkTxt">
                                <span class="fleft">备注：</span>
                                <textarea class="fright" id="remark"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <!--订单结算信息-->
                <div class="addorder satement writeWrap" >
                    <input type="hidden" name="canDelivery" id="canDelivery" value="${otv.canDelivery}">
                    <input type="hidden" name="baoYou" id="baoYou" value="${otv.baoYou}">
                    <span class="sateProNum">共<span>${otv.totalNum}</span>件商品</span>
                    <span class="sateMoney">合计：<span id="totalPriceAndTotalFreight">${otv.totalPriceAndTotalFreight}</span></span>
                    <input type="hidden" id="totalPrice" value="${otv.totalPrice}"/>
                    <a href="javascript:void(0)" class="sateSubmit fright submitOrder">提交订单</a>
                </div>
            </div>
            <!--ifream包含-->
        </div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png"/></a>
        <div class="storeSaleWrap">
            <div class="saleInner writeWrap">
                <p class="saleTitle">优惠券</p>
                <ul class="activeList">
                    <li class="on" value="0" cId="-1">不使用优惠券</li>
                    <c:forEach items="${couponList}" var="coupon">
                        <c:if test="${otv.totalPrice >= coupon.minPrice}">
                            <li class="on" value="${coupon.discount}" cId="${coupon.id}">${coupon.couponName}</li>
                        </c:if>
                    </c:forEach>
                </ul>
                <div class="closePop">关闭</div>
            </div>
        </div>
        <!--配送时间显示-->
        <div class="logisticsTimeShow">
            <div class="saleTime writeWrap">
                <p class="saleTitle">配送时间</p>
                <ul class="activeList">
                    <c:forEach items="${pssj}" var="sj" varStatus="index">
                        <li <c:if test="${index.index eq 3}">class="on"</c:if> onclick="selectSendTime(this)" timeArea="${sj.remark}">${sj.remark}</li>
                    </c:forEach>


                </ul>
                <div class="closePop">关闭</div>
            </div>
        </div>

</div>
        </div><!-- //wrap -->
<div class="addrIframe">
    <iframe src="" class="iframeAddr" id="iframeAddr"></iframe>
</div>
<!--左侧导航-->
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script>
    $(function () {
        //绑定收货地址改变事件
        $("#addressId").on('change', function () {
            addressChange()
        });
        //填写订单回填
        $(".selectAdd ,.addrShow").click(function () {
            $(".selectAdd").hide().siblings(".addrShow").css("display", "block");
            var $addrIframe = $(".addrIframe");
            if (!$addrIframe.find('iframe').attr("src")) {
                $addrIframe.find("iframe").attr("src", "${ctx}/orderTemp/selectAddr?addressId=" + $("#addressId").val());
            }
            $addrIframe.show().siblings(".wrap").hide();
        });
        //店铺优惠显示
        $(".storeSale .saleType").click(function () {
            $(".storeSaleWrap").show();
            document.body.style.overflow = "hidden";//禁用
        });
        //弹窗关闭
        $(".saleInner .activeList li").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
            $(".storeSaleWrap").fadeOut();
            document.body.style.overflow = "auto";
        });
        $(".saleInner .closePop").click(function () {
            $(".storeSaleWrap").hide();
            document.body.style.overflow = "auto";
        });
        //优惠券选择
        $(".saleInner .activeList li").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
            $(this).parents("div.storePart").find("span.couponName").text($(this).text());

            var price = Number($(this).val());
            var cId = Number($(this).attr("cId"));
            var totalPrice = $("#totalPrice").val();
            var totalFreight = $('span.totalFreight').text();
            $("#couponName").text($(this).text());
            $("#couponPrice").text(HgUtil.numberFormat(price,2));
            totalFreight = totalFreight.replace("￥","");
            if(isNaN(totalFreight)) {
                totalFreight = 0;
            }
            var totalPriceAndTotalFreight = HgUtil.numberFormat(Number(totalFreight) + Number(totalPrice) - price,2);
            $("#totalPriceAndTotalFreight").text(totalPriceAndTotalFreight);
            $("#couponId").val(cId);

            $(".storeSaleWrap").hide();
            $(".satement").show();
            document.body.style.overflow = "auto";
        });
        //配送时间显示
        $(".logisticsTime").click(function () {
            $(".logisticsTimeShow").show();
            document.body.style.overflow = "hidden";//禁用
        });
        $(".saleTime .activeList li").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
        });
        $(".saleTime .closePop").click(function () {
            $(".logisticsTimeShow").hide();
        });
        //任意点击区域，分享弹窗隐藏
        $(".storeSaleWrap ,.logisticsTimeShow").click(function (e) {
            if (!$(e.target).is(".saleInner") && !$(e.target).parents().is(".saleInner ")) {
                $(".storeSaleWrap ,.logisticsTimeShow").fadeOut();
                document.body.style.overflow = "auto";
            }
        });

     /*   $("textarea").on("focus", function () {
            $(".satement").hide();
        });
        $("textarea").on("blur", function () {
            $(".satement").show();
        })*/

        //提交订单
        $(function () {
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
        });
    });
   // sendTime_text
    function selectSendTime(liObj){
        var timeArea =$(liObj).attr("timeArea");

        $("#sendTime_text").html(timeArea);
      /*  $("#sendTime").html(timeArea);*/
    }

    //提交订单
    function saveOrder() {
        $(".submitOrder").off('click');
        var flag = $("#canDelivery").val();
        if (!$("#addressId").val()) {
            layer.open({
                content: "请选择收货地址",
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            });
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
            return;
        }
        if (!flag ||   flag == 'false') {
            layer.open({
                content: "不支持该地区配送",
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            });
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
            return;
        }
        //loading层
        var index = layer.open({type: 2})
        var addressId = $("#addressId").val();
        var remark =$("#remark").val();
        var timeSelect =  $("div.logisticsTimeShow li.on").text();
        $.ajax({
            url: contextPath + '/order/save',
            type: 'post',
            async: false,
            data: {addressId: addressId, couponId: $("#couponId").val(), orderFrom: $("#orderFrom").val(),remark:remark,timeSelect:timeSelect},
            dataType: 'json',
            success: function (message) {
                layer.close(index);
                if (message.success) {
                    var data = message.data;
                    var payTotalPrice = data.payTotalPrice;
                    var orderGroupCode = data.orderGroupCode;
                    var form = $('<form action="' + contextPath + '/order/success" method="post">' +
                            '<input type="hidden" name="orderGroupCode" value="' + orderGroupCode + '">' +
                            '<input type="hidden" name="payTotalPrice" value="' + payTotalPrice + '"></form>');
                    form.submit();
                } else {
                    layer.open({
                        content: message.message,
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    });
                    $(".submitOrder").on('click', function () {
                        saveOrder();
                    });
                }
            },
            error: function () {
                layer.close(index);
                layer.open({
                    content: message.message,
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
                $(".submitOrder").on('click', function () {
                    saveOrder();
                });
            }
        });
    }

    function addressChange() {
        //loading层
        var addressId = $("#addressId").val();
        $.ajax({
            url: contextPath + '/orderTemp/changAddress',
            type: 'post',
            async: false,
            data: {addressId: addressId},
            dataType: 'json',
            success: function (message) {
                if (message.success) {
                    var totalFreight = message.data;
                    var freightNumber = HgUtil.numberFormat(totalFreight,2);
                    if(freightNumber < 0){
                        $('span.totalFreight').replaceWith('<span class="fright totalFreight" style="color:red">不支持该区域配送</span>');
                        $("#canDelivery").val(false);
                        totalFreight = 0;
                    }else {
                        $("#canDelivery").val(true);
                        var baoyou = $("#baoYou").val();
                        if(baoyou && baoyou == 'true') {
                            $('span.totalFreight').replaceWith('<span class="fright totalFreight" style="color:red">包邮</span>');
                            totalFreight = 0;
                        }else{
                            $('span.totalFreight').text("￥" +freightNumber);
                        }
                    }
                    var couponPrice = Number($("#couponPrice").text());
                    var totalPrice = $("#totalPrice").val();
                    var totalPriceAndTotalFreight = HgUtil.numberFormat(Number(totalFreight) + Number(totalPrice) - couponPrice,2);
                    $("#totalPriceAndTotalFreight").text(totalPriceAndTotalFreight);
                }
            },
            error: function () {
            }
        });
    }

    function searchGoodSCart(){
        var loginFlag = "${member != null}";
        var URI = '/cart/findCount';
        if(loginFlag == 'false'){
            URI = '/cookieCart/findCount';
        }
        $.ajax({
            url: contextPath + URI,
            type: 'post',
            async: false,
            dataType: 'json',
            success: function (message) {
                var totalCount = message.data;
                $("#cartNum").text(HgUtil.numberFormat(totalCount,0));
            }
        });
    }
</script>
</body>
</html>
