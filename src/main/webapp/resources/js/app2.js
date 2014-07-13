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

WeixinApi.ready(function(Api) {
    // 微信分享的数据
    var wxData = {
        "appId": "", // 服务号可以填写appId
        "imgUrl" : 'http://godzilla.dlinkddns.com.cn/repaymentApp/resources/img/public/logo.png',
        "link" : 'http://godzilla.dlinkddns.com.cn/repaymentApp/index2.html#prom?r='+new Date().getTime(),
        "desc" : '一直以来我都觉得没有人帮我还信用卡是不科学的，今天终于被我找到了！哈哈哈哈',
        "title" : "终于找到了，帮我还信用卡的那个人"
    };

    // 分享的回调
    var wxCallbacks = {
        // 分享操作开始之前
        ready : function() {
            // 你可以在这里对分享的数据进行重组
            console.log("准备分享");
        },
        // 分享被用户自动取消
        cancel : function(resp) {
            // 你可以在你的页面上给用户一个小Tip，为什么要取消呢？
            console.log("分享被取消");
        },
        // 分享失败了
        fail : function(resp) {
            // 分享失败了，是不是可以告诉用户：不要紧，可能是网络问题，一会儿再试试？
            console.log("分享失败");
        },
        // 分享成功
        confirm : function(resp) {
            // 分享成功了，我们是不是可以做一些分享统计呢？
            //window.location.href='http://192.168.1.128:8080/wwyj/test.html';
            console.log("分享成功");
        },
        // 整个分享过程结束
        all : function(resp) {
            // 如果你做的是一个鼓励用户进行分享的产品，在这里是不是可以给用户一些反馈了？
            console.log("分享结束");
        }
    };
    // 用户点开右上角popup菜单后，点击分享给好友，会执行下面这个代码
    Api.shareToFriend(wxData, wxCallbacks);
    // 点击分享到朋友圈，会执行下面这个代码
    Api.shareToTimeline(wxData, wxCallbacks);
    // 点击分享到腾讯微博，会执行下面这个代码
    Api.shareToWeibo(wxData, wxCallbacks);
});

console.log("END!");