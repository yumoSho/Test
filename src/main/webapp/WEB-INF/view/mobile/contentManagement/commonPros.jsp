﻿<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/view/include/taglibs.jspf" %>
<c:set var="com.glanway.jty.servlet.jsp.EscapeXmlELResolver.escape" value="${false}" scope="page" />
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
    <link rel="stylesheet" href="${ctx}/css/mobile/helpcenter.css" />

</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<div class="wrap writeWrap">
    <%@ include file="/WEB-INF/view/include/mobile/headerText.jspf" %><!-- //head -->
    <div class="main">
        <!--常见问题-->
        <div class="compl">
            <ul class="addTxt">
                <c:forEach items="${commonPros.data }" var="commonPro">
                    <li>
                        <a href="javascript:void(0);">${commonPro.title}</a>
                        <div style="font-size:.28rem;color:#999;">
                                ${commonPro.content}
                        </div>
                    </li>
                 </c:forEach>
               <%-- <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0);">如何退换货</a>
                    <div style="font-size:.28rem;color:#999;">
                        <p>
                            1，有退换货要求时请第一时间联系我们，告知我们您的退货信息（退货时间，快递单号，原因）避免造成丢件
                            <br />
                            2，核对并填写退货原因和数量
                            <br />
                            3，将填写好的商品清单，与商品一同退回；请填写顾客姓名淘宝会员名，订单编号，送回地址，退货原因和退货数量等，并一同送返
                            <br />
                            4，公司不接受平邮，到付，货运等快递方式，请谅解！使用此类发货时进行退货的如有造成损失，将由发货人字形承担。
                            <br />
                            5，注意：1签收后，商品和包装完整，在不影响二次销售的情况下，顾客自己承担运费，7天无理由退货，2，自签收之日起7个工作日内，如您认为商品由质量问题（有相关政府部门提供数面鉴定），将为您办理退货。
                        </p>
                    </div>
                </li>--%>
            </ul>
        </div>
        <div class="load">正在加载…</div>
    </div><!-- //main -->
    <a href="javascript:void(0);" class="gotoTop"><img src="${ctx}/images/mobile/icon9.png" /></a>
</div><!-- //wrap -->
</body>
<script src="${ctx}/js/mobile/baiduTemplate.js"></script>

<script type="text/javascript">
    var page=2;
    var pageSize=10;
    var totalPage=${commonPros.totalPages};
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
                url:"${ctx}/mobile/contentManagement/helpCenterCommon/list",
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
                                content: e.content,

                            }
                            var html=bt('newsModel', a);
                            $(".addTxt").append(html);
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

    <li>
        <a href="javascript:void(0);"><!=title!></a>
        <div style="font-size:.28rem;color:#999;">
            <!=content!>
        </div>
    </li>
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
<script>

    $(function () {
        $(".addTxt").on("click", " li a", function () {
            $(this).siblings("div").stop(true ,true).slideToggle().parents("li").siblings().find("div").slideUp();
            $(this).toggleClass("bgI").parents("li").siblings().find("a").removeClass("bgI");
        });
    });
</script>
</html>
