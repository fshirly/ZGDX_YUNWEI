/**
 * 提交编辑
 */
function doEditTopo(){
	var widgetTitle = $("#widgetTitle").val();
//	console.log("widgetTitle==="+widgetTitle);
	var widgetName = $("#widgetName").val();
//	console.log("widgetName==="+widgetName);
	var widgetId = $("#widgetId").val();
	var id = $("#topoSel").val();
//	console.log("topo的id==="+id);
	var url = $("#url").val();
//	console.log("beafore url==="+url);
	url = url.replace(/(topoId=)[^;]+/, '$1' + id);
//	console.log("after url==="+url);
	var index = window.$('#tabsIndex').val();
//	alert(index);
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
