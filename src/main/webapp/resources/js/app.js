$(function(){
// image upload
	// id-card front
    $('#id-card-front').after($('<div><input type="file"  name="id-card-front" accept="image/*" id="id-card-front-upload" capture></div>').css('display', 'none'));
    $('#id-card-front').click(function(){
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
            dataType: "json",
			contentType: false,
            success: function(json){
                alert('oh yeah!');
                console.log(json);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
	// id-card back
	$('#id-card-back').after($('<div><input type="file"  name="id-card-back" accept="image/*" id="id-card-back-upload" capture></div>').css('display', 'none'));
    $('#id-card-back').click(function(){
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
            dataType: "json",
			contentType: false,
            success: function(json){
                alert('oh yeah!');
                console.log(json);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
// credit card number
	// get categories
	$('#credit-card-number').click(function(){
		$.ajax({
			url: "http://192.168.0.191:8080/repayment/api/dict/binCode",
            type: "GET",
            dataType: "json",
            success: function(json){
                alert('oh yeah!');
                console.log(json);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
		});
	});
// limit step 2
	//industry
	$('#industry').click(function(){
		$.get("http://localhost:8080/repayment/api/dict/industry", function(json){
			var $industry = $('<div data-role="main" class="ui-content"><select name="industry" id="industry-select"></select>xxx</div>');
			$industry.popup();
			$industry.popup('open');
			console.log(json);
			// for(var i in json){
				// $('#industry-select').append('<option value=' + json + '></option>');
			// }
			
			// json.key
			// json.value
		});
	});
});