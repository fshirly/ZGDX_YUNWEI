
 $(function(){
	 
	// $("#temTr").hide();
	 
	 //console.log("1111111111111");
	 
	 if($("#roomView").val() === "temperatureView")
	 {
		$("#temTr").show();
	 }
	 else
	 {
		 $("#temTr").hide();
	 }
	 
	 $("#roomView").change(function(){
		 
		 if($(this).val() === "temperatureView")
		 {
			$("#temTr").show();
		 }
		 else
		 {
			 $("#temTr").hide();
		 }
	 });
 });

/**
 * 提交编辑
 */
function doRoom3dEdit(){
	
	var widgetTitle = $("#widgetTitle").val();//部件标题
	var widgetName = $("#widgetName").val();//部件名称
	var widgetId = $("#widgetId").val();//部件ID
	var roomId = $("#roomId").val();//机房ID
	var roomView = $("#roomView").val();//机房视图
	var temperView = $("#temperView").val();//动环视图
	var url = $("#url").val();//加载机房url
	
	url = url.replace(/(roomId=)[^;]+/, '$1' + roomId);
	url = url.replace(/(viewType=)[^;]+/, '$1' + roomView);
	url = url.replace(/(drivceType=)[^;]+/, '$1' + temperView);
	
//	console.log("url=" + url);
	
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
