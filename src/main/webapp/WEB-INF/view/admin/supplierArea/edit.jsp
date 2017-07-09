<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
	<%@ include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<style>
	
	.table-module01 td{
		border-bottom:1px solid #c5c3c3;
	}
</style>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
	<!-- Begin 主内容区 -->
	<div class="content">
	    <!-- Begin 当前位置 -->
	   	<div class="position">
	        <span>当前位置：</span>
	        <a href="${ctx}/admin/homePage" title="首页">首页</a> &gt;
 			<a href="${ctx}/admin/supplierArea/index" title="物流管理">物流管理</a> &gt;
	        <a href="${ctx}/admin/supplierArea/index" title="物流区域管理">物流区域管理</a> &gt;
	                       编辑
	    </div> 
	    <!-- //End 当前位置 -->
	    <div style="width:100%;height:600px;"> 
	        <div data-options="region:'center',split:true" title="物流区域信息" style="height: 600px; width: 80%">
	            <div id="cate-sel-dlg" style="display: none">
					<table id="datagrid"></table>
					<div id="pagination" style="height: 50px"></div>
	            </div>
	            <form id="fromSave" action="${ctx}/admin/supplierArea/update" method="post">
	                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
	                    <tbody>
                                <input type="hidden" name="id" value="${supplierArea.id }"/>
	                    <tr>
	                        <th width="130" align="right">邮寄地区名称：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="text" name="areaName" maxlength="30" style="width:400px;height:24px;" value="${supplierArea.areaName }"/>
	                         <%--   <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th width="130" align="right">价格：<b class="color-red">*</b></th>
	                        <td>
	                            <input type="text" name="price" maxlength="20" style="width:200px;height:24px;" value="${supplierArea.price }"/>
	                         <%--   <span class="errorTip"></span>--%>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <th width="130" align="right">地址：<b class="color-red">*</b></th>
	                        <td>
	                        	<table cellpadding="0" cellspacing="0" class="table-module01">
	                        		<tr>
	                        			<td colspan="2">
	                        				<input type="checkbox" id="allCheckBox"/>全国
	                        			</td>
	                        		</tr>
	                        		<c:set var="cityListIndex" value="0" /><!-- 定义该城市在数据中的位置 -->
									<c:forEach items="${provinceList}" var="province" varStatus="provinceStatus">
										<tr>
											<td style="width:15%;border-right:1px solid #c5c3c3;"><input type="checkbox" class="provinceName"/>${province.provinceName}</td>
											<td>
												<c:forEach items="${province.hatCities }" var="hatcity"  varStatus="cityStatus">
													<%-- <c:out value="${cityListIndex} "/> --%>
													<input type="checkbox" class="cityName"
														name="hatCityList[${cityListIndex }].hatCity.id"  value="${hatcity.id }"/>
													<a>${hatcity.cityName}</a>&nbsp;
													<c:set var="cityListIndex" value="${cityListIndex + 1}" /> 
												</c:forEach>
											</td>
										</tr>	                        	
									</c:forEach>
	                        	</table>
	                        </td>
	                    </tr>
	                     
	                    <tr>
	                        <td></td>
	                        <td align="left" >
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存" />
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消"/>
	                            </span>
	                            
	                          <!--   <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" id="testButton" value="测试"/>
	                            </span> -->
	                        </td>
	                    </tr>
	                    </tbody>
	                </table>
	            </form>
	        </div>
	    </div>
	    <!-- //End 主内容区 -->
	</div>
</div>
<m:f/>
<script src="${ctx}/js/admin/supplierArea/supplierArea.js"></script>
<script type="text/javascript">
    $(function(){
       
        
        
        
      

    });
    
</script>
<script type="text/javascript">
$(function(){
	 //自动勾选原地址信息
	 var cityStr  = '${cityStr }';
	 var cityArray = cityStr.split("-");
	 for(var i=0; i<cityArray.length ;i++){
	 	var value = cityArray[i];
	 	if("" != value){
			$(".cityName[value='"+ value +"']").attr("checked", true);
	 	}
	 }
	 
	  $(".btn-cancel-wrap input").click(function() {
          window.location.href = "${ctx}/admin/supplierArea/index";
      });
	 
});
</script>
