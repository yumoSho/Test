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
            <a href="${ctx}/admin/afterSaleProtocol/index"; title="">售后保障</a> &gt;
            用户注册协议管理
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/afterSaleProtocol/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>

                    <tr>
                        <th width="130" align="right">内容：<b class="color-red">*</b></th>
                        <td >
                            <textarea id="editor_" name="content" style="height: 400px" class="required" maxlength="9999">${dictionary.content}</textarea>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td align="left">
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel"  type="button" value="取消" onclick=""/>
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
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/website/add.js"></script>

