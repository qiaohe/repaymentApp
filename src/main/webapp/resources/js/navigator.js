"use strict";

function getParameterByName(ParaName) {
    ParaName = ParaName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + ParaName + "=([^&#]*)"),
        results = regex.exec(location.hash);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

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
            var id = getParameterByName("memberId");
            if(isNaN(Number(id))) {
                console.log("memberId is NaN!");
                window.location.replace('http://wechat.memedai.cn/repaymentApp/index2.html#prom?r='+new Date().getTime());
            }
            else {
                id = Number(id) + "";
            }
            if(this.id === "0") {
                if(!(/pay-success/.test(window.location) || /pay-fail/.test(window.location))) {
                    window.location.href = 'http://wechat.memedai.cn/repaymentApp/index2.html#prom?r='+new Date().getTime();
                }
            }
            else {
                this.id = id;
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
                console.log("Exception: can not get destPage from url!");
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
    if(window.applicationCache.status == window.applicationCache.UPDATEREADY) {
        window.applicationCache.update();
    }

    if(!(/pay-success/.test(window.location) || /pay-fail/.test(window.location))) {
        member.getId();
        member.getStatus();
        member.getDestPage();
        member.whetherApplying();
        member.setDestPage();
        if (Number(member.status) > 2) {
            member.getBasicInfo();
        }
        if(member.destPage === "repayment") {member.destPage += "-0"; member.destPage = "#" + member.destPage;}
        if(!/term/.exec(window.location)) {
//            $.mobile.navigate(member.destPage + "?memberId=" + member.id);
            if(!/#/.test(member.destPage)) member.destPage = "#" + member.destPage;
            var locationTo = window.location.href;
            locationTo = locationTo.replace(/#\w+/g, member.destPage);
        }
    }
})();
console.log("navigation ends!");