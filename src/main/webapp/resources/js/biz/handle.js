/**
 * Created by Richard Xue on 14-5-21.
 */
$(function(){
    var handle = {};
    handle.init = function(){
        handle.bankMap = handle.makeDict("BANK");
        handle.initEvent();
        handle.loadData();
    };
    handle.makeDict = function (type) {
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
    handle.loadData = function() {
        $("#handle-table").html("<tr>\n"+
            "<td style=\"width:6%;\">贷款编号</td>\n"+
            "<td style=\"width:10%;\">申请编号</td>\n"+
            "<td style=\"width:8%;\">审核时间</td>\n"+
            "<td style=\"width:6%;\">贷款人姓名</td>\n"+
            "<td style=\"width:10%;\">信用卡卡号</td>\n"+
            "<td style=\"width:6%;\">发卡行</td>\n"+
            "<td style=\"width:6%;\">贷款金额</td>\n"+
            "<td style=\"width:6%;\">已转金额</td>\n"+
            "<td style=\"width:12%;\">错误原因</td>\n"+
            "<td style=\"width:12%;\">操作</td>\n"+
        "</tr>");

        $.ajax({
            url: "../api/account/loans/search?q=p.CONFORMED=9",
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
                        "<td>"+handle.bankMap[entity.bank]+"</td>\n"+
                        "<td>"+entity.amt+"</td>\n"+
                        "<td>"+entity.payAmt+"</td>\n"+
                        "<td>"+entity.errorMsg+"</td>\n"+
                        "<td class=\"operation\" loanNo=\""+entity.loanId+"\">\n"+
                            "<input type=\"button\" class=\"fallBack\" value=\"转账退回\">\n"+
                            "<input type=\"button\" class=\"addTo\" value=\"补款\">\n"+
                            "<input type=\"button\" class=\"takeBack\" value=\"追回\">\n"+
                        "</td>"+
                        "</tr>";
                });
                $("#handle-table").append(contentHtml);
            }
        });
    };
    handle.initEvent = function(){
        $("#handle-table").on("click",".fallBack",function(){
            var $fallBack = $(this);
            var loanNo = $(this).parent().attr("loanNo");
            bootbox.confirm("确认退回该笔贷款吗？",function(text){
                if(text) {
                    $.ajax({
                        url: "../api/account/loan/"+loanNo+"/cancel",
                        dataType: "json",
                        type: "GET",
                        contentType: "application/json",
                        success: function (json) {
                            if (json) {
                                $fallBack.parents("tr").remove();
                            }
                        }
                    });
                }
            });
        });

        $("#handle-table").on("click",".addTo",function(){
            var $addTo = $(this);
            var loanNo = $(this).parent().attr("loanNo");;
            bootbox.prompt("请输入转账流水号",function(text){
                if(text) {
                    $.ajax({
                        url: "../api/account/loan/"+loanNo+"/code/" + text,
                        dataType: "json",
                        type: "GET",
                        contentType: "application/json",
                        success: function (json) {
                            if(json) {
                                bootbox.alert("操作成功！",function(){
                                    $addTo.parents("tr").remove();
                                });
                            } else {
                                bootbox.alert("操作成功！");
                            }
                        },
                        error: function(){
                            bootbox.alert("请求异常！");
                        }
                    });
                }
            });
        });

        $("#handle-table").on("click",".takeBack",function(){
            var $takeBack = $(this);
            var loanNo = $(this).parent().attr("loanNo");
            bootbox.confirm("请确认是否已追回款项？",function(text){
                if(text) {
                    $.ajax({
                        url: "../api/account/loan/"+loanNo+"/takeback",
                        dataType: "json",
                        type: "GET",
                        contentType: "application/json",
                        success: function (json) {
                            if (json) {
                                $takeBack.parents("tr").remove();
                            }
                        }
                    });
                }
            });
        });
    };
    handle.init();
});