<%-- ~ 通用 head, 通过title, nocache 属性设置title和是否缓存 --%>
<%@ tag pageEncoding="UTF-8" body-content="scriptless" isELIgnored="false" %>
<%@ attribute name="nocache" type="java.lang.Boolean" required="false" %>
<%@ attribute name="title" type="java.lang.String" required="false" %>
<%@ attribute name="description" type="java.lang.String" required="false" %>
<%@ attribute name="bodyClass" type="java.lang.String" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("ctx", request.getContextPath());%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <c:if test="${nocache}">
        <meta http-equiv="Cache-Control" content="no-store"/>
        <meta http-equiv="Pragma" content="no-conf.cache"/>
        <meta http-equiv="Expires" content="0"/>
    </c:if>
    <%--<base href="<%=request.getContextPath()()%>"/>--%>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="${description}">
	<title>${simpleWebsite.name} - 后台管理系统</title>
    <!-- favicon.ico -->
    <link rel="icon" href="${ctx}/favicon.ico">
    <link rel="shortcut icon" href="${ctx}/favicon.ico">
	<link rel="stylesheet" href="${ctx}/css/admin/normalize.css">
	<link rel="stylesheet" href="${ctx}/css/admin/common.css">
	<script type="text/javascript">window.Glanway = window.Glanway || {}; window.Glanway.contextPath = "${ctx}";</script>
    <script type="text/javascript" src="${ctx}/js/modernizr-2.6.2.min.js"></script>
    <%-- 将标签中内容插入 --%>
    <jsp:doBody />
    <style type="text/css">
        label.error {
            margin-left: 10px;
            color: red;
        }
    </style>
    <%--<script>
        $(function(){
            var platformSet = "${sessionScope.platformSet.name}";
            if(platformSet == ""){
                $.ajax({
                    type:"POST",
                    url:"${ctx}/platformSet/index"
                })
            }
        });
    </script>--%>
</head>
<%--<body <c:if test="${index eq true}">class="index bg"</c:if>>--%>
<body class="${bodyClass}">
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->