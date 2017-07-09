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
    <title>优惠劵</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/coupon.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="notShowTop"/>
        <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
        <div class="main">
            <input type="hidden" id="saleCoupon_hidden_type" value="${type}">
            <div class="coupon samePadding">
                <div class="couponTop clearfix">
                    <div class="couponTLeft <c:if test="${type==0 }">on</c:if>" onclick="quertCoupon(0)">
                        <i class="expired1"></i>
                        <p class="hi5">有效期内</p>
                        <p class="hi4">${notUsed }</p>
                    </div>
                    <div class="couponTRight <c:if test="${type==1 }">on</c:if>"  onclick="quertCoupon(1)">
                        <i class="expired2"></i>
                        <p class="hi5">已过期</p>
                        <p class="hi4">${overdue }</p>
                    </div>
                </div>
                <div class="couponText">
                    <div class="cTleft" style="display:block">
                    <c:if test="${type==0 }">
                    <c:forEach items="${_conpons.data}" var="coupon">
                        <table border="1">
                            <tr>
                                <td>优惠劵编码</td>
                                <td>${coupon.code}</td>
                            </tr>
                            <tr>
                                <td>优惠券名称</td>
                                <td>${coupon.couponName}</td>
                            </tr>
                            <tr>
                                <td>优惠方案</td>
                                <td>${coupon.discount}元</td>
                            </tr>
                            <tr>
                                <td>规则</td>
                                <td>满${coupon.minPrice}减${coupon.discount}</td>
                            </tr>
                            <tr>
                                <td>使用日期</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.beginDate}" type="both"/>~<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.endDate}" type="both"/></td>
                            </tr>
                        </table>
                        </c:forEach>
                       </c:if>
                    </div>
                    <div class="cTright" style="display:none">
                        <c:if test="${type==1 }">
                    <c:forEach items="${_conpons.data}" var="coupon">
                        <table border="1">
                            <tr>
                                <td>优惠劵编码</td>
                                <td>${coupon.code}</td>
                            </tr>
                            <tr>
                                <td>优惠券名称</td>
                                <td>${coupon.couponName}</td>
                            </tr>
                            <tr>
                                <td>优惠方案</td>
                                <td>${coupon.discount}元</td>
                            </tr>
                            <tr>
                                <td>规则</td>
                                <td>满${coupon.minPrice}减${coupon.discount}</td>
                            </tr>
                            <tr>
                                <td>使用日期</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.beginDate}" type="both"/>~<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${coupon.endDate}" type="both"/></td>
                            </tr>
                        </table>
                        </c:forEach>
                       </c:if>
                    </div>
                </div>
            </div>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        <div class="errorLabel"></div>
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
    <script>
        window.contextPath="${ctx}";
    </script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/common.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/jquery.validate.js"></script>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script>
        $(document).ready(function () {
            $(".couponTop div").click(function () {
                $(".couponTop div").eq($(this).index()).addClass("on").siblings().removeClass('on');
                $(".couponText div").hide().eq($(this).index()).show();
            });
        });
        $(document).ready(function () {
            $("table tr td:even").css({ background: "#fafafa", width: "25%", textAlign: "center" });
            $("table tr td:odd").css({ paddingLeft: "0.25rem" });
        });

        $(".cTleft,.cTright").on("click", "table", function () {
            $(this).parents(".cTleft,.cTright").find("table tr td:even").css({ background: "#fafafa", width: "25%", textAlign: "center" });
            $(this).parents(".cTleft,.cTright").find("table tr td:odd").css({ paddingLeft: "0.25rem" });
        });
        $(function () {
            $(window).scroll(function () {
                $(".cTleft table,.cTright table").trigger("click");
            });
        });
        function quertCoupon(type){
        	window.location.href="${ctx}/mobile/person-center/userMessages/saleCoupon?type="+type+"";
        }



        var page=2;
        var pageSize=10;
        var totalPage=${_conpons.totalPages};
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

                if(!flag || page>totalPage ){
                    $(".load").hide();
                    return false;
                }
                $(".load").css({opacity: 1});
                $.ajax({
                    url:"${ctx}/mobile/person-center/userMessages/saleCoupon/list",
                    type:"get",
                    data: {page:page,size:pageSize},
                    traditional: true,
                }).done(function(result){
                    var data = result.rows;
                    if(data){
                        $.each(data,function(i,e){
                            var a={
                                id: e.id,
                                code: e.code,
                                couponName: e.couponName,
                                discount: e.discount,
                                minPrice: e.minPrice,
                                beginDate:new Date(e.beginDate).Format("yyyy.MM.dd"),
                                endDate:new Date(e.endDate).Format("yyyy.MM.dd"),
                            }

                            //获取到屏幕顶部距离
                            var loadheight = $(".load").offset().top - $(window).scrollTop() + $(".load").height();
                            //获取屏幕高度
                            var screenheight = $(window).height();

                            /*  if (loadheight < screenheight) {
                             var html=bt('moreList', a);
                             $(".productList").append(html);
                             } */
                            var html=bt('moreList', a);
                            if($("#saleCoupon_hidden_type").val()==0){
                                //有效期内
                                $(".cTleft").append(html);
                            }else if($("#saleCoupon_hidden_type").val()==1){
                                //已过期
                                $(".cTright").append(html);
                            }


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

        Date.prototype.Format = function(fmt)
        {
            var o = {
                "M+" : this.getMonth()+1,                 //月份
                "d+" : this.getDate(),                    //日
                "h+" : this.getHours(),                   //小时
                "m+" : this.getMinutes(),                 //分
                "s+" : this.getSeconds(),                 //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S"  : this.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }
    </script>
    <script id="moreList" type="text/html">

        <table border="1">
            <tr>
                <td>优惠劵编码</td>
                <td><!=code!></td>
            </tr>
            <tr>
                <td>优惠券名称</td>
                <td><!=couponName!></td>
            </tr>
            <tr>
                <td>优惠方案</td>
                <td><!=discount!>元</td>
            </tr>
            <tr>
                <td>规则</td>
                <td>满<!=minPrice!>减<!=discount!></td>
            </tr>
            <tr>
                <td>使用日期</td>
                <td><!=beginDate!>~<!=endDate!></td>
            </tr>
        </table>
    </script>
</body>
</html>
