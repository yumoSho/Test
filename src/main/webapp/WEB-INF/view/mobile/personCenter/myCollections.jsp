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
     <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="notShowTop"/>
        <div class="head">
            <div class="pageTitle headTop samePadding clearfix">
                <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <span>商品收藏</span>
                <a href="javascript:void(0);" class="editCart fright">编辑</a>
            </div>
        </div><!-- //head -->
        <div class="main">
            <div class="collectProList sameMargin">
                <div class="productList pinterest clearfix">
                     <c:forEach items="${collects.data}" var="c">
                    <div class="collectProItem">
                        <p><input type="checkbox" class="checkOneInput" name="checkOne" g-id="${c.goods.id}" g-goodsFrom="${c.goodsFrom}" g-otherId="${c.otherId}"/></p>
                        <div class="proItem writeWrap fleft">
                            <a href="${ctx}/mobile/detail/${c.goods.id}" class="proImg"><img src="${ctx}/${c.goods.image}_347x385.jpg" alt="aa" /></a>
                            <div class="proMessage">
                                <p class="proPrice"><fmt:formatNumber value="${c.goods.promotePrice}" type="currency" pattern="￥.00"/></p>
                                <a href="${ctx}/mobile/detail/${c.goods.id}" class="proName overflow">${c.goods.title}</a>
                                <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="${c.goods.id}" />
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="personlBut sureDelete" style="text-align: center">
                <a href="javascript:void(0);" id="sureDelete" class="<%--fleft--%>"><img src="${ctx}/images/mobile/deleGreen.png" />确认删除</a>
                <a href="javascript:void(0);" id="addCartMore" class="fright">加入购物车</a>
            </div>
            <!--加载更多-->
            <div class="load">正在加载...</div>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <!--左侧导航-->
    <script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/baiduTemplate.js"></script>
    <script src="${ctx}/js/mobile/common.js"></script>
    <script id="moreList" type="text/html">

		<div class="collectProItem">
                        <p><input type="checkbox" class="checkOneInput" name="checkOne" g-id="<!=id!>" g-goodsFrom="<!=goodsFrom>" g-otherId="<!=otherId>"/></p>
                        <div class="proItem writeWrap fleft">
                            <a href="${ctx}/mobile/detail/<!=id!>" class="proImg"><img src="${ctx}/<!=image!>_347x385.jpg" alt="aa" /></a>
                            <div class="proMessage">
                                <p class="proPrice">￥<!=promotePrice!></p>
                                <a href="${ctx}/mobile/detail/<!=id!>" class="proName overflow"><!=title!></a>
                                <img src="${ctx}/images/pc/proCart.jpg" class="cartIcon" g-id="<!=id!>" />
                            </div>
                        </div>
        </div>
        
    </script>
    <script>
        $(function () {
            $(".editCart").click(function () {
                if ($(this).text() === "编辑") {
                    $(this).text("完成");
                    $(".collectProList .collectProItem .checkOneInput").show();
                    $(".sureDelete").show();
                } else {
                    $(this).text("编辑");
                    $(".collectProList .collectProItem .checkOneInput").hide();
                    $(".sureDelete").hide();
                }
            });
            //删除选中
            $("#sureDelete").click(function () {
                var checkedNum = $(".collectProList  .productList  input:checked").size();
                if (checkedNum < 1) {
                    layer.open({
                        content: '请选择一个产品',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    });
                } else {
                    layer.open({
                        content: '确定删除选中的产品吗？',
                        btn: ['确定', '取消'],
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        yes: function () {
                        	/* layer.open({ content: '删除成功！', time: 1, style: 'font-family:Microsoft YaHei' });
                             $(".collectProList  .productList  input:checked").parents(".collectProItem").remove(); */
                           
                           	var trList = $(".collectProList  .productList  input:checked");
               
                var id = [];
                $.each(trList,function(i,e){
                   var g = $(this).attr("g-id");
                    id.push(g);
                });
                if (id.length == 0) {
                    return false;
                };
                $.ajax({
                    url: '${ctx}/person-center/remove-Selected',
                    data: {"id": id},
                    dataType: "json",
                    type: "POST",
                    traditional: true,
                }).done(function (data) {
                    if(data.success){
                    	 layer.open({ content: '删除成功！', time: 1, style: 'font-family:Microsoft YaHei' });
                          $(".collectProList  .productList  input:checked").parents(".collectProItem").remove(); 
                        
                       
                            location.reload();
                       
                    }else{
                        if(!data.message){
                            window.location.href="${ctx}/login"
                        }else{
                            layer.msg('取消收藏成功！',{ time: 1500}, function(){
                                location.reload();
                            });
                        }
                    }
                }).fail(function (data) {
                	layer.open({ content: '删除失败！', time: 1, style: 'font-family:Microsoft YaHei' });
                	location.reload();
                    
                })
                        }
                    });
                }
            });

            $("#addCartMore").click(function () {
               // var trList = $(this).parents(".collectionsTable").find(":input[checked]").parents("tr");
                var trList = $(".collectProList  .productList  input:checked");

                if (trList.length == 0) {
                    layer.msg('请选择要加入购物车的商品',{ time: 1500}, function(){});
                    return false;
                };
                var shref = "${ctx}/cart/saveAsync?";
                $.each(trList,function(i,e){
                    var g = $(this).attr("g-id");
                    var goodsFrom=$(this).attr("g-goodsFrom");
                    var otherId=$(this).attr("g-otherId");

                    if(g){
                        shref+="goodsId="+g+"&buyCount=1&goodsFrom="+goodsFrom+"&otherId="+otherId+"&";
                    }
                });
                debugger;
                layer.open( {
                    content: '确认加入购物车？',
                    btn: ["确定", "取消"],
                   yes: function () {
                    $.ajax({
                        url: shref,
//                    data: {"id": id},
                        dataType: "json",
                        type: "get",
                        traditional: true,
                    }).done(function (data) {
                        if(data.success){
                            $(".collectProList  .productList  input:checked").parents(".collectProItem").remove();
                            layer.open({ content: '加入成功！', time: 3000, style: 'font-family:Microsoft YaHei' });
                            location.reload();
                        }else{
                            if(!data.message){
                                window.location.href="${ctx}/login"
                            }else{
                                layer.open({ content: '加入失败！', time: 3000, style: 'font-family:Microsoft YaHei' });
                                location.reload();
                            }
                        }
                    }).fail(function (data) {
                        layer.open({ content: '请求失败！', time: 3000, style: 'font-family:Microsoft YaHei' });
                        location.reload();
                    })
                   }
                });
            });

            $(".cartIcon").click(function() {
                var gId = $(this).attr("g-id");
                /*if((Number($(this).attr("oId")))>=0){
                    oid = Number($(this).attr("oId"));
                }
                if(oid==0){
                    goodsFrom=0;
                }else{
                    goodsFrom=3;
                }*/
                var url = "";

                    url="${ctx}/cart/saveAsync?goodsId="+gId+"&buyCount=1";

                $.ajax({
                    url: url,
                    type: "POST",
                }).done(function (data) {
                    if (data.success) {
                        layer.open({ content: '加入成功！', time: 3000, style: 'font-family:Microsoft YaHei' });
                        location.reload();
                        searchGoodSCart();
                    } else if (!data.message) {
                        window.location.href = contextPath + "/login?redirectURL=" + location.href;
                    } else if (data.message) {
                        layer.open({ content: '加入失败！', time: 1500, style: 'font-family:Microsoft YaHei' });
                        location.reload();
                    }
                }).fail(function (data) {
                    layer.open({ content: '加入失败！', time: 1500, style: 'font-family:Microsoft YaHei' });
                    location.reload();
                });
            });
          
        });   
        var page=2;
       	var pageSize=10;
        var totalPage=${collects.totalPages};
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
                  //获取加载的数据
                 // var moreLi = baidu.template("moreList");
                  $.ajax({
                              url:"${ctx}/mobile/person-center/collected/list",
                              type: "POST",
                              async: false,
                              data: {page:page},
                              success: function(page){
                                  var data = page.rows;
                                  if(data){
                                      $.each(data,function(i,e){
                                          var a={
                                              id: e.goods.id,
                                              title: e.goods.title,
                                              image: e.goods.image,
                                              price: e.goods.price,
                                              promotePrice: e.goods.promotePrice,
                                              goodsFrom: e.goodsFrom,
                                              otherId: e.otherId
                                              
                                          //    startDate:new Date(e.startDate).Format("yyyy.MM.dd"),
                                             // endDate: new Date(e.endDate).Format("yyyy.MM.dd"),
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
                                          $(".productList").append(html);
                                          
                                      });
                                     
                                  }
                                  if(totalPage<=page.page){
                                      flag=false;
                                      $(".load").hide();
                                  }else{
                                      page+=1;
                                      flag=true;
                                  }
                                  
                                 /*  if($(".collectProItem").length<=pageSize){
                                  	ok = true;
                                  } */
                              },
                              error:function(page){
                                  alert("获取失败.请稍候再试");
                                  flag=true;
                                  $(".load").css({opacity: 0}).hide();
                              }
                          });

             
                  

            }
      });
    </script>
</body>
</html>
