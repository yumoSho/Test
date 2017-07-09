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
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/brand/index" title="商品管理">商品管理</a> &gt;
            <a href="${ctx}/admin/model/index" title="商品模型">商品模型</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <!-- Begin 选项卡 -->
            <div class="tab-wrap">
                <ul class="tab">
                    <li class="on" data-value="0"><a href="javascript:void(0);" title="基本信息">基本信息</a></li>
                    <li data-value="1"><a href="javascript:void(0);" title="商品属性">商品属性</a></li>
                    <li data-value="2"><a href="javascript:void(0);" title="商品规格">商品规格</a></li>
                    <li data-value="3"><a href="javascript:void(0);" title="商品参数">商品参数</a></li>
                </ul>
            </div>
            <!-- //End 选项卡 -->

            <div class="tab-ct-wrap">
                <!-- Begin 选项卡1 -->
                <form action="../update" method="post">
                    <div class="tab1">
                        <table class="table-module01" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <th width="160" align="right" valign="top">名称：<b class="color-red">*</b></th>
                                <td valign="top">
                                    <input type="hidden" name="id" value="${model.id}">
                                    <input type="text" name="name" value="${model.name}">
                                    <input type="hidden" name="TOKEN" value="${TOKEN}">
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right">是否启用属性：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useAttribute"
                                                  value="1" ${model.useAttribute ? 'checked' : ''} >是</label>
                                    <label><input type="radio" name="useAttribute"
                                                  value="0" ${model.useAttribute ? '' : 'checked'} >否</label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right">是否启用规格：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useSpec" value="1" ${flag?'onclick="return false"':''} ${model.useSpec ? 'checked' : ''}>是</label>
                                    <label><input type="radio" name="useSpec" value="0" ${flag?'onclick="return false"':''} ${model.useSpec ? '' : 'checked'}>否</label>
                                    ${flag?'<label style="color:red">规格已关联商品不能被禁用或启用</label>':''}
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right">是否启用参数：<b class="color-red">*</b></th>
                                <td>
                                    <label><input type="radio" name="useParameter"
                                                  value="1" ${model.useParameter ? 'checked' : ''}>是</label>
                                    <label><input type="radio" name="useParameter"
                                                  value="0" ${model.useParameter ? '' : 'checked'} >否</label>
                                </td>
                            </tr>
                            <tr>
                                <th width="160" align="right">&nbsp;</th>
                                <td>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit js-next-step" type="button" value="下一步"/>
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <a href="../index" class="btn-cancel">取消</a>
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //End 选项卡1 -->

                    <div class="tab1" style="display:none;" >
                        <button class="btn-sure" type="button" id="btn-add-attr">添加属性</button>
                        <table class="table-module01 table-module02" cellpadding="0" cellspacing="0">
                            <thead>
                            <tr>
                                <th width="17%">属性名称</th>
                                <th width="17%">属性别名</th>
                                <th width="14%">展示类型</th>
                                <th width="14%">属性值</th>
                                <th width="17%">排序</th>
                                <th width="17%">是否显示</th>
                                <th width="4%">操作</th>
                            </tr>
                            </thead>
                            <tbody id="attr-list">
                            <c:set var="m" value="${model}"/>
                            <c:forEach var="attr" varStatus="st" items="${m.attributes}">
                                <tr>
                                    <td valign="top">
                                        <input type="hidden" name="attributes[${st.index}].id" value="${attr.id}">
                                        <input type="text" name="attributes[${st.index}].name" class="required repeatAttrValidate attr"
                                               value="${attr.name}" maxlength="5">
                                    </td>
                                    <td valign="top">
                                        <input type="text" name="attributes[${st.index}].alias" value="${attr.alias}">
                                    </td>
                                    <td valign="top">
                                        <select name="attributes[${st.index}].displayType" style="width: 100%;">
                                            <option value="0" ${0 eq attr.displayType ? 'selected':''}>选择</option>
                                            <option value="1" ${1 eq attr.displayType ? 'selected':''}>文本</option>
                                            <option value="2" ${2 eq attr.displayType ? 'selected':''}>区间</option>
                                        </select>
                                    </td>
                                    <td valign="top">
                                        <c:choose>
                                            <%-- 选择 --%>
                                            <c:when test="${0 eq attr.displayType}">
                                                <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].id" value="${v.id}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].value" value="${v.value}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].sort" value="${v.sort}">
                                                </c:forEach>
                                                <select size="999999" style="width: 142px; height: 50px;">
                                                    <c:forEach var="v" items="${attr.attributeValues}">
                                                        <option value="${v.value}" data-id="${v.id}"
                                                                data-sort="${v.sort}">${v.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <button type="button" class="btn-sure" data-act="edit-s" style="display:block;margin-top:10px;">编辑
                                                </button>
                                            </c:when>
                                            <%-- 区间 --%>
                                            <c:when test="${2 eq attr.displayType}">
                                                <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].id" value="${v.id}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].value" value="${v.value}">
                                                    <input type="hidden" name="attributes[${st.index}].attributeValues[${vvs.index}].sort" value="${v.sort}">
                                                </c:forEach>
                                                <select size="999999" style="width: 142px; height: 50px;">
                                                    <c:forEach var="v" varStatus="vvs" items="${attr.attributeValues}">
                                                        <option value="${fn:split(v.value,'-')[0]},${fn:split(v.value,'-')[1]}" data-id="${v.id}" data-sort="${v.sort}">${v.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <button type="button" class="btn-sure" data-act="edit-r" style="display:block;margin-top:10px;">编辑
                                                </button>
                                            </c:when>
                                            <%-- 文本 --%>
                                            <c:otherwise>
                                                <input type="hidden" name="attributes[${st.index}].attributeValues[0].id" value="${attr.attributeValues[0].id}">
                                                <input type="hidden" name="attributes[${st.index}].attributeValues[0].value" style="width:120px;" value="${attr.attributeValues[0].value}">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td valign="top">
                                        <span class="spinner">
                                            <input type="text" name="attributes[${st.index}].sort" class="required digits" value="${attr.sort}" max="999">
                                            <span class="spin-buttons">
                                                <span class="btn-up"></span>
                                                <span class="btn-down"></span>
                                            </span>
                                        </span>
                                    </td>
                                    <td valign="top">
                                        <select name="attributes[${st.index}].visible" style="width: 100%;">
                                            <option value="1" ${attr.visible?'selected':''}>可见</option>
                                            <option value="0" ${attr.visible?'':'selected'}>隐藏</option>
                                        </select>
                                    </td>
                                    <td valign="top">
                                        <div class="operateBox" >
                                            <img src="${ctx}/images/admin/icon-delete01.png" width="15" height="15" title="Delete" alt="Delete">
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tbody>
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
	                                        <input class="btn-cancel js-pre-step" type="button" value="返回上一步">
	                                    </span>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="tab1" style="display:none;" >
                        <button class="btn-sure" type="button" id="btn-add-spec">添加规格</button>
                        <div id="spec-sel-dlg" style="width: 598px">
                            <table id="spec-grid"></table>
                            <div id="spec-pagination" style="width: 400px;"></div>
                        </div>
                        <table class="table-module01 table-module02">
                            <thead>
                            <tr>
                                <th width="25%">规格名称</th>
                                <th width="25%">规格别名</th>
                                <th width="25%">排序</th>
                                <th width="25%">操作</th>
                            </tr>
                            </thead>
                            <tbody id="spec-list">
                            <c:forEach var="ms" varStatus="st" items="${model.modelSpecs}">
                                <tr data-id="${ms.id}">
                                    <td valign="top">
                                        <input type="hidden" name="modelSpecs[${st.index}].id" value="${ms.id}"/>
                                            ${ms.spec.name}
                                        <input type="hidden" name="modelSpecs[${st.index}].spec.id" value="${ms.spec.id}">
                                    </td>
                                    <td valign="top">${ms.spec.alias}</td>

                                    <td valign="top" style="text-align:center;">
                                        <span class="spinner">
                                            <input type="text" name="modelSpecs[${st.index}].sort" class="required digits" max="999" value="${ms.sort}">
                                            <span class="spin-buttons">
                                                <span class="btn-up"></span>
                                                <span class="btn-down"></span>
                                            </span>
                                        </span>
                                    </td>
                                    <td valign="top">
                                        <div class="operateBox" style="padding-top:4px;">
                                            <img <%-- onclick="$(this).closest('tr').remove();" --%>src="${ctx}/images/admin/icon-delete01.png" width="15" height="15" title="Delete" alt="Delete">
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
							</tbody>
                            <tfoot>
                            	<tr>
	                                <td colspan="5" style="text-align: center">
	                                	<div class="at_btn" style="width: 100%;">
                                             <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
		                                    <span class="btn-sure-wrap">
		                                        <input class="btn-sure btn-edit js-next-step" type="button" value="下一步"/>
		                                    </span>
		                                    <span class="btn-cancel-wrap">
		                                        <input class="btn-cancel js-pre-step" type="button" value="返回上一步">
		                                    </span>
	                                    </div>
	                                </td>
	                            </tr>
                            </tfoot>
                        </table>
                    </div>

                    <div class="tab1" style="display:none;" >
                        <button type="button" class="btn-sure" id="btn-add-param-group">添加参数组</button>
                        <div id="param-group-list" style="margin-top:10px;">
                            <c:forEach var="group" varStatus="st" items="${model.parameters}">
                                <div style="margin-bottom:10px;">
                                    <input type="hidden" name="parameters[${st.index}].id" value="${group.id}">
                                    <table class="table-module01">
                                        <colgroup>
                                            <col style="width:20%"></col>
                                            <col style="width:50%"></col>
                                            <col style="width:30%"></col>
                                        </colgroup>
                                        <thead style="border-bottom:1px solid #ddd;background:#f7f7f7;">
                                        <tr>
                                            <td colspan="2" width="70%">
                                                参数组名称:
                                                <input type="text" name="parameters[${st.index}].name" class="required groupPara repeatParamValidate"
                                                       minlength="2" maxlength="12" value="${group.name}"/>
                                            </td>
                                            <td>
                                                <button class="btn-sure" type="button">添加参数</button>
                                                <button class="btn-cancel"
                                                        type="button"<%-- onclick="$(this).closest('div').remove();"--%>>移除
                                                </button>
                                            </td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="p" varStatus="pst" items="${group.children}">
                                            <tr style="border-bottom:1px dotted #ddd;">
                                                <th valign="top" width="20%">参数名称</th>
                                                <td valign="top" width="50%">
                                                    <input type="hidden"
                                                           name="parameters[${st.index}].children[${pst.index}].id"
                                                           value="${p.id}">
                                                    <input type="text"
                                                           name="parameters[${st.index}].children[${pst.index}].name"
                                                           class="required childPara repeatParamChildValidate" minlength="2" maxlength="12" value="${p.name}">
                                                </td>
                                                <td valign="top">
                                                    <button class="btn-cancel" type="button"
                                                            onclick="$(this).closest('tr').remove();">移除
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:forEach>
                        </div>
                        <table>
                            <tr>
                                <td>
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存"/>
                                    </span>
                                    <span class="btn-cancel-wrap">
                                        <input class="btn-cancel js-pre-step" type="button" value="返回上一步">
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id="cats-dlg" style="display: none;height: 330px;background-color: #fff; width: 500px; left: 280px; top: 50px; z-index: 9002;position: absolute;box-shadow: 3px;border-radius: 5px;border:solid #ccc;">
                        <div class="panel-header panel-header-noborder window-header" style="width: 500px;background: transparent;padding: 0;height: 40px;background-color: #a6c542;">
                            <div class="panel-title" style="height: 40px;line-height: 40px;text-indent: 20px;color: #fff;font-weight: bold;font-size: 14px;">编辑属性值</div>
                            <div class="panel-tool" style="    right: 10px;position: absolute;margin-top: -30px;height: 24px;overflow: hidden;">
                                <a class="panel-tool-close" href="javascript:void(0)" style="    display: inline-block;width: 24px;height: 24px;opacity: 0.6;margin: 0 0 0 2px;vertical-align: top;    background-image: url(${ctx}/../images/admin/icon-close.png);    background-repeat: no-repeat;"></a>
                            </div>
                        </div>

                    </div>
                    <div id="cats-dlg1" style="display: none;height: 330px;background-color: #fff; width: 500px; left: 280px; top: 50px; z-index: 9002;position: absolute;box-shadow: 3px;border-radius: 5px;border:solid #ccc;">
                        <div class="panel-header1 panel-header-noborder window-header" style="width: 500px;background: transparent;padding: 0;height: 40px;background-color: #a6c542;">
                            <div class="panel-title" style="height: 40px;line-height: 40px;text-indent: 20px;color: #fff;font-weight: bold;font-size: 14px;">编辑属性值</div>
                            <div class="panel-tool" style="    right: 10px;position: absolute;margin-top: -30px;height: 24px;overflow: hidden;">
                                <a class="panel-tool-close1" href="javascript:void(0)" style="    display: inline-block;width: 24px;height: 24px;opacity: 0.6;margin: 0 0 0 2px;vertical-align: top;    background-image: url(${ctx}/../images/admin/icon-close.png);    background-repeat: no-repeat;"></a>
                            </div>
                        </div>

                    </div>
                </form>
            </div>
        </div>
        <!-- //End 主内容区 -->
    </div>

    <%@include file="modelTpl.jspf" %>
    <%@include file="attrTpl.jspf"%>
    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/model-edit.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/product/attributes.js"></script>
    <script type="text/javascript">
        $(function(){
            $('.spinner').spinner({
                max: 2147483647
                , min: 0
                , step: 1
                , allowEmpty: true
                , minusBtn: '.btn-down'
                , plusBtn: '.btn-up'
            });
        });
        var $z = $('#mod-grid').jqGrid({
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
     function removeTr(a){
         a.parent().parent().remove();
     }
        $(".tab li").live("click", function (index, element) {
            var n = $(this).index();
            showTab(n);
            /*
             $(this).addClass("on").siblings().removeClass("on");
             $(".tab-ct-wrap .tab1").eq(n).show().siblings().hide();
             */
        });

        $('.js-next-step').each(function(i, el) {
            $(el).click(function() {
                var index = $('.tab li.on').nextAll().not('[style="display: none;"]').attr('data-value');
                showTab(index);
            });
        });

        $('.js-pre-step').each(function(i, el) {
            $(el).click(function() {
                var index = $('.tab li.on').prevAll().not('[style="display: none;"]').attr('data-value');
                showTab(index);
            });
        });

        //
        $('form:last').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 12,
                    remote: {
                        url: '../check',
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