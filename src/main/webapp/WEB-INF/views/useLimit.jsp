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


    <style>
        .errorCss {
            border: 1px solid red;
            background: url(../../resources/img/error.jpg) no-repeat right;
        }

        .successCss {
            background: url(../../resources/img/success.jpg) no-repeat right;
        }
    </style>

    <script>
        $(function () {
            $("#crlTextId").on("change", function () {
                var crlText = $("#crlTextId");
                if (crlText.val() < 100) {
                    crlText.parent().removeClass("ui-body-inherit ui-corner-all successCss");
                    crlText.parent().addClass("errorCss");
                    crlText.parent().innerHTML("<span style='color: red'>请输入整百数字</span>");
                    return false;
                } else {
                    crlText.parent().removeClass("ui-body-inherit ui-corner-all errorCss");
                    crlText.parent().addClass("successCss");
                    return true;
                }
            });
        });
    </script>
</head>
<body>


<div data-role="page" id="useLimit">
    <div data-role="content">
        <div style="text-align: center;background-color: #d3d3d3">
            <h1>当前可用额度50,000</h1>
        </div>
        <form id="useLimitForm" action="#" method="post">

            <div>
                <label for="crlTextId">官人本次想动用多少额度？（仅支持整百）</label>
                <input type="number" min="0" step="100" name="crl" id="crlTextId" value="">
            </div>

            <fieldset class="ui-grid-a">
                <div class="ui-block-a">
                    <a href="#" class="ui-shadow ui-btn ui-corner-all">3期</a>
                </div>
                <div class="ui-block-b">
                    <a href="#" class="ui-shadow ui-btn ui-corner-all">6期</a>
                </div>
            </fieldset>

            <div>
                <div>
                    <span>您每期需要还款：</span>
                    <span style="background-color: #d3d3d3">￥540.20</span>
                </div>
                <p style="color: red;text-align: right">相比信用卡，最高可省￥1500</p>
            </div>
            <div>
                <label for="log">同意借贷协议</label>
                <input type="checkbox" name="login" id="log" value="1" data-mini="true" width="20%">
                <a href="#towDayWaitResult" class="ui-btn ui-shadow ui-corner-all ui-btn-a">提交申请（新户）</a>
                <a href="#useLimitOfGridDialog" id="useLimitBtn" class="ui-btn ui-shadow ui-corner-all ui-btn-a"
                   data-rel="dialog">确认，去复活信用卡（老户）</a>
            </div>
        </form>

    </div>
</div>

<div data-role="page" id="towDayWaitResult">
    <div data-role="content">
        <div style="text-align: center">
            <img src="<%= request.getContextPath()%>/resources/img/2day.jpg">
        </div>
        <div style="text-align: center">
            <p>银行对于信用卡还款都有3天的宽限期</p>

            <p>我们不会让您出滞纳金的[酷]</p>

            <p>• 由于这是您的“第一次”，故申请的结果（额</p>

            <p>度和每期还款金额）可能与预估数值有出入。</p>

            <p>• 申请结果出来后，我们会及时通知您。</p>
        </div>
        <div>
            <a href="#towDayResult" class="ui-btn">知道了</a>
        </div>
    </div>
</div>

<div data-role="page" id="towDayResult">
    <div data-role="content">
        <div style="text-align: center">
            <h2>恭喜官人！</h2>
        </div>
        <div style="text-align: center">
            <p>您本次可动用的额度</p>
            <span>￥</span>
            <span style="font-size: 50px">5,000</span>
        </div>
        <div class="ui-grid-a">
            <div class="ui-block-a">
                <p>还清期数：</p>
            </div>
            <div style="text-align: left">
                <div class="ui-block-b">
                    <fieldset data-role="controlgroup" id="qis" data-type="horizontal" data-mini="true">
                        <input type="radio" name="radio-choice-b" id="radio-choice-c" value="list" checked="checked">
                        <label for="radio-choice-c">3期</label>
                        <input type="radio" name="radio-choice-b" id="radio-choice-d" value="grid">
                        <label for="radio-choice-d">6期</label>
                    </fieldset>
                </div>
            </div>
        </div>

        <div>
            <span>每期需还：</span>
            <span><b>￥540.20</b></span>
        </div>
        <div style="text-align: right">
            <p style="color: red">相比信用卡，总计帮您省了￥150！</p>

            <p style="color: red">相当于一个月的早饭啊！</p>
        </div>
        <div>
            <a href="#" class="ui-btn">确认，复活我的信用卡</a>
        </div>
    </div>
