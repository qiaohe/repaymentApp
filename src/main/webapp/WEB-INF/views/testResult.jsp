<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-22
  Time: 下午2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.mobile-1.3.2.min.css">
    <script src="<%=request.getContextPath() %>/resources/js/jquery.1.9.1.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.mobile-1.3.2.min.js"></script>
    <title>测试结果</title>
</head>
<body>
<div data-role="page" id="testResult">
    <div style="text-align: center">
        <h1>信用额度：${crl}</h1>
        <img src="<%=request.getContextPath() %>/resources/img/testResult.jpg">
        <p>您的额度打败了70%的用户</p>
        <p>您的额度将被保存，您可以随时使用。</p>
    </div>
    <div>
        <button value="去跟小伙伴嘚瑟一下"></button>
        <button value="巨款啊！现在就去用"></button>
    </div>
</div>
</body>
</html>
