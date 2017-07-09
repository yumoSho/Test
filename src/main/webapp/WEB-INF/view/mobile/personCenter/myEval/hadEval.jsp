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
    <title>已评价</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/myEval.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/userCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <style>
        .nullNotice{
            text-align: center;
            font-size: .3rem;
            color: #999;
            padding: 2rem 0;
        }
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="notShowTop"/>
        <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
        <div class="main">
            <div class="evalBtns">
                <a href="${ctx}/mobile/person-center/comment" >待评价</a>
                <a href="${ctx}/mobile/person-center/comment?commented=1" class="on">已评价</a>
            </div>
            <div class="hadEval">
                <c:forEach items="${comments.data}" var="c">
                <div class="evaledItem writeWrap">
                    <div class="evalTop clearfix">
                        <a href="${ctx}/mobile/detail/${c.goods.id}" class="fleft"><img src="${ctx}/${c.goods.image}" /></a>
                        <div class="evalRight fright">
                            <a href="${ctx}/mobile/detail/${c.goods.id}" class="overflow">${c.goods.title}</a>
                            <%--<p><span>规格：<span>18</span>大小：<span>10袋装</span></span></p>--%>
                        </div>
                    </div>
                    <p class="evalText">
                        <%--看了这家店很久，终于下手了，东西很不错，性价比很高，便宜实惠，强烈推荐~--%>
                        ${c.content}
                    </p>
                    <c:if test="${not empty c.photos}">
                    <div class="evaledImgs">
                        <c:forEach items="${fn:split(c.photos,',')}" var="p">
                            <c:if test="${not empty p}">
                            <img src="${ctx}/${p}" />
                            </c:if>
                        </c:forEach>
                    </div>
                    </c:if>
                </div>
                </c:forEach>
                <c:if test="${fn:length(comments.data) eq 0}">
                        <div class="nullNotice">暂时没有评价！</div>
                </c:if>
                <%--<div class="evaledItem writeWrap">
                    <div class="evalTop clearfix">
                        <a href="../../product/productDetail.html" class="fleft"><img src="${ctx}/images/mobile/evalImg1.jpg" /></a>
                        <div class="evalRight fright">
                            <a href="../../product/productDetail.html" class="overflow"> [日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</a>
                            <p><span>规格：<span>18</span>大小：<span>10袋装</span></span></p>
                        </div>
                    </div>
                    <p class="evalText">
                        看了这家店很久，终于下手了，东西很不错，性价比很高，便宜实惠，强烈推荐~
                    </p>
                    <div class="evaledImgs">
                        <img src="${ctx}/images/mobile/evalImg.jpg" />
                        <img src="${ctx}/images/mobile/evalImg.jpg" />
                    </div>
                </div>--%>
            </div>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
    <script>
        window.contextPath="${ctx}";
    </script>
    <script src="${ctx}/js/mobile/jquery.raty.min.js"></script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/common.js"></script>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script id="moreList" type="text/html">
        <div class="evaledItem writeWrap">
            <div class="evalTop clearfix">
                <a href="${ctx}/mobile/detail/<!=id!>" class="fleft"><img src="${ctx}/<!=img!>" /></a>
                <div class="evalRight fright">
                    <a href="${ctx}/mobile/detail/<!=id!>" class="overflow"> <!=title!></a>
                    <%--<p><span>规格：<span>18</span>大小：<span>10袋装</span></span></p>--%>
                </div>
            </div>
            <p class="evalText">
                <%--看了这家店很久，终于下手了，东西很不错，性价比很高，便宜实惠，强烈推荐~--%>
                <!=content!>
            </p>
            <!if(photos.length>0)!>
            <div class="evaledImgs">
                <!for(var i=0 ; i<photos.length ; i++){ !>
                <img src="${ctx}/<!=photos[i]!>" />
                <!}!>
            </div>
            <!}!>
        </div>
    </script>
    <script>
        $(function () {
            //评分
            $("#star").raty({
                path: "${ctx}/images/mobile/",
                starOff: 'evalIcon3.png',
                starOn: 'evalIcon2.png',
                size: 24,
                score: 1
            });
            var page=2;
            var pageSize=10;
            var totalPage=${comments.totalPages};
            var bt=baidu.template;
            //设置左分隔符为 <!
            baidu.template.LEFT_DELIMITER='<!';
            //设置右分隔符为 <!
            baidu.template.RIGHT_DELIMITER='!>';
            var flag =true;
            $(window).scroll(function () {

                var scrollTop = $(this).scrollTop();
                var scrollHeight = $(document).height();
                var windowHeight = $(this).height();



                if (scrollTop + windowHeight == scrollHeight) {

                    if(!flag || page>=totalPage ){
                        $(".load").hide();
                        return false;
                    }
                    $(".load").css({opacity: 1});
                    $.ajax({
                        url:"${ctx}/mobile/person-center/comment-list?commented=1",
                        type:"get",
                        data: {page:page,size:pageSize,
                        },
                        traditional: true,
                    }).done(function(result){
                        var data = result.results;
                        if(data){
                            $.each(data,function(i,e){
                                var a={
                                    id: e.id,
                                    title: e.title,
                                    img: e.image
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
</body>
</html>
