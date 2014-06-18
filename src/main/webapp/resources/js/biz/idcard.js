/**
 * Created by Richard Xue on 14-6-18.
 */
$(function(){
    var idcard = {};
    idcard.init = function() {
        if(typeof window.dialogArguments != "undefined") {
            var param = window.dialogArguments;
            alert(param.name);
        }
    };
    idcard.init();
});