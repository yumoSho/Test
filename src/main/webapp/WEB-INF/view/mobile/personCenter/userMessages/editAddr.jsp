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
    <title>编辑收货地址</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link href="${ctx}/css/mobile/swiper.css" rel="stylesheet"/>
    <link href="${ctx}/js/mobile/layer/need/layer.css" rel="stylesheet"/>
    <link href="${ctx}/css/mobile/personalCenter.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/mui.picker.css" rel="stylesheet" />
    <link href="${ctx}/css/mobile/mui.poppicker.css" rel="stylesheet"/>

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main">
        <form class="addrForm" id="newAddrForm">
            <c:if test="${deliveryAddress.id ne '' }">
                <input type="hidden" name="id" id="" value="${deliveryAddress.id }">
            </c:if>
            <ul class="editAddr">
                <li class="addrSelect">
                    <input type="text"name="cityShow" id="showCity" readonly class="addrInput mui-btn mui-btn-block" placeholder="请选择省市区" />
                    <input type="hidden" name="privinceCode" id="privinceCode" value="${deliveryAddress.privinceCode }">
                    <input type="hidden" name="cityCode" id="cityCode" value="${deliveryAddress.cityCode }">
                    <input type="hidden" name="areaCode" id="areaCode" value="${deliveryAddress.areaCode }">
                </li>
                <li><input type="text" placeholder="请输入详细地址" class="addrInput" name="address" value="${deliveryAddress.address }"/></li>
                <li><input type="text" placeholder="请输入收货人姓名" class="addrInput" name="consignee" value="${deliveryAddress.consignee }"/></li>
                <li><input type="text" placeholder="请输入收货人联系电话" class="addrInput" name="consigneePhone" value="${deliveryAddress.consigneePhone }"/></li>
                <%--<li><input type="text" placeholder="请输入邮编" class="addrInput" name="email" /></li>--%>
            </ul>
            <div class="setDefault"><label><input type="checkbox" name="isDefault" value="1" <c:if test="${deliveryAddress.isDefault}">checked</c:if> />设为默认地址</label></div>
            <div class="personlBut"><input type="button" class="submitEval " value="保存地址信息" /></div>
        </form>
    </div><!-- //main -->
    <div class="leftNavCover"></div>
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
    <div class="errorLabel"></div>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/swiper.min.js"></script>
<script src="${ctx}/js/mobile/layer/layer.js"></script>
<script src="${ctx}/js/mobile/mui.min.js"></script>
<script src="${ctx}/js/mobile/mui.picker.js"></script>
<script src="${ctx}/js/mobile/mui.poppicker.js"></script>
<script src="${ctx}/js/mobile/city.data-pc.js"></script>
</body>

<script>
    $(function () {
        var privinceCode=$("#privinceCode").val();
        (function ($, doc) {
            $.init();
            $.ready(function () {
                //级联示例
                var  cityPicker3 = new $.PopPicker({
                    layer: 3
                });
                cityPicker3.setData(cityData3);

                //  cityPicker3.pickers[0].setSelectedIndex(1);

                cityPicker3.pickers[0].setSelectedValue(privinceCode,0,true);
                //  cityPicker3.setValue("610000",0);
                //   cityPicker3.pickers[0].triggerChange(true);
                // cityPicker3.pickers[1].triggerChange(true);
                //cityPicker3.setValue("610100",1);
                //cityPicker3.setValue("610111",2);
                //  self.elementItems.indexOf(elementItem)

                //cityPicker3.pickers[1].setSelectedValue("610200",0,false);
                //  cityPicker3.pickers[2].setSelectedValue("610103",1,true);

                /*  cityPicker3.pickers[1].setSelectedIndex(2);
                 cityPicker3.pickers[2].setSelectedIndex(3); */
                var showCityPickerButton = doc.getElementById('showCity');
                var cityResult3 = doc.getElementById('showCity');
                showCityPickerButton.addEventListener('tap', function (event) {
                    cityPicker3.show(function (items) {
                        doc.getElementById("privinceCode").value = (items[0] || {}).value;
                        doc.getElementById("cityCode").value = (items[1] || {}).value;
                        doc.getElementById("areaCode").value = (items[2] || {}).value;
                        // jQuery("#_areaCode").val((items[2] || {}).value);
                        cityResult3.value = (items[0] || {}).text + " " + (items[1] || {}).text + " " + (items[2] || {}).text;
                        //返回 false 可以阻止选择框的关闭
                        //    var pp =cityPicker3.getSelectedItems();

                        //return false;

                    });
                }, false);
                var pp =cityPicker3.getSelectedItems();
                cityResult3.value = pp[0].text;

            });

        })(mui, document);




        //表单验证
        $(".addrForm").validate({
            errorLabelContainer: $(".errorLabel"),
            rules: {
                cityShow:{
                    required: true
                },
                address: {
                    required: true
                },
                consigneePhone: {
                    required: true,
                    mobile: true
                },
                consignee: {
                    required: true
                }
            },
            messages: {
                cityShow: {
                    required: "请选择省市区"
                },
                address: {
                    required: "请输入地址"
                },
                consignee: {
                    required: "请输入收货人姓名"
                },
                consigneePhone: {
                    required: "请输入收货人电话",
                    mobile: "请正确输入电话号码"
                }
            }
        });

        //  mui-poppicker-btn-ok
        //  $("#showCity").click();
    });
    $("body").scrollTop(0);
    //保存事件
    $(".submitEval").on("click", function (e) {
        save(e);
    });

    function save() {
        $(".submitEval").off("click");
        var formValidate = $("#newAddrForm").validate();
        if (!formValidate.checkForm()) {
            formValidate.showErrors();
            $(".submitEval").on("click", function (e) {
                save(e);
            });
            return false;
        }

        var formData = $("#newAddrForm").serialize();
        var isDefault = $("input[type=checkbox]:checked").val();
        /* if (isDefault) {
         formData += "&isDefault=" + isDefault;
         }*/
        $.ajax({
            url: contextPath + '/deliveryAddress/saveByMobile',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                if (!message.success) {
                    layer.open({
                        content: message.message,
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    });
                    $(".submitEval").on("click", function (e) {
                        save(e);
                    });
                } else {
                    location.href = '${ctx}/mobile/person-center/userMessages/chuiceAddr';
                }

            },
            error: function () {
                layer.open({
                    content: '服务器忙',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
                $("#newAddrForm input.surBtn").on("click", function (e) {
                    save(e);
                });
            }
        });
    }



</script>
</html>
