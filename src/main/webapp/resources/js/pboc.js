$(function () {
// START   
    var path = "api/";

    function getNshow(path) {
        $('#table').html('<div class="grid ylo">ID</div><div class="grid ylo">身份证号</div><div class="grid ylo">姓名</div><div class="grid ylo">时间</div><div class="grid ylo">员工编号</div><div class="grid ylo">无此人</div><div class="grid ylo">状态</div>');

        $.get(path, function (json) {
            for (var i = 0, l = json.length; i < l; i++) {
                for (var j = 0, k = json[i].length; j < k; j++) {
                    if(j == 3)
                        $('#table').append('<div class="grid">' + json[i][j].slice(0, 10) + '</div>');
                    else if(j == 5){
                        if(json[i][j])
                            $('#table').append('<div class="grid">Y</div>');
                        else
                            $('#table').append('<div class="grid"></div>');
                    }
                    else if(j == 6){
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
                    }
                    else
                        $('#table').append('<div class="grid">' + json[i][j] + '</div>');
                }
            }

            $('.grid').click(function () {
                if ($(this).html().length == 18) {
                    // window.location = 'credit.html?id=' + $(this).prev().html() + '&certNo=' + $(this).html();
					window.open('credit.html?id=' + $(this).prev().html() + '&certNo=' + $(this).html(), '_blank');
                }
            });
        }, 'json');
    }

    getNshow(path + 'pboc/summary');

    $('#for-all').click(function () {
        //$("input:checkbox:not(:checked)").trigger('click');
        $("#chooseboxes input:checkbox").trigger('click');
    });

    $('#none').click(function(){
        if($(this).is(':checked')){
            var q = 'q=pb.RISK=1' ;
            getNshow(path + "pboc/search?" + q);
        }
    });

    $('#search').click(function () {
        var q = [], id = $('#id').val();
        if(id)
            q.push("pb.id='" + id + "''");

        var name = $('#name').val();
        if(name)
            q.push("pb.name='" + name + "''");

        var since = $('#since').val();
        if(since)
            q.push("pb.CREATE_TIME>='" + since + "'");

        var to = $('#to').val();
        if(to)
            q.push("pb.CREATE_TIME<='" + to + "'");

        var keyiner = $('#typein').val();
        if(keyiner)
            q.push("pb.KEYINER='" + keyiner + "'");

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

        getNshow(path + "pboc/search?" + q);
    });
// END
});