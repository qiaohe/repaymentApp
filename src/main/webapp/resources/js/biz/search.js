/**
 * Created by Richard Xue on 14-5-19.
 */
$(function(){
    var search = {},
        payStatus = {
            "0":"待处理",
            "1":"已转账",
            "2":"完成",
            "9":"错误",
            "10":"取消"
        };
    search.init = function(){
        search.bankMap = search.makeDict("BANK");
        var url = "../api/account/loans/search?q=1=1";
        search.loadData(url);
        search.initEvent();
    };
    search.initEvent = function() {
        $("#search-info").on("click","#search-btn",function(){
            var loanNo = $.trim($("#loanNo").val());
            var status = $.trim($("#status").val());
            var transDate = $("#transDate").val().replace(/\s+/g,"");
            var param = "";
            if(loanNo) {
                param += " and b.BID="+loanNo;
            }
            if(status) {
                param += " and p.CONFORMED="+status;
            }
            if(transDate) {
                if(transDate.length == 10) {
                    var endDate = new Date(transDate.substr(0,4),parseInt(transDate.substr(5,2),10)-1,transDate.substr(8,2));
                    endDate.setDate(parseInt(transDate.substr(8,2),10)+1);
                    param += " and p.TRANS_TIME>'"+transDate+"' and p.TRANS_TIME<'"+ $.formatDate1(endDate.getTime())+"'";
                } else {
                    alert("转账日期格式不正确！");
                    return;
                }
            }
            if(param) {
                param = param.replace(" and","");
            } else {
                param = "1=1";
            }
            search.loadData("../api/account/loans/search?q="+param);
        });
    };
    search.makeDict = function (type) {
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
    search.loadData = function(url){
        $("#search-table").html("<tr>\n"+
                "<td style=\"width:6%;\">贷款编号</td>\n"+
                "<td style=\"width:8%;\">申请编号</td>\n"+
                "<td style=\"width:8%;\">审核时间</td>\n"+
                "<td style=\"width:6%;\">贷款人姓名</td>\n"+
                "<td style=\"width:10%;\">信用卡卡号</td>\n"+
                "<td style=\"width:6%;\">发卡行</td>\n"+
                "<td style=\"width:6%;\">贷款金额</td>\n"+
                "<td style=\"width:6%;\">已转金额</td>\n"+
                "<td style=\"width:8%;\">转账流水号</td>\n"+
                "<td style=\"width:8%;\">转账日期</td>\n"+
                "<td style=\"width:6%;\">复核人</td>\n"+
                "<td style=\"width:8%;\">复核时间</td>\n"+
                "<td style=\"width:8%;\">错误描述</td>\n"+
                "<td style=\"width:4%;\">状态</td>\n"+
            "</tr>");
        $.ajax({
            url: url,
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            success: function (json) {
                if (!json) { return; }
                var contentHtml = "";
                $.each(json,function(i,entity){
                    var status = payStatus[entity.confirmStatus];
                    contentHtml += "<tr>\n"+
                        "<td>"+entity.loanId+"</td>\n"+
                        "<td>"+entity.appNo+"</td>\n"+
                        "<td>"+ $.formatDate1(entity.appDate)+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.cardNo+"</td>\n"+
                        "<td>"+search.bankMap[entity.bank]+"</td>\n"+
                        "<td>"+entity.amt+"</td>\n"+
                        "<td>"+entity.payAmt+"</td>\n"+
                        "<td>"+(entity.transCode?entity.transCode:"")+"</td>\n"+
                        "<td>"+$.formatDate1(entity.transTime)+"</td>\n"+
                        "<td>"+(entity.confirmId?entity.confirmId:"")+"</td>\n"+
                        "<td>"+$.formatDate1(entity.confirmDate)+"</td>\n"+
                        "<td>"+(entity.errorMsg?entity.errorMsg:"")+"</td>\n"+
                        "<td>"+(status?status:"")+"</td>\n"+
                    "</tr>";
                });
                $("#search-table").append(contentHtml);
            }
        });
    };
    search.init();
});
