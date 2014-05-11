(function ($) {
// START
    var appDetail = {},
        creditPath = "api/credit",
        imgPrefix = "",
        tvResult = {
            "0": "不通过",
            "1": "通过",
            "2": "未接通",
            "3": "非本人"
        },
        tvType = {
            "0": "未验证",
            "1": "微信验证",
            "2": "电话验证",
            "3": "无需验证"
        },
        tvDicision = {
            "0": "不通过",
            "1": "通过",
            "2": "系统"
        },
        tvPhoneType = {
            "1": "人行手机",
            "2": "人行公司电话",
            "3": "人行家庭电话",
            "4": "人行配偶电话",
            "5": "自填手机号码",
            "6": "114查询公司电话"
        },
        cardStatus = {
            "0": "未判断",
            "1": "有效",
            "2": "姓名不符",
            "3": "卡号不符",
            "4": "过期失效"
        },
        cardEmailStatus = {
            "0": "有效",
            "1": "失效"
        },
        repaymentType = {
            "AVERAGE_CAPITAL_INTEREST": "等额本息",
            "AVERAGE_CAPITAL": "等额本金",
            "INTEREST_PLUS_CAPITAL": "随本付息",
            "INTEREST_ONLY": "按期付息，到期还本"
        },
        userFlag = {
            "0": "新用户",
            "1": "老用户-前次退件",
            "2": "老用户-前次过件"
        };
    appDetail.init = function () {
        this.initEvent();
        // initialize data
        var url = window.location.href, paramPrefix = "?applyNo=";
        var applyNo = appDetail.applyNo = url.substring(url.indexOf(paramPrefix) + paramPrefix.length);
        $.get(creditPath + "/" + applyNo, function (json) {
            appDetail.loadData(json);
            appDetail.autoTvTelephone(1);
        });
        $.get("api/dict/reason", function (json) {
            var reasonMap = {};
            $.each(json, function (i, e) {
                reasonMap[e.key] = e.value;
            });
            appDetail.reasonMap = reasonMap;
        });
        appDetail.initTvData();
    };
    appDetail.initEvent = function () {
        // 工作资料
        $("#switch-work-res").find(".switch-res-item:visible").each(function (i, e) {
            $(e).click(function () {
                if (i == 1) {
                    $("#pension-record").show();
                    $("#fund-record").hide();
                    $("#job-info").hide();
                } else if (i == 2) {
                    $("#pension-record").hide();
                    $("#fund-record").show();
                    $("#job-info").hide();
                } else {
                    $("#pension-record").hide();
                    $("#fund-record").hide();
                    $("#job-info").show();
                }
            });
        });
        // 逾期信息
        $("#switch-delay-res").find(".switch-res-item:visible").each(function (i, e) {
            $(e).click(function () {
                if (i == 1) {
                    $("#delay-res-0").hide();
                    $("#delay-res-1").show();
                    $("#delay-res-2").hide();
                } else if (i == 2) {
                    $("#delay-res-0").hide();
                    $("#delay-res-1").hide();
                    $("#delay-res-2").show();
                } else {
                    $("#delay-res-0").show();
                    $("#delay-res-1").hide();
                    $("#delay-res-2").hide();
                }
            });
        });
        // 信息汇总
        $("#switch-info-summary").find(".switch-res-item:visible").each(function (i, e) {
            $(e).click(function () {
                if (i == 1) {
                    $("#info-summary-0").hide();
                    $("#info-summary-1").show();
                    $("#info-summary-2").hide();
                } else if (i == 2) {
                    $("#info-summary-0").hide();
                    $("#info-summary-1").hide();
                    $("#info-summary-2").show();
                } else {
                    $("#info-summary-0").show();
                    $("#info-summary-1").hide();
                    $("#info-summary-2").hide();
                }
            });
        });
        $("#phoneType").on("change", function () {
            var type = $(this).val();
            if (type == 6) {
                $("#tv-phone").removeAttr("readonly").removeClass("grey-input").val("");
            } else {
                $("#tv-phone").attr("readonly", "readonly").addClass("grey-input");
                appDetail.autoTvTelephone(type);
            }
        });
        // 照会完成
        $("#doTv").on("click", function () {
            var tvData = {
                appNo: appDetail.applyNo,
                type: parseInt($("#tvType").val(), 10),
                telephoneType: parseInt($("#phoneType").val(), 10),
                telephoneNo: $("#tv-phone").val(),
                question: $("#creditor-question").val(),
                decision: parseInt($("#tvDecision").val(), 10)
//                createTime:$("#tv-create-time").val()
            };
            $.ajax({
                url: creditPath + "/telephoneTV",
                data: JSON.stringify(tvData),
                dataType: "json",
                type: "POST",
                contentType: "application/json",
                success: function (json) {
                    appDetail.addTv(json);
                }
            });
        });
        // 完成
        $("#sumbit").on("click", function () {
            appDetail.approveInfo("7");
        });
        // 暂存
        $("#save").on("click", function () {
            appDetail.approveInfo("99");
        });
    };
    appDetail.autoTvTelephone = function (type) {
        $.get("api/pboc/" + appDetail.idcard + "/phone/" + type, function (json) {
            $("#tv-phone").val(json);
        });
    };
    appDetail.addTv = function (e) {
        var contentHtml = '<tr><td>' + tvPhoneType[e.telephoneType] + '</td><td>' + e.telephoneNo + '</td><td>' + e.question + '</td><td>' + tvResult[e.decision] + '</td><td>' + $.formatDate(e.createTime) + '</td></tr>';
        $("#tv-list-table").append(contentHtml);
    };
    appDetail.initTvData = function () {
        $.get(creditPath + "/telephoneTV/" + appDetail.applyNo, function (json) {
            var contentHtml = "";
            $.each(json, function (i, e) {
                contentHtml += '<tr><td>' + tvPhoneType[e.telephoneType] + '</td><td>' + e.telephoneNo + '</td><td>' + e.question + '</td><td>' + tvResult[e.decision] + '</td><td>' + $.formatDate(e.createTime) + '</td></tr>';
            });
            $("#tv-list-table").append(contentHtml);
        });
    };
    appDetail.approveInfo = function (status) {
        var decision = $("#applyResult").val();
        var reason1 = $("#credit-reasion-1").val();
        var reason2 = $("#credit-reasion-2").val();
        var reason3 = $("#credit-reasion-3").val();
        if (reason1 && (reason1.charAt(0) != decision || !appDetail.reasonMap[reason1])) {
            alert("征信原因码一错误");
            return;
        }
        if (reason2 && (reason2.charAt(0) != decision || !appDetail.reasonMap[reason2])) {
            alert("征信原因码二错误");
            return;
        }
        if (reason2 && (reason3.charAt(0) != decision || !appDetail.reasonMap[reason3])) {
            alert("征信原因码三错误");
            return;
        }
        var approveData = {
            amt: $("#borrowing-amount").val(),
            sugCrl: $("#apply-amount").val(),
            apr: parseInt($("#year-rate").val(), 10) / 100,
            term: $("#borrowing-limit").val(),
            decision: $("#applyResult").val(),
            reason1: reason1,
            reason2: reason2,
            reason3: reason3,
            opinion: $("#credit-opinion").val(),
            profile: $("#customer-desc").val(),
            creditor: $("#creditor").val()
//            status:status
        };
        $.ajax({
            url: creditPath + "/approve/" + appDetail.applyNo,
            data: JSON.stringify(approveData),
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            success: function (json) {
                alert("success");
            }
        });
    };
    appDetail.loadData = function (json) {
        var member = json.member;
        // 会员信息
        $("#memberNo").val(member.id);
        $("#username").val(member.userName);
        $("#memberMobile").val(member.mobile);
        $("#email").val(member.email);
        $("#wechatNo").val(member.wcNo);
        $("#wechatName").val(member.wcUserName);
        $("#wechatProvince").val(member.wcProvince);
        $("#wechatCity").val(member.wcProvince);
        $("#blockCode").val(member.blockCode);
//        $("#blockTime").val($.formatDate(member.blockTime));
        $("#memberCreateTime").val(member.blockCode);

        var idCard = member.idCard;
        appDetail.idcard = idCard.idNo;
        // 身份证信息
        $("#idcard").val(idCard.idNo);
        $("#name").val(idCard.name);
        $("#gender").val(idCard.sex);
        $("#birthday").val(idCard.birthday);
        $("#nation").val(idCard.nationality);
        $("#address").val(idCard.address);
        $("#validityStart").val($.formatDate(idCard.validFrom));
        $("#validityEnd").val($.formatDate(idCard.validThru));
        $("#idcardCreateTime").val($.formatDate(idCard.createTime));

        $("#idcardPositiveImg").attr("src", imgPrefix + idCard.imageFront);
        $("#idcardNegativeImg").attr("src", imgPrefix + idCard.imageBack);
        // 信用卡信息
        appDetail.loadCredits(member.creditCards);
        // 账单信息
        appDetail.loadBills(member.creditCardBills);

        var application = json.application;
        // 申请基本信息
        $("#applNo").val(application.applicationNo);
        $("#existingFlag").val(userFlag[application.existingFlag]);
        $("#applAmount").val(application.amt);
        $("#applLimit").val(application.term);
        $("#applType").val(repaymentType[application.repayType]);
        $("#applNote").val(application.title);
        $("#applCreateTime").val($.formatDate(application.createTime));
        $("#appTime").val($.formatDate(application.applyTime));

        var pboc = json.pboc;
        // 基础资料
        $("#marriage").val(pboc.maritalState);
        $("#mobile").val(pboc.moblie);
        $("#num-district").val(pboc.pbocmobileCity);
        $("#phone-at-work").val(pboc.officeTelephoneNo);
        $("#phone-home").val(pboc.officeTelephoneNo);
        $("#education").val(pboc.eduDegree);
        $("#address-conflict").val(pboc.registeredAddress);
        $("#live-city").val(pboc.homeCity);
        $("#live-address").val(pboc.homeAddress);
        $("#live-status").val(pboc.homeResidenceType);
        $("#update-date").val(pboc.addressGetTime);
        $("#mate-name").val(pboc.partnerName);
        $("#mate-id").val(pboc.partnerCertNo);
        $("#mate-phone").val(pboc.partnerTelephoneNo);
        // 工作资料
        // 工作信息
        $("#work-at").val(pboc.employer);
        $("#profession").val(pboc.employerCity);
        $("#industry").val(pboc.industry);
        $("#industry-catography").val(pboc.industryAddress);
        $("#work-address").val(pboc.employerAddress);
        $("#work-city").val(pboc.employerCity);
        $("#position").val(pboc.occupation);
        $("#level").val(pboc.title);
        $("#since").val(pboc.startYear);
        $("#working-age").val(pboc.industryYear);
        $("#works-since").val(pboc.hisGetting);
        $("#update-date-1").val(pboc.employerGetTime);
        // 养老金缴纳记录
        if (pboc.yGetTime) {
            $("#pension-date").val(pboc.yRegisterDate);
            $("#month-in-work").val(pboc.yWorkDate);
            $("#base").val(pboc.yOwnBasicMoney);
            $("#pension-state").val(pboc.yState);
            $("#amount-each-2").val(pboc.yMoney);
            $("#corp-conflict-2").val(pboc.yOrganName);
            $("#reason-suspension").val(pboc.pauseReason);
            $("#update-date-2").val($.formatDate(pboc.yGetTime));
        } else {
            $("#pension-record").hide();
            $("#switch-work-res").find(".switch-res-item:eq(1)").hide().end().css({'width': '18%'});
        }
        // 公积金缴纳记录
        if (pboc.getTime) {
            $("#fund-date").val(pboc.registerDate);
            $("#fund-since").val(pboc.firstMonth);
            $("#fund-to").val(pboc.toMonth);
            $("#fund-state").val(pboc.state);
            $("#amount-each-3").val(pboc.pay);
            $("#corp-conflict-3").val(pboc.organName);
            $("#update-date-3").val($.formatDate(pboc.getTime));
        } else {
            $("#fund-record").hide();
            $("#switch-work-res").find(".switch-res-item:eq(2)").hide().prev().css({"border-right": 'none'}).end().end().css({'width': '18%'});
        }

        // 银行资料
        $("#earlist-credit").val(pboc.firstLoanMonth);
        // 逾期信息-信用卡
        $("#account-num").val(pboc.cardCount);
        $("#amount-delayed-2").val(pboc.cardHighestOverdueAmountPerMon);
        $("#delayed-month").val(pboc.cardMaxDuration);
        $("#delayed-current").val(pboc.cardOverDuePerYear);
        $("#delayed-times").val(pboc.cardOverDueNum);
        // 逾期信息-贷款
        $("#num-delayed").val(pboc.loanCount);
        $("#amount-delayed").val(pboc.loanHighestOverdueAmountPerMon);
        $("#time-delayed").val(pboc.loanMaxDuration);
        // 逾期信息-准贷记卡
        $("#account-num-3").val(pboc.semiCardCount);
        $("#overdraft-amount").val(pboc.semiCardHighestOverdueAmountPerMon);
        $("#overdraft-time").val(pboc.semiCardMaxDuration);
        // 信用卡信息汇总
        $("#num-corp").val(pboc.cardOrg);
        $("#num-accounts").val(pboc.cardAccountCount);
        $("#amount-credit").val(pboc.cardCreditLimit);
        $("#credit-average").val(pboc.cardAvgCreditLimit);
        $("#credit-max").val(pboc.cardMaxCreditLimitPerOrg);
        $("#credit-min").val(pboc.cardMinCreditLimitPerOrg);
        $("#credit-used").val(pboc.cardUsedCreditLimit);
        $("#used-average").val(pboc.cardLatest6MonthUsedAvgAmount);
        // 贷款信息汇总
        $("#num-loan").val(pboc.loanAccountCount);
        $("#amount-loan").val(pboc.loanCreditLimit);
        $("#amount-remaining").val(pboc.loanBalance);
        $("#payback-average").val(pboc.loanLatest6MonthUsedAvgAmount);
        // 准贷记卡信息汇总
        $("#num-corp-3").val(pboc.semiCardOrg);
        $("#num-accounts-3").val(pboc.semiCardAccountCount);
        $("#amount-credit-3").val(pboc.semicardCreditLimit);
        $("#credit-average-3").val(pboc.semiCardAvgCreditlimit);
        $("#credit-max-3").val(pboc.semiCardMaxCreditLimitPerOrg);
        $("#credit-min-3").val(pboc.semicardMinCreditLimitPerOrg);
        $("#overdraft-remaining").val(pboc.semiCardUsedCreditLimit);
        $("#overdraft-average").val(pboc.semiCardLatest6MonthUsedAvgAmount);
        // 人行查询次数
        $("#search-time").val(pboc.cardQueryLatest6Month);

        // 账务信息
        $("#loan-times").val();
        $("#loan-amount").val();
        $("#delay-days").val();
        $("#max-delay-days").val();
        $("#loan-status").val();
        $("#normal-times").val();
        $("#delay-times").val();
        $("#before-delay-times").val();
        $("#close-times").val();
        $("#stay-times").val();
        $("#wait-times").val();

        // 风险提示
        var aScore = application.aScore;
        var riskRemind = aScore.riskRemind;
        $("#riskRemind").find("input").each(function (i, e) {
            if (riskRemind.charAt(i) == "1") {
                $(e).prop("checked", true);
            }
        });
        // 前次申请情况
        var creditResult = member.creditResult;
        if (creditResult) {
            $("#last-appl-no").val(creditResult.lastApplicationNo);
            $("#last-credit-score").val(creditResult.lastScore);
            $("#last-credit-level").val(creditResult.lastRating);
            $("#last-credit-result").val(creditResult.lastDecision);
            $("#last-credit-reason1").val(creditResult.lastReason1);
            $("#last-credit-reason2").val(creditResult.lastReason2);
            $("#last-credit-reason3").val(creditResult.lastReason3);
            $("#last-report-time").val(creditResult.lastPbocBackTime);
            $("#last-create-time").val($.formatDate(creditResult.createTime));
        }
        // 信用评分
        $("#credit-score").val(aScore.score);
        $("#credit-level").val(aScore.rating);
        // 微信照会
        var telephoneVerification = application.telephoneVerification;
        $("#tv-type").val(tvType[telephoneVerification.type]);
        $("#live-address-question").val(telephoneVerification.tvQues1);
        $("#answer1").val(telephoneVerification.tvMemAns1);
        $("#work-address-question").val(telephoneVerification.tvQues2);
        $("#answer2").val(telephoneVerification.tvMemAns2);
        $("#result-table").val(tvDicision[telephoneVerification.decision]);
        $("#wc-create-time").val($.formatDate(telephoneVerification.createTime));
        // 电话照会
        // 审核
        var approval = application.approval;
        $("#borrowing-amount").val(approval.amt);
        $("#bank-amount").val(pboc.rh_crl);
        $("#apply-amount").val(approval.sugCrl);
        $("#forecast-amount").val(member.preCrl);
        $("#year-rate").val(parseFloat(approval.apr, 10) * 100);
        $("#borrowing-limit").val(approval.term);
        $("#applyResult").val(approval.decision);
        $("#credit-reasion-1").val(approval.reason1);
        $("#credit-reasion-2").val(approval.reason2);
        $("#credit-reasion-3").val(approval.reason3);
        $("#customer-desc").val(approval.profile);
        $("#credit-opinion").val(approval.opinion);
        $("#creditor").val(approval.creditor);
    };
    appDetail.loadCredits = function (credits) {
        var contentHtml = "";
        $.each(credits, function (i, creditCard) {
            contentHtml += '<div class="itemDiv" style="height: 70px;">\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">银行</div>\n        ' +
                '<div class="grid-2"><input type="text" name="bank" readonly="readonly" value="' + creditCard.bank + '"></div>\n        ' +
                '<div class="grid-2">卡号</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardNo" readonly="readonly" style="width: 300px;" value="' + creditCard.cardNo + '"></div>\n        ' +
                '<div class="grid-2">卡类型</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardType" readonly="readonly" value="' + creditCard.type + '"></div>\n        ' +
                '<div class="grid-2">卡片状态</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardStatus" readonly="readonly" value="' + creditCard.status + '"></div>\n    ' +
                '</div>\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">电子邮箱</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardStatus" readonly="readonly" style="width: 300px;" value="' + creditCard.email + '"></div>\n        ' +
                '<div class="grid-2">账单邮箱状态</div>\n        ' +
                '<div class="grid-2"><input type="text" name="billEmailStatus" readonly="readonly" value="' + cardEmailStatus[creditCard.status] + '"></div>\n        ' +
                '<div class="grid-2">创建时间</div>\n        ' +
                '<div class="grid-2"><input type="text" name="creditCreateTime" readonly="readonly" value="' + $.formatDate(creditCard.createTime) + '"></div>\n    ' +
                '</div>\n' +
                '</div>';
        });
        $("#creditInfoList").after(contentHtml);
    };
    appDetail.loadBills = function (bills) {
        if (!bills) {
            return;
        }
        var contentHtml = "";
        $.each(bills, function (i, bill) {
            contentHtml += '<div class="cardOrder">账单1</div>\n' +
                '<input type="button" name="billImgBtn" style="margin:5px auto 5px 50px" value="账单影像">\n' +
                '<img src="' + bill.image + '" id="billImg" style="position:absolute; top:5px; left:40%; display:none">\n\n' +
                '<div class="itemDiv" style="height: 105px;">\n    <div class="single-item">\n        ' +
                '<div class="grid-2">银行</div>\n        ' +
                '<div class="grid-2"><input type="text" name="bank" readonly="readonly" style="width: 300px;" value="' + bill.bank + '"></div>\n        ' +
                '<div class="grid-2">固定额度</div>\n        ' +
                '<div class="grid-2"><input type="text" name="fixedQuota" readonly="readonly" value="' + bill.crl + '"></div>\n        ' +
                '<div class="grid-2">最后还款日</div>\n        ' +
                '<div class="grid-2"><input type="text" name="lastRepayDate" readonly="readonly" value="' + bill.payDue + '"></div>\n        ' +
                '<div class="grid-2">人民币应还款额</div>\n        ' +
                '<div class="grid-2"><input type="text" name="rmbRepay" readonly="readonly" value="' + bill.amtRmb + '"></div>\n    </div>\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">美元应还款额</div>\n        ' +
                '<div class="grid-2"><input type="text" name="dollarRepay" readonly="readonly" value="' + bill.amtUsd + '"></div>\n        ' +
                '<div class="grid-2">账单周期开始日</div>\n       ' +
                '<div class="grid-2"><input type="text" name="billCycleStart" readonly="readonly" value="' + bill.cycleFrom + '"></div>\n        ' +
                '<div class="grid-2">账单周期结束日</div>\n        ' +
                '<div class="grid-2"><input type="text" name="billCycleEnd" readonly="readonly" value="' + bill.cycleThru + '"></div>\n    </div>\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">电子邮箱</div>\n        ' +
                '<div class="grid-2"><input type="text" name="billEmail" readonly="readonly" style="width: 300px;" value="' + bill.email + '"></div>\n        ' +
                '<div class="grid-2">创建时间</div>\n       ' +
                '<div class="grid-2"><input type="text" name="billCreateTime" readonly="readonly" value="' + $.formatDate(bill.createTime) + '"></div>\n    ' +
                '</div>\n</div>';
        });
        $("#billInfoList").after(contentHtml);
    };
    // initialize
    appDetail.init();
// END
})(jQuery);