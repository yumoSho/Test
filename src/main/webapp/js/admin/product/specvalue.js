$(function(){
    $.validator.addMethod("repeatSpecValValidate", function(value, element) {
        var $specVal = $(".specVal");
        var specVal =[];
        if($specVal && $specVal.length!=0){
            $.each($specVal,function(){
                specVal.push($(this).val());
            });
        }
        var index=specVal.indexOf(value);
        var last = specVal.lastIndexOf(value);
        return this.optional(element) || (index==last);
    }, "规格值重复");
    $.validator.addMethod("repeatSpecCodeValidate", function(value, element) {
        var $specCode = $(".specCode");
        var specCode =[];
        if($specCode && $specCode.length!=0){
            $.each($specCode,function(){
                specCode.push($(this).val());
            });
        }
        var index=specCode.indexOf(value);
        var last = specCode.lastIndexOf(value);
        return this.optional(element) || (index==last);
    }, "规格编码重复");
});

(function ($, Mustache, exports) {
    var contextPath = (window.Besture || window).contextPath || '';

    function SpecValue() {
        var me = this;
        me.specValueTpl = $('#tpl-spec-value').html();
        me.$specValueList = $('#spec-value-list');

        Mustache.parse(me.specValueTpl);

        me.bindEvent();
    }

    SpecValue.prototype = {
        bindEvent: function () {
            var me = this;
            $('[name=displayType]').change(function () {
                //me.refreshSpecValueList();
            });

            $('#btn-add-spec-value').click($.proxy(me, 'addSpecValue'));

            $('form:last').validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 2,
                        maxlength: 12,
                        remote: {
                            url: contextPath + '/admin/spec/check',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                id: function () {
                                    return $('[name=id]').val();
                                },
                                name: function () {
                                    return $('[name=name]').val();
                                }
                            }
                        }
                    },
                    alias: {
                        maxlength: 12
                    },
                    sort: {
                        required: true,
                        digits: true,
                        max: 999
                    }
                },
                submitHandler:function(form){
                    var tr = $("#spec-value-list").children("tr").length;
                    if (tr < 1) {
                        Glanway.Messager.alert("提示", "请填写规格值")
                        return false;
                    }
                    form.submit();
                    $(form).find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                    $("#subtn").attr('disabled',true);
                }
            });
        },
        addSpecValue: function () {
            var me = this;

            me.$specValueList.append(Mustache.render(me.specValueTpl))
            //
            me.$specValueList.find('tr').each(function (i, tr) {
                $(tr).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (j, input) {
                    var $input = $(input);
                    $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + i + ']'));
                });
            });

            me.rebindEvent();

            // me.refreshSpecValueList();
        },
        refreshSpecValueList: function () {
            var type = $('[name=displayType]:checked').val(),
                $tab = $('#spec-value-list').closest('table'),
                $tr = $tab.find('tr'),
                $th = $tr.find('th:eq(3)'),
                $td = $tr.find('td:eq(3)');

            // text type
            if (0 == type) {
                $th.hide();
                $td.hide();
                $td.find('input').val('');
            } else {
                $th.show();
                $td.show();
            }
        },
        rebindEvent: function () {
            $('#spec-value-list').find('tr').each(function(i, el) {
                var $tr = $(el),
                    $delBtn = $tr.find('.operateBox img:last'),
                    index = $tr.index();

                $delBtn.off('click').on('click', function() {
                    var id = $tr.find('[name="specValues[' + index + '].id"]').val();
                    if (!id) {
                        $tr.remove();
                        $("#spec-value-list").find('tr').each(function(j, deltr) {
                            $(deltr).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (m, input) {
                                var $input = $(input);
                                $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + j + ']'));
                                var $label = $input.next("label");
                                if($label && $label.length!=0){
                                    $input.next("label").attr('for', $input.next("label").attr('for').replace(/[[0-9]*]/, '[' + j + ']'));
                                }
                            });
                        });
                    } else {
                        Glanway.Messager.confirm("警告", "您确定要删除选择本行记录吗？", function (r){
                            if(r){
                                $.ajax({
                                    type: 'post',
                                    url: contextPath + '/admin/spec/deleteSpecValue/' + id,
                                    dataType: 'json',
                                    success: function(data) {
                                        if (data.success) {
                                            layer.alert(data.message, {
                                                skin: 'layer-ext-message' //样式类名
                                                ,closeBtn:1
                                                ,time:3000
                                                ,title:'提示 [3秒后消失]'
                                                ,shade: 0
                                                ,offset:'rb'
                                                ,btn:''
                                            });
                                            $tr.remove();
                                            $("#spec-value-list").find('tr').each(function(j, deltr) {
                                                $(deltr).find('input[name]:enabled,select[name]:enabled,textarea[name]:enabled').each(function (m, input) {
                                                    var $input = $(input);
                                                    $input.attr('name', $input.attr('name').replace(/[[0-9]*]/, '[' + j + ']'));
                                                    var $label = $input.next("label");
                                                    if($label && $label.length!=0){
                                                        $input.next("label").attr('for', $input.next("label").attr('for').replace(/[[0-9]*]/, '[' + j + ']'));
                                                    }
                                                });
                                            });
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
                                    error:function(data) {
                                        layer.alert('系统错误', {
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
                        });
                    }
                });
            });
            $('.spinner').spinner({
                max: 999
                , min: 0
                , step: 1
                , allowEmpty: true
                , minusBtn: '.btn-down'
                , plusBtn: '.btn-up'
            });
        }
    };

    exports.specvalue = new SpecValue();
    specvalue.rebindEvent();
})(jQuery, Mustache, window);