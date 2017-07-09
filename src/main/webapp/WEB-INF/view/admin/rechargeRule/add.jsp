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
            <a href="${ctx}/admin/website/index" title="平台管理">平台管理</a> &gt;
            <a href="${ctx}/admin/rechargeRule/index" title="">充值返利规则管理</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="save" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
            <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                <tbody>


                <tr>
                    <th width="130" align="right">充值金额：<b class="color-red">*</b></th>
                    <td>
                          <span id="money-spinner" class="spinner">
                                <input id="money" name="money" class="input" value="">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span>
                        <script>
                            $('#money-spinner').spinner({
                                max: 100000
                                , min: 0
                                , step: 1
                                , allowEmpty: true
                                , minusBtn: '.btn-down'
                                , plusBtn: '.btn-up'
                            });
                        </script>
                        
                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">返现比例：<b class="color-red">*</b></th>
                    <td>
                          <span id="discount-spinner" class="spinner">
                                <input id="discount" name="discount" class="input" value="">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span> %
                        <script>
                            $('#discount-spinner').spinner({
                                max: 1000
                                , min: 0
                                , step: 1
                                , allowEmpty: true
                                , minusBtn: '.btn-down'
                                , plusBtn: '.btn-up'
                            });
                        </script>

                    </td>
                </tr>
                <tr>
                    <th width="130" align="right">排序：<b class="color-red">*</b></th>
                    <td>
                          <span id="sort-spinner" class="spinner">
                                <input id="sortNum" name="sortNum" class="input" value="1">
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
  </div>
    <m:f/>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>

    <script type="text/javascript" src="${ctx}/js/admin/initeditor.js"></script>
    <script type="text/javascript">

        $(function(){
            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/rechargeRule/index";
            });

            //验证
            $("#form").validate({
                rules:{
                    money:{
                    	 required: true,
                         maxlength: 20
                          },
                    discount:{
                    	required: true,
                        maxlength: 100
                          },
                    sortNum:{
                    	required: true,
                    	digits:true,
                    	max: 999
                    },
                                     
                },
            submitHandler: function(form){
                $("#form").find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                form.submit();
            }

        });
        });

    </script>

