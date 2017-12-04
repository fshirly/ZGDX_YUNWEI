$(function(){
	edit();
	initTree();
	initDeviceName(7);
});

/**
 * 根据moClassId获取第一个主机信息
 * @param moClassId
 * @return
 */
function initDeviceName(moClassId){
	var path=getRootName();
	var uri=path+"/monitor/gridsterEdit/initDeviceName?moClassId="+moClassId;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.deviceip);
		$("#ipt_deviceId").val(data.moid);
		$("#ipt_moClassId").val(data.moClassId);
		var moClass = 'host';
		if(moClassId == 8){
			moClass = 'vhost';
		}
		if(moClassId == 9){
			moClass = 'vm';
		}
		if(moClassId == 5){
			moClass = 'router';
		}
		if(moClassId == 6){
			moClass = 'switcher';
		}
		if(moClassId == 59){
			moClass = 'switcherl2';
		}
		if(moClassId == 60){
			moClass = 'switcherl3';
		}
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+moClass;
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}


/**
 * 根据parentMoClassId获取设备接口信息
 * @param moClassId
 * @return
 */
function initInterfaceName(parentMoClassId){
	var path=getRootName();
	var uri=path+"/monitor/gridsterEdit/initInterfaceName?parentMoClassId="+parentMoClassId;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val("设备"+data.deviceIP+"_接口"+data.moName);
		$("#ipt_deviceId").val(data.deviceMOID);
		$("#ipt_moClassId").val(data.moClassId);
		$("#ipt_ifId").val(data.moID);
		var moClass = 'host';
		if(parentMoClassId == 8){
			moClass = 'vhost';
		}
		if(parentMoClassId == 9){
			moClass = 'vm';
		}
		if(parentMoClassId == 5){
			moClass = 'router';
		}
		if(parentMoClassId == 6){
			moClass = 'switcher';
		}
		if(parentMoClassId == 59){
			moClass = 'switcherl2';
		}
		if(parentMoClassId == 60){
			moClass = 'switcherl3';
		}
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+moClass+"&IfMOID="+$("#ipt_ifId").val();
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}


/**
 * 根据moClassId获取第一个中间件信息
 * @param moClassId
 * @return
 */
function initMiddleName(jmxTypeId){
	var path=getRootName();
	var jmxType='websphere';
	if(jmxTypeId == 20){
		jmxType='tomcat';
	}
	if(jmxTypeId == 53){
		jmxType='weblogic';
	}
	var uri=path+"/monitor/gridsterEdit/initMiddleName?jmxType="+jmxType;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.ip);
		$("#ipt_deviceId").val(data.moId);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+jmxType;
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}


/**
 * 根据moClassId获取第一个数据库
 * @param moClassId
 * @return
 */
function initDbName(dbmsTypeId){
	var path=getRootName();
	var dbmstype='oracle';
	if(dbmsTypeId == 16){
		dbmstype='mysql';
	}
	if(dbmsTypeId == 54){
		dbmstype='db2';
	}
	var uri=path+"/monitor/gridsterEdit/initDbName?dbmstype="+dbmstype;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.ip);
		$("#ipt_deviceId").val(data.moid);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+dbmstype;
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}

/**
 * 根据moClassId获取第一个DB2实例信息
 * @param moClassId
 * @return
 */
