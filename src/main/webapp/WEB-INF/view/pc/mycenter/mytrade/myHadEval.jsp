﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的收藏</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
     <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <%@include file="/WEB-INF/view/include/vlibs.jspf" %>
</head>
<body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@include file="/WEB-INF/view/include/pc/head.jspf"%>
        <!-- //head -->
       <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx }/">首页</a><span class="forGt"> &gt;</span><span>会员中心</span><span class="forGt"> &gt;</span><span>个人信息</span>
                </div>
            </div>
            <div class="centerMargin clearfix">
                <!-- 左侧导航 -->
                 <%@include file="/WEB-INF/view/include/pc/personalCenterleftNav.jspf"%>
             
                <div class="rightWrap fr">
                    <div class="sameTitle color666">我的评价</div>
                        <div class="myRating clearfix">
                            <a href="${ctx}/person-center/comment?commented=0">待评价</a>
                            <a href="${ctx}/person-center/comment?commented=1" class="on">已评价</a>
                        </div>
                        <c:if test="${fn:length(comments.data)>0}">
                        <div class="myRatingList">
                                <table  class="myRatingTabTwota">
                                    <tr>
                                        <th width="365">商品名称</th>
                                        <th width="346">评价内容</th>
                                        <th width="144">评分</th>
                                        <th>发表日期</th>
                                    </tr>
                                    <c:forEach items="${comments.data}" var="comment">
                                    <tr>
                                        <td>
                                            <a href="${ctx}/detail/${comment.goods.id}" class="myCentershaoppingImg fl">
                                                <img src="${ctx}/${comment.goods.image}_78x86.jpg" width="78" height="86" />
                                            </a>
                                            <a href="${ctx}/detail/${comment.goods.id}" class="myCentershaoppingTitle overflow fl">${comment.goods.title}</a>
                                            <span class="myCentershaoppingSpan fl">¥ <span><fmt:formatNumber value="${comment.goods.promotePrice}" type="currency" pattern="0.00"/></span></span>
                                        </td>
                                        <td >
                                            <span class="introduce overflow">${comment.content}</span>
                                        </td>
                                        <td class="icoStarY">
                                            <c:forEach begin="1" end="${comment.grade}">
                                                <img src="${ctx}/images/pc/icoStarY.png" width="16" height="16" />
                                            </c:forEach>
                                            <c:forEach begin="1" end="${5-comment.grade}">
                                                <img src="${ctx}/images/pc/icoStarG.png" width="16" height="16" />
                                            </c:forEach>
                                        </td>
                                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.commentTime}" type="both"/></td>
                                    </tr>
                                    </c:forEach>
                                </table>
                            
                        </div>
                        <!-- 分页-->
                        <%-- <form id="pagination-form" class="pagination-form">
                            <m:pagination totalPages="${comments.totalPages}" pageParam="page" skip="false"/>
                        </form> --%>
                        <form id="pagination-form" class="pagination-form">
	                		<m:pagination totalPages="${comments.totalPages}" pageParam="page" skip="false"/>
	                	</form>
                        </c:if>
                        <c:if test="${fn:length(comments.data) eq 0}">
		                     <div class="emptyList clearfix">
		                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
		                        <ul class="fl emptyUl">
		                            <li>您没有已评论的商品~</li>
		                            <li><a href="${ctx}/search">去购物>></a></li>
		                        </ul>
		                    </div>
                        </c:if>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   </div>
    <!-- //wrap -->
     <script src="${ctx}/js/lib/layer/layer.js"></script>
    <script type="text/javascript">

    $(function(){
        $(".introduce").each(function(){
            $(this).html(js.lang.String.decodeHtml($(this).html()));
        });
    });
</script>
   
</body>
</html>
