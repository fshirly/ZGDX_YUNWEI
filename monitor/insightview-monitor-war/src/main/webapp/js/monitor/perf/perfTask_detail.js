var path = getRootName();
$(document).ready(function() {
	var taskId=$('#taskId').val();
//	console.log("taskID==="+taskId)
	getAuthTypeForDetail(taskId);
});

/**
 * 判断是哪一种类型
 * @param taskId
 * @return
 */
function getAuthTypeForDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/getAuthType";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				//				alert(data.moClassId);
				var showId = data.moClassId;
				
				if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").show();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("dbms");
					if(showId == 54){
						initDB2Detail(taskId);
					}else{
						initDBDetail(taskId);
					}
					
				}else if(showId == 8 || showId == 75){
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").show();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("Vmware");
					initVMwareDetail(taskId);
				}else if(showId == 19 || showId==20 || showId==53){
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").show();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("MiddleWare");
					initMiddleWareDetail(taskId);
				}else if(showId == 44){
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").show();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("room");
					initRoomDetail(taskId);
				}else if(showId == 91){
					$("#isShowIp").hide();
					$("#isShowSite").show();
					$("#isShowSite2").show();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("site");
					initDnsDetail(taskId);
				}else if(showId == 92){
					$("#isShowIp").hide();
					$("#isShowSite").show();
					$("#isShowSite2").show();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").show();
					$("#tblHttpInfo").hide();
					$("#authType").val("room");
					initFtpDetail(taskId);
				}else if(showId == 93){
					$("#isShowIp").hide();
					$("#isShowSite").show();
					$("#isShowSite2").show();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").show();
					$("#authType").val("room");
					initHttpDetail(taskId);
				}else if(showId == 94){
					$("#isShowIp").hide();
					$("#isShowSite").show();
					$("#isShowSite2").show();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("site");
					initTcpDetail(taskId);
				}else if(showId == -1){
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").hide();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("unKnown");
					setRead(taskId,showId);
				}else{
					$("#isShowIp").show();
					$("#isShowSite").hide();
					$("#isShowSite2").hide();
					$("#tblSnmpInfo").show();
					$("#tblVMwareInfo").hide();
					$("#tblDBMSInfo").hide();
					$("#tblMiddleWareInfo").hide();
					$("#tblRoomInfo").hide();
					$("#tblFtpInfo").hide();
					$("#tblHttpInfo").hide();
					$("#authType").val("SNMP");
					setRead(taskId,showId);
				}
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息详情（DBMS）
 * @param taskId
 * @return
 */
function initDBDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initDBVal";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				if(data.dbmsCommunityBean!=null){
					$("#lbl_dbName").text(data.dbmsCommunityBean.dbName);
					$("#lbl_dbmsType").text(data.dbmsCommunityBean.dbmsType);
					$("#lbl_dbUserName").text(data.dbmsCommunityBean.userName);
					$("#lbl_dbPassword").text(data.dbmsCommunityBean.password);
					$("#lbl_dbPort").text(data.dbmsCommunityBean.port);
					$("#lbl_deviceIp").text(data.dbmsCommunityBean.ip);
					$("#lbl_domainName").text(data.domainName);
					if(data.domainId != -1  && data.domainId != 0 && data.domainId != null){
						$("#lbl_domainName").text(data.domainName);
					}else if(data.domainId == -1){
						$("#lbl_domainName").text("所有");
					}else{
						$("#lbl_domainName").text("");
					}
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化DB2信息详情（DBMS）
 * @param taskId
 * @return
 */
function initDB2Detail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initDB2Val";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				if(data.dbmsCommunityBean!=null){
					$("#lbl_dbName").text(data.dbmsCommunityBean.dbName);
					$("#lbl_dbmsType").text(data.dbmsCommunityBean.dbmsType);
					$("#lbl_dbUserName").text(data.dbmsCommunityBean.userName);
					$("#lbl_dbPassword").text(data.dbmsCommunityBean.password);
					$("#lbl_dbPort").text(data.dbmsCommunityBean.port);
					$("#lbl_deviceIp").text(data.dbmsCommunityBean.ip);
					$("#lbl_domainName").text(data.domainName);
					if(data.domainId != -1  && data.domainId != 0 && data.domainId != null){
						$("#lbl_domainName").text(data.domainName);
					}else if(data.domainId == -1){
						$("#lbl_domainName").text("所有");
					}else{
						$("#lbl_domainName").text("");
					}
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息详情（VMware）
 * @param taskId
 * @return
 */
function initVMwareDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initVmwareVal";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").show();
				iterObj(data,"lbl");
				var moId = data.moId;
				if(data.vmIfCommunityBean!=null){
					$("#lbl_port").text(data.vmIfCommunityBean.port);
					$("#lbl_userName").text(data.vmIfCommunityBean.userName);
					$("#lbl_password").text(data.vmIfCommunityBean.password);
				}
				doInitMoInfo(taskId,moId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息详情（中间件）
 * @param taskId
 * @return
 */
function initMiddleWareDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initMiddleWareDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				if(data.middleWareCommunityBean!=null){
					$("#lbl_middlePort").text(data.middleWareCommunityBean.port);
					$("#lbl_middleUserName").text(data.middleWareCommunityBean.userName);
					$("#lbl_middlePassword").text(data.middleWareCommunityBean.passWord);
					$("#lbl_middleWareType").text(data.middleWareCommunityBean.middleWareType);
					var middleWareName = data.middleWareCommunityBean.middleWareName;
					if(middleWareName==1){
						$("#lbl_middleWareName").text("weblogic");
					}else if(middleWareName==2){
						$("#lbl_middleWareName").text("tomcat");
					}else if(middleWareName==3){
						$("#lbl_middleWareName").text("websphere");
					}
					if(data.middleWareCommunityBean.url != null){
						$("#lbl_url").text(data.middleWareCommunityBean.url);
					}
					if(data.domainId != -1  && data.domainId != 0 && data.domainId != null){
						$("#lbl_domainName").text(data.domainName);
					}else if(data.domainId == -1){
						$("#lbl_domainName").text("所有");
					}else{
						$("#lbl_domainName").text("");
					}
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

//初始化详情信息
function setRead(taskId,showId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/findDeviceByTaskId";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").show();
				iterObj(data,"lbl");
				var moId = data.moId;
				var oTable = document.getElementById('tblSnmpInfo');
				$("#lbl_taskId").text(taskId);
				
				var snmpPort=$("#lbl_snmpPort").val();
				var readCommunity=$("#lbl_readCommunity").val();
//				if(snmpPort==0 || snmpPort==""){
//					$("#lbl_snmpPort").text('161');
//					$("#lbl_snmpPortV3").text('161');
//				}
//				if(readCommunity==''){
//					$("#lbl_readCommunity").text('public');
//					$("#lbl_readCommunityV3").text('public');
//				}
//				snmpPort=$("#lbl_snmpPort").val();
//				readCommunity=$("#lbl_readCommunity").val();
				var moName=$('#lbl_moName').val();
				var moPort=$('#lbl_snmpPort').val();
				var snmpVersion=$("#lbl_snmpVersion").val();
				var manufacturerID = data.manufacturerID;
				if(manufacturerID == 1){
					$("#tblSnmpInfo").hide();
				}else{
					if(snmpVersion==0){
						$("#lbl_snmpVersion").text('V1');
						$("#row1").show();
						$("#row2").show();
						$("#row3").hide();
						$("#row4").hide();
						$("#row5").hide();
						$("#row6").hide();
						$("#row7").hide();
					}
					if(snmpVersion==1){
						$("#lbl_snmpVersion").text('V2');
						$("#row1").show();
						$("#row2").show();
						$("#row3").hide();
						$("#row4").hide();
						$("#row5").hide();
						$("#row6").hide();
						$("#row7").hide();
					}
					if(snmpVersion==3){
						$("#lbl_snmpVersion").text('V3');
						$('#lbl_moNameV3').text(moName);
						$('#lbl_snmpPortV3').text(moPort);
						$("#row1").hide();
						$("#row2").hide();
						$("#row3").show();
						$("#row4").show();
						$("#row5").show();
						$("#row6").show();
						$("#row7").show();
					}
				}
					doInitMoInfo(taskId,moId);
			}
		};
	ajax_(ajax_param);
}


function doInitMoInfo(taskId,moId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listInitMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
			"taskId":taskId,
			"moId":moId,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#monitorView  tr:not(:first)").remove()
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				var timeUnit = data[i].split(",")[3];
				var timeUnitVal = "分";
				if(timeUnit == 1){
					timeUnitVal = "分";
				}else if(timeUnit == 2){
					timeUnitVal = "时";
				}else if(timeUnit == 3){
					timeUnitVal = "天";
				}
//				$("#tblTaskInfo tr:eq("+(7+i)+")").remove();
				html+="<td><b class='title'><label id='lbl_mo"+data[i].split(",")[0]+"'>"+data[i].split(",")[1]+"：</label></b>" 
				+"<label class='inputs' id='lbl_doIntervals"+data[i].split(",")[0]+"'>"+data[i].split(",")[2]+"</label>&nbsp;"+timeUnitVal+"</td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitorView').append(html);
//			toShowMoInfo(taskId);
			
		}
	};	
	ajax_(ajax_param);	
}

