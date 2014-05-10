// Configuration
var config = {};
config.api_path = "http://180.168.35.37/repaymentApp/api/";
 
var id_pattern = /(?:memberId=)\d+/;
config.member_id = id_pattern.exec(window.location).toString();
config.member_id = config.member_id.slice(9, config.member_id.length);

var status_pattern = /(?:status=)\d+/;
config.status = status_pattern.exec(window.location).toString();
config.status = config.status.slice(7, config.status.length);

 // config.member_id = '1';
 // config.status = '8';

config.debug = false;

var member = {};
var app = {};
var dict = {};

member.id = config.member_id;
member.status = config.status;

if(member.status != '1' && member.status != '2'){
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
}

if(member.status == '5.1')
	$.mobile.navigate('#congratulation');

// Actions
function stdError(jqXHR, textStatus, errorThrown){
	if(config.debug)
		alert(textStatus + ' ' + errorThrown);
}

function recongizeIdCard(form_data, url_path, datatype){
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
});

$(document).on('pagebeforeshow', '#limit', function(){
	$('#credit-card').unbind().keyup(function(){
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
			if(!member.anothertest){
				$('#next-step').attr('href', '#basic-info');
			}
			else{
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
		}
		else
			$('#next-step').attr('href', '#');
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
		$('#next-step').attr('href', '#');
		member.anothertest = true;
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
		var tmp = $(this).val();
		if(tmp.length > 0)
			$('#amount-txt').hide();
		else
			$('#amount-txt').show();
			
		if(parseInt(tmp) % 100 && tmp.length > 3)
			$(this).val(parseInt(tmp) - (parseInt(tmp) % 100));
			
		if(parseInt(tmp) > parseInt(member.avlcrl))
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
	
	if(parseInt(member.status) > 6){
		$('#varifying').remove();
		member.validate = 1;
	}
	else{	
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
	}
	
	// $('#agree').click(function(){
		// if($(this).is(':checked') && member.validate && app.term && app.amount){
			// $('#request').attr('href', '#suspension').click(function(){
				// applyLoan(app).success(function(){
					// $('#request').off('click');
				// });
			// });
		// }
	// });
	$('#request').click(function(){
		if($('#agree').is(':checked') && member.validate && app.term && app.amount){
			if(member.status < 7){
				$.mobile.navigate('#suspension');
				applyLoan(app);
				$(this).off('click');
			}
			else{
				if(!member.creditcard){
					member.creditcard = [];
					$.get(config.api_path + "members/" + member.id +"/creditCard", function(data){
						var tmp = [];
						$.each(data, function(ind, obj){
							tmp += '<div class="card-container">' + obj.cardNo +'</div><hr>';
							member.creditcard.push([obj.cardNo, obj.bank]);
						});
						$('#add-another-2').before($(tmp));
						$('.card-container').click(function(){
							app.card = $(this).html();
							$('#num-tail-2').html(app.card.slice(app.card.length - 4, app.card.length));
							$('#cardlist-2').popup('close');
							$('#card-confirm-2').show();
						});
					});
				}
				$("#cardlist-2").popup('open');
				$('#Y-2').click(function(){
					loanToThisCard(app.card).success(function(){});
				});
				
				$('#N-2').click(function(){
					$('#card-confirm-2').hide();
				});
				
				$('#add-another-2').click(function(){
					$('#card-add-box-2').show();
				});
				
				$('#return-2').click(function(){
					$('#card-add-box-2').hide();
				});
				
				$('#addcard-2').click(function(){
					addCreditCard($('#new-cardnum-2').val()).success(function(){
						$('#add-another-2').before($('<div class="card-container">' + $('#new-cardnum-2').val() +'</div><hr>'));
						$('#card-add-box-2').hide();
					}).error(function(){
						$('#card-add-box-2').hide();
					});
				});
			}
		}
	});
});

$(document).on('pagecreate', '#congratulation', function(){
	$('#amt-x').html();

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
			$('#add-card-box').hide();
		}).error(function(){
			$('#add-card-box').hide();
		});
	});
});

