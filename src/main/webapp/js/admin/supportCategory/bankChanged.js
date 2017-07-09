/**
 * 银行和帮助中心分类联动
 * 选择银行列表，联动显示帮助分类列表
 * Created by Administrator on 2016/5/17.BankChanged SupportCategory
 */
(function ($, exports) {
    function BankChanged() {
        var bankChanged = this;
        $("#supportCategory_bank").off("change").on("change", function () {
        	$("#supportCategory_category").empty();
        	bankChanged.supportCategory($(this).val());

        });
     

        var supportCategory_bank = $("#supportCategory_bank").val();
        var supportCategoryId = $("#supportCategoryId").val();
        this.supportCategory(supportCategory_bank,supportCategoryId);
    };
    BankChanged.prototype = {
    		supportCategory: function (bankid,supportCategoryId) {
            if (!bankid) return;
            $.ajax({
                type: "POST",
                url: contextPath + "/admin/supportCategory/ajaxListSupportCategoryByBankId",
                data: "bankId=" + bankid,
                error: function () {
                },
                success: function (data) {
                    if (data && data.length > 0) {
                        var supportCategoryDom = $("#supportCategory_category").empty();
                        
                        $.each(data, function (i, obj) {
                            var option = $('<option value="' + obj.id + '">' + obj.name + '</option>');
                            supportCategoryDom.append(option);
                        });
                       
                        //修改页面，需要默认选中之前保存的分类
                        if(supportCategoryId != null && supportCategoryId > 0){
                        	$("#supportCategory_category").val(supportCategoryId); 
                        }
                       
                    }
                }
            });
        },
        
    };
   exports.bankChanged = new BankChanged();
})(jQuery, window);
