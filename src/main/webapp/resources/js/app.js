/* global $:false */
/* global WeixinJSBridge:false */
/* global alert:false */
"use strict";
var config = {};
config.api_path = "http://180.168.35.37/repaymentApp/api/";

config.debug = false;
config.local_debug = false;

if (config.local_debug) {
	config.member_id = "36";
	config.status = "10";
}
else {
	config.id_pattern = /(?:memberId=)\d+/;
	config.member_id = config.id_pattern.exec(window.location).toString();
	config.member_id = config.member_id.slice(9, config.member_id.length);

	config.status_pattern = /(?:status=)\d+/;
	config.status = config.status_pattern.exec(window.location).toString();
	config.status = config.status.slice(7, config.status.length);
}

var member = {};
var app = {};
var dict = {};

member.id = config.member_id;
member.status = config.status;
member.loanable = false;

// Actions
function navigateThruStatus() {
	if (member.status == "1"){
		member.isnew = 1;
	}
	else if (member.status == "2") {
		var tmp = localStorage.getItem("idcard_front");
		if (tmp) {
			member.id_card = tmp;
		}

		tmp = localStorage.getItem("valid_thru");
		if (tmp) {
			member.valid_thru = tmp;
		}

		tmp = localStorage.getItem("credit_card");
		if (tmp) {
			member.card_num = tmp;
		}

		tmp = localStorage.getItem("industry");
		if (tmp) {
			member.industry = tmp;
		}

		tmp = localStorage.getItem("education");
		if (tmp) {
			member.education = tmp;
		}

		tmp = localStorage.getItem("email");
		if (tmp) {
			member.email = tmp;
		}

		if (member.id_card && member.valid_thru && member.card_num){
			$.mobile.navigate("#basic-info");
		}
	}
	else {
		getMemberInfo();
		getCreditLimit();
		getAvlCrl();
	}

	if (member.status == "5.1") {
		$.mobile.navigate("#congratulation");
	}
	else if (member.status == "12") {
		$.mobile.navigate("#fail");
	}
}

function getMemberInfo() {
	$.ajax({
		url: config.api_path + "members/" + member.id,
		type: "GET",
		dataType: "json",
		async: false,
		success: function (json) {
			member.id_card = json.idCardNo;
			member.valid_thru = json.validThru;
			member.industry = json.industry;
			member.education = json.education;
			member.email = json.email;
			member.mobile_varified = json.hasMobilePhone;
		},
		error: function () {
			if (config.debug)
				alert(config.api_path + "members/" + member.id);
		}
	});
}

function getCreditLimit() {
	$.ajax({
		url: config.api_path + "members/" + member.id + "/crl",
		type: "GET",
		dataType: "json",
		async: false,
		success: function (json) {
			member.limit = json.creditLimit;
			member.rank = json.rankOfLimit;
		},
		error: function () {
			if (config.debug)
				alert(config.api_path + "members/" + member.id + "/crl");
		}
	});
}

function getAvlCrl() {
	$.ajax({
		url: config.api_path + "members/" + member.id + "/avlCrl",
		type: "GET",
		dataType: "text",
		async: false,
		success: function (text) {
			member.avlcrl = text;
		},
		error: function () {
			if (config.debug)
				alert(config.api_path + "members/" + member.id + "/avlCrl");
		}
	});
}

// function stdError(jqXHR, textStatus, errorThrown) {
// 	if (config.debug)
// 		alert(jqXHR + " " + textStatus + " " + errorThrown);
// }

function recongizeIdCard(form_data, url_path, datatype) {
	return $.ajax({
		url: url_path,
		type: "POST",
		data: form_data,
		cache: false,
		processData: false,
		contentType: false,
		dataType: datatype
	});
}

function getBincode() {
	$.getJSON(config.api_path + "dict/binCode", function (json) {
		dict.bincode = json;
	});
}

