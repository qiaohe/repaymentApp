/* global alert:false */
"use strict";

var config = {
        api_path: "api/",
        debug: true,
        time_stamp: "?time=" + (new Date()).getTime(),
        alert_url: function(url) {
            if(config.debug) {
                alert(url);
            }
        }
    },

    member = (function() {
        var member = {};

        member.getId = function() {
            var id_ptn = /memberId=(\d+)/;
            try {
                this.id = id_ptn.exec(window.location)[1];
            }
            catch (e) {
                alert("Exception: can not get memberId from url!");
            }
        };

        member.getStatus = function() {
            var $this = this;
            $.ajax({
                url: config.api_path + "members/" + this.id + "/status" + config.time_stamp,
                type: "GET",
                async: false,
                dataType: "text",
                success: function (text) {
                    $this.status = text;
                },
                error: function() {
                    config.alert_url(config.api_path + "members/{memberId}/status" + config.time_stamp);
                }
            });
        };

        member.getDestPage = function() {
            var dest_ptn = /#(\w+)/;
            try {
                this.dest_page = dest_ptn.exec(window.location)[1];
            }
            catch (e) {
                alert("Exception: can not get destPage from url!");
            }
        };

        member.getBasicInfo = function() {
            var $this = this;
            $.ajax({
                url: config.api_path + "members/" + this.id + config.time_stamp,
                type: "GET",
                dataType: "json",
                async: false,
                success: function (json) {
                    $this.id_card = json.idCardNo;
                    $this.valid_thru = json.validThru;
                    $this.industry = json.industry;
                    $this.education = json.education;
                    $this.email = json.email;
                    $this.mobile_varified = json.hasMobilePhone;
                    $this.existingFlag = json.existingFlag;
                    $this.gender = json.sex;
                },
                error: function () {
                    config.alert_url(config.api_path + "members/{memberId}" + config.time_stamp);
                }
            });
        };

        member.whetherApplying = function() {
            var $this = this;
            $.ajax({
                url: config.api_path + "app/members/" + this.id + config.time_stamp,
                type: "GET",
                dataType: "text",
                async: false,
                success: function(text) {
                    $this.isapplying = ("true" === text);
                },
                error: function () {
                    config.alert_url(config.api_path + "app/members/{memberId}" + config.time_stamp);
                }
            });
        };

        member.setDestPage = function() {
            var status = this.status,
                dest_page = this.dest_page;
            if (/limit/.test(dest_page)) {
                if (status === "1") {
                    this.isnew = 1;
                    this.dest_page = "#limit";
                }
                else if (Number(status) > 2) {
                    this.dest_page = "#result";
                }
                else{
                    this.dest_page = "#limit";
                    var tmp = localStorage.getItem("id_card");
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
            else if (/loan/.test(dest_page)) {
                if (member.isapplying) {
                    this.dest_page = "#patience";
                }
            }
            else if (/congratulation/.test(dest_page)) {
                if (status === "5.2") {
                    this.dest_page = "#fail";
                }
                else if (status !== "5.1") {
                    this.dest_page = "#loan";
                }
            }
            else if (/patience/.test(dest_page) || /suspension/.test(dest_page)) {
                if (Number(status) > 5.2) {
                    this.dest_page = "#loan";
                }
                else if (status === "5.1") {
                    this.dest_page = "#congratulation";
                }
                else if (status === "5.2") {
                    this.dest_page = "#fail";
                }
            }
        };
        return member;
    })();

(function navigate() {
    member.getId();
    member.getStatus();
    member.getDestPage();
    member.whetherApplying();
    member.setDestPage();
    if (Number(member.status) > 2) {
        member.getBasicInfo();
    }
    $.mobile.navigate(member.dest_page);
})();

console.log("navigation ends!");