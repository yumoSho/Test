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
    <title>商品列表页</title>
    <meta name="description" content="">
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
                <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>商品详情</span>
            </div>
        </div>
        <!-- 筛选条件 -->
        <div class="filterWrap samewidth">
          <%--  <c:if test="${!empty param.kw}">
                <div class="hadSelect">
                    <span>“${fn:escapeXml(param.kw)}”</span>相关商品，共<span>${numFound}</span>件
                </div>
            </c:if>--%>



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
                该商品已下架！<br />请查看其它商品
               <%-- <p>
                    1、您输入的关键字是否有错别字，或者减少关键字试试；<br />
                    2、如果您使用自然语言进行搜索，请提炼出关键字再搜索试试！
                </p>--%>
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
