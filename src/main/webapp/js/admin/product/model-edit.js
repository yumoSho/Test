$(function(){
        $.validator.addMethod("repeatParamValidate", function(value, element) {
        var $groupPara = $(".groupPara");
        var groupParaVal =[];
        if($groupPara && $groupPara.length!=0){
            $.each($groupPara,function(){
                groupParaVal.push($(this).val());
            });
        }
        var index=groupParaVal.indexOf(value);
        var last = groupParaVal.lastIndexOf(value);
        return this.optional(element) || (index==last);
    }, "参数名称重复");

    $.validator.addMethod("repeatParamChildValidate", function(value, element) {
        var $tobdy =$(element).parents("tbody");
        var $childPara =$tobdy.find(".childPara");
        var childParaVal =[];
        if($childPara && $childPara.length!=0){
            $.each($childPara,function(){
                childParaVal.push($(this).val());
            });
        }
        var index=childParaVal.indexOf(value);
        var last = childParaVal.lastIndexOf(value);
        return this.optional(element) || (index==last);
    }, "子参数名称重复");

});
var modelZc=(function($, Mustache, exports) {
    var contextPath = (window.Besture || window)['contextPath'] || '',
        CATEGORY_TREE_URL = contextPath + '/admin/category/tree',
        DEL_MODEL_SPEC_URL = contextPath + '/admin/model/delModelSpec',
        num=0;
    var ModE = function() {
        var me = this;

        me.$attrList = $('#attr-list');
        me.attrTpl = $('#tpl-attr').html();

        me.$specList = $('#spec-list');
        me.specTpl = $('#tpl-spec').html();

        me.$paramGroupList = $('#param-group-list');
        me.paramGroupTpl = $('#tpl-param-group').html();

        me.paramTpl = $('#tpl-param').html();

        me.$catsTree = $('#cats-tree');

        Mustache.parse(me.attrTpl);
        Mustache.parse(me.specTpl);
        Mustache.parse(me.paramGroupTpl);
        Mustache.parse(me.paramTpl);

        me.bindEvent();
        me.refresh();
    };

    ModE.prototype = {
        bindEvent: function() {
            var me = this;

            $('[name=useAttribute]').change($.proxy(me.refresh, me));
            $('[name=useSpec]').change($.proxy(me.refresh, me));
            $('[name=useParameter]').change($.proxy(me.refresh, me));

            $("#btn-add-attr").click($.proxy(me.addAttr, me));
            $('#btn-add-spec').click(function() {
                layer.open({
                    zIndex: 10,
                    title: '添加规格值',
                    type: 1,
                    skin: 'layer-ext-alertpop', //加上边框
                    area: ['600px', '400px'], //宽高
                    shadeClose: true,
                    content: $('#spec-sel-dlg'),
                    btn: ['确定', '取消'],
                    cancel: function (index) {
                        layer.close(index);
                        //$dlg.remove();
                    },
                    yes: function (index) {
                        var keys = $g.jqGrid('getGridParam', 'selarrrow'), i;
                        for (i = 0; i < keys.length; i++) {
                            var row = $g.jqGrid('getRowData', keys[i]);
                            me.addSpec(i, row);
                        }
                        layer.close(index);
                    }
                });
                var $g = $('#spec-grid').jqGrid({
                    url: contextPath+ '/admin/spec/list',
                    datatype: 'json',
                    colNames: ['ID', '规格名称', '规格别名', '排序'],
                    colModel: [
                        {name: 'id', index: 'id', hidden: true, key: true, align: 'center'},
                        {name: 'name', index: 'name', template: 'text', align: 'center'},
                        {name: 'alias', index: 'alias', template: 'text', align: 'center'},
                        {name: 'sort', index: 'sort', template: 'text', align: 'center'}
                    ],
                    multiselect: true,
                    autowidth: true,
                    shrinkToFit: true,
                    height: '100%',
                    pager: '#spec-pagination',
                    sortname: 'lastModifiedDate',
                    sortorder: 'desc'
                });
            });
            $('#btn-add-param-group').click($.proxy(me.addParamGroup, me));
            me.rebindSpecRemoveEvent();
            me.rebindParamEvent();
        },

        rebindSpecRemoveEvent: function() {
            var me = this;
            me.$specList.find('tr').each(function(i, tr) {
                $(tr).find('img:last').off('click').on('click', function() {
                    me.removeSpec($(this).closest('tr'));
                });
            });
        },

        removeSpec: function($entry) {
            var id = $entry.data('id');
            Glanway.Messager.confirm("警告", "您确定要删除选择的行记录吗？",function(ret){
                if(ret){
                    if (!id) {
                        $entry.remove();
                    } else {
                        $.ajax({
                            url: DEL_MODEL_SPEC_URL,
                            data: {id: id},
                            dataType: 'json',
                            success: function(data) {
                                if (data.success) {
                                    $entry.remove();
                                } else {
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
                            },
                            error: function() {
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
                        });
                    }
                }})
        },

        onCatsSelected: function() {
            var me = this;
            var checkeds = me.$catsTree.tree('getChecked'),
                text = '',
                hiddens = [];

            $.each(checkeds, function(i, node) {
                if (i > 0) {
                    text += ',';
                }
                text += node.name || node.text;
                hiddens.push($('<input type="hidden" name="category.id">').val(node.id));
            });

            $('[name="category.id"]').remove();
            $('[name="category.name"]').val(text).after(hiddens);
        },

        refresh: function() {
            var me = this,
                enableAttr = +$('[name=useAttribute]:checked').val(),
                enableSpec = +$('[name=useSpec]:checked').val(),
                enableParam = +$('[name=useParameter]:checked').val(),
                $attrTab = $('.tab-wrap>.tab>li:eq(1)'),
                $specTab = $('.tab-wrap>.tab>li:eq(2)'),
                $paramTab = $('.tab-wrap>.tab>li:eq(3)');

            if (enableAttr) {
                $attrTab.show();
            } else {
                me.$attrList.html('');
                $attrTab.hide();
            }

            if (enableSpec) {
                $specTab.show();
            } else {
                me.$specList.html('');
                $specTab.hide();
            }

            if (enableParam) {
                $paramTab.show();
            } else {
                me.$paramGroupList.html('');
                $paramTab.hide();
            }

            var l=$(".editPage .tab li:visible:last");
            $(".js-next-step").show();
            $(".tab1").eq(l.data("value")).find(".js-next-step").hide();

            var f=$(".editPage .tab li:visible:first");
            $(".js-pre-step").show();
            $(".tab1").eq(f.data("value")).find(".js-pre-step").hide();
        },

        addAttr: function() {
            var me = this;
            me.$attrList.append(Mustache.render(me.attrTpl));
            // 修正索引
            me.$attrList.find('tr').each(function (i, tr) {
                $(tr).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (j, input) {
                    var $input = $(input);
                    $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                });
            });
        },
        addSpec: function(index, row) {
            var me = this;
            if (me.$specList.find('input[name$="spec.id"][value="'+row.id+'"]').length > 0) {
                return;
            }
            me.$specList.append(Mustache.render(me.specTpl, row));

            me.$specList.find('tr').each(function (i, tr) {
                $(tr).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (j, input) {
                    var $input = $(input);
                    $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                });
            });
            $('.spinner').spinner({
                max: 2147483647
                , min: 0
                , step: 1
                , allowEmpty: true
                , minusBtn: '.btn-down'
                , plusBtn: '.btn-up'
            });

            me.rebindSpecRemoveEvent();
        },
        addParamGroup: function() {
            var me = this,
                $group = $(Mustache.render(me.paramGroupTpl));

            me.$paramGroupList.append($group);
            me.rebindParamEvent();

            me.$paramGroupList.children('div').each(function (i, el) {
                $(el).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (j, input) {
                    var $input = $(input);
                    $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                });
            });
        },

        rebindParamEvent: function() {
            var me = this;
            // 参数组事件绑定
            me.$paramGroupList.find('thead').each(function(i, thead) {
                var $group = $(thead).closest('div');
                // 添加参数
                $(thead).find('tr button:first').off('click').on('click',function() {
                    var $paramList = $group.find('tbody'),
                        groupName = $group.find('input[name]:enabled:first').attr('name') || '',

                        groupName = groupName.substring(0, groupName.indexOf("."));

                    $paramList.append(Mustache.render(me.paramTpl));
                    $paramList.find('tr').each(function (i, tr) {
                        $(tr).find('td>input[name]:enabled,td>select[name]:enabled,td>textarea[name]:enabled').each(function (j, input) {
                            var $input = $(input);
                            var flag=$input.attr("name").indexOf("id")
                            if(flag<0){
                                $input.attr('name', groupName + '.children[' + i + '].name');
                            }
                        });
                    });
                    // 参数删除事件
                    $paramList.each(function(i, tbody) {
                        var $entryTr = $(tbody).find('tr');
                        $entryTr.each(function(n,childreDel){
                            var $childreWait = $(childreDel)
                            $childreWait.find('button:last').off('click').on('click', function() {
                                me.removeParam($childreWait);
                            });
                        })
                    });
                });
                // 删除参数组
                $(thead).find('tr button:last').off('click').on('click', function() {
                    me.removeParamGroup($group);
                });
            });


        },

        removeParamGroup: function($entry) {
            Glanway.Messager.confirm("警告", "您确定要删除选择的行记录吗？",function(ret){
                var referenced = $('input[name=referenced]').val();
                referenced = referenced === 'false' ? false : !!referenced;
                if(ret){
                    if (referenced) {
                        layer.alert('此记录有关联数据，无法执行删除操作', {
                            skin: 'layer-ext-message' //样式类名
                            ,closeBtn:1
                            ,time:3000
                            ,title:'错误 [3秒后消失]'
                            ,shade: 0
                            ,offset:'rb'
                            ,btn:''
                        });
                        return;
                    }
                    $entry.remove();
                    $('#param-group-list').children('div').each(function (i, el) {
                        $(el).find('input[name][parent="true"]').each(function (j, input) {
                            var $input = $(input);
                            $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                            var $label = $input.next("label")
                            if($label && $label.length!=0){
                                $input.next("label").attr('for', $input.next("label").attr('for').replace(/[[0-9]*]/, '[' + i + ']'));
                            }
                            $(el).find('input[name][children="true"]').each(function (m, inputChildre) {
                                var $inputChildre = $(inputChildre);
                                $inputChildre.attr('name',"parameters["+i+"].children["+m+"].name" );
                                var $childreLabel = $inputChildre.next("label")
                                if($childreLabel && $childreLabel.length!=0){
                                    $inputChildre.next("label").attr('for',"parameters["+i+"].children["+m+"].name" );
                                }
                            });
                        });
                    });
                }
            });
        },


        removeParam: function($entry) {
            Glanway.Messager.confirm("警告", "您确定要删除选择的行记录吗？",function(ret){
                if(ret){
                    $entry.remove();
                    $("#param-group-list .table-module01  thead").width();
                    $('#param-group-list').children('div').each(function (i, el) {
                        $(el).find('input[name][parent="true"]').each(function (j, input) {
                            var $input = $(input);
                            $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                            var $label = $input.next("label")
                            if($label && $label.length!=0){
                                $input.next("label").attr('for', $input.next("label").attr('for').replace(/[[0-9]*]/, '[' + i + ']'));
                            }
                            $(el).find('input[name][children="true"]').each(function (m, inputChildre) {
                                var $inputChildre = $(inputChildre);
                                $inputChildre.attr('name',"parameters["+i+"].children["+m+"].name" );
                                var $childreLabel = $inputChildre.next("label")
                                if($childreLabel && $childreLabel.length!=0){
                                    $inputChildre.next("label").attr('for',"parameters["+i+"].children["+m+"].name" );
                                }
                            });
                        });
                    });
                }
            });
        }
    };

    exports.modelEdit = new ModE();
    return exports.modelEdit;
})(jQuery, Mustache, window);