function initDb2InsName(){
	var path=getRootName();
	var uri=path+"/monitor/db2Manage/initDb2InstanceName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.instancename);
		$("#ipt_deviceId").val(data.moid);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=db2_instance";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}

/**
 * 根据moClassId获取第一个DB2数据库信息
 * @param moClassId
 * @return
 */
function initDb2dbName(){
	var path=getRootName();
	var uri=path+"/monitor/db2Manage/initDb2dbName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.databaseName);
		$("#ipt_deviceId").val(data.moId);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=db2_db";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}

function initMsSqlServerName(){
	var path=getRootName();
	var uri=path+"/monitor/msManage/initMsServerName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.serverName);
		$("#ipt_deviceId").val(data.dbmsMoid);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=mssqlserver";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}

function initSybaseServerName(){
	var path=getRootName();
	var uri=path+"/monitor/sybaseManage/initSybaseServerName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.serverName);
		$("#ipt_deviceId").val(data.dbmsMoid);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=sybaseserver";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);	
}

function initMsSqlDbName(){
	var path=getRootName();
	var uri=path+"/monitor/msDbManage/initMsSqlDbName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.databaseName);
		$("#ipt_deviceId").val(data.moId);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=mssqldb";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);

}

function initSybaseDbName(){
	var path=getRootName();
	var uri=path+"/monitor/sybaseDbManage/initSybaseDbName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.databaseName);
		$("#ipt_deviceId").val(data.moId);
		var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass=sybasedatabase";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);

}
//获取第一个连接池
function initJdbcPoolName(id,jmxTypeId){
	var path=getRootName();
	var jmxType='websphere';
	if(jmxTypeId == 20){
		jmxType='tomcat';
	}
	if(jmxTypeId == 53){
		jmxType='weblogic';
	}
	var uri=path+"/monitor/db2Manage/initJdbcPoolName?jmxType="+jmxType;
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if (data.dsName == null || data.dsName == "") {
			$("#ipt_deviceIp").val(data.ip);
		} else {
			$("#ipt_deviceIp").val(data.dsName);
		}
		
		$("#ipt_deviceId").val(data.moId);
		var urlParams = "moID="+$('#ipt_deviceId').val();
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}
/**
 * 保存视图模板
 * @return
 */
function save(){
	var portalName=$('#ipt_portalName').val();
	if(portalName == ""){
		var newPortalName=$('#portalName').val();
		var path=getRootName();
		var uri=path+"/monitor/gridsterEdit/addPortalInfo";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"portalContent":$("#ipt_portalContent").val(),
					"portalName":newPortalName,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==true){
				$.messager.alert("提示","视图配置成功！","info");
			}else{
				$.messager.alert("提示","视图配置失败！","error");
			}
		}
		};
			ajax_(ajax_param);
	}else{
		var path=getRootName();
		var uri=path+"/monitor/gridsterEdit/modifyPortalInfo";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"portalName":portalName,
					"portalContent":$("#ipt_portalContent").val(),
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==true){
				$.messager.alert("提示","视图配置成功！","info");
			}else{
				$.messager.alert("提示","视图配置失败！","error");
			}
		}
		};
			ajax_(ajax_param);
	}
	
}

function doBack(){
	var portalName = $('#ipt_portalName').val();
	if(portalName == ''){
		$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
	}else{
		var path = getRootPatch();
		var uri = path + "/monitor/gridsterEdit/showOnePortal";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"portalName" : portalName,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error"); 
			},
			success : function(data) {
				if(data == ""){
					$('#ipt_portalName').val("");
					$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
				}else{
					$('#ipt_portalName').val(portalName);
					$("#ipt_portalContent").val(data.portalContent);
				}
				
			}
		}
		ajax_(ajax_param);
	}
	
}

function toShowWidget(){
	var path=getRootName();
	var uri=path+"/monitor/gridsterEdit/toShowWidget";
	var widgetFilter=$('#portalName').val();
	/*if(widgetFilter != ""){
		var params=widgetFilter.split("&");
		for ( var int = 0; int < params.length; int++) {
			if(params[int].split("=")[0] == "moClass"){
				widgetFilter = params[int].split("=")[1];
			}
		}
		uri=path+"/monitor/gridsterEdit/toShowWidget?widgetFilter="+widgetFilter;
	}*/
	if(widgetFilter != ""){
		uri=path+"/monitor/gridsterEdit/toShowWidget?widgetFilter="+widgetFilter;
	}
	var iWidth=1300; //弹出窗口的宽度; 
	var iHeight=700; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-60-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-20-iWidth)/2; //获得窗口的水平位置;
	window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}



