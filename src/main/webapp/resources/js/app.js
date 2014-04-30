$(function (){ 


	var api_path = "http://180.168.35.37/repaymentApp/api/";
	
	var id_pattern = /(?:memberId=)\d+/;
	var member_id = id_pattern.exec(window.location).toString();
	member_id = member_id.slice(9, member_id.length);
	
	var status_pattern = /(?:status=)\d+/;
	var status = status_pattern.exec(window.location).toString();
	status = status.slice(7, status.length);
	
	// var member_id = '2';
	// var status = '4';

//limit
    // upload front
    $('#front-upload').change(function (e) {
        var formData = new FormData();
		$.mobile.loading("show");
        formData.append("idCardFrontFile", e.target.files[0]);
        $.ajax({
            url: api_path + "members/" + member_id + "/idCardFront",
            type: "POST",
            data: formData,
			cache: false,
            processData: false,
            contentType: false,
            dataType: "text",
            success: function (text) {
				$.mobile.loading("hide");
				$('#front-num').html(text).css('color', '#c0c0c0');
				$('#tip-front').attr('src', 'resources/img/public/correct.png');
            },
            error: function () {
				$.mobile.loading("hide");
				$('#front-num').html('无法识别, 请重新拍摄!').css({'color':'#cc0000', 'border-color':'#cc0000'});
				$('#tip-front').attr('src', 'resources/img/public/wrong.png');
			}
        });
    });
    // upload back
    $('#back-upload').change(function (e) {
        var formData = new FormData();
		$.mobile.loading("show");
        formData.append("idCardBackFile", e.target.files[0]);
        $.ajax({
            url: api_path + "members/" + member_id + "/idCardBack",
            type: "POST",
            data: formData,
			cache: false,
            processData: false,
            contentType: false,
            dataType: "text",
            success: function (text) {
				$.mobile.loading("hide");
				$('#back-num').html("有效期至" + text);
				$('#tip-back').attr('src', 'resources/img/public/correct.png');
            },
            error: function () {
				$.mobile.loading("hide");
				$('#back-num').html('无法识别, 请重新拍摄!').css('color', '#cc0000');
				$('#tip-back').attr('src', 'resources/img/public/wrong.png');
			}
        });
    });
    // credit card number
	var bincode;
	$('#credit-card').keyup(function(){
		if(!bincode){
			$.getJSON(api_path + "dict/binCode", function (json) {
				bincode = json;
			});
		}
		
		var num = $(this).val();
		if(num)
			$('#credit-num').hide();
		else
			$('#credit-num').show();
		
	});
	
    // $('#credit-card-number').keyup(function () {
		// num = $(this).val();
		// if(num.length > 0)
			// $('#placeholder-credit').hide();
		// else
			// $('#placeholder-credit').show();

		// if(num.length > 1){
			// if(3 == num[0]){
				// if(5 == num[1])
					// $('#card-img').attr('src', 'resources/img/jcb.png');
				// else if(6 == num[1])
					// $('#card-img').attr('src', 'resources/img/dalai.png');
				// else if(7 == num[1])
					// $('#card-img').attr('src', 'resources/img/yuntong.png');
			// }	
			// else if(4 == num[0])
				// $('#card-img').attr('src', 'resources/img/visa.png');
			// else if(5 == num[0])
				// $('#card-img').attr('src', 'resources/img/mastercard.png');
			// else if(6 == num[0] && 2 == num[1])
				// $('#card-img').attr('src', 'resources/img/cup.png');
		// }
		// else
			// $('#card-img').attr('src', 'resources/img/blank.png');
    // });

	var industry, education;
	if (!industry) {
		$.getJSON(api_path + "dict/industry", function (json) {
			if(!json[0]){
				json = [{"key":"1","value":"政府机关、社会团体"},{"key":"2","value":"军事、公检法"},{"key":"3","value":"学校、医院"},{"key":"4","value":"专业事务所"},{"key":"5","value":"信息通信、IT互联网"},{"key":"6","value":"金融业"},{"key":"7","value":"交通运输"},{"key":"8","value":"公共事业"},{"key":"9","value":"能源矿产"},{"key":"10","value":"商业零售、内外贸易"},{"key":"11","value":"房地产、建筑业"},{"key":"12","value":"加工、制造业"},{"key":"13","value":"餐饮、酒店、旅游"},{"key":"14","value":"服务、咨询"},{"key":"15","value":"媒体、体育、娱乐"},{"key":"16","value":"农林牧渔"},{"key":"17","value":"网店店主"},{"key":"18","value":"学生"},{"key":"19","value":"自由职业者"},{"key":"20","value":"其他"}];
			}
			var tmp = [];
			for(var i in json) {
				tmp += '<option value=' + json[i].key + '>' + json[i].value + '</option>';
			}
			$('#industry-select').append($(tmp)).selectmenu().selectmenu('refresh');
			industry = 1;
		});
	}

	if (!education) {
		$.getJSON(api_path + "dict/education", function (json) {
			if(!json[0]){
				json = [{"key":"1","value":"初中及以下"},{"key":"2","value":"高中、中专"},{"key":"3","value":"大专"},{"key":"4","value":"本科"},{"key":"5","value":"硕士"},{"key":"6","value":"博士"}];
			}
			var tmp = [];
			for(var i in json) {
				tmp += '<option value=' + json[i].key + '>' + json[i].value + '</option>';
			}
			$('#education-select').append($(tmp)).selectmenu().selectmenu('refresh');
			education = 1;
		});
	}
//basic-info
	$('#email').keyup(function(){
		$('#mail-same').html($(this).val());
	});

	$('#hand-in').click(function(){
		$('#bkg-0').show();
		$('#billmail').show();
	});
	
	$('#industry-select').change(function(){
		$('#industry-txt').hide();
	});
	
	$('#education-select').change(function(){
		$('#education-txt').hide();
	});
	
	$('#email').keyup(function(){
		if($(this).val().length > 0)
			$('#email-txt').hide();
		else
			$('#email-txt').show();
	});
//billmail
	var limit = 0, rank = 0, another_card = 0;
	$('#skip, #continue').click(function(){
		var mail;
		if($('#mail-new').val())
			mail = $('#mail-new').val();
		else
			mail = $('#email').val();
			
		if(!limit){
			$.ajax({
				url: api_path + "members/" + member_id,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					creditCarNo: $('#credit-card').val(),
					industry: $('#industry-select').val(),
					education: $('#education-select').val(),
					email: $('#email').val(),
					billEmail: mail,
					billPassword: $('#password').val()
				}),
				dataType: "json",
				success: function (json) {
					limit = json.creditLimit;
					rank = json.rankOfLimit;
					
					if(limit > 4000){
						$('#rank-cmt').html("，官人您是权贵啊！");
						$('#option-1').html('巨款啊！现在就去申请借款');
						$('#option-2').html('去跟小伙伴嘚瑟一下');
						$('#option-3').html('换张信用卡再试试');
						$('#amt-x, #loan-limit').html(limit);
					}

					$('#loan-limit').html(limit);
					$('#amt-shown').html(limit);
					$('#rank-shown').html(Math.round(rank * 100) + "&#37");
					
					
				},
				error: function () {
					console.log('error!');
				}
			});
			
		}        
	});  
