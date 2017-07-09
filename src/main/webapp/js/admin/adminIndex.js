/**
 * 批量删除js
 * Created by Administrator on 2016/4/7.
 */
$('.operateBar .operate-delete').click(function () {
    var keys = $g.jqGrid('getGridParam', 'selarrrow');
    1 > keys.length ? Glanway.Messager.alert("提示", "您至少应该选择一行记录") : Glanway.Messager.confirm("警告", "您确定要删除选择的" + keys.length + "行记录吗？", function (r) {
        if (!r) return;
        var loadding = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            url: 'delete',
            type: 'post',
            traditional: true,
            data: {id: keys}
        }).done(function (data) {
            layer.close(loadding);
            var removed;
            if (data.success) {
                removed = data.result || [];
                $g.trigger("reloadGrid");
                layer.alert("操作成功", {
                    skin: 'layer-ext-message' //样式类名
                    , closeBtn: 1
                    , time: 3000
                    , title: '提示 [3秒后关闭]'
                    , shade: 0
                    , offset: 'rb'
                    , btn: ''
                });
            }else{
                $g.trigger("reloadGrid");
                layer.alert(data.message, {
                    skin: 'layer-ext-message' //样式类名
                    , closeBtn: 1
                    , time: 3000
                    , title: '提示 [3秒后关闭]'
                    , shade: 0
                    , offset: 'rb'
                    , btn: ''
                });
            }
        }).fail(function () {
            layer.close(loadding);
            layer.alert('系统错误', {
                skin: 'layer-ext-message' //样式类名
                , closeBtn: 1
                , time: 3000
                , title: '提示 [3秒后关闭]'
                , shade: 0
                , offset: 'rb'
                , btn: ''
            });
        });
    });
});

//删除
function customDel() {
    var $tr = $(this).closest("tr.jqgrow"),
        rid = $tr.attr("id"),
        visible = $('#datagrid').jqGrid('getRowData', rid).visible,
        $id = $(this).closest("table.ui-jqgrid-btable").attr('id').replace(/_frozen([^_]*)$/, '$1'),
        $grid = $("#" + $id),
        op = {
            extraparam: {}

        };
    var rowData = $grid.jqGrid('getRowData', rid);
    var shopRoleId = rowData['shopRoleId'];
    var str = "您确定要删除选中的记录吗?";
    Glanway.Messager.confirm('警告', str, function (r) {
        r && $.ajax({
            url: 'delete',
            type: 'post',
            traditional: true,
            data: {"id": rid}
        }).done(function (data) {
            var removed;
            if (data && data.success) {
                removed = data.result || [];
                $grid.trigger("reloadGrid");
                layer.alert('操作成功', {
                    skin: 'layer-ext-message' //样式类名
                    , closeBtn: 1
                    , time: 3000
                    , title: '提示 [3秒后关闭]'
                    , shade: 0
                    , offset: 'rb'
                    , btn: ''
                });
            } else {
                layer.alert(data.message, {
                    skin: 'layer-ext-message' //样式类名
                    , closeBtn: 1
                    , time: 3000
                    , title: '提示 [3秒后关闭]'
                    , shade: 0
                    , offset: 'rb'
                    , btn: ''
                });
            }
        }).fail(function () {
            // TODO ERROR MSG
            layer.alert('系统错误', {
                skin: 'layer-ext-message' //样式类名
                , closeBtn: 1
                , time: 3000
                , title: '提示 [3秒后关闭]'
                , shade: 0
                , offset: 'rb'
                , btn: ''
            });
        });
    });
}


function customEdit(obj) {
    var $tr = $(obj).closest("tr.jqgrow"),
        rid = $tr.attr("id"),
        visible = $('#datagrid').jqGrid('getRowData', rid).visible,
        $id = $(obj).closest("table.ui-jqgrid-btable").attr('id').replace(/_frozen([^_]*)$/, '$1'),
        $grid = $("#" + $id),
        op = {
            extraparam: {}

        };
    window.location.href = "edit/" + rid;
}

/**
 * 将json串中被转义的字符还原
 * @param str
 * @returns {*}
 */
function restoreStr(str) {
    if (!str) return "";
    str = str.replace(new RegExp("<br>", "gm"), "\n");
    str = str.replace(new RegExp("&nbsp;", "gm"), " ");
    str = str.replace(new RegExp("&lt;", "gm"), "<");
    str = str.replace(new RegExp("&gt;", "gm"), ">");
    str = str.replace(new RegExp("&amp;", "gm"), "&");
    str = str.replace(new RegExp("&#039;", "gm"), "\'");
    str = str.replace(new RegExp("&#034;", "gm"), "\"");
    return str;
}

/**
 * 将后台传来的JSON串转化为对象
 * @param str
 * @returns {undefined}
 */
function toObject(str) {
    if (!str) return undefined;
    return JSON.parse(restoreStr(str));
}

/**
 * 重数据字典对象中得到下拉框的值
 */
function getSearchoptions(dictionaries) {
    var rtVal = ":全部";
    $.each(dictionaries, function (i, obj) {
        rtVal += ";" + obj.dicCode + ":" + obj.dicName;
    });
    return rtVal;
}

/**
 * 通过数据字典中的ID,dicName,得到下拉框的值
 * @param dictionaries
 * @returns
 */
function getSearchoptionsById(dictionaries) {
    var rtVal = ":全部";
    $.each(dictionaries, function (i, obj) {
        rtVal += ";" + obj.id + ":" + obj.dicName;
    });
    return rtVal;
}


/**
 * 根据数据字典来格式化数据
 * @param val
 * @param dictionaries
 * @returns {string}
 */
function formatByDictionary(val, dictionaries) {
    var rtVal = "";
    if (val) {
        $.each(dictionaries, function (i, obj) {
            if (val == obj.dicCode) {
                rtVal = obj.dicName;
                return false;
            }
        });
    }
    return rtVal;
}
/**
 * 根据数据字典的ID来格式化数据
 * @param val
 * @param dictionaries
 * @returns
 */
function formatByDictionaryById(val, dictionaries) {
    var rtVal = "";
    if (val) {
        $.each(dictionaries, function (i, obj) {
            if (val == obj.id) {
                rtVal = obj.dicName;
                return false;
            }
        });
    }
    return rtVal;
}

function objToStr(obj){
    return obj ? obj :　"";
}