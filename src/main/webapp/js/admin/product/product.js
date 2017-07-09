    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id', '商品名称', '产品简介/卖点', '所属分类','模型', '所属品牌',/*'希望修改价格', '成本价','修改前价格', '库存',*/ '商品状态', '标签',  '上架日期', '创建日期'],
        colModel: [
            {template: 'actions2', width: 80, fixed: true},
            {name: 'id', index: 'P.id', hidden: true, key: true, align: 'left'},
            {name: 'title', index: 'P.title', template: 'text', align: 'left', searchoptions: {sopt: ["cn"]}},
            {name: 'intro', index: 'P.intro', template: 'text', align: 'left'},
            {name: 'category.name', index: 'C.name', template: 'text', align: 'left', sortable: false},
            {name: 'category.model', index: 'M.name', template: 'text', align: 'left', sortable: false,formatter: function (va) {return va.name;}},
            {name: 'brand.name', index: 'B.name', template: 'text', align: 'left', sortable: false},
            /*{name: 'hopeCostPrice', index: 'P.hopeCostPrice', template: 'text', align: 'center', search: false},
            {name: 'costPrice', index: 'P.costPrice', template: 'text', align: 'center', search: false},
            {name: 'lastCostPrice', index: 'P.lastCostPrice', template: 'text', align: 'center', search: false},
            {name: 'stock', index: 'P.stock', template: 'text', align: 'center', search: false},*/
            {
                name: 'isPutaway', index: 'P.isPutaway', formatter: function (va) {
                if (va) return "已上架"; else return "未上架";
            }, stype: 'select', searchoptions: {value: ':全部;0:未上架;1:已上架'}, align: 'left'
            },
            {name: 'label.labelName', index: 'LL.labelName', template: 'text', align: 'left', search: true},
            {name: 'registerDate', index: 'P.registerDate', template: 'date', search: false, align: 'left'},
            {name: 'createdDate', index: 'P.createdDate', template: 'date', search: false, align: 'left'}
        ],
        multiselect: true,
        autowidth: true,
        shrinkToFit: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc',
        subGrid : true,
        subGridWidth:30,
        subGridUrl : 'goodsOfProduct',
        subGridRowExpanded : function(subgrid_id, row_id) {
            var subgrid_table_id, pager_id;
            subgrid_table_id = subgrid_id + "_t";
            pager_id = "p_" + subgrid_table_id;
            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id
                + "' class='wode' ></table>");
            var aa = jQuery("#" + subgrid_table_id).jqGrid(
                {
                    url : "goodsOfProduct/?id=" + row_id,
                    datatype : "json",
                    colNames : [ '商品编号','商品规格', '单品名称','价格','库存', ],
                    colModel : [
                        {name : "sn",  index : "sn",key : true,width:30,sortable : false,search:false},
                        {name : "specValues",  index : "specValues",key : false,width:30,sortable : false,search:false,formatter: function (va) {
                            var sv= ""
                            if(va){
                                for(var i=0 ;i<va.length;i++){
                                    sv+=va[i].spec.name+":"+va[i].name;
                                    if(i!=va.length-1){
                                        sv+=sv+";";
                                    }
                                }
                            }
                            return sv;
                        }},
                        {name : "title",index : "title",  sortable : false,width:30,search:false},
                        {name : "price",index : "price",width:30,sortable : false,search:false},
                        {name : "stock",index : "stock",sortable : false,width:30,search:false}
                    ],
                    rowNum : 20,
                    toolbar:[false,''],
                    shrinkToFit: true,
                    autowidth: true,
                    forceFit:true,
                    height : '100%'
                })

            var $listPanel = $(".listPage");
            if($listPanel.length > 0){
                aa.setGridWidth($listPanel.width()-200);
                $(window).resize(function(){
                    aa.setGridWidth($listPanel.width()-200);
                });

            }
            aa.jqGrid("destroyFilterToolbar");
        }
    });


    //删除
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

