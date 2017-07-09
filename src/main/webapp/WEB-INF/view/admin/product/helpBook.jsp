<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../include/taglibs.jspf" %>
<m:h>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/easyui/themes/icon.css">
    <%@include file="../../include/elibs.jspf" %>
    <style>
        #help .help_block h1 {font-weight: bold;color: #990000;font-size: 16px;float: left;margin-bottom: 5px;border-bottom: 1px solid #09c;padding: 0 10px;}
        #help .help_block * {clear: both;}
        .tableFrame {font-size: 12px;background: #fff;border: none 0;border-left: 1px solid #dde6ea;table-layout: fixed;font-family: Arial,'lucida grande',tahoma,helvetica,'微软雅黑',sans-serif;}
        .theadTitle th {color: #404040;padding: 0;height: 40px;line-height: 40px;background: #eaedf0;border: 1px solid #dde6ea;border-width: 1px 1px 1px 0;text-align: center;font-size: 14px;word-wrap: break-word;font-weight: bold}
        #help .help_block td {text-align: left;}
        .tableFrame td {background: #FFF;height: 26px;line-height: 26px;padding: 2px 4px;border: 1px solid #dde6ea;border-width: 0 1px 1px 0;word-wrap: break-word;word-break: break-all;}
        .bold {font-weight: bold;}
        .red{color: red}
    </style>
</m:h>
<%@include file="../../include/admin/support.jspf" %>
<%@include file="../../include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="../../include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
          <div class="position">
				<span>您当前的位置：</span>
                <a href="${ctx}/admin" title="首页">首页</a> &gt;
                <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
                <a href="${ctx}/admin/product/index" title="商品信息">商品信息</a> &gt; 出版物导入
		</div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <div id="help">

                <div class="help_block">
                    <h1>产品导入说明（填写要修改的信息即可）</h1>
                    <table class="tableFrame" width="60%" border="0" cellspacing="0" cellpadding="0">
                        <tbody><tr class="theadTitle">
                            <th width="160">可用别名</th>
                            <th>说明</th>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <span class="bold"><span class="red">注意：如下字段为必填或者需要注意格式的字段，通过EXCEL导入都是没有开启规格和未上架的商品，所有列必须和模板Excel列一一对应。</span><br></span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">产品标题</span><br>
                            </td>
                            <td><span class="red">必填字段（可接受任何字符）</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">产品简介</span><br>
                            </td>
                            <td><span class="red">必填字段，可接受任意字符</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">产品编号</span><br>
                            </td>
                            <td><span class="red">必填字段，（可接受任意字符）</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">产品类别</span><br>
                            </td>
                            <td><span class="red">必填字段，必须是已经存再的类别编号</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">售价</span><br>
                            </td>
                            <td><span class="red">必填字段，数字类型（保留小数后两位，单位：元）</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">标签</span><br>
                            </td>
                            <td><span class="">可选字段，必须为标签编号</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">库存</span><br>
                            </td>
                            <td><span class="red">必填字段，数字类型（整数类型）</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">重量</span><br>
                            </td>
                            <td><span class="red">必填字段，数字类型（保留小数后两位，单位：克）</span></td>
                        </tr>
                        <tr>
                            <td>
                                <span class="bold">警戒库存</span><br>
                            </td>
                            <td><span class="red">必填字段，数字类型（整数类型）</span></td>
                        </tr>
                        </tbody></table>
                </div>
            </div>
        </div>
        <!-- //End 主内容区 -->
    </div>

    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
    <script type="text/javascript">

    </script>
<m:f/>