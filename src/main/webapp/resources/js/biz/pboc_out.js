/**
 * Created by Richard Xue on 14-6-17.
 */
$(function(){
    var pboc_out = {};
    pboc_out.init = function(){
        pboc_out.loadData("api/pboc/out/list");
        pboc_out.initEvent();
    };
    pboc_out.loadData = function(url) {
        $("#pboc-table").html("<tr style=\"background: #ffff00;\">\n"+
            "<td style=\"width:4%;\"><input type=\"checkbox\" id=\"checkAll\" style=\"margin-left: 40%;\"></td>\n"+
            "<td style=\"width:6%;\">序号</td>\n"+
            // "<td style=\"width:10%;\">ID</td>\n"+
            "<td style=\"width:10%;\">姓名</td>\n"+
            "<td style=\"width:15%;\">身份证</td>\n"+
            "<td style=\"width:10%;\">PDF</td>\n"+
            "<td style=\"width:10%;\">身份证正面</td>\n"+
            "<td style=\"width:10%;\">身份证反面</td>\n"+
        "</tr>");
        $.ajax({
            url: url,
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                if(!data) return;
                var contentHtml = "";
                $.each(data,function(i,entity){
                    contentHtml += "<tr>\n"+
                        "<td><input type=\"checkbox\" class=\"check-item\" style=\"margin-left: 40%;\"></td>\n"+
                        "<td>"+(i+1)+"</td>\n"+
                        // "<td>"+entity.id+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.idNo+"</td>\n"+
                        "<td><input type=\"button\" class=\"idCardPdf\" value=\"下载\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;'></td>\n"+
                        "<td>\n"+
                        "    <input type=\"button\" class=\"idCardFront\" value=\"处理\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;' imgName=\""+entity.imageFront+"\" idNo=\""+entity.idNo+"\">\n"+
                        "</td>\n"+
                        "<td>\n"+
                        "    <input type=\"button\" class=\"idCardBack\" value=\"处理\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;' imgName=\""+entity.imageBack+"\" idNo=\""+entity.idNo+"\">\n"+
                        "</td>\n"+
                    "</tr>"
                });
                $("#pboc-table").append(contentHtml);
            },
            error: function(data) {
                alert("服务器请求异常！");
            }
        });
    };
    pboc_out.initEvent = function(){
        $("#pboc-table").on("click","#checkAll",function(){
            $(".check-item").prop("checked",$(this).prop("checked"));
        });
        $("#pboc-table").on("click",".check-item",function(){
            var checkAll = true;
            $(".check-item").each(function(i){
                if(!$(this).prop("checked")) {
                    checkAll = false;
                    return false;
                }
            });
            $("#checkAll").prop("checked",checkAll);
        });
        $("#pboc-table").on("click",".idCardPdf",function(){
            $.ajax({
                url: "api/pboc/out",
                dataType: "json",
                type: "GET",
                contentType: "application/json",
                success: function (json) {
                    alert("---->");
                },
                error: function(data) {
                }
            });
        });
        $("#pboc-table").on("click",".idCardFront",function(){
            var param = {
                imgName : $(this).attr("imgName"),
                idCard : $(this).attr("idNo"),
                frontOrBack : "1"
            };
            window.showModalDialog("idcard.html",param,"dialogWidth=1000px;dialogHeight=800px");
        });

        $("#pboc-table").on("click",".idCardBack",function(){
            var param = {
                imgName : $(this).attr("imgName"),
                idCard : $(this).attr("idNo"),
                frontOrBack : "2"
            };
            window.showModalDialog("idcard.html",param,"dialogWidth=1000px;dialogHeight=800px");
        });
    };
    pboc_out.init();
});