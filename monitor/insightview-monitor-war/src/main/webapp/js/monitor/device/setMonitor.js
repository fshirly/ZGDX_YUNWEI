$(document).ready(function() {
	var moClassId = $("#moClassId").val();
	var className = $("#className").val();
	var nemanufacturername = $("#nemanufacturername").val();
//	console.log("moClassId=="+moClassId+",  className="+className+",  nemanufacturername=="+nemanufacturername);
	$("#ipt_mobjectClassID").val(className);
	$("#ipt_mobjectClassID").attr("alt", moClassId);
	$("#ipt_moClassID").val(className);
	$("#ipt_moClassID").attr("alt", moClassId);
	
    $('#deviceSetting').tabs({
        border: false,
		tabPosition:'left',
    });

	if(moClassId == -1 || nemanufacturername == "未知厂商"){//如果是未知的对象类型，则只展示对象配置
//		$("#mobjectSetting").show();
//		$("#divCommunity").hide();
//		$("#divThresholdSetting").hide();
//		$("#monitorSetting").show();
		$('#deviceSetting').tabs('enableTab', 0); 
		$('#deviceSetting').tabs('disableTab', 1); 
		$('#deviceSetting').tabs('disableTab', 2); 
		$('#deviceSetting').tabs('enableTab', 3); 
	}else{
		if(moClassId!=15&&moClassId!=16&&moClassId!=54&&moClassId!=81&&moClassId!=86&&moClassId!=19&&moClassId!=20&&moClassId!=53&&moClassId!=44&&(nemanufacturername=="未知类型"||nemanufacturername==""||nemanufacturername==null||nemanufacturername=="null")){
//			$("#mobjectSetting").show();
//			$("#divCommunity").show();
//			$("#divThresholdSetting").hide();
//			$("#monitorSetting").hide();
			
			$('#deviceSetting').tabs('enableTab', 0); 
			$('#deviceSetting').tabs('enableTab', 1); 
			$('#deviceSetting').tabs('disableTab', 2); 
			$('#deviceSetting').tabs('disableTab', 3); 
		}else{
//			$("#mobjectSetting").show();
//			$("#divCommunity").show();
//			$("#divThresholdSetting").show();
//			$("#monitorSetting").show();
//			
			$('#deviceSetting').tabs('enableTab', 0); 
			$('#deviceSetting').tabs('enableTab', 1); 
			$('#deviceSetting').tabs('enableTab', 2); 
			$('#deviceSetting').tabs('enableTab', 3);
		}
	}
	//如果为虚拟机，监测器配置不展示
	if(moClassId == 9){
//		$("#monitorSetting").hide();
		$('#deviceSetting').tabs('disableTab', 3);
	}
	
	//如果为vCenter，阈值配置不展示
	if(moClassId == 75){
//		$("#divThresholdSetting").hide();
		$('#deviceSetting').tabs('disableTab', 2); 
	}
	
	//判断是否展示端口
	var moClassId = $("#moClassId").val();
	isShowPort(moClassId);
	var port = $("#port").val();
	$("#ipt_serverPort").val(port);
	
	if(moClassId == 15 || moClassId ==16 || moClassId ==54 || moClassId ==81 || moClassId ==86){
		$("#authTypeDefault").val("dbms");
		$("#treeTypeDefault").val("dbms");
	}else if(moClassId == 8 || moClassId == 75){
		$("#authTypeDefault").val("Vmware");
		$("#treeTypeDefault").val("Vmware");
		initVmwareVal();
	}else if(moClassId == 19 || moClassId == 20 || moClassId ==53){
		$("#authTypeDefault").val("middle");
		$("#treeTypeDefault").val("middle");
	}else if(moClassId == 44){
		$("#authTypeDefault").val("room");
		$("#treeTypeDefault").val("room");
		$("#isMoAlias").hide();
	}else if(moClassId == -1){
		$("#authTypeDefault").val("unKnown");
		$("#treeTypeDefault").val("unKnown");
	}else{
		$("#authTypeDefault").val("SNMP");
		$("#treeTypeDefault").val("SNMP");
	}
	//默认绑定模板
	var templateID = $("#templateID").val();
//	console.log("templateID=="+templateID);
	$("#ipt_templateID").val(templateID);
	chooseTemplate();
	
	if(moClassId != -1){
		getCollectPeriodVal(moClassId);
//	initInfo();
		doInitThresholdTable();
	}
	
   
});

