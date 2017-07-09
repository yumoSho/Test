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
    <title>待评价</title>
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
                <a href="${ctx}/mobile/person-center/comment" class="on">待评价</a>
                <a href="${ctx}/mobile/person-center/comment?commented=1">已评价</a>
            </div>
            <div class="pinterest">
                <c:forEach items="${goodses.data}" var="g">
                <div class="evaledItem writeWrap">
                    <div class="evalTop clearfix">
                        <a href="${ctx}/mobile/detail/${g.id}" class="fleft"><img src="${ctx}/${g.image}" /></a>
                        <div class="evalRight fright">
                            <a href="${ctx}/mobile/detail/${g.id}" class="overflow">${g.title}</a>
                            <%--<p><span>规格：<span>18</span></span></p>
                            <p>大小：<span>10袋装</span></p>--%>
                        </div>
                    </div>
                    <a href="${ctx}/mobile/person-center/go-comment?id=${g.id}&ordid=${g.tempOrderDetailId}&src=${g.image}" class="goEval">评价</a>
                </div>
                </c:forEach>
                <c:if test="${fn:length(goodses.data) eq 0}">
                    <div class="nullNotice">暂时没有评价！</div>
                </c:if>
            </div>
            <div class="load">正在加载...</div>
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
                <a href="${ctx}/mobile/detail/<!=id!>" class="fleft"><img src="${ctx}/<img>" /></a>
                <div class="evalRight fright">
                    <a href="${ctx}/mobile/detail/<!=id!>" class="overflow"><!=title!></a>
                    <%--<p><span>规格：<span>18</span></span></p>
                    <p>大小：<span>10袋装</span></p>--%>
                </div>
            </div>
            <a href="${ctx}/mobile/person-center/go-comment?id=<!=id!>&ordid=<!=ordid!>&src=<!=img>" class="goEval">评价</a>
        </div>
    </script>
    <script>
        $(function () {
            //评分
            $("#star").raty({
                path: "${ctx}/images/mobile",
                starOff: 'evalIcon3.png',
                starOn: 'evalIcon2.png',
                size: 24,
                score: 1
            });

            var page=2;
            var pageSize=10;
            var totalPage=${goodses.totalPages};
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
                        url:"${ctx}/mobile/person-center/comment-list",
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
                                    img: e.image,
                                    ordid: e.product.store.id
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
