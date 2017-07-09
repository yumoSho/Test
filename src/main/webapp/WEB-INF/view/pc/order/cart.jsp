<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
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
    <title>购物车</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/cart.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
    <div class="main samewidth">
        <!-- 面包屑 -->
        <div class="positionWrap">
            <div class="position">
                <span>您所在当前位置：</span>
                <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>购物车</span>
            </div>
        </div>
        <c:choose>
            <c:when test="${fn:length(rtMap.cartList) > 0}">
                <div class="cartList samewidth clearfix">
                    <!-- 购物车公用流程-->
                    <div class="cartFlow clearfix">
                        <!-- 第一步-->
                        <div class=" flow on">
                            <div class="flowBgWrap">
                                <div class="flowBg"></div>
                                <span class="flowNum">1</span>
                            </div>
                            <div class="flowDes">1、我的购物车</div>
                        </div>
                        <!-- 第二步-->
                        <div class=" flow">
                            <div class="flowBgWrap">
                                <div class="flowBg"></div>
                                <span class="flowNum">2</span>
                            </div>
                            <div class="flowDes">2、填写核对订单信息</div>
                        </div>
                        <!-- 第三步-->
                        <div class=" flow">
                            <div class="flowBgWrap">
                                <div class="flowBg"></div>
                                <span class="flowNum">3</span>
                            </div>
                            <div class="flowDes">3、成功提交订单</div>
                        </div>
                        <!-- 第四步-->
                        <div class=" flow">
                            <div class="flowBgWrap">
                                <div class="flowBg"></div>
                                <span class="flowNum">4</span>
                            </div>
                            <div class="flowDes">4、订单支付</div>
                        </div>
                    </div>
                    <div class="productList checkOrderMessage fl">
                        <div class="title">我的购物车</div>
                        <table class="productTable">
                            <thead>
                            <tr>
                                <th width="50">
                                    <label>
                                        <input type="checkbox" class="checkAllList" />
                                        全选
                                    </label>
                                </th>
                                <th width="150"></th>
                                <th width="200">商品信息</th>
                                <th width="240">单价</th>
                                <th width="200">数量</th>
                                <th width="150">金额</th>
                                <th width="150">操作</th>
                            </tr>
                            </thead>
                            <input type="hidden" id="loginFlag" value="<c:choose><c:when test="${member == null}">false</c:when><c:otherwise>true</c:otherwise></c:choose>"/>
                            <tbody>
                            <c:forEach items="${rtMap.cartList}" var="cart">
                                <tr>
                                    <td>
                                        <input type="hidden" name="id" value="${cart.id}">
                                        <input type="checkbox" value="${cart.id}" name="proListCheckbox"
                                               <c:if test="${cart.selected}">checked</c:if> />
                                    </td>
                                    <td><a href="${ctx}/detail/${cart.goodsId}?goodsFrom=${cart.goodsFrom}&oid=${cart.otherId}">
                                        <img src="${ctx}/${cart.goodsImg}_79x86" width="79" height="86"/></a></td>
                                    <td>
                                        <a href="${ctx}/detail/${cart.goodsId}?goodsFrom=${cart.goodsFrom}&oid=${cart.otherId}" class="proTitle color666">${cart.goodsName}</a>
                                        <p class="brand color666">${cart.specOne}</p>
                                        <p class="brand color666">${cart.specTwo}</p>
                                    </td>
                                    <td>
                                <span class="onePrice">
                                <c:choose>
                                    <c:when test="${cart.isLive}">
                                        ￥<span class="unitPrice">${cart.price}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color:red">该商品已下架</span>
                                    </c:otherwise>
                                </c:choose>
                                </span>
                                    </td>
                                    <td>
                                        <div class="numberHabdel">
                                            <div class="numAdd clearfix">
                                                <c:choose>
                                                    <c:when test="${cart.isLive}">
                                                        <input type="hidden" name="oldCount">
                                                        <a href="javascript:void(0);" class="fl reduce numDown btn">-</a>
                                                        <input type="text" class="numberImport fl " value="${cart.buyCount}"/>
                                                        <a href="javascript:void(0);" class="fl numUp btn">+</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div style="width:100%;padding-left:10px">${cart.buyCount}</div>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:if test="${cart.isLive}">
                                                    <span class="reduce" style="text-align: center;font-size: 12px;color: #ada5aa;<c:if test="${cart.buyCount > cart.inventory}">color:red;font-weight: bold</c:if> ">
                                     剩余${cart.inventory}件
                                </span>
                                                </c:if>

                                                <input  type="hidden" name="canBuy" value="${cart.buyCount <= cart.inventory && cart.isLive}">
                                            </div>
                                        </div>
                                    </td>
                                    <td><span class="cartPrice">￥<span class="priceNum">${cart.totalPrice}</span></span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${member != null}">
                                                <c:if test="${cart.isLive}">
                                                    <a href="javascript:void(0);" class="handelAction moveToFavorite">移入收藏</a>
                                                </c:if>
                                                <a href="javascript:void(0);" class="handelAction delete">删除</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="javascript:void(0);" class="handelAction delete">删除</a>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="settlement clearfix">
                            <label class="fl">
                                <input type="checkbox" class="checkAllList" />
                                全选
                            </label>
                            <a href="javascript:void(0);" class=" deleteChecked fl">删除选中的商品</a>
                            <div class="hasChecked color666 fl">已选择 <span class="allProNum">${rtMap.totalBuyCount}</span> 件商品</div>
                            <div class="lastMoney color666 fl">总价（不包含运费）：<span class="allProPrice">￥<span class="lastHadPay">${rtMap.totalPrice}</span></span></div>
                            <a href="javascript:void(0);" class=" fr goToPay btn">去结算</a>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- 购物车-空提示 -->
                <div class="cartNull samewidth">
                <img src="${ctx}/images/pc/CartNull.png" width="91" height="83" />
                <p>您的购物车中没有商品，请先去挑选心爱的商品吧！</p>
                <a href="${ctx}/search" class="goForShopping">去购物 ></a>
                </div>
            </c:otherwise>
        </c:choose>
    </div><!-- //main -->
    <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
    <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
</div><!-- //wrap -->
<div id="subForm" style="display: none">

</div>
<script src="${ctx}/js/pc/layer/layer.js"></script>
<script src="${ctx}/js/pc/cart/cart.js"></script>
</body>
</html>