function edit(){
	var radioChecked = $(':radio:checked');
	if(radioChecked.val() === '1'){
		$('#divPortalInfo').show();
		$('#divUploadPortal').hide();
	}
	if(radioChecked.val() === '2'){
		$('#divPortalInfo').hide();
		$('#divUploadPortal').show();
	}
}

/**
 * 文件上传
 * 
 */
function doupload() {
	var path=getRootName();
	var portalName = $('#ipt_portalName').val();
    $.ajaxFileUpload({
		fileElementId : 'file',
		type:'POST',
		url : path+"/monitor/gridsterEdit/uploadPortalInfo",
		dataType : 'json',
		data: {
			"portalName" : portalName
		},
		//contentType : "application/json",
		secureuri : false,
		//fileFilter:"jsp",
		beforeSend : function() {
			if(!portalName){
				$.messager.alert("提示", "请选择用户");
				return false;
			}
			if(!$("#file").val()){
				$.messager.alert("提示", "请选择文件");
				return false;
			}
			return true;
		},
		success : function(data, status) {
			$.messager.alert("提示","上传成功！");
		},
		error : function(data, status) {
			$.messager.alert("提示","上传失败！");		
			}
	});
}


/*function doUploadFile(){
	var portalName=$('#ipt_portalName').val();
	var filePath = $('#ipt_fileupload').val();
//	var obj = document.getElementById("ipt_fileupload");
//	var file_url = obj.files.item(0).getAsDataURL();   
//	alert("file_url="+file_url);
	var path=getRootName();
	var uri=path+"/monitor/gridsterEdit/uploadPortalInfo";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"filePath":filePath,
				"portalName":portalName,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");  
	},
	success:function(data){
		if(data==true){
			$.messager.alert("提示","文件上传成功！","info");
		}else{
			$.messager.alert("提示","文件上传失败！","error");
		}
	}
	};
		ajax_(ajax_param);
	
}*/

/**
 * 上传前验证
 * @param imgFile
 * @return
 */
function initFile(file) {
//	alert($("input[type='file']").val());
	var passfix = file.value.substring(file.value.lastIndexOf(".") + 1,
			file.value.length);
	if(passfix != "xml"){
		$.messager.alert("提示", "对不起，系统仅支持XML"
				+"的文件，请您调整格式后重新上传，谢谢 !", "info");
	}
}


/*
 * 上传过后的回调
 * 
 * @param imgFile
 */
function uploadCallBack(fileObj) {
	$("#"+fileObj.uploadTagId).val(fileObj.filePath);
//	alert($("#"+fileObj.uploadTagId).val());
}

//function toUpload() {
//	if ($("#ipt_fileupload").val() != '') {
//		doUploadFile('ipt_fileupload');
//	} else {
//		$.messager.alert('提示', '未选择上传文件', 'error');
//	}
//}


function getPos(obj){  
	obj.focus();  
	var s=document.selection.createRange();  
	var reg=new RegExp("><","g"); 
	var value=("<widget><chart>sdf</chart></widget>");
	s.text=value;
	var portalContent=document.getElementById("ipt_portalContent");
	portalContent.value=portalContent.value.replace(reg, ">\n<");
	}  

//预览视图
function previewPortal(){
	var path=getRootName();
	var uri=path+"/monitor/portal/initPortalContent";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"portalContent":$("#ipt_portalContent").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");  
	},
	success:function(data){
		if(data==true){
			var urlParams = $('#urlParams').val();
			
			if(urlParams != ''){
				urlParams = "?"+urlParams;
			}
			var uri=path+"/monitor/portal/showPortalView"+urlParams;
//			var iWidth=1300; //弹出窗口的宽度;
//			var iHeight=700; //弹出窗口的高度;
//			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
//			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
//			window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
			window.location = uri;
		}else{
			$.messager.alert("提示","视图格式不对！","error");
		}
	}
	};
		ajax_(ajax_param);
		
	
}



