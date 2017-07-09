<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<!-- data grid -->
<table id="model-dg" class="easyui-datagrid" data-options="url: 'model/list', idField:'id', toolbar: '#model-dg-tb'">
    <thead>
    <th data-options="checkbox: true"></th>
    <th data-options="field: 'name', sortable: true, filter:'text', width:100">Name</th>
    <th data-options="field: 'alias', filter:'text', width:100">Alias</th>
    <th data-options="field: 'useAttribute', filter:'combobox', formatter: util.fmtBoolean, width: 100">Has 属性</th>
    <th data-options="field: 'useSpec', filter:'combobox', formatter: util.fmtBoolean, width:100">Has 规格s</th>
    <th data-options="field: 'useParameter', filter:'combobox', formatter: util.fmtBoolean, width:100">Has 参数</th>
    </thead>
</table>
<div id="model-dg-tb">
    <a href="javascript:void(0);" class="easyui-linkbutton" data-act="add" data-options="iconCls:'icon-add', plain: true,">New</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-act="edit" data-options="iconCls:'icon-edit', plain: true">编辑</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-act="delete"  data-options="iconCls:'icon-remove', plain: true">移除</a>
</div>
<!-- dialog -->
<div id="model-dlg" class="easyui-dialog"
     data-options="maximizable:true,modal:true, closed: true, buttons: '#model-dlg-btns'" style="width: 750px;">
    <form method="post">
        <input name="id" type="hidden"/>

        <div class="easyui-tabs">
            <div id="tab-basic" title="Basic" style="padding:10px 60px 20px 60px">
                <table cellpadding="5">
                    <tr>
                        <td>Name:</td>
                        <td>
                            <input name="name" type="text" class="easyui-textbox easyui-validatebox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Alias:</td>
                        <td>
                            <input name="alias" type="text" class="easyui-textbox"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Categories:</td>
                        <td>
                            <input name="categoryId" type="text" class="easyui-combotree" data-options="panelHeight:'auto', url: 'category/cats', onLoadSuccess: catsTreeLoaded ">
                        </td>
                    </tr>
                    <tr>
                        <td>Use Attribute:</td>
                        <td>
                        	<label>
                        		<input type="radio" name="useAttribute" value="1" checked="checked" />是
                            </label>
                        	<label>
                        		<input type="radio" name="useAttribute" value="0"/>否
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Use 规格:</td>
                        <td>
                        	<label>
                        		<input type="radio" name="useSpec" value="1" checked="checked" />是
                            </label>
                        	<label>
                        		<input type="radio" name="useSpec" value="0"/>否
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Use Parameter:</td>
                        <td>
                        	<label>
                        		<input type="radio" name="useParameter" value="1" checked="checked" />是
                            </label>
                        	<label>
                        		<input type="radio" name="useParameter" value="0"/>否
                            </label>
                        </td>
                    </tr>
                </table>
            </div>
            <div id='tab-attr' title="Attribute"  style="padding:10px 60px 20px 60px">
            	<a href="javascript:;" class="easyui-linkbutton">添加属性</a>
            	<table >
            		<thead>
            			<tr>
            				<th>属性名称</th>
            				<th>属性别名</th>
            				<th>显示 Type</th>
            				<th>属性值</th>
            				<th>排序</th>
            				<th>显示</th>
            				<th>Recommend</th>
            				<th>Action</th>
            			</tr>
            		</thead>
            		<tbody></tbody>
            	</table>
            </div>
            <div title="规格" style="padding:10px 60px 20px 60px">
            </div>
            <div id="tab-param" title="Parameter">
            	<a href="javascript:;" class="easyui-linkbutton" data-act="apg">添加一个参数组</a>
            	<div id="param-group"></div>
            </div>
        </div>
    </form>
</div>
<div id="model-dlg-btns">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-act="save" data-options="iconCls: 'icon-ok'">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-act="update" data-options="iconCls: 'icon-ok'">Update</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls: 'icon-cancel'" onclick="$('#model-dlg').dialog('close');">取消</a>
</div>
<script type="text/javascript" src="${ctx}/resource/js/admin/model.js"></script>
<script type="text/javascript">
    (function () {
        $('textarea.easyui-kindeditor').kindeditor({
            uploadJson: '${ctx}/storage/ul'
        });
    })();
</script>
