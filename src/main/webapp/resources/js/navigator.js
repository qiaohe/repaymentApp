/* global $:false */
/* global alert:false */
// Configuration
"use strict";

var config = {};
config.api_path = "api/";
config.debug = false;
config.time = new Date();
config.time = "?time=" + config.time.getTime();

var id_pattern = /(?:memberId=)\d+/;
config.member_id = id_pattern.exec(window.location).toString();
config.member_id = config.member_id.slice(9, config.member_id.length);

// Methods
function getStatus() {
    var status;
    $.ajax({
        url: config.api_path + "members/" + config.member_id + "/status" + config.time,
        type: "GET",
        async: false,
        dataType: "text",
        success: function (text) {
            status = text;
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + config.member_id + "/status");
        }
    });
    return status;
}

function getDestination() {
    var des_pattern = /#[\w-]+\?/;
    var des = des_pattern.exec(window.location).toString();
    des = des.slice(0, des.length - 1);
    return des;
}

function getMemberInfo() {
    $.ajax({
        url: config.api_path + "members/" + member.id + config.time,
        type: "GET",
        dataType: "json",
        async: false,
        success: function (json) {
            member.id_card = json.idCardNo;
            member.valid_thru = json.validThru;
            member.industry = json.industry;
            member.education = json.education;
            member.email = json.email;
            member.mobile_varified = json.hasMobilePhone;
            member.existingFlag = json.existingFlag;
            member.gender = json.sex;
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "members/" + member.id);
        }
    });
}

function whetherApplying() {
    $.ajax({
        url: config.api_path + "app/members/" + member.id + config.time,
        type: "GET",
        dataType: "text",
        async: false,
        success: function(text) {
            member.applying = ("true" == text);
        },
        error: function () {
            if (config.debug)
                alert(config.api_path + "app/members/" + member.id);
        }
    });
}

function navigateThruStatusNDes(status, destination) {
    if (/limit/.test(destination)) {
        if (status= "1") {
            member.isnew = 1;
        }
        else if (parseInt(status) > 2) {
            $.mobile.navigate("#result");
        }
        else{
            var tmp = localStorage.getItem("idcard_front");
            if (tmp) {
                member.id_card = tmp;
            }

            tmp = localStorage.getItem("valid_thru");
            if (tmp) {
                member.valid_thru = tmp;
            }

            tmp = localStorage.getItem("credit_card");
            if (tmp) {
                member.credit_card = tmp;
            }

            tmp = localStorage.getItem("industry");
            if (tmp) {
                member.industry = tmp;
            }

            tmp = localStorage.getItem("education");
            if (tmp) {
                member.education = tmp;
            }

            tmp = localStorage.getItem("email");
            if (tmp) {
                member.email = tmp;
            }
        }
    }
    else if (/loan/.test(destination)) {
        if (member.applying) {
            $.mobile.navigate("#patience");
        }
    }
    else if (/congratulation/.test(destination)) {
        if (status == "5.2") {
            $.mobile.navigate("#fail");
        }
        else if (status != "5.1") {
            $.mobile.navigate("#loan");
        }
    }
}

// Actions
var member = {};
member.id = config.member_id;

$(function () {
    config.status = getStatus();
    member.status = config.status;
    config.destination = getDestination();
    if (parseInt(config.status) > 2) {
        getMemberInfo();
        whetherApplying();
        localStorage.clear();
    }
    navigateThruStatusNDes(member.status, config.destination);
});