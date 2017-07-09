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
    <title>邮箱注册机激活-${seo.title}</title>
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
                <span class="fl">
                    欢迎注册
                </span>
                <span class="fr">
                    已有账号，
                    <a href="${ctx}/login">去登录</a>
                </span>
            </p>
            <div class="phoneBackPwd1">
                <p>
                    <a class="fl phoneBack">手机注册</a>
                    <a class="fr emailBack changeBorC">邮箱注册</a>
                </p>
                <div id="emailBack2">
                    <img src="${ctx}/images/pc/succ.png" alt="成功" width="52" height="52" />
                    <p>激活链接已发送至您的登录邮箱：<a href="javascript:;" id="user-email">${param.mail}</a></p>
                    <p>
                        请您及时<a href="javascript:void(0)" id="login-email">登录邮箱</a>，按邮件中的指示完成账号激活操作<br />
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
</body>
</html>

<script>
    $(function(){
        //点击登陆邮箱
        $("#login-email").click(function(){
            var $email=$("#user-email").text(),
                    $sEmail=$email.split("@"),
                    length=$sEmail.length,
                    $suffix=$sEmail[length-1];
            var $loginUrl="http://mail."+$suffix;
            window.open($loginUrl);
        });
    });
</script>
