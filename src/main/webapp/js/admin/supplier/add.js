/**
 * Created by Administrator on 2016/4/7.
 */

$(function () {
    $(".btn-cancel-wrap input").click(function () {
        window.location.href = contextPath + "/admin/supplier/index";
    });

    //验证
    $("#form").validate({
        rules: {
            supplierName: {required: true},
            email: {required: true,email:true},
            contact: {required: true},
            phone: {required: true,phone:true},
            areaCode: {required: true},
            address: {required: true},
        },
        submitHandler: function(form){
            $("#form").find(":submit").attr("disabled", true).attr("value",
                "Submitting...").css("letter-spacing", "0");
            form.submit();
        }
    });
})

