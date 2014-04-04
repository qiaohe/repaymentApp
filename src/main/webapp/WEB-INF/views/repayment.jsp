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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.mobile-1.4.2.min.css">
    <script src="<%=request.getContextPath() %>/resources/js/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.mobile-1.4.2.min.js"></script>
</head>
<body>
<div data-role="page" id="repayment">
    <div data-role="content">
        <div>
            2013-09-09 注资￥990,000入卡片2345
        </div>
        <div>
            <h4>下期应还金额</h4>
            <h1>￥542,000</h1>
            <h4>4月20日到期</h4>
        </div>

        <div>
            <ul data-role="listview" data-inset="true">
                <li>
                    <a href="#">立即还本期款项</a>
                </li>
                <li>
                    <a href="#">注资详情</a>
                </li>
                <li>
                    <a href="#">还款明细</a>
                </li>
                <li>
                    ...
                </li>
                <li>
                    <a href="#">总计借款信息</a>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
