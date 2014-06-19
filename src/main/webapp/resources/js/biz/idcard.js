/**
 * Created by Richard Xue on 14-6-18.
 */
$(function(){
    var idcard = {};
    idcard.init = function() {
        if(typeof window.dialogArguments != "undefined") {
            var data = window.dialogArguments;
            $("#target").attr("src","api/resources/idcard/"+data.imgName);
        }
        idcard.initCrop();
        idcard.initEvent();
    };
    idcard.initEvent = function() {
        $("#idcard-sv").click(function(){
            if(!$('#w').val()) {
                alert("请选择区域！");
                return;
            }
            var data = {
                fixedWidth : 600,
                fixedHeight : 400,
                x : $('#x1').val(),
                y : $('#y1').val(),
                width : $('#w').val(),
                height : $('#h').val(),
                type : "1"
            };
            var idNo = "362326197805010018";
            $.ajax({
                url: "api/pboc/crop/"+idNo,
                dataType: "json",
                data : JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                success: function (json) {
                    alert("裁剪成功！");
                    window.close();
                },
                error: function(data) {
                    alert("请求异常！");
                }
            });

        });
    };
    idcard.initCrop = function(){
        var jcrop_api;
        $('#target').Jcrop({
            onChange:   showCoords,
            onSelect:   showCoords,
            onRelease:  clearCoords
        },function(){
            jcrop_api = this;
        });

        $('#coords').on('change','input',function(e){
            var x1 = $('#x1').val(),
                x2 = $('#x2').val(),
                y1 = $('#y1').val(),
                y2 = $('#y2').val();
            jcrop_api.setSelect([x1,y1,x2,y2]);
        });

        function showCoords(c) {
            $('#x1').val(c.x);
            $('#y1').val(c.y);
            $('#x2').val(c.x2);
            $('#y2').val(c.y2);
            $('#w').val(c.w);
            $('#h').val(c.h);
        };

        function clearCoords() {
            $('#coords input').val('');
        };
    };
    idcard.init();
});