// result
	var crl = 0;
	$('#option-1').click(function(){
		if(!crl){
		
			if(!member_id){
				var id_pattern = /(?:memberId=)\d+/;
				var member_id = id_pattern.exec(window.location).toString();
				member_id = member_id.slice(9, member_id.length);
			}
			
			$.get(api_path + "members/" + member_id + "/crl", function(text){
				crl = text;
			});
		}
		$('#loan-limit').html(crl);
	});
	
	$('#option-2').click(function(){
		$('#share').show();
	});
	
	$('#option-3').click(function(){
		another_card = 1;
		$('#next-step').attr('href', '#result').click(function(){
			
			$.ajax({
				url: api_path + "members/" + member_id,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					creditCarNo: $('#credit-card').val(),
					industry: $('#industry-select').val(),
					education: $('#education-select').val(),
					email: $('#email').val(),
					billEmail: mail,
					billPassword: $('#password').val()
				}),
				dataType: "json",
				success: function (json) {
					limit = json.creditLimit;
					rank = json.rankOfLimit;
					
					if(limit > 4000){
						$('#rank-cmt').html("，官人您是权贵啊！");
						$('#option-1').html('巨款啊！现在就去申请借款');
						$('#option-2').html('去跟小伙伴嘚瑟一下');
						$('#option-3').html('换张信用卡再试试');
						$('#amt-x, #loan-limit').html(limit);
					}

					$('#loan-limit').html(limit);
					$('#amt-shown').html(limit);
					$('#rank-shown').html(Math.round(rank * 100) + "&#37");
					
					
				},
				error: function () {
					console.log('error!');
				}
			});
			
		});
	});
	
	$('#share').click(function(){
		$(this).hide();
	});
