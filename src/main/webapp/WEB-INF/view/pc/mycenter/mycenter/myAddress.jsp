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
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
    <style type="text/css">
     	label.error {
     position: initial; 
    width: 100px;
    color: #ff0000; 
   
   
}
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
                    <div class="sameTitle color666">收货地址<a href="javascript:void(0);" class="addAdd fr"><sup>+</sup> 添加新地址</a></div>
                    <div class="addressBox">
                    <c:forEach items="${deliveryAddress.data}" var="da">
                        <div class="addressItem ${da.isDefault?'on':''} clearfix">
                            <span class="defaultAddr fl">默认地址</span>
                            <ul class="addrContent fl">
                                <li class="name">${da.consignee} <span>收</span></li>
                                <li class="addrDetails">${da.fieldOne} ${da.fieldTwo} ${da.fieldThree} ${da.address}</li>
                                <li class="tel">${fn:substring(da.consigneePhone,"0","4" )}*****${fn:substring(da.consigneePhone,"9","11")}</li>
                            </ul>
                            <table class="addrSettingBox fr" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <p class="changeBtn" daid="${da.id}">修改</p>
                                        <p class="deleBtn" daid="${da.id}" >删除</p>
                                        <c:if test="${!da.isDefault}">
                                            <p class="defaultBtn"  daid="${da.id}">设为默认地址</p>
                                        </c:if>
                                    </td>
                                </tr>
                            </table>
                        </div>
                   </c:forEach>
                        
                        <!-- <div class="addressItem  clearfix">
                            <span class="defaultAddr fl">默认地址</span>
                            <ul class="addrContent fl">
                                <li class="name">张女士 <span>收</span></li>
                                <li class="addrDetails">上海 上海市 徐汇区 宛平南路381号宛青大厦310室</li>
                                <li class="tel">1800*****18</li>
                            </ul>
                            <table class="addrSettingBox fr" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <p class="changeBtn">修改</p>
                                        <p class="deleBtn">删除</p>
                                        <p class="defaultBtn">设为默认地址</p>
                                    </td>
                                </tr>
                            </table>
                        </div> -->
                    </div>
                    <div class="addAddrBox ">
                        <a href="javascript:void(0);" class="addAdd"><sup>+</sup> 添加新地址</a>
                    </div>
                    <!-- 分页 -->
                    <form id="pagination-form" class="pagination-form">
	                		<m:pagination totalPages="${deliveryAddress.totalPages}" pageParam="page" skip="false"/>
	                </form>
                </div>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   
   <!-- 添加新地址弹窗-->
        <div class="newAddrPop">
            <div class="newAddrWrap">
                <div class="title">
                    <span>添加新地址</span>
                    <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
                    <a href="javascript:void(0);" class="closeImg fr">
                        <img src="${ctx }/images/pc/shutIcon.png" width="20" height="20" />
                    </a>
                </div>
                <form id="newAddrForm">
                    <table class="addAddrTable">
                        <tr>
                            <td><span class="redStar">*</span>收件人：</td>
                            <td>
                                <input type="text" class="addrSameInput" name="consignee" maxlength="20" id="consignee" />
                           		<input type="hidden" id="adId" name="id"  value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="redStar">*</span>地址：</td>
                            <td>
                                <input type="hidden" id="defaultCityCode" value=""/>
                                <input type="hidden" id="defaultAreaCode" value=""/>
                                <select name="privinceCode" id="privinceCode">
                                    <option>请选择省份</option>
                                    <c:forEach items="${provinces}" var="province">
                                        <option value="${province.provinceCode}">${province.provinceName}</option>
                                    </c:forEach>
                                </select>
                                <select name="cityCode" id="cityCode" >
                                    <option>请选择市/区</option>
                                </select>

                                <select name="areaCode" id="areaCode">
                                    <option value="0">请选择区/县/街道</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="redStar">*</span>详细地址：</td>
                            <td>
                                <input type="text" class="detaileAddr" name="address" maxlength="120" id="address" />
                            </td>
                        </tr>
                        <tr>
                            <td><span class="redStar">*</span>手机号码：</td>
                            <td>
                                <input type="text" class="addrSameInput" name="consigneePhone" maxlength="11" id="consigneePhone"/>
                            </td>
                        </tr>
                        <tr>
                            <td>固定电话：</td>
                            <td>
                               <input type="text" class="addrSameInput"  name="telephone" maxlength="20" id="telephone" />
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <label>
                                    <input type="checkbox"  id="isDefault" name="isDefault" value="1"/>
                                    设为默认地址
                                </label>
                            </td>
                        </tr>
                    </table>
                    <div class="addrSubmitBtns">
                        <input type="button" class=" surBtn btn" value="确定" />
                        <input type="reset" class=" resetBtn btn" value="取消" />
                    </div>
                </form>
            </div>
        </div>
   </div>
    <!-- //wrap -->
      <script src="${ctx}/js/lib/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/cityGanged.js"></script>
    <script type="text/javascript">

    $(function () {
        //删除弹窗
        $(".deleBtn").click(function () {
            var val = $(this).attr("daid")
            layer.confirm("是否删除此地址？", {
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


        $(".defaultBtn").click(function () {
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
        //  表单验证

        $.validator.addMethod("areaSet", function (value, element) {
            return this.optional(element) || ( $("#privinceCode option:selected").val() && $("#cityCode option:selected").val() && $("#areaCode option:selected").val());
        }, "请选择地址");
        
        function initAddPop(){
        	 $(".newAddrPop").fadeIn();
             $("#adId").val("");
             $("#consignee").val("");
             $("#address").val("");
             $("#consigneePhone").val("");
             $("#telephone").val("");
             $("#isDefault").attr("checked",false);
             $("#privinceCode").val("");
             $("#cityCode").val("");
             $("#cityCode option").remove();
             $("<option value=''>请选择市区</option>").appendTo("#cityCode");
             $("#areaCode").val("");
             $("#areaCode option").remove();
             $("<option  value=''>请选择县/区/街道</option>").appendTo("#areaCode");
             //添加新地址弹窗距离屏幕上边距离
             var windowHeight = $(window).height();
             var topMargin = (windowHeight - $(".newAddrWrap").height()) / 2;
             $(".newAddrWrap").css("top", topMargin);
             $(".newAddrPop").fadeIn();
        }
        
        //打开弹窗
        $(".addAdd").click(function () {
        	 $(".newAddrPop").fadeIn();
             $("#adId").val("");
             $("#consignee").val("");
             $("#address").val("");
             $("#consigneePhone").val("");
             $("#telephone").val("");
             $("#isDefault").attr("checked",false);
             $("#privinceCode").val("");
             $("#cityCode").val("");
             $("#cityCode option").remove();
             $("<option value=''>请选择市区</option>").appendTo("#cityCode");
             $("#areaCode").val("");
             $("#areaCode option").remove();
             $("<option  value=''>请选择县/区/街道</option>").appendTo("#areaCode");
             //添加新地址弹窗距离屏幕上边距离
             var windowHeight = $(window).height();
             var topMargin = (windowHeight - $(".newAddrWrap").height()) / 2;
             $(".newAddrWrap").css("top", topMargin);
             $(".newAddrPop").fadeIn();
        });
        $("#privinceCode").change(function(){
            $("#cityCode").val("");
            $("#cityCode option").remove();
            $("<option value=''>请选择市区</option>").appendTo("#cityCode");
            $("#cityCode").change();
        });

        $("#privinceCode").change(function(){
            $("#cityCode option").remove();
            $("<option value=''>请选择市区</option>").appendTo("#cityCode");
            $("#areaCode").val("");
            $("#areaCode option").remove();
            $("<option value=''>请选择县/区/街道</option>").appendTo("#areaCode");
        })
       /*  $(".addAddrBox .addAdd").click(function () {debugger;
            $(".newAddrPop").fadeIn();
            var marginTop =  ($(document).scrollTop(400));
            $(".newAddrWrap").css("top", marginTop);
        }); */
        //关闭弹窗
        $(".resetBtn,.closeImg ").click(function () {
            $(".newAddrPop").fadeOut();
            $(".correct").remove();
            $("#newAddrForm").validate({}).resetForm()
        });

        $(".changeBtn").off("click").on("click",function(){
            var addrid = $(this).attr("daid");
            $.ajax({
                url: "${ctx}/person-center/edit?id="+addrid,
                dataType: "json",
                cache: false
            }).done(function(data){
                if(data.success){
                    $(".addAdd").click();
                    var da=data.data;
                    $("#adId").val(da.id);
                    $("#consignee").val(da.consignee);
                    $("#address").val(da.address);
                    $("#consigneePhone").val(da.consigneePhone);
                    $("#telephone").val(da.telephone);
                    $("#isDefault").attr("checked",da.isDefault)
                    var  sCode = da.privinceCode;//省
                    var  cCode = da.cityCode;	//市
                    var  aCode = da.areaCode;  //区
                    $("#defaultCityCode").val(cCode);
                    $("#defaultAreaCode").val(aCode);
                    $("#privinceCode").val(sCode);
                    if (!sCode) return;
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/hatCity/ajaxListCityBySuperCode",
                        data: "superCode=" + sCode,
                        error: function () {
                        },
                        success: function (data) {
                            if (data && data.length > 0) {
                                var cityDom = $("#cityCode").empty();
                                cityDom.append("<option value=''>请选择市/区</option>");
                                $.each(data, function (i, obj) {
                                    var option = $('<option value="' + obj.cityCode + '">' + obj.cityName + '</option>');
                                    cityDom.append(option);
                                });

                                /*如果是编辑页面，则需要选中的默认值*/
                                var cityCode = $("#defaultCityCode").val();
                                if (cityCode) {
                                    $("#cityCode").val(cityCode);
                                } else {
                                    cityCode = $("#cityCode").val();
                                }

                            }
                        }
                    });
                    $("<input id='defaultCityCode' value='+cCode+'/>").appendTo(document);

                    if (!cCode) return;
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/hatArea/ajaxListAreaBySuperCode",
                        data: "superCode=" + cCode,
                        error: function () {
                        },
                        success: function (data) {
                            if (data && data.length > 0) {
                                var areaDom = $("#areaCode").empty();
                                areaDom.append("<option  value=''>请选择县/区/街道</option>");
                                $.each(data, function (i, obj) {
                                    var option = $('<option value="' + obj.areaCode + '">' + obj.areaName + '</option>');
                                    areaDom.append(option);
                                });
                                $("#areaCode").val(aCode);
                            }
                        }
                    });

                }

            });
        });


        $("#newAddrForm").validate({
            rules: {
                consignee: {
                    required: true
                },
                areaCode: {
                    required: true,
                    areaSet: true
                },
                address: {
                    required: true
                },
                consigneePhone: {
                    required: true,
                    /* mobile: true */
                }
            }, messages: {
                consignee: {
                    required: "此项必填"
                },
                areaCode: {
                    required: "请选择地址"
                },
                address: {
                    required: "此项必填"
                },
                consigneePhone: {
                    required: "此项必填",
                   /*  mobile: "请输入电话号码" */
                }
            },
            highlight: function (element, errorClass) {
                $(element).siblings(".correct").remove();
            },
            unhighlight: function (element, errorClass) {
                if (element.name == "telephone") return;
                $(element).parents("td").append("<span class='correct'></span>");
            }
        });

        //添加新地址表单提交
        $("#newAddrForm input.surBtn").on("click", function (e) {
            save();
        });
        function save() {
            $("#newAddrForm input.surBtn").off("click");
            var formValidate = $("#newAddrForm").validate();
            if (!formValidate.checkForm()) {
                formValidate.showErrors();
                $("#newAddrForm input.surBtn").on("click", function (e) {
                    save(e);
                });
                return false;
            }

            var formData = $("#newAddrForm").serialize();
            var isDefault = $("input[type=checkbox]:checked").val();
            if (isDefault) {
                formData += "&isDefault=" + isDefault;
            }
            $.ajax({
                url: contextPath + '/deliveryAddress/save',
                type: 'post',
                async: false,
                data: formData,
                dataType: 'json',
                success: function (message) {
                    var icon = 1;
                    var msg = "保存成功";
                    if (!message.success) {
                        icon = 2;
                        msg = message.message;
                        if(msg){
                            layer.msg(msg, {icon: icon,time: 2000 });
                        }else{
                            location.href="${ctx}/login";
                        }
                        $("#newAddrForm input.surBtn").on("click", function (e) {
                            save(e);
                        });
                    } else {

                    }
                    layer.msg(msg, {
                        icon: icon,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        if (icon == 1) {
                            //关闭弹窗
                            $(".newAddrPop").fadeOut();
                            document.documentElement.style.overflow = 'scroll';
                            //重新绑定事件
                            $("#newAddrForm input:reset").click();
                            $("#newAddrForm input.surBtn").on("click", function (e) {
                                save(e);
                            });
                            location.reload();
                        }
                    });
                },
                error: function () {
                    layer.msg("服务器忙，请稍后再试", {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    $("#newAddrForm input.surBtn").on("click", function (e) {
                        save(e);
                    });
                }
            });
        }
    });
</script>
   
</body>
</html>
