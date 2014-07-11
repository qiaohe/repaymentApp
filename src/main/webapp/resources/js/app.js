/* global alert:false */
/* global config:false */
/* global member:false */
/* global json:false */
/* global WeixinJSBridge:false */
"use strict";

if (!config || !member) {
    alert("Config or member is undefined!");
}

var device = {
    getUserAgent: function() {
        this.userAgent = navigator.userAgent.toLowerCase();
    },

    getAndroidVersion: function() {
        if(this.userAgent.search("android") !== -1) {
            this.androidVersion = this.userAgent.slice(this.userAgent.indexOf("android") + 8, this.userAgent.indexOf("android") + 11);
            this.androidVersion = Number(this.androidVersion);
        }
    }
},
    dict = {
        getBincode: function() {
            var $this = this;
            $.getJSON(config.apiPath + "dict/binCode", function (json) {
                $this.bincode = json;
            });
        },

        getCardIconSrc: function(bankNo) {
            var iconPath = "resources/img/card_icon/", icon;
            switch(bankNo){
                case 1:
                    icon = "icbc.png";
                    break;
                case 2:
                    icon = "abc.png";
                    break;
                case 3:
                    icon = "bc.png";
                    break;
                case 4:
                    icon = "cbc.png";
                    break;
                case 5:
                    icon = "ctb.png";
                    break;
                case 6:
                    icon = "ceb.png";
                    break;
                case 7:
                    icon = "cgb.png";
                    break;
                case 8:
                    icon = "hxb.png";
                    break;
                case 9:
                    icon = "cmsb.png";
                    break;
                case 10:
                    icon = "pingan.png";
                    break;
                case 11:
                    icon = "cib.png";
                    break;
                case 12:
                    icon = "cmb.png";
                    break;
                case 13:
                    icon = "spdb.png";
                    break;
                case 14:
                    icon = "citic.png";
                    break;
                case 15:
                    icon = "psbc.png";
                    break;
                default:
                    icon = "card.png";
            }
            return iconPath += icon;
        },

        validateCardNo: function(cardNum) {
            cardNum = cardNum.replace(/ /g, "");
            var sum = 0,
                l = cardNum.length;
            if (l > 15) {
                for (var i = 0; i < l; i++) {
                    var tmp = Number(cardNum[i], 10);
                    if ((i % 2) === 0) {
                        if (2 * tmp > 9) {
                            sum += (2 * tmp - 9);
                        }
                        else {
                            sum += 2 * tmp;
                        }
                    }
                    else {
                        sum += tmp;
                    }
                }
                return sum % 10 === 0;
            }
            else {
                return false;
            }
        },

        setCardIcon: function(imgId, num) {
            if (num.length < 2) {
                $("#" + imgId).attr("src", "resources/img/card_icon/card.png");
            }
            else if (num.length <= 6) {
                if (num[0] === "4") {
                    $("#" + imgId).attr("src", "resources/img/card_icon/visa.png");
                }
                else if (num[0] === "5") {
                    $("#" + imgId).attr("src", "resources/img/card_icon/master.png");
                }
            }
            else if (num.length > 6) {
                var $this = this;
                $.each(this.bincode, function (i, value) {
                    if (value.binNo === num.replace(/ /g, "").slice(0, 6)) {
                        var iconSrc = $this.getCardIconSrc($this.bincode[i].bankNo);
                        $("#" + imgId).attr("src", iconSrc);
                    }
                });
            }
        },

        numberWithCommas: function(x) {
            x = x.toString();
            var pattern = /(-?\d+)(\d{3})/;
            while (pattern.test(x)) {
                x = x.replace(pattern, "$1,$2");
            }
            return x;
        },

        getformatDate: function(ms, type) {
            var date = new Date(ms),
                year = date.getFullYear(),
                month = +date.getMonth() + 1,
                day = +date.getDate() < 10 ? "0"+date.getDate() : date.getDate(),
                hour = +date.getHours() < 10 ? "0"+date.getHours() : date.getHours(),
                minute = +date.getMinutes() < 10 ? "0"+date.getMinutes():date.getMinutes(),
                result;
            month = month < 10 ? "0" + month : month;
            if(type === "1") {
                result = year +"-"+ month +"-"+ day;
            } else if(type === "2") {
                result = month+"月"+day+"日 "+hour+":"+minute;
            }
            return result;
        },

        getReadableDate: function(ms) {
            var date = new Date(ms),
                month = date.getMonth()+ 1,
                day = date.getDate(),
                year = date.getFullYear();
            return [year, month, day];
        }
    };

