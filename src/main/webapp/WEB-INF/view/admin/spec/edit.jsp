<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
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
            <a href="${ctx}/admin/spec/index" title="商品规格">商品规格</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="${ctx}/admin/spec/update" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <th width="130" align="right" valign="top">名称：<b class="color-red">*</b></th>
                        <td  valign="top">
                            <input type="hidden" name="id" value="${m.id}">
                            <input type="text" name="name" value="${m.name}">
                            <input type="hidden" name="TOKEN" value="${TOKEN}">
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right"  valign="top">别名：</th>
                        <td  valign="top"><input type="text" name="alias" value="${m.alias}"></td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">排序：<b class="color-red">*</b></th>
                        <td valign="top">
                        <span id="sort-spinner" class="spinner">
                            <input id="sort" name="sort" class="input ni error" value="${m.sort}">
                            <span class="spin-buttons">
                                <span class="btn-up"></span>
                                <span class="btn-down"></span>
                            </span>
                            <script type="text/javascript">
                                    $('#sort-spinner').spinner({
                                        max: 2147483647
                                        ,min: 0
                                        ,step: 1
                                        ,allowEmpty: true
                                        ,minusBtn: '.btn-down'
                                        ,plusBtn: '.btn-up'
                                    });
                            </script>
                        </span>
                            <span class="errorTip"></span>
                        </td>
                    </tr>
                    <tr>
                    	<td>&nbsp;</td>
                        <td>
                            <button type="button" class="btn-sure" id="btn-add-spec-value">添加规格值</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table class="table-module01 table-module02" style="border: 1px solid #ccc;">
                    <thead>
                    <tr>
                        <th width="130">值<b class="color-red">*</b></th>
                        <th width="130">别名</th>
                        <th width="130">编码<b class="color-red">*</b></th>
                        <th width="130">排序<b class="color-red">*</b></th>
                        <th width="130">操作</th>
                    </tr>
                    </thead>
                    <tbody id="spec-value-list">
                    <c:forEach var="value" varStatus="st" items="${m.specValues}">
                        <tr>
                            <td valign="top">
                                <input type="hidden" name="specValues[${st.index}].id" value="${value.id}">
                                <input type="text" class="required repeatSpecValValidate specVal" maxlength="10" name="specValues[${st.index}].name" value="${value.name}">
                            </td>
                            <td valign="top"><input type="text" name="specValues[${st.index}].alias" value="${value.alias}">
                            </td>
                            <td valign="top">
                                <input type="text" maxlength="10" class="required repeatSpecCodeValidate specCode" name="specValues[${st.index}].code" value="${value.code}">
                            </td>
                            <td valign="top">
                            <span class="spinner">
                                <input type="text" class="required digits" max="999" name="specValues[${st.index}].sort" value="${value.sort}">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span>
                            </td>
                            <td valign="top">
                                <div class="operateBox" style="padding-top:4px;">
                                    <img src="${ctx}/images/admin/icon-delete01.png" width="15" height="15" title="Delete" alt="Delete">
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    	<tr>
                        <td colspan="5">
                        	<div class="at_btn">
								<span class="btn-sure-wrap">
									<input class="btn-sure btn-edit" type="submit" value="保存"/>
								</span>
								<span class="btn-cancel-wrap">
                                    <a href="../index" class="btn-cancel">取消</a>
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

    <%@include file="specValueTpl.jspf"%>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/specvalue.js"></script>
    <m:message message="${message}" error="${error}" />
<m:f/>