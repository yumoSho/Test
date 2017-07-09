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
    <title>找回密码2</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/login.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/layer/layer.js"></script>

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <div class="head">
        <div class="pageTitle writeWrap samePadding clearfix headTop">
            <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
            <span>找回密码</span>
        </div>
    </div><!-- //head -->
    <div class="main">
        <div class="samePadding">
            <form class="formDiv">
                <div class="promptMobile">
                    请点击获取验证码并在手机：<span>${phone}</span> 中查看短信，并填写验证码
                </div>
                <ul class="displayFrame">
                    <li class="heightBtn">
                        <span>短信验证码：</span>
                        <input type="text" class="verificationCode codeInput" placeholder="请输入验证码" id="verificationCodes" name="pCode" maxlength="6" />
                        <input type="hidden" name="phone" id="phone" value="${phone}">
                        <input type="button" value="获取短信验证码" id="noteBtn" class="getCodes" />
                    </li>
                </ul>
                <input type="button" class="PWDnextStep nextStep sameBtn" value="下一步">
            </form>
        </div>
    </div><!-- //main -->
    <div class="errorLabel"></div>
</div><!-- //wrap -->

<script>$(function () {
    $(document).keypress(function(e) {
        // 回车键事件
        if(e.which == 13) {
            $(".nextStep").click();
        }
    });

    /* $("#verificationCodes").on("input", function () {
     if ($("#verificationCodes").val()) {
     $("#verificationCodes").next(".error").remove();
     }
     });*/

    var btn = $(".getCodes");
    /*绑定验证码事件*/
    btn.on("click", function () {
        sendMsg();//发送短信
    });

    /*发送短信*/
    function sendMsg() {
        btn.off("click");
        $.ajax({
            url: contextPath + '/retrieve/sendPhoneVCode',
            type: 'post',
            async: true,
            dataType: 'json',
            data:{phone:$("#phone").val()},
            success: function (message) {
                if (message.success) {
                    countDown();
                } else {
                    layer.open({
                        content: message.message,
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    })
                    btn.on("click", function () {
                        sendMsg();//发送短信
                    });
                }
            },
            error: function () {
                layer.open({
                    content: "请求失败",
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                })
                btn.on("click", function () {
                    sendMsg();//发送短信
                });
            }
        });
    }

    /*发送短信验证码后进行倒计时*/
    function countDown() {
        btn.addClass("on");
        var countdown = false;
        if (!countdown) {
            var sec = 60;
            countdown = true;
            var interval = setInterval(function () {
                if (sec == -1) {
                    btn.removeClass("on");
                    btn.val("免费获取验证码");
                    countdown = false;
                    clearInterval(interval);
                    btn.on("click", function () {
                        sendMsg();//发送短信
                    });
                } else {
                    btn.val(sec + "秒后重新获取");
                    sec--;
                    $(".successMsg").css("visibility", "visible")
                }
            }, 1000);
        }
    };


    /*校验短信验证码*/
    $(".nextStep").on("click", function () {
        validateCode();
    });

    function validateCode() {
        $(".nextStep").off("click");
        var val = $("#verificationCodes").val();
        if (!val) {
            layer.open({
                content: "请输入验证码",
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            })
            $(".nextStep").on("click", function () {
                validateCode();
            });
            return false;
        }
        var phone = $("#phone").val();
        $.ajax({
            url: contextPath + '/retrieve/validateCodes',
            type: 'post',
            async: true,
            dataType: 'json',
            data: {pCode: $("#verificationCodes").val(),phone:phone},
            success: function (message) {
                if (message.success) {
                    document.location.href = contextPath + "/retrieve/phoneToStep3?phone=" + phone;
                } else {
                    var code = message.data;
                    switch (code) {
                        case 2:
                        {
                            layer.open({
                                content: message.message,
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                        }
                            ;
                            break;
                        default:
                        {
                            $("#verificationCodes").after('<label for="verificationCodes" generated="true" class="error" style="left: -1rem;top: 1.05rem;">' + message.message + '</label>');
                        }
                    }
                    $(".nextStep").on("click", function () {
                        validateCode();
                    });
                }
            },
            error: function () {
                $(".nextStep").on("click", function () {
                    validateCode();
                });
            }
        });
    }
});</script>

</body>
</html>
