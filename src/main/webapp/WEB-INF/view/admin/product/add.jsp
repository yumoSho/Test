<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <c:set var="com.glanway.hg.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />
    <link rel="stylesheet" href="${ctx}/css/admin/uploader.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <style>
        .editPage ul {
            margin: 0;
            padding: 0;
            list-style: none;
        }
        .tpl-list li {
            border: 1px solid #ccc;
            padding: 5px;
            margin: 3px;
            cursor: pointer;
        }

        .js-plupload .fl {
            position: absolute;
            top: 40%;
            left: 40%;
            color: #666;
        }


        .wUeditor {
            width: 950px
        }

        .error {
            color: red;
        }

        #img1 {
            position: relative;
        }
        .le_list .le_list input[type="radio"]{margin-top:8px}
        tbody.productNode tr td .color-red{margin-left: -75%}
        textarea{
            width: 400px;
            height: 70px;
            max-width: 400px;
            max-height: 70px;
            resize: none;
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
            <a href="${ctx}/admin/product/index" title="商品信息">商品信息</a> &gt; 新增
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <!-- Begin 选项卡 -->
            <div class="tab-wrap">
                <ul class="tab">
                    <li class="on">
                        <a href="javascript:void(0);" title="基本信息">基本信息</a>
                    </li>
                    <li data-tab="spec" style="display: none;">
                        <a href="javascript:void(0);" title="规格">规格</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" title="详情">详情</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" title="SEO编辑">SEO编辑</a>
                    </li>
                </ul>
            </div>
            <!-- //End 选项卡 -->

            <div class="tab-ct-wrap">
                <!-- Begin 选项卡1 -->
                <form action="save" method="post">
                    <%-- 基本信息 --%>
                    <div class="tab1">
                        <table class="table-module01" cellpadding="0" cellspacing="0">
                            <tbody>

                            <tr>
                                <th width="160" align="right" valign="top">商品名称：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="title" value="${product.title}" class="required" maxlength="200"
                                           style="width:500px;">
                                    <input type="hidden" id="audit" name="audit" value="0"/>
                                    <input type="hidden" name="TOKEN" value="${TOKEN}">
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">子标题：</th>
                                <td valign="top">
                                    <input type="text" name="intro" value="${product.intro}" style="width:500px;" maxlength="150">

                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">商品编号：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="sn" class="required" value="${product.sn}" maxlength="50">
                                </td>
                            </tr>
                            <tr>
                            <tr>
                                <th width="160" align="right" valign="top">类别：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="category.name" class="categoryName" readonly>
                                    <input type="hidden" name="category.id" class="categoryId required" value="" >
                                    <button class="btn-sure" id="btn-cat-sel" type="button">选择分类</button>
                                    <div id="cate-sel-dlg" style="display: none">
                                        <ul id="cats-tree" class="ztree"
                                            style="background-color: #fff;    height: 200px;overflow-y: auto;"></ul>
                                    </div>
                                    <label for="category.id" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">模型：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="model.name" readonly value="${model.name}">
                                    <input type="hidden" name="model.id" class="required" value="${model.id}">
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">品牌：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <select name="brand.id" data-placeholder="请选择品牌" class="required select chosen-select brand-chosen ni">
                                        <option></option>
                                    </select>
                                    <label for="brand.id" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr class="single-product">
                                <th width="160" align="right" valign="top">库存：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <span class="spinner">
                                        <input id="stock"  name="stock" class="input " value=""  maxlength="5">
                                        <span class="spin-buttons">
                                            <span class="btn-up"></span>
                                            <span class="btn-down"></span>
                                        </span>
                                     </span>
                                    <label for="stock" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr class="single-product">
                                <th width="160" align="right" valign="top">预警库存：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <span class="spinner">
                                        <input  name="alertStock" id="alertStock" class="input " value=""  maxlength="6">
                                        <span class="spin-buttons">
                                            <span class="btn-up"></span>
                                            <span class="btn-down"></span>
                                        </span>
                                     </span>
                                    以下
                                    <label for="alertStock" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr class="">
                                <th width="160" align="right" valign="top">销量：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <span class="spinner">
                                        <input  name="sales" id="sales" class="input " value="0"  maxlength="6">
                                        <span class="spin-buttons">
                                            <span class="btn-up"></span>
                                            <span class="btn-down"></span>
                                        </span>
                                     </span>
                                    以下
                                    <label for="alertStock" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr class="single-product">
                                <th width="160" align="right" valign="top">售价：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="price" id="price" value="" class=""/>
                                    <input type="hidden" id="attrFlag" edit="false"  value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">重量： <b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="weight" value="" class="required decimalValidate"/>g
                                    <label for="weight" generated="true" class="error" style="display: none"></label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">促销： <b class="color-red"></b></th>
                                <td valign="top">
                                    <textarea name="promotionalInfo" maxlength="200"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">服务： <b class="color-red"></b></th>
                                <td valign="top">
                                    <textarea name="service" maxlength="200"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">温馨提示： <b class="color-red"></b></th>
                                <td valign="top">
                                    <textarea name="tip" maxlength="200"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th width="130" align="right" valign="top">区域：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <select id="areas" name="areas" class="select chosen-select " data-placeholder="请选择配送区域" multiple="multiple">
                                        <c:forEach var="province" items="${provinces}">
                                            <option value="${province.id}">${province.provinceName}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="areas" generated="true" class="error"></label>
                                </td>
                            </tr>

                            </tbody>
                            <!-- base -->
                            <tbody>
                            <c:set var="attrCount" value="0"/>
                            <c:forEach var="attr" items="${baseModel.attributes}" varStatus="st">
                                <tr>
                                    <th width="160" align="right" valign="top">
                                            ${attr.name}：<b class="color-red">*</b>
                                    </th>
                                    <td style="text-align:left;">
                                            <%--<input type="hidden" name="attributeId" value="${attr.id}"/>--%>
                                        <input type="hidden" name="attributeValues[${st.index}].attribute.id"
                                               value="${attr.id}" style="width:150px;"/>
                                        <c:choose>
                                            <%-- 文本 --%>
                                            <c:when test="${attr.displayType == 1}">
                                                <%--<input type="hidden" name="attributeValueId" value="${attr.attributeValues[0].id}" style="width:150px;"/>--%>
                                                <%--<input type="hidden" name="attributeValueId" value="&lt;%&ndash;${attr.attributeValues[0].id}&ndash;%&gt;"/>&lt;%&ndash;${attr.attributeValues[0].firstValue}&ndash;%&gt;--%>
                                                <%--<input type="text" name="value" value=""/>--%>

                                                <input type="hidden" name="attributeValues[${st.index}].id"
                                                       style="width:150px;"/>
                                                <input type="text" name="attributeValues[${st.index}].value" class="required"
                                                       style="width:150px;"/>
                                            </c:when>
                                            <%-- 选择和区间--%>
                                            <c:otherwise>
                                                <select name="attributeValues[${st.index}}.id" class="required"
                                                        style="width:150px;">
                                                    <c:forEach var="value" items="${attr.attributeValues}">
                                                        <option value="${value.id}" ${fne:hasEqPropObj(product.attributeValues, value, 'id') ? 'selected' : ''}>
                                                                ${value.value}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <%--
                                                <select name="attributeValueId" style="width:150px;">
                                                    <c:forEach var="value" items="${attr.attributeValues}">
                                                        <option value="${value.id}">
                                                                ${value.value}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                --%>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <c:set var="attrCount" value="${attrCount + 1}"/>
                            </c:forEach>
                            </tbody>
                            <tbody id="attr-list" data-index="${attrCount}"></tbody>
                            <tbody id="param-list"></tbody>
                            <tr>
                                <th width="160" align="right" valign="top">是否上架：<b class="color-red">*</b></th>
                                <td class="le_list">
                                    <label>
                                        <input type="radio" name="isPutaway" value="1" checked> <span>上架</span>
                                    </label>
                                    <label>
                                        <input type="radio" name="isPutaway" value="0"><span>不上架</span>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right" valign="top">标签：<b class="color-red">*</b></th>
                                <td class="le_list">
                                    <label>
                                        <input type="radio" name="label.id" value=""><span>无标签</span>
                                    </label>
                                    <c:forEach items="${labels}" var="label">
                                        <label>
                                            <input type="radio" name="label.id" value="${label.id}"><span>${label.labelName}</span>
                                        </label>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tbody>

                            <input type="hidden" name="isPutaway" value="0"/>
                            <%--<tr>
                                <th width="160" align="right"> 缩略图：</th>
                                <td>
                                    <b class="color-red">请上传99*95分辨率的图片</b>
                                    <ul class="upload-list" id="pro-img" flag="pro-img">
                                        <li class="js-plupload" name="productImgs[0].path" id="img1"
                                            data-list-target="img1:thumb:img">
                                            <span class="fl">主图</span></li>
                                        </li>
                                        <li class="js-plupload" name="productImgs[1].path" id="img2"
                                            data-list-target="img2:thumb:img"></li>
                                        <li class="js-plupload" name="productImgs[2].path" id="img3"
                                            data-list-target="img3:thumb:img"></li>
                                        <li class="js-plupload" name="productImgs[3].path" id="img4"
                                            data-list-target="img4:thumb:img"></li>
                                        <li class="js-plupload" name="productImgs[4].path" id="img5"
                                            data-list-target="img5:thumb:img"></li>
                                    </ul>
                                </td>
                            </tr>--%>


                            <tr>
                                <th align="right"> 商品图片：</th>
                                <td>
                                    <div class="pro-uploader cz" id="pro-img">
                                        <div class="upload-desc">请上传图片<span style="color:#dd0000">请上传450*490像素的图片</span></div>
                                        <ul class="upload-list">
                                            <c:forEach var="idx" begin="0" end="4">
                                                <li id="pro-primary-queue-${idx}">
                                                    <span class="primary-img-txt">上传图片</span>
                                                    <c:if test="${not empty product.productImgs[idx].path}">
                                                        <div data-saved-url="${ctx}/${product.productImgs[idx].path}">
                                                            <input type="hidden" name="productImgs[${idx}].id" value="${product.productImgs[idx].id}">
                                                            <input type="hidden" name="productImgs[${idx}].path" value="${product.productImgs[idx].path}">
                                                        </div>
                                                    </c:if>
                                                </li>
                                                <script type="text/javascript">
                                                    $(function(){
                                                        Uploader2({
                                                            browse_button: 'pro-primary-queue-${idx}',
                                                            url:'${ctx}/storage/images/preupload',
                                                            policy: true,
                                                            name: 'productImgs[${idx}].path',
                                                            list: 'pro-primary-queue-${idx}',
                                                            filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
                                                            mode: 't',
                                                            max_file_count: '1',
                                                            max_file_size: '1m'
                                                        });
                                                    });
                                                </script>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th width="160" align="right"> 启用规格：</th>
                                <td>
                                    <label>
                                        <input name="enableSpecs" type="checkbox" id="btn-enable-spec" value="1">
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right">&nbsp;</th>
                                <td>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="button" data-act="next" value="下一步"/>
                                        <input type="hidden" name="id">
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <input class="btn-cancel" type="button" value="取消" onclick="history.back();">
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <%--</form>--%>
                    </div>
                    <!-- //End 选项卡1 -->
                    <%-- 货品信息 --%>
                    <div class="tab1" style="display:none;">
                        <table class="table-module01 table-module02" cellpadding="0" cellspacing="0">
                            <tbody id="spec-list"></tbody>
                        </table>
                        <div class="at_btn">
                            <button onclick="Goods.generateGoods();" type="button" class="btn-sure">生成货品</button>
                            <button onclick="Goods.cleanGoods();" type="button" class="btn-cancel">清空所有货品</button>
                        </div>
                        <table class="table-module01 table-module02">
                            <thead>
                            <tr>
                                <th width="17%">商品名称</th>
                                <th width="16%">商品编号</th>
                                <th width="12%">规格</th>
                                <th width="16%">库存</th>
                                <th width="16%">警戒库存</th>
                                <th width="16%">售价</th>
                                <th width="7%">操作</th>
                            </tr>
                            </thead>
                            <tbody class="productNode" id="goods-list">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="8">
                                    <div class="at_btn" style="width:200px;">
                                        <span class="btn-sure-wrap">
                                            <input class="btn-sure btn-edit" type="button" data-act="next" value="下一步"/>
                                        </span>
                                        <span class="btn-cancel-wrap">
                                            <input class="btn-cancel" type="button" data-act="prev" value="上一步">
                                        </span>
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                    <%-- 详细信息 --%>
                    <div class="tab1" style="display:none;">
                        <table class="table-module01 table-module02">
                            <tr>
                                <td rowspan="2">商品简介:</td>
                                <%--<td class="wUeditor">
                                    <c:if test="${fn:length(templates) > 0}">
                                        <ul class="tpl-list">
                                            <c:forEach var="tpl" items="${templates}">
                                                <li data-tpl-content="${tpl.detail}" class="js-ueditor-tpl">
                                                    <fne:safe-html>${tpl.name}</fne:safe-html></li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </td>--%>
                            </tr>
                            <tr>
                                <td class="wUeditor">
                                    <script id="ueditor" name="detail" type="text/plain">
                                        <fne:safe-html>${product.detail}</fne:safe-html></script>
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="2">手机商品简介:</td>
                                <%--<td class="wUeditor">
                                    <c:if test="${fn:length(templates) > 0}">
                                        <ul class="tpl-list">
                                            <c:forEach var="tpl" items="${templates}">
                                                <li data-tpl-content="${tpl.detail}" class="js-ueditor-tpl">
                                                    <fne:safe-html>${tpl.name}</fne:safe-html></li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </td>--%>
                            </tr>
                            <tr>
                                <td class="wUeditor">
                                    <script id="ueditor1" name="mobileDetail" type="text/plain">
                                        <fne:safe-html>${product.mobileDetail}</fne:safe-html></script>
                                </td>
                            </tr>
                            <tfoot>
                            <tr>
                                <td colspan="2">
                                    <div class="at_btn" style="width:200px">
                                        <span class="btn-sure-wrap">
                                            <input class="btn-sure btn-edit" type="button" data-act="next" value="下一步"/>
                                        </span>
                                        <span class="btn-cancel-wrap">
                                            <input class="btn-cancel" type="button" value="上一步" data-act="prev">
                                        </span>
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                    <div class="tab1" style="display: none; ">
                            <table class="table-module01 ">
                                <tr>
                                    <td align="right" width="180">SEO标题：</td>
                                    <td >
                                        <input type="text" name="seoTitle" style="width: 400px;height: 22px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" width="180">SEO关键字：</td>
                                    <td >
                                        <textarea name="seoKeyword"  style="width: 400px;height: 100px;"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" width="180" >SEO描述：</td>
                                    <td >
                                        <textarea name="seoDescription" style="width: 400px;height: 100px;"></textarea>
                                    </td>
                                </tr>
                                <tfoot>
                                <tr>
                                    <td colspan="2">
                                        <div class="at_btn" style="width:500px">
                                        <span class="btn-sure-wrap">
                                            <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                        </span>
                                        <span class="btn-cancel-wrap">
                                            <input class="btn-cancel" type="button" value="上一步" data-act="prev">
                                        </span>
                                        </div>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                    </div>
                </form>

                <div id="goods-dlg" data-options="modal:true,closed:true" style="width:800px;">
                    <table id="datagrid"></table>
                </div>
                <!-- //End 主内容区 -->
            </div>

            <div id="cate-sup-dlg" style="display: none;width:598px">
                <table id="supplier-grid" style="width:400px"></table>
                <div id="supplier-pagination" style="width:400px!important;"></div>
            </div>
            <%@include file="productTpl.jspf" %>
            <script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
            <script type="text/javascript" src="${ctx}/js/admin/product/goods.js"></script>
            <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>

            <script type="text/javascript">

                $(function () {
                    $('.chosen-select').chosen({});
                    $('.spinner').spinner({
                        max: 2147483647
                        , min: 0
                        , step: 1
                        , allowEmpty: true
                        , minusBtn: '.btn-down'
                        , plusBtn: '.btn-up'
                    });




                    /*fixAccessoryList();*/
                    var editor = UE.getEditor('ueditor',{autoHeightEnabled: false,initialFrameHeight : 500});
                    var editor1 = UE.getEditor('ueditor1',{autoHeightEnabled: false,initialFrameHeight : 500});
                    var arrayObj = [editor, editor1];
                    $('.js-ueditor-tpl').each(function (i) {
                        $(this).click(function (e) {
                            var me = this;
                            arrayObj[i].execCommand('insertHtml', $(me).data('tpl-content') || '');
                            $.isFunction(e.preventDefault) && e.preventDefault();
                        });
                    })

                });
            </script>
<m:f/>