/**
 * jquery easyui & kindeditor
 * 当在 tabs / dialog 中会出问题
 * 源码被混淆了， 抱怨句， 真TM难扩展,
 * 如果无法自动初始化就手动初始化吧 $('textarea.easyui-kindeditor').kindeditor({});
 */
(function ($, K) {
	if (!K)
		throw "KindEditor未定义!";

	function create(target) {
		var opts = $.data(target, 'kindeditor').options;
		var editor = K.create(target, opts);
		$.data(target, 'kindeditor').options.editor = editor;
		/**
		 * fix 关闭标签再打开后无法获取焦点
		 * http://kindeditor.net/view.php?bbsid=4&postid=4641
		 */
		setTimeout(function() { editor.remove().create(); }, 0);
	}

	$.fn.kindeditor = function (options, param) {
		if (typeof options == 'string') {
			var method = $.fn.kindeditor.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};
		return this.each(function () {
			var state = $.data(this, 'kindeditor');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'kindeditor', {
						options : $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options)
					});
			}
			create(this);
		});
	};

	$.fn.kindeditor.parseOptions = function (target) {
		return $.extend({}, $.parser.parseOptions(target, []));
	};

	$.fn.kindeditor.methods = {
		editor : function (jq) {
			return $.data(jq[0], 'kindeditor').options.editor;
		}
	};

    var contextPath =  (window.Besture || window)['contextPath'] || '';
	$.fn.kindeditor.defaults = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		filePostName: 'file',
//        basePath: contextPath,
		// uploadJson: 'http://localhost:9091/storage/ul'
        uploadJson: contextPath + '/storage/files/ul',
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link'
		],
		afterChange:function(){
			this.sync();  //这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
		}
	};
	$.parser.plugins.push("kindeditor");

	// fix form clear
	var oldClear = $.fn.form.methods.clear;
	$.fn.form.methods.clear = function(jq) {
		var ret = oldClear.apply(this, arguments);
		jq.find('textarea.easyui-kindeditor').each(function(i, e) {
			var state = $.data(e, 'kindeditor'),
				opts;
			if (state && (opts = state.options) && opts.editor) {
				opts.editor.html('');
			}
		});
		return ret;
	};
	
	// fix form load
	var oldLoad = $.fn.form.methods.load;
	$.fn.form.methods.load = function(jq, data) {
		var ret = oldLoad.apply(this, arguments);
		jq.find('textarea.easyui-kindeditor').each(function(i, e) {
			var state = $.data(e, 'kindeditor'),
				opts,
				editor;
			if (state && (opts = state.options) && (editor = opts.editor)) {
				editor.html(data[editor.srcElement[0].name]);
			}
		});
		return ret;
	};
})(jQuery, KindEditor);