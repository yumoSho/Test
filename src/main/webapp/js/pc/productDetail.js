$(".jqzoom").imagezoom();
$("#thumblist li a").click(function () {
    //增加点击的li的class:tb-selected，去掉其他的tb-selecte
    $(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
    //赋值属性
    $(".jqzoom").attr('src', $(this).find("img").attr("mid"));
    $(".jqzoom").attr('rel', $(this).find("img").attr("big"));
});

$(".next").click(function () {
    $("li.tb-selected").removeClass("tb-selected").next().addClass("tb-selected");
    if ($("li.tb-selected").index() == -1) {
        $(".tb-thumb").find("li:first-child").addClass("tb-selected");
    };
    $(".jqzoom").attr("src", $("li.tb-selected").find("img").attr("big"));
});

$(".prev").click(function () {
    $("li.tb-selected").removeClass("tb-selected").prev().addClass("tb-selected");
    if ($("li.tb-selected").index() == -1) {
        $(".tb-thumb").find("li:last-child").addClass("tb-selected");
    };
    $(".jqzoom").attr("src", $("li.tb-selected").find("img").attr("big"));
});
//小图个数
var liNumbers = $(".tb-thumb li").size();
//if (liNumbers > 4) {
//    $(".next").show();
//    $(".prev").show();
//    $(".next").click(function () {
//        $(".tb-thumb li:first-child").css("margin-left", "-80px")
//    });
//    $(".prev").click(function () {
//        $(".tb-thumb li:first-child").css("margin-left", "0")
//    });
//}
$('.proDes a').click(function () {
    var i = $('.proDes a').index(this);
    $('.proDes a').eq(i).addClass('on').siblings().removeClass('on');
    $('.tabContent .forTab').eq(i).show().siblings().hide();
});
//收藏效果

$(".sharePart .proCollect").click(function () {
    /*collectNum += 1;
    if (collectNum % 2 !== 0) {
        $(this).addClass("on");
        $(this).text("已收藏");
        layer.msg("收藏成功！");
    } else {
        $(this).removeClass("on");
        $(this).text("收藏");
        layer.msg("取消收藏成功！");
    }*/
    $this= $(this);
    var goodsId= $this.data("id");
    if($this.hasClass("on")){
        $.ajax({
            type: "POST",
            url: contextPath+'/person-center/remove-collect',
            data: "goodsid="+goodsId+"&goodsFrom="+goodsFrom+"&otherId="+oid,
        }).done(function (data) {
            if(data.success){
                $this.removeClass("on");
                $this.text("收藏");
                layer.msg('取消收藏成功',{ time: 1500}, function(){});
            }else{
                if(!data.message){
                    window.location.href=contextPath+"/login?redirectURL="+location.href;
                }else{
                    layer.msg('取消收藏失败',{ time: 1500}, function(){});
                }
            }
        }).fail(function (data) {
            layer.msg('取消收藏失败',{ time: 1500}, function(){});
        })
    }else{
        $.ajax({
            type: "POST",
            url: contextPath+'/person-center/add-collect',
            data: "goodsid="+goodsId+"&goodsFrom="+goodsFrom+"&otherId="+oid,
        }).done(function (data) {
            if(data.success){
                $this.addClass("on");
                $this.text("已收藏");
                layer.msg('收藏成功',{ time: 1500}, function(){});
            }else{
                if(!data.message){
                    window.location.href=contextPath+"/login?redirectURL="+location.href;
                }else{
                    layer.msg('收藏失败',{ time: 1500}, function(){});
                }
            }
        }).fail(function (data) {
            layer.msg('收藏失败',{ time: 1500}, function(){});
        })
    }
});
/*//加入购物车效果
$(".proForCart a:first-child").click(function () {
    layer.msg("加入购物车成功！");
});*/
//搭配部分js效果
$(".centerProWrap .proInner").each(function () {
    var itemNum = $(this).find(".centerProItem").size();
    $(this).css("width", itemNum * 198 + "px");
});
//搭配切换
new GlanwayTab(".centerTab .tabBtn a", ".tabContent .forTabContent ");
$(".content2 .salePartTab a,.proMsgTable .colorChuice a,.rightMsgTab .bottomRightTab a").click(function () {
    $(this).addClass("on").siblings().removeClass("on");
});
//产品信息切换
new GlanwayTab(".rightMsgTab .bottomRightTab a", ".proTabMain .forTab");