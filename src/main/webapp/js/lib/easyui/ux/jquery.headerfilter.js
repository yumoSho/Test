/**
 * 根据 datagrid-filter 修改
 * datagrid-filter: http://www.jeasyui.com/extension/datagrid_filter.php
 *
 * 将 过滤器移动到 columns 中, 允许自定义 filter 参数, 修改一系列
 *
 * usage:
 * 在 column 中直接指定 filter
 * 简单模式1: 只指定组件
 * <th data-options="field: 'birth', filter: 'datebox'">birth</th>
 *
 * 简单模式2: 只指定 operator (传递给 server 的操作符)
 * <th data-options="field: 'name', filter: 'eq'">name</th>
 *
 * 完整模式:
 * <th data-options="field: 'name', filter: {
 *       op: [{text:'清除'}, {text:'等于', name:'EQ'}, {text:'大于', name:'GT'}]
 *       editor : 'combobox',
 *       type: 'S',         -- server 定义的类型标示, 不指定则根据 editor 自动判定
 *       options: {
 *          // editable: false,
 *          // all: true,
 *          // allText: '',
 *          // allValue: '',
 *          data: [                         --- 可省略
 *              {text: 'All', value: ''},
 *              {text: 'Y', value: 'Y'},
 *              {text: 'N', value: 'N'}
 *          ]
 *       }
 * }">name</th>
 *
 * @author vacoor
 */