//选择模板
function chooseTemplate(){
	var moClassId = $("#ipt_mobjectClassID").attr("alt");
	if(moClassId == 15 || moClassId ==16 || moClassId ==54 || moClassId ==81 || moClassId ==86 || moClassId == 19 || moClassId == 20 || moClassId ==53 || moClassId ==44 ){
		doInitDBMoList();
	}else{
		doInitMoList();
	}
}
//SNMP凭证
function isOrnoCheckSnmp() {
	if ($("#ipt_snmpVersion").val() == "0"
			|| $("#ipt_snmpVersion").val() == "1") {
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



/**
 * 初始化默认展示信息
 * @return
 */
function initInfo(){
	//初始化凭证信息
	initCommunity();
	//初始化阈值配置
	initThreshold();
}

function initThreshold(){
	var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
	var moClassId = $("#ipt_moClassID").attr("alt");
	if(sourceClassId == 15 || sourceClassId == 16 || sourceClassId == 54 || sourceClassId == 81 || sourceClassId == 86 || sourceClassId == 44){
		$("#chooseSource").show();
		$("#ipt_sourceMOName").val("");
	}else{
		$("#chooseSource").hide();
		var deviceip = $("#deviceip").val();
		$("#ipt_sourceMOName").val(deviceip);
	}
	if(sourceClassId==moClassId){
		$("#isShowMO1").hide();
		$("#isShowMO2").hide();
	}else{
		$("#isShowMO1").show();
		$("#isShowMO2").show();
	}
}

function initCommunity(){
	var showId = $("#moClassId").val();
	var className = $("#className").val();
//	console.log("初始化凭证===="+showId);
	if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").show();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("dbms");
		if(showId == 15 ){
			$("#ipt_dbmsType").val(className);
			$("#ipt_dbPort").val("1521");
			initDBVal();
			var docDBName = document.getElementById('ipt_dbName');
			docDBName.readOnly = false;
		}else if(showId == 16 ){
			$("#ipt_dbmsType").val(className);
			$("#ipt_dbPort").val("3306");
			initDBVal();
			var docDBName = document.getElementById('ipt_dbName');
			docDBName.readOnly = false;
		}else if(showId == 54 ){
			$("#ipt_dbmsType").val(className);
			$("#ipt_dbPort").val("50000");
			initDB2Val();
			var docDBName = document.getElementById('ipt_dbName');
			docDBName.readOnly = true;
		}else if(showId == 81 ){
			$("#ipt_dbmsType").val(className);
			$("#ipt_dbPort").val("5000");
			initDBVal();
			var docDBName = document.getElementById('ipt_dbName');
			docDBName.readOnly = false;
		}else if(showId == 86 ){
			$("#ipt_dbmsType").val(className);
			$("#ipt_dbPort").val("1433");
			initDBVal();
			var docDBName = document.getElementById('ipt_dbName');
			docDBName.readOnly = false;
		}
		
		$("#isShowButton").show();
	}else if(showId == 8 || showId == 75){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").show();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("Vmware");
		$("#ipt_VMport").val('443');
		initVmwareVal();
		$("#isShowButton").show();
	}else if(showId == 19 || showId == 20 || showId ==53){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").show();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("middle");
		initMiddleWareVal();
		$("#isShowButton").show();
	}else if(showId == 44){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").show();
		$("#authType").val("room");
		$("#ipt_VMport").val('6580');
		initRoomDetail();
		$("#isShowButton").show();
	}else{
		$("#divAddSNMPCommunity").show();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("SNMP");
		var snmpPort = $("#ipt_snmpPort").val();
		var readCommunity = $("#ipt_readCommunity").val();
		var writeCommunity = $("#ipt_writeCommunity").val();
		if (snmpPort == 0) {
//			$("#ipt_snmpPort").val('161');
			$("#ipt_snmpPort").val("");
		}
//		if (readCommunity == '') {
//			$("#ipt_readCommunity").val('public');
//		}
//		if (writeCommunity == '') {
//			$("#ipt_writeCommunity").val('public');
//		}
		initSnmpVal();
		$("#isShowButton").hide();
	}
}


/**
 * 初始化SNMP凭证信息
 * @return
 */
function initSnmpVal(){
	var taskId = $("#taskId").val();
	doInitMoList();
	var deviceIp = $("#deviceip").val();
	//console.log("deviceip="+deviceIp);
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/getSNMPByIP?deviceIp="+deviceIp;
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,
			data:{
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if (data.snmpVersion == "0" || data.snmpVersion == "1") {
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
				iterObj(data, "ipt"); 
//				console.log("deviceip==="+deviceip)
				if(data.snmpVersion == "0"){
					$("#ipt_snmpVersion").val("V1");
				}else if(data.snmpVersion == "1"){
					$("#ipt_snmpVersion").val("V2");
				}else if(data.snmpVersion == "3"){
					$("#ipt_snmpVersion").val("V3");
				}
				
				$("#ipt_deviceIP").val(deviceIp);
				
			}
		};
	ajax_(ajax_param);
}


/**
 * 初始化信息（VMware）
 * @param taskId
 * @return
 */
function initVmwareVal(){
	var taskId = $("#taskId").val();
	doInitMoList();
	var deviceip = $("#deviceip").val();
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/initVmwareVal";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"deviceip":deviceip,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
//				console.log(" vmware  deviceip==="+deviceip)
				$("#ipt_deviceIP").val(deviceip);
				$("#ipt_VMport").val(data.port);
				$("#ipt_userName").val(data.userName);
				$("#ipt_password").val(data.password);
//				doInitMoList(taskId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（DBMS）
 * @param taskId
 * @return
 */
function initDBVal(){
	var taskId = $("#taskId").val();
	doInitDBMoList();
	var deviceip = $("#deviceip").val();
	var moClassId = $("#moClassId").val();
	var port = $("#port").val();
	var dbmsType = "Oracle";
	if(moClassId==15){
		dbmsType = "Oracle";
	}else if(moClassId==16){
		dbmsType = "Mysql";
	}else if(moClassId==54){
		dbmsType = "DB2";
	}else if(moClassId==81){
		dbmsType = "Sybase";
	}else if(moClassId==86){
		dbmsType = "MsSql";
	}
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/initDBVal";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"ip":deviceip,
				"dbmsType" : dbmsType,
				"port" : port,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
//				console.log(" db deviceip==="+deviceip)
				$("#ipt_dbDeviceIP").val(deviceip);
				$("#ipt_dbmsType").val(dbmsType);
				$("#ipt_dbPort").val(port);
				if(data != null){
					$("#ipt_dbName").val(data.dbName);
					$("#ipt_dbUserName").val(data.userName);
					$("#ipt_dbPassword").val(data.password);
				}
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（DBMS）
 * @param taskId
 * @return
 */
function initDB2Val(){
	var taskId = $("#taskId").val();
	doInitDBMoList();
	var deviceip = $("#deviceip").val();
	var moClassId = $("#moClassId").val();
	var port = $("#port").val();
	var dbName = $("#dbName").val();
	var dbmsType = "Oracle";
	if(moClassId==15){
		dbmsType = "Oracle";
	}else if(moClassId==16){
		dbmsType = "Mysql";
	}else if(moClassId==54){
		dbmsType = "DB2";
	}else if(moClassId==81){
		dbmsType = "Sybase";
	}else if(moClassId==86){
		dbmsType = "MsSql";
	}
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/initDB2Val?dbName="+dbName;
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"ip":deviceip,
				"dbmsType" : dbmsType,
				"port" : port,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
//				console.log(" db deviceip==="+deviceip)
				$("#ipt_dbDeviceIP").val(deviceip);
				$("#ipt_dbmsType").val(dbmsType);
				$("#ipt_dbPort").val(port);
				if(data != null){
					$("#ipt_dbName").val(data.dbName);
					$("#ipt_dbUserName").val(data.userName);
					$("#ipt_dbPassword").val(data.password);
				}
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（中间件）
 * @param taskId
 * @return
 */
function initMiddleWareVal(){
	var taskId = $("#taskId").val();
	doInitDBMoList();
	var deviceip = $("#deviceip").val();
	var port = $("#port").val();
	var moClassId = $("#moClassId").val();
	var middleWareType ="tomcat";
	if(moClassId==19){
		middleWareType ="websphere";
	}else if(moClassId==20){
		middleWareType ="tomcat";
	}else if(moClassId==53){
		middleWareType ="weblogic";
	}
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/initMiddleWareVal";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"ipAddress":deviceip,
				"middleWareType":middleWareType,
				"port" :port,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
//				console.log("middle deviceip==="+deviceip)
				$("#ipt_middleDeviceIP").val(deviceip);
				$("#ipt_middlePort").val(data.port);
				$("#ipt_middleUserName").val(data.userName);
				$("#ipt_middlePassWord").val(data.passWord);
				$("#ipt_middleWareType").val(data.middleWareType);
				var middleWareName = data.middleWareName;
				$("#ipt_middleWareName").attr("alt",middleWareName);
				if(middleWareName==1){
					$("#ipt_middleWareName").val("weblogic");
					$("#isShowUser").show();
					$("#isShowPwd").show();
				}else if(middleWareName==2){
					$("#ipt_middleWareName").val("tomcat");
					$("#isShowUser").hide();
					$("#isShowPwd").hide();
				}else if(middleWareName==3){
					$("#ipt_middleWareName").val("websphere");
					$("#isShowUser").hide();
					$("#isShowPwd").hide();
				}
//				doInitDBMoList(taskId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（机房环境监控）
 */
function initRoomDetail(){
	var taskId = $("#taskId").val();
	doInitDBMoList();
	var deviceip = $("#deviceip").val();
	var moClassId = $("#moClassId").val();
	var port = $("#port").val();
	var path=getRootName();
	var uri=path+"/monitor/configObjMgr/initRoomDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"ipAddress":deviceip,
				"port" :port,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt")
				$("#ipt_deviceIP").val(deviceip);
				$("#ipt_roomPort").val(data.port);
				$("#ipt_roomUserName").val(data.userName);
				$("#ipt_roomPassWord").val(data.passWord);
			}
		};
	ajax_(ajax_param);
}


/*
 * 选择对象类型
 */
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/addDevice/initTree";
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
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择对象类型", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
//					console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_mobjectClassID','"
							+ _id + "','"+ className + "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}


/*
 * 选择隐藏树，对应展示需填数据
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId,className, showVal) {
	//判断是否为叶子节点
	var path = getRootName();
	var uri=path+"/monitor/addDevice/isLeaf";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"classID" : showId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (true == data || "true" == data) {
					$("#" + controlId).val(showVal);
					$("#" + controlId).attr("alt", showId);
					$("#" + divId).dialog('close');
					$("#ipt_mobjectClassName").val(className);
					$('#deviceSetting').tabs('disableTab', 1); 
					$('#deviceSetting').tabs('disableTab', 2);
					$('#deviceSetting').tabs('disableTab', 3);
					isShowPort(showId);
				}
			}
		};
		ajax_(ajax_param);
	
}

/**
 * 设置对象类型
 * @return
 */
function setObjectType(){
	var showId = $("#ipt_mobjectClassID").attr("alt");
	var className = $("#ipt_mobjectClassName").val();
	var moAlias = $("#ipt_moAlias").val();
	var moid = $("#moid").val();
	var showVal = $("#ipt_mobjectClassID").val();
	var result=checkInfo('#tblAddDevice');
	if (result == true) {
		if(showId== 15|| showId==16|| showId==54 || showId==81|| showId==86 || showId==19 || showId==20 || showId==53 || showId==44){
			var result=checkInfo('#tblAddDevice');
	//		console.log("result = "+result);
			var port = $("#ipt_serverPort").val();
			if(port == null || port ==""){
				$.messager.alert("提示", "端口不能为空！", "info");
			}else{
				if (!(/^[0-9]*[1-9][0-9]*$/.test(port)) && port != "") {
					$.messager.alert("提示", "端口只能输入正整数！", "info", function(e) {
						$("#ipt_serverPort").focus();
					});
				}else{
					//验证设备是否已存在
					isExistDevice(showId,className,showVal,moid,moAlias);
				}
			}
		}else{
			
			isExistDevice(showId,className,showVal,moid,moAlias);
		}
	}
}
/**
 * 是否已存在设备
 */
function isExistDevice(showId,className, showVal,moid,moAlias){
//	console.log("showId="+showId+",className="+className+",showVal="+showVal);
	var path = getRootPatch();
	var classID =showId;
	var deviceip = $("#deviceip").val();
	var port = $("#port").val();
//	console.log("port 默认="+port);
	
	if(classID==-1||classID==2||classID==3||classID==4||classID==5||classID==6||classID==59||classID==60||classID==7||classID==8||classID==75||classID==9){
		var uri = path +"/monitor/configObjMgr/isDevice?moClassId="+classID+"&deviceip="+deviceip+"&moid="+moid;
	}else if(classID== 15|| classID==16|| classID==54|| classID==81|| classID==86){
		port = $("#ipt_serverPort").val();
//		console.log("port ="+port);
		var uri = path +"/monitor/configObjMgr/isDatabase?moClassId="+classID+"&ip="+deviceip+"&port="+port+"&moid="+moid;
	}else if(classID==19 || classID==20 || classID==53){
		port = $("#ipt_serverPort").val();
		var uri = path +"/monitor/configObjMgr/isMiddle?moClassId="+classID+"&ip="+deviceip+"&port="+port+"&moid="+moid;
	}else if(classID==44){
		var uri = path +"/monitor/configObjMgr/isRoom?moClassId="+classID+"&ip="+deviceip+"&port="+port;
	}
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				var moAlias = $("#ipt_moAlias").val();
				if (true == data || "true" == data) {
					$.messager.alert("提示", "该设备已存在！", "info");
					var moClassId = $("#moClassId").val();
					var className = $("#className").val();
//					console.log("moClassId=="+moClassId+",  className="+className);
					$("#ipt_mobjectClassID").val(className);
					$("#ipt_mobjectClassID").attr("alt", moClassId);
					$("#ipt_serverPort").val("");
					$('#deviceSetting').tabs('enableTab', 1);
					$('#deviceSetting').tabs('enableTab', 2);
					$('#deviceSetting').tabs('enableTab', 3);
				} else {
					updateClass(showId,className, showVal,moAlias);
				}
			}
		};
		ajax_(ajax_param);
}

/**
 * 更新对象类型
 * @param showId
 * @return
 */
function updateClass(showId,className, showVal,moAlias){
	var taskId = $("#taskId").val();
	var moid = $("#moid").val();
	var port = $("#ipt_serverPort").val();
	var deviceip = $("#deviceip").val();
	if(port == "" || port == null || port ==''){
		port = -1;
	}
	var path = getRootName();
	var uri = path + "/monitor/configObjMgr/updateClass?taskId="+taskId+"&port="+port+"&moAlias="+moAlias;
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"deviceip" : deviceip,
		"moid" :moid,
		"moClassId" : showId,
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if(data == true || data == "true"){
			$.messager.alert("提示", "配置对象类型成功！", "info");
			doAfterChange(showId,className, showVal);
			parent.parent.$('#popWin'),window('close');
			window.frames['component_2'].frames['component_2'].reloadTable();
		}else{
			$.messager.alert("错误", "配置对象类型失败！", "error");
			var moClassId = $("#moClassId").val();
			var className = $("#className").val();
//				console.log("moClassId=="+moClassId+",  className="+className);
			$("#ipt_mobjectClassID").val(className);
			$("#ipt_mobjectClassID").attr("alt", moClassId);
			isShowPort(moClassId);
		}
		$('#deviceSetting').tabs('enableTab', 1);
		$('#deviceSetting').tabs('enableTab', 2);
		$('#deviceSetting').tabs('enableTab', 3);
	}
	};
	ajax_(ajax_param);
}

