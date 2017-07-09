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
    </style>
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
            <a href="${ctx}/admin/activity/index" title="活动管理">活动管理</a> &gt;
            <a href="${ctx}/admin/activity/index" title="活动管理">活动管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->

        <div class="editPage">
            <form action="../update" method="post">
                <table class="table-module01" cellpadding="0" cellspacing="0">
                    <tbody class="tab1">
                    <tr>
                        <th width="130" align="right" valign="top">活动名称：<b class="color-red">*</b></th>
                        <td valign="top"><input type="text" name="activityName" value="${m.activityName}"></td>
                        <input type="hidden" value="${m.id}" name="id"/>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">活动图片：<b class="color-red">*</b></th>
                        <td >
                            <ul class="upload-list" flag="pro-img">
                                <li class="js-plupload" name="activityImgPath" id="img1"
                                    data-list-target="img1:thumb:img">
                                    <c:if test="${not empty m.activityImgPath}">
                                        <img width="100%" height="100%" src="${ctx}/${m.activityImgPath}" style="width:110px; height:110px">
                                        <input type="hidden" name="activityImgPath" value="${m.activityImgPath}">
                                    </c:if>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th width="160" align="right" valign="top">所属银行：<b class="color-red">*</b></th>
                        <td valign="top">
                            <select name="bank.id" data-placeholder="请选择银行" class="required select chosen-select brand-chosen ni">
                                <option></option>
                                <c:forEach var="bank" items="${banks}">
                                    <option value="${bank.id}"
                                            <c:choose>
                                                <c:when test="${bank.id==m.bank.id}">
                                                    selected
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                            </c:choose>
                                            >${bank.bankName}</option>
                                </c:forEach>
                            </select>
                            <label for="bank.id" generated="true" class="error" style="display: none"></label>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">折扣类型：<b class="color-red">*</b></th>
                        <td valign="top">
                            <c:set var="activityType" value="${m.activityType}"/>
                            <label><input type="radio" name="activityType" value="0" ${activityType ==0  ?'checked':''}/>折扣</label>
                            <label><input type="radio" name="activityType" value="1" ${activityType ==1 ?'checked':''}/>固定</label>
                        </td>
                        <input type="hidden" name="TOKEN" value="${TOKEN}">
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">商品：<b class="color-red">*</b></th>
                        <td valign="top">
                                <span class="btn-sure-wrap">
									<input class="btn-sure" type="button" id="sel-goods" value="选择商品"/>
								</span>
                            <div>
                                <table class="table-module01 table-module02" id="table-discount" ${activityType==0?'':'style="display: none"'}>
                                    <thead>
                                    <tr style="text-align: center">
                                        <th width="5%">操作</th>
                                        <th width="10%">编号</th>
                                        <th width="45%">商品标题</th>
                                        <th width="20%">分类</th>
                                        <th width="20%">排序</th>
                                    </tr>
                                    </thead>
                                    <tbody class="content-discount">
                                        <c:if test="${activityType==0 }">
                                            <c:forEach items="${m.activityGoodses}" var="ag" varStatus="f">
                                                <tr style="text-align: center">
                                                    <td><a href="javasrcipt:void(0)" class="delTr">删除</a></td>
                                                    <td><p>${ag.goods.sn}</p></td>
                                                    <td><p>${ag.goods.title}</p></td>
                                                    <td><p>${ag.goods.product.category.name}</p></td>
                                                    <td><input type="hidden" name="activityGoodses[${f.index}].goods.id" value="${ag.goods.id}">
                                                        <span class="spinner"><input name="activityGoodses[${f.index}].sort" class="input required" value="${ag.sort}">
                                                            <span class="spin-buttons"><span class="btn-up"></span><span class="btn-down"></span></span></span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                                <table class="table-module01 table-module02" id="table-fix" ${activityType==1?'':'style="display: none"'}>
                                    <thead>
                                    <tr style="text-align: center;">
                                        <th width="8%">操作</th>
                                        <th width="12%">编号</th>
                                        <th width="40%">商品标题</th>
                                        <th width="20%">分类</th>
                                        <th width="10%">排序</th>
                                        <th width="10%">活动价</th>
                                    </tr>
                                    </thead>
                                    <tbody class="content-fix">
                                        <c:if test="${activityType==1 }">
                                        <c:forEach items="${m.activityGoodses}" var="mag" varStatus="i">
                                            <tr style="text-align: center">
                                                <td><a href="javasrcipt:void(0)" class="delTr">删除</a></td>
                                                <td><p>${mag.goods.sn}</p></td>
                                                <td><p>${mag.goods.title}</p></td>
                                                <td><p>${mag.goods.product.category.name}</p></td>
                                                <td><input type="hidden" name="activityGoodses[${i.index}].goods.id" value="${mag.goods.id}">
                                                    <span class="spinner">
                                                        <input name="activityGoodses[${i.index}].sort" class="input required" value="${mag.sort}">
                                                        <span class="spin-buttons"><span class="btn-up"></span><span class="btn-down"></span></span></span></td>
                                                <td><input type="text" name="activityGoodses[${i.index}].activityPrice" class="decimalValidate required" value="${mag.activityPrice}"></td></tr>
                                        </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>

                    <tr id="discount" ${activityType!=0?'style="display:none"':''}>
                        <th width="130" align="right" valign="top">活动折扣：<b class="color-red">*</b></th>
                        <td valign="top">
                            <input type="text" name="discount" id="discount-input" value="${m.discount}" />
                            <span class="tip">※注：如折扣输入8.5折扣为8.5折。</span>
                        </td>
                    </tr>

                    <tr>
                        <th width="130" align="right" valign="top">活动时间：<b class="color-red">*</b></th>
                        <td>
                            <input id="startTime" name="startDate" class="Wdate" type="text"
                                   value="<fmt:formatDate value="${m.startDate}" type="date" pattern="yyyy-MM-dd"/>"
                                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd', autoPickDate:true, maxDate:'#F{$dp.$D(\'endTime\')||\'2099-12-31\'}'})"/>
                            <span class="errorTip"></span>
                            <span>——</span>
                            <input id="endTime" name="endDate" class="Wdate" type="text"
                                   value="<fmt:formatDate value="${m.endDate}" type="date" pattern="yyyy-MM-dd"/>"
                                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2099-12-31'})"/>
                            <span class="errorTip"></span>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right" valign="top">排序：<b class="color-red">*</b></th>
                        <td valign="top">
                            <span id="sort-spinner" class="spinner">
                                <input id="sort" name="sort" class="input" value="${m.sort}">
                                <span class="spin-buttons">
                                    <span class="btn-up"></span>
                                    <span class="btn-down"></span>
                                </span>
                            </span>
                            <script>
                                $('#sort-spinner').spinner({
                                    max: 999999
                                    , min: 0
                                    , step: 1
                                    , allowEmpty: true
                                    , minusBtn: '.btn-down'
                                    , plusBtn: '.btn-up'
                                });
                            </script>
                            <%-- <input type="text" name="sort" autocomplete="off">--%></td>
                    </tr>
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
                    </tbody>
                </table>
            </form>
            <%-- TAB --%>
            <div id="goods-list" style="display: none;width:590px">
                <table id="datagrid" ></table>
                <div id="pagination" ></div>
            </div>
        </div>
        <!-- //End 主内容区 -->
    </div>

    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/mustache.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript">
        $(function(){
            $('.spinner').spinner({
                max: 999999
                , min: 0
                , step: 1
                , allowEmpty: true
                , minusBtn: '.btn-down'
                , plusBtn: '.btn-up'
            });
            $(".delTr").each(function(i,e){
                $(this).click(delTr);
            });
            jQuery.validator.addMethod("decimalValidate", function(value, element) {
                var tel = /^(([1-9]+\d*)|([1-9]+\d*\.\d{1,2}))$/;
                return this.optional(element) || (tel.test(value));
            }, "请填写大于0的小数并可以保留两位小数");
            $('.chosen-select').chosen({});
            var goodsId = new Array();
            <c:forEach items="${m.activityGoodses}" var="c" varStatus="j">
                goodsId[${j.index}]="${c.goods.id}";
            </c:forEach>


            var activityTypeVal=0
            var flag= false;


            $("input[name='activityType']").change(function(){
                activityTypeVal = $(this).val();
                flag=true;
                if(activityTypeVal=="0"){
                    $("#discount").attr("disabled",false).show();
                    $("#table-discount").hide()
                    $("#table-fix").hide()
                    $("#table-discount tbody").html("");
                    $("#table-fix tbody").html("")
                    goodsId.length=0
                }else if(activityTypeVal=="1"){
                    $("#discount").attr("disabled",true).hide();
                    $("#discount-input").val("");
                    goodsId.length=0
                    $("#table-discount").hide()
                    $("#table-fix").hide()
                    $("#table-discount tbody").html("");
                    $("#table-fix tbody").html("")
                }
            });
            $("select[name='bank.id']").change(function(){
                $("#table-discount tbody").html("");
                $("#table-fix tbody").html("");
                $("#table-discount").hide();
                $("#table-fix").hide();
                goodsId.length=0;
            });

            $('#sel-goods').click(function () {
                var bankId = $("select[name='bank.id']").val();
                if(!bankId){
                    Glanway.Messager.alert("提示", "请选择银行");
                    return false;
                }
                //todo 选择银行
                layer.open({
                    zIndex: 10,
                    title: '选择商品',
                    type: 1,
                    skin: 'layer-ext-alertpop', //加上边框
                    area: ['594px', '400px'], //宽高
                    shadeClose: true,
                    content: $('#goods-list'),
                    btn: ['确定', '取消'],
                    cancel: function (index) {
                        $g.GridUnload()
                        layer.close(index);
                    },
                    yes: function (index) {
                        var keys = $g.jqGrid('getGridParam', 'selarrrow'), i;
                        if(keys.length==0){
                            Glanway.Messager.alert("提示", "请选择至少一个商品");
                            return false;
                        }
                        $.each(keys,function(i,row){
                            var $row =$g.jqGrid('getRowData',row);
                            var id = $row.id;
                            var sn = $row.sn;
                            var title = $row.title;
                            var categoryName = $row['category.name'];
                            if(activityTypeVal=="0"){
                                if(goodsId.indexOf(id)>-1){
                                    /*Glanway.Messager.alert("提示", "这件【"+title+"】商品重复");
                                    return false;*/
                                    return
                                }
                                goodsId.push(id);
                                $(".content-discount").append("<tr style='text-align: center'><td><a href=\"javasrcipt:void(0)\" class=\"delTr\">删除</a></td><td>"+sn+"</td><td><p>"+title+"</p></td><td>"+categoryName+
                                        "</td><td><input type=\'hidden\' name='activityGoodses["+(goodsId.length-1)+"].goods.id' value='"+id+"'/>" +
                                        "<span  class=\"spinner\"><input  name='activityGoodses["+(goodsId.length-1)+"].sort' class=\"input required\" value=\"1\"><span class=\"spin-buttons\"><span class=\"btn-up\"></span><span class=\"btn-down\"></span></span></span>" +
                                        "</td></tr>");
                                $("#table-discount").show();

                            }else{
                                if(goodsId.indexOf(id)>-1){
                                    /*Glanway.Messager.alert("提示", "这件【"+title+"】商品重复");
                                    return false;*/
                                    return;
                                }
                                goodsId.push(id);
                                $(".content-fix").append("<tr style='text-align: center'><td><a href=\"javasrcipt:void(0)\" class=\"delTr\">删除</a></td><td>"+sn+"</td><td><p>"+title+"</p></td><td>"+categoryName+
                                        "</td><td><input type=\'hidden\' name='activityGoodses["+(goodsId.length-1)+"].goods.id' value='"+id+"'/>" +
                                        "<span  class=\"spinner\"><input  name='activityGoodses["+(goodsId.length-1)+"].sort' class=\"input required\" value=\"1\"><span class=\"spin-buttons\"><span class=\"btn-up\"></span><span class=\"btn-down\"></span></span></span>" +
                                        "<td><input type='text' name='activityGoodses["+(goodsId.length-1)+"].activityPrice' class='decimalValidate required' value=''/></td>"+
                                        "</td></tr>");
                                $("#table-fix").show();
                            }
                            $('.spinner').spinner({
                                max: 999999
                                , min: 0
                                , step: 1
                                , allowEmpty: true
                                , minusBtn: '.btn-down'
                                , plusBtn: '.btn-up'
                            });

                        });
                        $(".delTr").click(delTr);
                        var row = $g.jqGrid('getRowData', keys[0]);;
                        layer.close(index);
                    }
                });
                var url;
                if(flag){
                    url='../get-goodses?search_bankId_eq_L='+bankId+'&id='+$("input[name='id']").val();
                }else{
                    url='../get-goodses?search_bankId_eq_L='+bankId
                }
                var $g = $('#datagrid').jqGrid({
                    url: url,
                    datatype: 'json',
                    colNames: [ 'id', '商品编号', '商品名称', '所属分类','售价'],
                    colModel: [

                        {name: 'id', index: 'id', hidden: true, key: true, align: 'center'},
                        {name: 'sn', index: 'sn', template: 'text', align: 'center', searchoptions: {sopt: ["cn"]}},
                        {name: 'title', index: 'title', template: 'text', align: 'center'},
                        {name: 'category.name', index: 'pCName', template: 'text', align: 'center', sortable: false},
                        {name: 'hopeCostPrice', index: 'hopeCostPrice', template: 'text', align: 'center', sortable: false}
                    ],
                    multiselect: true,
                    autowidth: true,
                    shrinkToFit: true,
                    height: 'auto',
                    pager: '#pagination',
                    sortname: 'id',
                    sortorder: 'asc'
                });
            });

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
                        url: '../check',
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
                'bank.id':{
                    required:true
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
                var at =$("input[name='activityType']:checked").val();
                var discount = $(".content-discount").find("tr").length;
                var fix = $(".content-fix").find("tr").length
                if (at=="0"){
                    if(discount<=0) {
                        Glanway.Messager.alert("提示", "请选择商品");
                        return false
                    }
                }else{
                    if(fix<=0) {
                        Glanway.Messager.alert("提示", "请选择商品");
                        return false
                    }
                }
                if(  imgValidate.validateImg()){
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing", "0");
                    form.submit();
                }

            }
        });
    </script>
<m:f/>