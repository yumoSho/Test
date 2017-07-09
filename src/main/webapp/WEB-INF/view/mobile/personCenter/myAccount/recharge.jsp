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
    <title>充值</title>
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
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="writeWrap rech">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
    <div class="main  font24 personalMargin">
        <form name="rechange" action="${ctx}/person-center/rechargePay" method="post">
            <div class="rechangeTop color666">
                <img src="${ctx}/images/mobile/rechangeIcon.png" width="61" height="72" />
                <p>充值账户：<span>${member.memberName}</span></p>
                <p>我的余额：<span>￥${balance}</span></p>
            </div>
            <table class="rechangeT">
                <c:forEach items="${rechargeRuleList}" var="rr" varStatus="index">
                    <tr>
                        <td class="tTitle samePadding"><c:if   test="${index.first}">充值金额：</c:if></td>
                        <td>
                            <label>
                                <input type="radio" name="rechargeId" value="${rr.id}"  <c:if test="${index.first}">checked="checked"</c:if>/>${rr.money}元（返现${rr.rtMoney}元）
                            </label>
                        </td>
                    </tr>
                </c:forEach>
                <%--<tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />7000元（返现70元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />5000元（返现50元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />3000元（返现30元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />1000元（返现10元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />700元（返现7元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />500元（返现5元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />300元（返现3元）
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="radio" name="rechangeMoney" />100元（返现1元）
                        </label>
                    </td>
                </tr>--%>
            </table>
            <div class="rechangeDeal samePadding">
                <input type="checkbox" name="deal" required="required" checked />
                    <span>
                        我已阅读并同意
                        <a href="javascript:;" class="dealDialogBtn">《充返活动协议》</a>，知悉充值本金和返现金额不可提现、转移、转赠。
                    </span>
            </div>
            <div class="agreeBtn samePadding"><a href="javascript:void(0)" onclick="$(this).parents('form').submit()" >马上充值</a></div>
        </form>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<div class="writeWrap recharge" style="display:none;">
    <div class="head">
        <div class="pageTitle headTop samePadding clearfix">
            <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
            <span>充值协议</span>
        </div>
    </div><!-- //head -->
    <div class="main  font24 personalMargin">
        <div class="agreementsContent">
            第1条 本站服务条款的确认和接纳<br />
            1.1本站的各项电子服务的所有权和运作权归睢宁银行所有。用户同意所有注册协议条款并完成注册程序，才能成为本站的正式用户。用户确认：本协议条款是处理双方权利义务的契约，始终有效，法律另有强制性规定或双方另有特别约定的，依其规定。<br />
            1.2用户点击同意本协议的，即视为用户确认自己具有享受本站服务、下单购物等相应的权利能力和行为能力，能够独立承担法律责任。<br />
            1.3如果您在18周岁以下，您只能在父母或监护人的监护参与下才能使用本站。<br />
            1.4睢宁银行保留在中华人民共和国大陆地区法施行之法律允许的范围内独自决定拒绝服务、关闭用户账户、清除或编辑内容或取消订单的权利。<br />
            第2条 本站服务<br />
            2.1睢宁银行通过互联网依法为用户提供互联网信息等服务，用户在完全同意本协议及本站规定的情况下，方有权使用本站的相关服务。<br />
            2.2用户必须自行准备如下设备和承担如下开支：（1）上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他必备的上网装置；（2）上网开支，包括并不限于网络接入费、上网设备租用费、手机流量费等。<br />
            第3条 用户信息<br />
            3.1用户应自行诚信向本站提供注册资料，用户同意其提供的注册资料真实、准确、完整、合法有效，用户注册资料如有变动的，应及时更新其注册资料。如果用户提供的注册资料不合法、不真实、不准确、不详尽的，用户需承担因此引起的相应责任及后果，并且睢宁银行保留终止用户使用睢宁银行各项服务的权利。<br />
            3.2用户在本站进行浏览、下单购物等活动时，涉及用户真实姓名/名称、通信地址、联系电话、电子邮箱等隐私信息的，本站将予以严格保密，除非得到用户的授权或法律另有规定，本站不会向外界披露用户隐私信息。<br />
            3.3用户注册成功后，将产生用户名和密码等账户信息，您可以根据本站规定改变您的密码。用户应谨慎合理的保存、使用其用户名和密码。用户若发现任何非法使用用户账号或存在安全漏洞的情况，请立即通知本站并向公安机关报案。<br />
            3.4用户同意，睢宁银行拥有通过邮件、短信电话等形式，向在本站注册、购物用户、收货人发送订单信息、促销活动等告知信息的权利。<br />
            3.5用户不得将在本站注册获得的账户借给他人使用，否则用户应承担由此产生的全部责任，并与实际使用人承担连带责任。<br />
            3.6用户同意，睢宁银行有权使用用户的注册信息、用户名、密码等信息，登录进入用户的注册账户，进行证据保全，包括但不限于公证、见<br />
        </div>
        <div class="agreeBtnBox">
            <a href="javascript:;" class="agreementBtn">同意并继续</a>
        </div>
    </div>
    <!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="../../../images/icon9.png" /></a>
</div>
<script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
<script src="${ctx}/js/mobile/common.js"></script>
<script>

    $(function () {
        $(".recharge").find(".main").css("padding-bottom", "0");
        //用户注册协议页面
        $(".dealDialogBtn").on("click",function () {
            $(".rech").hide();
            $(".recharge").show();
            $(".agreementBtn").on("click",function () {
                $(".rechangeDeal").children("input").attr("checked", "checked");
                $(".recharge").hide();
                $(".rech").show();
            })
            $(".recharge").find(".back").on("click", function () {
                $(".recharge").hide();
                $(".rech").show();
            })
        });
    });
</script>
</body>
</html>