/**
 * 是否展示端口的输入框
 */
function isShowPort(classID){
	if(classID== 15|| classID==16|| classID==54 || classID==81 || classID==86 || classID==19 || classID==20 || classID==53){
		$("#isShowPort").show();
	}else{
		$("#isShowPort").hide();
	}
}
/**
 * 点击树后的操作
 */
function doAfterChange(showId,className, showVal){
	//配置对象类型
//	 updateClass(showId,showVal);
	var treeTypeDefault = $("#treeTypeDefault").val();
	var taskId = $("#taskId").val();

	$("#className").val(className);
	//置空配置阈值界面
	$("#ipt_moClassID").val(showVal);
	$("#ipt_moClassID").attr("alt", showId);
	$("#ipt_moID").val("");
	$("#ipt_kpiID").val("");
	$("#ipt_kpiName").val("");
	$("#isShowMO1").hide();
	$("#isShowMO2").hide();
	if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
		$("#chooseSource").show();
		$("#ipt_sourceMOName").val("");
	}else{
		$("#chooseSource").hide();
		var deviceip = $("#deviceip").val();
		$("#ipt_sourceMOName").val(deviceip);
	}
	reloadTable();
	if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").show();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("dbms");
		if(showId == 15 ){
			$("#ipt_dbmsType").val(showVal);
			$("#ipt_dbPort").val("1521");
		}else if(showId == 16 ){
			$("#ipt_dbmsType").val(showVal);
			$("#ipt_dbPort").val("3306");
		}else if(showId == 54 ){
			$("#ipt_dbmsType").val(showVal);
			$("#ipt_dbPort").val("50000");
		}else if(showId == 81 ){
			$("#ipt_dbmsType").val(showVal);
			$("#ipt_dbPort").val("5000");
		}else if(showId == 86 ){
			$("#ipt_dbmsType").val(showVal);
			$("#ipt_dbPort").val("1433");
		}
		
		if(treeTypeDefault=="dbms"){
			$("#monitorFlag").val("edit");
		}else{
			$("#monitorFlag").val("add");
		}
		doInitDBMoList();
	}else if(showId == 8 || showId == 75){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").show();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("Vmware");
		$("#ipt_VMport").val('443');
		if(treeTypeDefault=="Vmware"||treeTypeDefault=="SNMP"){
			$("#monitorFlag").val("edit");
		}else{
			$("#monitorFlag").val("add");
		}
		doInitMoList();
	}else if(showId == 19 || showId == 20 || showId ==53){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").show();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("middle");
		if(treeTypeDefault=="middle"){
			$("#monitorFlag").val("edit");
		}else{
			$("#monitorFlag").val("add");
		}
		if(showId == 53){
			$("#isShowUser").show();
			$("#isShowPwd").show();
		}else{
			$("#isShowUser").hide();
			$("#isShowPwd").hide();
		}
		setMiddleUrl(showId);
		doInitDBMoList();
	}else if(showId == 44){
		$("#divAddSNMPCommunity").hide();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").show();
		$("#authType").val("room");
		$("#ipt_VMport").val('6580');
		doInitDBMoList();
	}else if(showId == -1){
//		$("#mobjectSetting").show();
//		$("#divCommunity").hide();
//		$("#divThresholdSetting").hide();
//		$("#monitorSetting").hide();
		$('#deviceSetting').tabs('enableTab', 0); 
		$('#deviceSetting').tabs('disableTab', 1); 
		$('#deviceSetting').tabs('disableTab', 2); 
		$('#deviceSetting').tabs('enableTab', 3); 
	}else{
		$("#divAddSNMPCommunity").show();
		$("#divAddVMwareCommunity").hide();
		$("#divAddDBMSCommunity").hide();
		$("#divAddMiddlewareCommunity").hide();
		$("#divAddRoomCommunity").hide();
		$("#authType").val("SNMP");
		var snmpPort = $("#ipt_snmpPort").val();
		var readCommunity = $("#ipt_readCommunity").val();
		var writeCommunity = $("#ipt_writeCommunity").val();
		if (snmpPort == 0) {
			$("#ipt_snmpPort").val('161');
		}
		if (readCommunity == '') {
			$("#ipt_readCommunity").val('public');
		}
		if (writeCommunity == '') {
			$("#ipt_writeCommunity").val('public');
		}
		if(treeTypeDefault=="Vmware"||treeTypeDefault=="SNMP"){
			$("#monitorFlag").val("edit");
		}else{
			$("#monitorFlag").val("add");
		}
		doInitMoList();
	}
}


/**
 * 设置凭证
 * @return
 */
function setCommunity(){
	var authType = $("#authType").val();
	var authTypeDefault =  $("#authTypeDefault").val();
	var flag ="add";
	if(authTypeDefault == authType){
		flag = "update";
	}else{
		flag = "add";
	}
	if(authType=="SNMP"){
		setSnmpCommunity(flag);
	}else if(authType=="Vmware"){
		var checkRS =checkInfo("#tblAuthCommunityInfo") ;
		if(checkRS == true){
			setVMwareCommunity(flag);
		}
	}else if(authType=="middle"){
		var checkRS =checkInfo("#tblMiddlewareCommunity") ;
		if(checkRS == true){
			setMiddleCommunity(flag);
		}
	}else if(authType=="room"){
		var checkRS =checkInfo("#tblRoomCommunity") ;
		if(checkRS == true){
			setRoomCommunity(flag);
		}
	}else{
		var checkRS =checkInfo("#tblDBMSCommunity") ;
		if(checkRS == true){
			setDBMSCommunity(flag);
		}
	}
}

/*
 * 验证IP格式
 */
function checkPort(flag) {
	var port =null;
	var message = "";
	if(flag == 1){
		port = $("#ipt_snmpPort").val();
		message = "SNMP端口只能输入正整数！";
	}else if(flag == 2){
		port = $("#ipt_VMport").val();
		message = "VMware登录端口只能输入正整数！";
	}else if(flag == 3){
		port = $("#ipt_dbPort").val();
		message = "数据库认证端口只能输入正整数！";
	}else{
		port = $("#ipt_middlePort").val();
		message = "中间件认证端口只能输入正整数！";
	}
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", message, "info", function(e) {
			if(flag == 1){
				$("#ipt_snmpPort").focus();
			}else if(flag == 2){
				$("#ipt_VMport").focus();
			}else if(flag == 3){
				$("#ipt_dbPort").focus();
			}else{
				$("#ipt_middlePort").focus();
			}
			
		});
		return false;
	}
	return true;
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
 * 设置SNMP
 * @param flag
 * @return
 */
