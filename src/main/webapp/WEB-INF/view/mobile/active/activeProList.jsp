<!DOCTYPE html>
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
    <title>活动商品列表</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/store.css" rel="stylesheet" />
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <div class="head">
            <div class="headTop samePadding clearfix">
                <a href="javascript:history.go(-1);" class="menuBtn fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <div class="headSearch fleft">
                    <a href="../product/search.html"><img src="${ctx}/images/mobile/icon2.png" class="searchBtn" /></a>
                    <input type="text" placeholder="请输入关键字进行搜索" class="searchInput" />
                </div>
                <a href="${ctx}/cart" class="headCart fright"><img src="${ctx}/images/mobile/icon3.png" /></a>
            </div>
        </div><!-- //head -->
        <div class="main">
            <c:if test="${not empty activityMgr.activityBannerPath}">
            <a href="javascript:void(0)"  class="bannerImg" style="background-image: url(${ctx}/${activityMgr.activityBannerPath});"></a>
            </c:if>
            <div class="storeDiv">
                <div class="productList clearfix samePadding pinterest">
                    <c:forEach items="${activityMgr.activityGoodses}" var="aag">
                    <div class="proItem">
                        <a href="${ctx}/mobile/detail/${aag.goods.id}?goodsFrom=2&oid=${aag.id}" class="proImg"><img src="${ctx}/${aag.goods.image}" /></a>
                        <div class="proMessage">
                            <p class="proPrice clearfix">
                                ￥<span><fmt:formatNumber value="${aag.goods.promotePrice}" type="currency" pattern="0.00"/></span>
                                <img src="${ctx}/images/pc/proCart.jpg" class="iconAddCart fright" oid=${aag.id} g-id="${aag.goods.id}" onclick="cartIconClick(this)"/>
                            </p>
                            <div class="proName overflow">
                                <div><a href="${ctx}/mobile/detail/${aag.goods.id}?goodsFrom=2&oid=${aag.id}" >${aag.goods.title}</a></div>
                                <p>${aag.goods.intro}</p>
                            </div>
                        </div>
                        <c:if test="${aag.goods.product.label.labelPath}">
                        <div class="hotSale" style="background:url(${ctx}/${aag.goods.product.label.labelPath}) no-repeat center;background-size:100%"></div>
                        </c:if>
                    </div>
                    </c:forEach>
                </div>
                <!--加载更多-->
                <div class="load">正在加载...</div>
                </div>
        </div><!-- //main -->
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <%--<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script id="moreList" type="text/html">
        <div class="proItem">
            <a href="../product/productDetail.html" class="proImg"><img src="${ctx}/images/mobile/activeL2.jpg" /></a>
            <div class="proMessage">
                <p class="proPrice">￥<span>79.00</span></p>
                <a href="../product/productDetail.html" class="proName overflow">[日本 · 美肤控油小标兵] CANMAKE 井田棉花糖控</a>
            </div>
        </div>
        <div class="proItem">
            <a href="../product/productDetail.html" class="proImg"><img src="${ctx}/images/mobile/activeL2.jpg" /></a>
            <div class="proMessage">
                <p class="proPrice">￥<span>79.00</span></p>
                <a href="../product/productDetail.html" class="proName overflow">[日本 · 美肤控油小标兵] CANMAKE 井田棉花糖控</a>
            </div>
            <div class="hotSale" style="background:url(${ctx}/images/mobile/hotSale.png) no-repeat center;background-size:100%"></div>
        </div>
    </script>
    <script>
        //瀑布流代码
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
        });
    </script>--%>
</body>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script>
    var member= ${not empty member};
    /*  $(".cartIcon,.iconAddCart").click(
     cartIconClick(obj);
     });*/

    function cartIconClick(obj){
        var id = $(obj).attr("g-id");
        var oid = Number($(obj).attr("oId"));
        var flag = member;
        var url = "";
        if(flag){
            url="${ctx}/cart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom=3&otherId=" + oid;
        }else{
            url="${ctx}/cookieCart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom=3&otherId=" + oid;
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
    }
</script>
</html>
