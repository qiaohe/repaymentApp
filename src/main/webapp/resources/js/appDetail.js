(function ($) {
// START
    var appDetail = {},
        creditPath = "api/credit",
        imgPrefix = "api/resources/idcard/",
        pdfPrefix = "api/resources/pboc/",
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
        },
        conflictMap = {
            "1": "不一致",
            "0": "一致"
        },
        loanStatus = {
            "0": "正常",
            "1": "逾期",
            "2": "打呆",
            "8": "等待放款",
            "9": "结清",
            "10": "放款失败",
            "null": ""
        },
        marriageStatus = { // 婚姻状况
            "1": "已婚",
            "2": "未婚",
            "3": "离异",
            "4": "未知"
        },
        homeStatus = { // 居住状况
            "1": "自置",
            "2": "租住",
            "3": "未知"
        },
        duty = { // 职务
            "1": "高级领导",
            "2": "中级领导",
            "3": "一般员工",
            "4": "其他"
        },
        dutyTitle = { // 职称
            "1": "高级职称",
            "3": "中级职称",
            "3": "初级职称"
        },
        yStatus = {
            "1": "当月正常",
            "2": "历史正常",
            "3": "停缴",
            "4": "其他"
        },
        gender = {
            "MALE": "男",
            "FEMALE": "女"
        };
    appDetail.init = function () {
        this.initEvent();
        appDetail.maxApplyAmt = 50000;
        // initialize dictionary
        appDetail.educationMap = appDetail.makeDict("EDUCATION");
        appDetail.industryMap = appDetail.makeDict("INDUSTRY");
        appDetail.bankMap = appDetail.makeDict("BANK");
        appDetail.reasonMap = appDetail.makeDict("reason");
        // initialize data
        var url = window.location.href, paramPrefix = "?appNo=";
        var appNo = appDetail.appNo = url.substring(url.indexOf(paramPrefix) + paramPrefix.length);
        $.get(creditPath + "/" + appNo, function (json) {
            appDetail.loadData(json);
            appDetail.autoTvTelephone(1);
        });
        appDetail.initTvData();
    };
    appDetail.makeDict = function (type) {
        var map = {};
        $.ajax({
            url: "api/dict/" + type,
            dataType: "json",
            type: "GET",
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (!json) return;
                $.each(json, function (i, dict) {
                    map[dict.key] = dict.value;
                });
            }
        });
        return map;
    };
    appDetail.initEvent = function () {
        // show idcard image
        $("#idcardPositive").click(function () {
            var $idcardPositiveImg = $("#idcardPositiveImg");
            if (!$idcardPositiveImg) {
                return;
            }
            if ($idcardPositiveImg.is(":visible")) {
                $idcardPositiveImg.hide();
            } else {
                $idcardPositiveImg.show();
            }
        });
        $("#idcardNegative").click(function () {
            var $idcardNegativeImg = $("#idcardNegativeImg");
            if (!$idcardNegativeImg) {
                return;
            }
            if ($idcardNegativeImg.is(":visible")) {
                $idcardNegativeImg.hide();
            } else {
                $idcardNegativeImg.show();
            }
        });
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
        // 人行信息pdf Button
        $("#rhBaseInfo,#rhWorkInfo,#rhResInfo").click(function () {
            window.open(pdfPrefix + appDetail.idcard + ".pdf", "_blank");
        });
        // 贷款信息
        // 0-正常 1-逾期 2-打呆 8-等待放款 9-结清
        $("#normal-times").click(function () {
            appDetail.showLoansByStatus('0');
        });
        $("#delay-times").click(function () {
            appDetail.showLoansByStatus('1');
        });
        $("#before-delay-times").click(function () {
            appDetail.showLoansByStatus('once');
        });
        $("#close-times").click(function () {
            appDetail.showLoansByStatus('9');
        });
        $("#stay-times").click(function () {
            appDetail.showLoansByStatus('2');
        });
        $("#wait-times").click(function () {
            appDetail.showLoansByStatus('8');
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
            if(!$.trim($("#tv-phone").val()) || !$.trim($("#creditor-question").val())) {
                alert("照会信息错误，请重新填写！");
                return;
            }
            if($("#tvDecision").val() == "1" && $.trim($("#creditor-question").val()) == "") {
                alert("请填写征信员问题！");
                return;
            }
            if($("#phoneType").val() == '6' && $("#tvDecision").val() == '1' && $.trim($("#tv-phone").val()) == '') {
                alert("请填写电话号码！");
                return;
            }
            var tvData = {
                appNo: appDetail.appNo,
                type: parseInt($("#tvType").val(), 10),
                telephoneType: parseInt($("#phoneType").val(), 10),
                telephoneNo: $("#tv-phone").val(),
                question: $("#creditor-question").val(),
                decision: parseInt($("#tvDecision").val(), 10)
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
        $("#submit").on("click", function () {
            var applyAmtStr = $.trim($("#apply-amount").val());
            var borrowingAmtStr = $.trim($("#borrowing-amount").val());
            if(applyAmtStr == "" || borrowingAmtStr == "") {
                alert("核准借款金额和审批额度不能为空！");
                return;
            }
            var applyAmt = parseFloat(applyAmtStr);
            if(applyAmt > appDetail.maxApplyAmt) {
                alert("审批额度不能大于"+appDetail.maxApplyAmt+"！");
                return;
            }
            var borrowingAmt = parseFloat(borrowingAmtStr);
            if(borrowingAmt > applyAmt) {
                alert("核准借款金额不能大于审批额度！");
                return;
            }
            var accountAvlStr = $("#account-avl-amt");
            if (appDetail.existingFlag == 2 && parseFloat(accountAvlStr) < borrowingAmt) {
                alert("审批额度不能大于'账务信息'的可用额度！");
                return;
            }
            var applyResult = $("#applyResult").val();
            if(applyResult == "") {
                alert("请选择审核结果！");
                return;
            }
            var reason1 = $.trim($("#credit-reasion-1").val());
            var reason2 = $.trim($("#credit-reasion-2").val());
            var reason3 = $.trim($("#credit-reasion-3").val());
            if(reason1 == "") {
                alert("请输入征信原因码一！");
                return;
            }
            if (reason1.charAt(0) != applyResult || !appDetail.reasonMap[reason1]) {
                alert("征信原因码一错误！");
                return;
            }
            if (reason2 && (reason2.charAt(0) != applyResult || !appDetail.reasonMap[reason2])) {
                alert("征信原因码二错误！");
                return;
            }
            if (reason3 && (reason3.charAt(0) != applyResult || !appDetail.reasonMap[reason3])) {
                alert("征信原因码三错误！");
                return;
            }
            if($.trim($("#credit-opinion").val()) == "") {
                alert("请填写征信意见！");
                return;
            }
            appDetail.readApproveCont();
            $("#doTv").prop("disabled",true);
            $("#operationDiv").children().prop("disabled",true);
            appDetail.approveInfo("5");
        });
        // 暂存
        $("#save").on("click", function () {
            appDetail.readApproveCont();
            $("#doTv").prop("disabled",true);
            $("#operationDiv").children().prop("disabled",true);
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
        $.get(creditPath + "/telephoneTV/" + appDetail.appNo, function (json) {
            var contentHtml = "";
            $.each(json, function (i, e) {
                contentHtml += '<tr><td>' + tvPhoneType[e.telephoneType] + '</td><td>' + e.telephoneNo + '</td><td>' + e.question + '</td><td>' + tvResult[e.decision] + '</td><td>' + $.formatDate(e.createTime) + '</td></tr>';
            });
            $("#tv-list-table").append(contentHtml);
        });
    };
    appDetail.approveInfo = function (status) {
        var reason1 = $.trim($("#credit-reasion-1").val());
        var reason2 = $.trim($("#credit-reasion-2").val());
        var reason3 = $.trim($("#credit-reasion-3").val());
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
            creditor: $("#creditor").val(),
            status:status
        };
        $.ajax({
            url: creditPath + "/approve/" + appDetail.appNo,
            data: JSON.stringify(approveData),
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            success: function (json) {
                alert("提交成功！");
                var appNos = $.cookie("appNos");
                appNos = new RegExp(appDetail.appNo+",").test(appNos) ? appNos.replace(appDetail.appNo+",","") : appNos.replace(appDetail.appNo,"");
                if($.trim(appNos)) {
                    $.cookie("appNos",appNos);
                    window.location.href = "appDetail.html?appNo="+appNos.split(",")[0];
                } else {
                    window.location.href = "appSummary.html";
                }
            },
            error:function() {
                alert("提交失败，请重新提交！");
                $("#doTv").prop("disabled",false);
                $("#operationDiv").children().prop("disabled",false);
                appDetail.reverseApproveCont();
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
        $("#blockTime").val($.formatDate(member.blockTime));
        $("#memberCreateTime").val($.formatDate(member.createTime));
        if(member.mobile) {
            $.ajax({
                url: "api/dict/mobileArea/"+member.mobile,
                dataType: "json",
                type: "GET",
                contentType: "application/json",
                success: function (json) {
                    if (json) {
                        $("#mobileCity").val(json);
                    }
                }
            });
        }

        var idCard = member.idCard;
        appDetail.idcard = idCard.idNo;
        // 身份证信息
        $("#idcard").val(idCard.idNo);
        $("#name").val(idCard.name);
        $("#gender").val(gender[idCard.sex]);
        $("#birthday").val($.formatDate(idCard.birthday));
        $("#nation").val(idCard.nationality);
        $("#address").val(idCard.address);
        $("#validityStart").val($.formatDate(idCard.validFrom));
        $("#validityEnd").val($.formatDate(idCard.validThru));
        $("#idcardCreateTime").val($.formatDate(idCard.createTime));

        if (idCard.imageFront) {
            $("#idcardPositiveImg").attr("src", imgPrefix + idCard.imageFront);
        }
        if (idCard.imageBack) {
            $("#idcardNegativeImg").attr("src", imgPrefix + idCard.imageBack);
        }
        // 信用卡信息
        appDetail.loadCredits(member.creditCards);
        // 账单信息
        appDetail.loadBills(member.creditCardBills);

        var application = json.application;
        // 申请基本信息
        $("#applNo").val(application.applicationNo);
        appDetail.existingFlag = application.existingFlag; // 用户状态
        $("#existingFlag").val(userFlag[application.existingFlag]);
        $("#applAmount").val(application.amt);
        $("#applLimit").val(application.term);
        $("#applType").val(repaymentType[application.repayType]);
        $("#applNote").val(application.title);
        $("#applCreateTime").val($.formatDate(application.createTime));
        $("#appTime").val($.formatDate(application.applyTime));

        var pboc = json.pboc;
        // 基础资料
        $("#marriage").val(marriageStatus[pboc.maritalState]);
        $("#mobile").val(pboc.mobile);
        $("#num-district").val(pboc.mobileCity);
        $("#phone-at-work").val(pboc.officeTelephoneNo);
        $("#phone-home").val(pboc.homeTelephoneNo);
        $("#education").val(appDetail.educationMap[pboc.eduDegree]);
        $("#address-conflict").val(conflictMap[pboc.registeredAddress]);
        $("#live-city").val(pboc.homeCity);
        $("#live-address").val(pboc.homeAddress).attr("title", pboc.homeAddress);
        $("#live-status").val(homeStatus[pboc.homeResidenceType]);
        $("#update-date").val(pboc.addressGetTime);
        $("#mate-name").val(pboc.partnerName);
        $("#mate-id").val(pboc.partnerCertNo);
        $("#mate-phone").val(pboc.partnerTelephoneNo);
        // 工作资料
        // 工作信息
        $("#work-at").val(pboc.employer);
        $("#profession").val(pboc.occupation);
        $("#industry").val(pboc.industry);
        $("#industry-catography").val(appDetail.industryMap[pboc.industryAddress]);
        $("#work-address").val(pboc.employerAddress).attr("title", pboc.employerAddress);
        $("#work-city").val(pboc.employerCity);
        $("#position").val(duty[pboc.duty]);
        $("#level").val(dutyTitle[pboc.title]);
        $("#since").val(pboc.startYear);
        $("#working-age").val(pboc.industryYear);
        $("#works-since").val(pboc.hisGetting);
        $("#update-date-1").val(pboc.employerGetTime);
        // 养老金缴纳记录
        if (pboc.yGetTime) {
            $("#pension-date").val(pboc.yRegisterDate);
            $("#month-in-work").val(pboc.yWorkDate);
            $("#base").val(pboc.yOwnBasicMoney);
            $("#pension-state").val(yStatus[pboc.yState]);
            $("#amount-each-2").val(pboc.yMoney);
            $("#corp-conflict-2").val(conflictMap[pboc.yOrganName]);
            $("#reason-suspension").val(pboc.pauseReason);
            $("#update-date-2").val(pboc.yGetTime);
        } else {
            $("#pension-record").hide();
            $("#switch-work-res").find(".switch-res-item:eq(1)").hide().end().css({'width': 250});
        }
        // 公积金缴纳记录
        if (pboc.getTime) {
            $("#fund-date").val(pboc.registerDate);
            $("#fund-since").val(pboc.firstMonth);
            $("#fund-to").val(pboc.toMonth);
            $("#fund-state").val(yStatus[pboc.state]);
            $("#amount-each-3").val(pboc.pay);
            $("#corp-conflict-3").val(conflictMap[pboc.organName]);
            $("#update-date-3").val(pboc.getTime);
        } else {
            $("#fund-record").hide();
            $("#switch-work-res").find(".switch-res-item:eq(2)").hide().end().css({'width': $("#switch-work-res").width() - 150});
            $("#switch-work-res").find(".switch-res-item:visible:last").css({"border-right": "none"});
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
        if (pboc.loanCount) {
            $("#num-delayed").val(pboc.loanCount);
            $("#amount-delayed").val(pboc.loanHighestOverdueAmountPerMon);
            $("#time-delayed").val(pboc.loanMaxDuration);
        } else {
            $("#delay-res-1").hide();
            $("#switch-delay-res").find(".switch-res-item:eq(1)").hide().end().css({'width': 180});
        }
        // 逾期信息-准贷记卡
        if (pboc.semiCardCount) {
            $("#account-num-3").val(pboc.semiCardCount);
            $("#overdraft-amount").val(pboc.semiCardHighestOverdueAmountPerMon);
            $("#overdraft-time").val(pboc.semiCardMaxDuration);
        } else {
            $("#delay-res-2").hide();
            $("#switch-delay-res").find(".switch-res-item:eq(2)").hide().end().css({'width': $("#switch-delay-res").width() - 100});
            $("#switch-delay-res").find(".switch-res-item:visible:last").css({"border-right": "none"});
        }

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
        if (pboc.loanAccountCount) {
            $("#num-loan").val(pboc.loanAccountCount);
            $("#amount-loan").val(pboc.loanCreditLimit);
            $("#amount-remaining").val(pboc.loanBalance);
            $("#payback-average").val(pboc.loanLatest6MonthUsedAvgAmount);
        } else {
            $("#delay-res-1").hide();
            $("#switch-info-summary").find(".switch-res-item:eq(1)").hide().end().css({'width': 320});
        }
        // 准贷记卡信息汇总
        if (pboc.semiCardAccountCount) {
            $("#num-corp-3").val(pboc.semiCardOrg);
            $("#num-accounts-3").val(pboc.semiCardAccountCount);
            $("#amount-credit-3").val(pboc.semicardCreditLimit);
            $("#credit-average-3").val(pboc.semiCardAvgCreditlimit);
            $("#credit-max-3").val(pboc.semiCardMaxCreditLimitPerOrg);
            $("#credit-min-3").val(pboc.semicardMinCreditLimitPerOrg);
            $("#overdraft-remaining").val(pboc.semiCardUsedCreditLimit);
            $("#overdraft-average").val(pboc.semiCardLatest6MonthUsedAvgAmount);
        } else {
            $("#delay-res-2").hide();
            $("#switch-info-summary").find(".switch-res-item:eq(2)").hide().end().css({'width': $("#switch-info-summary").width() - 170});
            $("#switch-info-summary").find(".switch-res-item:visible:last").css({"border-right": "none"});
        }
        // 人行查询次数
        $("#search-time").val(pboc.cardQueryLatest6Month);

        // 贷款信息
        appDetail.makeLoansMap(json.loans.loans);
        appDetail.loadLoansSummary(json.loans);
        appDetail.loadLoans();

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
            $("#last-appl-no").val(creditResult.applicationNo);
            if (creditResult.lastScore) {
                $("#last-credit-score").val(new String(creditResult.lastScore).substring(0, 6));
            }
            $("#last-credit-level").val(creditResult.lastRating);
            $("#last-credit-result").val(creditResult.lastDecision);
            $("#last-credit-reason1").val(creditResult.lastReason1);
            $("#last-credit-reason2").val(creditResult.lastReason2);
            $("#last-credit-reason3").val(creditResult.lastReason3);
            if (creditResult.lastPbocBackTime > 0) {
                $("#last-report-time").val($.formatDate(creditResult.lastPbocBackTime));
            }
            $("#last-create-time").val($.formatDate(creditResult.createTime));
        }
        // 信用评分
        if (aScore.score) {
            $("#credit-score").val(new String(aScore.score).substring(0, 6));
        }
        $("#credit-level").val(aScore.rating);
        // 微信照会
        var telephoneVerification = application.telephoneVerification;
        $("#tv-type").val(tvType[telephoneVerification.type]);
        $("#live-address-question").val(telephoneVerification.tvQues1).attr("title", telephoneVerification.tvQues1);
        $("#answer1").val(telephoneVerification.tvMemAns1).attr("title", telephoneVerification.tvMemAns1);
        $("#work-address-question").val(telephoneVerification.tvQues2).attr("title", telephoneVerification.tvQues2);
        $("#answer2").val(telephoneVerification.tvMemAns2).attr("title", telephoneVerification.tvMemAns2);
        $("#result-table").val(tvDicision[telephoneVerification.decision]);
        $("#wc-create-time").val($.formatDate(telephoneVerification.createTime));
        // 电话照会

        // 账务信息
        var account = json.account;
        if(account) {
            $("#account-all-amt").val(account.crl);
            $("#account-avl-amt").val(account.crlAvl);
            $("#account-used-amt").val(account.crlUsed);
        }
        // 审核
        var approval = application.approval;
        $("#borrowing-amount").val(approval.amt);
        $("#bank-amount").val(pboc.rh_crl);
        $("#apply-amount").val(approval.sugCrl);
        $("#forecast-amount").val(member.preCrl);
        $("#year-rate").val(parseFloat(approval.apr) * 100);
        $("#borrowing-limit").val(approval.term);
        if(approval.decision) {
            $("#applyResult").val(approval.decision);
        } else {
            $("#applyResult").val("");
        }
        $("#credit-reasion-1").val(approval.reason1);
        $("#credit-reasion-2").val(approval.reason2);
        $("#credit-reasion-3").val(approval.reason3);
        $("#customer-desc").val(approval.profile);
        $("#credit-opinion").val(approval.opinion);
        $("#creditor").val(approval.creditor);
        if(application.status == '3' || application.status == '99') {
            $("#operationDiv").children().show();
        } else {
            appDetail.readApproveCont();
            $("#doTv").remove();
            $("#operationDiv").children().remove();
        }
    };
    appDetail.loadCredits = function (credits) {
        var contentHtml = "";
        $.each(credits, function (i, creditCard) {
            contentHtml += '<div class="itemDiv" style="height: 70px;">\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">银行</div>\n        ' +
                '<div class="grid-2"><input type="text" name="bank" readonly="readonly" value="' + appDetail.bankMap[creditCard.bank] + '"></div>\n        ' +
                '<div class="grid-2">卡号</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardNo" readonly="readonly" style="width: 300px;" value="' + creditCard.cardNo + '"></div>\n        ' +
                '<div class="grid-2">卡类型</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardType" readonly="readonly" value="' + (creditCard.type ? creditCard.type : "") + '"></div>\n        ' +
                '<div class="grid-2">卡片状态</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardStatus" readonly="readonly" value="' + (creditCard.isValid ? '有效' : '失效') + '"></div>\n    ' +
                '</div>\n    ' +
                '<div class="single-item">\n        ' +
                '<div class="grid-2">电子邮箱</div>\n        ' +
                '<div class="grid-2"><input type="text" name="cardStatus" readonly="readonly" style="width: 300px;" value="' + (creditCard.email ? creditCard.email : "") + '"></div>\n        ' +
                '<div class="grid-2">账单邮箱状态</div>\n        ' +
                '<div class="grid-2"><input type="text" name="billEmailStatus" readonly="readonly" value="' + (creditCard.status ? cardEmailStatus[creditCard.status] : "") + '"></div>\n        ' +
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
                '<img src="'+bill.image+'" style="position:absolute; top:5px; left:40%; display:none">\n\n' +
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
    appDetail.makeLoansMap = function (loans) {
        var loansMap = {}, onces = [];
        $.each(loans, function (i, loan) {
            // 0-正常 1-逾期 2-打呆 8-等待放款 9-结清
            if (!loansMap[loan.status]) {
                loansMap[loan.status] = new Array(loan);
            } else {
                loansMap[loan.status].push(loan);
            }
            // 当逾期天数为0，最大逾期天数大于0时，表示曾经逾期
            if (loan.curDelq == 0 && loan.maxDelq > 0) {
                onces.push(loan);
            }
        });
        loansMap['once'] = onces;
        appDetail.loansMap = loansMap;
    };
    appDetail.loadLoansSummary = function (loans) {
        var loansMap = appDetail.loansMap;
        $("#loan-times").val(loans.count);
        $("#loan-amount").val(loans.amount);
        $("#delay-days").val(loans.curDelq);
        $("#max-delay-days").val(loans.maxDelq);

        // 0-正常 1-逾期 2-打呆 8-等待放款 9-结清
        var loanMap = appDetail.loansMap;
        var normalLen = getLenByStatus('0'),
            delayLen = getLenByStatus('1'),
            beforeLen = getLenByStatus('once'),
            closeLen = getLenByStatus('9'),
            stayLen = getLenByStatus('2'),
            waitLen = getLenByStatus('8');
        $("#normal-times").val(normalLen);
        $("#delay-times").val(delayLen);
        $("#before-delay-times").val(beforeLen);
        $("#close-times").val(closeLen);
        $("#stay-times").val(stayLen);
        $("#wait-times").val(waitLen);

        // 设置状态
        var statusDisplay = "";
        if (stayLen != 0) {
            statusDisplay = loanStatus['2'];
        } else if (delayLen != 0) {
            statusDisplay = loanStatus['1'];
        } else if (normalLen != 0) {
            statusDisplay = loanStatus['0'];
        } else if (closeLen != 0) {
            statusDisplay = loanStatus['9'];
        } else if (waitLen != 0) {
            statusDisplay = loanStatus['8'];
        }
        $("#loan-status").val(statusDisplay);

        function getLenByStatus(status) {
            if (loanMap[status] && loanMap[status].length != 0) {
                return loanMap[status].length;
            } else {
                return 0;
            }
        }
    };
    appDetail.loadLoans = function () {
        var loansMap = appDetail.loansMap;
        var initLoans = [];
        if (loansMap['2']) {
            initLoans = initLoans.concat(loansMap['2']);
        }
        if (loansMap['1']) {
            initLoans = initLoans.concat(loansMap['1']);
        }
        if (loansMap['once'] && loansMap['once'].length != 0) {
            initLoans = initLoans.concat(loansMap['once']);
        }
        if (!initLoans && initLoans.length != 0) {
            return;
        }
        var contentHtml = appDetail.genLoansTableHtml(initLoans);
        $("#loans-div").html(contentHtml);
        $("#loans-div").find("tr:gt(0)").find("td:eq(0)").click(function () {
            var borrowNo = $(this).text();
            if (borrowNo) {
                appDetail.showLoanDetail(borrowNo);
            }
        });
        $("#loans-div").find("tr:gt(0)").find("td:eq(1)").click(function () {
            var applNo = $(this).text();
            if (applNo) {
                window.open("appDetail.html?appNo=" + applNo, "_blank");
            }
        });
    };
    appDetail.showLoanDetail = function (borrowNo) {
        $.get("api/account/loans/" + borrowNo, function (json) {
            var contentHtml = "<table class=\"repay-table-style\"><tr>\n" +
                "<td>逾期天数</td>\n" +
                "<td>贷款期数</td>\n" +
                "<td>第X期</td>\n" +
                "<td>还款日期</td>\n" +
                "<td>应还本金及利息(元)</td>\n" +
                "<td>应还本金(元)</td>\n" +
                "<td>应还利息(元)</td>\n" +
                "<td>已还本金(元)</td>\n" +
                "<td>已还利息(元)</td>\n" +
                "<td>逾期本金(元)</td>\n" +
                "<td>逾期罚息(元)</td>\n" +
                "</tr>";
            $.each(json, function (i, repayPlan) {
                contentHtml += '<tr><td>' + repayPlan.overDueDay + '</td>' +
                    '<td>' + repayPlan.term + '</td>' +
                    '<td>' + repayPlan.termNo + '</td>' +
                    '<td>' + $.formatDate(repayPlan.dueDate) + '</td>' +
                    '<td>' + repayPlan.dueAmt + '</td>' +
                    '<td>' + repayPlan.duePrincipal + '</td>' +
                    '<td>' + repayPlan.dueInterest + '</td>' +
                    '<td>' + repayPlan.paidPrincipal + '</td>' +
                    '<td>' + repayPlan.paidInterest + '</td>' +
                    '<td>' + repayPlan.overDueAmt + '</td>' +
                    '<td>' + repayPlan.overDueDay + '</td></tr>';
            });
            new Dialog(contentHtml + "</table>", {title: "贷款明细", modal: false}).show();
        });
    };
    appDetail.showLoansByStatus = function (status) {
        var contentHtml = appDetail.genLoansTableHtml(appDetail.loansMap[status]);
        if (contentHtml) {
            new Dialog(contentHtml, {title: "贷款信息", modal: false}).show();
            $(".dialog").find(".loans-table-style tr:gt(0)").find("td:eq(0)").click(function () {
                var borrowNo = $(this).text();
                if (borrowNo) {
                    appDetail.showLoanDetail(borrowNo);
                }
            });
            $(".dialog").find(".loans-table-style tr:gt(0)").find("td:eq(1)").click(function () {
                var applNo = $(this).text();
                if (applNo) {
                    window.open("appDetail.html?appNo=" + applNo, "_blank");
                }
            });
        }
    };
    appDetail.genLoansTableHtml = function (loans) {
        if (loans && loans.length != 0) {
            var contentHtml = "<table class=\"loans-table-style\" style=\"width: 100%;\">\n" +
                "<tr>\n" +
                "<td>贷款编号</td>\n" +
                "<td>申请编号</td>\n" +
                "<td>贷款金额(元)</td>\n" +
                "<td>年利率(%)</td>\n" +
                "<td>贷款期数(月)</td>\n" +
                "<td>借款起始日期</td>\n" +
                "<td>应还本金(元)</td>\n" +
                "<td>应还利息(元)</td>\n" +
                "<td>已还本金(元)</td>\n" +
                "<td>已还利息(元)</td>\n" +
                "<td>逾期天数</td>\n" +
                "<td>最大逾期天数</td>\n" +
                "<td>状态</td>\n" +
                "</tr>\n";
            $.each(loans, function (i, loan) {
                contentHtml += '<tr><td style="cursor: pointer;text-decoration: underline;">' + loan.id + '</td>' +
                    '<td style="cursor: pointer;text-decoration: underline;">' + loan.applicationNo + '</td>' +
                    '<td>' + loan.amt + '</td>' +
                    '<td>' + parseFloat(loan.apr) * 100 + '</td>' +
                    '<td>' + loan.term + '</td>' +
                    '<td>' + $.formatDate(loan.startDate) + '</td>' +
                    '<td>' + loan.principal + '</td>' +
                    '<td>' + loan.interest + '</td>' +
                    '<td>' + loan.paidPrincipal + '</td>' +
                    '<td>' + loan.paidInterest + '</td>' +
                    '<td>' + loan.curDelq + '</td>' +
                    '<td>' + loan.maxDelq + '</td>' +
                    '<td>' + loanStatus[loan.status] + '</td></tr>';
            });
            return contentHtml + "</table>";
        } else {
            return "";
        }
    };
    appDetail.readApproveCont = function() {
        $("#tvType").prop("disabled",true);
        $("#phoneType").prop("disabled",true);
        $("#tvDecision").prop("disabled",true);
        $("#creditor-question").attr("readonly","readonly");
        $("#borrowing-amount").attr("readonly","readonly");
        $("#apply-amount").attr("readonly","readonly");
        $("#applyResult").prop("disabled",true);
        $("#credit-reasion-1").attr("readonly","readonly");
        $("#credit-reasion-2").attr("readonly","readonly");
        $("#credit-reasion-3").attr("readonly","readonly");
        $("#customer-desc").attr("readonly","readonly");
        $("#credit-opinion").attr("readonly","readonly");
    };
    appDetail.reverseApproveCont = function(){
        $("#tvType").prop("disabled",false);
        $("#phoneType").prop("disabled",false);
        $("#tvDecision").prop("disabled",false);
        $("#creditor-question").removeAttr("readonly");
        $("#borrowing-amount").removeAttr("readonly");
        $("#apply-amount").removeAttr("readonly");
        $("#applyResult").prop("disabled",false);
        $("#credit-reasion-1").removeAttr("readonly");
        $("#credit-reasion-2").removeAttr("readonly");
        $("#credit-reasion-3").removeAttr("readonly");
        $("#customer-desc").removeAttr("readonly");
        $("#credit-opinion").removeAttr("readonly");
    };
    // initialize
    appDetail.init();
// END
})(jQuery);