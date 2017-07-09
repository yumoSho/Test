<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/admin/common_site.css">
    <%@include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="../include/admin/adminHead.jspf" %>

<div class="main">
    <div class="content_warps">
        <div class="descriptions sitemap-cen">
            <div class="sitemap-one clearfix">
                <h2>快捷菜单</h2>
            </div>
            <div class="sitemap-two">
                <c:forEach items="${modules}" var="module">
                    <h2><b><a href="#">${module.name}</a></b></h2>
                    <p>
                        <c:forEach items="${module.pages}" var="pagez">
                            <%--<c:if test="${fn:contains(pagez.url,'index')}">--%>
                                <a href=${ctx}${pagez.url}>${pagez.name}</a>
                            <%--</c:if>--%>
                        </c:forEach>
                    </p>
                </c:forEach>

            </div>
        </div>
    </div>
</div><!-- //main -->

<script type="text/javascript" src="${ctx}/js/jq.Slide.js"></script>
<script type="text/javascript" src="${ctx}/js/admin/common.js"></script>
<m:f/>