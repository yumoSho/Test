$(function () {

    //点击用户注册
    $(".agreementP a").click(function () {
        var $iframe = $("#iframeDiv").show();
        $iframe.find("iframe").show();
        $(".wrap").hide();
    });

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
    //注册页面验证
    $(".registForm").validate({
        errorLabelContainer: $(".errorLabel"),
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
            email:{
                email:true,
                isOnlyMail:true
            },
            sure:"required",
            imageSelOne:{
                accept:false
            },
            imageSelTwo:{
                accept:false
            }

        },
        messages: {
            memberName: {
                required: "请输入用户名",
                rangelength: $.validator.format("请输入长度4-20位用户名")
            },
            password: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位密码")
            },
            confirmPassword: {
                required: "请输入密码",
                rangelength: $.validator.format("请输入长度6-20位密码"),
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
                required: "必须同意用户注册协议"
            }
        },
        highlight: function (element, errorClass) {
            $(element).siblings(".correct").remove();
        },
        unhighlight: function (element, errorClass) {
           if(element.id == 'codes' || element.name == 'email' || element.name == 'pCodes' || element.name == 'sure') return false;
            $(element).parents().siblings(".error").remove();
            $(element).parents("td").append("<span class='correct'></span>");
        }
    });


    //图片验证码
    $(".change").click(function () {
        $("#kaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
        $("#codes").val("");
    });

    //获取验证码
    var btn = $(".getCodes");
    /*绑定验证码事件*/
        btn.on("click", function () {
            sendMsg();//发送短信
        });
    /*发送短信*/
    function sendMsg(){
        if($(".registForm").validate().element($("#phone")) & $(".registForm").validate().element($("#codes"))){
            btn.off("click");
            $(".successMsg").css("visibility", "visible")
            $.ajax({
                url: contextPath + '/reg/sendPhoneVCode',
                type: 'post',
                async: true,
                data: {phone: $("#phone").val(),iCode: $("#codes").val()},
                dataType: 'json',
                success: function (message) {
                    if(message.success){
                        countDown();
                    }else{
                        $("label.cusError").remove();
                        var code = message.data;
                        layer.open({
                            content: message.message,
                            time: 2,
                            style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                        });
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
    function countDown() {
        btn.addClass("on");
        var countdown = false;
        if (!countdown) {
            var sec = 60;
            countdown = true;
            var interval = setInterval(function () {
                if (sec == -1) {
                    btn.removeClass("on");
                    btn.val("免费获取验证码");
                    countdown = false;
                    clearInterval(interval);
                    btn.on("click", function () {
                        sendMsg();//发送短信
                    });
                } else {
                    btn.val(sec + "秒后重新获取");
                    sec--;
                    $(".successMsg").css("visibility", "visible")
                }
            }, 1000);
        }
    };

    /*绑定注册事件*/
    $("#submit").on('click',function(){
        register();
    });

    /*注册方法*/
    function register(){
        $("#submit").off('click');
        /*validate 验证*/
        var formValidate = $(".registForm").validate();
        if(!formValidate.checkForm()){
            formValidate.showErrors();
            $("#submit").on('click',function(){
                register();
            });
            return false;
        }

        /*发送请求到后台*/
        var formData = $(".registForm").serialize();
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
                    var code = message.data;
                    var message = message.message;
                    if(!message) {
                        message = "系统错误";
                    }
                    layer.open({
                        content: message,
                        time: 2,
                        style: "background-color:#fff; color:#333; border:none;font-family:Microsoft YaHei",
                    });
                    /*重新绑定注册事件*/
                    $("#submit").on('click',function(){
                        register();
                    });
                }
            },
            error:function(){
                alert("系统错误");
                $("#submit").on('click',function(){
                    register();
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
})