function validateCardNo(num) {
	var sum = 0, l = num.length;
	if (l > 15) {
		for (var i = 0; i < l; i++) {
			var tmp = parseInt(num[i], 10);
			if ((i % 2) === 0) {
				if (2 * tmp > 9) {
					sum += (2 * tmp - 9);
				}
				else {
					sum += 2 * tmp;
				}
			}
			else {
				sum += tmp;
			}
		}
		return sum % 10 === 0;
	}
	else
		return false;
}

function testLimit(obj) {
	$.ajax({
		url: config.api_path + "members/" + member.id,
		type: "POST",
		async: false,
		contentType: "application/json",
		data: JSON.stringify({
			creditCarNo: obj.card_num.replace(/ /g, ""),
			industry: obj.industry,
			education: obj.education,
			email: obj.email,
			billEmail: obj.bill_email,
			billPassword: obj.password
		}),
		dataType: "json",
		success: function (json) {
			member.limit = json.creditLimit;
			member.rank = json.rankOfLimit;
			member.anothertest = 0;
		},
		error: function () {
			alert(config.api_path + "members/" + member.id);
		}
	});
}

function sendVarificationCode(phone_num) {
	return $.ajax({
		url: config.api_path + "sms/" + phone_num,
		type: "GET",
		dataType: "text",
		error: function () {
			alert(config.api_path + "sms/" + phone_num);
		}
	});
}

function matchVarificationCode(vcode, phone_num) {
	return $.ajax({
		url: config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode,
		type: "POST",
		async: false,
		data: JSON.stringify({
			mobilePhone: phone_num,
			code: vcode
		}),
		dataType: "text",
		error: function () {
			alert(config.api_path + "members/" + member.id + "/" + phone_num + "/" + vcode);
		}
	});
}

function countPayback(obj) {
	$.ajax({
		url: config.api_path + "app/saveCost",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			term: obj.term,
			amt: obj.amount,
			memberId: member.id
		}),
		dataType: "json",
		success: function (json) {
			$("#each-term").html(json.payBackEachTerm);
			$("#saved").html(json.savedCost);
		},
		error: function () {
			alert(config.api_path + "app/saveCost");
		}
	});
}

function applyLoan(obj) {
	return $.ajax({
		url: config.api_path + "app",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			term: obj.term,
			amt: obj.amount,
			memberId: member.id
		}),
		dataType: "text",
		error: function () {
			alert(config.api_path + "app");
		}
	});
}

function addCreditCard(new_card) {
	return $.ajax({
		url: config.api_path + "members/" + member.id + "/creditCards/" + new_card,
		type: "POST",
		async: false,
		dataType: "text",
		error: function () {
			alert(config.api_path + "members/" + member.id + "/creditCards/" + new_card);
		}
	});
}

function loanToThisCard(card_num) {
	return $.ajax({
		url: config.api_path + "app/members/" + member.id + "/creditCard/" + card_num,
		type: "POST",
		data: card_num,
		dataType: "text",
		error: function () {
			alert(config.api_path + "app/members/" + member.id + "/creditCard/" + card_num);
		}
	});
}

function addOptions(element_id, json) {
	var tmp = [];
	for (var i in json) {
		tmp += "<option value=" + json[i].key + ">" + json[i].value + "</option>";
	}
	$("#" + element_id).append($(tmp)).selectmenu().selectmenu("refresh");
}

// Events
navigateThruStatus();