function setSnmpCommunity(flag){
//	console.log("flag === "+flag);
	var checkFormRS = true;
	var checkKeyRS = true;
	var snmpVersion = $("#ipt_snmpVersion").val();
//	console.log("snmpVersion=="+snmpVersion);
	if(snmpVersion==0 || snmpVersion==1){
		checkFormRS = checkInfo('#readCommunity')&& checkInfo('#snmpPort');
	}else if(snmpVersion == 3){
		checkFormRS = checkInfo('#readCommunity')&& checkInfo('#usmUser')&& checkInfo('#snmpPort');
		var authAlogrithm= $("#ipt_authAlogrithm").val();
//		console.log("authAlogrithm == "+authAlogrithm);
		if(authAlogrithm != -1){
			checkKeyRS = checkKey();
		}
	}
//	console.log("checkKeyRS==="+checkKeyRS+" , checkFormRS0"+checkFormRS);
	var index = 1;
	var deviceip = $("#deviceip").val();
	if (checkFormRS == true) {
		if (checkKeyRS == true) {
			if (checkPort(index)) {
				var path = getRootName();
				var classID = $("#ipt_mobjectClassID").attr("alt");
				var moClassNames = $("#className" ).val();
				var uri = path + "/monitor/configObjMgr/setSnmpCommunity?flag="+flag;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"deviceIP" : deviceip,
					"readCommunity" : $("#ipt_readCommunity").val(),
					"writeCommunity" : $("#ipt_writeCommunity").val(),
					"snmpPort" : $("#ipt_snmpPort").val(),
					"moID" : $("#moid").val(),
					"snmpVersion" : $("#ipt_snmpVersion").val(),
					"usmUser" : $("#ipt_usmUser").val(),
					"contexName" : $("#ipt_contexName").val(),
					"authAlogrithm" : $("#ipt_authAlogrithm").val(),
					"authKey" : $("#ipt_authKey").val(),
					"encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").val(),
					"encryptionKey" : $("#ipt_encryptionKey").val(),
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "配置凭证成功！", "info");
						var authType = $("#authType").val();
						$("#authTypeDefault").val(authType);
					} else {
						$.messager.alert("提示", "配置凭证失败！", "error");
					}
					
				}
				};
				ajax_(ajax_param);
			}
		}
	}
}

/**
 * 设置Vmware
 */
function setVMwareCommunity(flag) {
	var index = 2;
	var deviceip = $("#deviceip").val();
	if (checkPort(index)) {
		var moClassNames = $("#className" ).val();
		var path = getRootName();
		var uri = path + "/monitor/configObjMgr/setVMwareCommunity?flag="+flag;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deviceIP" : deviceip,
				"authType" : 3,
				"port" : $("#ipt_VMport").val(),
				"moID" : $("#moid").val(),
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "配置凭证成功！", "info");
					var authType = $("#authType").val();
					$("#authTypeDefault").val(authType);
				} else {
					$.messager.alert("提示", "配置凭证失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 设置数据库认证
 */
function setDBMSCommunity(flag){
	var index = 3;
	var deviceip = $("#deviceip").val();
	if (checkPort(index)) {
		var path = getRootName();
		var moClassNames = $("#className" ).val();
		var uri = path + "/monitor/configObjMgr/setDBMSCommunity?flag="+flag;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ip" : deviceip,
				"dbName" : $("#ipt_dbName").val(),
				"dbmsType" : $("#ipt_dbmsType").val(),
				"userName" : $("#ipt_dbUserName").val(),
				"password" : $("#ipt_dbPassword").val(),
				"port" : $("#ipt_dbPort").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "配置凭证成功！", "info");
					var authType = $("#authType").val();
					$("#authTypeDefault").val(authType);
				} else {
					$.messager.alert("提示", "配置凭证失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 设置JMX凭证
 * @return
 */
function setMiddleCommunity(flag){
	var index = 4;
	var deviceip = $("#deviceip").val();
	if (checkPort(index)) {
		var middleWareName = 3;
		var middleWareType = "websphere";
		var classID = $("#ipt_mobjectClassID").attr("alt");
		if(classID == 19){
			middleWareName = 3;
			middleWareType = "websphere";
		}else if(classID == 20){
			middleWareName = 2;
			middleWareType = "tomcat";
		}else if(classID == 53){
			middleWareName = 1;
			middleWareType = "weblogic";
		}
		var path = getRootName();
		var moClassNames = $("#ipt_mobjectClassID" ).val();
//		console.log("classID==="+classID);
//		console.log("moClassNames==="+moClassNames);
		var uri = path + "/monitor/configObjMgr/setMiddleCommunity?flag="+flag;
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"ipAddress" :deviceip,
					"middleWareName" : middleWareName,
					"middleWareType" : middleWareType,
					"port" : $("#ipt_middlePort").val(),
					"userName" : $("#ipt_middleUserName").val(),
					"passWord" : $("#ipt_middlePassWord").val(),
					"url" : $("#ipt_url").val(),
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "配置凭证成功！", "info");
						var authType = $("#authType").val();
						$("#authTypeDefault").val(authType);
					} else {
						$.messager.alert("提示", "配置凭证失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
	}
}

/**
 * 设置机房环境监控凭证
 */
function setRoomCommunity(flag) {
	var index = 2;
	var deviceip = $("#deviceip").val();
	if (checkPort(index)) {
		var path = getRootName();
		var uri = path + "/monitor/configObjMgr/setRoomCommunity?flag="+flag;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ipAddress" : deviceip,
				"port" : $("#ipt_roomPort").val(),
				"userName" : $("#ipt_roomUserName").val(),
				"passWord" : $("#ipt_roomPassWord").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "配置凭证成功！", "info");
					var authType = $("#authType").val();
					$("#authTypeDefault").val(authType);
				} else {
					$.messager.alert("提示", "配置凭证失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 判断是否展示清空按钮
 */
function isShowClearBtn(){
    var kpiName = $("#ipt_kpiName").val();
    if (kpiName == "" || kpiName == null) {
        $("#btnChoseKpi").show();
        $("#btnClearKpi").hide();
    } else {
        $("#btnChoseKpi").hide();
        $("#btnClearKpi").show();
    }
    var sourceName = $("#ipt_sourceMOName").val();
    var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassId == 15 || sourceClassId == 16 || sourceClassId == 54 || sourceClassId == 81 || sourceClassId == 86 || sourceClassId == 44){
		if (sourceName == "" || sourceName == null) {
	        $("#chooseSource").show();
	        $("#btnClearSource").hide();
	    } else {
	        $("#chooseSource").hide();
	        $("#btnClearSource").show();
	    }
	}else{
		$("#chooseSource").hide();
		$("#btnClearSource").hide();
	}
    
    var moName = $("#ipt_moName").val();
    if (moName == "" || moName == null) {
        $("#btnChoseMo").show();
        $("#btnClearMo").hide();
    } else {
        $("#btnChoseMo").hide();
        $("#btnClearMo").show();
    }
}

/**
 * 清空指标
 */
function clearPerfKPIDef(){
    $("#ipt_kpiID").val("");
    $("#ipt_kpiName").val("");
    var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassId == 15 || sourceClassId == 16 || sourceClassId == 54 || sourceClassId == 81 || sourceClassId == 86 || sourceClassId == 44){
		$("#sourceMOID").val("");
		$("#ipt_sourceMOName").val("");
	}
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    isShowClearBtn();
}

/**
 * 清空源对象
 */
function clearSource(){
    $("#sourceMOID").val("");
    $("#ipt_sourceMOName").val("");
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    isShowClearBtn();
}

/**
 * 清空管理对象
 */
function clearMoRescource(){
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    $("#btnChoseMo").show();
    $("#btnClearMo").hide();
}

/*
 * 加载管理对象
 */
function loadMoRescource(){
	var kpiName = $("#ipt_kpiName").val();
	if(kpiName != null && kpiName != ""){
		var sourceMOName = $("#ipt_sourceMOName").val();
		if(sourceMOName != null && sourceMOName != ""){
			var sourceMOID = $("#moid").val();
			var classID = $("#ipt_kpiClassID").val();
			var parentClassID = $("#ipt_mobjectClassID").attr("alt");
			var dbSourceMOID = $("#sourceMOID").val();
			var index = 1;
			var path=getRootName();
			if(classID==2||classID==3||classID==4||classID==5||classID==6||classID==59||classID==60||classID==7||classID==8||classID==75||classID==9){
				var uri=path+"/monitor/discover/toDiscoverDeviceList?flag=choose&index="+index+"&deviceType1=" + classID;
				window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
			}else if(null != sourceMOID && ""!= sourceMOID){
				if(classID==10){
					var uri=path+"/monitor/alarmmgr/moKPIThreshold/toNetworkIf?flag=choose&deviceMOID="+sourceMOID;
				}else if(classID ==11){
					var uri=path+"/monitor/alarmmgr/moKPIThreshold/toVolumeList?flag=choose&deviceMOID="+sourceMOID;
				}else if(classID==12){
					var uri=path+"/monitor/alarmmgr/moKPIThreshold/toCPUsList?flag=choose&deviceMOID="+sourceMOID;
				}else if(classID==13){
					var uri=path+"/monitor/alarmmgr/moKPIThreshold/toMemoriesList?flag=choose&deviceMOID="+sourceMOID;
				}else if(classID==24){// oracle回滚段
					var uri=path+"/monitor/orclDbManage/toOrclRollSEGList?flag=choose&instanceMOID="+dbSourceMOID;
				}else if(classID==25){// oracle数据文件
					var uri=path+"/monitor/orclDbManage/toOrclDataFileList?flag=choose&instanceMOID="+dbSourceMOID;
				}else if(classID==27){// SGA
					var uri=path+"/monitor/orclDbManage/toOrclSgaList?flag=choose&instanceMOID="+dbSourceMOID;
				}else if(classID==29){// 数据库
					var uri=path+"/monitor/dbObjMgr/toMysqlDB?flag=choose&sqlServerMOID="+dbSourceMOID;
				}else if(classID==30){// 运行对象
					var uri=path+"/monitor/alarmmgr/moKPIThreshold/toMysqlRunObj?flag=choose&index=chooseForThreshold&sqlServerMOID="+dbSourceMOID;
				}else if(classID==31){// 系统变量
					var uri=path+"/monitor/dbObjMgr/toMysqlSysVar?flag=choose&sqlServerMOID="+dbSourceMOID;
				}else if(classID==32){// J2EE应用
					var uri=path+"/monitor/DeviceTomcatManage/toJ2eeAppList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==33){// WebModule
					var uri=path+"/monitor/DeviceTomcatManage/toWebModuleList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==34){// Servlet
					var uri=path+"/monitor/DeviceTomcatManage/toServletList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==36){// 线程池
					var uri=path+"/monitor/DeviceTomcatManage/toThreadPoolList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==37){// 类加载信息
					var uri=path+"/monitor/DeviceTomcatManage/toMiddleClassLoadList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==38){// JDBC连接池
					var uri=path+"/monitor/DeviceTomcatManage/toMiddleWareJdbcPoolList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==39){// JMS信息
					var uri=path+"/monitor/DeviceTomcatManage/toMiddleWareJMSList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==40){// JTA信息DeviceTomcatManage
					var uri=path+"/monitor/DeviceTomcatManage/toMiddleWareJTAList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==41){// 中间件内存池
					var uri=path+"/monitor/DeviceTomcatManage/toMiddlewareMemPool?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==42){// 中间件Java虚拟机
					var uri=path+"/monitor/DeviceTomcatManage/toMiddlewareJvm?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==43){// JDBC数据源
					var uri=path+"/monitor/DeviceTomcatManage/toJDBCDSList?flag=choose&parentMoId="+sourceMOID;
				}else if(classID==45){// 阅读器
					var uri=path+"/monitor/envManager/toReaderList?flag=choose&moClassId="+classID;
				}else if(classID==46 || classID==47 || classID==48 || classID==49 || classID==50 || classID==51 || classID==52){
					var uri=path+"/monitor/envManager/toTagList?flag=choose&moClassId="+classID;
				}else if(classID==56){// DB2数据库资源
					var uri=path+"/monitor/db2Manage/toDb2InfoList?flag=choose&isMoid=true&instanceMOID="+dbSourceMOID;
				}else if(classID==57){// DB2缓冲池资源
					var uri=path+"/monitor/db2Manage/toShowDb2BufferPoolInfo?flag=choose&dbMoId="+dbSourceMOID;
				}else if(classID==83){// SyBase数据库设备
					var uri=path+"/monitor/sybaseDbManage/toSybaseDeviceList?flag=choose&serverMoId="+dbSourceMOID;
				}else if(classID==84){// SyBase数据库
					var uri=path+"/monitor/sybaseDbManage/toSybaseDBList?flag=choose&serverMoId="+dbSourceMOID;
				}else if(classID==88){// MySql数据库设备
					var uri=path+"/monitor/msDbManage/toMsDeviceList?flag=choose&serverMoId="+dbSourceMOID;
				}else if(classID==80){// MySql数据库
					var uri=path+"/monitor/msDbManage/toMsDBList?flag=choose&serverMoId="+dbSourceMOID;
				}else if(classID==23){
					// oralce表空间
					if(parentClassID == 15){
						var uri=path+"/monitor/orclDbManage/toShowOrclTbsInfo?flag=choose&instanceMOID="+dbSourceMOID;
					}
					// DB2表空间资源
					else if(parentClassID == 54){
						var uri=path+"/monitor/db2Manage/toShowDb2TbsInfo?flag=choose&dbMoId="+dbSourceMOID;
					}
				}
				window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
			}
		}else{
			$.messager.alert("提示","请先选择源对象！","info");
		}
	}else{
		$.messager.alert("提示","请先选择指标！","info");
	}
}

/*
 * 加载指标
 */
function loadPerfKPIDef(){
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var path=getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/toSelectPerfKPIDef?flag=choose&classID="+classID;
	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

/*
 * 指标详情
 */
function findPerfKPIDefInfo(){
	var kpiID = $("#ipt_kpiID").val();
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findPerfKPIDef";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"kpiID" : kpiID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_kpiName").val(data.name);
			$("#ipt_kpiClassID").val(data.classID);
			$("#ipt_descr").val(data.quantifier);
			isShowClearBtn();
			$("#ipt_moID").val("");
		    $("#ipt_moName").val("");
		}
	};
	ajax_(ajax_param);		
}

/*
 * 新增
 */
function setThreshold(){
	var setThresholdFlag = $("#setThresholdFlag").val();
//	console.log("setThresholdFlag===="+setThresholdFlag);
	if(setThresholdFlag=="update"){
		checkBeforeUpdate();
	}else{
		checkBeforeAdd();
	}
}

/*
 * 验证添加的阈值规则定义的唯一性
 */
function checkBeforeAdd(){
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceClassID = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassID == 15 || sourceClassID ==16 || sourceClassID ==81 || sourceClassID ==86 || sourceClassID ==54 || sourceClassID ==44){
		var sourceMOID = $("#sourceMOID").val();
	}else{
		var sourceMOID = $("#ipt_sourceMOID").val();
	}
	var moID = $("#ipt_moID").val();
	var kpiID = $("#ipt_kpiID").val();
//	console.log("classId=="+classId+",moID=="+moID+"kpiID=="+kpiID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/checkBeforeAdd";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"classID" : classID,
				"sourceMOID" :sourceMOID,
				"moID" : moID,
				"kpiID" : kpiID,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (false == data || "false" == data) {
					$.messager.alert("提示", "该阈值规则定义已存在！", "info");
				} else {
					addThreshold();
					return;
				}
			}
		};
		ajax_(ajax_param);	
}


