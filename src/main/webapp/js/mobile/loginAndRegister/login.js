/**
 * Created by Administrator on 2016/4/20.
 */

$(function () {
    $("#submit").on("click", function () {
        login();
    });

    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            jQuery("#submit").click();
        }
    });

    //表单验证
    $("#loginForm").validate({
        errorLabelContainer: $(".errorLabel"),
        rules: {
            loginKey: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            loginKey: {
                required: "请输入账户名"
            },
            password: {
                required: "请输入密码"
            }
        }
    })

    //图片验证码
    $(".change").click(function () {
        $("#kaptchaImage").css("visibility", "hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility", "visible");
        $("#vcode").val("");
    });

    function login() {
        $("#submit").off("click");

        var formValidate = $(".loginForm").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#submit").on('click',function(){
                login();
            });
            return false;
        }
        var data = $(".loginForm").serialize();
        $.ajax({
            url: contextPath + '/login/login',
            type: 'post',
            async: false,
            data: data,
            dataType: 'json',
            success: function (message) {
                if (message.success) {
                    var redirectURL = $("#redirectURL").val();
                    if (!redirectURL) {
                        redirectURL = contextPath + "/";
                    }
                    window.top.document.location.href = redirectURL;
                } else {
                    layer.open({
                        content: message.message,
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    });
                    $("#submit").on("click", function () {
                        login();
                    });
                }
            },
            error: function () {
                $("#submit").on("click", function () {
                    login();
                });
            }
        });
    }

});

