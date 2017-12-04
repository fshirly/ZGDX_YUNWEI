var path = getRootName();
var ruleID=$('#ruleID').val();
$(document).ready(function() {
	initThresholdDetail();
});

/*
 * 初始化阈值规则定义信息
 */
function initThresholdDetail(){
	var path=getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/initThresholdDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"ruleID":ruleID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
				var showId = data.classID;
//				console.log("showId==="+showId);
				if(showId<1){
					$("#isShow").hide(); 
				}else if(showId==22 || showId==26 || showId==28 || showId==14 || showId==15 ||showId==16 ||showId==54 ||showId==55 ||showId==17 || showId==18 || showId==19 || showId==20  || showId==53 || showId==44 || showId==45 || showId==90  || showId==91 || showId==92 || showId==93 ){
					$("#isShow").hide(); 
				}else{
					$("#isShow").show(); 
				}
			}
		};
	ajax_(ajax_param);
}
