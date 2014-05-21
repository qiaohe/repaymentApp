/**
 * Created by Richard Xue on 14-5-19.
 */
(function($){
    var transfer = {};
    transfer.init = function(){
        transfer.bankMap = transfer.makeDict("BANK");
        transfer.initEvent();
        transfer.loadData();
    };
    transfer.initEvent = function() {
        $("#transfer-table").on("click",".transfer",function(){
            var $transfer = $(this);
            var $transferNo = $transfer.parent().prev().find(".transferNo");
            var transferNo = $.trim($transferNo.val());
            if(!transferNo) {
                alert("请输入转账流水号！");
                return;
            }
            var loanNo = $(this).attr("loanNo");
            $.ajax({
                url: "../api/account/loan/"+loanNo+"/code/" + transferNo,
                dataType: "json",
                type: "GET",
                contentType: "application/json",
                success: function (json) {
                    if(json) {
                        bootbox.alert("操作成功！",function(){
                            $transfer.parents("tr").remove();
                        });
                    } else {
                        bootbox.alert("操作成功！");
                    }
                },
                error: function(){
                    bootbox.alert("请求异常！");
                }
            });
        });
    };
    transfer.makeDict = function (type) {
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
    transfer.loadData = function() {
        $("#transfer-table").html("<tr>\n"+
            "<td style=\"width:8%;\">贷款编号</td>\n"+
            "<td style=\"width:13%;\">申请编号</td>\n"+
            "<td style=\"width:10%;\">审核时间</td>\n"+
            "<td style=\"width:8%;\">贷款人姓名</td>\n"+
            "<td style=\"width:13%;\">信用卡卡号</td>\n"+
            "<td style=\"width:8%;\">发卡行</td>\n"+
            "<td style=\"width:8%;\">贷款金额</td>\n"+
            "<td style=\"width:8%;\">应转金额</td>\n"+
            "<td style=\"width:13%;\">转账流水号</td>\n"+
            "<td style=\"width:10%;\">操作</td>\n"+
        "</tr>");

        $.ajax({
            url: "../api/account/loans/search?q=p.CONFORMED=0",
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
                        "<td>"+transfer.bankMap[entity.bank]+"</td>\n"+
                        "<td>"+entity.amt+"</td>\n"+
                        "<td>"+(entity.amt-entity.payAmt)+"</td>\n"+
                        "<td><input type=\"text\" class=\"transferNo\" value=\"\"></td>\n"+
                        "<td><input type=\"button\" class=\"transfer\" value=\"已转账\" loanNo=\""+entity.loanId+"\" style=\"width: 100px;\"></td>\n"+
                    "</tr>";
                });
                $("#transfer-table").append(contentHtml);
            }
        });
    };
    transfer.init();
})(jQuery);