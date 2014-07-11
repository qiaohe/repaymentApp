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
    $("#prom-step-cont").height(img1Height+img2Height+80);
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

resetWechatShare();

function resetWechatShare() {
    if(typeof WeixinJSBridge === "undefined") {
        return;
    }
    var share = {};
    share.title = "终于找到了，帮我还信用卡的那个人";
    share.desc = "一直以来我都觉得没有人帮我还信用卡是不科学的，今天终于被我找到了！哈哈哈哈";
    share.img_url = "../img/public/logo.png";
    share.link = window.location.origin + window.location.pathname.replace("index.html","index2.html") + "#prom";
    share.appid = "";

    document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
        WeixinJSBridge.on('menu:share:appmessage', function(argv){
            WeixinJSBridge.invoke("sendAppMessage",{
                "appid": share.appid,
                "img_url": share.img_url,
                "img_width": "200",
                "img_height": "150",
                "link": share.link,
                "desc": share.desc,
                "title": share.title
            }, function(res) {
                console.log(res);
            });
        });
        WeixinJSBridge.on('menu:share:timeline', function(argv){
            WeixinJSBridge.invoke("shareTimeline",{
                "img_url": share.img_url,
                "img_width": "200",
                "img_height": "150",
                "link": share.link,
                "desc": share.desc,
                "title": share.title
            }, function(res) {
                console.log(res);
            });
        });
        WeixinJSBridge.on('menu:share:weibo', function(argv){
            WeixinJSBridge.invoke('shareWeibo',{
                "content": share.desc,
                "url": share.link
            }, function(res) {
                console.log(res);
            });
            return false;
        });
    }, false);
}

console.log("END!");