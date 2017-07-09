/**
 * Created by Administrator on 2016/4/20.
 */

$(function () {
    $(function () {
        $("#signupForm").validate({
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
                    required: "请输入用户名"
                },
                password: {
                    required: "请输入密码"
                }
            }
        });
    });
    $("#submit").on("click", function () {
        login();
    });

    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            jQuery("#submit").click();
        }
    });


    //图片验证码
    $(".change").click(function () {
        $("#kaptchaImage").css("visibility", "hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility", "visible");
        $("#vcode").val("");
    });

    function login() {
        $("#submit").off("click");
        var formValidate = $("#signupForm").validate();
        if (!formValidate.checkForm()) {
            formValidate.showErrors();
            $("#submit").on("click", function () {
                login();
            });
            return;
        }
        var data = $("#signupForm").serialize();
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
                    $("#password").next("label").remove();
                    $("#password").after('<label for="password" generated="true" class="error">' + message.message + '</label>');
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

