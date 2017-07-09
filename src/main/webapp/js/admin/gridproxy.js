/**
 * GridProxy & Grid
 *
 *uusage:
 * Grid.config({
 *	   grid:'#website-dg',
 *	   abtn: '#website-dg-tb a.easyui-linkbutton[data-act=add]',
 *	   ebtn: '#website-dg-tb a.easyui-linkbutton[data-act=edit]',
 *	   dbtn: '#website-dg-tb a.easyui-linkbutton[data-act=delete]',
 *	   adlg:'#website-dlg',
 *	   edlg:'#website-dlg',
 *	   sbtn: '#website-dlg-btns a.easyui-linkbutton[data-act=save]',
 *	   ubtn: '#website-dlg-btns a.easyui-linkbutton[data-act=update]',
 *	   onUpdate: function() {
 *	      console.log('update');
 *	   }
 *	});
 * 
 * @author vacoor
 */
// 
;var GridProxy = (function($) {
	var defaults = {
		grid : null,
		init : $.noop,
		message : {
			noRecordSelected : 'No record selected'
		},
		onNoRecordSelected : function() {
			var me = this;

            Besture.Messager.alert('提示', me.message.noRecordSelected);
            /*
			$.messager.show({
				title: 'warn',
				msg: me.message.noRecordSelected
			});
			*/
		},
		onBeforeSave: function(record) {
			$.messager.progress({
				title : 'Tip',
				msg : 'Please wait...'
			});
		},

		onSaveSuccess : function(record, data) {
			var me = this,
				$grid = me.$grid;
			
			$grid.datagrid('insertRow', {
				index: 0,
				row: !$.isEmptyObject(data.data) ? data.data : record
			}).datagrid('selectRow', 0);

			$.messager.show({
				title: '提示',
				msg : 'Add Success!'
			});
			this.$grid.datagrid('reload');
		},

		onSaveFailure : function(record, data) {
			$.messager.show({
				title : 'Error',
				msg : data.message
			});
		},

		// update
		onBeforeUpdate: function(index, record) {
			$.messager.progress({
				title : '提示',
				msg : 'Please wait...'
			});
		},

		onUpdateSuccess : function(index, record, data) {
			this.$grid.datagrid('updateRow', {
				index : index,
				row : !$.isEmptyObject(data.data) ? data.data : record
			}).datagrid('selectRow', index);

			$.messager.show({
				title : '提示',
				msg : '更新成功'
			});
			this.$grid.datagrid('reload');
		},

		onUpdateFailure : function(index, record, data) {
			$.messager.show({
				title : 'Error',
				msg : data.message
			});
		},
		// delete
		onBeforeDelete : function(rows) {
		   var me = this;
           Besture.Messager.confirm("Confirm", "Are you sure delete selection record ?", function(r) {
		   // $.messager.confirm('Confirm', 'Are you sure delete selection record?', function(r) {
		      if (r) {
	            $.messager.progress({
	               title : '提示',
	               msg : 'Please wait...'
	            });
	            me._doDelete(rows);
		      }
		   });
		},
		onDeleteSuccess : function(data) {
			$.messager.show({
				title : '提示',
				msg : '删除成功'
			});
			
			this.$grid.datagrid('reload');
            this.$grid.data('treegrid') && this.$grid.treegrid('reload');
		},
		onDeleteFailure : function(data) {
			$.messager.show({
				title : 'Error',
				msg : (data.message || 'Unknown Error')
			});
		},
		onException: function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.show({
				title: 'Error',
				msg: 'Request failure'//textStatus + ":" + errorThrown
			});
		}
	};
	
	var Proxy = function(opts) {
		var me = this,
			opts = $.extend(true, {}, defaults, opts);

		$.extend(true, me, opts);

		me.$grid = $(me.grid);
		1 !== me.$grid.length && $.error('can not find a unique grid: ' + opts.grid);

		me.init();
		delete me.grid;
		delete me.init;
	};

	Proxy.prototype = {
	   callDefault: function(args /*arguments obj*/) {
	      var method = this.callDefault.caller,
	          n;
	      for (n in this) {
	         if (this.hasOwnProperty(n) && this[n] == method) {
               break;
	         }
	      }
	      if (n && defaults.hasOwnProperty(n)) {
	         defaults[n].apply(this, args || []);
	      }
	   },
		// save
		doSave : function(record) {
			var me = this,
				$grid = me.$grid;

			typeof record !== 'object' && $.error('Invalid record');

			if (false === me.onBeforeSave(record)) {
				return;
			}

			me.saveUrl = me.saveUrl || ($grid.datagrid('options').url||$grid.treegrid('options').url).replace(/([^/]+)+$/, 'save');
			$.ajax({
				url : me.saveUrl,
				dataType : 'json',
				data : me._normalizeParam(record),
				type : 'post',
				success : function(data, textStatus, jqXHR) {
					$.messager.progress('close');
					if (false === data.success) {
						me.onSaveFailure(record, data);
					} else {
						me.onSaveSuccess(record, data);
					}
				},
				error: function() {
					$.messager.progress('close');
					me.onException.apply(me, arguments);
				}
			});
		},

		// update
		doUpdate : function(record) {
			var me = this,
				$grid = me.$grid,
				index;

				typeof record !== 'object' && $.error('Invalid record');
				!record.id && $.error("Invalid record id");

				index = $grid.datagrid('getRowIndex', record.id);

				if (false === me.onBeforeUpdate(index, record)) {
					return;
				}

				me.updateUrl = me.updateUrl || ($grid.datagrid('options').url || $grid.treegrid('options').url).replace(/([^/]+)+$/, 'update');
				$.ajax({
					url : me.updateUrl,
					dataType : 'json',
					data : me._normalizeParam(record),
					type : 'post',
					success : function(data) {
						$.messager.progress('close');
						if (false === data.success) {
							me.onUpdateFailure(index, record, data);
                        } else {
							me.onUpdateSuccess(index, record, data);
						}
					},
					error: function() {
						$.messager.progress('close');
						me.onException.apply(me, arguments);
					}
				});
		},
		
		doDelete: function() {
			var me = this,
				rows = me.$grid .datagrid('getSelections'),
				params = '';

			if (!rows.length) {
				me.onNoRecordSelected('delete');
				return;
			}

			me.onBeforeDelete(rows);
		},

		// delete
		_doDelete : function(rows) {
			var me = this,
				params = ''
				rows = $.isArray(rows) ? rows : [rows];
			
			$.each(rows, function(i, e) {
				i > 0 && (params += '&');
				params += 'id=' + e.id;
			});

			me.deleteUrl = me.deleteUrl || (me.$grid.datagrid('options').url || me.$grid.treegrid('options').url).replace(/([^/]+)+$/, 'delete');
			$.ajax({
				url : me.deleteUrl,
				type : 'post',
				data : params,
				success : function(data) {
					$.messager.progress('close');
					if (data.success) {
						me.onDeleteSuccess(data);
					} else {
						me.onDeleteFailure(data);
					}
				},
				error: function() {
					$.messager.progress('close');
					me.onException.apply(me, arguments);
				}
			});
		},
		// private , object --> query string
		_normalizeParam: function(record) {
			if ('object' !== $.type(record)) {
				return record;
			}

			var param = '', v;

			for (var p in record) {
				v = record[p];
				if ('array' === $.type(v)) {
					$.each(v, function(i, e) {
						param += '&' + p + '=' + (e||'');
					});
				} else {
					param += '&' + p + '=' + (v||'');
				}
			}
			return param.substring(1);
		}
	};
	return Proxy;
})(jQuery);

