<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
</m:h>
<%@include file="../../include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/couponTempl/index" ; title="运营管理">运营管理</a> &gt;
            <a href="${ctx}/admin/couponTempl/index" ; title="">优惠券模板管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/couponTempl/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${couponTempl.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>
                    <tr>
                        <th width="130" align="right">模板名称：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="name" value="${couponTempl.name}" class="required" maxlength="30"/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">优惠额度：<b class="color-red">*</b></th>
                        <td>
                            <%--<input type="number" name="discount" value="${couponTempl.discount}" class="required" maxlength="30"/>--%>
<span id="discount-spinner" class="spinner">
        <input id="discount" name="discount" class="input required" value="${couponTempl.discount}">
        <span class="spin-buttons">
            <span class="btn-up"></span>
            <span class="btn-down"></span>
        </span>
    </span>
                            <script>
                                $('#discount-spinner').spinner({
                                    max: 999999
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
                        <th width="130" align="right">最小订单金额：<b class="color-red">*</b></th>
                        <td>
                            <%--<input type="number" name="minPrice" value="${couponTempl.minPrice}" class="required"/>--%>
<span id="sort-spinner" class="spinner">
        <input id="sortNum" name="minPrice" class="input required" value="${couponTempl.minPrice}">
        <span class="spin-buttons">
            <span class="btn-up"></span>
            <span class="btn-down"></span>
        </span>
    </span>
                            <script>
                                $('#sort-spinner').spinner({
                                    max: 999999
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
                        <th width="130" align="right">是否自动发放：<b class="color-red">*</b></th>
                        <td>
                            <input type="radio" name="autoSend" class="autoSend" <c:if test="${couponTempl.autoSend}">checked</c:if> value="1"/> 是
                            <input type="radio" name="autoSend" class="autoSend"  <c:if test="${!couponTempl.autoSend}">checked</c:if> value="0"/> 否

                        </td>
                    </tr>
                    <tr class="autoSend">
                        <th width="130" align="right">自动发放最低金额：<b class="color-red"><c:if test="${couponTempl.autoSend}">*</c:if></b></th>
                        <td>
                            <%--<input type="number" name="autoMinPrice" value="${couponTempl.autoMinPrice}" <c:if test="${couponTempl.autoSend}">class="required"</c:if> />--%>
 <span id="autoMinPrice-spinner" class="spinner">
        <input id="autoMinPrice" name="autoMinPrice" class="input required" value="${couponTempl.autoMinPrice}">
        <span class="spin-buttons">
            <span class="btn-up"></span>
            <span class="btn-down"></span>
        </span>
    </span>
                            <script>
                                $('#autoMinPrice-spinner').spinner({
                                    max: 999999
                                    , min: 0
                                    , step: 1
                                    , allowEmpty: true
                                    , minusBtn: '.btn-down'
                                    , plusBtn: '.btn-up'
                                });
                            </script>
                        </td>
                    </tr>
                    <tr class="autoSend">
                        <th width="130" align="right">自动发放最大金额：<b class="color-red"><c:if test="${couponTempl.autoSend}">*</c:if></b></th>
                        <td>
                            <%--<input type="number" name="autoMaxPrice" value="${couponTempl.autoMaxPrice}" <c:if test="${couponTempl.autoSend}">class="required"</c:if>  maxlength="30"/>--%>
<span id="autoMaxPrice-spinner" class="spinner">
        <input id="autoMaxPrice" name="autoMaxPrice" value="${couponTempl.autoMaxPrice}" class="input required">
        <span class="spin-buttons">
            <span class="btn-up"></span>
            <span class="btn-down"></span>
        </span>
    </span>
                            <script>
                                $('#autoMaxPrice-spinner').spinner({
                                    max: 9999999
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
        </div>
        <!-- //End 主内容区 -->
    </div>
    <m:f/>
    <%--省市联动js--%>
    <script type="text/javascript" src="${ctx}/js/admin/couponTempl/add.js"></script>

