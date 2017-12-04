$(document).ready(function() {
	var scopeID = $("#scopeID").val();
	initDomainIPScopeDetail(scopeID);
});

/*
 * 初始化信息
 */
function initDomainIPScopeDetail(scopeID){
	var path=getRootName();
	var uri=path+"/monitor/domainipscope/initDomainIPScopeDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"scopeID":scopeID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#ipt_domainID").val(data.domainName);
				$("#ipt_domainID").attr("alt",data.domainID);
				$("#ipt_domainDescr").val(data.domainDescr);
				$("#ipt_startIPStr").val(data.ip1);
				$("#ipt_scopeType").val(data.scopeType);
//				$("input:radio[name='scopeType']").eq(data.scopeType).attr("checked",'checked');
				var flag = data.scopeType;
				if(flag ===1){
					$("input:radio[name='scopeType'][value=1]").attr("checked",'checked');
				}else{
					$("input:radio[type=radio][value=2]").attr("checked",'checked');
				}
				
				if(data.scopeType==1 || data.scopeType == "1"){
					$("#show1").hide();
					$("#show2").show();
				}else if(data.scopeType==2 || data.scopeType == "2"){
					$("#show1").show();
					$("#show2").hide();
				}
				$("#ipt_endIPStr").val(data.ip2);
				$("#ipt_network").val(data.ip2);
			}
		};
	ajax_(ajax_param);
}

/**
 * 根据所选范围类型不一样，显示对应的页面
 */
function edit(){
	var radioChecked = $(':radio:checked');
	$('#ipt_scopeType').val(radioChecked.val());
	//1:子网 2：IP范围
	if(radioChecked.val()=='1'){
		$("#show1").hide();
		$("#show2").show();
	}else if(radioChecked.val()=='2'){
		$("#show1").show();
		$("#show2").hide();
	}
	$("#ipt_endIPStr").val("");
	$("#ipt_network").val("");
}

function setNetwork(){
	var endIP = $("#ipt_endIPStr").val();
	$("#ipt_network").val(endIP);
}

function setEndip(){
	var network = $("#ipt_network").val();
	$("#ipt_endIPStr").val(network);
}

/*
 * 选择对象类型
 */
var treeLst=[];
function choseDomainTree(){
		var path = getRootPatch();
		var uri = path + "/platform/managedDomain/findManagedDomainTreeVal";
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
				dataTreeOrg.add(0, -1, "选择管理域", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].domainId;
					var _name = gtmdlToolList[i].domainName;
					var _parent = gtmdlToolList[i].parentId;
					dataTreeOrg.add(_id, _parent, _name, "javascript:hiddenMObjectTreeSetValEasyUi('divDomain','ipt_domainID','"
							+ _id + "','" + _name + "');");
				}
				$('#dataDomainTreeDiv').empty();
				$('#dataDomainTreeDiv').append(dataTreeOrg + "");
				$('#divDomain').dialog('open');
			}
		}
		ajax_(ajax_param);
}

/*
 * 选择隐藏树，是否显示宿主设备
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	getDomainDesc(showId);
}

/*
 * 获得管理域描述
 */
function getDomainDesc(showId) {
	$("#ipt_domainDescr").val("");
	var path = getRootName();
	var uri = path + "/monitor/domainipscope/getDomainDesc?domainID="+showId;
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
			$("#ipt_domainDescr").val(data);
		}
	};
	ajax_(ajax_param);
}

/*
 * 验证IP格式
 */
function checkIP(flag) {
	var message = "";
	if(flag == 1){
		var deviceIP = $("#ipt_startIPStr").val();
		 message = "起始ip地址错误，请填写正确的起始ip地址";
	}else if(flag == 2){
		var deviceIP = $("#ipt_endIPStr").val();
		message = "终止ip地址错误，请填写正确的终止ip地址";
	}else if(flag == 3){
		var deviceIP = $("#ipt_network").val();
		message = "网络掩码错误，请填写正确的网络掩码";
	}
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", message, "error", function(e) {
			$("#ipt_startIPStr").focus();
			if(flag == 1){
				$("#ipt_startIPStr").focus();
			}else if(flag == 2){
				$("#ipt_endIPStr").focus();
			}else if(flag == 3){
				$("#ipt_network").focus();
			}
		});
		return false;
	}
	return true;
}

/**
 * 验证ip范围是否有效
 * @return
 */
function checkIPScope(){
	var startIPStr = $("#ipt_startIPStr").val();
	var endIPStr = $("#ipt_endIPStr").val();
	if (judge_ip(startIPStr) > judge_ip(endIPStr)) {
		$.messager.alert("提示","请输入有效的IP地址范围！","error"); 
		$("#ipt_endIPStr").focus();
		return false;
	}else{
		return true;
	}
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
//    console.log(ip+"========="+ num);
    return num;
}

function toUpdate(){
	var result = checkInfo("#tblDomainIPScopeModify");
//	console.log("是否为空："+result);
	if(result == true){
		var checkStartIPRS = checkIP(1);
		if(checkStartIPRS==true){
			var scopeType = $('input[name="scopeType"]:checked').val();
			
			if(scopeType ==1 || scopeType == "1"){//子网
				var checkNetwork = checkIP(3);
				if(checkNetwork==true){
					checkForUpdate();
				}
			}else if(scopeType ==2 || scopeType == "2"){//ip范围
				var checkEndIPRS = checkIP(2);
				if(checkEndIPRS==true){
					var ipScope = checkIPScope();
					if(ipScope == true){
						checkForUpdate();
					}
				}
			}
		}
	}
}

/**
 * 验证是否已经存在
 */
function checkForUpdate(){
	var scopeID = $("#scopeID").val();
	var scopeType = $('input[name="scopeType"]:checked').val();
	if(scopeType ==1 || scopeType == "1"){
		var ip2 = $("#ipt_network").val();
	}else if(scopeType ==2 || scopeType == "2"){
		var ip2 = $("#ipt_endIPStr").val();
	}
	var path = getRootName();
	var uri = path + "/monitor/domainipscope/checkExist?flag=update";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"scopeID" : scopeID,
			"ip1" :$("#ipt_startIPStr").val(),
			"ip2" : ip2,
			"domainID" : $("#ipt_domainID").attr("alt"),
			"scopeType" : scopeType,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				updateDomainIPScope();
			} else {
				$.messager.alert("提示", "该管理域IP范围已经存在！", "error");
			}

		}
	};
	ajax_(ajax_param);
}

/**
 * 更新
 */
function updateDomainIPScope(){
	var scopeID = $("#scopeID").val();
	var scopeType = $('input[name="scopeType"]:checked').val();
	if(scopeType ==1 || scopeType == "1"){
		var ip2 = $("#ipt_network").val();
	}else if(scopeType ==2 || scopeType == "2"){
		var ip2 = $("#ipt_endIPStr").val();
	}
	var path = getRootName();
	var uri = path + "/monitor/domainipscope/updateDomainIPScope";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"scopeID" : scopeID,
			"ip1" :$("#ipt_startIPStr").val(),
			"ip2" : ip2,
			"domainID" : $("#ipt_domainID").attr("alt"),
			"scopeType" : scopeType,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "管理域IP范围更新成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "管理域IP范围更新失败！", "error");
			}

		}
	};
	ajax_(ajax_param);
}