/*
 * 验证添加的阈值规则定义的唯一性(编辑时)
 */
function checkBeforeUpdate(){
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceClassID = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassID == 15 || sourceClassID ==16  || sourceClassID ==54 || sourceClassID ==81  || sourceClassID ==86 || sourceClassID ==44){
		var sourceMOID = $("#sourceMOID").val();
	}else{
		var sourceMOID = $("#ipt_sourceMOID").val();
	}
	var moID = $("#ipt_moID").val();
	var kpiID = $("#ipt_kpiID").val();
	var ruleID = $("#ipt_ruleID").val();
//	console.log("classId=="+classId+",moID=="+moID+"kpiID=="+kpiID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/checkBeforeUpdate";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"ruleID" : ruleID,
				"classID" : classID,
				"sourceMOID" :sourceMOID,
				"moID" : moID,
				"kpiID" : kpiID,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (false == data || "false" == data) {
					$.messager.alert("提示", "该阈值规则定义已存在！", "info");
				} else {
					doUpdate();
					return;
				}
			}
		};
		ajax_(ajax_param);	
}


function addThreshold(){
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceClassID = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassID == 15 || sourceClassID ==16  || sourceClassID ==54 || sourceClassID ==81  || sourceClassID ==86 || sourceClassID ==44){
		var sourceMOID = $("#sourceMOID").val();
	}else{
		var sourceMOID = $("#moid").val();
	}
	var moID = $("#ipt_moID").val();
    if (moID == "") {
        moID = -1;
    }
	var kpiID = $("#ipt_kpiID").val();
	var upThreshold = $("#ipt_upThreshold").val();
	var upRecursiveThreshold = $("#ipt_upRecursiveThreshold").val();
	var downThreshold = $("#ipt_downThreshold").val();
	var downRecursiveThreshold = $("#ipt_downRecursiveThreshold").val();
	var descr = $("#ipt_descr").val();
	var result=checkInfo('#divThresholdAdd');
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/addThreshold";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"classID" : classID,
			"moID" : moID,
			"sourceMOID" : sourceMOID,
			"kpiID" : kpiID,
			"upThreshold" : upThreshold,
			"upRecursiveThreshold":upRecursiveThreshold,
			"downThreshold" : downThreshold,
			"downRecursiveThreshold":downRecursiveThreshold,
			"descr" : descr,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "阈值规则定义新增成功！", "info");
				$("#setThresholdFlag").val("");
				resetThreshodForm();
				isShowClearBtn();
				reloadTable();
			} else {
				$.messager.alert("提示", "阈值规则定义新增失败！", "error");
			}
		}
	};	
	if(result == true ){
		var isThresholdRS = isThreshold();
		if(isThresholdRS==true){
			ajax_(ajax_param);
		}
	}
}

/**
 * 更新
 * @return
 */
function doUpdate(){
	var ruleID = $("#ipt_ruleID").val();
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceClassID = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassID == 15 || sourceClassID ==16 || sourceClassID ==54 || sourceClassID ==81  || sourceClassID ==86 || sourceClassID ==44){
		var sourceMOID = $("#sourceMOID").val();
	}else{
		var sourceMOID = $("#moid").val();
	}
	var moID = -1;
	if(classID==2||classID==3||classID==4||classID==5||classID==6||classID==59||classID==60||classID==7||classID==8||classID==75||classID==9){
		moID=-1;
	}else{
		moID = $("#ipt_moID").val();
	}
	var kpiID = $("#ipt_kpiID").val();
	var upThreshold = $("#ipt_upThreshold").val();
	var upRecursiveThreshold = $("#ipt_upRecursiveThreshold").val();
	var downThreshold = $("#ipt_downThreshold").val();
	var downRecursiveThreshold = $("#ipt_downRecursiveThreshold").val();
	var descr = $("#ipt_descr").val();
	var result=checkInfo('#divThresholdAdd');
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/updateThreshold";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"ruleID" : ruleID,
			"classID" : classID,
			"moID" : moID,
			"sourceMOID" : sourceMOID,
			"kpiID" : kpiID,
			"upThreshold" : upThreshold,
			"upRecursiveThreshold":upRecursiveThreshold,
			"downThreshold" : downThreshold,
			"downRecursiveThreshold":downRecursiveThreshold,
			"descr" : descr,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "阈值规则定义更新成功！", "info");
				$("#setThresholdFlag").val("");
				resetThreshodForm();
				isShowClearBtn();
				reloadTable();
			} else {
				$.messager.alert("提示", "阈值规则定义更新失败！", "error");
			}
		}
	};	
	if(result == true ){
		var isThresholdRS = isThreshold();
		if(isThresholdRS==true){
			ajax_(ajax_param);
		}
	}
}


