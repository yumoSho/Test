/**
 * Created by Administrator on 2016/4/7.
 */
$(function(){
    $.validator.addMethod("isRoleExists", function(value, element){
        var exists = 0;
        $.ajax({
            url: contextPath + '/admin/role/checkIsRoleExists',
            type: 'post',
            async: false,
            data: {'name': value},
            dataType: 'json',
            success: function(data) {
                exists = data.isExists;
            }
        });
        return this.optional(element) || !exists;
    },"该角色名已存在");
});