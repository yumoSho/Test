var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id','商品名称','排序','分类','最后修改时间'],
    colModel: [
        { template: 'actions2' },
        { name: 'id', index: 'id', hidden: true, key: true ,align: 'center'},
        { name: 'goods.title', index: 'gTitle', template: 'text', align: 'center'},
        { name: 'sort', index: 'sort', template: 'text', align: 'center'},
        { name: 'goods',index:'gPtCName',  template: 'text',align: 'center',formatter:function(v){
            return v['product.category'].name;
        }},
        { name: 'lastModifiedDate', index: 'lastModifiedDate',template: 'date',search:false,align: 'center'}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit:true,
    height: 'auto',
    pager: '#pagination'
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
            layer.alert(data.message, {
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

