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
    <title>注册-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link href="${ctx}/css/pc/login.css" rel="stylesheet" />
    <style type="text/css" >
      /*  .proContent p{
            font-size:14px; color:#666;line-height:24px; height:320px; letter-spacing:1px; overflow:auto;
        }*/
    </style>
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
    </div><!-- //head -->
    <div class="registMain">
        <p>
                <span class="fl">
                    欢迎注册
                </span>
                <span class="fr">
                    已有账号，
                    <a href="${ctx }/login">去登录</a>
                </span>
        </p>

        <!--   //手机注册  -->
        <div class="phoneInfo">
            <p>
                <a href="javascript:void(0);" class="fl phoneRegist">手机注册</a>
                <a href="javascript:void(0);" class="fr emailRegist">邮箱注册</a>
            </p>
            <form id="phoneForm">
                <input type="hidden" name="regType" value="1">
                <input type="hidden" name="recommendedCode" value="${recommendedCode }">
                <table>
                    <tr>
                        <td><span class="redStar">*</span><span>用 户 名：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" class="txtWidth" placeholder="4-20位字符，支持汉字、字母、数字组合" name="memberName" id="memberName" maxlength="20">
                            </div>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span>手 机 号：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" name="phone" id="phone" maxlength="11" class="txtWidth" placeholder="请输入手机号码">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>验 证 码：</td>
                        <td>
                            <div class="locat changeL1">
                                <input type="text" placeholder="输入验证码" maxlength="4" name="iCodes" id="codes" class="verCode">
                            </div>
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="marL1">
                                <img src="${ctx}/captcha/captchaImage" alt="验证码"
                                     style="cursor: pointer;" width="98" height="44"
                                     id="kaptchaImage" class="change"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>短信验证码：</td>
                        <td>
                            <div class="locat changeL1">
                                <input type="text" class="verificationCodes verCode" name="pCodes" maxlength="6" id="pCodes">
                            </div>
                        </td>
                        <td>
                            <input type="button" class="getCode msgCode getMsgCode getCodes" value="获取短信验证码 " />
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>密 码：</td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="password" class="password txtWidth" placeholder="6-20位字符，建议由字母，数字和符号两种以上组合" name="password" maxlength="20">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span>确 认 密 码：</td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="password" class="txtWidth" name="confirmPassword" maxlength="20" placeholder="再次输入密码">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="checkbox" name="sure" id="sure" checked="checked" class="agreeBox" />我已阅读并同意
                                <a class="protocol" href="javascript:void(0);">《用户注册协议》</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <input type="button" class="phBtn txtWidth getCode submit" id="submit" value="立即注册 " />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--  //邮箱注册  -->
        <div class="mailInfo">
            <p>
                <a class="fl phoneRegist">手机注册</a>
                <a class="fr emailRegist">邮箱注册</a>
            </p>
            <form id="mailForm">
                <input type="hidden" name="regType" value="2">
                <input type="hidden" name="recommendedCode" value="${recommendedCode }">
                <table>
                    <tr>
                        <td><span class="redStar">*</span><span>用 户 名：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" class="txtWidth" placeholder="4-20位字符，支持汉字、字母、数字组合" name="memberName" id="imemberName" maxlength="20">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span>邮 箱 号：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="text" class="txtWidth" placeholder="请输入邮箱号" name="email" id="mail" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span>验 证 码：</span></td>
                        <td>
                            <div class="locat changeL1">
                                <input type="text" placeholder="输入验证码" maxlength="4" name="iCodes" id="icodes" class="verCode">
                            </div>
                        </td>
                        <td>
                            <a href="javascript:void(0);">
                                <img src="${ctx}/captcha/captchaImage" alt="验证码"
                                     style="cursor: pointer;" width="98" height="44"
                                     id="ikaptchaImage" class="change"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span>密 码：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="password" class="password2 txtWidth" placeholder="6-20位字符，建议由字母，数字和符号两种以上组合" name="password" maxlength="20">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="redStar">*</span><span>确 认 密 码：</span></td>
                        <td colspan="2">
                            <div class="locat">
                                <input type="password" class="txtWidth" name="confirmPassword" maxlength="20" placeholder="再次输入密码">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <div class="locat isure" style="width: 300px;">
                                <input type="checkbox" name="sure" id="isure" checked="checked" class="agreeBox" />我已阅读并同意
                                <a class="protocol" href="javascript:void(0);">《用户注册协议》</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <input type="button" class="phBtn txtWidth getCode submit" id="isubmit" value="立即注册 " />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div><!-- //main -->
    <div class="foot">
        <%@ include file="/WEB-INF/view/include/pc/copyRight.jspf" %>
    </div><!-- //foot -->
    <!--//用户注册协议弹出层-->
    <div class="popAgreements" style="display:none;">
        <div class="diaShadow"></div>
        <div class="diaContent">
            <div class="diaTitle clearfix">
                <span class="fl">${dictionary.dicName}</span>
                <a href="javascript:void(0);" class="closeDialog fr"></a>
            </div>
            <div class="proContent" style="padding:36px 28px 50px;height: 350px;overflow-y: scroll">
                ${dictionary.content}
            </div>
            <div class="diaFoot">
                <input type="button" value="同意并继续" />
            </div>
        </div>
    </div>
    <!--//用户注册协议弹出层-->
</div><!-- //wrap -->

<script src="${ctx}/js/pc/loginAndRegister/register.js"></script>
<script>
    $(function () {
        //切换
        $(".emailRegist").click(function () {
            $(".phoneInfo").hide();
            $(".mailInfo").show();
        });
        $(".phoneRegist").click(function () {
            $(".mailInfo").hide();
            $(".phoneInfo").show();
        });

        //用户注册协议弹窗
        var agreeBtn = $(".protocol");
        agreeBtn.click(function () {
            $(".popAgreements").show();
        });
        //关闭注册协议弹窗
        $(".closeDialog,.diaFoot").click(function () {
            $(".popAgreements").fadeOut("fast");
        });
        $(".diaFoot").on('click', function() {
            $("#sure,#isure").attr("checked","checked");
        });


    });
</script>
</body>
</html>