//监测器配置

/**
 * 显示根据任务ID列表出来的监测器
 * @param taskId
 * @return
 */
function toShowMo(taskId){
	$('#monitorSetting').dialog('open');
	doInitMoList();
	toCheckMo(taskId);
}

/**
 * 默认勾选某条任务包含的监测器
 * @param taskId
 * @return
 */
function toCheckMo(taskId){
//	console.log("taskId====="+taskId)
	if(taskId=="" || taskId==null){
		taskId =-1;
	}
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
//			console.log("data==="+data)
			for (var i=0;i<data.length;i++){ 
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					var mid=data[i].split(",")[0];
					var interval=data[i].split(",")[1];
					var timeUnit =1;
					var doIntervals = interval;
					if(mid=="53"){
						timeUnit =0;
					}
					else{
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


/**
 * 根据任务ID获取监测器信息
 * @param taskId
 * @return
 */
function doInitMoList(){
	var length=$("#monitor").find("tr").length-1;
//	console.log("主机 beafore ==="+length)
	var length2=$("#monitor").find("tr").length-1;
//	console.log("主机 after ==="+length2)
	var deviceId=$("#ipt_moid").val(); 
	var moid =$("#moid").val(); 
//	console.log("moID==="+moid);
	var taskId =$("#taskId").val(); 
	var templateID=$("#ipt_templateID").val();
//	console.log("templateID==="+templateID);
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
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
				+"<td><input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='"+data[i].split(",")[2]+"' style='width:60px;'  validator='{\"default\":\"ptInteger\"}'/></td>"
				+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" +
				"<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
				"</select></td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
			}
			$("#monitor").empty();
			$('#monitor').append(html);
			
			for (var i=0;i<data.length;i++){
//				console.log(data[i].split(",")[0]+"--"+data[i].split(",")[1]+"--"+data[i].split(",")[2]+"--"+data[i].split(",")[3])
				//设置单位
				var timeUnit =data[i].split(",")[3];
				$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value='"+data[i].split(",")[3]+"'] ").attr("selected",true);
				
				//如果监测器没有没有默认周期，则采用对象类型的周期
				if(data[i].split(",")[2] == -1 || data[i].split(",")[2] == "-1"){
					//下拉框默认展示采集周期默认值
					var collectPeriod = $("#collectPeriod").val();
//					console.log("collectPeriod="+collectPeriod);
					if(collectPeriod != -1){
						$("#ipt_doIntervals"+data[i].split(",")[0]).val(collectPeriod);
					}else{
						$("#ipt_doIntervals"+data[i].split(",")[0]).val(20);
					}
//					$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value=1] ").attr("selected",true);
					$("#ipt_timeUnit"+data[i].split(",")[0]).val(1);
				}
			}
			var flag=$("#monitorFlag").val();
			if(flag=="edit"){
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					checkboxs[j].checked=false;
				}
				toCheckMo(taskId);
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
//		console.log(moTypeLstJson);
//		console.log(typeof(moTypeLstJson));
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
		$("#monitor").empty();
		$('#monitor').append(html);
		
	}
	};	
	ajax_(ajax_param);
}

/**
 * 获取监测器（数据库）
 * @return
 */
function doInitDBMoList(){
//	var length=$("#monitor").find("tr").length-1;
//	console.log("数据库 beafore ==="+length)
//	var length2=$("#monitor").find("tr").length-1;
//	console.log("数据库 beafore ==="+length2)
	
//	var length2=$("#monitor").find("tr").length-1;
	var templateID=$("#ipt_templateID").val();
	var moClassId=$("#ipt_mobjectClassID").attr("alt");
	var taskId =$("#taskId").val();
	//没有套用模板
	if(templateID == -1 || templateID == "-1"){
		var path=getRootName();
		var uri=path+"/monitor/perfTask/listDBMoList";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			async : false,
			data:{
				"moClassId":moClassId,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				var html='';
				var trHTML1 = "<tr>"
				var trHTML2 = "</tr>"
				for (var i=0;i<data.length;i++){ 
					html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
					+"<td><input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='"+data[i].split(",")[2]+"' style='width:60px;'  validator='{\"default\":\"ptInteger\"}'/></td>"
					+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>";
					if(data[i].split(",")[0]=="53"){
						html+="<option value='0'>秒</option><option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
					"</select></td>";
					}else{
						html+="<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
					"</select></td>";
					}
					if((i+1)%2 != 0){
						html=trHTML1+html;
					}else{
						html=html+trHTML2;
					}
				}
				$("#monitor").empty();
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
				
				var flag=$("#monitorFlag").val();
//				console.log("db flag="+flag);
				if(flag=="edit"){
					var checkboxs=document.getElementsByName('moType');
					for(var j = 0; j < checkboxs.length;j++){
						checkboxs[j].checked=false;
					}
					toCheckMo(taskId);
				}
				
			}
		};	
		ajax_(ajax_param);
	}else{
		getMOTypeList(templateID);
	}
	
}


/**
 * 新增监测器配置信息
 * @return
 */
function doSetMonitors(){
	var flag =$("#monitorFlag").val();
//	console.log("flag===="+$("#monitorFlag").val());
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	var motimeunit='';
	var moClassId = $("#ipt_mobjectClassID").attr("alt");
//	console.log("moClassID==="+moClassId)
	var dbName = "";
	if(moClassId == 54){
		dbName = $("#ipt_dbName").val();
	}
	var taskId = $('#taskId').val(); 
//	console.log("taskId==="+taskId);
	if(taskId == null ||taskId ==""){
		taskId = -1;
	}
	var deviceIp = $("#deviceip").val();
	$('input:checked[name=moType]').each(function() { 
		arrChk.push($(this).val());
	});
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
//		console.log(">>>>>>>");
//		console.log(molist);
//		console.log(mointerval);
		var templateID=$("#ipt_templateID").val();
		var moTypeLstJson = $("#moTypeLstJson").val();
		var path=getRootName();
		if(templateID == -1 || templateID == "-1"){
			var uri=path+"/monitor/configObjMgr/doSetMonitors?flag="+flag+"&dbName="+dbName+"&templateID=-1";
		}else{
			var moClassId=$("#ipt_mobjectClassID").attr("alt");
			var moId =$("#moid").val();
			var uri=path+"/monitor/configObjMgr/doSetMonitors?flag="+flag+"&dbName="+dbName+"&moTypeLstJson="+moTypeLstJson+"&templateID="+templateID+"&moClassId="+moClassId;
		}
		var monitorCheck = checkInfo('#monitor');
		if(monitorCheck == true){
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					async: false,
					data:{	
				"deviceIp" :deviceIp,
				"moId":$("#moid").val(),
				"moClassId":$("#moClassId").val(),
				"status":1,
				"moList" : molist,	
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				"taskId" : taskId,
				"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
//			console.log(data);
				if(data.setTemplateFlag == false || data.setTemplateFlag == "false"){
					$.messager.alert("提示","绑定模板失败！","info");
				}else if(data.isNoMonitor == true || data.isNoMonitor == "true"){
					if(data.isInMoType == true || data.isInMoType == "true"){
						if(data.isVCenterTask == true || data.isVCenterTask == "true"){
							$.messager.alert("提示","此设备已通过vCenter方式分发任务,不能配置监测器！","error");
						}else{
							if(data.setMonitorsRS==true ||"true"==data.setMonitorsRS){
								$.messager.alert("提示","配置监测器成功！","info");
								$('#taskId').val(data.taskId); 
							}else{
								$.messager.alert("提示","配置监测器失败！","error");
							}
						}
					}else{
						$.messager.alert("提示","该设备没有属于该模板中监测器类型的监测器！","info");
					}
				}else{
					$.messager.alert("提示","没有属于该设备的监测器！","info");
				}
			}
			};
			ajax_(ajax_param);
		}
	}
}


/**
 * 配置阈值的管理对象树
 * @return
 */
function choseThresholdTree(){
	var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
//	console.log("init thredshold ===="+sourceClassId);
	var path = getRootPatch();
	var uri = path + "/monitor/configObjMgr/initThresholdTree?sourceClassId="+sourceClassId;
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
			dataTreeOrg = new dTree("dataTreeOrg", path
					+ "/plugin/dTree/img/");
			dataTreeOrg.add(0, -1, "选择管理对象", "");

			// 得到树的json数据源
			var datas = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].classId;
				var _nameTemp = gtmdlToolList[i].classLable;
				var _parent = gtmdlToolList[i].parentClassId;
				var className = gtmdlToolList[i].className;