$(document).on('pagecreate', '#repayment-0', function(){
	$.ajax({
		url: config.api_path + 'account/members/' + member.id,
		type: "GET",
		async: false,
		dataType:'json',
		success: function(json){ 
			member.loan = json;
		},
		error: stdError
	});

	generateSumPage(member.loan);
	generateRepaymentPages(member.loan);
	registerEvents(member.loan);
	
	function generateSumPage(obj){
		$('#total-amount').html(obj.totalAmount);
		$('#total-times').html(obj.loanCount);
		$('#total-payback').html(obj.totalDueAmt);
		$('#total-saved').html(Math.round(obj.totalSavedCost + 100) / 100);

		for(var i = 0; i < obj.loanCount; i++){
			var tmp = '<li><div class="sl" id="' + ('sl-' + (i + 1)) + '"></div><div class="sr" id="' + ('sr-' + (i + 1)) + '"></div></li>';
			$('#total-specific').append(tmp);
			$('#sl-' + (i + 1)).html(i + 1);
			var readable_date = getReadableDate(obj.loans[i].startDate);
			$('#sr-' + (i + 1)).append('借款日期: ' + readable_date + '<br>借款金额: &yen' + obj.loans[i].amount + '<br>注入卡片: 尾号' + obj.loans[i].creditCardNo.slice(obj.loans[i].creditCardNo.length - 4, obj.loans[i].creditCardNo.length) + '<br>总计应还: &yen' + obj.loans[i].dueAmt + '<br>较信用卡最低还款额，约省&yen' + Math.round(obj.loans[i].savedCost * 100) / 100);
		}
	}
	
	function generateRepaymentPages(obj){
		var loans = obj.loans;
		for(var i = 0; i < loans.length; i++){
			var id = "repayment-" + i;
			if(i){
				var tmp = $('<div data-role="page" id="' + id + '">' + $('#repayment-0').html() + '</div>');
				tmp.appendTo($('body'));
				//window.location.hash = id; 
    			$.mobile.initializePage();
			}
			$('#' + id + ' .r-time').html(getReadableDate(loans[i].startDate));
			$('#' + id + ' .r-amt').html(loans[i].amount);
			$('#' + id + ' .r-tail').html(loans[i].creditCardNo.slice(loans[i].creditCardNo.length - 4, loans[i].creditCardNo.length));
			$('#' + id + ' .r-next').html(loans[i].dueAmt);
			var date = new Date(loans[i].startDate);
			var month = date.getMonth()+1;
			var duemonth = month + loans[i].paidTerm + 1;
			var day = date.getDate();
			if(duemonth > 12)
				duemonth -= 12;
			$('#' + id + ' .r-deadline').html(duemonth + '月' + day + '日');

			$('#' + id + ' li:nth-child(1) .li-r').html(getReadableDate(loans[i].startDate));
			$('#' + id + ' li:nth-child(2) .li-r').html('尾号' + loans[i].creditCardNo.slice(loans[i].creditCardNo.length - 4, loans[i].creditCardNo.length));
			$('#' + id + ' li:nth-child(3) .li-r').html(loans[i].amount);
			$('#' + id + ' li:nth-child(4) .li-r').html(obj.loanCount + '期(已还' + loans[i].paidTerm + '期)');
			$('#' + id + ' li:nth-child(5) .li-r').html('每月' + day + '日');
			$('#' + id + ' li:nth-child(6) .li-r').html(loans[i].principal);
			$('#' + id + ' li:nth-child(7) .li-r').html(loans[i].restPrincipal);
			
			for(var j = 0; j < loans[i].paidTerm; j++){
				if(j == 0)
					$('#' + id + ' ul:last-child').after('<li></li>');
				else
					$('#' + id + ' ul:last-child').before('<li></li>');
			}
		}
	}

	function registerEvents(obj){
		if(obj.loans.length > 1){
			$('#repayment-0').on('swipeleft', function(){
				$.mobile.changePage('#repayment-1', {transition: 'slide'});
			});
			
			for(var i = 1; i < obj.loans.length - 1; i++){
				$('#repayment-' + i).registerSlides(i);
			}

			$('#repayment-' + (obj.loans.length - 1)).on('swiperight', function(){
				$.mobile.changePage('#repayment-' + (obj.loans.length - 2), {transition: 'slide', reverse: true});
			});
		}

		for(var i = 0; i < obj.loans.length; i++){
			popLoanSpecific(i)
		}
	}

	function registerSlides(i){
		$('#repayment-' + i).on('swipeleft', function(){
			$.mobile.changePage('#repayment-' + (i + 1), {transition: 'slide'});
		});

		$('#repayment-' + i).on('swiperight', function(){
			$.mobile.changePage('#repayment-' + (i - 1), {transition: 'slide', reverse: true});
		});
	}

	function popLoanSpecific(i){
		$('#repayment-' + i + ' ul li:nth-child(2)').click(function(){
			//
			$('#repayment-' + i + ' .r-popup').popup('open');
		});
	}

	function getReadableDate(million_seconds){
		var date = new Date(million_seconds);
		var month = date.getMonth()+1;
		var day = date.getDate();
		var year = date.getFullYear();
		return year + '-' + month + '-' + day;
	}
	
	



	
	
});

$(document).on('pagecreate', '#sum-loan', function(){
	$('#sum-loan a').click(function(){
		$.mobile.back();
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