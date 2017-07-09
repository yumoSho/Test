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
    <title>特价专区</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/saleProduct.css" rel="stylesheet" />
    <c:set var="foot" value="1"/>
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
            <div class="tabBtn">
                <a href="javascript:void(0);" class="on">正在进行</a>
                <i></i>
                <a href="${ctx}/mobile/timed-panic-buying/index?type=1">即将开始</a>
            </div>
            <div class="information samePadding pinterest">
                <c:forEach items="${discountGoodses.data}" var="discount">
                <div class="informationDiv clearfix">
                    <a href="${ctx}/mobile/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}" class="informationImg fleft">
                        <img src="${ctx}/${discount.goods.image}" />
                    </a>
                    <div class="informationRight fright">
                        <p class="colorP">&yen; <span class="sizeP"><fmt:formatNumber value="${discount.goods.promotePrice}" type="currency" pattern="0.00"/></span></p>
                        <div>
                            <a href="${ctx}/mobile/detail/<!=gid!>?goodsFrom=3&oid=<!=id!>" class="overflow"><!=title!></a>
                            <p><!=intro!></p>
                        </div>
                        <div class="overflow1">
                            <div><a href="${ctx}/mobile/detail/${discount.goods.id}?goodsFrom=3&oid=${discount.id}">${discount.goods.title}</a></div>
                            <p>${discount.goods.intro}</p>
                        </div>
                        <div class="countDown">
                            <span class="time" data-endtime="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${discount.endDate}" type="both"/>"></span>
                        </div>
                        <p class="marginP clearfix">
                            <span class="  fleft"><b class="weight sizeP colorP">${discount.discount}</b> 折</span>
                            <span class="fright">销量<span>${discount.goods.product.sales}</span>件</span>
                        </p>
                        <p>市 场 价：￥<span><fmt:formatNumber value="${discount.goods.price}" type="currency" pattern="0.00"/></span></p>
                        <img src="${ctx}/images/pc/proCart.jpg" class="iconAddCart fright" oId="${discount.id}" g-id="${discount.goods.id}" onclick="cartIconClick(this)"/>
                    </div>
                </div>
                </c:forEach>
            </div>
            <!--加载更多-->
            <div class="load">正在加载...</div>
        </div><!-- //main -->
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        <%@ include file="/WEB-INF/view/include/mobile/foot.jspf" %><!-- //foot -->
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/jquery.glanway.timeUp.1.0.js"></script>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script id="moreList" type="text/html">
        <div class="informationDiv clearfix">
            <a href="${ctx}/mobile/detail/<!=gid!>?goodsFrom=3&oid=<!=id!>" class="informationImg fleft">
                <img src="${ctx}/<!=img!>" />
            </a>
            <div class="informationRight fright">
                <p class="colorP">&yen; <span class="sizeP"><!=price!></span></p>
                <div class="overflow1">
                    <div><a href="${ctx}/mobile/detail/<!=gid!>?goodsFrom=3&oid=<!=id!>" class="overflow"><!=title!></a></div>
                    <p><!=intro!></p>
                </div>
                <div class="countDown">
                    <span class="time" data-endtime="<!=et!>"></span>
                </div>
                <p class="marginP clearfix">
                    <span class="  fleft"><b class="weight sizeP colorP"><!=discount!></b> 折</span>
                    <span class="fright">销量<span><!=sales!></span>件</span>
                </p>
                <p>市 场 价：￥<span><!=oprice!></span></p>
                <img src="${ctx}/images/pc/proCart.jpg" class="iconAddCart fright" oId="<!=id!>" g-id="<!=gid!>" onclick="cartIconClick(this)"/>
            </div>
        </div>

    </script>
    <script>
        //倒计时
        $(".time").timeUp({
            "template": "剩下%d天%h时%m分",
            "end": function (obj) {
                //alert(obj.html() + " 结束啦");
            }
        });
       /* //瀑布流代码
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
                        $(".time").timeUp({
                            "template": "剩下%d天%h时%m分",
                            "end": function (obj) {
                                //alert(obj.html() + " 结束啦");
                            }
                        });
                    }
                }
            }
        });*/

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
        $(function(){
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
            var pageSize=6;
            var totalPage=${discountGoodses.totalPages};
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
                        url:"${ctx}/mobile/timed-panic-buying/list",
                        type:"get",
                        data: {page:page,pageSize:pageSize,provinceId:0,type:0},
                        traditional: true,
                    }).done(function(result){
                        var data = result.rows;
                        if(data){
                            $.each(data,function(i,e){
                                var d= new Date();
                                d.setTime(e.endDate)
                                var a={
                                    id: e.id,
                                    gid: e.goods.id,
                                    img: e.goods.image,
                                    price: e.goods.promotePrice.toFixed(2),
                                    title: e.goods.title,
                                    discount: e.discount.toFixed(2),
                                    sales: e.goods['product.sales'],
                                    oprice: e.goods.price.toFixed(2),
                                    et: d.format('yyyy-MM-dd hh:mm:ss')
                                }
                                var html=bt('moreList', a);
                                $(".pinterest").append(html);
                                $(".time").timeUp({
                                    "template": "剩下%d天%h时%m分",
                                    "end": function (obj) {
                                        //alert(obj.html() + " 结束啦");
                                    }
                                });
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
        })
    </script>
</body>
</html>