// loan	
	$('#amount').keyup(function(){
		var tmp = $(this).val();
		if(tmp.length > 0)
			$('#amount-txt').hide();
		else
			$('#amount-txt').show();
			
		if(tmp % 100 && tmp.length > 3)
			$(this).val(tmp -= (tmp % 100));
			
		if(tmp > crl)
			$(this).val(crl);
	});
	
	var Term;
	$('#term-3').click(function(){
		$('#term-3').css('background-color', '#3ca0e6');
		$('#term-6').css('background-color', '#c0c0c0');
		$.ajax({
			url: api_path + 'app/saveCost',
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				term: '3',
				amt: $('#amount').val(),
				memberId: member_id
			}),
			dataType: "json",
			success: function (json) {
				$('#each-term').html(json.payBackEachTerm);
				$('#saved').html(json.savedCost);
			},
			error: function () {
				alert("error!");
			}
		});
		Term = 3;
	});
	
	$('#term-6').click(function(){
		$('#term-3').css('background-color', '#c0c0c0');
		$('#term-6').css('background-color', '#3ca0e6');
		$.ajax({
			url: api_path + 'app/saveCost',
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				term: '6',
				amt: $('#amount').val(),
				memberId: member_id
			}),
			dataType: "json",
			success: function (json) {
				$('#each-term').html(json.payBackEachTerm);
				$('#saved').html(json.savedCost);
			},
			error: function () {
				alert("error!");
			}
		});
		Term = 6;
	});
	
	$('#got-it, #got-it-x, #got-it-y, #got-it-z').click(function(){
		WeixinJSBridge.call('closeWindow');
	});
	
    //loan
	var creditCards=[], card_to;
    $('#go-choose-card').click(function(){
		if(creditCards.length < 2){
			$.get(api_path + "members/" + member_id +"/creditCard", function(data){
				var tmp = [];
				$.each(data, function(idx, obj){
					tmp += '<div class="card-container">' + obj.cardNo +'</div><hr>';
					creditCards.push([obj.cardNo, obj.bank]);
				});
				$('#add-another').before($(tmp));
				$('.card-container').click(function(){
					card_to = $(this).html();
					$('#num-tail').html(card_to.slice(card_to.length - 4, card_to.lenth));
					$('#cardlist').popup('close');
					$('#card-confirm').show();
				});
			});
		}
	});
	
	var phone_num, validate_code;
	
	$('#phone').keyup(function(){
		var tmp = $(this).val();
		if(tmp.length > 0)
			$('#phone-txt').hide();
		else
			$('#phone-txt').show();
		
		phone_num = tmp;
	});
	
	$('#acquire-code').click(function(){
		phone_num = $('#phone').val();
		if(phone_num.length != 11)
			alert('请输入正确的手机号码!');
		else{
			$.get(api_path + "dict/mobileArea" + phone_num, function(text){
				// if(text="北京")
				// alert(text);
			});
			// var city = ;
			$.get(api_path + "sms/" + phone_num, function(code){
				alert("您的验证码已发送!");
				validate_code = code;
			});
		}
	});
	
	$('#code').keyup(function(){
		var vcode = $(this).val();
		if(vcode.length > 0)
			$('#code-txt').hide();
		else
			$('#code-txt').show();
			
		if(vcode.length == 6 && phone_num.length == 11){
			$.ajax({
				url: api_path + 'sms/' + phone_num + '/' + vcode,
				type: "POST",
				data: JSON.stringify({
					mobilePhone: phone_num,
					code: vcode
				}),
				dataType: "text",
				success: function (text) {
					if("true" == text)
						alert("您已输入有效的验证码!");
					else
						alert("验证码错误!");
				},
				error: function () {
					alert("error!");
				}
			});
		}
	});
	
	//
	$('#request').click(function(){
		alert('working!');
		$.ajax({
			url: api_path + "app",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				term: Term,
				amt: $('#amount').val(),
				memberId: member_id
			}),
			dataType: "text",
			success: function (text) {
				// alert("借款申请成功! 流水号" + text + "!");
			},
			error: function () {
				alert("error!");
			}
		});
	});
	
	$.get(api_path + "members/" + member_id + "/crl", function(json){
			var crl_text = json.creditLimit; 
			var rank_text = json.rankOfLimit;
			if(crl_text > 4000){
				$('#rank-cmt').html("，官人您是权贵啊！");
				$('#option-1').html('巨款啊！现在就去申请借款');
				$('#option-2').html('去跟小伙伴嘚瑟一下');
				$('#option-3').html('换张信用卡再试试');
				$('#amt-x, #loan-limit').html(crl_text);
			}
			crl = crl_text;
			$('#amt-shown, #loan-limit').html(crl);
			$('#rank-shown').html(Math.round(rank_text * 100) + "&#37");
		},
	"json");
	
	// status > 2 means there exists the user' crl
	if(status > 1){
		$('#option-1').attr('href', '#loan?' + Math.random());
		$('#option-3').attr('href', '#limit?' + Math.random());
		
		$.post(api_path + "app/members/" + member_id, member_id, function(json){
			$('#amt-x').html(json.amt);
			$('#term-' + json.term + '-shown').css('background-color', '#3ca0e6');
			$('#each-x').html(json.repayPerTerm);
			$('#saved-x').html(json.saveCost);
		},
		"json");
		
		$.get(api_path + "members/" + member_id, function(json){
			if(json.idCardNo)
				$('#front-num').html(json.idCardNo);
			if(json.validThru)
				$('#back-num').html("有效期至" + json.validThru);
			if(json.industry){
				$("#industry-select").val(json.industry);
				$('#industry-txt').hide();
			}
			if(json.education){
				$("#education-select").val(json.education);
				$('#education-txt').hide();
			}
		}, "json");
		
		$.get(api_path + "app/members/" + member_id + "/progress", function(text){
			$('#hours').html(Math.round(48 * (100 - text)/100));
		});
	}
	
	if(status == '5.2'){
		$.mobile.navigate("#fail");
	}
	
	if(member_id == '59'){
		// $.mobile.navigate('#congratulation');
		// alert('59');
	}
	
	
	
	$('a').on({
		vmousedown: function(){
			$(this).css('box-shadow', '0 0 5px black');
		}, vmouseup: function(){
			$(this).css('box-shadow', 'none');
		}		
	});
	
	$('#Y').click(function(){
		$.post(api_path + "app/members/" + member_id + "/creditCard/" + $('#card-list option:selected').val(), function(success){
			alert("您的借款将在2个工作日内到帐！");
		});
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
		$.post(api_path + "members/" + member_id + "/creditCards/" + $('#new-cardnum').val(), function(num_txt){
			$('#add-another').before($('<div class="card-container">' + $('#new-cardnum').val() +'</div><hr>'));
		});
		alert("您的信用卡添加成功！");
	});

	
	
	
	
	
	
	
	
	
	
	
	
});
