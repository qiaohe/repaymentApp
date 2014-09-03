/**
 * Created by Richard Xue on 14-5-19.
 */
(function($){
    var detail1 = {},
        imgPrefix = "../api/resources/idcard/",
        memberStatus = {
            "NORMAL":"0",
            "CLOSED":"1",
            "REJECTED":"2"
        },
        decisionStatus = {
            "A":"通过",
            "D":"拒绝"
        },
        gender = {
            "MALE": "男",
            "FEMALE": "女"
        };
    detail1.init = function(){
        detail1.bankMap = detail1.makeDict("BANK");
        detail1.industryMap = detail1.makeDict("INDUSTRY");
        detail1.educationMap = detail1.makeDict("EDUCATION");
        detail1.initData();
        detail1.initEvent();
    };
    detail1.makeDict = function (type) {
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
    detail1.initData = function() {
        var url = window.location.href, paramPrefix = "?memberNo=";
        var memberNo = detail1.memberNo = url.substring(url.indexOf(paramPrefix) + paramPrefix.length);
        $.ajax({
            url: "../api/members/"+memberNo+"/profile",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            success: function (json) {
                if (!json) {
                    return;
                }
                detail1.loadData(json);
            }
        });
    };
    detail1.initEvent = function(){
        var mleft = $(".account-content").css("margin-left");
        $("#idcardPositiveImg").css({"left":parseFloat(mleft.substr(0,mleft.length-2))+$("#user-info").width(),"top":88});
        $("#user-info").on("click","#idcard",function(){
            var $idcardPositiveImg = $("#idcardPositiveImg");
            if (!$idcardPositiveImg.attr("src")) {
                return;
            }
            if ($idcardPositiveImg.is(":visible")) {
                $idcardPositiveImg.hide();
            } else {
                $idcardPositiveImg.show();
            }
        });
    };
    detail1.loadData = function(json) {
        // 用户信息
        var member = json.member;
        var account = json.account;
        var idcard = member.idCard;
        var creditResult = member.creditResult;
        $("#memberId").text(member.id);
        $("#wechatNo").text(member.wcUserName);
        $("#name").text(idcard.name);
        $("#idcard").text(idcard.idNo);
        $("#idcardPositiveImg").attr("src",imgPrefix + idcard.imageFront);
        $("#gender").text(gender[idcard.sex]);
        $("#idcard-address").text(idcard.address);
        $("#birthday").text($.formatDate(idcard.birthday));
        $("#idcard-valid").text($.formatDate(idcard.validFrom)+"-"+$.formatDate(idcard.validThru));
        $("#profession").text(detail1.industryMap[member.industry]);
        $("#degree").text(detail1.educationMap[member.education]);
        $("#mobile").text(member.mobile);
        if(member.mobile) {
            $.ajax({
                url: "../api/dict/mobileArea/"+member.mobile,
                dataType: "json",
                type: "GET",
                contentType: "application/json",
                success: function (json) {
                    if (json) {
                        $("#mobileCity").text(json);
                    }
                }
            });
        }
        $("#block").text(member.blockCode);
        if(member.blockTime > 0) {
            $("#blockTime").text($.formatDate(member.blockTime));
        } else {
            $("#blockTime").text();
        }
        $("#email").text(member.email);
        if(memberStatus[member.status] && memberStatus[member.status] == "2") {
            $("#dishonored").text("Y");
        } else {
            $("#dishonored").text("N");
        }
        $("#register").text($.formatDate(member.createTime));
        $("#last-rating").text(creditResult.lastRating);
        $("#forecastAmt").text(member.preCrl);
        if(account) {
            $("#limitAmt").text(account.crl);
            $("#avlAmt").text(account.crlAvl);
            if(account.debit_amt) {
                $("#cashAmt").text(account.debit_amt.toFixed(2));
            } else {
                $("#cashAmt").text("0.00");
            }
        }
        // 申请记录
        var applications = json.applications;
        var appRecordHtml = "";
        if(applications) {
            $.each(applications,function(i,application){
                var decision = decisionStatus[application.decision];
                appRecordHtml += "<tr><td class=\"app-appNo\">"+application.appNo+"</td><td>"+ $.formatDate(application.applyDate)+"</td><td>"+(decision?decision:"")+"</td></tr>";
            });
        }
        $("#app-record").append(appRecordHtml);
        $("#app-record").on("click",".app-appNo",function(){
            var appNo = $.trim($(this).text());
            window.open("../appDetail.html?appNo="+appNo,"_blank");
        });
        // 信用卡
        var creditCards = member.creditCards;
        if(creditCards && creditCards.length != 0) {
            var creditCardHtml = "";
            $.each(creditCards,function(i,card){
                creditCardHtml += "<tr><td>"+detail1.bankMap[card.bank]+"</td><td>"+card.type+"</td><td>"+card.cardNo+"</td><td>"+(card.email?card.email:"")+"</td><td>"+(card.isValid?"Y":"N")+"</td></tr>";
            });
            $("#credit-table").append(creditCardHtml);
        }
        // 账单信息
        var creditCardBills = member.creditCardBills;
        if(creditCardBills && creditCardBills.length != 0) {
            var billHtml = "";
            $.each(creditCardBills,function(i,bill){
                billHtml += "<tr><td>"+bill.email+"</td><td>"+detail1.bankMap[bill.bank]+"</td><td>"+bill.crl+"</td><td>"+ $.formatDate(bill.cycleFrom)+"-"+$.formatDate(bill.cycleThru)+"</td><td>"+bill.amtRmb+"/"+bill.amtUsd+"</td><td>"+ $.formatDate(bill.createTime)+"</td></tr>";
            });
            $("#bill-table").append(billHtml);
        }
    };
    detail1.init();
})(jQuery);