(function ($) {
    // START
    var appSummary = {},
        path = "api/credit",
        userType = {
            "0":"新用户",
            "1":"老用户-前次退件",
            "2":"老用户-前次过件"
        },
        appStatus = {
            "0":"客户提交申请",
            "1":"提交查询人行",
            "2":"人行报告返回",
            "3":"内部批次完成",
            "4":"A级征信审核完成",
            "5":"B级征信审核完成",
            "6":"客户放弃",
            "7":"客户接受，发放借款",
            "99":"暂存"
        };

    appSummary.init = function() {
        this.initEvent();
        $.get(path, function (json) {
            appSummary.loadData(json);
        });
    };
    appSummary.initEvent = function() {
        // click search button
        $("#search").click(function(){
            var param = appSummary.generateParam();
            $.get(path+"/search?q="+param,function (json) {
                appSummary.loadData(json);
            });
        });
        $("#prev").on("click",function(){

        });
        $("#next").on("click",function(){

        });
    };
    appSummary.loadData = function(json) {
        // head of table
        var contentHtml = '<tr>' +
            '<td style="width: 4%;text-align: center"></td>' +
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
        if(!json) {
            $("#data-table").html(contentHtml);
            return;
        }
        $.each(json,function(i,ele){
            contentHtml += '<tr>' +
                '<td style="text-align: center;">'+(i+1)+'</td>' +
                '<td class="clickable">'+ele.appNo+'</td>' +
                '<td>'+ele.name+'</td>' +
                '<td>'+ele.idCardNo+'</td>' +
                '<td>'+userType[ele.existingFlag]+'</td>' +
                '<td>'+$.formatDate(ele.applyDate)+'</td>' +
                '<td>'+ele.mobileCity+'</td>' +
                '<td>'+appStatus[ele.status]+'</td>' +
                '<td>'+ele.creditor+'</td>' +
                '<td>'+$.formatDate(ele.createDate)+'</td>' +
                '</tr>';
        });
        $("#data-table").html(contentHtml);
        $("#data-table").find(".clickable").each(function(){
            $(this).on("click",function(){
                window.open("/appDetail.html?applyNo="+$(this).text(),"_blank");
            });
        });
    };
    appSummary.formatDate = function(ms) {
//        return $.formatDate(ms).format("yyyy-MM-dd hh:mm:ss");
        return $.formatDate(ms);
    };
    appSummary.generateParam = function() {
        var name = $("#name").val();
        var idcard = $("#idcard").val();
        var moblie = $("#mobile").val();
        var memberNo = $("#memberNo").val();
        var applyNo = $("#applyNo").val();
        var applyFrom = $("#applyFrom").val();
        var applyTo = $("#applyTo").val();
        var procFrom = $("#procFrom").val();
        var procTo = $("#procTo").val();
        var creditor = $("#creditor").val();
        var reasonCode = $("#reasonCode").val();
        var userType = $("#userType").val();
        var systemFlag = $("#systemFlag").prop("checked");
        var tvFlag = $("#tvFlag").prop("checked");
        var status = $("#status").val();

        var param = (name ? " and idcard.name='"+name+"'" : "")
            + (idcard ? " and idcard.idNo='"+idcard+"'" : "")
            + (moblie ? " and mem.mobile='"+moblie+"'" : "")
            + (memberNo ? " and mem.id='"+memberNo+"'" : "")
            + (applyNo ? "and appl.applicationNo='"+applyNo+"'" : "")
            + (applyFrom ? " and appl.applyTime>='"+applyFrom+"'" : "")
            + (applyTo ? " and appl.applyTime<='"+applyTo+"'" : "")
            + (procFrom ? " and appl.createTime>='"+procFrom+"'" : "")
            + (procTo ? " and appl.createTime<='"+procTo+"'" : "")
            + (creditor ? " and apv.creditor='"+creditor+"'" : "")
            + (reasonCode ? " and (apv.reason1 = '"+reasonCode+"' or apv.reason2 = '"+reasonCode+"' or apv.reason3 = '"+reasonCode+"')" : "")
            + (userType ? " and appl.existingFlag='"+userType+"'" : "")
            + (systemFlag ? " and appltv.decision ='2'" : "")
//            + (tvFlag ? " and appltv.applicationNo='"+tvFlag+"'" : "")
            + (status ? " and appl.status='"+status+"'" : "");
        return  param.replace("and",'');
    };
    // initialize
    appSummary.init();
    // END
})(jQuery);