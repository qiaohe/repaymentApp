<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-20
  Time: 下午1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>测额度</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.mobile-1.3.2.min.css">

    <script src="<%=request.getContextPath() %>/resources/js/jquery.1.9.1.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.mobile-1.3.2.min.js"></script>

    <style>
        #progressbox {
            border: 1px solid #ccc;
            padding: 1px;
            position: relative;
            width: 400px;
            border-radius: 3px;
            margin: 10px;
            display: none;
            text-align: left;
        }

        #progressbar {
            height: 40px;
            border-radius: 3px;
            background-color: #20bbfb;
            width: 1%;
        }

        #statustxt {
            top: 3px;
            left: 50%;
            position: absolute;
            display: inline-block;
            color: #000000;
        }
    </style>
    <script type="text/javascript">
        $(function () {

            //elements
            var progressbox = $('#progressbox');
            var progressbar = $('#progressbar');
            var statustxt = $('#statustxt');
            var submitbutton = $("#SubmitButton");
            var myform = $("#UploadForm");
            var output = $("#output");
            var completed = '0%';

            $(myform).ajaxForm({
                beforeSend: function () { //brfore sending form
                    submitbutton.attr('disabled', ''); // disable upload button
                    statustxt.empty();
                    progressbox.slideDown(); //show progressbar
                    progressbar.width(completed); //initial value 0% of progressbar
                    statustxt.html(completed); //set status text
                    statustxt.css('color', '#000'); //initial color of status text
                },
                uploadProgress: function (event, position, total, percentComplete) { //on progress
                    progressbar.width(percentComplete + '%') //update progressbar percent complete
                    statustxt.html(percentComplete + '%'); //update status text
                    if (percentComplete > 50) {
                        statustxt.css('color', '#fff'); //change status text to white after 50%
                    }
                },
                complete: function (response) { // on complete
                    output.html(response.responseText); //update element with received data
                    myform.resetForm();  // reset form
                    submitbutton.removeAttr('disabled'); //enable submit button
                    progressbox.slideUp(); // hide progressbar
                }
            });
        });
    </script>

</head>
<body>

<form id="UploadForm" action="<%=request.getContextPath() %>/members/3/idCardFront.html" method="post"
      enctype="multipart/form-data"
      data-ajax="false">
    <input type="file" name="idCardFrontFile"><br>
    <input type="submit" value="Upload File to Server">
</form>

<div id="progressbox">
    <div id="progressbar"></div>
    <div id="statustxt">0%</div>
</div>
<div id="output"></div>
</body>
</html>
