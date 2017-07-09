/**
 * 省市联动
 * Created by Administrator on 2016/4/7.
 */
(function ($, exports) {
    function CityGanged() {
        var cityGanged = this;
        $("#privinceCode").off("change").on("change", function () {
            cityGanged.loadCitys($(this).val());

        });
        $("#cityCode").off("change").on("change", function () {
            cityGanged.loadAreas($(this).val());
        });

        var privinceCode = $("#privinceCode").val();
        this.loadCitys(privinceCode);
    };
    CityGanged.prototype = {
        loadCitys: function (superCode) {
            if (!superCode) return;
            $.ajax({
                type: "POST",
                url: contextPath + "/hatCity/ajaxListCityBySuperCode",
                data: "superCode=" + superCode,
                error: function () {
                },
                success: function (data) {
                    if (data && data.length > 0) {
                        var cityDom = $("#cityCode").empty();

                        $.each(data, function (i, obj) {
                            var option = $('<option value="' + obj.cityCode + '">' + obj.cityName + '</option>');
                            cityDom.append(option);
                        });

                        /*如果是编辑页面，则需要选中的默认值*/
                        var cityCode = $("#defaultCityCode").val();
                        if (cityCode) {
                            $("#cityCode").val(cityCode);
                            $("#defaultCityCode").remove();
                        } else {
                            cityCode = $("#cityCode").val();
                        }
                        cityGanged.loadAreas(cityCode);
                    }
                }
            });
        },
        loadAreas: function (superCode) {
            if (!superCode) return;
            $.ajax({
                type: "POST",
                url: contextPath + "/hatArea/ajaxListAreaBySuperCode",
                data: "superCode=" + superCode,
                error: function () {
                },
                success: function (data) {
                    if (data && data.length > 0) {
                        var areaDom = $("#areaCode").empty();

                        $.each(data, function (i, obj) {
                            var option = $('<option value="' + obj.areaCode + '">' + obj.areaName + '</option>');
                            areaDom.append(option);
                        });
                        var areaCode = $("#defaultAreaCode").val();
                        if (areaCode) {
                            $("#areaCode").val(areaCode);
                            $("#defaultAreaCode").remove();
                        }
                    }
                }
            });
        }
    };
    exports.cityGanged = new CityGanged();
})(jQuery, window);
