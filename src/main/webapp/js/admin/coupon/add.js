/**
 * Created by Administrator on 2016/4/6.
 */

$(function () {
    $(".btn-cancel-wrap input").click(function () {
        window.location.href = contextPath + "/admin/couponTempl/index";
    });

    $('input:radio[name=autoSend]').click(function(){
        var val = $(this).val();
        if(val == 1){
          $('input[name=autoMinPrice]').addClass("required").closest("tr").find('th>b').text("*");
          $('input[name=autoMaxPrice]').addClass("required").closest("tr").find('th>b').text("*");
        } else {
            $('input[name=autoMinPrice]').removeClass("required").closest("tr").find('th>b').empty();
            $('input[name=autoMaxPrice]').removeClass("required").closest("tr").find('th>b').empty();
        }
    });
    //验证
    $("#form").validate({
        submitHandler: function(form){
            $("#form").find(":submit").attr("disabled", true).attr("value",
                "Submitting...").css("letter-spacing", "0");
            form.submit();
        }
    });


});

