/**
 * Created by Richard Xue on 14-5-10.
 */
(function($){
    Date.prototype.format=function(fmt) {
        var o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "/u65e5",
            "1" : "/u4e00",
            "2" : "/u4e8c",
            "3" : "/u4e09",
            "4" : "/u56db",
            "5" : "/u4e94",
            "6" : "/u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }

    $.extend({
        formatDate:function(ms){
            return ms ? new Date(ms).format("yyyyMMdd") : "";
        },
        formatDate1:function(ms){
            if(!ms) return;
            if(/^\d+$/.test(ms)) {
                return ms ? new Date(ms).format("yyyy-MM-dd") : "";
            } else {
                return ms.substring(0,10);
            }
        },
        mustache : function (template, view, partials) {
            return Mustache.render(template, view, partials);
        }
    });

    $.fn.extend({
        mustache : function (view, partials) {
            return $(this).map(function (i, elm) {
                var template = $.trim($(elm).html());
                var output = $.mustache(template, view, partials);
                return $(output).get();
            });
        }
    });

})(jQuery);

