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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.mobile-1.4.2.min.css">

    <script src="<%=request.getContextPath() %>/resources/js/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.mobile-1.4.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>

    <link href="<%=request.getContextPath() %>/resources/css/indexGraygreen.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/resources/css/base.css" rel="stylesheet">

</head>
<body>

<div class="main" data-role="page" id="testCredit">
    <div class="content" data-role="content">
        <div class="header">
            <span class="header_icon"></span>
            <span class="header_icon_text">请拍摄身份证、信用卡</span>
            <span class="header_icon_text">请保持卡片充满屏幕</span>
        </div>
        <form id="testCreditLimitForm" action="idCardFront.html" enctype="multipart/form-data" method="post" class="form">
            <div class="menu">
                <div class="menu_left">
                    <span class="menu_icon1"></span>
                </div>
                <div class="url_space">
                    <input type="file" class="txtInput" style="visibility: hidden" id="uploadFile1">
                    <input type="text" class="txtInput font_yh" placeholder="拍摄身份证正面" id="open_text1" >
                </div>
                <sapn class="camera" id="open1"></sapn>
            </div>
            <div class="menu">
                <div class="menu_left">
                    <span class="menu_icon2"></span>
                </div>
                <div class="url_space">
                    <input type="file" class="txtInput font_yh font_yh" style="visibility: hidden" id="uploadFile2">
                    <input type="text" class="txtInput" placeholder="拍摄身份证反面" id="open_text2">
                </div>
                <sapn class="camera" id="open2"></sapn>
            </div>
            <div class="menu">
                <div class="menu_left">
                    <span class="menu_icon3"></span>
                </div>
                <div class="url_space">
                    <input type="file" class="txtInput font_yh" style="visibility: hidden" id="uploadFile3">
                    <input type="text" class="txtInput" placeholder="拍摄信用卡·正面" id="open_text3">
                </div>
                <sapn class="camera" id="open3"></sapn>
            </div>
            <a href="#personMessage" id="link_memberInfo" data-role="button" class="footer">
                <span class="next">下一步</span><span class="number">1/2</span>
            </a>
        </form>
    </div>
</div>



<!---personMessage-->
<div data-role="page" id="personMessage" class="personMessageMain">
    <div class="personMessage_content" data-role="content">
        <div class="header">
            <span class="personMessage_header_icon"></span>
        </div>

        <form id="memberInfoForm" action="updateMember.html" method="post" data-ajax="false"class="personMessage_form">
            <span class="personMessage_icon"></span>
            <div data-demo-html="true" class="select_space">
                <label for="industry" class="ui-select">请选择您从事的职业</label>
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

            <div class="select_space">
                <label for="education" class="ui-select">请选择您的学历</label>
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
            <div id="billMailbox_div" class="select_space">
                <div class="emm">
                    <label>请输入你的Email</label>

                    <input type="email" name="email" id="email" placeholder="您的邮箱地址..">
                </div>
                <a href="#testLimitBillbox"  data-role="button" class="footer">
                    <span class="next">提交</span><span class="number">2/2</span>
                </a>

            </div>
        </form>
    </div>
</div>


<div data-role="page" id="testLimitBillbox">
    <div data-role="header">
        <h2>账单邮箱</h2>
    </div>
    <div data-role="content">

        <fieldset data-role="controlgroup">
            <table width="100%">
                <tr>
                    <td colspan="2">
                        <label>信用卡账单Email</label>
                    </td>
                </tr>
                <tr>
                    <td width="10%">
                        <input type="radio" name="radioEmail"  value="" checked="true"/>
                    </td>
                    <td  width="90%">
                        <input type="email" placeholder="信用卡账单Email" id="emailAddress1">
                    </td>
                </tr>
                <tr>
                    <td width="10%">
                        <input type="radio" name="radioEmail"  value="" />
                    </td>
                    <td width="90%">
                        <label>feitian.gan@gmail.com</label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label>上述Email密码</label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="text" placeholder="上述Email密码" >
                    </td>
                </tr>
            </table>
        </fieldset>


        <div class="ui-grid-a">
            <div class="ui-block-a">
                <a href="#testLimitResult" class="ui-btn">跳过本步</a>
            </div>
            <div class="ui-block-b">
                <a href="#testLimitResult" class="ui-btn">确定,继续</a>
            </div>
        </div>
    </div>
</div>

<div data-role="page" id="testLimitResult">
    <div data-role="content">
        <div style="text-align: center">
            <h1>信用额度：50000</h1>
            <img src="<%=request.getContextPath() %>/resources/img/testResult.jpg">
            <p>您的额度打败了70%的用户</p>
            <p>您的额度将被保存，您可以随时使用。</p>
        </div>
        <div>
            <a href="#" id="useLimitLink" class="ui-btn">巨款啊！现在就去复活信用卡</a>
            <a href="#" class="ui-btn">去跟小伙伴嘚瑟一下</a>
            <a href="#testLimitBillbox" class="ui-btn">换张信用卡再试试</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/testCreditLimit.js"></script>
<script>
    $(function(){
        $("#useLimitLink").click(function(){
            location.href = 'useLimit.html';
        });
    });
</script>
</body>
</html>