$(document).on("pagecreate", "#limit", function () {
	if (member.id_card) {
		$("#front-num").html(member.id_card).css("color", "#222222");
		$("#front-upload").attr("disabled", true);
		$("#tip-front").attr("src", "resources/img/public/correct.png");
	}
	else {
		$("#front-upload").change(function (e) {
			$.mobile.loading("show", {html: "<span><center><img src='resources/img/public/correct.png'></center></span>"});
			var form_data = new FormData();
			form_data.append("idCardFrontFile", e.target.files[0]);
			recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardFront", "json").success(function (json) {
				$("#front-num").html(json.idNo).css("color", "#222222");
				$("label[for='front-upload']").css("border-color", "#c0c0c0");
				$("#tip-front").attr("src", "resources/img/public/correct.png");
				$("#front-upload").attr("disabled", true);
				member.id_card = json.idNo;
				member.gender = json.sex;
				localStorage.setItem("idcard_front", json.idNo);
				localStorage.setItem("gender", json.sex);
			}).error(function () {
				$("#front-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
				$("label[for='front-upload']").css("border-color", "#cc0000");
				$("#tip-front").attr("src", "resources/img/public/wrong.png");
			}).complete(function () {
				$.mobile.loading("hide");
			});
		});
	}

	if (member.valid_thru) {
		$("#back-num").html("有效期至" + member.valid_thru).css("color", "#222222");
		$("#back-upload").attr("disabled", true);
		$("#tip-back").attr("src", "resources/img/public/correct.png");
	}
	else {
		$("#back-upload").change(function (e) {
			$.mobile.loading("show", {html: "<span><center><img src='resources/img/public/correct.png'></center></span>"});
			var form_data = new FormData();
			form_data.append("idCardBackFile", e.target.files[0]);
			recongizeIdCard(form_data, config.api_path + "members/" + member.id + "/idCardBack", "text").success(function (text) {
				$("#back-num").html("有效期至" + text).css("color", "#222222");
				$("label[for='back-upload']").css("border-color", "#c0c0c0");
				$("#tip-back").attr("src", "resources/img/public/correct.png");
				$("#back-upload").attr("disabled", true);
				member.valid_thru = text;
				localStorage.setItem("valid_thru", text);
			}).error(function () {
				$("#back-num").html("无法识别, 请重新拍摄!").css({"color": "#cc0000", "border-color": "#cc0000"});
				$("label[for='back-upload']").css("border-color", "#cc0000");
				$("#tip-back").attr("src", "resources/img/public/wrong.png");
			}).complete(function () {
				$.mobile.loading("hide");
			});
		});
	}

	if (!dict.bincode) {
			getBincode();
	}

	$("#credit-card").on("keyup", function (e) {
		$("#card-tip").hide();
		$("#tip-credit").css({"height": "22px", "width": "32px"});
		var num = $(this).val();
		if (num)
			$("#credit-num").hide(); 
		else
			$("#credit-num").show();

		if (num.length < 2) {
			$("#tip-credit").attr("src", "resources/img/card_icon/card.png");
		}
		else if (num.length <= 6) {
			if (num[0] == "4") {
				$("#tip-credit").attr("src", "resources/img/card_icon/visa.png");
			}
			else if (num[0] == "5") {
				$("#tip-credit").attr("src", "resources/img/card_icon/master.png");
			}
		}
		else if (num.length > 6) {
			$.each(dict.bincode, function (i, val) {
				if (val.binNo == num.replace(/ /g, "").slice(0, 6)) {
					switch(dict.bincode[i].bankNo){
						case 1:
							$("#tip-credit").attr("src", "resources/img/card_icon/icbc.png");
							break;
						case 2:
							$("#tip-credit").attr("src", "resources/img/card_icon/abc.png");
							break;
						case 3:
							$("#tip-credit").attr("src", "resources/img/card_icon/bc.png");
							break;
						case 4:
							$("#tip-credit").attr("src", "resources/img/card_icon/cbc.png");
							break;
						case 5:
							$("#tip-credit").attr("src", "resources/img/card_icon/ctb.png");
							break;
						case 6:
							$("#tip-credit").attr("src", "resources/img/card_icon/ceb.png");
							break;
						case 7:
							$("#tip-credit").attr("src", "resources/img/card_icon/cgb.png");
							break;
						case 8:
							$("#tip-credit").attr("src", "resources/img/card_icon/hxb.png");
							break;
						case 9:
							$("#tip-credit").attr("src", "resources/img/card_icon/cmsb.png");
							break;
						case 10:
							$("#tip-credit").attr("src", "resources/img/card_icon/pingan.png");
							break;
						case 11:
							$("#tip-credit").attr("src", "resources/img/card_icon/cib.png");
							break;
						case 12:
							$("#tip-credit").attr("src", "resources/img/card_icon/cmb.png");
							break;
						case 13:
							$("#tip-credit").attr("src", "resources/img/card_icon/spdb.png");
							break;
						case 14:
							$("#tip-credit").attr("src", "resources/img/card_icon/citic.png");
							break;
						case 15:
							$("#tip-credit").attr("src", "resources/img/card_icon/psbc.png");
							break;
						default:
							$("#tip-credit").attr("src", "resources/img/card_icon/card.png");
					}
				}
			});
		}
		
		if(validateCardNo(num.replace(/ /g, ""))) {
			$.ajax({
				url: config.api_path + "members/" + member.id + "/creditCard/" + num.replace(/ /g, ""),
				type: "GET",
				async: false,
				dataType: "text",
				success: function (text) {
					if (text == "true") {
						$("#tip-credit").attr("src", "resources/img/public/wrong.png").css({"height": "22px", "width": "22px"});
						$("#card-tip").show();
					}
					else {
						localStorage.setItem("credit_card", num);
						if (!member.anothertest) {
							$("#next-step").attr("href", "#basic-info");
						}
						else{
							$("#next-step").attr("href", "#result").off().click(function () {
								var obj = {};
								obj.card_num = num;
								obj.industry = member.industry;
								obj.education = member.education;
								obj.email = member.email;
								obj.bill_email = member.billemail;
								obj.password = member.password;
								testLimit(obj);
							});
						}	
					}
				},
				error: function () {
					// alert(config.api_path + member.id + "/creditCard/" + num.replace(/ /g, ""));
					// $("#tip-credit").attr("src", "resources/img/public/wrong.png");
					// $("#card-tip").show();
					alert(config.api_path + "members/" + member.id + "/creditCard/" + num.replace(/ /g, ""));
				}
			});
		}
		else {
			$("#next-step").attr("href", "#");
		}

		if (num.replace(/ /g, "").length % 4 === 0 && e.keyCode != 8) {
			$(this).val(num + " ");
		}
	});

	$("#credit-card").on("paste", function () {
		$(this).trigger("keyup");
	});

	if (member.card_num) {
		$("#credit-card").val(member.card_num).trigger("keyup");
	}
});

