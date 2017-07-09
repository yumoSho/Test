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
            <a href="${ctx}/admin/supportCategory/index"; title="">内容管理</a> &gt;
            <a href="${ctx}/admin/news/index" ; title="">资讯管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/news/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${news.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">类别：<b class="color-red">*</b></th>
                    <td >
                        <input type="hidden" name="newsTypeName" id="newsTypeName">
                        <select id="supportCategory_category" name="newsTypeId" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <c:forEach items="${typeList}" var="t" >
                                <option value="${t.id }" <c:if test="${t.id==news.newsTypeId }">selected="true"</c:if>>${t.name }</option>

                            </c:forEach>
                        </select>
                        <label for="supportCategory_category" generated="true" class="error"></label>
                        <script>
                            $('.chosen-select').chosen({});
                        </script>

                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">名称：<b class="color-red">*</b></th>
                    <td >
                      <input name="title" class="text_sketch" type="text" value="${news.title }">
                        
                    </td>
                </tr>

                <tr>
                    <th width="130" align="right" valign="top">LOGO：<b class="color-red"></b></th>
                    <td>
                        <input type="hidden" name="validateImg" value="${not empty news.image?'1':''}">
                        <div class="uploader-1" id="single-drop-zone">
                            <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传179*144像素的图片</span></div>
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
                                        <c:if test="${not empty news.image}">
                                            <input type="hidden" name="image" value="${news.image}" data-saved-url="${ctx}/${news.image}"/>
                                        </c:if>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">内容：<b class="color-red">*</b></th>
                    <td >
                           <textarea id="editor_" name="content" style="height: 400px">${news.content}</textarea>

                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">属性：</th>
                    <td>
                        <input name="isShow" value="1" class="text_sketch" <c:if test="${news.isShow}">checked</c:if> type="checkbox">显示&nbsp;
                        <input name="isHot" value="1" class="text_sketch" <c:if test="${news.isHot}">checked</c:if> type="checkbox">首页推荐
                    </td>
                </tr>
               <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                         <%--<input name="sortNum" value="${news.sortNum }" class="text_sketch" type="text">--%>
                    <span id="sort-spinner" class="spinner">
<input id="sortNum" name="sortNum" class="input" value="${news.sortNum }">
<span class="spin-buttons">
<span class="btn-up"></span>
<span class="btn-down"></span>
</span>
</span>
                        <script>
                            $('#sort-spinner').spinner({
                                min: 0
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
       
        
        --%>
    </div>
    <!-- //End 主内容区 -->
</div>
</div>
<m:f/>
<-- 图片上传校验 -->
<script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
<script type="text/javascript">

$(function(){
    $(".btn-cancel-wrap input").click(function() {
        window.location.href = "${ctx}/admin/news/index";
    });

    //验证
    $("#form").validate({
        rules:{
            newsTypeId:{
                required:true
            },
            title:{
            	 required: true,
                 maxlength: 20
                  },
            validateImg:{
                required:true
            },
            content:{
            	required: true,
                maxlength: 10000
                  },
            sortNum:{
            	required: true,
            	digits:true,
            	max: 999
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
        var newsTypeName = $('select[name=newsTypeId]').find("option:selected").text();
        $('#newsTypeName').val(newsTypeName);
        if (imgValidate.validateImg()) { //图片验证
            $("#form").find(":submit").attr("disabled", true).attr("value",
                    "保存中...").css("letter-spacing", "0");
            form.submit();
        }
    }

});
});

</script>