//预览视图
function previewPortal2(){
	var portalName = $('#ipt_portalName').val();
	var path=getRootName();
	var uri=path+"/monitor/gridster/initPortalContent";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"portalContent":$("#ipt_portalContent").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");  
	},
	success:function(data){
		if(data==true){
			var urlParams = $('#urlParams').val();
			if(urlParams != ''){
				urlParams = "?"+urlParams;
			}
			if(portalName == 'Device'){
				urlParams = '';
			}
//			if(portalName == 'Router'){
//				urlParams = '?moClass=router';
//			}
//			if(portalName == 'Switcher'){
//				urlParams = '?moClass=switcher';
//			}
			var uri=path+"/monitor/gridster/showPortalView"+urlParams;
			
			var iWidth = window.screen.availWidth -210; //弹出窗口的宽度190
			var y = window.screen.availHeight-102;
			var y2 = y-29;
			var iHeight = window.screen.availHeight-265; //弹出窗口的高度;
			var iTop = 165;
//				(window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = 188;
//				(window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
		}else{
			$.messager.alert("提示","视图格式不对！","error");
		}
	}
	};
		ajax_(ajax_param);
		
	
}



//树数据
var _treeData = "";
var map = getMap();
var _currentNodeId = -1;
var _currentNodeName = "";

/**
function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
	
}
*/

function treeClickAction(id, name,parentId,relationType) {
	$('#ipt_deviceIp').val("");
	_currentNodeId = id;
	_currentNodeName = name;
	_currentParnetId = parentId;
	_currentRltType = relationType;
	if(id == 7 || id == 8 || id == 9 || id == 5 || id == 6 || id == 59 || id == 60){
		initDeviceName(id);
	}else if(id == 10){
		initInterfaceName(parentId);
	}else if(id == 19 || id == 20){
		initMiddleName(id);
	}else if(id == 15 || id == 16){
		initDbName(id);
	}else if(id == 56){
		initDb2dbName(id);
	}else if(id == 53){
		initMiddleName(id);
	}else if(id == 55){
		initDb2InsName(id);
	}else if(id == 87){
		initMsSqlServerName(id);
	}else if(id == 38){
		initJdbcPoolName(id,parentId);
	}else if(id == 80){
		initMsSqlDbName(id);
	}else if(id == 82){
		initSybaseServerName(id);
	}else if(id == 84){
		initSybaseDbName(id);
	}else if(id == 91){
		initDnsSiteName();
	}else if(id == 92){
		initFtpSiteName();
	}else if(id == 93){
		initHttpSiteName();
	}else if(id == 94){
		initPortSiteName();
	}
	reloadTable();
}


/*
 * 更新界面
 */
function reloadTable() {
	doInitPortal();
}

/**
 * 初始化树菜单
 */

/*var treeLst=[];
function initTree() {
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/initPortalTree";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "设备类型", "javascript:treeClickAction(null,'无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].classId;
				var _name = gtmdlToolList[i].classLable;
				var _parent = gtmdlToolList[i].parentClassId;
				var className = gtmdlToolList[i].className;
				map.put(_id,className);
				treeLst.push(_id);
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' value='定位' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			
			//操作tree对象   
			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}*/



var treeLst=[];
function initTree() {
	var path = getRootPatch();
	var uri = path + "/monitor/deviceManager/initDeviceTree";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "监测对象", "javascript:treeClickAction(1,'监测对象',-1,'');");
			map.put(1,'MO');
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _classId = gtmdlToolList[i].classId;
				var _classLable = gtmdlToolList[i].classLable;
				var _parentClassId = gtmdlToolList[i].parentClassId;
				var _newParentID = gtmdlToolList[i].newParentID;
				var _relationID = gtmdlToolList[i].relationID;
				var _relationPath = gtmdlToolList[i].relationPath;
				var _className = gtmdlToolList[i].className;
				// 97-机房监控   96-空调  73-ups
				if(_classId !=97 && _classId !=96 && _classId !=73 ){
					map.put(_classId,_className);
					treeLst.push(_relationID);
					dataTree.add(_relationID, _newParentID, _classLable, "javascript:treeClickAction('" + _classId + "','" + _classLable + "','" + _parentClassId +"','" + _relationPath + "');");
				}
				
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			
			//操作tree对象   
			//dataTree.openAll();
			//物理主机是7，_relationID=6
			dataTree.openTo(6,true);
			_currentNodeId=7;
			_currentNodeName = '物理主机';
			_currentParnetId = 3;
			_currentRltType = 1;
			doInitPortal();
		}
	}
	ajax_(ajax_param);
}

