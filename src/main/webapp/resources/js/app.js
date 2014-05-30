﻿/* global $:false */
/* global alert:false */
/* global config:false */
/* global member:false */
/* global WeixinJSBridge:false */
/* global console:false */
"use strict";
var app = {};
var dict = {};
var share = {};

// Methods
function getCreditLimit() {
    $.ajax({
        url: config.api_path + "members/" + member.id + "/crl" + config.time,
        type: "GET",
        dataType: "json",
        async: false,
        success: function (json) {
            member.limit = json.creditLimit;
            member.rank = json.rankOfLimit;
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id + "/crl" + config.time);
        }
    });
}

function getAvlCrl() {
    $.ajax({
        url: config.api_path + "members/" + member.id + "/avlCrl" + config.time,
        type: "GET",
        dataType: "text",
        async: false,
        success: function (text) {
            member.avlcrl = text;
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id + "/avlCrl" + config.time);
        }
    });
}

function recongizeIdCard(form_data, url_path, datatype) {
    return $.ajax({
        url: url_path,
        type: "POST",
        data: form_data,
        cache: false,
        processData: false,
        contentType: false,
        dataType: datatype
    });
}

function getBincode() {
    $.getJSON(config.api_path + "dict/binCode", function (json) {
        dict.bincode = json;
        alert("get the bincode!");
    });
}

function getCardIconSrc(bankNo) {
    var icon_path = "resources/img/card_icon/", icon;
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
    return icon_path += icon;
}

function validateCardNo(num) {
    num = num.replace(/ /g, "");
    var sum = 0, l = num.length;
    if (l > 15) {
        for (var i = 0; i < l; i++) {
            var tmp = parseInt(num[i], 10);
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
    else
        return false;
}

function whetherUsedCard(card_num) {
    card_num = card_num.replace(/ /g, "");
    var taken;
    $.ajax({
        url: config.api_path + "members/" + member.id + "/creditCard/" + card_num + config.time,
        type: "GET",
        async: false,
        dataType: "text",
        success: function (text) {
            taken = text == "true";
        },
        error: function () {
            if (config.debug) {
                alert (config.api_path + "members/" + member.id + "/creditCard/" + card_num + config.time);
            }
        }
    });
    return taken;
}

function setCardIcon(img_id, num) {
    if (num.length < 2) {
        $("#" + img_id).attr("src", "resources/img/card_icon/card.png");
    }
    else if (num.length <= 6) {
        if (num[0] == "4") {
            $("#" + img_id).attr("src", "resources/img/card_icon/visa.png");
        }
        else if (num[0] == "5") {
            $("#" + img_id).attr("src", "resources/img/card_icon/master.png");
        }
    }
    else if (num.length > 6) {
        $.each(dict.bincode, function (i, value) {
            if (value.binNo == num.replace(/ /g, "").slice(0, 6)) {
                var icon_scr = getCardIconSrc(dict.bincode[i].bankNo);
                $("#" + img_id).attr("src", icon_scr);
            }
        });
    }
}

function testLimit() {
    $.ajax({
        url: config.api_path + "members/" + member.id + config.time,
        type: "POST",
        async: false,
        contentType: "application/json",
        data: JSON.stringify({
            creditCarNo: member.credit_card.replace(/ /g, ""),
            industry: member.industry,
            education: member.education,
            email: member.email,
            billEmail: "",
            billPassword: ""
        }),
        dataType: "json",
        success: function (json) {
            member.limit = json.creditLimit;
            member.rank = json.rankOfLimit;
            member.anothertest = 0;
            localStorage.clear();
            $.mobile.navigate("#result");
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id);
        }
    });
}

function enableLimitTest(btn_id) {
    if(member.credit_card && member.industry && member.education && member.email) {
        $("#" + btn_id).css("background-color", "#3ca0e6").tap(function () {
            testLimit();
        });
    }
}

function getAppNo() {
    $.ajax({
        url: config.api_path + "app/members/" + member.id + "/appNo" + config.time,
        type: "GET",
        async: false,
        dataType: "text",
        success: function (text) {
            member.appNo = text;
        },
        error: function () {
            if (config.debug) {
                alert(config.api_path + "app/members/" + member.id + "/appNo" + config.time);
            }
        }
    });
}

function whetherLoanable() {
    $.ajax({
        url: config.api_path + "app/members/" + member.id + "/loanable" + config.time,
        type: "POST",
        async: false,
        dataType: "text",
        success: function (text) {
            member.loanable = (text == "true");
        },
        error: function () {
            if (config.debug) {
                alert(config.api_path + "app/members/" + member.id + "/loanable" + config.time);
            }
        }
    });
}

function sendVarificationCode(phone_num) {
    return $.ajax({
        url: config.api_path + "sms/" + phone_num + config.time,
        type: "GET",
        dataType: "text",
        error: function () {
            if (config.debug)
                alert(config.api_path + "sms/" + phone_num);
        }
    });
}

function matchVarificationCode(vcode, phone_num) {
    return $.ajax({
        url: config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode + config.time,
        type: "POST",
        async: false,
        data: JSON.stringify({
            mobilePhone: phone_num,
            code: vcode
        }),
        dataType: "text",
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode + config.time);
        }
    });
}

