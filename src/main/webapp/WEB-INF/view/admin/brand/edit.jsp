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
            <a href="${ctx}/admin/brand/index" title="品牌管理">品牌管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="../update" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody class="tab1">
                    <tr>
                        <th width="130" align="right" valign="top">名称：<b class="color-red">*</b></th>
                        <td valign="top"><input type="text" name="name" value="${m.name}"></td>
                        <input type="hidden"  name="id" value="${m.id}"/>
                        <input type="hidden" name="TOKEN" value="${TOKEN}">
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">别名：<b class="color-red">&nbsp;</b></th>
                        <td valign="top"><input type="text" name="alias" value="${m.alias}"></td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">LOGO：<b class="color-red"></b></th>
                        <td>
                            <div class="uploader-1" id="single-drop-zone">
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
                                                    list: 'upload-queue',
                                                    filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                    mode: 't',
                                                    max_file_count: '1',
                                                    max_file_size: '1m'
                                                });
                                            });
                                        </script>
                                    </div>
                                    <div class="upload-input">请选择...</div>
                                    <ul class="upload-queue" id="upload-queue">
                                        <li>
                                            <c:if test="${not empty m.logo}">
                                                <input type="hidden" name="logo" value="${m.logo}" data-saved-url="${ctx}/${m.logo}"/>
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
                                <input id="sort" name="sort" class="input" value="${m.sort}">
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
                            <label><input type="radio" name="enabled" value="1" ${m.enabled ? 'checked':''} style="margin-top: 10px;">是</label>
                            <label><input type="radio" name="enabled" value="0" ${m.enabled ? '':'checked'} style="margin-top: 10px;">否</label>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">是否推荐：<b class="color-red">*</b></th>
                        <td valign="top">
                            <label><input type="radio" name="recommend" value="1" ${m.recommend ? 'checked':''}  style="margin-top: 10px;">是</label>
                            <label><input type="radio" name="recommend" value="0"  ${m.recommend ? '':'checked'} style="margin-top: 10px;">否</label>
                            <label style="color: red">* 品牌启用，才可以推荐</label>
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
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript">
        $(function(){
            $.validator.addMethod("recommendValidate", function(value, element) {
                var enable = $("input[name='enabled']:checked").val();
                if(enable=='0'){
                    $("input[name='recommend']").attr("checked",false);
                    $("input[name='recommend'][value='0']").attr("checked",true);
                }
                var recommendVal = $("input[name='recommend']:checked").val();
                return this.optional(element) || ( (enable=="1" && recommendVal=='0')||(enable=="1" && recommendVal=='1')||(enable=="0" && recommendVal=='0'));
            }, "开启品牌才可以推荐");
        })
            // form validation
            $('form:last').validate({
                rules: {
                    name: {
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
                                    return $('input[name=name]').val();
                                }
                            }
                        }
                    },
                    alias: {
                        maxlength: 12
                    },
                    sort: {
                        required: true,
                        digits: true,
                        max: 999
                    },
                    recommend:{
                        required: true,
                        recommendValidate:true
                    },
                    enabled:{
                        required: true
                    }
                },
                errorPlacement: function(error, element) {
                    if(element.attr("name")=="enabled" || element.attr("name")=='recommend') {
                        error.appendTo(element.parent().parent())
                    }else{
                        error.appendTo(element.parent());
                    }
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