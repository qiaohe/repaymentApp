// Configuration
var config = {};
config.api_path = "http://180.168.35.37/repaymentApp/api/"

var id_pattern = /(?:memberId=)\d+/;
config.member_id = id_pattern.exec(window.location).toString();
config.member_id = config.member_id.slice(9, config.member_id.length);

var status_pattern = /(?:status=)\d+/;
config.status = status_pattern.exec(window.location).toString();
config.status = config.status.slice(7, status.length);

//config.member_id = '2';
//config.status = '4';

config.debug = true;

var member = {};
var app = {};
var dict = {};

member.id = config.member_id;
member.status = config.status;

$.ajax({
	url: config.api_path + "members/" + member.id,
	type: "GET",
	dataType: "json",
	async: false,
	success: function(json){
		member.id_card = json.idCardNo;
		member.valid_thru = json.validThru;
		member.industry = json.industry;
		member.education = json.education;
		member.email = json.email;
	},
	error: stdError
});

$.ajax({
	url: config.api_path + "members/" + member.id + "/crl",
	type: "GET",
	dataType: "json",
	async: false,
	success: function(json){
		member.limit = json.creditLimit;
		member.rank = json.rankOfLimit;
	},
	error: stdError
});

$.ajax({
	url: config.api_path + 'members/' + member.id + '/avlCrl',
	type: "GET",
	dataType: "text",
	async: false,
	success: function(text){
		member.avlcrl = text;
	},
	error: stdError
});

// Actions
function stdError(jqXHR, textStatus, errorThrown){
	if(config.debug)
		alert(textStatus + ' ' + errorThrown);
}

function recongizeIdCard(form_data, url_path, type){
	return $.ajax({
		url: url_path,
		type: "POST",
		data: form_data,
		// cache: false,
		processData: false,
		contentType: false,
		dataType: type
	});
}

function validateCardNo(num){
	var sum = 0, l = num.length;
	if(l > 15){
		for(var i = 0; i < l; i++){
			var tmp = parseInt(num[i]);
			if(!(i % 2)){
				(2 * tmp > 9) ? (sum += (2 * tmp - 9)) : (sum += 2 * tmp);
			}
			else{
				sum += tmp;
			}
		}
		return !(sum % 10) ;
	}
	else
		return false;
}

function testLimit(obj){
	$.ajax({
		url: config.api_path + "members/" + member.id,
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			creditCarNo: obj.card_num,
			industry: obj.industry,
			education: obj.education,
			email: obj.email,
			billEmail: obj.bill_email,
			billPassword: obj.password
		}),
		dataType: "json",
		async: false,
		success: function(json){
			member.limit = json.creditLimit;
			member.rank = json.rankOfLimit;
		},
		error: stdError
	});
}

function getSIMDistrict(phone_num){
	return $.ajax({
		url: config.api_path + "dict/mobileArea/" + phone_num,
		type: "GET",
		dataType: "text",
		error: stdError
	});
}

function sendVarificationCode(phone_num){
	return $.ajax({
		url: config.api_path + "sms/" + phone_num,
		type: "GET",
		dataType: "text",
		error: stdError
	});
}

function matchVarificationCode(vcode, phone_num){
	return $.ajax({
		url: config.api_path + 'sms/' + phone_num + '/' + vcode,
		type: "POST",
		async: false,
		data: JSON.stringify({
			mobilePhone: phone_num,
			code: vcode
		}),
		dataType: "text",
		error: stdError
	});
}

function countPayback(obj){
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
		success: function(json){
			$('#each-term').html(json.payBackEachTerm);
			$('#saved').html(json.savedCost);
		},
		error: stdError
	});
}

function applyLoan(obj){
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
		error: stdError
	});
}

function addCreditCard(new_card){
	return $.ajax({
		url: config.api_path + "members/" + member.id + "/creditCards/" + new_card,
		type: "POST",
		dataType: "text",
		error: stdError
	});
}

function loanToThisCard(card_num){
	return $.ajax({
		url: config.api_path + "app/members/" + member.id + "/creditCard/" + card_num,
		type: "POST",
		data: card_num,
		dataType: "text",
		error: stdError
	});
}

function addOptions(element_id, json){
	var tmp = [];
	for(var i in json) {
		tmp += '<option value=' + json[i].key + '>' + json[i].value + '</option>';
	}
	$('#' + element_id).append($(tmp)).selectmenu().selectmenu('refresh');
}

