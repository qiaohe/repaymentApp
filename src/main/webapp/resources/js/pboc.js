﻿$(function () {
// START   
    var path = "api/",initFlag = 0,pboc = {};
    pboc.page = {
        curPage : 1,
        perPage : 15,
        lastPage : false
    };
    pboc.init = function () {
        pboc.getNshow(path + 'pboc/search?curPage=1&q=pb.status in (2, 3)');
        pboc.initEvent();
    };
    pboc.initEvent = function () {
        $('#for-all').click(function () {
            var $all = $(this);
            if($all.attr("checkFlag") && $all.attr("checkFlag")!= "0") {
                $("#chooseboxes input:checkbox").prop("checked",false);
                $all.attr("checkFlag","0");
            } else {
                $("#chooseboxes input:checkbox").prop("checked",true);
                $all.attr("checkFlag","1");
            }
        });
        $("input.check").click(function(e){
            e.preventDefault();
            var $btn = $(this).parent().prev();
            $btn.prop("checked",!$btn.prop("checked"));
        });
        $("#forNone").click(function(e){
            e.preventDefault();
            var $none = $(this).prev();
            $none.prop("checked",!$none.prop("checked"));
        });
        $('#search').click(function () {
            var q = pboc.getParameter() || "q=pb.status in (2, 3)";
            pboc.getNshow(path + "pboc/search?curPage=1&" + q);
        });

        $("#prev").click(function(){
            var curPage = pboc.page.curPage - 1;
            if(curPage < 1) {
                alert("已经是第一页");
                return;
            }
            var q = pboc.getParameter() || "q=pb.status in (2, 3)";
            pboc.getNshow(path + "pboc/search?curPage="+curPage+"&" + q);
            pboc.page.curPage = curPage;
        });
        $("#next").click(function(){
            var curPage = pboc.page.curPage + 1;
            if(pboc.page.lastPage) {
                alert("已经是最后一页");
                return;
            }
            var q = pboc.getParameter() || "q=pb.status in (2, 3)";
            pboc.getNshow(path + "pboc/search?curPage="+curPage+"&" + q);
            pboc.page.curPage = curPage;
        });
    };
    pboc.getParameter = function() {
        var q = [], id = $('#id').val();
        if(id)
            q.push("pb.certNo='" + id + "'");

        var name = $('#name').val();
        if(name)
            q.push("pb.name='" + name + "'");

        var since = $('#since').val();
        if(since)
            q.push("pb.CREATE_TIME>='" + since + "'");

        var to = $('#to').val();
        if(to)
            q.push("pb.CREATE_TIME<='" + to + "'");

        var keyiner = $('#typein').val();
        if(keyiner)
            q.push("pb.KEYINER='" + keyiner + "'");

        if($('#none').prop("checked")) {
            q.push("pb.RISK=1");
        }

        var status = [];
        if ($('#undocumented').is(':checked'))
            status.push('1');
        if ($('#documenting').is(':checked'))
            status.push('2');
        if ($('#temporary').is(':checked'))
            status.push('3');
        if ($('#finished').is(':checked'))
            status.push('4');
        if ($('#modify-id').is(':checked'))
            status.push('5');
        status = status.join();

        if(q.length){
            if(status.length){
                q = "q=" + q.join(' and ') + " and pb.status in (" + status + ")";
            }
            else{
                q = "q=" + q.join(' and ');
            }
        }
        else if(status.length){
            q = "q=pb.status in (" + status + ")";
        }
        return q instanceof Array ? "" : q;
    };
    pboc.getNshow = function (path) {
        $('#table').html('<div class="grid ylo">序号</div><div class="grid ylo">身份证号</div><div class="grid ylo">姓名</div><div class="grid ylo">时间</div><div class="grid ylo">员工编号</div><div class="grid ylo">无此人</div><div class="grid ylo">状态</div>');

        $.get(path, function (json) {
            var idNos = [],certNos = [];
            if(!json || json.length < pboc.page.perPage) {
                pboc.page.lastPage = true;
            } else {
                pboc.page.lastPage = false;
            }
            for (var i = 0, l = json.length; i < l; i++) {
                for (var j = 0, k = json[i].length; j < k; j++) {
                    if(j == 3) {
                        $('#table').append('<div class="grid">' + json[i][j].slice(0, 10) + '</div>');
                    } else if(j == 5){
                        if(json[i][j])
                            $('#table').append('<div class="grid">Y</div>');
                        else
                            $('#table').append('<div class="grid"></div>');
                    } else if(j == 6){
                        var tmp;
                        if(json[i][j] == '1')
                            tmp="待建档";
                        else if(json[i][j] == '2')
                            tmp="建档中";
                        else if(json[i][j] == '3')
                            tmp="暂存";
                        else if(json[i][j] == '4')
                            tmp="完成";
                        else if(json[i][j] == '5')
                            tmp="修改id";
                        $('#table').append('<div class="grid">' + tmp + '</div>');
                    } else if(j == 1) {
                        if(initFlag == 0) {
                            certNos.push(json[i][j]);
                        }
                        $('#table').append('<div class="grid" style="cursor: pointer;">' + json[i][j] + '</div>');
                    } else {
                        if(j > 6) {
                            continue;
                        }
                        if(initFlag == 0 && j == 0) {
                            idNos.push(json[i][j]);
                        }
                        $('#table').append('<div class="grid">' + json[i][j] + '</div>');
                    }
                }
            }

            if(idNos && idNos.length != 0) {
                $.cookie("idNos",idNos.join(","));
            }
            if(certNos && certNos.length != 0) {
                $.cookie("certNos",certNos.join(","));
            }
            initFlag = 1;

            // set table height
            if(json && json.length != 0) {
                $('#table').height(27*json.length+27);
            } else {
                $('#table').height(27);
            }

            $('.grid').click(function () {
                if ($(this).html().length == 18) {
                    window.open('credit.html?id=' + $(this).prev().html() + '&certNo=' + $(this).html(), '_blank');
                }
            });
        }, 'json');
    };

    pboc.init();
// END
});