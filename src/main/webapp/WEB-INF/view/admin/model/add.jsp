<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>

<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="Home">首页</a> &gt;
            <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
            <a href="${ctx}/admin/model/index" title="商品模型">商品模型</a> &gt;
            新增
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <!-- Begin 选项卡 -->
            <div class="tab-wrap">
                <ul class="tab">
                    <li class="on" data-value="0"><a href="javascript:void(0);" title="基本信息">基本信息</a></li>
                    <li data-value="1"><a href="javascript:void(0);" title="属性">属性</a></li>
                    <li data-value="2"><a href="javascript:void(0);" title="规格">规格</a></li>
                    <li data-value="3"><a href="javascript:void(0);" title="参数">参数</a></li>
                </ul>
            </div>
            <!-- //End 选项卡 -->
            <form action="save" method="post">
            <div class="tab-ct-wrap">
                <!-- Begin 选项卡1 -->

                    <div class="tab1">
                        <table class="table-module01" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <th width="130" align="right" valign="top">模型名称：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="text" name="name">
                                    <input type="hidden" name="TOKEN" value="${TOKEN}">
                                </td>
                            </tr>
                            <tr>
                                <th width="130" align="right">启用属性：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useAttribute" value="1" checked>是</label>
                                    <label><input type="radio" name="useAttribute" value="0">否</label>
                                </td>
                            </tr>
                            <tr>
                                <th width="130" align="right">启用规格：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useSpec" value="1" checked>是</label>
                                    <label><input type="radio" name="useSpec" value="0">否</label>
                                </td>
                            </tr>
                            <tr>
                                <th width="130" align="right">启用参数：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useParameter" value="1" checked>是</label>
                                    <label><input type="radio" name="useParameter" value="0" >否</label>
                                </td>
                            </tr>
                            <tr>
                                <th width="130" align="right">&nbsp;</th>
                                <td>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit js-next-step" type="button" value="下一步"/>
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <a href="index" class="btn-cancel js-pre-step">取消</a>
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //End 选项卡1 -->
                    <div class="tab1" style="display:none;">
                        <button class="btn-sure" type="button" id="btn-add-attr">添加属性</button>
                        <table class="table-module01 table-module02" cellpadding="0" cellspacing="0">
                            <thead>
                            <tr>
                                <th width="17%">属性名称</th>
                                <th width="17%">属性别名</th>
                                <th width="14%">属性类型</th>
                                <th width="14%">属性值</th>
                                <th width="17%">排序</th>
                                <th width="17%">显示</th>
                                <th width="4%">操作</th>
                            </tr>
                            </thead>
                            <tbody id="attr-list">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="7" style="text-align: center">
                                	<div class="at_btn" style="width: 100%;">
                                        <span class="btn-sure-wrap">
                                            <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                        </span>
	                                    <span class="btn-sure-wrap">
	                                        <input class="btn-sure btn-edit js-next-step" type="button" value="下一步"/>
	                                    </span>
	                                    <span class="btn-cancel-wrap">
	                                        <input class="btn-cancel js-pre-step" type="button" value="上一步">
	                                    </span>
                                    </div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                <div class="tab1" style="display:none;">
                    <button class="btn-sure" type="button" id="btn-add-spec">添加规格</button>
                    <table class="table-module01 table-module02" style="border-collapse:inherit;">
                        <thead>
                        <tr>
                            <th width="25%">规格</th>
                            <th width="25%">规格别名</th>
                            <th width="25%">排序</th>
                            <th width="25%">操作</th>
                        </tr>
                        </thead>
                        <tbody id="spec-list">
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="7" style="text-align: center">
                                <div class="at_btn" style="width: 100%">
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit js-next-step" type="button" value="下一步">
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <input class="btn-cancel js-pre-step" type="button" value="上一步">
                                    </span>
                                </div>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                    <div class="tab1" style="display:none;">
                        <button type="button" class="btn-sure" id="btn-add-param-group">添加参数组</button>
                        <div id="param-group-list" style="margin-top:10px;">
                        </div>


                        <table>
	                        <tr>
	                            <td>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <input class="btn-cancel js-pre-step" type="button" value="上一步">
                                    </span>
	                            </td>
	                        </tr>
                        </table>
                    </div>

            </div>
            </form>
        </div>
        <!-- //End 主内容区 -->
        <div id="spec-sel-dlg" style="width: 598px">
            <table id="spec-grid"></table>
            <div id="spec-pagination" style="width: 400px;"></div>
        </div>
    </div>

    <%@include file="attrTpl.jspf"%>
    <%@include file="modelTpl.jspf" %>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/model-edit.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/attributes.js"></script>
    <script type="text/javascript">
        /*var $z = $('#mod-grid').jqGrid({
            url: '${ctx}/admin/spec/list',
            datatype: 'json',
            colNames: ['id','添加规格'],
            colModel: [
                { name: 'id', index: 'id', hidden: true, key: true ,align: 'center'},
                { name: 'name', index: 'name', template: 'text', align: 'center',width:440},
            ],
            multiselect: true,
            autowidth: true,
            shrinkToFit:true,
            pager: '#pagination',
            onSelectRow: function(id){
                var celldata = $('#mod-grid').jqGrid('getCell',id,'name');
                $.post("${ctx}/admin/spec/selectOne", {id: id}, function (date) {
                    modelZc.addSpec(0,date);
                    $("#spec-sel-dlg").hide();
                    $(".pop-mask").hide();
                });
            }
        });*/


        $(".panel-tool-close").click(function(){
            $(".pop-mask").hide();
            $("#spec-sel-dlg").hide();
        });

        function showTab(i) {
            var validator = $('form:last').validate(),
                    valid = validator.checkForm();
            if (valid) {
                $('.tab li').eq(i).addClass('on').siblings().removeClass('on');
                $(".tab-ct-wrap .tab1").eq(i).show().siblings().hide();
            } else {
                validator.showErrors();
                validator.focusInvalid();
            }
        }

        $(".tab").on("click", 'li', function (index, element) {
            var n = $(this).index();
            showTab(n);
            /*
             $(this).addClass("on").siblings().removeClass("on");
             $(".tab-ct-wrap .tab1").eq(n).show().siblings().hide();
             */
        });

        $('.js-next-step').each(function (i, el) {
            $(el).click(function () {
                var index = $('.tab li.on').nextAll().not('[style="display: none;"]').attr('data-value');
                showTab(index);
            });
        });

        $('.js-pre-step').each(function (i, el) {
            $(el).click(function () {
                var index = $('.tab li.on').prevAll().not('[style="display: none;"]').attr('data-value');
                showTab(index);
            });
        });
        function  removeTr($this){
            Glanway.Messager.confirm("警告", "您确定要删除这行记录吗？",
                    function(ret){
                        if(ret){
                            $this.closest('tr').remove();
                        }
                    });
        }
        //
        $('form:last').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 20,
                    remote: {
                        url: 'check',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: function () {
                                return $('[name=id]').val();
                            },
                            name: function () {
                                return $('[name=name]').val();
                            }
                        }
                    }
                }
            },
            submitHandler: function(form){
                $(form).find(":submit").attr("disabled", true).attr("value",
                        "保存中...").css("letter-spacing", "0");
                form.submit();
            }
        });
    </script>
        <m:message message="${message}" error="${error}"/>
<m:f/>