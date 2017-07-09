<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
    <style>
        .upload-entry-icon img{
            width:110px;height:110px;
        }
        .content-fix tr td p {
            height: 30px;
            overflow: hidden;
        }
        label.error {
            text-align: left;
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
            <span>您当前的位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/activity/index" title="营销管理">营销管理</a> &gt;
            <a href="${ctx}/admin/discount/index" title="限时抢购">限时抢购</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
                <form action="save" method="post">
                    <table class="table-module01" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <td colspan="6">
                                    <span class="btn-sure-wrap">
									    <input class="btn-sure" type="button" id="sel-goods" value="选择商品"/>
								    </span>
                                </td>
                            </tr>
                            <tr>
                                <td width="19%">商品名称</td>
                                <td width="19%">商品分类</td>
                                <td width="19%">排序</td>
                                <td width="19%">折扣<span style="color: red">(如折扣输入8.5折扣为8.5折)</span></td>
                                <td width="19%">活动时间</td>
                                <td width="5%">操作 <input type="hidden" name="TOKEN" value="${TOKEN}"></td>
                            </tr>
                        </thead>
                        <tbody class="tab1" id="goods-list">
                        <c:forEach items="${discountGoodses}" var="i" varStatus="j">
                            <tr>
                                <th width="19%">
                                    <input name="discountGoods[${j.index}].id" type="hidden" value="${i.id}">
                                    <input name="discountGoods[${j.index}].goods.id" type="hidden" value="${i.goods.id}">
                                    <input name="" type="text" disabled value="${i.goods.title}">
                                </th>
                                <th width="19%">
                                    <input name=""  type="text" disabled value="${i.goods.product.category.name}">
                                </th>
                                <th width="19%">
                                    <input name="discountGoods[${j.index}].sort" type="text" class="digits required" value="${i.sort}">
                                </th>
                                <th width="19%">
                                    <input type="text" name="discountGoods[${j.index}].discount" class="required" min="0.01" max="10.00" value="${i.discount}"/>
                                </th>
                                <td width="19%">
                                    <input name="discountGoods[${j.index}].startDate" class="Wdate required " type="text" id="start${j.index}" value="<fmt:formatDate value="${i.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />"
                                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'2099-12-31'})"/>
                                    <label for="discountGoods[${j.index}].startDate" generated="true" class="error" style="display:none">此项为必填项</label>
                                    <input name="discountGoods[${j.index}].endDate" class="Wdate required " type="text" id="end${j.index}"  value="<fmt:formatDate value="${i.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'2099-12-31'})"/>
                                    <label for="discountGoods[${j.index}].endDate" generated="true" class="error" style="display:none">此项为必填项</label>
                                </td>
                                <th width="5%">
                                    <div class="operateBox"  style="padding-top:4px;">
                                        <img src="${ctx}/images/admin/icon-delete01.png" onclick="removeTr(this);" width="15" height="15" title="Delete" alt="Delete">
                                    </div>
                                </th>
                            </tr>

                        </c:forEach>
                        </tbody >
                        <tfoot>
                        <tr>
                            <th width="130" align="right">&nbsp;</th>
                            <td>
								<span class="btn-sure-wrap">
									<input class="btn-sure btn-edit" type="submit" value="保存"/>
								</span>
								<span class="btn-cancel-wrap">
                                    <a href="index" class="btn-cancel">取消</a>
                                </span>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </form>
            <%-- TAB CONTENT END --%>
            <div id="goods-list1" style="display: none;width:590px">
                <table id="datagrid" style="width: 598px"></table>
                <div id="pagination" style="width: 598px"></div>
            </div>
        </div>

        <!-- //End 主内容区 -->
    </div>
        <%@include file="goodsTpl.jspf"%>
        <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
        <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
        <script type="text/javascript" src="${ctx}/js/admin/mustache.js"></script>
        <script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
        <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
        <script type="text/javascript" src="${ctx}/js/admin/marketing/activity-goods-edit.js"></script>
    <script type="text/javascript">
        $(function(){
            jQuery.validator.addMethod("decimalValidate", function(value, element) {
                var tel = /^(([1-9]+\d*)|([0-9]+\d*\.\d{1,2}))$/;
                return this.optional(element) || (tel.test(value));
            }, "请填写大于0的小数并可以保留两位小数");
            $('.chosen-select').chosen({});
            var goodsId = new Array();
            var activityTypeVal=0
           




            function delTr(){
                goodsId.shift($(this).parent().parent().find("input[name$='goods.id']").val());
                $(this).parent().parent().remove();
                $(".content-discount").find('tr').each(function (i, tr) {
                    $(tr).find('input[name]:enabled').each(function (j, el) {
                        $(el).attr('name', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
                        $(el).siblings("label").attr('for', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
                    });
                });
                $(".content-fix").find('tr').each(function (i, tr) {
                    $(tr).find('input[name]:enabled').each(function (j, el) {
                        $(el).attr('name', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));
                        $(el).siblings("label").attr('for', $(el).attr('name').replace(/\[[0-9]*\]/, '[' + i + ']'));

                    });
                });
                if(goodsId.length==0){
                    $("#table-discount").hide();
                    $("#table-fix").hide();
                }
            }
        })
        $('form:last').validate({
            rules: {
                activityName: {
                    required: true,
                    minlength: 1,
                    maxlength: 15,
                    remote: {
                        url: 'check',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: function () {
                                return $('input[name=id]').val();
                            },
                            name: function () {
                                return $('input[name=name]').val();
                            }
                        }
                    }
                },
                discount:{
                    required: function(){
                        var at =$("input[name='activityType']:checked").val();
                        if (at=="0"){

                            return true;
                        }else{
                            return false;
                        }
                    },
                    min:0.1,
                    max:10
                },
                startDate:{
                    required:true
                },
                endDate:{
                    required:true
                },
                alias: {
                    maxlength: 12
                },
                sort: {
                    required: true,
                    digits: true,
                    max: 999
                },

            },
            messages:{
                startDate:"请填写开始时间",
                endDate: "请填写结束时间"
            },
            errorPlacement: function(error, element) {
                if(element.attr("name")=="enabled" || element.attr("name")=='recommend') {
                    error.appendTo(element.parent().parent())
                }else{
                    error.appendTo(element.parent());
                }
            },
            submitHandler: function(form){
                var c = $("#goods-list").find("tr").length;
                /* if(c<=0) {
                    Glanway.Messager.alert("提示", "请选择商品");
                    return false
                } */
                if(  imgValidate.validateImg()){
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }

            }
        });
    </script>

<m:f/>