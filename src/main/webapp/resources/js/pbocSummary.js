$(function () {
// START   
    var path = "api/";

    function getNshow(path) {
        $('#table').html('<div class="grid"></div><div class="grid">姓名</div><div class="grid">身份证号</div><div class="grid">时间</div><div class="grid">员工编号</div><div class="grid">无此人(y)</div>');

        $.get(path, function (json) {
            for (var i = 0, l = json.length; i < l; i++) {
                for (var j = 0, k = json[i].length; j < k; j++) {
                    $('#table').append('<div class="grid">' + json[i][j] + '</div>');
                }
            }

            $('.grid').click(function () {
                if ($(this).html().length == 18) {
                    window.location = 'pboc.html?id=' + $(this).prev().html() + '&certNo=' + $(this).html();
                }
            });
        }, 'json');
    }

    getNshow(path + 'pboc/summary');

    $('#for-all').click(function () {
        $("input:checkbox:not(:checked)").trigger('click');
    });

    $('#search').click(function () {
        var q = 'q=', name = $('#name').val(), id = $('#id').val(), since = $('#since').val(), to = $('#to').val(), status = [];
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
        if ($('#undocumented').is(':checked'))
            status = ['1', '2', '3', '4', '5'];

        status = status.join();

        if (name) {
            q += ("pb.name='" + name + "'");
            if (id)
                q += (" and pb.certNo='" + id + "'");
            if (since)
                q += (" and pb.CREATE_TIME>='" + since + "'");
            if (to)
                q += (" and pb.CREATE_TIME<='" + to + "'");
            q += (' and pb.status in (' + status + ')');
        }
        else if (id) {
            q += ("pb.certNo='" + id + "'");
            if (since)
                q += (" and pb.CREATE_TIME>='" + since + "'");
            if (to)
                q += (" and pb.CREATE_TIME<='" + to + "'");
            q += (' and pb.status in (' + status + ')');
        }
        else if (since) {
            q += ("pb.CREATE_TIME>='" + since + "'");
            if (to)
                q += (" and pb.CREATE_TIME<='" + to + "'");
            q += (' and pb.status in (' + status + ')');
        }
        else if (to) {
            q += ("pb.CREATE_TIME<='" + to + "'");
            q += (' and pb.status in (' + status + ')');
        }
        else
            q += 'pb.status in (' + status + ')';

        getNshow(path + "pboc/search?" + q);
    });
// END
});