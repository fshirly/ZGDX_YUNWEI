$(document).ready(function() {
	$("#viewSnmp").hide();
	$("#monitorTitle").hide();
	$("#tblChooseTemplate").hide();
	
	//snmp凭证加载完成，展示表格
	$('#tblSNMPCommunity').datagrid({  
	    onLoadSuccess:function(data){  
		//console.log("加载完成。。。。。");
			$("#viewSnmp").show();  
				
	}  
	})  
	var snmpVersion = $("#ipt_snmpVersion").val();
	var readCommunity = $("#ipt_readCommunity").val();
	var writeCommunity = $("#ipt_writeCommunity").val();
	var snmpPort = $("#ipt_snmpPort").val();
	if (snmpPort == 0) {
		$("#ipt_snmpPort").val('161');
	}
	if (readCommunity == '') {
		$("#ipt_readCommunity").val('public');
	}
	if (writeCommunity == '') {
		$("#ipt_writeCommunity").val('public');
	}
	snmpPort = $("#ipt_snmpPort").val();
	readCommunity = $("#ipt_readCommunity").val();
	writeCommunity = $("#ipt_writeCommunity").val();
	$("#ipt_readCommunity").val(readCommunity);
	$("#ipt_writeCommunity").val(writeCommunity);
	$("#ipt_snmpPort").val(snmpPort);
	
	var vmwarePort = $("#ipt_port").val();
	if (vmwarePort == 0) {
		$("#ipt_port").val('443');
	}
	
	if ($("#ipt_dbmsType").val() == "mysql") {
		$("#ipt_dbPort").val(3306);
		$("#isMysql").show();
		$("#isOracle").hide();
	} else if ($("#ipt_dbmsType").val() == "oracle") {
		$("#ipt_dbPort").val(1521);
		$("#isMysql").hide();
		$("#isOracle").show();
	} else if ($("#ipt_dbmsType").val() == "db2") {
		$("#ipt_dbPort").val(50000);
		$("#isMysql").hide();
		$("#isOracle").show();
	}
	
	var roomPort = $("#roomPort").val('6580');
	if (roomPort == 0) {
		$("#roomPort").val('6580');
	}
	
});

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
					//97-机房监控   96-空调  73-ups
					if(_id !=97 && _id !=96 && _id !=73){
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_classID','"
							+ _id + "','"+ className + "','" + _nameTemp + "');");
					}
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
					$("#authsTitle").show();
					$("#testDiv").hide();
					$("#className").val(className);
					$("#ipt_deviceIP").val("");
					$("#ipt_httpUrl").val("");
					$("#ipt_dnsIPAddr").val("");
					$("#ipt_ftpIPAddr").val("");
					$("#ipTilte").show();
					$("#ipInput").show();
					$("#sucessTip").hide();
					$("#errorTip").hide();
					$("#isShowFtpcom").hide();
					$("#objTypeTr").removeAttr("style");
					$("#monitorTitle").hide();
					$("#tblChooseTemplate").hide();
					$("#monitor  tr:not(:first)").remove();
					if(showId == 15 || showId ==16 || showId ==54  || showId ==81 || showId ==86){
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").show();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#roomAuth").hide();
//						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").show();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("dbms");
						if(showId == 15 ){
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbPort").val("1521");
							$("#isMysql").hide();
							$("#isOracle").show();
						}else if(showId == 16 ){
							$("#ipt_mysqlDbmsType").val(showVal);
							$("#ipt_dbName").val("mysql");
							$("#ipt_dbPort").val("3306");
							$("#isMysql").show();
							$("#isOracle").hide();
						}else  if(showId == 54 ){
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbPort").val("50000");
							$("#isMysql").hide();
							$("#isOracle").show();
						}else  if(showId == 81 ){
							$("#ipt_mysqlDbmsType").val(showVal);
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbName").val("sybase");
							$("#ipt_dbPort").val("5000");
							$("#isMysql").show();
							$("#isOracle").hide();
						}else  if(showId == 86	 ){
							$("#ipt_mysqlDbmsType").val(showVal);
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbName").val("mssql");
							$("#ipt_dbPort").val("1433");
							$("#isMysql").show();
							$("#isOracle").hide();
						}
						
					}else if(showId == 8 || showId == 75){
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").show();
						$("#middleAuth").hide();
						$("#roomAuth").hide();
//						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").show();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("Vmware");
						$("#ipt_port").val('443');
					}else if(showId == 19 || showId == 20 || showId == 53){
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").show();
						$("#roomAuth").hide();
//						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").show();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("middle");
						if(showId == 19 ){
							$("#isShowUserAndPwd").hide();
//							$("#middlePortTr").css({'float':'left'});
							$("#ipt_middlePort").val("8880");
						}else if(showId == 20 ){
							$("#isShowUserAndPwd").hide();
//							$("#middlePortTr").css({'float':'left'});
							$("#ipt_middlePort").val("8999");
						}else if(showId == 53 ){
							$("#isShowUserAndPwd").show();
//							$("#middlePortTr").removeAttr("style");
							$("#ipt_middlePort").val("7001");
						}
						$("#ipt_url").val("");
					}else if(showId == 44){
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#roomAuth").show();
//						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").show();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("room");
						$("#ipt_roomPort").val('6580');
					}
					else if(showId == 93){//http
						$("#objTypeTr").css({'float':'left'});
						$("#ipTilte").hide();
						$("#ipInput").hide();
						$("#testDiv").show();
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#authsTitle").hide();
						$("#auths").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").show();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("site");
						$("#tblChooseTemplate").show();
						$('#ipt_templateID').combobox({
							panelHeight : '120',
							url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId=93',
							valueField : 'templateID',
							textField : 'templateName',
							editable : false,
							onSelect:function(record){  
								doInitDBMoList();
							},
							onLoadSuccess:function(){
								$("#ipt_templateID").combobox('setValue', -1);
							}
						});
						doInitDBMoList();
					}
					else if(showId == 91){//dns
						$("#objTypeTr").css({'float':'left'});
						$("#ipTilte").hide();
						$("#ipInput").hide();
						$("#testDiv").show();
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#authsTitle").hide();
						$("#auths").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").show();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").hide();
						$("#authType").val("site");
						$("#tblChooseTemplate").show();
						$('#ipt_templateID').combobox({
							panelHeight : '120',
							url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId=91',
							valueField : 'templateID',
							textField : 'templateName',
							editable : false,
							onSelect:function(record){  
								doInitDBMoList();
							},
							onLoadSuccess:function(){
								$("#ipt_templateID").combobox('setValue', -1);
							}
						});
						doInitDBMoList();
					}
					else if(showId == 92){//ftp
						$("#objTypeTr").css({'float':'left'});
						$("#ipTilte").hide();
						$("#ipInput").hide();
						$("#testDiv").show();
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#authsTitle").hide();
						$("#auths").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").show();
						$("#tblSiteTcp").hide();
						$("#authType").val("site");
						$("#ipt_ftpPort").val("21");
						var isAuth = $('input[name="isAuth"]:checked').val();
						if (isAuth=='1'){
							$("#isShowFtpcom").hide();
						}else {
							$("#isShowFtpcom").show();
						}
						$("#tblChooseTemplate").show();
						$('#ipt_templateID').combobox({
							panelHeight : '120',
							url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId=92',
							valueField : 'templateID',
							textField : 'templateName',
							editable : false,
							onSelect:function(record){  
								doInitDBMoList();
							},
							onLoadSuccess:function(){
								$("#ipt_templateID").combobox('setValue', -1);
							}
						});
						doInitDBMoList();
					}
					else if(showId == 94){//tcp
						$("#objTypeTr").css({'float':'left'});
						$("#ipTilte").hide();
						$("#ipInput").hide();
						$("#testDiv").show();
						$("#snmpAuth").hide();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#authsTitle").hide();
						$("#auths").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
						$("#tblSiteTcp").show();
						$("#authType").val("site");
						$("#tblChooseTemplate").show();
						$('#ipt_templateID').combobox({
							panelHeight : '120',
							url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId=94',
							valueField : 'templateID',
							textField : 'templateName',
							editable : false,
							onSelect:function(record){  
								doInitDBMoList();
							},
							onLoadSuccess:function(){
								$("#ipt_templateID").combobox('setValue', -1);
							}
						});
						doInitDBMoList();
					}
					else{
						$("#snmpAuth").show();
						$("#viewSnmp").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#middleAuth").hide();
						$("#roomAuth").hide();
//						$("#tblSNMPCommunityInfo").show();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#tblMiddlewareCommunity").hide();
						$("#tblRoomCommunity").hide();
						$("#tblSiteHttp").hide();
						$("#tblSiteDNS").hide();
						$("#tblSiteFTP").hide();
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
						
						initSnmpTable();
						$("#viewSnmp").show();
					}
				}
			}
		};
		ajax_(ajax_param);	
}

