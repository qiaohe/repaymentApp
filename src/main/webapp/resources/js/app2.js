/* global alert:false */
/* global config:false */
/* global member:false */
/* global json:false */
/* global WeixinJSBridge:false */
"use strict";

$(document).on("pagecreate", "#prom", function () {
    var documentWidth = $(document).width();
    var curWidth = 0;
    if(documentWidth >= 640) {
        curWidth = 640;
    } else {
        curWidth = documentWidth;
    }
    curWidth *= 0.9;
    var img1Width = $("#prom-step-img1").width();
    var img1Height = $("#prom-step-img1").height();
    var img2Width = $("#prom-step-img2").width();
    var img2Height = $("#prom-step-img2").height();
    $("#prom-step1").css({'position':"absolute",'left':img1Width+10,'top':0,'width':curWidth-img1Width-15});
    $("#prom-step2").css({'position':"absolute",'left':10,'top':img1Height+30,'width':curWidth-img2Width-15});
    $("#prom-step3").css({'position':"absolute",'left':10,'top':img1Height+img2Height,'width':curWidth-15});
    $("#prom-step-img1").css({'position':"absolute",'top':0,'left':0});
    $("#prom-step-img2").css({'position':"absolute",'top':img1Height-20,'left':curWidth-img2Width});
    $("#prom-step-cont").height(img1Height+img2Height+100);
});

$(document).on("pagecreate", "#team", function () {
    var documentWidth = $(document).width();
    var curWidth = 0;
    if(documentWidth >= 640) {
        curWidth = 640;
    } else {
        curWidth = documentWidth;
    }
    $(".member-item-right").width(curWidth*0.9-85);
});

console.log("END!");