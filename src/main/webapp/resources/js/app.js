$(function(){
// image upload control
$('#id-card-front').after($('<div><input type="file"  name="id-card-front" accept="image/*" id="id-card-front-upload" capture></div>').css('display', 'none'));
$('#id-card-front').click(function(){
	$('#id-card-front-upload').trigger('click');
});
$('#id-card-front-upload').change(function(e){
	var reader = new FileReader();
	reader.readAsDataURL(e.target.files[0]);
	reader.onload = function(e){
		$.ajax({
			url: "192.168.0.186:8080/repaymentApp/api/dict/binCode",
			data: {
				id-card-front: 
			},
		});
	};
});
//
});