/**
 * 加载设备信息列表页面
 * 
 * @return
 */
function loadDeviceInfo() {
	var path = getRootName();
	var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose";
	window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");

}

/**
 * 设备信息
 * @return
 */
function findDeviceInfo() {
	var moID = $("#ipt_deviceId").val();
	var path = getRootName();
	var uri = path + "/monitor/alarmmgr/moKPIThreshold/findDeviceInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		dateType : "json",
		data : {
			"moID" : moID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#ipt_deviceIP").val(data.deviceip);
//			console.log($("#ipt_deviceId").val());
		}
	};
	ajax_(ajax_param);
}

/*
 * SNMP凭证中，根据协议版本选择展示表单
 */
function isOrnoCheck() {
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

/*
 * 数据库凭证，展示默认的端口号
 */
function showDefaultPort(){
	if ($("#ipt_dbmsType").val() == "mysql") {
		$("#ipt_dbPort").val(3306);
	} else if ($("#ipt_dbmsType").val() == "oracle") {
		$("#ipt_dbPort").val(1521);
	}
}


/* 
 * SNMP验证：验证表单信息
 * 
 */
function checkSnmpForm() {
	var checkControlAttr = new Array('ipt_deviceIP', 'ipt_readCommunity',
			'ipt_writeCommunity');
	var checkMessagerAttr = new Array('请先选择设备！', '请输入读Community！',
			'请输入写Community！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

function checkForm(){
	var checkControlAttr = new Array('ipt_deviceIP', 'ipt_readCommunity',
	'ipt_writeCommunity');
var checkMessagerAttr = new Array('请先选择设备！', '请输入读Community！',
	'请输入写Community！');
return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

function toAdd(){
	var authType = $("#authType").val();
	if(authType != "site"){
		var result = checkInfo("#tblAddDevice"); 
		if(result == true){
			//判断认证信息类别
			if(authType=="SNMP"){
				addSnmp();
			}else if(authType=="Vmware"){
				addVmware();
			}else if(authType=="middle"){
				addMiddleCommunity();
			}else if(authType=="room"){
				addRoomCommunity();
			}else{
				addDBMSCommunity();
			}
		}
	}else{
		var classID = $("#ipt_classID").attr("alt");
		if(classID==null || classID==""){
			$.messager.alert("提示", "对象类型不能为空！", "error");
		}else{
			toAddSite();
		}
	}
}

/*
 * 验证SNMP验证中该设备是否存在
 */
function checkSnmpCommunity() {
	var readCommunity = $("#ipt_readCommunity").val();
	var snmpVersion = $("#ipt_snmpVersion").val();
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/checkAddSnmp";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"readCommunity" : readCommunity,
			"snmpVersion" : snmpVersion,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该凭证已存在！", "error");
			} else {
				doAddSnmp();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 验证Vmware验证中该设备是否存在
 */
function checkAddAuthCommunity(flag) {
	var result = checkInfo("#tblAuthCommunityInfo");
	var deviceIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/platform/sysVMIfCommunity/checkAddSyaAuthCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deviceIP" : deviceIP,
			"flag" : flag,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "该设备IP在Vmware验证中已存在！", "error");
			} else {
				addVmware();
				return;
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
}

/*
 * 验证数据库验证中该设备是否存在
 */
function checkAddDBMSCommunity() {
	var result = checkInfo("#tblDBMSCommunity");
	var deviceIP = $("#ipt_deviceIP").val();
	var flag = 3;
	if (checkIP(flag)) {
		var classID = $("#ipt_classID").attr("alt");
		if(classID == 16){
			var dbmsType = $("#ipt_mysqlDbmsType").val();
			var dbName = "mysql";
		}else{
			var dbmsType = $("#ipt_dbmsType").val();
			var dbName = $("#ipt_dbName").val();
		}
//		console.log("dbmsType=="+dbmsType+",dbName=="+dbName);
		var path = getRootName();
		var uri = path + "/monitor/addDevice/checkAddDBMSCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ip" : deviceIP,
				"dbName" :dbName,
				"dbmsType" : dbmsType,
				"userName" : $("#ipt_dbUserName").val(),
				"password" : $("#ipt_dbPassword").val(),
				"port" : $("#ipt_dbPort").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (false == data || "false" == data) {
					$.messager.alert("提示", "该设备IP在数据库验证中已存在！", "error");
				} else {
					addDBMSCommunity();
					return;
				}
			}
		};
		if(result == true){
			ajax_(ajax_param);
		}
	}
}

/**
 * 验证中间件凭证是否存在
 * @return
 */
function checkAddMiddleCommunity(){
	var result = checkInfo("#tblMiddlewareCommunity");
	var deviceIP = $("#ipt_deviceIP").val();
	var middleWareName = 3;
	var classID = $("#ipt_classID").attr("alt");
	if(classID == 19){
		middleWareName = 3;
	}else if(classID == 20){
		middleWareName = 2;
	}else if(classID == 53){
		middleWareName = 1;
	}
	var flag = 4;
	if(result == true){
		if (checkIP(flag)) {
			var path = getRootName();
			var uri = path + "/monitor/MiddleWareCommunity/checkCommunity?flag=add";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"ipAddress" : deviceIP,
					"middleWareName" : middleWareName,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (false == data || "false" == data) {
						$.messager.alert("提示", "该IP在中间件凭证中已存在！", "error");
					} else {
						addMiddleCommunity();
						return;
					}
				}
			};
			ajax_(ajax_param);
		}
	}
}
/*
 * 验证IP格式
 */
