<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@ include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/website/index" title="平台管理">平台管理</a> &gt;
            <a href="${ctx}/admin/advertisement/index" title="">广告管理</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">图片名称：<b class="color-red">*</b></th>
                    <td >
                      <input type="text"  name="title" />
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">平台模块：<b class="color-red">*</b></th>
                    <td >
                        </select>
                        <select id="dicId" name="deviceType" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${dictionaries_pt}" var="pt" >
                                <option value="${pt.dicCode }">${pt.dicName }</option>
                            </c:forEach>
                        </select>
                        <label for="dicId" generated="true" class="error"></label>

                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">广告位置：<b class="color-red">*</b></th>
                    <td >
                        <input type="hidden" name="posName" id="posName" value="">
                        <select id="ggId" name="pos" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${posList}" var="pos" >
                                <option value="${pos.dicCode }">${pos.dicName }</option>
                            </c:forEach>
                        </select>
                        <label for="dicId" generated="true" class="error"></label>

                    </td>
                </tr>

                <tr>
                    <th width="130" align="right" valign="top">LOGO：<b class="color-red"></b></th>
                    <td>
                        <input type="hidden" name="validateImg"/>
                        <div class="uploader-1" id="single-drop-zone">
                            <div class="upload-desc">请上传图片<%--<span style="color:#dd0000">请上传244*94像素的图片</span>--%></div>
                            <div class="upload-single">
                                <div class="actions">
                                    <span id="image" name="image" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                    <script type="text/javascript">
                                        $(function () {
                                            Uploader2({
                                                file_data_name: 'file',
                                                browse_button: 'image',
                                                url:'${ctx}/storage/images/preupload',
                                                policy: true,
                                                download:false,
                                                name: 'image',
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
                                        <c:if test="${not empty advertisement.image}">
                                            <input type="hidden" name="image" value="${advertisement.image}" data-saved-url="${ctx}/${advertisement.image}"/>
                                        </c:if>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
                 <tr>
                    <th width="130" align="right">广告链接：<b class="color-red">*</b></th>
                    <td >
                           <input name="field1" value="http://" class="text_sketch" type="text">
                        
                    </td>
                </tr> 
                <tr>
                    <th width="130" align="right">是否显示：<b class="color-red">*</b></th>
                    <td >
                        <input type="checkbox" name="isShow" value="1" checked> 是
                    </td>
                </tr>
                
                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td >
                         <input id="u1156_input" name="sortNum" value="1" class="text_sketch" type="text">
                        
                    </td>
                </tr>
                 <tr>
                    <td></td>
                    <td align="left">
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消" onclick=""/>
	                            </span>
                    </td>
                </tr>
                </tbody>
            </table>
            </form>
        </div>
        <!-- //End 主内容区 -->
    </div>
  </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>

    <script type="text/javascript">

        $(function(){
            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/advertisement/index";
            });
            $('.chosen-select').chosen({});
            //验证
            $("#form").validate({
                rules:{
                    title:{
                    	 required: true,
                         maxlength: 20
                          },
                    image:{
                    	required: true,
                        maxlength: 500
                          },
                    sortNum:{
                    	required: true,
                    	digits:true,
                    	max: 999
                    },
                    validateImg: {
                        required: true
                    },
                    field1:{
                        required: true,
                        url:true
                    },
                    deviceType:{
                        required:true
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
                var posName = $("#ggId").find('option:selected').text();
                $("#posName").val(posName);
                $("#form").find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                form.submit();
            }

        });
        });

    </script>

