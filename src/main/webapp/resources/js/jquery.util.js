/**
 * Created by li Jiwang on 14-3-24.
 */

/**
 * 添加一个层，用于覆盖整个用户操作区域，防止操作者重复操作
 *
 * @param message
 */
function showBlock(message) {
    message = message || '<br/>&nbsp;&nbsp;&nbsp;正在处理中，请稍候。。。';
    message = "<span class='loading'>" + message + "</span>";
    $.blockUI({
        message : message,
        css : {
            width : '100px',
            height : '30px',
            '-webkit-border-radius' : '10px',
            '-moz-border-radius' : '10px',
            opacity : .8
        },
        overlayCSS : {
            opacity : '0.2'
        }
    });
    setTimeout($.unblockUI, 2000);
}
