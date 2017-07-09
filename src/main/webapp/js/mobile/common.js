//产品列表的间距去除
//$(".productList .proItem:odd").css("margin-left", "4%");
$(function () {
    //搜索页面
    $(".searchInput").focus(function () {
        window.location.href = contextPath+"/mobile/input/";
    });
    //底部选中效果
    var imgSrc = $(".foot a.on").find("img").data("src");
    $(".foot a.on").find("img").attr("src", imgSrc);
    //表单验证
    setInterval(function () {
        $(".errorLabel").hide();
    }, 3000);
    //地址选填
    $(".head .headTop .menuBtn").click(function () {
        $(".addrIframe").show();
        $(".wrap").hide();
    });


    $(".addrList a").click(function () {
        var thisText = $(this).text();
        var thisId= $(this).data("id")
        /*$(".headBar .headAddr span").text(thisText);
         $(".headBar .headAddr span").data("id",thisId);*/
        var p =parent.location.href;
        var index = p.indexOf("?")
        if(index < 0){
            var a = replaceParamVal("provinceId","");
            parent.location.href=a+"?provinceId="+thisId;
        }else{
            var a = replaceParamVal("provinceId","");
            if(a.lastIndexOf("&") == (a.length -1)){
                parent.location.href=a+"provinceId="+thisId;
            }else{
                parent.location.href=a+"&provinceId="+thisId;
            }

        }
    });
    function replaceParamVal(paramName,replaceWith) {
        var oUrl = parent.location.href.toString();
        var re=eval('/('+ paramName+'=)([^&]*)/gi');
        var nUrl = oUrl.replace(re,replaceWith);
        return  nUrl;
    }

    $(".gotoTop").click(function () {
        $("html,body").animate({ "scrollTop": '0' }, 500);
    });
});
function slide(name, width, height) {
    if ($(name).children().length > 1) {
        $(name).slidesjs({
            width: width,
            height: height,
            navigation: false,
            play: {
                auto: false,
                restartDelay: 2500
            },
            callback: {
                complete: function (number) {
                    clearTimeout(slideTimer)
                    var slideTimer = setTimeout(function () {
                        $(".slidesjs-play").click();
                    }, 4000)
                }
            }
        });
        //导航点居中
        var pagination = $(name).find(".slidesjs-pagination li").size() * 18;
        $(name).find(".slidesjs-pagination").css("margin-left", "-" + pagination / 200 + "rem");
    }
}
function slide1(name, width, height) {
    if ($(name).children().length > 1) {
        $(name).slidesjs({
            width: width,
            height: height,
            pagination: false,
            play: {
                auto: false,
                restartDelay: 2500
            },
            callback: {
                complete: function (number) {
                    clearTimeout(slideTimer)
                    var slideTimer = setTimeout(function () {
                        $(".slidesjs-play").click();
                    }, 4000)
                }
            }
        });
    }
}
//瀑布流代码
$(window).scroll(function () {
    //回到顶部
    var height = $(window).scrollTop();
    var goTop = $(".gotoTop");
    if (height > 200) {
        goTop.fadeIn(500);
    } else {
        goTop.fadeOut(500);
    }
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