$(document).on("pageshow", "#limit", function(){
	if (member.anothertest) {
		$("#credit-card").focus().val("");
		$("#tip-credit").attr("src", "resources/img/card_icon/card.png");
	}

	if (member.isnew) {
		$("#pop-limit").popup("open");
		setTimeout(function () {$("#pop-limit").remove();}, 3000);
	}
});

$(document).on("pagecreate", "#basic-info", function(){
	if(!dict.industry){
		$.getJSON(config.api_path + "dict/industry", function(json){
			addOptions("industry-select", json);
			dict.industry = json;
		});
	}
	
	if(!dict.education){
		$.getJSON(config.api_path + "dict/education", function(json){
			addOptions("education-select", json);
			dict.education = json;
		});
	}
	
	if (member.industry) {
		$("#industry-select option[value='" + member.industry + "']").attr("selected", "selected");
	}

	if (member.education) {
		$("#education-select option[value='" + member.education + "']").attr("selected", "selected");
	}

	if (member.bill_email) {
		$("#mail-new").val(member.bill_email);
	}

	if (member.password) {
		$("#password").val(member.password);
	}

	$("#industry-select").change(function(){
		member.industry = $(this).val();
		localStorage.setItem("industry", $(this).val);
		$("#industry-txt").hide();
	});
	
	$("#education-select").change(function(){
		member.education = $(this).val();
		localStorage.setItem("education", $(this).val);
		$("#education-txt").hide();
	});
	
	$("#email").keyup(function(){
		$("#mail-same").html($(this).val());
		localStorage.setItem("email", $(this).val);
		if($(this).val().length)
			$("#email-txt").hide();
		else
			$("#email-txt").show();
		
		if($("#industry-select").val() && $("#education-select").val() && $(this).val().length > 8){
			$("#hand-in").attr({
				"href": "#billmail",
				"data-rel": "popup"
			});
		}
	});

	$("#mail-new").click(function () {
		localStorage.setItem("bill_email", $(this).val);
		$("#radio-new").trigger("click");
	});
	
	$("#password").click(function () {
		localStorage.setItem("password", $(this).val);
	});

	$("#skip, #continue").click(function(){
		var obj = {};
		obj.card_num = $("#credit-card").val();
		member.cardnum = obj.card_num;
		obj.industry = $("#industry-select").val();
		member.industry = obj.industry;
		obj.education = $("#education-select").val();
		member.education = obj.education;
		obj.email = $("#email").val();
		member.email = obj.email;
		if($("#mail-new").val().length > 8)
			obj.bill_email = $("#mail-new").val();
		else
			obj.bill_email = obj.email;
		member.billemail = obj.blii_email;
		obj.password = $("#password").val();
		member.password = obj.password;
		testLimit(obj);
	});
});

