$(document).ready(function() {
});


/**
 * 提交
 */
function doCommit() {
	if (checkInfo("#tblEdit")) {
			var widgetTitle = $("#widgetTitle").val();
			var widgetName = $("#widgetName").val();
			var moClass = $("#deviceName").val();
			var widgetId = $("#widgetId").val();
			var url = $("#url").val();
			url = url.replace(/(moClass=)[^;]+/, '$1' + moClass);
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

