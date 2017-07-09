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
            新增
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="save" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <th width="130" align="right" valign="top">名称：<b class="color-red">*</b></th>
                        <td valign="top"><input type="text" name="name"></td>
                        <input type="hidden" name="TOKEN" value="${TOKEN}">
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">别名：</th>
                        <td valign="top"><input type="text" name="alias"></td>
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
                           <%-- <input type="text" name="sort" autocomplete="off">--%></td>
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
                        <th width="23%">值<b class="color-red">*</b></th>
                        <th width="23%">别名</th>
                        <th width="23%">编码<b class="color-red">*</b></th>
                        <th width="23%">排序<b class="color-red">*</b></th>
                        <th width="8%">操作</th>
                    </tr>
                    </thead>
                    <tbody id="spec-value-list"></tbody>
                    <tfoot>
                    	<tr>
	                        <td colspan="5">
	                        	<div class="at_btn">
									<span class="btn-sure-wrap">
										<input class="btn-sure btn-edit" id="subtn" type="submit" value="保存"/>
									</span>
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
</div>
    <%@include file="specValueTpl.jspf"%>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/specvalue.js"></script>
    <script type="text/javascript">

    </script>
<m:message message="${message}" error="${error}" />
<m:f/>