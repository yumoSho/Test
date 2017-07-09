$(function () {
    /*会员名称唯一性校验规则*/
    $.validator.addMethod("isOnlyName", function (value, element) {
        var exists = 0;
        $.ajax({
            url: contextPath + '/reg/verifyMemberName',
            type: 'post',
            async: false,
            data: {'memberName': value},
            dataType: 'json',
            success: function (message) {
                exists = message.success;
            }
        });
        return this.optional(element) || exists;//表单不为空时才触发验证？
    }, $.validator.format("用户名已注册"));

    /*手机号唯一性校验规则*/
    $.validator.addMethod("isOnlyPhone", function (value, element) {
        var exists = 0;
        $.ajax({
            url: contextPath + '/reg/verifyPhone',
            type: 'post',
            async: false,
            data: {'phone': value},
            dataType: 'json',
            success: function (message) {
                exists = message.success;
            }
        });
        return this.optional(element) || exists;//表单不为空时才触发验证？
    }, $.validator.format("手机号已注册"));

    /*邮箱唯一性校验规则*/
    $.validator.addMethod("isOnlyMail", function (value, element) {
        var exists = 0;
        $.ajax({
            url: contextPath + '/reg/verifyMail',
            type: 'post',
            async: false,
            data: {'mail': value},
            dataType: 'json',
            success: function (message) {
                exists = message.success;
            }
        });
        return this.optional(element) || exists;//表单不为空时才触发验证？
    }, $.validator.format("邮箱已注册"));

    /*用户名校验*/
    $.validator.addMethod("userName", function (value, element) {
        var flag = 0;
        if(value.match(/^[a-zA-Z\d\u4e00-\u9fa5][\w\u4e00-\u9fa5]{2,19}[a-zA-Z\u4e00-\u9fa5\d]$/)){
            flag = true;
        }
        return this.optional(element) || flag;//表单不为空时才触发验证？
    }, $.validator.format("请输入汉字、字母、数字及下划线组合"));
    //手机注册验证
    $("#phoneForm").validate({
        rules: {
            memberName: {
                required: true,
                rangelength: [4, 20],
                isOnlyName: true,
                userName:true
            },
            password: {
                required: true,
                rangelength: [6, 20]
            },
            confirmPassword: {
                required: true,
                rangelength: [6, 20],
                equalTo: ".password"
            },
            phone: {
                required: true,
                mobile: true,
                isOnlyPhone: true
            },
            iCodes: {
                required: true
            },
            pCodes: "required",
            sure:"required",
        },
        messages: {
            memberName: {
                required: "请输入用户名",
                rangelength: $.validator.format("请输入长度4-20位字符")
            },
            password: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符")
            },
            confirmPassword: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符"),
                equalTo: "两次密码输入不一致"
            },
            phone: {
                required: "请输入手机号",
                mobile: "请输入一个正确的手机号码"
            },
            iCodes: {
                required: "请输入验证码"
            },
            pCodes: {
                required: "请输入短信验证码"
            },
            sure:{
                required: "必须同意该协议"
            }
        },
        highlight: function (element, errorClass) {

        },
        unhighlight: function (element, errorClass) {

        }
    });

