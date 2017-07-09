var GlanwayTab = function (titleItems, contentItems) {
    var $ti = $(titleItems);
    var $ci = $(contentItems);
    var index = 0;
    $ti.each(function (i) {
        $(this).click(function () {
            $(this).addClass("on").siblings().removeClass("on");
            index = i;
            $ci.eq(i).show().siblings().hide();
            return false;
        });
    });
    this.next = function () {
        if ((index + 1) == $ti.length) {
            return;
        }
        index++;
        $ti.eq(index).click();
    }
    $ti.eq(index).click();
}