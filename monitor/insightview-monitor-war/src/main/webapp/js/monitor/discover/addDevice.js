$(document).ready(function() {
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
//	console.log(vmwarePort);
	if (vmwarePort == 0) {
		$("#ipt_port").val('443');
	}
	
	if ($("#ipt_dbmsType").val() == "mysql") {
		$("#ipt_dbPort").val(3306);
	} else if ($("#ipt_dbmsType").val() == "oracle") {
		$("#ipt_dbPort").val(1521);
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
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_classID','"
							+ _id + "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}

//var myAlert=window.alert; 
//window.alert=function(msg){ 
//	myAlert (msg+"-TEST"); 
//} 
/*
 * 选择隐藏树，对应展示需填数据
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, showVal) {
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
					if(showId == 15 || showId ==16){
						$("#snmpAuth").hide();
						$("#dbAuth").show();
						$("#vmwareAuth").hide();
						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").show();
						$("#authType").val("dbms");
						if(showId == 15 ){
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbPort").val("1521");
						}else{
							$("#ipt_dbmsType").val(showVal);
							$("#ipt_dbPort").val("3306");
						}
						
					}else if(showId == 8){
						$("#snmpAuth").hide();
						$("#dbAuth").hide();
						$("#vmwareAuth").show();
						$("#tblSNMPCommunityInfo").hide();
						$("#tblAuthCommunityInfo").show();
						$("#tblDBMSCommunity").hide();
						$("#authType").val("Vmware");
					}else{
						$("#snmpAuth").show();
						$("#dbAuth").hide();
						$("#vmwareAuth").hide();
						$("#tblSNMPCommunityInfo").show();
						$("#tblAuthCommunityInfo").hide();
						$("#tblDBMSCommunity").hide();
						$("#authType").val("SNMP");
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
	var result = checkInfo("#tblAddDevice") 
	if(result == true){
		//判断认证信息类别
		var authType = $("#authType").val();
		if(authType=="SNMP"){
			var flag = "add";
			checkSnmpCommunity(flag);
		}else if(authType=="Vmware"){
			var flag = "add";
			checkAddAuthCommunity(flag);
		}else{
			checkAddDBMSCommunity();
		}
	}
}

/*
 * 验证SNMP验证中该设备是否存在
 */
function checkSnmpCommunity(flag) {
	var result = checkInfo("#tblSNMPCommunityInfo");
	var deviceIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/checkAddSnmp";
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
				$.messager.alert("提示", "该设备IP在SNMP验证中已存在！", "error");
			} else {
				addSnmp();
				return;
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
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
		var path = getRootName();
		var uri = path + "/monitor/addDevice/checkAddDBMSCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ip" : deviceIP,
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
	}else{
		port = $("#ipt_dbPort").val();
		message = "数据库认证端口只能输入正整数！";
	}
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", message, "info", function(e) {
			$("#ipt_snmpPort").focus();
		});
		return false;
	}
	return true;
}

/*
 * 新增（SNMP认证）
 */
function addSnmp(){
	var flag = 1;
	if (checkIP(flag)) {
		var path = getRootName();
		var moClassNames = $("#ipt_classID" ).val();
		var uri = path + "/monitor/addDevice/addSnmpCommunity?moClassNames="+moClassNames;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deviceIP" : $("#ipt_deviceIP").val(),
				"readCommunity" : $("#ipt_readCommunity").val(),
				"writeCommunity" : $("#ipt_writeCommunity").val(),
				"snmpPort" : $("#ipt_snmpPort").val(),
				"moID" : $("#ipt_deviceId").val(),
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
					$.messager.alert("提示", "监测对象添加成功！", "info");
					resetForm('addDevice');
				} else {
					$.messager.alert("提示", "监测对象添加失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/*
 * 新增（Vmware认证）
 */
function addVmware() {
	var flag = 2;
	if (checkIP(flag)) {
		var path = getRootName();
		var uri = path + "/monitor/addDevice/addVmware";
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
					resetForm('addDevice');
				} else {
					$.messager.alert("提示", "监测对象加失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/*
 * 新增（数据库认证）
 */
function addDBMSCommunity(){
	var path = getRootName();
	var moClassNames = $("#ipt_classID" ).val();
	var uri = path + "/monitor/addDevice/addDBMSCommunity?moClassNames="+moClassNames;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ip" : $("#ipt_deviceIP").val(),
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
				$.messager.alert("提示", "监测对象添加成功！", "info");
				resetForm('addDevice');
			} else {
				$.messager.alert("提示", "监测对象添加失败！", "error");
			}

		}
	};
	ajax_(ajax_param);
}

/*
 * 填写对象IP前线选择对象吧类型
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
	var classID = $("#ipt_classID").attr("alt");
	if(classID==15 || classID==16){
		var deviceIP = $("#ipt_deviceIP").val();
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
					return ;
				}
			}
			};
			ajax_(ajax_param);
		}
	}
}