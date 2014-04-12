$(function(){
	window_height = $(window).height();
	window_width = $(window).width();
	
	function setHeight($obj, para){
		if(para <= 1)
			$obj.css('height', para * window_height);
		else
			$obj.css('height', para);
	}
	
	function setWidth($obj, para){
		if(para <= 1)
			$obj.css('width', para * window_width);
		else
			$obj.css('width', para);
	}
//#limit
	setHeight($('#front-cover'), 0.35);
	setHeight($('#credit-card-number-text'), $('#id-card-front-text').height());
	// upload front
	$('#id-card-front-text').after($('<div><input type="file" accept="image/jpg, image/jpeg" id="id-card-front-upload" capture></div>').css('display', 'none'));
	
	$('#id-card-front-text').click(function(){
		$('#id-card-front-upload').trigger('click');
	});
    
	$('#id-card-front-upload').change(function(e){
        var formData = new FormData();
        formData.append("idCardFrontFile", e.target.files[0]);
        $.ajax({
            url: "http://192.168.0.191:8080/repayment/api/members/1/idCardFront",
            type: "POST",
            data: formData,
            processData: false,
			contentType: false,
            dataType: "json",
            success: function(json){
                $('input[id=id-card-front-text]').val(json.idCardFront);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
	// upload back
	$('#id-card-back-text').after($('<div><input type="file" accept="image/jpg, image/jpeg" id="id-card-back-upload" capture></div>').css('display', 'none'));
	
	$('#id-card-back-text').click(function(){
		$('#id-card-back-upload').trigger('click');
	});
    
	$('#id-card-back-upload').change(function(e){
        var formData = new FormData();
        formData.append("idCardBackFile", e.target.files[0]);
        $.ajax({
            url: "http://192.168.0.191:8080/repayment/api/members/1/idCardBack",
            type: "POST",
            data: formData,
            processData: false,
			contentType: false,
            dataType: "json",
            success: function(json){
                $('input[id=id-card-back-text]').val(json.idCardBack);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
	// credit card number
	$.get("http://192.168.0.191:8080/repayment/api/dict/binCode", function(json){
		// for(var i in json){
			// console.log(json[i]);
		// }
		alert(json);
	});

	$.get("http://192.168.0.191:8080/repayment/api/dict/education", function(json){
		
	});
	
	$('#next-step').click(function(){
		$.get("http://192.168.0.191:8080/repayment/api/dict/industry", function(json){
			for(var i in json){
				$('#industry-select').append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
			}
		});
		
		$.get("http://192.168.0.191:8080/repayment/api/dict/education", function(json){
			for(var i in json){
				$('#education-select').append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
			}
		});
	});
	
//#limit-step2
	setHeight($('#front-cover2'), 0.35);
	setHeight($('#email-text'), $('#id-card-front-text').height());
	//step2-submit
	$('#step2-submit').click(function(){
		$('#billmail-same-shown').html($('#email-text').val());
	});
//billmail
	setWidth($('#billmail'), 0.8);
	setHeight($('#billmail'), 0.75);
	setHeight($('#billmail-cover'), 0.25);
	// without the billmail
	$('#skip').click(function(){
		$.ajax({
			url: "http://192.168.0.191:8080/repayment/api/dict/...",
            type: "POST",
			data: {
				creditCarNo: $('#credit-card-number-text').val(),
				industry: $('#key_industry').val(),
				education: $('#key_education').val(),
				email: $('#email-text').val()
			},
            dataType: "json",
            success: function(json){
                console.log(json);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
		});
	});
	// with the billmail
	$('#confirm').click(function(){
		$.ajax({
			url: "http://192.168.0.191:8080/repayment/api/dict/...",
            type: "POST",
			data: {
				creditCardNo: $('#credit-card-number-text').val(),
				industry: $('#key_industry').val(),
				education: $('#key_education').val(),
				email: $('#email-text').val(),
				// billmail: ,
			},
            dataType: "json",
            success: function(json){
                console.log(json);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
		});
	});

	











	// // set the size of front-cover and bill-mail
	// $('#front-cover, #back-cover').css('height', 0.35 * $(window).height());
	// $('#bill-mail').css({'height': 0.8 * $(window).height(), 'width': 0.8 * $(window).width()});
	// $('#bill-mail-cover').css('height', '35%');
// // make the select tags unselectable
	// $('#id-card-front-text, #id-card-back-text').disableSelection();
// // the font-size of credit card number and e-mail
	// $('#credit-card-number-text, #email-text').css('font-size', '1.8em');
// // image upload
	// // id-card front
    // $('#id-card-front-text').after($('<div><input type="file"  name="id-card-front" accept="image/*" id="id-card-front-upload" capture></div>').css('display', 'none'));
    // $('#id-card-front-text').click(function(){
        // $('#id-card-front-upload').trigger('click');
    // });
    // $('#id-card-front-upload').change(function(e){
        // var formData = new FormData();
        // formData.append("idCardFrontFile", e.target.files[0]);
        // $.ajax({
            // url: "http://192.168.0.191:8080/repayment/api/members/1/idCardFront",
            // type: "POST",
            // data: formData,
            // processData: false,
            // dataType: "json",
			// contentType: false,
            // success: function(json){
                // $('#id-card-front-text').html('<option selected>' + json.idCardFront + '</option>');
                // console.log(json);
            // },
            // error: function(XMLHttpRequest, textStatus, errorThrown){
                // console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            // }
        // });
    // });
	// // id-card back
	// $('#id-card-back-text').after($('<div><input type="file"  name="id-card-back" accept="image/*" id="id-card-back-upload" capture></div>').css('display', 'none'));
    // $('#id-card-back-text').click(function(){
        // $('#id-card-back-upload').trigger('click');
    // });
    // $('#id-card-back-upload').change(function(e){
        // var formData = new FormData();
        // formData.append("idCardBackFile", e.target.files[0]);
        // $.ajax({
            // url: "http://192.168.0.191:8080/repayment/api/members/1/idCardBack",
            // type: "POST",
            // data: formData,
            // processData: false,
            // dataType: "json",
			// contentType: false,
            // success: function(json){
                // $('#id-card-back-text').html('<option selected>' + json.idCardBack + '</option>');
                // console.log(json);
            // },
            // error: function(XMLHttpRequest, textStatus, errorThrown){
                // console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            // }
        // });
    // });
// // credit card number
	// // get categories
	// $('#credit-card-number-text').click(function(){
		// $.ajax({
			// url: "http://192.168.0.191:8080/repayment/api/dict/binCode",
            // type: "GET",
            // dataType: "json",
            // success: function(json){
                // console.log(json);
            // },
            // error: function(XMLHttpRequest, textStatus, errorThrown){
                // console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            // }
		// });
	// });
// // limit step 2
	// //industry
		// // $.get("http://192.168.0.191:8080/repayment/api/dict/industry", function(json){
			// var json = [{"key":"1","value":"政府机关、社会团体"},{"key":"2","value":"军事、公检法"},{"key":"3","value":"学校、医院"},{"key":"4","value":"专业事务所"},{"key":"5","value":"信息通信、IT互联网"},{"key":"6","value":"金融业"},{"key":"7","value":"交通运输"},{"key":"8","value":"公共事业"},{"key":"9","value":"能源矿产"},{"key":"10","value":"商业零售、内外贸易"},{"key":"11","value":"房地产、建筑业"},{"key":"12","value":"加工、制造业"},{"key":"13","value":"餐饮、酒店、旅游"},{"key":"14","value":"服务、咨询"},{"key":"15","value":"媒体、体育、娱乐"},{"key":"16","value":"农林牧渔"},{"key":"17","value":"网店店主"},{"key":"18","value":"学生"},{"key":"19","value":"自由职业者"},{"key":"20","value":"其他"}];
			// for(var i in json){
				// $('#industry-select').append('<option value=' + json[i].key + '>' + json[i].value + '</option>');
			// }
			// // json.key
			// // json.value
		// // });
	// //education
		// // $.get("http://192.168.0.191:8080/repayment/api/dict/industry", function(json){
			// var json_1 = [{"key":"1","value":"初中及以下"},{"key":"2","value":"高中、中专"},{"key":"3","value":"大专"},{"key":"4","value":"本科"},{"key":"5","value":"硕士"},{"key":"6","value":"博士"}];
			// for(var i in json_1){
				// $('#education-select').append('<option value=' + json_1[i].key + '>' + json_1[i].value + '</option>');
			// }
		// // });
	// // bill-mail
	// $('#submit-step-2').click(function(){
		// $('#billmail-same-shown').html($('#email-text').val());
	// });
	// // result
	// // $('#result-btn-1').css({'top': 0.55 * $(window).height(), 'width': 0.55 * $(window).width()});
	// // $('#result-btn-2').css({'top': 0.68 * $(window).height(), 'width': 0.55 * $(window).width()});
	// // $('#result-btn-3').css({'top': 0.81 * $(window).height(), 'width': 0.55 * $(window).width()});
	// // loan
	// $('#loan-cover').css('height', 0.25 * $(window).height());
});