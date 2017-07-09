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
    <title>我的评价</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
     <%@ include file="/WEB-INF/view/include/pc/common.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/pc/userCenter.css"  />
    <link rel="stylesheet" href="${ctx}/js/lib/uploader2/uploader2.css">
    <script type="text/javascript" src="${ctx}/js/lib/plupload-2.1.2/plupload.full.min.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lib/uploader2/uploader2.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/util.js"></script>
     <link rel="stylesheet" href="${ctx}/css/admin/uploader.css" type="text/css">
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
                            <a href="${ctx}/person-center/comment?commented=0" class="on">待评价</a>
                            <a href="${ctx}/person-center/comment?commented=1">已评价</a>
                        </div>
                        <c:if test="${fn:length(goodses.data)>0}">
                        <div class="myRatingList">
                            
                                <c:forEach items="${goodses.data}" var="goods" varStatus="j">
                                <div class="myRatingItem">
                                    <table class="myRatingTabOneTable">
                                        <tr>
                                            <td width="50%">
                                                <a href="${ctx}/detail/${goods.id}" class="myCentershaoppingImg fl">
                                                    <img src="${ctx}/${goods.image}_78x86.jpg" width="78" height="86" />
                                                </a>
                                                <a href="${ctx}/detail/${goods.id}" class="myCentershaoppingText overflow fl">${goods.title}</a>
                                                <span class="myCentershaoppingSpan fleft">¥ <span><fmt:formatNumber value="${goods.promotePrice}" type="currency" pattern="0.00"/></span></span>
                                            </td>
                                            <td width="50%">
                                                <a href="javascript:void(0);" class="evaluation fr">评价</a>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class="twoTable">
                                        <table class="myRatingTabTwoTable">
                                            <tr>
                                                <td>评价</td>
                                                <td><span class="starScore" data-score="1"></span></td>
                                            </tr>
                                            <tr>
                                                <td>内容</td>
                                                <td>
                                                    <textarea class="textarea"></textarea>
                                                    <input type="hidden" name="gid" value="${goods.id}"/>
                                                    <input type="hidden" name="od" value="${goods.tempOrderDetailId}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>上传图片</td>
                                                <td>
                                                    <%--<a href="#" class="sheetImg">
                                                        <img src="../../../images/sheetImg.png" width="80" height="80" />
                                                        <input type="file" />
                                                    </a>--%>
                                                  <%--   <ul class="upload-list" id="pro-img" flag="pro-img">
                                                    <li class="js-plupload" name="productImgs[0].path" id="img1-${j.index}"
                                                        data-list-target="img1-${j.index}:thumb:img"></li>
                                                    <li class="js-plupload" name="productImgs[1].path" id="img2-${j.index}"
                                                        data-list-target="img2-${j.index}:thumb:img"></li>
                                                    <li class="js-plupload" name="productImgs[2].path" id="img3-${j.index}"
                                                        data-list-target="img3-${j.index}:thumb:img"></li>
                                                    <li class="js-plupload" name="productImgs[3].path" id="img4-${j.index}"
                                                        data-list-target="img4-${j.index}:thumb:img"></li>
                                                    <li class="js-plupload" name="productImgs[4].path" id="img5-${j.index}"
                                                        data-list-target="img5-${j.index}:thumb:img"></li>
                                                    </ul> --%>
                                                    <div class="pro-uploader cz" id="pro-img">
                                                    <ul class="upload-list">
	                                            <c:forEach var="idx" begin="0" end="4">
	                                                <li id="pro-primary-queue-${idx}-${j.index}">
	                                                    <span class="primary-img-txt">上传图片</span>
	                                                    <%-- <c:if test="${not empty photoList[idx]}">
	                                                        <div data-saved-url="${ctx}/${photoList[idx]}">
	                                                            <input type="hidden" name="productImgs[${idx}].id" value="${productImgs[idx].id}">
	                                                            <input type="hidden" name="photoList[${idx}]" value="${photoList[idx]}">
	                                                        </div>
	                                                    </c:if> --%>
	                                                </li>
	                                                <script type="text/javascript">
	                                                    $(function(){
	                                                        Uploader2({
	                                                            browse_button: 'pro-primary-queue-${idx}-${j.index}',
	                                                            url:'${ctx}/storage/images/preupload',
	                                                            policy: true,
	                                                            name: 'photoList[${idx}]',
	                                                            list: 'pro-primary-queue-${idx}-${j.index}',
	                                                            filters: {mime_types: [{title: 'Allowd Files', extensions: 'jpg,png,gif'}]},
	                                                            mode: 't',
	                                                            max_file_count: '1',
	                                                            max_file_size: '10m'
	                                                        });
	                                                    });
	                                                </script>
	                                            </c:forEach>
                                        </ul></div>
                                                    <span class="explain">上传凭证：每张图片大小不超过10M，支持JPG、PNG格式、最多上传5张</span>
                                                </td>
                                            </tr>
                                        </table>
                                        <a href="javascript:void(0);" class="evaluation evaluationBtton">评价</a>
                                    </div>
                                </div>
                                </c:forEach>
                                                  
                        </div>
                        <!-- 分页-->
                   
                        <form id="pagination-form" class="pagination-form">
	                		<m:pagination totalPages="${goodses.totalPages}" pageParam="page" skip="false"/>
	                	</form>
                        </c:if>
                        <!--我的待评价(空)-->
                        <c:if test="${fn:length(goodses.data) eq 0 }">
                            <div class="emptyList clearfix">
                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
                        <ul class="fl emptyUl">
                            <li>您没有已评论的商品~</li>
                            <li><a href="${ctx}/search">去购物>></a></li>
                        </ul>
                    </div>
                        </c:if>
                </div>
              
               
                   
		                <%--  <div class="rightWrap fr">
		                    
		                    <div class="sameTitle">我的收藏</div>
		
		                    <div class="emptyList clearfix">
		                        <img src="${ctx }/images/pc/emptyImg.png" width="160" height="160" class="fl" />
		                        <ul class="fl emptyUl">
		                            <li>您还没有收藏任何商品，快去寻找心仪的全球好货吧~</li>
		                            <li><a href="${ctx}/search">去购物>></a></li>
		                        </ul>
		                    </div>
		
		                </div>
                    --%>
            </div>
        </div>    
        <!-- //main -->
        <%@include file="/WEB-INF/view/include/pc/foot.jspf" %>
        <!-- //foot -->
        <!-- 右侧悬浮框-->
        <%@include file="/WEB-INF/view/include/pc/rightFloatAlert.jspf"%>
   
   </div>
    <!-- //wrap -->
     
    <script src="${ctx}/js/pc/jquery.slides.min.js"></script>
    <script src="${ctx}/js/pc/Tab.js"></script>
    <script src="${ctx}/js/pc/jquery.raty.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/js/code-html.js"></script>
    <script type="text/javascript">

    GlanwayTab(".tabBtn p", ".tabDiv>table");
    $(".tabBtn p").click(function () {
        $(this).addClass("on").siblings().removeClass();
    })
    $(".myRatingTabOneTable .evaluation").click(function () {
        if ($(this).is(".on")) {
            $(this).removeClass("on").html("评价").parents(".myRatingTabOneTable").siblings().hide();
        } else {
            $(this).addClass("on").html("收起评价").parents(".myRatingTabOneTable").siblings().show();
        }
    })
    //星级评价
    $(".starScore").each(function (i) {
        $(this).raty({
            starOff: '${ctx}/images/pc/icoStarG.png',
            starOn: '${ctx}/images/pc/icoStarY.jpg',
            targetKeep: true,
            score: function () {
                return $(this).attr('data-score');
            }
        });
    });
    var clicked= false
    $(".evaluationBtton").click(function () {
        clicked=true;
        var $twoTable = $(this).prev();
        if(clicked){
            var $myRatingTabTwoTable = $("textarea", $twoTable);
            if ($myRatingTabTwoTable.val().length==0) {
                layer.msg('评价信息不能为空', {
                    icon: 2,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {});
                clicked =false;
                return;
            }
            var commentText = js.lang.String.decodeHtml($myRatingTabTwoTable.val());
            var $input = $("input[name='score']", $twoTable);
            var score  = $input.val();
            var pic = "";
            $(".upload-list input[type=hidden]",$twoTable).each(function(i,e){
                pic+=$(e).val()+",";
            });
            
            pic = pic.substr(0,pic.length-1);
            od=$("input[name='od']",$twoTable).val();
            gid=$("input[name='gid']",$twoTable).val();
            $.ajax({
                url:"${ctx}/person-center/commenting",
                type:"post",
                data:{'orderDetail.id':od,'goods.id':gid,grade:score,content:commentText,photos:pic}
            }).done(function(data){
                if(data.success){
                    layer.msg('评价成功', {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        location.href="${ctx}/person-center/comment?commented=0";
                    });
                }else{
                    if(data.data == 0){
                        location.href="${ctx}/login?redirectUrl=${ctx}/person-center/comment?commented=0"
                    }else{
                        layer.msg(data.message, {
                            icon: 2,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function () {});
                        clicked=false;
                    }
                }
            }).fail(function(){
                layer.msg('服务器繁忙，请稍候再试', {
                    icon: 2,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {});
                clicked=false;
            })

        }
    });
</script>
   
</body>
</html>
