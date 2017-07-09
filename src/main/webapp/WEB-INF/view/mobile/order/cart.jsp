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
    <title>购物车</title>
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
    <c:set var="foot" value="2" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <%--head--%>
    <c:set value="true" var="isCart"/>
    <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/head.jspf" %>
    <div class="main" style="padding-bottom:0">
        <input type="hidden" id="loginFlag"
               value="<c:choose><c:when test="${member == null}">false</c:when><c:otherwise>true</c:otherwise></c:choose>"/>
        <c:choose>
            <c:when test="${fn:length(rtMap.cartList) > 0}">
                <div>
                    <div class="cartList pinterest " style="padding-bottom: 1.4rem">
                        <c:forEach items="${rtMap.cartList}" var="cart">
                            <div class="cartItem writeWrap">
                                <div class="itemTop">
                                    <input type="checkbox" class="checkOne" value="${cart.id}" name="proListCheckbox" <c:if test="${cart.selected}">checked</c:if> />
                                    <input type="hidden" name="id" value="${cart.id}">
                                </div>
                                <div class="itemBottom clearfix">
                                    <a href="${ctx}/mobile/detail/${cart.goodsId}?goodsFrom=${cart.goodsFrom}&oid=${cart.otherId}" class="fleft cartProImg">
                                        <img src="${ctx}/${cart.goodsImg}"/>
                                    </a>
                                    <div class="fright cartProDes">
                                        <a href="${ctx}/mobile/detail/${cart.goodsId}?goodsFrom=${cart.goodsFrom}&oid=${cart.otherId}" class="cartProName overflow">${cart.goodsName}</a>
                                        <div class="cartProNameHandel clearfix">
                                            <c:choose>
                                                <c:when test="${cart.isLive}">
                                                    <div class="fleft price">价格：<span>￥<span>${cart.price}</span></span></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color:red">该商品已下架</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="fright numWrap clearfix">
                                                <input type="hidden" name="id" value="${cart.id}">
                                                <input type="hidden" name="oldCount">
                                                <a href="javascript:void(0);" class="fleft numRelize numDown">-</a>
                                                <input type="text" class="numInput fleft numberImport" value="${cart.buyCount}"/>
                                                <a href="javascript:void(0);" class="numAdd numUp">+</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <!--购物车编辑前显示-->
                    <div class="cartBottom samePadding writeWrap clearfix">
                        <label class=" fleft"><input type="checkbox" class="checkAll"/>全选</label>
                        <div class="cartAccount fleft">合计：<span>￥<span class="lastHadPay">${rtMap.totalPrice}</span></span></div>
                        <a href="javascript:void(0)" class="goForPay fright">去结算</a>
                    </div>
                    <!--购物车编辑后显示-->
                    <div class="cartEdit samePadding writeWrap clearfix" style="display:none">
                        <label class=" fleft"><input type="checkbox" class="checkAll"/>全选</label>
                        <a href="javascript:void(0);" class="forCollect"><img src="${ctx}/images/mobile/icon35a.png"/>移动到收藏夹</a>
                        <a href="javascript:void(0);" class="deleteChecked"><img src="${ctx}/images/mobile/icon36.png"/>删除</a>
                    </div>
                    <!--加载更多-->
                    <%--<div class="load">正在加载...</div>--%>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cartNull">
                    <img src="${ctx}/images/mobile/icon12.png"/>
                    <p>你的购物车中没有商品</p>
                </div>
            </c:otherwise>
        </c:choose>

    </div><!-- //main -->
<c:if test="${fn:length(rtMap.cartList) lt 1}">
    <%@ include file="/WEB-INF/view/include/mobile/foot.jspf" %><!-- //foot -->
</c:if>
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png"/></a>
</div><!-- //wrap -->
<!--左侧导航-->
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script src="${ctx}/js/mobile/order/cart.js"></script>
<script id="moreList" type="text/html">
    <div class="cartItem writeWrap">
        <div class="itemTop">
            <input type="checkbox" class="checkOne" value="${cart.goodsId}" name="proListCheckbox" <c:if test="${cart.selected}">checked</c:if> />
            <input type="hidden" name="id" value="${cart.id}">
        </div>
        <div class="itemBottom clearfix">
            <a href="${ctx}/mobile/detail/<!cart.id!>" class="fleft cartProImg">
                <img src="${ctx}/<!cart.goodsImg!>"/>
            </a>
            <div class="fright cartProDes">
                <a href="${ctx}/mobile/detail/<!cart.goodsId!>" class="cartProName overflow"><!cart.goodsName!></a>
                <div class="cartProNameHandel clearfix">
                    <c:choose>
                        <c:when test="<!cart.isLive!>">
                            <div class="fleft price">价格：<span>￥<span><!cart.price!></span></span></div>
                        </c:when>
                        <c:otherwise>
                            <span style="color:red">该商品已下架</span>
                        </c:otherwise>
                    </c:choose>
                    <div class="fright numWrap clearfix">
                        <input type="hidden" name="id" value="<!cart.id!>">
                        <input type="hidden" name="oldCount">
                        <a href="javascript:void(0);" class="fleft numRelize numDown">-</a>
                        <input type="text" class="numInput fleft numberImport" value="<!cart.buyCount!>"/>
                        <a href="javascript:void(0);" class="numAdd numUp">+</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>
<script>

    $(function () {
        //编辑购物车
        $(".editCart").click(function () {
            if ($(this).text() === "编辑") {
                $(this).text("完成");
                $(".cartBottom").hide().siblings(".cartEdit").show();
            } else {
                $(this).text("编辑");
                $(".cartBottom").show().siblings(".cartEdit").hide();
            }
        });




    /*    //瀑布流代码
        $(window).scroll(function () {
            var ok = true;
            if (ok) {
                //获取加载的数据
                var moreLi = baidu.template("moreList");
                //获取到屏幕顶部距离
                var loadheight = $(".load").offset().top - $(window).scrollTop() + $(".load").height();
                //获取屏幕高度
                var screenheight = $(window).height();
                if (loadheight < screenheight) {
                    for (var i = 1; i < 4; i++) {
                        $(".pinterest").append(moreLi);
                    }
                }
            }
        });*/
    });
</script>
</body>
</html>
