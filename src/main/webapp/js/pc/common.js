$(function(){
    var link = document.createElement('link');
    link.rel='shortcut icon';
    link.type='image/x-icon';
    link.href=contextPath + '/images/favicon.ico';
    link.media='screen';
    document.getElementsByTagName('head')[0].appendChild(link);
    //document.head.appendChild(link);
});

category =null;
function getCategory() {
    $.ajax({
        url:  window.contextPath+'/category',
        type: 'post',
        cache:true
    }).done(function (data) {
        category = data;
        var $subNav = $(".slideNav");
        //一级分类
        if(category){
            for(var i=0;i<category.length;i++){
                $("<li></li>").append($("<a></a>").attr("style","background:url("+contextPath+"/"+category[i].picture+") 25% 7px no-repeat;").attr("href",window.contextPath+"/search?&cat="+category[i].id).text(category[i].name)).appendTo($subNav);
            }
        }
        //二级分类
        $(".slideNav li>a").hover(function () {
            $this = $(this);
            var index = $(this).parent().index()
            var $thirdNav = $("<ul></ul>").addClass("righNav");
            //var $li = $("<li></li>");
            //var $thirdBrand = $("<ul></ul>").addClass("thirdNavUl").attr("id","thirdNavUl");
            var secodeChild = category[index].children;

            for(var i=0;i<secodeChild.length;i++){

                var $sKind = $("<li></li>").addClass("")
                var thirdChild = secodeChild[i].children;

                $sKind.append($("<a></a>").attr("href",window.contextPath+"/search?&cat="+secodeChild[i].id).html(secodeChild[i].name+(thirdChild.length>0?"<i></i>":"")));

                var $fivethNav = $("<div></div>").addClass("navLinks");


                for(var j=0;j<thirdChild.length;j++){
                    if(thirdChild[j]){
                        $("<a></a>").attr("href",window.contextPath+"/search?&cat="+thirdChild[j].id).text(thirdChild[j].name).appendTo($fivethNav);
                    }
                }
                $fivethNav.appendTo($sKind);
                $sKind.appendTo($thirdNav);
            }
            if(secodeChild && secodeChild.length==0 ){
                $thirdNav.html('<div class="hasNoType" style="text-align: center">暂无分类</div>');
            }
            //$thirdNav.appendTo($thirdNav);
            $thirdNav.appendTo($this);
            /*}*/
            //三级导航高度
            $thirdNav.css("min-height", $subNav.height()-30-2);
            $(this).children().show();
        }, function () {
            $(this).children().remove();
        });
    })
}

