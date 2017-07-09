$(function () {
    //设置新密码
    $(".setPasswordBox").validate({
        rules: {
            password: {
                required: true,
                rangelength: [6, 20]
            },
            confirm_password: {
                required: true,
                rangelength: [6, 20],
                equalTo: ".password"
            }
        },
        messages: {
            password: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符")
            },
            confirm_password: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符"),
                equalTo: "两次密码输入不一致"
            }
        },
        highlight: function (element, errorClass) {
            $(element).parents(".inputBox").siblings(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
            $(element).parents("td").append("<span class='correct'></span>");
        }
    });

});