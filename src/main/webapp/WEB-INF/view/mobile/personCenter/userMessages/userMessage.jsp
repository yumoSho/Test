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
    <title>修改密码</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
      <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/cropper.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/mui.min.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
  
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="notShowTop"/>
        <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
        <div class="main">
            <form id="formPersonal" action="${ctx}/mobile/person-center/userMessages/updateUserMessage" method="post">
            <input type="hidden" name="id" value="${member.id}">
                <div class="personalCenter padding10">
                    <div class="personalImg clearfix">
                        <span class="spanPadding">头像</span>
                        <div class="fileUpload">
                            <span class="spanImg">
                             <c:if test="${ empty member.headImage}">
                            <img id="phoneHead" src="${ctx}/images/personPhoto.png" alt="头像"  width="108px" height="108px"/>
                             </c:if>
                            <c:if test="${not empty member.headImage}">
                                <img id="phoneHead" src="${ctx}${member.headImage}_108x109.jpg" alt="头像"  width="108px" height="108px"/>
                            </c:if>
                            </span> 
                            <input type="hidden" id="phoneHeadVal" name="headImage" value=""/>
                            <input id="imageSel" name="imageSel" style="display: none"  type="file" <%--accept="image/*"--%> capture="camera">
                       
                        </div>
                    </div>
                    
                    
                    <div class="personalText">

                        <div class="personl samePadding">
                            <span class="redStar">*</span>会员名：<input type="text" disabled="disabled" value="${member.memberName }"/>
                        </div>
                        <div class="personl samePadding">
                            <span class="redStar">*</span>手机号：<input type="text" name="phone" value="${member.phone }"/>
                        </div>
                        <div class="personl samePadding">
                            <span class="redStar">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;别：
                            <input class="magicRadio" type="radio" name="sex" id="r1" value="1" <c:if test="${member.sex==1}">checked</c:if>>
                            <label for="r1">男</label>
                            <input class="magicRadio" type="radio" name="sex" id="r2" value="0" <c:if test="${member.sex==0}">checked</c:if>>
                            <label for="r2">女</label>
                        </div>
                        <div class="personl samePadding"><span class="redStar">*</span>生&nbsp;&nbsp;&nbsp;&nbsp;日：<input type="date" name="birthdate" value="${member.birthdate }"/></div>
                        <div class="personl samePadding">
                            真实姓名：<input type="text" name="realName" value="${member.realName }"/>
                        </div>
                        <div class="personl samePadding">
                           地址：<input type="text" name="" value=""/>
                        </div>
                    </div>
                </div>
                <div class="redBut"><input type="submit" value="保存信息" class="redHold" /></div>
            </form>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        <div class="errorLabel"></div>
    </div><!-- //wrap -->
   <div>
       <div id="showEdit" style="display: none;width:100%;height: 100%;position: absolute;top:61;left: 0;z-index: 9;">
           <div style="width:100%;position: absolute;top:10px;left:0px;">
               <button class="mui-btn" data-mui-style="fab" id='cancleBtn' style="margin-left: 10px; margin-left: 10px;  top: 3em;">取消</button>
               <button class="mui-btn" data-mui-style="fab" data-mui-color="primary" id='confirmBtn' style="top: 3em;  float: right;   margin-right: 10px;">确定</button>
           </div>
           <div id="report">
               <img src="${ctx}/images/mobile/information_01.jpg" style="width: 300px;height:300px">
           </div>
       </div>

   </div>
    <script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
    <script>
        window.contextPath="${ctx}";
    </script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/common.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx}/js/mobile/lrz.all.bundle.js"></script>
    <script type="text/javascript" src="${ctx}/js/mobile/cropper.min.js"></script>
    <script>
        $(function () {

            $("#formPersonal").validate({
                errorLabelContainer: $(".errorLabel"),
                rules: {
                    phone: {
                        required: true,
                        mobile: true
                    }
                },
                messages: {
                    phone: {
                        required: "请输入电话号码",
                        mobile: "请正确输入电话号码"
                    }
                }
            })
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
                    },

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
                                style: "background-color:red; color:#fff; border:none;font-family:Microsoft YaHei",
                            })
                            location.href="${ctx}/login";
                        }else{
                            $(form).find(":submit").attr("disabled", false).attr("value",
                                    "保存")
                            if(data.message){
                                layer.open({
                                    content: data.message,
                                    time: 2,
                                    style: "background-color:red; color:#fff; border:none;font-family:Microsoft YaHei",
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
                            style: "background-color:red; color:#fff; border:none;font-family:Microsoft YaHei",
                        });
                        location.href="${ctx}/login"
                    });
                }
            });
            
            
            $("#phoneHead").click(function(e){
            
                $("#imageSel").click();
            })
        });
        /*图片上传*/
        function toFixed2(num) {
            return parseFloat(+num.toFixed(2));
        }

        $('#cancleBtn').on('click', function() {
            $("#showEdit").fadeOut();
            $('#showResult').fadeIn();
        });

        $('#confirmBtn').on('click', function() {
            $("#showEdit").fadeOut();

            var $image = $('#report > img');
            var dataURL = $image.cropper("getCroppedCanvas");
            var imgurl = dataURL.toDataURL("image/jpeg", 0.5);
            $("#phoneHead").attr("src", imgurl);
            $("#phoneHeadVal").val(imgurl);
            $('#showResult').fadeIn();

            //var data = $image.cropper("getData");
            //console.log(data);
            //console.log('imgurl' + imgurl);
        });
        function cutImg() {
            $('#showResult').fadeOut();
            $("#showEdit").fadeIn();
            var $image = $('#report > img');
            $image.cropper({
                aspectRatio: 1 / 1,
                autoCropArea: 0.7,
                strict: true,
                guides: false,
                center: true,
                highlight: false,
                dragCrop: false,
                cropBoxMovable: false,
                cropBoxResizable: false,
                zoom: -0.2,
                checkImageOrigin: true,
                background: false,
                minContainerHeight: 450,
                minContainerWidth: 320
            });
        }

        function doFinish(startTimestamp, sSize, rst) {

            $("#report").html('<img src="' + rst.base64 + '" style="width: 100%;height:100%">');
            cutImg();
        }

        $('#imageSel').on('change', function() {
            var startTimestamp = (new Date()).valueOf();
            var that = this;
            var dh = $(window).height();

            $("#report").height(dh);
            var scrollTop = $(document).scrollTop();
            $("#showEdit").css("top",scrollTop);
            //lrz3: https://github.com/think2011/localResizeIMG3
            // lrz(this.files[0], {
            //     width: 800,
            //     height: 800,
            //     // 压缩开始
            //     before: function() {
            //         console.log('压缩开始');
            //     },
            //     // 压缩失败
            //     fail: function(err) {
            //         console.error(err);
            //     },
            //     // 压缩结束（不论成功失败）
            //     always: function() {
            //         console.log('压缩结束');
            //     },
            //     // 压缩成功
            //     done: function(results) {
            //         //console.log(results);
            //         doFinish(startTimestamp, that.files[0].size, results);
            //         results.base64 = null; //释放内存
            //     }
            // });


            //lrz4: https://github.com/think2011/localResizeIMG4
            lrz(this.files[0], {
                width: 800,
                height: 800,
                quality: 0.7
            })
                    .then(function(rst) {
                        //console.log(rst);
                        doFinish(startTimestamp, that.files[0].size, rst);
                        return rst;
                    })
                    .then(function(rst) {
                        // 这里该上传给后端啦
                        // 伪代码：ajax(rst.base64)..

                        return rst;
                    })
                    .then(function(rst) {
                        // 如果您需要，一直then下去都行
                        // 因为是Promise对象，可以很方便组织代码 \(^o^)/~
                    })
                    .catch(function(err) {
                        // 万一出错了，这里可以捕捉到错误信息
                        // 而且以上的then都不会执行

                        alert(err);
                    })
                    .always(function() {
                        // 不管是成功失败，这里都会执行
                    });
        });
        /*图片上传*/
    </script>

</body>
</html>
