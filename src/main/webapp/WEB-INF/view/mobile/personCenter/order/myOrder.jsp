<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>我的订单</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/myOrder.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <c:set value="我的订单" var="common"/>
    <c:set value="mobile/person-center/index" var="href"/>
    <c:set value="true" var="notShowTop"/>
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
    <div class="main">
        <div class="orderDetail font24">
            <!--订单类型切换按钮-->
            <div class="myOrd">
                <a href="javascript:void(0);" status="-1"  <c:if test="${param.status == -1 || null == param.status}"> class="on" </c:if>  id="all" onclick="searchOrderByStatus(this)">全部</a>
                <a href="javascript:void(0);" status="1" <c:if test="${param.status == 1}"> class="on" </c:if> id="dfk" onclick="searchOrderByStatus(this)">待付款</a>
                <a href="javascript:void(0);" status="3" <c:if test="${param.status == 3}"> class="on" </c:if> id="dfh" onclick="searchOrderByStatus(this)">待发货</a>
                <a href="javascript:void(0);" status="4" <c:if test="${param.status == 4}"> class="on" </c:if> id="dsh" onclick="searchOrderByStatus(this)">待收货</a>
                <a href="javascript:void(0);" status="6" <c:if test="${param.status == 6}"> class="on" </c:if> id="ywc" onclick="searchOrderByStatus(this)">已完成</a>
            </div>
            <!--为空提示-->
            <!--<div class="orderNull">您暂时还没有订单！</div>-->
            <!--产品介绍分类-->
            <c:choose>
                <c:when test="${fn:length(orderList.data) > 0}">
                    <c:forEach items="${orderList.data}" var="order" varStatus="i">
                        <div class="aOrder">

                            <div class="storeMessage messageForMargin writeWrap">
                                <%--<p class="storeName font24">店铺：<span>${order.storeName}</span></p>--%>
                                <div class="orderMessage font24 clearfix">
                                    <div class="orderNum fleft">
                                        <input type="hidden" class="orderId" value="${order.id}">
                                        <span>订单编号：</span><a href="${ctx}/mobile/myCenter/order/orderDetails/${order.id}">${order.code}</a>
                                    </div>
            <span class="fright storeOrderState">
                 <c:choose>
                     <c:when test="${order.status == 1}">待付款</c:when>
                     <c:when test="${order.status == 2}">已付款</c:when>
                     <c:when test="${order.status == 3}">待发货</c:when>
                     <c:when test="${order.status == 4}">待收货</c:when>
                     <c:when test="${order.status == 5}">已确认收货</c:when>
                     <c:when test="${order.status == 6}">交易完成</c:when>
                     <c:when test="${order.status == 7}">交易取消</c:when>
                     <c:otherwise> </c:otherwise>
                 </c:choose>
            </span>
                                </div>
                            </div>
                            <div>
                                    <c:set var="ordertotalNum" value="0"/>
                                <c:forEach var="orderDetail" items="${order.orderDetails}">
                                    <c:set var="ordertotalNum" value="${ordertotalNum + orderDetail.goodsNum}"/>
                                    <div class="evaledItem writeWrap">
                                        <div class="evalTop clearfix">
                                            <a href="${ctx}/mobile/detail/${orderDetail.goodsId}" class="fleft"><img src="${ctx}/${orderDetail.goodsImage}" /></a>
                                            <div class="evalRight fright">
                                                <a href="${ctx}/mobile/detail/${orderDetail.goodsId}" class="overflow">${orderDetail.goodsName}</a>
                                                <p><span style="display: inline-block;width: 4rem;overflow: hidden;height: .6rem;">${orderDetail.goodsSpec}</span></p>
                                            </div>
                                        </div>
                                        <c:if test="${order.status == 6 &&  orderDetail.commented != true  }">
                                            <a href="${ctx}/mobile/person-center/comment" class="goEval">评价</a>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </div>
                            <ul class="orderMoney writeWrap font24">
                                <li>共<span></span>件${ordertotalNum}商品，合计<span>${order.price}</span>元</li>
                                <li class="clearfix">
                                    <c:choose>
                                        <c:when test="${order.status == 1}">
                                            <a href="javascript:void(0)" class="seeExpress forBorderColor fright pay">立即付款</a>
                                            <a href="javascript:" class="seeExpress forBorderColor fright" onclick="cancleOrder(this)" >取消订单</a>
                                        </c:when>
                                        <c:when test="${order.status == 2}"></c:when>
                                        <c:when test="${order.status == 3}"></c:when>
                                        <c:when test="${order.status == 4}">
                                            <a href="javascript:void(0)" class="seeExpress forBorderColor fright" onclick="receiveOrder(this)">确认收货</a>
                                            <a href="${ctx}/mobile/myCenter/order/expressDetail/${order.id}" class="seeExpress forBorderColor fright">查看物流</a>
                                        </c:when>
                                        <c:when test="${order.status == 5}">
                                            <a href="javascript:;" onclick="finishOrder(this)" class="seeExpress forBorderColor fright">交易完成</a>
                                            <a href="${ctx}/mobile/myCenter/order/expressDetail/${order.id}" class="seeExpress forBorderColor fright">查看物流</a>
                                        </c:when>
                                        <c:when test="${order.status == 6 || order.status == 7}">
                                            <a href="javascript:" class="seeExpress forBorderColor fright" onclick="deleteOrder(this)" >删除</a>
                                            <c:if test="${order.status == 6}">
                                                <a href="${ctx}/mobile/myCenter/order/expressDetail/${order.id}" class="seeExpress forBorderColor fright">查看物流</a>
                                            </c:if>

                                        </c:when>
                                        <c:otherwise> </c:otherwise>
                                    </c:choose>

                                </li>
                            </ul>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="margin: 2rem 42%">暂无订单！</div>
                </c:otherwise>
            </c:choose>
        </div>
        <!--加载更多-->
        <div class="load">正在加载...</div>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<!--左侧导航-->
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
<script id="moreList" type="text/html">
    <div class="aOrder">
            <div class="storeMessage messageForMargin writeWrap">
          <%--  <p class="storeName font24">店铺：<span><!=order.storeName!></span></p>--%>
                <div class="orderMessage font24 clearfix">
            <div class="orderNum fleft">
            <input type="hidden" class="orderId" value="<!=order.id!>">
            <span>订单编号：</span><a href="${ctx}/mobile/myCenter/order/orderDetails/<!=order.id!>"><!=order.code!></a>
            </div>
            <span class="fright storeOrderState">
            <! if(order.status == 1){!>
                待付款
            <!}else if(order.status == 2){!>已付款
           <!} else if(order.status == 3){!>待发货
            <!}else if(order.status == 4){!>待收货
            <!}else if(order.status == 5){!>已确认收货
            <!}else if(order.status == 6){!>交易完成
            <!}else if(order.status == 7){!>交易取消
            <!}else{!><!}!>
    </span>
    </div>
    </div>
    <div>
   <!for(var i=0; i< order.orderDetails.length; i++){!>
    <div class="evaledItem writeWrap">
            <div class="evalTop clearfix">
            <a href="<!=ctx!>/mobile/detail/<!=order.orderDetails[i].goodsId!>" class="fleft"><img src="<!=ctx!>/<!=order.orderDetails[i].goodsImage!>" /></a>
            <div class="evalRight fright">
            <a href="<!=ctx!>/mobile/detail/<!=order.orderDetails[i].goodsId!>" class="overflow"><!=order.orderDetails[i].goodsName!></a>
            <p><span style="display: inline-block;width: 4rem;overflow: hidden;height: .6rem;"><!=order.orderDetails[i].goodsSpec!></span></p>
            </div>
            </div>
            <!if(order.status == 6 && !order.orderDetails[i].commented){!>
                <a href="<!=ctx!>/mobile/person-center/comment" class="goEval">评价</a>
            <!}!>
            </div>
            <!}!>
           <ul class="orderMoney writeWrap font24">
            <li>共<span><!=totalNum!></span>件商品，合计<span><!=order.price!></span>元</li>
    <li class="clearfix">
            <!if(order.status == 1){!>
            <a href="javascript:void(0)" class="seeExpress forBorderColor fright pay">立即付款</a>
            <a href="javascript:" class="seeExpress forBorderColor fright" onclick="cancleOrder(this)" >取消订单</a>
        <!}else if(order.status==4){!>
            <a href="javascript:void(0)" class="seeExpress forBorderColor fright" onclick="receiveOrder(this)">确认收货</a>
            <a href="<!=ctx!>/mobile/myCenter/order/expressDetail/<!=order.id!>" class="seeExpress forBorderColor fright">查看物流</a>
        <!}else if(order.status == 5) { !>
            <a href="javascript:;" onclick="finishOrder(this)" class="seeExpress forBorderColor fright">交易完成</a>
            <a href="<!=ctx!>/mobile/myCenter/order/expressDetail/<!=order.id!>" class="seeExpress forBorderColor fright">查看物流</a>
        <!}else if(order.status == 6 || order.status == 7){ !>
            <a href="javascript:void(0)" class="seeExpress forBorderColor fright" onclick="deleteOrder(this)" >删除</a>
                 <!if(order.status == 6){!>
                    <a href="<!=ctx!>/mobile/myCenter/order/expressDetail/<!=order.id!>" class="seeExpress forBorderColor fright">查看物流</a>
                <!}!>
            <!}else{!><!}!>
            </li>
            </ul>

            </div>
