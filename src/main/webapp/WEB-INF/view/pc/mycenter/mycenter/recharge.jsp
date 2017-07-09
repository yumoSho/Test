<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/vlibs.jspf" %>
<c:set var="com.glanway.jty.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />

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
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/userCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/pc/login.css" rel="stylesheet" />
    <style type="text/css" >

       /* .proContent p{
            font-size:14px; color:#666;line-height:24px;  letter-spacing:1px; overflow:auto;
        }*/
    </style>

    <c:set var="personalCenterNav" value="recharge"></c:set>
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
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>会员中心</span><span class="forGt"> &gt;</span><span>充值</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>
                <div class="rightWrap fr">
                    <div class="sameTitle color666">充值</div>

                    <div class="userBalanceInfo">
                        <div class="userBalanceContent">
                            <p>充值账户:<span>${member.memberName}</span></p>
                            <p>我的余额:<span>￥${balance}</span></p>
                        </div>
                    </div>
                    <form id="memberRecharge" action="${ctx}/person-center/rechargePay" method="post">
                        <table class="rechangeTable">
                            <c:forEach items="${rechargeRuleList}" var="rr" varStatus="index">
                            <c:set var="i" value="${index.index}"></c:set>
                            <c:if test="${i%4 eq 0}">
                            <tr>
                                <td <c:if   test="${index.first}"> class="alignR" </c:if>><c:if   test="${index.first}">充值金额：</c:if></td>
                                </c:if>
                                <td>
                                    <label>
                                        <input type="radio" name="rechargeId" value="${rr.id}" <c:if test="${index.first}">checked="checked"</c:if>/>
                                            ${rr.money}（返现${rr.rtMoney}元）
                                    </label>
                                </td>
                                </c:forEach>
                            <tr>
                                <td></td>
                                <td colspan="3" class="rechangeAgree">
                                    <%--<label>--%>
                                        <input type="checkbox" name="sure" id="isure" />
                                        我已阅读并同意<a href="javaScript:;" class="rechangeDeal">《充返活动协议》</a>，知悉充值本金和返现金额不可提现、转移、转赠。
                                        <span style="color: red;display: none" id="sureErr">必须同意该协议</span>
                                    <%--</label>--%>
                                </td>
                            </tr>
                        </table>
                        <div class="rechangeButtomWrap">
                            <a href="javascript:void(0)" onclick="$(this).parents('form').submit()" class="rechangeButtom">马上充值</a>
                        </div>
                    </form>
                </div>
            </div>
        </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
        <!--//协议弹出层-->

        <div class="popAgreements" style="">
            <div class="diaShadow"></div>
            <div class="diaContent">
                <div class="diaTitle clearfix">
                    <span class="fl">${dictionary.dicName}</span>
                    <a href="javascript:void(0);" class="closeDialog fr"></a>
                </div>
                <div class="proContent" style="padding:36px 28px 50px;height: 350px;overflow-y: scroll;">
                    ${dictionary.content}
                </div>
                <div class="diaFoot">
                    <input value="同意并继续" type="button">
                </div>
            </div>
        </div>
        <!--//协议弹出层-->
    </div><!-- //wrap -->
</body>
	<script type="text/javascript">

        $(".closePic").click(function(){
            $(".diaContent").fadeOut("fast");
        });

	 $("#memberRecharge").validate({
	        rules: {rechargeId: {
	                required: true,
	            },
	            sure:"required",
	        },
	        messages: {
	        	rechargeId: {
	                required: "请选择要充值的金额",

	            },

	            sure:{
	                required: "必须同意该协议"
	            }
	        },
	        highlight: function (element, errorClass) {
                if(element.name == 'sure') {
                    $("#sureErr").show();
                    return false;
                }
//	            $(element).siblings(".correct").remove();
	        },
	        unhighlight: function (element, errorClass) {
//	            $(element).parents("td").append("<span class='correct'></span>");
                if(element.name == 'sure') {
                    $("#sureErr").hide();
                }
	        }
	    });



		var agreeBtn = $(".rechangeDeal");
		agreeBtn.click(function () {
		    $(".diaContent").fadeIn("fast");
            $(".diaShadow").show();
		});


		$(".closeDialog,.diaFoot").click(function () {
            $(".diaContent").fadeOut("fast");
            $(".diaShadow").hide();
        });
        $(".diaFoot").on('click', function() {
            $("#sure,#isure").attr("checked","checked");
        });
</script>
</html>