function checkIP(flag) {
	var deviceIP = $("#ipt_deviceIP").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e) {
			$("#ipt_deviceIP").focus();
		});
		return false;
	}
	var port =null;
	var message = "";
	if(flag == 1){
		port = $("#ipt_snmpPort").val();
		message = "SNMP端口只能输入正整数！";
	}else if(flag == 2){
		port = $("#ipt_port").val();
		message = "VMware登录端口只能输入正整数！";
	}else if(flag == 3){
		port = $("#ipt_dbPort").val();
		message = "数据库凭证端口只能输入正整数！";
	}else if(flag == 4){
		port = $("#ipt_middlePort").val();
		message = "中间件凭证端口只能输入正整数！";
	}else if(flag == 5){
		port = $("#ipt_roomPort").val();
		message = "机房环境监控凭证端口只能输入正整数！";
	}
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port)) && port != "") {
		$.messager.alert("提示", message, "info", function(e) {
			if(flag == 1){
				$("#ipt_snmpPort").focus();
			}else if(flag == 2){
				$("#ipt_port").focus();
			}else if(flag == 3){
				$("#ipt_dbPort").focus();
			}else if(flag == 4){
				$("#ipt_middlePort").focus();
			}else if(flag == 5){
				$("#ipt_roomPort").focus();
			}else if(flag == 5){
				$("#ipt_roomPort").focus();
			}
			
		});
		return false;
	}
	return true;
}

function checkPort(flag){
	var port =null;
	var message = "";
	if(flag == 1){
		port = $("#ipt_snmpPort").val();
		message = "SNMP端口只能输入正整数！";
	}else if(flag == 2){
		port = $("#ipt_port").val();
		message = "VMware登录端口只能输入正整数！";
	}else if(flag == 3){
		port = $("#ipt_dbPort").val();
		message = "数据库凭证端口只能输入正整数！";
	}else if(flag == 4){
		port = $("#ipt_middlePort").val();
		message = "中间件凭证端口只能输入正整数！";
	}else if(flag == 5){
		port = $("#ipt_roomPort").val();
		message = "机房环境监控凭证端口只能输入正整数！";
	}else if(flag == 6){
		port = $("#ipt_roomPort").val();
		message = "FTP端口号只能输入正整数！";
	}
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", message, "info", function(e) {
			if(flag == 1){
				$("#ipt_snmpPort").focus();
			}else if(flag == 2){
				$("#ipt_port").focus();
			}else if(flag == 3){
				$("#ipt_dbPort").focus();
			}else if(flag == 4){
				$("#ipt_middlePort").focus();
			}else if(flag == 5){
				$("#ipt_roomPort").focus();
			}else if(flag == 6){
				$("#ipt_ftpPort").focus();
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

/*			
 * 新增（SNMP认证）
 */
function addSnmp(){
	var deviceIP = $("#ipt_deviceIP").val();
//	var snmpCommunity = $('#tblSNMPCommunity').datagrid('getSelected');
	
//	console.log("snmpCommunity ==="+snmpCommunity);
//	var snmpId = snmpCommunity.id;
//	var snmpPort = snmpCommunity.snmpPort;
//	if (snmpCommunity.readCommunity == null || snmpCommunity.readCommunity =="") {
//		$.messager.alert('提示', '没有任何选中项', 'error');
//	}else{
		var path = getRootName();
		var classID = $("#ipt_classID").attr("alt");
		var moClassNames = $("#className" ).val();
		var uri = path + "/monitor/addDevice/addSnmpCommunity?moClassNames="+moClassNames+"&deviceIP="+deviceIP;
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
			if (true == data || "true" == data) {
				$.messager.alert("提示", "监测对象添加成功！", "info");
				$('#popWin').window('close');
//			var flag = $("#flag").val();
//			if(flag == true || "true"==flag){
				window.frames['component_2'].reloadTable();
//			}else{
//				$('#tblDeviceTask').datagrid('load');
//			}
//			window.reloadTableContractChange();
//			$('#popWin').window('close');
			} else {
				$.messager.alert("提示", "监测对象添加失败！", "error");
			}
			
		}
		};
		ajax_(ajax_param);
//	} 
}

/*
 * 新增（Vmware认证）
 */
function addVmware() {
	var flag = 2;
	var isExsitVmware = $("#isExsitVmware").val();
	var result = checkInfo("#tblAuthCommunityInfo");
	if (checkIP(flag)) {
		var moClassNames = $("#className" ).val();
		var path = getRootName();
		var uri = path + "/monitor/addDevice/addVmware?moClassNames="+moClassNames+"&isExsitVmware="+isExsitVmware;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deviceIP" : $("#ipt_deviceIP").val(),
				"authType" : 3,
				"port" : $("#ipt_port").val(),
				"moID" : $("#ipt_deviceId").val(),
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "监测对象添加成功！", "info");
					$('#popWin').window('close');
//					var flag = $("#flag").val();
//					if(flag == true || "true"==flag){
						window.frames['component_2'].reloadTable();
//					}else{
//						$('#tblDeviceTask').datagrid('load');
//					}
				} else {
					$.messager.alert("提示", "监测对象加失败！", "error");
				}

			}
		};
		if(result==true){
			ajax_(ajax_param);
		}
	}
}

