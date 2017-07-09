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
    <title>首页-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/index.css" rel="stylesheet"/>
    <c:set var="nav" value="0"/>
    <style>
        .allKind .slideNav{display: block}
        .floorItem,.timeItem,.hoverWrap {
            position: relative;
        }
        .hotPro {
            position: absolute;
            top: 0;
            right: 0px;
        }
        .hoverWrap .hotPro {
            position: absolute;
            top: 0;
            right: 9px;
        }
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
        <div class="main">
            <!--banner轮播和banner上新闻推送-->
            <div class="bannerPart">
                <div class="bannerSlide">
                    <c:forEach items="${flowBanners}" var="flowBanner">
                    <a href="${flowBanner.url}" style="background:url(${ctx}/${flowBanner.image}) center no-repeat;"></a>
                    </c:forEach>
                </div>
                    <div class="bannerNews">
                        <c:if test="${fn:length(newses)>0}">
                        <div class="indexNews clearfix">
                            <div class="newsTitle clearfix"><span class="fl">资讯快报</span><a href="${ctx}/contentManagement/newsList" class="fr">更多 <span>></span></a></div>
                            <div class="newsList">
                                <c:forEach items="${newses}" var="news">
                                <a href="${ctx}/contentManagement/newsDetail?id=${news.id}">${news.title}</a>
                                </c:forEach>
                            </div>
                        </div>
                        </c:if>
                        
                        <a href="${ctx }/contentManagement/content/coupon" class="indexSaleCard"
                           style="background:url(<c:if test="${not empty couponContentManagement.image}">${ctx }/${couponContentManagement.image}_300x89</c:if>
                           <c:if test="${empty couponContentManagement.image}">${ctx}/images/pc/indexSale.jpg</c:if>)">
                            <p>优惠券专区</p>
                        </a>
                        <c:if test="${not empty advertisement}">
                        <a href="${advertisement.field1}"><img src="${ctx}/${advertisement.image}" width="300" height="132" class="proPushed" title="${advertisement.title}" /></a>
                        </c:if>
                    </div>
            </div>
            <div class="samewidth">
                <!--banner图下快链-->
                <c:if test="${fn:length(advertisements)>0}">
                <div class="topQuickLInks clearfix">
                    <c:forEach items="${advertisements}" var="ad" begin="0" end="3">
                    <a href="${ad.field1}"><img src="${ctx}/${ad.image}" width="291" height="300" /></a>
                    </c:forEach>
                </div>
                </c:if>
                <!--爆品推荐-->
                <c:if test="${fn:length(newGoodses)>0}">
                <div class="hotPushed timeSale">

                    <div class="timeSaleTitle"><span>爆品推荐</span></div>
                    <div class="hotProList clearfix">
                        <c:forEach items="${newGoodses}" var="newGoods" begin="0" end="2" varStatus="i">
                        <div class="hotProItem hotProItem${i.index+1}">
                            <div class="hoverWrap">
                                <a href="${ctx}/detail/${newGoods.goods.id}" class="timeSaleImg"><img src="${ctx}/${newGoods.goods.image}" width="222" height="243" /></a>
                                <c:forEach items="${labels}" var="label">
                                    <c:if test="${label.id eq newGoods.goods.product.label.id}">
                                        <img src="${ctx}/${label.labelPath}" width="50" height="42" class="hotPro" />
                                    </c:if>
                                </c:forEach>

                                <div class="proMsgWrap">
                                    <a href="${ctx}/detail/${newGoods.goods.id}" class="indexProName">${newGoods.goods.title}</a>
                                    <p>${newGoods.goods.intro}</p>
                                    <a href="javascript:void(0);" class="fr  add-cart" data-id="${newGoods.goods.id}"><img src="${ctx}/images/pc/proCart.jpg" width="35"></a>
                                    <div class="proPricePart clearfix">
                                        <div class="fl leftPrice">￥<span><fmt:formatNumber value="${newGoods.goods.price}" type="currency" pattern="0.00"/></span></div>
                                        <div class="fr rightPrice color666">销量<span>${newGoods.goods.product.sales}</span>件</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--限时抢购-->
                <c:if test="${fn:length(discountGoodses)>0}">
                <div class="timeSale">
                    <div class="timeSaleTitle"><span>限时抢购</span></div>

                    <div class="timeProList clearfix">
                        <c:forEach items="${discountGoodses}" var="discount" begin="0" end="3">
                        <div class="timeItem">
                            <a href="${ctx}/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}"><img src="${ctx}/${discount.goods.image}" width="285" height="312" /></a>
                            <c:forEach items="${labels}" var="label">
                                <c:if test="${label.id eq discount.goods.product.label.id}">
                                    <img src="${ctx}/${label.labelPath}" width="50" height="42" class="hotPro" />
                                </c:if>
                            </c:forEach>
                            <div class="timeProName padding10 color666">
                                <h3><a href="${ctx}/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}">${discount.goods.title}</a></h3>
                                <p>${discount.goods.intro}</p>
                            </div>
                            <div class="saleNumber padding10 clearfix">
                                <div class="saleLeft fl">${discount.discount}<span>折</span></div>
                                <div class="saleNum color666 padding10 fr">销量<span>${discount.goods.product.sales}</span>件</div>
                            </div>
                            <div class="nowPrice padding10">现&nbsp;&nbsp;&nbsp;价：￥<span><fmt:formatNumber value="${discount.goods.promotePrice}" type="currency" pattern="0.00"/></span></div>
                            <div class="oldPrice padding10 color666">市场价：￥<span><fmt:formatNumber value="${discount.goods.price}" type="currency" pattern="0.00"/></span>
                                <a href="javascript:void(0);" class="fr  add-cart" oId="${discount.id}" data-id="${discount.goods.id}"><img src="${ctx}/images/pc/proCart.jpg" width="45" height="37"></a>
                            </div>
                            <div class="saleTime" data-countdate="<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${discount.endDate}" type="both"/>">
                                <img src="${ctx}/images/pc/timeBg.png" width="15" height="15" class="clock" />
                                <span>
                                    剩余<span class="time_d"></span>天<span class="time_h"></span>时<span class="time_m"></span>分
                                </span>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--类型切换部分一楼-->
                <c:if test="${not empty first}">
                <div class="floor florr1">
                    <div class="tabBtn">
                        <c:forEach items="${first}" var="f" varStatus="j">
                        <a href="${ctx}/search?cat=${f.category.id}" <c:if test="${j.index eq 0}"> class="on" </c:if>>${f.category.name}</a>
                        </c:forEach>
                        <%--<a href="javascript:void(0);">特色专区</a>
                        <a href="javascript:void(0);">禽肉蛋奶</a>
                        <a href="javascript:void(0);">粮油副食</a>--%>
                    </div>
                    <div class="floor1Tab">
                        <c:forEach items="${first}" var="g" varStatus="index">
                        <div class="floor1Pro clearfix"  <c:if test="${index.index ne 0}"> style="display: none"</c:if>  >
                            <a href="javascript:void(0);" class="leftImg fl">
                                <img src="${g.pic}_282x697" width="282" height="697" />
                                <span>${g.category.name}</span>
                            </a>
                            <div class="floorRightPro fl clearfix">
                                <!--1-->
                                <c:forEach items="${g.categoryGoodses}" var="cg" begin="0" end="7">
                                <div class="floorItem" style="position: relative">
                                    <a href="${ctx}/detail/${cg.goods.id}"><img src="${ctx}/${cg.goods.image}" width="222" height="243" /></a>
                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id eq cg.goods.product.label.id}">
                                            <img src="${ctx}/${label.labelPath}" width="50" height="42" class="hotPro" />
                                        </c:if>
                                    </c:forEach>
                                    <div class="floorProBottom">
                                        <div class="florProName padding10">
                                            <h3><a href="${ctx}/detail/${cg.goods.id}">${cg.goods.title}</a></h3>
                                            <p>${cg.goods.intro}</p>
                                        </div>
                                        <p>
                                            ￥<span><fmt:formatNumber value="${cg.goods.price}" type="currency" pattern="0.00"/></span>
                                            <a href="javascript:void(0);" class="fr  add-cart" data-id="${cg.goods.id}"><img src="${ctx}/images/pc/proCart.jpg" width="45" height="37"></a>
                                        </p>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--类型切换部分二楼-->
                <c:if test="${not empty second}">
                <div class="floor florr1"  >
                    <div class="tabBtn tabBtn2">
                        <c:forEach items="${second}" var="s" varStatus="sd">
                        <a href="${ctx}/search?cat=${s.category.id}" <c:if test="${sd.index eq 0}"> class="on" </c:if>>${s.category.name}</a>
                        </c:forEach>
                    </div>
                    <div class="floor2Tab">
                        <c:forEach items="${second}" var="gs" varStatus="p">
                        <div class="floor1Pro clearfix"  <c:if test="${p.index ne 0}"> style="display: none"</c:if> >
                            <a href="javascript:void(0)" class="leftImg fr">
                                <img src="${gs.pic}_282x697" width="282" height="697" />
                                <span>${gs.category.name}</span>
                            </a>
                            <div class="floorRightPro fl clearfix">
                                <c:forEach items="${gs.categoryGoodses}" var="sg" begin="0" end="7">
                                <div class="floorItem" style="position: relative">
                                    <a href="${ctx}/detail/${sg.goods.id}"><img src="${ctx}/${sg.goods.image}" width="222" height="243" /></a>
                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id eq sg.goods.product.label.id}">
                                            <img src="${ctx}/${label.labelPath}" width="50" height="42" class="hotPro" />
                                        </c:if>
                                    </c:forEach>
                                    <div class="floorProBottom">
                                        <div class="florProName padding10">
                                            <h3><a href="${ctx}/detail/${sg.goods.id}">${sg.goods.title}</a></h3>
                                            <p>${sg.goods.intro}</p>
                                        </div>
                                        <p>
                                            ￥<span><fmt:formatNumber value="${sg.goods.price}" type="currency" pattern="0.00"/></span>
                                            <a href="javascript:void(0);"  class="fr  add-cart" data-id="${sg.goods.id}"><img src="${ctx}/images/pc/proCart.jpg" width="45" height="37"></a>
                                        </p>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--类型切换部分三楼-->
                <c:if test="${not empty third}">
                <div class="floor florr1"  >
                    <div class="tabBtn tabBtn3">
                        <c:forEach items="${third}" var="t" varStatus="h">
                            <a href="${ctx}/search?cat=${t.category.id}"  <c:if test="${h.index eq 0}"> class="on" </c:if>>${t.category.name}</a>
                        </c:forEach>
                    </div>
                    <div class="floor3Tab">
                        <c:forEach items="${third}" var="thd" varStatus="dp">
                        <div class="floor1Pro clearfix" <c:if test="${dp.index ne 0}"> style="display: none"</c:if>>
                            <a href="javascript:void(0);" class="leftImg fl">
                                <img src="${thd.pic}_282x697" width="282" height="697" />
                                <span>${thd.category.name}</span>
                            </a>
                            <div class="floorRightPro fl clearfix">
                                <!--1-->
                                <c:forEach items="${thd.categoryGoodses}" var="thg" begin="0" end="7">
                                <div class="floorItem" style="position: relative">
                                    <a href="${ctx}/detail/${thg.goods.id}"><img src="${ctx}/${thg.goods.image}" width="222" height="243" /></a>
                                    <c:forEach items="${labels}" var="label">
                                        <c:if test="${label.id eq thg.goods.product.label.id}">
                                            <img src="${ctx}/${label.labelPath}" width="50" height="42" class="hotPro" />
                                        </c:if>
                                    </c:forEach>
                                    <div class="floorProBottom">
                                        <div class="florProName padding10">
                                            <h3><a href="${ctx}/detail/${thg.goods.id}">${thg.goods.title}</a></h3>
                                            <p>${thg.goods.intro}</p>
                                        </div>
                                        <p>
                                            ￥<span><fmt:formatNumber value="${thg.goods.price}" type="currency" pattern="0.00"/></span>
                                            <a href="javascript:void(0);" class="fr  add-cart" data-id="${thg.goods.id}"><img src="${ctx}/images/pc/proCart.jpg" width="45" height="37"></a>
                                        </p>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--底部上部分链接-->
                <c:if test="${fn:length(footAdvertisements)>0}">
                <div class="bottomLinks clearfix">
                    <c:forEach items="${footAdvertisements}" var="foot" begin="0" end="3">
                    <a href="${foot.field1}">
                        <img src="${ctx}/${foot.image}" width="291" height="191" />
                        <p>${foot.title}</p>
                    </a>
                    </c:forEach>
                </div>
                </c:if>
            </div>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script src="${ctx}/js/pc/jquery.slides.min.js"></script>
    <script src="${ctx}/js/pc/Tab.js"></script>
    <script src="${ctx}/js/pc/layer/layer.js"></script>
    <script>
        $(function () {
            var GlanwayTab2 = function (titleItems, contentItems) {
                var $ti = $(titleItems);
                var $ci = $(contentItems);
                var index = 0;
                var prev = $(".prev"),
                        next = $(".next");
                var len = $ti.size();
                //初始化按钮索引
                prev.data("index", len - 1);
                next.data("index", 0);
                $ti.each(function (i) {
                    $(this).hover(function () {
                        index = i;
                        $(this).addClass("on").siblings().removeClass("on");
                        prev.data("index", (i - 1) < 0 ? i - 1 : len - 1);
                        next.data("index", (i + 1) < len ? i + 1 : 0);
                        $ti.eq(i).addClass("on").siblings().removeClass("on");
                        $ci.eq(i).show().siblings().hide();
                        return false;
                    });
                });
                $ti.eq(index).hover();
            }
            //一楼切换
            new GlanwayTab2(".tabBtn a", " .floor1Tab .floor1Pro");
            new GlanwayTab2(".tabBtn2 a", " .floor2Tab .floor1Pro");
            new GlanwayTab2(".tabBtn3 a", " .floor3Tab .floor1Pro");



            //导航轮播
            if ($(".bannerSlide").children().length > 1) {
                $(".bannerSlide").slidesjs({
                    height: 500,
                    navigation: false,
                    play: {
                        auto: true,
                        restartDelay: 2500
                    },
                    callback: {
                        complete: function (number) {
                            clearTimeout(slideTimer)
                            var slideTimer = setTimeout(function () {
                                $(".slidesjs-play").click();
                            }, 4000)
                        }
                    }
                });
                //导航点居中
                var pagination = $(".bannerSlide").find(".slidesjs-pagination li").size() * 30;
                $(".bannerSlide").find(".slidesjs-pagination").css("margin-left", "-" + pagination / 2 + "px");
            };
            //banner轮播上边的新闻js
            var windowWidth = $(window).width();
            $(".bannerPart .bannerNews").css("margin-right",(windowWidth-1200)/2);
            //倒计时
            setInterval(function(){
                $(".saleTime").each(function(){
                    var date = $(this).data("countdate");
                    show_time(date,$(this));
                });

            },1000);
            //楼层按钮样式切换
            $(".floor .tabBtn a").hover(function () {
                $(this).addClass("on").siblings().removeClass("on");
            });
            //产品部分加入购物车
            var flag = ${not empty member};
            var  clicked= false;
            $(".add-cart").click(function () {
                var id = $(this).data("id");
                var oid = 0;
                if((Number($(this).attr("oId")))>=0){
                    oid = Number($(this).attr("oId"));
                }
              /*  var oid = Number($(this).attr("oId"));*/
                if(oid==0){
                    goodsFrom=0;
                }else{
                    goodsFrom=3;
                }
                var url="";
                if(flag){
                    url="${ctx}/cart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom="+goodsFrom+"&otherId=" + oid;
                }else{
                    url="${ctx}/cookieCart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom="+goodsFrom+"&otherId=" + oid;
                }
                if(clicked){
                    return false;
                }
                clicked =true;

                $.ajax({
                    type: "get",
                    url: url,
                }).done(function (data) {
                    if(data.success){
                        layer.msg('已加入购物车',{ time: 1500}, function(){});
                        cartAsyn();
                    }else{
                        layer.msg(data.message,{ time: 1500}, function(){});
                    }
                    clicked = false;
                }).fail(function (data) {
                    layer.msg('加入购物车失败',{ time: 1500}, function(){});
                    clicked = false;
                })
                /*layer.msg("加入购物车成功！");*/
            });


        });
        function show_time(dataStr,jTarget) {
            var time_start = new Date().getTime(); //设定当前时间
            var time_end = new Date(dataStr).getTime(); //设定目标时间

            // 计算时间差
            var time_distance = time_end - time_start;
            if(time_distance<=0){
                return false;
            }
            // 天
            var int_day = Math.floor(time_distance / 86400000)
            time_distance -= int_day * 86400000;
            // 时
            var int_hour = Math.floor(time_distance / 3600000)
            time_distance -= int_hour * 3600000;
            // 分
            var int_minute = Math.floor(time_distance / 60000)
            time_distance -= int_minute * 60000;
            // 秒
            var int_second = Math.floor(time_distance / 1000)
            // 时分秒为单数时、前面加零
            if (int_hour < 10) {
                int_hour = "0" + int_hour;
            }
            if (int_minute < 10) {
                int_minute = "0" + int_minute;
            }
            if (int_second < 10) {
                int_second = "0" + int_second;
            }
            // 显示时间
            $(".time_d",jTarget).html(int_day);
            $(".time_h",jTarget).html(int_hour);
            $(".time_m",jTarget).html(int_minute);
            // 设置定时器
            //setTimeout("show_time()", 1000);
        }
    </script>
</body>
</html>
