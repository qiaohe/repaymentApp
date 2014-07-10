/* global alert:false */
/* global config:false */
/* global member:false */
/* global json:false */
/* global WeixinJSBridge:false */
"use strict";

//$(document).on("pageshow", "#prom", function () {
//
//});

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