/*
 * 新增（数据库认证）
 */
function addDBMSCommunity(){
	var result = checkInfo("#tblDBMSCommunity");
	var db2CommunityId = $("#db2CommunityId").val();
	if(db2CommunityId =="" || db2CommunityId ==null){
		db2CommunityId = -1;
	}
	var flag = 3;
	if (checkIP(flag)) {
		var classID = $("#ipt_classID").attr("alt");
		if(classID == 16 ){
			var dbmsType = $("#ipt_mysqlDbmsType").val();
			var dbName = "mysql";
		}else if(classID == 81 ){
			var dbmsType = $("#ipt_mysqlDbmsType").val();
			var dbName = "sybase";
		}else if(classID == 86 ){
			var dbmsType = $("#ipt_mysqlDbmsType").val();
			var dbName = "msSql";
		}else{
			var dbmsType = $("#ipt_dbmsType").val();
			var dbName = $("#ipt_dbName").val();
		}
//		console.log("dbmsType=="+dbmsType+",dbName=="+dbName);
		var path = getRootName();
		var moClassNames = $("#className" ).val();
		var isExistDB = $("#isExistDB").val();
		var uri = path + "/monitor/addDevice/addDBMSCommunity?moClassNames="+moClassNames+"&isExistDB="+isExistDB+"&db2CommunityId="+db2CommunityId;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" :db2CommunityId,
				"ip" : $("#ipt_deviceIP").val(),
				"dbName" : dbName,
				"dbmsType" : dbmsType,
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
					$.messager.alert("提示", "监测对象添加成功！", "info");
					$('#popWin').window('close');
//					var flag = $("#flag").val();
//					if(flag == true || "true"==flag){
						window.frames['component_2'].reloadTable();
//					}else{
//						$('#tblDeviceTask').datagrid('load');
//					}
				} else {
					$.messager.alert("提示", "监测对象添加失败！", "error");
				}

			}
		};
		if(result == true){
			ajax_(ajax_param);
		}
	}
}

/**
 * 新增（JMX凭证）
 * @return
 */
function addMiddleCommunity(){
	$("#btnSave").attr('disabled', true);
	var result = checkInfo("#tblMiddlewareCommunity");
	var userName = "";
	var passWord = "";
	var middleWareName = 3;
	var middleWareType = "websphere";
	var classID = $("#ipt_classID").attr("alt");
	if(classID == 19){
		middleWareName = 3;
		middleWareType = "websphere";
	}else if(classID == 20){
		middleWareName = 2;
		middleWareType = "tomcat";
	}else if(classID == 53){
		middleWareName = 1;
		middleWareType = "weblogic";
		userName = $("#ipt_middleUserName").val();
		passWord = $("#ipt_middlePassWord").val();
	}
	var flag = 4;
	if(result == true){
		if (checkIP(flag)) {
			var path = getRootName();
			var moClassNames = $("#className" ).val();
			var isExistMiddle = $("#isExistMiddle").val();
			var uri = path + "/monitor/addDevice/addMiddleCommunity?moClassNames="+moClassNames+"&isExistMiddle="+isExistMiddle;
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"ipAddress" :$("#ipt_deviceIP").val(),
						"middleWareName" : middleWareName,
						"middleWareType" : middleWareType,
						"port" : $("#ipt_middlePort").val(),
						"userName" : userName,
						"passWord" : passWord,
						"url" : $("#ipt_url").val(),
						"t" : Math.random()
					},
					error : function() {
						$("#btnSave").removeAttr("disabled");
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "监测对象添加成功！", "info");
							$('#popWin').window('close');
//							var flag = $("#flag").val();
//							if(flag == true || "true"==flag){
								window.frames['component_2'].reloadTable();
								$("#btnSave").removeAttr("disabled");
//							}else{
//								$('#tblDeviceTask').datagrid('load');
//							}
//							window.reloadTableContractChange();
//							window.frames['component_2'].reloadTable();
						} else {
							$("#btnSave").removeAttr("disabled");
							$.messager.alert("提示", "监测对象添加失败！", "error");
						}

					}
				};
			ajax_(ajax_param);
		}
	}
}
/*
 * 填写对象IP前先选择对象类型
 */
function isClass(){
	var classID = $("#ipt_classID").attr("alt");
	if(classID==null || classID==""){
		$.messager.alert("提示", "请先选择对象类型！", "error");
	}
}

/*
 * 对象类型为数据库时，验证对象IP是已经发现的
 */
function isDiscovered(){
	var deviceIP = $("#ipt_deviceIP").val();
	if(deviceIP!=null && deviceIP!=""){
		if(checkDeviceIP(deviceIP)){
			var classID = $("#ipt_classID").attr("alt");
			if(classID==15 || classID==16 || classID==54  || classID==81 || classID==86 || classID==19 || classID==20 || classID==53 || classID==44){
				if(deviceIP!=null && deviceIP!=""){
					var path = getRootName();
					var uri = path + "/monitor/addDevice/isDiscovered";
					var ajax_param = {
							url : uri,
							type : "post",
							datdType : "json",
							data : {
						"deviceip" : $("#ipt_deviceIP").val(),
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (false == data || "false" == data) {
							$.messager.alert("提示", "该对象IP还未发现,请重新填写！", "error");
							$('#ipt_deviceIP').val("");
							$('#ipt_deviceIP').focus();
						}else{
							innitCommunity(classID);
						}
					}
					};
					ajax_(ajax_param);
				}
			}else if(classID==8){
				var path = getRootName();
				var uri = path + "/monitor/addDevice/isVCenter";
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"deviceip" : $("#ipt_deviceIP").val(),
					"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (false == data || "false" == data) {
							$.messager.alert("提示", "该对象IP的vCenter设备已经发现,请重新填写！", "error");
							$('#ipt_deviceIP').val("");
							$('#ipt_deviceIP').focus();
						}else{
							initVmwareCommunity();
						}
					}
				};
				ajax_(ajax_param);
			}else if(classID==75){
				initVmwareCommunity();
			}
			else{
				var deviceIP = $('#ipt_deviceIP').val();
				if(deviceIP != ""){
					initSnmpTable();
					$("#viewSnmp").show();
				}
			}
		}
	}
	
}