//菜单定位
/**
function toSerach(treedata){   
	var treeName=$("#treeName").val();
	if(treeName==""){
		_currentNodeId=treeName;
		reloadTable();
	}else{
		var path=getRootName();
		var uri=path+"/monitor/gridster/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"classLable":treeName,    
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data != ""){
				for ( var i = 0; i < data.length; i++) {
					var treeId=data[i].classId;
					for ( var j = 0; j < treeLst.length; j++) {
						if(treeId==treeLst[j]){
							treedata.openTo(treeId,true);
						}
					}
				}
				_currentNodeId=data[0].classId;
				reloadTable();
			}
		}
		};
		ajax_(ajax_param);
	}
	
}
*/
//菜单定位
function toSerach(treedata){   
	var treeName=$("#treeName").val();
	if(treeName==""){
		treeClickAction('2','设备','1','1');
	}else{
		var path=getRootName();
		var uri=path+"/monitor/deviceManager/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"classLable":treeName,    
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data != null && data != ""){				
				var classLable = data.classLable;
				var parentClassId = data.parentClassId;
				var newParentID = data.newParentID;
				var relationID = data.relationID;
				var relationPath = data.relationPath;
				treedata.openTo(relationID,true);
				_currentNodeId=data.classId;
				reloadTable();
			}
		}
		};
		ajax_(ajax_param);
	}
	
}


function initTree2(){
	$("#divId").f_stree({
		   url:url,
		   onSelect: function(node) {
			  //添加执行的功能或行为
		   },
		   parentIdField: 'parentTypeId'
	      });

}

//加载某一个portal模板
function doInitPortal(){
	var portalName=map.get(_currentNodeId);
	if(portalName == 'Interface'){
		var portalParentName = map.get(_currentParnetId);
		portalName = portalParentName+"/"+portalName;
	}
	if(portalName == 'Db2Instance'){
		portalName = 'DB2_INSTANCE';
	}
	if(portalName == 'Db2Db'){
		portalName = 'DB2_DB';
	}
	$('#portalName').val(portalName);
	$("#ipt_ifName").val("");
	var path = getRootPatch();
	var uri = path + "/monitor/gridsterEdit/showOnePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : portalName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == ""){
				$('#ipt_portalName').val("");
				$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
			}else{
				$('#ipt_portalName').val(portalName);
				$("#ipt_portalContent").val(data.portalContent);
			}
			
		}
	}
	ajax_(ajax_param);
	
}

/**
 * 加载设备信息列表页面
 * @return
 */
/**
function loadDeviceInfo(){
	var path=getRootName();
	var uri=path+"/monitor/discover/toDiscoverDeviceList?flag=choose";
	window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}
*/

