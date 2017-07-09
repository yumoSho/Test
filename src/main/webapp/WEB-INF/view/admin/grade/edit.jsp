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
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/member/index" ; title="会员管理">会员管理</a> &gt;
            <a href="${ctx}/admin/grade/index" ; title="会员管理">会员等级管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/grade/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${grade.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tr>
                        <th width="130" align="right" style="border-bottom:#aaa 1px solid ">名称：<b class="color-red">*</b></th>
                        <td style="border-bottom:#aaa 1px solid ">
                            <input type="text" name="name" value="${grade.name}" maxlength="20"/>

                        </td>
                    </tr>
                    <tr >
                        <th width="130" valign="top" align="right">商品类别折扣：<b class="color-red">*</b></th>
                        <td>
                            <table id="discount">
                                <tbody>
                                <c:forEach items="${grade.details}" var="gd" varStatus="index">
                                    <tr index = "${index.index}">
                                        <td>
                                            <select name="categoryId[${index.index}]" style="width: 120px;" class="required">
                                                <option value="">请选择分类</option>
                                                <!-- 循环语句 for-->
                                                <c:forEach items="${categoryList2}" var="cg" >
                                                    <option value="${cg.id}" <c:if test="${gd.categoryId == cg.id}">selected</c:if> >${cg.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="number" step="0.1" min="0" max="10" name="discount[${index.index}]" value="${gd.discount}" style="width: 120px;" class="required">
                                        </td>
                                        <td>
                                            ※注：如折扣输入8.5折扣为8.5折。
                                            <a href="javascript:void(0)" onclick="rowRM(this)">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </td>

                    </tr>
                    <tr>
                        <th>

                        </th>
                        <td>
                            <a href="javascript:void(0)" onclick="rowAdd()">添加</a>
                            <span id="validMsg" style="color: red;font-size: 16px;margin-left: 30px;"></span>
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
                </table>
            </form>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <m:f/>
    <script src="${ctx}/js/BaiduTemplate.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/adminIndex.js"></script>
    <%--行模板--%>
    <script type="text/html" id="rowTmpl">
        <tr index = "<!=index!>">
            <td>
                <select name="categoryId[<!=index!>]" style="width: 120px;" class="required">
                    <option value="">请选择分类</option>
                    <!-- 循环语句 for-->
                    <!for(var i=0;i< list.length;i++){!>
                    <option value="<!=list[i].id!>"><!=list[i].name!></option>
                    <!}!>
                </select>
            </td>
            <td>
                <input type="number" step="0.5" min="0" max="10" name="discount[<!=index!>]" style="width: 120px;" class="required">
            </td>
            <td>
                ※注：如折扣输入8.5折扣为8.5折。
                <a href="javascript:void(0)" onclick="rowRM(this)">删除</a>
            </td>
        </tr>
    </script>

    <script type="text/javascript">
        //给模板传值
        var rowData = {list:toObject("${categoryList}")};

        $(function () {
            $(".btn-cancel-wrap input").click(function () {
                window.location.href = contextPath + "/admin/grade/index";
            });
            $("#form").validate({
                rules: {
                    "name": {required: true}/*,
                     "discount[]":{required:true},
                     "categoryId[]":{required:true}*/
                },
                submitHandler: function (form) {
                    //检查分类是否重复
                    var categoryIds = [];
                    var flag = true;
                    $("#discount tr").each(function(i,obj){
                        var categoryId = $(this).find("select").find("option:selected").val();
                        if(categoryIds.indexOf(categoryId) == -1){
                            categoryIds.push(categoryId);
                        }else{
                            $("#validMsg").text("分类重复");
                            flag =false;
                            return false;}

                    });
                    if(categoryIds.length < 1){
                        $("#validMsg").text("请填写数据");
                        return false;
                    }
                    if(!flag) return false;
                    $("#form").find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }
            })
            ;
        })
        //行添加
        function rowAdd(){
            var bt;
            if(!bt){
                bt = baidu.template;
                bt.LEFT_DELIMITER='<!';
                bt.RIGHT_DELIMITER='!>'
            }
            var $table =  $("#discount");
            var index =$table.find("tr").size();
            rowData.index = index;
            var html = bt('rowTmpl',rowData);
            $table.find("tbody").append(html);
        }
        //行删除
        function rowRM(obj){
            var $curTR = $(obj).closest("tr");
            var index = Number($curTR.attr("index"));
            var nextTR = $curTR.nextAll("tr");
            $curTR.remove();
            nextTR.each(function(i,data){
                var $sel = $(this).find("select");
                var $ipt = $(this).find("input");
                $(this).attr("index", + (index + i));
                $sel.attr("name","categoryId[" + (index + i) +"]");
                $ipt.attr("name","discount[" + (index + i) +"]");
            });
        }
    </script>