/**
 * 默认任务信息的监测器
 * @param taskId
 * @return
 */
function toShowMoInfo(taskId){
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
//			console.log(data)
			for (var i=0;i<data.length;i++){ 
				var moTypeName=$('#lbl_mo'+data[i].split(",")[0]).html();
				var interval=data[i].split(",")[1];
				//console.log(i+"-----moTypeName:"+moTypeName+",---interval:"+interval);
				$('#lbl_doIntervals'+data[i].split(",")[0]).html(interval/60);
				}
		}
	};	
	ajax_(ajax_param);	
}

function doInitDBMoInfo(taskId,moClassId){
//	console.log("初始化监测器：taskID==="+taskId+" ,  moClassId==="+moClassId);
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listInitDBMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
			"taskId":taskId,
			"moClassId":moClassId,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#monitorView  tr:not(:first)").remove()
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				var timeUnit = data[i].split(",")[3];
				var timeUnitVal = "分";
				if(timeUnit == 0){
					timeUnitVal = "秒";
				} else if(timeUnit == 1){
					timeUnitVal = "分";
				}else if(timeUnit == 2){
					timeUnitVal = "时";
				}else if(timeUnit == 3){
					timeUnitVal = "天";
				}
//				$("#tblTaskInfo tr:eq("+(7+i)+")").remove();
				html+="<td><b class='title'><label id='lbl_mo"+data[i].split(",")[0]+"'>"+data[i].split(",")[1]+"：</label></b>" 
				+"<label class='inputs' id='lbl_doIntervals"+data[i].split(",")[0]+"'>"+data[i].split(",")[2]+"</label>&nbsp;"+timeUnitVal+"</td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitorView').append(html);
		}
	};	
	ajax_(ajax_param);	
}

/**
 * 初始化信息详情（机房环境监控）
 * @param taskId
 * @return
 */
function initRoomDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initRoomDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				if(data.roomCommunityBean!=null){
					$("#lbl_roomUserName").text(data.roomCommunityBean.userName);
					$("#lbl_roomPassword").text(data.roomCommunityBean.passWord);
					$("#lbl_roomPort").text(data.roomCommunityBean.port);
				}
				$("#lbl_deviceIp").text(data.deviceIp);
				if(data.domainId != -1 && data.domainId != 0 && data.domainId != null){
					$("#lbl_domainName").text(data.domainName);
				}else if(data.domainId == -1){
					$("#lbl_domainName").text("所有");
				}else{
					$("#lbl_domainName").text("");
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化dns信息
 */
function initDnsDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initDnsDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#lbl_siteName").text(data.deviceIp);
				$('#lbl_siteUrl').text(data.ipAddr);
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化ftp信息
 */
function initFtpDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initFtpDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#lbl_siteName").text(data.deviceIp);
				$('#lbl_siteUrl').text(data.ipAddr);
				$("#lbl_ftpPort").text(data.port);
				if(data.isAuth == 2){
					$("#tblFtpInfo").show();
					if(data.siteCommunityBean != null){
						$("#lbl_ftpUserName").text(data.siteCommunityBean.userName);
						$("#lbl_ftpPassword").text(data.siteCommunityBean.password);
					}
				}else{
					$("#tblFtpInfo").hide();
				}
				
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化http信息
 */
function initHttpDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initHttpDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#lbl_siteName").text(data.deviceIp);
				$('#lbl_siteUrl').text(data.ipAddr);
				if(data.siteCommunityBean != null){
					var requestMethod = data.siteCommunityBean.requestMethod;
					if(requestMethod == 1){
						$("#lbl_requestMethod").text("GET");
					}else if(requestMethod == 2){
						$("#lbl_requestMethod").text("POST");
					}else if(requestMethod == 3){
						$("#lbl_requestMethod").text("HEAD");
					}
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化tcp信息
 */
function initTcpDetail(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initTcpDetail";
	var ajax_param={
			url:uri,
			type:"post",
			dataType:"json",
			async : false,

			data:{
				"taskId":taskId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#deviceInfoView").hide();
				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#lbl_siteName").text(data.deviceIp);
				$('#lbl_siteUrl').text(data.ipAddr);
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}