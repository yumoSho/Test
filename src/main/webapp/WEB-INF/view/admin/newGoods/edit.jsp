<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <link rel="stylesheet" href="${ctx}/js/lib/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }

        .layout-split-east {
            border-left: #f5f5f5;
        }

        .table-module01 td {
            position: relative;
        }

        .table-module01 td .errorTip {
            position: absolute;
            left: 12px;
            top: 35px;
        }

        .table-module01 td .errorTip.errcon {
            top: 112px;
        }

        .upload-entry-icon img {
            width: 110px;
            height: 110px;
        }

        #pagination_center .ui-pg-input {
            width: 45px !important;
        }
    </style>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span> <a href="${ctx}/admin/homePage" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/newgoods/index" title="活动管理">活动管理</a> &gt;
            <a href="${ctx}/admin/newgoods/index" title="精选新品">精选新品</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <div class="tab-ct-wrap">
                <form action="${ctx}/admin/sale/update" method="post"
                      id="cat-form">
                    <table class="table-module01" cellpadding="0" cellspacing="0">
                        <tbody class="tab1">
                        <tr>
                            <th width="130" align="right">商品名称：</th>
                            <td>
                                <input name="id" type="hidden" value="${Sale.id}">
                                <input type="hidden" name="lastModifiedBy" value="${user.username}">
                                <input class="btn-sure" type="button" value="选择商品" onclick="showGoods()"/>
                            </td>
                        </tr>
                        <tr>
                            <th></th>
                            <td>
                                <table id="goods" border="1px" style="width: 50%; height: 10%">
                                    <tr>
                                        <c:forEach items="${glist.data}" var="goods">
                                            <c:if test="${Sale.goodsId==goods.id}">
                                                <td><input readonly="readonly" style="border:0px" type="button"
                                                           value="删除" onclick="deletegoods()"/></td>
                                                <td>${Sale.goodsId}</td>
                                                <td>${goods.title}</td>
                                                <td>${goods.product.title}</td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">图片：<b class="color-red">*</b></th>
                            <td valign="top">
                                <div id="upload-list" style="float:left;">
                                    <c:if test="${not empty Sale.image}">
                                        <img src="${ctx}/${Sale.image}" style="width:110px; height:110px">
                                    </c:if>
                                </div>
                            	<span class="btn-sure-wrap">
                                <input class="js-plupload btn-sure" name="image" type="button" value="上传图片"
                                       data-list-target="upload-list:thumb">
                            	</span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">折扣：<b class="color-red">*</b></th>
                            <td valign="top">
                                <input type="text" value="${Sale.discount }" name="discount" autocomplete="off">
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">开始时间：<b class="color-red">*</b></th>
                            <td valign="top">
                                <input id="startTime"
                                       value="<fmt:formatDate value="${Sale.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                       name="startTime" class="Wdate" type="text"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true, maxDate:'#F{$dp.$D(\'endTime\')||\'2099-12-31\'}'})"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">结束时间：<b class="color-red">*</b></th>
                            <td valign="top">
                                <input id="endTime"
                                       value="<fmt:formatDate value="${Sale.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                       name="endTime" class="Wdate" type="text"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2099-12-31'})"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right" valign="top">排序：<b
                                    class="color-red">*</b></th>
                            <td valign="top">
                                <input type="text" value="${Sale.position}" name="position">
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <td align="left"><span class="btn-sure-wrap"> <input
                                    class="btn-sure btn-edit" type="submit" value="保存"/>
								</span></td>
                            <td><span class="btn-cancel-wrap"> <input
                                    class="btn-cancel" type="button" value="取消" onclick=""/>
								</span></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <%-- TAB CONTENT END --%>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <div id="light" class="cms-pop" style="width:1000px;height:400px;left:30%;top:30%;">
        <div class="cms-pop-t">
            <div class="cms-pop-title">
                <b>选择商品</b>
                <b style="margin-left: 900px; cursor: pointer;" onclick="shut()">X</b>
            </div>
        </div>
        <div class="cms-pop-body">
            <table class="table-module01" width="95%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <div class="listPage">
                            <div class="table-module03">
                                <table id="datagrid"></table>
                                <div id="pagination"></div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="cms-pop-btns">
                            <span class="btn-sure-wrap"><input class="btn-sure btn-save" type="button" value="确定"
                                                               onclick="closeGoods()"/></span>
                            <span class="btn-sure-wrap"><input class="btn-sure btn-save" type="button" value="取消"
                                                               onclick="closeButton()"/></span>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
<script type="text/javascript">

    $(function () {

        $("#datagrid").setGridWidth(960);
    });

    $(".btn-cancel-wrap .btn-cancel").click(function () {
        window.location.href = "${ctx}/admin/sale/index";
    });

    $('#cat-form').validate({
        rules: {
            startTime: {
                required: true
            },
            endTime: {
                required: true
            },
            discount: {
                required: true,
                range: [0, 10]
            },
            position: {
                required: true,
                digits: true,
            }
        },
        messages: {
            startTime: {
                required: "开始时间不能为空"
            },
            endTime: {
                required: "结束时间不能为空"
            },
            discount: {
                required: "折扣不能为空",
                range: "折扣必须在0 ~ 10"
            },
            position: {
                required: "排序不能为空",
                digits: "排序必须为自然数"
            }

        },
        errorPlacement: function (error, element) {
            error.appendTo(element.siblings("span:.errorTip"));
        },
        submitHandler: function (form) {
            $(form).find(":submit").attr("disabled", true).attr("value",
                    "保存中...").css("letter-spacing", "0");
            form.submit();
        }
    });

    var $g = $('#datagrid').jqGrid({
        url: '${ctx}/admin/goods/list',
        datatype: 'json',
        colNames: ['id', '商品标题', '所属类型'],
        colModel: [
            {name: 'id', index: 'p.id', template: 'text', key: true, align: 'center'},
            {name: 'title', index: 'P.title', template: 'text', align: 'center'},
            {name: 'product.title', index: 'P.title', template: 'text', align: 'center'}
        ],
        multiselect: true,
        autowidth: true,
        shrinkToFit: true,
        autoheight: true,
        pager: '#pagination',
        sortname: 'lastModifiedDate',
        sortorder: 'desc'
    });

    function showGoods() {
        $(".pop-mask").show();
        $("#light").show();
    }
    function closeGoods() {
        var keys = $g.jqGrid('getGridParam', 'selarrrow');
        if (1 < keys.length) {
            Glanway.Messager.alert("提示", "只能选择一个商品");
        } else {
            $("#goods").html("");
            var id = $g.jqGrid('getGridParam', 'selrow');
            var rowData = $g.jqGrid('getRowData', id);
            if (null != rowData.id) {
                var producttitle = rowData["product.title"];
                var html = "<tr><td style='display:none'><input type='hidden' name='goodsId' value='" + rowData.id + "'/></td><td><input type='button'  value='删除' onclick=\"deletegoods()\"/></td><td>" + rowData.id + "</td><td>" + rowData.title + "</td><td>" + producttitle + "</td></tr>"
                $("#goods").append(html);
                $("#goods").show();
                $("#light").hide();
                $(".pop-mask").hide();
            } else {
                Glanway.Messager.alert("提示", "请选择一个商品");
            }
        }
    }

    function closeButton() {
        $(".pop-mask").hide();
        $("#light").hide();
    }

    function deletegoods() {
        $("#goods tr").remove();
    }

    function shut() {
        $("#light").hide();
        $(".pop-mask").hide();
    }
</script>

<m:f/>