$(document).on("pagecreate", "#result", function(){
	$("#option-2").click(function(){
		$("#share").show();
	});
	
	$("#share").click(function(){
		$(this).hide();
	});
	
	$("#option-3").click(function(){
		$("#next-step").attr("href", "#?123");
		member.anothertest = true;
	});
});

$(document).on("pagebeforeshow", "#result", function(){
	if (!member.limit) {
		getCreditLimit();
	}

	if (!member.avlcrl) {
		getAvlCrl();
	}

	$("#amt-shown").html(member.limit);
	$("#rank-shown").html(Math.round(member.rank * 100) + "&#37");
	if(member.limit > 4000){
		$("#rank-cmt").html("，官人您是权贵啊！");
		$("#option-1").html("巨款啊！现在就去申请借款");
		$("#option-2").html("去跟小伙伴嘚瑟一下");
		$("#option-3").html("换张信用卡再试试");
	}
	
	if(!member.loanable){
		$.ajax({
			url: config.api_path + "app/members/" + member.id,
			type: "GET",
			dataType: "text",
			async: false,
			success: function(text){
				member.loanable = ("false" == text);
				if(member.avlcrl > 1000 && member.loanable){
					$("#option-1").attr("href", "#loan");
				}
				else{
					$("#option-1").attr("href", "#");
				}
			},
			error: function () {
				if (config.debug)
					alert(config.api_path + "app/members/" + member.id);
			}
		});
	}
	
});

$(document).on("pagecreate", "#loan", function () {

});

