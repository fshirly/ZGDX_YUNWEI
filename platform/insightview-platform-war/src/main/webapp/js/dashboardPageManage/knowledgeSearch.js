$(document).ready(
	function() {
			$("#searchBtn").click(function() {
						/*if ('' == $('#title').val()) {
							$("#errorText").html("请输入搜索关键字!");
								return false;
						}*/
		    var keywords = $("#title").val();
		    var str = "";
		    if(keywords.indexOf("#") !=-1 || keywords.indexOf("+") !=-1 || keywords.indexOf("&") !=-1 || keywords.indexOf("%") !=-1 ){
		    	str = keywords.replace(/#/g,"%23");
		    	str = str.replace(/&/g,"%26");
		    	str = str.replace(/\+/g,"%2B");
		    	str = str.replace(/%/g,"%25");
		    }else{
		    	str = keywords;
		    }
			parent.parent.toShowTabsInfo('/dashboardPageManage/retrievalResults?keywords=' + $('#title').val(),'知识检索');
	});
});