function checkDeviceIP(deviceIP){
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "对象IP格式错误，请填写正确的对象IP！", "info", function(e) {
			$("#ipt_deviceIP").val("");
			$("#ipt_deviceIP").focus();
		});
		return false;
	}
	return true;
}

function innitCommunity(classID){
	if(classID==15 || classID==16  || classID==81 || classID==86 ){
		initDBCommunity(classID);
	}else if(classID==44){
		initRoomCommunity();
	}else if(classID==19 || classID==20 || classID==53){
		initMiddleCommunity();
	}
}



/**
 * 初始化VMware
 * @return
 */
function initVmwareCommunity(){
	var deviceIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/monitor/addDevice/initVmwareCommunity";
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
			$("#isExsitVmware").val(data.isExsitVmware);
			if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
//				iterObj(data.vmwareCommunity,"ipt");
				var vmwareCommunity = data.vmwareCommunity;
				$("#ipt_deviceIP").val(deviceIP);
				$("#ipt_port").val(vmwareCommunity.port);
				$("#ipt_deviceId").val(vmwareCommunity.moID);
				$("#ipt_userName").val(vmwareCommunity.userName);
				$("#ipt_password").val(vmwareCommunity.password);
			} 
		}
	};
	ajax_(ajax_param);
}

/**
 * 初始化数据库
 * @return
 */
function initDBCommunity(classID){
	var deviceIP = $("#ipt_deviceIP").val();
	if(classID == 16 || classID == 81 || classID == 86){
		var dbmsType = $("#ipt_mysqlDbmsType").val();
	}else{
		var dbmsType = $("#ipt_dbmsType").val();
	}
	var path = getRootName();
	var uri = path + "/monitor/addDevice/initDBCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ip" : deviceIP,
			"dbName" : $("#ipt_dbName").val(),
			"dbmsType" : dbmsType,
			"userName" : $("#ipt_dbUserName").val(),
			"password" : $("#ipt_dbPassword").val(),
			"port" : $("#ipt_dbPort").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#isExistDB").val(data.isExistDB);
			var dbCommunity = data.dbCommunity;
			var docs = document.getElementById('ipt_dbPort');
			if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
				$("#ipt_deviceIP").val(deviceIP);
				$("#ipt_dbName").val(dbCommunity.dbName);
				$("#ipt_dbmsType").val(dbCommunity.dbmsType);
				$("#ipt_mysqlDbmsType").val(dbCommunity.dbmsType);
				$("#ipt_dbUserName").val(dbCommunity.userName);
				$("#ipt_dbPassword").val(dbCommunity.password);
				$("#ipt_dbPort").val(dbCommunity.port);
				//docs.readOnly = true;
			} else{
				//docs.readOnly = false;
			}
		}
	};
	ajax_(ajax_param);
}

function initDB2Community(){
	var deviceIP = $("#ipt_deviceIP").val();
	if(deviceIP != null && deviceIP != ""){
		var dbmsType = $("#ipt_dbmsType").val();
		var dbName= $("#ipt_dbName").val();
//		console.log("dbName="+$("#ipt_dbName").val());
		var path = getRootName();
		var uri = path + "/monitor/addDevice/initDB2Community?dbName="+dbName;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ip" : deviceIP,
				"dbmsType" : dbmsType,
				"userName" : $("#ipt_dbUserName").val(),
				"password" : $("#ipt_dbPassword").val(),
				"port" : $("#ipt_dbPort").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				$("#isExistDB").val(data.isExistDB);
				var dbCommunity = data.dbCommunity;
				var docs = document.getElementById('ipt_dbPort');
				if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
					$("#ipt_deviceIP").val(deviceIP);
					$("#ipt_dbName").val(dbCommunity.dbName);
					$("#ipt_dbmsType").val(dbCommunity.dbmsType);
					$("#ipt_mysqlDbmsType").val(dbCommunity.dbmsType);
					$("#ipt_dbUserName").val(dbCommunity.userName);
					$("#ipt_dbPassword").val(dbCommunity.password);
					$("#ipt_dbPort").val(dbCommunity.port);
					$("#db2CommunityId").val(dbCommunity.id);
					//docs.readOnly = true;
				} else{
					//docs.readOnly = false;
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 初始化中间件
 * @return
 */
function initMiddleCommunity(){
	var deviceIP = $("#ipt_deviceIP").val();
	var middleWareType = "websphere";
	var classID = $("#ipt_classID").attr("alt");
	if(classID == 19){
		middleWareType = "websphere";
	}else if(classID == 20){
		middleWareType = "tomcat";
	}else if(classID == 53){
		middleWareType = "weblogic";
	}
	var path = getRootName();
	var uri = path + "/monitor/addDevice/initMiddleCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : deviceIP,
			"middleWareType" : middleWareType,
			"port" : $("#ipt_middlePort").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#isExistMiddle").val(data.isExistMiddle);
			var middleCommunity = data.middleCommunity;
			var docs = document.getElementById('ipt_middlePort');
			
			if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
				$("#ipt_deviceIP").val(deviceIP);
				$("#ipt_middleWareName").val(middleCommunity.middleWareName);
				$("#ipt_middleWareType").val(middleCommunity.middleWareType);
				$("#ipt_middlePort").val(middleCommunity.port);
				$("#ipt_middleUserName").val(middleCommunity.userName);
				$("#ipt_middlePassWord").val(middleCommunity.passWord);
				$("#ipt_url").val(middleCommunity.url);
				//docs.readOnly = true;
			}else{
				//docs.readOnly = false;
				if(classID == 19){
					$("#ipt_url").val("http://"+deviceIP+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
				}else if(classID == 20){
					$("#ipt_url").val("http://"+deviceIP+":"+"8080");
				}else if(classID == 53){
					$("#ipt_url").val("http://"+deviceIP+":"+"7001"+"/console/login/LoginForm.jsp");
				}
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 初始化机房监控
 * @return
 */
function initRoomCommunity(){
	var deviceIP = $("#ipt_deviceIP").val();
	var classID = $("#ipt_classID").attr("alt");
	var path = getRootName();
	var uri = path + "/monitor/addDevice/initRoomCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : deviceIP,
			"port" : $("#ipt_roomPort").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#isExistRoom").val(data.isExistRoom);
			var roomCommunity = data.roomCommunity;
			var docs = document.getElementById('ipt_roomPort');
			if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
				$("#ipt_deviceIP").val(deviceIP);
				$("#ipt_roomPort").val(roomCommunity.port);
				$("#ipt_roomUserName").val(roomCommunity.userName);
				$("#ipt_roomPassWord").val(roomCommunity.passWord);
				//docs.readOnly = true;
			}else{
				//docs.readOnly = false;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增（机房监控环境凭证）
 * @return
 */
function addRoomCommunity(){
	var result = checkInfo("#tblRoomCommunity");
	var flag = 5;
	if(result == true){
		if (checkIP(flag)) {
			var path = getRootName();
			var moClassNames = $("#className" ).val();
			var isExistRoom = $("#isExistRoom").val();
			var uri = path + "/monitor/addDevice/addRoomCommunity?moClassNames="+moClassNames+"&isExistRoom="+isExistRoom;
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"ipAddress" :$("#ipt_deviceIP").val(),
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
							$.messager.alert("提示", "监测对象添加成功！", "info");
							$('#popWin').window('close');
							window.frames['component_2'].reloadTable();
						} else {
							$.messager.alert("提示", "监测对象添加失败！", "error");
						}

					}
				};
			ajax_(ajax_param);
		}
	}
}

/**
 * 重置IP
 * @return
 */
function resetIP(){
	var classID = $("#ipt_classID").attr("alt");
	if(classID==15 || classID==16 || classID==81 || classID==86 || classID==19 || classID==20 || classID==53 || classID==44){
		innitCommunity(classID);
	}else if(classID==54){
		initDB2Community();
	}else if(classID==8 || classID==75){
		initVmwareCommunity();
	}else{
		var deviceIP = $('#ipt_deviceIP').val();
		if(deviceIP != ""){
			initSnmpTable();
			$("#viewSnmp").show();
		}
	}
}

/**
 * 获得snmp凭证
 * @return
 */
function initSnmpTable() {
	var deviceIp = $('#ipt_deviceIP').val();
	var path = getRootName();
	$('#tblSNMPCommunity')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 780,
						height : 260,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
						fitColumns : true,
						url : path + '/monitor/addDevice/listSnmpCommunity?deviceIp='+deviceIp,
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAddSnmp();
							}
						}],
						columns : [ [
//								{
//									field : 'id',
//									checkbox : true
//								},
								{
									field : 'readCommunity',
									title : '读团体名',
									width : 350,
									align : 'center'
								},
								{
									field : 'writeCommunity',
									title : '写团体名',
									width : 350,
									align : 'center'
								},
								{
									field : 'alias',
									title : '别名',
									width : 280,
									align : 'center',
									sortable : true
								},
								{
									field : 'snmpVersion',
									title : '协议版本',
									width : 350,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 0) {
											return 'V1';
										} else if (value == 1) {
											return 'V2';
										} else if (value == 3) {
											return 'V3';
										}
									}
								},
								{
									field : 'snmpPort',
									title : '端口',
									width : 350,
									align : 'center'
								}] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblSNMPCommunity').resizeDataGrid(0, 0, 0, 0);
	});
}

