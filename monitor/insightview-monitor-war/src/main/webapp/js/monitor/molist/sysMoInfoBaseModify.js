var path = getRootName();
var moClassTemp = "";
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
			moClassTemp = data.moClass;
			$("#ipt_moName").val(data.moName);
			$("#ipt_moClass").val(data.moClass.substring(3));
			$("#ipt_monitorTypeName").combobox('setValue',data.monitorTypeID);
			$("#ipt_moProperty").combobox('setValue',data.monitorProperty);
			$("#monitorProperty").val(data.monitorProperty);
			$("#ipt_doIntervals").val(data.doIntervals);
			$("#unit  option[value='"+data.timeUnit+"'] ").attr("selected",true);
			$("#ipt_moDescr").val(data.moDescr);
			if(data.monitorProperty == 0){
				$("#deviceConfigRangeDiv").show();
				$("#othersConfigRangeDiv").hide();
			}else{
				$("#deviceConfigRangeDiv").hide();
				$("#othersConfigRangeDiv").show();
			}
		}
	}
	};
	ajax_(ajax_param);
}




function getMap() {// 初始化map_,给map_对象增加方法，使map_像Map
    var map_ = new Object();     
    map_.put = function(key, value) {     
        map_[key+'_'] = value;     
    };     
    map_.get = function(key) {     
        return map_[key+'_'];     
    };     
    map_.remove = function(key) {     
        delete map_[key+'_'];     
    };     
    map_.keyset = function() {     
        var ret = "";     
        for(var p in map_) {     
            if(typeof p == 'string' && p.substring(p.length-1) == "_") {     
                ret += ",";     
                ret += p.substring(0,p.length-1);     
            }     
        }     
        if(ret == "") {     
            return ret.split(",");     
        } else {     
            return ret.substring(1).split(",");     
        }     
    };     
    return map_;     
}  


function toUpdateBaseInfo(){
	var result = checkInfo('#tblUpdateSysMoInfo');
	if (result == true) {
		var mid = $("#ipt_mid").val();
		var moName = $("#ipt_moName").val();
		var moClass = $("#ipt_moClassPre").val()+$("#ipt_moClass").val();
		var monitorTypeID = $("#ipt_monitorTypeName").combobox('getValue');
		var monitorProperty = $("#ipt_moProperty").combobox('getValue');
		var doIntervals = $("#ipt_doIntervals").val();
		var unitValue = $("#unit option:selected").val();
		var moDescr = $("#ipt_moDescr").val();
		if (moClass != moClassTemp) {
//			if (moClass.indexOf("Job") != 0  || moClass.charCodeAt(0) != "J".charCodeAt(0)) {
//				$.messager.alert("提示","监测器调度类名格式错误!以Job开头，例如:JobSnmpCpu","info");
//			} else {
				var uri=path+"/monitor/sysMo/checkMoClass";
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"json",
						data:{
							"moClass":moClass,
							"t" : Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data > 0){
						$.messager.alert("提示","监测器调度类名重复！","info");
					}else{
						console.log("moClass = "+moClass);
						var uri=path+"/monitor/sysMo/updateMoInfo";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"mid":mid,
									"moName":moName,
									"moClass":moClass,
									"monitorTypeID":monitorTypeID,
									"monitorProperty":monitorProperty,
									"doIntervals":doIntervals,
									"moDescr":moDescr,
									"timeUnit":unitValue,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data == true){
								$.messager.alert("提示","修改监测器成功！","info");
								parent.$('#popWin').window('close');
								parent.window.frames['component_2'].window.reloadTable();
							}else{
								$.messager.alert("提示","修改监测器失败！","error");
							}
						}
						};
						ajax_(ajax_param);
					}
				}
				};
				ajax_(ajax_param);
//			}
		}else{

			var uri=path+"/monitor/sysMo/updateMoInfo";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"mid":mid,
						"moName":moName,
						"moClass":moClass,
						"monitorTypeID":monitorTypeID,
						"monitorProperty":monitorProperty,
						"doIntervals":doIntervals,
						"moDescr":moDescr,
						"timeUnit":unitValue,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				console.info(window.location.href);
				console.info(parent.window.location.href);
				console.info(parent.window.frames.length);
				console.info(parent.window.frames[0].window.location.href);
				console.info(parent.window.frames[1].window.reloadTable);
				console.info(parent.window.frames[2].window.location.href);
				console.info(parent.window.frames[3].window.location.href);
				
				
				if(data == true){
					$.messager.alert("提示","修改监测器成功！","info");
					parent.$('#popWin').window('close');
					parent.window.frames['component_2'].window.reloadTable();
				}else{
					$.messager.alert("提示","修改监测器失败！","error");
				}
			}
			};
			ajax_(ajax_param);
		
		}
	}

}

//时间换算
function timeFun(doIntervals,unitValue){
	if (unitValue == 0) {
		doIntervals = doIntervals;
	} else if (unitValue == 1) {
		doIntervals = doIntervals * 60;
	} else if (unitValue == 2) {
		doIntervals = doIntervals * 60 * 60;
	} else {
		doIntervals = doIntervals * 24 * 60 * 60;
	}   
	return doIntervals;
}