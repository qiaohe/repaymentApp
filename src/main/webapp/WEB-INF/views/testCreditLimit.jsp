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
    <title></title>
    <link rel="stylesheet" href="resources/css/jquery.mobile-1.3.2.min.css">
    <script src="resources/js/jquery.1.9.1.js"></script>
    <script src="resources/js/jquery.mobile-1.3.2.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#file1").on("change", function () {
                $.ajax({
                    url: "fileUpload.do",
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        alert(data);
                    },
                    error: function (data) {
                        alert(data);
                    }
                });
            });
        });
    </script>

</head>
<body>

<!-- 扫描省份证，信用卡-->
<div data-role="page" id="testCreditLimit">
    <div data-role="content">
        <form id="form" action="uploadIdCardFront.html" enctype="multipart/form-data" method="post" data-ajax="false">
            <div data-role="header">
                <h1>测额度</h1>
            </div>
            <div data-role="fieldcontain" style="vertical-align: middle">
                <img src="resources/img/testImg.png">
            </div>
            <div>
                <label for="file">身份证-正面:</label>
                <input type="file" name="file" id="file" value="">
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

<!-- 会员信息-->
<div data-role="page" id="memberInfo">

    <form action="testLimit_save.do" method="post" data-ajax="false">
        <div data-demo-html="true">
            <label for="industry" class="ui-select">官人拿的是什么饭碗？</label>
            <select name="member.industry" id="industry">
                <option value="0">未知</option>
                <option value="1">政府机关、社会团体</option>
                <option value="2">军事、公检法</option>
                <option value="3">专业事务所</option>
                <option value="4">金融业</option>
                <option value="5">交通运输</option>
                <option value="6">公共事业</option>
                <option value="7">能源矿产</option>
                <option value="8">商业零售、内外贸易</option>
                <option value="9">房地产、建筑业</option>
                <option value="10">加工、制造业</option>
                <option value="11">服务、咨询</option>
                <option value="12">农林牧渔</option>
                <option value="13">网店店主</option>
                <option value="14">学生</option>
                <option value="15">自由职业者</option>
                <option value="16">其他</option>
            </select>
        </div>

        <div>
            <label for="education" class="ui-select">官人学富几车？</label>
            <select name="member.education" id="education">
                <option value="0">未知</option>
                <option value="1">初中及以下</option>
                <option value="2">高中、中专</option>
                <option value="3">大专</option>
                <option value="4">本科</option>
                <option value="5">硕士</option>
                <option value="6">博士</option>
            </select>
        </div>

        <div>
            <label for="member_email">官人平时鸿雁传书用哪个Email？</label>
            <input type="text" name="member.email" id="member_email" value="">
        </div>
        <div>
            <input type="radio" name="radio_email" id="radio_email_1" value="1" checked="checked">
            <label for="radio_email_1">我也用这个Email收信用卡电子账单的</label>
        </div>

        <div>
            <label for="member_password">官人平时鸿雁传书用哪个Email？</label>
            <input type="text" name="member.password" id="member_password" value="">
        </div>
        <div>
            <input type="radio" name="radio_email" id="radio_email_2" value="2">
            <label for="radio_email_2">我用其他Email收信用卡电子账单</label>
        </div>

        <div data-role="content" data-display="false">
            <div data-role="fieldcontain">
                <label for="bill_email">账单邮箱</label>
                <input type="text" name="member.email" id="bill_email" value="">
            </div>

            <div data-role="fieldcontain">
                <label for="bill_password">邮箱密码</label>
                <input type="text" name="member.password" id="bill_password" value="">
            </div>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
</body>
</html>
