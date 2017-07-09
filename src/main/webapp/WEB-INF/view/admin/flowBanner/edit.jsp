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
            <a href="${ctx}/admin/flowBanner/index" ; title="">轮播图管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/flowBanner/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${flowBanner.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">图片名称：<b class="color-red">*</b></th>
                    <td >
                      <input type="text"  name="title" value="${flowBanner.title }" />
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">平台模块：<b class="color-red">*</b></th>
                    <td >
                        <select id="dicId" name="platformDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择</option>
                            <c:forEach items="${dictionaries_plat}" var="pt" >
                                <option value="${pt.id }" <c:if test="${flowBanner.platformDictionary.id eq pt.id }">selected="selected"</c:if>>${pt.dicName }</option>

                            </c:forEach>
                        </select>
                        <label for="dicId" generated="true" class="error"></label>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">页面名称：<b class="color-red">*</b></th>
                    <td >
                        <select id="pdId" name="pageDictionary.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择</option>
                            <c:forEach items="${dictionaries_page}" var="pg" >
                                <option value="${pg.id }" <c:if test="${flowBanner.pageDictionary.id eq pg.id }">selected="selected"</c:if>>${pg.dicName }</option>

                            </c:forEach>
                        </select>
                        <label for="pdId" generated="true" class="error"></label>
                        
                    </td>
                </tr>

                <tr>
                    <th width="130" align="right" valign="top">LOGO：<b class="color-red"></b></th>
                    <td>
                        <input type="hidden" name="validateImg" value="${not empty flowBanner.image?'1':''}">
                        <div class="uploader-1" id="single-drop-zone">
                            <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传1349*442像素的图片</span></div>
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
                                        <c:if test="${not empty flowBanner.image}">
                                            <input type="hidden" name="image" value="${flowBanner.image}" data-saved-url="${ctx}/${flowBanner.image}"/>
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
                           <input name="url" value="${flowBanner.url }" class="text_sketch" type="text">
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">有效期开始：<b class="color-red">*</b></th>
                    <td >
                        <fmt:formatDate var="beginDate" value="${flowBanner.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <input type="text" name="beginDate" value="${beginDate}" readonly id="beginDate" style="border:1px solid #d4d4d4;width: 195px;height:25px;padding-left: 4px" class="Wdate valid" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00', maxDate:'#F{$dp.$D(\'endDate\')||\'2099-12-31\'}'})">
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">有效期结束：<b class="color-red">*</b></th>
                    <td >
                        <fmt:formatDate var="endDate" value="${flowBanner.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <input type="text" readonly value="${endDate}" name="endDate" id="endDate" style="border:1px solid #d4d4d4;width: 195px;height: 25px;padding-left: 4px" class="Wdate valid" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59', minDate:'#F{$dp.$D(\'beginDate\')}',maxDate:'2099-12-31'})">
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">是否启用：<b class="color-red">*</b></th>
                    <td >
                        <input type="checkbox" name="isShow" value="1" <c:if test="${flowBanner.isShow == 1}">checked</c:if> > 是
                    </td>
                </tr>
                
                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td >
                        <span id="sort-spinner" class="spinner">
                                <input id="sortNum" name="sortNum" class="input" value="${flowBanner.sortNum }">
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
            <%--
        </div>
        
        --%>
    </div>
    <!-- //End 主内容区 -->
</div>
</div>
<m:f/>
<-- 图片上传校验 -->
<script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
<--富文本初始化js-->
<script type="text/javascript" src="${ctx}/js/admin/initeditor.js"></script>
<script type="text/javascript">

$(function(){
    $(".btn-cancel-wrap input").click(function() {
        window.location.href = "${ctx}/admin/flowBanner/index";
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
            url:{
            	required: true,
            	url:true
            },
            'platformDictionary.id':{
                required:true
            },
            'pageDictionary.id':{
                required:true
            },
            beginDate:{
                required:true
            },
            endDate:{
                required:true
            }
        },
        messages:{
            validateImg: {
                required: "图片不能为空"
            }
        },errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function(form){

            $("#form").find(":submit").attr("disabled", true).attr("value",
                    "保存中...").css("letter-spacing", "0");
            form.submit();
        }

});
});

</script>


