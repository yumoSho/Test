<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
<%@include file="/WEB-INF/view/include/elibs.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${simpleWebsite.name}后台管理系统</title>
    <meta name="description" content="">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <link rel="stylesheet" href="${ctx}/css/normalize.css">
    <link rel="shortcut icon" href="${ctx }/images/favicon.ico" type="image/x-icon"/>
    <script src="${ctx}/js/modernizr-2.6.2.min.js"></script>

    <style type="text/css">
        /*
         * 登录
         */
        html, body {
            height: 100%;
            margin:0 !important;
        }

        .wrap {
            height: 100%;
        }

        .login-wrap, .login-wrap-bg {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .img-responsive {
            max-width: 100%;
            height: auto;
        }

        .login-form-wrap {
            position: absolute;
            left: 50%;
            top: 50%;
            margin: -197px 0 0 -215px;
            width: 430px;
            height: 395px;
            border-radius: 5px;
            box-shadow: 0 0 5px #0075a1;
            background: url(${ctx}/images/admin/bg-login-form.png) repeat;
        }

        .logo-login {
            margin-top: 40px;
            text-align: center;
        }

        .logo-login p {
            margin-bottom: 10px;
            font-size: 18px;
            font-weight: 700;
            color: #FFF;
            font-family: \5FAE\8F6F\96C5\9ED1;
        }

        .login-item {
            position: relative;
        }

        .login-item s {
            position: absolute;
            left: 7px;
            top: 10px;
        }

        .login-item input[type="text"], .login-item input[type="password"] {
            padding: 10px 10px 10px 36px;
            width: 200px; /*height:20px;*/
            line-height: 20px;
            border: 0;
            background: url(${ctx}/images/admin/bg-login-input.png) repeat;
            border-radius: 3px;
        }

        .login-item input[type="checkbox"] {
            margin-right: 5px;
            width: 13px;
            height: 13px;
            vertical-align: middle;
        }

        .login-form td {
            padding: 7px 10px;
        }

        .login-form-label {
            width: 100px;
            text-align: right;
            color: #FFF;
            font-size: 14px;
        }

        .login-rememberpwd {
            color: #FFF;
            font-size: 12px;
        }

        .login-submit {
            width: 136px;
            height: 36px;
            text-align: center;
            font-size: 14px;
            color: #FFF;
            letter-spacing: 5px;
            border: 1px solid #FFF;
            border-radius: 3px;
            background: none;
        }

        .loginError {
            font-size: 14px;
            color: #F00;
        }

        .captcha-refresh {
            color: #FFF;
            font-size: 14px;
            text-decoration: none;
        }

        .captcha-img {
            display: block;
            float: right;
            margin-top: 5px
        }

        .login-item input[type="text"].required, .login-item input[type="password"].required, input.required {
            border: 1px solid transparent
        }

        label[for=identifyPicCodeInput] {
            position: absolute;
            top: 54px;
            left: 0
        }
    </style>
</head>
<body class="login">
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->

<div class="wrap">
    <div class="login-wrap">
        <div class="login-wrap-bg">
            <img class="img-responsive" src="${ctx}/images/admin/bg-login.jpg"/>
        </div>
        <div class="login-form-wrap">
            <div class="logo-login">
                <img src="${ctx}/images/admin/logo-login.png" width="159" height="35" alt="Anyforweb"/><br/>
                <p>Do any for you on the web</p>
            </div>
            <div class="login-form">
                <form id="loginForm" action="${ctx}/admin/login" method="post">
                    <table cellpadding="0" cellspacing="0">
                        <tbody>
                        <tr>
                            <td class="login-form-label"><label>用户名</label></td>
                            <td>
                                <div class="login-item">
                                    <s class="icon-login-name"></s>
                                    <input class="required" type="text" name="loginName"
                                           value="admin"/>
                                    <span class="loginError"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="login-form-label"><label>密　码</label></td>
                            <td>
                                <div class="login-item">
                                    <s class="icon-login-pwd"></s>
                                    <input class="required" type="password" name="<%--loginpwd--%>password" value="admin123"/>
                                    <span class="loginError"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="login-form-label"><label>验证码</label></td>
                            <td>
                                <div class="login-item" style="width: 300px;">
                                    <input type="text" name="identifyPicCode" class="required" id="identifyPicCodeInput"
                                           style="width: 120px;"/>
						                <span class="captcha-img">
											<img src="${ctx}/captcha/captchaImageWithSession" alt="captcha"
                                                 width="70px;" height="30px" style="vertical-align: middle;"/>
											<a id="picRefresh" class="captcha-refresh" href="javascript:void(0);">看不清?换一张</a>
										</span>
                                    <span class="loginError"> </span>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <div class="loginError" id="loginError">
                                    ${errorMsg}
                                </div>
                            </td>
                        </tr>
                        <%--<tr>
                            <td>&nbsp;</td>
                            <td>
                                <div class="login-item login-rememberpwd">
                                    <input id="rememberPwd" type="checkbox" value=""><label for="rememberPwd">记住密码</label>
                                </div>
                            </td>
                        </tr>--%>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <div class="login-item">
                                    <input class="login-submit" type="submit" onclick="disable" value="登录"/>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div><!-- //wrap -->

<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<!--<script type="text/javascript" src="js/common.js"></script>-->
<script type="text/javascript">
    $(function () {


        //请求图形验证码
        $("#picRefresh,.captcha-img img").click(function () {
            var $img = $('.captcha-img img'),
                    src = $img.attr('src'),
                    index = src.indexOf('?');
            src = -1 < index ? src.substring(0, index) : src;
            src += '?' + Math.floor(Math.random() * 10000088800);
            $img.attr('src', src);
        });

        $("#loginForm").validate({
            rules: {
                identifyPicCode: {
                    checkCaptcha: true
                }
            },
            messages: {
                identifyPicCode: {
                    required: "请输入验证码"
                }
            },
            errorPlacement: function (error, element) {
                /*if("请输入验证码" == error.text()){
                 error.appendTo("#loginError");
                 return false;
                 }*/

                error.appendTo(element.siblings("span:.loginError"));

                //element.css("border","1px solid red ")
            },

            submitHandler: function(form) {
                $(form).find(":submit").attr("disabled", true).attr("value",
                        "登录中...").css("letter-spacing","0");
                form.submit();
            }
        });
        $.validator.addMethod("checkCaptcha", function (value, element) {
            var exists = 0;
            $.ajax({
                url: "${ctx}/captcha/validateSigninCaptcha",
                type: 'post',
                async: false,
                data: {"captcha": value},
                dataType: 'json',
                success: function (data) {
                    exists = data;
                },
                traditional: true
            });
            return this.optional(element) || exists;
        }, "验证码错误");

        $("input[name=loginName]").focus(function(){
            $("#loginError").text("");
        })

    })
    /* <% if(null != request.getParameter("dev")) { %>
     $(function() {
     setTimeout(function() {
     $('input[name=loginName]').val('admin');
     $('input[name=password]').val('admin123');
     $('form').submit();
     }, 500);
     });
    <%}%>*/
</script>
</body>
</html>