function countPayback(obj) {
    $.ajax({
        url: config.api_path + "app/saveCost" + config.time,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            term: obj.term,
            amt: obj.amount,
            memberId: member.id
        }),
        dataType: "json",
        success: function (json) {
            $("#each-term").html(json.payBackEachTerm);
            $("#saved").html(Math.round(json.savedCost * 100) / 100);
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "app/saveCost" + config.time);
        }
    });
}

function applyLoan(obj) {
    return $.ajax({
        url: config.api_path + "app" + config.time,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            term: obj.term,
            amt: obj.amount,
            memberId: member.id,
            creditCarNo: obj.credit_card
        }),
        dataType: "text",
        error: function () {
            if (config.debug)
                alert(config.api_path + "app" + config.time);
        }
    });
}

function numberWithCommas(x) {
    x = x.toString();
    var pattern = /(-?\d+)(\d{3})/;
    while (pattern.test(x))
        x = x.replace(pattern, "$1,$2");
    return x;
}

function loanToThisCard(card_num) {
    return $.ajax({
        url: config.api_path + "app/members/" + member.id + "/creditCard/" + card_num + config.time,
        type: "POST",
        data: card_num,
        dataType: "text",
        error: function () {
            alert(config.api_path + "app/members/" + member.id + "/creditCard/" + card_num + config.time);
        }
    });
}

function addCreditCard(new_card) {
    new_card = new_card.replace(/ /g, "");
    return $.ajax({
        url: config.api_path + "members/" + member.id + "/creditCards/" + new_card + config.time,
        type: "POST",
        async: false,
        dataType: "text",
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id + "/creditCards/" + new_card + config.time);
        }
    });
}

function addOptions(element_id, json) {
    var tmp = [];
    for (var i in json) {
        tmp += "<option value=" + json[i].key + ">" + json[i].value + "</option>";
    }
    $("#" + element_id).append($(tmp)).selectmenu().selectmenu("refresh");
}

function formatDate(ms,type) {
    var date = new Date(ms);
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDay();
    var hour = date.getHours();
    var minute = date.getMinutes();

    var result = "";
    if(type == "2") {
        result = (month+1)+"-"+day+" "+hour+":"+minute;
    }
    return result;
}

function getReadableDate(million_seconds){
    var date = new Date(million_seconds);
    var month = date.getMonth()+1;
    var day = date.getDate();
    var year = date.getFullYear();
    return [year, month, day];
}

function requestAvilable() {
    $("#request").css("background-color", "#3ca0e6");
}

function returnFootPrint(id, status) {
    $.ajax({
        url: config.api_path + "members/" + id + "/status/" + status,
        type: "GET",
        async: false,
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + id + "/status/" + status);
        }
    });
}

function shareToChat() {
    WeixinJSBridge.invoke("sendAppMessage",{
        "appid": share.appid,
        "img_url": share.img_url,
        "img_width": "200",
        "img_height": "150",
        "link": share.link,
        "desc": share.description,
        "title": share.title
    }, function(res) {
        if (config.debug) {
            alert(res);
        }
    });
}

function shareToTimeline() {
    WeixinJSBridge.invoke("shareTimeline",{
        "img_url": share.img_url,
        "img_width": "200",
        "img_height": "150",
        "link": share.link,
        "desc": share.description,
        "title": share.title
    }, function(res) {
        if (config.debug) {
            alert(res);
        }
    });
}

function shareToSina() {
    share_to('tsina');
    return false;
}

function shareToTencent() {
//    WeixinJSBridge.invoke("shareWeibo",{
//        "content": share.discription,
//        "url": share.link,
//    }, function(res) {
//        if (config.debug) {
//            alert(res);
//        }
//    });
    share_to('tqq');
    return false;
}

function shareToQzone() {
    share_to('qzone');
    return false;
}

function shareToRenren() {
    share_to('renren');
    return false;
}

// Actions
$(document).on("pagecreate", function() {
    if (member.gender == 1) {
        $(".gender").html("娘子");
    }
});

