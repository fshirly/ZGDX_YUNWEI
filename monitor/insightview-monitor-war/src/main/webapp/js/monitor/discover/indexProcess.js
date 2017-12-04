var timerId;
$(function() {
	var way = $("#way").val();
	if(way == "1" || way == 1 || way == "2" || way == 2){
		$("#showProcess").show();
		$("#hideProcess").hide();
	}else{
		$("#showProcess").hide();
		$("#hideProcess").show();
	}
	
	//每隔0.5秒自动调用方法，实现进度条的实时更新
	timerId = window.setInterval(getForm, 500);
});

function getForm() {
	var way = $("#way").val();
	//使用JQuery从后台获取JSON格式的数据
	var uri = getRootName()+"/monitor/discover/getProgress2?taskID3=" + $("#taskID").val();
	$.ajax( {
		type : "post",
		url : uri,//发送请求地址
		contentType : "json",
		dataType : "json",
		async:false,
		success : function(data) { 
			$.each(data, function(i, item) {
//				console.log("progressValue==="+item.progressValue);
				if (item.progressValue >= 100) {
					var nextstep = document.getElementById("nextstep");
					nextstep.style.display = 'block';
					var cancelDiscover = document.getElementById("cancelDiscover");
					cancelDiscover.style.display = 'none'; 
					$("#hideProcess").hide();
					window.clearInterval(timerId);
				}
//				console.log("isError==="+item.isError);
				if(item.isError == "true" ||item.isError == true){
					if(way == "1" || way == 1 || way == "2" || way == 2){
						$('#p').progressbar('setValue', item.progressValue);
					}else{
						$("#hideProcess").hide();
					}
					$("#processtxt").animate( {
						//height : "300px"
					});
					$('#processtxt').prepend("<Br>\n" + item.progressString);
				}else{
					if (item.progressValue == 0 && way != "3" && way != 3) {
						//如果没有取到最新值,进度不更新
					} else if(item.progressValue < 100 && (way == "3" || way == 3)){
						$("#processtxt").animate( {
							//height : "300px"
						});
						$('#processtxt').prepend("<Br>\n" + item.progressString);
					}else {
						$('#p').progressbar('setValue', item.progressValue);
						if(way == "1" || way == 1 || way == "2" || way == 2){
							$("#processtxt").animate( {
								//height : "300px"
							});
							$('#processtxt').prepend("<Br>\n" + item.progressString);
						}
					}
				}
			});
		},
		//请求出错的处理
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.clearInterval(timerId);
			//alert("请求出错");
		}
	});
}

function cancel(){
	var navigationBar = $("#navigationBar").val();
	$.messager.confirm("操作提示", "您确定要取消本次发现吗？", function (data) {
		window.location.href = getRootName()+'/monitor/discover/discoverDevice?navigationBar='+navigationBar;
	})
}

function next() {
	var taskID = $("#taskID").val();
	var navigationBar = $("#navigationBar").val();
	window.location.href = getRootName()+'/monitor/discover/getDiscoverResult?taskID=' + taskID+'&navigationBar='+navigationBar;
}