$(function () {
    bindKeypress("nextStepByMail");
    function bindKeypress(id){
        $(document).off('keypress').on('keypress',function(e) {
            // 回车键事件
            if(e.which == 13) {
                $("#" + id).click();
            }
        });
    }
    //找回密码方式切换
    $(".phoneBack").click(function () {
        $(this).addClass("changeBorC");
        $(".emailBack").removeClass("changeBorC");
        $("#emailBack1").hide();
        $("#phoneBack1").show();
        $("#mobile").focus();
        bindKeypress("nextStepByPhone");
        $("#phoneBack1").find(".kaptchaImage").trigger("click");//刷新图片
    });
    $(".emailBack").click(function () {
        $(this).addClass("changeBorC");
        $(".phoneBack").removeClass("changeBorC")
        $("#phoneBack1").hide();
        $("#emailBack1").show();
        $("#mail").focus();
        bindKeypress("nextStepByMail");
        $("#emailBack1").find(".kaptchaImage").trigger("click");//刷新图片
    });

    //邮箱找回密码验证
    $(".mailBox").validate({
        rules: {
            mail: {
                required: true,
                email: true
            },
            vcodes: "required"
        },
        messages: {
            mail: {
                required: "请输入邮箱",
                email: "请输入一个有效的邮箱"
            },
            vcodes: "请输入验证码"
        },
        highlight: function (element, errorClass) {
            $(element).parents("td").children(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
            if(element.name == 'vcodes') return false;
            $(element).parents("td").append("<span class='correct'></span>");
        }
    });
    //手机找回密码验证
    $(".mobileBox").validate({
        rules: {
            mobile: {
                required: true,
                mobile: true
            },
            vcodes: "required"
        },
        messages: {
            mobile: {
                required: "请输入手机号",
                email: "请输入正确的手机号"
            },
            vcodes: "请输入验证码"
        },
        highlight: function (element, errorClass) {
            $(element).parents("td").children(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
            if(element.name == 'vcodes') return false;
            $(element).parents("td").append("<span class='correct'></span>");
        }
    });

    //绑定邮箱改密码下一步 事件
    $("#nextStepByMail").on("click",function(){
        submitByMal();
    });

    /**
     * 邮箱改密码提交
     * @returns {boolean}
     */
    function submitByMal(){
        $("#nextStepByMail").off("click");
        var formValidate = $(".mailBox").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#nextStepByMail").on('click',function(){
                submitByMal();
            });
            return false;
        }
        var formData = $(".mailBox").serialize();
        $.ajax({
            url: contextPath + '/retrieve/validateStepOneByEmail',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                if(message.success){
                    document.location.href = contextPath + "/retrieve/step2?type=1&key=" + $("#mail").val();
                }else{
                    $('#emailBack1 label.error').remove();
                    var code = message.data;
                    $("#kaptchaImage").trigger("click");//刷新图片
                    switch (code){
                        case 1: {$("#mail").after('<label for="mail"  class="error">' + message.message + '</label>');};break;
                        case 2: {$("#codes").after('<label for="codes"  class="error">' + message.message + '</label>');};break;
                        default:{$("#codes").after('<label for="codes"  class="error">' + message.message + '</label>');}
                    }
                    /*重新绑定注册事件*/
                    $("#nextStepByMail").on('click',function(){
                        submitByMal();
                    });
                }
            },
            error:function(){
                alert("系统错误");
                $("#nextStepByMail").on('click',function(){
                    submitByMal();
                });
            }
        });
    }

    //绑定手机改密码下一步 事件
    $("#nextStepByPhone").on("click",function(){
        submitByPhone();
    });

    /**
     * 手机改密码提交
     * @returns {boolean}
     */
    function submitByPhone(){
        $("#nextStepByPhone").off("click");
        var formValidate = $(".mobileBox").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#nextStepByPhone").on('click',function(){
                submitByPhone();
            });
            return false;
        }
        var formData = $(".mobileBox").serialize();
        $.ajax({
            url: contextPath + '/retrieve/validateStepOneByMobile',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                debugger;
                if(message.success){
                    document.location.href = contextPath + "/retrieve/step2?type=2&key=" + $("#mobile").val();
                }else{
                    $('#phoneBackPwd1 label.error').remove();
                    $("#kaptchaImage2").trigger("click");//刷新图片
                    var code = message.data;
                    switch (code){
                        case 1: {$("#mobile").after('<label for="mobile"  class="error">' + message.message + '</label>');};break;
                        case 2: {$("#vcodes").after('<label for="vcodes" class="error">' + message.message + '</label>');};break;
                        default:{$("#vcodes").after('<label for="vcodes"  class="error">' + message.message + '</label>');}
                    }
                    /*重新绑定注册事件*/
                    $("#nextStepByPhone").on('click',function(){
                        submitByPhone();
                    });
                }
            },
            error:function(){
                alert("系统错误");
                $("#nextStepByPhone").on('click',function(){
                    submitByPhone();
                });
            }
        });
    }


    //图片验证码
    $(".kaptchaImage").click(function () {
        $(this).css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
        $(this).parents('td').siblings("td").find("input").val("");
    });
});