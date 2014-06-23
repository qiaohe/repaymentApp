/**
 * Created by Richard Xue on 14-5-19.
 */
$(function($){
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
        detail2.loadData("../api/members/loans/search?q=m.ID='"+memberNo+"'");
        detail2.initEvent();
    };
    detail2.initEvent = function() {
        $("#search-btn").on("click",function(){
            var memberNo = $.trim($("#memberNo").val());
            var loanNo = $.trim($("#loanNo").val());
            var loanStatus = $.trim($("#status").val());
            var param = "";
            if(memberNo) {
                param += " and m.ID = '"+memberNo+"'";
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
    };
    detail2.loadData = function(url){
        $("#detail2-table").html("<tr>\n"+
            "<td style=\"width:4%;\"></td>"+
            "<td style=\"width:6%;\">会员编号</td>\n"+
            "<td style=\"width:6%;\">姓名</td>\n"+
            "<td style=\"width:10%;\">贷款编号</td>\n"+
            "<td style=\"width:8%;\">金额</td>\n"+
            "<td style=\"width:6%;\">APR</td>\n"+
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
                        "<td style='cursor: pointer;'> >> </td>"+
                        "<td>"+entity.memberNo+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.loanNo+"</td>\n"+
                        "<td>"+entity.amt+"</td>\n"+
                        "<td>"+entity.apr+"</td>\n"+
                        "<td>"+entity.term+"</td>\n"+
                        "<td>"+ $.formatDate(entity.loanDate)+"</td>\n"+
                        "<td>"+entity.prinaipal+"/"+entity.paidPrinaipal+"</td>\n"+
                        "<td>"+entity.interest+"/"+entity.paidInterest+"</td>\n"+
                        "<td>"+entity.paidOverdueInterest+"</td>\n"+
                        "<td>"+entity.curDelq+"/"+entity.maxDelq+"</td>\n"+
                        "<td>"+loanStatus[entity.status]+"</td>\n"+
                    "</tr>";
                });
                $("#detail2-table").append(contentHtml);
                 detail2.bindDetail();
            }
        });

    };
    detail2.bindDetail = function() {

    };
    detail2.init();
})(jQuery);