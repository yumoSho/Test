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
    <title>选择收货地址</title>
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
    <div class="main">
        <form>
            <div class="receipAddress padding10">

                <c:forEach items="${deliveryAddresses}" var="da">
                    <div class="receipAd writeWrap">
                        <div class="receipBott">
                            <div class="receipName clearfix">
                                <span class="receipL fleft">${da.consignee }</span>
                                <span class="receipR fright">${da.consigneePhone }</span>
                            </div>
                            <p class="receipText samePadding">${da.fieldOne }${da.fieldTwo }${da.fieldThree }${da.address }</p>
                        </div>
                        <div class="receipDefault clearfix">
                            <div daid="${da.id}" class="receipD fleft  <c:if test="${da.isDefault eq true}">on</c:if>">设为默认</div>
                            <a class="receipEdit fright samePadding" href="${ctx }/mobile/person-center/userMessages/editAddr?id=${da.id}"><i class="edit"></i>编辑</a>
                            <a href="javascript:void(0);" daid="${da.id}" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                        </div>
                    </div>
                </c:forEach>
                <%--<div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">董呱呱</span>
                            <span class="receipR fright">134****2503</span>
                        </div>
                        <p class="receipText samePadding">广灵四路116号5号楼402</p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft on">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>
                <div class="receipAd writeWrap">
                    <div class="receipBott">
                        <div class="receipName clearfix">
                            <span class="receipL fleft">小洪还是小蓝</span>
                            <span class="receipR fright">136****4140</span>
                        </div>
                        <p class="receipText samePadding">
                            广灵四路116号5号楼402广灵四路116号5号楼402广灵四路116号5号楼 402
                        </p>
                    </div>
                    <div class="receipDefault clearfix">
                        <div class="receipD fleft">设为默认</div>
                        <a class="receipEdit fright samePadding" href="editAddr.html"><i class="edit"></i>编辑</a>
                        <a href="javascript:void(0);" class="receipDelet fright samePadding"><i class="delet"></i>删除</a>
                    </div>
                </div>--%>


            </div>
            <div style="height: 30px"></div>
            <div class="redBut" style="position: fixed; top: 79%; left: 0%;"><a href="${ctx }/mobile/person-center/userMessages/editAddr">添加收货地址</a></div>
        </form>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    <div class="errorLabel"></div>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script src="${ctx}/js/lib/layer-v2.2/layer/layer.js"></script>
</body>

<script>
    $(document).ready(function () {

        /* $(".receipCent").click(function () {
         $(this).addClass("on").siblings().removeClass("on");
         var uName = parent.$(this).find(".receipL").text();
         var uTell = parent.$(this).find(".receipR").text();
         var uAddr = parent.$(this).find(".receipText").text();
         parent.$(".topMessage .fleft span").text(uName);
         parent.$(".topMessage .fRight").text(uTell);
         parent.$(".addrShow .detailAddr span").text(uAddr);
         parent.$(".defaultShow").show().siblings().hide();
         }); */
        $(".head .pageTitle .back").click(function () {
            parent.$(".defaultShow").show().siblings().hide();
        });

        $(".receipD").click(function () {
            var val = $(this).attr("daid")
            layer.confirm("确定要设为默认地址？", {
                btn: ["确定", "取消"]
            }, function () {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/person-center/set-def",
                    data: "id=" + val
                }).done(function(data){
                    if(data.success){
                        layer.msg("设置成功！", { icon: 1, time: 2000},function(){
                            location.reload();
                        })
                    }else if(data.message){
                        layer.msg(data.message, { icon: 1, time: 2000});
                    }else{
                        location.href="${ctx}/login";
                    }
                }).fail(function(){
                    layer.msg("服务器繁忙，请稍候再试", { icon: 1, time: 2000});
                })
            });
        });

        $(".receipDelet").click(function () {

            var val = $(this).attr("daid")
            layer.confirm("是否删除此商品？", {
                btn: ["确定", "取消"]
            }, function () {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/person-center/del-add",
                    data: "id=" + val
                }).done(function(data){
                    if(data.success){
                        layer.msg("删除成功！", { icon: 1, time: 2000},function(){
                            location.reload();
                        })
                    }else if(data.message){
                        layer.msg(data.message, { icon: 1, time: 2000});
                    }else{
                        location.href="${ctx}/login";
                    }
                }).fail(function(){
                    layer.msg("服务器繁忙，请稍候再试", { icon: 1, time: 2000});
                })
            });
        });

    });
</script>

</html>
