/**
 * Created by Administrator on 2016/4/12.
 */

$(function () {
    $(".btn-cancel-wrap input").click(function () {
        window.location.href = contextPath + "/admin/member/index";
    });
    $('.chosen-select').chosen({});
    $("#form").validate({
        rules: {
            memberName: {required: true},
            password:{minlength:6},
            password2:{minlength:6, equalTo: "#password"},
            phone: {required: true, phone: true},
            email: {email: true},
            //idNo: {required: true, isIdCardNo: true},
            status: {required: true}
        },
        submitHandler: function (form) {
            $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
            form.submit();
        }
    })
    ;
})

