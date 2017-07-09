/**
 * Created by Administrator on 2016/4/6.
 */

$(function () {
    //必填
    $(".autoSend").click(function(){
        var val = $(this).val();
        if(val == 1){
            $(".autoSend").find("b").text("*");
            $(".autoSend").find("input").addClass("required");
        }else {
            $(".autoSend").find("b").text("");
            $(".autoSend").find("input").removeClass("required");
        }
    });

    $(".btn-cancel-wrap input").click(function () {
        window.location.href = contextPath + "/admin/couponTempl/index";
    });

    //验证
    $("#form").validate({
        submitHandler: function(form){
            $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
            form.submit();
        }
    });


});