$(document).on("pagecreate", "#limit", function () {
    if (!dict.bincode) {
        getBincode();
    }

    if (member.id_card) {
        $("#front-num").html(member.id_card).css("color", "#222222");
        $("#front-upload").attr("disabled", true);
        $("#tip-front").attr("src", "resources/img/public/correct.png");
    }
    else {
        $("#front-upload").change(function (e) {
            //$.mobile.loading("show", {html: "<span><center><img src='resources/img/other_icons/loading.png'></center></span>"});
            $("#front-num").html("正在识别...").css("color", "#222222");
            var form_data = new FormData();
            form_data.append("idCardFrontFile", e.target.files[0]);
            recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardFront", "json").success(function (json) {
                $("#front-num").html(json.idNo).css("color", "#222222");
                $("label[for='front-upload']").css("border-color", "#c0c0c0");
                $("#tip-front").attr("src", "resources/img/public/correct.png");
                $("#front-upload").attr("disabled", true);
                member.id_card = json.idNo;
                member.gender = json.sex;
                if (member.gender == 1) {
                    $(".gender").html("娘子");
                }
            }).error(function () {
                $("#front-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                $("label[for='front-upload']").css("border-color", "#cc0000");
                $("#tip-front").attr("src", "resources/img/public/wrong.png");
            });
        });
    }

    if (member.valid_thru) {
        $("#back-num").html("有效期至" + member.valid_thru).css("color", "#222222");
        $("#back-upload").attr("disabled", true);
        $("#tip-back").attr("src", "resources/img/public/correct.png");
    }
    else {
        $("#back-upload").change(function (e) {
            $("#back-num").html("正在识别...").css("color", "#222222");
            var form_data = new FormData();
            form_data.append("idCardBackFile", e.target.files[0]);
            recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardBack", "text").success(function (text) {
                $("#back-num").html("有效期至" + text).css("color", "#222222");
                $("label[for='back-upload']").css("border-color", "#c0c0c0");
                $("#tip-back").attr("src", "resources/img/public/correct.png");
                $("#back-upload").attr("disabled", true);
                member.valid_thru = text;
            }).error(function () {
                $("#back-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                $("label[for='back-upload']").css("border-color", "#cc0000");
                $("#tip-back").attr("src", "resources/img/public/wrong.png");
            });
        });
    }

    $("#credit-card").on("keyup", function (e) {
        $("#card-tip").hide();
        $("#tip-credit").css({"height": "22px", "width": "32px"});
        var num = $(this).val();
        if (num)
            $("#credit-num").hide();
        else
            $("#credit-num").show();

        setCardIcon("tip-credit", num);

        if(validateCardNo(num)) {
            if (whetherUsedCard(num)) {
                $("#tip-credit").attr("src", "resources/img/public/wrong.png").css({"height": "22px", "width": "22px"});
                $("#card-tip").show();
            }
            else {
                if (!member.anothertest) {
                    $("#next-step").attr("href", "#basic-info").css("background-color", "#3ca0e6");
                }
                else{
                    $("#next-step").attr("href", "#result").css("background-color", "#3ca0e6").off("click").click(function () {
                        member.credit_card = num;
                        testLimit();
                    });
                }
                member.credit_card = num.replace(/ /g, "");
            }
        }
        else {
            $("#next-step").attr("href", "#");
        }

        if (num.length % 5 == 4) {
            if (e.keyCode != 8)
                $(this).val(num + " ");
            else
                $(this).val(num.slice(0, num.length - 1));
        }
    });

    if (member.credit_card) {
        $("#credit-card").val(member.credit_card);
        $("#tip-credit").attr("src", localStorage.getItem("card_icon"));
        $("#credit-num").hide();
        $("#next-step").css("background-color", "#3ca0e6").attr("href", "#basic-info");
    }
});

$(document).on("pageshow", "#limit", function(){
    if (member.anothertest) {
        $("#credit-card").val("").focus().trigger("tap");
        $("#tip-credit").attr("src", "resources/img/card_icon/card.png");
    }

    if (member.isnew) {
        $("#pop-limit").popup("open");
    }
});

$(document).on("pagecreate", "#basic-info", function(){
    if(!dict.industry){
        $.getJSON(config.api_path + "dict/industry", function(json){
            addOptions("industry-select", json);
            dict.industry = json;
        });
    }

    if(!dict.education){
        $.getJSON(config.api_path + "dict/education", function(json){
            addOptions("education-select", json);
            dict.education = json;
        });
    }

    if (member.industry) {
        $("#industry-select option[value='" + member.industry + "']").attr("selected", "selected");
    }

    $("#industry-select").change(function () {
        $("#industry-txt").hide();
        member.industry = $(this).val();
        localStorage.setItem("industry", $(this).val());
        enableLimitTest("hand-in");
    });

    if (member.education) {
        $("#education-select option[value='" + member.education + "']").attr("selected", "selected");
    }

    $("#education-select").change(function () {
        $("#education-txt").hide();
        member.education = $(this).val();
        localStorage.setItem("education", $(this).val());
        enableLimitTest("hand-in");
    });

    $("#email").keyup(function(){
        if($(this).val().length)
            $("#email-txt").hide();
        else
            $("#email-txt").show();

        member.email = $(this).val();
        enableLimitTest("hand-in");
    });
});

