$(function() {
    var roleIdAndName = [];
    $.validator.addMethod("isRoleExists", function (value, element) {
        var exists = 0;
        roleIdAndName = [$("#roleId").val(), $("#roleName").val()];
        $.ajax({
            url: '${ctx}/admin/role/hasAnnotherRole',
            type: 'post',
            async: false,
            data: {
                'roleIdAndName': roleIdAndName
            },
            dataType: 'json',
            success: function (data) {
                exists = data.isExists;
            },
            traditional: true
        });
        return this.optional(element) || !exists;
    }, "该角色名已存在");
});