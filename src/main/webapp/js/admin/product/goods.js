$(function(){
    jQuery.validator.addMethod("decimalValidate", function(value, element) {
        var tel = /^(([1-9]+\d*)|([0-9]+\d*\.\d{1,2}))$/;
        return this.optional(element) || (tel.test(value));
    }, "请填写大于0的小数并可以保留两位小数");
    jQuery.validator.addMethod("intValidate", function(value, element) {
        var tel = /^(([1-9]+\d*))$/;
        return this.optional(element) || (tel.test(value));
    }, "请填写整数大于0的整数");
})
var Goods = (function ($, Mustache) {
    var contextPath = Besture.contextPath || '',
        CATEGORY_TREE_URL = contextPath + '/admin/category/tree',
        MODEL_DETAIL_URL = contextPath + '/admin/model/detail/',
        M_TPL = '{{#.}}<li><a href="javascript:void(0);" data-id="{{id}}">{{name}}</a></li>{{/.}}',
        BASE_RULES = {
            title: {
                required: true,
                minlength: 1,
                maxlength: 250
            },
            sn: {
                required: true,
                minlength: 2,
                maxlength: 14
            },
            weight: {
                required: true,
                number: true,
                min: 0,
                max: 100000
            },
            areas:{
                required: true,
            },
            stock:{
                required: true
            },
            alertStock:{
                required: true
            },
            price:{
                required: true,
                decimalValidate:true
            },
            tip:{
                required: false,
                maxlength:200
            },
            service:{
                required: false,
                maxlength:200
            },
            promotionalInfo:{
                required: false,
                maxlength:200
            }
        };

    var Goods = function () {
        var me = this;

        me.specs = window['currentSpecs'] || [];
        me.ignoreSpecIds = window['currentIgnoreSpecIds'] || [];  // 不生成货号的id
        me.$attrsList = $('#attr-list');
        me.$paramsList = $('#param-list');
        me.$specList = $('#spec-list');
        me.$productSpecs = $('#product-specs');
        me.$goodsList = $('#goods-list');
        me.$accessoryList = $('#acce-list');
        me.$enableSpecBtn = $('#btn-enable-spec');

        me.attrsTpl = $('#tpl-attrs').html();
        me.paramsTpl = $('#tpl-params').html();
        me.specsTpl = $('#tpl-specs').html();
        me.specsImgTpl = $('#tpl-specs-img').html();
        me.goodsTpl = $('#tpl-goods').html();

        Mustache.parse(me.attrsTpl);
        Mustache.parse(me.paramsTpl);
        Mustache.parse(me.specsTpl);
        Mustache.parse(me.specsImgTpl);
        Mustache.parse(me.goodsTpl);



        /*--选择分类--*/
        $('#btn-cat-sel').click(function () {
            var sel= null;
            var setting = {
                treeId : "cats-tree",
                data : {
                    simpleData : {
                        enable : true,
                        idKey : "id",
                        pidKey : "pId",
                        rootPId : null
                    }
                },
                callback: {
                    beforeClick: beforeClick
                }
            };
            function beforeClick(treeId, treeNode, clickFlag) {
                $(".categoryName").val(treeNode.name);
                $(".categoryId").val(treeNode.id);
                $(".categoryId").valid();
                $("input[name='model.id']").val(treeNode.modelId);
                $("input[name='model.id']").valid();
                $("input[name='model.name']").val(treeNode.modelName);
                var $brands = $('select[name="brand.id"]').html(''),
                    brands = treeNode.brands || [],
                    i;
                $brands.append(new Option("", ""));
                for (i = 0; i < brands.length; i++) {
                    $brands.append(new Option(brands[i].name, brands[i].id));
                }
                $('.chosen-select').trigger("chosen:updated");

                me.onCategoryChange();
                sel=true;
            }
            $ztree=null;
            var treeNodes;
            $.ajax({
                async:true,//是否异步
                cache:false,//是否使用缓存
                type:'POST',//请求方式：post
                //data:{"roleId":null},
                dataType:'json',//数据传输格式：json
                url:contextPath+"/admin/category/tree",//请求的action路径
                error:function(){
                    alert('亲，请求失败！');
                },
                success:function(data){
                    treeNodes = data;
                    $.fn.zTree.init($("#cats-tree"), setting, treeNodes);
                    $ztree =$.fn.zTree.getZTreeObj("cats-tree");
                }
            });
            layer.open({
                zIndex: 10,
                title: '选择上级分类',
                type: 1,
                skin: 'layer-ext-alertpop', //加上边框
                area: ['600px', '400px'], //宽高
                shadeClose: true,
                content: $('#cate-sel-dlg'),
                btn: ['确定', '取消'],
                cancel: function (index) {
                    $ztree.destroy();
                    layer.close(index);
                },
                yes: function (index) {
                    var selectedNode = $ztree.getSelectedNodes();
                    if(selectedNode && selectedNode.length!=0){
                        $ztree.destroy();
                        layer.close(index);
                    }else{
                        Glanway.Messager.alert("提示", "请选择一个父级分类");
                    }
                }
            });
        });
        /*--选择分类--*/

        /*--选择触发validate验证--*/
        $(".chosen-select").on('change', function(evt, params) {
            $(this).valid();
        });

        /*--购买时间段--*/
        $("#buy-time").click(function(){
            var isChecked = $(this).attr("checked");
            if(isChecked){
                $("input[name='startTime'],input[name='endTime'],input[name='safePrice']").css({"background-color": "#fff"}).attr("disabled",false);
            }else{
                $("input[name='startTime'],input[name='endTime'],input[name='safePrice']").val("").css({"background-color": "#eee"}).attr("disabled",true);
            }

        });
        /*--购买时间段--*/

        /*--点击上面li 切换--*/
        $(".tab li").each(function(i){
            $(this).click(function(){
                me.showTab(i);
            })
        });
        /*--点击上面li 切换--*/

       me.initEvent();
        me.rebindRemoveEvent();
    };

    Goods.prototype = {
        showTab: function (index) {
            var validator = $('form:last').validate();

            if (validator.checkForm()) {

            } else {
                validator.showErrors();
                validator.focusInvalid();
                return;
            }
            $('.tab-wrap ul.tab li').eq(index).addClass("on").siblings().removeClass("on");
            $('.tab-ct-wrap .tab1').eq(index).show().siblings().hide();
        },

        rebindRemoveEvent: function () {
            var me = this;

            // 删除按钮事件
            me.$accessoryList.find('div > h2 > span > a').each(function (i, el) {
                $(el).off('click').on('click', function () {
                    $(this).closest('div').remove();
                });
            });
        },

        bindImage: function () {
            var me = this,
                $images = $('#pro-img .upload-list input[name$=path]');
            var $goods = $('#goods-list ul');
            for (var i = 0; i < $goods.length; i++) {
                var $gImgs = $($goods[i]).find('li');
                for (var j = 0; j < $images.length; j++) {
                    var $li = $gImgs[j];
                    $('<img />').attr({'width': '100%', 'height': '100%', 'src': contextPath + '/' + $($images[j]).val()}).appendTo($li);
                    $('<input/>').attr({'type': 'hidden', 'name': 'goods[' + i + '].productImgs[' + j + '].path'}).val( $($images[j]).val()).appendTo($li);
                    $('<input/>').attr({'type': 'hidden', 'name': 'goods[' + i + '].productImgs[' + j + '].sort'}).val(j).appendTo($li);
                }
            }
        },

        initEvent: function () {
            var me = this,
                $tab = $('.tab-ct-wrap'),
                $basicTab = $tab.find('.tab1:nth-child(1)'),
                $specTab = $tab.find('.tab1:nth-child(2)'),
                $detailTab = $tab.find('.tab1:nth-child(3)'),
                $acceTab = $tab.find('.tab1:nth-child(4)');

            // me.$enableSpecBtn.closest('tr').hide();
            me.showSpecTab(me.$enableSpecBtn.attr('checked'));
            me.$enableSpecBtn.change(function () {
                var enableSpec = $(this).attr("checked");
                $("#btn-enable-spec").attr("isFlag","false");
                if(enableSpec){
                    $("#stock").rules("remove","required");
                    $("#alertStock").rules("remove","required");
                    $("#price").rules("remove","required decimalValidate");
                    $(".single-product").hide();
                }else{
                    $(".single-product").show();
                    $("#stock").rules("add",{required: true,maxlength:4});
                    $("#alertStock").rules("add",{required: true,maxlength:4});
                    $("#price").rules("add",{required: true,decimalValidate:true});

                }
                me.showSpecTab(this.checked);
                me.cleanGoods();
            });

            // 基本信息验证
            $('form:last').validate({
                rules: BASE_RULES,
                errorPlacement: function($error, $el) {
                    var $label = $el.siblings('label.error');
                    if ($label.length < 1) {
                        $label = $el.closest('ul').siblings('.group-error');
                    }

                    if ($label.length > 0) {
                        $label.text($error.text());
                    } else {
                        $el.after($error);
                    }
//                    console.log(error, element);
                },
                submitHandler:function(form){
                        $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                        form.submit();
                }
            });

            // basic info tab --> 规格/详细信息
            $basicTab.find('[data-act=next]').click(function () {
                var validator = $('form:last').validate(),
                    enableSpec = me.$enableSpecBtn.attr('checked');
                if (validator.checkForm()) {
                    var next = enableSpec ? 1 : 2;
                    me.showTab(next);

                    // 追加验证规则
                    // $.extend(true, validator.settings.rules, {});
                } else {
                    validator.showErrors();
                    validator.focusInvalid();
                }
                return false;
            });

            // 规格 --> 详细信息
            $specTab.find('[data-act=next]').click(function () {
                var validator = $('form:last').validate();

                if (validator.checkForm()) {
                    me.showTab(2);

                    // $.extend(true, validator.settings.rules, { });
                } else {
                    validator.showErrors();
                    validator.focusInvalid();
                }
                return false;
            });

            // 规格 --> 基本信息
            $specTab.find('[data-act=prev]').click(function () {
//				var validator = $('form:last').validate();
//				validator.settings.rules = BASE_RULES;
                me.showTab(0);
            });

            $detailTab.find('[data-act=next]').click($.proxy(me.showTab, me, 3));
            $detailTab.find('[data-act=prev]').click(function () {
                var enableSpec = me.$enableSpecBtn.attr('checked'),
                    prev = enableSpec ? 1 : 0;
                me.showTab(prev);
            });

            $acceTab.find('[data-act=prev]').click($.proxy(me.showTab, me, 2));



            function renderTo(url, data, tpl, $container) {
                $.ajax({
                    url: url,
                    data: data,
                    dataType: 'json',
                    success: function (json) {
                        var filtered = [];
                        $.each(json || [], function (i, m) {
                            var has = false;
                            $.each($selectedModelContainer.find('a[data-id]'), function (i, item) {
                                var selected = $(item).data('id');
                                if (m.id == selected) {
                                    has = true;
                                    return false;
                                }
                            });
                            !has && (filtered.push(m));
                        });
                        var html = Mustache.render(tpl, filtered);
                        $container.html(html);
                    }
                })
            }


        },

        onCategoryChange: function () {
            var me = this,
                currMid = $('[name="model.id"]').val();

            $.getJSON(MODEL_DETAIL_URL + currMid, function (json) {
                var model = json.data;

                me.$attrsList.html('');
                me.$paramsList.html('');
                me.$specList.html('');
                me.$productSpecs.html('');
                me.$goodsList.html('');

                if (model.modelSpecs && model.modelSpecs.length > 0) {
                    $('input[name=enableSpecs]').closest('tr').show();
                } else {
                    $('input[name=enableSpecs]').closest('tr').hide();
                }

                if (model.attributes && model.attributes.length > 0) {

                    $.each(model.attributes, function (i, attr) {
                        var type = attr['displayType'];
                        attr.textMode = (0 != type && 2 != type)?false:true;
                        attr.index = $('#attr-list').data('index') + i;
                    });
                    me.$attrsList.html(Mustache.render(me.attrsTpl, model));
                }

                if (model.parameters && model.parameters.length > 0) {
                    me.$paramsList.html(Mustache.render(me.paramsTpl, model));
                }
                me.$paramsList.find('tr').each(function (i, tr) {
                    $(tr).find('input[name]:enabled').each(function (j, el) {
                        $(el).attr('name', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
                    })
                });

                me.specs = [];
                $.each(model.modelSpecs || [], function (i, el) {
                    el.spec.specId = el.spec.id; // for tpl
                    me.specs.push(el.spec);
                    // 影响货号，暂时是使用这个
                    if (!el['isAffectGoods']) {
                        me.ignoreSpecIds.push(el.spec.id);
                    }
                });

                if (me.specs.length > 0) {
                    me.$enableSpecBtn.closest('tr').show();
                    me.$specList.html(Mustache.render(me.specsTpl, me.specs));
                    parseSelectAll();
                } else {
                    me.$enableSpecBtn.closest('tr').hide();
                }
            });
        },

        showSpecTab: function (show) {
            var $tab = $('[data-tab=spec]');
            show ? ($tab.show()) : ($tab.hide());
        },

        getSpecValue: function (specId, specValueId) {
            var me = this, ret;
            $.each(me.specs, function (i, spec) {
                if (specId == spec.id) {
                    $.each(spec.specValues, function (i, value) {
                        if (specValueId == value.id) {
                            ret = value;
                            value.spec = spec;
                            return false;
                        }
                    });
                    return false;
                }
            });
            return ret;
        },
        /**
         * 规格加入图片
         * -- 放弃
         */
        /*specImage: function() {
         var me = this;

         // 如果没有传递则获取选中的规格
         var specValues = this.getSelectedSpecValues(), specs = [];
         for (var i = 0; i < specValues.length; i++) {
         for (var j = 0; j < specValues[i].length; j++) {
         specs.push({
         specValues: specValues[i][j],
         index: i + "" + j
         });
         }
         }
         me.$productSpecs.html(Mustache.render(me.specsImgTpl, specs));
         parseUpload();
         },*/
        /**
         * 生成所有货品
         */
        generateGoods: function (goods) {
            var me = this;
            // 是否是不影响货品编号的规格
            function isIgnoreSpec(id) {
                for (var i = 0; i < me.ignoreSpecIds.length; i++) {
                    var ignore = me.ignoreSpecIds[i];
                    if (id == ignore) {
                        return true;
                    }
                }
                return false;
            }

            // 如果没有传递则获取选中的规格
            if (undefined == goods) {
                var specValues = this.getSelectedSpecValues(),
                    i, j, k;

                goods = [];

                // 获取所有选中的规格值
                if (specValues.length > 0) {
                    // 计算所有的规格值组合类型
                    specValues = this.cartesianProduct.apply(this, specValues) || [];
                    // 遍历所有组合
                    for (i = 0; i < specValues.length; i++) {
                        var code = ($('[name=sn]').val() || ''),
                            sorted = [];

                        // 遍历每个规格组合中的规格值
                        for (j = 0; j < specValues[i].length; j++) {
                            var value = specValues[i][j];   // 规格值
                            value.index = j;     // for template

                            // 按照规格排序
                            for (k = sorted.length - 1; k > -1; k--) {
                                if (value.spec.sort >= sorted[k].spec.sort) {
                                    sorted.splice(k - 1, k -1, value);
                                    break;
                                }
                            }
                            if (k < 0) {
                                sorted.unshift(value);
                            }
                        }

                        // 按照顺序生成 code (product 8位 sn + 8位 规格值 code)
                        /*
                        for (k = 0; k < 4; k++) {
                            if (!sorted[k] || isIgnoreSpec(sorted[k].spec.id)) {
                                sorted[k] = {};
                            }
                            code += sorted[k].code || '';
                        }
                        */
                        code = Number(code) + Number($.map(sorted, function(item) { return item.code || '' }).join(''));
                        var images= new Array();
                            $('#pro-img .upload-list>li').each(function(i,el){
                                var path = $(this).find("input[name$=path]").val()
                                if(path==undefined){
                                    path="";
                                }
                                debugger;
                               images[i]={path:path};
                            });
                        goods.push({
                            title: $('[name=title]').val(),
                            specValues: specValues[i],
                            sn: $("input[name='sn']").val(),
                            stock: $('[name="stock"]').val(),
                            alertStock: $('input[name="alertStock"]').val(),
                            hopeCostPrice: $('input[name="price"]').val(),
                            //stock: $('[name=stock]').val(),  //
                            img:images,
                            goodsIndex: i       // for template
                        });
                    }
                }
            } else {
                $.each(goods, function (i, el) {
                    el.index = i;
                });
            }

            me.cleanGoods();
            if (goods.length > 0) {
                me.$goodsList.html(Mustache.render(me.goodsTpl, goods));
                var images= new Array();
                $('#pro-img .upload-list>li').each(function(i,el){
                    var path = $(this).find("input[name$=path]").val()
                    if(path){
                        $("#goods-list .upload-list").each(function(p,m){
                            $(m).children("li").each(function( j,me){
                                if(j==i){
                                    $(me).append('<div data-saved-url="'+contextPath+'/'+path+'">'+
                                        '<input type="hidden" name="goods['+p+'].productImgs['+j+'].path" value="'+path+'">'+
                                        '</div>');
                                }
                            })
                        })
                    }
                });
                debugger;
                $('#goods-list').find('li').each(function(index, el) {
                        var id= el.getAttribute("id");
                        var j =id.split("-")[0].split("goodsList")[1];
                        var k =id.split("-")[1].split("img")[1];
                        if (!el.uploader) {
                            el.uploader = Uploader2({
                                url: contextPath + '/storage/images/preupload',
                                policy: true,
                                list: id,
                                browse_button: id,
                                download:false,
                                name: 'goods['+j+'].productImgs['+k+'].path',
                                mode: 't',
                                max_file_count: 1,
                                max_file_size: '20m',
                                filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]}
                            });
                        }
                });
            }
            /*parseUpload();*/
            $('.spinner').spinner({
                max: 9999
                , min: 0
                , step: 1
                , allowEmpty: true
                , minusBtn: '.btn-down'
                , plusBtn: '.btn-up'
            });
            // 绑定商品主图
            me.bindImage();
           /* $.each($("input[name*='discount']"),function(i,e){
                $(e).rules("add", { required:true, discountValidate :true ,
                    min:0.0001,
                    max:1,});
            });*/
        },

        /**
         * 清除所有货品
         */
        cleanGoods: function () {
            this.$goodsList.html('');
        },

        /**
         * 获取所有选择的规格值
         */
        getSelectedSpecValues: function () {
            var me = this,
                specs = me.specs,
                specValues = [],
                values = [],
                i;
            for (i = 0; i < specs.length; i++) {
                values = [];
                $('[name=' + specs[i].id + ']:checked').each(function (i, el) {
                    values.push(me.getSpecValue(el.name, el.value));
                });

                if (values.length > 0) {
                    specValues.push(values);
                }
            }

            return specValues;
        },
        /**
         * 获取规格值的图片
         * -- 放弃
         */
        /*getSpecValueImage: function () {
         $('.g-spec').each(function() {
         var id = $(this).siblings('input[type=hidden]').val();
         var file = $('#'+id).find('input[type=file]').val();
         console.log(id);
         console.log(file);
         });
         },*/

// 计算的笛卡尔积
        cartesianProduct: function () {

            function addTo(curr, args) {
                var i,
                    copy,
                    rest = args.slice(1),
                    last = !rest.length,
                    result = [];

                for (i = 0; i < args[0].length; i++) {
                    copy = curr.slice();
                    copy.push(args[0][i]);

                    if (last) {
                        result.push(copy);
                    } else {
                        result = result.concat(addTo(copy, rest));
                    }
                }

                return result;
            }

            return addTo([], Array.prototype.slice.call(arguments));
        }
    }
    ;

    return window.__g = new Goods();
})(jQuery, Mustache);