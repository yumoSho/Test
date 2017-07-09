$.fn.extend({
    "timeUp": function (params) {
        var $targets = $(this);
        $targets.each(function () {
            var $me = $(this);
            var $stopTime = $me.data("endtime");

            var k = $stopTime.split(' ');
            var k1 = k[1].split(':');
            var k2 = k[0].replace(/-/g,"/");
            //console.log(k2);
            var thisTimer = setInterval(function () {
                var endTime = new Date(new Date(k2).getTime() + k1[0] * 3600000 + k1[1] * 60000 + k1[2] * 1000);
                //console.log(endTime);
                var nowTime = new Date();
                var buyTime = parseInt((endTime.getTime() - nowTime.getTime()) / 1000);
                if (buyTime < 0) {
                    buyTime = 0;
                    if (params && params.end) {
                        params.end($me);
                        clearInterval(thisTimer);
                    }
                };
                var d = parseInt(buyTime / 3600 / 24),
                    h = parseInt((buyTime / 3600) % 24),
                    m = parseInt((buyTime / 60) % 60),
                    s = parseInt(buyTime % 60);
                var text = (params && params.template)
                    ? params.template
                    : "剩余 %d 天 %h 小时 %m 分钟 %s 秒";
                text = text.replace("%d", d);
                text = text.replace("%h", h);
                text = text.replace("%m", m);
                text = text.replace("%s", s);

                $me.html(text);
            }, 1000);
        });
    }
});