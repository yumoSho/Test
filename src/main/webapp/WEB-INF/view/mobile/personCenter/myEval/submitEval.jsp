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
    <title>提交评价</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <meta name="format-detection" content="telephone=no" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

    <link rel="stylesheet" href="${ctx}/css/mobile/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/common.css">
    <link rel="stylesheet" href="${ctx}/css/mobile/base.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/cropper.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/mui.min.css">
    <link href="${ctx}/css/mobile/myEval.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/userCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet" />
    <script src="${ctx}/js/mobile/modernizr-2.6.2.min.js"></script>
    <script src="${ctx}/js/mobile/rem.js"></script>
    <style>
        .evalUpload{float: left}
        .phone-img{widht:1.2rem;height: 1.16rem}
    </style>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <c:set value="true" var="notShowTop"/>
        <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %>
        <div class="main">
            <div class="evalBtns">
                <a href="${ctx}/mobile/person-center/comment" class="on">待评价</a>
                <a href="${ctx}/mobile/person-center/comment?commented=1">已评价</a>
            </div>
            <form id="evalForm">
                <div class="haveEval">
                    <div class="evalItem writeWrap">
                        <div class="evalText clearfix">
                            <a href="${ctx}/mobile/detail/${id}" class="fleft"><img src="${ctx}/${src}" /></a>
                            <textarea class="fright" placeholder="请评价此款商品吧~" maxlength="200" name="content"></textarea>
                        </div>
                        <div class="evalImgs">
                            <a href="javascript:void(0);" class="evalUpload">

                                <div class="del" style="position: absolute;">
                                    删除
                                </div>


                                <input type="hidden" class="phoneHeadVal" name="headImage" value=""/>
                                <img src=""  class="phone-img">

                            </a>
                            <a href="javascript:void(0);" class="evalUpload" style="display: none">
                                <input type="hidden" class="phoneHeadVal" name="headImage" value=""/>
                                <img src=""  class="phone-img">
                            </a>
                            <a href="javascript:void(0);" class="evalUpload"  style="display: none">
                                <input type="hidden" class="phoneHeadVal" name="headImage" value=""/>
                                <img src=""  class="phone-img">
                            </a>
                            <a href="javascript:void(0);" class="evalUpload"  style="display: none">
                                <input type="hidden" class="phoneHeadVal" name="headImage" value=""/>
                                <img src=""  class="phone-img">
                            </a>
                            <a href="javascript:void(0);" class="evalUpload"  style="display: none">
                                <input type="hidden" class="phoneHeadVal" name="headImage" value=""/>
                                <img src=""  class="phone-img">
                            </a>
                            <input id="imageSel" name="imageSel" class="evalImgUpload" type="file" accept="image/*" capture="camera">
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="evalAllNum writeWrap clearfix">
                    <span class="fleft">整体评分：</span>
                    <div class="fleft evalStar" id="grade"></div>
                </div>
                <div class="personlBut"><input type="submit" class="submitEval " value="提交评价" /></div>
            </form>
        </div><!-- //main -->
        <div class="leftNavCover"></div>
        <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
        <div class="errorLabel"></div>
    </div><!-- //wrap -->
    <div id="showEdit" style="display: none;width:100%;height: 100%;position: absolute;top:0;left: 0;z-index: 9;">
        <div style="width:100%;position: absolute;top:10px;left:0px;">
            <button class="mui-btn" data-mui-style="fab" id='cancleBtn' style="margin-left: 10px; top:5em">取消</button>
            <button class="mui-btn" data-mui-style="fab" data-mui-color="primary" id='confirmBtn' style="float:right;margin-right: 10px; top:5em">确定</button>
        </div>
        <div id="report">
            <img src="${ctx}/images/mobile/information_01.jpg" style="width: 300px;height:300px">
        </div>
    </div>
    <script src="${ctx}/js/mobile/jquery-1.7.2.min.js"></script>
    <script>
        window.contextPath="${ctx}";
    </script>
    <script src="${ctx}/js/mobile/jquery.raty.min.js"></script>
    <script src="${ctx}/js/mobile/swiper.min.js"></script>
    <script src="${ctx}/js/mobile/layer/layer.js"></script>
    <script src="${ctx}/js/mobile/jquery.validate.js"></script>
    <script src="${ctx}/js/mobile/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/mobile/lrz.all.bundle.js"></script>
    <script type="text/javascript" src="${ctx}/js/code-html.js"></script>
    <script type="text/javascript" src="${ctx}/js/mobile/cropper.min.js"></script>
    <script>
        $(function () {
            //评分
            $("#grade").raty({
                path: "${ctx}/images/mobile",
                starOff: 'evalIcon3.png',
                starOn: 'evalIcon2.png',
                size: 24,
                score: 1,
                scoreName   : 'grade'
            });
            $("#evalForm").validate({
                errorLabelContainer: $(".errorLabel"),

                rules: {
                    content: {
                        required: true,
                        maxlength:200,
                        minlength:5
                    },
                    imageSel:{
                        accept:false
                    },
                },
                messages: {
                    content: {
                        required: "请输入评价"
                    }
                },
                submitHandler:function(form){
                    var grade = $("input[name='grade']").val();
                    var photos =new Array();
                    $.each($(".phoneHeadVal"),function(i,e){
                        var p = $(this).val()
                        if(p){
                            var head = base64Head="data:image/jpeg;base64,";
                            p= p.split(head)[1];
                            photos.push(p);
                        }
                    });
                    var content= js.lang.String.encodeHtml($("textarea[name='content']").val())
                    $.ajax({
                        url:"${ctx}/mobile/person-center/commenting",
                        type:"post",
                        traditional: true,
                        data:{'goods.id':${id},grade:grade,photoList:photos,'orderDetail.id':${ordid},content:content}
                    }).done(function(data){
                        if(data.success){
                            layer.open({
                                content: '评论成功',
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                            window.location.href="${ctx}/mobile/person-center/comment"
                        }else if(data.message ==""){
                            window.location.href="${ctx}/login?redirectURL=/mobile/person-center/go-comment?ordid=${ordid}&id=${id}&src=${src}"
                        }else if(data.message){
                            layer.open({
                                content: data.message,
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                        }else{
                            layer.open({
                                content: '评论失败',
                                time: 2,
                                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                            })
                        }
                    }).fail(function(){
                        layer.open({
                            content: '评论失败',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        })
                    })
                }

            });
            $imgUpload = null;
            $(".evalUpload").click(function(){
                $imgUpload = $(this);
                $('#imageSel').click();
                $("#imageSel").outerHTML+='';
            })
            /* tu pian */
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
                var i= $imgUpload.index()
                $(".phone-img").eq(i).attr("src", imgurl);
                $(".phoneHeadVal").eq(i).val( imgurl);
                $('#showResult').fadeIn();
                $imgUpload.next(".evalUpload").show();


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

            $('#imageSel').on('change click', function() {
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
                if (that.files.length === 0) return;
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

        });
    </script>
</body>
</html>
