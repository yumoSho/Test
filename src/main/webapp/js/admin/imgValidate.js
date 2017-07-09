/**
 * Created by tianxuan on 2016/1/7.
 *
 */

 (function($,exports){
    function ImgValidate(){
        $("ul[flag^=pro-img]").children("li").css("cursor", "pointer").append($('<span class="imgMsg" style="position:absolute;color: #ccc;display: inline-block;top:35%;left: 15%">点击上传图片</span>'));
    }

    ImgValidate.prototype = {
        validateImg:function(){
            var flag = true;
            var imgs = $(".upload-list li");
            imgs.each(function(i){
                var li = $(this);
                var src = li.find("img").attr("src");
                var j = 0; //计数器，判断是否为第一个验证未通过的图片
                if (!src || src == '') {
                    li.find("span.imgMsg").remove();
                    li.append($('<span class="imgMsg" style="position:absolute;color: red;display: inline-block;top:35%;left: 15%;font-weight: 900">点击上传图片</span>'));
                    /*滚动到第一个验证未通过的图片处*/
                    if(j<1){
                        li.after("<input />");
                        li.next("input").focus().remove();
                        flag = false;
                        j++;
                    }
                }
            });
            return flag;
        }
    }

    exports.imgValidate = new ImgValidate();
})(jQuery, window);
