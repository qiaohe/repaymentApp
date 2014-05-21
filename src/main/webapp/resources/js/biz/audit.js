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
            var $input = $(this).parent().prev().find("input");
            if($.trim($input.val()) == "" || $.trim($input.val()) == "0") {
                bootbox.alert("请输入实转金额！");
                return;
            }
            bootbox.dialog();
        });
        $("#audit-table").on("click",".confirm",function(){
            var $input = $(this).parent().prev().find("input");
            if($.trim($input.val()) == "" || $.trim($input.val()) == "0") {
                bootbox.alert("请输入实转金额！");
                return;
            }
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
                            "<input type=\"button\" class=\"confirm\" value=\"已复核\" loanNo=\""+entity.loanId+"\" style=\"width: 80;\"></td>\n"+
                        "</tr>";
                });
                $("#audit-table").append(contentHtml);
            }
        });
    };
    audit.init();
})(jQuery);