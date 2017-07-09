<!DOCTYPE html>
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
    <title>产品详情页</title>
    <meta name="title" content="${goods.product.seoTitle}">
    <meta name="keywords" content="${goods.product.seoKeyword}">
    <meta name="description" content="${goods.product.seoDescription}">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/product.css" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js"></script>
    <style>
        .dis{
            background: #eee;
        }

        .mask {
            position: absolute;
            width: 100%;
            top: 0px;
            left: 0px;
            background: rgba(0,0,0,0.8);
            z-index: 1001;
            display: none;
        }
        .tips {
            position: absolute;
            width: 100%;
            top: 0px;
            left: 0px;
            z-index: 51;
            color: #fff;
            line-height: 35px;
            background: url(${ctx}/images/mobile/here.png) top right no-repeat;
            background-size: 80px;
            display: none;
            z-index: 1001;
        }
        .tips dl {
            text-align: center;
            margin-top: 10%;
            font-size: 0.2rem;
        }
        .forTab p{
            font-size:.26rem;color:#666; line-height:.4rem;
        }
    </style>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <div class="detailHead clearfix">
            <div class="harfOpcity"></div>
            <a href="javascript:history.go(-1);" class="detailBack fleft"><img src="${ctx}/images/mobile/icon17.png" /></a>
            <div class="detailHandel fright">
                <%--<a href="${ctx}/cart" class="detailCart"><img src="${ctx}/images/mobile/icon18.png" /></a>--%>
                    <a href="javascript:void(0);" class="detailCart"></a>
                <a href="javascript:void(0);" class="detailManu"><img src="${ctx}/images/mobile/icon19.png" /></a>
                <div class="handelList">
                   <%-- <a href="${ctx}/mobile/person-center/collected" class="collect"><img src="${ctx}/images/mobile/icon20.png" /></a>--%>
                    <a href="javascript:void(0);" class="collect"><img src="${ctx}/images/mobile/icon20.png" /></a>
                    <a href="javascript:void(0);" class="share"><img src="${ctx}/images/mobile/icon21.png" /></a>
                </div>
            </div>
        </div><!-- //head -->
        <div class="main writeWrap">
            <div class="proDetailBanner">
                <c:forEach items="${goods.productImgs}" var="img" varStatus="index">
                <a style="background:url(${ctx}/${img.path}) center no-repeat;background-size:100% 100%; width:7.5rem;height:8.83rem; display: block;"></a>
                </c:forEach>
            </div>
            <div class="detailProName samePadding">
                ${goods.title}
            </div>
            <div class="detailPrice samePadding">
                <span>售&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 价：<span class="price">￥<span><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/></span></span></span>
                <span style="float: right;">商品编号：<span>${goods.sn}</span></span>
                <c:if test="${not empty goods.product.promotionalInfo}">
                <a href="javaScript:;" class="discount clearfix"><p class="fleft">促销信息：</p><p class="fright">${goods.product.promotionalInfo}</p></a>
                </c:if>
            </div>
            <!--配送信息-->
            <div class="delivery samePadding">
                <span>配送至：</span>
                <div class="goodsAddr">
                    <img src="${ctx}/images/mobile/addrIcon.png" class="fleft" />
                    <select class="fleft">
                        <c:forEach items="${goods.product.areas}" var="h">
                            <option value="${h.id}">${h.provinceName}</option>
                        </c:forEach>
                    </select>
                    <img src="${ctx}/images/mobile/addrChoice.png" class="addrChoice fright" />
                </div>
                <p>满100元包邮</p>
            </div>
            <!--服务-->
            <c:if test="${not empty goods.product.service}">
            <div class="servece samePadding">
                <p class="fleft">服&nbsp;&nbsp;&nbsp;务：</p>
                <p class="fleft">
                    ${goods.product.service}
                </p>
            </div>
            </c:if>
            <!--推荐配件-->
            <c:if test="${fn:length(accessoy) > 0 or fn:length(packages) > 0}">
            <div class="recommend writeWrap wrap">
                <div class="combo">
                    <c:choose>
                        <c:when test="${fn:length(accessoy)>0 and fn:length(packages)>0 }">
                            <a href="javascript:void(0);" class="on">推荐配件</a>
                            <a href="javascript:void(0);">优惠套餐</a>
                        </c:when>
                        <c:when test="${fn:length(accessoy)>0 and fn:length(packages) eq 0 }">
                            <a href="javascript:void(0);" class="on">推荐配件</a>
                        </c:when>
                        <c:when test="${fn:length(accessoy)eq 0 and fn:length(packages) > 0 }">
                            <a href="javascript:void(0);" class="on">优惠套餐</a>
                        </c:when>
                    </c:choose>
                </div>
                <div class="recommendContentBox">
                    <c:if test="${fn:length(accessoy)>0}">
                    <div class="recommendContent" id="pjContent">
                        <div class="recommendSlide samePadding">
                        <c:set var="c" value="0"/>
                        <c:forEach items="${accessoy}" var="a" varStatus="p">
                            <c:forEach items="${a.packageDetails}" var="d" varStatus="w">
                                <c:set var="c" value="${c+1}"/>
                                <c:if test="${c eq 1 or c%6 eq 1}">
                                <div>
                                </c:if>
                                <div class="leftPro g-${d.goods.id}-pro">
                                    <a href="${ctx}/mobile/detail/${d.goods.id}">
                                        <img src="${ctx}/${d.goods.image}" width="147" height="162" />
                                        <p>${d.goods.title}</p>
                                    </a>
                                    <label><input type="checkbox" data-id="${d.goods.id}" data-price="<fmt:formatNumber value="${d.goods.promotePrice}" type="currency" pattern="0.00"/>"  /><span>￥<span><fmt:formatNumber value="${d.goods.promotePrice}" type="currency" pattern="0.00"/></span></span></label>
                                </div>

                            <c:if test="${c eq 6 or c%6 eq 0 or (w.last and p.last)}">
                            </div>
                            </c:if>
                            </c:forEach>
                        </c:forEach>
                        </div>
                        <div class="choiceCount samePadding clear">
                            <span class="fleft">已选择</span>
                            <span class="fright"><b id="pjCount">${0}</b>个配件</span>
                        </div>
                        <div class="evalPrice samePadding">
                            <span class="fleft">搭配价</span>
                            <span class="fright">￥<span id="pjPrice"><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/></span></span>
                        </div>
                        <a href="javascript:void(0);" id="pjbuy" class="saleNow font24 fright sameMargin dis">立即购买</a>
                    </div>
                    </c:if>
                    <c:if test="${fn:length(packages)>0}">
                    <div class="recommendContent">
                        <ul class="suitNav samePadding font24">
                            <c:forEach items="${packages}" var="p" varStatus="index">
                            <li>
                                <a href="javascript:void(0);" <c:if test="${index.index eq 0}"> class="on" </c:if>>${p.name}</a>
                            </li>
                            </c:forEach>
                        </ul>
                        <div class="suitBox samePadding font24">
                        </div>
                        <p class="suitPriceTitle samePadding font24 clear">优惠套餐价</p>
                        <div class="selfPrice samePadding clear">
                            <span class="fleft">原价</span>
                            <span class="fright" >￥<span id="oprice">0.00</span></span>
                        </div>
                        <div class="suitPrice samePadding">
                            <span class="fleft">套餐价</span>
                            <span class="fright">￥<span id="nprice">0.00</span></span>
                        </div>
                        <div class="savePrice samePadding">
                            <span class="fleft">立即节约</span>
                            <span class="fright" >￥<span id="sprice">0.00</span></span>
                        </div>
                        <a href="javascript:void(0)" id="pid" pid="" class="saleNow font24 fright sameMargin">立即购买</a>
                    </div>
                    </c:if>
                </div>
            </div>
            </c:if>
            <div class="proDetail">
                <div class="detailBtns">
                    <a href="javascript:void(0);" class="on">商品介绍</a>
                    <a href="javascript:void(0);">商品评价</a>
                    <a href="javascript:void(0);">售后保障</a>
                </div>
                <div class="tabContent">
                    <!--商品介绍-->
                    <div class="forTab">
                        ${goods.product.mobileDetail}
                    </div>
                    <!--商品评价-->
                    <div class="forTab samePadding">
                        <!--为空提示-->
                        <c:if test="${fn:length(comments.data) eq 0}">
                            <div class="nullNotice">此商品暂时没有评价！</div>
                        </c:if>
                        <c:if test="${fn:length(comments.data)>0}">
                        <div class="pinterest">
                            <c:forEach items="${comments.data}" var="comment">
                            <div class="evalList">
                                <img src="${ctx}/images/pc/icon22<c:if test="${comment.grade ne 5}">${comment.grade}</c:if>.png"  />
                                <p class="evalText">${comment.content}</p>
                                <c:if test="${not empty comment.photos}">
                                <div class="evalImgs">
                                    <c:set var="photo" value="${fn:split(comment.photos ,',')}"/>
                                    <c:forEach items="${photo}" var="p">
                                        <a href="javascript:void(0);"><img src="${ctx}/${p}" /></a>
                                    </c:forEach>
                                </div>
                                </c:if>
                                <div class="userEval"><span class="userName">${comment.member.memberName}</span> | <span class="orderTime"><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.commentTime}" type="both"/></span></div>
                            </div>
                            </c:forEach>
                        </div>
                        </c:if>
                    </div>
                    <!--售后保障-->
                    <div class="forTab samePadding">
                        <div style="font-size:12px; color:#666; line-height:25px;padding:.2rem">
                            <p>
                                本产品全国联保，享受三包服务，质保期为：一年质保<br />
                                本产品提供上门安装调试、提供上门检测和维修等售后服务，自收到商品之日起，如您所购买家电商品出现质量问题，请先联系厂家进行检测，凭厂商提供的故障检测证明，在“客户服务-返修退换货”页面提交退换申请，将有专业售后人员提供服务。我们承诺您：30天内产品出现质量问题可退货，180天内产品出现质量问题可换货，超过180天按国家三包规定享受服务。
                                您可以查询本品牌在各地售后服务中心的联系方式，请点击这儿查询......
                                <br />
                                <br />
                                品牌官方网站：http://shop.letv.com/ <br />
                                售后服务电话：400-900-9000
                            </p>
                            <img src="${ctx}/images/mobile/bottomIcon1.jpg" style="width:1.57rem;margin-top:40px; margin-bottom:5px;" />
                            <div style="font-size:12px; color:#666; line-height:25px;">
                                本产品全国联保，享受三包服务，质保期为：一年质保本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。
                            </div>
                            <img src="${ctx}/images/mobile/bottomIcon2.jpg" style="width:1.57rem;margin-top:40px; margin-bottom:5px;" /><br />
                            <div style="font-size:12px; color:#666; line-height:25px;">
                                本产品全国联保，享受三包服务，质保期为：一年质保本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <!--加载更多-->
            <div class="load">正在加载...</div>
            <!--人气单品-->
            <c:if test="${fn:length(hot)>0}">
            <p class="proHot samePadding">人气单品</p>
            <div class="indexCardSlide samePadding">
                <c:forEach items="${hot}" var="h" varStatus="j">
                    <c:if test="${j.index eq 0 or j.index%2 eq 0}">
                        <div class="productList sliderItem">
                    </c:if>
                    <div class="proItem fleft">
                        <a href="${ctx}/mobile/detail/${h.id}" class="proImg"><img src="${ctx}/${h.image}" /></a>
                        <div class="proMessage">
                            <p class="proPrice">￥<span><fmt:formatNumber value="${h.promotePrice}" type="currency" pattern="0.00"/></span></p>
                            <a href="${ctx}/mobile/detail/${h.id}" class="proName overflow">${h.title}</a>
                        </div>
                    </div>
                    <c:if test="${j.index%2 eq 1 or j.last}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            </c:if>
        </div><!-- //main -->
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        <!--加入购物车操作-->
        <div class="prohandel samePadding clearfix">
            <a href="javascript:void(0);" class="forCollect">
                <img src="${ctx}/images/mobile/icon24.png" />
                <p>收藏</p>
            </a>
            <a href="javascript:void(0);" class="forCart" id="addCart" g-id="${goods.id}">加入购物车</a>
            <a href="javascript:void(0);" class="buyNow"  id="buyNow" g-id="${goods.id}">立即购买</a>
        </div>
        <!--分享-->
        <div class="detailShare">
            <div class="shareInner">
                <div class="shareTop">
                    <p class="topTitle">分享</p>
                    <div class="shareLinks share">
                        <a href="#" data-plat="sinaminiblog">
                            <img src="${ctx}/images/mobile/icon29.png" />
                            <p>新浪微博</p>
                        </a>
                        <a href="#" class="weixin" data-plat="weixin">
                            <img src="${ctx}/images/mobile/icon30.png" />
                            <p>微信好友</p>
                        </a>
                        <a href="#" data-plat="qzone">
                            <img src="${ctx}/images/mobile/icon31.png" />
                            <p>QQ空间</p>
                        </a>
                    </div>
                </div>
                <a href="javascript:void(0);" class="forgiveShare">取消</a>
            </div>
        </div>
        <!--选择规格-->
        <div class="chuiceLabel">
            <div class="chuiceInner writeWrap">
                <div class="chuiceProDes">
                    <img id="image" src="${ctx}/${goods.image}" class="chuiceImg" />
                    <div class="labelMsg">
                        <p id="title">${goods.title}</p>
                        <p class="salePrice">售价:<span>￥<span id="price"><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern=".00"/></span></span></p>
                    </div>
                    <img src="${ctx}/images/mobile/icon32.png" class="closeImg" />
                </div>
                <div class="labelWrap">
                    <c:if test="${fn:length(specAndValues)>0 }">
                        <c:forEach items="${specAndValues}" var="sv">
                        <div class="labels samePadding">
                            <p class="labelTitle">${sv.name}:</p>
                            <div class="labeBtns clearfix">
                                <c:forEach items="${sv.specValues}" var="v">
                                    <a href="javascript:void(0);" data-value="${sv.id}:${v.id}" >${v.name}</a>
                                </c:forEach>
                            </div>
                        </div>
                        </c:forEach>
                    </c:if>
                    <div class="buyNumbers samePadding clearfix">
                        <span class="fleft">购买数量</span>
                        <div class="buyNumBtns fright clearfix">
                            <a href="javascript:void(0);" class="numRelize fleft">-</a>
                            <input type="text" class="numInput fleft" value="1"  id="stock" stock="${goods.stock}" />
                            <a href="javascript:void(0);" class="numAdd fright">+</a>
                        </div>
                    </div>
                    <div class="samePadding font24 buyCount">当前库存：<sapn id="currStock">${goods.stock}</sapn>件</div>
                </div>
                <a href="javascript:void(0);" class="makeSureBtn sameBtn sameMargin">确定</a>
            </div>
        </div>
    </div><!-- //wrap -->
    <div class="mask"></div>
    <div class="tips">
        <dl>
            <dt>分享到微信，请点击右上角</dt>
            <dd>再选择【发送给朋友】</dd>
            <dd>或【分享到朋友圈】</dd>
        </dl>
    </div>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script src="${ctx}/js/mobile/Tab.js"></script>
    <script type="text/javascript" src="${ctx}/js/sku.js"></script>
    <!--评价百度模板-->
    <script id="moreList" type="text/html">
        <div class="evalList">
            <img src="${ctx}/images/mobile/icon22<!=grade!>.png" />
            <p class="evalText">
                <!=content!>
            </p>
            <div class="evalImgs">
                <!for(var j=0;j<5;j++){!>
                <a href="javascript:void(0);"><img src="${ctx}/<!=images[j]!>" /></a>
                <!}!>
            </div>
            <div class="userEval"><span class="userName"><!=name!></span> | <span class="orderTime"><!=t!></span></div>
        </div>
    </script>
    <!--优惠套餐百度模板-->
    <script id="suitProBox" type="text/html">
        <!for(var i=0;i < packageDetails.length;i++){!>
            <div class="leftPro">
                <a href="${ctx}/mobile/detail/<!=packageDetails[i].goods.id!>">
                    <img src="${ctx}/<!=packageDetails[i].goods.image!>" width="147" height="162" />
                    <p><!=packageDetails[i].goods.title!></p>
                </a>
            </div>
        <!}!>
    </script>
    <script src="${ctx}/js/mobile/jquery.slides.min.js"></script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script>
        var goodsId= ${goods.id};
        var packages =<c:if test="${empty packagesJson}">[]</c:if><c:if test="${not empty packagesJson}">${packagesJson}</c:if>;
        var goodsFrom ="${goodsFrom}";
        var oid = "${oid}";
        var member= ${not empty member};
        $(function () {
            $("#pid").click(function(){
               var pid = $(this).data("pid");
                buyPackage(pid);
            });
            function buyPackage(packageId){
                    this.location.href="${ctx}/order/addByTaoCan?packageId="+packageId;
            }

            <%-- 配件--%>
            $("#pjbuy").click(function(j,me){
                if($(this).hasClass("dis")){
                    return false;
                }else{
                    var num = parseInt($(".numInput").val());
                    var url="${ctx}/order/addByPeiJian?"
                    var $content1 = $("#pjContent input[type='checkbox']:checked");

                    url+="goodsId=${goods.id}&buyCount="+num+"&goodsFrom=${gf}&otherId=${oid}";
                    $.each($content1,function(c,m){
                        debugger;
                        var aid = $(this).data("id");
                        url+="&goodsId="+aid+"&count=1"
                    });
                    location.href=url;
                }
            });
            var pjSize = 0;
            $("#pjContent").find("label").each(function(i,e){
                $(this).change(function(){
                    var $content1 = $("#pjContent input[type='checkbox']:checked");
                    pjSize =$content1.length;
                    if(pjSize>0){
                        $("#pjCount").html(pjSize);
                        var priceSum=<fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/>;
                        $.each($content1,function(v,n){
                            priceSum  = parseFloat(priceSum) + parseFloat($(this).data("price"));
                        });
                        $("#pjPrice").text(priceSum.toFixed(2));
                        $("#pjbuy").removeClass("dis");
                    }else{
                        $("#pjbuy").addClass("dis");
                        $("#pjCount").html(0);
                        $("#pjPrice").text("<fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/>")
                    }
                })
            });
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
                    if(sku.id && sku.id != ''/*${goods.id}*/) {
                        /*var target="${ctx}/mobile/detail/"+ sku.id;
                         window.location.href=target;*/
                        $("#title").html(sku.title);
                        $("#price").html(sku.promotePrice.toFixed(2));
                        $("#image").attr("src","${ctx}/"+sku.images+"_100x100.jpg");
                        $("#stock").attr("stock",sku.stock);
                        $("#stock").val("1")
                        $("#currStock").html(sku.stock);
                        $("#buyNow").attr("g-id",sku.id);
                        $("#addCart").attr("g-id",sku.id);
                        $("#show-stock").html(sku.stock);
                    }
                }
            });
            function isEmptyObject(e) {
                var t;
                for (t in e)
                    return !1;
                return !0
            }

            //头部按钮显示
            $(".detailManu").click(function () {
                $(".handelList").toggle();
            });

            if (isWeiXin()) {
                $(".weixin").click(function () {
                    $(".mask").height($(document).height()).show();
                    $(".tips").show();
                    $("html,body").animate({ scrollTop: 0 }, 100);
                });
                $(".share a:not('.weixin')").click(function (event) {
                    var plat = $(this).data("plat");
                    bShare.share(event, plat, 0);
                    return false;
                });
            }else {
                $(".share a").click(function (event) {
                    var plat = $(this).data("plat");
                    bShare.share(event, plat, 0);
                    return false;
                });
            }
            $(".mask").click(function () {
                $(".mask").hide();
                $(".tips").hide();
            });
            function isWeiXin() {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    return true;
                } else {
                    return false;
                }
            }

            //设置左分隔符为 <!
            baidu.template.LEFT_DELIMITER='<!';
            //设置右分隔符为 <!
            baidu.template.RIGHT_DELIMITER='!>';
            var urlStr = window.location.href,
                webRoot = " ";
            (urlStr.indexOf("phonejintianyuan") != -1) ? webRoot = "/phonejintianyuan/" : webRoot = '/';
            //人气单品
            slide(".indexCardSlide", 713, 690);
            //推荐搭配
            slide(".recommendSlide", 715, 1100);
            //商品大图
            slide(".proDetailBanner", 750, 832);
            //优惠套餐页签形式数据请求/百度模板方法
            var data = [];
            $(".suitNav li").each(function (i) {
                $(this).click(function () {
                    var $_this = $(this);
                    $(".suitBox").html("加载中...");
                    //使用baidu.template命名空间

                    var bt = baidu.template;

                    //最简使用方法
                    var html = bt('suitProBox', packages[i]);

                    $(".suitBox").html(html);
                    $("#oprice").text(packages[i].originalPrice.toFixed(2))
                    $("#nprice").text(packages[i].saveMoney.toFixed(2))
                    $("#sprice").text((packages[i].originalPrice-packages[i].saveMoney).toFixed(2))
                    $("#pid").data("pid",packages[i].id);
                });
            });

            $(".suitNav li:first").trigger("click");

            Date.prototype.format = function(format) {
                var date = {
                    "M+": this.getMonth() + 1,
                    "d+": this.getDate(),
                    "h+": this.getHours(),
                    "m+": this.getMinutes(),
                    "s+": this.getSeconds(),
                    "q+": Math.floor((this.getMonth() + 3) / 3),
                    "S+": this.getMilliseconds()
                };
                if (/(y+)/i.test(format)) {
                    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
                }
                for (var k in date) {
                    if (new RegExp("(" + k + ")").test(format)) {
                        format = format.replace(RegExp.$1, RegExp.$1.length == 1
                                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
                    }
                }
                return format;
            }

            var page=2;
            var pageSize=1;
            var totalPage=${comments.totalPages};
            var flag =true;
            $(window).scroll(function () {
                var scrollTop = $(this).scrollTop();
                var scrollHeight = $(document).height();
                var windowHeight = $(this).height();
                if (scrollTop + windowHeight == scrollHeight) {
                    if(!flag || (page>totalPage) || ($(".forTab").eq(1).is(":hidden"))){
                        return false;
                    }
                    $(".load").css({opacity: 1});
                    $.ajax({
                        url:"${ctx}/mobile/comments/",
                        type:"get",
                        data: {page:page,size:pageSize,id:${goods.id}
                        },
                        traditional: true,
                    }).done(function(result){
                        var data = result.results;
                        if(data){
                            $.each(data,function(i,e){
                                var d= new Date();
                                d.setTime(e.commentTime)
                                var a={
                                    content: e.content,
                                    grade: e.grade == 5 ?'': e.grade,
                                    name: e.member.memberName,
                                    images: e.photos.split(","),
                                    t: d.format('yyyy-MM-dd')
                                }
                                var html=bt('moreList', a);
                                $(".pinterest").append(html);
                            });
                        }

                        if(totalPage<=result.page){
                            flag=false;
                            $(".load").hide();
                        }else{
                            page+=1;
                            flag=true;
                        }
                    }).fail(function(){
                        layer.open({
                            content: '获取失败.请稍候再试',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        })
                        flag=true;
                        $(".load").css({opacity: 0}).hide();
                    });
                }

            });

        });
    </script>
    <script src="${ctx}/js/mobile/proDetail.js"></script>
</body>
</html>