// Events
$(document).on('pagecreate', '#limit', function(){
	$('#front-upload').change(function(e){
		var form_data = new FormData();
		$.mobile.loading('show');
		form_data.append('idCardFrontFile', e.target.files[0]);
		recongizeIdCard(form_data, config.api_path + 'members/' + member.id + '/idCardFront', 'json').success(function(json){
			$.mobile.loading('hide');
			$('#front-num').html(json.idNo).css('color', '#333333');
			member.gender = json.sex;
			$('#tip-front').attr('src', 'resources/img/public/correct.png');
			$('#front-upload').attr('disabled', true);
		}).error(function(){
			$.mobile.loading('hide');
			$('#front-num').html('无法识别, 请重新拍摄!').css({'color':'#cc0000', 'border-color':'#cc0000'});
			$('#tip-front').attr('src', 'resources/img/public/wrong.png');
		});
	});

	$('#back-upload').change(function(e){
		var form_data = new FormData();
		$.mobile.loading('show');
		form_data.append('idCardBackFile', e.target.files[0]);
		recongizeIdCard(form_data, config.api_path + 'members/' + member.id + '/idCardBack', 'text').success(function(text){
			$.mobile.loading('hide');
			$('#back-num').html("有效期至" + text).css('color', '#333333');
			$('#tip-back').attr('src', 'resources/img/public/correct.png');
			$('#back-upload').attr('disabled', true);
		}).error(function(){
			$.mobile.loading('hide');
			$('#back-num').html('无法识别, 请重新拍摄!').css({'color':'#cc0000', 'border-color':'#cc0000'});
			$('#tip-back').attr('src', 'resources/img/public/wrong.png');
		});
	});

	$('#credit-card').keyup(function(){
		if(!dict.bincode){
			$.getJSON(config.api_path + "dict/binCode", function(json){
				dict.bincode = json;
			});
		}
		
		num = $(this).val();
		if(num)
			$('#credit-num').hide();
		else
			$('#credit-num').show();
		
		if(validateCardNo(num)){
			$('#next-step').attr('href', '#basic-info');
		}
	});
});

$(document).on('pagecreate', '#basic-info', function(){
	if(!dict.industry){
		$.getJSON(config.api_path + "dict/industry", function(json){
			addOptions('industry-select', json);
			dict.industry = json;
		});
	}
	
	if(!dict.education){
		$.getJSON(config.api_path + "dict/education", function(json){
			addOptions('education-select', json);
			dict.education = json;
		});
	}
	
	$('#industry-select').change(function(){
		member.industry = $(this).val();
		$('#industry-txt').hide();
	});
	
	$('#education-select').change(function(){
		member.education = $(this).val();
		$('#education-txt').hide();
	});
	
	$('#email').keyup(function(){
		$('#mail-same').html($(this).val());
		if($(this).val().length)
			$('#email-txt').hide();
		else
			$('#email-txt').show();
		
		if($('#industry-select').val() && $('#education-select').val() && $(this).val().length > 8){
			$('#hand-in').attr({
				'href': '#billmail',
				'data-rel': 'popup'
			});
		}
	});

	$('#mail-new').click(function(){
		$('#radio-new').trigger('click');
	});
	
	$('#skip, #continue').click(function(){
		var obj = {};
		obj.card_num = $('#credit-card').val();
		member.cardnum = obj.card_num;
		obj.industry = $('#industry-select').val();
		member.industry = obj.industry;
		obj.education = $('#education-select').val();
		member.education = obj.education;
		obj.email = $('#email').val();
		member.email = obj.email;
		if($('#mail-new').val().length > 8)
			obj.bill_email = $('#mail-new').val();
		else
			obj.bill_email = obj.email;
		member.billemail = obj.blii_email;
		obj.password = $('#password').val();
		member.password = obj.password;
		testLimit(obj);
	});
});

$(document).on('pagecreate', '#result', function(){
	$('#option-2').click(function(){
		$('#share').show();
	});
	
	$('#share').click(function(){
		$(this).hide();
	});
	
	$('#option-3').click(function(){
		if(validateCardNo($('#credit-card').val())){
			$('#next-step').attr('href', '#result').click(function(){
				var obj = {};
				obj.card_num = $('#credit-card').val();
				obj.industry = member.industry;
				obj.education = member.education;
				obj.email = member.email;
				obj.bill_email = member.billemail;
				obj.password = member.password;
				testLimit(obj);
			});
		}
	});
});