function loadDeviceInfo(){
	var id = _currentNodeId;
	var name = _currentNodeName;
	var parentId = _currentParnetId;
	var relationType = _currentRltType;
	var path=getRootName();
	if(id=="1"){//表示查询所有的设备
		uri = path+'/monitor/deviceManager/toDeviceList?mOClassID=-1&flag=1';
	}else if(id=="2"){//表示网络设备
		uri = path+'/monitor/deviceManager/toDeviceList?mOClassID=5,6,59,60&flag=1';
	}else if(id=="3"){//表示主机
		uri = path+'/monitor/deviceManager/toDeviceList?mOClassID=7,8,9&flag=1';
	}else if(id=="4" || id=="8"){//表示虚拟设备或虚拟主机
		uri = path+'/monitor/deviceManager/toDeviceList?mOClassID=8&flag=1';
	}else if(id=="10"){//表示接口
		uri = path+'/monitor/deviceManager/toInterfaceList?mOClassID='+parentId+'&flag=1';
	}else if(id=="11"){//表示磁盘
		uri = path+'/monitor/deviceManager/toDiscList?mOClassID='+parentId+'&flag=1';
	}else if(id=="12"){//表示cpu
		uri = path+'/monitor/deviceManager/toCpuList?mOClassID='+parentId+'&flag=1';
	}else if(id=="13"){//表示内存
		uri = path+'/monitor/deviceManager/toMemoryList?mOClassID='+parentId+'&flag=1';	
	}else if(id=="14" || id=="15" || id=="16"){		
		uri = path+'/monitor/dbObjMgr/toDataBaseList?dbmstype='+id+'&flag=1';
	}else if( id=="17" || id=="18" || id=="19" || id=="20"  ){//中间件部份待开发
		uri = path+'/monitor/DeviceTomcatManage/toMiddlewareList?jmxType='+id+'&flag=1';
	}else if( id=="53" ){//weblogic
		uri = path+'/monitor/DeviceTomcatManage/toMiddlewareList?jmxType='+id+'&flag=1';
	}else if( id=="55" ){//DB2实例
		uri = path+'/monitor/db2Manage/toDb2InstanceList?flag=1';
	}else if( id=="56" ){//DB2数据库
		uri = path+'/monitor/db2Manage/toDb2InfoList?flag=1';
	}else if( id=="38" ){//连接池
		uri = path+'/monitor/DeviceTomcatManage/toMiddleWareJdbcPoolList?jmxType='+id+'&flag=1';
	}else if( id=="87" ){//msServer
		uri = path+'/monitor/msDbManage/toMsServerList?flag=1';
	}else if( id=="82" ){//sybaseServer
		uri = path+'/monitor/sybaseDbManage/toSybaseServerList?flag=1';
	}else if( id=="80" ){//msDb
		uri = path+'/monitor/msDbManage/toMsDBList?flag=1';
	}else if( id=="84" ){//sybaseDb
		uri = path+'/monitor/sybaseDbManage/toSybaseDBList?flag=1';
	}else if( id=="91" ){//DNS
		uri = path+'/monitor/webSite/toWebSiteDnsInfo?flag=1';
	}else if( id=="92" ){//FTP
		uri = path+'/monitor/webSite/toWebSiteFtpInfo?flag=1';
	}else if( id=="93" ){//HTTP
		uri = path+'/monitor/webSite/toWebSiteHttpInfo?flag=1';
	}else if( id=="94" ){//TCP
		uri = path+'/monitor/webSite/toWebSitePortInfo?flag=1';
	}
	else{//表示单类型的设备
		uri = path+'/monitor/deviceManager/toDeviceList?mOClassID='+id+'&flag=1';
	}	
	window.open(uri,"","height=500px,width=1000px,left=250,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}

/**
 * 加载数据库信息列表页面
 * @return
 */
function loadOracleInfo(){
	var path=getRootName();
	var uri=path+"/monitor/deviceManager/toDataBaseList?dbmstype=14&flag=1";
	window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}

/**
 * 查找设备及snmp信息
 */
function findDeviceInfo(){
		var deviceId = $("#ipt_deviceId").val();
		var path = getRootName();
		var uri=path+"/monitor/perfTask/findDeviceInfo";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"moId" : deviceId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var moClass="host";
				var portalName=map.get(_currentNodeId);
				if(portalName=="VHost"){
					moClass="vhost";
				}
				if(portalName=="VM"){
					moClass="vm";
				}
				if(portalName=="Oracle"){
					moClass="oracle";
				}
				if(portalName=="Mysql"){
					moClass="mysql";
				}
				if(portalName=="Router"){
					moClass="router";
				}
				if(portalName=="Switcher"){
					moClass="switcher";
				}
				if(portalName=="SwitcherL2"){
					moClass="switcherl2";
				}
				if(portalName=="SwitcherL3"){
					moClass="switcherl3";
				}
				var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+moClass;
				$('#urlParams').val(urlParams);
			}
		};
		ajax_(ajax_param);		
}


/**
 * 查找Oracle数据库信息
 */
