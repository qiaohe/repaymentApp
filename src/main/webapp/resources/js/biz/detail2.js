/**
 * Created by Richard Xue on 14-5-19.
 */
(function($){
    var detail2 = {},
        loanStatus = {
            "0": "正常",
            "1": "逾期",
            "2": "打呆",
            "8": "等待放款",
            "9": "结清",
            "null": ""
        };
    detail2.init = function() {
        var url = window.location.href, paramPrefix = "?memberNo=";
        var memberNo = detail2.memberNo = url.substring(url.indexOf(paramPrefix) + paramPrefix.length);
        detail2.loadData("../api/members/loans/search?q=idcard.MEMBER_ID='"+memberNo+"'");
        detail2.initEvent();
    };
    detail2.initEvent = function() {
        $("#search-btn").on("click",function(){
            var memberNo = $.trim($("#memberNo").val());
            var loanNo = $.trim($("#loanNo").val());
            var loanStatus = $.trim($("#status").val());
            var param = "";
            if(memberNo) {
                param += " and idcard.MEMBER_ID = '"+memberNo+"'";
            }
            if(loanNo) {
                param += " and b.BID = '"+loanNo+"'";
            }
            if(loanStatus) {
                param += " and b.STATUS = '"+loanStatus+"'";
            }
            if(param) {
                param = param.replace(" and","");
            } else {
                param = " 1=1";
            }
            detail2.loadData("../api/members/loans/search?q="+param);
        });
        $("#detail2-table").on("click",".detail",function(){
            var loanNo = $(this).attr("loanNo");
            if(loanNo) {
                detail2.showLoanDetail(loanNo);
            }
        });
    };
    detail2.loadData = function(url){
        $("#detail2-table").html("<tr>\n"+
            "<td style=\"width:4%;\"></td>"+
            "<td style=\"width:6%;\">会员编号</td>\n"+
            "<td style=\"width:6%;\">姓名</td>\n"+
            "<td style=\"width:10%;\">贷款编号</td>\n"+
            "<td style=\"width:8%;\">金额</td>\n"+
            "<td style=\"width:6%;\">APR(%)</td>\n"+
            "<td style=\"width:6%;\">期数</td>\n"+
            "<td style=\"width:10%;\">贷款日期</td>\n"+
            "<td style=\"width:12%;\">应还/已还本金</td>\n"+
            "<td style=\"width:12%;\">应还/已还利息</td>\n"+
            "<td style=\"width:8%;\">支付罚息</td>\n"+
            "<td style=\"width:6%;\">逾期天数</td>\n"+
            "<td style=\"width:8%;\">贷款状态</td>\n"+
            "</tr>");
        $.ajax({
            url: url,
            dataType: "json",
            type: "GET",
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (!json) { return; }
                var contentHtml = "";
                $.each(json,function(i,entity){
                    contentHtml += "<tr>\n"+
                        "<td class=\"detail\" loanNo=\""+entity.loanNo+"\" style='cursor: pointer;'> >> </td>"+
                        "<td>"+entity.memberNo+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.loanNo+"</td>\n"+
                        "<td>&yen;"+entity.amt.toFixed(2)+"</td>\n"+
                        "<td>"+(entity.apr*100).toFixed(2)+"</td>\n"+
                        "<td>"+entity.term+"</td>\n"+
                        "<td>"+ $.formatDate(entity.loanDate)+"</td>\n"+
                        "<td>&yen;"+entity.prinaipal.toFixed(2)+"/&yen;"+entity.paidPrinaipal.toFixed(2)+"</td>\n"+
                        "<td>&yen;"+entity.interest.toFixed(2)+"/&yen;"+entity.paidInterest.toFixed(2)+"</td>\n"+
                        "<td>&yen;"+entity.paidOverdueInterest.toFixed(2)+"</td>\n"+
                        "<td>"+entity.curDelq+"/"+entity.maxDelq+"</td>\n"+
                        "<td>"+loanStatus[entity.status]+"</td>\n"+
                        "</tr>";
                });
                $("#detail2-table").append(contentHtml);
            }
        });
    };
    detail2.showLoanDetail = function (borrowNo) {
        $.get("../api/account/loans/" + borrowNo, function (json) {
            var contentHtml = "<table class=\"detail2-repay-table\">\n"+
                "<tr>\n"+
                "    <td style=\"width:6%;\">期数</td>\n"+
                "    <td style=\"width:10%;\">还款日</td>\n"+
                "    <td style=\"width:8%;\">应还本息</td>\n"+
                "    <td style=\"width:12%;\">应还/已还本金</td>\n"+
                "    <td style=\"width:12%;\">应还/已还利息</td>\n"+
                "    <td style=\"width:6%;\">逾期天数</td>\n"+
                "    <td style=\"width:12%;\">应还/已还罚息</td>\n"+
                "</tr>";
            $.each(json, function (i, repayPlan) {
                contentHtml += '<tr>' +
                    '<td>' + repayPlan.termNo + '</td>' +
                    '<td>' + $.formatDate(repayPlan.dueDate) + '</td>' +
                    '<td>&yen;' + repayPlan.dueAmt.toFixed(2) + '</td>' +
                    '<td>&yen;' + repayPlan.duePrincipal.toFixed(2)+"/&yen;"+repayPlan.paidPrincipal.toFixed(2) + '</td>' +
                    '<td>&yen;' + repayPlan.dueInterest.toFixed(2)+"/&yen;"+repayPlan.paidInterest.toFixed(2) + '</td>' +
                    '<td>' + repayPlan.overDueDay + '</td>' +
                    '<td>&yen;' + repayPlan.overDue_Interest.toFixed(2)+"/&yen;"+ repayPlan.paidOverDueInterest.toFixed(2)+ '</td>';
            });
            new Dialog(contentHtml + "</table>", {title: "贷款明细(贷款编号"+borrowNo+")", modal: false}).show();
        });
    };
    detail2.init();
})(jQuery);