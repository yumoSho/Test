var GlanwayTab = function (titleItems, contentItems) {
    var $ti = $(titleItems);
    var $ci = $(contentItems);
    var index = 0;
    var prev = $(".prev"),
        next = $(".next");
    var len = $ti.size();
    //初始化按钮索引
    prev.data("index", len - 1);
    next.data("index", 0);
    $ti.each(function (i) {
        $(this).click(function () {
            index = i;
            prev.data("index", (i - 1) < 0 ? i - 1 : len - 1);
            next.data("index", (i + 1) < len ? i + 1 : 0);
            $ci.eq(i).show().siblings().hide();
            return false;
        });
    });
   $ (".otherBtn").click(function () {
        var i = $(this).data("index");
        $ti.eq(i).addClass("on").show().parents().addClass("on").siblings().removeClass("on").find("a").removeClass("on");
        $ci.eq(i).show().siblings().hide();
        prev.data("index", (i - 1) < 0 ? len - 1 : i - 1);
        next.data("index", (i + 1) < len ? i + 1 : 0);
   });
   $ti.eq(index).click();
}
$