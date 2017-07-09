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
    <title>资讯列表-${seo.title}</title>
    <meta name="keywords" content="${seo.keyWords}">
    <meta name="description" content="${seo.content}">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
 <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx }/css/pc/news.css" />
</head>
<body>
<c:set var="nav" value="3"/>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="wrap">
        <%@ include file="/WEB-INF/view/include/pc/head.jspf" %><!-- //head -->
        <div class="main samewidth">
            <!-- 面包屑 -->
            <div class="positionWrap">
                <div class="position">
                    <span>您所在当前位置：</span>
                    <a href="${ctx}/">首页</a><span class="forGt"> &gt;</span><span>新闻资讯</span>
                </div>
            </div>
            <!--新闻分类-->
            <!--<div class="newClassify">
                <a href="javascript:void(0);" class="on">全部</a>
                <a href="javascript:void(0);">直播农场</a>
                <a href="javascript:void(0);">菜谱专区</a>
                <a href="javascript:void(0);">新闻</a>
                <a href="javascript:void(0);">银行专区</a>
            </div>-->
            <div class="newClassify">
                <div class="tabBtn">
                    <a href="${ctx }/contentManagement/newsList" <c:if test="${_newsType == null }">class="on"</c:if>>全部</a>
                    <c:forEach items="${newsTypes }" var="newsType">
                    	<a href="${ctx }/contentManagement/newsList?type=${newsType.id }" <c:if test="${_newsType == newsType.id }">class="on"</c:if>>${newsType.name }</a>
                    </c:forEach>
                 <!--    <a href="javascript:void(0);">直播农场</a>
                    <a href="javascript:void(0);">菜谱专区</a>
                    <a href="javascript:void(0);">新闻</a>
                    <a href="javascript:void(0);">银行专区</a> -->
               </div>
            </div>
                <!--新闻列表-->
                <div class="newList">
                <c:forEach items="${newss.data }" var="news">
                    <dl>
                        <dt>
                            <a href="${ctx }/contentManagement/newsDetail?id=${news.id}">
                                <img src="${ctx }/${news.image}" width="179" height="144" alt="新闻图片" />
                            </a>
                        </dt>
                        <dd>
                            <h2>
                                <a href="${ctx }/contentManagement/newsDetail?id=${news.id}">${news.title}</a>
                            </h2>
                            <p>${news.shotContent}</p>
                            <div>
                                <a href="${ctx }/contentManagement/newsDetail?id=${news.id}" class="readBtn">
                                    阅读全文 &gt;&gt;
                                </a>
                            </div>
                        </dd>
                    </dl>
                  </c:forEach>
                    <!-- <dl>
                        <dt>
                            <a href="newDetail.html">
                                <img src="../../images/newsImg.jpg" width="179" height="144" alt="新闻图片" />
                            </a>
                        </dt>
                        <dd>
                            <h2>
                                <a href="newDetail.html">美好生活靠理财规划 分红险占据保险理财主流</a>
                            </h2>
                            <p>很多人谈起理财，就想到钱生钱，而且希望能够获得暴利生大钱。跟风式地买股票、盲目地买基金，最后损失惨重的投资者不在少数。去年7月份以来，A股进入一波大牛市，但多数人是在股市大涨后期才进入，不幸遭遇了大震荡。国内最大的理财论坛——随手记理财调查显示，经历震荡后，仅有12.9%的股民守住了盈利..</p>
                            <div>
                                <a href="newDetail.html" class="readBtn">
                                    阅读全文 &gt;&gt;
                                </a>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>
                            <a href="newDetail.html">
                                <img src="../../images/newsImg.jpg" width="179" height="144" alt="新闻图片" />
                            </a>
                        </dt>
                        <dd>
                            <h2>
                                <a href="newDetail.html">美好生活靠理财规划 分红险占据保险理财主流</a>
                            </h2>
                            <p>很多人谈起理财，就想到钱生钱，而且希望能够获得暴利生大钱。跟风式地买股票、盲目地买基金，最后损失惨重的投资者不在少数。去年7月份以来，A股进入一波大牛市，但多数人是在股市大涨后期才进入，不幸遭遇了大震荡。国内最大的理财论坛——随手记理财调查显示，经历震荡后，仅有12.9%的股民守住了盈利..</p>
                            <div>
                                <a href="newDetail.html" class="readBtn">
                                    阅读全文 &gt;&gt;
                                </a>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>
                            <a href="newDetail.html">
                                <img src="../../images/newsImg.jpg" width="179" height="144" alt="新闻图片" />
                            </a>
                        </dt>
                        <dd>
                            <h2>
                                <a href="newDetail.html">美好生活靠理财规划 分红险占据保险理财主流</a>
                            </h2>
                            <p>很多人谈起理财，就想到钱生钱，而且希望能够获得暴利生大钱。跟风式地买股票、盲目地买基金，最后损失惨重的投资者不在少数。去年7月份以来，A股进入一波大牛市，但多数人是在股市大涨后期才进入，不幸遭遇了大震荡。国内最大的理财论坛——随手记理财调查显示，经历震荡后，仅有12.9%的股民守住了盈利..</p>
                            <div>
                                <a href="newDetail.html" class="readBtn">
                                    阅读全文 &gt;&gt;
                                </a>
                            </div>
                        </dd>
                    </dl> -->
                </div>

                <div class="newList hide">
                    <dl>
                        <dt>
                            <a href="newDetail.html">
                                <img src="../../images/newsImg.jpg" width="179" height="144" alt="新闻图片" />
                            </a>
                        </dt>
                        <dd>
                            <h2>
                                <a href="newDetail.html">2美好生活靠理财规划 分红险占据保险理财主流</a>
                            </h2>
                            <p>很多人谈起理财，就想到钱生钱，而且希望能够获得暴利生大钱。跟风式地买股票、盲目地买基金，最后损失惨重的投资者不在少数。去年7月份以来，A股进入一波大牛市，但多数人是在股市大涨后期才进入，不幸遭遇了大震荡。国内最大的理财论坛——随手记理财调查显示，经历震荡后，仅有12.9%的股民守住了盈利..</p>
                            <div>
                                <a href="newDetail.html" class="readBtn">
                                    阅读全文 &gt;&gt;
                                </a>
                            </div>
                        </dd>
                    </dl>

                </div>

                <!-- 分页 -->
                <form id="pagination-form" class="pagination-form">
	                	<m:pagination totalPages="${newss.totalPages}" pageParam="page" skip="false"/>
	            </form>

            </div><!-- //main -->
        <%@ include file="/WEB-INF/view/include/pc/foot.jspf" %><!-- //foot -->
        <%@ include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf" %>
    </div><!-- //wrap -->
    <script>
        $(function () {
            $(".tabBtn").find("a").click(function () {
                var _this = $(this),
                    newLists = $(".newList");
                idx = _this.index();
                _this.addClass("on").siblings().removeClass("on");
                //$(newLists[idx]).removeClass("hide").siblings().addClass("hide");
            });
        });
    </script>
</body>

</html>
