 $(function(){
// START
var info = {};
info.path = "api/";
var id_pattern = /(?:id=)\d+/;
info.id = id_pattern.exec(window.location).toString();
info.id = info.id.slice(3, info.id.length);

var cert_pattern = /(?:certNo=)\d+/;
info.cert = cert_pattern.exec(window.location).toString();
info.cert = info.cert.slice(7, info.cert.length);

jQuery.ajaxSetup({async:false});
$.get(info.path + 'pboc/' + info.id, function(json){
	$('#name').val(json.name);
	$('#id').val(json.certNo);
	$('#modified-name').val(json.newName);
    $('#report-time').val(json.reportCreateTime);
	$('#modified-id').val(json.newCertNo);
	$('#modified-report-time').val(json.newReportCreateTime);
	$('#marriage').val(json.maritalState);
	$('#mobile').val(json.mobile);
	$('#operator').val(json.keyiner);
	$('#num-district').val(json.mobileCity);
	$('#phone-at-work').val(json.officeTelephoneNo);
	$('#phone-home').val(json.homeTelephoneNo);
	$('#education').val(json.eduDegree);
	$('#show-id').val(json.idImage);

	if(!json.registeredAddress)
		$('#address-conflict').prop('checked', true);
	else
		$('#address-conflict').prop('checked', false);
		
	$('#spouse-name').val(json.partnerName);
	$('#spouse-id').val(json.partnerCertNo);
	$('#spouse-phone').val(json.partnerTelephoneNo);
	$('#livein').val(json.homeCity);
	$('#address').val(json.homeAddress);
	$('#living-state').val(json.homeResidenceType);
	$('#update-date').val(json.addressGetTime);
	$('#work-at').val(json.employer);
	$('#work-in').val(json.employerCity);
	$('#work-address').val(json.employerAddress);
	$('#profession').val(json.occupation);
	$('#industry').val(json.industry);
	$('#industry-catography').val(json.industryAddress);
	$('#position').val(json.duty);
	$('#level').val(json.title);
	$('#since').val(json.startYear);
	$('#working-age').val(json.industryYear);
	$('#update-date-1').val(json.employerGetTime);
	$('#works-since').val(json.hisGetting);
	$('#pension-date').val(json.yRegisterDate);
	$('#month-in-work').val(json.yWorkDate);
	$('#base').val(json.yOwnBasicMoney);
	$('#pension-state').val(json.yState);
	$('#amount-each-2').val(json.yMoney);

	if(json.yOrganName)
		$('#corp-conflict-2').prop('checked', true);
	else
		$('#corp-conflict-2').prop('checked', false);
		
	$('#reason-suspension').val(json.pauseReason);
	$('#update-date-2').val(json.yGetTime);
	$('#fund-date').val(json.registerDate);
	$('#fund-since').val(json.firstMonth);
	$('#fund-to').val(json.toMonth);
	$('#fund-state').val(json.yState);
	$('#amount-each-3').val(json.pay);
    $('#delayed-current').val(json.cardOverDueNum);
	
	if(json.organName)
		$('#corp-conflict-3').prop('checked', true);
	else
		$('#corp-conflict-3').prop('checked', false);
		
	$('#update-date-3').val(json.getTime);
	$('#earlist-credit').val(json.firstLoanMonth);
	$('#num-delayed').val(json.loanCount);
	$('#amount-delayed').val(json.loanHighestOverdueAmountPerMon);
	$('#time-delayed').val(json.loanMaxDuration);
    $('#time-delayed-2').val(json.cardOverDuePerYear);
	$('#account-num').val(json.cardCount);
	$('#amount-delayed-2').val(json.cardHighestOverdueAmountPerMon);
	$('#time-delayed-2').val(json.cardMaxDuration);
	$('#time-delayed-2').val(json.cardOverDuePerYear);
	$('#account-num-3').val(json.semiCardCount);
	$('#overdraft-amount').val(json.semiCardHighestOverdueAmountPerMon);
	$('#overdraft-time').val(json.semiCardMaxDuration);
	$('#num-loan').val(json.loanAccountCount);
	$('#amount-loan').val(json.loanCreditLimit);
	$('#amount-remaining').val(json.loanBalance);
	$('#payback-average').val(json.loanLatest6MonthUsedAvgAmount);
	$('#num-corp').val(json.cardOrg);
    $('#credit-min').val(json.semicardMinCreditLimitPerOrg);
	$('#num-accounts').val(json.cardAccountCount);
	$('#amount-credit').val(json.cardCreditLimit);
	$('#credit-average').val(json.cardAvgCreditLimit);
	$('#credit-max').val(json.cardMaxCreditLimitPerOrg);
	$('#credit-used').val(json.cardUsedCreditLimit);
	$('#used-average').val(json.cardLatest6MonthUsedAvgAmount);
	$('#num-corp-3').val(json.semiCardOrg);
    $('#search-time').val(json.cardQueryLatest6Month);
	$('#num-accounts-3').val(json.semiCardAccountCount);
	$('#amount-credit-3').val(json.semicardCreditLimit);
	$('#credit-average-3').val(json.semiCardAvgCreditlimit);
	$('#credit-max-3').val(json.semiCardMaxCreditLimitPerOrg);
	$('#credit-min-3').val(json.semicardMinCreditLimitPerOrg);
	$('#overdraft-average').val(json.semiCardLatest6MonthUsedAvgAmount);

	for(var i = 0; i<15; i++){
		if(json['rh_1' + i])
			$('#g-' + i).prop('checked', true);
		else
			$('#g-' + i).prop('checked', false);
	}
	// CARD_QUERYLATEST6MONTH	近6个月信用卡审核查询次数json.cardQueryLatest6Month
	info.keyiner = json.keyiner;
	info.createTime = json.createTime;
	info.rh_crl = json.rh_crl;
	info.flag = json.flag;
	info.risk = json.risk;
	info.status = json.status;
	
	if(info.status == '4' || info.status == '5'){
		$('input').attr('disabled', 'disabled');
	}
	
}, 'json');

$.get(info.path + 'pboc/' + info.id + '/idCard', function(text){
	info.imgpath = info.path.slice(0, info.path.length - 4) + 'resources/idcard/' + text;
	$('#img').attr('src', info.imgpath);
}, 'text');

jQuery.ajaxSetup({async:true});

//photo and pdf
$('#pdf').attr('src', info.path.slice(0, info.path.length - 4) + 'resources/pboc/' + info.cert + '.pdf');
$('#show-idimg').click(function(){
	$('#img').show();
});
$('#img').click(function(){
	$(this).hide();
});

function update(){
	$.ajax({
		url: info.path + 'pboc/' + info.id,
		type: "POST",
        async: false,
		contentType: "application/json",
		data: JSON.stringify({
            id: info.id,
			name: $('#name').val(),
            newName: $('#modified-name').val(),
            reportCreateTime: $('#report-time').val(),
			certNo: $('#id').val(),
			newCertNo: $('#modified-id').val(),
			newReportCreateTime: $('#modified-report-time').val(),
			maritalState: $('#marriage').val(),
			mobile: $('#mobile').val(),
			mobileCity: $('#num-district').val(),
			officeTelephoneNo: $('#phone-at-work').val(),
            homeTelephoneNo: $('#phone-home').val(),
			eduDegree: $('#education').val(),
			idImage: $('#show-id').val(),
			registeredAddress: info.registeredAddress,
			partnerName: $('#spouse-name').val(),
			partnerCertNo: $('#spouse-id').val(),
			partnerTelephoneNo: $('#spouse-phone').val(),
			homeCity: $('#livein').val(),
			homeAddress: $('#address').val(),
			homeResidenceType: $('#living-state').val(),
			addressGetTime: $('#update-date').val(),
			employer: $('#work-at').val(),
			employerCity: $('#work-in').val(),
			employerAddress: $('#work-address').val(),
			occupation: $('#profession').val(),
			industry: $('#industry').val(),
			industryAddress: $('#industry-catography').val(),
			duty: $('#position').val(),
			title: $('#level').val(),
			startYear: $('#since').val(),
			industryYear: $('#working-age').val(),
			employerGetTime: $('#update-date-1').val(),
			hisGetting: $('#works-since').val(),
			yRegisterDate: $('#pension-date').val(),
			yWorkDate: $('#month-in-work').val(),
			yOwnBasicMoney: $('#base').val(),
			yState: $('#fund-state').val(),
			yMoney: $('#amount-each-2').val(),
			yOrganName: info.yOrganName,
            registeredAddress: info.registeredAddress,
			pauseReason: $('#reason-suspension').val(),
			yGetTime: $('#update-date-2').val(),
			registerDate: $('#fund-date').val(),
			firstMonth: $('#fund-since').val(),
			toMonth: $('#fund-to').val(),
			state: $('#fund-state').val(),
			pay: $('#amount-each-3').val(),
			organName: info.organName,
			getTime: $('#update-date-3').val(),
			firstLoanMonth: $('#earlist-credit').val(),
			loanCount: $('#num-delayed').val(),
			loanHighestOverdueAmountPerMon: $('#amount-delayed').val(),
			loanMaxDuration: $('#time-delayed').val(),
			cardCount: $('#account-num').val(),
            cardOverDueNum: $('#delayed-current').val(),
            cardOverDuePerYear: $('#time-delayed-2').val(),
			cardHighestOverdueAmountPerMon: $('#amount-delayed-2').val(),
			cardMaxDuration: $('#time-delayed-2').val(),
			cardOverDuePerYear: $('#time-delayed-2').val(),
			semiCardCount: $('#account-num-3').val(),
			semiCardHighestOverdueAmountPerMon: $('#overdraft-amount').val(),
			semiCardMaxDuration: $('#overdraft-time').val(),
			loanAccountCount: $('#num-loan').val(),
			loanCreditLimit: $('#amount-loan').val(),
			loanBalance: $('#amount-remaining').val(),
			loanLatest6MonthUsedAvgAmount: $('#payback-average').val(),
			cardOrg: $('#num-corp').val(),
            cardQueryLatest6Month: $('#search-time').val(),
			cardAccountCount: $('#num-accounts').val(),
			cardCreditLimit: $('#amount-credit').val(),
			cardAvgCreditLimit: $('#credit-average').val(),
			cardMaxCreditLimitPerOrg: $('#credit-max').val(),
			cardUsedCreditLimit: $('#credit-used').val(),
			cardLatest6MonthUsedAvgAmount: $('#used-average').val(),
			semiCardOrg: $('#num-corp-3').val(),
			semiCardAccountCount: $('#num-accounts-3').val(),
			semicardCreditLimit: $('#amount-credit-3').val(),
			semiCardAvgCreditlimit: $('#credit-average-3').val(),
			semiCardMaxCreditLimitPerOrg: $('#credit-max-3').val(),
			semicardMinCreditLimitPerOrg: $('#credit-min-3').val(),
			semiCardLatest6MonthUsedAvgAmount: $('#overdraft-average').val(),
			rh_1: info.rh_1,
			rh_2: info.rh_2,
			rh_3: info.rh_3,
			rh_4: info.rh_4,
			rh_5: info.rh_5,
			rh_6: info.rh_6,
			rh_7: info.rh_7,
			rh_8: info.rh_8,
			rh_9: info.rh_9,
			rh_10: info.rh_10,
			rh_11: info.rh_11,
			rh_12: info.rh_12,
			rh_13: info.rh_13,
			rh_14: info.rh_14,
			keyiner: info.keyiner,
			createTime: info.createTime,
			rh_crl: info.rh_crl,
			flag: info.flag,
			risk: info.risk,
			status: info.status
		}),
		dataType: "text",
		success: function(text){
			console.log(text);
		},
		error: function(){
			alert("error");
		}
	});
}

function checkboxes(){
	if($('#address-conflict').prop('checked') == 'true')
		info.registeredAddress = 1;
	else
		info.registeredAddress = 0;

	if($('#corp-conflict-2').prop('checked') == 'true')
		info.yOrganName = 1;
	else
		info.yOrganName = 0;

	if($('#corp-conflict-3').prop('checked') == 'true')
		info.organName = 1;
	else
		info.organName = 0;

	for(var i = 0; i<15; i++){
		if($('#g-' + i).prop('checked') == 'true')
			info['rh_1' + i] = 1;
		else
			info['rh_1' + i] = 0;
	}
}


$('#save').click(function(){
	info.status = 3;
	checkboxes();
	update();
	window.close();
});

$('#finish').click(function(){
	info.status = 4;
	checkboxes();
	update();
    window.close();
});

$('#no-record').click(function(){
	info.status = 4;
	info.risk = 1;
	checkboxes();
	update();
    window.close();
});

$('#id-modify').click(function(){
	info.status = 5;
	checkboxes();
	update();
    window.close();
});


// END
});