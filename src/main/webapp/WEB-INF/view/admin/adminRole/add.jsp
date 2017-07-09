<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }
        .layout-split-east  {
            border-left: #f5f5f5;
        }
        .table-module01 td{position: relative;}
    </style>

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
        <a href="${ctx}/admin/module/index" title="权限管理">权限管理</a> &gt;
        <a href="${ctx}/admin/role/index" title="角色管理">角色管理</a> &gt;
        新增
    </div>
    <!-- //End 当前位置 -->
    <div class="" style="width:100%;height:500px;">
        <div <%--class="editPage"--%> data-options="region:'center',split:true" title="基础信息" style=" float:left;width: 50%;margin: 10px; border-right:none">
            <form id="fromSave" action="${ctx}/admin/role/save" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>
                    <tr>
                        <th width="130" align="right">角色名：<b class="color-red">*</b></th>
                        <td>
                            <input id="roleAddName" type="text" name="name" maxlength="20"/>
                            <span class="errorTip"></span>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">角色权限：<b class="color-red"></b></th>
                        <td>
                            <span class="btn-sure-wrap">
                                <input id="treeWindow" class="btn-sure btn-edit" type="button" value="添加权限" />
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">备注：</th>
                        <td>
                            <input id="remark" type="text" name="remark" >
                            <span class="errorTip"></span>
                        </td>
                    </tr>
                    <input type="hidden" name="pageIds">
                    <tr>
                        <td></td>
                        <td align="left" >
                            <span class="btn-sure-wrap">
                                <input id="save" class="btn-sure btn-edit" type="submit" value="保存" />
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
        <div id="ztree" data-options="region:'east',split:true" title="包含页面" style="float:left;z-index:99;margin-left:30px;display:none;">
            <ul id="roleTree" class="ztree"></ul>
        </div>
    </div>
    <!-- //End 主内容区 -->
</div>
<m:f/>
<script type="text/javascript" src="${ctx}/js/admin/adminRole/role.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/adminRole/add.js"></script>