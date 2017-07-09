<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
    <%--<%@include file="../../include/admin/support.jspf" %>--%>
</m:h>
<%@ include file="../../include/admin/adminHead.jspf" %>
<div class="main">
    <%@ include file="../../include/admin/adminSidebar.jspf" %>

    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/couponTempl/index" ; title="运营管理">运营管理</a> &gt;
            <a href="${ctx}/admin/qrCode/index" ; title="二维码管理">二维码管理</a> &gt;
            列表
        </div>
        <!-- //End 当前位置 -->
        <div class="listPage">
            <!-- Begin 操作条 -->
            <div class="operateBar">
                <ul>
                    <li>
                        <a class="operate-delete" href="javascript:void(0);" class="">
                            <s class="icon-delete"></s>删除
                        </a>
                    </li>
                    <li>
                        <a class="operate-add" href="add">
                            <s class="icon-add-green"></s>新增
                        </a>
                    </li>
                </ul>
            </div>
            <!-- //End 操作条 -->
            <div class="table-module03">
                <table id="datagrid"></table>
                <div id="pagination" style="height: 50px"></div>
            </div>
        </div>
    </div>
    <!-- //End 主内容区 -->
</div>

<m:message message="${message}" error="${error}"/>
<%--批量删除js--%>
<script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
<script>
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id', '二维码名称', '链接','访问量', '生成时间'],
        colModel: [
            {template: 'actions2', width: 80}
            , {name: 'id', index: 'id', hidden: true, key: true}
            , {name: 'name', index: 'name', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
            , {name: 'link', index: 'link', template: 'text', sortable: false,searchoptions: {sopt: ["eq"]}}
            , {name: 'visitorCount', index: 'visitorCount', template: 'text', sortable: true,search:false}
            , {name: 'createdDate', index: 'createdDate', template: 'date', sortable: false,search:false}
        ],
        multiselect: true,
        autowidth: true,
        shrinkToFit: true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'id',
        sortorder: 'desc'
    });

</script>
<m:f/>

