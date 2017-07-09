/**
 * Created by Administrator on 2016/4/7.
 */
$(document).ready(function(){
    loadTree();

    $("#treeWindow").toggle(function(){
        $("#ztree").css("display","");
    },function(){
        $("#ztree").css("display","none");
    });
});

var setting = {
    treeId : "roleTree",

    check : {
        enable : true,
        chkStyle : "checkbox"
    },
    chkboxType : { "Y" : "ps", "N" : "ps" },
    data : {
        simpleData : {
            enable : true,
            idKey : "id",
            pidKey : "pId",
            rootPId : null
        }
    },
    treeNode : {
        isParent : true
    }
};

function loadTree(){
    var treeNodes;
    $.ajax({
        async:true,//是否异步
        cache:false,//是否使用缓存
        type:'POST',//请求方式：post
        data:{"roleId":$("#roleId").val()},
        dataType:'json',//数据传输格式：json
        url: contextPath + "/admin/module/tree",//请求的action路径
        error:function(){
            //请求失败处理函数
            //alert('亲，请求失败！');
        },
        success:function(data){
            treeNodes = data;
            $.fn.zTree.init($("#roleTree"), setting, treeNodes);

        }
    });
}

$(function(){
    $(".btn-cancel-wrap input").click(function() {
        window.location.href = contextPath + "/admin/role/index";
    });

    //验证
    $("#fromSave").validate({
        rules:{
            name:{
                required: true,
                maxlength: 20,
                isRoleExists: true
            },
            remark: {
                maxlength: 200
            }
        },
        messages:{
            name:{
                required: "角色名不能为空",
                maxlength: "角色名不能超过20个字符"
            },
            remark: {
                maxlength: "备注不能超过200个字符"
            }
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.siblings("span:.errorTip"));
        },
        submitHandler: function(form){
            var treeObj = $.fn.zTree.getZTreeObj("roleTree");
            var nodes = treeObj.getCheckedNodes(true);
            var pageIds = "";
            if (0 < nodes.length) {
                for(var i = 0; i < nodes.length; i++){
                    pageIds += nodes[i].modelId + ",";
                }
            }
            $("input[name=pageIds]").val(pageIds);
            $(form).find(":submit").attr("disabled", true).attr("value",
                "Submitting...").css("letter-spacing", "0");
            form.submit();
        }
    });
})