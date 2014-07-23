/**
 * Created by Richard Xue on 14-7-16.
 */

$(function(){
    var idcard1 = {};
    idcard1.init = function(){
        if(typeof window.dialogArguments != "undefined") {
            var data = window.dialogArguments;
            idcard1.idNo = data.idNo;
            idcard1.type = data.frontOrBack;
            idcard1.imgName = data.imgName;
            idcard1.imgSrc = "api/resources/idcard/processed/"+idcard1.imgName+"?r="+new Date().getTime();
        } else {
            idcard1.idNo = $.getParamter("idNo");
            idcard1.type = $.getParamter("frontOrBack");
            idcard1.imgName = $.getParamter("imgName");
            idcard1.imgSrc = "api/resources/idcard/processed/"+idcard1.imgName+"?r="+new Date().getTime();
        }
        idcard1.initCrop();
        idcard1.initEvent();
    };

    idcard1.initCrop = function(){
        var cropzoom = $('#crop_container').cropzoom({
            width:600,
            height:400,
            bgColor: '#CCC',
            enableRotation:true,
            enableZoom:false,
            zoomSteps:10,
            rotationSteps:3,
            selector:{
                centered:true,
                startWithOverlay: true,
                borderColor:'blue',
                borderColorHover:'red'
            },
            image:{
                source:idcard1.imgSrc,
                width:1024,
                height:768,
                minZoom:10,
                maxZoom:150
            }
        });
//        cropzoom.setSelector(45,45,100,50,true);
        $('#crop').click(function(){
            cropzoom.send('api/pboc/rotate/'+idcard1.idNo,'POST',{},function(rta){
                if(rta && rta == "1") {
                    window.location.href = "idcard2.html?imgName="+idcard1.imgName+"&idNo="+idcard1.idNo+"&frontOrBack="+idcard1.type+"&r="+new Date().getTime();
//                    window.close();
                } else {
                    alert("处理异常！");
                }
            });
        });
        $('#restore').click(function(){
            cropzoom.restore();
        });
    };
    idcard1.initEvent = function(){
        $("#idcard-cancel").click(function(){
            window.close();
        });
        $("#idcard-restore").click(function(){
            if(confirm("您确定要恢复成原图？")) {
                $.ajax({
                    url: "api/pboc/idcard/"+idcard1.idNo+"/"+idcard1.type,
                    dataType: "json",
                    data : {},
                    type: "POST",
                    contentType: "application/json",
                    success: function (json) {
                        if(json == "0") {
                            alert("未知异常！");
                        } else if(json == "1") {
                            idcard1.initCrop();
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
    idcard1.init();
});
