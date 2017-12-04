var path = getRootName();
$(document).ready(function() {
	initBaseInfo();
});


//初始化数据
function initBaseInfo(){
	var mid = $("#ipt_mid").val();
	var uri=path+"/monitor/sysMo/getMoBaseInfoByMid";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"mid":mid,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data != null){
			iterObj(data, "lbl");
			if(data.monitorProperty == 0){
				$("#lbl_moProperty").text("设备");
			}else{
				$("#lbl_moProperty").text("其它");
			}
			if(data.timeUnit == 1){
				$("#lbl_timeUnit").text("分");
			}else if(data.timeUnit == 2){
				$("#lbl_timeUnit").text("时");
			}else{
				$("#lbl_timeUnit").text("天");
			}
		}
	}
	};
	ajax_(ajax_param);
}

