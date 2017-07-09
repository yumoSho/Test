<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>充值记录</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<%--
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
--%>
    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <c:set value="true" var="isReg"/>
    <c:set value="订单详情" var="title"/>
    <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
    <div class="main">
        <div class="rechangeHistory samePadding">
            <div class="rechangeHistoryText">
                <div class="cTleft" style="display:block">
                    <c:forEach items="${balancePaymentDetails.data}" var="balancePaymentDetail">
                        <table border="1">
                            <tr>
                                <td>充值金额</td>
                                <td>￥${balancePaymentDetail.amount }</td>
                            </tr>
                            <tr>
                                <td>赠送金额</td>
                                <td>￥<c:if test="${balancePaymentDetail.giftAmount==null }">0.00</c:if><c:if test="${balancePaymentDetail.giftAmount!=null }">${balancePaymentDetail.giftAmount }</c:if></td>
                            </tr>
                            <tr>
                                <td>充值时间</td>
                                <td><fmt:formatDate value="${balancePaymentDetail.startTime}" type="both"/></td>
                            </tr>
                        </table>
                    </c:forEach>
                   <%-- <table border="1">
                        <tr>
                            <td>充值金额</td>
                            <td>￥1000.00</td>
                        </tr>
                        <tr>
                            <td>赠送金额</td>
                            <td>￥10.00</td>
                        </tr>
                        <tr>
                            <td>充值时间</td>
                            <td>2015-09-11 14：20</td>
                        </tr>
                    </table>
                    <table border="1">
                        <tr>
                            <td>充值金额</td>
                            <td>￥1000.00</td>
                        </tr>
                        <tr>
                            <td>赠送金额</td>
                            <td>￥10.00</td>
                        </tr>
                        <tr>
                            <td>充值时间</td>
                            <td>2015-09-11 14：20</td>
                        </tr>
                    </table>
                    <table border="1">
                        <tr>
                            <td>充值金额</td>
                            <td>￥1000.00</td>
                        </tr>
                        <tr>
                            <td>赠送金额</td>
                            <td>￥10.00</td>
                        </tr>
                        <tr>
                            <td>充值时间</td>
                            <td>2015-09-11 14：20</td>
                        </tr>
                    </table>
                    <table border="1">
                        <tr>
                            <td>充值金额</td>
                            <td>￥1000.00</td>
                        </tr>
                        <tr>
                            <td>赠送金额</td>
                            <td>￥10.00</td>
                        </tr>
                        <tr>
                            <td>充值时间</td>
                            <td>2015-09-11 14：20</td>
                        </tr>
                    </table>
                    <table border="1">
                        <tr>
                            <td>充值金额</td>
                            <td>￥1000.00</td>
                        </tr>
                        <tr>
                            <td>赠送金额</td>
                            <td>￥10.00</td>
                        </tr>
                        <tr>
                            <td>充值时间</td>
                            <td>2015-09-11 14：20</td>
                        </tr>
                    </table>--%>
                </div>
            </div>
        </div>
        <!--加载更多-->
        <div class="load">正在加载...</div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/common.js"></script>
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
</body>
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


    $(".cTleft").on("click", "table", function () {
        $(this).parents(".cTleft").find("table tr td:even").css({ background: "#fafafa", width: "25%", textAlign: "center" });
        $(this).parents(".cTleft").find("table tr td:odd").css({ paddingLeft: "0.25rem" });
    });
    $(function () {
        $(window).scroll(function () {
            $(".cTleft table").trigger("click");
        });
    });

    var page=2;
    var pageSize=10;
    var totalPage=${balancePaymentDetails.totalPages};
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
                url:"${ctx}/mobile/person-center/rechangeHistory/list",
                type:"get",
                data: {page:page,size:pageSize},
                traditional: true,
            }).done(function(result){
                var data = result.rows;
                if(data){
                    $.each(data,function(i,e){
                        var a={
                            id: e.id,
                            amount: e.amount,
                            giftAmount: e.giftAmount,
                            createdDate:new Date(e.createdDate).Format("yyyy.MM.dd.hh.mm.ss"),
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
                        $(".cTleft").append(html);

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
            <td>充值金额</td>
            <td>￥<!=amount!></td>
        </tr>
        <tr>
            <td>赠送金额</td>
            <td>￥<!=giftAmount!></td>
        </tr>
        <tr>
            <td>充值时间</td>
            <td><!=createdDate!></td>
        </tr>
    </table>
</script>
</html>
