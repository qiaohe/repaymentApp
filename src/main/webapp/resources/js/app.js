/* global $:false */
/* global WeixinJSBridge:false */
/* global alert:false */
/* global config:false */
/* global member:false */
/* global console:false */
"use strict";
$(function () {
// START    
    var app = {};
    var dict = {};

// Methods
    function getCreditLimit() {
        $.ajax({
            url: config.api_path + "members/" + member.id + "/crl",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (json) {
                member.limit = json.creditLimit;
                member.rank = json.rankOfLimit;
            },
            error: function () {
                if (config.debug)
                    alert(config.api_path + "members/" + member.id + "/crl");
            }
        });
    }

    function getAvlCrl() {
        $.ajax({
            url: config.api_path + "members/" + member.id + "/avlCrl",
            type: "GET",
            dataType: "text",
            async: false,
            success: function (text) {
                member.avlcrl = text;
            },
            error: function () {
                if (config.debug)
                    alert(config.api_path + "members/" + member.id + "/avlCrl");
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
        });
    }

    function getCardIconSrc(bankNo) {
        var img_path = "resources/img/card_icon/", icon_src;
        switch(bankNo){
            case 1:
                icon_src = img_path + "icbc.png";
                break;
            case 2:
                icon_src = img_path + "abc.png";
                break;
            case 3:
                icon_src = img_path + "bc.png";
                break;
            case 4:
                icon_src = img_path + "cbc.png";
                break;
            case 5:
                icon_src = img_path + "ctb.png";
                break;
            case 6:
                icon_src = img_path + "ceb.png";
                break;
            case 7:
                icon_src = img_path + "cgb.png";
                break;
            case 8:
                icon_src = img_path + "hxb.png";
                break;
            case 9:
                icon_src = img_path + "cmsb.png";
                break;
            case 10:
                icon_src = img_path + "pingan.png";
                break;
            case 11:
                icon_src = img_path + "cib.png";
                break;
            case 12:
                icon_src = img_path + "cmb.png";
                break;
            case 13:
                icon_src = img_path + "spdb.png";
                break;
            case 14:
                icon_src = img_path + "citic.png";
                break;
            case 15:
                icon_src = img_path + "psbc.png";
                break;
            default:
                icon_src = img_path + "card.png";
        }
        return icon_src;
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
        return $.ajax({
            url: config.api_path + "members/" + member.id + "/creditCard/" + card_num.replace(/ /g, ""),
            type: "GET",
            async: false,
            dataType: "text"
        });
    }

    function testLimit(obj) {
        $.ajax({
            url: config.api_path + "members/" + member.id,
            type: "POST",
            async: false,
            contentType: "application/json",
            data: JSON.stringify({
                creditCarNo: obj.card_num.replace(/ /g, ""),
                industry: obj.industry,
                education: obj.education,
                email: obj.email,
                billEmail: obj.bill_email,
                billPassword: obj.password
            }),
            dataType: "json",
            success: function (json) {
                member.limit = json.creditLimit;
                member.rank = json.rankOfLimit;
                member.anothertest = 0;
            },
            error: function () {
                if (config.debug)
                    alert(config.api_path + "members/" + member.id);
            }
        });
    }

    function sendVarificationCode(phone_num) {
        return $.ajax({
            url: config.api_path + "sms/" + phone_num,
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
            url: config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode,
            type: "POST",
            async: false,
            data: JSON.stringify({
                mobilePhone: phone_num,
                code: vcode
            }),
            dataType: "text",
            error: function () {
                if (config.debug)
                    alert(config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode);
            }
        });
    }

    function countPayback(obj) {
        $.ajax({
            url: config.api_path + "app/saveCost",
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
                $("#saved").html(json.savedCost);
            },
            error: function () {
                if (config.debug)
                    alert(config.api_path + "app/saveCost");
            }
        });
    }

    function applyLoan(obj) {
        return $.ajax({
            url: config.api_path + "app",
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
                    alert(config.api_path + "app");
            }
        });
    }

    function loanToThisCard(card_num) {
        return $.ajax({
            url: config.api_path + "app/members/" + member.id + "/creditCard/" + card_num,
            type: "POST",
            data: card_num,
            dataType: "text",
            error: function () {
                alert(config.api_path + "app/members/" + member.id + "/creditCard/" + card_num);
            }
        });
    }

    function addCreditCard(new_card) {
        return $.ajax({
            url: config.api_path + "members/" + member.id + "/creditCards/" + new_card,
            type: "POST",
            async: false,
            dataType: "text",
            error: function () {
                if (config.debug)
                    alert(config.api_path + "members/" + member.id + "/creditCards/" + new_card);
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

// Actions
    $(document).on("pagecreate", "#limit", function () {
        if (member.id_card) {
            $("#front-num").html(member.id_card).css("color", "#222222");
            $("#front-upload").attr("disabled", true);
            $("#tip-front").attr("src", "resources/img/public/correct.png");
        }
        else {
            $("#front-upload").change(function (e) {
                $.mobile.loading("show", {html: "<span><center><img src='resources/img/other_icons/loading.png'></center></span>"});
                var form_data = new FormData();
                form_data.append("idCardFrontFile", e.target.files[0]);
                recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardFront", "json").success(function (json) {
                    $("#front-num").html(json.idNo).css("color", "#222222");
                    $("label[for='front-upload']").css("border-color", "#c0c0c0");
                    $("#tip-front").attr("src", "resources/img/public/correct.png");
                    $("#front-upload").attr("disabled", true);
                    member.id_card = json.idNo;
                    member.gender = json.sex;
                    localStorage.setItem("idcard_front", json.idNo);
                    localStorage.setItem("gender", json.sex);
                }).error(function () {
                    $("#front-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                    $("label[for='front-upload']").css("border-color", "#cc0000");
                    $("#tip-front").attr("src", "resources/img/public/wrong.png");
                }).complete(function () {
                    $.mobile.loading("hide");
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
                $.mobile.loading("show", {html: "<span><center><img src='resources/img/other_icons/loading.png'></center></span>"});
                var form_data = new FormData();
                form_data.append("idCardBackFile", e.target.files[0]);
                recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardBack", "text").success(function (text) {
                    $("#back-num").html("有效期至" + text).css("color", "#222222");
                    $("label[for='back-upload']").css("border-color", "#c0c0c0");
                    $("#tip-back").attr("src", "resources/img/public/correct.png");
                    $("#back-upload").attr("disabled", true);
                    member.valid_thru = text;
                    localStorage.setItem("valid_thru", text);
                }).error(function () {
                    $("#back-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
                    $("label[for='back-upload']").css("border-color", "#cc0000");
                    $("#tip-back").attr("src", "resources/img/public/wrong.png");
                }).complete(function () {
                    $.mobile.loading("hide");
                });
            });
        }

        if (!dict.bincode) {
            getBincode();
        }

        $("#credit-card").on("keyup", function (e) {
            $("#card-tip").hide();
            $("#tip-credit").css({"height": "22px", "width": "32px"});
            var num = $(this).val();
            if (num)
                $("#credit-num").hide();
            else
                $("#credit-num").show();

            if (num.length < 2) {
                $("#tip-credit").attr("src", "resources/img/card_icon/card.png");
            }
            else if (num.length <= 6) {
                if (num[0] == "4") {
                    $("#tip-credit").attr("src", "resources/img/card_icon/visa.png");
                }
                else if (num[0] == "5") {
                    $("#tip-credit").attr("src", "resources/img/card_icon/master.png");
                }
            }
            else if (num.length > 6) {
                $.each(dict.bincode, function (i, val) {
                    if (val.binNo == num.replace(/ /g, "").slice(0, 6)) {
                        var icon_scr = getCardIconSrc(dict.bincode[i].bankNo);
                        $("#tip-credit").attr("src", icon_scr);
                    }
                });
            }

            if(validateCardNo(num)) {
                whetherUsedCard(num).success(function (text) {
                    if (text == "true") {
                        $("#tip-credit").attr("src", "resources/img/public/wrong.png").css({"height": "22px", "width": "22px"});
                        $("#card-tip").show();
                    }
                    else {
                        localStorage.setItem("credit_card", num.replace(/ /g, ""));
                        if (!member.anothertest) {
                            $("#next-step").attr("href", "#basic-info");
                        }
                        else{
                            $("#next-step").attr("href", "#result").click(function () {
                                var obj = {};
                                obj.card_num = num;
                                obj.industry = member.industry;
                                obj.education = member.education;
                                obj.email = member.email;
                                obj.bill_email = member.billemail;
                                obj.password = member.password;
                                testLimit(obj);
                            });
                        }
                    }
                }).error(function () {
                    if (config.debug)
                        alert(config.api_path + "members/" + member.id + "/creditCard/" + num.replace(/ /g, ""));
                });
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

        if (member.card_num) {
            $("#credit-card").val(member.card_num).trigger("keyup");
        }
    });

    $(document).on("pageshow", "#limit", function(){
        if (member.anothertest) {
            $("#credit-card").focus().val("");
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

        if (member.education) {
            $("#education-select option[value='" + member.education + "']").attr("selected", "selected");
        }

        if (member.bill_email) {
            $("#mail-new").val(member.bill_email);
        }

        if (member.password) {
            $("#password").val(member.password);
        }

        $("#industry-select").change(function(){
            member.industry = $(this).val();
            localStorage.setItem("industry", $(this).val);
            $("#industry-txt").hide();
        });

        $("#education-select").change(function(){
            member.education = $(this).val();
            localStorage.setItem("education", $(this).val);
            $("#education-txt").hide();
        });

        $("#email").keyup(function(){
            $("#mail-same").html($(this).val());
            localStorage.setItem("email", $(this).val);
            if($(this).val().length)
                $("#email-txt").hide();
            else
                $("#email-txt").show();

            if($("#industry-select").val() && $("#education-select").val() && $(this).val().length > 8){
                $("#hand-in").attr({
                    "href": "#billmail",
                    "data-rel": "popup"
                });
            }
        });

        $("#mail-new").click(function () {
            localStorage.setItem("bill_email", $(this).val);
            $("#radio-new").trigger("click");
        });

        $("#password").click(function () {
            localStorage.setItem("password", $(this).val);
        });

        $("#skip, #continue").click(function(){
            var obj = {};
            obj.card_num = $("#credit-card").val();
            member.cardnum = obj.card_num;
            obj.industry = $("#industry-select").val();
            member.industry = obj.industry;
            obj.education = $("#education-select").val();
            member.education = obj.education;
            obj.email = $("#email").val();
            member.email = obj.email;
            if($("#mail-new").val().length > 8)
                obj.bill_email = $("#mail-new").val();
            else
                obj.bill_email = obj.email;
            member.billemail = obj.blii_email;
            obj.password = $("#password").val();
            member.password = obj.password;
            testLimit(obj);
        });
    });

    $(document).on("pagecreate", "#result", function(){
        $("#option-2").click(function(){
            $("#share").show();
        });

        $("#share").click(function(){
            $(this).hide();
        });

        $("#option-3").click(function(){
            $("#next-step").attr("href", "#?123");
            member.anothertest = true;
        });
    });

    $(document).on("pagebeforeshow", "#result", function(){
        if (!member.limit) {
            getCreditLimit();
        }

        if (!member.avlcrl) {
            getAvlCrl();
        }

        $("#amt-shown").html(member.limit);
        $("#rank-shown").html(Math.round(member.rank * 100) + "&#37");
        if(member.limit > 4000){
            $("#rank-cmt").html("，官人您是权贵啊！");
            $("#option-1").html("巨款啊！现在就去申请借款");
            $("#option-2").html("去跟小伙伴嘚瑟一下");
            $("#option-3").html("换张信用卡再试试");
        }

        if (member.avlcrl > 1000 && !member.applying) {
            $("#option-1").attr("href", "#loan");
        }
        else {
            $("#option-1").attr("href", "#");
        }

        if (member.status == "5.1") {
            $("#option-1").attr("href", "#congratulation");
        }
    });

    $(document).on("pagecreate", "#loan", function () {
        $("#request").click(function(){
            if($("#agree").is(":checked") && member.validate && app.term && app.amount){
                if(member.existingFlag === 0 || member.existingFlag == 1){
                    app.credit_card = "";
                    applyLoan(app);
                    $.mobile.navigate("#suspension");
                    $(this).off("click");
                }
                else {
                    $("#cardlist-2").popup("open");
                }
            }
        });

        $("#ok").click(function() {
            $("#out-of-area").hide();
            $("#request").off("click");
        });

        $("#Y-2").click(function(){
            applyLoan(app);
        });

        $("#N-2").click(function(){
            $("#card-confirm-2").hide();
        });

        $("#add-another-2").click(function(){
            $("#card-add-box-2").show();
            $("#cardlist-2").popup("close");
        });

        $("#return-2").click(function(){
            $("#card-add-box-2").hide();
        });

        $("#addcard-2").click(function(){
            var card_num = $("#new-cardnum-2").val();
            if (validateCardNo(card_num)) {
                addCreditCard($("#new-cardnum-2").val()).success(function(){
                    alert("您的信用卡添加成功!");
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
    });

    $(document).on("pagebeforeshow", "#loan", function () {
        if (!member.avlcrl) {
            getAvlCrl();
        }
        $("#loan-limit").html(Math.round(member.avlcrl));

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
                if(phone_num.length != 11)
                    alert("请输入正确的手机号码!");
                else{
                    $.get(config.api_path + "dict/mobileArea/" + phone_num, function(text){
                        if(text == "北京" || text == "上海" || text == "广州" || text == "深圳") {
                            member.phone = phone_num;
                            sendVarificationCode(member.phone).success(function(){
                                alert("您的验证码已发送!");
                            }).error(function () {
                                    alert("您的验证码发送失败!");
                                }
                            );
                        }
                        else {
                            // alert("您的手机非北上广深号码, 暂时无法为您服务!");
                            $("#out-of-area").show();
                            returnFootPrint(member.id, "0");
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
                            alert("您已输入有效的验证码!");
                            member.validate = true;
                        }
                        else
                            alert("验证码错误!");
                    });
                }
            });
        }
    });

    $(document).on("pageshow", "#loan", function () {
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
        });

        $("#term-3").off("click").click(function(){
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            app.term = "3";
            app.amount = $("#amount").val();
            countPayback(app);
        });

        $("#term-6").off("click").click(function(){
            $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
            $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
            app.term = "6";
            app.amount = $("#amount").val();
            countPayback(app);
        });

        if(!member.creditcard){
            member.creditcard = [];
            $.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
                var tmp = [];
                $.each(data, function(ind, obj){
                    var src = getCardIconSrc(obj.bank);
                    tmp += "<div class='card-container' style='line-height: 40px'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding-right: 30px'>" + obj.cardNo + "</div></div><hr>";
                    member.creditcard.push([obj.cardNo, obj.bank]);
                });
                $("#cardlist-2").prepend($(tmp));
            });
        }

        $("#new-cardnum-2").keyup(function (e) {
            $("new-cardnum-2-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
            var tmp = $(this).val();
            if(tmp.length > 0)
                $("#new-cardnum-2-placeholder").hide();
            else
                $("#new-cardnum-2-placeholder").show();

            if (tmp.length < 2) {
                $("#tip-new-cardnum-2").attr("src", "resources/img/card_icon/card.png");
            }
            else if (tmp.length <= 6) {
                if (tmp[0] == "4") {
                    $("#tip-new-cardnum-2").attr("src", "resources/img/card_icon/visa.png");
                }
                else if (tmp[0] == "5") {
                    $("#tip-new-cardnum-2").attr("src", "resources/img/card_icon/master.png");
                }
            }
            else if (tmp.length > 6) {
                $.each(dict.bincode, function (i, val) {
                    if (val.binNo == tmp.replace(/ /g, "").slice(0, 6)) {
                        var src = getCardIconSrc(dict.bincode[i].bankNo);
                        $("#tip-new-cardnum-2").attr("src", src);
                    }
                });
            }

            if (tmp.length % 5 == 4) {
                if (e.keyCode != 8)
                    $(this).val(tmp + " ");
                else
                    $(this).val(tmp.slice(0, tmp.length - 1));
            }
        });
    });

    $(document).on("click", ".card-container", function () {
        app.credit_card = $(this).children("div").html();
        $("#cardlist-2").popup("close");
        $("#card-confirm-2").show();
        $("#num-tail").html(app.credit_card.slice(app.credit_card.length - 4, app.credit_card.length));
    });

    $(document).on("click", ".card-container-0", function () {
        app.credit_card = $(this).children("div").html();
        $("#cardlist").popup("close");
        $("#card-confirm").show();
        $("#num-tail").html(app.credit_card.slice(app.credit_card.length - 4, app.credit_card.length));
    });

    $(document).on("pagecreate", "#congratulation", function(){
        var appNo_pattern = /(?:appNo=)\d+/;
        var appNo = appNo_pattern.exec(window.location).toString();
        appNo = appNo.slice(6, appNo.length);

        $.ajax({
            url: config.api_path + "app/" + appNo,
            type: "GET",
            async: false,
            dataType: "json",
            success: function (json) {
                $("#amt-x").html(json.amt);
                $("#term-" + json.term + "-shown").css("background-color", "#3ca0e6");
                $("#each-x").html(json.repayPerTerm);
                $("#saved-x").html(json.saveCost);
            },
            error: function () {
                alert(config.api_path + "app/" + appNo);
            }
        });

        if(!member.creditcard){
            member.creditcard = [];
            $.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
                var tmp = [];
                $.each(data, function(ind, obj){
                    var src = getCardIconSrc(obj.bank);
                    tmp += "<div class='card-container-0' style='line-height: 40px'><img src='" + src + "' class='card-in-list'><div style='float:right; line-height:40px; padding-right: 30px'>" + obj.cardNo + "</div></div><hr>";
                    member.creditcard.push([obj.cardNo, obj.bank]);
                });
                $("#cardlist").prepend($(tmp));
            });
        }

        $("#new-cardnum").keyup(function (e) {
            $("new-cardnum-tip").html("请使用您自己的卡片, 否则借款将无法注入").css("color", "#333333");
            var tmp = $(this).val();
            if(tmp.length > 0)
                $("#new-cardnum-placeholder").hide();
            else
                $("#new-cardnum-placeholder").show();

            if (tmp.length < 2) {
                $("#tip-new-cardnum").attr("src", "resources/img/card_icon/card.png");
            }
            else if (tmp.length <= 6) {
                if (tmp[0] == "4") {
                    $("#tip-new-cardnum").attr("src", "resources/img/card_icon/visa.png");
                }
                else if (tmp[0] == "5") {
                    $("#tip-new-cardnum").attr("src", "resources/img/card_icon/master.png");
                }
            }
            else if (tmp.length > 6) {
                $.each(dict.bincode, function (i, val) {
                    if (val.binNo == tmp.replace(/ /g, "").slice(0, 6)) {
                        var src = getCardIconSrc(dict.bincode[i].bankNo);
                        $("#tip-new-cardnum").attr("src", src);
                    }
                });
            }

            if (tmp.length % 5 == 4) {
                if (e.keyCode != 8)
                    $(this).val(tmp + " ");
                else
                    $(this).val(tmp.slice(0, tmp.length - 1));
            }
        });

        $("#Y").click(function(){
            loanToThisCard(app.credit_card).success(function(){});
        });

        $("#N, #close-3").click(function(){
            $("#card-confirm").hide();
        });

        $("#add-another").click(function(){
            $("#card-add-box").show();
        });

        $("#return").click(function(){
            $("#card-add-box").hide();
        });

        $("#addcard").click(function(){
            var card_num = $("#new-cardnum").val();
            if (validateCardNo(card_num)) {
                addCreditCard($("#new-cardnum").val()).success(function(){
                    alert("您的信用卡添加成功!");
                    $("#card-add-box").hide();
                    member.creditcard = 0;
                }).error(function(){
                    $("#new-cardnum-placeholder").html("不可用的信用卡号!").css("color", "#cc0000");
                });
            }
            else {
                $("#new-cardnum-placeholder").html("错误的信用卡号!").css("color", "#cc0000");
            }
        });

        $("#close-0").click(function() {
            $("#cardlist").popup("close");
        });

        $("#close-1").click(function() {
            $("#card-add-box").hide();
        });

    });

    $(document).on("pagecreate", "#repayment-0", function(){
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

        generateSumPage(member.loan);
        generateRepaymentPages(member.loan);
        registerEvents(member.loan);

        function generateSumPage(obj){
            $("#total-amount").html(obj.totalAmount);
            $("#total-times").html(obj.loanCount);
            $("#total-payback").html(obj.totalDueAmt);
            $("#total-saved").html(Math.round(obj.totalSavedCost + 100) / 100);

            for(var i = 0; i < obj.loanCount; i++){
                var tmp = "<li><div class='sl' id='" + ("sl-" + (i + 1)) + "'></div><div class='sr' id='" + ("sr-" + (i + 1)) + "'></div></li>";
                $("#total-specific").append(tmp);
                $("#sl-" + (i + 1)).html(i + 1);
                var readable_date = getReadableDate(obj.loans[i].startDate);
                $("#sr-" + (i + 1)).append("借款日期: " + readable_date + "<br>借款金额: &yen" + obj.loans[i].amount + "<br>注入卡片: 尾号" + obj.loans[i].creditCardNo.slice(obj.loans[i].creditCardNo.length - 4, obj.loans[i].creditCardNo.length) + "<br>总计应还: &yen" + obj.loans[i].dueAmt + "<br>较信用卡最低还款额，约省&yen" + Math.round(obj.loans[i].savedCost * 100) / 100);
            }
        }

        function generateRepaymentPages(obj){
            var loans = obj.loans;
            var templateHtml = $("#repayment-0").html();
            for(var i = 0; i < loans.length; i++){
                var id = "repayment-" + i;
                if(i){
                    var tmp = $("<div data-role='page' id='" + id + "'>" + templateHtml + "</div>");
                    tmp.appendTo($("body"));
                    $.mobile.initializePage();
                }
                var loan = loans[i];
                var creditCardSuffix = loan.creditCardNo.slice(loan.creditCardNo.length - 4, loan.creditCardNo.length);
                $("#" + id + " .repay-s-time").html(getReadableDate(loan.startDate));
                $("#" + id + " .repay-s-info").html("借款&yen;"+loan.amount+"入卡片"+creditCardSuffix);
                $("#" + id + " .r-next").html(loan.dueAmt);
                var date = new Date(loan.startDate);
                var month = date.getMonth()+1;
                var duemonth = month + loan.paidTerm + 1;
                var day = date.getDate();
                if(duemonth > 12) {
                    duemonth -= 12;
                }
                $("#" + id + " .r-deadline").html(duemonth + "月" + day + "日");

                var loanInfo = [getReadableDate(loan.startDate),"尾号" + creditCardSuffix,loan.amount,
                    obj.loanCount + "期(已还" + loan.paidTerm + "期)","每月" + day + "日",loan.principal,loan.restPrincipal];
                $("#"+id+" .r-popup .li-r").each(function(i,e){
                    $(e).html(loanInfo[i]);
                });

                var repayHistory = $("#" + id + " .repay-history");
                $("#" + id + " .repay-item:eq(2)").off("click").on("click",function(){
                    if(repayHistory.is("visible")) {
                        repayHistory.hide();
                    } else {
                        if(repayHistory.attr("load") && repayHistory.attr("load") == "1") {
                            repayHistory.show();
                        } else {
                            loadLoanInfo(id,repayHistory,loan.loanId);
                        }
                    }
                });
                // loadLoanInfo(repayHistory,loan.loanId);
            }
        }

        // load repay plan history
        function loadLoanInfo(containerId,repayHistory,loanId) {
            $.ajax({
                url: config.api_path + "account/loans/" + loanId,
                type: "GET",
                async: false,
                success:function(json){
                    if(!json) {
                        return;
                    }
                    var contentHtml = "";
                    $.each(json,function(i,repayPlan){
                        contentHtml += "<li class=\"repay-h-item\">\n"+
                            "<div class=\"repay-h-term\">\n"+
                            "    <div class=\"repay-h-index\">第"+repayPlan.termNo+"期还款</div>\n"+
                            "    <div class=\"repay-h-time\">"+repayPlan.dueDate+"</div>\n"+
                            "</div>\n"+
                            "<div class=\"repay-h-status\">\n"+
                            "    <div class=\"repay-h-amt\">"+repayPlan.dueAmt+"</div>\n"+
                            "    <div class=\"repay-h-sta\">还款成功</div>\n"+
                            "</div>\n"+
                            "<div class=\"repay-h-img\"></div>\n"+
                            "</li>";
                    });
                    repayHistory.html(contentHtml).attr("load","1");
                    repayHistory.find("li").on("click",function(){
                        var repayPlanDetailPop = $("#"+containerId+" .repay-popup-his");
                        repayPlanDetailPop.find(".li-r").each(function(){

                        });
                    });
                },
                error: function () {
                    if (config.debug)
                        alert(config.api_path + "account/loans/" + loanId);
                }
            });
        }

        function registerEvents(obj){
            if(obj.loans.length > 1){
                $("#repayment-0").on("swipeleft", function(){
                    $.mobile.changePage("#repayment-1", {transition: "slide"});
                });

                for(var i = 1; i < obj.loans.length - 1; i++){
                    registerSlides(i);
                }

                $("#repayment-" + (obj.loans.length - 1)).on("swiperight", function(){
                    $.mobile.changePage("#repayment-" + (obj.loans.length - 2), {transition: "slide", reverse: true});
                });
            }

            for(var j = 0; j < obj.loans.length; j++){
                popLoanSpecific(j);
            }
        }

        function registerSlides(i) {
            $("#repayment-" + i).on("swipeleft", function(){
                $.mobile.changePage("#repayment-" + (i + 1), {transition: "slide"});
            });

            $("#repayment-" + i).on("swiperight", function(){
                $.mobile.changePage("#repayment-" + (i - 1), {transition: "slide", reverse: true});
            });
        }

        function popLoanSpecific(i){
            $("#repayment-" + i + " ul li:nth-child(2)").click(function(){
                $("#repayment-" + i + " .r-popup").popup("open");
            });
        }

        function getReadableDate(million_seconds){
            var date = new Date(million_seconds);
            var month = date.getMonth()+1;
            var day = date.getDate();
            var year = date.getFullYear();
            return year + "-" + month + "-" + day;
        }







    });

    $(document).on("pagecreate", "#sum-loan", function(){
        $("#sum-loan a").click(function(){
            $.mobile.back();
        });
    });




















    $("a").on({
        vmousedown: function(){
            $(this).css("box-shadow", "0 0 5px black");
        }, vmouseup: function(){
            $(this).css("box-shadow", "none");
        }
    });

// $("#got-it, #got-it-x, #got-it-y, #got-it-z").click(function(){
//     WeixinJSBridge.call("closeWindow");
// });

    $(document).on("vclick", "#got-it, #got-it-x, #got-it-y, #got-it-z", function () {
        WeixinJSBridge.call("closeWindow");
    });

    window.onunload = function () {
        var print_status;
        var pattern = /#\w+\?/;
        var hash = pattern.exec(window.location).toString();
        hash = hash.slice(0, hash.length - 1);
        switch(hash){
            case "#basic-info":
                print_status = "0";
                break;
            case "#result":
                print_status = "1";
                break;
            case "#congratulation":
                print_status = "2";
                break;
            default:
                print_status = "-1";
        }
        if (print_status != "-1")
            returnFootPrint(member.id, print_status);
    };

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



    console.log("END!");
// END
});