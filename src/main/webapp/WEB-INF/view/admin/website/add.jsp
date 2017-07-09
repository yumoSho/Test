<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
</m:h>
<%@include file="../../include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/website/index"  title="">平台管理</a> &gt;
            <a href="${ctx}/admin/website/index"  title="">平台设置</a> &gt;
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/website/save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>
                    <tr>
                        <th width="130" align="right">网站名称：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="name" value="${website.name}" maxlength="30"/>
                        </td>
                    </tr>
                    <%--<tr>
                        <th width="130" align="right">网站设置名称：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="intro" value="${website.intro}" maxlength="100">

                        </td>
                    </tr>--%>
                    <tr>
                        <th width="130" align="right">版权信息：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="description" value="${website.description}" maxlength="201">

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">客服热线：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="phone" value="${website.phone}" maxlength="100">

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">客服QQ：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="qq" value="${website.qq}" maxlength="100">

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">网站地址：<%--<b class="color-red">*</b>--%></th>
                        <td>
                            <input type="text" name="url" value="${website.url}" maxlength="100">
                            <span style="color: red;">如：http://www.baidu.com</span>
                        </td>
                    </tr>

                    <tr>
                        <th width="130" align="right" valign="top">网站前台LOGO：<b class="color-red"></b></th>
                        <td>
                            <input type="hidden" name="validateImg" value="${not empty website.logo?'1':''}">
                            <div class="uploader-1" id="">
                                <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传244*94像素的图片</span></div>
                                <div class="upload-single">
                                    <div class="actions">
                                        <span id="logo" name="logo" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                        <script type="text/javascript">
                                            $(function () {
                                                Uploader2({
                                                    file_data_name: 'file',
                                                    browse_button: 'logo',
                                                    url:'${ctx}/storage/images/preupload',
                                                    policy: true,
                                                    download:false,
                                                    name: 'logo',
                                                    list: 'upload-queue-banner',
                                                    filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                    mode: 't',
                                                    max_file_count: '1',
                                                    max_file_size: '1m'
                                                }).bind('FileFiltered', function (up, file) {
                                                    var el = file.el,
                                                            name = el ? el.getAttribute('data-name') || el.value : file.name;
                                                    //验证图片必填
                                                    $("input[name='validateImg']").val("1").siblings(".error").empty();
                                                    $("input[name='validateImg']").valid();
                                                }).bind('FilesRemoved', function (up, file) {
                                                    $("input[name='validateImg']").val("");
                                                    $("input[name='validateImg']").valid();
                                                });
                                            });
                                        </script>
                                    </div>
                                    <div class="upload-input">请选择...</div>
                                    <ul class="upload-queue" id="upload-queue-banner">
                                        <li>
                                            <c:if test="${not empty website.logo}">
                                                <input type="hidden" name="logo" value="${website.logo}" data-saved-url="${ctx}/${website.logo}"/>
                                            </c:if>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>



                    <tr>
                        <th width="130" align="right" valign="top">网站默认图片：<b class="color-red"></b></th>
                        <td>
                            <input type="hidden" name="validateImg1" value="${not empty website.defaultImage?'1':''}">
                            <div class="uploader-1" >
                                <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传607*534像素的图片</span></div>
                                <div class="upload-single">
                                    <div class="actions">
                                        <span id="defaultImage" name="defaultImage" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                        <script type="text/javascript">
                                            $(function () {
                                                Uploader2({
                                                    file_data_name: 'file',
                                                    browse_button: 'defaultImage',
                                                    url:'${ctx}/storage/images/preupload',
                                                    policy: true,
                                                    download:false,
                                                    name: 'defaultImage',
                                                    list: 'upload-queue',
                                                    filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                    mode: 't',
                                                    max_file_count: '1',
                                                    max_file_size: '1m'
                                                }).bind('FileFiltered', function (up, file) {
                                                    var el = file.el,
                                                            name = el ? el.getAttribute('data-name') || el.value : file.name;
                                                    //验证图片必填
                                                    $("input[name='validateImg1']").val("1").siblings(".error").empty();
                                                    $("input[name='validateImg1']").valid();
                                                }).bind('FilesRemoved', function (up, file) {
                                                    $("input[name='validateImg1']").val("");
                                                    $("input[name='validateImg1']").valid();
                                                });
                                            });
                                        </script>
                                    </div>
                                    <div class="upload-input">请选择...</div>
                                    <ul class="upload-queue" id="upload-queue">
                                        <li>
                                            <c:if test="${not empty website.defaultImage}">
                                                <input type="hidden" name="logo" value="${website.defaultImage}" data-saved-url="${ctx}/${website.defaultImage}"/>
                                            </c:if>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td align="left">
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel"  type="button" value="取消" onclick=""/>
	                            </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/website/add.js"></script>

