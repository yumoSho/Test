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
    <title>找回密码1</title>
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
            <div class="login samePadding">
                <form class="formDiv mobileBox">
                    <ul class="displayFrame">
                        <li>
                            <span class="spanWidthsperd"><i class="redStar">*</i>手机号</span>
                            <input type="text" placeholder="请输入手机号码" maxlength="11" autofocus class="tellInput" name="mobile" id="mail" />
                        </li>
                        <li class="heightBtn">
                            <span class="spanWidthsperd"><i class="redStar">*</i>验证码</span>
                            <input type="text" placeholder="请输入验证码" class="verificationCode" name="vcodes" maxlength="4"/>
                            <a href="javascript:void(0)"><img src="${ctx}/captcha/captchaImage" class="fright codeImg change" id="kaptchaImage"/></a>
                        </li>
                    </ul>
                    <input type="button" class="PWDnextStep nextStep sameBtn" id="nextStepByPhone" value="下一步">
                </form>
            </div>
        </div><!-- //main -->
        <div class="errorLabel"></div>
    </div><!-- //wrap -->
    <script>
        $(function () {
            //手机找回密码验证
            $(".mobileBox").validate({
                errorLabelContainer: $(".errorLabel"),
                rules: {
                    mobile: {
                        required: true,
                        mobile: true
                    },
                    vcodes: "required"
                },
                messages: {
                    mobile: {
                        required: "请输入手机号",
                        email: "请输入正确的手机号"
                    },
                    vcodes: "请输入验证码"
                },
                highlight: function (element, errorClass) {
                    $(element).parents("td").children(".correct").remove();
                },
                unhighlight: function (element, errorClass) {
                    if(element.name == 'vcodes') return false;
                    $(element).parents("td").append("<span class='correct'></span>");
                }
            });


            //绑定手机改密码下一步 事件
            $("#nextStepByPhone").on("click",function(){
                submitByPhone();
            });

            /**
             * 手机改密码提交
             * @returns {boolean}
             */
            function submitByPhone(){
                $("#nextStepByPhone").off("click");
                var formValidate = $(".mobileBox").validate();
                if(!formValidate.checkForm()){
                    formValidate.showErrors();
                    $("#nextStepByPhone").on('click',function(){
                        submitByPhone();
                    });
                    return false;
                }
                var formData = $(".mobileBox").serialize();
                $.ajax({
                    url: contextPath + '/retrieve/validateStepOneByMobile',
                    type: 'post',
                    async: false,
                    data: formData,
                    dataType: 'json',
                    success: function (message) {
                        if(message.success){
                            document.location.href = contextPath + "/retrieve/step2?type=2&key=" + $("input[name=mobile]").val();
                        }else{
                            $('label.error').remove();
                            $("#kaptchaImage2").trigger("click");//刷新图片
                            var code = message.data;
                            layer.open({
                                content: message.message,
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                            /*重新绑定注册事件*/
                            $("#nextStepByPhone").on('click',function(){
                                submitByPhone();
                            });
                        }
                    },
                    error:function(){
                        alert("系统错误");
                        $("#nextStepByPhone").on('click',function(){
                            submitByPhone();
                        });
                    }
                });
            }


            //图片验证码
            $(".change").click(function () {
                $("#kaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
                $("#kaptchaImage").parent("a").siblings("input").val("");
            });
        });$(function () {
            //手机找回密码验证
            $(".mobileBox").validate({
                errorLabelContainer: $(".errorLabel"),
                rules: {
                    mobile: {
                        required: true,
                        mobile: true
                    },
                    vcodes: "required"
                },
                messages: {
                    mobile: {
                        required: "请输入手机号",
                        email: "请输入正确的手机号"
                    },
                    vcodes: "请输入验证码"
                },
                highlight: function (element, errorClass) {
                    $(element).parents("td").children(".correct").remove();
                },
                unhighlight: function (element, errorClass) {
                    if(element.name == 'vcodes') return false;
                    $(element).parents("td").append("<span class='correct'></span>");
                }
            });


            //绑定手机改密码下一步 事件
            $("#nextStepByPhone").on("click",function(){
                submitByPhone();
            });

            /**
             * 手机改密码提交
             * @returns {boolean}
             */
            function submitByPhone(){
                $("#nextStepByPhone").off("click");
                var formValidate = $(".mobileBox").validate();
                if(!formValidate.checkForm()){
                    formValidate.showErrors();
                    $("#nextStepByPhone").on('click',function(){
                        submitByPhone();
                    });
                    return false;
                }
                var formData = $(".mobileBox").serialize();
                $.ajax({
                    url: contextPath + '/retrieve/validateStepOneByMobile',
                    type: 'post',
                    async: false,
                    data: formData,
                    dataType: 'json',
                    success: function (message) {
                        if(message.success){
                            document.location.href = contextPath + "/retrieve/step2?type=2&key=" + $("input[name=mobile]").val();
                        }else{
                            $('label.error').remove();
                            $("#kaptchaImage2").trigger("click");//刷新图片
                            var code = message.data;
                            layer.open({
                                content: message.message,
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                            /*重新绑定注册事件*/
                            $("#nextStepByPhone").on('click',function(){
                                submitByPhone();
                            });
                        }
                    },
                    error:function(){
                        alert("系统错误");
                        $("#nextStepByPhone").on('click',function(){
                            submitByPhone();
                        });
                    }
                });
            }


            //图片验证码
            $(".change").click(function () {
                $("#kaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
                $("#kaptchaImage").parent("a").siblings("input").val("");
            });
        });
    </script>
</body>
</html>
