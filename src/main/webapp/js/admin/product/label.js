var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id','标签名称','标签图标','是否启用','标签编码','排序'],
    colModel: [
        { template: 'actions2' },
        { name: 'id', index: 'id', hidden: true, key: true ,align: 'left'},
        { name: 'labelName', index: 'labelName', template: 'text', align: 'left'},
        { name: 'labelPath', index: 'labelPath', template: 'img', align: 'center'},
        {
            name: 'disabled', index: 'disabled', formatter: function (va) {
            if (va) return "否"; else return "是";
        }, stype: 'select', searchoptions: {value: ':全部;0:是;1:否'}, align: 'left'
        },
        { name: 'labelCode', index: 'labelCode', template: 'text', align: 'left'},
        { name: 'sort', index: 'sort', template: 'text', align: 'left'},
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit: true,
    pager: '#pagination',
    sortname: 'createdDate',
    height: 'auto',
    sortorder: 'desc'
});

$('.operateBar .operate-delete').click(function () {
    var keys = $g.jqGrid('getGridParam', 'selarrrow');
    1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("警告", "您确定要删除选择的" + keys.length + "行记录吗？", function (r) {
        r && $.ajax({
            url: 'delete',
            type: 'post',
            traditional: true,
            data: { id: keys }
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