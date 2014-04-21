$(function () {
	// show the browser version
	var browser= (function(){
		var ua= navigator.userAgent, tem, 
		M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*([\d\.]+)/i) || [];
		if(/trident/i.test(M[1])){
			tem=  /\brv[ :]+(\d+(\.\d+)?)/g.exec(ua) || [];
			return 'IE '+(tem[1] || '');
		}
		M= M[2]? [M[1], M[2]]:[navigator.appName, navigator.appVersion, '-?'];
		if((tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
		return M.join(' ');
	})();
	// alert(browser);

	var api_path = "http://180.168.35.37:80/repaymentApp/api/";
	// var member_path = api_path + "members/1/";
	// var id_pattern = /(?:memberId=)\d+/;
	// var member_id = id_pattern.exec(window.location).toString();
	// member_id = member_id.slice(9, member_id.length);
	var member_path = api_path + "members/" + "11" +"/";
	alert(member_path);
	
    var bincode, industry, education;

    var window_height = window.innerHeight;
    var window_width = window.innerWidth;

    function setHeight($obj, para) {
        if (para <= 1)
            $obj.css('height', para * window_height);
        else
            $obj.css('height', para);
    }

    function setWidth($obj, para) {
        if (para <= 1)
            $obj.css('width', para * window_width);
        else
            $obj.css('width', para);
    }

//#limit
    setHeight($('#front-cover'), 0.39);
    $('.tip-image').css({'max-height': 0.8 * $('#id-card-front-text').height(), 'min-height': 0.8 * $('#id-card-front-text').height()});
    // upload front

    

    $('#id-card-front-upload').change(function (e) {
        var formData = new FormData();
        formData.append("idCardFrontFile", e.target.files[0]);
		$.mobile.loading('show');
        $.ajax({
            url: member_path + "idCardFront",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (json) {
				$('#id-card-front').html(json).css('color', '#aacc00');
				$.mobile.loading('hide');
            },
            error: function () {
				$('#id-card-front').html('无法识别, 请重新拍摄!').css('color', '#cc0000');
				$.mobile.loading('hide');
			}
        });
    });
    // upload back
    $('#id-card-back-upload').change(function (e) {
        var formData = new FormData();
        formData.append("idCardBackFile", e.target.files[0]);
		$.mobile.loading('show');
        $.ajax({
            url: member_path + "idCardBack",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "text",
            success: function (text) {
				$('#id-card-back').html(text).css('color', '#aacc00');
				$.mobile.loading('hide');
            },
            error: function () {
				$('#id-card-back').html('无法识别, 请重新拍摄!').css('color', '#cc0000');
				$.mobile.loading('hide');
			}
        });
    });
    // credit card number
	$.getJSON(api_path + "dict/binCode", function (json) {
                bincode = json;
	});
	
    $('#credit-card-number').keyup(function () {
		num = $(this).val();
		if(num.length > 1){
			if(3 == num[0]){
				if(5 == num[1])
					$('#card-img').attr('src', 'resources/img/jcb.png');
				else if(6 == num[1])
					$('#card-img').attr('src', 'resources/img/dalai.png');
				else if(7 == num[1])
					$('#card-img').attr('src', 'resources/img/yuntong.png');
			}	
			else if(4 == num[0])
				$('#card-img').attr('src', 'resources/img/visa.png');
			else if(5 == num[0])
				$('#card-img').attr('src', 'resources/img/mastercard.png');
			else if(6 == num[0] && 2 == num[1])
				$('#card-img').attr('src', 'resources/img/cup.png');
		}
		else
			$('#card-img').attr('src', 'resources/img/blank.png');
    });

	$('#next-step').click(function(){
		if (!industry) {
			$.getJSON(api_path + "dict/industry", function (json) {
			
				if(!json[0]){
					json = [{"key":"1","value":"政府机关、社会团体"},{"key":"2","value":"军事、公检法"},{"key":"3","value":"学校、医院"},{"key":"4","value":"专业事务所"},{"key":"5","value":"信息通信、IT互联网"},{"key":"6","value":"金融业"},{"key":"7","value":"交通运输"},{"key":"8","value":"公共事业"},{"key":"9","value":"能源矿产"},{"key":"10","value":"商业零售、内外贸易"},{"key":"11","value":"房地产、建筑业"},{"key":"12","value":"加工、制造业"},{"key":"13","value":"餐饮、酒店、旅游"},{"key":"14","value":"服务、咨询"},{"key":"15","value":"媒体、体育、娱乐"},{"key":"16","value":"农林牧渔"},{"key":"17","value":"网店店主"},{"key":"18","value":"学生"},{"key":"19","value":"自由职业者"},{"key":"20","value":"其他"}];
				}
				
				var $industry_select = $('#industry-select');
				for(var i in json) {
					$industry_select.append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
				}
				$industry_select.selectmenu('refresh');
				industry = 1;
            });
        }

		if (!education) {
			$.getJSON(api_path + "dict/education", function (json) {
				
				if(!json[0]){
					json = [{"key":"1","value":"初中及以下"},{"key":"2","value":"高中、中专"},{"key":"3","value":"大专"},{"key":"4","value":"本科"},{"key":"5","value":"硕士"},{"key":"6","value":"博士"}];
				}
				
				var $education_select = $('#education-select');
				for(var i in json) {
					$education_select.append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
				}
				$education_select.selectmenu('refresh');
				education = 1;
			});
		}
	});
//#limit-step2
    setHeight($('#front-cover2'), 0.35);
    setHeight($('#email-text'), $('#id-card-front-text').height());
    //step2-submit
    $('#email-text').keyup(function () {
        if ($('#industry-select').val() && $('#education-select').val() && $(this).val())
            $('#step2-submit').attr('href', '#billmail').css('background-color', 'rgb(60, 160, 230)');
    });

    $('#step2-submit').click(function () {
        $('#same-mail-shown').html($('#email-text').val());
    });
//billmail
    setWidth($('#billmail'), 0.8);
    setHeight($('#billmail'), 0.75);
    setHeight($('#billmail-cover'), 0.25);
    // without the billmail
    $('#skip').click(function () {
		// $.mobile.navigate('#waiting-for-limit');
        $.ajax({
            url: member_path,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
               creditCarNo: $('#credit-card-number').val(),
               industry: $('#industry-select').val(),
               education: $('#education-select').val(),
               email: $('#email-text').val()
            }),
            dataType: "json",
            success: function (json) {
                // json = $.parseJSON(json);
				$('#result-amount').html("&yen " + json.creditLimit);
				$('#result-discription').html("您的额度打败了" + 100 * json.rankOfLimit + "&#37的用户! xxxxxxx");
				$('#option-1').html("现在就去复活信用卡");
				$('#option-2').html("share it");
				$('#option-3').html("换张信用卡再试试");
				alert(json.creditLimit + ' ' + json.rankOfLimit);
            },
            error: function () {
                alert('error!');
            },
			complete: function(){
				$.mobile.navigate('#limit-result');
			}
        });
    });
    // with the billmail
    $('#confirm').click(function () {
		$.mobile.navigate('#waiting-for-limit');
		var ml;
		if($('#bill-mail-typein').val())
			ml = $('#bill-mail-typein').val();
		else
			ml = $('#email-text').val();
        $.ajax({
            url: member_path,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
				creditCarNo: $('#credit-card-number-text').val(),
				industry: $('#industry-select').val(),
				education: $('#education-select').val(),
				email: $('#email-text').val(),
				billEmail: ml,
				billPassword: $('#mail-password').val()
            }),
            dataType: "json",
            success: function (json) {
				$('#result-amount').html("&yen " + json.creditLimit);
				$('#result-discription').html("您的额度打败了" + 100 * json.rankOfLimit + "&#37的用户! xxxxxxx");
				$('#option-1').html("现在就去复活信用卡");
				$('#option-2').html("share it");
				$('#option-3').html("换张信用卡再试试");
				$.mobile.navigate($("#limit-result"), "none");
            },
            error: function () {
                console.log('error!');
            }
        });
    });

    //loan
	setHeight($('#loan-cover'), 0.35);
	
	var creditCards=[];
    $('#loan-request').click(function(){
		if(creditCards.length < 2){
			$.get(member_path + "creditCard", function(data){
				var $chosen_number = $('#chosen-number');
				
				$.each(data, function(idx, obj){
					$chosen_number.append('<option>' + obj.cardNo + '</option>');
					creditCards.push([obj.cardNo, obj.bank]);
				});
				
				$chosen_number.attr('size', creditCards.length);
				$chosen_number.selectmenu('refresh');
				
			});
		}
	});
	
	$('#chosen-number').change(function(){
		var num = $('#chosen-number').val();
		if(confirm('你确定要复活尾号' + num.slice(num.length - 4, num.length) + '的卡片?')){
			$.mobile.navigate('#success-tip');
		}
	});
	
	
	var validate_code;
	$('#acquire-vcode').click(function(){
		var phone_num = $('#phone-number').val();
		if(!phone_num || phone_num.length != 11)
			alert('wrong number!');
		else{
			$.get(api_path + phone_num, function(vcode){
				alert("your validate code is " + vcode + "!");
				validate_code = vcode;
			});
		}
	});
	
	$('#vcode-typed-in').keyup(function(){
		var vcode = $(this).val();
		if(vcode.length == 6){
			$.ajax({
				url: api_path + /mobilePhone/code,
				type: "POST",
				data: formData,
				processData: false,
				contentType: false,
				dataType: "json",
				success: function (json) {
					$('#id-card-front').html(json).css('color', '#aacc00');
					$.mobile.loading('hide');
				},
				error: function () {
					$('#id-card-front').html('无法识别, 请重新拍摄!').css('color', '#cc0000');
					$.mobile.loading('hide');
				}
			});
		}
	});
	
	
});