member = (function(member) {
    member.whetherUsedCard = function(cardNum) {
        cardNum = cardNum.replace(/ /g, "");
        var taken,
            $this = this;
        $.ajax({
            url: config.apiPath + "members/" + this.id + "/creditCard/" + cardNum + config.timeStamp,
            type: "GET",
            async: false,
            dataType: "text",
            success: function (text) {
                taken = text === "true";
            },
            error: function() {
                config.alertUrl(config.apiPath + "members/" + $this.id + "/creditCard/" + cardNum + config.timeStamp);
            }
        });
        return taken;
    };

    member.getCreditLimit = function() {
        var $this = this;
        $.ajax({
            url: config.apiPath + "members/" + this.id + "/crl" + config.timeStamp,
            type: "GET",
            dataType: "json",
            async: false,
            success: function (json) {
                $this.limit = json.creditLimit;
                $this.rank = json.rankOfLimit;
            },
            error: function () {
                config.alertUrl(config.apiPath + "members/" + $this.id + "/status" + config.timeStamp);
            }
        });
    };

    member.getAvlCrl = function() {
        var $this = this;
        $.ajax({
            url: config.apiPath + "members/" + this.id + "/avlCrl" + config.timeStamp,
            type: "GET",
            dataType: "text",
            async: false,
            success: function (text) {
                $this.avlCrl = text;
            },
            error: function () {
                config.alertUrl(config.apiPath + "members/" + $this.id + "/avlCrl" + config.timeStamp);
            }
        });
    };

    member.recognizeIdCard = function(formData, urlPath, dataType) {
        return $.ajax({
            url: urlPath,
            type: "POST",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            dataType: dataType
        });
    };

    member.testLimit = function() {
        var $this = this;
        $.ajax({
            url: config.apiPath + "members/" + this.id + config.timeStamp,
            type: "POST",
            async: false,
            contentType: "application/json",
            data: JSON.stringify({
                creditCarNo: this.creditCard.replace(/ /g, ""),
                industry: this.industry,
                education: this.education,
                email: this.email,
                billEmail: "",
                billPassword: ""
            }),
            dataType: "json",
            success: function (json) {
                $this.limit = json.creditLimit;
                $this.rank = json.rankOfLimit;
                $this.anothertest = 0;
                localStorage.clear();
                $.mobile.navigate("#result");
            },
            error: function () {
                config.alertUrl(config.apiPath + "members/" + $this.id + config.timeStamp);
            }
        });
    };

    member.getFirstLoanAppNo = function() {
        var $this = this;
        $.ajax({
            url: config.apiPath + "app/members/" + this.id + "/appNo" + config.timeStamp,
            type: "GET",
            async: false,
            dataType: "text",
            success: function (text) {
                $this.firstLoanAppNo = text;
            },
            error: function () {
                    config.alertUrl(config.apiPath + "app/members/" + $this.id + "/appNo" + config.timeStamp);
            }
        });
    };

    member.whetherLoanable = function() {
        var $this = this;
        $.ajax({
            url: config.apiPath + "app/members/" + this.id + "/loanable" + config.timeStamp,
            type: "POST",
            async: false,
            dataType: "text",
            success: function (text) {
                $this.isLoanable = (text === "true");
            },
            error: function () {
                config.alertUrl(config.apiPath + "app/members/" + $this.id + "/loanable" + config.timeStamp);
            }
        });
    };

    member.acquireVerificationCode = function(phoneNum) {
        var $this = this;
        return $.ajax({
            url: config.apiPath + "sms/" + phoneNum + config.timeStamp,
            type: "GET",
            dataType: "text",
            error: function () {
                config.alertUrl(config.apiPath + "sms/" + phoneNum);
            }
        });
    };

    member.matchVerificationCode = function(code, phoneNum) {
        var $this = this;
        return $.ajax({
            url: config.apiPath + "members/" + this.id + "/" + phoneNum + "/" + code + config.timeStamp,
            type: "POST",
            async: false,
            data: JSON.stringify({
                mobilePhone: phoneNum,
                code: code
            }),
            dataType: "text",
            error: function () {
                config.alertUrl(config.apiPath + "members/" + $this.id + "/" + phoneNum + "/" + code + config.timeStamp);
            }
        });
    };

    member.countPaybackEachTerm = function(obj) {
        var $this = this;
        $.ajax({
            url: config.apiPath + "app/saveCost" + config.timeStamp,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                term: obj.term,
                amt: obj.amount,
                memberId: this.id
            }),
            dataType: "json",
            success: function(json) {
                $("#each-term").html(json.payBackEachTerm);
                $("#saved").html(Math.round(json.savedCost * 100) / 100);
            },
            error: function () {
                config.alertUrl(config.apiPath + "app/saveCost" + config.timeStamp);
            }
        });
    };

    member.loanApplication = {};

    member.applyLoan = function(loanApplication) {
        return $.ajax({
            url: config.apiPath + "app" + config.timeStamp,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                term: loanApplication.term,
                amt: loanApplication.amount,
                memberId: member.id,
                creditCarNo: loanApplication.creditCard
            }),
            dataType: "text",
            error: function () {
                config.alertUrl(config.apiPath + "app" + config.timeStamp);
            }
        });
    };

    member.loanToCard = function(cardNum) {
        var $this = this;
        return $.ajax({
            url: config.apiPath + "app/members/" + this.id + "/creditCard/" + cardNum + config.timeStamp,
            type: "POST",
            data: cardNum,
            dataType: "text",
            error: function () {
                config.alertUrl(config.apiPath + "app/members/" + $this.id + "/creditCard/" + cardNum + config.timeStamp);
            }
        });
    };

    member.addCreditCard = function(cardNum) {
        cardNum = cardNum.replace(/ /g, "");
        var $this = this;
        return $.ajax({
            url: config.apiPath + "members/" + this.id + "/creditCards/" + cardNum + config.timeStamp,
            type: "POST",
            async: false,
            dataType: "text",
            error: function () {
                config.alertUrl(config.apiPath + "members/" + $this.id + "/creditCards/" + cardNum + config.timeStamp);
            }
        });
    };

    member.returnFootPrint = function(id, status) {
        var $this = this;
        $.ajax({
            url: config.apiPath + "members/" + id + "/status/" + status,
            type: "GET",
            async: false,
            success : function (res) {
                console.log(res);
            },
            error: function () {
                    config.alertUrl(config.apiPath + "members/" + id + "/status/" + status);
            }
        });
    };

    return member;
})(member);

$(document).on("pagebeforeshow", function() {
    if (member.gender === 1) {
        $(".gender").html("娘子");
    }
});

