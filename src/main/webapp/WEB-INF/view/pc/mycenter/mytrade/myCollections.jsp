﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的收藏</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
     <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/pc/common.css">
    <link rel="stylesheet" href="${ctx}/css/pc/base.css">
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <script src="${ctx}/js/pc/modernizr-2.6.2.min.js"></script>
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/pc/head.jspf"%>
        <!-- //head -->
       <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx }/">首页</a><span class="forGt"> &gt;</span><span>我的关注</span><span class="forGt"> &gt;</span><span>我的收藏</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                 <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>
                <c:if test="${fn:length(collects.data)>0}">
                <div class="rightWrap fr">
                    <div class="sameTitle">我的收藏</div>
                    <!--<div class="emptyList clearfix">
                        <img src="../../../images/emptyImg.png" width="160" height="160" class="fl" />
                        <ul class="fl emptyUl">
                            <li>您还没有收藏任何商品，快去寻找心仪的全球好货吧~</li>
                            <li><a href="#">去购物>></a></li>
                        </ul>
                    </div>-->
                    <table class="collectionsTable">
                        <tr>
                            <th class="chooseAll" width="84">
                                <input id="checkAll" type="checkbox" /> 全选
                            </th>
                            <th width="446">商品信息</th>
                            <th width="275">价格</th>
                            <th class="operation" width="275">操作</th>
                        </tr>
                        <c:forEach items="${collects.data}" var="c">
                            <tr>
                                <td class="chooseAll">
                                    <input name="subBox" type="checkbox" g-id="${c.goods.id}"/>
                                </td>
                                <td class="proInfor">
                                    <a href="${ctx}/detail/${c.goods.id}">
                                        <img src="${ctx}/${c.goods.image}">
                                        <p class="proTitle overflow">${c.goods.title}</p>
                                    </a>
                                </td>
                                <td class="price">￥ <fmt:formatNumber value="${c.goods.promotePrice}" type="currency" pattern="0.00"/></td>
                                <td class="operation">
                                    <a id="addCart" class="addCart addToCart" onclick="javascript:void(0);" g-id="${c.goods.id}" g-goodsFrom="${c.goodsFrom}" g-otherId="${c.otherId}" <%--target="_blank" href="${ctx}/cart/save?goodsId=${c.goods.id}&buyCount=1"--%>>加入购物车</a>
                                    <span class="cancelCollect" g-id="${c.goods.id}">取消收藏</span>
                                </td>
                            </tr>
                            </c:forEach>
                        <!-- <tr>
                            <td class="chooseAll">
                                <input name="subBox" type="checkbox" />
                            </td>
                            <td class="proInfor">
                                <a href="../../product/productDetail.html">
                                    <img src="../../../images/myCentershaoppingImg.jpg">
                                    <p class="proTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</p>
                                </a>
                            </td>
                            <td class="price">￥ 109.00</td>
                            <td class="operation">
                                <span class="addCart">加入购物车</span>
                                <span class="cancelCollect">取消收藏</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="chooseAll">
                                <input name="subBox" type="checkbox" />
                            </td>
                            <td class="proInfor">
                                <a href="../../product/productDetail.html">
                                    <img src="../../../images/myCentershaoppingImg.jpg">
                                    <p class="proTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</p>
                                </a>
                            </td>
                            <td class="price">￥ 109.00</td>
                            <td class="operation">
                                <span class="addCart">加入购物车</span>
                                <span class="cancelCollect">取消收藏</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="chooseAll">
                                <input name="subBox" type="checkbox" />
                            </td>
                            <td class="proInfor">
                                <a href="../../product/productDetail.html">
                                    <img src="../../../images/myCentershaoppingImg.jpg">
                                    <p class="proTitle overflow">[日本 · 给眼睛做个蒸汽SPA] KIRIBAI 桐灰化学舒缓眼部天然红豆蒸汽眼罩</p>
                                </a>
                            </td>
                            <td class="price">￥ 109.00</td>
                            <td class="operation">
                                <span class="addCart">加入购物车</span>
                                <span class="cancelCollect">取消收藏</span>
                            </td>
                        </tr> -->
                        <tr>
                            <td colspan="4" class="operationBox">
                                <span class="addCart addCartMore">加入购物车</span>
                                <span class="cancelMore">取消收藏</span>
                            </td>
                        </tr>
                    </table>
                    <!-- 分页 -->
                    <form id="pagination-form" class="pagination-form">
	                	<m:pagination totalPages="${collects.totalPages}" pageParam="page" skip="false"/>
	                </form>
                </div>
              </c:if>
               <!--我的收藏(空)-->
                    <c:if test="${fn:length(collects.data) eq 0}">
		                 <div class="rightWrap fr">
		                    
		                    <div class="sameTitle">我的收藏</div>
		
		                    <div class="emptyList clearfix">
		                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
		                        <ul class="fl emptyUl">
		                            <li>您还没有收藏任何商品，快去寻找心仪的全球好货吧~</li>
		                            <li><a href="${ctx}/search">去购物>></a></li>
		                        </ul>
		                    </div>
		
		                </div>
                    </c:if>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   </div>
    <!-- //wrap -->
     <script src="${ctx}/js/lib/layer/layer.js"></script>
    <script type="text/javascript">

    $(function () {
        $("#checkAll").click(function () {
            $('input[name="subBox"]').attr("checked", this.checked);
        });
        var $subBox = $("input[name='subBox']");
        $subBox.click(function () {
            $("#checkAll").attr("checked", $subBox.length == $("input[name='subBox']:checked").length ? true : false);
        });



        //取消选中的收藏 piliang
        $(".cancelMore").click(function () {
            var trList = $(this).parents(".collectionsTable").find(":input[checked]").parents("tr");

            var id = [];
            $.each(trList,function(i,e){
                var g = $(this).find("input").attr("g-id");
                if(g){
                    id.push(g);
                }
            });
            if (id.length == 0) {
                layer.msg('请选择要取消的商品',{ time: 1500}, function(){});
                return false;
            };
            layer.confirm("确认取消收藏？", {
                btn: ["确定", "取消"]
            }, function () {
            $.ajax({
                url: '${ctx}/person-center/remove-Selected',
                data: {"id": id},
                dataType: "json",
                type: "POST",
                traditional: true,
            }).done(function (data) {
                if(data.success){
                    $(".cancelMore").parents(".collectionsTable").find(":input[checked]").parents("tr").remove();
                    layer.msg('取消收藏成功！',{ time: 1500}, function(){
                        location.reload();
                    });
                }else{
                    if(!data.message){
                        window.location.href="${ctx}/login"
                    }else{
                        layer.msg('取消收藏失败！',{ time: 1500}, function(){
                            location.reload();
                        });
                    }
                }
            }).fail(function (data) {
                layer.msg('取消收藏失败！',{ time: 1500}, function(){
                    location.reload();
                });
            })
            });
        });


        //取消收藏
        $(".cancelCollect").click(function () {
            var $this = $(this);
            var gId = $(this).attr("g-id");
            layer.confirm("确认取消收藏？", {
                btn: ["确定", "取消"]
            }, function () {
                $.ajax({
                    type: "POST",
                    url: '${ctx}/person-center/remove-collect',
                    data: "goodsid="+gId,
                }).done(function (data) {
                    if(data.success){
                       
                        $this.parents("tr").remove();
                        layer.msg('取消收藏成功！',{ icon:1}, function(){
                            location.reload();
                        });
                    }else{
                        if(!data.message){
                            window.location.href="${ctx}/login"
                        }else{
                            layer.msg('取消收藏失败！',{ icon:1}, function(){
                                location.reload();
                            });
                        }
                    }
                }).fail(function (data) {
                    layer.msg('取消收藏失败',{ icon:1}, function(){
                        location.reload();
                    });
                });
            });
        });

        /*//加入购物车
        $(".addCart").click(function () {
            debugger;
            var trList = $(this).parents(".collectionsTable").find(":input[checked]").parents("tr");
            var tagName = $(this)[0].tagName.toLowerCase();
            if(tagName=="a"){
                return true;
            }
            if(trList.length<1){
                return false;
            }
            var shref="${ctx}/cart/save?"
            $.each(trList,function(i,e){
                var g = $(this).find("input").attr("g-id");
                if(g){
                    shref+="&goodsId="+g+"&buyCount=1";
                }

            });
            window.open(shref);
        });*/


        //批量加入购物车
        $(".addCartMore").click(function () {
            var trList = $(this).parents(".collectionsTable").find(":input[checked]").parents("tr");
            if (trList.length == 0) {
                layer.msg('请选择要加入购物车的商品',{ time: 1500}, function(){});
                return false;
            };
            var shref = "${ctx}/cart/saveAsync?";
            $.each(trList,function(i,e){
                var g = $(this).find("input").attr("g-id");
                if(g){
                    shref+="goodsId="+g+"&buyCount=1&";
                }
            });
            layer.confirm("确认加入购物车？", {
                btn: ["确定", "取消"]
            }, function () {
                $.ajax({
                    url: shref,
//                    data: {"id": id},
                    dataType: "json",
                    type: "POST",
                    traditional: true,
                }).done(function (data) {
                    if(data.success){
                        $(".addCartMore").parents(".collectionsTable").find(":input[checked]").parents("tr").remove();
                        layer.msg('添加购物车成功！',{ time: 1500}, function(){
                            location.reload();
                        });
                    }else{
                        if(!data.message){
                            window.location.href="${ctx}/login"
                        }else{
                            layer.msg('操作失败！',{ time: 1500}, function(){
                                location.reload();
                            });
                        }
                    }
                }).fail(function (data) {
                    layer.msg('请求失败！',{ time: 1500}, function(){
                        location.reload();
                    });
                })
            });
        });
        $(".addToCart").click(function(){

            var url="${ctx}/cart/saveAsync?goodsId="+$(this).attr("g-id")+"&buyCount="+1+"&goodsFrom="+$(this).attr("g-goodsFrom")+"&otherId="+$(this).attr("g-otherId");

         /*   if(clicked){
                return false;
            }
            clicked =true;*/

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
        });
    });
</script>
   
</body>
</html>
