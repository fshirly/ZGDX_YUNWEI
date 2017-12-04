/**
 * 选择部门树
 */
function choseDeptTree() {
	initDeptTree();
	$('#divChoseDept').dialog('open');
}
/**
 * 部门树
 */
var _initDeptCount = 0;
function initDeptTree() {
	if (0 == _initDeptCount) {
		++_initDeptCount;
		var path = getRootPatch();
		var uri = path + "/permissionDepartment/findDeptLst";
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
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择部门", "");

				// 得到树的json数据源
				var datas = eval('(' + data.deptLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].deptId;
					var _nameTemp = gtmdlToolList[i].deptName;
					var _parent = gtmdlToolList[i].parentDeptID;

					dataTreeOrg.add(_id, _parent, _nameTemp,
							"javascript:hiddenDeptDTreeSetValEasyUi('divChoseDept','ipt_deptId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataDeptTreeDiv').empty();
				$('#dataDeptTreeDiv').append(dataTreeOrg + "");
			}
		}
		ajax_(ajax_param);
	}
}

function hiddenDeptDTreeSetValEasyUi(divId,controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	cancelRedBox(controlId);
}

/**
 * 判断输入的ip是否属于该子网段
 */
function isInSubNet(checkIp){
	var result = false;
	var subNetId = $("#subNetId").val();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/isInSubNet?subNetId="+subNetId+"&checkIp="+checkIp;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async: false,
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			result = data;
		}
	};
	ajax_(ajax_param);
	
	return result;
}

/**
 * IP地址转换为整数
 */
function judge_ip(ip){
	var num = 0;  
    ip = ip.split(".");  
    num = Number(ip[0]) * 256 * 256 * 256 
    	+ Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
    num = num >>> 0;  
    return num;
}

/**
 * 分配到部门
 * @return
 */
function toAssign(){
	var result = checkInfo('#tblSubnetInfo');
	var startIp = $("#ipt_startIp").val();
	var endIp = $("#ipt_endIp").val();
//	console.log("endIp>>"+endIp);
	if(result == true){
		var checkStartIPRS =isInSubNet(startIp);
		if(checkStartIPRS == false || checkStartIPRS == "false"){
			$.messager.alert("错误","输入的起始IP地址不属于该子网，请重新输入!","error"); 
//			$("#ipt_startIp").focus();
			return 
		}else if(!isInSubNet(endIp)){
			$.messager.alert("错误","输入的终止IP地址不属于该子网，请重新输入!","error"); 
//			$("#ipt_startIp").focus();
			return 
		}else if (judge_ip(startIp) > judge_ip(endIp)) {
			$.messager.alert("提示","请输入有效的IP地址范围!","error"); 
			return;
		}else{
			checkIsAllFree(startIp,endIp);
		}
	}
}

/**
 * 判断输入的地址范围是否有已经使用的
 * @return
 */
function checkIsAllFree(startIp,endIp){
	var subNetId = $("#subNetId").val();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/checkIsAllFree?startIp="+startIp+"&endIp="+endIp+"&subNetId="+subNetId;
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
//			console.log("checkIsAllFree  >> "+data.freeIds);
			if (data.isAllFree == true || data.isAllFree == "true") {
				doAssign(data.freeIds,1);
			} else {
				var deptName = $("#ipt_deptId").val();
				if(data.reserveNum == 0 && data.useNum != 0){
					var messageTip = "您所指定的范围，一共有"+data.totalNum+"个地址，其中有"+data.freeNum+"个空闲，"+data.useNum+"个正被预占或者占用，"+
					"您确定要将其中"+data.freeNum+"个空闲的地址分配到"+deptName+"吗？"
				}else if(data.reserveNum != 0 && data.useNum == 0){
					var messageTip = "您所指定的范围，一共有"+data.totalNum+"个地址，其中有"+data.freeNum+"个空闲，"+data.reserveNum+"个为保留地址，"+
					"您确定要将其中"+data.freeNum+"个空闲的地址分配到"+deptName+"吗？"
				}else if(data.reserveNum != 0 && data.useNum != 0){
					var messageTip = "您所指定的范围，一共有"+data.totalNum+"个地址，其中有"+data.freeNum+"个空闲，"+data.useNum+"个正被预占或者占用，"
					+data.reserveNum+"个为保留地址，"+"您确定要将其中"+data.freeNum+"个空闲的地址分配到"+deptName+"吗？"
				}
				$("#messageTip").text(messageTip);
				$("#freeIds").val(data.freeIds);
				assignTipInfo(data.freeIds,messageTip)
//				$('#divUseInfo').window('open');
			}
		}
	};
	ajax_(ajax_param);
}

function assignTipInfo(freeIds,messageTip){
	parent.parent.$('#popWin3').window({
		title:'提示',
		width:600,
		height : 200,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		modal:true,
		href: getRootName() + '/platform/subnetViewManager/toAssignTipInfo?freeIds='+freeIds+'&messageTip='+encodeURI(messageTip)
	});
}

/**
 * 将所有的空闲ip地址分配到部门
 * @return
 */
function doAssign(freeIds,flag){
//	console.log("doAssign=="+freeIds)
	if(freeIds != null && freeIds != ""){
		var deptId = $("#ipt_deptId").attr("alt");
		var subNetIdStr = $("#subNetId").val();
		var subNetId =parseInt(subNetIdStr);
		if(deptId == null || deptId == ""){
			$.messager.alert("提示", "请选择部门！", "info");
		}else{
			var ids = $("#ids").val();
			var path = getRootName();
			var uri = path + "/platform/subnetViewManager/doFreeAssignDept";
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
				"ids":freeIds,
				"deptId" : deptId,
				"subNetId" :subNetId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data == true) {
					$.messager.alert("提示", "分配到部门成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].frames['component_2'].reloadTable();
					window.frames['component_2'].initTree();
					if(flag == 2 || flag == "2"){
						$('#popWin3').window('close');
					}
				} else {
					$.messager.alert("提示", "分配到部门失败！", "error");
				}
			}
			};
			ajax_(ajax_param);
		}
	}else{
		$.messager.alert("提示","没有空闲地址可以分配!","info");
	}
}

/**
 * 关闭弹出
 * @return
 */
function cancle(){
	$('#popWin3').window('close');
}

function viewFreeAddress(freeIds){
//	console.log("viewFreeAddress freeIds=="+freeIds);
	var subNetId = $("#subNetId").val();
	var startIp = $("#ipt_startIp").val();
	var endIp = $("#ipt_endIp").val(); 
	var deptId = $("#ipt_deptId").attr("alt");
	if(freeIds != null && freeIds != ""){
		parent.parent.$('#popWin2').window({
			title:'空闲地址列表',
			width:600,
			height : 460,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			modal:true,
			href: getRootName() + '/platform/subnetViewManager/toViewFreeAddress?subNetId='+subNetId+'&startIp='+startIp+'&endIp='+endIp+'&deptId='+deptId
		});
	}else{
		$.messager.alert("提示","没有空闲地址!","info");
	}
	
}