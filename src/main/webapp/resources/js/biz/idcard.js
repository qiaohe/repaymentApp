/**
 * Created by Richard Xue on 14-6-18.
 */
$(function(){
    var idcard = {};
    idcard.init = function() {
        if(typeof window.dialogArguments != "undefined") {
            var data = window.dialogArguments;
            idcard.idNo = data.idNo;
            idcard.type = data.frontOrBack;
            idcard.imgName = data.imgName;
            $("#target").attr("src","api/resources/idcard/processed/"+data.imgName+"?r="+new Date().getTime());
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
                type : idcard.type
            };
            $.ajax({
                url: "api/pboc/crop/"+idcard.idNo,
                dataType: "json",
                data : JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                success: function (json) {
                    if(json && json == "1") {
                        alert("裁剪成功！");
                        window.close();
                    } else {
                        alert("裁剪失败！");
                        window.close();
                    }
                },
                error: function(data) {
                    alert("请求异常！");
                }
            });
        });
        $("#idcard-cancel").click(function(){
            window.close();
        });
        $("#idcard-restore").click(function(){
            if(confirm("您确定要恢复成原图？")) {
                $.ajax({
                    url: "api/pboc/idcard/"+idcard.idNo+"/"+idcard.type,
                    dataType: "json",
                    data : {},
                    type: "POST",
                    contentType: "application/json",
                    success: function (json) {
                        if(json == "0") {
                            alert("未知异常！");
                        } else if(json == "1") {
                            $("#target").attr("src","api/resources/idcard/processed/"+idcard.imgName+"?r="+new Date().getTime());
                            idcard.initCrop();
                        } else if(json == "2") {
                            alert("图片不存在！");
                        }
                    },
                    error: function(data) {
                        alert("请求异常！");
                    }
                });
            }
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