$(document).on("pageshow", "#loan", function () {
	if(!member.loanable){
		$.ajax({
			url: config.api_path + "app/members/" + member.id,
			type: "GET",
			dataType: "text",
			async: false,
			success: function(text){
				member.loanable = ("false" == text);
				if (!member.loanable) {
					$.mobile.navigate("#suspension");
				}
			},
			error: function () {
				if (config.debug)
					alert(config.api_path + "app/members/" + member.id);
			}
		});
	}

	if (!member.avlcrl) {
		getAvlCrl();
	}

	$("#loan-limit").html(Math.round(member.avlcrl));
	
	$("#phone").off("click").click(function () {
		if($(this).val.length > 0)
			$("#phone-txt").hide();
		else
			$("#phone-txt").show();
	});

	app.term = "3";
	$("#amount").val("").off("keyup").keyup(function(){
		var tmp = $("#amount").val();
		if(tmp.length > 0)
			$("#amount-txt").hide();
		else
			$("#amount-txt").show();
			
		if (tmp.length > 3 && parseInt(tmp, 10) % 100) {
			$(this).val(parseInt(tmp, 10) - (parseInt(tmp, 10) % 100));
		}
			
		if(parseInt(tmp, 10) > parseInt(member.avlcrl, 10))
			$(this).val(parseInt(member.avlcrl));

		if ($(this).val().length > 3) {
			app.amount = $(this).val();
			countPayback(app);
		}
	});
	
	$("#term-3").off("click").click(function(){
		$("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
        $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
		app.term = "3";
		app.amount = $("#amount").val();
		countPayback(app);
	});
	
	$("#term-6").off("click").click(function(){
        $("#term-3").toggleClass("term-chose").toggleClass("term-chose-not");
        $("#term-6").toggleClass("term-chose").toggleClass("term-chose-not");
		app.term = "6";
		app.amount = $("#amount").val();
		countPayback(app);
	});
	
	if(member.mobile_varified == "true"){
		$("#varifying").remove();
		member.validate = 1;
	}
	else{	
		$("#acquire-code").off("click").click(function(){
			var phone_num = $("#phone").val();
			if(phone_num.length != 11)
				alert("请输入正确的手机号码!");
			else{
				$.get(config.api_path + "dict/mobileArea/" + phone_num, function(text){
					if(text == "北京" || text == "上海" || text == "广州" || text == "深圳") {
						member.phone = phone_num;
						sendVarificationCode(member.phone).success(function(){
							alert("您的验证码已发送!");
						}).error(function () {
							alert("您的验证码发送失败!");
						}
						);
					}
					else {
						// alert("您的手机非北上广深号码, 暂时无法为您服务!");
						$("#out-of-area").show();
						returnFootPrint(member.id, "0");
					}
				});
			}
		});
		
		$("#phone").off().keyup(function(){
			if($(this).val())
				$("#phone-txt").hide();
			else
				$("#phone-txt").show();
		});
		
		$("#code").off().keyup(function(){
			var vcode = $(this).val();
			if(vcode)
				$("#code-txt").hide();
			else
				$("#code-txt").show();
				
			if(vcode.length == 6 && member.phone.length == 11){
				matchVarificationCode(vcode, member.phone).success(function(text){
					if("true" == text){
						alert("您已输入有效的验证码!");
						member.validate = true;
					}
					else
						alert("验证码错误!");
				});
			}
		});
	}
	
	if(!member.creditcard){
		member.creditcard = [];
		$.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
			var tmp = [];
			$.each(data, function(ind, obj){
				tmp += "<div class='card-container'>" + obj.cardNo +"</div><hr>";
				member.creditcard.push([obj.cardNo, obj.bank]);
			});
			// $("#add-another-2").before($(tmp)).popup("refresh");
			$("#cardlist-2").prepend($(tmp));
		});
	}

	$("#request").off("click").click(function(){
		if($("#agree").is(":checked") && member.validate && app.term && app.amount){
			applyLoan(app);
			if(parseInt(member.status, 10) < 10){
				$.mobile.navigate("#suspension");
				$(this).off("click");
			}
			else {
				$(this).html("确认, 去选择信用卡");
				$("#cardlist-2").popup("open");
			}
		}
	});

	$("#Y-2").click(function(){
		loanToThisCard(app.card).success(function(){});
	});
	
	$("#N-2").click(function(){
		$("#card-confirm-2").hide();
	});
	
	$("#add-another-2").click(function(){
		$("#card-add-box-2").show();
	});
	
	$("#return-2").click(function(){
		$("#card-add-box-2").hide();
	});
	
	$("#addcard-2").click(function(){
		addCreditCard($("#new-cardnum-2").val()).success(function(){
			$("#add-another-2").before($("<div class='card-container'>" + $("#new-cardnum-2").val() +"</div><hr>"));
			$("#card-add-box-2").hide();
		}).error(function(){
			$("#card-add-box-2").hide();
		});
	});
});

$(document).on("click", ".card-container", function () {
	app.card = $(this).html();
	$("#cardlist-2").popup("close");
	$("#card-confirm-2").show();
});

