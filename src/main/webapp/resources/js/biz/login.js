/**
 * Created by Richard on 14-8-24.
 */
$(function(){
    var login = {};
    login.init = function() {
        login.initEvent();
    };
    login.initEvent = function(){
        $("#loginDiv").on("click","#login",function(){
            var usernameVal = $.trim($("#username").val());
            if(usernameVal == "") {
                alert("请输入用户名！");
                return;
            }
            var passwordVal = $("#password").val();
            if(passwordVal == "") {
                alert("请输入密码！");
                return;
            }
            var data = {
                username:usernameVal,
                password:passwordVal
            };
            $.ajax({
                url: "api/login",
                dataType: "json",
                type: "POST",
                data: JSON.stringify(data),
                async: false,
                contentType: "application/json",
                success: function (json) {
                    if (!json) return;
                    alert("登录成功！");
                    window.location.href = "home.html?menus="+json;
                }
            });
        });
    };
    login.init();
});