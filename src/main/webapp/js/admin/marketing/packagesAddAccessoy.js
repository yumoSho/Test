function t(text) { return text; }
var ctx = window.contextPath || '';




$("#btn-primary-sel").click(function(){
    var $grid1 = $('#primaryProductGrid').jqGrid({
        url: ctx+'/admin/activity/get-goodses'
        , datatype: 'json'
        , colNames: [
            t('ID'), t('商品名称'), t('商品分类'), t('商品图片'), t('商品价格')
        ]
        , colModel: [
            {name: 'id', index: 'id', template: "text",hidden:true }
            , {name: 'title', index: 'title', template: "text",width:250 }
            , {name: 'product.category', index: 'pCName' ,template: "text", width:250 ,formatter: function (va) {return va.name;}}
            , {name: 'image', index: 'image', template: "text" , hidden: true,width:380 }
            , {name: 'price', index: 'price', template: "text" ,width:170 }
        ]
        , pager: '#pagination'
        , rowNum: 10
        , filterbar: true
        , altRows: false
        , multiselect: true
        , autowidth: false
        , height: 'auto'
        // , shrinkToFit: true
        , sortname: 'lastModifiedDate'
        , sortorder: 'desc'
        , batchDelBtn: '.operateBar .operate-delete'
    });


    layer.open({
        type: 1
        ,title: '添加主要商品'
        ,area: ['760px', '500px']
        ,skin: 'layer-ext-alertpop'
        ,content: $('#primaryProductDlg') //这里content是一个普通的String或html标签
        ,closeBtn:2
        ,btn: ['确定', '取消']
        ,btn1:function(){
            var keys = $grid1.jqGrid('getGridParam', 'selarrrow');
            if(keys.length<1){
                layer.alert('至少选择一条数据！');
            }else if(keys.length>1){
                layer.alert('最多选择一条数据');
            }else{
                for (i = 0; i < keys.length; i++) {
                    var data = $grid1.jqGrid('getRowData', keys[i]);
                    var html = '<div class="suits">'
                        +'<ul>'
                        +'<li>'
                        +'<div class="p-img">'
                        +'<input type="hidden" name="composes[].accessories[].productId" value="">'
                        +'<input type="hidden" name="composes[].accessories[].goodsId" value="">'
                        +'<a href="javascript:void(0)" title="'+data.title+'">'
                        +'<img src="'+ctx+'/'+data.image+'" width="99" height="99">'
                        +'</a>'
                        +'</div>'
                        +'<div class="p-name">'
                        +'<a href="javascript:void(0)" title="'+data.title+'">'
                        +data.title
                        +'</a>'
                        +'</div>'
                        +'</li>'
                        +'</ul>'
                        +'</div>';
                    $('#primaryProduct').html(html);
                    $('#pryProductId').val(data.productId);
                    $('#pryGoodsId').val(data.id);

                    $('#acce-list').html('');
                    $('#packageDetail').html('');
                    $('#goodsIds').val('');
                    $('#productIds').val('');
                }
                var index = layer.open();
                layer.close(index)

            }

        },btn2:function(){
        }
    });

});

$('#btn-packages-sel').click(function(){
    if($('#primaryProduct').html()==''){
//			layer.alert('请先选择主产品！');
        layer.msg('请先选择主产品！');
        return false;
    }
    layer.open({
        type:1
        ,title:'配件详情'
        ,skin: 'layer-ext-alertpop'
        ,area:['880px', '650px']
        ,content:$('#addPackagesDlg')
        ,btn: ['确定', '取消']
        ,yes:function(index,layero){
            if(!$('#combine-Form').valid()){
                return false;
            }
            if($('#packageDetail').html()==''){
                layer.msg("请选择商品！");
                return false;
            }

            var productDetail = $('#packageDetail').html();
            var combineName = $('#combineName').val();

            var content =
                '<tbody>'
                +'<tr class="packagesTr">'
                +'<td>'
                +'<div class="em_list" data-composes="productId">'
                +'<h2 class="clearfix">'
                +'<span class="fl">配件名称：<b>'+combineName+'</b></span>'
                +'<span class="fr">'
                +'<input type="hidden" id="id" value="19">'
                +'<a href="javascript:void(0);" class="editCombineD">编辑</a>'
                +'<a href="javascript:void(0);" class="deleteCombineD">移除</a>'
                +'</span>'
                +'</h2>'
                +'<div class="suits suits_2">'
                +productDetail
                +'</div>'
                +'</div>'
                +'</td>'
                +'</tr>'
                +'</tbody>';
            $('#acce-list').html(content);
            layer.close(index);


        }
    });
});

