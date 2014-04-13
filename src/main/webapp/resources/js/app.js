$(function () {
    window_height = $(window).height();
    window_width = $(window).width();

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

    var bincode, industry, education;

//#limit
    setHeight($('#front-cover'), 0.35);
    $('.tip-image').css({'max-height': $('#id-card-front-text').height(), 'min-height': $('#id-card-front-text').height()});
    // upload front
    $('#id-card-front-text').after($('<div><input type="file" accept="image/jpg, image/jpeg" id="id-card-front-upload" capture></div>').css('display', 'none'));

    $('#id-card-front-text').click(function () {
        $('#id-card-front-upload').trigger('click');
    });

    $('#id-card-front-upload').change(function (e) {
        var formData = new FormData();
        formData.append("idCardFrontFile", e.target.files[0]);
        $.ajax({
            url: "http://localhost:8080/repayment/api/members/1/idCardFront",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (json) {
                if (json.idCardFront.length > 1) {
                    $('input[id=id-card-front-text]').val(json.idCardFront);
                    $('input[id=id-card-front-text]').css('color', 'black');
                }
                else {
                    $('input[id=id-card-front-text]').val("无法识别, 请重新拍摄");
                    $('input[id=id-card-front-text]').css('color', 'red');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
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
        $.ajax({
            url: "http://localhost:8080/repayment/api/members/1/idCardBack",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (json) {
                if (json.idCardBack.length > 1) {
                    $('input[id=id-card-back-text]').val(json.idCardBack);
                    $('input[id=id-card-back-text]').css('color', 'black');
                }
                else {
                    $('input[id=id-card-back-text]').val("无法识别, 请重新拍摄");
                    $('input[id=id-card-back-text]').css('color', 'red');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
    // credit card number
    $('#credit-card-number-text').keyup(function () {
        if (!bincode) {
            $.getJSON("http://localhost:8080/repayment/api/dict/binCode", function (json) {
                bincode = json;
            });
        }

        if ($(this).val().length == 6) {
            // validation
            $.each(bincode, function (key, val) {
                if (val == $(this).val()) {
                    $('#next-step').attr('href', '#limit-step2').css('background-color', 'rgb(60, 160, 230)');
                    $('#next-step').click(function () {
                        if (!industry) {
                            $.getJSON("http://localhost:8080/repayment/api/dict/industry", function (json) {
                                $.each(json, function (key, val) {
                                    $('#industry-select').append('<option value=' + key + '>' + val + '</option>');
                                });
                                industry = 1;
                            });
                        }

                        if (!education) {
                            $.getJSON("http://localhost:8080/repayment/api/dict/education", function (json) {
                                $.each(json, function (key, val) {
                                    $('#education-select').append('<option value=' + key + '>' + val + '</option>');
                                });
                                education = 1;
                            });
                        }
                    });
                }
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
            url: "http://localhost:8080/repayment/api/members/1",
            type: "POST",
            contentType: "json",
            data: JSON.stringify({
                creditCarNo: $('#credit-card-number-text').val(),
                industry: $('#industry-select').val(),
                education: $('#education-select').val(),
                email: $('#email-text').val()
            }),
            dataType: "json",
            success: function (json) {
                console.log(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
    // with the billmail
    $('#confirm').click(function () {
        $.ajax({
            url: "http://localhost:8080/repayment/api/members/1",
            type: "POST",
            data: {
                creditCardNo: $('#credit-card-number-text').val(),
                industry: $('#key_industry').val(),
                education: $('#key_education').val(),
                email: $('#email-text').val()
                // billmail: ,
            },
            dataType: "json",
            success: function (json) {
                console.log(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });

    //
    $('#loan-request').click(function () {
        $.ajax({
            url: "http://192.168.0.191:8080/repayment/api/members/1/app",
            type: "POST",
            data: JSON.stringify({
                amt: 1000,
                term: 3
            }),
			contentType: "application/json",
            dataType: "json",
            success: function (json) {
                console.log(json);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
});