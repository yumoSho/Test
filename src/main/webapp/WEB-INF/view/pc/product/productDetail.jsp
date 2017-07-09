﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<c:set var="com.glanway.jty.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>产品详情页-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <meta name="title" content="${goods.product.seoTitle}">
    <meta name="keywords" content="${goods.product.seoKeyword}">
    <meta name="description" content="${goods.product.seoDescription}">
    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/product.css" rel="stylesheet" />

    <style>
        .hotPro {
            position: absolute;
            top: 0;
            right: 0px;
        }
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %><%--head--%>
        <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>商品详情</span>
                </div>
            </div>
            <!-- 顶部产品信息部分 -->
            <div class="detailTop clearfix">
                <div class="topLeft fl">
                    <div class="box">
                        <div class="tb-booth tb-pic tb-s310">
                            <a href="javascript:void(0);"><img src="${ctx}/${goods.image}" rel="${ctx}/${goods.image}" alt="产品大图" class="jqzoom" /></a>
                            <c:forEach items="${labels}" var="label">
                                <c:if test="${label.id eq goods.product.label.id}">
                                    <img src="${ctx}/${label.labelPath}" style="width: 70px;height: 58px" class="hotPro" />
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="imaWrap">
                            <ul class="tb-thumb" id="thumblist">
                                <c:forEach items="${goods.productImgs}" var="img" varStatus="index">
                                <li <c:if test="${index.index eq 0}"> class="tb-selected" </c:if> ><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${ctx}/${img.path}" mid="${ctx}/${img.path}" big="${ctx}/${img.path}" width="62" height="67"></a></div></li>
                                </c:forEach>
                                <%--<li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${ctx}/images/pc/showImg3.jpg" mid="${ctx}/images/pc/showImg1.jpg" big="${ctx}/images/pc/showImg1.jpg"></a></div></li>
                                <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${ctx}/images/pc/showImg2.jpg" mid="${ctx}/images/pc/showImg2.jpg" big="${ctx}/images/pc/showImg2.jpg"></a></div></li>
                                <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${ctx}/images/pc/showImg3.jpg" mid="${ctx}/images/pc/showImg1.jpg" big="${ctx}/images/pc/showImg1.jpg"></a></div></li>--%>
                            </ul>
                            <a href="javascript:void(0);" class="prev arrow-btn"><img src="${ctx}/images/pc/detaileft.png" width="10" height="17" /></a>
                            <a href="javascript:void(0);" class="next arrow-btn"><img src="${ctx}/images/pc/detailRight.png" width="10" height="17" /></a>
                        </div>
                    </div>
                    <div class="sharePart clearfix">
                        <div class="proNum fl">商品编号：<span>${goods.sn}</span></div>
                        <div class="bdsharebuttonbox share fl clearfix">
                            <span class="fl">分享:</span>
                            <div class="fl">
                                <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                                <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                                <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                            </div>
                        </div>
                        <script>window._bd_share_config = { "common": { "bdSnsKey": {}, "bdText": "", "bdMini": "2", "bdMiniList": false, "bdPic": "", "bdStyle": "0", "bdSize": "24" }, "share": {} }; with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>
                        <div class="proCollect fr" data-id="${goods.id}" style="color: green">收藏</div>
                    </div>
                </div>
                <div class="topRight fr">
                    <div class="rightTop clearfix">
                        <div class="leftName fl">
                            <p class="color666">
                                ${goods.title}
                            </p>
                            <span class="color666">${goods.product.intro}</span>
                        </div>
                        <div class="rightEval color666 fr">
                            <p>评分</p>
                            <div class="evalImg">
                                <c:forEach begin="1" end="${score}" >
                                <img src="${ctx}/images/pc/icoStarY.png" width="16" height="16" />
                                </c:forEach>
                                <c:forEach begin="1" end="${5-score}">
                                    <img src="${ctx}/images/pc/icoStarG.png" width="16" height="16" />
                                </c:forEach>
                            </div>
                            <p>销量：<span>${goods.product.sales}</span></p>
                        </div>
                    </div>
                    <div class="centerSlect">
                        <table class="proMsgTable sku-meta" >
                            <tr>
                                <td width="76">价&nbsp;&nbsp;格：</td>
                                <td width="520"><div class="price">￥<span><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/></span></div></td>
                            </tr>
                            <c:if test="${not empty goods.product.promotionalInfo}">
                            <tr>
                                <td colspan="2" class="saleMsg">促销信息：<span>${goods.product.promotionalInfo}</span></td>
                            </tr>
                            </c:if>
                            <tr>
                                <td>配送至：</td>
                                <td class="goodsAddr">
                                    <select id="addr">
                                        <c:forEach items="${goods.product.areas}" var="h">
                                        <option value="${h.id}">${h.provinceName}</option>
                                        </c:forEach><%--
                                        <option>西安</option>
                                        <option>苏州</option>--%>
                                    </select>
                                    <span>满100元<%--或购买5种以上商品--%>包邮</span>
                                </td>
                            </tr>
                            <c:if test="${not empty goods.product.service}">
                            <tr>
                                <td>服&nbsp;&nbsp;务：</td>
                                <td class="fastSend">
                                    ${goods.product.service}
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fn:length(specAndValues)>0 }">
                                <c:forEach items="${specAndValues}" var="sv">
                                    <tr>
                                        <td>${sv.name}：</td>
                                        <td class="colorChuice"  data-property="${sv.name}">
                                            <c:forEach items="${sv.specValues}" var="v">
                                            <a href="javascript:void(0);" data-value="${sv.id}:${v.id}">${v.name}</a>
                                            </c:forEach>
                                            <%--<a href="javascript:void(0);">白色</a>
                                            <a href="javascript:void(0);">蓝色</a>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <tr>
                                <td>数&nbsp;&nbsp;量：</td>
                                <td class="clearfix">
                                    <div class="proNMumPart fl">
                                        <input class="numInput" type="text" value="1" stock="${goods.stock}" />
                                        <a href="javascript:void(0);" class="numberAdd"><img src="${ctx}/images/pc/upBtn.png" width="7" height="4" /></a>
                                        <a href="javascript:void(0);" class="numberdecrice"><img src="${ctx}/images/pc/bottomBtn.png" width="7" height="4" /></a>
                                    </div>
                                    <span class="fl nowGoods">（当前库存：<span>${goods.stock}</span>件）</span>
                                </td>
                            </tr>
                        </table>
                        <c:if test="${not empty goods.product.tip}">
                        <div class="buyNotice color666">
                            <p>温馨提示：</p>
                            ${goods.product.tip}
                        </div>
                        </c:if>
                        <div class="proForCart">
                            <a href="javascript:void(0);">加入购物车</a>
                            <a href="javascript:void(0);" >立即购买</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 中部搭配切换部分 -->
            <c:if test="${fn:length(accessoy) > 0 or fn:length(packages) > 0}">
            <div class="centerTab">
                <div class="tabBtn">
                    <c:choose>
                        <c:when test="${fn:length(accessoy)>0 and fn:length(packages)>0 }">
                            <a href="javascript:void(0);" class="on" onmousemove="click(this)">推荐搭配</a>
                            <a href="javascript:void(0);" onmousemove="click(this)">优惠套餐</a>
                        </c:when>
                        <c:when test="${fn:length(accessoy)>0 and fn:length(packages) eq 0 }">
                            <a href="javascript:void(0);" class="on" onmousemove="click(this)">推荐搭配</a>
                        </c:when>
                        <c:when test="${fn:length(accessoy)eq 0 and fn:length(packages) > 0 }">
                            <a href="javascript:void(0);" class="on" onmousemove="click(this)">优惠套餐</a>
                        </c:when>
                    </c:choose>
                </div>
                <div class="tabContent">
                    <c:if test="${fn:length(accessoy)>0}">
                    <div class="forTabContent content1 clearfix">
                        <div class="leftPro fl clearfix">
                            <a href="${ctx}/detail/${goods.id}" class="fl">
                                <img src="${ctx}/${goods.image}" width="147" height="162" />
                                <p>
                                    ${goods.title}
                                </p>
                            </a>
                            <span class="addImg fl">
                                <img src="${ctx}/images/pc/add.png" />
                            </span>
                        </div>
                        <div class="centerProWrap fl ">
                            <div class="proInner clearfix">
                                <c:forEach items="${accessoy}" var="a">
                                    <c:forEach items="${a.packageDetails}" var="d">
                                <div class="centerProItem clearfix g-${d.goods.id}-pro">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                    <div class="leftPro fl g-${d.goods.id}-left" style="position: relative">
                                        <a href="${ctx}/detail/${d.goods.id}">
                                            <img src="${ctx}/${d.goods.image}" width="147" height="162" />
                                            <p>${d.goods.title}</p>
                                        </a>
                                        <label><input type="checkbox" data-id="${d.goods.id}" class="g-${d.goods.id}-check" data-price="<fmt:formatNumber value="${d.goods.promotePrice}" type="currency" pattern="0.00"/>" /><span>￥<span><fmt:formatNumber value="${d.goods.promotePrice}" type="currency" pattern="0.00"/></span></span></label>
                                    </div>
                                </div>
                                    </c:forEach>
                                </c:forEach>
                                <%--<div class="centerProItem clearfix">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                    <div class="leftPro fl">
                                        <a href="productDetail.html">
                                            <img src="${ctx}/images/pc/partImg1.jpg" width="147" height="162" />
                                            <p>杭白菜，青白爽口 清新肠胃；杭白菜</p>
                                        </a>
                                        <label><input type="checkbox" /><span>￥<span>12.3</span></span></label>
                                    </div>
                                </div>
                                <div class="centerProItem clearfix">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                    <div class="leftPro fl">
                                        <a href="productDetail.html">
                                            <img src="${ctx}/images/pc/partImg2.jpg" width="147" height="162" />
                                            <p>杭白菜，青白爽口 清新肠胃；杭白菜</p>
                                        </a>
                                        <label><input type="checkbox" /><span>￥<span>12.3</span></span></label>
                                    </div>
                                </div>
                                <div class="centerProItem clearfix">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                    <div class="leftPro fl">
                                        <a href="productDetail.html">
                                            <img src="${ctx}/images/pc/partImg3.jpg" width="147" height="162" />
                                            <p>杭白菜，青白爽口 清新肠胃；杭白菜</p>
                                        </a>
                                        <label><input type="checkbox" /><span>￥<span>12.3</span></span></label>
                                    </div>
                                </div>
                                <div class="centerProItem clearfix">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                    <div class="leftPro fl">
                                        <a href="productDetail.html">
                                            <img src="${ctx}/images/pc/partImg2.jpg" width="147" height="162" />
                                            <p>杭白菜，青白爽口 清新肠胃；杭白菜</p>
                                        </a>
                                        <label><input type="checkbox" /><span>￥<span>12.3</span></span></label>
                                    </div>
                                </div>--%>
                            </div>
                        </div>
                        <div class="rightPrice fr clearfix">
                            <span class="addImg fl"><img src="${ctx}/images/pc/dengyu.png" /></span>
                            <div class="payNum fl">
                                <p class="hadChecked">已选择<span>0</span>个配件</p>
                                <p class="partPrice">搭配价：<span>￥<span><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/></span></span></p>
                                <a href="javascript:void(0);" class="buyNow dis">立即购买</a>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${fn:length(packages)>0}">
                    <div class="forTabContent content2 clearfix">
                        <div class="salePartTab clearfix">
                            <c:forEach items="${packages}" var="p" varStatus="index">
                            <a href="javascript:void(0);" <c:if test="${index.index eq 0}"> class="on" </c:if>>${p.name}</a>
                            </c:forEach>
                        </div>
                        <div class="partTabWrap">
                            <c:forEach items="${packages}" var="p">
                            <div class="tabItem">
                                <div class="leftPro fl clearfix">
                                    <a href="${ctx}/detail/${goods.id}" class="fl">
                                        <img src="${ctx}/${goods.image}"  width="147" height="162"/>
                                        <p>
                                            ${goods.title}
                                        </p>
                                    </a>
                                    <span class="addImg fl">
                                        <img src="${ctx}/images/pc/add.png" />
                                    </span>
                                </div>
                                <div class="centerProWrap fl ">
                                    <div class="proInner clearfix">
                                        <c:forEach items="${p.packageDetails}" var="pd" >
                                        <div class="centerProItem clearfix">
                                            <span class="addImg fl"><img src="${ctx}/images/pc/add.png" /></span>
                                            <div class="leftPro fl pa_${pd.goods.id}_left" style="position: relative">
                                                <a href="${ctx}/detail/${pd.goods.id}">
                                                    <img src="${ctx}/${pd.goods.image}" width="147" height="162" />
                                                    <p>${pd.goods.title}</p>
                                                </a>
                                            </div>
                                        </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="rightPrice fr clearfix">
                                    <span class="addImg fl"><img src="${ctx}/images/pc/dengyu.png" /></span>
                                    <div class="payNum fl">
                                        <p class="hadChecked">优惠套餐价</p>
                                        <s>原价：<span><fmt:formatNumber value="${p.originalPrice}" type="currency" pattern="0.00"/></span></s>
                                        <p class="partPrice">套餐价：<span>￥<span><fmt:formatNumber value="${p.saveMoney}" type="currency" pattern="0.00"/></span></span></p>
                                        <c:set var="pp" value="${p.originalPrice-p.saveMoney}"/>
                                        <p class="partPrice">立即节约：<span>￥<span><fmt:formatNumber value="${pp}" type="currency" pattern="0.00"/></span></span></p>
                                        <a href="javascript:void(0);" onclick="buyPackage(this,'${p.id}')" class="buyNow">立即购买</a>
                                    </div>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>
                    </c:if>
                </div>
            </div>
            </c:if>
            <!-- 底部信息展示 -->
            <div class="bottomProMsg clearfix">
                <!-- 左侧人气单品 -->
                <c:if test="${fn:length(hot)>0}">
                <div class="leftShow fl">
                    <p class="leftTitle color666">人气单品</p>
                    <div class="leftProList" style="position: relative">
                        <c:forEach items="${hot}" var="h">
                        <div class="leftProItem clearfix">
                            <a href="${ctx}/detail/${h.id}">
                                <img src="${ctx}/${h.image}" width="285" height="312" />
                                <p class="color666 padding10">
                                    ${h.title}
                                </p>
                            </a>
                            <c:forEach items="${labels}" var="label">
                                <c:if test="${label.id eq h.product.label.id}">
                                    <img src="${ctx}/${label.labelPath}" style="width: 50px;height: 42px" class="hotPro" />
                                </c:if>
                            </c:forEach>
                            <span class="subTitle padding10">${h.intro}</span>
                            <a href="javascript:void(0);" class="fr  add-cart" data-id="${h.id}"><img src="${ctx}/images/pc/proCart.jpg" width="35"></a>
                            <div class="leftProPrice fl">
                                ￥<span><fmt:formatNumber value="${h.promotePrice}" type="currency" pattern="0.00"/></span>
                            </div>
                            <div class="fr rightPrice color666">销量<span style="color:red;font-weight: 600"> ${h.product.sales}&nbsp;</span>件</div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <!--右侧产品切换 -->
                <div class="rightMsgTab fr">
                    <div class="bottomRightTab">
                        <a href="javascript:void(0);" class="on">商品信息</a>
                        <a href="javascript:void(0);">商品评价</a>
                        <a href="javascript:void(0);">售后保障</a>
                    </div>
                    <div class="proTabMain">
                        <!--商品信息-->
                        <div class="forTab">
                            ${goods.product.detail}
                        </div>
                        <!--商品评价-->
                        <div class="forTab">
                            <c:if test="${fn:length(comments.data) >0 }" >
                            <table class="proEvalTable">
                                <tr style="background-color: #fafafa;">
                                    <th width="300">评价</th>
                                    <th width="100">顾客满意度</th>
                                    <th width="100">用户名</th>
                                    <th width="100">评价时间</th>
                                </tr>
                                <c:forEach items="${comments.data}" var="comment">
                                <tr>
                                    <td>
                                        <p>${comment.content}</p>
                                        <c:if test="${not empty comment.photos}">
                                            <c:set var="photo" value="${fn:split(comment.photos ,',')}"/>
                                            <c:forEach items="${photo}" var="p">
                                                <a href="javascript:void(0);"><img src="${ctx}/${p}" width="64" height="69" /></a>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                    <td>

                                        <img src="${ctx}/images/pc/icon22<c:if test="${comment.grade ne 5}">${comment.grade}</c:if>.png" width="116" height="19" />

                                    </td>
                                    <td><span class="userName">${comment.member.memberName}</span></td>
                                    <td><span class="evalTime"><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.commentTime}" type="both"/></span></td>
                                </tr>
                                </c:forEach>

                            </table>
                                <form id="pagination-form" class="pagination-form">
                                    <m:pagination totalPages="${comments.totalPages}" pageParam="page" skip="false"/>
                                </form>
                            </c:if>
                            <c:if test="${ empty comments.data}">
                                <div class="proEvalNull">
                                    商品还没有评价记录！
                                </div>
                            </c:if>
                        </div>
                        <!--售后保障-->
                        <div class="forTab">
                            <div style="font-size:12px; color:#666; line-height:25px;">
                                ${dictionary_SHBZ.content}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script src="${ctx}/js/pc/jquery.imagezoom.js"></script>
    <script src="${ctx}/js/pc/Tab.js"></script>
    <script src="${ctx}/js/jquery.slides.min.js"></script>
    <script src="${ctx}/js/pc/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/js/sku.js"></script>
    <script>
        var accessory =<c:if test="${empty accessoryJson}">[]</c:if><c:if test="${not empty accessoryJson}">${accessoryJson}</c:if>;
        var packages =<c:if test="${empty packagesJson}">[]</c:if><c:if test="${not empty packagesJson}">${packagesJson}</c:if>;
        var rendered = false;
        var _sku = SKU({
            container: $('.sku-meta')[0],
            skuMap: ${sv},
            defaultSku: '${goods.svStr}',
            allowUnselect: false,
            updateSku: function (sku, $el) {
                if (!sku || isEmptyObject(sku)) {
                    return;
                }
                setTimeout(function() {
                    var $selected = $('a[data-value].on').eq(0);
                    if (!rendered && 0 < $selected.length) {
                        rendered = true;
                        $selected.triggerHandler('click');
                    }
                }, 0);


                if(sku.id && sku.id != '${goods.id}') {
                    var target="${ctx}/detail/"+ sku.id+"?goodsFrom=${gf}&oid=${oid}";
                    window.location.href=target;
                }
            }
        });
        var addr =$("#addr").val();
        var goodsFrom ="${goodsFrom}";
        var oid = "${oid}";
        var clicked =false;
        var flag = ${not empty member};
        $(".proForCart a").eq(0).click(function(){
            var num = parseInt($(".numInput").val());
            var url="";
            if(flag){
                url="${ctx}/cart/saveAsync?goodsId=${goods.id}&buyCount="+num+"&goodsFrom="+goodsFrom+"&otherId="+oid;
            }else{
                url="${ctx}/cookieCart/saveAsync?goodsId=${goods.id}&buyCount="+num+"&goodsFrom="+goodsFrom+"&otherId="+oid;
            }
            if(clicked){
                return false;
            }
            clicked =true;

            $.ajax({
                type: "get",
                url: url
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
        });
        <%---------------立即购买-------------------%>
        $(".proForCart a").eq(1).click(function(){
            var num = parseInt($(".numInput").val());
            window.location.href = "${ctx}/order/add?goodsId=${goods.id}&count="+num+"&goodsFrom="+goodsFrom+"&otherId="+oid;
        });

        function isEmptyObject(e) {
            var t;
            for (t in e)
                return !1;
            return !0
        }

        function buyPackage(o,packageId){
            var $this = $(o);
            if($this.hasClass("dis")){
                return false;
            }else{
                this.location.href="${ctx}/order/addByTaoCan?packageId="+packageId;
            }
        }
        $(function () {
            $.ajax({
                type: "POST",
                url: '${ctx}/person-center/check-collected',
                data: "goodsid=${goods.id}&goodsFrom=${gf}&otherId=${oid}",
            }).done(function (data) {
                if(data.success){
                    $(".sharePart .proCollect").text("已收藏")
                    $(".sharePart .proCollect").addClass("on")
                }
            }).fail(function (data) {
                layer.msg('收藏获取失败',{ time: 1500}, function(){});
            })


            //套餐切换
            new GlanwayTab(".salePartTab a", ".partTabWrap .tabItem");
            //购物车增加：
            var addBtn = $(".numberAdd");
            var minusBtn = $(".numberdecrice");
            var input = $(".numInput");
            addBtn.click(function () {
                var val = parseInt(input.val());
                var addVal = val + 1;
                var stock = parseInt(input.attr("stock"));
                if(addVal>stock){
                    input.val(stock.toString());
                }else{
                    input.val(addVal.toString());
                }
            });
            minusBtn.click(function () {
                var val = parseInt(input.val());
                var addVal = val - 1;
                if (addVal >= 1) {
                    input.val(addVal.toString());
                }
                else {
                    input.val("1")
                }
            });

            //购物车输入框校验
            $(".numInput").blur(function () {
                var val = $.trim($(this).val());
                if (!val.isPositiveNum()) {
                    $(this).val(1);
                }
                var val = parseInt(val);
                var stock = parseInt($(this).attr("stock"))
                if(val>stock){
                    $(this).val(stock);
                }
            });
            String.prototype.isPositiveNum = function () {
                return (/^[0-9]*[1-9][0-9]*$/.test(this));
            }
            <%-- 配件--%>
            $(".content1 .rightPrice .buyNow").click(function(j,me){
                if($(this).hasClass("dis")){
                    return false;
                }else{
                    var num = parseInt($(".numInput").val());
                    var url="${ctx}/order/addByPeiJian?"
                    var $content1 = $(".content1 input[type='checkbox']:checked");

                    url+="goodsId=${goods.id}&buyCount="+num+"&goodsFrom=${gf}&otherId=${oid}";
                    $.each($content1,function(c,m){
                        var aid = $(this).data("id");
                        url+="&goodsId="+aid+"&count=1"
                    });
                    location.href=url;
                }
            });
        });
        function isMore() {
            var otherNum = parseInt($(".proMsgTable .nowGoods span").text());
            var inputText = $(".proNMumPart input").val();
            if (inputText > otherNum) {
                layer.msg("库存不够了亲！");
                return false;
            }
        }
        var thisInput = 0;
        $(".tabContent .centerProWrap .proInner").each(function () {
            $(this).find(".centerProItem label input").change(function () {
                var $this = $(this);
                thisInput = $this.parents(".proInner").find("input:checked").size();
                if (thisInput > 0) {
                    $(".rightPrice .hadChecked span").text(thisInput);
                    var priceCountCheck =  $this.parents(".proInner").find("input:checked");
                    var priceSum=<fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/>;
                    $.each(priceCountCheck,function(i,e){
                       priceSum  = parseFloat(priceSum) + parseFloat($(this).data("price"));
                    });
                    $(".rightPrice .partPrice span").text(priceSum.toFixed(2));
                    $(".content1 .rightPrice .buyNow").removeClass("dis");
                } else {
                    $(".content1 .rightPrice .buyNow").addClass("dis")
                    $(".rightPrice .hadChecked span").text(0);
                    $(".rightPrice .partPrice span").text("<fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/>");
                }
            });
        });


        var popHtml=['<div class="mask pro-mask" style="',
            '    background-color: #000;',
            '    width: 148px;',
            '    height: 249px;',
            '    position: absolute;',
            '    opacity: 0.5;',
            '    -moz-opacity: 0.5;',
            '    filter:alpha(opacity=50);',
            '                           "></div>',
            '						   <div class="pro-mask" style="',
            '    position: absolute;',
            '    font-size: 24px;',
            '    text-align: center;',
            '    vertical-align: middle;',
            '    width: 148px;',
            '    height: 249px;',
            '    margin: 0 0;',
            '            "><span style="',
            '    vertical-align: middle;',
            '    line-height: 249px; ',
            '    color: #fff; ',
            '">不支持本地区</span></div>'].join("");
        /*checkAccessory();*/
        $("#addr").change(function(){
            addr =$(this).val()
            /*checkAccessory();
            checkPackage();*/
            /*$.each($(".tabItem"),function(){
                var len = $(this).eq(0).find(".pro-mask").length;
                if(len>0){
                    $(this).eq(0).find(".buyNow").addClass("dis")
                }else{
                    $(this).eq(0).find(".buyNow").removeClass("dis")
                }
            });*/
        })
        /*function checkAccessory(){
            $.each(accessory,function(i,e){
                $.each(e.packageDetails,function(j,me){
                    var areas = me.goods.product.areas;
                    var flag =false;
                    $.each(areas,function(k,p){
                        if(p.id == parseInt(addr)){
                            console.log(e,me, p.id)
                            flag =true;
                            return false;
                        }
                    });
                    var gid =me.goods.id;
                    var $gpro = $(".g-"+gid+"-pro");
                    if(flag){
                        $.each($gpro,function(m,c){
                            var $proMask = $(this).find(".pro-mask");
                            $proMask.remove();
                        });
                    }else{
                        console.log("false"+me.goods.id)
                        $.each($gpro,function(n,o){
                            var $proMask = $(this).find(".pro-mask");
                            if($proMask.length ==0){
                                var ckeckPrice = $(".g-"+gid+"-check");
                                if(ckeckPrice.is(":checked")){
                                    ckeckPrice.click();
                                }
                                var leftgid = $gpro.find(".g-"+gid+"-left")
                                leftgid.prepend(popHtml);
                            }
                        });
                    }
                })

            })

        }*/

        /*checkPackage();
        function checkPackage(){
            $.each(packages ,function(i,e){
                $.each(e.packageDetails,function(j,me){
                    var areas = me.goods.product.areas;
                    var flag =false;
                    $.each(areas,function(k,p){
                        if(p.id == parseInt(addr)){
                            flag =true;
                            return false;
                        }
                    });
                    var gid =me.goods.id;
                    var $gpro = $(".pa_"+gid+"_left");
                    if(flag){
                        $.each($gpro,function(m,c){
                            var $proMask = $(this).find(".pro-mask");
                            $proMask.remove();
                        });
                    }else{
                        $.each($gpro,function(n,o){
                            var $proMask = $(this).find(".pro-mask");
                            if($proMask.length ==0){
                                $(this).prepend(popHtml);
                            }
                        });
                    }
                })

            })
        }*/
$(function(){
    //产品部分加入购物车
    var flag = ${not empty member};
    var  clicked= false;
    $(".add-cart").click(function () {
        var id = $(this).data("id");
        var url="";
        if(flag){
            url="${ctx}/cart/saveAsync?goodsId="+id+"&buyCount=1";
        }else{
            url="${ctx}/cookieCart/saveAsync?goodsId="+id+"&buyCount=1";
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
    <script src="${ctx}/js/pc/productDetail.js"></script>
</body>
</html>
