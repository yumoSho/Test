$(function(){
	//全国复选框
	$("#allCheckBox").click(function(){
		var $allcheckBox = $("input[type=checkBox]"); //所有
		if ($(this).is(":checked")){
        	//选中
        	$allcheckBox.each(function(){
        		$(this).attr('checked', true);
        	});
        }else{
        	//取消
        	$allcheckBox.each(function(){
        		$(this).attr('checked', false);
        	});
        }
	}); 
	
	 /**点击相应省份，所属市全选和全部取消***/
	 $(".provinceName").click(function(){
     	//父级节点的下一个td的复选框
       	var $cityCheckBox = $(this).parent().next().children(".cityName"); 
        if ($(this).is(":checked")){
        	//选中
        	$cityCheckBox.each(function(){
        		$(this).attr('checked', true);
        	});
        }else{
        	//取消
        	$cityCheckBox.each(function(){
        		$(this).attr('checked', false);
        	});
        }
     });
	 
	 /**点击市级复选框动作***/
	 $(".cityName").click(function(){
		 var $provinceCheckBox = $(this).parent().prev().children();
		 if ($(this).is(":checked")){
			//选中
		 	var $siblingBox = $(this).siblings(".cityName");  //兄弟节点
		 	var num = $siblingBox.length + 1 ; //加它本身
		 	var selectNum = 1; //当前已经选中
		 	//遍历已选中的个数
		 	$siblingBox.each(function(){
		 		if($(this).attr('checked')){
		 			selectNum++;
		 		}
		 	});
		 	//全部选中，勾选省级复选框
		 	if(selectNum == num){
		 	 	$provinceCheckBox.each(function(){
		     		$(this).attr('checked', true);
		        });
		 	}
	     }else{
	    	//取消
	     	$provinceCheckBox.each(function(){
	     		$(this).attr('checked', false);
	        });
	     }
	 });
	 
	 $('#btn-cat-sel').click(function(){
		 openWindow();
	 });
	 
	//验证
     $("#fromSave").validate({
         rules:{
         	supplierName:{
                 required: true,
                 maxlength: 30
             },
             areaName: {
             	required: true,
                 maxlength: 30
             },
             price: {
             	required: true, 
             	number: true
             }
         },
         messages:{
         	supplierName:{
                 required: "供应商不能为空",
                 maxlength: "供应商名称不能超过30个字符"
             },
             areaName:{
                 required: "邮寄地区名称不能为空",
                 maxlength: "邮寄地区名称不能超过30个字符"
             },
             price:{
                 required: "价格不能为空",
                 number: "请输入一个有效的数字"
             }
         },
         submitHandler: function(form){
				$(form).find(":submit").attr("disabled", true).attr("value",
						"Submitting...").css("letter-spacing", "0");
				
            form.submit();
         }
     });
	   
});

var lastSel = undefined; //记录所选的行id
function openWindow() {
	 $('#datagrid').jqGrid({
		    url: contextPath + '/admin/supplier/list',
		    datatype: 'json',
		    colNames: ['id', '供应商编号', '供应商名称', '电话'],
		    colModel: [
		        {name: 'id', index: 'id', hidden: true, key: true},
		        {name: 'supplierNum', index: 'supplierNum', template: 'text', sortable: false, search:true},
		       	{name: 'supplierName', index: 'supplierName', template: 'text', sortable: false, searchoptions: {sopt: ["cn"]}},
		        {name: 'phone', index: 'phone', template: 'text', sortable: false, search:true},
		    ],
		    multiselect: true,
		    shrinkToFit: true,
		    height: '100%',
		    width:'100%',
		    pager: '#pagination',
		    sortname: 'createdDate',
		    sortorder: 'desc',
		    onSelectRow: function (rowId, status, e) {
		        if (rowId == lastSel) {
		            $(this).jqGrid("resetSelection");
		            lastSel = undefined;
		            status = false;
		        } else {
		            lastSel = rowId;
		        }
		    },
			beforeSelectRow: function (rowId, e) {
		            $(this).jqGrid("resetSelection");
		            return true;
		    }
		});
	 
    layer.open({
        zIndex: 10,
        title: '选择供应商',
        type: 1,
        skin: 'layer-ext-alertpop', //加上边框
        area: ['600px', '400px'], //宽高
        shadeClose: true,
        content: $('#cate-sel-dlg'),
        btn: ['确定', '取消'],
        cancel: function (index) {
            layer.close(index);
        },
        yes: function (index) {
       	 var rowData =  $('#datagrid').jqGrid('getRowData', lastSel);
       	 if(rowData.length == undefined){
	        	 var supplierName = rowData.supplierName;
	        	 var supplierId = rowData.id;
	        	 $("#supplierName").val(supplierName);
	        	 $("#supplierId").val(supplierId);
				 layer.close(index);
       	 }else{
       		 Glanway.Messager.alert("提示", "您至少应该选择一行记录");
       	 }
        }
    });
}