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
    <title>修改密码</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/mobile/personalCenter.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main wholeHei">
        <form name="changePassword" action="" method="post" id="alterForm">
            <div class="writeWrap wrap">
                <div class="changPassword">原密码：<input type="password" name="oldPwd" /></div>
                <div class="changPassword">新密码：<input type="password" name="newPwd" id="confirmPass" /></div>
                <div class="changPassword">确认密码：<input type="password" name="confirmPsd" /></div>
            </div>
            <div class="redBut"><input type="submit" value="保存"></div>
        </form>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    <div class="errorLabel"></div>
</div><!-- //wrap -->
</body>
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script>
    $(function () {
        //表单验证
        $("#alterForm").validate({
            errorLabelContainer: $(".errorLabel"),
            rules: {
                oldPwd: {
                    required: true,
                },
                newPwd: {
                    required: true,
                    minlength:6,
                    maxlength:20
                },
                confirmPsd: {
                    required: true,
                    equalTo: "#confirmPass"
                }
            },
            messages: {
                oldPwd: {
                    required: "请输入原密码"
                },
                newPwd: {
                    required: "请输入新密码",
                },
                confirmPsd: {
                    required: "请确认密码",
                    equalTo: "两次输入不一致"
                }
            },
            submitHandler: function(form) {
                $(form).find(":submit").attr("disabled", true).attr("value",
                        "修改中...")
                $.ajax({
                    type: "POST",
                    url: "${ctx}/person-center/update-pwd",
                    data: $("form").serialize(),
                }).done(function(data){
                    if(data.success){
                        $(form).find(":submit").attr("disabled", false).attr("value",
                                "保存")
                        layer.open({
                            content: '修改成功',
                            time: 2,
                            style: "background-color:#19aa4b; color:#fff; border:none;font-family:Microsoft YaHei",
                        })
                        location.href="${ctx}/login";
                    }else{
                        $(form).find(":submit").attr("disabled", false).attr("value",
                                "保存")
                        if(data.message){
                            layer.open({
                                content: data.message,
                                time: 2,
                                style: "background-color:#19aa4b; color:#fff; border:none;font-family:Microsoft YaHei",
                            })
                        }else{
                            location.href="${ctx}/login";
                        }
                    }
                }).fail(function(data){
                    $(form).find(":submit").attr("disabled", false).attr("value",
                            "保存")
                    layer.open({
                        content: data.message,
                        time: 2,
                        style: "background-color:#19aa4b; color:#fff; border:none;font-family:Microsoft YaHei",
                    })
                    location.href="${ctx}/login"
                });
            }
        });
    });
</script>

</html>
