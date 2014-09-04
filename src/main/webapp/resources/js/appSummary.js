(function ($) {
    // START
    var appSummary = {},
        path = "api/credit",
        userType = {
            "0": "新用户",
            "1": "老用户-前次退件",
            "2": "老用户-前次过件"
        },
        appStatus = {
            "0": "客户提交申请",
            "1": "提交查询人行",
            "2": "人行报告返回",
            "3": "内部批次完成",
            "4": "A级征信审核完成",
            "5": "B级征信审核完成",
            "6": "客户放弃",
            "7": "客户接受，发放借款",
            "99": "暂存"
        };
    appSummary.page = {
        curPage : 1,
        perPage : 15,
        lastPage : false
    };
    appSummary.init = function () {
        this.initEvent();
        appSummary.loadDataPage(path);
    };
    appSummary.loadDataPage = function(path) {
        $.get(path, function (json) {
            appSummary.loadData(json);
        });
    };
    appSummary.initEvent = function () {
        // click search button
        $("#search").click(function () {
            var param = appSummary.generateParam();
            appSummary.loadDataPage(path + "/search?curPage=1&q=" + param);
        });
        $("#forSystem").click(function(){
            var $system = $(this).prev();
            $system.prop("checked",!$system.prop("checked"));
        });
        $("#forTv").click(function(){
            var $tv = $(this).prev();
            $tv.prop("checked",!$tv.prop("checked"));
        });
        $("#prev").click(function(){
            var curPage = appSummary.page.curPage - 1;
            if(curPage < 1) {
                alert("已经是第一页");
                return;
            }
            var param = appSummary.generateParam();
            appSummary.loadDataPage(path + "/search?curPage="+curPage+"&q=" + param);
            appSummary.page.curPage = curPage;
        });
        $("#next").click(function(){
            var curPage = appSummary.page.curPage + 1;
            if(appSummary.page.lastPage) {
                alert("已经是最后一页");
                return;
            }
            var param = appSummary.generateParam();
            appSummary.loadDataPage(path + "/search?curPage="+curPage+"&q=" + param);
            appSummary.page.curPage = curPage;
        });
    };
    appSummary.loadData = function (json) {
        // head of table
        var contentHtml = '<tr style="background: #ffff00;">' +
            '<td style="width: 4%;text-align: center">序号</td>' +
            '<td>申请编号</td>' +
            '<td>姓名</td>' +
            '<td style="width:12%;">身份证号码</td>' +
            '<td>新旧用户标识</td>' +
            '<td>申请时间</td>' +
            '<td>手机城市</td>' +
            '<td>状态</td>' +
            '<td>征信员</td>' +
            '<td>处理时间</td>' +
            '</tr>';
        if(!json || json.length < appSummary.page.perPage) {
            appSummary.page.lastPage = true;
        } else {
            appSummary.page.lastPage = false;
        }
        if (!json) {
            $("#data-table").html(contentHtml);
            return;
        }
        var applNos = [];
        $.each(json, function (i, ele) {
            applNos.push(ele.appNo);
            contentHtml += '<tr>' +
                '<td style="text-align: center;">' + (i + 1) + '</td>' +
                '<td class="clickable">' + ele.appNo + '</td>' +
                '<td>' + ele.name + '</td>' +
                '<td>' + ele.idCardNo + '</td>' +
                '<td>' + userType[ele.existingFlag] + '</td>' +
                '<td>' + $.formatDate(ele.applyDate) + '</td>' +
                '<td>' + ele.mobileCity + '</td>' +
                '<td>' + appStatus[ele.status] + '</td>' +
                '<td>' + ele.creditor + '</td>' +
                '<td>' + $.formatDate(ele.createDate) + '</td>' +
                '</tr>';
        });
        if(applNos && applNos.length != 0) {
            $.cookie("appNos",applNos.join(","));
        }
        $("#data-table").html(contentHtml);
        $("#data-table").find(".clickable").each(function () {
            $(this).on("click", function () {
                window.open("appDetail.html?appNo=" + $(this).text(), "_blank");
            });
        });
    };
    appSummary.generateParam = function () {
        var name = $("#name").val();
        var idcard = $("#idcard").val();
        var moblie = $("#mobile").val();
        var memberNo = $("#memberNo").val();
        var applyNo = $("#applyNo").val();
        var applyFrom = $.trim($("#applyFrom").val());
        var applyTo = $.trim($("#applyTo").val());
        var procFrom = $.trim($("#procFrom").val());
        var procTo = $.trim($("#procTo").val());
        var creditor = $("#creditor").val();
        var reasonCode = $("#reasonCode").val();
        var userType = $("#userType").val();
        var systemFlag = $("#systemFlag").prop("checked");
        var tvFlag = $("#tvFlag").prop("checked");
        var status = $("#status").val();

        if(applyFrom && applyFrom.length != 8) {
            alert("申请时间格式不正确！");
            return;
        }
        if(applyTo && applyTo.length != 8) {
            alert("申请时间格式不正确！");
            return;
        }
        if(procFrom && procFrom.length != 8) {
            alert("处理时间格式不正确！");
            return;
        }
        if(procTo && procTo.length != 8) {
            alert("处理时间格式不正确！");
            return;
        }

        var param = (name ? " and idcard.NAME='" + name + "'" : "")
            + (idcard ? " and idcard.ID_NO='" + idcard + "'" : "")
            + (moblie ? " and mem.MOBILE='" + moblie + "'" : "")
            + (memberNo ? " and mem.ID='" + memberNo + "'" : "")
            + (applyNo ? "and appl.APPL_NO='" + applyNo + "'" : "")
            + (applyFrom ? " and appl.APPLY_TIME>='" + applyFrom + "'" : "")
            + (applyTo ? " and appl.APPLY_TIME<='" + appSummary.addDay(applyTo) + "'" : "")
            + (procFrom ? " and appl.CREATE_TIME>='" + procFrom + "'" : "")
            + (procTo ? " and appl.CREATE_TIME<='" + appSummary.addDay(procTo) + "'" : "")
            + (creditor ? " and apv.CREDITOR='" + creditor + "'" : "")
            + (reasonCode ? " and (apv.REASON_1 = '" + reasonCode + "' or apv.REASON_2 = '" + reasonCode + "' or apv.REASON_3 = '" + reasonCode + "')" : "")
            + (userType ? " and appl.EXISTING_FLAG='" + userType + "'" : "")
            + (systemFlag ? " and appltv.DECISION ='2'" : "")
            + (tvFlag ? " and t1.NUM>0" : "")
            + (status ? " and appl.STATUS='" + status + "'" : "");
        if (param) {
            return  param.replace("and", '');
        } else {
            return " 1=1";
        }
    };
    appSummary.addDay = function(time) {
        if(!time || time.length != 8) {
            return time;
        }
        var oldDate = (parseInt(time.substring(6,8),10) + 1);
        var newDate = new Date(time.substring(0,4)+"-"+time.substring(4,6));
        newDate.setDate(oldDate);
        var year = "" + newDate.getFullYear();
        var month = (newDate.getMonth()+1) > 9 ? "" + newDate.getMonth()+1 : "0"+(newDate.getMonth()+1);
        var day = (newDate.getDate() > 9 ? "": "0") + newDate.getDate();
        return year + month + day;
    };
    // initialize
    appSummary.init();
    // END
})(jQuery);