$('#selectScdGoods').click(function(){
    var pryGoodsId = $('#pryGoodsId').val();
    var $grid = $('#goodsGrid').jqGrid({
        url: ctx+'/admin/activity/get-goodses?search_id_ne_L='+pryGoodsId
        , datatype: 'json'
        , postData: {
            pryGoodsId : pryGoodsId
        }
        , colNames: [
            t('ID'), t('商品名称'), t('商品分类'), t('商品图片'), t('商品价格')
        ]
        , colModel: [
            {name: 'id', index: 'id', template: "text",hidden:true }
            , {name: 'title', index: 'title', template: "text",width:250 }
            , {name: 'product.category', index: 'pCName' ,template: "text", width:250 ,formatter: function (va) {return va.name;}}
            , {name: 'image', index: 'image', template: "text" , hidden: true,width:380 }
            , {name: 'price', index: 'price', template: "text" ,width:170 }
        ]
        , pager: '#goodsPagination'
        , rowNum: 10
        , filterbar: true
        , altRows: false
        , multiselect: true
        , autowidth: false
        , height: 'auto'
        // , shrinkToFit: true
        , sortname: 'lastModifiedDate'
        , sortorder: 'desc'
        , batchDelBtn: '.operateBar .operate-delete'
    });

    layer.open({
        type: 1
        ,title: '添加组合产品'
        ,skin: 'layer-ext-alertpop'
        ,area: ['760px', '500px']
        ,content: $('#goodsDlg') //这里content是一个普通的String或html标签
        ,closeBtn:2
        ,btn: ['确定', '取消']
        ,yes:function(index,layero){
            var keys = $grid.jqGrid('getGridParam', 'selarrrow');
            if(keys.length<1){
                layer.msg('至少选择一条数据！');
                return false
            }else{
                var content = '';
                var goodsIds = '';
                var productIds = '';
                var len = keys.length;
                content +='<div class="em_list">'
                    +'<div class="suits suits_2">'
                    +'<ul id="recoul" style="width: 290px;">'
                for (i = 0; i < keys.length; i++) {
                    var data = $grid.jqGrid('getRowData', keys[i]);
                    content += ' <li>';
                    if(i!=len-1){
                        content += '<s></s>';
                        goodsIds += data.id+',';
                        productIds += data.productId+',';
                    }else{
                        goodsIds +=data.id;
                        productIds += data.productId;
                    }
                    content += '<div class="p-img">'
                        +'<input type="hidden" name="composes[].accessories[].productId" value="'+data.productId+'">'
                        +'<input type="hidden" name="composes[].accessories[].goodsId" value="'+data.id+'">'
                        +'<a href="javascript:void(0)" title="'+data.title+'">'
                        +'<img src="'+ctx+'/'+data.image+'" width="99" height="99">'
                        +'</a>'
                        +'</div>'
                        +'<div class="p-name">'
                        +'<a href="javascript:void(0)" title="'+data.title+'">'
                        +data.title
                        +'</a>'
                        +'</div>'
                        +'</li>';
                }
                content += '</ul>'
                    +'</div>'
                    +'</div>';
                $('#goodsIds').val(goodsIds);
                $('#productIds').val(productIds);
                $('#packageDetail').html(content);
                layer.close(index);

            }

        },
        btn2:function(){
        }
    })
})

$('#btn_sure').click(function(){
    var id = $('#combine_id').val();
    var ids = $('#ids').val();
    var combineName = $('#combineName').val();
    var goodsIds = $('#goodsIds').val();
    var productIds = $('#productIds').val();
    var pryProductId = $('#pryProductId').val();
    var pryGoodsId = $('#pryGoodsId').val();
    if(pryGoodsId==''){
        layer.msg('请先选择主产品！');
        return false;
    }
    if(goodsIds==''){
        layer.msg('请添加组合产品');
        return false;
    }
    if(id!=''&&ids!=''){
        $.ajax({
            url:ctx+'/admin/accessoy/savePackages',
            type:'post',
            async :false,
            traditional: true,
            data:{
                id:id,
                name : combineName,
                'primaryProduct.id' : pryProductId,
                'primaryGoods.id' : pryGoodsId,
                goodsIds : goodsIds,
                productIds : productIds
            },
            success : function(data){
                location.href = ctx+'/admin/accessoy/index';
            },
            error: function(data){
                layer.msg('操作失败！');
                return false;
            }
        })
    }else{
        $.ajax({
            url:ctx+'/admin/accessoy/savePackages',
            type:'post',
            async :false,
            traditional: true,
            data:{
                name : combineName,
                'primaryProduct.id' : pryProductId,
                'primaryGoods.id' : pryGoodsId,
                goodsIds : goodsIds,
                productIds : productIds
            },
            success : function(data){
                location.href = ctx+'/admin/accessoy/index';
            },
            error: function(data){
                layer.msg('操作失败！');
                return false;
            }
        })
    }

});

$(function(){
    $('#acce-list').on('click','.editCombineD',function(){

        if($('#primaryProduct').html()==''){
            layer.msg('请先选择主产品!');
//				layer.alert('请先选择主产品！');
            return false;
        }
        layer.open({
            type:1
            ,title:'配件详情'
            ,area:['880px', '600px']
            ,content:$('#addPackagesDlg')
            ,btn: ['确定', '取消']
            ,btn1:function(index,layero){


                $('#combine-Form').valid()

                var productDetail = $('#packageDetail').html();
                var combineName = $('#combineName').val();
                var content =
                    '<tbody>'
                    +'<tr class="packagesTr">'
                    +'<td>'
                    +'<div class="em_list" data-composes="productId">'
                    +'<h2 class="clearfix">'
                    +'<span class="fl">配件名称：<b>'+combineName+'</b></span>'
                    +'<span class="fr">'
                    +'<input type="hidden" id="id" value="19">'
                    +'<a href="javascript:void(0);" class="editCombineD">编辑</a>'
                    +'<a href="javascript:void(0);" class="deleteCombineD">移除</a>'
                    +'</span>'
                    +'</h2>'
                    +'<div class="suits suits_2">'
                    +productDetail
                    +'</div>'
                    +'</div>'
                    +'</td>'
                    +'</tr>'
                    +'</tbody>';
                $('#acce-list').html(content);
            }
        });
    })
});


$('#acce-list').on('click','.deleteCombineD',function(){
    $('#acce-list').html('');
    $('#packageDetail').html('');
    $('#goodsIds').val('');
    $('#productIds').val('');
});