</script>
<script>
    $(function () {

        //头部切换按钮添加样式
        $(".myOrd a").click(function () {
            $(this).addClass("on").siblings().removeClass("on");
        });

        $("a.pay").on('click', function () {
            payOrder(this);
        });
    });
    //订单支付
    function payOrder(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        window.location.href = contextPath + '/order/selectPayment?id=' + orderId;
    }
    //取消订单
    function cancleOrder(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
//        var obj = this;
        layer.open({
            content: '确定取消选中订单吗？',
            btn: ['确定', '取消'],
            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            yes: function () {
                window.location.href = contextPath + '/mobile/myCenter/order/cancleOrder/' + orderId;
//                layer.msg("取消成功！", {icon: 1});
            },
            no:function(){}
        });
    }
    //申请退换货
    function returnOrder(obj) {
        //var $this = $(event.srcElement);
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        window.location.href = contextPath + '/mobile/myCenter/returned/returnApply/' + orderId;
    }
    //确认收货
    function receiveOrder(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        window.location.href = contextPath + '/mobile/myCenter/order/receive/' + orderId;
    }
    //交易完成
    function finishOrder(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        window.location.href = contextPath + '/mobile/myCenter/order/finish/' + orderId;
    }
    //删除订单
    function deleteOrder(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        layer.open({
            content: '确定删除选中订单吗？',
            btn: ['确定', '取消'],
            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            yes: function () {
//                layer.open({content: '删除成功！', time: 1, style: 'font-family:Microsoft YaHei'});
                window.location.href = contextPath + '/mobile/myCenter/order/deleteOrder/' + orderId;
            },
            no:function(){}
        });
    }
    //订单详情
    function orderDetails(obj) {
        var $this = $(obj);
        var orderId = $this.closest(".aOrder").find(".orderId").val();
        window.location.href = contextPath + '/mobile/myCenter/order/orderDetails/' + orderId;
    }
    //根据商品ID跳转至商品详情
    function toDetailsByGoodsId(obj) {
        var $this = $(obj);
        var goodsId=$this.parent().find('.goodsId').val()
        window.location.href = contextPath +'/mobile/detail/'+goodsId;
    }
    //按条件搜索订单
    function searchOrder(type) {
        var para = "";
        var beginDate;
        var endDate;
        if (1 == type) {
            para = $("#keywordId").val();
        } else if (2 == type) {
            beginDate = $("#beginDate").val();
            endDate = $("#endDate").val();
        }
        var status = $('.navigationText').find('.active').attr('status');
        var onId = $('.navigationText').find('.active').attr('id')
        window.location.href = contextPath + '/mobile/myCenter/order/orderList?type=' + (!type ? -1 : type) + "&onId=" + onId + '&status=' + (!status ? -1 : status) + '&para=' + (!para ? "" : para) + '&beginDate=' + (!beginDate ? "" : beginDate) + '&endDate=' + (!endDate ? "" : endDate);
    }
    //按状态搜索订单
    function searchOrderByStatus(obj) {
        var $this = $(obj);
        var status = $this.attr('status');
        window.location.href = contextPath + '/mobile/myCenter/order/orderList?status=' + (!status ? -1 : status) + "&onId=" + $this.attr('id');
    }
    function dateFormat(format, date) {
        var o = {
            "M+": date.getMonth() + 1, //month
            "d+": date.getDate(), //day
            "h+": date.getHours(), //hour
            "m+": date.getMinutes(), //minute
            "s+": date.getSeconds(), //second
            "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
            "S": date.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
    $(function(){
        $(".btn_back img").off("click");
    });

    var page=2;
    var pageSize=10;
    var totalPage=${totalPages};
    var status = "${param.status}";
    var onId = "${param.onId}";
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
                url:"${ctx}/mobile/myCenter/order/orderListByAjax",
                type:"get",
                data: {page:page,size:pageSize,status:status,
                    onId:onId},
                traditional: true,
            }).done(function(result){
                var data = result.rows;
                if(data){
                    $.each(data,function(i,obj){
                      var a = {ctx:contextPath,order:obj};
                        var totalNum = 0;
                        $.each(obj.orderDetails,function(i,row){
                            totalNum += Number(row.goodsNum);
                        });
                        a.totalNum = totalNum;
                        var html=bt('moreList', a);
                        $(".orderDetail").append(html);
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