//				console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
				dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi2('divThresholdMObject','ipt_moClassID','"
						+ _id + "','"+ className + "','" + _nameTemp + "');");
			}
			$('#dataThresholdTreeDiv').empty();
			$('#dataThresholdTreeDiv').append(dataTreeOrg + "");
			$('#divThresholdMObject').dialog('open');
		}
	}
	ajax_(ajax_param);
}


function hiddenMObjectTreeSetValEasyUi2(divId, controlId, showId,className, showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
	var moClassId = $("#ipt_moClassID").attr("alt");
	
	//选择管理对象后，判断是否展示管理对象
	if(sourceClassId==moClassId || moClassId == 22 || moClassId == 26 || moClassId == 28 || moClassId == 45 || moClassId == 55 || moClassId==82 || moClassId==87 ){
		$("#isShowMO1").hide();
		$("#isShowMO2").hide();
	}else{
		$("#isShowMO1").show();
		$("#isShowMO2").show();
	}
	
	//如果对象类型为mysql、oracle、db2、动环，则阈值的源对象不为服务IP，需要选择
	if(sourceClassId == 15 || sourceClassId == 16  || sourceClassId == 54 || sourceClassId == 81  || sourceClassId == 86 || sourceClassId == 44){
		$("#sourceMOID").val("");
		$("#ipt_sourceMOName").val("");
	}else{
		var deviceip = $("#deviceip").val();
		$("#ipt_sourceMOName").val(deviceip);
	}
	$("#ipt_moID").val("");
	$("#ipt_moName").val("");
	$("#ipt_kpiID").val("");
	$("#ipt_kpiName").val("");
}

/*
 * 查找接口信息
 */
