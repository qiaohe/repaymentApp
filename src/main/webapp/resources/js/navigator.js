/* global alert:false */
"use strict";

var config = {
        apiPath: "api/",
        debug: true,
        timeStamp: "?time=" + (new Date()).getTime(),
        alertUrl: function(url) {
            if(this.debug) {
                alert(url);
            }
        }
    },

    member = (function() {
        var member = {};

        member.getId = function() {
            var idPtn = /memberId=(\d+)/;
            try {
                this.id = idPtn.exec(window.location)[1];
            }
            catch (e) {
                window.location.href = 'http://godzilla.dlinkddns.com.cn/repaymentApp/index2.html#prom?r='+new Date().getTime();
            }
        };

        member.getStatus = function() {
            var $this = this;
            $.ajax({
                url: config.apiPath + "members/" + this.id + "/status" + config.timeStamp,
                type: "GET",
                async: false,
                dataType: "text",
                success: function (text) {
                    $this.status = text;
                },
                error: function() {
                    config.alertUrl(config.apiPath + "members/" + $this.id + "/status" + config.timeStamp);
                }
            });
        };

        member.getDestPage = function() {
            var destPtn = /#(\w+)/;
            try {
                this.destPage = destPtn.exec(window.location)[1];
            }
            catch (e) {
                alert("Exception: can not get destPage from url!");
            }
        };

        member.getBasicInfo = function() {
            var $this = this;
            $.ajax({
                url: config.apiPath + "members/" + this.id + config.timeStamp,
                type: "GET",
                dataType: "json",
                async: false,
                success: function (json) {
                    $this.idCard = json.idCardNo;
                    $this.validThru = json.validThru;
                    $this.industry = json.industry;
                    $this.education = json.education;
                    $this.email = json.email;
                    $this.mobileVarified = json.hasMobilePhone;
                    $this.existingFlag = json.existingFlag;
                    $this.gender = json.sex;
                },
                error: function () {
                    config.alertUrl(config.apiPath + "members/" + $this.id + config.timeStamp);
                }
            });
        };

        member.whetherApplying = function() {
            var $this = this;
            $.ajax({
                url: config.apiPath + "app/members/" + this.id + config.timeStamp,
                type: "GET",
                dataType: "text",
                async: false,
                success: function(text) {
                    $this.isapplying = ("true" === text);
                },
                error: function () {
                    config.alertUrl(config.apiPath + "app/members/" + $this.id + config.timeStamp);
                }
            });
        };

        member.setDestPage = function() {
            var status = this.status,
                destPage = this.destPage;
            if (/limit/.test(destPage)) {
                if (status === "1") {
                    this.isnew = 1;
                    this.destPage = "#limit";
                    var tmp = localStorage.getItem("id_card");
                    if (tmp) {
                        member.idCard = tmp;
                    }

                    tmp = localStorage.getItem("gender");
                    if (tmp) {
                        member.gender = tmp;
                    }

                    tmp = localStorage.getItem("valid_thru");
                    if (tmp) {
                        member.validThru = tmp;
                    }

                    tmp = localStorage.getItem("credit_card");
                    if (tmp) {
                        member.creditCard = tmp;
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
                else if (Number(status) > 2) {
                    this.destPage = "#result";
                }
            }
            else if (/loan/.test(destPage)) {
                if (member.isapplying) {
                    this.destPage = "#patience";
                }
            }
            else if (/congratulation/.test(destPage)) {
                if (status === "5.2") {
                    this.destPage = "#fail";
                }
                else if (status !== "5.1") {
                    this.destPage = "#loan";
                }
            }
            else if (/patience/.test(destPage) || /suspension/.test(destPage)) {
                if (Number(status) > 5.2) {
                    this.destPage = "#loan";
                }
                else if (status === "5.1") {
                    this.destPage = "#congratulation";
                }
                else if (status === "5.2") {
                    this.destPage = "#fail";
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
    if(member.destPage === "repayment") {member.destPage += "-0"; member.destPage = "#" + member.destPage;}
    $.mobile.navigate(member.destPage + "?memberId=" + member.id + config.timeStamp);
})();
console.log("navigation ends!");