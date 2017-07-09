/**
 * Created by Administrator on 2016/5/3.
 */
//其他js
$(function () {
    //添加优惠券js
    $(".useCardInner .useCardBtn ").click(function () {
        $(this).toggleClass("on");
        $(this).siblings(".cardSelect").toggle();
    });
    //收货地址添加样式
    $(".checkOrderMessage .addrItem").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
        setAddressId();
    });
    //设置订单的收货地址 id
    function setAddressId() {
        var id = $("div.shoppingAddr .addrInfo .on").find("input[name=id]").val();
        $("#addressId").val(id).trigger("change");
    }

    //绑定收货地址改变事件
    $("#addressId").on('change', function () {
        addressChange()
    });
    //当收货地址切换之后，重新加载商品页面
    function addressChange() {
        //loading层
        var index = layer.load(2, {
            shade: [0.5, '#fff'], //0.1透明度的白色背景
            scrollbar: false
        });
        var addressId = $("#addressId").val();
        $.ajax({
            url: contextPath + '/orderTemp/changAddress',
            type: 'post',
            async: false,
            data: {addressId: addressId},
            dataType: 'json',
            success: function (message) {
                layer.close(index);
                if (message.success) {
                    var totalFreight = message.data;
                    var freightNumber = HgUtil.numberFormat(totalFreight,2);
                    if(freightNumber < 0){
                        $('span.totalFreight').replaceWith('<span class="totalFreight" style="color:red">不支持该区域配送</span>');
                        $("#canDelivery").val(false);
                        totalFreight = 0;
                    }else {
                        $("#canDelivery").val(true);
                        var baoyou = $("#baoYou").val();
                        if(baoyou && baoyou == 'true') {
                            $('span.totalFreight').replaceWith('<span class="totalFreight" style="color:red">包邮</span>');
                            totalFreight = 0;
                        }else{
                            $('span.totalFreight').text("￥" +freightNumber);
                        }

                    }
                    var couponPrice = Number($("input[name=cradSelect]:checked").val());
                        var totalPrice = $("#totalPrice").text();
                        var totalPriceAndTotalFreight = HgUtil.numberFormat(Number(totalFreight) + Number(totalPrice) - couponPrice,2);
                        $("#totalPriceAndTotalFreight").text(totalPriceAndTotalFreight);
                }
            },
            error: function () {
            }
        });
    }
});
//提交订单
$(function () {
    $(".submitOrder").on('click', function () {
        saveOrder();
    });

    //优惠券选择
    $("input[name=cradSelect]").click(function(){
        var price = Number($(this).val());
        var cId = Number($(this).attr("cId"));
        var totalPrice = $("#totalPrice").text();
        var totalFreight = $('span.totalFreight').text();
        $("#couponPrice").text(HgUtil.numberFormat(price,2));
        totalFreight = totalFreight.replace("￥","");
        if(isNaN(totalFreight)) {
            totalFreight = 0;
        }
        var totalPriceAndTotalFreight = HgUtil.numberFormat(Number(totalFreight) + Number(totalPrice) - price,2);
        $("#totalPriceAndTotalFreight").text(totalPriceAndTotalFreight);
        $("#couponId").val(cId);
    });


});

//提交订单
function saveOrder() {
    $(".submitOrder").off('click');
    var flag = $("#canDelivery").val();
    if (!$("#addressId").val()) {
        layer.msg("请选择收货地址", {icon: 2});
        $(".submitOrder").on('click', function () {
            saveOrder();
        });
        return;
    }
    if (!flag ||   flag == 'false') {
        layer.msg("不支持该地区配送", {icon: 2});
        $(".submitOrder").on('click', function () {
            saveOrder();
        });
        return;
    }
    //loading层
    var index = layer.load(2, {
        shade: [0.5, '#fff'], //0.1透明度的白色背景
        scrollbar: false
    });
    var addressId = $("#addressId").val();
    var invoiceId = $("#invoiceId").val();
    var remark =$("#remark").val();
    var timeSelect = $("input[name=timeSelect]:checked").val();
    $.ajax({
        url: contextPath + '/order/save',
        type: 'post',
        async: false,
        data: {addressId: addressId, couponId: $("#couponId").val(), orderFrom: $("#orderFrom").val(),remark:remark,timeSelect:timeSelect},
        dataType: 'json',
        success: function (message) {
            layer.close(index);
            if (message.success) {
                var data = message.data;
                var payTotalPrice = data.payTotalPrice;
                var orderGroupCode = data.orderGroupCode;
                var form = $('<form action="' + contextPath + '/order/success" method="post">' +
                    '<input type="hidden" name="orderGroupCode" value="' + orderGroupCode + '">' +
                    '<input type="hidden" name="payTotalPrice" value="' + payTotalPrice + '"></form>');
                form.append('<input id="goSuccessBtn" type="submit">');
                $("#goSuccess").append(form);
                $("#goSuccessBtn").click();
            } else {
                layer.msg(message.message, {icon: 2});
                $(".submitOrder").on('click', function () {
                    saveOrder();
                });
            }
        },
        error: function () {
            layer.close(index);
            layer.msg("服务器忙，请稍后再试", {icon: 2});
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
        }
    });
}

