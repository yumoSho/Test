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
    <title>${simpleWebsite.name}</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <c:set var="foot" value="0"></c:set>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/mobile/head.jspf" %>
        <div class="main">
            <!--banner轮播-->
            <c:if test="${fn:length(flowBanners)>0}">
            <div class="indexBanner">
                <c:forEach items="${flowBanners}" var="flowBanner">
                <a href="${flowBanner.url}" style="background:url(${ctx}/${flowBanner.image}) center no-repeat; background-size:100% 100%"></a>
                </c:forEach>
            </div>
            </c:if>
            <!--导航部分-->
            <div class="nav writeWrap">
                <ul class="navUl clearfix">
                    <li>
                        <a href="${ctx}/mobile/timed-panic-buying/index"><img src="${ctx}/images/mobile/navIcon1.png" /></a>
                    </li>
                    <li>
                        <a href="${ctx}/mobile/activity"><img src="${ctx}/images/mobile/navIcon2.png" /></a>
                    </li>
                    <li>
                        <a href="${ctx}/mobile/contentManagement/newsList"><img src="${ctx}/images/mobile/navIcon3.png" /></a>
                    </li>
                    <li>
                        <a href="${ctx}/mobile/allKindList"><img src="${ctx}/images/mobile/navIcon4.png" /></a>
                    </li>
                </ul>
            </div>
            <!--资讯快报-->
            <c:if test="${fn:length(newses)>0}">
            <div class="indexNews writeWrap clearfix">
                <a href="${ctx}/mobile/contentManagement/newsList"><img src="${ctx}/images/mobile/newsBg.png" class="fleft" /></a>
                <div class="newsList fleft">
                    <c:forEach items="${newses}" var="news">
                    <a href="${ctx}/mobile/contentManagement/newsDetail?id=${news.id}">${news.title}</a>
                    </c:forEach>
                </div>
            </div>
            </c:if>
            <div class="sameMargin">
                <!--优惠专区-->
                <div class="indexCardSlide">
                    <a href="${ctx}/mobile/contentManagement/content/coupon" style="background:url(
                    <c:if test="${not empty couponContentManagement.image}">${ctx }/${couponContentManagement.image}_713x181</c:if><c:if test="${empty couponContentManagement.image}">${ctx}/images/mobile/indexCard.png</c:if>) center no-repeat"></a>

                </div>
                <!--图片介绍-->
                <c:if test="${fn:length(advertisements)>0}">
                <div class="indexImgShow clearfix">
                    <c:forEach items="${advertisements}" var="ad" begin="0" end="3" varStatus="j">
                    <a href="${ad.field1}"><img src="${ctx}/${ad.image}<c:if test="${j.index eq 0}">_378x570</c:if><c:if test="${j.index ne 0}">_329x185</c:if>" /></a>
                    </c:forEach>
                </div>
                </c:if>
                <!--爆品推荐-->
                <c:if test="${fn:length(newGoodses)>0}">
                <div class="hot">
                    <div class="timeSaleTitle">
                        <span class="largeText">爆品推荐</span>
                        <!--<img src="${ctx}/images/mobile/icon7.png" />-->
                    </div>
                    <div class=" hotPro clearfix" >
                        <c:forEach items="${newGoodses}" var="newGoods" begin="0" end="2" varStatus="i">
                            <div class="proItem fleft" style="position: relative;">
                                <a href="${ctx}/mobile/detail/${newGoods.goods.id}" class="proImg"><img src="${ctx}/${newGoods.goods.image}_220x241" /></a>


                                <c:forEach items="${labels}" var="label">
                                    <c:if test="${label.id == newGoods.goods.product.label.id}"><%--添加小图标  测试图片 images/favicon.ico--%>
                                    <div style="width: 20px; height: 20px; background-size: 100%; position: absolute;  top: 0em;
                                            right: 0em; background:url(${ctx}/${label.labelPath}) no-repeat center;background-size:100%"></div>
                                    </c:if>
                                </c:forEach>

                                <div class="proMessage">
                                    <div class="proName color666">
                                        <h3><a href="${ctx}/mobile/detail/${newGoods.goods.id}">${newGoods.goods.title}</a></h3>
                                        <p>${newGoods.goods.intro}</p>
                                    </div>
                                    <p class="saleNum color666">销量<span>${newGoods.goods.product.sales}</span>件</p>
                                    <p class="proPrice colorRed clearfix">
                                        ￥<span><fmt:formatNumber value="${newGoods.goods.price}" type="currency" pattern="0.00"/></span>
                                        <img src="${ctx}/images/pc/proCart.jpg" class="iconAddCart fright" g-id="${newGoods.goods.id}" style="width: 26px;"/>
                                    </p>
                                    <%--<c:if test="${not empty newGoods.labelPath}">
                                        <div class="hotSale" style="background:url(${ctx}/${newGoods.labelPath}) no-repeat center;background-size:100%"></div>
                                    </c:if>--%>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <c:if test="${fn:length(discountGoodses)>0}">
                <!--限时抢购-->
                <div class="timeSale">
                    <div class="timeSaleTitle"><span>限时抢购</span></div>
                    <div class="productList quickSale clearfix">
                        <c:forEach items="${discountGoodses}" var="discount" begin="0" end="3">
                        <div class="proItem fleft" style="position: relative;">
                            <a href="${ctx}/mobile/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}" class="proImg"><img src="${ctx}/${discount.goods.image}_347x380" /></a>

                            <c:forEach items="${labels}" var="label">
                                <c:if test="${label.id == newGoods.goods.product.label.id}"><%--添加小图标  测试图片 images/favicon.ico--%>
                                    <div style="width: 20px; height: 20px; background-size: 100%; position: absolute;  top: 0em;
                                            right: 0em; background:url(${ctx}/${label.labelPath}) no-repeat center;background-size:100%"></div>
                                </c:if>
                            </c:forEach>

                            <div class="proMessage">
                                <div class="proName overflow">
                                    <h3><a href="${ctx}/mobile/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}">${discount.goods.title}</a></h3>
                                    <p>${discount.goods.intro}</p>
                                </div>
                                <div class="saleNum clearfix">
                                    <span class="fleft color666"><span class="colorRed">${discount.discount}</span>折</span>
                                    <span class="fright color666">销量<span>${discount.goods.product.sales}</span>件</span>
                                </div>
                                <div class="nowPrice colorRed">现价：<span>￥<span><fmt:formatNumber value="${discount.goods.promotePrice}" type="currency" pattern="0.00"/></span></span></div>
                                <div class="marketPrice color666">市场价：<span>￥<span><fmt:formatNumber value="${discount.goods.price}" type="currency" pattern="0.00"/></span></span></div>
                                <img src="${ctx}/images/pc/proCart.jpg" class="iconAddCart fright" oId="${discount.id}" g-id="${discount.goods.id}"  style="width: 26px;"/>
                                <div class="countDown color666">
                                    <span class="time"  style="color: #e60019;" data-endtime="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${discount.endDate}" type="both"/>">剩下00天00时00分</span>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--楼层切换-->
                <c:if test="${not empty first}">
                <div class="floorTab">
                    <div class="tabBtn1 tabBtn">
                        <c:forEach items="${first}" var="f" varStatus="j">
                        <a href="javascript:void(0);"  <c:if test="${j.index eq 0}"> class="on" </c:if> >${f.category.name}</a>
                        </c:forEach>
                    </div>
                    <div class="tabContent1">
                        <c:forEach items="${first}" var="g">
                        <div class="tabContent">
                            <div class="productList clearfix" >
                                <c:forEach items="${g.categoryGoodses}" var="cg" begin="0" end="7">
                                <div class="proItem fleft" style="position: relative;">
                                    <a href="${ctx}/mobile/detail/${cg.goods.id}" class="proImg"><img src="${ctx}/${cg.goods.image}_347x380" /></a>

                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id == newGoods.goods.product.label.id}"><%--添加小图标  测试图片 images/favicon.ico--%>
                                            <div style="width: 20px; height: 20px; background-size: 100%; position: absolute;  top: 0em;
                                                    right: 0em; background:url(${ctx}/${label.labelPath}) no-repeat center;background-size:100%"></div>
                                        </c:if>
                                    </c:forEach>

                                    <div class="proMessage">
                                        <a href="${ctx}/mobile/detail/${cg.goods.id}" class="proName overflow">${cg.goods.title}</a>
                                        <p class="subTitle">${cg.goods.intro}</p>
                                        <p class="proPrice colorRed">￥<span><fmt:formatNumber value="${cg.goods.price}" type="currency" pattern="0.00"/></span></p>
                                      <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${cg.goods.id}" style="width: 26px;"/>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <c:if test="${not empty second}">
                <div class="floorTab">
                    <div class="tabBtn1 tabBtn">
                        <c:forEach items="${second}" var="s" varStatus="sd">
                            <a href="javascript:void(0);" <c:if test="${sd.index eq 0}"> class="on" </c:if>>${s.category.name}</a>
                        </c:forEach>
                    </div>
                    <div class="tabContent1">
                        <c:forEach items="${second}" var="gs">
                        <div class="tabContent">
                            <div class="productList clearfix">
                                <c:forEach items="${gs.categoryGoodses}" var="sg" begin="0" end="7">
                                <div class="proItem fleft" style="position: relative;">
                                    <a href="${ctx}/mobile/detail/${sg.goods.id}" class="proImg"><img src="${ctx}/${sg.goods.image}_347x380" /></a>

                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id == newGoods.goods.product.label.id}"><%--添加小图标  测试图片 images/favicon.ico--%>
                                            <div style="width: 20px; height: 20px; background-size: 100%; position: absolute;  top: 0em;
                                                    right: 0em; background:url(${ctx}/${label.labelPath}) no-repeat center;background-size:100%"></div>
                                        </c:if>
                                    </c:forEach>

                                    <div class="proMessage">
                                        <a href="${ctx}/mobile/detail/${sg.goods.id}" class="proName overflow">${sg.goods.title}</a>
                                        <p class="subTitle">${sg.goods.intro}</p>
                                        <p class="proPrice colorRed">￥<span><fmt:formatNumber value="${sg.goods.price}" type="currency" pattern="0.00"/></span></p>
                                        <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${sg.goods.id}" style="width: 26px;"/>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <c:if test="${not empty third}">
                <div class="floorTab">
                    <div class="tabBtn1 tabBtn">
                        <<c:forEach items="${third}" var="t" varStatus="h">
                        <a href="javascript:void(0);"<c:if test="${h.index eq 0}"> class="on" </c:if>>${t.category.name}</a>
                    </c:forEach>
                    </div>
                    <div class="tabContent1">
                        <c:forEach items="${third}" var="thd">
                        <div class="tabContent">
                            <div class="productList clearfix">
                                <c:forEach items="${thd.categoryGoodses}" var="thg" begin="0" end="7">
                                <div class="proItem fleft" style="position: relative;">
                                    <a href="${ctx}/mobile/detail/${thg.goods.id}" class="proImg"><img src="${ctx}/${thg.goods.image}_347x380" /></a>

                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id == newGoods.goods.product.label.id}"><%--添加小图标  测试图片 images/favicon.ico--%>
                                            <div style="width: 20px; height: 20px; background-size: 100%; position: absolute;  top: 0em;
                                                    right: 0em; background:url(${ctx}/${label.labelPath}) no-repeat center;background-size:100%"></div>
                                        </c:if>
                                    </c:forEach>

                                    <div class="proMessage clearfix">
                                        <a href="${ctx}/mobile/detail/${thg.goods.id}" class="proName overflow">${thg.goods.title}</a>
                                        <p class="subTitle">${thg.goods.intro}</p>
                                        <p class="proPrice colorRed">￥<span><fmt:formatNumber value="${thg.goods.price}" type="currency" pattern="0.00"/></span></p>
                                        <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${thg.goods.id}" style="width: 26px;"/>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
            </div>
            <!--广告介绍-->
            <c:if test="${fn:length(footAdvertisements)>0}">
            <div class="bannerShow swiper-container" id="annouceSlide">
                <div class="bannerShowInner swiper-wrapper clearfix">
                    <c:forEach items="${footAdvertisements}" var="foot" begin="0" end="3">
                    <a href="${foot.field1}" class="swiper-slide">
                        <img src="${ctx}/${foot.image}_291x191" />
                        <p>${foot.title}</p>
                    </a>
                    </c:forEach>
                </div>
            </div>
            </c:if>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/mobile/foot.jspf" %><!-- //foot -->
        <%@ include file="/WEB-INF/view/include/mobile/rightfoot.jspf" %>
    </div><!-- //wrap -->
    <div class="addrIframe" id="addrIframe">
        <iframe src="${ctx}/mobile/addSelect" class="iframeInner" id="iframeInner"></iframe>
    </div>
    <script src="${ctx}/js/mobile/jquery.slides.min.js"></script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/jquery.glanway.timeUp.1.0.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/Tab.js"></script>
    <script>
        //倒计时
        $(".time").timeUp({
            "template": "剩下%d天%h时%m分",
            "end": function (obj) {
                //alert(obj.html() + " 结束啦");
            }
        });
        $(function () {
            slide(".indexBanner", 750, 390);
            //优惠券轮播
            slide(".indexCardSlide", 713, 181);
            //人气推荐部分js
            $(".popular .popularBrand li:eq(2)").css("margin-left", 0);
            //广告滑动
            var brandpart = new Swiper('#annouceSlide', {
                slidesPerView: 2.5,
                spaceBetween: 0
            });
            new GlanwayTab(".tabBtn1 a", ".tabContent1 .tabContent");

            //返回顶部
            $(".goTop").click(function () {
                $("html,body").animate({ "scrollTop": '0' }, 500);
            });
            var member= ${not empty member};
            $(".cartIcon,.iconAddCart").click(function() {
                var gId = $(this).attr("g-id");
                var flag = member;
                var oid = 0;
                if((Number($(this).attr("oId")))>=0){
                    oid = Number($(this).attr("oId"));
                }
                if(oid==0){
                    goodsFrom=0;
                }else{
                    goodsFrom=3;
                }
                var url = "";
                if(flag){
                    url="${ctx}/cart/saveAsync?goodsId="+gId+"&buyCount=1&goodsFrom="+goodsFrom+"&otherId=" + oid;
                }else{
                    url="${ctx}/cookieCart/saveAsync?goodsId="+gId+"&buyCount=1&goodsFrom="+goodsFrom+"&otherId=" + oid;
                }
                $.ajax({
                    url: url,
                    type: "POST",
                }).done(function (data) {
                    if (data.success) {
                        layer.open({
                            content: '加入购物车成功',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        })
                        searchGoodSCart();
                    } else if (!data.message) {
                        window.location.href = contextPath + "/login?redirectURL=" + location.href;
                    } else if (data.message) {
                        layer.open({
                            content: '加入购物车失败',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        })
                    }
                }).fail(function (data) {
                    layer.open({
                        content: '加入购物车失败',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    })
                });
            });
            //修改仅一楼样式被隐藏的问题   20161104
            $(".sameMargin .floorTab .tabContent1 .tabContent").each(function(){
                if($(this).index() != 0 ){
                    $(this).css("display","none");
                }
            });
        });
    </script>
</body>
</html>
