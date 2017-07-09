var $g = $('#datagrid').jqGrid({
    url: 'list',
    datatype: 'json',
    colNames: [ 'id','商品名称','商品编号','分类','品牌','成本价','库存','商品状态','所属产品','创建时间'],
    colModel: [
        { name: 'id', index: 'id', hidden: true, key: true ,align: 'center'},
        { name: 'title', index: 'title', template: 'text', align: 'center',width:220},
        { name: 'sn',index:'sn', template:'text',align: 'center',width:220},
        { name: 'product.category', index: 'PCName', template: 'text', align: 'center',width:220,formatter:function(v){
            return v.name;
        }},
        { name: 'product.brand', index: 'PBName', template: 'text', align: 'center',width:230,formatter:function(v){
            return v.name;
        }},
        { name: 'price', index: 'price', template: 'text', align: 'center',width:230},
        { name: 'stock', index: 'stock', template: 'text', align: 'center',width:230,formatter:function(v){
            return "<span style='color:red'>"+v+"</span>"
        }},
        { name: 'product.isPutaway',searchoptions: {sopt: ["eq"]},  index: 'PIsPutaway', template: 'text', align: 'center',width:230,formatter: function (va) {
            debugger;
            if (va) return "已上架"; else return "未上架";
        }, stype: 'select', searchoptions: {value: ':全部;0:未上架;1:上架'}},
        { name: 'product.title', index: 'PTitle', template: 'text', align: 'center',width:230},
        { name: 'createdDate', index: 'createdDate', template: 'date', align: 'center',width:230,search:false},
    ],
    autowidth: true,
    shrinkToFit: true,
    pager: '#pagination',
    sortname: 'createdDate',
    height: 'auto',
    sortorder: 'desc'
});
