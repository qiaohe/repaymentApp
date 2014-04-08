$(function(){
// image upload control
$('#id-card-front').after($('<div><input type="file"  name="id-card-front" accept="image/*" id="id-card-front-upload" capture></div>').css('display', 'none'));
$('#id-card-front').click(function(){
	$('#id-card-front-upload').trigger('click');
});
$('#id-card-front-upload').change(function(e){
	var formData = new FormData(e.target.result);
	$.ajax({
		url: "http://192.168.0.186:8080/repaymentApp/api/members/1/idCardFront",
		type: "POST",
		data: formData,
		contentType: false,
		processData: false,
		success: function(json){
			alert('oh yeah!');
			console.log(json);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
		}
	});
});
//
});