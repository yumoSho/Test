$(function(){
    var link = document.createElement('link');link.rel='shortcut icon';link.type='image/x-icon';link.href=contextPath + '/images/favicon.ico';link.media='screen';document.head.appendChild(link);
});
category =null;
function getCategory() {
    $.ajax({
        url:  window.contextPath+'/category',
        type: 'post',
        cache:true
    }).done(function (data) {
        category = data;
        var $subNav = $(".subNav");
        if(category){
            for(var i=0;i<category.length;i++){
                $("<li></li>").append($("<a></a>").attr("href",window.contextPath+"/search?&cat="+category[i].id).text(category[i].name)).appendTo($subNav);
            }
        }
        $(".navBar .subNav>li").hover(function () {
            $this = $(this);
            var index = $this.index();
            var $thirdNav = $("<div></div>").addClass("thirdNav");

            var $thirdBrand = $("<ul></ul>").addClass("thirdNavUl").attr("id","thirdNavUl");
            var secodeChild = category[index].children;

            for(var i=0;i<secodeChild.length;i++){
                var $sKind = $("<li></li>").addClass("")
                var thirdChild = secodeChild[i].children

                $sKind.append($("<a></a>").attr("href",window.contextPath+"/search?&cat="+secodeChild[i].id).html(secodeChild[i].name+(thirdChild.length>0?"<i></i>":"")));

                var $fivethNav = $("<div></div>").addClass("fourthNav");


                for(var j=0;j<thirdChild.length;j++){
                    if(thirdChild[j]){
                        $("<a></a>").attr("href",window.contextPath+"/search?&cat="+thirdChild[j].id).text(thirdChild[j].name).appendTo($fivethNav);
                    }
                }
                $fivethNav.appendTo($sKind);
                $sKind.appendTo($thirdBrand);
            }
            if(secodeChild && secodeChild.length==0 ){
                $thirdBrand.html('<div class="hasNoType">暂无分类</div>');
            }
            $thirdBrand.appendTo($thirdNav);
            $thirdNav.appendTo($this);
            /*}*/
            //三级导航高度
            var subNavNum = $(".navBar .allProType .subNav > li").size();
            $(".navBar .allProType .thirdNav").css("min-height", subNavNum * 50 - 31);
            $(this).children("div").show();
        }, function () {
            $(this).children("div").remove();
        });
    })
}
$(function () {
    getCategory();


    //导航显示
    $("#categoryNav").hover(function () {
        $(".subNav", $(this)).show();

    }, function () {
     $(".subNav", $(this)).hide();
    });



    //返回顶部
    $(".goToTop").click(function () {
        $("html,body").animate({ "scrollTop": '0' }, 500);
    });

    //头部搜索js
    $(".searchSelect").click(function () {
        var thisList = $(".selectUl").css("display");
        if (thisList == "none") {
            $(this).find(".selectUl").show();
        } else {
            $(this).find(".selectUl").hide();
        }
        return false;
    });
    $(".searchSelect .selectUl a").click(function () {
        var thisText = $(this).text();
        var type = $(this).data("type");

        var changeText = $(this).parents(".selectUl").siblings().text();
        var changType = $(this).parents(".selectUl").siblings().data("type");

        $(".searchSelect .selectDefault").text(thisText);
        $(".searchSelect .selectDefault").data("type",type);
        $(this).text(changeText);
        $(this).data("type",changType);
    });

    $(".headSearchPart .searchBtn").click(function () {
        var kw = $(".searchInput").val();
        var type = $(".selectDefault").data("type");
        if(type=="0"){
            window.location.href =  contextPath+"/search?kw=" + kw;
        }else{
            window.location.href =  contextPath+"/store/search?name=" + kw;
        }
    });
    $(".searchInput ").keydown(function (event) {
        if (event.keyCode == 13) {
            var kw = $(".searchInput").val();
            var type = $(".selectDefault").data("type");
            if(type=="0"){
                window.location.href = contextPath+"/search?kw=" + kw;
            }else{
                window.location.href = contextPath+"/store/search?name=" + kw;
            }
        }
    });

});

//js工具类
(function($,window){
    function HgUtil(){
    }
    HgUtil.prototype = {
        numberFormat:function(number,len){
            return isNaN(number) ? 0 : Number(number).toFixed(len);
        }
    }
    window.HgUtil = new HgUtil();
})(jQuery,window);