var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id','分类名称','分类编号', '上级分类', '是否可见','模型','分类图标'],
    colModel: [
        { template: 'actions2' },
        { name: 'id', index: 'tc.id', hidden: true, key: true ,align: 'left'},
        { name: 'name', index: 'tc.name', template: 'text', align: 'left'},
        { name: 'categoryCode', index: 'tc.categoryCode', template: 'text', align: 'left'},
        { name: 'parent.name', index: 'tc.parentName', template: 'text', align: 'left'},
        { name: 'visible',index:'tc.visible', formatter: function (va) {
            if (va) return "是"; else return "否";
        }, stype: 'select', searchoptions: {value: ':全部;1:是;0:否'}, align: 'left'},
        { name: 'model.name', index:'tc.modelName',template:'text',align:'left'},
        { name: 'picture', index: 'picture', template: 'img', align: 'center'}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit:true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'createdDate',
    sortorder: 'desc'
});

$('.operateBar .operate-delete').click(function () {
    var keys = $g.jqGrid('getGridParam', 'selarrrow');
    1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("警告", "您确定要删除选择的" + keys.length + "行记录吗？", function (r) {
        r && $.ajax({
            url: 'delete',
            type: 'post',
            traditional: true,
            data: {id: keys}
        }).done(function (data) {
            var removed;
            if (data.success) {
                removed = data.success || [];
                $g.trigger("reloadGrid");
                layer.alert('操作成功', {
                    skin: 'layer-ext-message' //样式类名
                    ,closeBtn:1
                    ,time:3000
                    ,title:'提示 [3秒后消失]'
                    ,shade: 0
                    ,offset:'rb'
                    ,btn:''
                });
            }else{
                layer.alert(data.message, {
                    skin: 'layer-ext-message' //样式类名
                    ,closeBtn:1
                    ,time:3000
                    ,title:'提示 [3秒后消失]'
                    ,shade: 0
                    ,offset:'rb'
                    ,btn:''
                });
            }
        }).fail(function () {
            layer.alert('操作失败', {
                skin: 'layer-ext-message' //样式类名
                ,closeBtn:1
                ,time:3000
                ,title:'提示 [3秒后消失]'
                ,shade: 0
                ,offset:'rb'
                ,btn:''
            });
        });
    });
});