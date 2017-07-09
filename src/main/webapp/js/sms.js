
/**
 * 发送短信接口
 */

// 验证码发送并进行倒计时
function sendSmsCode(type) {
    //发送验证码
    var $validator = $("#reg_form").validate();
    var isValidate = $validator.element(type == 1 ? $("#email")
        : $("#phone"));
    var isValidateCode = $validator.element($("#vcode"));
    if (!isValidate || !isValidateCode) {
        $validator.showErrors();
        return;
    }
    $.post('${ctx}/register/sendRegisterSms', {
        type : type,
        mobileOrEmail : mobileOrEmail,
        vcode : $('#vcode').val(),
    }, function(data) {
        verify = data; //返回的验证码信息
    });

    fun_timedown(60, type);
}


// 倒计时
function fun_timedown(time, type) {
    if (time == 'undefined')
        time = 60;
    $("#timedown" + type).text(time + "秒后重新发送");
    $("#timedown" + type).attr("onclick", "").css({
        "background" : "none",
        "background-color" : "#ccc",
        "opacity" : "1",
        "cursor": "default"
    });
    time = time - 1;
    if (time >= 0) {
        setTimeout("fun_timedown(" + time + "," + type + ")", 1000);
    } else {
        var str = type == 1 ? "获取邮箱验证码" : "获取短信验证码";
        $("#timedown" + type).text(str);
        $("#timedown" + type)
            .attr("onclick", "sendverifyCode(" + type + ")")
            .css({
                "background" : "url(../images/ico_left_03.png) center no-repeat",
                "background-color" : "none",
                "opacity" : "0.8",
                "cursor": "pointer"
            });
    }
}