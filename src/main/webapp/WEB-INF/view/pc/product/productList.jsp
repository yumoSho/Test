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
    <title>商品列表页-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/product.css">
    <link href="${ctx}/js/pc/layer/skin/layer.css" rel="stylesheet" />


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
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>商品列表</span>
                </div>
            </div>
            <!-- 筛选条件 -->
            <div class="filterWrap samewidth">
                <c:if test="${!empty param.kw}">
                <div class="hadSelect">
                    <span>“${fn:escapeXml(param.kw)}”</span>相关商品，共<span>${numFound}</span>件
                </div>
                </c:if>
                <div class="hadChuice clearfix">
                    <span class="chuiceTitle fl">您已选择：</span>
                    <div class="searchItems fl">

                        <%--关键字--%>
                        <c:if test="${!empty param.kw}">
                            <a href="${fne:rp(paramValues, 'kw')}" class="chuiceItem">
                                <span class="typeTitle">关键字:</span>
                                <span class="typeName ">${fn:escapeXml(param.kw)}</span>
                                <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                            </a>
                        </c:if>
                        <%--关键字--%>

                        <%--分类--%>
                        <c:if test="${!empty param.cat}">
                            <a href="${fne:rpTwo(paramValues, 'cat','ppath')}" class="chuiceItem ">
                                <span class="typeTitle">分类:</span>
                                <span class="typeName">${fn:escapeXml(cat.name)}</span>
                                <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                            </a>
                        </c:if>
                        <%--分类--%>

                        <%--品牌--%>
                        <c:if test="${not empty param.brand}">
                            <a href="${fne:rp(paramValues, 'brand')}" class="chuiceItem ">
                                <span class="typeTitle">品牌:</span>
                                <span class="typeName">${groupedProps.brand.values[0].name}</span>
                                <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                            </a>
                        </c:if>
                        <%--品牌--%>

                        <%--动态属性--%>
                        <c:forEach var="ppath" items="${ppaths}">
                            <a href="${fne:rpv(paramValues, 'ppath', ppath.path)}" class="chuiceItem ">
                                <span class="typeTitle">${fn:substringBefore(ppath.name,':' )}:</span>
                                <span class="typeName">${fn:substringAfter(ppath.name,':' )}</span>
                                <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                            </a>
                        </c:forEach>
                        <%--动态属性--%>


                        <%--<a href="javascript:void(0);" class="chuiceItem">
                            <span class="typeTitle">品牌:</span>
                            <span class="typeName ">西饼屋</span>
                            <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                        </a>
                        <a href="javascript:void(0);" class="chuiceItem ">
                            <span class="typeTitle">产地:</span>
                            <span class="typeName">西安</span>
                            <img src="${ctx}/images/pc/redClose.png" width="14" height="13" />
                        </a>--%>
                    </div>
                </div>
                <div class="filterBtnsWrap">
                    <%--分类--%>
                    <c:if test="${empty  param.cat and fn:length(pCats) > 1}">
                        <div class="filterItemSame clearfix">
                            <span class="itemTitle fl">分类：</span>

                            <div class="btnWrap fl">
                                <div>
                                    <a href="javascript:void(0);" class="on">全部</a>
                                    <c:forEach var="entry" items="${pCats}">
                                        <c:set var="cat" value="${entry.value}"/>
                                        <a href="${fne:ap(paramValues, 'cat', cat.key)}" data-id="${cat.key}" title="${fn:escapeXml(cat.name)}">${fn:escapeXml(cat.name)}</a>
                                    </c:forEach>
                                </div>
                            </div>
                            <a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>
                        </div>
                    </c:if>
                    <%--分类--%>

                    <%--品牌--%>
                    <c:if test="${empty param.brand and fn:length(groupedProps.brand.values) > 1}">
                        <div class="filterItemSame clearfix">
                            <span class="itemTitle fl">品牌：</span>
                            <div class="btnWrap fl">
                                <div>
                                    <a href="javascript:void(0);" class="on">全部</a>
                                    <c:forEach var="brand" items="${groupedProps.brand.values}">
                                        <a href="${fne:ap(paramValues, 'brand', brand.key)}" title="${fn:escapeXml(brand.name)}">${fn:escapeXml(brand.name)}</a>
                                    </c:forEach>
                                </div>
                            </div>
                            <a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>
                        </div>
                    </c:if>
                    <%--品牌--%>

                    <%--动态属性--%>
                    <c:forEach var="prop" items="${groupedProps}">
                        <c:set var="prop" value="${prop.value}"/>
                        <c:if test="${prop.key != 'brand' and fn:length(prop.values) > 1}">
                            <div class="filterItemSame clearfix">
                                <span class="itemTitle fl" title="${prop.name}" data-id="${prop.key}">${prop.name}：</span>
                                <div class="btnWrap fl">
                                    <div>
                                        <a href="javascript:void(0);" class="on">全部</a>
                                        <c:forEach var="value" items="${prop.values}">
                                            <c:set var="pv" value="${prop.key}:${value.key}"/>
                                            <a href="${fne:ap(paramValues, 'ppath', pv)}" title="${fn:escapeXml(value.name)}">${fn:escapeXml(value.name)}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                                <a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>
                            </div>
                        </c:if>
                    </c:forEach>
                    <%--动态属性--%>

                    <div class="filterItemSame clearfix">
                        <span class="itemTitle fl">标签：</span>
                        <div class="btnWrap fl">
                            <a href="${fne:rp(paramValues,'labelId' )}" <c:if test="${empty param.labelId }"> class="on" </c:if> >全部</a>
                            <c:forEach items="${labels}" var="label">
                                <c:set var="l" value="${label.id}"/>
                                <c:set var="rlabel" value="${fne:rp(paramValues,'labelId' )}"/>

                                <a  href="${rlabel}&labelId=${l}" <c:if test="${param.labelId eq l}"> class="on" </c:if> >${label.labelName}</a>
                            </c:forEach>
                        </div>
                        <a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>
                    </div>

                    <%--<div class="filterItemSame clearfix">
                        <span class="itemTitle fl">品牌：</span>
                        <div class="btnWrap fl">
                            <a href="javascript:void(0);">全部</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                            <a href="javascript:void(0);">西饼屋</a>
                        </div>
                        <a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>
                    </div>
                    <div class="filterItemSame clearfix">
                        <span class="itemTitle fl">分类：</span>
                        <div class="btnWrap fl">
                            <a href="javascript:void(0);">全部</a>
                            <a href="javascript:void(0);">牛奶饮品</a>
                            <a href="javascript:void(0);">饼干糕点</a>
                            <a href="javascript:void(0);">糖果/巧克力</a>
                            <a href="javascript:void(0);">冲调饮品</a>
                            <a href="javascript:void(0);">粮油调味</a>
                            <a href="javascript:void(0);">生鲜食品</a>
                        </div>
                        <!--<a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>-->
                    </div>
                    <div class="filterItemSame clearfix">
                        <span class="itemTitle fl">产地：</span>
                        <div class="btnWrap fl">
                            <a href="javascript:void(0);">全部</a>
                            <a href="javascript:void(0);">黑龙江</a>
                            <a href="javascript:void(0);">西安</a>
                            <a href="javascript:void(0);">广州</a>
                            <a href="javascript:void(0);">四川</a>
                        </div>
                        <!--<a href="javascript:void(0);" class="moreBtn fl"><span>更多</span><img src="${ctx}/images/pc/downBtn.png" wdith="7" height="4" /></a>-->
                    </div>--%>
                </div>
                <div class="typeFilter">
                    <%--<a href="javascript:void(0);" class="defaultFilter on">默认</a>
                    <a href="javascript:void(0);" class="otherFilter">
                        销量
                        <span class="up"></span>
                        <span class="down"></span>
                    </a>
                    <a href="javascript:void(0);" class="otherFilter">
                        价格
                        <span class="up"></span>
                        <span class="down"></span>
                    </a>
                    --%>
                    <a href="${fne:rp(paramValues, 'order' )}" class="defaultFilter
                        <c:if test="${order eq 'moren'}">on</c:if>
                        ">默认</a>

                    <c:choose>
                        <c:when test="${order eq 'sales-asc'}">
                            <a href="${fne:rp(paramValues, 'order' )}&order=sales-desc" class="otherFilter">
                                销量
                                <span class="up on"></span>
                                <span class="down "></span>
                            </a>
                        </c:when>
                        <c:when test="${order eq 'sales-desc'}">

                            <a href="${fne:rp(paramValues, 'order' )}&order=sales-asc" class="otherFilter">
                                销量
                                <span class="up"></span>
                                <span class="down on"></span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${fne:rp(paramValues, 'order' )}&order=sales-desc" class="otherFilter">
                                销量
                                <span class="up"></span>
                                <span class="down"></span>
                            </a>
                        </c:otherwise>
                    </c:choose>


                    <c:choose>
                        <c:when test="${order eq 'price-asc'}">
                            <a href="${fne:rp(paramValues, 'order' )}&order=price-desc" class="otherFilter">
                                价格
                                <span class="up on"></span>
                                <span class="down"></span>
                            </a>

                        </c:when>
                        <c:when test="${order eq 'price-desc'}">
                            <a href="${fne:rp(paramValues, 'order' )}&order=price-asc" class="otherFilter">
                                价格
                                <span class="up "></span>
                                <span class="down on"></span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${fne:rp(paramValues, 'order' )}&order=price-desc" class="otherFilter">
                                价格
                                <span class="up"></span>
                                <span class="down"></span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <!-- 产品列表 -->
            <c:if test="${fn:length(results)>0}">
            <div class="activeProList commonProList clearfix">
                <c:forEach items="${results}" varStatus="st" var="item">
                <div class="proItem hot" id="g_${item.id}">
                    <a target="_blank" href="${ctx}/detail/${item.id}" title="${item.title}">
                        <img src="${ctx}/${item.image}" width="230" height="252" /></a>
                    <a target="_blank" href="${ctx}/detail/${item.id}" title="${item.title}" class="proName color666 padding10">${item.title}</a>
                    <p class="proSubName padding10">${item.intro}</p><%--<div class="proPrice color666 clearfix">
                        <span class="fl">价格：<span>￥12.9</span></span>
                        <span class="fr">销量<span>1214</span>件</span>
                    </div>--%>
                    <div class="activePrice color666 padding10"><span class="sPrice">￥<span><fmt:formatNumber value="${item.price}" type="currency" pattern="0.00"/></span></span></div>
                    <c:if test="${not empty item.labelPath}">
                    <img src="${ctx}/${item.labelPath}" width="50" height="42" class="hotPro" />
                    </c:if>
                    <c:forEach items="${item.imgs}" var="img">
                        <input type="hidden" class="img" value="${img}"/>
                    </c:forEach>
                    <div class="proHandel">
                        <a href="javascript:void(0);" class="proBig"><img src="${ctx}/images/pc/seeBig.jpg" width="15" height="15" /></a>
                        <a href="javascript:void(0);" class="proCart" data-id="${item.id}"><img src="${ctx}/images/pc/proCart.jpg" width="45" height="37" /></a>
                    </div>
                </div>
                </c:forEach>
            </div>
            <!-- 分页 -->
            <form id="pagination-form" class="pagination-form">
                <m:pagination totalPages="${totalPages}" pageParam="page" skip="false"/>
            </form>
            </c:if>
            <c:if test="${fn:length(results) eq 0}">
                <div class="proNullNotice">
                    没有找到相关的商品！<br />建议您按照以下提示调整关键字
                    <p>
                        1、您输入的关键字是否有错别字，或者减少关键字试试；<br />
                        2、如果您使用自然语言进行搜索，请提炼出关键字再搜索试试！
                    </p>
                </div>
            </c:if>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
        <div class="proMoreImg">
            <div class="zz-box">
                <div class="zoompic">
                    <a href="javascipt:void(0);">
                        <img src="${ctx}/images/pc/showImg1.jpg" width="451" height="490" alt="产品大图" class="jqzoom" />
                    </a>
                </div>
                <div class="sliderbox clearfix">
                    <a href="javascript:void(0);" class="arrow-btn btn-prev">上一张</a>
                    <div class="slider" id="thumbnail">
                        <ul>
                            <li class="current"><img src="${ctx}/images/pc/showImg2.jpg" width="64" height="69" alt="产品大图" longdesc="1" /></li>
                            <li><img src="${ctx}/images/pc/showImg3.jpg" bigsrc="" width="64" height="69" alt="产品小图" longdesc="2" /></li>


                        </ul>
                    </div>
                    <a href="javascript:void(0);" class="arrow-btn btn-next">下一张</a>
                </div>
                <img src="${ctx}/images/pc/closeProPop.png" width="32" height="32" class="closeShow" />
            </div>
        </div>
    </div><!-- //wrap -->
    <script src="${ctx}/js/pc/zzimage.js"></script>
    <%--<script src="${ctx}/js/pc/imgShow.js"></script>--%>
    <script src="${ctx}/js/pc/layer/layer.js"></script>

    <script>
        $(function () {
            var flag = ${not empty member};
            //更多按钮点击效果
            $(".filterItemSame .moreBtn").click(function () {
                var otherHeight = $(this).siblings(".btnWrap ").height();
                if (otherHeight == 38) {
                    $(this).siblings(".btnWrap ").css("height", "auto");
                    $(this).find("span").text("收起");
                    $(this).find("img").attr("src", "${ctx}/images/pc/upBtn.png");
                } else {
                    $(this).siblings(".btnWrap ").css("height", "38px");
                    $(this).find("span").text("更多");
                    $(this).find("img").attr("src", "${ctx}/images/pc/downBtn.png");
                }
            });
            //检索条件部分js
            $(".filterItemSame .btnWrap a").click(function () {
                $(this).addClass("on").siblings().removeClass("on");
            });
            //销量和价格效果
            $(".typeFilter .otherFilter").click(function () {
                var $this = $(this);
                $this.siblings().removeClass("on");
                $this.siblings().children().removeClass("on");
                if ($this.find(".down").hasClass("on")) {
                    $this.find(".up").addClass("on").siblings().removeClass("on");
                } else {
                    $this.find(".down").addClass("on").siblings().removeClass("on");
                }
            });
            //默认点击效果
            $(".defaultFilter").click(function () {
                $(this).addClass("on").siblings().children().removeClass("on");
            });
            $(".commonProList .proItem .proBig").click(function () {
                var imgs = $(this).parents(".proItem").find(".img");
                var imgs1 = [],imgs2=[];
                $.each(imgs,function(i,e){
                    imgs1.push($(this).val());
                    imgs2.push($(this).val());
                })

               /* var imgShow1 = ["../../images/showImg1.jpg", "../../images/showImg2.jpg"],
                        imgShow2 = ["../../images/showImg1.jpg", "../../images/showImg2.jpg"],
                        imgShow3 = ["../../images/showImg1.jpg", "../../images/showImg3.jpg", "../../images/showImg1.jpg"],
                        imgShow4 = ["../../images/showImg1.jpg", "../../images/showImg3.jpg", "../../images/showImg1.jpg"];

*/
                $("#thumbnail").html("");
                $(".zoompic").find("a").html("");
                var bigImg = "<img src='" + imgs1[0] + "'  width=\"451\" height=\"490\" alt=\"产品大图\" class=\"jqzoom\"  />";
                var html = "<ul>";
                for (var i = 0 ; i < imgs1.length; i++) {
                    html += " <li  " + (i == 0 ? "class='current'" : "") + "><img src='" + imgs1[i] + "' bigsrc='" + imgs2[i] + "' width='64' height='69'" + "alt='产品大图' longdesc='" + (i + 1) + "' /></li>";
                }

                html += "</ul>";
                $("#thumbnail").append(html);
                $(".zoompic").find("a").append(bigImg);
                $(".proMoreImg").show();
            });
            //关闭展示大图
            $(".closeShow").click(function () {
                $(".proMoreImg").hide();
            });

            //点击切换大图
            $("#thumbnail").on("click", "li", function () {
                $me = $(this);
                $me.addClass("current").siblings().removeClass("current");
                $(".zoompic").find("img").attr("src", $me.find("img").attr("bigsrc"));
            });
            //点击下一个切换
            $(".btn-next").click(function () {
                $("li.current").removeClass("current").next().addClass("current");
                if ($("li.current").index() == -1) {
                    $("#thumbnail li:first-child").trigger("click");
                };
                $(".zoompic").find("img").attr("src", $("li.current").find("img").attr("bigsrc"));
            });
            //点击上一个切换
            $(".btn-prev").click(function () {
                $("li.current").removeClass("current").prev().addClass("current");
                if ($("li.current").index() == -1) {
                    $("#thumbnail li:last-child").trigger("click");
                };
                $(".zoompic").find("img").attr("src", $("li.current").find("img").attr("bigsrc"));
            });

            //任意点击区域，弹窗大图隐藏
            $(".proMoreImg ,.proMoreImg .zz-box .closeShow").click(function (e) {
                if (!$(e.target).is(".zz-box") && !$(e.target).parents().is(".zz-box")) {
                    $(".proMoreImg").hide();
                }
            });
            $(".proMoreImg .zz-box .closeShow").click(function (e) {
                $(".proMoreImg").hide();
            });
            //产品部分加入购物车
            var  clicked= false;
            $(".proItem .proHandel .proCart").click(function () {
                var id = $(this).data("id");
                var area = $(".addrChecked").data("id");
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
                /*layer.msg("加入购物车成功！");*/
            });
        });
    </script>
</body>
</html>
