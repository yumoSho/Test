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
    <title>邮箱找回密码1</title>
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
                <form class="formDiv mailBox" >
                    <ul class="displayFrame">
                        <li>
                            <span class="spanWidthsperd">邮箱号</span>
                            <input type="text" placeholder="请输入邮箱号码" maxlength="25" class="tellInput" name="mail" id="mobile"/>
                        </li>
                        <li class="heightBtn">
                            <span class="spanWidthsperd">验证码</span>
                            <input type="text" maxlength="4" class="codeImport" autocomplete="off" placeholder="输入验证码" name="vcodes" id="codes">
                            <img src="${ctx}/captcha/captchaImage"
                                 style="width: 20%; padding-top:0.05rem;height: 0.54rem; cursor: pointer;"
                                 class="kaptchaImage change fright" id="kaptchaImage"/>
                        </li>
                    </ul>
                    <input type="button" class="PWDnextStep nextStep sameBtn" value="下一步" id="nextStepByMail">
                </form>
            </div>
        </div><!-- //main -->
        <div class="errorLabel"></div>
    </div><!-- //wrap -->

    <script>
        $(function(){
            //邮箱找回密码验证
            $(".mailBox").validate({
                errorLabelContainer: $(".errorLabel"),
                rules: {
                    mail: {
                        required: true,
                        email: true
                    },
                    vcodes: "required"
                },
                messages: {
                    mail: {
                        required: "请输入邮箱",
                        email: "请输入一个有效的邮箱"
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

            //绑定邮箱改密码下一步 事件
            $("#nextStepByMail").on("click",function(){
                submitByMal();
            });

            /**
             * 邮箱改密码提交
             * @returns {boolean}
             */
            function submitByMal(){
                $("#nextStepByMail").off("click");
                var formValidate = $(".mailBox").validate();
                if(!formValidate.checkForm()){
                    formValidate.showErrors();
                    $("#nextStepByMail").on('click',function(){
                        submitByMal();
                    });
                    return false;
                }
                var formData = $(".mailBox").serialize();
                $.ajax({
                    url: contextPath + '/retrieve/validateStepOneByEmail',
                    type: 'post',
                    async: false,
                    data: formData,
                    dataType: 'json',
                    success: function (message) {
                        if(message.success){
                            document.location.href = contextPath + "/retrieve/step2?type=1&key=" + $("input[name=mail]").val();
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
                            $("#nextStepByMail").on('click',function(){
                                submitByMal();
                            });
                        }
                    },
                    error:function(){
                        alert("系统错误");
                        $("#nextStepByMail").on('click',function(){
                            submitByMal();
                        });
                    }
                });
            }
        });
    </script>
</body>
</html>