//添加新地址相关js
$(function () {
    //打开新地址弹窗
    $(".newAddr ").click(function () {
        //document.documentElement.style.overflow = 'hidden';
        $(".newAddrPop").fadeIn();
        //添加新地址弹窗距离屏幕上边距离
        var windowHeight = $(window).height();
        var topMargin = (windowHeight - $(".newAddrWrap").height()) / 2;
        $(".newAddrWrap").css("top", topMargin);
    });
    //关闭新地址弹窗
    $(".newAddrPop .resetBtn,.newAddrPop .closeImg ").click(function () {
        $(".newAddrPop").fadeOut();
        //document.documentElement.style.overflow = 'scroll';
    });
    //  添加新地址表单验证
    $("#newAddrForm").validate({
        rules: {
            consignee: {
                required: true
            },
            area: {
                required: true,
                areaSet: "areaSet"
            },
            address: {
                required: true
            },
            consigneePhone: {
                required: true,
                mobile: true
            }
        }, messages: {
            consignee: {
                required: "此项必填"
            },
            area: {
                required: "请选择地址"
            },
            address: {
                required: "此项必填"
            },
            consigneePhone: {
                required: "此项必填",
                mobile: "请输入电话号码"
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
                    $("#newAddrForm input.surBtn").on("click", function (e) {
                        save(e);
                    });
                } else {
                    var addrList = message.data;
                    //重置收货地址
                    resetAddrList(addrList);
                    //刷新 更多收货地址
                    resetAddressList(addrList);
                }
                layer.msg(msg, {
                    icon: icon,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    if (icon == 1) {
                        //关闭弹窗
                        $(".newAddrPop").fadeOut();
                        //document.documentElement.style.overflow = 'scroll';
                        //重新绑定事件
                        $("#newAddrForm input:reset").click();
                        $("#newAddrForm input.surBtn").on("click", function (e) {
                            save(e);
                        });
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

    //局部刷新收货地址（两个）
    function resetAddrList(list) {
        if (list.length > 0) {
            var container = $("div.addrList .addrInfo");
            container.empty();
            $.each(list, function (i, address) {
                var htm = '<div class="addrItem fl' + (address.isDefault ? ' on">' : '">') +
                    '<input type="hidden" name="id" value="' + address.id + '">' +
                    '<p>常用地址</p><div>' + address.consignee + ' 收</div>' +
                    '<p class="font-color">' + address.fieldOne + '&nbsp;' + address.fieldTwo + '&nbsp;' + address.fieldThree + '<br/>' + address.address + '</p>' +
                    '<p class="font-color">' + address.consigneePhone + '</p></div>';
                container.append($(htm));
                if (i == 1) {
                    return false;
                }
            });
            //收货地址添加样式
            $(".checkOrderMessage .addrItem").click(function () {
                $(this).addClass("on").siblings().removeClass("on");
            });
            //设置订单的收货地址 id
            var id = $("div.shoppingAddr .addrInfo .on").find("input[name=id]").val();
            $("#addressId").val(id).trigger("change");
        }
    }

    //刷新更多收货地址
    function resetAddressList(list) {
        if (list.length > 0) {
            var container = $("div.moreAddrWrap .addressList");
            container.empty();
            $.each(list, function (i, address) {
                var htm = '<div class="addrContent ' + (address.isDefault ? ' on">' : '">') +
                    '<input type="hidden" name="id" value="' + address.id + '">' +
                    '<div class="name"><span>' + address.consignee + '</span>收</div>' +
                    '<p>' + address.fieldOne + '&nbsp;' + address.fieldTwo + '&nbsp;' + address.fieldThree + '&nbsp;' + address.address + '</p>' +
                    '<div class="tellNum">' + address.consigneePhone + '</div></div>';
                container.append($(htm));
            });
            $(" .choiceAddrWrap .addrContent").click(function () {
                $(this).addClass("on").siblings().removeClass("on");
            });
        }
    }
});
//更多地址弹窗
$(function () {
    //更多地址选中样式
    $(" .choiceAddrWrap .addrContent").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
    });
    //打开更多地址
    $(".moreAddr").click(function () {
        //document.documentElement.style.overflow = 'hidden';
        $(".moreAddrWrap").fadeIn();
        //添加新地址弹窗距离屏幕上边距离
        var windowHeight = $(window).height();
        var topMargin = (windowHeight - $(".choiceAddrWrap").height()) / 2;
        $(".choiceAddrWrap").css("top", topMargin);
    });
    //关闭更多地址 弹窗
    $(".choiceAddrWrap .closeImg,.choiceAddrWrap .sure").click(function () {
        $(".moreAddrWrap").fadeOut();
        //document.documentElement.style.overflow = 'scroll';
    });
    //点击确定时的操作
    $("div.choiceAddrWrap .sure").on('click', function () {
        //设置订单收货地址id
        var onAddr = $("div.moreAddrWrap .addressList .on");
        var id = onAddr.find("input[name=id]").val();
        $("#addressId").val(id).trigger("change");
        //将展示的两个收货地址 替换为 选中的收货地址
        var container = $("div.shoppingAddr .addrInfo");
        container.empty();
        container.append(onAddr.clone().addClass("fl"));
    });
});