$(document).on("pagecreate", "#congratulation", function(){
	var appNo_pattern = /(?:appNo=)\d+/;
	var appNo = appNo_pattern.exec(window.location).toString();
	appNo = appNo.slice(6, appNo.length);

	$.ajax({
		url: config.api_path + "app/" + appNo,
		type: "GET",
		async: false,
		dataType: "json",
		success: function (json) {
			$("#amt-x").html(json.amt);
			$("#term-" + json.term + "-shown").css("background-color", "#3ca0e6");
			$("#each-x").html(json.repayPerTerm);
			$("#saved-x").html(json.saveCost);
		},
		error: function () {
			alert(config.api_path + "app/" + appNo);
		}
	});

	$("#go-choose-card").click(function(){
		if(!member.creditcard){
			member.creditcard = [];
			$.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
				var tmp = [];
				$.each(data, function(ind, obj){
					tmp += "<div class='card-container'>" + obj.cardNo +"</div><hr>";
					member.creditcard.push([obj.cardNo, obj.bank]);
				});
				$("#add-another").before($(tmp));
				$(".card-container").click(function(){
					app.card = $(this).html();
					$("#num-tail").html(app.card.slice(app.card.length - 4, app.card.length));
					$("#cardlist").popup("close");
					$("#card-confirm").show();
				});
			});
		}
	});
	
	$("#Y").click(function(){
		loanToThisCard(app.card).success(function(){});
	});
	
	$("#N").click(function(){
		$("#card-confirm").hide();
	});
	
	$("#add-another").click(function(){
		$("#card-add-box").show();
	});
	
	$("#return").click(function(){
		$("#card-add-box").hide();
	});
	
	$("#addcard").click(function(){
		addCreditCard($("#new-cardnum").val()).success(function(){
			$("#add-another").before($("<div class='card-container'>" + $("#new-cardnum").val() +"</div><hr>"));
			$("#add-card-box").hide();
		}).error(function(){
			$("#add-card-box").hide();
		});
	});
});

