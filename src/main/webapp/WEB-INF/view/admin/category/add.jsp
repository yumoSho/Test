<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
</m:h>
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
            <a href="${ctx}/admin/category/index" title="商品分类">商品分类</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">

            <div class="tab-ct-wrap">
                <form action="save" method="post" id="cat-form">
                    <table class="table-module01" cellpadding="0" cellspacing="0">
                        <tbody class="tab1">
                        <tr>
                            <th width="130" align="right" valign="top">名称：<b class="color-red">*</b></th>
                            <td valign="top"><input type="text" name="name"></td>
                            <input type="hidden" name="TOKEN" value="${TOKEN}">
                        </tr>

                        <tr>
                            <th width="130" align="right" valign="top">上级分类：</th>
                            <td valign="top">
                                <input type="text" name="parent.name" class="parentName" readonly>
                                <input type="hidden" name="parent.id" class="parentId">
                                <input type="hidden" name="path" class="path">
                                <input type="hidden" name="pathNames" class="pathNames">
                                <button class="btn-sure" id="btn-cat-sel" type="button">选择分类</button>
                                <button class="btn-sure" id="btn-cat-cal" type="button">取消分类</button>
                                <div id="cate-sel-dlg" style="display: none">
                                    <ul id="cats-tree" class="ztree" style="background-color: #fff;    height: 290px;overflow-y: auto;"></ul>
                                </div>
                            </td>
                        </tr>


                        <tr>
                            <th width="130" align="right" valign="top">分类图标：<b class="color-red"></b></th>
                            <td>
                                <div class="uploader-1" id="single-drop-zone">
                                    <div class="upload-desc">请上传图片<span style="color:#dd0000">一级PC端分类请上传22*100像素的PNG图片,三级手机端分类请上传140*150像素图片</span></div>
                                    <div class="upload-single">
                                        <div class="actions">
                                            <span id="picture" name="picture" class="input" download="false" style="position: relative; z-index: 1;"><a href="javascript:void(0);">选择文件</a></span>
                                            <script type="text/javascript">
                                                $(function () {
                                                    Uploader2({
                                                        file_data_name: 'file',
                                                        browse_button: 'picture',
                                                        url:'${ctx}/storage/images/preupload',
                                                        policy: true,
                                                        download:false,
                                                        name: 'picture',
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
                                                <c:if test="${not empty model.picture}">
                                                    <input type="hidden" name="logo" value="${model.picture}" data-value="${ctx}/${model.picture}"/>
                                                </c:if>

                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">模型：<b class="color-red">*</b></th>
                            <td valign="top">
                                <select id="model" name="model.id" data-placeholder="请选择" class="select chosen-select ni">
                                    <option value=""></option>
                                    <c:forEach var="m" items="${models}">
                                        <option value="${m.id}">${m.name}</option>
                                    </c:forEach>
                                </select>
                                <label for="model" generated="true" class="error"></label>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">品牌：<b class="color-red">*</b></th>
                            <td valign="top">
                                <select id="brands" name="brands" class="select chosen-select " data-placeholder="请选择关联品牌" multiple="multiple">
                                    <c:forEach var="brand" items="${brands}">
                                        <option value="${brand.id}">${brand.name}</option>
                                    </c:forEach>
                                </select>
                                <label for="brands" generated="true" class="error"></label>
                            </td>
                        </tr>

                        <tr>
                            <th width="130" align="right" valign="top">排序：<b class="color-red">*</b></th>
                            <td valign="top">
                                <span id="sort-spinner" class="spinner">
                                <input id="sort" name="sort" class="input" value="">
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
                            <th width="130" align="right" valign="top">是否可见：<b class="color-red">*</b></th>
                            <td>
                                <label><input name="visible" type="radio" value="1" checked="checked">可见</label>
                                <label><input name="visible" type="radio" value="0" >隐藏</label>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">&nbsp;</th>
                            <td>
                                <span class="btn-sure-wrap">
                                    <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                </span>
                                <span class="btn-cancel-wrap">
                                    <a class="btn-cancel" href="${ctx}/admin/category/index">取消</a>
                                </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <%-- TAB CONTENT END --%>
        </div>
        <!-- //End 主内容区 -->
    </div>

    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
    <script type="text/javascript">
        $(function(){
            $('.chosen-select').chosen({});
            $('#btn-cat-sel').click(function() {
                var sel= null;
                var setting = {
                    treeId : "cats-tree",
                    data : {
                        simpleData : {
                            enable : true,
                            idKey : "id",
                            pidKey : "pId",
                            rootPId : null
                        }
                    },
                    callback: {
                        beforeClick: beforeClick
                    }
                };
                function beforeClick(treeId, treeNode, clickFlag) {
                    $(".parentName").val(treeNode.name);
                    $(".parentId").val(treeNode.id);
                    $(".path").val(treeNode.path);
                    $(".pathNames").val(treeNode.pathNames);
                    sel=true;
                }
                $ztree=null;
                var treeNodes;
                $.ajax({
                    async:true,//是否异步
                    cache:false,//是否使用缓存
                    type:'POST',//请求方式：post
                    //data:{"roleId":null},
                    dataType:'json',//数据传输格式：json
                    url:"${ctx}/admin/category/tree",//请求的action路径
                    error:function(){
                        alert('亲，请求失败！');
                    },
                    success:function(data){
                        treeNodes = data;
                        $.fn.zTree.init($("#cats-tree"), setting, treeNodes);
                        $ztree =$.fn.zTree.getZTreeObj("cats-tree");
                    }
                });
                layer.open({
                    zIndex: 10,
                    title: '选择上级分类',
                    type: 1,
                    skin: 'layer-ext-alertpop', //加上边框
                    area: ['600px', '400px'], //宽高
                    shadeClose: true,
                    content: $('#cate-sel-dlg'),
                    btn: ['确定', '取消'],
                    cancel: function (index) {
                        $ztree.destroy();
                        layer.close(index);
                    },
                    yes: function (index) {
                        var selectedNode = $ztree.getSelectedNodes();
                        if(selectedNode && selectedNode.length!=0){
                            $ztree.destroy();
                            layer.close(index);
                        }else{
                            Glanway.Messager.alert("提示", "请选择一个父级分类");
                        }
                    }
                });
            });
            /*取消分类*/
            $("#btn-cat-cal").click(function(){
                $(".parentName").val("");
                $(".parentId").val("");
                $(".path").val("");
                $(".pathNames").val("");
            })

        })

        $('#cat-form').validate({
            rules: {
                "name": {
                    required: true,
                    minlength: 2,
                    maxlength: 20,
                    remote: {
                        url: 'check',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: function () {
                                return $('[name=id]').val();
                            },
                            name: function () {
                                return $('[name=name]').val();
                            }
                        }
                    }
                },
                brands:{
                    required: true,
                },
                "model.id": "required",
                "sort": {
                    required: true,
                    digits: true,
                    max: 999
                }
            },
            submitHandler: function(form){
                $(form).find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                form.submit();
            }
        });
    </script>
    <m:f/>