$(document).on("pagecreate", "#result", function(){
    $("#option-2").click(function(e){
        $("#share").show();
    });

    $("#share-close").click(function () {
        $("#share").hide();
    });

    $("#option-3").click(function(){
        $("#next-step").attr("href", "#");
        member.anothertest = true;
    });

    share.img_url = "../img/8-1/sword.png";
    share.link = window.location;
    share.description = "么么贷的描述, 暂缺";
    share.title = "么么贷的title, 暂缺";
    share.appid = "";

    $("#share-wechat").click(function () {
        shareToChat();
    });

    $("#share-circle").click(function () {
        shareToTimeline();
    });

    $("#share-sina").click(function () {
        shareToSina();
    });

    $("#share-tencent").click(function () {
        shareToTencent();
    });

    $("#share-qzone").click(function () {
        shareToQzone();
    });

    $("#share-renren").click(function () {
        shareToRenren();
    });
});

$(document).on("pagebeforeshow", "#result", function(){
    getCreditLimit();
    getAvlCrl();

    if (member.status == "11" || member.status == "12"){
        member.limit = 0;
        member.rank = 0;
    }

    $("#amt-shown").html(numberWithCommas(member.limit));
    $("#rank-shown").html(Math.round(member.rank * 100) + "&#37");
    if(member.limit > 4000){
        $("#rank-cmt").html("，官人您是权贵啊！");
        $("#option-1").html("巨款啊！现在就去申请借款");
        $("#option-2").html("去跟小伙伴嘚瑟一下");
        $("#option-3").html("换张信用卡再试试");
    }

    whetherLoanable();
    if (member.loanable) {
        $("#option-1").attr("href", "#loan").css("background-color", "#3ca0e6");
    }
    else {
        $("#option-1").attr("href", "#");
    }

    if (member.status == "5.1") {
        $("#option-1").attr("href", "#congratulation").css("background-color", "#3ca0e6");
    }
    else if (member.status == "5.2") {
        $("#option-1").attr("href", "#fail").css("background-color", "#3ca0e6");
    }

    $("#option-3").tap(function () {
        member.anothertest = 1;
    });
});

$(document).on("pagecreate", "#loan", function () {
    if (!dict.bincode) {
        getBincode();
    }

    $("#request").click(function(){
        if($("#agree").is(":checked") && member.validate && app.term && app.amount){
            if (member.existingFlag == 2) {
                $("#cardlist-2").popup("open");
            }
            else {
                app.credit_card = "";
                applyLoan(app);
                $.mobile.navigate("#suspension");
                $(this).off("click");
            }
        }
    });

    $("#agree").click(function () {
        if($("#agree").is(":checked") && member.validate && app.term && app.amount)
            requestAvilable();
    });

    $("#ok").click(function() {
        $("#out-of-area").hide();
        $("#request").off("click");
    });

    $("#Y-2").click(function(){
        applyLoan(app);
    });

    $("#N-2, #close-4").click(function(){
        $("#card-confirm-2").hide();
    });

    $("#add-another-2").click(function(){
        $("#cardlist-2").popup("close");
        $("#card-add-box-2").show();
    });

    $("#return-2").click(function(){
        $("#card-add-box-2").hide();
    });

    $("#addcard-2").click(function(){
        var card_num = $("#new-cardnum-2").val();
        if (validateCardNo(card_num)) {
            addCreditCard($("#new-cardnum-2").val()).success(function(text){
                if (text == "true") {
                    $("#varifying-tips h4").html("您的信用卡添加成功!");
                    $("#varifying-tips").show();
                    var icon_src = getCardIconSrc(card_num.replace(/ /g, "").slice(0, 6));

                    var tmp = "<div class='card-container' style='line-height: 40px'><img src='" + icon_src + "' class='card-in-list'><div style='float:right; line-height:40px; padding-right: 30px; font-size: 1.5em'>" + card_num + "</div></div><hr>";

                    $("#cardlist-2").prepend($(tmp));
                }
                else {
                    $("#varifying-tips h4").html("您的信用卡添加失败!");
                    $("#varifying-tips").show();
                }
                $("#card-add-box-2").hide();
                member.creditcard = 0;
            }).error(function(){
                $("#new-cardnum-2-placeholder").html("不可用的信用卡号!").css("color", "#cc0000");
            });
        }
        else {
            $("#new-cardnum-2-placeholder").html("错误的信用卡号!").css("color", "#cc0000");
        }
    });

    $("#close").click(function() {
        $("#cardlist-2").popup("close");
    });

    $("#close-2").click(function() {
        $("#card-add-box-2").hide();
    });

    $("#varifying-tips a").click(function () {
        $("#varifying-tips").hide();
    });
});

