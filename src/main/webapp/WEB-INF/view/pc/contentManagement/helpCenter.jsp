﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<c:set var="com.glanway.hg.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>帮助中心-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <link rel="stylesheet" href="../../css/normalize.css">
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" href="../../css/base.css">
    <link href="../../css/userCenter.css" rel="stylesheet" />
    <link href="../../css/helpCenter.css" rel="stylesheet" />
    <script src="../../js/modernizr-2.6.2.min.js"></script>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %>
        <!-- //head -->
        <div class="main samewidth">
            <!-- //面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>帮助中心</span><span class="forGt"> &gt;</span><span>常见问题</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                <%@ include file="/WEB-INF/view/include/pc/leftNav.jspf" %>
                <div class="rightWrap fr userIndex">
                    <span class="comPro">常见问题</span>
                    <div>
                        <p style="color:#666;font-size:12px;line-height:20px;padding-top:34px;letter-spacing:1px;">
                            客户在接受商品订购与送货的同时，有义务遵守以下交易条款。您在京东下订单之前或接受京东送货之前，请您仔细阅读以下条款：
                            <br />
                            <br />
                            1. 订购的商品价格以您下订单时京东网上价格为准。
                            <br />
                            <br />
                            2. 请清楚准确地填写您的真实姓名、送货地址及联系方式。因如下情况造成订单延迟或无法配送等，京东将不承担责任：
                            <br />
                            <br />
                            &nbsp;A. 客户提供错误信息和不详细的地址；
                            <br />
                            <br />
                            &nbsp;B. 货物送达无人签收，由此造成的重复配送所产生的费用及相关的后果；
                            <br />
                            <br />
                            &nbsp;C. 不可抗力，例如：自然灾害、交通戒严、突发战争等。
                            <br />
                            <br />
                            3. 安全性：无论您是电话订购商品或是网络订购商品，我们会保证交易信息的安全，并由京东授权的员工处理您的订单。
                            <br />
                            <br />
                            4. 隐私权：京东尊重您的隐私权，在任何情况下，我们都不会将您的个人和订单信息出售或泄露给任何第三方（国家司法机关调取除外）。我们从网站上或电话中得到的所有客户信息仅用来处理您的相关订单。
                            <br />
                            <br />
                            5. 免责：如因不可抗力或其它京东无法控制的原因使京东销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等，京东会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失。
                            <br />
                            <br />
                            6. 客户监督：京东希望通过不懈努力，为客户提供最佳服务，京东在给客户提供服务的全过程中接受客户的监督。
                            <br />
                            <br />
                            7. 争议处理：如果客户与京东之间发生任何争议，可依据当时双方所认定的协议及相关法律进行解决。
                        </p>
                    </div>
                </div>
            </div>

        </div>
        <!-- //main -->
        <div class="foot">
            <div class="footBar">
                <div class="samewidth">
                    <a href="#"><img src="../../images/footIcon1.png" width="116" height="42" /></a>
                    <a href="#"><img src="../../images/footIcon2.png" width="116" height="42" /></a>
                    <a href="#"><img src="../../images/footIcon3.png" width="116" height="42" /></a>
                    <a href="#"><img src="../../images/footIcon4.png" width="116" height="42" /></a>
                </div>
            </div>
            <div class="footPart samewidth">
                <div class="footCenter clearfix">
                    <div class="footTell fl">
                        <div class="telephoneNum">
                            <span>客服中心电话：</span>
                            <p>400-8210-8888</p>
                        </div>
                        <p class="workTime">工作时间：周一至周日，9：00至18：00</p>
                    </div>
                    <div class="footLinks fl">
                        <ul>
                            <li>购物指南</li>
                            <li><a href="../login/regist.html">用户注册</a></li>
                            <li><a href="#">导购演示</a></li>
                        </ul>
                        <ul>
                            <li>付款方式</li>
                            <li><a href="#">支付方式</a></li>
                            <li><a href="#">充值说明</a></li>
                            <li><a href="#">礼品卡说明</a></li>
                            <li><a href="#">优惠券使用</a></li>
                            <li><a href="#">发票说明</a></li>
                        </ul>
                        <ul>
                            <li>配送方式</li>
                            <li><a href="#">配送范围</a></li>
                            <li><a href="#">配送时间</a></li>
                            <li><a href="#">配送运费</a></li>
                            <li><a href="#">物流查询</a></li>
                        </ul>
                        <ul>
                            <li>售后服务</li>
                            <li><a href="#">退换货正常</a></li>
                            <li><a href="#">退换货流程</a></li>
                            <li><a href="helpCenterIndex.html">常见问题</a></li>
                        </ul>
                        <ul>
                            <li>会员中心</li>
                            <li><a href="#">会员权利与义务</a></li>
                            <li><a href="#">积分说明</a></li>
                            <li><a href="#">会员等级说明</a></li>
                        </ul>
                    </div>
                    <div class="erweima fr">
                        <img src="../../images/erweima.png" width="100" height="100" />
                        <p class="color666">扫码关注我们</p>
                    </div>
                </div>
                <div class="friendLinks">
                    <a href="#">友情链接</a>
                    <a href="#">友情链接</a>
                    <a href="#">友情链接</a>
                    <a href="#">友情链接</a>
                </div>
                <div class="copyRight color666">版权所有 2015-2016 商城网 沪ICP-0000000</div>
            </div>
        </div>
        <!-- //foot -->
    </div>
    <!-- //wrap -->
    <script src="../../js/jquery-1.7.2.min.js"></script>
    <script src="../../js/common.js"></script>
    <script>
        $(function () {

        });
    </script>
</body>
</html>
