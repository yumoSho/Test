<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
    <style>
        select{
            width:200px;
        }
       #pagination input[type=text]{
            width:20px!important;
           padding-right: 7px;
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
            <a href="${ctx}/admin/coupon/grantIndex" ; title="">优惠券发放</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/couponGrant/save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tbody>
                    <tr style="border-bottom: solid 1px #ccc">
                        <th width="10%" align="right">优惠券：<b class="color-red">*</b></th>
                        <td style="padding-left: 3%" >
                            <select id="templateId" name="templateId" data-placeholder="请选择" class="select chosen-select ni required">
                                <option value=""></option>
                                <c:forEach items="${coupons}" var="coupon">
                                    <option value="${coupon.templateId}" couponName="${coupon.couponName}" count="${coupon.status}" storeId="${coupon.storeId}">${coupon.couponName}</option>
                                </c:forEach>
                            </select>
                            <label for="templateId" generated="true" class="error"></label>
                            <input type="hidden" name="couponName" id="couponName">
                            <input type="hidden" name="storeId" id="storeId">
                        </td>
                       <%-- <th width="130" align="right">店铺：<b class="color-red">*</b></th>
                        <td>
                            <select name="storeId" id="storeId">
                                <c:forEach items="${storeList}" var="store">
                                    <option value="${store.id}">${store.name}</option>
                                </c:forEach>
                            </select>

                        </td>--%>
                    </tr>
                    <tr>
                        <th align="right">指定用户：</th>
                        <td >
                            <div class="listPage">
                            <div class="table-module03">
                                <table id="datagrid"></table>
                                <div id="pagination" style="height: 50px"></div>
                            </div>
                                </div>
                            <input type="hidden" name="memberIds" id="memberIds" class="required">
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td align="left" >
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="发放"/>
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
        <script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
        <script type="text/javascript">
            var memberIds = new Array();
            var $g = $('#datagrid').jqGrid({
                url:  '${ctx}/admin/member/list',
                datatype: 'json',
                colNames: ['id', '会员编号', '用户名', '手机号', '关注店铺'],
                colModel: [
                    {name: 'id', index: 'id', hidden: true, key: true}
                    , {name: 'memberCode', index: 'memberCode', template: 'text', sortable: false,search:true}
                    , {name: 'memberName', index: 'memberName', template: 'text', sortable: false,searchoptions: {sopt: ["cn"]}}
                    , {name: 'phone', index: 'phone', template: 'text', sortable: false, search: true}
                    , {name: 'store.name', index: 'storeName', template: 'text', sortable: false, searchoptions: {sopt: ["cn"]}}
                ],
                multiselect: true,
                autowidth: false,
                shrinkToFit: true,
                height: 'auto',
                pager: '#pagination',
                sortname: 'registerDate',
                sortorder: 'desc',
                onSelectAll:function(ids,status){
                    if(status){
                         memberIds =  hebing_array(memberIds,ids)
                    } else{
                        memberIds =  chaji_array(memberIds,ids)
                    }
                    $("#memberIds").val(memberIds.join(","));
                },
                onSelectRow:function(rowid,status){
                    var index = memberIds.indexOf(rowid)
                    if(status){
                        if(index == -1){
                            memberIds.push(rowid);
                        }
                    } else{
                            memberIds.splice(index,1);
                    }
                    $("#memberIds").val(memberIds.join(","));
                }
            });

            $(function(){
                $('.chosen-select').chosen({});
                $("#templateId").change(function(){
                    var storeId = $(this).find("option:selected").attr("storeId");
                    var couponName = $(this).find("option:selected").attr("couponName");
                    var count = $(this).find("option:selected").attr("count");
                    $("#templateId").next("div").next("span").remove();
                    if(count != undefined){
                        $("#couponName").val(couponName);
                        $("#storeId").val(storeId);
                        $("#templateId").next("div").after('<span> 剩余'+count+'张，如果不足将自动生成</span>');
                    }

                });
                //验证
                $("#form").validate({
                    submitHandler: function(form){
                        $("#form").find(":submit").attr("disabled", true).attr("value",
                                "保存中...").css("letter-spacing", "0");
                        form.submit();
                    }
                });
            });

            function hebing_array(a,b) {
                for (var i = 0, j = 0, ci, r = {}, c = []; ci = a[i++] || b[j++]; ) {
                    if (r[ci]) continue;
                    r[ci] = 1;
                    c.push(ci);
                }
                return c;
            }

            function chaji_array(arr1,arr2){
                var arr3 = [];
                for (var i = 0; i < arr1.length; i++) {
                    var flag = true;
                    for (var j = 0; j < arr2.length; j++) {
                        if (arr2[j] == arr1[i]) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        arr3.push(arr1[i]);
                    }
                }
                return arr3;
            }
        </script>

