<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <style>
        .table-module02 tbody td{border-bottom: none}
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
            <a href="${ctx}/admin" title="Home">首页</a> &gt;
            <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
            <a href="${ctx}/admin/model/index" title="商品模型">商品模型</a> &gt;
            基本模型
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <button class="btn-sure" id="btn-add-attr">添加属性</button>
            <form id="saveForm" action="${ctx}/admin/model/base/save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <table class="table-module01 table-module02">
                    <thead>
                    <tr>
                        <th width="16%">属性名称</th>
                        <th width="16%">属性别名</th>
                        <th width="12%">属性类型</th>
                        <th width="18%">属性值</th>
                        <th width="16%">排序</th>
                        <th width="16%">显示</th>
                        <th width="6%">操作</th>
                    </tr>
                    </thead>
                    <tbody id="attr-list">
                    <c:forEach var="attr" varStatus="st" items="${m.attributes}">
                        <tr>
                            <td valign="top">
                                <input type="hidden" name="attributes[${st.index}].id" value="${attr.id}">

                                <input type="text" name="attributes[${st.index}].name" class="required repeatAttrValidate attr"
                                       value="${attr.name}"  maxlength="5">
                            </td>
                            <td valign="top">
                                <input type="text" name="attributes[${st.index}].alias" value="${attr.alias}">
                            </td>
                            <td valign="top">
                                <select name="attributes[${st.index}].displayType" style="width: 100%;">
                                    <option value="0" ${0 eq attr.displayType ? 'selected':''}>选择</option>
                                    <option value="1" ${1 eq attr.displayType ? 'selected':''}>文本</option>
                                    <option value="2" ${2 eq attr.displayType ? 'selected':''}>区间</option>
                                </select>
                            </td>
                            <td valign="top" style="text-align:left;">
                                <c:choose>
                                    <%-- 选择 --%>
                                    <c:when test="${0 eq attr.displayType}">
                                        <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                            <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].id" value="${v.id}">
                                            <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].value" value="${v.value}">
                                            <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].sort" value="${v.sort}">
                                        </c:forEach>
                                        <select size="999999" style="width: 142px; height: 50px;">
                                            <c:forEach var="v" items="${attr.attributeValues}">
                                                <option value="${v.value}" data-id="${v.id}" data-sort="${v.sort}">${v.value}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="button" class="btn-sure" data-act="edit-s" style="display:block;margin-top:10px;">编辑</button>
                                    </c:when>
                                    <%-- 区间 --%>
                                    <c:when test="${2 eq attr.displayType}">
                                        <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                            <input type="hidden"
                                                   name="attributes[${st.index}].attributeValues[${vvs.index}].id"
                                                   value="${v.id}">
                                            <input type="hidden"
                                                   name="attributes[${st.index}].attributeValues[${vvs.index}].value"
                                                   value="${v.value}">
                                            <input type="hidden"
                                                   name="attributes[${st.index}].attributeValues[${vvs.index}].sort"
                                                   value="${v.sort}">
                                        </c:forEach>
                                        <select size="999" style="width: 142px; height: 50px;">
                                            <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                                <option value="${fn:split(v.value,' - ')[0]},${fn:split(v.value,' - ')[1]}" data-id="${v.id}" data-sort="${v.sort}">${v.value}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="button" class="btn-sure" data-act="edit-r" style="display:block;margin-top:10px;">编辑
                                        </button>
                                    </c:when>
                                    <%-- 文本 --%>
                                    <c:otherwise>
                                        <input type="hidden" name="attributes[${st.index}].attributeValues[0].id" value="${attr.attributeValues[0].id}">
                                        <input type="hidden" name="attributes[${st.index}].attributeValues[0].value" style="width:120px;" value="${attr.attributeValues[0].value}">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td valign="top">
                            <span class="spinner">
                                <input type="text" name="attributes[${st.index}].sort" class="required digits" max="999" value="${attr.sort}">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span>
                            </td>
                            <td valign="top">
                                <select name="attributes[${st.index}].visible" style="width: 100%;">
                                    <option value="1" ${attr.visible?'selected':''}>可见</option>
                                    <option value="0" ${attr.visible?'':'selected'}>隐藏</option>
                                </select>
                            </td>
                            <td valign="top">
                                <div class="operateBox" style="padding-top:4px;">
                                    <img src="${ctx}/images/admin/icon-delete01.png" width="15" height="15" title="Delete"
                                         alt="Delete">
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="7" style="text-align: center">
                            <div class="at_btn" style="width: 100%;">
                                <button class="btn-sure" >保存</button>
                                <span class="btn-cancel-wrap">
                                    <a href="index" class="btn-cancel">取消</a>
                                </span>
                            </div>

                        </td>
                    </tr>
                    </tfoot>
                </table>

            </form>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <%@include file="attrTpl.jspf" %>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/attributes.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#saveForm').ajaxForm({
                beforeSubmit: function (data, form) {
                    var validator = $(form).validate(),
                            ret = validator.checkForm();
                    if (!ret) {
                        validator.showErrors();
                    }else{
                        $(form).find(".at_btn .btn-sure:button").attr("disabled", true).text("保存中...").css("letter-spacing", "0");
                    }
                    return ret;
                },
                success: function (result) {
                    if (result.success) {
                        Glanway.Messager.alert('提示', '保存成功');
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                        <%--window.location.href = '${actx}/model/base';--%>
                    } else {
                        Glanway.Messager.alert('错误', '保存失败');
                    }
                }
            });
        });

        $('.spinner').spinner({
            max: 2147483647
            , min: 0
            , step: 1
            , allowEmpty: true
            , minusBtn: '.btn-down'
            , plusBtn: '.btn-up'
        });
    </script>
<m:f/>