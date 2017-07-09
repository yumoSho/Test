/**
 * Created by Administrator on 2016/4/6.
 */

$(function () {
    $(".btn-cancel-wrap input").click(function () {
        window.location.href = contextPath + "/admin/bank/index";
    });

    //验证
    $("#form").validate({
        rules: {
            name: {required: true},
            logo: {required: true},
            recommendImg: {required: true},
            contact: {required: true},
            phone: {required: true,phone:true},
            areaCode: {required: true},
            labels: {required: true},
            address: {required: true},
            intro: {required: true},
            detailedIntro: {required: true}
        },
        highlight: function (element, errorClass) {
            if(element.name == "labels"){
                $("#labelVal").focus();
            }
    },
        submitHandler: function(form){
            $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
            form.submit();
        }
    });

    //店铺标签处理
    $("#addLabel").click(function(){
        var labelVal = $("#labelVal").val();
        var spDiv = $("#labelWrap");
        var tags = spDiv.find("input:hidden");
        var flag = true;
        if(labelVal){
            tags.each(function (i, inp) {
                if ($(inp).val() == labelVal){
                    flag = false;
                    return false;
                }
            });
            if (flag) {
                var spHtml = $('<li>'
                    + labelVal
                    + ' <b onclick="delSp(this);"></b><input type="hidden" name="label" value="' + labelVal + '"></li>');
                spDiv.prepend(spHtml);
            }
            $("#ygztag").css("display", "table-row");
            $("#labels").val("1").siblings("label.error").hide();
            $("#labelVal").val("").focus();
        }
    });

});
function delSp(obj) {
    $(obj).parent('li').remove();
    var tags = $("#labelWrap").children();
    if (1 > tags.length) {
        $('#ygztag').hide();
        $("#labels").val("").siblings("label.error").show();
    }
}

