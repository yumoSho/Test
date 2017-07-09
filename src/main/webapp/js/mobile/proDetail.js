$(function () {
    //分享弹窗
    $(".detailHandel .handelList .share").click(function () {
        $(".detailShare").show();
        $(".handelList").hide();
        $(".wrap").css("height", $(window).height()).addClass("show");
        document.body.style.overflow = "hidden";//禁用
    });
    //分享弹窗关闭
    $(".detailShare .shareInner .forgiveShare").click(function () {
        $(".detailShare").hide();
        $(".wrap").css("height", $(window).height()).addClass("show");
        document.body.style.overflow = "auto";
    });
    //任意点击区域，分享弹窗隐藏
    $(".detailShare").click(function (e) {
        if (!$(e.target).is(".shareInner") && !$(e.target).parents().is(".shareInner")) {
            $(".detailShare").hide();
            $(".wrap").css("height", "auto").removeClass("show");
            document.body.style.overflow = "auto";
        }
    });
    //选择规格弹窗显示
    var $forCart= null;
    $(".prohandel .forCart, .prohandel .buyNow").click(function () {
        $(".chuiceLabel").show();
        if($(this).hasClass("forCart")){
            $forCart = $(this);
        }
        $(".wrap").css("height", $(window).height()).addClass("show");
        document.body.style.overflow = "hidden";//禁用
    });
    //选择规格弹窗隐藏
    $(".chuiceLabel .chuiceInner .makeSureBtn, .chuiceProDes .closeImg").click(function () {
        $(".chuiceLabel").hide();
        $(".wrap").css("height", "auto").removeClass("show");
        document.body.style.overflow = "auto";//启用

        if($(this).hasClass("makeSureBtn") && $forCart){
            var gId = $forCart.attr("g-id");
            var flag=member;
            var val = $(".numInput").val();
            var url= "";
            if(flag){
                url =contextPath+"/cart/saveAsync?goodsId="+gId+"&buyCount="+val+"&goodsFrom="+goodsFrom+"&otherId="+oid;
            }else{
                url=contextPath+"/cookieCart/saveAsync?goodsId="+gId+"&buyCount="+val+"&goodsFrom="+goodsFrom+"&otherId="+oid;
            }
            $.ajax({
                url: url,
                type: "POST",
            }).done(function (data) {
                if(data.success){
                    layer.open({
                        content: '加入购物车成功',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    })
                }else if(!data.message){
                    window.location.href=contextPath+"/login?redirectURL="+location.href;
                }else if(data.message){
                    layer.open({
                        content: '加入购物车失败',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    })
                }
            }).fail(function (data) {
                layer.open({
                    content: '加入购物车失败',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                })
            });
        }else if($(this).hasClass("makeSureBtn")){
            var val = $(".numInput").val();
            var gId = $("#buyNow").attr("g-id");
            location.href=contextPath+"/order/add?goodsId="+gId+"&count="+val+"&goodsFrom="+goodsFrom+"&otherId="+oid;
        }
        $forCart = null;
    });


    //任意点击区域，分享弹窗隐藏
    $(".chuiceLabel").click(function (e) {
        if (!$(e.target).is(".chuiceInner") && !$(e.target).parents().is(".chuiceInner")) {
            $(".chuiceLabel").hide();
            /*$(".labelWrap .labels .labeBtns a").removeClass("on");*/
            $(".wrap").css("height", "auto").removeClass("show");
            document.body.style.overflow = "auto";
        }
    });
    new GlanwayTab(".detailBtns a", ".tabContent .forTab");
    new GlanwayTab(".combo a", ".recommendContentBox .recommendContent");
    /*//商品评价瀑布流代码
    $(window).scroll(function () {
        var ok = true;
        if (ok) {
            //获取加载的数据
            var moreLi = baidu.template("moreList");
            //获取到屏幕顶部距离
            var loadheight = $(".load").offset().top - $(window).scrollTop() + $(".load").height();
            //获取屏幕高度
            var screenheight = $(window).height();
            if (loadheight < screenheight) {
                for (var i = 1; i < 4; i++) {
                    $(".pinterest").append(moreLi);
                }
            }
        }
        var yes = true;
        if (yes) {
            //获取加载的数据
            var moreLi = baidu.template("askList");
            //获取到屏幕顶部距离
            var loadheight = $(".load").offset().top - $(window).scrollTop() + $(".load").height();
            //获取屏幕高度
            var screenheight = $(window).height();
            if (loadheight < screenheight) {
                for (var i = 1; i < 4; i++) {
                    $(".askWrap").append(moreLi);
                }
            }
        }
    });*/

    $.ajax({
        type: "POST",
        url: contextPath+'/person-center/check-collected',
        data: "goodsid="+goodsId+"&goodsFrom="+goodsFrom+"&otherId="+oid,
    }).done(function (data) {
        if(data.success){
            $(".prohandel .forCollect").addClass("on");
            $(".prohandel .forCollect").find("img").attr("src", contextPath+"/images/mobile/icon24a.png");
        }
    }).fail(function (data) {
        layer.open({
            content: '获取收藏失败',
            time: 2,
            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
        })
    })


    //收藏功能
    $(".prohandel .forCollect").click(function () {
        $this= $(this);
        if($this.hasClass("on")){
            $.ajax({
                type: "POST",
                url: contextPath+'/person-center/remove-collect',
                data: "goodsid="+goodsId+"&goodsFrom="+goodsFrom+"&otherId="+oid,
            }).done(function (data) {
                if(data.success){
                    $this.removeClass("on");
                    $this.find("img").attr("src", contextPath+"/images/mobile/icon24.png");
                    layer.open({
                        content: '取消收藏成功',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                    })
                }else{
                    if(!data.message){
                        window.location.href=contextPath+"/login?redirectURL="+location.href;
                    }else{
                        layer.open({
                            content: '取消收藏失败',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                        })
                    }
                }
            }).fail(function (data) {
                layer.open({
                    content: '取消收藏成功',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                })
            })
        }else{
            $.ajax({
                type: "POST",
                url: contextPath+'/person-center/add-collect',
                data: "goodsid="+goodsId+"&goodsFrom="+goodsFrom+"&otherId="+oid,
            }).done(function (data) {
                if(data.success){
                    $this.addClass("on");
                    $this.find("img").attr("src", contextPath+"/images/mobile/icon24a.png");
                    layer.open({
                        content: '收藏成功',
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                    })
                }else{
                    if(!data.message){
                        window.location.href=contextPath+"/login?redirectURL="+location.href;
                    }else{
                        layer.open({
                            content: '加入收藏失败',
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                        })
                    }
                }
            }).fail(function (data) {
                layer.open({
                    content: '加入收藏失败',
                    time: 2,
                    style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei"
                })
            })
        }
    });
    $(".collect").click(function () {
        $('.prohandel .forCollect').trigger("click");
    });


    //购买数量增减
    var addBtn = $(".numAdd");
    var minusBtn = $(".numRelize");
    var input = $(".numInput");
    addBtn.click(function () {
        var val = parseInt(input.val());
        var addVal = val + 1;
        var stock = parseInt(input.attr("stock"));
        if(addVal>stock){
            input.val(stock.toString());
        }else{
            input.val(addVal.toString());
        }
    });
    minusBtn.click(function () {
        var val = parseInt(input.val());
        var addVal = val - 1;
        if (addVal >= 1) {
            input.val(addVal.toString());
        }
        else {
            input.val("1")
        }
    });
    //购物车输入框校验
    $(".numInput").blur(function () {
        var val = $.trim($(this).val());
        if (!val.isPositiveNum()) {
            $(this).val(1);
        }
        var val = parseInt(val);
        var stock = parseInt($(this).attr("stock"))
        if(val>stock){
            $(this).val(stock);
        }
    });
    String.prototype.isPositiveNum = function () {
        return (/^[0-9]*[1-9][0-9]*$/.test(this));
    }
});