$(document).on("pagebeforeshow", "#loan", function () {
    if (!member.avlcrl) {
        getAvlCrl();
    }
    $("#loan-limit").html(numberWithCommas(Math.round(member.avlcrl)));

    if(member.existingFlag == 2){
        $("#request").html("确认, 去选择信用卡");
    }

    if(member.mobile_varified){
        $("#varifying").remove();
        member.validate = 1;
    }
    else{
        $("#acquire-code").off("click").click(function(){
            var phone_num = $("#phone").val();
            if(phone_num.length != 11) {
                $("#varifying-tips h4").html("请输入正确的手机号码!");
                $("#varifying-tips").show();
            }
            else{
                $.get(config.api_path + "dict/mobileArea/" + phone_num, function(text){
                    if(text == "北京" || text == "上海" || text == "广州" || text == "深圳") {
                        member.phone = phone_num;
                        sendVarificationCode(member.phone).success(function(){
                            $("#varifying-tips h4").html("您的验证码已发送!");
                            $("#varifying-tips").show();
                        }).error(function () {
                            $("#varifying-tips h4").html("您的验证码发送失败!");
                            $("#varifying-tips").show();
                        });
                    }
                    else {
                        $("#out-of-area").show();
                        returnFootPrint(member.id, "-1");
                    }
                });
            }
        });

        $("#phone").off("keyup").keyup(function(){
            if($(this).val())
                $("#phone-txt").hide();
            else
                $("#phone-txt").show();
        });

        $("#code").off("keyup").keyup(function(){
            var vcode = $(this).val();
            if(vcode)
                $("#code-txt").hide();
            else
                $("#code-txt").show();

            if(vcode.length == 6 && member.phone.length == 11){
                matchVarificationCode(vcode, member.phone).success(function(text){
                    if("true" == text){
                        $("#varifying-tips h4").html("您已输入有效的验证码!");
                        $("#varifying-tips").show();
                        member.validate = true;
                        if($("#agree").is(":checked") && member.validate && app.term && app.amount)
                            requestAvilable();
                    }
                    else {
                        $("#varifying-tips h4").html("验证码错误!");
                        $("#varifying-tips").show();
                    }
                });
            }
        });
    }

    $("#phone").off("click").click(function () {
        if($(this).val.length > 0)
            $("#phone-txt").hide();
        else
            $("#phone-txt").show();
    });

    app.term = "3";
    $("#amount").val("").off("keyup").keyup(function(){
        var tmp = $("#amount").val();
        if(tmp.length > 0)
            $("#amount-txt").hide();
        else
            $("#amount-txt").show();

        if (tmp.length > 3 && parseInt(tmp, 10) % 100) {
            $(this).val(parseInt(tmp, 10) - (parseInt(tmp, 10) % 100));
        }

        if(parseInt(tmp, 10) > parseInt(member.avlcrl, 10))
            $(this).val(parseInt(member.avlcrl));

        if ($(this).val().length > 3) {
            app.amount = $(this).val();
            countPayback(app);
        }

        if($("#agree").is(":checked") && member.validate && app.term && app.amount)
            requestAvilable();
    });

    $("#term-3").off("click").click(function(){
        if (app.term != "3"){
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            app.term = "3";
            app.amount = $("#amount").val();
            countPayback(app);
        }
    });

    $("#term-6").off("click").click(function(){
        if (app.term != "6") {
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            app.term = "6";
            app.amount = $("#amount").val();
            countPayback(app);
        }
    });

    if(!member.creditcard){
        member.creditcard = [];
        $.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
            var tmp = [];
            $.each(data, function(ind, obj){
                var src = getCardIconSrc(obj.bank);
                tmp += "<div class='card-container' style='line-height: 40px'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding-right: 30px; font-size: 1.5em'>" + obj.cardNo + "</div></div><hr>";
                member.creditcard.push([obj.cardNo, obj.bank]);
            });
            $("#cardlist-2").prepend($(tmp));
        });
    }

    $("#new-cardnum-2").off("keyup").keyup(function (e) {
        $("new-cardnum-2-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
        var tmp = $(this).val();
        if(tmp.length > 0)
            $("#new-cardnum-2-placeholder").hide();
        else
            $("#new-cardnum-2-placeholder").show();

        setCardIcon("tip-new-cardnum-2", tmp);

        if (tmp.length % 5 == 4) {
            if (e.keyCode != 8)
                $(this).val(tmp + " ");
            else
                $(this).val(tmp.slice(0, tmp.length - 1));
        }
    });
});

$(document).on("tap", ".card-container", function () {
    app.credit_card = $(this).children("div").html();
    $("#cardlist-2").popup("close");
    setTimeout(function () {
        $("#card-confirm-2").show();
    }, 200);
    $("#num-tail").html(app.credit_card.slice(app.credit_card.length - 4, app.credit_card.length));
});