//邮箱注册验证
    $("#mailForm").validate({
        rules: {
            memberName: {
                required: true,
                rangelength: [4, 20],
                isOnlyName: true,
                userName:true
            },
            password: {
                required: true,
                rangelength: [6, 20]
            },
            confirmPassword: {
                required: true,
                rangelength: [6, 20],
                equalTo: ".password2"
            },
            iCodes: {
                required: true
            },
             email:{
                 required:true,
             email:true,
             isOnlyMail:true
             },
            sure:"required",
        },
        messages: {
            memberName: {
                required: "请输入用户名",
                rangelength: $.validator.format("请输入长度4-20位字符")
            },
            password: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符")
            },
            confirmPassword: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位字符"),
                equalTo: "两次密码输入不一致"
            },
            iCodes: {
                required: "请输入验证码"
            },
            email :{
                required:"请输入邮箱"
            },
            sure:{
                required: "必须同意该协议"
            }
        },
        highlight: function (element, errorClass) {
            $(element).siblings(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
            $(element).parents("td").append("<span class='correct'></span>");
        }
    });

    //图片验证码
    $("#kaptchaImage").click(function () {
        $("#kaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
        $("#codes").val("");
    });
    $("#ikaptchaImage").click(function () {
        $("#ikaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
        $("#icodes").val("");
    });

    //获取验证码
    var btn = $(".getCodes");
    /*绑定验证码事件*/
        btn.on("click", function () {
            sendMsg();//发送短信
        });
    /*发送短信*/
    function sendMsg(){
        if($("#phoneForm").validate().element($("#phone")) & $("#phoneForm").validate().element($("#codes"))){
            btn.off("click");
            $.ajax({
                url: contextPath + '/reg/sendPhoneVCode',
                type: 'post',
                async: true,
                data: {phone: $("#phone").val(),iCode: $("#codes").val()},
                dataType: 'json',
                success: function (message) {
                        $("#phoneForm label.error").remove();
                    if(message.success){
                        countDown();
                    }else{
                        //$("#pCodes").parents("td").find("label").remove();
                        var code = message.data;
                        switch (code){
                            case 1: {$("#phone").after('<label for="phone" generated="true" class="error" style="display: block;">' + message.message + '</label>');};break;
                            case 2: {$("#codes").after('<label for="codes" generated="true" class="error" style="display: block;">' + message.message + '</label>');};break;
                            case 4: {$("#pCodes").after('<label for="pCodes" generated="true" class="error" style="display: block;">' + message.message + '</label>');};break;
                            default:{$("#pCodes").after('<label for="pCodes" generated="true" class="error" style="display: block;">' + message.message + '</label>');}
                        }
                        $("#kaptchaImage").trigger("click");//刷新图片
                        btn.on("click", function () {
                            sendMsg();//发送短信
                        });
                    }
                },
                error:function(){
                    $("#pCodes").parents("td").append('<label for="pCodes" generated="true" class="error">请求失败</label>');
                    btn.on("click", function () {
                        sendMsg();//发送短信
                    });
                }
            });
        }
    }
    /*发送短信验证码后进行倒计时*/

    //60秒验证码
    var wait = 60;
    function countDown() {
        var $me = $("input.getMsgCode");
        if (wait == 0) {
            $me.removeAttr("disabled");
            $me.val("获取短信验证码");
            $me.css({
                "background": "#19aa4b",
                "border-color": "#19aa4b"
            });
            wait = 60;
            btn.on("click", function () {
                sendMsg();//发送短信
            });
        } else {
            $me.attr("disabled", true);
            $me.val("重新发送(" + wait + ")");
            $me.css({
                "background": "#ccc",
                "border-color": "#ccc"
            });
            wait--;
            setTimeout(function () {
                    countDown()
                },
                1000)
        }
    };

    /*绑定手机注册事件*/
    $("#submit").on('click',function(){
        register("phoneForm");
    });
    /*邮箱注册事件*/
    $("#isubmit").on('click',function(){
        registerByMail("mailForm");
    });

    /*手机注册方法*/
    function register(formName){
        $("#submit").off('click');
        /*validate 验证*/
        var formValidate = $("#" + formName).validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#submit").on('click',function(){
                 register("phoneForm");
            });
            return false;
        }

        /*发送请求到后台*/
        var formData = $("#" + formName).serialize();
        $.ajax({
            url: contextPath + '/reg/register',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                if(message.success){
                    document.location.href = contextPath + "/";
                }else{
                    $("#phoneMail label.error").remove();
                    var code = message.data;
                    switch (code){
                        case 1: {$("#phone").after('<label for="phone" generated="true" class="error">' + message.message + '</label>');};break;
                        case 3: {$("#pCodes").after('<label for="pCodes" generated="true" class="error">' + message.message + '</label>');};break;
                        case 5: {$("#memberName").after('<label for="memberName" generated="true" class="error">' + message.message + '</label>');};break;
                        default:{alert("系统错误");}
                    }
                    /*重新绑定注册事件*/
                    $("#submit").on('click',function(){
                         register("phoneForm");
                    });
                }
            },
            error:function(){
                alert("系统错误");
                $("#submit").on('click',function(){
                     register("phoneForm");
                });
            }
        });

    }

    /*邮箱注册方法*/
    function registerByMail(formName){
        $("#isubmit").off('click');
        /*validate 验证*/
        var formValidate = $("#" + formName).validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#isubmit").on('click',function(){
                registerByMail("mailForm");
            });
            return false;
        }

        /*发送请求到后台*/
        var formData = $("#" + formName).serialize();
        $.ajax({
            url: contextPath + '/reg/registerByMail',
            type: 'post',
            async: false,
            data: formData,
            dataType: 'json',
            success: function (message) {
                if(message.success){
                    document.location.href = contextPath + "/reg/emailActivate?mail=" + $("#mail").val();
                }else{
                    $("#mailForm label.error").remove();
                    var code = message.data;
                    switch (code){
                        case 2: {$("#icodes").after('<label for="icodes" generated="true" class="error">' + message.message + '</label>');};break;
                        case 5: {$("#imemberName").after('<label for="imemberName" generated="true" class="error">' + message.message + '</label>');};break;
                        default:{alert("系统错误");}
                    }
                            $("#ikaptchaImage").trigger("click");//刷新图片
                    /*重新绑定注册事件*/
                    $("#isubmit").on('click',function(){
                        registerByMail("mailForm");
                    });
                }
            },
            error:function(){
                alert("系统错误");
                    $("#ikaptchaImage").trigger("click");//刷新图片
                $("#isubmit").on('click',function(){
                    registerByMail("mailForm");
                });
            }
        });

    }



    //用户注册协议弹窗
    var agreeBtn = $(".agreementBtn");
    agreeBtn.click(function () {
        $(".popAgreements").show();
    });
    //关闭注册协议弹窗
    $(".shutBtn,.agreeBtn").click(function () {
        $(".popAgreements").fadeOut("fast");
        $(".registTable .validateCheckbox input").attr("checked", "checked");
    });
    //要发送短息必须填写手机号和验证艾曼
  /*  $("#phone,#codes").blur(function(){
        var phone = $("#phone").val();
        var codes = $("#codes").val();
        if(phone && phone.length == 11 && codes){
            $(".getCodes").css('background-color','red').css('color','#fff');
        }else{
            $(".getCodes").css('background-color','#efefef').css('color','#b1b1b1');
        }
    });*/
})
