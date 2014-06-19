/**
 * Created by Richard Xue on 14-6-17.
 */
$(function(){
    var pboc_out = {};
    pboc_out.init = function(){
        pboc_out.initEvent();
    };
    pboc_out.initEvent = function(){
        $("#pboc-table").on("click","#checkAll",function(){
            $(".check-item").prop("checked",$(this).prop("checked"));
        });
        $("#pboc-table").on("click",".check-item",function(){
            var checkAll = true;
            $(".check-item").each(function(i){
                if(!$(this).prop("checked")) {
                    checkAll = false;
                    return false;
                }
            });
            $("#checkAll").prop("checked",checkAll);
        });
    };
    pboc_out.init();
});