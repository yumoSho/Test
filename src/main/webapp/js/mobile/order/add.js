/**
 * Created by Administrator on 2016/5/3.
 */
//其他js
$(function () {

    /*地推人*/
    $(".pushPerson .pushTop .personHandleImg").click(function () {
        $(this).addClass("down");
        $("img", $(this)).attr("src", contextPath+"/images/mobile/jt_down.png");
        var childShow = $(".pushPerson .personList").css("display");
        if (childShow == "none") {
            $(".pushPerson .personList").show();
        } else {
            $(this).removeClass("down");
            $("img", $(this)).attr("src", contextPath+"/images/mobile/jt_right.png");
            $(".pushPerson .personList").hide();
        }
    });
    $("#personListUl li a").click(function () {
        var numText = $(".personNumber", $(this)).text();
        var nameText = $(".personNames", $(this)).text();
        var id = $(this).data("id");
        $(".pushPerson .pushTop").data("id",id);
        $(".pushPerson .pushTop .personNum").text(numText);
        $(".pushPerson .pushTop .personName").text(nameText);
        $(".pushPerson .personList").hide();
        $(".pushPerson .pushTop .personHandleImg img").attr("src", contextPath+"/images/mobile/jt_right.png");
        $(".pushPerson .pushTop .personHandleImg").removeClass("down");
    });
    /*地推人*/


    setAddressId();
    //设置订单的收货地址 id
    function setAddressId() {
        var id = $(".oneadd").find("input[name=id]").val();
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
                    var data = message.data;
                    //重新绑定购买商品的数据
                    resetProList(data);
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
});

//提交订单
function saveOrder() {
    $(".submitOrder").off('click');
    var canDeliverys = $("input[name=canDelivery]");
    if (!$("#addressId").val()) {
        layer.msg("请选择收货地址", {icon: 2});
        $(".submitOrder").on('click', function () {
            saveOrder();
        });
        return;
    }
    var flag = true;
    canDeliverys.each(function () {
        if ($(this).val() == 'false') {
            flag = false;
            return false;
        }
    });
    if (!flag) {
        layer.msg("商品不支持配送", {icon: 2});
        $(".submitOrder").on('click', function () {
            saveOrder();
        });
        return;
    }
    /*校验购买数量*/
    var canBuy = true;
    $("input[name=canBuy]").each(function (i, obj) {
        if ($(obj).val() == 'false') {
            canBuy = false;
            $(this).siblings(".numberImport").focus();//数量超限的获取焦点
            return false;
        }
    });
    if (!canBuy) {
        layer.msg("商品数量超限", {icon: 2}, function () {
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
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
    var id =$(".pushPerson .pushTop").data("id");
    if(id ==0 ){
        id = undefined;
    }
    debugger;
    $.ajax({
        url: contextPath + '/order/save',
        type: 'post',
        async: false,
        data: {addressId: addressId, invoiceId: invoiceId, orderFrom: $("#orderFrom").val(),pushPeopleId:id},
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
                form.submit();
                $(".submitOrder").off('click').on('click', function () {
                    saveOrder();
                });
            } else {
                layer.msg(message.message, {icon: 2});
                $(".submitOrder").on('click', function () {
                    saveOrder();
                });
            }
        },
        error: function () {
            layer.close(index);
            layer.msg("服务器繁忙", {icon: 2});
            $(".submitOrder").on('click', function () {
                saveOrder();
            });
        }
    });
}
//商品相关js
$(function () {
    //删除弹窗
    $(".remove").click(function () {
        var obj = this;
        $('.mask_div').show();
        $(".folse").click(function () {
            $('.mask_div').hide();
        });
        $(".true").click(function () {
            var id = $(obj).closest(".one_item").find(".head_part input[name=id]").val();
            var addressId = $("#addressId").val();
            $.ajax({
                url: contextPath + '/orderTemp/delete',
                type: 'post',
                async: false,
                data: {id: id, addressId: addressId},
                dataType: 'json',
                success: function (message) {
                    if (message.success) {
                        $('.mask_div').hide();
                        var data = message.data;
                        //重新绑定购买商品的数据
                        resetProList(data);
                        layer.msg("删除成功！", {icon: 1});
                    }
                },
                error: function () {
                }
            });

        });
    });



    //绑定数量修改事件
    bindEvent();

    //商品数量 +1 、-1操作
    //op: 1->加 2->减 3->直接修改数值
    function changeBuyCount(op, obj) {
        unBindEvent();//解除事件
        var id = $(obj).closest(".one_item").find(".head_part input[name=id]").val();
        var addressId = $("#addressId").val();
        var buyCount = 0;
        if (op == 3) {
            buyCount = $(obj).val();
            if (!buyCount || isNaN(buyCount) || buyCount < 1) {
                $(obj).val($(obj).siblings("input:hidden[name=oldCount]").val());
                layer.msg("商品数量超限", {icon: 2});
                bindEvent();
                return false;
            }
        } else {
            buyCount = $(obj).siblings("input.numberImport").val();
        }
        //loading层
        var index = layer.load(2, {
            shade: [0.5, '#fff'], //0.1透明度的白色背景
            scrollbar: false
        });
        $.ajax({
            url: contextPath + '/orderTemp/changeBuyCount',
            type: 'post',
            async: false,
            data: {id: id, addressId: addressId, type: op, buyCount: buyCount},
            dataType: 'json',
            success: function (message) {
                layer.close(index);
                if (message.success) {
                    var data = message.data;
                    //重新绑定购买商品的数据
                    resetProList(data);
                } else {
                    if (op == 3) {
                        var oldVal = $(obj).siblings("input:hidden[name=oldCount]").val();
                        $(obj).siblings("input:hidden").siblings("input.numberImport").val(oldVal)
                    }
                    bindEvent();
                    layer.msg(message.message, {icon: 2});
                }
            },
            error: function () {
                bindEvent();
                layer.close(index);
                layer.msg("服务器繁忙", {icon: 2});
            }
        });
    }

    //重新绑定购买商品的数据
    window.resetProList = function resetProList(data) {
        var container = $("div.cartProList");
        container.empty();
        $.each(data.listMap, function (i, otm) {
            var canDelivery = otm.canDelivery;
            var  freightHtm = '运费：<span>￥<span class="transportMoney">' + otm.freight + '</span>';
            if(!canDelivery){
                freightHtm = '<span style="color: red">不支持配送</span>';
            }

            var productTable = $('<div class="allchk clearfix"><input type="hidden" name="canDelivery" value="' + otm.canDelivery +'"><span class="clear_all  fleft"> 供应商：<span>'+otm.supplierName+'</span>&nbsp;&nbsp;'+freightHtm+'</span></div>');
            container.append(productTable);
            $.each(otm.otList, function (i, tmp) {
                var canBuy = true;
                if (tmp.buyCount > tmp.inventory) {
                    canBuy = false;
                }
                var htm = '<div class="one_item"><div class="head_part clearfix">' +
                    '<input type="hidden" name="id" value="'+tmp.id+'"><div class="remove friendLinks"><img src="'+contextPath+'/images/mobile/remove.png"/></div></div>' +
                    '<div class="cont_part clearfix"><a href="javascript:void(0)" class="img_div"><img src="'+contextPath+'/'+tmp.goodsImg+'"/></a>' +
                    '<a href="javascript:void(0)" class="title">'+tmp.goodsName+'</a>' +
                    '<div class="repertory">库存数量：'+tmp.inventory+'</div>' +
                    '<div class="itemprice">&yen;<span class="unitPrice">'+tmp.price+'</span></div>' +
                    '<div class="itemcount clearfix"><input type="hidden" name="oldCount"><a class="btnCount btncut fleft numDown" href="javascript:void(0);"><img src="'+contextPath+'/images/mobile/cut.jpg"/></a><input type="number" class="numberImport numinput fleft " value="'+tmp.buyCount+'"/><input  type="hidden" name="canBuy" value="'+canBuy+'"><a class="btnCount btnadd numUp fleft" href="javascript:void(0);"><img src="'+contextPath+'/images/mobile/add.jpg"/></a></div>' +
                    '</div></div>' ;
                container.append(htm);
            });
        });
        //绑定事件
        setTimeout(bindEvent, 300);
        $("div.orderMoney .orderNum").text(HgUtil.numberFormat(data.totalNum, 0));
        $("div.orderMoney .allMoneyNum").text(HgUtil.numberFormat(data.totalPrice, 2));
        $("div.orderMoney .transportMoney").text(HgUtil.numberFormat(data.totalFreight, 2));
        $("span.havePayMoneyNum").text(HgUtil.numberFormat(data.totalPriceAndTotalFreight, 2));
        //删除弹窗
        $(".handelAction").click(function () {
            deleteGoods(this);
        });
    }

    function bindEvent() {
        //保留修改前的值
        $("input.numberImport").focus(function () {
            $(this).siblings("input:hidden").val(this.value);
        });
        //修改购买数量事件绑定
        $("input.numberImport").change(function () {
            changeBuyCount(3, this);
        });
        //增加购买数量事件
        $(".numUp").on('click', function () {
            changeBuyCount(1, this);
        });
        //减少购买数量事件
        $(".numDown").on('click', function () {
            changeBuyCount(2, this);
        });
    }

    function unBindEvent() {
        //增加购买数量事件
        $(".numUp,.numDown").off('click');
    }

    function getFrightHtm(otm) {
        var htm = '运费：<span>￥<span class="transportMoney">' + HgUtil.numberFormat(otm.freight, 2) + '</span></span>';
        if (!otm.canDelivery) {
            htm = '<span>不支持配送</span>';
        }
        return htm;
    }

});
//添加新地址相关js
$(function () {
    //打开新地址弹窗
    //更多地址显示
    $(".address_info .title .newadd").click(function () {
        $(".address_info .writeadd").show();
    });
    //关闭新地址弹窗
    $(".resetBtn").click(function () {
        $(".address_info .writeadd").hide();
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
                    }
                });
            },
            error: function () {
                layer.msg("服务器繁忙", {
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
            var container = $("div.addrInfo");
            container.empty();
            $.each(list, function (i, address) {
                if(address.default != 'true'){
                    var htm = '<div class="oneadd active">' +
                        '<input type="hidden" name="id" value="'+address.id+'"><div class="addtype">常用地址</div>' +
                        '<div class="addline"><span class="name">'+address.consignee+'</span>&nbsp;收&nbsp;&nbsp;'+address.consigneePhone+'</div>' +
                        '<div class="addline">'+address.fieldOne+'&nbsp;'+address.fieldTwo+'&nbsp;'+address.fieldThree+'&nbsp;'+address.address+'</div></div>';
                    container.append($(htm));
                        return false;
                }
            });
            //设置订单的收货地址 id
            var id = $("div.shoppingAddr .addrInfo").find("input[name=id]").val();
            $("#addressId").val(id).trigger("change");
        }
    }
});
//更多地址弹窗
$(function () {
    //打开更多地址
    $(".moreAddr").click(function (e) {
        e.preventDefault();
        var addressId = $("#addressId").val();
        if(addressId > 0) {
            $("#moAddress").attr('src', contextPath +"/deliveryAddress/moreAddress?addressId=" + addressId).show();
            $('.wrap').hide();
        }
    });

});

//选择发票弹窗
$(function () {
    //打开发票弹窗
    $(".invoice_info .samePadding").click(function () {
        $(".wrap").hide();
        $("#moAddress").attr('src', contextPath +"/memberInvoice").show();
    });

});