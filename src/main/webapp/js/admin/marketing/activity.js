var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: ['操作', 'id','活动列表图','活动名称','折扣','开始时间','结束时间','排序','活动banner图','添加时间'],
    colModel: [
        { template: 'actions2', width:150},
        { name: 'id', index: 'id', hidden: true, key: true ,align: 'left'},
        { name: 'activityImgPath', index: '', template: 'img', align: 'center',width:230},
        { name: 'activityName', index: 'activityName', template: 'text', align: 'left',width:230},
        { name: 'discount', index: 'discount', template: 'text', align: 'left',width:220},
        { name: 'startDate',index:'alias', template: 'date',search:false, formatter:"date", align: 'left', formatoptions: {newformat:'Y-m-d H:m:s'}},
        { name: 'endDate', index: 'brandCode', template: 'date',search:false, formatter:"date", align: 'left',formatoptions: {newformat:'Y-m-d H:m:s'}},
        { name: 'sort', index: 'sort', template: 'text', align: 'left',width:220},
        { name: 'activityBannerPath', index: '', template: 'img', align: 'center',width:230},
        { name: 'createdDate', index: 'sort', template: 'date',search:false,align: 'left'}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit: true,
    pager: '#pagination',
    sortname: 'sort',
    height: 'auto',
    sortorder: 'asc'
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