$(document).ready(function() {
	var oldCollectorId = $("#ipt_oldCollectorId").val();
	var serverName = $("#serverName").val();
//	console.log(oldCollectorId +","+serverName);
	//初始化采集机
	$("#ipt_serverId").val(serverName);
	$("#ipt_serverId").attr("alt",oldCollectorId);
	if(serverName != null && serverName != ""){
		$("#btnUnChose").show();
	}
});


/**
 * 选择所属采集机
 */
function choseHostTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/perfTask/findHostLst";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"trmnlBrandNm" : "",
				"qyType" : "brandName",
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
//				console.log(data)
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择所属采集机", "");

				// 得到树的json数据源
				var datas = eval('(' + data.hostListJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].serverid;
					var _nameTemp = gtmdlToolList[i].ipaddress;
					var _parent = gtmdlToolList[i].parentId;
					
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenHostTreeSetValEasyUi('divHost','ipt_serverId','"
							+ _id + "','" + _nameTemp + "');");
				}
				$('#dataHostTreeDiv').empty();
				$('#dataHostTreeDiv').append(dataTreeOrg + "");
				$('#divHost').dialog('open');
			}
		}
		ajax_(ajax_param);
}

function hiddenHostTreeSetValEasyUi(divId, controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	$("#btnUnChose").show();
}

/**
 * 取消选择的所属采集机
 * @return
 */
function cancelChose(){
	$("#ipt_serverId").val("");
	$("#ipt_serverId").attr("alt", -1);
	$("#btnUnChose").hide();
}

function appointCollector(){
	var taskId = $("#taskId").val();
	var serverId = $("#ipt_serverId").attr("alt");
	if(serverId == null || serverId == "" || serverId == ''){
		serverId = -1;
	}
	var oldCollectorId = $("#ipt_oldCollectorId").val();
//	console.log("oldCollectorId="+oldCollectorId);
//	console.log("serverId="+serverId);
	if(oldCollectorId != serverId){
		var moClassId = $("#moClassId").val();
		if(moClassId != 91 && moClassId != 92 && moClassId != 93 && moClassId !=94){
			isInScope();
		}else{
			doAppointCollector();
		}
	}else{
		$.messager.alert("提示", "指定的采集机与原采集机相同！", "info");
	}
	
}

/**
 * 判断所属的采集机的管理域范围
 */
function isInScope(){
	var deviceIp = $("#deviceIp").val();
	var serverId = $("#ipt_serverId").attr("alt");
//	console.log("serverId=="+serverId);
	if(serverId != null && serverId != "" && serverId != ''){
		var path = getRootName();
		var uri = path + "/monitor/perfTask/isInScope";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"deviceIp":deviceIp,
			"collectorId":serverId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "找不到采集机！", "info");
			} else {
				doAppointCollector();
			}
		}
		}
		ajax_(ajax_param);
	}
}

/**
 * 指定采集机
 * @param taskId
 * @return
 */
function doAppointCollector(){
	var taskId = $("#taskId").val();
	var serverId = $("#ipt_serverId").attr("alt");
	if(serverId == null || serverId == "" || serverId == ''){
		serverId = -1;
	}
	var oldCollectorId = $("#ipt_oldCollectorId").val();
//	console.log("oldCollectorId="+oldCollectorId);
//	console.log("serverId="+serverId);
	if(oldCollectorId != serverId){
		var path = getRootName();
		var uri = path + "/monitor/perfTask/appointCollector";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"progressStatus":1,
			"operateStatus" : 6,
			"collectorId" : serverId,
			"oldCollectorId" : oldCollectorId,
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "指定采集机成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "指定采集机失败！", "error");
			}
		}
		}
		ajax_(ajax_param);
	}else{
		$.messager.alert("提示", "指定的采集机与原采集机相同！", "info");
	}
}

