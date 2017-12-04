$(document).ready(function() {
	var snmpVersion = $("#ipt_snmpVersion").val();
	var readCommunity = $("#ipt_readCommunity").val();
	var snmpPort = $("#ipt_snmpPort").val();
	if (snmpPort == 0) {
		$("#ipt_snmpPort").val('161');
	}
	if (readCommunity == '') {
		$("#ipt_readCommunity").val('public');
	}
	snmpPort = $("#ipt_snmpPort").val();
	readCommunity = $("#ipt_readCommunity").val();
	$("#ipt_readCommunity").val(readCommunity);
	$("#ipt_snmpPort").val(snmpPort);
	
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
 * 测试
 * @return
 */
function getTestCondition(){
		var path = getRootName();
		var uri = path + "/monitor/stoneu/test";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				async:false,
				data:{
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
					if (data>0) {
						$("#sucessTip").show();
						$("#errorTip").hide();
						$("#temp").show();
						$("#manufacturerID").val(data);
						initSelect();
						doInitMoList();
					} else {
						$("#sucessTip").hide();
						$("#errorTip").show();
						$("#temp").hide();
					}

				}
			};
		ajax_(ajax_param);
}




function insertCondition(){
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
				if(snmpVersion==0 || snmpVersion==1){
					checkModevice();
				}else{
				// 新建监测
				insertData();
				}
			}
		}
}

function insertData(){
	var path = getRootName();
	var neManufacturerID=$("#manufacturerID").val();
	var uri = path + "/monitor/stoneu/doAdd";
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
	if(neManufacturerID !=null && neManufacturerID !=""){
		if(arrChk.length==0){
		$.messager.alert("提示","至少选择一个监测器！","error");
	}else{
		for ( var int = 0; int < arrChk.length; int++) {
			molist+=arrChk[int]+",";
			mointerval+=$("#ipt_doIntervals"+arrChk[int]).val()+",";
			var select=$("#ipt_timeUnit"+arrChk[int]).val();
			selectList.push(select);
		}
		for ( var int = 0; int < selectList.length; int++) {
			motimeunit+=selectList[int]+",";
		}
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data:{
				"moTypeLstJson":moTypeLstJson,
				"templateID" : templateID,
				"moList" : molist,
				"MOClassID":$("#moclassID").val(),
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				"deviceIp":$("#ipt_deviceIP").val(),
				"neManufacturerID":neManufacturerID,
				 "SNMPbean.snmpVersions":$("#ipt_snmpVersion").val(),
				 "SNMPbean.deviceIP":$("#ipt_deviceIP").val(),
				 "SNMPbean.snmpPort":$("#ipt_snmpPort").val(),
				 "SNMPbean.readCommunity" :$("#ipt_readCommunity").val(),
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
	}
	}else{
		$.messager.alert("提示","请先测试其是否连通，否则数据无效！","error");
	}
	 
}


/**
 * 获取监测器
 * @param moid
 */
function doInitMoList(){
	var neManufacturerID = $("#manufacturerID").val();
	var templateID=$("#ipt_templateID").combobox('getValue');
	if(templateID == null || templateID == ""){
		templateID = -1;
		$("#ipt_templateID").combobox('setValue', templateID);
	}
	$("#monitor  tr:not(:first)").remove();
	//没有套用模板
	if((templateID == -1 || templateID == "-1")&& null !=neManufacturerID && neManufacturerID !=''){
		var path=getRootName();
		var uri=path+"/monitor/perfTask/listMoListForManufacturerID";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			async : false,
			data:{
				"manufacturerID":neManufacturerID,
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
					//设置单位
					var timeUnit =data[i].split(",")[3];
					$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value='"+data[i].split(",")[3]+"'] ").attr("selected",true);
					
					//如果监测器没有没有默认周期，则采用对象类型的周期
					if(data[i].split(",")[2] == -1 || data[i].split(",")[2] == "-1"){
						//下拉框默认展示采集周期默认值
						var collectPeriod = $("#collectPeriod").val();
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


function initSelect(){
var moClassID = $("#moclassID").val();
	// 展示模板
	$('#ipt_templateID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId='+moClassID,
		valueField : 'templateID',
		textField : 'templateName',
		editable : false,
		onSelect:function(record){  
			doInitMoList();
		}
	});
}


/*
 * 验证SNMP验证中该设备是否存在
 */
function checkModevice() {
	var deviceIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/monitor/stoneu/toCheck";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
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
				insertData();
				return;
			}
		}
	};
	ajax_(ajax_param);
}
