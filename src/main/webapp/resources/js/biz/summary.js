/**
 * Created by Richard Xue on 14-5-19.
 */
$(function(){
    var summary = {},
        sex = {
            "0": "男",
            "1": "女"
        };
    summary.init = function() {
        summary.initEvent();
        // initialize dictionary
        summary.educationMap = summary.makeDict("EDUCATION");
        summary.industryMap = summary.makeDict("INDUSTRY");
        summary.loadData("../api/members/loanSummary");
    };
    summary.makeDict = function (type) {
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
    summary.initEvent = function() {
        $("#search-btn").on("click",function(){
            var idcard = $.trim($("#idcard").val());
            var name = $.trim($("#name").val());
            var mobile = $.trim($("#mobile").val());
            var param = "";
            if(idcard) {
                param += " and ic.ID_NO='"+idcard+"'";
            }
            if(name) {
                param += " and ic.NAME='"+name+"'";
            }
            if(mobile) {
                param += " and m.mobile='"+mobile+"'";
            }
            if(param) {
                summary.loadData("../api/members/loanSummary/search?q="+param.replace(" and",""));
            } else {
                summary.loadData("../api/members/loanSummary");
            }
        });
    };
    summary.loadData = function(url){
        $("#summary-table").html("<tr>\n"+
            "<td style=\"width:6%;\">会员号</td>\n"+
            "<td style=\"width:8%;\">微信号</td>\n"+
            "<td style=\"width:6%;\">姓名</td>\n"+
            "<td style=\"width:10%;\">身份证号</td>\n"+
            "<td style=\"width:8%;\">手机号</td>\n"+
            "<td style=\"width:6%;\">手机归属地</td>\n"+
            "<td style=\"width:4%;\">性别</td>\n"+
            "<td style=\"width:10%;\">行业</td>\n"+
            "<td style=\"width:6%;\">学历</td>\n"+
            "<td style=\"width:10%;\">email</td>\n"+
            "<td style=\"width:6%;\">贷款笔数</td>\n"+
            "<td style=\"width:8%;\">BLK</td>\n"+
            "</tr>");
        $.ajax({
            url: url,
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            success: function (json) {
                if (!json) return;
                var contentHtml = "";
                $.each(json, function (i, entity) {
                    var industry = summary.industryMap[entity.industry];
                    var education = summary.educationMap[entity.education];
                    contentHtml += "<tr>\n"+
                        "<td class=\"summary-mem\">"+entity.id+"</td>\n"+
                        "<td>"+entity.wcNo+"</td>\n"+
                        "<td>"+(entity.name?entity.name:"")+"</td>\n"+
                        "<td>"+(entity.idNo?entity.idNo:"")+"</td>\n"+
                        "<td>"+(entity.mobile?entity.mobile:"")+"</td>\n"+
                        "<td>"+(entity.mobileCity?entity.mobileCity:"")+"</td>\n"+
                        "<td>"+sex[entity.sex]+"</td>\n"+
                        "<td>"+(industry?industry:"")+"</td>\n"+
                        "<td>"+(education?education:"")+"</td>\n"+
                        "<td>"+(entity.email?entity.email:"")+"</td>\n"+
                        "<td class=\"summary-count\">"+entity.countOfLoan+"</td>\n"+
                        "<td>"+(entity.blockCode?entity.blockCode:"")+"</td>\n"+
                        "</tr>";
                });
                $("#summary-table").append(contentHtml);
            }
        });
        summary.bindDetail();
    };
    summary.bindDetail = function() {
        $("#summary-table").on("click",".summary-mem",function(){
            var memberNo = $.trim($(this).text());
            window.open("detail1.html?memberNo="+memberNo,"_blank");
        });
        $("#summary-table").on("click",".summary-count",function(){
            var count = $.trim($(this).text());
            if(!count || count == "0") {
                return;
            }
            var memberNo = $.trim($(this).prevAll(".summary-mem").text());
            if(memberNo) {
                window.open("detail2.html?memberNo="+memberNo,"_blank");
            }
        });
    };
    summary.init();
});