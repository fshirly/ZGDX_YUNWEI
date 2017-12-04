$(document).ready(function() {
	$("#testDiv").show();
	var moid = $("#moid").val();
	var hiddenTemplateID = $("#hiddenTemplateID").val();
	var MOclassID = $("#ipt_moClassID").val();
	var taskID = $("#taskId").val();
	if(null ==taskID || taskID=='0' ){
		taskID = -1;
	}
	var className="";
	if(MOclassID==96){
		className = "空调监控";
	}
	if(MOclassID==73){
		className = "UPS监控";
	}
	$("#ipt_moClassName").val(className);
	var snmpVersion = $("#ipt_snmpVersion").val();
	var snmpVersionName="";
	if(snmpVersion=="0"){
		snmpVersionName ="V1";
	}else if(snmpVersion=="1"){
		snmpVersionName ="V2";
	}else if(snmpVersion=="3"){
		snmpVersionName ="V3";
	}
	$("#ipt_snmpVersionName").val(snmpVersionName);
	isOrnoCheck();
	// 展示模板
	$('#ipt_templateID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId='+MOclassID,
		valueField : 'templateID',
		textField : 'templateName',
		editable : false,
		onSelect:function(record){  
			doInitMoList(moid,taskID);
		},
		onLoadSuccess:function(){
			$("#ipt_templateID").combobox('setValue', hiddenTemplateID);
			doInitMoList(moid,taskID);
		}
	});
	//doInitMoList(moid,taskID);//之前是放到onLoadSuccess，因为可能ipt_templateID在没渲染玩就去取导致取不到。
});

/*
 * SNMP凭证中，根据协议版本选择展示表单
 */
function isOrnoCheck() {
	if ($("#ipt_snmpVersion").val() == "0" || $("#ipt_snmpVersion").val() == "1") {
		$("#usmUser").hide();
		$("#authAlogrithm").hide();
		$("#encryptionAlogrithm").hide();
		$("#readCommunity").show();
	} else {
		$("#readCommunity").hide();
		$("#usmUser").show();
		$("#authAlogrithm").show();
		$("#encryptionAlogrithm").show();
		
	}
}
 

function checkKey(){
	var authKey= $("#ipt_authKey").val();
	var encryptionAlogrithm = $("#ipt_encryptionAlogrithm").val();
	if(authKey!="" && authKey!=null && encryptionAlogrithm !="-1" && encryptionAlogrithm !=-1){
		return true;
	}else{
		if(authKey=="" || authKey==null){
			$.messager.alert('提示', '请输入认证KEY!', 'info');
		}
		if(encryptionAlogrithm=="-1" || encryptionAlogrithm==-1){
			$.messager.alert('提示', '请选择加密算法!', 'info');
		}
		return false;
	}
}


/**
 * 空调测试
 * @return
 */
var objectData="";
function getTestCondition(){
		var path = getRootName();
		var moClassID = $("#ipt_moClassID").val();
		var uri = path + "/monitor/acUPS/testCondition";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				async:false,
				data:{
					"MOClassID":moClassID,
					"address":$("#ipt_deviceIP").val(),
			 	   "snmpVersion" :$("#ipt_snmpVersion").val(),
			 	   "port":$("#ipt_snmpPort").val(),
			 	   "community":$("#ipt_readCommunity").val(),
			 	   "securityName":$("#ipt_usmUser").val(),
			 	   "authProtocol":$("#ipt_authAlogrithm").val(),
			 	   "authPassphrase":$("#ipt_authKey").val(),
			 	   "privProtocol":$("#ipt_encryptionAlogrithm").val(),
			 	   "privPassphrase":$("#ipt_encryptionKey").val(),
			 	   "contextName":$("#ipt_contexName").val()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data.flag) {
						objectData = data.result;
						$("#sucessTip").show();
						$("#errorTip").hide();
					} else {
						$("#sucessTip").hide();
						$("#errorTip").show();
					}

				}
			};
		ajax_(ajax_param);
		return objectData;
}



/**
 * 获取监测器
 * @param moid
 */