$(document).on('pagebeforeshow', '#result', function(){
	$('#amt-shown').html(member.limit);
	$('#rank-shown').html(Math.round(member.rank * 100) + "&#37");
	if(member.limit > 4000){
		$('#rank-cmt').html('，官人您是权贵啊！');
		$('#option-1').html('巨款啊！现在就去申请借款');
		$('#option-2').html('去跟小伙伴嘚瑟一下');
		$('#option-3').html('换张信用卡再试试');
	}
	
	if(!member.loanable){
		$.ajax({
			url: config.api_path + 'app/members/' + member.id,
			type: "GET",
			dataType: "text",
			async: false,
			success: function(text){
				member.loanable = ('false' == text);
			},
			error: stdError
		});
	}
	
	if(member.avlcrl > 1000 && member.loanable){
		$('#option-1').attr('href', '#loan');
	}
	else{
		$('#option-1').attr('href', '#');
	}
});

$(document).on('pagebeforeshow', '#loan', function(){
	$('#loan-limit').html(Math.round(member.avlcrl));
	
	$('#amount').keyup(function(){
		var tmp = parseInt($(this).val());
		if(tmp.length > 0)
			$('#amount-txt').hide();
		else
			$('#amount-txt').show();
			
		if(tmp % 100 && tmp.length > 3)
			$(this).val(tmp -= (tmp % 100));
			
		if(tmp > member.avlcrl)
			$(this).val(member.avlcrl);
	});
	
	$('#term-3').click(function(){
		$('#term-3').css('background-color', '#3ca0e6');
		$('#term-6').css('background-color', '#c0c0c0');
		app.term = '3';
		app.amount = $('#amount').val();
		countPayback(app);
	});
	
	$('#term-6').click(function(){
		$('#term-6').css('background-color', '#3ca0e6');
		$('#term-3').css('background-color', '#c0c0c0');
		app.term = '6';
		app.amount = $('#amount').val();
		countPayback(app);
	});
	
	$('#acquire-code').click(function(){
		phone_num = $('#phone').val();
		if(phone_num.length != 11)
			alert('请输入正确的手机号码!');
		else{
			// $.get(config.api_path + "dict/mobileArea" + phone_num, function(text){
				// if(text="北京")
				// alert(text);
			// });
			member.phone = phone_num;
			sendVarificationCode(member.phone).success(function(){
				alert('您的验证码已发送!');
			});
		}
	});
	
	$('#phone').keyup(function(){
		if($(this).val())
			$('#phone-txt').hide();
		else
			$('#phone-txt').show();
	});
	
	$('#code').keyup(function(){
		var vcode = $(this).val();
		if(vcode)
			$('#code-txt').hide();
		else
			$('#code-txt').show();
			
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
	
	$('#agree').click(function(){
		if($(this).is(':checked') && member.validate && app.term && app.amount){
			$('#request').attr('href', '#suspension').click(function(){
				applyLoan(app).success(function(){});
			});
		}
	});
});

$(document).on('pagecreate', '#congratulation', function(){
	$('#go-choose-card').click(function(){
		if(!member.creditcard){
			member.creditcard = [];
			$.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
				var tmp = [];
				$.each(data, function(ind, obj){
					tmp += '<div class="card-container">' + obj.cardNo +'</div><hr>';
					member.creditcard.push([obj.cardNo, obj.bank]);
				});
				$('#add-another').before($(tmp));
				$('.card-container').click(function(){
					app.card = $(this).html();
					$('#num-tail').html(app.card.slice(app.card.length - 4, app.card.length));
					$('#cardlist').popup('close');
					$('#card-confirm').show();
				});
			});
		}
	});
	
	$('#Y').click(function(){
		loanToThisCard(app.card).success(function(){});
	});
	
	$('#N').click(function(){
		$('#card-confirm').hide();
	});
	
	$('#add-another').click(function(){
		$('#card-add-box').show();
	});
	
	$('#return').click(function(){
		$('#card-add-box').hide();
	});
	
	$('#addcard').click(function(){
		addCreditCard($('#new-cardnum').val()).success(function(){
			$('#add-another').before($('<div class="card-container">' + $('#new-cardnum').val() +'</div><hr>'));
		});
	});
});




























$('a').on({
	vmousedown: function(){
		$(this).css('box-shadow', '0 0 5px black');
	}, vmouseup: function(){
		$(this).css('box-shadow', 'none');
	}		
});

$('#got-it, #got-it-x, #got-it-y, #got-it-z').click(function(){
	WeixinJSBridge.call('closeWindow');
});

if(member.status == '5.2')
	$.mobile.navigate('#congratylation');