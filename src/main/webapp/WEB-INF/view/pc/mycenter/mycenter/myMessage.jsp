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
    <title>个人信息</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <link rel="stylesheet" href="${ctx}/js/lib/uploader2/uploader2.css">

    <link rel="stylesheet" href="${ctx}/css/admin/uploader.css" type="text/css">
    <style>
        .upload-image-entry .upload-thumb-wrap .upload-thumb {
            display: table-cell;
            width: 110px;
            height: 110px;
            text-align: center;
            vertical-align: middle;
        }
        ul.upload-list li.upload-entry{
            margin:0;
            border:0;
        }
       /* ul.upload-list li{
            background: url(../../images/pc/headPortraitImg.png) center center no-repeat;
        }*/
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/pc/head.jspf"%>
        <!-- //head -->
       <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>会员中心</span><span class="forGt"> &gt;</span><span>个人信息</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>
                <div class="rightWrap fr">
                    <div class="sameTitle color666">个人信息</div>


                    <div class="headPortraitpadding">
                        <form id="headPortraitpaddingForm" action="info" method="Post">
                            <div class="headPortrait" style="width: 400px; height: 100px;margin: 0 auto;padding-left: 32%;">
                                <ul class="upload-list">

                                    <li id="pro-primary-queue">
                                        <span class="primary-img-txt">上传图片</span>
                                        <c:if test="${not empty member.headImage}">
                                            <div data-saved-url="${ctx}/${member.headImage}">
                                                <input type="hidden" name="headImage" value="${member.headImage}">
                                            </div>
                                        </c:if>
                                    </li>
                                    <script type="text/javascript">
                                        $(function(){
                                            Uploader2({
                                                browse_button: 'pro-primary-queue',
                                                url:'${ctx}/storage/images/preupload',
                                                policy: true,
                                                download:false,
                                                name: 'headImage',
                                                list: 'pro-primary-queue',
                                                filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                mode: 't',
                                                max_file_count: '1',
                                                max_file_size: '10m'
                                            });
                                        });
                                    </script>

                                </ul>
                                <%--<p class="uploadPhoto">
                                    <a href="">上传图像</a>
                                </p>--%>



                            </div>
                            <div  style="width: 400px; height: 50px;margin: 0 auto;padding-left: 32%">
                                请上传110*110像素的图像(.jpg .png .gif),<br>
                                图片大小不超过10M
                            </div>
                            <table class="headPortraittable">
                                <tr>
                                    <td><i>*</i>会&ensp;员&ensp;名：</td>
                                    <td><input type="text" name="name" placeholder="请输入用户名" class="inputText" value="${member.memberName }" disabled="disabled"/></td>
                                </tr>
                                <tr>
                                    <td><i>*</i>手&ensp;机&ensp;号：</td>
                                    <td><input type="text" name="phone" placeholder="请输入电话号码" class="inputText" value="${member.phone }"/></td>
                                </tr>
                                <tr>
                                    <td><i>*</i>邮&emsp;&emsp;箱：</td>
                                    <td><input type="text" name="email" placeholder="请输入邮箱" class="inputText"  value="${member.email }"/></td>
                                </tr>
                                <tr>
                                    <td>性&emsp;&emsp;别：</td>
                                    <td class="radio">
                                        <input type="radio" name="sex" value="0" <c:if test="${member.sex==0 }">checked</c:if>/><span>女</span>
                                        <input type="radio" name="sex" value="1" <c:if test="${member.sex==1 }">checked</c:if>/><span>男</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>生&emsp;&emsp;日：</td>
                                    <td class="poaoson"><input type="text" name="birthdate" value="${member.birthdate }"  class="inputText" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'1970-01-01', maxDate:'2099-12-31'})"/></td>
                                </tr>
                                <tr>
                                    <td>真实姓名：</td>
                                    <td class="poaoson"><input type="text" name="realName" value="${member.realName }" class="inputText" /></td>
                                </tr>
                                <tr>
                                    <td>地&emsp;&emsp;址：</td>
                                    <td class="poaoson"><input type="text" name="address" value="${member.address}" class="inputText" /></td>
                                </tr>
                            </table>
                            <input type="submit" value="保存" class="submintBtn" />
                        </form>
                    </div>
                </div>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   </div>
    <!-- //wrap -->
    <script src="${ctx}/js/jquery.slides.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
    <%--<script type="text/javascript" src="${ctx}/js/admin/util.js"></script>--%>
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lib/uploader2/uploader2.js"></script>
    <script src="${ctx}/js/pc/jquery.validate.js"></script>
    <script type="text/javascript">
    $(function () {
        //表单验证
        $("#headPortraitpaddingForm").validate({
            rules: {
                name:{
                    required: true
                },
                mobile: {
                    required: true,
                    mobile: true,
                },
                email: {
                    required: true,
                    email: true,
                }
            },
            messages: {
                name: {
                    required: "请输入您的会员名"
                },
                mobile: {
                    required: "请输入您的手机号码",
                    mobile: "请输入正确的信息"
                },
                email: {
                    required: "请输入您的邮箱地址",
                    email: "请输入正确的信息"
                }
            }
        })
    });
    </script>
</body>
</html>