(function ($) {
    var oldLoadDataMethod = $.fn.datagrid.methods.loadData;
    $.fn.datagrid.methods.loadData = function (jq, data) {
        jq.each(function () {
            $.data(this, 'datagrid').filterSource = null;
        });
        return oldLoadDataMethod.call($.fn.datagrid.methods, jq, data);
    };

    $.extend($.fn.datagrid.defaults, {
        filterMenuIconCls: 'icon-ok',
        filterBtnIconCls: 'icon-filter',
        filterBtnPosition: 'right',
        filterPosition: 'bottom',
        remoteFilter: false,
        filterDelay: 1000,
        // filters: [],   // 擦， 居然不是拷贝，居然多个实例用的是同一个
        filters: null,
        clearFilterName: undefined,

        encodeFilters: function (param, filters) {
            // param.filters = JSON.stringify(filters);
            $.each(filters, function (i, f) {
                var name = 'search_' + f.field + '_' + f.op;
                f.type && (name += '_' + f.type);

                if (f.value || f.value === false || f.value === 0) {
                    param[name] = f.value;
                }
            });
        },

        formatDate: function(date) {
            if (!date) {
                return '';
            }

            if (typeof date === 'number') {
                date = new Date(date);
            }

            var y = date.getFullYear(),
                m = date.getMonth() + 1,
                d = date.getDate(),
                value = y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
            return value;
        }
    });

    function init(target) {
        var state = $.data(target, 'datagrid'),
            opts = state.options,
            onResizeColumn = opts.onResizeColumn,
            onResize = opts.onResize,
            onBeforeLoad = opts.onBeforeLoad,
            loadFilter = opts.loadFilter;

        opts.filters = opts.filters || [];

        // wrap function
        opts.onResizeColumn = function (field, width) {
            if (opts.fitColumns) {
                adjustEditor(target, null, 10);
                $(target).datagrid('fitColumns');
                adjustEditor(target);
            } else {
                adjustEditor(target, field);
            }
            onResizeColumn.apply(target, arguments);
        };

        opts.onResize = function (width, height) {
            if (opts.fitColumns) {
                adjustEditor(target, null, 10);
                $(target).datagrid('fitColumns');
                adjustEditor(target);
            }
            onResize.apply(this, arguments);
        }

        opts.onBeforeLoad = function (param) {
            $.isFunction(opts.encodeFilters) && opts.encodeFilters(param, opts.filters);
            return onBeforeLoad.apply(this, arguments);
        };

        opts.loadFilter = function (data) {
            data = loadFilter.call(this, data);
            return filter.apply(this, arguments);
        };

        if (!$('#datagrid-filter-style').length) {
            $('head').append(
                    '<style id="datagrid-filter-style">' +
                    'a.datagrid-filter-btn{display:inline-block;width:22px;height:22px;vertical-align:top;cursor:pointer;opacity:0.6;filter:alpha(opacity=60);}' +
                    'a:hover.datagrid-filter-btn{opacity:1;filter:alpha(opacity=100);}' +
                    '</style>'
            );
        }

        createFilters(true);
        createFilters();

        if (opts.fitColumns) {
            setTimeout(function () {
                adjustEditor(target);
            }, 0);
        }


        // -------------------

        /**
         * create  filter component
         */
        function createFilters(frozen) {
            var dc = state.dc,
                fields = $(target).datagrid('getColumnFields', frozen),
                table = (frozen ? dc.header1 : dc.header2).find('table.datagrid-htable'),
                tr;

            if (frozen && opts.rownumbers) {
                fields.unshift('_');
            }

            table.find('tr').each(function () {
                $(this).height($(this).height());
            });

            // clear the old filter component
            table.find('input.datagrid-filter').each(function () {
                if (this.filter.destroy) {
                    this.filter.destroy(this);
                }
                if (this.menu) {
                    $(this.menu).menu('destroy');
                }
            });
            table.find('tr.datagrid-filter-row').remove();

            // create new filter component
            tr = $('<tr class="datagrid-header-row datagrid-filter-row"></tr>');
            if (opts.filterPosition == 'bottom') {
                tr.appendTo(table.find('tbody'));
            } else {
                tr.prependTo(table.find('tbody'));
            }

            for (var i = 0; i < fields.length; i++) {
                var field = fields[i],
                    colOpts = $(target).datagrid('getColumnOption', field),
                    filter,
                    editor,
                    div,
                    td;

                if (colOpts && (colOpts.checkbox || colOpts.expander || !(filter = colOpts.filter))) {
                    field = '_';
                }
                td = $('<td></td>').attr('field', field).appendTo(tr);

                if (field == '_') {
                    continue;
                }
                div = $('<div class="datagrid-filter-c"></div>').appendTo(td);

                // filter = typeof filter !== 'string' ? filter : { op: [filter] };
                if (typeof filter === 'string') {
                    if (opts.editors[filter]) {
                        filter = { editor: filter };
                    } else {
                        filter = { op : filter };
                    }
                }

                filter.editor = filter.editor || 'text';
                filter.type = filter.type || opts.types[filter.editor] || 'S';
                filter.op = filter.op || opts.operators[filter.type] || 'EQ';
                filter.op = $.isArray(filter.op) ? filter.op : [filter.op];

                // op: { name:'EQ', text: '等于' }
                $.each(filter.op, function (index, item) {
                    if (typeof item === 'string') {
                        item = filter.op[index] = { name: item };
                    }

                    item.text = item.text || item.name;
                });

                // get editor
                editor = opts.editors[filter.editor];
                !editor && $.error("invalid filter editor type");

                // 
                if (filter.editor == 'combobox' || filter.editor == 'datebox') {
                    var defaults = {
                        editable: false,
                        panelHeight: 'auto',
                        data: opts.comboboxFilterData,
                        onChange: function(value) {
                            var me = this,
                                $me = $(me),
                                isDateBox = $me.hasClass('datebox-f'),
                                name = $me.attr('name'),
                                op = me.filter.op[0],
                                type = me.filter.type;

                            if ('' === value) {
                                // grid.datagrid('removeFilter', name);
                                removeFilter(target, name);
                            } else {
                                // datebox
                                if(isDateBox) {
                                    value = opts.formatDate($.fn.datebox.defaults.parser(value));
                                }
                                //grid.datagrid('addFilter', {
                                addFilter(target, {
                                    field: name,
                                    op: op,
                                    type: type,
                                    value: value
                                });
                            }
                            // grid.datagrid('doFilter');
                            doFilter(target);
                        }
                    };

                    if (filter.options && filter.options.all) {
                        defaults.loadFilter = function(data) {
                            if ($.isArray(data)) {
                                var coOpts = $(this).data('combobox').options, all = {};
                                all[coOpts.textField || 'name'] = coOpts.allText || 'All';

                                all[coOpts.valueField || 'value'] = coOpts.allValue || '';
                                data.unshift(all);
                            }
                            return data;
                        };
                    }

                    if ($.fn.combo.methods['initClear'] && filter.editor == 'datebox') {
                        $.extend(defaults, {
                            onInit: function() { $(this).combobox('initClear'); }
                        });
                    }

                    filter.options = $.extend(defaults, filter.options || {});
                }

                // create editor
                var editorEl = editor.init(div, filter.options || {})
                    .addClass('datagrid-filter')
                    .attr('name', field)
                    .attr('autocomplete', 'off');
                editorEl[0].editor = editor;
                editorEl[0].filter = filter;

                /**
                 * simple filter
                 */
                if (filter.op.length < 2 && 'text' === filter.editor) {
                    editorEl.bind('keydown', function (e) {
                        var me = $(this),
                            field = me.attr('name'),
                            filter = this.filter,
                            op = filter.op[0] || 'LK';

                        this.timer && clearTimeout(this.timer);

                        if (13 == e.keyCode) {

                            addFilter(target, {
                                field: field,
                                op: op,
                                type: filter.type,
                                value: me.val()
                            });
                            doFilter(target);
                        } else {
                            this.timer = setTimeout(function () {
                                addFilter(target, {
                                    field: field,
                                    op: op,
                                    type: filter.type,
                                    value: me.val()
                                });
                                doFilter(target);
                            }, opts.filterDelay);
                        }
                    });
                } else if (filter.op.length > 1) {
                    editorEl[0].menu = createFilterMenu(div, filter.op);
                    /*
                     if (filter.options && filter.options.onInit) {
                     filter.options.onInit.call(editorEl[0]);
                     }
                     */
                }

                if (filter.options && filter.options.onInit) {
                    filter.options.onInit.call(editorEl[0]);
                }

                adjustEditor(target, field);
            }
        }

        function createFilterMenu(container, operators) {
            if (!operators) {
                return null;
            }

            var btn = $('<a class="datagrid-filter-btn">&nbsp;</a>').addClass(opts.filterBtnIconCls);
            if (opts.filterBtnPosition == 'right') {
                btn.appendTo(container);
            } else {
                btn.prependTo(container);
            }
            var menu = $('<div></div>').appendTo('body');
            menu.menu({
                alignTo: btn,
                onClick: function (item) {
                    var btn = $(this).menu('options').alignTo;
                    var td = btn.closest('td[field]');
                    var field = td.attr('field');
                    var inputEl = td.find('input.datagrid-filter');
                    var value = inputEl[0].editor.getValue(inputEl);

                    addFilter(target, {
                        field: field,
                        // op: inputEl.filter.op,
                        op: {
                            name: item.name,
                            text: item.text
                        },
                        type: inputEl[0].filter.type,
                        value: value
                    });

                    doFilter(target);
                }
            });
            $.each(operators, function (index, item) {
                menu.menu('appendItem', {
                    name: item.name,
                    text: item.text
                });
            });
            btn.bind('click', {menu: menu}, function (e) {
                $(e.data.menu).menu('show');
                return false;
            });

            return menu;
        }

        /**
         *adjust editor size
         *
         * @param target
         * @param field
         * @param width
         */
        function adjustEditor(target, field, width) {
            var grid = $(target),
                header = grid.datagrid('getPanel').find('div.datagrid-header'),
                editorEl = field ? header.find('input.datagrid-filter[name="' + field + '"]') : header.find('input.datagrid-filter');

            editorEl.each(function () {
                var me = this,
                    name = $(me).attr('name'),
                    colOpts = grid.datagrid('getColumnOption', name),
                    btn = $(this).closest('div.datagrid-filter-c').find('a.datagrid-filter-btn');

                if (width !== undefined) {
                    me.editor.resize(this, width);
                } else {
                    me.editor.resize(this, colOpts.width - btn._outerWidth());
                }
            });
        }

        /**
         * filter data
         *
         * @param data
         * @returns {*}
         */
        function filter(data) {
            var state = $.data(this, 'datagrid'),
                opts = state.options,
                rows, filter, row, i, j;

            if ($.isArray(data)) {
                data = {
                    total: data.length,
                    rows: data
                };
            }

            // locale filter TODO 这里好像其实是没有用的
            if (!opts.remoteFilter) {
                state.filterSource = state.filterSource || data;
                data = $.extend({}, state.filterSource);

                // exists filter
                if (opts.filters.length) {
                    rows = [];
                    for (i = 0; i < data.rows.length; i++) {
                        row = data.rows[i];
                        for (j = 0; j < opts.filters.length; j++) {
                            filter = opts.filters[j];
                            if (!filter || !$.isFunction(filter.filterFn) || false === filter.filterFn(row)) {
                                continue;
                            }
                            rows.push(row);
                        }
                    }
                    data = {
                        total: data.total - (data.rows.length - rows.length),
                        rows: rows
                    };
                }

                // exists pagination
                if (opts.pagination) {
                    var dg = $(this),
                        pager = dg.datagrid('getPager');

                    pager.pagination({
                        onSelectPage: function (pageNum, pageSize) {
                            opts.pageNumber = pageNum;
                            opts.pageSize = pageSize;
                            pager.pagination('refresh', {
                                pageNumber: pageNum,
                                pageSize: pageSize
                            });
                            dg.datagrid('loadData', state.filterSource);
                        }
                    });
                    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
                    var end = start + parseInt(opts.pageSize);
                    data.rows = data.rows.slice(start, end);
                }
            }
            return data;
        }
    }


    /**
     * get filter index, return -1 if not found.
     */
    function getFilterIndex(target, field) {
        var rules = $(target).datagrid('options').filters;
        for (var i = 0; i < rules.length; i++) {
            if (rules[i].field == field) {
                return i;
            }
        }
        return -1;
    }

    function getEditorEl(target, field) {
        var opts = $(target).datagrid('options');
        var header = $(target).datagrid('getPanel').find('div.datagrid-header');
        return header.find('tr.datagrid-filter-row td[field="' + field + '"] input.datagrid-filter');
    }

    function addFilter(target, filter) {
        var opts = $(target).datagrid('options'),
            filters = opts.filters,
            editorEl = getEditorEl(target, filter.field),
            editor = editorEl[0].editor,
            index,
            menu;

        if (typeof filter.op === 'string') {
            filter.op = {text: filter.op, name: filter.op};
        }

        if (filter.op.name == opts.clearFilterName) {
            removeFilter(target, filter.field);
            return;
        }

        if (editorEl[0].filter.options) {
            filter.field = editorEl[0].filter.options.name || filter.field;
        }

        if ((index = getFilterIndex(target, filter.field)) >= 0) {
            $.extend(filters[index], {
                field: filter.field,
                op: filter.op.name,
                type: filter.type,
                value: filter.value
            });
        } else {
            //
            filters.push({
                field: filter.field,
                op: filter.op.name,
                type: filter.type,
                value: filter.value
            });
        }

        if (!editorEl.length) {
            return;
        }

        if (filter.op.name != opts.clearFilterName && filter.value !== undefined) {
            editor.setValue(editorEl, filter.value);
        }

        menu = editorEl[0].menu;
        if (menu) {
            menu.find('.' + opts.filterMenuIconCls).removeClass(opts.filterMenuIconCls);
            var item = menu.menu('findItem', filter.op['text']);
            menu.menu('setIcon', {
                target: item.target,
                iconCls: opts.filterMenuIconCls
            });
        }
    }

    /**
     * remove filter for give field
     *
     * @param target
     * @param field
     */
    function removeFilter(target, field) {
        var grid = $(target),
            opts = grid.datagrid('options');

        // 不同名称问题
        var t = getEditorEl(target, field)[0];
        if (!t) return;

        field = t.filter.options.name || field;
        if (field) {
            var index = getFilterIndex(target, field);
            if (index >= 0) {
                opts.filters.splice(index, 1);
            }
            _clear([field]);
        } else {
            opts.filters = [];
            var fields = grid.datagrid('getColumnFields', true).concat(grid.datagrid('getColumnFields'));
            _clear(fields);
        }

        function _clear(fields) {
            for (var i = 0; i < fields.length; i++) {
                var editorEl = getEditorEl(target, fields[i]);
                if (editorEl.length) {
                    editorEl[0].editor.setValue(editorEl, '');
                    var menu = editorEl[0].menu;
                    if (menu) {
                        menu.find('.' + opts.filterMenuIconCls).removeClass(opts.filterMenuIconCls);
                    }
                }
            }
        }
    }

    function doFilter(target) {
        var state = $.data(target, 'datagrid'),
            opts = state.options;

        if (opts.remoteFilter) {
            $(target).datagrid('load');
        } else {
            $(target).datagrid('getPager').pagination('refresh', {pageNumber: 1});
            $(target).datagrid('options').pageNumber = 1;
            $(target).datagrid('loadData', state.filterSource || state.data);
        }
    }

    // editor --> type
    $.fn.datagrid.defaults.types = {
        text: 'S',
        textarea: 'S',
        checkbox: 'B',
        numberbox: 'I',
        combobox: 'B',
        datebox: 'D'
    };

    // type --> operator
    $.fn.datagrid.defaults.operators = {
        B: 'EQ',
        I: 'EQ',
        F: 'EQ',
        N: 'EQ',
        D: 'EQ',
        S: 'LK'
    };

    $.fn.datagrid.defaults.comboboxFilterData = [
        { text:'All',  value: '' },
        { text:'Y',  value: 'Y' },
        { text:'N',  value: 'N' }
    ];

    $.extend($.fn.datagrid.methods, {
        enableFilter: function (jq) {
            return jq.each(function () {
                init(this);
            });
        },
        addFilter: function (jq, filter) {
            return jq.each(function () {
                addFilter(this, filter);
            });
        },
        removeFilter: function (jq, field) {
            return jq.each(function () {
                removeFilter(this, field);
            });
        },
        clearFilter: function(jq, field) {
            return jq.each(function() {
                var $grid = $(this),
                    fields = $grid.datagrid('getColumnFields');

                fields = fields.concat($grid.datagrid('getColumnFields', true));
                $.each(fields, function(i, f) {
                    removeFilter($grid, f);
                })
            });
        },
        doFilter: function (jq) {
            return jq.each(function () {
                doFilter(this);
            });
        },
        getEditorEl: function (jq, field) {
            return getEditorEl(jq[0], field);
        }
    });

    /* invalid */
    var oldAfterRender = $.fn.datagrid.defaults.view.onAfterRender;
    $.fn.datagrid.defaults.view.onAfterRender = function(target) {
        var grid = $(target),
            state = $.data(target, 'datagrid'),
            dc = state.dc,
            frozenFilters = dc.header1.find('table.datagrid-htable').find('input.datagrid-filter'),
            filters = dc.header2.find('table.datagrid-htable').find('input.datagrid-filter'),
            opts, cols,
            ret = oldAfterRender.apply(this, arguments);

        if (grid.length > 0 && (frozenFilters.length < 1 && filters.length < 1)) {
            opts = state.options; //grid.datagrid('options');
            cols = opts.columns;

            $.each(cols[0], function (i, e) {
                if (e.filter && (typeof e.filter === 'string' || typeof e.filter === 'object')) {
                    grid.datagrid('enableFilter');
                    return false;
                }
            });
        }

        return ret;
    };
})(jQuery);
