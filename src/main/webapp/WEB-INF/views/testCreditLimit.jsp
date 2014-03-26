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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery-ui.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.mobile-1.3.2.min.css">

    <script src="<%=request.getContextPath() %>/resources/js/jquery.1.9.1.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery-ui.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.mobile-1.3.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>

    <script src="<%=request.getContextPath() %>/resources/js/jquery.util.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            $( "#memberInfoForm" ).validate({
                submitHandler: function( form ) {
                    $("#memberInfoForm").ajaxSubmit(function(data){
                        if(data != null || data != ""){
                            location.href = "testResult.html?crl="+data;
                        }else{
                            alert("提交失败!");
                            return false;
                        }
                    });
                }
            });

            //身份证正面上传
            $("#idCardFrontFile").on("change", function () {
                $("#testCreditLimitForm").ajaxSubmit(function(data){
                    if(data != "" && data != "1"){
                        $("#p_idCardNumber_id").remove();
                        $("#idCardFrontFile").parent().append("<p id='p_idCardNumber_id' style='color: red'>身份证号码为:"+data+"</p>");
                        $("#idcardNo").val(data);
                    }else{
                        alert("识别失败!");
                        $("#idcardNo").val("");
                        return false;
                    }
                });
                showBlock("");
            });

            //身份证反面上传
            $("#idCardBackFile").on("change", function () {
                $("#testCreditLimitForm").attr("action", "idCardBack.html");
                $("#testCreditLimitForm").ajaxSubmit(function(data){
                    if(data != null || data != ""){
                        alert("上传成功并保存!")
                        $("#idCardBackFile_isSave").val("1");
                        $("#p_idCardDateThru_id").remove();
                        $("#idCardBackFile").parent().append("<p id='p_idCardDateThru_id' style='color: red'>省份证到期时间:"+data+"</p>");
                    }else{
                        $("#idCardBackFile_isSave").val("");
                        alert("上传失败");
                        return false;
                    }
                });
            });

            //信用卡上传
            $("#creditCardFile").on("change", function () {
                $("#testCreditLimitForm").attr("action", "creditCard.html");
                $("#testCreditLimitForm").ajaxSubmit(function(data){
                    if(data != null || data != ""){
                        alert("上传成功!")
                        $("#creditCardNo").val(data);
                    }else{
                        $("#creditCardNo").val("");
                        alert("上传失败!");
                        return false;
                    }
                });
            });

            //拍照提交
            $("#link_memberInfo").on("click",function(){
                var idcardNo = $("#idcardNo").val();
                var idCardBackFile_isSave = $("#idCardBackFile_isSave").val();
                var creditCardNo = $("#creditCardNo").val();
                if(idcardNo == "" || idcardNo == null ){
                    alert("请扫描省份证!");
                    return false;
                }
                if(creditCardNo == "" || creditCardNo == null){
                    alert("请扫描信用卡!");
                    return false;
                }
            });

            //radio_email_1
            $("#radio_email_1").on("click",function(){
                $("#billMailbox_other_div").hide();
            });

            //radio_email_2
            $("#radio_email_2").on("click",function(){
                $("#billMailbox_other_div").show();
            });
        });
    </script>

    <style>
        label.error {
            font-weight: bold;
            color: red;
            padding: 2px 8px;
            margin-top: 2px;
        }
    </style>
</head>
<body>

<!-- 扫描省份证，信用卡-->
<div data-role="page" id="testCreditLimit">
    <div data-role="content">
        <form id="testCreditLimitForm" action="idCardFront.html" enctype="multipart/form-data" method="post">
            <input type="hidden" name="id" value="${member.id}">
            <div data-role="header">
                <h1>测额度</h1>
            </div>
            <div data-role="fieldcontain" style="text-align: center">
                <img src="<%=request.getContextPath() %>/resources/img/testImg.png" >
            </div>
            <div>
                <label for="idCardFrontFile">身份证-正面:</label>
                <input type="file" name="idCardFrontFile" id="idCardFrontFile">
                <input type="hidden" id="idcardNo">
            </div>
            <div>
                <label for="idCardBackFile">身份证-反面 :</label>
                <input type="file" name="idCardBackFile" id="idCardBackFile" value="">
                <input type="hidden" id="idCardBackFile_isSave">
            </div>
            <div>
                <label for="creditCardFile">信用卡-正面:</label>
                <input type="file" name="creditCardFile" id="creditCardFile" value="">
                <input type="hidden" id="creditCardNo">
            </div>
            <a href="#memberInfo" id="link_memberInfo" data-role="button">下一步</a>
        </form>
    </div>
</div>

<!-- 会员信息-->
<div data-role="page" id="memberInfo">

    <form id="memberInfoForm" action="updateMember.html" method="post" data-ajax="false">
        <input type="hidden" name="id" value="${member.id}">
        <div data-demo-html="true">
            <label for="industry" class="ui-select">官人拿的是什么饭碗？</label>
            <select name="industry" id="industry">
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
            <select name="education" id="education">
                <option value="0">未知</option>
                <option value="1">初中及以下</option>
                <option value="2">高中、中专</option>
                <option value="3">大专</option>
                <option value="4">本科</option>
                <option value="5">硕士</option>
                <option value="6">博士</option>
            </select>
        </div>

        <div id="billMailbox_div">
            <div>
                <label for="email">官人平时鸿雁传书用哪个Email？</label>
                <input type="email" name="email" id="email" class="required email">
            </div>
            <div>
                <input type="radio" name="radio_email" id="radio_email_1" value="1" checked="checked">
                <label for="radio_email_1">我也用这个Email收信用卡电子账单的</label>
            </div>

            <div>
                <label for="password">给我邮箱密码，我能更好地为官人服务？</label>
                <input type="password" name="password" id="password" required>
            </div>
            <div>
                <input type="radio" name="radio_email" id="radio_email_2" value="2">
                <label for="radio_email_2">我用其他Email收信用卡电子账单</label>
            </div>
        </div>

        <div data-role="content" id="billMailbox_other_div" style="display: none">
            <div data-role="fieldcontain">
                <label for="billEmail">账单邮箱</label>
                <input type="email" name="billEmail" id="billEmail" value="" class="required email">
            </div>

            <div data-role="fieldcontain">
                <label for="passwordOfBillEmail">邮箱密码</label>
                <input type="password" name="passwordOfBillEmail" id="passwordOfBillEmail" value="">
            </div>
        </div>
        <div>
            <input type="submit" id="memberInfoBtn" value="提交">
        </div>
    </form>
</div>
</body>
</html>
