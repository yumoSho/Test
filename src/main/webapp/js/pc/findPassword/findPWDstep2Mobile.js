$(function () {
    $(document).keypress(function(e) {
        // 回车键事件
        if(e.which == 13) {
            $(".nextStep").click();
        }
    });

    $("#verificationCodes").on("input", function () {
        if ($("#verificationCodes").val()) {
            $("#verificationCodes").next(".error").remove();
        }
    });

    var btn = $(".getCodes");
    /*绑定验证码事件*/
    btn.on("click", function () {
        sendMsg();//发送短信
    });

    /*发送短信*/
    function sendMsg() {
        btn.off("click");
        $.ajax({
            url: contextPath + '/retrieve/sendPhoneVCode',
            type: 'post',
            async: true,
            dataType: 'json',
            data:{phone:$("#phone").val()},
            success: function (message) {
                if (message.success) {
                    countDown();
                } else {
                    $("#verificationCodes").next("label").remove();
                    $("#verificationCodes").after('<label for="verificationCodes" class="error" >' + message.message + '</label>');
                    btn.on("click", function () {
                        sendMsg();//发送短信
                    });
                }
            },
            error: function () {
                $("#verificationCodes").after('<label for="verificationCodes" class="error" >请求失败</label>');
                btn.on("click", function () {
                    sendMsg();//发送短信
                });
            }
        });
    }

    /*发送短信验证码后进行倒计时*/

    //60秒验证码
    var wait = 60;
    function countDown() {
        $me = btn;
        if (wait == 0) {
            $me.removeAttr("disabled");
            $me.val("获取验证码");
            $me.on("click", function () {
                sendMsg();//发送短信
            });
            $me.css({
                "background": "#19aa4b",
                "border-color": "#19aa4b"
            });
            wait = 60;
        } else {
            $me.attr("disabled", true);
            $me.val("重新发送(" + wait + ")");
            $me.css({
                "background": "#ccc",
                "border-color": "#ccc"
            });
            wait--;
            setTimeout(function () {
                    countDown();
                },
                1000)
        }
    };
});



    /*校验短信验证码*/
    $(".nextStep").on("click", function () {
        validateCode();
    });

    function validateCode() {
        $(".nextStep").off("click");
        var val = $("#verificationCodes").val();
        if (!val) {
            $("#verificationCodes").next("label").remove();
            $("#verificationCodes").after('<label for="verificationCodes" generated="true" class="error" >请输入验证码</label>');
            $(".nextStep").on("click", function () {
                validateCode();
            });
            return false;
        }
        var phone = $("#phone").val();
        $.ajax({
            url: contextPath + '/retrieve/validateCodes',
            type: 'post',
            async: true,
            dataType: 'json',
            data: {pCode: $("#verificationCodes").val(),phone:phone},
            success: function (message) {
                if (message.success) {
                    document.location.href = contextPath + "/retrieve/phoneToStep3?phone=" + phone;
                } else {
                    var code = message.data;
                    switch (code) {
                        case 2:
                        {
                            $("#verificationCodes").after('<label for="verificationCodes" generated="true" class="error" >' + message.message + '</label>');
                        }
                            ;
                            break;
                        default:
                        {
                            $("#verificationCodes").after('<label for="verificationCodes" generated="true" class="error" >' + message.message + '</label>');
                        }
                    }
                    $(".nextStep").on("click", function () {
                        validateCode();
                    });
                }
            },
            error: function () {
                $(".nextStep").on("click", function () {
                    validateCode();
                });
            }
        });
    }