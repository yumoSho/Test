$(function(){
    //默认购物车全选状态    20161104
    $(".cartList input[type='checkbox']").attr("checked", "checked");

    //判断是否全选
    isCheckedAll();

    searchGoodSCart();//拉取头部数量

    //全选和反选
    $(".checkAll").click(function () {
        var checked = $(this).attr("checked");
        if (checked == "checked") {
            $(".cartList input[type='checkbox']").attr("checked", "checked");
        } else {
            $(".cartList input[type='checkbox']").removeAttr("checked");
        }
        selected();
    });
    //d单选
    $(".cartList input[name='proListCheckbox']").click(function () {
        selected();
    });


    /*选中商品后请求后台的方法*/
    function selected() {
        //loading层
        var index =layer.open({type: 2})
        var checkedList = $(".cartList input[type='checkbox']:checked");
        var ids = "";
        checkedList.each(function (i) {
            ids += "id=" + $(this).val() + "&";
        });
        var loginFlag = $("#loginFlag").val();
        var URI = '/cart/selectGoods';
        if (loginFlag == 'false') {
            URI = '/cookieCart/selectGoods';
        }
        $.ajax({
            url: contextPath + URI,
            type: 'post',
            async: false,
            data: ids,
            dataType: 'json',
            success: function (message) {
                if (message.success) {
                    layer.close(index);
                    resetProList(message.data);
                    isCheckedAll();
                }
            },
            error: function () {
            }
        });
    }
    /*商品数量操作*/
    bindGoodsNumEvent();

    //删除选中商品事件
    $(".cartEdit .deleteChecked").click(function (e) {
        var checkedNum = $(".cartList .itemTop input:checked");
        if (checkedNum.size() < 1) {
            layer.open({
                content: '请选择一个商品',
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            });
        } else {
            layer.open({
                content: '确定删除选中的产品吗？',
                btn: ['确定', '取消'],
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                yes: function () {
                    var ids = "";
                    checkedNum.each(function(){
                        ids += "id=" + $(this).val()+"&";
                    });

                    delGoods(ids);
                    $(".cartItem .itemTop input:checked").parents(".cartItem").remove();
                    $(".checkAll").removeAttr("checked");
                },
                no: function(){

                }
            });
        }
    });

    //购物车移入收藏
    $(".cartEdit .forCollect").click(function () {
        var loginFlag = $("#loginFlag").val();
        if (loginFlag == 'false') {
            location.href = contextPath + '/login?redirectURL=' + contextPath + "/cart";
            return false;
        }
        var checkedNum = $(".cartList .itemTop input:checked");
        if (checkedNum.size() < 1) {
            layer.open({
                content: '请选择一个产品',
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            })
        } else {
            var ids = "";
            checkedNum.each(function(){
                ids += "id=" + $(this).val()+"&";
            });
            $.ajax({
                url: contextPath + '/cart/moveToFavorite',
                type: 'post',
                async: false,
                data: ids,
                dataType: 'json',
                success: function (message) {
                    if (message.success) {
                        resetProList(message.data);
                        $(".cartItem .itemTop input:checked").parents(".cartItem").remove();
                        layer.open({
                            content: '移入收藏夹成功',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        })
                    }
                },
                error: function () {
                    layer.open({
                        content: '服务器忙',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    })
                }
            });

        }
    });


    //结算
    $(".goForPay ").click(function () {
        var loginFlag = $("#loginFlag").val();
        var checkedGoods =  $(".cartList .itemTop input:checked");
        if (loginFlag == 'true') {
            var dataLen = checkedGoods.length;
            if (dataLen < 1) {
                layer.open({
                    content: '请选择商品',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
                return;
            }
            var canBuy = true;
            $("input[name=canBuy]").each(function (i, obj) {
                if ($(obj).val() == 'false' && $(obj).closest(".cartItem").find("input:checkbox[name=proListCheckbox]:checked").length > 0) {
                    canBuy = false;
                    $(obj).siblings(".numberImport").focus();//数量超限的获取焦点
                    return false;
                }
            });
            if (!canBuy) {
                layer.open({
                    content: '有商品不能购买',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
                return;
            }
            var form = $('<form action="' + contextPath + '/order/addByCart" method="post"></form>');
            checkedGoods.each(function (i) {
                form.append('<input type="text" name="cartId" value="' + $(this).val() + '">');
            });
            form.submit();
        }else{
            location.href = contextPath + '/login?redirectURL=' + contextPath + "/cart";
        }
    });
});

/*绑定商品数量操作js*/
function bindGoodsNumEvent() {
    //保留修改前的值
    $("input.numberImport").focus(function () {
        $(this).siblings("input:hidden[name=oldCount]").val(this.value);
    });
    //修改购买数量事件绑定
    $("input.numberImport").change(function () {
        changeBuyCount(3, this);
    });
    //增加购买数量事件
    $(".numUp").off('click').on('click', function () {
        changeBuyCount(1, this);
    });
    //减少购买数量事件
    $(".numDown").off('click').on('click', function () {
        changeBuyCount(2, this);
    });
}



/*判断当前状态是否为全选*/
function isCheckedAll(){
    //判断当前是否是全选状态
    var CheckList = $(".cartList input[type='checkbox']");
    if (CheckList.length > 0 && CheckList.not("input:checked").length < 1) {
        $(".checkAll").attr("checked", "checked");
    }else{
        $(".checkAll").removeAttr("checked");
    }
}
/*解除商品数量操作时间绑定*/
function unBindEvent() {
    $(".numUp,.numDown").off('click');
}

//商品数量 +1 、-1操作
//op: 1->加 2->减 3->直接修改数值
function changeBuyCount(op, obj) {
    unBindEvent();//解除事件
    var id = $(obj).siblings("input[name=id]").val();
    var buyCount = 0;
    if (op == 3) {
        buyCount = $(obj).val();
        if (!buyCount || isNaN(buyCount) || buyCount < 1) {
            $(obj).val($(obj).siblings("input:hidden[name=oldCount]").val());
            layer.open({
                content: "商品数量超限",
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            });
            bindGoodsNumEvent();
            return false;
        }
    } else {
        buyCount = Number($(obj).siblings("input.numberImport").val());
        if(op == 1){
            buyCount += 1;
        }else{
            buyCount -= 1;
        }
    }
    //loading层
    var index =layer.open({type: 2})
    var loginFlag = $("#loginFlag").val();
    var URI = '/cart/changeBuyCount';
    if (loginFlag == 'false') {
        URI = '/cookieCart/changeBuyCount';
    }
    $.ajax({
        url: contextPath + URI,
        type: 'post',
        async: false,
        data: {id: id, type: op, buyCount: buyCount},
        dataType: 'json',
        success: function (message) {
            layer.close(index);
            if (message.success) {
                var data = message.data;
                if(op != 3){
                    $(obj).siblings("input.numberImport").val(buyCount);
                }
                resetProList(data);
            } else {
                if (op == 3) {
                    var oldVal = $(obj).siblings("input:hidden").val();
                    $(obj).siblings("input:hidden").siblings("input.numberImport").val(oldVal)
                }
                bindGoodsNumEvent();
                layer.open({
                    content: message.message,
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
            }
        },
        error: function () {
            bindGoodsNumEvent();
            layer.close(index);
            layer.msg("服务器繁忙", {icon: 2});
        }
    });
}


//重新绑定页面数据
function resetProList(data) {
    $("span.lastHadPay").text(Number(data.totalPrice).toFixed(2));
    setTimeout(bindGoodsNumEvent, 300);
    searchGoodSCart();//更新头部商品数量
}





//删除购物车商品
function delGoods(ids) {
    var loginFlag = $("#loginFlag").val();
    var URI = '/cart/delete';
    if (loginFlag == 'false') {
        URI = '/cookieCart/delete';
    }
    $.ajax({
        url: contextPath + URI,
        type: 'post',
        async: false,
        data: ids,
        dataType: 'json',
        success: function (message) {
            if (message.success) {
                layer.open({content: '删除成功！', time: 1, style: 'font-family:Microsoft YaHei'});
                resetProList(message.data);
                isCheckedAll();
            } else {
                layer.open({
                    content: '操作失败！',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                });
            }
        },
        error: function () {
            layer.open({
                content: '服务器忙！',
                time: 2,
                style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
            });
        }
    });
}
