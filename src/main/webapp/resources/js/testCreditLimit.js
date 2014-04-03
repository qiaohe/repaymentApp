/**
 * Created by dell on 14-4-2.
 */
$(function(){
    /**启动摄像机功能**/
    $(document).ready(function(){
        $('#open1'||'#open_text1').click(function(){
            $('#uploadFile1[type=file]:first').trigger('click');
            $('#open_text1').css('display','none');
        });
        $('#open2'||'#open_text2').click(function(){
            $('#uploadFile2[type=file]:first').trigger('click');
            $('#open_text2').css('display','none');
        });
        $('#open3'||'#open_text3').click(function(){
            $('#uploadFile3[type=file]:first').trigger('click');
            $('#open_text3').css('display','none');
        });
    });
});






























//    $(document).ready(function(){
//        var a=document.getElementById("emailAddress").value();
//            var email = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
//            if(a!=email){
//                alert('请输入合法的email地址！')
//            }
//    });

//$(document).ready(function(){
//        $(document).mouseout(function(){
//            var email=document.getElementById("emailAddress").value;
//            var isemail=/^\w+([-\.]\w+)*@\w+([\.-]\w+)*\.\w{2,4}$/;
//            if (email=="") {
//                error();
//                return false;
//            }
//            if (email.length>25){
//                error();
//                return false
//            }
//            if (!isemail.test(email)){
//                error();
//                return false;
//            }
//        });

//        $('#error1').error(function(){
//            $('#error1').css('display','block');
//        });
