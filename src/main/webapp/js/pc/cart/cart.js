/**
 * Created by Administrator on 2016/5/8.
 */
/*列表操作js*/
$(function () {
    //表格奇数行添加背景颜色
    $(" .productTable  tr:even").css("background-color", "#fafafa");
    //判断当前是否是全选状态
    var notCheckList = $(".productList input[name='proListCheckbox']").not("input:checked");
    if (notCheckList.length < 1) {
        $(".checkAllList").attr("checked", "checked");
    }

    currCheckAll();
        //判断当前是否是全选状态
    function currCheckAll(){
        var checkList = $(".productList input[name='proListCheckbox']");
        if (checkList.length > 0 && checkList.not("input:checked").length < 1) {
            $(".checkAllList").attr("checked", "checked");
        }else {
            $(".checkAllList").removeAttr("checked");
        }
    }
    //全选/取消全选
    bindSelectEvent();
    //绑定商品数量改变事件
    bindGoodsNumEvent();


    /*绑定全选事件*/
    function bindSelectEvent() {
        $(".checkAllList").off('click').on('click', function () {
            var checked = $(this).attr("checked");
            if (checked == "checked") {
                $(".productList input[type='checkbox']").attr("checked", "checked");
            } else {
                $(".productList input[type='checkbox']").removeAttr("checked");
            }
            selected(this);//选中商品后花心页面
        });
        $(".productList input[name='proListCheckbox']").on('click', function () {
            selected(this);//选中商品后花心页面
        });
    }

    /*解除选择商品事件*/
    function unBindSelectEvent() {
        $(".checkAllList").off('click');
        $(".productList input[name='proListCheckbox']").off("click");
    }

    /*选中商品后请求后台的方法*/
    function selected(obj) {
        unBindSelectEvent;
        //loading层
        var index = layer.load(2, {
            shade: [0.5, '#fff'], //0.1透明度的白色背景
            scrollbar: false
        });
        var checkedList = $(".productList input[name='proListCheckbox']:checked");
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
                }
            },
            error: function () {
            }
        });
    }

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

    /*解除商品数量操作时间绑定*/
    function unBindEvent() {
        $(".numUp,.numDown").off('click');
    }

    //商品数量 +1 、-1操作
    //op: 1->加 2->减 3->直接修改数值
    function changeBuyCount(op, obj) {
        unBindEvent();//解除事件
        var id = $(obj).closest("tr").find("input[name=id]").val();
        var addressId = $("#addressId").val();
        var buyCount = 0;
        if (op == 3) {
            buyCount = $(obj).val();
            if (!buyCount || isNaN(buyCount) || buyCount < 1) {
                $(obj).val($(obj).siblings("input:hidden[name=oldCount]").val());
                layer.msg("商品数量超限", {icon: 2});
                bindGoodsNumEvent();
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
                    resetProList(data);
                } else {
                    if (op == 3) {
                        var oldVal = $(obj).siblings("input:hidden").val();
                        $(obj).siblings("input:hidden").siblings("input.numberImport").val(oldVal)
                    }
                    bindGoodsNumEvent();
                    layer.msg(message.message, {icon: 2});
                }
            },
            error: function () {
                bindGoodsNumEvent();
                layer.close(index);
                layer.msg("服务器忙,请稍后再试", {icon: 2});
            }
        });
    }

    //重新绑定页面数据
    function resetProList(data) {
        var loginFlag = $("#loginFlag").val();
        var container = $(".productTable tbody");
        container.empty();
        $.each(data.cartList, function (i, cart) {
            var selected = "",
                priceHtm = '￥<span class="unitPrice">' + HgUtil.numberFormat(cart.price, 2) + '</span>',
                buyCountHtm = '<input type="hidden" name="oldCount"><a href="javascript:void(0);" class="fl reduce numDown btn">-</a><input type="text" class="numberImport fl " value="' + HgUtil.numberFormat(cart.buyCount, 0) + '"/><a href="javascript:void(0);" class="fl numUp btn">+</a>';
            var canBuy = true;
            var fontStyle = "",
                opterHtm = '<a href="javascript:void(0);" class="handelAction delete">删除</a>';
            if (cart.buyCount > cart.inventory) {
                canBuy = false;
                fontStyle = "color:red;font-weight: bold";
            }
            buyCountHtm += ' <span class="reduce" style="text-align: center;font-size: 12px;color: #ada5aa;'+ fontStyle +'"> 剩余'+cart.inventory+'件</span>';
            if (loginFlag == 'true') {
                if (canBuy) {
                    opterHtm = '<a href="javascript:void(0);" class="handelAction moveToFavorite">移入收藏</a>' + opterHtm;
                }
            }
            if (cart.selected) {
                selected = "checked";
            }
            if (!cart.isLive) {
                priceHtm = '<span style="color:red">该商品已下架</span>';
                buyCountHtm = '<div style="width:100%;padding-left:10px">'+HgUtil.numberFormat(cart.buyCount, 0)+'</div>';
            }
            var htm = '<tr><td><input type="hidden" name="id" value="' + cart.id + '"><input type="checkbox" value="' + cart.id + '" name="proListCheckbox" ' + selected + '></td>' +
                '<td><a href='+ contextPath +'"/detail/' + cart.goodsId + '"><img src="' + contextPath + '/' + cart.goodsImg + '_79x86" width="79" height="86"/></a></td>' +
                '<td><a href='+ contextPath +'"/detail/' + cart.goodsId + '" class="proTitle color666">' + cart.goodsName + '</a>' +
                '<p class="brand color666">' + HgUtil.nullToEmpty(cart.specOne) + '</p>' +
                '<p class="brand color666">' +HgUtil.nullToEmpty(cart.specTwo) + '</p></td>' +
                '<td><span class="onePrice">' + priceHtm + '</span></td>' +
                '<td><div class="numberHabdel"><div class="numAdd clearfix">' + buyCountHtm + '</div><input  type="hidden" name="canBuy" value="' + canBuy + '"> </div></td></td>' +
                '<td><span class="cartPrice">￥<span class="priceNum">' + HgUtil.numberFormat(cart.totalPrice,2) + '</span></span></td>' +
                '<td>' + opterHtm + '</td></tr>';
            container.append(htm);
        });
        //绑定总金额
        $("span.allProNum").text(data.totalBuyCount);
        $("span.lastHadPay").text(HgUtil.numberFormat(data.totalPrice,2));
        //全选/取消全选
        bindSelectEvent();
        //绑定商品数量改变事件
        setTimeout(bindGoodsNumEvent, 300);
        //绑定删除时间
        bindDeleteEvent();
        //绑定移动收藏夹事件
        bindMoveEvent();
        //更新头部的购物车商品数量
        //searchGoodSCart();
        currCheckAll();//是否全选

        searchGoodSCart();//更新购物车数量
    }

    bindDeleteEvent();

    //绑定商品删除时间
    function bindDeleteEvent() {
        //单品删除弹窗
        $(".delete").click(function () {
            var obj = this;
            layer.confirm("是否删除此商品？", {
                btn: ["确定", "取消"]
            }, function () {
                var id = $(obj).closest("tr").find("input[name=id]").val();
                delGoods("id=" + id);
            });
        });

        //批量删除弹窗
        $(".deleteChecked").click(function () {
            layer.confirm("是否删除选中商品？", {
                btn: ["确定", "取消"]
            }, function () {
                var checkedList = $(".productList input[name='proListCheckbox']:checked");
                var ids = "";
                checkedList.each(function (i) {
                    ids += "id=" + $(this).val() + "&";
                });
                delGoods(ids);
            });
        });
    }


    /*删除商品*/
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
                layer.closeAll('dialog');
                if (message.success) {
                    resetProList(message.data);
                    cartAsyn();//跟新侧边栏购物车
                } else {
                    layer.msg("操作失败！", {icon: 2});
                }
            },
            error: function () {
                layer.msg("服务器忙，请稍后再试！", {icon: 2});
            }
        });
    }

    bindMoveEvent();
    //绑定移动收藏夹事件
    function bindMoveEvent() {
        //移入收藏夹
        $(".moveToFavorite").click(function () {
            var obj = this;
            layer.confirm("是否将此商品移入收藏夹？", {
                btn: ["确定", "取消"]
            }, function () {
                var id = $(obj).closest("tr").find("input[name=id]").val();
                $.ajax({
                    url: contextPath + '/cart/moveToFavorite',
                    type: 'post',
                    async: false,
                    data: {id: id},
                    dataType: 'json',
                    success: function (message) {
                        layer.closeAll('dialog');
                        if (message.success) {
                            layer.msg("移入收藏成功！", {icon: 1});
                            resetProList(message.data);
                        } else {
                            layer.msg("操作失败！", {icon: 2});
                        }
                    },
                    error: function () {
                        layer.msg("服务器忙，请稍后再试！", {icon: 2});
                    }
                });
            });
        });
    }


    //结算
    $(".goToPay ").click(function () {
        var loginFlag = $("#loginFlag").val();
        if (loginFlag == 'true') {
            var dataLen = $("table.productTable tbody input[name=proListCheckbox]:checked").length;
            if (dataLen < 1) {
                layer.msg("请选择商品后进行结算", {icon: 2});
                return;
            }
            var canBuy = true;
            $("input[name=canBuy]").each(function (i, obj) {
                if ($(obj).val() == 'false' && $(obj).closest("tr").find("input:checkbox[name=proListCheckbox]:checked").length > 0) {
                    canBuy = false;
                    $(this).siblings(".numberImport").focus();//数量超限的获取焦点
                    return false;
                }
            });
            if (!canBuy) {
                layer.msg("所选商品不符合购买条件", {icon: 2});
                return;
            }
            var checkedList = $(".productList input[name='proListCheckbox']:checked");
            var form = $('<form action="' + contextPath + '/order/addByCart" method="post"></form>');
            checkedList.each(function (i) {
                form.append('<input type="text" name="cartId" value="' + $(this).val() + '">');
            });
            form.append('<input id="subFormBtn" type="submit">');
            $("#subForm").append(form);
            $("#subFormBtn").click();
        } else {
            location.href =contextPath + '/login?redirectURL=' + contextPath + "/cart";
            /*layer.open({
                zIndex: 10,
                title: '登录',
                type: 2,
                skin: 'layer-ext-moon', //加上边框
                area: ['420px', '450px'], //宽高
                shadeClose: false,//点击遮罩是否关闭弹框
                content:
            });*/
        }
    });
});

