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
                equalTo: "#password"
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
                required:"请输入邮箱",
                email:"请输入合法邮箱"
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
    $(".change").click(function () {
        $("#kaptchaImage").css("visibility","hidden").attr('src', contextPath + '/captcha/captchaImage?' + Math.floor(Math.random() * 100)).css("visibility","visible");
        $("#codes").val("");
    });

    /*邮箱注册事件*/
    $("#isubmit").on('click',function(){
        registerByMail("mailForm");
    });

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
})