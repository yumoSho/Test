$(document).ready(function (){
    //图片索引
    var _index  = 0;
    var imgList = $("#thumbnail li img");
    $("#thumbnail li img").each(function (i,item) {
        $me = $(this);
        $me.click(function () {
            _index = i;
            return;
        })
    });

    $(".zoompic>a>img").load(function(){
        $(".zoompic>a>img:hidden").show();
    });

    //小图片左右滚动
    var $slider = $('.slider ul');
    var $slider_childs = $('.slider ul li').length;
    var $slider_width = $('.slider ul li').width();
    $slider.width($slider_childs * $slider_width);



    if ($slider_childs < 2) {
        $(".proMoreImg .sliderbox .arrow-btn").css("opacity", "0");
    } else {
        $(".proMoreImg .sliderbox .arrow-btn").css("opacity", "1");
    }
    var slider_count = 0;

    $('.btn-next').click(function () {
        if (_index >= $slider_childs - 1) {
            _index = 0;
            $slider.animate({left: '0px'}, 'fast');
            slider_count = 0;
        }
        else
        {
            //每次点击+1
            _index++;
            //索引超过4开始移动
            if(_index > 200 && _index > (slider_count +5))
            {
                slider_count++;
                $slider.animate({left: '-=' + $slider_width + 'px'}, 'fast');
            }
        }

        tag_pic();
        show_big_pic();
    });

    $('.btn-prev').click(function() {
        if (_index <= 0) {
            _index = $slider_childs - 1;
            $slider.animate({left: '-'+($slider_childs - 5)*$slider_width+'px'}, 'fast');
            slider_count = $slider_childs - 5;
        }
        else
        {
            _index--;
            if(_index < slider_count){
                slider_count--;
                $slider.animate({left: '+=' + $slider_width + 'px'}, 'fast');
            }
        }
        tag_pic();
        show_big_pic();
    });

    //详情页
    var $imaWrap = $(".imaWrap ul");
    var $imaWrap_len = $(".imaWrap ul li").length;
    var $imaWrap_width = $(".imaWrap ul li").width();
    $imaWrap.width($imaWrap_len * $imaWrap_width);

    if ($imaWrap_len < 2) {
        $(".imaWrap .arrow-btn").css("opacity", "0");
    } else {
        $(".imaWrap .arrow-btn").css("opacity", "1");
    }
    slider_count = 0;

    $('.next').click(function () {
        console.log(132465);
        if (_index >= $imaWrap_len - 1) {
            _index = 0;
            $imaWrap.animate({ left: '0px' }, 'fast');
            slider_count = 0;
        }
        else {
            //每次点击+1
            _index++;
            //索引超过4开始移动
            if (_index > 200 && _index > (slider_count + 5)) {
                slider_count++;
                $imaWrap.animate({ left: '-=' + $imaWrap_width + 'px' }, 'fast');
            }
        }

        tag_pic();
        show_big_pic();
    });

    $('.prev').click(function () {
        if (_index <= 0) {
            _index = $imaWrap_len - 1;
            $imaWrap.animate({ left: '-' + ($imaWrap_len - 5) * $imaWrap_width + 'px' }, 'fast');
            slider_count = $imaWrap_len - 5;
        }
        else {
            _index--;
            if (_index < slider_count) {
                slider_count--;
                $imaWrap.animate({ left: '+=' + $imaWrap_width + 'px' }, 'fast');
            }
        }
        tag_pic();
        show_big_pic();
    });

    //标记显示的图片
    function tag_pic(){
        $("#thumbnail li.current").removeClass("current");
        $("#thumbnail li").eq(_index).addClass("current");
    }

    function show_big_pic(){
        $(".zoompic a img").hide().attr({ "src": $("#thumbnail li").eq(_index).find(">img").attr("src"), "title": $("#thumbnail li").eq(_index).find(">img").attr("title") });
        $(".zoompic a").attr({ "href": $("#thumbnail li").eq(_index).find(">img").attr("longdesc")});

        //优化显示测试数据
        $(".info .count").html(slider_count);
        $(".info .index").html(_index);
    }
});
