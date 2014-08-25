/**
 * Created by Richard on 14-8-24.
 */
$(function(){
    var home = {};
    home.init = function() {
        var menus = $.getParamter("menus");
        home.replaceContent(menus);
    };
    home.replaceContent = function(url) {
        var menuUrls = url.split(",");
        var contentHtml = "<div style=\"color: #555;font-size: 20px;text-align: center;line-height: 24px;\">菜单</div>";
        contentHtml += "<ul class=\"list-group\">\n";
        $.each(menuUrls,function(index,ele){
            if(!home.exceptUrl(ele)) {
                contentHtml += "<li class=\"list-group-item\"><a href=\""+ele+"\">"+ele+"</a></li>\n";
            }
        });
        contentHtml += "</ul>";
        $("#loginDiv").html(contentHtml);
    };
    home.exceptUrl = function(menu) {
        return "home.html,viewer.html,appDetail.html,credit.html,account/detail1.html".indexOf(menu) > -1;
    };
    home.init();
});