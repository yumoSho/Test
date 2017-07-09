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
    <title>填写地址</title>
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
                <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><a href="${ctx}/cart">购物车</a><span class="forGt"> &gt;</span><span>填写订单</span>
            </div>
        </div>
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
            <div class=" flow on">
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
        <!--填写核对订单信息-->
        <div class="checkOrderMessage">
            <div class="noticeTitle">填写核对订单信息</div>
            <div class="shoppingAddr">
                <input type="hidden" name="addressId" id="addressId" value="${defaultAddressId}">
                <input type="hidden" name="couponId" id="couponId">
                <input type="hidden" name="orderFrom" value="${orderFrom}" id="orderFrom">
                <div class="title">收货地址</div>
                <div class="addrList clearfix">
                    <div class="addrInfo">
                        <c:forEach items="${addressList}" var="address" end="1" varStatus="index">
                            <div class="addrItem fl <c:if test="${index.first}">on</c:if>">
                                <input type="hidden" name="id" value="${address.id}">
                                <p><c:if test="${address.isDefault}">常用地址</c:if></p>
                                <div>${address.consignee} 收</div>
                                <p class="color666">
                                        ${address.fieldOne}&nbsp;${address.fieldTwo}&nbsp;${address.fieldThree}<br/>
                                        ${address.address}
                                </p>
                                <p class="color666">${address.consigneePhone}</p>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="addrhandel fl">
                        <a href="javascript:void(0);" class="newAddr btn">添加新地址</a>
                        <a href="javascript:void(0);" class="moreAddr btn">更多地址</a>
                    </div>
                </div>
            </div>
        </div>
        <!--使用优惠券-->
        <div class="useCard">
            <div class="topTitle cardTitle">使用优惠券</div>
            <div class="useCardInner">
                <div class="useCardBtn">
                    使用优惠券
                </div>
                <div class="cardSelect">
                    <ul>
                        <li>
                            <span><input type="radio" value="0" cId="-1" name="cradSelect" checked="checked" /></span>
                            <%--<span>￥<span>-0</span></span>--%>
                            <span>不使用优惠券</span>
                        </li>
                        <c:forEach items="${couponList}" var="coupon">
                            <c:if test="${otv.totalPrice >= coupon.minPrice}">
                                <li>
                                    <span><input type="radio" value="${coupon.discount}" cId="${coupon.id}" name="cradSelect" /></span>
                                    <span>￥<span>-${coupon.discount}</span></span>
                                    <span>${coupon.couponName}</span>
                                    <span>有效期至<fmt:formatDate value="${coupon.endDate}" type="date" dateStyle="full"/></span>
                                    <span>此券仅限订单商品满${coupon.minPrice}可使用</span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                  <%--  <div class="cardNum clearfix">
                        <div class="cardNumInput fl">
                            &lt;%&ndash;<input type="text" placeholder="请输入优惠券号" />&ndash;%&gt;
                            &lt;%&ndash;<img src="${ctx}/images/pc/closeIput.png" width="11" height="13" />&ndash;%&gt;
                        </div>
                        &lt;%&ndash;<a href="javascript:void(0);" class="fl">使用</a>&ndash;%&gt;
                    </div>--%>
                </div>
            </div>
        </div>
        <!--物流配送信息-->
        <div class="goodsTime">
            <div class="topTitle cardTitle">物流配送信息</div>
            <div class="timeSelect clearfix" style="font-size: 16px">
                <span class="fl">配送时间：</span>
                <div class="times fl">
                    <c:forEach items="${pssj}" var="sj" varStatus="index">
                        <label><input type="radio" name="timeSelect"  value="${sj.remark}" <c:if test="${index.index eq 0}">checked="checked"</c:if> />${sj.remark}</label>
                    </c:forEach>
