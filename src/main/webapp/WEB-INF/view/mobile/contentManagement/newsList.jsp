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
    <title>帮助中心</title>
    <meta name="description" content="">
    <meta content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=no, width=device-width" name="viewport">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <%@ include file="/WEB-INF/view/include/mobile/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/mobile/aboutus.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main">
        <div class="newsLi samePadding">
           <%-- <a href="#">全部</a>
            <a href="#" class="on">直播农村</a>
            <a href="#">菜谱专区</a>
            <a href="#">新闻</a>
            <a href="#">银行专区</a>--%>
            <a href="${ctx }/mobile/contentManagement/newsList" <c:if test="${_newsType == null }">class="on"</c:if>>全部</a>
               <input type="hidden" id="newList_hidden_newsType" value="${_newsType }">
               <c:forEach items="${newsTypes }" var="newsType">
                   <a href="${ctx }/mobile/contentManagement/newsList?type=${newsType.id }" <c:if test="${_newsType == newsType.id }">class="on"</c:if>>${newsType.name }</a>
               </c:forEach>

        </div>
        <div class="newsList samePadding">
            <div class="module-list-01 noticelist pinterest">

            <c:forEach items="${newss.data }" var="news">
                <dl>
                    <dt>
                        <a href="${ctx }/mobile/contentManagement/newsDetail?id=${news.id}&type=${news.newsTypeId}" title="${news.title}" rel="nofollow">
                            <img src="${ctx }/${news.image}" alt="${news.title}" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="${ctx }/mobile/contentManagement/newsDetail?id=${news.id}&type=${news.newsTypeId}" title="${news.title}">
                                    ${news.title}
                            </a>
                        </h2>
                        <span><fmt:formatDate value="${news.createdDate }" type="date"/></span>
                    </dd>
                </dl>
            </c:forEach>
                <%--<dl>
                    <dt>
                        <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                            <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                                美好生活靠理财规划 分红险占据保险理财
                                主流
                            </a>
                        </h2>
                        <span>2016-02-13</span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                            <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                                美好生活靠理财规划 分红险占据保险理财
                                主流
                            </a>
                        </h2>
                        <span>2016-02-13</span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                            <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                                美好生活靠理财规划 分红险占据保险理财主流
                            </a>
                        </h2>
                        <span>2016-02-13</span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                            <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                                美好生活靠理财规划 分红险占据保险理财主流
                            </a>
                        </h2>
                        <span>2016-02-13</span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                            <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
                        </a>
                    </dt>
                    <dd>
                        <h2 class="overflow">
                            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                                美好生活靠理财规划 分红险占据保险理财主流
                            </a>
                        </h2>
                        <span>2016-02-13</span>
                    </dd>
                </dl>--%>
            </div>
            <!--加载更多-->
            <div class="load">正在加载...</div>
        </div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>
<script id="moreList" type="text/html">
    <dl>
        <dt>
            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
            </a>
        </dt>
        <dd>
            <h2 class="overflow">
                <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                    美好生活靠理财规划 分红险占据保险理财主流
                </a>
            </h2>
            <span>2016-02-13</span>
        </dd>
    </dl>
    <dl>
        <dt>
            <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流" rel="nofollow">
                <img src="../../images/newsList.png" alt="美好生活靠理财规划 分红险占据保险理财主流" />
            </a>
        </dt>
        <dd>
            <h2 class="overflow">
                <a href="newsDetail.html" title="美好生活靠理财规划 分红险占据保险理财主流">
                    美好生活靠理财规划 分红险占据保险理财主流</a>
            </h2>
            <span>2016-02-13</span>
        </dd>
    </dl>
</script>


<script type="text/javascript">
    var page=2;
    var pageSize=10;
    var totalPage=${newss.totalPages};
    var bt=baidu.template;
    var ok = true;
    //瀑布流代码
    $(window).scroll(function () {
        // var ok = true;
        if (ok) {
            /*  //获取加载的数据
             var moreLi = baidu.template("moreList");
             //获取到屏幕顶部距离
             var loadheight = $(".load").offset().top - $(window).scrollTop() + $(".load").height();
             //获取屏幕高度
             var screenheight = $(window).height();
             if (loadheight < screenheight) {
             for (var i = 1; i < 4; i++) {
             $(".pinterest").append(moreLi);
             }
             } */


            //设置左分隔符为 <!
            baidu.template.LEFT_DELIMITER='<!';
            //设置右分隔符为 <!
            baidu.template.RIGHT_DELIMITER='!>';
            $.ajax({
                url:"${ctx}/mobile/contentManagement/news/list",
                type: "POST",
                async: false,
                data: {page:page,pageSize:pageSize},
                success: function(page){
                    var data = page.rows;
                    if(data){
                        $.each(data,function(i,e){//debugger;
                            var a={
                                id: e.id,
                                title: e.title,
                                //   content: e.mobileContent,
                                image: e.image,
                                //createdDate:e.createdDate,
                                createdDate:new Date(e.createdDate).Format("yyyy.MM.dd"),
                                newsType:$("#newList_hidden_newsType").val(),
                                //    startDate:new Date(e.startDate).Format("yyyy.MM.dd"),
                                // endDate: new Date(e.endDate).Format("yyyy.MM.dd"),
                            }
                            var html=bt('newsModel', a);
                            $(".module-list-01").append(html);
                        });

                    }
                    if(page.lastPage){
                        //  $(".record_bottom").hide();
                        ok=false;
                    }else{
                        page+=1;
                    }
                },
                error:function(page){
                    // alert("获取失败.请稍候再试");
                }
            })
        }
    });
</script>

<script id="newsModel" type="text/html">

    <dl>
        <dt>
            <a href="${ctx}/mobile/contentManagement/newsDetail?id=<!=id!>&type=<!=newsType!>" title="<!=title!>" rel="nofollow"><img src="${ctx}/<!=image!>_198x152" alt="<!=title!> "  width="198px" height="152px"/></a>
        </dt>
        <dd>
            <h2><a href="${ctx}/mobile/contentManagement/newsDetail?id=<!=id!>&type=<!=newsType!>" title="<!=title!>"><!=title!></a></h2>
            <span><!=createdDate!></span>
        </dd>
    </dl>
</script>
<script>
    Date.prototype.Format = function(fmt)
    {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
    $(function () {

    });

</script>
</html>
