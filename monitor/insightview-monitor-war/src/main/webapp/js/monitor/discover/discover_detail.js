var path = getRootName();
$(document).ready(function() {
	initTaskDetail();
});

/*
 * 初始化阈值规则定义信息
 */
function initTaskDetail(){
	var taskId=$('#taskId').val();
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
