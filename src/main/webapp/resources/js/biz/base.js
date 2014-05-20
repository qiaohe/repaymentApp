/**
 * Created by Richard Xue on 14-5-20.
 */
$(function () {
    var base = {};
    base.basePath = "192.168.0.155:8080/";
    base.makeDict = function (type) {
        var map = {};
        $.ajax({
            url: base.basePath + "api/dict/" + type,
            dataType: "json",
            type: "GET",
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (!json) return map;
                $.each(json, function (i, dict) {
                    map[dict.key] = dict.value;
                });
            }
        });
        return map;
    };
});