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
    <title>活动列表</title>
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
            <div class="brandList pinterest clearfix">
                <c:forEach items="${activities.data}" var="ad">
                <a href="${ctx}/mobile/activity/detail/${ad.id}">
                    <img src="${ctx}/${ad.activityImgPath}" alt="${ad.activityName}" />
                    <span>入场疯抢</span>
                </a>
                </c:forEach>
            </div>
            <!--加载更多-->
            <div class="load">正在加载...</div>
        </div><!-- //main -->
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script id="moreList" type="text/html">
        <a href="${ctx}/mobile/activity/detail/<!=id!>">
            <img src="${ctx}/<!=img!>" alt="<!=title!>" />
            <span>入场疯抢</span>
        </a>
    </script>
    <script>
        var page=2;
        var pageSize=6;
        var totalPage=${activities.totalPages};
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

                if(!flag || page > totalPage){
                    $(".load").hide();
                    return false;
                }
                $(".load").css({opacity: 1});
                $.ajax({
                    url:"${ctx}/mobile/activity/list",
                    type:"get",
                    data: {page:page,pageSize:pageSize,provinceId:0},
                    traditional: true,
                }).done(function(result){
                    var data = result.rows;
                    if(data){
                        $.each(data,function(i,e){
                            var a={
                                id: e.id,
                                title: e.activityName,
                                img: e.activityImgPath
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
    </script>
</body>
</html>
