<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/icon.css">
    <link rel="stylesheet" href="${ctx}/css/uploader2.css"/>
    <%@include file="../../include/elibs.jspf" %>
    <style>
        /*.filePreview {
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            width: 100px;
            height: 30px;
            font-size: 15px;          /!* 增大不同浏览器的可点击区域 *!/
            opacity: 0;               /!* 实现的关键点 *!/
            filter: alpha(opacity=0); /!* 兼容IE *!/
        }*/

    </style>
</m:h>
<%@include file="../../include/admin/support.jspf" %>
<%@include file="../../include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
         <div class="position">
             <span>您当前的位置：</span>
             <a href="${ctx}/admin" title="首页">首页</a> &gt;
             <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
             <a href="${ctx}/admin/product/index" title="商品信息">商品信息</a> &gt; 商品导入
		</div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="import" method="post" enctype="multipart/form-data">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <th width="130" align="right" valign="top">获得模板：<b class="color-red">&nbsp;</b></th>
                        <td valign="bottom"><a href="getTemplate" target="_blank"><input type="button" class="btn-sure red" value="点击这里，下载Excel模板"></a>&nbsp;&nbsp;<a href="helpBook" style="color: red" target="_blank">请首先阅读模板文件说明</a></td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">Excel文件：<b class="color-red">*</b></th>
                        <%--<td valign="top"><input type="file" class="filePreview" name="filename"/></td>--%>
                        <!-- S 单文件上传 -->
                       <td valign="top">
                           <div>
                            <div class="desc" style="color:#999;line-height:30px;font-size: 12px;">请上传<strong>2M以内</strong>文件</div>
                            <div class="single" id="single">
                                <div class="actions">
                                    <a href="javascript:;" id="upload">选择文件</a>
                                </div>
                                <div class="upload-input">请选择..</div>
                                <div id="entry">
                                    <c:if test="${not empty file.serverName}">
                                        <input type="hidden" data-name="服务器文件显示名称" name="serverName" value="${file.serverName}">
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <!-- E 单文件上传 -->
                        <%--<input type="hidden" id="localName" name="localName"  value="">--%>
                       </td>
                    </tr>

                    <%--<tr>
                        <th width="130" align="right">&nbsp;</th>
                        <td>
								<span class="btn-sure-wrap">
									<input class=" btn-cancel" id="submit" type="submit" value="保存" disabled="disabled"/>
								</span>
								<span class="btn-cancel-wrap">
                                    <a href="index" class="btn-cancel">取消</a>
								</span>
                        </td>
                    </tr>--%>
                    </tbody>
                </table>
            </form>
            <div class="table-module03">
                <table id="datagrid"></table>
                <div id="pagination"></div>
            </div>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/uploader2.js"></script>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
    <%--<script type="text/javascript" src="${ctx}/js/admin/import.js"></script>--%>
    <script type="text/javascript">
        var uploader = Uploader2({
            name: 'serverName',
            list: 'entry',
            mode: 'l',
            max_file_count: 1,
            max_file_size: '50G',
            baseUrl: '${ctx}',
            url: '${ctx}/admin/product/toImport/import',
            file_data_name: 'filename',
            browse_button: 'upload',
            filters: {
                mime_types : [ //只允许上传Excel文件
                    { title : "Excel files", extensions : "xls" },
                ]
            }
        }).bind('FileFiltered', function (up, file) {
            var el = file.el,
                    name = el ? el.getAttribute('data-name') : file.name;
            $("#localName").val(file.name);
            $('.upload-input').text(name).attr('title', name);
        }).bind('FilesRemoved', function (up, file) {
            $('.upload-input').text('请选择..').attr('title', '');
        }).bind('FileUploaded',function(up,file){
            $g.trigger("reloadGrid");
        }).bind("Error",function(up,err){
            if(err.code == -601){
                Glanway.Messager.alert("提示", "文件类型不支持");
                return false;
            }
            Glanway.Messager.alert("提示", err.message);
        }).bind('UploadComplete',function(uploader,files){
            $g.trigger("reloadGrid");
        });


        var $g = $('#datagrid').jqGrid({
            url: 'list',
            datatype: 'json',
            colNames: ['操作', 'id','文件名称','服务器文件名','文件错误信息','状态','上传日期'],
            colModel: [
                { template: 'actions4' },
                { name: 'id', index: 'id', hidden: true, key: true ,align: 'left'},
                { name: 'title', index: 'title', template: 'text', align: 'left'},
                { name: 'name',index:'name', template:'text',align: 'left'},
                { name: 'error', index: 'error', template: 'text', align: 'left'},
                { name: 'status', index: 'status', formatter: function (va) {
                    return va==0 ? '失败': '成功';
                }, stype: 'select', searchoptions: {value: ':全部;0:失败;1:成功'}, align: 'left'},
                { name: 'lastModifiedDate', index: 'lastModifiedDate', template: 'date', }
            ],
            multiselect: true,
            autowidth: true,
            shrinkToFit: true,
            height: 'auto',
            pager: '#pagination',
            sortname: 'lastModifiedDate',
            sortorder: 'desc'
        });


    </script>
<m:f/>