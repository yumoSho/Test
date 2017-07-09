<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <%@include file="../../include/elibs.jspf" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/admin/uploader.css">
    <style>
        .editPage ul {
            margin: 0;
            padding: 0;
            list-style: none;

        }

        .editPage ul li {
            list-style: none;
            float: left;
        }
        .tpl-list li {
            border: 1px solid #ccc;
            padding: 5px;
            margin: 3px;
            cursor: pointer;
        }
        .suits{width: auto;height: auto;}

        #packageDetail{
            width: 650px;
            height: 300px;
            overflow-x: auto;
        }
        #packageDetail > div,#packageDetail > div > div{
            width: 100%;
        }
        #recoul{
            width: 100% !important;
            padding-left: 0;
        }
        #recoul::after{
            content: "";
            clear: both;
        }
        #recoul > li{
            width: 150px;
            float: left;
        }
        .suits ul li s{
            margin-right: 10px;
        }
        .window{

        }
        .suits_2 {
            width: 100% !important;}
        .panel{
            top:40px;
        }
    </style>


</m:h>
<%@include file="../../include/admin/support.jspf" %>
<%@include file="../../include/admin/adminHead.jspf" %>

<html>
<body>
<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>

    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/packages/index" title="活动管理">活动管理</a> &gt;
            <a href="${ctx}/admin/accessoy/index" title="商品配件">商品配件</a>&gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="tab1">
            <!-- <input type="button" class="btn-sure" onclick="preview()" value="预览"> -->
            <input class="btn-cancel" type="button" value="返回"
                   onclick="history.back();">
            <table class="table-module01 table-module02"
                   style="border: 1px solid #ccc;">
                <tbody>
                <!-- <tr>
                        <td>
                            <button class="btn-sure" type="button" id="btn-mod-sel">选择供应商</button>
                            <input readonly="readonly" class="shopName" style="padding:0 20px;text-align: center;height: 26px;">
                            <input type="hidden" readonly="readonly" class="shopId" style="width: 300px;height: 26px;">
                        </td>
                    </tr> -->
                <tr>
                    <td>
                        <button class="btn-sure" type="button" id="btn-primary-sel">添加主要商品</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="primaryProduct">
                            <div class="suits">
                                <ul>
                                    <li><div class="p-img">
                                        <a href="javascript:void(0)"  title="${productPackage.primaryGoods.title}"> <img
                                                src="${ctx}/${productPackage.primaryGoods.image}" width="99" height="99">
                                        </a>
                                    </div>
                                        <div class="p-name">
                                            <a href="javascript:void(0)" title="${productPackage.primaryGoods.title}">${productPackage.primaryGoods.title}</a>
                                        </div></li>
                                </ul>
                            </div>
                        </div>
                        <input type="hidden" id="combine_id" value="${productPackage.id}">
                        <input type="hidden" id="pryProductId" value="${productPackage.primaryProduct.id}">
                        <input type="hidden" id="pryGoodsId" value="${productPackage.primaryGoods.id}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="btn-sure" type="button" id="btn-packages-sel">添加组合产品</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="table-module01 table-module02" id="acce-list">
                            <tbody>
                            <tr class="packagesTr">
                                <td><div class="em_list" data-composes="productId">
                                    <h2 class="clearfix">
                                        <span class="fl">配件名称：<b>${productPackage.name}</b></span>

														<span class="fr"><input type="hidden" id="id" value="19"><a
                                                                href="javascript:void(0);" class="editCombineD">编辑</a><a
                                                                href="javascript:void(0);" class="deleteCombineD">移除</a></span>
                                    </h2>
                                    <div class="suits suits_2">
                                        <div class="em_list">
                                            <div class="suits suits_2">
                                                <ul id="recoul" style="width: 290px;">
                                                    <c:forEach items="${productPackage.packageDetails}" var="combineDetail" varStatus="stat">

                                                            <li><c:if test="${!stat.last}"><s></s></c:if>
                                                                <div class="p-img">
                                                                    <a href="javascript:void(0)" title="${combineDetail.goods.title}">
                                                                        <img src="${ctx}/${combineDetail.goods.image}" width="99" height="99">
                                                                    </a>
                                                                </div>
                                                                <div class="p-name">
                                                                    <a href="javascript:void(0)" title="${combineDetail.goods.title}">${combineDetail.goods.title}</a>
                                                                </div>
                                                            </li>

                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center">
						<span class="btn-sure-wrap br">
							<input id="btn_sure" class="btn-sure" type="button" value="保存" ">
						</span>
						<span class="btn-cancel-wrap br">
							<input class="btn-cancel" type="button" value="返回" onclick="history.back();">
						</span></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="addPackagesDlg" style="display: none">
            <div class="tab1" id="addPackagesDiv">
                <form id="combine-Form">
                    <h2
                            style="border-bottom: 1px solid #ddd; padding-left: 2px; margin-bottom: 10px;">添加配件产品</h2>
                    <table class="table-module01" cellpadding="0" cellspacing="0"
                           style="border: 0;">
                        <tr>
                            <th width="130" align="right"><b class="color-red">*</b>配件名称：</th>
                            <td>
                                <input class="easyui-textbox" type="text" id="combineName" name="combineName" value="${productPackage.name}"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">选择商品：</th>
                            <td><input type="button" id="selectScdGoods" class="btn-sure" value="选择商品" onclick=""></td>
                        </tr>
                        <tr>
                            <th width="130" align="right"></th>
                            <td>
                                <div id="packageDetail">
                                    <div class="em_list">
                                        <div class="suits suits_2">
                                            <ul id="recoul" style="width: 290px;">
                                                <c:forEach items="${productPackage.packageDetails}" var="combineDetail" varStatus="stat">

                                                        <li><c:if test="${!stat.last}"><s></s></c:if>
                                                            <div class="p-img">
                                                                <a href="javascript:void(0)">
                                                                    <img src="${ctx}/${combineDetail.goods.image}" width="99" height="99">
                                                                </a>
                                                            </div>
                                                            <div class="p-name">
                                                                <a href="javascript:void(0)">${combineDetail.goods.title}</a>
                                                            </div>
                                                        </li>

                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" id="ids" value="${ids}">
                                <input type="hidden" id="goodsIds" value = "${goodsIds}">
                                <input type="hidden" id="productIds" value = "${productIds}">
                            </td>
                        </tr>

                    </table>
                </form>
            </div>
        </div>
        <div id="secondaryProductDlg">
            <div id="secondaryProductGrid"></div>
        </div>
        <div id="primaryProductDlg" style="display: none">
            <table id="primaryProductGrid"></table>
            <div id="pagination" style="height: 36px"></div>
        </div>
        <div id="goodsDlg" style="display: none">
            <table id="goodsGrid"></table>
            <div id="goodsPagination" style="height: 36px"></div>
        </div>
    </div>
</div>
</body>
<%--<%@include file="../../include/admin/support.jspf" %>--%>
<%--<%@include file="../../include/elibs.jspf" %>--%>
<%@include file="packagesTpl.jspf" %>
<%@include file="primaryProductTpl.jspf" %>
<%@include file="packageDetailTpl.jspf" %>
<script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/mustache.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/marketing/packagesAddAccessoy.js"></script>
<script type="text/javascript">
    $('#combine-Form').validate({
        "ignore" : ":hidden:not(.ni)",
        "rules" : {
            "combineName" : {
                "required" : true
            },

            "combineNote" : {
                "maxlength" : 255
            }
        }
    });
</script>

</html>
