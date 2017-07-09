<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
    <style>
        select {
            width: 145px;
        }
    </style>
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
            <a href="${ctx}/admin/coupon/index" ; title="">优惠券生成</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/coupon/save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>
                    <tr>
                        <th width="130" align="right">优惠券模板：<b class="color-red">*</b></th>
                        <td>
                            <select id="templateId" name="templateId" data-placeholder="请选择"
                                    class="select chosen-select ni required">
                                <option value=""></option>
                                <c:forEach items="${couponTemplList}" var="template">
                                    <option value="${template.id}">${template.name}</option>
                                </c:forEach>
                            </select>
                            <label for="templateId" generated="true" class="error"></label>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">优惠券名称：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="couponName" class="required" maxlength="30"/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">生成数量：<b class="color-red">*</b></th>
                        <td>
<span id="sort-spinner" class="spinner">
<input id="amount" name="amount" class="input required" value="1">
<span class="spin-buttons">
<span class="btn-up"></span>
<span class="btn-down"></span>
</span>
</span>
                            <script>
                                $('#sort-spinner').spinner({
                                    max: 99
                                    , min: 1
                                    , step: 1
                                    , allowEmpty: true
                                    , minusBtn: '.btn-down'
                                    , plusBtn: '.btn-up'
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">有效期开始时间：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="beginDate" readonly id="beginDate"
                                   style="border:1px solid #d4d4d4;height:25px;padding-left: 4px"
                                   class="Wdate valid required"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00', maxDate:'#F{$dp.$D(\'endDate\')||\'2099-12-31\'}'})">
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">有效期结束时间：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" readonly name="endDate" id="endDate"
                                   style="border:1px solid #d4d4d4;height: 25px;padding-left: 4px"
                                   class="Wdate valid required"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59', minDate:'#F{$dp.$D(\'beginDate\')}',maxDate:'2099-12-31'})">

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
    <script>
        $(function () {
            $(".btn-cancel-wrap input").click(function () {
                window.location.href = contextPath + "/admin/coupon/index";
            });
            $('.chosen-select').chosen({});
            //验证
            $("#form").validate({
                submitHandler: function (form) {
                    $("#form").find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }
            });


        });
    </script>

