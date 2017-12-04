$(document).ready(function() {
	initTemplateInfo();
});


/**
 * 根据moClassId获取监测器信息
 * @param moClassId
 * @return
 */
function doInitMoInfo(){
	var templateID = $("#ipt_templateID").val();
	var length=$("#monitor").find("tr").length-1;
	if(length>0){
		var trs=length/2+length%2+1;
		for(var i=1;i<trs;i++){
			$("#monitor tr:last").remove();
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/sysMoTemplate/listMoTypeByTemplateID";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
			"templateID":templateID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#monitorView  tr:not(:first)").remove()
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
//				console.log("data = "+data[i]);
				var timeUnitVal = "分";
				var timeUnit = data[i].split(",")[3];
				if(timeUnit == 0){
					 timeUnitVal = "秒";
				}
				if(timeUnit == 1){
					 timeUnitVal = "分";
				}
				if(timeUnit == 2){
					 timeUnitVal = "时";		
				}
				if(timeUnit == 3){
					 timeUnitVal = "天";
				}
				html+="<td><b class='title'><label id='lbl_mo"+data[i].split(",")[0]+"'>"+data[i].split(",")[1]+"：</label></b>" 
				+"<label class='inputs' id='lbl_doIntervals"+data[i].split(",")[0]+"'>"+data[i].split(",")[2]+"</label>&nbsp;"
				+"<label class='inputs' id='lbl_timeUnit"+data[i].split(",")[0]+"'>"+timeUnitVal+"</label></td>";
				
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitorView').append(html);
			
		}
	};	
	ajax_(ajax_param);	

}



function initTemplateInfo(){
	var templateID = $("#ipt_templateID").val();
//	console.log(templateID);
	var uri=getRootPatch()+"/monitor/sysMoTemplate/getMoTemplateInfoByID";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"templateID":templateID,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data != null){
			$("#lbl_templateName").text(data.templateName);
			$("#lbl_descr").text(data.descr);
			$("#lbl_moClassId").text(data.moClassLable);
			doInitMoInfo();
		}
	}
	};
	ajax_(ajax_param);

}