</div>

<div data-role="page" id="useLimitOfGridDialog">
    <div data-role="header">
        <h2>选择信用卡</h2>
    </div>
    <div data-role="content">
        <ul data-role="listview" data-inset="true">
            <li>
                <div>
                    <img src="<%= request.getContextPath()%>/resources/img/cmb.jpg">
                    <a href="#useLimitOfCreditDialog" data-rel="dialog" class="">4392********9950</a>
                </div>
            </li>
            <li>
                <div>
                    <img src="<%= request.getContextPath()%>/resources/img/cmb.jpg">
                    <a href="#useLimitOfCreditDialog" data-rel="dialog">4392********9950</a>
                </div>
            </li>
            <li>
                <div>
                    <img src="<%= request.getContextPath()%>/resources/img/cmb.jpg">
                    <a href="#useLimitOfCreditDialog" data-rel="dialog">4392********9950</a>
                </div>
            </li>
        </ul>
        <div>
            <a href="#useLimitOfAddCreditDialog" data-rel="dialog" class="ui-btn">添加其他信用卡</a>
        </div>
    </div>
</div>

<div data-role="page" id="useLimitOfCreditDialog">
    <div data-role="header">
        <h2>确定信用卡</h2>
    </div>
    <div role="main" class="ui-content">
        <div style="text-align: center">
            <img src="<%= request.getContextPath()%>/resources/img/cmb.jpg"><br/>
            4392********9950<br/>
            请确认是不是这张卡
        </div>
        <div class="ui-grid-a">
            <div class="ui-block-a">
                <a href="#useLimitOfGridDialog" class="ui-btn">重新选择</a>
            </div>
            <div class="ui-block-b">
                <a href="#useLimitOfFullResult" class="ui-btn">就是这张卡片</a>
            </div>
        </div>
    </div>
</div>

<div data-role="page" id="useLimitOfAddCreditDialog">
    <div data-role="header">
        <h2>添加信用卡</h2>
    </div>
    <div role="main" class="ui-content">
        <div class="ui-grid-b">
            <input type="number" value="" placeholder="请填写">
        </div>
        <div>
            <p style="color: sandybrown">请使用您自己的卡片，否则款项可能无法注入</p>
        </div>
        <div>
            <fieldset class="ui-grid-a">
                <div class="ui-block-a">
                    <a href="#useLimitOfGridDialog" class="ui-shadow ui-btn ui-corner-all ui-mini"
                       data-rel="dialog">返回</a>
                </div>
                <div class="ui-block-b">
                    <a href="#useLimitOfCreditDialog" class="ui-shadow ui-btn ui-corner-all ui-mini" data-rel="dialog">确定</a>
                </div>
            </fieldset>
        </div>
    </div>
</div>

<div data-role="page" id="useLimitOfFullResult">
    <div data-role="content">
        <div style="text-align: center">
            <img src="<%= request.getContextPath()%>/resources/img/full.jpg">
        </div>
        <div>
            您的信用卡君已满血复活！</br>
            您需要在下个月的今天还第一笔款。
        </div>
        <div>
            <a href="#" class="ui-btn">好的</a>
        </div>
    </div>
</div>


<div data-role="page" id="useLimitOfResult">
    <div data-role="content">
        <div>
            注资成功！</br>
            您的信用卡已满血复活！<br/>
            您还款到还钱帮的时间为 每月15号。
        </div>
    </div>
</div>

</body>
</html>