function doInitMoList(moid,taskID){
	var templateID=$("#ipt_templateID").combobox('getValue');
	if(templateID == null || templateID == ""){
		templateID = -1;
		$("#ipt_templateID").combobox('setValue', templateID);
	}
	$("#monitor  tr:not(:first)").remove();
	//没有套用模板
	if(templateID == -1 || templateID == "-1"){
		var path=getRootName();
		var uri=path+"/monitor/perfTask/listMoList";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			async : false,
			data:{
				"moId":moid,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data.length>0){
					$("#monitorTitle").show();
					$("#tblChooseTemplate").show();
				}else{
					$("#monitorTitle").hide();
					$("#tblChooseTemplate").hide();
				}
				var html='';
				var trHTML1 = "<tr>"
				var trHTML2 = "</tr>"
				for (var i=0;i<data.length;i++){ 
					html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
					+"<td><input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='"+data[i].split(",")[2]+"' style='width:60px;' validator='{\"default\":\"checkEmpty_ptInteger\"}'/></td>"
					+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" +
					"<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
					"</select></td>";
					if((i+1)%2 != 0){
						html=trHTML1+html;
					}else{
						html=html+trHTML2;
					}
				}
				
				$('#monitor').append(html);
				for (var i=0;i<data.length;i++){
//					console.log(data[i].split(",")[0]+"--"+data[i].split(",")[1]+"--"+data[i].split(",")[2]+"--"+data[i].split(",")[3])
					//设置单位
					var timeUnit =data[i].split(",")[3];
					$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value='"+data[i].split(",")[3]+"'] ").attr("selected",true);
					
					//如果监测器没有没有默认周期，则采用对象类型的周期
					if(data[i].split(",")[2] == -1 || data[i].split(",")[2] == "-1"){
						//下拉框默认展示采集周期默认值
						var collectPeriod = $("#collectPeriod").val();
//						console.log("collectPeriod="+collectPeriod);
						if(collectPeriod != -1){
							$("#ipt_doIntervals"+data[i].split(",")[0]).val(collectPeriod);
						}else{
							$("#ipt_doIntervals"+data[i].split(",")[0]).val(20);
						}
//						$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value=1] ").attr("selected",true);
						$("#ipt_timeUnit"+data[i].split(",")[0]).val(1);
					}
				}
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					checkboxs[j].checked=false;
				}
				toCheckMo(taskID);
			}
		};	
		ajax_(ajax_param);	
	}else{
		getMOTypeList(templateID);
	}
}


function getMOTypeList(templateID){
	//使用模板
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/listMoListByTemplete?templateID="+templateID;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			async : false,
			data:{
		"t" : Math.random()
	},
	error : function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		var moTypeLstJson = JSON.stringify(data);
		$("#moTypeLstJson").val(moTypeLstJson);
		if(data.length>0){
			$("#monitorTitle").show();
			$("#tblChooseTemplate").show();
		}else{
			$("#monitorTitle").hide();
			$("#tblChooseTemplate").hide();
		}
		var html='';
		var trHTML1 = "<tr>"
		var trHTML2 = "</tr>"
		for (var i=0;i<data.length;i++){ 
			var timeUnit =data[i].split(",")[3];
			var timeUnitVal = "分";
			if(timeUnit == 1){
				timeUnitVal = "分";
			}else if(timeUnit == 2){
				timeUnitVal = "时";
			}else if(timeUnit == 3){
				timeUnitVal = "天";
			}
			html+="<td><input type='checkbox' disabled id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
			+"<td>"+data[i].split(",")[2]+"&nbsp;&nbsp;&nbsp;&nbsp;<select class='inputs' id='ipt_doIntervals"+data[i].split(",")[3]+"' name='sel"+data[i].split(",")[3]+"' style='width:60px' disabled>" +
			"<option value='"+data[i].split(",")[3]+"'>"+timeUnitVal+"</option>"+
			"</select>&nbsp;</td>";
			if((i+1)%2 != 0){
				html=trHTML1+html;
			}else{
				html=html+trHTML2;
			}
		}
		$("#monitor  tr:not(:first)").remove();
		$('#monitor').append(html);
		
	}
	};	
	ajax_(ajax_param);
}



function updateAcdition(){
	var snmpVersion = $("#ipt_snmpVersion").val();
	var checkFormRS = true;
	var checkKeyRS = true;
		if(snmpVersion==0 || snmpVersion==1){
			checkFormRS =checkInfo('#acInfo')&& checkInfo('#readCommunity')&& checkInfo('#snmpPort');
		}else if(snmpVersion == 3){
			checkFormRS =checkInfo('#acInfo')&& checkInfo('#usmUser')&& checkInfo('#snmpPort');
			var authAlogrithm= $("#ipt_authAlogrithm").val();
			if(authAlogrithm != -1){
				checkKeyRS = checkKey();
			}
		}
		
		if (checkFormRS == true) {
			if (checkKeyRS == true) {
				// 新建空调监测
				if(snmpVersion==0 || snmpVersion==1){
					checkSnmpCommunity();
				}else{
					updateACData();
				}
			}
		}
}

