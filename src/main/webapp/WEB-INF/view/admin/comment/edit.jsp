<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<%@include file="/WEB-INF/view/include/elibs.jspf" %>
<%--<%@include file="${ctx}/include/elibs.jspf" %>--%>
<%--<%@include file="${ctx}/include/admin/support.jspf" %>--%>
<m:h>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/admin/uploader.css">
>
    <style type="text/css">
        .panel-header {
            background: #f5f5f5;
        }
        .layout-split-east  {
            border-left: #f5f5f5;
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
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/adminOrder/index" title="订单管理">订单管理</a> &gt;
            <a href="${ctx}/admin/comment/index" title="商品咨询管理">商品评价管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <%-- <div data-options="region:'east',split:true" title="包含页面" style="height: 800px; width: 350px;"></div>--%>
            <div <%--class="editPage"--%> data-options="region:'center',split:true" title="基础信息">
                <form id="fromSave" action="${ctx}/admin/comment/updateInEdit" method="post">
                    <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                        <tbody>
                        <tr>
                            <th width="130" align="right">商品名称：<b class="color-red"></b></th>
                            <td>
                                <input id="consultId" type="hidden" name="id"  value="${comment.id}">
                                <input id="goodsId" type="hidden" name="goods.id"  value="${comment.goods.id}">
                                <a href="${ctx}/admin/product/edit/${comment.goods.product.id}"  target="_blank">${comment.goods.title}</a>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">评价会员：<b class="color-red"></b></th>
                            <td>
                            	<input id="memberId" type="hidden" name="member.id"  value="${comment.member.id}">
                                <a href="${ctx}/admin/member/edit/${comment.member.id}"  target="_blank">${comment.member.memberName}</a>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">评价内容：<b class="color-red"></b></th>
                            <td>
                                <textarea rows="5" cols="30" id="content1" disabled="disabled"></textarea>
                                <input type="hidden" id="content" value="${comment.content}"/>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">评分：<b class="color-red"></b></th>
                            <td valign="top">
                               	<c:forEach begin="1" end="${comment.grade}">
                               		<img src="${ctx}/images/admin/heistar.png" style="width: 30px; height: 30px;">
                               	</c:forEach>
                               	<c:forEach begin="1" end="${5-comment.grade}">
                               		<img src="${ctx}/images/admin/kongstar.png" style="width: 30px; height: 30px;">
                               	</c:forEach>
							</td>
                        </tr>
                        <tr>
                            <th align="right">晒图：</th>
                            <td>
                            	<c:forEach items="${photos}" var="photo">
									<img src="${ctx}/${photo}" style="width: 110px; height: 110px;">
								</c:forEach>
							</td>
                        </tr>
                        <tr>
                            <th align="right">评价时间：</th>
                            <td>
                                <label><fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                            </td>
                        </tr>
                        <tr>
                            <th width="130" align="right">回复内容：<b class="color-red"></b></th>
                            <td>
                                <textarea rows="5" cols="30" id="reply1" name="reply1"></textarea>
                                <input type="hidden" id="reply" name="reply" value="${comment.reply}"/>
                                <span class="errorTip"></span>
                            </td>
                        </tr>
                        <tr>
                            <th align="right">回复时间：</th>
                            <td>
                                <label><fmt:formatDate value="${comment.replyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                            </td>
                        </tr>
                        <tr>
                            <th align="right">属性：</th>
                            <td>
                                <input type="checkbox" value="true" name="visible" ${true == comment.visible ? 'checked':''}>前台显示</input>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td align="left" >
                                    <span class="btn-sure-wrap">
                                        <input class="btn-sure btn-edit" type="submit" value="保存" />
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
        </div>
        <!-- //End 主内容区 -->
    </div>


    <m:f/>
<script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
        <script type="text/javascript" src="${ctx}/js/code-html.js"></script>
    <script type="text/javascript">

        $(function(){

            $(".btn-cancel-wrap input").click(function() {
                window.location.href = "${ctx}/admin/comment/index";
            });

            var answerhtml =$("#content").val();
            var d= js.lang.String.decodeHtml(answerhtml);
            $("#content1").text(d);

            var consulthtml =$("#reply").val();
            var consulttext= js.lang.String.decodeHtml(consulthtml);
            $("#reply1").text(consulttext);

            //验证
            $("#fromSave").validate({
                rules:{
                	reply1:{
                        required: true
                    }
                },
                messages:{
                	reply1:{
                        required: "回复不能为空"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.siblings("span:.errorTip"));
                },
                submitHandler: function(form){
                    var a= js.lang.String.encodeHtml($("#reply1").val())
                    $("#reply").val(a);
                    $(form).find(":submit").attr("disabled", true).attr("value",
                            "保存中...").css("letter-spacing","0");
                    form.submit();
                }
            });
        })
    </script>