/**
function findOracleInfo(){
		var deviceId = $("#ipt_oracleId").val();
		var path = getRootName();
		$('#urlParams').val(urlParams);
		var uri=path+"/monitor/deviceManager/findOracleInfo";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"moid" : deviceId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var moClass="oracle";
				var urlParams = "moID="+$('#ipt_oracleId').val()+"&moClass="+moClass;
				$('#urlParams').val(urlParams);
			}
		};
		ajax_(ajax_param);		
}
*/
function findOracleInfo(){
	var deviceId = $("#ipt_oracleId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/dbObjMgr/findOracleInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.ip);
			var portalName=map.get(_currentNodeId);
			var moClass="oracle";
			if(portalName=="Oracle"){
				moClass="oracle";
			}
			if(portalName=="Mysql"){
				moClass="mysql";
			}
			var urlParams = "moID="+$('#ipt_oracleId').val()+"&moClass="+moClass;
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}



//定义简单Map   
function getMap() {//初始化map_,给map_对象增加方法，使map_像Map     
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

/**
 * 加载设备对应接口列表
 * @return
 */
function loadMoNetqorkIf(){
	var deviceId = $("#ipt_deviceId").val();
	if(deviceId != ""){
		var path=getRootName();
		var uri=path+"/monitor/alarmmgr/moKPIThreshold/toNetworkIf?flag=choose&deviceMOID="+deviceId;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else{
		$.messager.alert('提示', '请先选择设备信息！', 'error');
	}
	
}


/*
 * 查找接口信息
 */
/**
function findNetworkIfInfo(){
	var ifId = $("#ipt_networkIfId").val();
	$("#ipt_ifId").val(ifId);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findNetworkIfInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : ifId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_ifName").val(data.ifName);
			var moClass="host";
			var portalName=map.get($('#ipt_moClassId').val());
			if(portalName=="VHost"){
				moClass="vhost";
			}
			if(portalName=="VM"){
				moClass="vm";
			}
			if(portalName=="Router" || portalName=="Switcher"){
				moClass="network";
			}
			var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+moClass;
			if(map.get(_currentNodeId) == "Interface"){
				urlParams = urlParams+"&IfMOID="+ifId;
			}
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);
}
*/
function findNetworkIfInfo(){
	var ifId = $("#ipt_networkIfId").val();
	$("#ipt_ifId").val(ifId);
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findNetworkIfInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : ifId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_deviceIp").val(data.ifName);
			$("#ipt_deviceId").val(data.deviceMOID);
			var moClass="host";
			var portalName=map.get($('#ipt_moClassId').val());
			if(portalName=="VHost"){
				moClass="vhost";
			}
			if(portalName=="VM"){
				moClass="vm";
			}
			if(portalName=="Router" || portalName=="Switcher"){
				moClass="network";
			}
			var urlParams = "moID="+$('#ipt_deviceId').val()+"&moClass="+moClass;
			if(map.get(_currentNodeId) == "Interface"){
				urlParams = urlParams+"&IfMOID="+ifId;
			}
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);
}

function findMiddleWareInfo(){
	var deviceId = $("#ipt_middleWareId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/deviceManager/findMiddleInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.ip);
			var portalName=map.get(_currentNodeId);
			var moClass="tomcat";
			if(portalName=="Tomcat"){
				moClass="tomcat";
			}
			if(portalName=="Websphere"){
				moClass="websphere";
			}
			var urlParams = "moID="+$('#ipt_middleWareId').val()+"&moClass="+moClass;
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

//DB2实例
function findDB2InsInfo(){
	var db2InsMoId = $("#ipt_db2InsMoId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/db2Manage/findDB2InsInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : db2InsMoId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.instancename);
			var urlParams = "moID="+$('#ipt_db2InsMoId').val()+"&moClass=db2Instance";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

//DB2数据库
function findDB2DbInfo(){
	var db2DbMoId = $("#ipt_db2DbMoId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/db2Manage/findDB2DbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : db2DbMoId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.databaseName);
			var urlParams = "moID="+$('#ipt_db2DbMoId').val()+"&moClass=db2db";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

//查询weblogic
function findMiddleWareInfo(){
	var deviceId = $("#ipt_middleWareId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/DeviceTomcatManage/findMiddleInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.ip);
			var portalName=map.get(_currentNodeId);
			var moClass="tomcat";
			if(portalName=="Tomcat"){
				moClass="tomcat";
			}
			if(portalName=="Websphere"){
				moClass="websphere";
			}
			if(portalName=="Weblogic"){
				moClass="weblogic";
			}
			var urlParams = "moID="+$('#ipt_middleWareId').val()+"&moClass="+moClass;
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

//查询连接池
function findJdbcPoolInfo(){
	var deviceId = $("#ipt_jdbcPoolId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findJdbcPoolInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.dsName);
			var urlParams = "moID="+$('#ipt_jdbcPoolId').val();
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}


function findSybaseServerInfo(){
	var deviceId = $("#ipt_moId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/sybaseManage/findSybaseServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.serverName);
			var urlParams = "moID="+$('#ipt_moId').val()+"&moClass=sybaseserver";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

function findSybaseDbInfo(){
	var deviceId = $("#ipt_moId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/sybaseDbManage/findSybaseDbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.databaseName);
			var urlParams = "moID="+$('#ipt_moId').val()+"&moClass=sybasedatabase";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

function findMsServerInfo(){
	var deviceId = $("#ipt_moId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/msManage/findMsServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.serverName);
			var urlParams = "moID="+$('#ipt_moId').val()+"&moClass=mssqlserver";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}

function findMsSqlDbInfo(){
	var deviceId = $("#ipt_moId").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/msDbManage/findMsSqlDbInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : deviceId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.databaseName);
			var urlParams = "moID="+$('#ipt_moId').val()+"&moClass=mssqldb";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		
}


function findSiteDnsInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/webSite/findSiteDnsInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.siteName);
			var urlParams = "moID="+moId+"&moClass=dns";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		

}


function findSiteFtpInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/webSite/findSiteFtpInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.siteName);
			var urlParams = "moID="+moId+"&moClass=ftp";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		

}

function findSiteHttpInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/webSite/findSiteHttpInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.siteName);
			var urlParams = "moID="+moId+"&moClass=http";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		

}

function findSitePortInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	$('#urlParams').val(urlParams);
	var uri=path+"/monitor/webSite/findSitePortInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$('#ipt_deviceIp').val(data.siteName);
			var urlParams = "moID="+moId+"&moClass=tcp";
			$('#urlParams').val(urlParams);
		}
	};
	ajax_(ajax_param);		

}


function initDnsSiteName(){
	var path=getRootName();
	var uri=path+"/monitor/webSite/initDnsSiteName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.siteName);
		$("#ipt_webSiteMoID").val(data.moID);
		var urlParams = "moID="+$('#ipt_webSiteMoID').val()+"&moClass=dns";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);
}

function initFtpSiteName(){
	var path=getRootName();
	var uri=path+"/monitor/webSite/initFtpSiteName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.siteName);
		$("#ipt_webSiteMoID").val(data.moID);
		var urlParams = "moID="+$('#ipt_webSiteMoID').val()+"&moClass=ftp";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);

}

function initHttpSiteName(){
	var path=getRootName();
	var uri=path+"/monitor/webSite/initHttpSiteName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.siteName);
		$("#ipt_webSiteMoID").val(data.moID);
		var urlParams = "moID="+$('#ipt_webSiteMoID').val()+"&moClass=http";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);

}

function initPortSiteName(){
	var path=getRootName();
	var uri=path+"/monitor/webSite/initPortSiteName";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		$("#ipt_deviceIp").val(data.siteName);
		$("#ipt_webSiteMoID").val(data.moID);
		var urlParams = "moID="+$('#ipt_webSiteMoID').val()+"&moClass=tcp";
		$('#urlParams').val(urlParams);
	}
	};
		ajax_(ajax_param);

}