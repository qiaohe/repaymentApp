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
                        "<td><input type=\"checkbox\" class=\"check-item\" style=\"margin-left: 40%;\" idNo=\""+entity.idNo+"\"></td>\n"+
                        "<td>"+(i+1)+"</td>\n"+
                        // "<td>"+entity.id+"</td>\n"+
                        "<td>"+entity.name+"</td>\n"+
                        "<td>"+entity.idNo+"</td>\n"+
                        "<td><input type=\"button\" class=\"idCardPdf\" value=\"打开\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;' idNo=\""+entity.idNo+"\"></td>\n"+
                        "<td>\n"+
                        " <input type=\"button\" class=\"idCardFront\" value=\"处理\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;' imgName=\""+entity.imageFront+"\" idNo=\""+entity.idNo+"\">\n"+
                        "</td>\n"+
                        "<td>\n"+
                        " <input type=\"button\" class=\"idCardBack\" value=\"处理\" style='margin:5px 0 5px 40px;width: 80px;text-align: center;' imgName=\""+entity.imageBack+"\" idNo=\""+entity.idNo+"\">\n"+
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
    pboc_out.processImage = function(flag){
        $.ajax({
            url: "api/pboc/process/all",
            dataType: "JSON",
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                if(data && data == "1") {
                    console.log("处理成功！");
                } else {
                    alert("请稍后重试！");
                }
            },
            error: function(data) {
                alert("请求异常！");
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
        $("#processAll").on("click",function(){
            if(confirm("您确定要全部复制？")) {
                pboc_out.processImage();
            }
        });
        $("#processBatch").on("click",function(){
            var idNos = "";
            $("input.check-item:checked").each(function(){
                idNos += ($(this).attr("idNo") ? $(this).attr("idNo")+"," : "");
            });
            if(!idNos) {
                alert("请选择要处理身份证的项！");
                return;
            }
            $.ajax({
                url: "api/pboc/process/batch/"+idNos,
                dataType: "JSON",
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if(data && data == "1") {
                        alert("处理成功！");
                    } else {
                        alert("处理失败，请稍后重试！");
                    }
                },
                error: function(data) {
                    alert("请求异常！");
                }
            });
        });
        $("#download").on("click",function(){
            var idNos = "";
            $("input.check-item:checked").each(function(){
                idNos += ($(this).attr("idNo") ? $(this).attr("idNo")+"," : "");
            });
            if(!idNos) {
                alert("请选择要打包下载的项！");
                return;
            }
            $.ajax({
                url: "api/pboc/export/list/"+idNos,
                dataType: "JSON",
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if(data) {
                        window.open("api/resources/idcard/temp/"+data,"_blank");
                    } else {
                        alert("请稍后重试！");
                    }
                },
                error: function(data) {
                    alert("请求异常！");
                }
            });
        });
        $("#pboc-table").on("click",".idCardPdf",function(){
            var idNo = $(this).attr("idNo");
            $.ajax({
                url: "api/pboc/export/"+idNo,
                dataType: "JSON",
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if(data && data == "1") {
                        var url = "resources/plugin/pdf/web/viewer.html?pdfUrl=api/resources/idcard/temp/"+idNo+".pdf";
                        window.open(url,"_blank");
                    } else {
                        alert("请重新生成PDF文件！");
                    }
                },
                error: function(data) {
                    alert("请求异常！");
                }
            });
        });
        $("#pboc-table").on("click",".idCardFront",function(){
//            var param = {
//                imgName : $(this).attr("imgName"),
//                idNo : $(this).attr("idNo"),
//                frontOrBack : "1"
//            };
//            window.showModalDialog("idcard1.html?r="+new Date().getTime(),param,"dialogWidth=1280px;dialogHeight=1000px");
            window.open("idcard1.html?imgName="+$(this).attr("imgName")+"&idNo="+$(this).attr("idNo")+"&frontOrBack=1&r="+new Date().getTime());
        });
        $("#pboc-table").on("click",".idCardBack",function(){
//            var param = {
//                imgName : $(this).attr("imgName"),
//                idNo : $(this).attr("idNo"),
//                frontOrBack : "2"
//            };
//            window.showModalDialog("idcard1.html?r="+new Date().getTime(),param,"dialogWidth=1280px;dialogHeight=1000px");
            window.open("idcard1.html?imgName="+$(this).attr("imgName")+"&idNo="+$(this).attr("idNo")+"&frontOrBack=2&r="+new Date().getTime());
        });
    };
    pboc_out.init();
});