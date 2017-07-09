$(function(){
	$.validator.addMethod("repeatAttrValidate", function(value, element) {
		var $attr = $(".attr");
		var attrVal =[];
		if($attr && $attr.length!=0){
			$.each($attr,function(){
				attrVal.push($(this).val());
			});
		}
		var index=attrVal.indexOf(value);
		var last = attrVal.lastIndexOf(value);
		return this.optional(element) || (index==last);
	}, "属性名称重复");
});
(function ($, Mustache, exports) {
	var TYPE_SEL = '0',
		TYPE_TEXT = '1',
		TYPE_RANGE = '2',
		CONTEXT_PATH = (window.Besture || window)['contextPath'] || '',
		DEL_ATTR_URL = CONTEXT_PATH + '/admin/model/delAttr',
		DEL_ATTR_VAL_URL = CONTEXT_PATH + '/admin/model/delAttrVal';

	function Attributes() {
		var me = this;
		me.$attrList = $('#attr-list');
		me.attrItemTpl = $('#tpl-attr-item').html();
		me.selectTpl = $('#tpl-attr-val-sel').html();
		me.selectDlgTpl = $('#tpl-sel-dlg').html();
		me.selectDlgItemTpl = $('#tpl-sel-dlg-item').html();

		me.textTpl = $('#tpl-attr-val-text').html();
		me.rangeTpl = $('#tpl-attr-val-range').html();
		me.rangeDlgTpl = $('#tpl-range-dlg').html();
		me.rangeDlgItemTpl = $('#tpl-range-dlg-item').html();

		Mustache.parse(me.selectTpl);
		Mustache.parse(me.selectDlgTpl);
		Mustache.parse(me.selectDlgItemTpl);
		Mustache.parse(me.textTpl);
		Mustache.parse(me.rangeTpl);
		Mustache.parse(me.rangeDlgTpl);
		Mustache.parse(me.rangeDlgItemTpl);

		// me.initForm();
		me.rebindEvent();
	}

	$.extend(true, Attributes.prototype, {
		initForm: function() {
		},
		rebindEvent: function () {
			var me = this;

			$('#btn-add-attr').off('click').on('click', $.proxy(me.addAttr, me));
			me.$attrList.find('tr').each(function (i, attrEntryEl) {
				// 为类型选择绑定事件
				$(attrEntryEl).find('select:first').off('change')
					.on('change', $.proxy(me.onValTypeChange, me));

				// 为属性删除绑定事件
				$(attrEntryEl).find('img:last').off('click')
					.on('click', function() {
						me.removeAttr($(attrEntryEl))
					});

				// 为选择项编辑绑定事件
				$(attrEntryEl).find('[data-act=edit-s]').off('click')
					.on('click', $.proxy(me.editSelVal, me));

				// 为区间编辑绑定事件
				$(attrEntryEl).find('[data-act=edit-r]').off('click')
					.on('click', $.proxy(me.editRangeVal, me));
			});
			$('.spinner').spinner({
				max: 2147483647
				, min: 0
				, step: 1
				, allowEmpty: true
				, minusBtn: '.btn-down'
				, plusBtn: '.btn-up'
			});
		},

		/**
		 * 属性值类型发生改变
		 */
		onValTypeChange: function (event) {
			var me = this,
				target = event.target || event.srcElement,
				type = target.value,
				$valContainer = $(target).parent().next(),
				attrIndex = $(target).index();

			if (TYPE_SEL == type) {
				me.renderSelTo($valContainer, attrIndex);
			} else if (TYPE_TEXT == type) {
				me.renderTextTo($valContainer, attrIndex);
			} else if (TYPE_RANGE == type) {
				me.renderRangeTo($valContainer, attrIndex);
			}
			me.rebindEvent();
		},

		renderSelTo: function ($container, attrIndex) {
			var me = this,
				$html = $(Mustache.render(me.selectTpl)),
				$optContainer;

			$container.html('').append($html);
//			$optContainer = $container.find('select');
		},

		renderTextTo: function ($container, attrIndex) {
			var me = this,
				$html = $(Mustache.render(me.textTpl, { attrIndex: attrIndex || 0 }));

			$container.html('').append($html);
		},

		renderRangeTo: function ($container, attrIndex) {
			var me = this,
				$html = $(Mustache.render(me.rangeTpl));

			$container.html('').append($html);
		},

		/**
		 * 编辑选择项
		 * @param event
		 */
		editSelVal: function (event) {
			var me = this,
				target = event.target || event.srcElement,
				$opts = $(target).closest('td').find('option'),
				attrIndex = $(target).closest('tr').index(),
				$dlg;

			function fixIndex() {
				$dlg.find('tbody tr').each(function (i, el) {
					$(el).find('input[name]').each(function (j, input) {
						$(input).attr('name', $(input).attr('name').replace(/attributeValues[[0-9]*]/, 'attributeValues[' + i + ']'));
					});
				});
			}
			function binEvent() {
				// 绑定删除事件
				$dlg.find('img').off('click').on('click', function () {
					me.removeAttrVal($(this).closest('tr'));
					fixIndex();
				});
			}

			$dlg = $(Mustache.render(me.selectDlgTpl));

			$opts.each(function (i, opt) {
				$dlg.find('tbody').append($(Mustache.render(me.selectDlgItemTpl, {
					attrIndex: attrIndex,
					index: i,
					id: $(opt).data('id'),
					value: $(opt).val(),
					sort: $(opt).data('sort')
				})));
			});

			binEvent();
			// 绑定添加值
			$dlg.find('button').off('click').on('click', function () {
				var $tbody = $dlg.find('tbody'),
					length = $tbody.children('tr').length;
				$tbody.append($(Mustache.render(me.selectDlgItemTpl, {
					attrIndex: attrIndex,
					index: length
				})));
				fixIndex();
				binEvent();
			});

			$dlg.appendTo(document.body);
			layer.open({
				zIndex: 10,
				title: '编辑属性值',
				type: 1,
				skin: 'layer-ext-alertpop', //加上边框
				area: ['600px', '400px'], //宽高
				shadeClose: true,
				content: $dlg,
				btn: ['确定', '取消'],
				cancel: function (index) {
					layer.close(index);
					$dlg.remove();
				},
				yes: function (index) {
					var $td = $(target).closest('td'),
						$select = $td.find('select');

					$td.find('input:hidden').remove();
					$select.html('');
					var flag = true;
					$dlg.find('tbody tr').each(function (i, valEntryEl) {
						var $valEntry = $(valEntryEl),
							id = $valEntry.find('[name*=id]').val(),
							val = $valEntry.find('[name*=value]').val(),
							sort = $valEntry.find('[name*=sort]').val();
						if (val == "") {
							$valEntry.find('[name*=value]').next("span").css("display","block");
							flag = false;
							return
						} else {
							$valEntry.find('[name*=value]').next("span").hide();
							flag = true;
						}
						//if (sort == "") {
						if (!/^[0-9]+/.test(sort)) {
							$valEntry.find('[name*=sort]').next("span").css("display","block")
							flag = false;
							return
						} else {
							$valEntry.find('[name*=sort]').next("span").hide();
							flag = true;
						}
						$('<option>' + val + '</option>').attr('data-id', id)
							.attr('data-sort', sort)
							.val(val)
							.appendTo($select);
					});

					if (!flag) {
						return false;
					}
					// 不允许修改 type
					// $dlg.find('input').appendTo($td);
					$dlg.find('input').each(function (i, el) {
						$('<input type="hidden">').attr('name', el.name).val(el.value).appendTo($td);
					}).remove();

					layer.close(index);
					$dlg.remove();
				}
			});
		},
		// 编辑区间值
		editRangeVal: function (event) {
			var me = this,
				target = event.target || event.srcElement,
				$opts = $(target).closest('td').find('option'),
				attrIndex = $(target).closest('tr').index(),
				$dlg;

			function fixIndex() {
				$dlg.find('tbody tr').each(function (i, el) {
					$(el).find('input[name]').each(function (j, input) {
						$(input).attr('name', $(input).attr('name').replace(/attributeValues[[0-9]*]/, 'attributeValues[' + i + ']'));
					});
				});
			}

			function bindAttrRemoveEvent() {
				// 绑定删除事件
				$dlg.find('img').off('click').on('click', function () {
					me.removeAttrVal($(this).closest('tr'));
					fixIndex();
				});
			}

			$dlg = $(Mustache.render(me.rangeDlgTpl));

			$opts.each(function (i, opt) {
				var val = $(opt).val().split(',');
				$dlg.find('tbody').append($(Mustache.render(me.rangeDlgItemTpl, {
					attrIndex: attrIndex,
					index: i,
					id: $(opt).data('id'),
					firstValue: val[0],
					secondValue: val[1],
					sort: $(opt).data('sort')
				})));
			});

			bindAttrRemoveEvent();

			// 绑定添加值
			$dlg.find('button').off('click').on('click', function () {
				var $tbody = $dlg.find('tbody'),
					length = $tbody.children('tr').length;
				$tbody.append($(Mustache.render(me.rangeDlgItemTpl, {
					attrIndex: attrIndex,
					index: length
				})));
				fixIndex();
				bindAttrRemoveEvent();
			});

			$dlg.appendTo(document.body);
			layer.open({
				zIndex: 10,
				title: '编辑属性值',
				type: 1,
				skin: 'layer-ext-alertpop', //加上边框
				area: ['600px', '400px'], //宽高
				shadeClose: true,
				content: $dlg,
				btn: ['确定', '取消'],
				cancel: function (index) {
					layer.close(index);
					$dlg.remove();
				},
				yes: function (index) {
					var $td = $(target).closest('td'),
						$select = $td.find('select');

					$td.find('input:hidden').remove();
					$select.html('');
					var flag = true;
					$dlg.find('tbody tr').each(function (i, valEntryEl) {
						var $valEntry = $(valEntryEl),
							id = $valEntry.find('[name*=id]').val(),
							val = $valEntry.find('[name*=firstValue]').val(),

							val2 = $valEntry.find('[name*=secondValue]').val(),
							sort = $valEntry.find('[name*=sort]').val();
						if (val == "") {
							$valEntry.find('[name*=firstValue]').next("span").css("display","block")
							flag = false;
							return
						} else {
							$valEntry.find('[name*=firstValue]').next("span").hide();
							flag = true;
						}
						if (val2 == "") {
							$valEntry.find('[name*=secondValue]').next("span").css("display","block")
							flag = false;
							return
						} else {
							$valEntry.find('[name*=secondValue]').next("span").hide();
							flag = true;
						}
						if (!/^[0-9]+/.test(sort)) {
							$valEntry.find('[name*=sort]').next("span").css("display","block")
							flag = false;
							return
						} else {
							$valEntry.find('[name*=sort]').next("span").hide();
							flag = true;
						}
						$val = $valEntry.find('[name*=value][class*=value]');
						$val.val(val + "-" + val2);
						/*$valEntry.find('[name*=sort][class*=value]').val(val+"-"+val2);*/

						$('<option>' + val + '-' + val2 + '</option>').attr('data-id', id)
							.attr('data-sort', sort)
							.val(val + ',' + val2)
							.appendTo($select);
					});
					if (!flag) {
						return false;
					}

					// 不允许修改 type
					// $dlg.find('input').appendTo($td);
					$dlg.find('input').each(function (i, el) {
						$('<input type="hidden">').attr('name', el.name).val(el.value).appendTo($td);
					}).remove();

					layer.close(index);
					$dlg.remove();
				}
			});
		},

		/**
		 * 删除一个属性值
		 */
		removeAttrVal: function ($entry) {
			var id = $entry.find('input[name*=id]').val();
            Glanway.Messager.confirm("警告", "您确定要删除选择的行记录吗？",function(ret){
                if(ret){
                    if (!id) {
                        $entry.remove();
                    } else {
                        $.ajax({
                            url: DEL_ATTR_VAL_URL,
                            data: { id: id },
                            dataType: 'json',
                            success: function(data) {
                                if (data.success) {
                                    $entry.remove();
                                } else {
                                    Glanway.Messager.alert("提示", data.message);
                                }
                            },
                            error: function() {
								layer.alert('删除失败', {
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
                }
                })
		},

		/**
		 * 删除属性
		 */
		removeAttr: function($entry) {
			var me = this,
				id = $entry.find('input[name*=id]').val();
            Glanway.Messager.confirm("警告", "您确定要删除选择的行记录吗？",function(ret){
                if(ret){
                    if (id) {
                        $.ajax({
                            url: DEL_ATTR_URL,
                            data: { id: id },
                            dataType: 'json',
                            success: function(data) {
                                if (data.success) {
                                    $entry.remove();
                                    me.fixAttrIndex();
                                } else {
                                    Glanway.Messager.alert("提示", data.message);
                                }
                            },
                            error: function() {
								layer.alert('删除失败', {
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
                    } else {
                        $entry.remove();
                    }
                    me.fixAttrIndex();
                }
            });
		},

		addAttr: function () {
			var me = this;

			me.$attrList.append($(Mustache.render(me.attrItemTpl)));

			me.rebindEvent();
			me.fixAttrIndex();
		},

		fixAttrIndex: function() {
			var me = this;
			me.$attrList.children('tr').each(function(i, attrEntry) {
				$(attrEntry).find('input[name],select[name]').each(function(j, input) {
					$(input).attr('name', $(input).attr('name').replace(/attributes[[0-9]*]/, 'attributes[' + i + ']'));
					var $label = $(input).next("label");
					if($label && $label.length!=0){
						$(input).next("label").attr('for', $(input).next("label").attr('for').replace(/[[0-9]*]/, '[' + i + ']'));
					}
				});
			});
		}
	});

	exports.attrs = new Attributes();
})(jQuery, Mustache, window);
