/**
 * Created by Richard Xue on 14-7-16.
 */

$(function(){
    var idcard2 = {};
    idcard2.init = function(){
        if(typeof window.dialogArguments != "undefined") {
            var data = window.dialogArguments;
            idcard2.idNo = data.idNo;
            idcard2.type = data.frontOrBack;
            idcard2.imgName = data.imgName;
            idcard2.imgSrc = "api/resources/idcard/processed/"+data.imgName+"?r="+new Date().getTime();
        }
        idcard2.initCrop();
        idcard2.initEvent();
    };
    idcard2.initCrop = function(){
        var cropzoom = $('#crop_container').cropzoom({
            width:600,
            height:400,
            bgColor: '#CCC',
            enableRotation:true,
            enableZoom:false,
            zoomSteps:10,
            rotationSteps:10,
            selector:{
                centered:true,
                startWithOverlay: true,
                borderColor:'blue',
                borderColorHover:'red'
            },
            image:{
                source:idcard2.imgSrc,
                width:1024,
                height:768,
                minZoom:10,
                maxZoom:150
            }
        });
        cropzoom.setSelector(45,45,200,150,true);
        $('#crop').click(function(){
            cropzoom.send('api/pboc/cropRotate/410327198810087670','POST',{},function(rta){
            });
        });
        $('#restore').click(function(){
            cropzoom.restore();
        });
    };
    idcard2.initEvent = function(){

    };
    idcard2.init();
});
