/**
 * Created by Richard Xue on 14-6-18.
 */
$(function(){
    var idcard2 = {};
    idcard2.init = function() {
        if(typeof window.dialogArguments != "undefined") {
            var data = window.dialogArguments;
            idcard2.idNo = data.idNo;
            idcard2.type = data.frontOrBack;
            idcard2.imgName = data.imgName;
            $("#target").attr("src","api/resources/idcard/processed/"+data.imgName+"?r="+new Date().getTime());
        } else {
            idcard2.idNo = $.getParamter("idNo");
            idcard2.type = $.getParamter("frontOrBack");
            idcard2.imgName = $.getParamter("imgName");
            $("#target").attr("src","api/resources/idcard/processed/"+idcard2.imgName+"?r="+new Date().getTime());
        }
        idcard2.initCrop();
        idcard2.initEvent();
    };
    idcard2.initEvent = function() {
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
                type : idcard2.type
            };
            $.ajax({
                url: "api/pboc/crop/"+idcard2.idNo,
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
        $("#idcard-last").click(function(){
            window.location.href = "idcard1.html?imgName="+idcard2.imgName+"&idNo="+idcard2.idNo+"&frontOrBack="+idcard2.type+"&r="+new Date().getTime();
        });
        $("#idcard-restore").click(function(){
            if(confirm("您确定要恢复成原图？")) {
                $.ajax({
                    url: "api/pboc/idcard/"+idcard2.idNo+"/"+idcard2.type,
                    dataType: "json",
                    data : {},
                    type: "POST",
                    contentType: "application/json",
                    success: function (json) {
                        if(json == "0") {
                            alert("未知异常！");
                        } else if(json == "1") {
                            $("#target").attr("src","api/resources/idcard/processed/"+idcard2.imgName+"?r="+new Date().getTime());
                            idcard2.initCrop();
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
    idcard2.initCrop = function(){
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
    idcard2.init();
});