/**
 * Created by Administrator on 2016/4/6.
 */
$(function () {
    $(".btn-cancel-wrap input").click(function () {
        window.location.reload();
    });
    //验证
    $("#form").validate({
        rules: {
            name: {required: true,maxlength:20},
            intro: {required: true,maxlength:20},
            description: {required: true,maxlength:120},
            phone: {required: true,telephone:true},
            qq: {required: true,number:true},
            validateImg:{required: true},
            validateImg1:{required: true}
        },
        messages:{
            qq:{
                required:'请输入QQ号',
                number: "请输入正确的QQ号"
            },
            validateImg:{
                required:'图片不能为空'
            },
            validateImg1:{
                required:'图片不能为空'
            }
        },
        errorPlacement: function(error, element) {

            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            if (imgValidate.validateImg()) { //图片验证
                $("#form").find(":submit").attr("disabled", true).attr("value",
                    "保存中...").css("letter-spacing", "0");
                form.submit();
            }
        }
    });
});
