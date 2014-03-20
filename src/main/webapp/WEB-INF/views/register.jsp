<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-20
  Time: 上午11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--<link rel="stylesheet" href="http://localhost:8080/repayment/resources/css/jquery.mobile-1.4.2.min.css"/>--%>
    <%--<script type="javascript" scr="http://localhost:8080/repayment/resources/js/jquery.js"></script>--%>
    <%--<script type="javascript" scr="http://localhost:8080/repayment/resources/js/jquery.mobile-1.4.2.min.js"></script>--%>
    <link rel="stylesheet" href="http://localhost:8080/repayment/resources/css/jquery.mobile-1.4.2.min.css">
    <script src="http://localhost:8080/repayment/resources/js/jquery.js"></script>
    <script src="http://localhost:8080/repayment/resources/js/jquery.mobile-1.4.2.min.js"></script>
    <title>Member Register</title>

</head>
<body>
<div data-role="main" class="ui-content">

    <form action="http://localhost:8080/repayment/register_save.do" method="post">
        <div data-demo-html="true">
            <label for="select-choice-mini" class="select">官人拿的是什么饭碗？</label>
            <select name="member.industry" id="select-choice-mini" data-mini="true" data-inline="true">
                <option value="0">政府机关、社会团体</option>
            </select>
        </div>

        <div data-demo-html="true">
            <label for="select-choice-mini" class="select">官人学富几车？</label>
            <select name="member.education" id="education" data-mini="true" data-inline="true">
                <option value="1">本科</option>
            </select>
        </div>

        <div data-demo-html="true">
            <label>官人平时鸿雁传书用哪个Email？</label>
            <input type="text" name="member.email" id="email" value="">
        </div>

        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</body>
</html>