$(document).on("pagecreate", "#limit", function () {
    device.getUserAgent();
    device.getAndroidVersion();
    alert(device.androidVersion);
    if(device.androidVersion <= 2.3) {
//    if(1 <= 2.3) {
        $("#front-upload, #back-upload").remove();
        $("label[for='front-upload']").attr("for", "front-upload-2");
        $("label[for='back-upload']").attr("for", "back-upload-2");

        $("#front-upload-2, #back-upload-2").change(function() {
            var whichSide;
            if($(this).attr("id").search("front") !== -1) {
                whichSide = "front";
            }
            else if($(this).attr("id").search("back") !== -1) {
                whichSide = "back";
            }
            else {
                alert("The parameter whichSide is undefined!");
            }

            var iframe = $("<iframe></iframe>");
            iframe.attr({
                "src": "http://192.168.0.185:8080/repayment/index.html#limit?t=" + config.timeStamp,
                "name": "for-upload",
                "id": "for-upload"
            }).css({
                "width": "0",
                "height": "0"
            });

            $("#" + whichSide + "-form").attr({
                "target": "for-upload",
                "action": "api/members/" + member.id + "/idCard" + whichSide[0].toUpperCase() + whichSide.slice(1),
                "method": "post",
                "enctype": "multipart/form-data",
                "encoding": "multipart/form-data",
                "file": $("#" + whichSide + "-upload-2").val()
            }).after(iframe).submit();

            $("#" + whichSide + "-num").html("正在识别...");

            setTimeout(function() {
                $.ajax({
                    url: config.apiPath + "members/" + member.id + "/idCard",
                    type: "GET",
                    success: function(json) {
                        if(typeof member.idCard === "undefined" && json.idNo) {
                            member.idCard = json.idNo;
                            $("#front-num").html(member.idCard).css("color", "#222222");
                            $("#front-upload-2").attr("disabled", true);
                            $("#tip-front").attr("src", "resources/img/public/correct.png");
                        }

                        if(typeof member.gender === "undefined" && json.sex) {
                            member.gender = json.sex;
                            if (member.gender === "FEMALE") {
                                $(".gender").html("娘子");
                            }
                        }

                        if(typeof member.validThru === "undefined" && json.validThru) {
                            member.validThru = dict.getReadableDate(json.validThru).join(".");
                            $("#back-num").html("有效期至" + member.validThru).css("color", "#222222");
                            $("#back-upload-2").attr("disabled", true);
                            $("#tip-back").attr("src", "resources/img/public/correct.png");
                        }
                    },
                    error: function() {
                        $("#" + whichSide + "-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                        $("label[for='front-upload-2']").css("border-color", "#cc0000");
                        $("#tip-" + whichSide).attr("src", "resources/img/public/wrong.png");
                    }
                });
                iframe.remove();
            }, 7000);
        });
    }
    else {
        if (member.idCard) {
            $("#front-num").html(member.idCard).css("color", "#222222");
            $("#front-upload").attr("disabled", true);
            $("#tip-front").attr("src", "resources/img/public/correct.png");
        }
        else {
            $("#front-upload").change(function (e) {
                //$.mobile.loading("show", {html: "<span><center><img src='resources/img/other_icons/loading.png'></center></span>"});
                $("#front-num").html("正在识别...").css("color", "#222222");
                var formData = new FormData();
                formData.append("idCardFrontFile", e.target.files[0]);
                member.recognizeIdCard(formData, config.apiPath + "members/" + member.id + "/idCardFront", "json").success(function (json) {
                    $("#front-num").html(json.idNo).css("color", "#222222");
                    $("label[for='front-upload']").css("border-color", "#c0c0c0");
                    $("#tip-front").attr("src", "resources/img/public/correct.png");
                    $("#front-upload").attr("disabled", true);
                    member.idCard = json.idNo;
                    member.gender = json.sex;
                    if (member.gender === "FEMALE") {
                        $(".gender").html("娘子");
                    }
                }).error(function () {
                    $("#front-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                    $("label[for='front-upload']").css("border-color", "#cc0000");
                    $("#tip-front").attr("src", "resources/img/public/wrong.png");
                });
            });
        }

        if (member.validThru) {
            $("#back-num").html("有效期至" + member.validThru).css("color", "#222222");
            $("#back-upload").attr("disabled", true);
            $("#tip-back").attr("src", "resources/img/public/correct.png");
        }
        else {
            $("#back-upload").change(function (e) {
                $("#back-num").html("正在识别...").css("color", "#222222");
                var formData = new FormData();
                formData.append("idCardBackFile", e.target.files[0]);
                member.recognizeIdCard(formData, config.apiPath + "members/" + member.id + "/idCardBack", "text").success(function (text) {
                    $("#back-num").html("有效期至" + text).css("color", "#222222");
                    $("label[for='back-upload']").css("border-color", "#c0c0c0");
                    $("#tip-back").attr("src", "resources/img/public/correct.png");
                    $("#back-upload").attr("disabled", true);
                    member.validThru = text;
                }).error(function () {
                    $("#back-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                    $("label[for='back-upload']").css("border-color", "#cc0000");
                    $("#tip-back").attr("src", "resources/img/public/wrong.png");
                });
            });
        }
    }

    $("#credit-card").on("keyup", function (e) {
        var $cardTip = $("#card-tip"),
            $tipCredit = $("#tip-credit");

        $cardTip.hide();
        $tipCredit.css({"height": "22px", "width": "32px"});
        var num = $(this).val();
        dict.setCardIcon("tip-credit", num);

        if(dict.validateCardNo(num)) {
            if (member.whetherUsedCard(num)) {
                $tipCredit.attr("src", "resources/img/public/wrong.png").css({"height": "22px", "width": "22px"});
                $cardTip.html("不可用的信用卡号!").show();
            }
            else {
                if (!member.anothertest) {
                    $("#next-step").attr("href", "#basic-info").css("background-color", "#3ca0e6");
                }
                else{
                    $("#next-step").attr("href", "#result").css("background-color", "#3ca0e6").off("click").click(function () {
                        member.creditCard = num;
                        member.testLimit();
                    });
                }
                member.creditCard = num.replace(/ /g, "");
            }
        }
        else {
            $("#next-step").attr("href", "#");
            if (num.replace(/ /g, "").length === 16 || num.replace(/ /g, "").length === 18) {
                $cardTip.html("卡号错误!").show();
                $("#next-step").removeClass("bluebtn").attr("href", "#");
            }
            else {
                $cardTip.hide();
            }
        }

        if (num.length % 5 === 4) {
            if (e.keyCode !== 8) {
                $(this).val(num + " ");
            }
            else {
                $(this).val(num.slice(0, num.length - 1));
            }
        }
    }).focusin(function() {
        $("#credit-num").hide();
    }).focusout(function() {
        if(!$(this).val()) {
            $("#credit-num").show();
        }
    });

    if (member.creditCard) {
        $(this).val(member.creditCard);
        $("#tip-credit").attr("src", localStorage.getItem("card_icon"));
        $("#credit-num").hide();
        $("#next-step").css("background-color", "#3ca0e6").attr("href", "#basic-info");
    }
});

$(document).on("pageshow", "#limit", function(){
    if(typeof dict.bincode === "undefined") {
        dict.getBincode();
    }

    $("#quit").off("tap").tap(function() {
        member.returnFootPrint(member.id, "-1");
        WeixinJSBridge.call("closeWindow");
    });

    $("#continue").off("tap").tap(function() {
        $("#pop-limit").popup("close");
    });

    if (member.anothertest) {
        $("#credit-card").val("").scrollTop(350).focus().trigger("tap");
        $("#tip-credit").attr("src", "resources/img/card_icon/card.png");
    }

    if (member.isnew) {
        $("#pop-limit").popup("open");
        member.isnew = 0;
    }
});

$(document).on("pagecreate", "#basic-info", function(){
    function addOptions(elementId, json) {
        var tmp = [],
            $element = $("#" + elementId);
        for (var i in json) {
            if(json.hasOwnProperty(i)) {
                tmp += "<option value=" + json[i].key + ">" + json[i].value + "</option>";
            }
        }
        $element.append($(tmp)).selectmenu().selectmenu("refresh");
    }

    function enableLimitTest(btnId) {
        var mailRegEx = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if(member.creditCard && member.industry && member.education && mailRegEx.test(member.email)) {
            $("#" + btnId).css("background-color", "#3ca0e6").tap(function () {
                member.testLimit();
            });
        } else {
            $("#" + btnId).css("background-color", "silver");
        }
    }

    if (typeof dict.industry === "undefined"){
        $.getJSON(config.apiPath + "dict/industry", function(json){
            addOptions("industry-select", json);
            dict.industry = json;
        });
    }

    if(typeof dict.education === "undefined"){
        $.getJSON(config.apiPath + "dict/education", function(json){
            addOptions("education-select", json);
            dict.education = json;
        });
    }

    if (member.industry) {
        $("#industry-select:nth-child(" + (member.industry + 1) + ")").attr("selected", "selected");
    }

    if (member.education) {
        $("#education-select:nth-child(" + (member.education + 1) + ")").attr("selected", "selected");
    }

    $("#industry-select").change(function () {
        $("#industry-txt").hide();
        member.industry = $(this).val();
        localStorage.setItem("industry", $(this).val());
        enableLimitTest("hand-in");
    });

    $("#education-select").change(function () {
        $("#education-txt").hide();
        member.education = $(this).val();
        localStorage.setItem("education", $(this).val());
        enableLimitTest("hand-in");
    });

    $("#email").keyup(function(){
        member.email = $.trim($(this).val());
        enableLimitTest("hand-in");
    }).focusin(function() {
        $("#email-txt").hide();
    }).focusout(function() {
        if(!$(this).val()) {
            $("#email-txt").show();
        }
    });
});

$(document).on("pagecreate", "#result", function(){
    $("#option-2").click(function(e){
        $("#share").popup("open");
    });

    var width = $(window).width() * 0.9,
        height = width * 160 / 300;
    $("#share").css({"width":width,"height":height,"background-size":width +"px " +height+ "px"});
    $("#share img").css({"width":width*0.16,"height":width*0.16});

    member.whetherLoanable();
    if(member.isLoanable) {
        $("#option-1").css("background-color", "#3ca0e6").off("tap").on("tap", function() {
            $.mobile.changePage("#loan");
        });
    }

    if(member.status === "5.1") {
        $("#option-1").css("background-color", "#3ca0e6").off("tap").on("tap", function() {
            $.mobile.changePage("#congratulation");
        });
    }
    else if(member.status === "5.2") {
        $("#option-1").css("background-color", "#3ca0e6").off("tap").on("tap", function() {
            $.mobile.changePage("#fail");
        });
    }

    if(+member.status < 4) {
        $("#option-3").tap(function () {
            member.anothertest = 1;
            $.mobile.changePage("#limit");
        }).addClass("bluebtn");
    }
});

$(document).on("pagebeforeshow", "#result", function(){
    if(typeof member.limit === "undefined") {
        member.getCreditLimit();
    }

    if(typeof member.avlCrl === "undefined") {
        member.getAvlCrl();
    }

    if (member.status === "11" || member.status === "12"){
        member.limit = 0;
        member.rank = 0;
    }

    $("#amt-shown").html(dict.numberWithCommas(member.limit));
    $("#rank-shown").html(Math.round(member.rank * 100) + "&#37;");
    if(member.limit > 4000){
        if (member.gender === 1) {
            $("#rank-cmt").html("，娘子您是权贵啊！");
        }
        else {
            $("#rank-cmt").html("，官人您是权贵啊！");
        }
        $("#option-1").html("巨款啊！现在就去申请借款");
        $("#option-2").html("去跟小伙伴嘚瑟一下");
        $("#option-3").html("换张信用卡再试试");
    }
});

$(document).on("pageshow", "#result", function() {
    $("#share-sina").tap(function () {
        share_to('tsina',getShareConfig());
        return false;
    });

    $("#share-tencent").tap(function () {
        share_to('tqq',getShareConfig());
        return false;
    });

    $("#share-qzone").tap(function () {
        share_to('qzone',getShareConfig());
        return false;
    });

    $("#share-renren").tap(function () {
        share_to('renren',getShareConfig());
        return false;
    });
});

function getShareConfig() {
    return {
        title : "终于找到了，帮我还信用卡的那个人",
        desc : "一直以来我都觉得没有人帮我还信用卡是不科学的，今天终于被我找到了！哈哈哈哈",
        summary : "一直以来我都觉得没有人帮我还信用卡是不科学的，今天终于被我找到了！哈哈哈哈",
        url : 'http://godzilla.dlinkddns.com.cn/repaymentApp/index2.html#prom',
        img : 'http://godzilla.dlinkddns.com.cn/repaymentApp/resources/img/public/logo.png',
        width : screen.width,
        height : screen.height,
        left : 0,
        top : 0
    };
}

WeixinApi.ready(function(Api) {
    // 微信分享的数据
    var wxData = {
        "appId": "", // 服务号可以填写appId
        "imgUrl" : 'http://godzilla.dlinkddns.com.cn/repaymentApp/resources/img/public/logo.png',
        "link" : 'http://godzilla.dlinkddns.com.cn/repaymentApp/index2.html#prom',
        "desc" : '一直以来我都觉得没有人帮我还信用卡是不科学的，今天终于被我找到了！哈哈哈哈',
        "title" : "终于找到了，帮我还信用卡的那个人"
    };

    // 分享的回调
    var wxCallbacks = {
        // 分享操作开始之前
        ready : function() {
            // 你可以在这里对分享的数据进行重组
            console.log("准备分享");
        },
        // 分享被用户自动取消
        cancel : function(resp) {
            // 你可以在你的页面上给用户一个小Tip，为什么要取消呢？
            console.log("分享被取消");
        },
        // 分享失败了
        fail : function(resp) {
            // 分享失败了，是不是可以告诉用户：不要紧，可能是网络问题，一会儿再试试？
            console.log("分享失败");
        },
        // 分享成功
        confirm : function(resp) {
            // 分享成功了，我们是不是可以做一些分享统计呢？
            //window.location.href='http://192.168.1.128:8080/wwyj/test.html';
            console.log("分享成功");
        },
        // 整个分享过程结束
        all : function(resp) {
            // 如果你做的是一个鼓励用户进行分享的产品，在这里是不是可以给用户一些反馈了？
            console.log("分享结束");
        }
    };
    // 用户点开右上角popup菜单后，点击分享给好友，会执行下面这个代码
    Api.shareToFriend(wxData, wxCallbacks);
    // 点击分享到朋友圈，会执行下面这个代码
    Api.shareToTimeline(wxData, wxCallbacks);
    // 点击分享到腾讯微博，会执行下面这个代码
    Api.shareToWeibo(wxData, wxCallbacks);
});

function requestAvailable() {
    $("#request").addClass("bluebtn");
}

function requestUnavailable() {
    $("#request").removeClass("bluebtn");
}

$(document).on("pagecreate", "#loan", function () {
    member.loanApplication = {};
    var $newCardnum2 = $("#new-cardnum-2"),
        $verifyingTips = $("#varifying-tips");

    $("#request").off("click").click(function(){
        if($("#agree").attr("checkFlag") && member.validate && member.loanApplication.term && member.loanApplication.amount){
            if (member.existingFlag === 2) {
                $("#cardlist-2").popup("open");
            }
            else {
                member.loanApplication.creditCard = "";
                member.applyLoan(member.loanApplication);
                $.mobile.navigate("#suspension");
                $(this).off("click");
            }
        }
    });

    $("#agree").off("click").click(function () {
        var $agree = $(this);
        $agree.toggleClass("check-custom1").toggleClass("check-custom2");
        if($agree.attr("checkFlag")) {
            $agree.removeAttr("checkFlag");
        } else {
            $agree.attr("checkFlag","1");
        }
        if($agree.attr("checkFlag") && member.validate && member.loanApplication.term && member.loanApplication.amount) {
            requestAvailable();
        } else {
            requestUnavailable();
        }
    });

    $("#ok").off("click").click(function() {
        $("#out-of-area").hide();
        $("#request").off("click");
    });

    $("#Y-2").off("click").click(function(){
        member.applyLoan(member.loanApplication);
    });

    $("#N-2, #close-4").off("click").click(function(){
        $("#card-confirm-2").hide();
    });

    $("#add-another-2").off("click").click(function(){
        $("#new-cardnum-2-placeholder").val("");
        $("#cardlist-2").popup("close");
        $("#card-add-box-2").show();
    });

    $("#return-2").off("click").click(function(){
        $("#card-add-box-2").hide();
    });

    $("#addcard-2").off("tap").tap(function(){
        var cardNum = $newCardnum2.val().replace(/ /g, "");
        if (dict.validateCardNo(cardNum)) {
            if(member.whetherUsedCard(cardNum)) {
                member.addCreditCard($newCardnum2.val()).success(function(){
                    $("#card-add-box-2").hide();
                    member.loanApplication.creditCard = cardNum;
                    setTimeout(function () {
                        $("#card-confirm-2").show();
                    }, 500);
                    $("#num-tail-0").html(member.loanApplication.creditCard.slice(member.loanApplication.creditCard.length - 4, member.loanApplication.creditCard.length));
                    var iconSrc = dict.getCardIconSrc(cardNum.replace(/ /g, "").slice(0, 6));
                    var tmp = "<div class='card-container-0' style='line-height: 40px; background-color: #e7e7e7'><img src='" + iconSrc + "' class='card-in-list'><div style='float:right; line-height:40px; padding:3px 50px 0 10px; font-size: 1.5em'>" + cardNum + "</div></div><hr>";
                    $("#cardlist-2").prepend($(tmp));
                }).error(function(){
                    $newCardnum2.val("");
                    $("#new-cardnum-2-placeholder").html("不可用的信用卡号!").css("color", "#cc0000").show();
                });
            } else {
                $newCardnum2.val("");
                $("#new-cardnum-2-placeholder").html("不可用的信用卡号!").css("color", "#cc0000").show();
            }

        }
        else {
            $newCardnum2.val("");
            $("#new-cardnum-2-placeholder").html("错误的信用卡号!").css("color", "#cc0000").show();
        }
    });

    $("#close").off("click").click(function() {
        $("#cardlist-2").popup("close");
    });

    $("#close-2").off("click").click(function() {
        $("#card-add-box-2").hide();
    });

    $("#varifying-tips a").off("click").click(function() {
        $("#varifying-tips").hide();
    });
});

$(document).on("pagebeforeshow", "#loan", function () {
    if (typeof dict.bincode === "undefined") {
        dict.getBincode();
    }

    var $verifyingTips = $("#varifying-tips");
    member.phone = "";
    if (typeof member.avlCrl === "undefined") {
        member.getAvlCrl();
    }
    $("#loan-limit").html(dict.numberWithCommas(Math.round(member.avlCrl)));

    if(member.existingFlag === 2){
        $("#request").html("确认, 去选择信用卡");
    }

    var i = 60,
        $phone = $("#phone");
    if(member.mobileVarified){
        $("#varifying").remove();
        member.validate = 1;
    }
    else{
        $("#acquire-code").off("click").click(function(){
            var phoneNum = $phone.val();
            if(phoneNum.length !== 11) {
                $verifyingTips.find("h4").html("请输入正确的手机号码!");
                $verifyingTips.show();
            }
            else{
                $.get(config.apiPath + "dict/mobileArea/" + phoneNum, function(text){
                    if(text === "北京" || text === "上海" || text === "广州" || text === "深圳") {
                        member.phone = phoneNum;
                        if (i === 60) {
                            member.acquireVerificationCode(member.phone).success(function(){
                                $verifyingTips.find("h4").html("您的验证码已发送!");
                                $verifyingTips.show();
                                $(this).attr("disabled", "true");
                                member.refreshIntervalId = setInterval(function() {
                                    if (i > 0) {
                                        $("#acquire-code").html(i);
                                        i -= 1;
                                    }
                                    else {
                                        $("#acquire-code").html("重新获取");
                                        clearInterval(member.refreshIntervalId);
                                        i = 60;
                                    }
                                }, 1000);
                            }).error(function () {
                                $verifyingTips.find("h4").html("您的验证码发送失败!");
                                $verifyingTips.show();
                            });
                        }
                    }
                    else {
                        $("#out-of-area").show();
                        member.returnFootPrint(member.id, "-1");
                    }
                });
            }
        });

        $phone.off("focusin").focusin(function(){
            $("#phone-txt").hide();
        }).off("focusout").focusout(function() {
            if(!$(this).val()) {
                $("#phone-txt").show();
            }
        });

        $("#code").off("keyup").keyup(function(){
            var code = $(this).val();
            if(code.length === 6 && member.phone.length === 11){
                member.matchVerificationCode(code, member.phone).success(function(text){
                    if("true" === text){
                        $("#code-tip").attr("src", "resources/img/public/correct.png").css({
                            "height": "22px",
                            "margin-top": "3px"
                        });
                        member.validate = true;
                        if (typeof member.refreshIntervalId !== "undefined") {
                            clearInterval(member.refreshIntervalId);
                        }
                        if($("#agree").attr("checkFlag") && member.validate && member.loanApplication.term && member.loanApplication.amount) {
                            requestAvailable();
                        }
                    }
                    else {
                        $("#code-tip").attr("src", "resources/img/public/wrong.png").css({
                            "height": "22px",
                            "margin-top": "3px"
                        });
                    }
                });
            }
        }).off("focusin").focusin(function() {
            $("#code-txt").hide();
            $("#code-tip").attr("src", "resources/img/public/keyboard.png").css({
                "height": "16px",
                "margin-top": "5px"
            });
        }).off("focusout").focusout(function() {
            if(!$(this).val()) {
                $("#code-txt").show();
            }
        });
    }

    $phone.off("focusin").focusin(function () {
        $("#phone-txt").hide();
    }).off("focusout").focusout(function() {
        if(!$(this).val()) {
            $("#phone-txt").show();
        }
    });

    member.loanApplication.term = "3";
    $("#amount").val("").off("keyup").keyup(function(){
        var tmp = $(this).val();
        if(isNaN(parseFloat(tmp, 10))) {
            $(this).val("");
        } else {
            $(this).val(parseFloat(tmp, 10));
        }
        if(parseInt(tmp, 10) > parseInt(member.avlCrl, 10)) {
            $(this).val(parseInt(member.avlCrl));
        }

        if (parseFloat($(this).val()) >= 1000) {
            member.loanApplication.amount = $(this).val();
            member.countPaybackEachTerm(member.loanApplication);
        } else {
            member.loanApplication.amount = undefined;
            requestUnavailable();
            $("#each-term, #saved").html("");
        }

        if($("#agree").attr("checkFlag") && member.validate && member.loanApplication.term && member.loanApplication.amount) {
            requestAvailable();
        }
    }).off("focusin").focusin(function() {
        $("#amount-txt").hide();
    }).off("focusout").focusout(function() {
        var tmp = $(this).val();
        if(isNaN(parseInt(tmp))) {
            $(this).val("");
        } else if(tmp % 100) {
            member.loanApplication.amount = undefined;
            $("#varifying-tips h4").html("借款金额仅限整百!");
            $("#varifying-tips").show();
        }

        if (parseFloat($(this).val()) < 1000) {
            $("#varifying-tips h4").html("抱歉，最小借款金额为￥1000!");
            $("#varifying-tips").show();
        }
    });

    $("#term-3").off("click").click(function(){
        if (member.loanApplication.term !== "3"){
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            member.loanApplication.term = "3";
            var tmp = parseInt($("#amount").val());
            if(tmp >= 1000 && tmp%100 === 0) {
                member.loanApplication.amount = tmp;
                member.countPaybackEachTerm(member.loanApplication);
            } else {
                member.loanApplication.amount = undefined;
            }
        }
    });

    $("#term-6").off("click").click(function(){
        if (member.loanApplication.term !== "6") {
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            member.loanApplication.term = "6";
            var tmp = parseInt($("#amount").val());
            if(tmp >= 1000 && tmp%100 === 0) {
                member.loanApplication.amount = tmp;
                member.countPaybackEachTerm(member.loanApplication);
            } else {
                member.loanApplication.amount = undefined;
            }
        }
    });

    if(!member.creditcard){
        member.creditcard = [];
        $.get(config.apiPath + "members/" + member.id +"/creditCard", function(data){
            var tmp = [];
            $.each(data, function(ind, obj){
                var src = dict.getCardIconSrc(obj.bank);
                tmp += "<div class='card-container-0' style='line-height: 40px; background-color: #e7e7e7'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding:3px 50px 0 10px; font-size: 1.5em'>" + obj.cardNo + "</div></div><hr>";
                member.creditcard.push([obj.cardNo, obj.bank]);
            });
            $("#cardlist-2").prepend($(tmp));
        });
    }

    $("#new-cardnum-2").off("keyup").keyup(function (e) {
        $("new-cardnum-2-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
        var tmp = $(this).val();
        dict.setCardIcon("tip-new-cardnum-2", tmp);
        if (tmp.length % 5 === 4) {
            if (e.keyCode !== 8) {
                $(this).val(tmp + " ");
            }
            else {
                $(this).val(tmp.slice(0, tmp.length - 1));
            }
        }
    }).off("focusin").focusin(function() {
        $("#new-cardnum-2-placeholder").hide();
    }).off("focusout").focusout(function() {
        if(!$(this).val()) {
            $("#new-cardnum-2-placeholder").show();
        }
    });
});

$(document).on("tap", ".card-container-0", function () {
    member.loanApplication.creditCard = $(this).children("div").html();
    $("#cardlist-2").off("popupafterclose").one("popupafterclose", function(){
        setTimeout(function () {
            $("#card-confirm-2").show();
        }, 500);
    });
    $("#cardlist-2").popup("close");
    $("#num-tail-0").html(member.loanApplication.creditCard.slice(member.loanApplication.creditCard.length - 4, member.loanApplication.creditCard.length));
});

$(document).on("tap", ".card-container", function () {
    member.loanApplication.creditCard = $(this).children("div").html();
    $("#cardlist").off("popupafterclose").one("popupafterclose", function(){
        setTimeout(function () {
            $("#card-confirm").show();
        }, 500);
    });
    $("#cardlist").popup("close");
    $("#num-tail").html(member.loanApplication.creditCard.slice(member.loanApplication.creditCard.length - 4, member.loanApplication.creditCard.length));
});

$(document).on("pagebeforeshow", "#congratulation", function(){
    member.getFirstLoanAppNo();
    if (!dict.bincode) {
        dict.getBincode();
    }

    $("#agree-cong").click(function(){
        var $agreeConfig = $(this);
        $agreeConfig.toggleClass("check-custom1").toggleClass("check-custom2");
        if($agreeConfig.attr("checkFlag")) {
            $agreeConfig.removeAttr("checkFlag");
            $("#go-choose-card").removeClass("bluebtn").attr("href", "#");
        } else {
            $agreeConfig.attr("checkFlag","1");
            $("#go-choose-card").addClass("bluebtn").attr("href", "#cardlist");
        }
    });

    $.ajax({
        url: config.apiPath + "app/" + member.firstLoanAppNo + config.timeStamp,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (json) {
            $("#amt-x").html(dict.numberWithCommas(json.amt));
            $("#term-shown").html(json.term);
            $("#each-x").html("&yen;" + Math.round(json.repayPerTerm * 100)/100).css("color", "black");
            $("#saved-x").html("&yen;" + Math.round(json.saveCost * 100)/100).css("color", "black");
            if (!json.isFullyApproved) {
                $("#cong-discription").html("抱歉, 只能先借这么多给您...");
            }
        },
        error: function () {
            config.alertUrl(config.apiPath + "app/" + member.firstLoanAppNo + config.timeStamp);
        }
    });

    if(!member.creditcard){
        member.creditcard = [];
        $.get(config.apiPath + "members/" + member.id +"/creditCard", function(data){
            var tmp = [];
            $.each(data, function(ind, obj){
                var src = dict.getCardIconSrc(obj.bank);
                tmp += "<div class='card-container' style='line-height: 40px; background-color: #e7e7e7'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding:3px 50px 0 10px; font-size: 1.5em'>" + obj.cardNo + "</div></div><hr>";
                member.creditcard.push([obj.cardNo, obj.bank]);
            });
            $("#cardlist").prepend($(tmp));
        });
    }

    $("#new-cardnum").off("keyup").keyup(function (e) {
        $("new-cardnum-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
        var tmp = $(this).val();

        if(tmp.length > 0)
            $("#new-cardnum-placeholder").hide().html("请输入").css("color", "#333333");
        else
            $("#new-cardnum-placeholder").show();

        dict.setCardIcon("tip-new-cardnum", tmp);

        if (tmp.length % 5 == 4) {
            if (e.keyCode != 8)
                $(this).val(tmp + " ");
            else
                $(this).val(tmp.slice(0, tmp.length - 1));
        }
    });

    $("#Y").off("tap").tap(function(){
        member.loanToCard(member.loanApplication.creditCard).success(function(){});
    });

    $("#N, #close-3").off("tap").tap(function(){
        $("#card-confirm").hide();
    });

    $("#add-another").off("tap").tap(function(){
        $("#cardlist").off("popupafterclose").one("popupafterclose", function(){
            setTimeout(function() {
                $("#card-add-box").show();
            }, 500);
        });
        $("#cardlist").popup("close");
    });

    $("#return").off("tap").tap(function(){
        $("#card-add-box").hide();
    });

    $("#addcard").off("tap").tap(function(){
        var cardNum = $("#new-cardnum").val().replace(/ /g, "");
        if (dict.validateCardNo(cardNum)) {
            member.addCreditCard($("#new-cardnum").val()).success(function(){
                $("#card-add-box").hide();
                member.loanApplication.creditCard = cardNum;
                setTimeout(function () {
                    $("#card-confirm").show();
                }, 500);
                $("#num-tail").html(member.loanApplication.creditCard.slice(member.loanApplication.creditCard.length - 4, member.loanApplication.creditCard.length));
                var iconSrc = getCardIconSrc(cardNum.replace(/ /g, "").slice(0, 6));
                var tmp = "<div class='card-container' style='line-height: 40px; background-color: #e7e7e7'><img src='" + iconSrc + "' class='card-in-list'><div style='float:right; line-height:40px; padding:3px 50px 0 10px; font-size: 1.5em'>" + cardNum + "</div></div><hr>";
                $("#cardlist").prepend($(tmp));
            }).error(function(){
                $("#new-cardnum").val("");
                $("#new-cardnum-placeholder").html("不可用的信用卡号!").css("color", "#cc0000").show();
            });
        }
        else {
            $("#new-cardnum").val("");
            $("#new-cardnum-placeholder").html("错误的信用卡号!").css("color", "#cc0000").show();
        }
    });

    $("#close-0").off("tap").tap(function() {
        $("#cardlist").popup("close");
    });

    $("#close-1").off("tap").tap(function() {
        $("#card-add-box").hide();
    });
});

$(document).on("pagecreate", "#repayment-0", function () {
    if(!member.loan) {
        $.ajax({
            url: config.apiPath + "account/members/" + member.id,
            type: "GET",
            async: false,
            dataType:"json",
            success: function(json){
                member.loan = json;
            },
            error: function () {
                if (config.debug) {
                    config.alertUrl(config.apiPath + "account/members/" + member.id);
                }
            }
        });
    }

    if(!!member.loan && !!member.loan.loans && member.loan.loans.length !== 0) {
        generateCarousels(member.loan);
    } else {
        $.mobile.changePage("#no-repayment");
    }

    $(this).on({
        "swipeleft": function() {
            $(".repayment-item")[member.crntCaro].trigger("swipeleft");
        },
        "swiperight": function() {
            $(".repayment-item")[member.crntCaro].trigger("swiperight");
        }
    });
});

function generateCarousels(loanSummary) {
    if(!loanSummary) {
        return;
    }
    $(".repay-s-num").html(loanSummary.loanCount+"笔");
    var loans = loanSummary.loans;
    var contentHtml = "";
    for (var i = 0, len = loans.length; i < len; i++) {
        var loan = loans[i];
        contentHtml += "<div class='repayment-item'>" + generateItemLoan(loan,i) + "</div>";
    }
    $("#container").html(contentHtml);

    member.crntCaro = 0;
    sliderPage();

    $(".repay-item-pay").off("tap").tap(function(e){
        $.mobile.changePage("#pay-success");
    });

    $(".repay-item-detail").off("tap").tap(function (e) {
        e.preventDefault();
        var index = $(this).attr("index");
        var curLoan = loans[index];
        var detailInfo = [];
        detailInfo.push(dict.getformatDate(curLoan.startDate,"1"));
        detailInfo.push("尾号" + curLoan.creditCardNo.slice(curLoan.creditCardNo.length - 4, curLoan.creditCardNo.length));
        detailInfo.push(dict.numberWithCommas(curLoan.amount));
        detailInfo.push(curLoan.term + "期");
        var perDay = dict.getReadableDate(curLoan.startDate)[2];
        if(perDay > 28) {
            detailInfo.push("每月" + perDay + "日 （无"+perDay+"日则提前）");
        } else {
            detailInfo.push("每月" + perDay + "日");
        }
        detailInfo.push(dict.numberWithCommas(curLoan.dueAmt.toFixed(2)));
        detailInfo.push(dict.numberWithCommas(curLoan.lastDueAmt.toFixed(2)));

        var $aim = $("#loan-specific");
        $("#loan-specific").find(".loan-d-r").each(function(i,item){
            $(item).html(detailInfo[i]);
        });
        $aim.popup("open");
    });
    $(".loan-d-close").off("tap").tap(function(){
        $("#loan-specific").popup("close");
    });

    $(".repay-item-history").each(function(){
        var $repayHistory = $(this).next();
        var $lastChild = $(this).find(":last-child");
        $(this).off("click").on("click",function(){
            $lastChild.toggleClass("replay-expand").toggleClass(".replay-collapse");
            if($repayHistory.is(":visible")) {
                $repayHistory.hide();
            } else {
                $repayHistory.show();
            }
        });
    });

    $(".repay-footer-summary").off("tap").tap(function(){
        $.mobile.changePage("#sum-loan");
    });
}

function generateItemLoan(loan,index) {
    var contentHtml = "";
    var status = loan.status;
    // summary
    if(status == 1) { // 逾期
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+dict.getformatDate(loan.startDate,"1")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+dict.numberWithCommas(loan.amount)+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount-delay\">\n"+
            "    <div class=\"repay-amt-title\">最近应还金额</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+dict.numberWithCommas(loan.curDueAmt.toFixed(2))+"</span></div>\n"+
            //"    <div class=\"repay-amt-limit\"><span class=\"r-deadline\">"+getReadableDate(loan.applyDate)[1] + "月" + getReadableDate(loan.applyDate)[2] + "日"+"</span>到期，已逾期</div>\n"+
            "</div>\n"+
            "<div class=\"repay-item repay-item-pay\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-4.png');\"></div><div class=\"repay-info\">现在就去还款<span>(已逾期，请速速还)</span></div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-collapse\"></div></div>\n";
    } else if(status == 9) { // 结清
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+dict.getformatDate(loan.startDate,"1")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+dict.numberWithCommas(loan.amount)+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount\">\n"+
            "    <div class=\"repay-amt-title\">该笔借款已还清，总额：</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+loan.dueAmt+"</span></div>\n"+
            "</div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-collapse\"></div></div>\n";
    } else {
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+dict.getformatDate(loan.startDate,"1")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+dict.numberWithCommas(loan.amount)+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount\">\n"+
            "    <div class=\"repay-amt-title\">最近应还金额</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+dict.numberWithCommas(loan.curDueAmt.toFixed(2))+"</span></div>\n"+
            "    <div class=\"repay-amt-limit\"><span class=\"r-deadline\">"+dict.getReadableDate(loan.applyDate)[1] + "月" + dict.getReadableDate(loan.applyDate)[2] + "日"+"</span>到期</div>\n"+
            "</div>\n"+
            "<div class=\"repay-item repay-item-pay\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-4.png');\"></div><div class=\"repay-info\">现在就去还款</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-collapse\"></div></div>\n";
    }
    // history
    var repayList = loan.repayList;
    var termMap = {"1":"一","2":"二","3":"三","4":"四","5":"五","6":"六"};
    if(repayList && repayList.length != 0) {
        contentHtml += "<ul class=\"repay-history\">";
        for(var i = 0,len = repayList.length;i < len; i++) {
            var repayItem = repayList[i];
            contentHtml += "<li class=\"repay-h-item\">\n"+
                "<div class=\"repay-h-term\">\n"+
                "    <div class=\"repay-h-index\">第"+termMap[repayItem.termNo]+"期还款</div>\n"+
                "    <div class=\"repay-h-time\">"+dict.getformatDate(repayItem.createTime,"2")+"</div>\n"+
                "</div>\n"+
                "<div class=\"repay-h-status\">\n"+
                "    <div class=\"repay-h-amt\">"+repayItem.amt.toFixed(2)+"</div>\n"+
                "    <div class=\"repay-h-sta\">还款成功</div>\n"+
                "</div>\n"+
                "<div class=\"repay-h-img\"></div>\n"+
                "</li>";
        }
        contentHtml += "</ul>";
    } else {
        contentHtml += "<ul class=\"repay-history\"><li class=\"repay-h-item\"><div class=\"no-history\">暂无记录！</div></li></ul>";
    }
    return contentHtml;
}

function sliderPage() {
    var $items = $(".repayment-item");
    $items.current = $items[member.crntCaro];
    $items.prev = function() {
        if ($items.current !== $items[0]) {
            window.scrollTo(0, 0);
            $(".container").animate({"left": "+=" + $width});
            var ind = $items.index($items.current);
            $(".spot:eq(" + ind + ")").removeClass("spot-chosen");
            $(".spot:eq(" + (ind - 1) + ")").addClass("spot-chosen");
            $items.current = $items[ind - 1];
            member.crntCaro -= 1;
        }
    };
    $items.next = function() {
        if ($items.current !== $items[$items.length - 1]) {
            window.scrollTo(0, 0);
            $(".container").animate({"left": "-=" + $width});
            var ind = $items.index($items.current);
            $(".spot:eq(" + ind + ")").removeClass("spot-chosen");
            $(".spot:eq(" + (ind + 1) + ")").addClass("spot-chosen");
            $items.current = $items[ind + 1];
            member.crntCaro += 1;
        }
    };

    $items.off("swipeleft").off("swiperight");
    var $width = $(document).width();
    $items.css({"width":$width*0.9,"margin-left":$width*0.05,"margin-right":$width*0.045});
    $(".container").css({
        "width": $items.length * $width,
        "left": -($width * member.crntCaro)
    });
    // set width of dialog
    $("#loan-specific").css("width", $width*0.9);

    var tmp = "";
    if ($items.length > 1) {
        for(var i = 0, len = $items.length; i < len; i++) {
            tmp += "<div class='spot'></div>";
        }
    }
    $("#spots").css("width", 26 * $items.length + "px").html(tmp);
    $(".spot:eq(" + member.crntCaro + ")").addClass("spot-chosen");

    $(".repayment-item:not(:first, :last)").on({
        swipeleft: $items.next,
        swiperight: $items.prev
    });
    $(".repayment-item:first").on("swipeleft", $items.next);
    $(".repayment-item:last").on("swiperight", $items.prev);
}

function generateLoanSum(info) {
    if(info) {
        $("#total-amount").text(dict.numberWithCommas(info.totalAmount));
        $("#total-times").text(info.loanCount);
        $("#total-payback").text(dict.numberWithCommas(info.totalDueAmt.toFixed(2)));
        $("#total-saved").text(dict.numberWithCommas(info.totalSavedCost.toFixed(0)));
    }
    if(info.loans) {
        var contentHtml = "";
        var len = info.loans.length;
        $.each(info.loans,function(i,loan){
            contentHtml += "<li class=\""+(i!=len-1?"sum-item":"sum-item-last")+"\">\n"+
                "<div class=\"sum-item-l\">"+(i+1)+"</div>\n"+
                "<div class=\"sum-item-r\">\n"+
                "    <ul class=\"sum-r-detail\">\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">借款日期:</span>\n"+
                "            <span class=\"sum-r-r\">"+getReadableDate(loan.startDate).join("-")+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">借款金额:</span>\n"+
                "            <span class=\"sum-r-r\">&yen;"+dict.numberWithCommas(loan.amount)+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">注入卡片:</span>\n"+
                "            <span class=\"sum-r-r\">尾号"+loan.creditCardNo.substring(loan.creditCardNo.length - 4)+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">总计应还:</span>\n"+
                "            <span class=\"sum-r-r\">&yen;"+dict.numberWithCommas(loan.dueAmt.toFixed(2))+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">较信用卡最低还款金额:</span>\n"+
                "            <span class=\"sum-r-r\">约省&yen;"+loan.savedCost.toFixed(0)+"</span>\n"+
                "        </li>\n"+
                "    </ul>\n"+
                "</div>\n"+
                "</li>";
        });
        $("#total-specific").html(contentHtml);
    }
}

$("a").on({
    vmousedown: function(){
        $(this).css("box-shadow", "0 0 5px black");
    }, vmouseup: function(){
        $(this).css("box-shadow", "none");
    }
});

$(window).on("orientationchange",function(event){
    if($("#repayment-0").is(":visible")) {
        sliderPage();
    }
});
$(document).on("pagecreate", "#no-repayment", function () {
    $("#norepay-known").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});
$(document).on("pagebeforeshow", "#sum-loan", function () {
    if(!member.loan) {
        $.ajax({
            url: config.apiPath + "account/members/" + member.id,
            type: "GET",
            async: false,
            dataType:"json",
            success: function(json){
                member.loan = json;
            },
            error: function () {
                config.alertUrl(config.apiPath + "account/members/" + member.id);
            }
        });
    }
    generateLoanSum(member.loan);
});

$(document).on("pageshow", "#fail", function () {
    $("#got-it-y").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pageshow", "#full", function () {
    $("#got-it-z").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pageshow", "#suspension", function () {
    $("#got-it").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagebeforeshow", "#patience", function () {
    $.ajax({
        url: config.apiPath + "app/members/" + member.id + "/progress",
        type: "GET",
        dataType: "text",
        success: function(text) {
            $("#hours").html(text);
            var process = 100 - Math.floor(100 * Number(text) / 48);
            if(process < 15) {
                process = 15;
            }
            $("#bar-inner").css("width", process + "%");
        },
        error: function() {
                config.alertUrl(config.apiPath + "app/members/" + member.id + "/progress");
        }
    });
});

$(document).on("pageshow", "#patience", function() {
    $("#got-it-x").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#pay-success", function () {
    $("#pays-known").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#pay-fail", function () {
    $("#payf-known").off("tap").on("tap", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#feedback", function () {
    var $tip = $("#fd-tip");
    $(".fb-btn a").off("tap").tap(function () {
        var tmp = $.trim($("#fd-textarea").val());
        var $tag = $(this);
        if(tmp) {
            $tag.attr("href","#thanks-feedback");
            $tag.trigger("tag");
            $.ajax({
                url: config.apiPath + "members/" + member.id + "/feedback?f=" + tmp,
                type: "POST",
                success: function () {},
                error: function () {
                    config.alertUrl(config.apiPath + "members/" + member.id + "/feedback");
                }
            });
        }
    });
});

$(document).on("pagecreate", "#thanks-feedback", function () {
    // Back to Weixin
    $("#tfb-back").click(function(){
        WeixinJSBridge.call("closeWindow");
    });
});

//window.onunload = function () {
//    var printStatus,
//        ptn = /#(\w+)/,
//        hash = ptn.exec(window.location)[1];
//
//    if (hash === "#result" && member.status == "3.1") {
//        printStatus = "1";
//        returnFootPrint(member.id, printStatus);
//    }
//    else if (hash === "#congratulation" && (!member.loanApplication.creditCard)) {
//        printStatus = "2";
//        returnFootPrint(member.id, printStatus);
//    }
//
//    if ((member.status === "1" || member.status === "2") && (hash === "#limit" || hash === "#basic-info")) {
//        if (member.idCard) {
//            localStorage.setItem("id_card", member.idCard);
//        }
//        if (member.validThru) {
//            localStorage.setItem("valid_thru", member.validThru);
//        }
//        if ($("#credit-card").val()) {
//            localStorage.setItem("credit_card", $("#credit-card").val());
//            localStorage.setItem("card_icon", $("#tip-credit").attr("src"));
//        }
//        if (member.education) {
//            localStorage.setItem("education", member.education);
//        }
//        if (member.industry) {
//            localStorage.setItem("industry", member.industry);
//        }
//        if (member.email) {
//            localStorage.setItem("email", member.email);
//        }
//    }
//    else {
//        localStorage.clear();
//    }
//};
console.log("END!");