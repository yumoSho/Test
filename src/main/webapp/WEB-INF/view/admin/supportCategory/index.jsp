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
            <a href="${ctx}/admin/supportCategory/index"; title="">内容管理</a> &gt;
            <a href="${ctx}/admin/supportCategory/index" title="">帮助中心分类管理</a> &gt;
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
                        <a class="operate-add" href="addParent">
                            <s class="icon-add-green"></s>新增
                        </a>
                    </li>
                    
                  	<!-- <li>
                        <a class="operate-add to" href="javascript:void(0);">
                            <s class="icon-add-green"></s>新增二级分类
                        </a>
                    </li> -->
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

	//var adTypes = toObject("${advertisementTypes}");
    var $g = $('#datagrid').jqGrid({
        url: 'list',
        datatype: 'json',
        colNames: ['操作', 'id','分类名称',/*'上级分类',*/'排序','最后修改时间'],
        colModel: [
            {
            	template: 'actions2', width: 100
            },
    		{name: 'id', index: 'id', hidden: true, key: true},
            {name: 'name', index: 'name', template: 'text', sortable:false,search:true},
	        /*{name: 'parent.name', index: 'P.name', template: 'text', sortable:false,search:false},*/
            {name: 'sortNum', index: 'sortNum', template: 'text', sortable:true,search:false},
	        {name: 'createdDate', template: 'date',search:false, formatter:"date", formatoptions: {newformat:'Y-m-d H:m:s'}}
	        		        
	          ],
        multiselect: true,
        autowidth: true,
        shrinkToFit:true,
        height: 'auto',
        pager: '#pagination',
        sortname: 'createdDate',
        sortorder: 'desc',
        /**  subGrid : true,
        subGridWidth:30,
        subGridUrl : 'subSupportCategory',
        subGridRowExpanded : function(subgrid_id, row_id) {
        	//debugger;
            var subgrid_table_id, pager_id;
            subgrid_table_id = subgrid_id + "_t";
            pager_id = "p_" + subgrid_table_id;
            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id
                + "' class='wode' ></table>");
            var aa = jQuery("#" + subgrid_table_id).jqGrid(
                {
                    url : "subSupportCategory/?id=" + row_id,
                    datatype : "json",
                    colNames : ['操作', '分类名称','上级分类','所属银行','排序','最后修改时间' ],
                    colModel : [
						{
							template: 'actions2', width: 100
						
						
						},
                        {name: 'name', index: 'N.name', template: 'text', sortable:false,search:false},
				        {name: 'parent.name', index: 'P.name', template: 'text', sortable:false,search:false},
				        {name: 'bank.bankName', index: 'B.bankName', template: 'text', sortable:false,search:false},
			            {name: 'sortNum', index: 'sortNum', template: 'text', sortable:true,search:false},
				        {name: 'createdDate', template: 'date',search:false, formatter:"date", formatoptions: {newformat:'Y-m-d H:m:s'}}
                    ],
                    rowNum : 20,
                    toolbar:[false,''],
                    shrinkToFit: true,
                    autowidth: true,
                    forceFit:true,
                    height : '100%'
                })

            var $listPanel = $(".listPage");
            if($listPanel.length > 0){
                aa.setGridWidth($listPanel.width()-200);
                $(window).resize(function(){
                    aa.setGridWidth($listPanel.width()-200);
                });

            }
            aa.jqGrid("destroyFilterToolbar");
        }*/
    });
    
    
    $('.operateBar .to').click(function () {
        var keys = $g.jqGrid('getGridParam', 'selarrrow');
        if(keys.length<1){
        	Glanway.Messager.alert("提示", "请选择一个一级分类");
        	return;
        }else if(keys.length>1){
        	Glanway.Messager.alert("提示", "只能选择一个一级分类");
        	return;
        }
        
        window.location.href = "${ctx}/admin/supportCategory/add/"+keys;
    });
</script>
<m:f/>

