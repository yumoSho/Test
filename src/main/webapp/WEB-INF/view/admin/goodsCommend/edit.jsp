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
            <a href="${ctx}/admin/activity/index" title="营销管理">营销管理</a> &gt;
            <a href="${ctx}/admin/goods-commend/index" title="分类商品推荐">分类商品推荐</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="../update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${detail.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">分类：<b class="color-red">*</b></th>
                    <td >
                        <select name="category.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择分类</option>
                            <c:forEach items="${categories}" var="c" >
                                <option value="${c.id }" <c:if test="${detail.category.id eq c.id}">selected</c:if> >${c.name }</option>
                            </c:forEach>
                        </select>
                        <label for="category.id" generated="true" class="error"></label>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">分类图片<b class="color-red">*</b></th>
                    <td >
                        <input type="hidden" name="validateImg" value="${not empty detail.pic?'1':''}">
                        <div class="uploader-1" id="single-drop-zone">
                            <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传244*94像素的图片</span></div>
                            <div class="upload-single">
                                <div class="actions">
                                    <span id="pic" name="pic" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                    <script type="text/javascript">
                                        $(function () {
                                            Uploader2({
                                                file_data_name: 'file',
                                                browse_button: 'pic',
                                                url:'${ctx}/storage/images/preupload',
                                                policy: true,
                                                download:false,
                                                name: 'pic',
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
                                        <c:if test="${not empty detail.pic}">
                                            <input type="hidden" name="logo" value="${detail.pic}" data-saved-url="${ctx}/${detail.pic}"/>
                                        </c:if>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <th width="130" align="right">所属楼层：<b class="color-red">*</b></th>
                    <td >
                        <select name="floor" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="" ></option>
                            <option value="1" <c:if test="${detail.floor eq 1}">selected</c:if>>1F</option>
                            <option value="2" <c:if test="${detail.floor eq 2}">selected</c:if>>2F</option>
                            <option value="3" <c:if test="${detail.floor eq 3}">selected</c:if>>3F</option>
                        </select>
                        <label for="floor" generated="true" class="error"></label>
                    </td>
                </tr>

                <tr>
                    <th width="130" align="right" valign="top">商品：<b class="color-red">*</b></th>
                    <td valign="top">
                                <span class="btn-sure-wrap">
									<input class="btn-sure" type="button" id="sel-goods" value="选择商品"/>
								</span>
                        <div>
                            <table class="table-module01 table-module02" id="table-discount">
                                <thead>
                                <tr style="text-align: center">
                                    <th width="5%">操作</th>
                                    <th width="10%">编号</th>
                                    <th width="45%">商品标题</th>
                                    <th width="20%">分类</th>
                                    <th width="20%">排序</th>
                                </tr>
                                </thead>
                                <tbody class="content-discount">
                                <c:forEach items="${detail.categoryGoodses}" var="ag" varStatus="f">
                                    <tr style="text-align: center">
                                        <td><a href="javasrcipt:void(0)" class="delTr">删除</a></td>
                                        <td><p>${ag.goods.sn}</p></td>
                                        <td><p>${ag.goods.title}</p></td>
                                        <td><p>${ag.goods.product.category.name}</p></td>
                                        <td><input type="hidden" name="categoryGoodses[${f.index}].goods.id" value="${ag.goods.id}">
                                                    <span class="spinner"><input name="categoryGoodses[${f.index}].sort" class="input required" value="${ag.sort}">
                                                        <span class="spin-buttons"><span class="btn-up"></span><span class="btn-down"></span></span></span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>


                
                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td >
                        <span id="sort-spinner" class="spinner">
                                <input id="sort" name="sort" class="input" value="${detail.sort }">
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
            <div id="goods-list" style="display: none;width:590px">
                <table id="datagrid" ></table>
                <div id="pagination" ></div>
            </div>
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
<script type="text/javascript">

$(function(){

    $(".delTr").each(function(i,e){
        $(this).click(delTr);
    });
    var goodsId = new Array();
    <c:forEach items="${detail.categoryGoodses}" var="c" varStatus="j">
    goodsId[${j.index}]="${c.goods.id}";
    </c:forEach>

    $(".btn-cancel-wrap input").click(function() {
        window.location.href = "${ctx}/admin/flowBanner/index";
    });
    $('.chosen-select').chosen({});
    $(".chosen-select").on('change', function(evt, params) {
        $(this).valid();
    });

    $('#sel-goods').click(function () {
        layer.open({
            zIndex: 10,
            title: '选择商品',
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['594px', '400px'], //宽高
            shadeClose: true,
            content: $('#goods-list'),
            btn: ['确定', '取消'],
            cancel: function (index) {
                $g.GridUnload()
                layer.close(index);
            },
            yes: function (index) {
                var keys = $g.jqGrid('getGridParam', 'selarrrow'), i;
                if(keys.length==0){
                    Glanway.Messager.alert("提示", "请选择至少一个商品");
                    return false;
                }
                $.each(keys,function(i,row){
                    var $row =$g.jqGrid('getRowData',row);
                    var id = $row.id;
                    var sn = $row.sn;
                    var title = $row.title;
                    debugger;
                    var categoryName = $row['product.category'];
                    if(goodsId.indexOf(id)>-1){
                        return
                    }
                    goodsId.push(id);
                    $(".content-discount").append("<tr style='text-align: center'><td><a href=\"javasrcipt:void(0)\" class=\"delTr\">删除</a></td><td>"+sn+"</td><td><p>"+title+"</p></td><td>"+categoryName+
                            "</td><td><input type=\'hidden\' name='categoryGoodses["+(goodsId.length-1)+"].goods.id' value='"+id+"'/>" +
                            "<span  class=\"spinner\"><input  name='categoryGoodses["+(goodsId.length-1)+"].sort' class=\"input required\" value=\"1\"><span class=\"spin-buttons\"><span class=\"btn-up\"></span><span class=\"btn-down\"></span></span></span>" +
                            "</td></tr>");
                    $("#table-discount").show();
                    $('.spinner').spinner({
                        max: 999999
                        , min: 0
                        , step: 1
                        , allowEmpty: true
                        , minusBtn: '.btn-down'
                        , plusBtn: '.btn-up'
                    });

                });
                $(".delTr").click(delTr);
                var row = $g.jqGrid('getRowData', keys[0]);;
                layer.close(index);
            }
        });
        var $g = $('#datagrid').jqGrid({
            url: '${ctx}/admin/activity/get-goodses',
            datatype: 'json',
            colNames: [ 'id', '商品编号', '商品名称', '所属分类','售价'],
            colModel: [

                {name: 'id', index: 'id', hidden: true, key: true, align: 'center'},
                {name: 'sn', index: 'sn', template: 'text', align: 'center', searchoptions: {sopt: ["cn"]}},
                {name: 'title', index: 'title', template: 'text', align: 'center'},
                {name: 'product.category', index: 'pCName', template: 'text', align: 'center', sortable: false,formatter:function(v){
                    return v.name;
                }},
                {name: 'price', index: 'price', template: 'text', align: 'center', sortable: false}
            ],
            multiselect: true,
            autowidth: true,
            shrinkToFit: true,
            height: 'auto',
            pager: '#pagination',
            sortname: 'id',
            sortorder: 'asc'
        });
    });

    function delTr(){
        goodsId.shift($(this).parent().parent().find("input[name$='goods.id']").val());
        $(this).parent().parent().remove();
        $(".content-discount").find('tr').each(function (i, tr) {
            $(tr).find('input[name]:enabled').each(function (j, el) {
                $(el).attr('name', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
                $(el).siblings("label").attr('for', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
            });
        });
        if(goodsId.length==0){
            $("#table-discount").hide();
        }
    }

    //验证
    $("#form").validate({
        rules:{
            title:{
            	 required: true,
                 maxlength: 20
                  },
            validateImg:{
                required:true
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
    submitHandler: function(form){

        var discount = $(".content-discount").find("tr").length;
        var fix = $(".content-fix").find("tr").length;
        if(discount<=0) {
            Glanway.Messager.alert("提示", "请选择商品");
            return false
        }
        $("#form").find(":submit").attr("disabled", true).attr("value",
                "保存中...").css("letter-spacing", "0");
        form.submit();
    }

});
});

</script>