$(document).on("tap", ".card-container-0", function () {
    app.credit_card = $(this).children("div").html();
    $("#cardlist").popup("close");
    setTimeout(function () {
        $("#card-confirm").show();
    }, 200);
    $("#num-tail-0").html(app.credit_card.slice(app.credit_card.length - 4, app.credit_card.length));
});

$(document).on("pagebeforeshow", "#congratulation", function(){
    getAppNo();
    if (!dict.bincode) {
        getBincode();
    }

    $.ajax({
        url: config.api_path + "app/" + member.appNo + config.time,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (json) {
            $("#amt-x").html(numberWithCommas(json.amt));
            $("#term-shown").html(json.term);
            $("#each-x").html("&yen;" + Math.round(json.repayPerTerm * 100)/100).css({"color": "black", "font-family": "avrial"});
            $("#saved-x").html("&yen;" + Math.round(json.saveCost * 100)/100).css({"color": "black", "font-family": "avrial"})
        },
        error: function () {
            alert(config.api_path + "app/" + member.appNo + config.time);
        }
    });

    if(!member.creditcard){
        member.creditcard = [];
        $.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
            var tmp = [];
            $.each(data, function(ind, obj){
                var src = getCardIconSrc(obj.bank);
                tmp += "<div class='card-container-0' style='line-height: 40px'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding:0 30px; font-size: 1.5em'>" + obj.cardNo + "</div></div><hr>";
                member.creditcard.push([obj.cardNo, obj.bank]);
            });
            $("#cardlist").prepend($(tmp));
        });
    }

    $("#new-cardnum").off("keyup").keyup(function (e) {
        $("new-cardnum-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
        var tmp = $(this).val();

        if(tmp.length > 0)
            $("#new-cardnum-placeholder").hide();
        else
            $("#new-cardnum-placeholder").show();

        setCardIcon("tip-new-cardnum", tmp);

        if (tmp.length % 5 == 4) {
            if (e.keyCode != 8)
                $(this).val(tmp + " ");
            else
                $(this).val(tmp.slice(0, tmp.length - 1));
        }
    });

    $("#Y").off("click").click(function(){
        loanToThisCard(app.credit_card).success(function(){});
    });

    $("#N, #close-3").off("click").click(function(){
        $("#card-confirm").hide();
    });

    $("#add-another").off("click").click(function(){
        $("#cardlist").popup("close");
        $("#card-add-box").show();
    });

    $("#return").off("click").click(function(){
        $("#card-add-box").hide();
    });

    $("#addcard").off("click").click(function(){
        var card_num = $("#new-cardnum").val();
        if (validateCardNo(card_num)) {
            addCreditCard($("#new-cardnum").val()).success(function(){
                alert("您的信用卡添加成功!");
                $("#card-add-box").hide();

                var icon_src = getCardIconSrc(card_num.replace(/ /g, "").slice(0, 6));

                var tmp = "<div class='card-container-0' style='line-height: 40px'><img src='" + icon_src + "' class='card-in-list'><div style='float:right; line-height:40px; padding-right: 30px; font-size: 1.5em'>" + card_num + "</div></div><hr>";

                $("#cardlist-2").prepend($(tmp));

            }).error(function(){
                $("#new-cardnum-placeholder").html("不可用的信用卡号!").css("color", "#cc0000");
            });
        }
        else {
            $("#new-cardnum-placeholder").html("错误的信用卡号!").css("color", "#cc0000");
        }
    });

    $("#close-0").off("click").click(function() {
        $("#cardlist").popup("close");
    });

    $("#close-1").off("click").click(function() {
        $("#card-add-box").hide();
    });
});

$(document).on("pagecreate", "#repayment-0", function () {
    $.ajax({
        url: config.api_path + "account/members/" + member.id,
        type: "GET",
        async: false,
        dataType:"json",
        success: function(json){
            member.loan = json;
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "account/members/" + member.id);
        }
    });

    generateCarousels(member.loan);
    generateLoanSum(member.loan);
});