function updateACData(){
	var path = getRootName();
	var moName=$("#moName").val();
	var neManufacturerID=$("#neManufacturerID").val();
	var domainID=$("#domainID").val();
	if(moName !="" && neManufacturerID !="" && domainID !=""){
		if(objectData !=null && objectData !=""){
			moName = objectData.moName;
			neManufacturerID=objectData.neManufacturerID;
			domainID = objectData.domainID;
		}
	}
	var moClassID = $("#ipt_moClassID").val();
	 
	var moTypeLstJson =$("#moTypeLstJson").val();
	var templateID=$("#ipt_templateID").combobox('getValue');
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	var motimeunit='';
	$('input:checked[name=moType]').each(function() { 
		arrChk.push($(this).val());
	});
	if(arrChk.length==0){
		$.messager.alert("提示","至少选择一个监测器！","error");
	}else{
		var hidIP = $("#hiddenIp").val();
		var deviceIP = $("#ipt_deviceIP").val();
		var hidreadComm = $("#readComm").val();
		var hidwriteComm= $("#writeComm").val();
		var hidport =$("#port").val();
	if(moName !="" && neManufacturerID !="" && domainID !="" && deviceIP == hidIP 
			&& hidreadComm == $("#ipt_readCommunity").val() 
			&& hidwriteComm == $("#ipt_writeCommunity").val()
			&& hidport==$("#ipt_snmpPort").val()){
		for ( var int = 0; int < arrChk.length; int++) {
			molist+=arrChk[int]+",";
			mointerval+=$("#ipt_doIntervals"+arrChk[int]).val()+",";
			var select=$("#ipt_timeUnit"+arrChk[int]).val();
			selectList.push(select);
		}
		for ( var int = 0; int < selectList.length; int++) {
			motimeunit+=selectList[int]+",";
		}
	var uri = path + "/monitor/acUPS/updateCondition";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data:{
				"moTypeLstJson":moTypeLstJson,
				"templateID" : templateID,
				"moList" : molist,
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				"deviceIP":$("#ipt_deviceIP").val(),
				"mOAlias":$("#ipt_alias").val(),
				"domainID":domainID,
				"moID":$("#moid").val(),
				"moName":moName,
				"moClassID":parseInt(moClassID),
				"neManufacturerID" :neManufacturerID,
				"lastUpdateTime":new Date().format("yyyy-MM-dd hh:mm:ss"),
				 "SNMPbean.snmpVersions":$("#ipt_snmpVersion").val(),
				 "SNMPbean.deviceIP":$("#ipt_deviceIP").val(),
				 "SNMPbean.snmpPort":$("#ipt_snmpPort").val(),
				 "SNMPbean.readCommunity" :$("#ipt_readCommunity").val(),
				"SNMPbean.writeCommunity" : $("#ipt_writeCommunity").val(),
				"SNMPbean.USMUser" :$("#ipt_usmUser").val(),
				"SNMPbean.authAlogrithm" :$("#ipt_authAlogrithm").val(),
				"SNMPbean.authKey" : $("#ipt_authKey").val(),
				"SNMPbean.encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").val(),
				"SNMPbean.encryptionKey" : $("#ipt_encryptionKey").val(),
				"SNMPbean.contexName" : $("#ipt_contexName").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data) {
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					 
				}

			}
		};
	ajax_(ajax_param);
	}else{
		$.messager.alert("提示","请先测试其是否连通，否则数据无效！","error");
	}
  }
	
}
Date.prototype.format = function(format){
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var i in o) {
        var reg = new RegExp("(" + i + ")");
        if (reg.test(format)) {
            format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[i]).substr(("" + o[i]).length)));
        }
    }
    return format;
}


/*
 * 验证SNMP验证中该设备是否存在
 */
function checkSnmpCommunity() {
	var moid = $("#moid").val();
	var deviceIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/monitor/acUPS/checkSNMPCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moID":moid,
			"deviceIP" : deviceIP,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data) {
				$.messager.alert("提示", "该设备已存在！", "error");
			} else {
				updateACData();
				return;
			}
		}
	};
	ajax_(ajax_param);
}


/**
 * 默认勾选某条任务包含的监测器
 * @param taskId
 * @return
 */
function toCheckMo(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoListByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			for (var i=0;i<data.length;i++){ 
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					var mid=data[i].split(",")[0];
					var interval=data[i].split(",")[1];
					var timeUnit =1;
					var doIntervals = interval;
					if(interval >=86400){
						doIntervals = interval /60/60/24;
						timeUnit = 3;
					}else if(interval >= 3600){
						doIntervals = interval /60/60;
						timeUnit = 2;
					}else{
						doIntervals = interval /60;
						timeUnit = 1;
					}
					 if(checkboxs[j].value==mid)
				     { 
						 checkboxs[j].checked=true;
						 document.getElementsByName('sel'+mid)[0].value=doIntervals;
						 $("#ipt_timeUnit"+mid).val(timeUnit);
						 $("#ipt_doIntervals"+mid).val(doIntervals);
				     }
				}
				}
		}
	};	
	ajax_(ajax_param);	
}