$(document).on("pagecreate", "#repayment-0", function(){
	$.ajax({
		url: config.api_path + "account/members/" + member.id,
		type: "GET",
		async: false,
		dataType:"json",
		success: function(json){ 
			member.loan = json;
		},
		error: function () {
			if (config.debug)
				alert(config.api_path + "account/members/" + member.id);
		}
	});

	generateSumPage(member.loan);
	generateRepaymentPages(member.loan);
	registerEvents(member.loan);
	
	function generateSumPage(obj){
		$("#total-amount").html(obj.totalAmount);
		$("#total-times").html(obj.loanCount);
		$("#total-payback").html(obj.totalDueAmt);
		$("#total-saved").html(Math.round(obj.totalSavedCost + 100) / 100);

		for(var i = 0; i < obj.loanCount; i++){
			var tmp = "<li><div class='sl' id='" + ("sl-" + (i + 1)) + "'></div><div class='sr' id='" + ("sr-" + (i + 1)) + "'></div></li>";
			$("#total-specific").append(tmp);
			$("#sl-" + (i + 1)).html(i + 1);
			var readable_date = getReadableDate(obj.loans[i].startDate);
			$("#sr-" + (i + 1)).append("借款日期: " + readable_date + "<br>借款金额: &yen" + obj.loans[i].amount + "<br>注入卡片: 尾号" + obj.loans[i].creditCardNo.slice(obj.loans[i].creditCardNo.length - 4, obj.loans[i].creditCardNo.length) + "<br>总计应还: &yen" + obj.loans[i].dueAmt + "<br>较信用卡最低还款额，约省&yen" + Math.round(obj.loans[i].savedCost * 100) / 100);
		}
	}
	
	function generateRepaymentPages(obj){
		var loans = obj.loans;
		for(var i = 0; i < loans.length; i++){
			var id = "repayment-" + i;
			if(i){
				var tmp = $("<div data-role='page' id='" + id + "'>" + $("#repayment-0").html() + "</div>");
				tmp.appendTo($("body"));
    			$.mobile.initializePage();
			}
			$("#" + id + " .r-time").html(getReadableDate(loans[i].startDate));
			$("#" + id + " .r-amt").html(loans[i].amount);
			$("#" + id + " .r-tail").html(loans[i].creditCardNo.slice(loans[i].creditCardNo.length - 4, loans[i].creditCardNo.length));
			$("#" + id + " .r-next").html(loans[i].dueAmt);
			var date = new Date(loans[i].startDate);
			var month = date.getMonth()+1;
			var duemonth = month + loans[i].paidTerm + 1;
			var day = date.getDate();
			if(duemonth > 12)
				duemonth -= 12;
			$("#" + id + " .r-deadline").html(duemonth + "月" + day + "日");

			$("#" + id + " li:nth-child(1) .li-r").html(getReadableDate(loans[i].startDate));
			$("#" + id + " li:nth-child(2) .li-r").html("尾号" + loans[i].creditCardNo.slice(loans[i].creditCardNo.length - 4, loans[i].creditCardNo.length));
			$("#" + id + " li:nth-child(3) .li-r").html(loans[i].amount);
			$("#" + id + " li:nth-child(4) .li-r").html(obj.loanCount + "期(已还" + loans[i].paidTerm + "期)");
			$("#" + id + " li:nth-child(5) .li-r").html("每月" + day + "日");
			$("#" + id + " li:nth-child(6) .li-r").html(loans[i].principal);
			$("#" + id + " li:nth-child(7) .li-r").html(loans[i].restPrincipal);
			
			for(var j = 0; j < loans[i].paidTerm; j++){
				if(j === 0)
					$("#" + id + " ul:last-child").after("<li></li>");
				else
					$("#" + id + " ul:last-child").before("<li></li>");
			}
		}
	}

	function registerEvents(obj){
		if(obj.loans.length > 1){
			$("#repayment-0").on("swipeleft", function(){
				$.mobile.changePage("#repayment-1", {transition: "slide"});
			});
			
			for(var i = 1; i < obj.loans.length - 1; i++){
				registerSlides(i);
			}

			$("#repayment-" + (obj.loans.length - 1)).on("swiperight", function(){
				$.mobile.changePage("#repayment-" + (obj.loans.length - 2), {transition: "slide", reverse: true});
			});
		}

		for(var j = 0; j < obj.loans.length; j++){
			popLoanSpecific(j);
		}
	}

	function registerSlides(i) {
		$("#repayment-" + i).on("swipeleft", function(){
			$.mobile.changePage("#repayment-" + (i + 1), {transition: "slide"});
		});

		$("#repayment-" + i).on("swiperight", function(){
			$.mobile.changePage("#repayment-" + (i - 1), {transition: "slide", reverse: true});
		});
	}

	function popLoanSpecific(i){
		$("#repayment-" + i + " ul li:nth-child(2)").click(function(){
			$("#repayment-" + i + " .r-popup").popup("open");
		});
	}

	function getReadableDate(million_seconds){
		var date = new Date(million_seconds);
		var month = date.getMonth()+1;
		var day = date.getDate();
		var year = date.getFullYear();
		return year + "-" + month + "-" + day;
	}
	
	



	
	
});

$(document).on("pagecreate", "#sum-loan", function(){
	$("#sum-loan a").click(function(){
		$.mobile.back();
	});
});




















$("a").on({
	vmousedown: function(){
		$(this).css("box-shadow", "0 0 5px black");
	}, vmouseup: function(){
		$(this).css("box-shadow", "none");
	}		
});

$("#got-it, #got-it-x, #got-it-y, #got-it-z").click(function(){
	WeixinJSBridge.call("closeWindow");
});

$(".bkg").click(function () {
	$(this).hide();
});

window.onunload = function () {
	var print_status;
	var pattern = /#\w+\?/;
	var hash = pattern.exec(window.location).toString();
	hash = hash.slice(0, hash.length - 1);
	switch(hash){
		case "#basic-info":
			print_status = "0";
			break;
		case "#result":
			print_status = "1";
			break;
		case "#congratulation":
			print_status = "2";
			break;
		default:
			print_status = "-1";
	}
	if (print_status !== "-1")
		returnFootPrint(member.id, print_status);
};

function returnFootPrint(id, status) {
	$.ajax({
		url: config.api_path + "members/" + id + "/status/" + status,
		type: "GET",
		async: false,
		error: function () {
			if (config.debug)
				alert(config.api_path + "members/" + id + "/status/" + status);
		}
	});
}