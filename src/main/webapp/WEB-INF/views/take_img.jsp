<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-20
  Time: 下午1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link rel="stylesheet" href="resources/css/jquery.mobile-1.3.2.css">
    <script src="resources/js/jquery.1.9.1.js"></script>
    <script src="resources/js/jquery.mobile-1.3.2.min.js"></script>

</head>
<body>

<div data-role="page" id="pageone">

    <div data-role="content">
        <form id="form" action="fileUpload.do" method="post" enctype="multipart/form-data" data-ajax="false">
            <div data-role="header">
                <h1>测额度</h1>
            </div>
            <div data-role="fieldcontain" style="vertical-align: middle">
                <img src="resources/img/testImg.png">
            </div>

            <div>
                <label for="file1">身份证-正面:</label>
                <input type="file" name="file1" id="file1" value="">
            </div>
            <div>
                <label for="file2">身份证-反面 :</label>
                <input type="file" name="file2" id="file2" value="">
            </div>
            <div>
                <label for="file3">信用卡-正面:</label>
                <input type="file" name="file3" id="file3" value="">
            </div>

            <input type="submit" value="提交">
        </form>
    </div>
</div>
</body>
</html>
