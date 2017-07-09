var goodsTpl = $('#tpl-goods-item').html();
var $tbody=$("#goods-list");
var contextPath = (window.Besture || window)['contextPath'] || ''
Mustache.parse(goodsTpl);

$(".btn-cancel-wrap .btn-cancel").click(function () {
    window.location.href =contextPath+"/admin/newgoods/index";
});


var $g = $('#datagrid').jqGrid({
    url: contextPath+'/admin/newgoods/get-goodses',
    datatype: 'json',
    colNames: [ 'id','商品标题','商品图片','分类','价格'],
    colModel: [
        { name: 'id', index: 'id', template: 'text', key: true ,align: 'left',hidden: true},
        { name: 'title', index: 'title', template: 'text', align: 'left',searchoptions: {sopt: ["cn"]}},
        {name: 'image', index: '', template: 'img'  },
        { name: 'product.category', index: 'pCName', template: 'text', align: 'left',searchoptions: {sopt: ["cn"]},formatter:function(v){return v.name}},
        { name: 'price', index: 'price', template: 'text', align: 'left',searchoptions: {sopt: ["cn"]}}
    ],
    multiselect: true,
    autowidth: true,
    shrinkToFit:true,
    height: 'auto',
    pager: '#pagination',
    sortname: 'lastModifiedDate',
    sortorder: 'desc'
});

$('#add-goods').click(function () {
    layer.open({
        zIndex: 10,
        title: '选择商品',
        type: 1,
        skin: 'layer-ext-alertpop', //加上边框
        area: ['600px', '400px'], //宽高
        shadeClose: true,
        content: $('.sel-goods'),
        btn: ['确定', '取消'],
        cancel: function (index) {
            layer.close(index);
        },
        yes: function (index) {
            var keys = $g.jqGrid('getGridParam', 'selarrrow');
            $("#goods").html("");
            var id=$g.jqGrid('getGridParam','selarrrow');
            var rowData=new Array();
            for(var i=0;i<id.length;i++){
                row=$g.jqGrid('getRowData',id[i]);
                var flag=true;
                $("#goods-list input[name]").each(function(i,me){
                    $input =$(me);
                    if($input.val()==row.id){
                        flag =false;
                        return false;
                    }
                });
                if(!flag){
                }else{
                    row.catName = row['product.category'];
                    rowData.push(row);
                }
            }
            if( null != rowData){
                for(var i=0;i<rowData.length;i++){
                    $tbody.append(Mustache.render(goodsTpl,rowData[i]));
                }
            }
            layer.close(index);
        }
    });
});

function closeGoods(){
    var keys = $g.jqGrid('getGridParam', 'selarrrow');
    $("#goods").html("");
    var id=$g.jqGrid('getGridParam','selarrrow');
    var rowData=new Array();
    for(var i=0;i<id.length;i++){
        row=$g.jqGrid('getRowData',id[i]);
        var flag=true;
        $("#goods-list input[name]").each(function(i,me){
           $input =$(me);
            if($input.val()==row.id){
                Glanway.Messager.alert("提示", "出现重复商品："+row.title);
                flag=false;
                return false;
            }
        });
        if(!flag){
            return false;
        }
        row.catName = row['product.category.name'];
        rowData.push(row);
    }
    if( null != rowData){
        for(var i=0;i<rowData.length;i++){
            $tbody.append(Mustache.render(goodsTpl,rowData[i]));
        }
        $("#goods").show();
        $("#light").hide();
        $(".pop-mask").hide();
    }
}

function sub(){
    $("#goods-list tr").each(function (i, el) {
        $(el).find('input[name],select[name]').each(function(j, input) {
            var $input = $(input);
            $input.attr('name', $input.attr('name').replace(/(\[).*(?=])/, '$1' + i));
        });
    });
    validator = $('#cat-form').validate();
    if(validator.checkForm()){
        $('#cat-form').submit();
    }else{
        validator.showErrors();
        validator.focusInvalid();
    }
}

function removeTr(me){
    Glanway.Messager.confirm("警告", "您确定要删除此记录吗？", function (r) {
        if(r){
            $(me).parents("tr").remove();
        }
    });
}
