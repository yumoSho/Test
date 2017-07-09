﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>用户反馈</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/index.css" rel="stylesheet" />
    <style>
       .copy {
            margin-top: .55rem;
            display: inline-block;
            width: 1.55rem;
            height: .62rem;
            background: #19aa4b;
            line-height: .62rem;
            text-align: center;
            font-size: .24rem;
            color: #fff;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="writeWrap">
        <div class="head">
            <div class="pageTitle headTop samePadding clearfix">
                <a href="javascript:history.go(-1);" class="back fleft"><img src="${ctx}/images/mobile/icon14.png" /></a>
                <span>用户反馈</span>
            </div>
        </div><!-- //head -->
        <c:if test="${not empty sessionScope.member}">
            <div class="main samePadding writeWrap">
                <p class="suggestTitle font24">尊敬的用户：<br />您好,为了给您提供更好的服务，请留下您对网站的意见和建议！</p>
                <p class="suggestName">发表内容：</p>
                <form name="userSuggest" id="askForm" class="userSuggestArea">
                    <input name="memberId" type="hidden" value="${sessionScope.member.id}">
                    <textarea class="suggestArea" name="content"></textarea>
                    <input type="button" value="提交反馈" id="feedbackSubmit" class="s fleft" />
                    <a  href="${ctx}/mobile/"  class="fright" >取消</a>
                </form>
            </div><!-- //main -->
        </c:if>
        <c:if test="${empty sessionScope.member}">
            <div class="main samePadding writeWrap" style="text-align: center;">
                <p class="suggestTitle font24" style="font-size: 20px;color: #19aa4b;margin-top: 50px;">请先登录，再进行反馈</p>

                <a class="copy" href="${ctx}/login">登录</a>
            </div><!-- //main -->
        </c:if>
        <div class="errorLabel"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    </div><!-- //wrap -->
    <script src="${ctx}/js/mobile/jquery.validate.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>

    <script>
        $(function () {
            //表单验证
            $(".userSuggestArea").validate({
                errorLabelContainer: $(".errorLabel"),
                rules: {
                    content: {
                        required: true
                    }
                },
                messages: {
                    content: {
                        required: "请输入要发表的内容！"
                    }
                }
            })
        });
    </script>
</body>
<script>
    /*用户反馈绑定*/
    $("#feedbackSubmit").click(function () {
        memberFeedback();

    });

    /*用户反馈*/
    function memberFeedback(){
        $("#feedbackSubmit").off('click');
        /*validate 验证*/
        var formValidate = $("#askForm").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#feedbackSubmit").on('click',function(){
                memberFeedback();
            });
            return false;
        }


        var formData = $("#askForm").serialize();
        $.ajax({
            url: window.contextPath + '/person-center/saveByAjax',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                /*  if(message.result){ debugger;
                 location.reload();
                 }else{
                 debugger;
                 重新绑定注册事件
                 $("#feedbackSubmit").on('click',function(){
                 memberFeedback();
                 });
                 }*/
                layer.open({
                    content: '提交成功',
                    time: 3,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                })
                window.location.href =  contextPath+"/mobile/";

            },
            error:function(){
                alert("系统错误");
                $("#feedbackSubmit").on('click',function(){
                    memberFeedback();
                });
            }
        });

    };
</script>
</html>