function reloadSnmpTable() {
	reloadTableCommon_1('tblSNMPCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开新增snmp
 * @return
 */
function toAddSnmp(){
	resetForm('tblSNMPCommunityInfo');
	$("#usmUser").hide();
	$("#authAlogrithm").hide();
	$("#encryptionAlogrithm").hide();
	$("#readCommunity").show();
	
	$("#btnAddSnmpNow").unbind();
	$("#btnAddSnmpNow").bind("click", function() {
		checkSnmp();
	});
	$("#btnColseSnmpNow").unbind();
	$("#btnColseSnmpNow").bind("click", function() {
		$('#divAddSnmp').dialog('close');
	});
	$('#divAddSnmp').dialog('open');
	var snmpVersion = $("#ipt_snmpVersion").val();
	var readCommunity = $("#ipt_readCommunity").val();
	var writeCommunity = $("#ipt_writeCommunity").val();
	var snmpPort = $("#ipt_snmpPort").val();
	if (snmpPort == 0) {
		$("#ipt_snmpPort").val('161');
	}
	if (readCommunity == '') {
		$("#ipt_readCommunity").val('public');
	}
	if (writeCommunity == '') {
		$("#ipt_writeCommunity").val('public');
	}
	snmpPort = $("#ipt_snmpPort").val();
	readCommunity = $("#ipt_readCommunity").val();
	writeCommunity = $("#ipt_writeCommunity").val();
	$("#ipt_readCommunity").val(readCommunity);
	$("#ipt_writeCommunity").val(writeCommunity);
	$("#ipt_snmpPort").val(snmpPort);
	
}

/**
 * 校验是否存在
 * @return
 */
function checkSnmp(){
	var flag = 1;
	var snmpVersion = $("#ipt_snmpVersion").val();
//	console.log("snmpVersion=="+snmpVersion);
	var checkFormRS = true;
	var checkKeyRS = true;
	if(snmpVersion==0 || snmpVersion==1){
//		console.log("readCommunity=="+checkInfo('#readCommunity'));
//		console.log("snmpPort=="+checkInfo('#snmpPort'));
		checkFormRS = checkInfo('#readCommunity')&& checkInfo('#snmpPort');
	}else if(snmpVersion == 3){
		checkFormRS = checkInfo('#usmUser')&& checkInfo('#snmpPort');
		var authAlogrithm= $("#ipt_authAlogrithm").val();
//		console.log("authAlogrithm == "+authAlogrithm);
		if(authAlogrithm != -1){
			checkKeyRS = checkKey();
		}
	}
	
	if (checkFormRS == true) {
		if (checkKeyRS == true) {
			if(snmpVersion==0 || snmpVersion==1){
				checkSnmpCommunity();
			}else{
				doAddSnmp();
			}
			
		}
	}
}

/**
 * 新增凭证
 * @return
 */
function doAddSnmp(){
	if (checkIP(1)) {
		var path = getRootName();
		var classID = $("#ipt_classID").attr("alt");
		var moClassNames = $("#className" ).val();
		var uri = path + "/platform/snmpCommunity/addSnmpCommunity"
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"alias" : $("#ipt_alias").val(),
			"readCommunity" : $("#ipt_readCommunity").val(),
			"writeCommunity" : $("#ipt_writeCommunity").val(),
			"snmpPort" : $("#ipt_snmpPort").val(),
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
				$.messager.alert("提示", "添加凭证成功！", "info");
				reloadSnmpTable()
				$('#divAddSnmp').dialog('close');
			} else {
				$.messager.alert("提示", "添加凭证失败！", "error");
			}
			
		}
		};
		ajax_(ajax_param);
	}
}

