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
    <title>邮箱找回密码-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/login.css" rel="stylesheet" />
    <link href="${ctx}/css/pc/backPwd.css" rel="stylesheet" />
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap">
    <div class="loginHead">
        <div class="samewidth">
            <a href="${ctx}/">
                <img src="${ctx}/${simpleWebsite.logo}_176x96" alt="logo" width="176" height="96" />
            </a>
        </div>
    </div>
    <!-- //head -->
    <div class="backPwdMain">
        <p>
            <span class="fl">找回密码</span>
            <span class="fr">已有账号，<a href="${ctx}/login">去登录</a></span>
        </p>
        <div class="phoneBackPwd1">
            <p>
                <a class="fl phoneBack">手机找回</a>
                <a class="fr emailBack changeBorC">邮箱找回</a>
            </p>
            <div id="emailBack2">
                <img src="${ctx}/images/pc/succ.png" alt="成功" width="52" height="52" />
                <p>设置新密码链接已发送至您的邮箱：<a href="javascript:void(0)" id="user-email">${email}</a></p>
                <p>
                    请您在30分钟内登录邮箱，按邮件中的指示完成设置新密码操作<br />
                    <%--如果没有收到邮件，请点击<a href="javascript:;">重新发送</a>--%>
                </p>
            </div>
        </div>
    </div>
    <!-- //main -->
    <div class="foot"><%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %></div>
    <!-- //foot -->
</div>
<!-- //wrap -->
<script src="../../js/jquery-1.7.2.min.js"></script>
<script src="../../js/common.js"></script>
<script src="../../js/jquery.validate.js"></script>
<script>
    $(function () {
        $.validator.setDefaults({
            submitHandler: function () {
                window.location.href = "step3.html";
            }
        });

        $("#phoneBack2").validate({
            rules: {
                verCode: {
                    required: true
                }
            },
            messages: {
                verCode: {
                    required: "请输入验证码"
                }
            }
        });


        //60秒验证码
        var wait = 60;
        function time() {
            if (wait == 0) {
                $me.removeAttr("disabled");
                $me.val("获取验证码");
                $me.css({
                    "background": "#19aa4b",
                    "border-color": "#19aa4b"
                });
                wait = 60;
            } else {
                $me.attr("disabled", true);
                $me.val("重新发送(" + wait + ")");
                $me.css({
                    "background": "#ccc",
                    "border-color": "#ccc"
                });
                wait--;
                setTimeout(function () {
                            time()
                        },
                        1000)
            }
        };
        $(".getCode").click(function () {
            $me = $(this);
            time()
        });
    });
</script>
</body>
</html>
