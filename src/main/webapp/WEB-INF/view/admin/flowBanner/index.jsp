<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@ include file="/WEB-INF/view/include/elibs.jspf" %>
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
            <a href="${ctx}/admin/website/index" title="平台管理">平台管理</a> &gt;
            <a href="${ctx}/admin/flowBanner/index"; title="轮播图管理">轮播图管理</a> &gt;
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
<script type="text/javascript">

	var zcTypes = toObject("${zcTypes}");
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id','图片','名称','状态','平台','模块','添加时间'],
        colModel: [
            {
            	template: 'actions2', width: 100
            
            
            },
    		{name: 'id', index: 'id', hidden: true, key: true},
    		{name: 'image', index: 'image', template: 'img', sortable:false,search:false},
            {name: 'title', index: 'tu.title', template: 'text', sortable:false,searchoptions: {sopt: ["cn"]}},
           
            {
                name: 'isShow',
                index: 'tu.isShow',
                template: 'select',
                width: 125,
                stype: "select",
                sortable:false,
                search: true,
                formatter: "select",
                searchoptions: {sopt: ["eq", "ne"], value: ":全部;1:启用;2:停用"},
                editoptions: {value: "2:停用;1:启用"},
                align: 'center'
            },
            {
                name: 'platformDictionary.id', index: 'tu.F.id', template: 'select', stype: 'select',
                width: 150,
                sortable:false,
                searchoptions: {sopt: ["eq", "ne"], value: getSearchoptionsById(zcTypes)},
                formatter: function (val) {
                
                    return formatByDictionaryById(val, zcTypes);
                }
            },
            {name: 'pageDictionary.dicName', index: 'tu.P.dicName', template: 'text', sortable:false,searchoptions: {sopt: ["cn"]}},
	        
	        
	        {name: 'createdDate', template: 'date',search:false, formatter:"date", formatoptions: {newformat:'Y-m-d H:m:s'}}
	        		        
	          ],
        multiselect: true,
        autowidth: true,
        shrinkToFit:true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc'
    });
</script>
<m:f/>

