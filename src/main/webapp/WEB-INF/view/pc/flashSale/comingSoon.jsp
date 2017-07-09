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
    <title>首页-${seo.title}</title>
	<meta name="keywords" content="${seo.keyWords}">
	<meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>

    <link href="${ctx}/css/pc/comingSoon.css" rel="stylesheet"/>
    <c:set var="nav" value="1"/>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
        <div class="main">
            <div class="bannerSlide">
	           <c:forEach items="${flowBanners}" var="flowBanner">
	                    <a href="${flowBanner.url}" style="background:url(${ctx}/${flowBanner.image}) center no-repeat;"></a>
	           </c:forEach>
        	 </div> 
            <div class="comingSoonListNav samewidth">
                <ul>
                    <li><a href="${ctx }/timed-panic-buying/index">正在进行</a></li>
                    <li class="changeBgc"><a href="${ctx }/timed-panic-buying/index?type=1">即将开始</a></li>
                </ul>
            </div>
            <div class="comingSoonList  samewidth clearfix">
                <c:if test="${not empty discountGoodses.data}">
	                <c:forEach items="${discountGoodses.data}" var="discount">
		                <dl>
		                    <dt><a href="${ctx}/detail/${discount.goods.id}"><img src="${ctx}/${discount.goods.image}_230x252" width="230" height="252" /></a></dt>
		                    <dd class="twoHeight"><a href="${ctx}/detail/${discount.goods.id}">${discount.goods.title}</a></dd>
							<dd class="sucTitle">${discount.goods.intro}</dd>
		                    <dd class="addHeight">
		                        <span class="countLeft"><b>${discount.discount} </b> 折</span><span class="fr">销量${discount.goods.product.sales}件</span>
		                    </dd>
		                    <dd class="nowPrice">现&emsp;&emsp;价：￥<fmt:formatNumber value="${discount.goods.promotePrice}" type="currency" pattern="0.00"/></dd>
		                    <dd class="marketPrice">市&ensp;场&ensp;价：￥<fmt:formatNumber value="${discount.goods.price}" type="currency" pattern="0.00"/></dd>
		                     <dd class="countDown">
		                        <i></i>
		                        <em class="time" data-endtime="<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${discount.startDate}" type="both"/>"></em>
		                    </dd>
		                    <dd class="mask"><img src="${ctx }/images/pc/comingSoonshadow.png" alt="遮罩层" /></dd>
		                </dl>
	                </c:forEach>
                </c:if>
                
                <c:if test="${empty discountGoodses.data}">
	                <div style="margin: 61px 31px;font-size: 18px;color: #666;line-height: 29px;">
	                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
	                        <ul class="fl emptyUl">
	                            <li>暂无商品~</li>
	                            <!-- <li><a href="#">去购物>></a></li> -->
	                        </ul>
	                </div>
                </c:if>
                <%-- <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="${ctx }/images/pc/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl>
                <dl>
                    <dt><a href="../product/productList.html"><img src="../../images/img13.jpg" alt="玉米" /></a></dt>
                    <dd><a href="../product/productList.html">玉米家常小炒，快捷方便</a></dd>
                    <dd class="addHeight">
                        <span class="countLeft"><b>2.2 </b> 折</span><span class="countRight">销量1424件</span>
                    </dd>
                    <dd class="nowPrice">现&nbsp;&nbsp;价：￥100</dd>
                    <dd class="marketPrice">市场价：￥500</dd>
                    <dd class="countDown"><i></i><span>剩余2天23时8分</span></dd>
                    <dd class="mask"><img src="../../images/comingSoonshadow.png" alt="遮罩层" /></dd>
                </dl> --%>
            </div>
            <!-- 分页 -->
            <c:if test="${not empty discountGoodses.data}">
		            <form id="pagination-form" class="pagination-form">
		                <m:pagination totalPages="${discountGoodses.totalPages}" pageParam="page" skip="false"/>
		            </form>
            </c:if>  
            <!-- /分页 -->

        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script src="${ctx}/js/pc/jquery.slides.min.js"></script>
    <script src="${ctx}/js/pc/Tab.js"></script>
    <script src="${ctx}/js/pc/jquery.glanway.timeUp.1.0.js"></script>
    <script>
    $(".time").timeUp({
        "template": "剩余%d天%h时%m分",
        "end": function (obj) {
            alert(obj.html() + " 结束啦");
        }
    });
    
    $(function () {
    	  //导航轮播
    	    if ($(".bannerSlide").children().length > 1) {
    	        $(".bannerSlide").slidesjs({
    	            width: 1526,
    	            height: 500,
    	            navigation: false,
    	            play: {
    	                auto: true,
    	                restartDelay: 2500
    	            },
    	            callback: {
    	                complete: function (number) {
    	                    clearTimeout(slideTimer)
    	                    var slideTimer = setTimeout(function () {
    	                        $(".slidesjs-play").click();
    	                    }, 4000)
    	                }
    	            }
    	        });
    	        //导航点居中
    	        var pagination = $(".bannerSlide").find(".slidesjs-pagination li").size() * 30;
    	        $(".bannerSlide").find(".slidesjs-pagination").css("margin-left", "-" + pagination / 2 + "px");
    	    };
    	    });
    </script>
</body>
</html>
