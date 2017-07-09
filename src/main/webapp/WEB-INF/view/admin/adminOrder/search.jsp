<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@ include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/support.jspf" %>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>


<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>您当前的位置:</span> <a href="${ctx}/admin" title="首页">首页</a> &gt; <a
                href="${ctx}/admin/adminOrder/index" title="订单管理">订单管理</a> &gt;
            正常订单详情
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" id="printcontent">
            <input type="hidden" value="${order.id}" id="orderId">
            <div class="orderList">
                <table class="table-module01" width="100%">
                    <thead>
                    <tr>
                        <td colspan="6"><h2>当前订单【状态:
                            <c:choose>
                            <c:when test="${order.status eq 1}">待支付</c:when>
                            <c:when test="${order.status eq 2}">已支付</c:when>
                            <c:when test="${order.status eq 3}">待发货</c:when>
                            <c:when test="${order.status eq 4}">已发货</c:when>
                            <c:when test="${order.status eq 5}">已确认收货</c:when>
                            <c:when test="${order.status eq 6}">交易完成</c:when>
                            <c:when test="${order.status eq 7}">交易取消</c:when>
                            <c:when test="${order.status eq 8}">退换货处理中</c:when>
                            <c:when test="${order.status eq 9}">问题/缺货</c:when>
                        </c:choose>】</h2></td>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="16.6%">订单编号</td>
                        <td width="16.6%" style="color:red">${order.code}</td>
                        <td width="16.6%">订单状态</td>
                        <td width="16.6%">
                            <p class="order_State" id="orderStatus" status="${order.status}">
                                <c:choose>
                                    <c:when test="${order.status eq 1}">待支付</c:when>
                                    <c:when test="${order.status eq 2}">已支付</c:when>
                                    <c:when test="${order.status eq 3}">待发货</c:when>
                                    <c:when test="${order.status eq 4}">已发货</c:when>
                                    <c:when test="${order.status eq 5}">已确认收货</c:when>
                                    <c:when test="${order.status eq 6}">交易完成</c:when>
                                    <c:when test="${order.status eq 7}">交易取消</c:when>
                                    <c:when test="${order.status eq 8}">退换货处理中</c:when>
                                    <c:when test="${order.status eq 9}">问题/缺货</c:when>
                                </c:choose>
                            </p>
                        </td>
                    </tr>
                    <tr>
                        <td width="16.6%">下单日期</td>
                        <td width="16.6"><fmt:formatDate
                                value="${order.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td width="16.6%"></td>
                        <td width="16.6%"></td>
                    </tr>
                    <tr>
                        <td width="16.6%">操作</td>
                        <td width="16.6%">
                            <c:if test="${3 == order.status}">
                                <input id="btn-send" class="btn-sure" type="button" value="发货"/>
                            </c:if>
                            <c:if test="${7 != order.status}">
                                <input id="btn-cancle" class="btn-sure" type="button" value="取消订单"/>
                            </c:if>
                            </td>
                        <td width="16.6%"></td>
                        <td width="16.6%"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="orderList">
                <table class="table-module01" width="100%">
                    <thead>
                    <tr>

                        <td colspan="6"><h2>订单信息&nbsp&nbsp<input id="btn-addDeliver" class="btn-sure" type="button"
                                                                 value="添加物流信息"/></h2></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="16.6%">订单总价</td>
                        <td width="16.6%" style="color:red">
                            <fmt:formatNumber value="${order.price}" type="currency" pattern="￥0.00"/>
                        </td>
                        <td width="16.6%">支付金额</td>
                        <td width="16.6%" style="color:red">
                            <fmt:formatNumber value="${order.payPrice}" type="currency" pattern="￥0.00"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="16.6%">运费</td>
                        <td width="16.6%" style="color:red">
                            <fmt:formatNumber value="${order.deliverPrice}" type="currency" pattern="￥0.00"/>
                        </td>
                        <td width="16.6%">店铺</td>
                        <td width="16.6%">${order.storeName}</td>
                    </tr>
                    <tr>
                        <td width="16.6%">支付方式</td>
                        <td width="16.6%">${order.payment}</td>
                        <td width="16.6%">支付时间</td>
                        <td width="16.6%">
                            <fmt:formatDate value="${order.payDate}"
                                                          pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="16.6%">快递类型</td>
                        <td width="16.6%">顺丰快递</td>
                        <td width="16.6%">快递单号</td>
                        <td width="16.6%">
                            <input type="hidden" value="${order.deliverCode}" id="dCode">
                            ${order.deliverCode}
                        </td>
                    </tr>
                    <tr>
                        <td width="16.6%">收货人</td>
                        <td width="16.6%">${order.receiver}</td>
                        <td width="16.6%">联系电话</td>
                        <td width="16.6%">${order.contact}</td>
                    </tr>
                    <tr>
                        <td width="16.6%">收货地址</td>
                        <td width="16.6%" >${order.address}</td>
                        <td width="16.6%">配送时间</td>
                        <td width="16.6%">${order.psDate}</td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td colspan="3">
                            ${order.remark}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="orderList">
                <table class="table-module01" width="100%">
                    <thead>
                    <tr>

                        <td colspan="6"><h2>会员信息</h2></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="16.6%">会员编号</td>
                        <td width="16.6%">${order.member.memberCode}</td>
                        <td width="16.6%">会员名称</td>
                        <td width="16.6%">${order.member.memberName}</td>
                    </tr>
                    <tr>
                        <td width="16.6%">会员邮箱</td>
                        <td width="16.6%">${order.member.email}</td>
                        <td width="16.6%">会员手机</td>
                        <td width="16.6%">${order.member.phone}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="orderList orderDetail">
                <table class="table-module01" width="100%">
                    <thead>
                    <tr>
                        <td colspan="6"><h2>商品详情</h2></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="20%">商品编号</td>
                        <td width="16%">商品名称</td>
                        <td width="16%">商品规格</td>
                        <td width="16%">购买数量</td>
                        <td width="16%">单价</td>
                        <td width="16%">&nbsp&nbsp&nbsp小计（元）</td>
                    </tr>
                    <c:forEach items="${order.orderDetails}" var="t">
                        <tr>
                            <td width="20%">${t.goodsCode}</td>
                            <td width="16%">${t.goodsName}</td>
                            <td width="16%">${t.goodsSpec}</td>
                            <td width="16%">${t.goodsNum}</td>
                            <td width="16%">
                                <fmt:formatNumber value="${t.sellPrice}" type="currency" pattern="￥0.00"/>
                            </td>
                            <td width="16%">
                                &nbsp&nbsp&nbsp
                                <fmt:formatNumber value="${t.goodsNum * t.sellPrice}" type="currency" pattern="￥0.00"/>
                            </td>
                        </tr>
                        <c:set var="totalPrice" value="${totalPrice+t.goodsNum * t.sellPrice}"/>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td align="left" style="color:red">
                            <fmt:formatNumber value="${totalPrice}" type="currency" pattern="总价：￥0.00"/>
                        </td>
                    </tr>
                    </tbody>
                    </table>
                </div>
            <div class="orderList">
                <div style="text-align: center; padding-top: 40px;">
							<span class="btn-sure-wrap">
							 <input class="btn-sure btn-edit" id="print" type="button" value="打印"/>
							</span>
							 <span class="btn-cancel-wrap" style="margin-right: 20px;">
								<a href="${ctx}/admin/adminOrder/index" class="btn-cancel" id="close">关闭</a>
							</span>
                </div>
            </div>
                    <!-- TAB END -->
                    <!-- //End 主内容区 -->
                    <div id="addDeliverCode" hidden>
                        <div style="height:80%;margin-top: 20px;" align="center">
                           <div> 物流公司： <select  id="deliverCompanyCode" value="" style="width:170px;">
                               <c:forEach items="${deliverCompanyList}" var="com">
                                   <option value="${com.dicCode}">${com.dicName}</option>
                               </c:forEach>
                           </select></div>
                            <div style="margin-top: 10px;">快递单号： <input type="text" id="deliverCode" value="" style="height:26px;"></div>
                        </div>
                    </div>
                    <script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
                    <script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
                    <script type="text/javascript" src="${ctx}/js/lib/mustache.js"></script>
                    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
                    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
                    <script type="text/javascript" src="${ctx}/js/lib/jqprint/jquery.jqprint-0.3.js"></script>
                    <script type="text/javascript">
                        $(function () {
                            //打印按钮点击方法：
                            $("#print").on('click', function () {
                                //打印前设置不需要打印的按钮隐藏
                                $("#print").hide();
                                $("#btn-send").hide();
                                $("#btn-cancle").hide();
                                $("#btn-addDeliver").hide();
                                $("#close").hide();
                                //打印
                                $("#printcontent").jqprint();
                                //打印完成后显示所有按钮
                                $("#print").show();
                                $("#btn-send").show();
                                $("#btn-cancle").show();
                                $("#btn-addDeliver").show();
                                $("#close").show();
                            });
                            //已发货按钮点击方法：
                            $("#btn-send").on('click', function () {
                                Besture.Messager.confirm('温馨提示', ['您确定此订单已发货吗？', '如果是，请单击确定，否则单击取消'], function (ret) {
                                    if (ret) {
                                        var id = $("#orderId").val();
                                        var oldStatus = $("#orderStatus").attr('status');
                                        var deliverCode =$("#dCode").val();
                                        if (3 != oldStatus) {
                                            layer.msg("只能发货状态为待发货的订单!", {icon: 2});
                                            return;
                                        }
                                        if(!deliverCode){
                                            layer.msg("请先添加物流单号!", {icon: 2});
                                            return;
                                        }
                                        debugger;
                                        $.ajax({
                                            url: '${ctx}/admin/adminOrder/deliverOrder',
                                            type: 'post',
                                            data: {
                                                id: id
                                            },
                                            success: function (r) {
                                                // $.messager.progress('close');
                                                if (r.success) {
                                                    layer.msg('操作成功', {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 2000
                                                        , title: '更新成功'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    }, function () {
                                                        location.reload();
                                                    });

                                                } else {
                                                    layer.msg('操作失败', {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 2000
                                                        , title: '更新失败'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    }, function () {
                                                        location.reload();
                                                    });
                                                }
                                            },
                                            error: function () {
                                                $.messager.progress('close');
                                            }
                                        });
                                    }
                                });
                            });
                            //取消订单按钮点击方法：
                            $("#btn-cancle").on('click', function () {
                                Besture.Messager.confirm('温馨提示', ['您确定要取消此订单吗？', '如果是，请单击确定，否则单击取消'], function (ret) {
                                    if (ret) {
                                        var id = $("#orderId").val();
                                        var status = 7;//7为订单取消
                                        $.ajax({
                                            url: '${ctx}/admin/adminOrder/updateOrderStatusCancleById',
                                            type: 'post',
                                            data: {
                                                id: id,
                                                status: status
                                            },
                                            success: function (r) {
                                                //$.messager.progress('close');
                                                if (r.success) {
                                                    layer.msg('操作成功', {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 2000
                                                        , title: '更新成功'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    }, function () {
                                                        location.reload();
                                                    });
                                                } else {
                                                    layer.msg('操作失败', {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 2000
                                                        , title: '更新失败'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    }, function () {
                                                        location.reload();
                                                    });
                                                }
                                            },
                                            error: function () {
                                                $.messager.progress('close');
                                            }
                                        });
                                    }
                                });
                            });
                            //添加物流信息按钮点击方法：
                            $("#btn-addDeliver").on('click', function () {
                                var divStr = $('#addDeliverCode').html();
                                layer.open({
                                    zIndex: 10,
                                    title: '添加物流信息',
                                    type: 1,
                                    skin: layerSkin,
                                    area: ['400px', '200px'], //宽高
                                    shadeClose: false,//点击遮罩是否关闭弹框
                                    content: divStr,
                                    btn: ['确定', '取消'],
                                    yes: function (index, layero) {
                                        var code = layero.find('#deliverCode').val();
                                        var orderId = $('#orderId').val();
                                        var deliverCompanyCode = $("#deliverCompanyCode").val();//物流公司编号
                                        $.ajax({
                                            url: window.contextPath + '/admin/adminOrder/addDeliverCode',
                                            type: 'post',
                                            data: {deliverCode: code, orderId: orderId,deliverCompanyCode:deliverCompanyCode},
                                            success: function (data) {
                                                layer.close(index);
                                                if (data.success) {
                                                    layer.msg("操作成功", {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 3000
                                                        , title: '提示 [3秒后关闭]'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    }, function () {
                                                        location.reload();
                                                    });
                                                } else {
                                                    layer.msg(data.message, {
                                                        skin: 'layer-ext-message' //样式类名
                                                        , closeBtn: 1
                                                        , time: 3000
                                                        , title: '提示 [3秒后关闭]'
                                                        , shade: 0
                                                        , offset: 'rb'
                                                        , btn: ''
                                                    });
                                                }

                                            },
                                            error: function (data) {
                                                layer.msg('系统错误', {
                                                    skin: 'layer-ext-message' //样式类名
                                                    , closeBtn: 1
                                                    , time: 3000
                                                    , title: '提示 [3秒后关闭]'
                                                    , shade: 0
                                                    , offset: 'rb'
                                                    , btn: ''
                                                });

                                            }
                                        });

                                    },
                                    cancel: function (index) {
                                        layer.close(index);
                                    }
                                });
                            });
                        });
                        //根据订单状态获得状态描述
                        function getStatusDesc(val) {
                            var status = "";
                            switch (val) {
                                case 1:
                                    status = "待支付";
                                    break;
                                case 2:
                                    status = "已支付";
                                    break;
                                case 3:
                                    status = "待发货";
                                    break;
                                case 4:
                                    status = "待收货";
                                    break;
                                case 5:
                                    status = "已确认收货";
                                    break;
                                case 6:
                                    status = "交易完成";
                                    break;
                                case 7:
                                    status = "交易取消";
                                    break;
                                case 8:
                                    status = "退换货处理中";
                                    break;
                                case 9:
                                    status = "问题/缺货";
                                    break;
                                default:
                                    status = "";
                            }
                            return status;
                        };
                    </script>
                    <m:message message="${message}" error="${error}"/>
                    <m:f/>
					
