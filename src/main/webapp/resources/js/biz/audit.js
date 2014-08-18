/**
 * Created by Richard Xue on 14-5-19.
 */
(function(){
    var audit = {};
    audit.init = function() {
        audit.bankMap = audit.makeDict("BANK");
        audit.loadData();
        audit.initEvent();
    };
    audit.makeDict = function (type) {
        var map = {};
        $.ajax({
            url: "../api/dict/" + type,
            dataType: "json",
            type: "GET",
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (!json) return map;
                $.each(json, function (i, dict) {
                    map[dict.key] = dict.value;
                });
            }
        });
        return map;
    };
    audit.initEvent = function() {
        $("#audit-table").on("click",".error",function(){
            var amt = $.trim($(this).parent().prev().find("input").val());
            if(amt == "") {
                bootbox.alert("请输入实转金额！");
                return;
            }
            if(!/^\d+(\.\d+)?$/.test(amt)) {
                bootbox.alert("输入的实转金额不能包含非数字字符！");
                return;
            }
            var dialog = new Dialog($("#error-div").html(), {modal: false,showTitle:false});
            dialog.show();
            var $dialog = $(".dialog");
            var $error = $(this);
            var loanNo = $(this).attr("loanNo");
            $dialog.on("click",".msg-confirm",function(){
                var msg = $(".dialog").find(".msgType").val();
                $.ajax({
                    url: "../api/account/loan/"+loanNo+"/handle/"+parseFloat(amt)+"/msg/"+encodeURIComponent(msg),
                    dataType: "json",
                    type: "GET",
                    contentType: "application/json",
                    success: function (json) {
                        if(json) {
                            bootbox.alert("处理完成！",function(){
                                $error.parents("tr").remove();
                            });
                        } else {
                            bootbox.alert("错误处理出现异常！");
                        }
                    },
                    error: function() {
                        bootbox.alert("请求异常！");
                    }
                });
                dialog.close();
            });
            $dialog.on("click",".msg-cancel",function(){
                dialog.close();
            });
        });
        $("#audit-table").on("click",".confirm",function(){
            var $confirm = $(this);
            var amt = $.trim($confirm.parent().prev().find("input").val());
            if(amt == "" || parseFloat(amt)  == 0) {
                bootbox.alert("请输入实转金额！");
                return;
            }
            if(parseFloat($confirm.attr("amt")) != parseFloat(amt)) {
                bootbox.alert("实转金额与应转金额不一致,请重新输入！");
                return;
            }
            var loanNo = $(this).attr("loanNo");
            $.ajax({
                url: "../api/account/loan/"+loanNo+"/review/"+parseFloat(amt),
                dataType: "json",
                type: "GET",
                contentType: "application/json",
                success: function (json) {
                    if(json) {
                        bootbox.alert("审核完成！",function(){
                            $confirm.parents("tr").remove();
                        });
                    } else {
                        bootbox.alert("审核出现异常！");
                    }
                },
                error: function() {
                    bootbox.alert("请求异常！");
                }
            });
        });
    };
    audit.loadData = function() {
        $("#audit-table").html("<tr>\n"+
            "<td style=\"width:8%;\">贷款编号</td>\n"+
            "<td style=\"width:12%;\">申请编号</td>\n"+
            "<td style=\"width:8%;\">审核时间</td>\n"+
            "<td style=\"width:6%;\">贷款人姓名</td>\n"+
            "<td style=\"width:10%;\">信用卡卡号</td>\n"+
            "<td style=\"width:6%;\">发卡行</td>\n"+
            "<td style=\"width:6%;\">应转金额</td>\n"+
            "<td style=\"width:8%;\">转账时间</td>\n"+
            "<td style=\"width:8%;\">转账流水号</td>\n"+
            "<td style=\"width:8%;\">实转金额</td>\n"+
            "<td style=\"width:12%;\">操作</td>\n"+
        "</tr>");

        $.ajax({
            url: "../api/account/loans/search?q=p.CONFORMED=1",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            success: function (json) {
                if (!json) { return; }
                var contentHtml = "";
                $.each(json,function(i,entity){
                    contentHtml += "<tr>\n"+
                        "<td>"+entity.loanId+"</td>\n"+
                        "<td>"+entity.appNo+"</td>\n"+
                        "<td>"+ $.formatDate(entity.appDate)+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.cardNo+"</td>\n"+
                        "<td>"+audit.bankMap[entity.bank]+"</td>\n"+
                        "<td>"+entity.amt+"</td>\n"+
                        "<td>"+$.formatDate(entity.transTime)+"</td>\n"+
                        "<td>"+(entity.transCode?entity.transCode:"")+"</td>\n"+
                        "<td><input type=\"text\" class=\"payAmt\" value=\"\"></td>\n"+
                        "<td><input type=\"button\" class=\"error\" value=\"转账错误\" loanNo=\""+entity.loanId+"\" style=\"width: 100px;\">" +
                            "<input type=\"button\" class=\"confirm\" style=\"margin-left: 10px;\" value=\"已复核\" amt=\""+entity.amt+"\" loanNo=\""+entity.loanId+"\" style=\"width: 80;\"></td>\n"+
                        "</tr>";
                });
                $("#audit-table").append(contentHtml);
            }
        });
    };
    audit.init();
})(jQuery);