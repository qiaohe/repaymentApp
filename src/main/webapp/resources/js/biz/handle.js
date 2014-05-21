/**
 * Created by Richard Xue on 14-5-21.
 */
$(function(){
    var handle = {};
    handle.init = function(){
        handle.loadData();
    };
    handle.loadData = function() {
        handle.initEvent();
    };
    handle.initEvent = function(){
        $(".fallBack").on("click",function(){
            confirm("aa");
        });

        $(".addTo").on("click",function(){
            prompt("aaa");
        });

        $(".takeBack").on("click",function(){
            confirm("ss");
        });
    };
    handle.init();
});