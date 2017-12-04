$(document).ready(function() {
});


/**
 * 提交
 */
function doCommit() {
	if (checkNum() && checkInfo("#tblEdit")) {
			var widgetTitle = $("#widgetTitle").val();
			var widgetName = $("#widgetName").val();
			var moClass = $("#deviceName").val();
			var widgetId = $("#widgetId").val();
			var num = $("#num").val();
			var url = $("#url").val();
			url = url.replace(/(moClass=)[^;]+/, '$1' + moClass).replace(/(num=)[^;]+/, '$1' + num);
			var index = window.$('#tabsIndex').val();
			var eidtPortalName = window.$('#eidtPortalName').val();
			if(index != undefined){
				var ifr = window.document.getElementById('ifr'+eidtPortalName); 
				var win = ifr.window || ifr.contentWindow; 
				win.doReLoadData("24H",widgetTitle,url,widgetName,widgetId);
			}else{
				window.doReLoadData("24H",widgetTitle,url,widgetName,widgetId);
			}
			$('#popView').window('close');
	}
}
function checkNum() {
	var num = $("#num").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(num))) {
		$.messager.alert("提示", "排名只能输入正整数！", "info", function(e) {
			$("#num").focus();
		});
		return false;
	}
	return true;
}
