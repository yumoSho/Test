/**
 * Created by Administrator on 2016/4/6.
 */

 

$(function () {
    var $g = $('#datagrid').jqGrid({
            url: 'list',
            datatype: 'json',
            colNames: ['用户名', 'orderId', '手机号',  '注册平台', '登录次数', '最近登录时间'],
            colModel: [
                
                {name: 'memberName', index: 'memberName', template: 'text',width: 250, sortable: false, search: true}
                , {name: 'oId', index: 'oId', hidden: true, key: true}
                , {name: 'phone', index: 'M.phone', template: 'text',width: 200, sortable: false, search: false}          
               // ,{name: 'registerFromValue', index: 'registerFromValue', template: 'text', width: 100, sortable: false, search: true}
               , {
                    name: 'registerFrom', index: 'registerFrom', template: 'select', stype: 'select',
                    width: 150,
                    searchoptions: {sopt: ["eq", "ne"], value: getSearchoptions(platFormTypes)},
                    formatter: function (val) {
                        return formatByDictionary(val, platFormTypes);
                    }
                }
                , {name: 'loginNum', index: 'loginCount', template: 'text', sortable: true, search: false}
                , {name: 'lastLoginTime', index: 'lastLoginTime', template: 'date',width: 230, sortable: true, search: false}
            ],
            multiselect: false,
           // autowidth: true,
            shrinkToFit: false,
            width:1012,
            height: 'auto',
            pager: '#pagination',
            sortname: 'id',
            footerrow: true,
            userDataOnFooter: true,
            altRows: true,
            sortorder: 'desc',
            beforeRequest: function () {
                var postData = $("#datagrid").jqGrid("getGridParam", "postData");
                $.extend(postData, {
                    "search_source_eq_S": $("#source").val(),
                    "search_bankId_eq_L": $("#bankId").val(),
                   
                    "search_lastLoginTime_gt_D": $("#beginDate").val(),
                    "search_lastLoginTime_lt_D": $("#endDate").val()
                    /*"bankName":$("#bankId option:selected").text(),
                    "supplierName":$("#supplierId option:selected").text(),
                    "beginDate":$("#beginDate").val(),
                    "endDate":$("#endDate").val()*/
                });
            },
            loadComplete: function () {
                var bankName = $("#bankId option:selected").text();
               $("caption .bankName").text(bankName);
              
                var beginDate = $("#beginDate").val();
                var endDate = $("#endDate").val();
                $("caption .searchDate").text(beginDate.split(" ")[0] + " — " + endDate.split(" ")[0] );
            }
        })
        ;

    /*查询事件*/
    $("#search").click(function () {
        $("#datagrid").trigger("triggerToolbar");
    });

    /*导出事件*/
    $("#excelExport").click(function () {
        var url = "/admin/memberStatistics/export";
        window.HgUtil.exportExcel($("#datagrid"), url);
    });

});