<%--
                    <label><input type="radio" name="timeSelect" value="12:00-17:00" />12:00-17:00</label>
                    <label><input type="radio" name="timeSelect" value="12:00-20:00"/>12:00-20:00</label>--%>
                </div>

            </div>
        </div>
        <!--商品信息-->
        <div class="productMessage">
            <div class="topTitle">商品信息</div>
            <div class="breakOrder clearfix">
                <table class="breakOrderTable">
                    <tr>
                        <th width="200"></th>
                        <th width="270">商品信息</th>
                        <th width="265">原价</th>
                        <th width="265">现价</th>
                        <th width="230">数量</th>
                        <th width="230">合计</th>
                    </tr>
                    <tbody>
                            <c:forEach items="${otv.goodsList}" var="tmp">
                                <tr>
                                    <input type="hidden" name="id" value="${tmp.id}">
                                    <td><a href="${ctx}/detail/${tmp.goodsId}"><img src="${ctx}/${tmp.goodsImg}_79x86" width="79" height="86" /></a></td>
                                    <td><a href="${ctx}/detail/${tmp.goodsId}" class="orderProName overflow">${tmp.goodsName}</a></td>
                                    <td><span class="orderPrice">￥<span>${tmp.originalPrice}</span></span></td>
                                    <td><span class="orderPrice">￥<span>${tmp.price}</span></span></td>
                                    <td>
                                            ${tmp.buyCount}
                                    </td>
                                    <td>
                                    <p class="proAddNomey">￥<span>${tmp.totalPrice}</span></p>
                                </td>
                                </tr>
                            </c:forEach>
                    </tbody>
                </table>
                <ul class="orderPay fr">
                    <li>
                        <span>${otv.totalNum}</span>件商品，总商品金额：
                    </li>
                    <li>
                        ￥<span id="totalPrice">${otv.totalPrice}</span>
                    </li>
                    <li>
                        优惠金额：
                    </li>
                    <li>
                        ￥<span id="couponPrice">0.00</span>
                    </li>
                    <li>
                        运费：
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${!otv.canDelivery}">
                                <span class="totalFreight" style="color:red">不支持该区域配送</span>
                            </c:when>
                            <c:when test="${otv.canDelivery && otv.baoYou}">
                                <span class="totalFreight" style="color:red">包邮</span>
                            </c:when>
                            <c:otherwise>
                                <span class="totalFreight">￥${otv.totalFreight}</span>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
        <div class="mark">
            <span class="fl color666">备注：</span>
            <textarea class="fl" id="remark"></textarea>
        </div>
        <div class="payFororder clearfix">
            <input type="hidden" name="canDelivery" id="canDelivery" value="${otv.canDelivery}">
            <input type="hidden" name="baoYou" id="baoYou" value="${otv.baoYou}">
            <p class="hadPay fl">应付总额：<span>￥<span id="totalPriceAndTotalFreight">${otv.totalPriceAndTotalFreight}</span></span></p>
            <a href="javascript:void(0)" class="submitOrder fr">提交订单</a>
        </div>
    </div><!-- //main -->
    <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
    <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    <!-- 添加新地址弹窗-->
    <div class="newAddrPop" style="display:none;">
        <div class="newAddrWrap">
            <div class="title">
                <span>添加新地址</span>
                <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
                <a href="javascript:void(0);" class=" closeImg fr">
                    <img src="${ctx}/images/pc/closePop.png" width="20" height="20"/>
                </a>
            </div>
            <form id="newAddrForm">
                <table class="addAddrTable">
                    <tr>
                        <td><span class="redStar">*</span>收件人：</td>
                        <td>
                            <input type="text" class="addrSameInput" name="consignee" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>地址：</td>
                        <td>
                            <select name="privinceCode" id="privinceCode">
                                <c:if test="${empty provinces}">
                                    <option>请选择省份</option>
                                </c:if>
                                <c:forEach items="${provinces}" var="province">
                                    <option value="${province.provinceCode}">${province.provinceName}</option>
                                </c:forEach>

                            </select>
                            <select name="cityCode" id="cityCode">
                                <option>请选择市/区</option>
                            </select>
                            <select name="areaCode" id="areaCode">
                                <option>请选择区/县/街道</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>详细地址：</td>
                        <td>
                            <input type="text" class="detaileAddr" name="address" maxlength="120"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>手机号码：</td>
                        <td>
                            <input type="text" class="addrSameInput" name="consigneePhone" maxlength="11"/>
                        </td>
                    </tr>
                    <tr>
                        <td>固定电话：</td>
                        <td>
                            <input type="text" class="addrSameInput" name="telephone" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <label>
                                <input type="checkbox" 　name="isDefault" value="1"/> 设为默认地址
                            </label>
                        </td>
                    </tr>
                </table>
                <div class="addrSubmitBtns">
                    <input type="button" class=" surBtn btn" value="保存"/>
                    <input type="reset" class=" resetBtn btn" value="取消"/>
                </div>
            </form>
        </div>
    </div>
    <!-- 更多地址弹窗-->
    <div class="moreAddrWrap" style="display:none;">
        <div class="choiceAddrWrap">
            <div class="title clearfix"><span>请选择收货地址</span><img src="${ctx}/images/pc/closePop.png" width="20" height="20"
                                                                 class="fr closeImg"/></div>
            <div class="addressList">
                <c:forEach items="${addressList}" var="address">
                    <div class="addrContent <c:if test="${address.isDefault == true}">on</c:if>">
                        <input type="hidden" name="id" value="${address.id}">
                        <div class="name"><span>${address.consignee}</span>收</div>
                        <p>${address.fieldOne}&nbsp;${address.fieldTwo}&nbsp;${address.fieldThree}&nbsp;${address.address}</p>
                        <div class="tellNum">${address.consigneePhone}</div>
                    </div>
                </c:forEach>
            </div>
            <a href="javascript:void(0)" class="sure btn">确定</a>
        </div>
    </div>
</div><!-- //wrap -->
<div id="goSuccess" style="display: none"></div>
<script src="${ctx}/js/pc/layer/layer.js"></script>
<script src="${ctx}/js/pc/order/add.js"></script>
<%--省市联动js--%>
<script type="text/javascript" src="${ctx}/js/admin/cityGanged.js"></script>
</body>
</html>