function findNetworkIfInfo(){
	var moID = $("#ipt_networkIfId").val();
//	console.log("#ipt_networkIfId===="+moID);
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findNetworkIfInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.ifName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/*
 * 查找磁盘信息
 */
function findVolumesInfo(){
	var moID = $("#ipt_volumnsId").val();
//	console.log("#ipt_volumnsId"+moID);
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findVolumesInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.moName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/*
 * 查找CPU信息
 */
function findCPUsInfo(){
	var moID = $("#ipt_cpusId").val();
//	console.log("#ipt_cpusId"+moID);
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findCPUsInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.moName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/*
 * 查找内存信息
 */
function findMemoriesInfo(){
	var moID = $("#ipt_memoriesId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMemoriesInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.moName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 线程池信息
 * @return
 */
function findThreadPoolInfo(){
	var moID = $("#ipt_threadPoolId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findThreadPoolInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.threadName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 类加载信息
 * @return
 */
function findClassLoadInfo(){
	var moID = $("#ipt_classLoadId").val();
	$("#ipt_moID").val(moID);
//	console.log("moID=="+moID)
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findClassLoadInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.serverName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * JDBC数据源
 * @return
 */
function findJdbcDSInfo(){
	var moID = $("#ipt_jdbcDSId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findJdbcDSInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.dSName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * JDBC连接池
 * @return
 */
function findJdbcPoolInfo(){
	var moID = $("#ipt_jdbcPoolId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findJdbcPoolInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.dsName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 中间件内存池
 * @return
 */
function findMemPoolInfo(){
	var moID = $("#ipt_memPoolId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMemPoolInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.memName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * JTA信息
 * @return
 */
function findMiddleWareJTAInfo(){
	var moID = $("#ipt_middleJTAId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMiddleWareJTAInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.name);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * Java虚拟机信息
 * @return
 */
function findMiddlewareJvmInfo(){
	var moID = $("#ipt_middlewareJvmId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMiddlewareJvmInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.jvmName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询运行对象信息
 * @return
 */
function findMysqlRunObjInfo(){
	var moID = $("#ipt_runObjId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMysqlRunObjInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.moName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询表空间信息
 * @return
 */
function findOracleTBSInfo(){
	var moID = $("#ipt_oracleTbsMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleTBSInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.tbsname);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询oracle回滚段信息
 * @return
 */
function findOracleRollSegInfo(){
	var moID = $("#ipt_oracleRollSegMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleRollSegInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.segName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询数据文件信息
 * @return
 */
function findOracleDataFileInfo(){
	var moID = $("#ipt_oracleDataFileMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleDataFileInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.dataFileName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询SGA信息
 * @return
 */
function findOracleSgaInfo(){
	var moID = $("#ipt_oracleSgaMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleSgaInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.instanceName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询Oracle下面的数据库信息
 * @return
 */
function findOracleDbInfo(){
	var moID = $("#ipt_oracleDbMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleDbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.dbName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询数据库实例信息
 * @return
 */
function findOracleInsInfo(){
	var sourceMOID = $("#ipt_oracleInsMoId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findOracleInsInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.instancename);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询MysqlServer信息
 * @return
 */
function findMysqlServerInfo(){
	var sourceMOID = $("#ipt_mysqlServerId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMysqlServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.ip);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询Mysql下面的数据库信息
 * @return
 */
function findMysqlDBInfo(){
	var moID = $("#ipt_mysqlDBId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMysqlDBInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.dbName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询系统变量信息
 * @return
 */
function findMysqlSysVarInfo(){
	var moID = $("#ipt_mysqlSysVarId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMysqlSysVarInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.varName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询j2ee应用信息
 * @return
 */
function findJ2eeAppInfo(){
	var moID = $("#ipt_j2eeAppId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findJ2eeAppInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.appName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询JMS信息
 * @return
 */
function findMiddleJMSInfo(){
	var moID = $("#ipt_middleJMSId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findMiddleJMSInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.name);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 是否能够设置上限阈值
 */
function isHaveUpThreshold(){
	var kpiID = $("#ipt_kpiID").val();
	if(kpiID == 247 || kpiID == 248 || kpiID == 252 || kpiID == 253 || kpiID == 254 || kpiID == 108 || kpiID == 109 || kpiID == 116){
		$.messager.alert("提示", "该指标只能设置下限阈值！", "error");
		$("#ipt_upThreshold").val("");
		return;
	}
}

function isUpThreshold(){
	var upThreshold = $("#ipt_upThreshold").val();
	if(upThreshold==""||upThreshold==null){
		$.messager.alert("提示", "请先设置上限阈值！", "error");
		$("#ipt_upRecursiveThreshold").val("");
	}
}

function isDownThreshold(){
	var downThreshold = $("#ipt_downThreshold").val();
	if(downThreshold==""||downThreshold==null){
		$.messager.alert("提示", "请先设置下限阈值！", "error");
		$("#ipt_downRecursiveThreshold").val("");
	}
}

function isThreshold(){
	var upThreshold = $("#ipt_upThreshold").val();
	var downThreshold = $("#ipt_downThreshold").val();
	if(upThreshold=="" && downThreshold==""){
		$.messager.alert("提示", "请设置上限阈值或下限阈值！", "info");
		return false;
	}else{
		return true;
	}
}

/**
 * 新增后重置阈值配置表单
 * @return
 */
function resetThreshodForm(){
	$("#ipt_moID").val("");
	$("#ipt_moName").val("");
	$("#ipt_kpiID").val("");
	$("#ipt_kpiName").val("");
	var sourceClassId = $("#ipt_mobjectClassID").attr("alt");
	if(sourceClassId == 15 || sourceClassId == 16 || sourceClassId == 54 || sourceClassId == 81 || sourceClassId == 86 || sourceClassId == 44){
		$("#sourceMOID").val("");
		$("#ipt_sourceMOName").val("");
	}
	$("#ipt_upThreshold").val("");
	$("#ipt_upRecursiveThreshold").val("");
	$("#ipt_downThreshold").val("");
	$("#ipt_downRecursiveThreshold").val("");
	$("#ipt_descr").val("");
}

/*
 * 阈值列表
 */
function doInitThresholdTable() {
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceMOID = $("#moid").val();
	var path = getRootName();
	$('#tblThresholdList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
//						fitColumns:true,
						url : path + '/monitor/configObjMgr/listMOKPIThreshold',
						remoteSort : false,
						queryParams : {
							"classID" : classID,
							"sourceMOID" : sourceMOID
						},
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
										{
											field : 'className',
											title : '对象类型',
											align : 'center',
											width : 80,
										},
										{
											field : 'moName',
											title : '管理对象',
											align : 'center',
											width : 80,
										},
										{
											field : 'kpiName',
											title : '指标名称',
											align : 'center',
											width : 80,
										},
										{
											field : 'upThreshold',
											title : '上限阈值',
											align : 'center',
											width : 60,
										},
										{
											field : 'upRecursiveThreshold',
											title : '上限回归阈值',
											align : 'center',
											width : 80,
										},
										{
											field : 'downThreshold',
											title : '下限阈值',
											align : 'center',
											width : 60,
										},
										{
											field : 'downRecursiveThreshold',
											title : '下限回归阈值',
											align : 'center',
											width : 80,
										},
								{
									field : 'alarmDefineIDs',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toModify('
											+ row.ruleID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ row.ruleID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
}


/*
 * 更新表格
 */
function reloadTable(){
	var classID =$("#ipt_mobjectClassID").attr("alt");
	var sourceMOID = $("#moid").val();
	$('#tblThresholdList').datagrid('options').queryParams = {
		"classID" : classID,
		"sourceMOID" : sourceMOID
	};
	reloadTableCommon_1('tblThresholdList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 删除阈值规则定义
 */
function toDel(ruleID){
	$.messager.confirm("提示","确定删除此阈值规则定义?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/alarmmgr/moKPIThreshold/delThreshold";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"ruleID" : ruleID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "阈值规则定义删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "阈值规则定义删除失败！", "error");
						reloadTable();
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

function closeWindow(){
	var index = $('#index').val(); 
//	console.log("index==="+index);
	if("show"==index){
		//$('.default').val("");
		$('#popWin2').window('close');
	}else{
		//$('.default').val("");
		$('#popWin').window('close');
		window.frames['component_2'].frames['component_2'].reloadTable();
//		window.parent.window.frames['component_2'].reloadTable();

//		reloadTableCommon_1('tblDataList')
	}
}

/**
 * 修改阈值
 * @return
 */
function toModify(ruleID){
	$("#setThresholdFlag").val("update");
	initThresholdDetail(ruleID);
}

/*
 * 初始化阈值规则定义信息
 */
function initThresholdDetail(ruleID){
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
//				console.log("data==="+data);
//				iterObj(data,"ipt");
				$("#ipt_ruleID").val(ruleID);
				$("#ipt_moID").val(data.moID);
				$("#ipt_moName").val(data.moName);
				$("#ipt_moClassID").val(data.className);
				$("#ipt_moClassID").attr("alt", data.classID);
//				console.log("moid=="+$("#moid").val());
//				console.log("data的sourceMOID=="+data.sourceMOID);
//				console.log("sourceMOName=="+data.sourceMOName);
				$("#ipt_kpiID").val(data.kpiID);
				$("#ipt_kpiName").val(data.kpiName);
				$("#ipt_upThreshold").val(data.upThreshold);
				$("#ipt_upRecursiveThreshold").val(data.upRecursiveThreshold);
				$("#ipt_downThreshold").val(data.downThreshold);
				$("#ipt_downRecursiveThreshold").val(data.downRecursiveThreshold);
				$("#ipt_descr").val(data.descr);
				initThreshold();
				$("#ipt_sourceMOName").val(data.sourceMOName);
				$("#sourceMOID").val(data.sourceMOID);
				$("#ipt_sourceMOID").val(data.sourceMOID);
				isShowClearBtn();
			}
		};
	ajax_(ajax_param);
}

/**
 * 查询WebModule信息
 * @return
 */
function findWebModuleInfo(){
	var moID = $("#ipt_webModuleId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findWebModuleInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.warName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询Servlet信息
 * @return
 */
function findServletInfo(){
	var moID = $("#ipt_servletId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findServletInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.servletName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}


function loadSource(){
	var moid = $("#moid").val();
	var path=getRootName();
	var classID = $("#ipt_mobjectClassID").attr("alt");
	var moClassID = $("#ipt_kpiClassID").val();
	if(classID==15){	
		classID==15;
		var uri= path +'/monitor/orclDbManage/toOrclInstanceList?flag=choose&dbmsMoId='+moid;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(classID==16){	
		var uri= path +'/monitor/dbObjMgr/toMysqlServer?flag=choose&dbmsMoId='+moid;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(classID==81){	
		var uri= path +'/monitor/sybaseDbManage/toSybaseServerList?flag=chooseForThreshold&dbmsMoId='+moid;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(classID==86){	
		var uri= path +'/monitor/msDbManage/toMsServerList?flag=choose&dbmsMoId='+moid;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(classID==44){	
		var uri= path +'/monitor/envManager/toReaderList?flag=choose?parentID='+moid;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	
	if(classID==54 || moClassID==55 || moClassID==56){	
		if(moClassID == 23 || moClassID == 57){
			var uri= path +'/monitor/db2Manage/toDb2InfoList?flag=choose&dbmsMoId='+moid;
		}else{
			var uri= path +'/monitor/db2Manage/toDb2InstanceList?flag=choose&dbmsMoId='+moid;
		}
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
}

/**
 * 获得采集周期的默认值
 */
function getCollectPeriodVal(moClassId){
	var path = getRootName();
	var uri = path + "/monitor/configObjMgr/initCollectPeriodVal?moClassId="+moClassId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if(data!= -1){
				$("#collectPeriod").val(data);
			}
			initInfo();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询阅读器信息
 */
function findMOReadInfo(){
	var sourceMOID = $("#ipt_moReadMoId").val();
//	console.log("阅读器id===="+sourceMOID);
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/envManager/findMOReaderInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.readerLabel);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * 查询电子标签
 */
function findMOTagInfo(){
	var moID = $("#ipt_moTagMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/envManager/findMOTagInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.tagID);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

function setMiddleUrl(classID){
	var ip = $("#deviceip").val();
	if(classID == 20){
		$("#ipt_url").val("http://"+ip+":"+"8080");
	}else if(classID == 19 ){
		$("#ipt_url").val("http://"+ip+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
	}else if(classID == 53 ){
		$("#ipt_url").val("http://"+ip+":"+"7001"+"/console/login/LoginForm.jsp");
	}
}

function urlClick(){
	var ip = $("#deviceip").val();
	var classID = $("#moClassId").val();
	var url = $("#ipt_url").val();
	if(url == "" || url == null || url == ''){
		if(classID == 20){
			$("#ipt_url").val("http://"+ip+":"+"8080");
		}else if(classID == 19 ){
			$("#ipt_url").val("http://"+ip+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
		}else if(classID == 53 ){
			$("#ipt_url").val("http://"+ip+":"+"7001"+"/console/login/LoginForm.jsp");
		}
	}
}

function doForClick(value,index){
	var classID = $("#moClassId").val();
	if(value==51 && (classID == 19 || classID == 20 || classID == 53)){
		var ip = $("#deviceip").val();
		var port = $("#port").val();
		var middleWareType = "websphere";
		if(classID ==19){
			middleWareType = "websphere";
		}else if(classID ==20){
			middleWareType = "tomcat";
		}else if(classID ==53){
			middleWareType = "weblogic";
		}
		var path=getRootName();
		var uri = path + "/monitor/configObjMgr/getMiddleWareCommunity";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"ipAddress" : ip,
			"middleWareType" : middleWareType,
			"port" : port,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			var url = data.url;
			if (url==null || url == "" || url =='') {
				$.messager.alert("提示", "请先配置凭证的控制台URL！", "info");
				var id = "#ipt_mo"+index;
//				console.log("id==="+id)
				$(id).attr("checked",false);
			}
		}
		};
		ajax_(ajax_param);
	}
}

/**
 * DB2数据库实例资源
 * @return
 */
function findDB2InsInfo(){
	var sourceMOID = $("#ipt_db2InsMoId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/db2Manage/findDB2InsInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.instancename);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * DB2数据库资源（作为源对象）
 * @return
 */
function findDB2DbInfo(){
//	console.log("isMoid===源对象");
	var sourceMOID = $("#ipt_db2DbMoId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/db2Manage/findDB2DbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.databaseName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * DB2数据库资源(作为管理对象)
 * @return
 */
function findDB2DbInfo2(){
//	console.log("isMoid===true");
	var moID = $("#ipt_db2DbMoId2").val();
//	console.log("moID==="+moID);
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/db2Manage/findDB2DbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.databaseName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * db2缓冲池
 * @return
 */
function findDb2BufferPoolInfo(){
	var moID = $("#ipt_db2BufferPoolMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/db2Manage/findDb2BufferPoolInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.bufferPoolName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * db2表空间
 * @return
 */
function findDB2TBSInfo(){
	var moID = $("#ipt_db2TbsMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/db2Manage/findDB2TBSInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.tbsName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}


/**
 * msSqlServer
 */
function findMsSqlServerInfo(){
	var sourceMOID = $("#ipt_msServerMoId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/msDbManage/findMsSqlServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.serverName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * SyBaseServer
 */
function findSybaseServerDetail(){
	var sourceMOID = $("#ipt_sybaseServerMoId").val();
	$("#sourceMOID").val(sourceMOID);
	var path = getRootName();
	var uri=path+"/monitor/sybaseDbManage/findSyBaseServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : sourceMOID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_sourceMOName").val(data.serverName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * sybase数据库设备
 * @return
 */
function findSybaseDeviceInfo(){
	var moID = $("#ipt_sybaseDeviceMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/sybaseDbManage/findSybaseDeviceInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.deviceName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 * sybase数据库
 * @return
 */
function findSybaseDatabase(){
	var moID = $("#ipt_sybaseDbMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/sybaseDbManage/findSybaseDatabase";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.databaseName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 *  msSql数据库设备信息
 * @return
 */
function findMsDeviceInfo(){
	var moID = $("#ipt_msDeviceMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/msDbManage/findMsDeviceInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.deviceName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}

/**
 *  msSql数据库信息
 */
function findMsSQLDbInfo(){
	var moID = $("#ipt_msSQLDbMoId").val();
	$("#ipt_moID").val(moID);
	var path = getRootName();
	var uri=path+"/monitor/msDbManage/findMsSQLDatabaseInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moName").val(data.databaseName);
			isShowClearBtn();
		}
	};
	ajax_(ajax_param);
}