var path = getRootName();
$(document).ready(function() {
	initTaskDetail();
});

/*
 * 初始化阈值规则定义信息
 */
function initTaskDetail(){
	var taskId=$('#taskId').val();
//	console.log("taskID===="+taskId);
	var path=getRootName();
	var uri=path+"/monitor/discoverTask/initTaskDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"taskid":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
				if(data.lastopresult ==0){
					$("#lbl_lastopresult").text("成功");
				}else{
					$("#lbl_lastopresult").text("失败");
				}
			}
		};
	ajax_(ajax_param);
}

function closeWin(){
	var flag = $("#flag").val();
//	console.log("flag==="+flag)
	if(flag == "popWin"){
		$('#popWin').window('close');
	}else{
		$('#popWin2').window('close');
	}
}