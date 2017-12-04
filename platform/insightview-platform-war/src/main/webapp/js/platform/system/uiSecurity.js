/**
 * 界面权限控制JS文件
 */
$(document).ready(function() {
  var jsonObj = window.location + "";
  $.ajax({
    type: "POST",
    url: getPath() + "/sys/uiSecurity/queryData",
    data: jsonObj,
    dataType: 'json',
    contentType: "application/json",
    success: function(result) {
      //alert(result.data);
    },
    error: function(result) {
      $.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
    }
  });
});

function getPath() {
  var e = new Error('err');
  var stack = e.stack || e.sourceURL || e.stacktrace || '';
  var path = stack.substring(stack.indexOf("http"), stack.indexOf("/js/platform"));
  return path;
}