var Proxy2 = (function() {
	var Proxy2 = function(opts) {
      $.extend(true, this, new GridProxy(opts));
	};
	
	Proxy2.prototype = {
      'edit': function(event) { 
         var index = $(event.target || event.srcElement).closest('tr').attr('datagrid-row-index'),
             record = this.$grid.datagrid('getRows')[index];

         event.stopPropagation && event.stopPropagation() || (event.cancelBubble = true);
         this.doEdit(record);
      },

      'doEdit': function(record) {
         window.location.href = 'edit/' + record.id;
      },

      'delete': function(event) {
         var me = this,
             index = $(event.target || event.srcElement).closest('tr').attr('datagrid-row-index'),
             record = this.$grid.datagrid('getRows')[index];

         event.stopPropagation && event.stopPropagation() || (event.cancelBubble = true);

         Besture.Messager.confirm('提示', ['您确定要删除选择的记录吗？', '如果是，请点击确定，否则请点击取消'], function(ret) {
            if (ret) {
               me._doDelete(record);
            }
         });
      }
	};
	
	return Proxy2;
})(jQuery);


/**
 * @deprecated
 * -------- easyui grid --------------
 */
// Grid
var Grid = (function($) {
	var defaults = {
      // config
      grid: null,
      abtn: null,
      ebtn: null,
      dbtn: null,
      adlg: null,
      edlg: null,
      sbtn: null,
      ubtn: null,
      onBeforeAdd: $.noop,
      onBeforeEdit: $.noop
   };

   var Grid = function(opts) {
      var me = this;

      me.$adlg = $(opts.adlg);
      me.$edlg = $(opts.edlg);
      
      1 !== me.$adlg.length && $.error('can not find a unique add dialog: ' + opts.adlg);
      1 !== me.$edlg.length && $.error('can not find a unique edit dialog: ' + opts.edlg);
      
      me.$abtn = $(opts.abtn);	// add
      me.$ebtn = $(opts.ebtn);	// edit
      me.$sbtn = $(opts.sbtn);	// save
      me.$ubtn = $(opts.ubtn);	// update
      me.$dbtn = $(opts.dbtn);	// delete
      
      if (me.$sbtn.length < 1) {
         me.$sbtn = me.$adlg.find('a.easyui-linkbutton[data-act=save]');
      }
      if (me.$ubtn.length < 1) {
         me.$ubtn = me.$edlg.find('a.easyui-linkbutton[data-act=update]');
      }

      // 找不到按钮也允许自行指定事件绑定
      /*
      1 !== me.$sbtn.length && $.error('can not find a unique save button');
      1 !== me.$ubtn.length && $.error('can not find a unique update button');
      */
      delete opts.abtn;
      delete opts.ebtn;
      delete opts.adlg;
      delete opts.edlg;
      delete opts.sbtn;
      delete opts.ubtn;
      delete opts.dbtn;
      
      me.bindEvent();

      $.extend(true, me, new GridProxy(opts));
   };
   
   Grid.prototype = {
      bindEvent: function() {
         var me = this;
         
         /*
         me.$abtn.bind('click', function(me.onAdd, me));
         me.$ebtn.bind('click', function(me.onEdit, me));
         me.$sbtn.bind('click', function(me.onSave, me));
         me.$ubtn.bind('click', function(me.onUpdate, me));
         me.$dbtn.bind('click', function(me.onDelete, me));
         */
         // 为了覆盖, 这里不使用  $.proxy
         me.$abtn.bind('click', function() { me.onAdd(); });
         me.$ebtn.bind('click', function() { me.onEdit(); });
         me.$sbtn.bind('click', function() { me.onSave();});
         me.$ubtn.bind('click', function() { me.onUpdate(); });
         me.$dbtn.bind('click', function() { me.onDelete(); });
      },

      onAdd: function() {
         var me = this;
            $adlg = me.$adlg;
         me.$sbtn.show();
         me.$ubtn.hide();
         $adlg.find('form').form('reset');
         $adlg.dialog('open');//.dialog('setTitle', ctx.etitle);

         $.isFunction(me.onBeforeAdd) && me.onBeforeAdd();
      },
   
      onSave: function() {
         var me = this,
            $adlg = me.$adlg,
            $form = $adlg.find('form');
         
         if (!$form.form('validate')) {
        	 return;
         }
         
         me.doSave($form.form('getRecord'));
         $adlg.dialog('close');
         $form.form('clear');
      },
   
      onEdit: function() {
         var me = this;
            $edlg = me.$edlg,
            sels = me.$grid.datagrid('getSelections');

         if (sels.length < 1) {
            Besture.Messager.alert('提示', '您必须选择一条记录');
             /*
            $.messager.show({
               title : '警告',
               msg : '必须选择一条记录'
            });
            */
            return;
         }

         if (sels.length > 1) {
            Besture.Messager.alert('提示', 'You can only choose one record');
             /*
            $.messager.show({
               title : '警告',
               msg : '只能选择一条记录'
            });
            */
            return;
         }
         
         me.$ubtn.show();
         me.$sbtn.hide();
         $edlg.find('form').form('clear').form('load', sels[0]);
         $edlg.dialog('open');//.dialog('setTitle', ctx.etitle);

         $.isFunction(me.onBeforeEdit) && me.onBeforeEdit(sels[0]);
      },

      onUpdate: function() {
         var me = this,
            $edlg = me.$edlg,
            $form = $edlg.find('form');

         if (!$form.form('validate')) {
        	 return;
         }

         me.doUpdate($form.form('getRecord'));
         $edlg.dialog('close');
         $form.form('reset');
      },
      
      onDelete: function() {
         this.doDelete();
      }
   };

   Grid.config = function(opts) {
      return new Grid(opts);
   };
   
   return Grid;
})(jQuery);