<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <style>
        .upload-entry-icon img{
            width:110px;height:110px;
        }
    </style>
</m:h>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
            <a href="${ctx}/admin/label/index" title="标签管理">标签管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="../update" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody class="tab1">
                    <tr>
                        <th width="130" align="right" valign="top">名称：<b class="color-red">*</b></th>
                        <td valign="top"><input type="text" name="labelName" value="${label.labelName}"></td>
                        <input type="hidden"  name="id" value="${label.id}"/>
                        <input type="hidden" name="TOKEN" value="${TOKEN}">
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">标签图片：<b class="color-red"></b></th>
                        <td>
                            <input type="hidden" name="validateImg" value="${not empty label.labelPath?'1':''}">
                            <div class="uploader-1" id="single-drop-zone">
                                <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传50*42像素的图片</span></div>
                                <div class="upload-single">
                                    <div class="actions">
                                        <span id="labelPath" name="labelPath" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                        <script type="text/javascript">
                                            $(function () {
                                                Uploader2({
                                                    file_data_name: 'file',
                                                    browse_button: 'labelPath',
                                                    url:'${ctx}/storage/images/preupload',
                                                    policy: true,
                                                    download:false,
                                                    name: 'labelPath',
                                                    list: 'upload-queue',
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
                                    <ul class="upload-queue" id="upload-queue">
                                        <li>
                                            <c:if test="${not empty label.labelPath}">
                                                <input type="hidden" name="labelPath" value="${label.labelPath}" data-saved-url="${ctx}/${label.labelPath}"/>
                                            </c:if>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">排序：<b class="color-red">*</b></th>
                        <td valign="top">
                            <span id="sort-spinner" class="spinner">
                                <input id="sort" name="sort" class="input" value="${label.sort}">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span>
                            <script>
                                $('#sort-spinner').spinner({
                                    max: 999
                                    , min: 0
                                    , step: 1
                                    , allowEmpty: true
                                    , minusBtn: '.btn-down'
                                    , plusBtn: '.btn-up'
                                });
                            </script>
                            <%-- <input type="text" name="sort" autocomplete="off">--%></td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">是否启用：<b class="color-red">*</b></th>
                        <td valign="top">
                            <label><input type="radio" name="disabled" value="0" ${label.disabled ? '':'checked'} style="margin-top: 10px;">是</label>
                            <label><input type="radio" name="disabled" value="1" ${label.disabled ? 'checked':''} style="margin-top: 10px;">否</label>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">&nbsp;</th>
                        <td>
								<span class="btn-sure-wrap">
									<input class="btn-sure btn-edit" type="submit" value="保存"/>
								</span>
								<span class="btn-cancel-wrap">
                                    <a href="../index" class="btn-cancel">取消</a>
                                </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <%-- TAB --%>
        </div>
        <!-- //End 主内容区 -->
    </div>

    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/mustache.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript">

        // form validation
        $('form:last').validate({
            rules: {
                labelName: {
                    required: true,
                    minlength: 1,
                    maxlength: 20,
                    remote: {
                        url: '../check',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: function () {
                                return $('input[name=id]').val();
                            },
                            name: function () {
                                return $('input[name=labelName]').val();
                            }
                        }
                    }
                },
                sort: {
                    required: true,
                    digits: true,
                    max: 999
                },
                validateImg: {
                    required: true
                },
                disabled:{
                    required: true
                }
            },
            messages:{
                validateImg: {
                    required: "图片不能为空"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent());
            },
            submitHandler: function(form){
                if(imgValidate.validateImg()){
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }
            }
        });
    </script>
<m:f/>