$(function () {
    //分类
    getCategory();
    var provinceId = $(".addrChecked").data("id");
    if(provinceId){
        $.each($(".headAddrList a"),function(i,e){
            var pid= $(this).data("id")
            if(pid== provinceId){
                $(".addrChecked").text($(this).text())
            }
        });
    }

    //头部地址js
    $(".headAddr").hover(function () {
        $(this).find(".headAddrList ").show();
    }, function () {
        $(this).find(".headAddrList ").hide();
    });
    $(".addrItem a").click(function () {
        $(this).parents(".headAddrList").hide();
        var thisText = $(this).text();
        var thisId= $(this).data("id")
        $(".headBar .headAddr span").text(thisText);
        $(".headBar .headAddr span").data("id",thisId);
        var p =location.href;
        var index = p.indexOf("?")
        if(index < 0){
            var a = replaceParamVal("provinceId","");
            window.location.href=a+"?provinceId="+thisId;
        }else{
            var a = replaceParamVal("provinceId","");
            if(a.lastIndexOf("&") == (a.length -1)){
                window.location.href=a+"provinceId="+thisId;
            }else{
                window.location.href=a+"&provinceId="+thisId;
            }

        }
    });
    function replaceParamVal(paramName,replaceWith) {
        var oUrl = this.location.href.toString();
        var re=eval('/('+ paramName+'=)([^&]*)/gi');
        var nUrl = oUrl.replace(re,replaceWith);
        return  nUrl;
    }

    //右侧导航用户反馈
    $(".userBack span").click(function () {
        $(".userBackPop").show();
    });
    $(".userBack").click(function () {
        $(".userBackPop").show();
    });
    $(".userBackPop .backInner > img").click(function () {
        $(".userBackPop").hide();
    });
    //任意点击区域，用户反馈隐藏
    $(".userBackPop").click(function (e) {
        if (!$(e.target).is(".backInner") && !$(e.target).parents().is(".backInner")) {
            $(".userBackPop").hide();
        }
    });
    
    $(".goRightCart span").click(function () {
    	window.location.href=window.contextPath+'/cart';
    });
    $(".rightCollected span").click(function () {
    	window.location.href=window.contextPath+'/person-center/collected';
    });
    
    $(".rightCoupon span").click(function () {
    	window.location.href=window.contextPath+'/person-center/coupon';
    });
    
    $(".rightRecharge span").click(function () {
    	window.location.href=window.contextPath+'/person-center/recharge';
    });

    $(".rightCollected").click(function () {
        window.location.href=window.contextPath+'/person-center/collected';
    });

    $(".rightCoupon").click(function () {
        window.location.href=window.contextPath+'/person-center/coupon';
    });

    $(".rightRecharge").click(function () {
        window.location.href=window.contextPath+'/person-center/recharge';
    });


    var clip = new ZeroClipboard( document.getElementById("d_clip_button"), {
        moviePath: window.contextPath+"/js/pc/zero/ZeroClipboard.swf"
    } );

    // 复制内容到剪贴板成功后的操作
    clip.on( 'complete', function(client, args) {
        layer.msg('复制成功',{ time: 1500}, function(){});
    } );
    
    //分享弹窗
    $(".rightShare").click(function () {
    	   /* 	$("#rightFloat_a").attr("href","www.xxx.com");
    	    	$("#rightFloat_a").text('未登录');*/
    	    	 $.ajax({
    	             url: contextPath + '/reg/generateRecommendedCode',
    	             type: 'post',
    	             async: false,
    	             /*data: formData,*/
    	             dataType: 'json',
    	             success: function (data) {
    	            	 $("#rightFloat_a").attr("href",data.url);
    	             	$("#rightFloat_a").text(data.result);

    	             },
    	             error:function(){
    	                 alert("系统错误");
    	                
    	             }
    	         });
    	        $(".sharePop").show();
    });
    
    /*用户反馈绑定*/
    $("#feedbackSubmit").click(function () {
    	memberFeedback();

    });
    
    /*用户反馈*/
    function memberFeedback(){
        $("#feedbackSubmit").off('click');
        /*validate 验证*/
        var formValidate = $("#askForm").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#feedbackSubmit").on('click',function(){
            	memberFeedback();
            });
            return false;
        }
       
       
        var formData = $("#askForm").serialize();
        $.ajax({
            url: window.contextPath + '/person-center/saveByAjax',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
              /*  if(message.result){ debugger;
                	location.reload();
                }else{
                	 debugger;
                    重新绑定注册事件
                    $("#feedbackSubmit").on('click',function(){
                    	memberFeedback();
                    });
                }*/
                $(".userBackPop").hide();
                layer.msg('谢谢反馈',{ time: 1500}, function(){});
            },
            error:function(){
                alert("系统错误");
                $("#feedbackSubmit").on('click',function(){
                	memberFeedback();
                });
            }
        });

    };
    
    $(".sharePopInner .shareTitle").click(function () {
        $(".sharePop").hide();
    });
    //任意点击区域，分享弹窗隐藏
    $(".sharePop").click(function (e) {
        if (!$(e.target).is(".sharePopInner") && !$(e.target).parents().is(".sharePopInner")) {
            $(".sharePop").hide();
        }
    });
    //关闭头部广告
    $(".headAdverties .closeAdv img").click(function () {
        $(".headAdverties").hide();
    });
    //搜索类型切换
    $(".searchWrap .searchType").click(function () {
        $(".searchSelect").toggle();
    });
    $(".searchType .searchSelect").click(function () {
        var thisText = $(this).text();
        var type = $(this).data("type");

        var parentsText = $(".searchType span").text();
        var changType = $(".searchType span").data("type");
        $(this).text(parentsText);
        $(this).data("type",changType);
        $(".searchType span").text(thisText);
        $(".searchType span").data("type",type);

    });
    $(".searchWrap  .searchBtn").click(function () {
        var kw = $(".searchInput").val();
        var type = $(".searchType span").data("type");
        if(type=="0"){
            window.location.href =  contextPath+"/search?kw=" + kw;
        }else{
            window.location.href =  contextPath+"/contentManagement/newsList?newskeyword=" + kw;
        }
    });
    $(".searchInput ").keydown(function (event) {
        if (event.keyCode == 13) {
            var kw = $(".searchInput").val();
            var type = $(".searchType span").data("type");
            if(type=="0"){
                window.location.href = contextPath+"/search?kw=" + kw;
            }else{
                window.location.href = contextPath+"/contentManagement/newsList?newskeyword=" + kw;
            }
        }
    });

    //动态获取二级导航的高度
    var liNum = $(".slideNav >li").size() * $(".slideNav >li").height();
    $(".allKind .slideNav .righNav").css("height", liNum - 8 + "px");
    //右侧导航距离顶部高度
    var topHeight = $(window).height() - $(".handelList").height();
    $(".handelList").css("padding-top", topHeight / 2);
    //右侧导航介绍效果
    $(".rightFloat .handelList li").hover(function () {
        $(this).find("span").stop().animate({ left: "-140px" }, 500);
    }, function () {
        $(this).find("span").stop().animate({ left: "34px" }, 500);
    });
    //返回顶部
    $(".goTop").click(function () {
        $('body,html').animate({ scrollTop: 0 }, 300);
    });
    //右侧购物车效果
    $(".goRightCart").click(function () {
        $(this).addClass("on");
        $(".rightFloat").animate({ right: "0" }, 800);
    });
    $(".rightCartTitle a").click(function () {
        $(".goRightCart").removeClass("on");
        $(".rightFloat").animate({ right: "-310px" }, 500);
    });
    //全选和反选
    $(".checkAll").click(function () {
        var checked = $(this).attr("checked");
        if (checked == "checked") {
            $(".rightCartList input[type='checkbox']").attr("checked", "checked");
        } else {
            $(".rightCartList input[type='checkbox']").removeAttr("checked");
        }
    });
    $(".rightCartList input[name='checkOne']").click(function () {
        var len = $(".rightCartList input[name='checkOne']").length;
        var checkedLen = $(".rightCartList input:checked").length;
        $(".checkAll").attr("checked", len == checkedLen);
        if (len != checkedLen) {
            $(".rightCartList").removeAttr("checked");
        } else {
            $(".rightCartList input[type='checkbox']").attr("checked", "checked");
        }
    });
    //购物车增加：
    $(".numAdd").click(function () {
        var oo = $(this).prev().val();
        $(this).prev().val(parseInt($(this).prev().val()) + 1);
        var num = parseInt($(this).prev().val());
    });
    //购物车减少：
    $(".numDecrice").click(function () {
        var oo = $(this).next().val();
        if (parseInt(oo) > 1) {
            $(this).next().val(parseInt($(this).next().val()) - 1);
            var num = parseInt($(this).next().val());
        }
    });
    //购物车输入框校验
    $(".rightCartInput").blur(function () {
        var val = $.trim($(this).val());
        if (!val.isPositiveNum()) {
            $(this).val(1);
        }
    });
    String.prototype.isPositiveNum = function () {
        return (/^[0-9]*[1-9][0-9]*$/.test(this));
    }
    //结算按钮的点击
    //$(".goToPay").click(function () {
    //    var checkedLen = $(".rightCartList :checked").length;
    //    if (checkedLen > 0) {
    //        window.location.href="writeOrder.html";
    //    } else {
    //        alert("请选择一个商品！");
    //    }
    //});
    //发表咨询验证
    $("#askForm").validate({
        rules: {
        	content: {
                required: true
            }
        },
        messages: {
        	content: {
                required: "请输入意见和建议"
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
        },
        nullToEmpty:function (obj){
            return obj ? obj : '';
        }
    }
    window.HgUtil = new HgUtil();
})(jQuery,window);