function generateCarousels(loanSummary) {
    if(!loanSummary) {
        return;
    }
    var loans = loanSummary.loans;
    var contentHtml = "";
    for (var i = 0, len = loans.length; i < len; i++) {
        var loan = loans[i];
        contentHtml += "<div class='repayment-item'>" + generateItemLoan(loan,i) + "</div>";
    }
    $("#container").html(contentHtml);

    sliderPage();

    $(".repay-item-detail").off("tap").tap(function () {
        var index = $(this).attr("index");
        var curLoan = loans[index];
        var detailInfo = [];
        detailInfo.push(getReadableDate(curLoan.startDate).join("-"));
        detailInfo.push("尾号" + curLoan.creditCardNo.slice(curLoan.creditCardNo.length - 4, curLoan.creditCardNo.length));
        detailInfo.push(curLoan.amount);
        detailInfo.push(curLoan.term + "期");
        detailInfo.push("每月" + getReadableDate(curLoan.startDate)[2] + "日");
        detailInfo.push(curLoan.dueAmt);
        detailInfo.push(curLoan.restPrincipal.toFixed(2));

        var $aim = $("#loan-specific");
        $("#loan-specific").find(".loan-d-r").each(function(i,item){
            $(item).html(detailInfo[i]);
        });
        $aim.popup("open");
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
}

function generateItemLoan(loan,index) {
    var contentHtml = "";
    var status = loan.status;
    // summary
    if(status == 1) { // 逾期
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+getReadableDate(loan.startDate).join("-")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+loan.amount+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount-delay\">\n"+
            "    <div class=\"repay-amt-title\">最近应还金额</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+loan.dueAmt+"</span></div>\n"+
            "    <div class=\"repay-amt-limit\"><span class=\"r-deadline\">"+getReadableDate(loan.startDate)[1] + "月" + getReadableDate(loan.startDate)[2] + "日"+"</span>到期，已逾期</div>\n"+
            "</div>\n"+
            "<div class=\"repay-item\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-4.png');\"></div><div class=\"repay-info\">现在就去还款<span>(已逾期，请速速还)</span></div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-expand\"></div></div>\n";
    } else if(status == 9) { // 结清
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+getReadableDate(loan.startDate).join("-")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+loan.amount+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount\">\n"+
            "    <div class=\"repay-amt-title\">该笔借款已还清，总额：</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+loan.dueAmt+"</span></div>\n"+
            "</div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-expand\"></div></div>\n";
    } else {
        contentHtml = "<div class=\"repay-summary\">\n"+
            "    <div class=\"repay-s-time\">"+getReadableDate(loan.startDate).join("-")+"</div>\n"+
            "    <div class=\"repay-s-info\">借款&yen;"+loan.amount+"入卡片"+loan.creditCardNo.substring(loan.creditCardNo.length-4)+"</div>\n"+
            "</div>\n"+
            "<div class=\"repay-amount\">\n"+
            "    <div class=\"repay-amt-title\">最近应还金额</div>\n"+
            "    <div class=\"repay-amt-num\">&yen;<span class=\"r-next\">"+loan.dueAmt+"</span></div>\n"+
            "    <div class=\"repay-amt-limit\"><span class=\"r-deadline\">"+getReadableDate(loan.startDate)[1] + "月" + getReadableDate(loan.startDate)[2] + "日"+"</span>到期</div>\n"+
            "</div>\n"+
            "<div class=\"repay-item\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-4.png');\"></div><div class=\"repay-info\">现在就去还款</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-detail\" index=\""+index+"\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-3.png');\"></div><div class=\"repay-info\">查看借款详情</div><div class=\"replay-collapse\"></div></div>\n"+
            "<div class=\"repay-item repay-item-history\"><div class=\"repay-detail\" style=\"background-image: url('resources/img/other_icons/9-3-2.png');\"></div><div class=\"repay-info\">历史还款记录</div><div class=\"replay-collapse\"></div></div>\n";
    }
    // history
    var repayList = loan.repayList;
    if(repayList && repayList.length != 0) {
        contentHtml += "<ul class=\"repay-history\">";
        for(var i = 0,len = repayList.length;i < len; i++) {
            var repayItem = repayList[i];
            contentHtml += "<li class=\"repay-h-item\">\n"+
                "<div class=\"repay-h-term\">\n"+
                "    <div class=\"repay-h-index\">第"+repayItem.termNo+"期还款</div>\n"+
                "    <div class=\"repay-h-time\">"+formatDate(repayItem.createTime,"2")+"</div>\n"+
                "</div>\n"+
                "<div class=\"repay-h-status\">\n"+
                "    <div class=\"repay-h-amt\">"+repayItem.amt.toFixed(2)+"</div>\n"+
                "    <div class=\"repay-h-sta\">还款成功</div>\n"+
                "</div>\n"+
                "<div class=\"repay-h-img\"></div>\n"+
                "</li>";
        }
        contentHtml += "</ul>";
    }
    return contentHtml;
}

function sliderPage() {
    var $items = $(".repayment-item");
    $items.current = $items[0];

    $items.prev = function() {
        if ($items.current != $items[0]) {
            $(".container").css("top", "0").animate({"left": "+=" + $width});
            var ind = $items.index($items.current);
            $(".spot:eq(" + ind + ")").removeClass("spot-chosen");
            $(".spot:eq(" + (ind - 1) + ")").addClass("spot-chosen");
            $items.current = $items[ind - 1];
        }
    };

    $items.next = function() {
        if ($items.current != $items[$items.length - 1]) {
            $(".container").css("top", "0").animate({"left": "-=" + $width});
            var ind = $items.index($items.current);
            $(".spot:eq(" + ind + ")").removeClass("spot-chosen");
            $(".spot:eq(" + (ind + 1) + ")").addClass("spot-chosen");
            $items.current = $items[ind + 1];
        }
    };

    var $width = $(document).width();
    $items.css({"width":$width*0.9,"margin-left":$width*0.05,"margin-right":$width*0.045});
    $(".container").css("width", $items.length * $width);
//    $items.css("width", $width);

    var tmp = "";
    for(var i = 0, len = $items.length; i < len; i++) {
        tmp += "<div class='spot'></div>";
    }
    $("#spots").css("width", 26 * $items.length + "px").append(tmp);
    $(".spot:eq(0)").addClass("spot-chosen");

    $(".repayment-item:not(:first, :last)").on({
        swipeleft: $items.next,
        swiperight: $items.prev
    });
    $(".repayment-item:first").on("swipeleft", $items.next);
    $(".repayment-item:last").on("swiperight", $items.prev);
}

function generateLoanSum(info) {
    if(info) {
        $("#total-amount").text(info.totalAmount);
        $("#total-times").text(info.loanCount);
        $("#total-payback").text(info.totalDueAmt);
        $("#total-saved").text(info.totalSavedCost.toFixed(2));
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
                "            <span class=\"sum-r-r\">"+getReadableDate(loan.applyDate).join("-")+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">借款金额:</span>\n"+
                "            <span class=\"sum-r-r\">&yen;"+loan.amount+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">注入卡片:</span>\n"+
                "            <span class=\"sum-r-r\">尾号"+loan.creditCardNo.substring(loan.creditCardNo.length - 4)+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">总计应还:</span>\n"+
                "            <span class=\"sum-r-r\">&yen;"+loan.dueAmt+"</span>\n"+
                "        </li>\n"+
                "        <li class=\"sum-r-item\">\n"+
                "            <span class=\"sum-r-l\">较信用卡最低还款金额:</span>\n"+
                "            <span class=\"sum-r-r\">约省&yen;"+loan.savedCost.toFixed(2)+"</span>\n"+
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

$(document).on("pagecreate", "#fail", function () {
    $("#got-it-y").on("vclick", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#full", function () {
    $("#got-it-z").on("vclick", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#suspension", function () {
    $("#got-it").on("vclick", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#patience", function () {
    $.ajax({
        url: config.api_path + "app/members/" + member.id + "/progress",
        type: "GET",
        dataType: "text",
        success: function(text) {
            $("#hours").html(Math.round(parseFloat(text) * 48));
        },
        error: function() {
            if (config.debug)
                alert(config.api_path + "app/members/" + member.id + "/progress");
        }
    });

    $("#got-it-x").on("vclick", function () {
        WeixinJSBridge.call("closeWindow");
    });
});

$(document).on("pagecreate", "#feedback", function () {
    $("#fd-textarea").focus(function(){
        $(this).next().hide();
    }).blur(function(){
        if($.trim($(this).val()) == "") {
            $(this).next().show();
        }
    });

    $("#fd-textarea").next().tap(function(){
        $(this).hide();
        $(this).prev().focus();
    });

    $("#feedback a").tap(function () {
        var tmp = $.trim($("#fd-textarea").val());
        if(tmp) {
            $.ajax({
                url: config.api_path + "members/" + member.id + "/feedback?f=" + tmp,
                type: "POST",
                success: function () {},
                error: function () {
                    if (config.debug)
                        alert(config.api_path + "members/" + member.id + "/feedback");
                }
            });
        }
    });
});

window.onunload = function () {
    var print_status;
    var pattern = /#\w+\?/;
    var hash = pattern.exec(window.location).toString();
    hash = hash.slice(0, hash.length - 1);

    if (member.status == "0" && (hash == "#limit" || hash == "#basic-info")) {
        print_status = "0";
    }
    else if (hash == "#result" && status == "3.1") {
        print_status = "1";
    }
    else if (hash == "#congratulation" && (!app.credit_card)) {
        print_status = "2";
    }
    else {
        print_status = "-9";
    }

    if (print_status != "-9")
        returnFootPrint(member.id, print_status);

    if ((member.status == "1" || member.status == "2") && (hash == "#limit" || hash == "#basic-info")) {
        if (member.id_card) {
            localStorage.setItem("idcard_front", member.id_card);
        }
        if (member.valid_thru) {
            localStorage.setItem("valid_thru", member.valid_thru);
        }
        if (member.credit_card) {
            localStorage.setItem("credit_card", member.credit_card);
            localStorage.setItem("card_icon", $("#tip-credit").attr("src"));
        }
        if (member.education) {
            localStorage.setItem("education", member.education);
        }
        if (member.industry) {
            localStorage.setItem("industry", member.industry);
        }
        if (member.email) {
            localStorage.setItem("email", member.email);
        }
    }
    else {
        localStorage.clear();
    }
};

console.log("END!");