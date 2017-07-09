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
    <title>活动商品列表-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/active.css" rel="stylesheet" />
    <script src="${ctx}/js/pc/modernizr-2.6.2.min.js"></script>
    <c:set var="nav" value="2"/>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
        <div class="main samewidth">
            <c:if test="${fn:length(activityMgr.activityGoodses)>0}">
            <a href="#" class="activeProBanner" style="background:url(${ctx}/${activityMgr.activityBannerPath}) center no-repeat;"></a>
            <div class="activeProList commonProList clearfix">
                <c:forEach items="${activityMgr.activityGoodses}" var="g">
                <div class="proItem hot">
                    <a href="${ctx}/detail/${g.goods.id}?goodsFrom=2&oid=${g.id}"><img src="${ctx}/${g.goods.image}" width="230" height="252" /></a>
                    <a href="${ctx}/detail/${g.goods.id}?goodsFrom=2&oid=${g.id}" class="proName color666 padding10">${g.goods.title}</a>
                    <p class="subTitle padding10">${g.goods.intro}</p>
                    <div class="proPrice color666 clearfix">
                        <span class="fl">价格：<span>￥<fmt:formatNumber value="${g.goods.price}" type="currency" pattern="0.00"/></span></span>
                        <span class="fr" >销量<span style="color:red">${g.goods.product.sales}</span>件</span>
                    </div>
                    <div class="activePrice color666 padding10">活动价：<span class="sPrice">￥<span><fmt:formatNumber value="${g.goods.promotePrice}" type="currency" pattern="0.00"/></span></span>
                        <a href="javascript:void(0);" class="proCart add-cart" oId="${g.id}"  data-id="${g.goods.id}"><img src="/images/pc/proCart.jpg" height="20" /></a>
                    </div>
                    <c:if test="${not empty g.goods.product.label.labelPath}">
                    <img src="${g.goods.product.label.labelPath}" width="50" height="42" class="hotPro" />
                    </c:if>
                </div>
                </c:forEach>
            </div>
            </c:if>
            <c:if test="${empty activityMgr.activityGoodses }">
                <c:if test="${fn:length(activities.data) eq 0}">
                    <div class="proNullNotice">
                        没有找到相关的商品！<br />建议您按照以下提示调整关键字
                        <p>
                            1、您输入的关键字是否有错别字，或者更换区域试试；<br />
                            2、如果您使用自然语言进行搜索，请提炼出关键字再搜索试试！
                        </p>
                    </div>
                </c:if>
            </c:if>
            <!-- 分页 -->
            <%--<div class="samePage samewidth">
                <table class="pageList ">
                    <tbody>
                        <tr>
                            <td>
                                <div>
                                    <a class="prev" href="">&lt;</a>
                                    <a href="">1</a>
                                    <span>2</span>
                                    <a href="">3</a>
                                    <a href="">4</a>
                                    <a href="">5</a>
                                    <a class="next" href="">&gt;</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>--%>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script src="${ctx}/js/pc/layer/layer.js"></script>
    <script>
        $(function () {
            //产品部分加入购物车
            var flag = ${not empty member};
            var  clicked= false;
            $(".add-cart").click(function () {
                var id = $(this).data("id");
                var oid = Number($(this).attr("oId"));
                debugger;
                var url="";
                if(flag){
                    url="${ctx}/cart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom=2&otherId=" + oid;
                }else{
                    url="${ctx}/cookieCart/saveAsync?goodsId="+id+"&buyCount=1&goodsFrom=2&otherId=" + oid;
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
    </script>
</body>
</html>
