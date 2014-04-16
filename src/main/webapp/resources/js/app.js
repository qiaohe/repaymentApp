$(function () {

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
alert(browser);


	var api_path = "http://192.168.0.185:8080/repayment/api/";
    var bincode, industry, education;

    var window_height = $(window).height();
    var window_width = $(window).width();

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
    $('#id-card-front-text').after($('<div><input type="file" accept="image/jpg, image/jpeg" id="id-card-front-upload" capture></div>').css('display', 'none'));

    $('#id-card-front-text').click(function () {
        $('#id-card-front-upload').trigger('click');
    });

    $('#id-card-front-upload').change(function (e) {
        var formData = new FormData();
        formData.append("idCardFrontFile", e.target.files[0]);
		$.mobile.loading('show');
        $.ajax({
            url: api_path + "members/1/idCardFront",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (json) {
				$('input[id=id-card-front-text]').val(json).css('background-color', 'green').button("refresh");
				$.mobile.loading('hide');
            },
            error: function () {
				$('input[id=id-card-front-text]').val('无法识别, 请重新拍摄!').css('background-color', 'red').button("refresh");
				$.mobile.loading('hide');
			}
        });
    });
    // upload back
    $('#id-card-back-text').after($('<div><input type="file" accept="image/jpg, image/jpeg" id="id-card-back-upload" capture></div>').css('display', 'none'));

    $('#id-card-back-text').click(function () {
        $('#id-card-back-upload').trigger('click');
    });

    $('#id-card-back-upload').change(function (e) {
        var formData = new FormData();
        formData.append("idCardBackFile", e.target.files[0]);
		$.mobile.loading('show');
        $.ajax({
            url: api_path + "members/1/idCardBack",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (json) {
				$('input[id=id-card-back-text]').val(json).css('background-color', 'green').button("refresh");
				$.mobile.loading('hide');
            },
            error: function () {
				$('input[id=id-card-back-text]').val('无法识别, 请重新拍摄!').css('background-color', 'red').button("refresh");
				$.mobile.loading('hide');
			}
        });
    });
    // credit card number
	$.getJSON(api_path + "dict/binCode", function (json) {
                bincode = json;
	});
	
	// $('#credit-card-number-text').removeClass('ccnt-1').trigger('create');
	$('#credit-card-number-text').css({'background-color': 'red', 'background': 'http://192.168.0.185:8080/repayment/resources/img/ccb.png'});
	// $('#limit').trigger('create');
	
    $('#credit-card-number-text').keyup(function () {
		num = $(this).val();
		if(num.length > 1){
			if(3 == num[0]){
				if(5 == num[1])
					$(this).css('background', 'url(../img/jcb.png) no-repeat scroll 3px 3px');
				else if(6 == num[1])
					$(this).css('background', 'url(../img/dalai.png) no-repeat scroll 3px 3px');
				else if(7 == num[1])
					$(this).css('background', 'url(../img/yuntong.png) no-repeat scroll 3px 3px');
			}	
			else if(4 == num[0])
				$(this).css('background', 'url(../img/visa.png) no-repeat scroll 3px 3px');
			else if(5 == num[0])
				$(this).css('background', 'url(../img/mastercard.png) no-repeat scroll 3px 3px');
			else if(6 == num[0] && 2 == num[1])
				$(this).css('background', 'url(../img/cup.png) no-repeat scroll 3px 3px !important');
			else
				// $(this).css('background', 'url(../img/ccb.png) no-repeat scroll 3px 3px');
				console.log(num + ' is invalid');
		}
    });

	$('#next-step').click(function(){
		if (!industry) {
			$.getJSON(api_path + "dict/industry", function (json) {
				for(var i in json) {
					$('#industry-select').append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
				}
				industry = 1;
            });
        }

		if (!education) {
			$.getJSON(api_path + "dict/education", function (json) {
				for(var i in json) {
					$('#education-select').append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
				}
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
        $('#billmail-same-shown').html($('#email-text').val());
    });
//billmail
    setWidth($('#billmail'), 0.8);
    setHeight($('#billmail'), 0.75);
    setHeight($('#billmail-cover'), 0.25);
    // without the billmail
    $('#skip').click(function () {
        $.ajax({
            url: api_path + "members/1",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
               creditCarNo: $('#credit-card-number-text').val(),
               industry: $('#industry-select').val(),
               education: $('#education-select').val(),
               email: $('#email-text').val()
            }),
            dataType: "json",
            success: function (json) {
                console.log(json);
				$.mobile.changePage($("#wait-for-limit"), "none");
				alert('您的额度是' + json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
    // with the billmail
    $('#confirm').click(function () {
        $.ajax({
            url: api_path + "members/1",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
				creditCarNo: $('#credit-card-number-text').val(),
				industry: $('#industry-select').val(),
				education: $('#education-select').val(),
				email: $('#email-text').val(),
				billEmail: $('#email-text').val(),
				billPassword: $('#mail-password').val()
            }),
            dataType: "json",
            success: function (json) {
                console.log(json);
				$.mobile.changePage($("#wait-for-limit"), "none");
				alert('您的额度是' + json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });

    //loan
	setHeight($('#loan-cover'), 0.35);
	
	var creditCards=[];
    $('#loan-request').click(function(){
		if(creditCards.length < 2){
			$.get(api_path + "members/1/creditCard", function(data){
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
	
	
	
	
});