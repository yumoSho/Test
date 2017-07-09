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
    <title>产品列表</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/product.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
</head>
<body>
<c:set var="foot" value="1"/>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!--产品列表页-->
    <div id="show1">
        <div class="wrap">
            <%@ include file="/WEB-INF/view/include/mobile/head.jspf" %>
            <div class="main">
                <div class="filterBtns">
                    <a href="${fne:rp(paramValues, 'order' )}&order=moren" data-id="moren" <c:if test="${order eq 'moren'}">class="on"</c:if>>默认排序</a>
                    <a href="${fne:rp(paramValues, 'order' )}&order=sales-desc" data-id="sales-desc" <c:if test="${order eq 'sales-desc'}">class="on"</c:if>>销量优先</a>
                    <a href="${fne:rp(paramValues, 'order' )}&order=price-desc" data-id="price-desc" <c:if test="${order eq 'price-desc'}">class="on"</c:if>>价格优先</a>
                    <a href="javascript:void(0);" class="filter"><img src="${ctx}/images/mobile/icon15.png" />筛选</a>
                </div>
                <div class="productList pinterest samePadding clearfix">
                    <c:forEach items="${results}" var="rs">
                    <div class="proItem fleft">
                        <a href="${ctx}/mobile/detail/${rs.id}" class="proImg"><img src="${ctx}/${rs.image}" /></a>
                        <div class="proMessage">
                            <%--<p class="proPrice">￥<span>${rs.price}</span></p>   原
                            <a href="${ctx}/mobile/detail/${rs.id}" class="proName overflow">${rs.title}</a>
                            <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${rs.id}" onclick="cartIconClick(this)"/>--%>

                                <a href="${ctx}/mobile/detail/${rs.id}" class="proName overflow" style="line-height: .45rem;padding: 0;height: .50rem;">${rs.title}</a>
                                <p class="subTitle">${rs.intro}</p>
                                <p class="proPrice colorRed">￥<span><fmt:formatNumber value="${rs.price}" type="currency" pattern="0.00"/></span></p>
                                <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${rs.id}" style="width: 26px;"/>
                        </div>
                        <c:if test="${not empty rs.labelPath}">
                        <div class="hotSale" style="background:url(${ctx}/${rs.labelPath}) no-repeat center;background-size:100%"></div>
                        </c:if>
                    </div>
                        </c:forEach>
                </div>
                <!--加载更多-->
                <div class="load">正在加载...</div>
            </div><!-- //main -->
            <%@ include file="/WEB-INF/view/include/mobile/foot.jspf" %><!-- //foot -->
            <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        </div><!-- //wrap -->
    </div>
    <!--筛选页-->
    <div id="show2">
        <div class="wrap deviceHeight">
            <div class="head">
                <div class="pageTitle headTop samePadding clearfix">
                    <a href="javascript:void(0);" class="back back1 fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                    <span>筛选</span>
                </div>
            </div><!-- //head -->
            <div class="main">
                <div class="proLabelFilter">
                    <p>商品标签</p>
                    <div class="labelBtns">

                        <a href="javascript:void(0);" data-label="" <c:if test="${empty label}">class="on"</c:if>>全部</a>
                        <c:forEach items="${labels}" var="l">
                        <a href="javascript:void(0);" data-label="${l.id}" <c:if test="${label eq l.id}">class="on"</c:if>>${l.labelName}</a>
                        </c:forEach>
                    </div>
                    <input type="hidden" id="kw" value="${fn:escapeXml(param.kw)}"/>
                    <input type="hidden" id="cat" value="${fn:escapeXml(cat.key)}"/>
                    <input type="hidden" id="brand" value="<c:if test="${not empty param.brand}">${groupedProps.brand.values[0].key}</c:if>"/>
                    <c:forEach var="ppath" items="${ppaths}">
                        <input type="hidden" class="sel-ppath" value="${ppath.path}"/>
                    </c:forEach>
                </div>
                <ul class="forNextPage">

                   <%-- <li>
                        <span>品牌：</span>
                        <a href="javascript:void(0);" onclick="selectBrand(this)" class="bradFilter">
                            <span class="textTitle">全部</span>
                            <img src="${ctx}/images/mobile/icon16.png" class="fright" />
                        </a>
                    </li>--%>
                    <c:if test="${empty  param.cat and fn:length(pCats) > 1}">
                    <li>
                        <span>分类：</span>
                        <a href="javascript:void(0);" onclick="selectBrand(this,'cats')" class="typeFilter">
                            <span class="textTitle cat">全部</span>
                            <img src="${ctx}/images/mobile/icon16.png" class="fright" />
                        </a>
                    </li>
                    </c:if>
                    <%--------品牌---------%>

                    <c:if test="${empty param.brand and fn:length(groupedProps.brand.values) > 1}">
                        <li>
                            <span>品牌：</span>
                            <a href="javascript:void(0);" onclick="selectBrand(this,'brands')" class="bradFilter">
                                <span class="textTitle brand">全部</span>
                                <img src="${ctx}/images/mobile/icon16.png" class="fright" />
                            </a>
                        </li>
                    </c:if>

                    <c:forEach var="prop" items="${groupedProps}" varStatus="p">
                        <c:set var="prop" value="${prop.value}"/>
                        <c:if test="${prop.key != 'brand' and fn:length(prop.values) > 1}">
                    <li>
                        <span>${prop.name}：</span>
                        <a href="javascript:void(0);" onclick="selectBrand(this,'props','p${p.index}')" class="typeFilter">
                            <span class="textTitle prop">全部</span>
                            <img src="${ctx}/images/mobile/icon16.png" class="fright" />
                        </a>
                    </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div><!-- //main -->
            <div class="handelBtns sameMargin">
                <input type="reset" value="重置" />
                <input type="button" value="确定" />
            </div>
        </div><!-- //wrap -->
    </div>
    <!--分类筛选条件-->
    <div id="show3">
        <div class="wrap deviceHeight">
            <div class="head">
                <div class="pageTitle headTop samePadding clearfix">
                    <a href="javascript:void(0);" class="back back2 fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                    <span>筛选</span>
                </div>
            </div><!-- //head -->
            <div class="main">
                <div class="lastSelect brandSelect">
                    <a href="javascript:void(0);">休闲零食</a>
                    <a href="javascript:void(0);">牛奶饮料</a>
                    <a href="javascript:void(0);">饼干糕点</a>
                    <a href="javascript:void(0);">糖果/巧克力</a>
                    <a href="javascript:void(0);">冲调饮品</a>
                    <a href="javascript:void(0);">粮油调味</a>
                    <a href="javascript:void(0);">生鲜食品</a>
                </div>
            </div><!-- //main -->
        </div><!-- //wrap -->
    </div>
    <div class="addrIframe" id="addrIframe">
        <iframe src="${ctx}/mobile/addSelect" class="iframeInner" id="iframeInner"></iframe>
    </div>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script id="moreList" type="text/html">
        <div class="proItem fleft">
            <a href="${ctx}/mobile/detail/<!=id!>" class="proImg"><img src="${ctx}/<!=image!>" /></a>
            <div class="proMessage">
                <p class="proPrice">￥<span><!=price!></span></p>
                <a href="${ctx}/mobile/detail/<!=id!>" class="proName overflow"><!=title!></a>
                <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="<!=id!>" onclick="cartIconClick(this)"/>
            </div>
            <!if(label!=''){!>
            <div class="hotSale" style="background:url(${ctx}/images/mobile/<!=label!>) no-repeat center;background-size:100%"></div>
            <!}!>
        </div>
    </script>
    <script>
        var flag;
        var cats =new Array();
        <c:if test="${empty  param.cat and fn:length(pCats) > 1}">
            <c:forEach var="entry" items="${pCats}">
                <c:set var="cata" value="${entry.value}"/>
                cats.push({id:${cata.key},name:'${fn:escapeXml(cata.name)}'})
            </c:forEach>
        </c:if>

        var brands =new Array();
        <c:if test="${empty param.brand and fn:length(groupedProps.brand.values) > 1}">
            <c:forEach var="brand" items="${groupedProps.brand.values}">
                brands.push({id:${brand.key},name:'${fn:escapeXml(brand.name)}'})
            </c:forEach>
        </c:if>

        var props = new Object();
        <c:forEach var="prop" items="${groupedProps}" varStatus="j">
            <c:set var="prop" value="${prop.value}"/>
            <c:if test="${prop.key != 'brand' and fn:length(prop.values) > 1}">
                var prop${j.index} = new Array();
                <c:forEach var="value" items="${prop.values}">
                    <c:set var="pv" value="${prop.key}:${value.key}"/>
                    prop${j.index}.push({id:'${pv}',name:'${fn:escapeXml(value.name)}'})
                </c:forEach>
                props.p${j.index}=prop${j.index};
            </c:if>
        </c:forEach>

        var option ={
            cats:cats,
            brands:brands,
            props:props
        }
        function selectBrand(obj,prop,index) {
            $(".brandSelect").html("");
            if(prop == 'cats'){
                var catsList = option[prop];
                $.each(catsList,function(i,e){
                    $(".brandSelect").append('<a href="javascript:void(0);" data-id='+ e.id+'>'+e.name+'</a>')
                })
            }else if(prop == 'brands'){
                var brandsList = option[prop];
                $.each(brandsList,function(i,e){
                    $(".brandSelect").append('<a href="javascript:void(0);" data-id='+ e.id+'>'+e.name+'</a>')
                })
            }else{
                var props = option[prop];
                var propsList = props[index];
                $.each(propsList,function(i,e){
                    $(".brandSelect").append('<a href="javascript:void(0);" data-id='+ e.id+'>'+e.name+'</a>')
                })
            }
            $(".brandSelect a").live("click",function(){
                var thisText = $(this).text();
                var thisId = $(this).data("id");
                $("#show2").show().siblings().hide();
                var p = $(flag).find(".textTitle");
                p.text(thisText);
                p.data('id',thisId);
                if(p.hasClass("cat")){
                    $("#cat").val(thisId);
                }
                if(p.hasClass("brand")){
                    $("#brand").val(thisId);
                }
            });

            $("#show3").show().siblings().hide();
            flag =obj;
        }
        var member= ${not empty member};
      /*  $(".cartIcon,.iconAddCart").click(
                cartIconClick(obj);
        });*/

        function cartIconClick(obj){
            var gId = $(obj).attr("g-id");
            var flag = member;
            var url = "";
            if (flag) {
                url = contextPath + "/cart/saveAsync?goodsId=" + gId + "&buyCount=1";
            } else {
                url = contextPath + "/cookieCart/saveAsync?goodsId=" + gId + "&buyCount=1";
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
        $(function () {
            //过滤按钮添加样式
            $(".filterBtns a,.proLabelFilter a").not( $(".filterBtns a")[3] ).click(function () {
                $(this).addClass("on").siblings().removeClass("on");
            });
            $(".filter,.head .pageTitle .back2").click(function () {
                $("#show2").show().siblings().hide();
            });
            $(".bradFilter ,.typeFilter").click(function () {
                $("#show3").show().siblings().hide();
            });
            $(".head .pageTitle .back1").click(function () {
                $("#show1").show().siblings().hide();
            });
            $(".handelBtns input[type=button]").click(function(){
                var kv = $('#kw').val();
                var cv = $('#cat').val();
                var bv = $('#brand').val();
                var url ="${ctx}/mobile/search?kw="+kv;
                if(cv){
                    url+="&cat="+cv
                }
                if(bv){
                    url+="&brand="+bv
                }
                var label = $(".labelBtns a.on").data("label");
                url+="&label="+label;
                if($(".prop").length>0) {
                    $.each($(".prop"), function (i, e) {
                        var pp = $(this).data("id")
                        url += "&ppath=" + pp;
                    })
                }else{
                    $.each($(".sel-ppath"),function(){
                        var pp=$(this).val()
                        url += "&ppath=" + pp;
                    });
                }
                window.location.href=url;
            })
            //重置按钮
            $(".handelBtns input[type=reset]").click(function () {
                $(".proLabelFilter .labelBtns a:first-child").addClass("on").siblings().removeClass("on");
                $(".bradFilter .textTitle").text("全部");
                $(".typeFilter .textTitle").text("全部");
                var kv = $('#kw').val();
                window.location.href='${ctx}/mobile/search?kw='+kv;
            });
            //瀑布流代码
            /*$(window).scroll(function () {
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
            });*/
            //瀑布流代码
            var page=2;
            var pageSize=6;
            var totalPage=${totalPages};
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
                    var start = location.href.indexOf('?');
                    var str = location.href.substring(start+1)
                    str+="&page="+page+"&size="+pageSize
                    $(".load").css({opacity: 1});
                    $.ajax({
                        url:"${ctx}/mobile/search/list",
                        type:"get",
                        data: str,
                        traditional: true,
                    }).done(function(result){
                        var data = result.results;
                        if(data){
                            $.each(data,function(i,e){
                                var a={
                                    id: e.id,
                                    title: e.title,
                                    image: e.image,
                                    price: e.price.toFixed(2),
                                    label: e.labelPath
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