/**
 * ftp是否匿名
 */
function edit(){
	var isAuth = $('input[name="isAuth"]:checked').val();
	if (isAuth=='1'){
		$("#isShowFtpcom").hide();
	}else {
		$("#isShowFtpcom").show();
		initFTPCommunity();
	}
}

/**
 * 初始化ftp凭证信息
 * @return
 */
function initFTPCommunity(){
	var deviceIP = $("#ipt_ftpIPAddr").val();
	if(deviceIP != null && deviceIP != ""){
		var path = getRootName();
		var uri = path + "/monitor/addDevice/initFTPCommunity";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"ipAddress" : deviceIP,
			"port" : $("#ipt_ftpPort").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#isExistSite").val(data.isExistSite);
			var siteCommunity = data.siteCommunity;
			var docs = document.getElementById('ipt_dbPort');
			if (true == data.isExistSite || "true" == data.isExistSite) {
				$("#siteCommunityId").val(siteCommunity.id);
				$("#ipt_ftpUserName").val(siteCommunity.userName);
				$("#ipt_ftpPassWord").val(siteCommunity.password);
				$("#ipt_ftpPort").val(siteCommunity.port);
			} else{
				$("#siteCommunityId").val("");
				$("#ipt_ftpUserName").val("");
				$("#ipt_ftpPassWord").val("");
				$("#ipt_ftpPort").val(21);
			}
		}
		};
		ajax_(ajax_param);
	}
}

/**
 * 初始化http凭证信息
 * @return
 */
function initHttpCommunity(){
	var deviceIP = $("#ipt_httpUrl").val();
	if(deviceIP != null && deviceIP != ""){
		var path = getRootName();
		var uri = path + "/monitor/addDevice/initHttpCommunity";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"ipAddress" : deviceIP,
			"siteType" : 2,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#isExistSite").val(data.isExistSite);
			var siteCommunity = data.siteCommunity;
			var docs = document.getElementById('ipt_dbPort');
			if (true == data.isExistSite || "true" == data.isExistSite) {
				$("#siteCommunityId").val(data.id);
				var requestMethod = siteCommunity.requestMethod;
				if(requestMethod == 1){
					$("input:radio[name='requestMethod'][value=1]").attr("checked",'checked');
				}else if(requestMethod == 2){
					$("input:radio[name='requestMethod'][value=2]").attr("checked",'checked');
				}else if(requestMethod == 3){
					$("input:radio[name='requestMethod'][value=3]").attr("checked",'checked');
				}
			} else {
				$("#siteCommunityId").val("");
			}
		}
		};
		ajax_(ajax_param);
	}
}

/**
 * 站点测试
 * @return
 */
function getTestSite(){
	var checkSiteResult = checkSiteInfo();
	if(checkSiteResult == true){
		var moClassID = $("#ipt_classID").attr("alt");
		var ftpPort = $("#ipt_ftpPort").val();
		var tcpPort = $("#ipt_tcpPort").val();
		if(moClassID == 92){
			if(ftpPort > 2147483647){
				 $.messager.alert('提示', 'FTP端口号最大值不能超过2147483647!', 'info');
			        return false;
			}
		} else{
			ftpPort = 0;
		}
		//tcp
		if(moClassID == 94){
			if(tcpPort > 2147483647){
				 $.messager.alert('提示', 'TCP端口号最大值不能超过2147483647!', 'info');
			     return false;
			}
		} else{
			tcpPort = 0;
		}
		var path = getRootName();
		var uri = path + "/monitor/addDevice/testSite";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"moClassID" :moClassID,
					"siteHttp.httpUrl" : $("#ipt_httpUrl").val(),
					"siteHttp.requestMethod" : $('input[name="requestMethod"]:checked').val(),
					
					"siteDns.domainName" : $("#ipt_domainName").val(),
					"siteDns.ipAddr" : $("#ipt_dnsIPAddr").val(),
					
					"siteFtp.ipAddr" : $("#ipt_ftpIPAddr").val(),
					"siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
					"siteFtp.userName" : $("#ipt_ftpUserName").val(),
					"siteFtp.password" : $("#ipt_ftpPassWord").val(),
					
					"sitePort.ipAddr" : $("#ipt_tcpIPAddr").val(),
					"sitePort.port" : tcpPort == '' ? 0 : tcpPort,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$("#sucessTip").show();
						$("#errorTip").hide();
					} else {
						$("#sucessTip").hide();
						$("#errorTip").show();
					}

				}
			};
		ajax_(ajax_param);
	}
}

/**
 * 校验站点模块及站点名称验证
 * @return
 */
function checkSiteInfo(){
	var moClassID = $("#ipt_classID").attr("alt");
	var siteType = "";
	var checkIPResult = true;
	var siteName = "";
	var result
	//dns
	if(moClassID == 91){
		siteType = 2;
		siteName = $("#ipt_dnsSiteName").val();
		var result = checkInfo("#tblSiteDNS");
	}
	//ftp
	else if(moClassID == 92){
		siteType = 1;
		siteName = $("#ipt_ftpSiteName").val();
		var isAuth = $('input[name="isAuth"]:checked').val();
//		console.log("isAuth=="+isAuth);
		if(isAuth == 1){
			var result = checkInfo("#tblSiteFTP");
		}else{
			var result = checkInfo("#tblSiteFTP") && checkInfo("#isShowFtpcom");
		}

	}
	//http
	else if(moClassID == 93){
		siteType = 3;
		siteName = $("#ipt_httpSiteName").val();
		var result = checkInfo("#tblSiteHttp");
	}
	//http
	else if(moClassID == 94){
		siteType = 4;
		siteName = $("#ipt_tcpSiteName").val();
		var result = checkInfo("#tblSiteTcp");
	}
	
	if(result == true && checkIPResult == true){
		return true;
	}else{
		return false;
	}
}

/**
 * 新增站点
 */
function toAddSite(){
	var checkSiteResult = checkSiteInfo();
//	console.log("checkSiteResult=="+checkSiteResult);
	if(checkSiteResult == true){
		checkSiteName();
	}
}

/**
 * 校验站点名称
 * @return
 */
