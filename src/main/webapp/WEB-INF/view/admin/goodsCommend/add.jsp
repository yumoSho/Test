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
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="save" method="post">

                <input type="hidden" name="TOKEN" value="${TOKEN}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>
                <tr>
                    <th width="130" align="right">分类：<b class="color-red">*</b></th>
                    <td >
                        <select name="category.id" data-placeholder="请选择" class="select chosen-select ni">
                            <option value="">请选择分类</option>
                            <c:forEach items="${categories}" var="c" >
                                <option value="${c.id }">${c.name }</option>
                            </c:forEach>
                        </select>
                        <label for="category.id" generated="true" class="error"></label>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">分类图片<b class="color-red">*</b></th>
                    <td >
                        <input type="hidden" name="validateImg"/>
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
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <label for="validateImg" generated="true" class="error"></label>
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">所属楼层：<b class="color-red">*</b></th>
                    <td >
                        <select name="floor" data-placeholder="请选择" class="select chosen-select ni">
                            <option value=""></option>
                            <option value="1">1F</option>
                            <option value="2">2F</option>
                            <option value="3">3F</option>
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
                        <span>前台展示只展示商品排序前八</span>
                        <div>
                            <table class="table-module01 table-module02" id="table-discount" style="display: none">
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

                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>


                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td >
                        <span id="sort-spinner" class="spinner">
                            <input id="sort" name="sort" class="input" value="1">
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
                <table id="datagrid" style="width: 598px"></table>
                <div id="pagination" style="width: 598px"></div>
            </div>
        </div>
        <!-- //End 主内容区 -->
    </div>
  </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript">

        $(function(){
            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/flowBanner/index";
            })

            $(".chosen-select").on('change', function(evt, params) {
                $(this).valid();
            });
            $('.chosen-select').chosen({});
            var goodsId = new Array();
            $('#sel-goods').click(function () {
                layer.open({
                    zIndex: 10,
                    title: '选择商品',
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['600px', '400px'], //宽高
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
                            var categoryName = $row['product.category'];
                            if(goodsId.indexOf(id)>-1){
                                /* Glanway.Messager.alert("提示", "这件【"+title+"】商品重复");*/
                                return /*false*/;
                            }
                            goodsId.push(id);
                            $(".content-discount").append("<tr style='text-align: center'><td height='30px'><a href=\"javasrcipt:void(0)\" class=\"delTr\">删除</a></td><td  height='30px'><p>"+sn+"</p></td><td  height='30px'><p>"+title+"</p></td><td  height='30px'><p>"+categoryName+
                                    "</p></td><td  height='30px'><input type=\'hidden\' name='categoryGoodses["+(goodsId.length-1)+"].goods.id' value='"+id+"'/>" +
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
                    $("#table-fix").hide();
                }
            }


            //验证
            $("#form").validate({
                rules:{
                    'category.id':{
                    	 required: true,
                        remote: {
                            url: 'check',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                id: function () {
                                    return $('input[name=id]').val();
                                },
                                cid: function () {
                                    return $('input[name="category.id"]').val();
                                }
                            }
                        }
                    },
                    validateImg:{
                        required:true
                    },
                    pic:{
                    	required: true,
                        maxlength: 500
                    },
                    sort:{
                    	required: true,
                    	digits:true,
                    	max: 999
                    },
                    floor:{
                        required: true
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
            
            $('#form').submit(function() {
            	
            	});
        });

    </script>

