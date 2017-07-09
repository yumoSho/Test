//其他js效果
$(function () {
    //删除弹窗
    $(".handelAction").click(function () {
        layer.confirm("是否删除此商品？", {
            btn: ["确定", "取消"]
        }, function () {
            layer.msg("删除成功！", { icon: 1 });
        });
    });
    //收货地址添加样式
    $(".checkOrderMessage .addrItem").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
    });
});
//订单相关js
$(function () {
    //给单个订单金额赋值
    $(" .productTable tbody tr").each(function () {
        var unitPrice = +$(".unitPrice", $(this)).text();
        var proNums = +$(".numberImport ", $(this)).val();
        $(".priceNum", $(this)).text(unitPrice * proNums);
    });
    //表格奇数行添加背景颜色
    $(" .productTable  tr:even").css("background-color", "#fafafa");
    //购物车增加：
    $(".numUp").click(function () {
        var oo = $(this).prev().val();
        $(this).prev().val(parseInt($(this).prev().val()) + 1);
        var num = parseInt($(this).prev().val());
        getLineProPrice();
        getAllPriceAndPrice()
    });
    //购物车减少：
    $(".numDown").click(function () {
        var oo = $(this).next().val();
        if (parseInt(oo) > 1) {
            $(this).next().val(parseInt($(this).next().val()) - 1);
            var num = parseInt($(this).next().val());
            getAllPriceAndPrice();
        }
    });
    //获取单个行产品的金额
    function getLineProPrice() {
        $(" .productTable tbody tr").each(function () {
            var unitPrice = +$(".unitPrice", $(this)).text();
            var proNums = +$(".numberImport ", $(this)).val();
            $(".priceNum", $(this)).text(unitPrice * proNums);
        });
    };
    //初始化计算数量和价格
    var proNums = 0;
    var proPrice = 0;
    var lastHadTopay = 0;
    $(".productTable tbody tr").each(function () {
        var $this = $(this);
        var trNums = +$(".numberImport", $this).val();
        var trPrice = +$(".priceNum", $this).text();
        proNums += trNums;
        proPrice += trPrice;
    });
    lastHadTopay = proPrice + +$(".transportMoney").text();
    $(".orderNum").text(proNums);
    $(".allMoneyNum").text(proPrice);
    $(".havePayMoney").text("￥" + lastHadTopay);
    //获得商品总数量
    function getAllPriceAndPrice() {
        var proNums = 0;
        var proPrice = 0;
        var lastHadTopay = 0;
        $(".productTable tbody tr").each(function () {
            var trNums = +$(".numberImport", $(this)).val();
            var trPrice = +$(".priceNum", $(this)).text();
            proNums += trNums;
            proPrice += trPrice;
        });
        lastHadTopay = proPrice + +$(".transportMoney").text();
        $(".orderNum").text(proNums);
        $(".allMoneyNum").text(proPrice);
        $(".havePayMoney").text("￥" + lastHadTopay);
    };

});
//添加新地址相关js
$(function () {
    //打开新地址弹窗
    $(".newAddr ").click(function () {
        document.documentElement.style.overflow='hidden';
        $(".newAddrPop").fadeIn();
        //添加新地址弹窗距离屏幕上边距离
        var windowHeight = $(window).height();
        var topMargin = (windowHeight - $(".newAddrWrap").height()) / 2;
        $(".newAddrWrap").css("top", topMargin);
    });
    //关闭新地址弹窗
    $(".resetBtn,.closeImg ").click(function () {
        $(".newAddrPop").fadeOut();
    });
    //  添加新地址表单验证
    $("#newAddrForm").validate({
        rules: {
            receivePeople: {
                required: true
            },
            area: {
                required: true,
                areaSet: "areaSet"
            },
            detailAddr: {
                required: true
            },
            tellNum: {
                required: true,
                mobile: true
            }
        }, messages: {
            receivePeople: {
                required: "此项必填"
            },
            area: {
                required: "此项必填"
            },
            detailAddr: {
                required: "此项必填"
            },
            tellNum: {
                required: "此项必填",
                mobile: "请输入电话号码"
            }
        },
        highlight: function (element, errorClass) {
            $(element).siblings(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
            $(element).parents("td").append("<span class='correct'>可使用</span>");
        }
    });
    $.validator.addMethod("areaSet", function (value, element) {
        return !(value == 0 || value == "");
    }, "请选择地址");
});
//更多地址弹窗
$(function () {
    //更多地址选中样式
    $(" .choiceAddrWrap .addrContent").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
    });
    //打开更多地址
    $(".moreAddr  ").click(function () {
        $(".moreAddrWrap").fadeIn();
        $(window).scrollTop(0);
    });
    //关闭更多地址 弹窗
    $(".closeImg,.choiceAddrWrap .sure").click(function () {
        $(".moreAddrWrap").fadeOut();
    });
});
//发票相关弹窗
//$(function () {
//    $(".editIverice").click(function () {
//        $(".ivericePop").fadeIn();
//        $(window).scrollTop(0);
//        //获取发票信息
//        $(".save.btn").click(function () {
//            $("#ivericeForm").submit();
//            $(".invoiceMessage .noInvoice").remove();
//            var invoiceTitle = $(".ivericeType a.on").text();
//            var invoicename = $(".personIverice ").val();
//            var invoiceDetail = $(".ivericTable .ivericeContents a.on").text();
//            var companyName = $(".companyName").val();
//            if (invoiceTitle == '普通发票') {
//                $(".invoiceMessage").children().remove();
//                $(".invoiceMessage").append('<span>' + invoiceTitle + '</span><span>' + invoicename + '</span><span>' + invoiceDetail + '</span>');
//            } else {
//                $(".invoiceMessage").children().remove();
//                $(".invoiceMessage").append('<span>' + invoiceTitle + '</span><span>' + companyName + '</span>');
//            }
//        });
//    });
//    //关闭发票弹窗
//    $(".closeImg ,.resetBtn").click(function () {
//        $(".ivericePop").fadeOut();
//    });
//});