function checkSiteName(){
	var moClassID = $("#ipt_classID").attr("alt");
	var siteName = "";
	var ftpPort = $("#ipt_ftpPort").val();
	var tcpPort = $("#ipt_tcpPort").val();
	//dns
	if(moClassID == 91){
		siteName = $("#ipt_dnsSiteName").val();
		ftpPort = 0;
	}
	//ftp
	else if(moClassID == 92){
		siteName = $("#ipt_ftpSiteName").val();
		if(ftpPort > 2147483647){
			 $.messager.alert('提示', 'FTP端口号最大值不能超过2147483647!', 'info');
		        return false;
		}
	}
	//http
	else if(moClassID == 93){
		siteName = $("#ipt_httpSiteName").val();
		ftpPort = 0;
	}
	//tcp
	else if(moClassID == 94){
		siteName = $("#ipt_tcpSiteName").val();
		if(tcpPort > 2147483647){
			 $.messager.alert('提示', 'TCP端口号最大值不能超过2147483647!', 'info');
		     return false;
		}
	}
	var path = getRootName();
	var uri = path + "/monitor/addDevice/checkSiteName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moClassID" :moClassID,
			"siteName":siteName,
			"siteHttp.httpUrl" : $("#ipt_httpUrl").val(),
			
			"siteDns.domainName" : $("#ipt_domainName").val(),
			
			"siteFtp.ipAddr" : $("#ipt_ftpIPAddr").val(),
			"siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
					
			"sitePort.ipAddr" : $("#ipt_tcpIPAddr").val(),
			"sitePort.port" : tcpPort == '' ? 0 : tcpPort,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data.checkNameFlag || "false" == data.checkNameFlag) {
				$.messager.alert("提示", "该站点名称已存在！", "error");
				return false;
			}else if (false == data.checkUrlFlag || "false" == data.checkUrlFlag) {
				$.messager.alert("提示", "该监控地址已存在！", "error");
				return false;
			} else {
				doAddSite();
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增站点
 * @return
 */
function doAddSite(){
	var moClassID = $("#ipt_classID").attr("alt");
	var siteType = 1;
	var ftpPort = $("#ipt_ftpPort").val();
	var tcpPort = $("#ipt_tcpPort").val();
	//dns
	if(moClassID == 91){
		siteType = 2;
		siteName = $("#ipt_dnsSiteName").val();
		ftpPort = 0;
		tcpPort = 0;
	}
	//ftp
	else if(moClassID == 92){
		siteType = 1;
		siteName = $("#ipt_ftpSiteName").val();
		tcpPort = 0;
	}
	//http
	else if(moClassID == 93){
		siteType = 3;
		siteName = $("#ipt_httpSiteName").val();
		ftpPort = 0;
		tcpPort = 0;
	}
	//tcp
	else if(moClassID == 94){
		siteType = 4;
		siteName = $("#ipt_tcpSiteName").val();
		ftpPort = 0;
	}
	var isExistSite = $("#isExistSite").val();
	var siteCommunityId = $("#siteCommunityId").val();
	if(siteCommunityId == null || siteCommunityId == ""){
		siteCommunityId = -1;
	}
	
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
	} else{
		for ( var int = 0; int < arrChk.length; int++) {
			molist+=arrChk[int]+",";
			mointerval+=$("#ipt_doIntervals"+arrChk[int]).val()+",";
			var select=$("#ipt_timeUnit"+arrChk[int]).val();
			selectList.push(select);
		}
		for ( var int = 0; int < selectList.length; int++) {
			motimeunit+=selectList[int]+",";
		}
		
		var templateID=$("#ipt_templateID").combobox('getValue');
		var moTypeLstJson =$("#moTypeLstJson").val();
		var monitorCheck = checkInfo('#monitor');
		if(monitorCheck == true){
			var path = getRootName();
			var uri = path + "/monitor/addDevice/addSite?isExistSite="+isExistSite+"&siteCommunityId="+siteCommunityId+"&templateID="+templateID+"&moTypeLstJson="+moTypeLstJson;
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
				"moClassID" :moClassID,
				"siteType":siteType,
				"domainID":1,
				
				"moList" : molist,
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				
				"siteHttp.siteName" : $("#ipt_httpSiteName").val(),
//				"siteHttp.period" : $("#ipt_httpPeriod").val(),
				"siteHttp.httpUrl" : $("#ipt_httpUrl").val(),
				"siteHttp.requestMethod" : $('input[name="requestMethod"]:checked').val(),
				"siteHttp.siteInfo" : $("#ipt_httpSiteInfo").val(),
				
				
				"siteDns.siteName" : $("#ipt_dnsSiteName").val(),
				"siteDns.domainName" : $("#ipt_domainName").val(),
//				"siteDns.period" : $("#ipt_dnsPeriod").val(),
				"siteDns.ipAddr" : $("#ipt_dnsIPAddr").val(),
				"siteDns.siteInfo" : $("#ipt_dnsSiteInfo").val(),
				
				"siteFtp.siteName" : $("#ipt_ftpSiteName").val(),
				"siteFtp.ipAddr" : $("#ipt_ftpIPAddr").val(),
				"siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
//				"siteFtp.period" : $("#ipt_ftpPeriod").val(),
				"siteFtp.siteInfo" : $("#ipt_ftpSiteInfo").val(),
				"siteFtp.isAuth" : $('input[name="isAuth"]:checked').val(),
				"siteFtp.userName" : $("#ipt_ftpUserName").val(),
				"siteFtp.password" : $("#ipt_ftpPassWord").val(),
				
				"sitePort.siteName" : $("#ipt_tcpSiteName").val(),
				"sitePort.ipAddr" : $("#ipt_tcpIPAddr").val(),
				"sitePort.port" : tcpPort == '' ? 0 : tcpPort,
				"sitePort.siteInfo" : $("#ipt_tcpSiteInfo").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "监测对象添加成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					$.messager.alert("提示", "监测对象添加失败！", "error");
				}
				
			}
			};
			ajax_(ajax_param);
		}
		
	}
}

/**
 * 获取监测器（数据库）
 * @return
 */
function doInitDBMoList(){
	var templateID=$("#ipt_templateID").combobox('getValue');
	if(templateID == null || templateID == ""){
		templateID = -1;
	}
	var moClassID = $("#ipt_classID").attr("alt");
	$("#monitor  tr:not(:first)").remove();
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
			"moClassId":moClassID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data.length>0){
				$("#monitorTitle").show();
			}else{
				$("#monitorTitle").hide();
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
//		console.log(moTypeLstJson);
//		console.log("length=="+data.length);
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