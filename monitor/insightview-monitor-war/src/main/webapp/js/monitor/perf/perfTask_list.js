var timerId;
var timerId2;
var timerId3;
$(document).ready(function() {
	var serverId=$('#serverId').val();
	doInitTable(serverId);
	//每隔0.5秒自动调用方法，实现操作进度的实时更新
	timerId = window.setInterval(getOpProcess, 5000);
	timerId2 = window.setInterval(getOpCollector, 5000);
	timerId3 = window.setInterval(getStatus, 5000);
//	$("#ipt_authAlogrithm").combobox({
//		 onChange:function() {
//			$('#ipt_encryptionAlogrithm').combobox('enable');
//			}
//	 });
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(serverId){
	var path = getRootName(); 
	$('#tblPerfTask')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/perfTask/listPerfTasks',
						remoteSort : false,
						queryParams : {
							"serverId" : serverId,
							"status" : -2,
							"taskId" : 0
						},
						idField : 'taskId',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						},
						{
							'text' : '批量重发',
							'iconCls' : 'icon-batchSend',
							handler : function() {
								doBatchResend();
							}
						},
						{
							'text' : '批量暂停',
							'iconCls' : 'icon-batchStop',
							handler : function() {
								doBatchStop();
							}
						},
						{
							'text' : '批量恢复',
							'iconCls' : 'icon-batchStart',
							handler : function() {
								doBatchStart();
							}
						}  ],
						columns : [ [
						        {
						            field : 'taskId',
						            checkbox : true
						        },
								{
									field : 'status',
									title : '任务状态',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==-1){
											return '已删除';
										}else if(value==0){
											return '<img src =" '+path+'/style/images/icon/icon_run.png"  title="运行中" />';
										}else if(value==1){
											return '<img src =" '+path+'/style/images/icon/icon_stop.png"  title="已停止" />';
										}else{
											return '';
										}
									},
									sortable : true
								},	
								{
									field : 'lastStatusTime',
									title : '最近状态时间',
									width : 100,
									align : 'center',
									sortable : true
								},
//						        {
//						            field : 'taskId',
//						            title : '任务ID',
//						            width : 50,
//						            align : 'center',
//						            sortable : true,
//						        },
								{
									field : 'deviceIp',
									title : '被采设备',
									width : 150,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
						        		if(value && value.length > 20){
						        		value2 = value.substring(0,20) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'className',
									title : '对象类型',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'deviceManufacture',
									title : '被采设备厂商',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==null){
											return "未知";
										}else{
											return value;
										}
									},
//									sortable : true
								},
								{
									field : 'deviceType',
									title : '被采设备类型',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==null){
											return "未知";
										}else{
											return value;
										}
									},
//									sortable : true
								},
								{
									field : 'moCounts',
									title : '监测器数',
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ row.taskId+','+row.serverId+',&quot;'+row.serverName
												+ '&quot;);">'+value+'</a>';
									},
								},
								{
									field : 'createTime',
									title : '任务创建时间',
									width : 100,
									align : 'center',
								},
								
								{
									field : 'serverName',
									title : '所属采集机',
									width : 110,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if(value==null||value=="null"||value==""){
											return "";
										}else{
											return value;
										}
									}
								},
								{
									field : 'taskIds',
									title : '操作',
									width : 180,  
									align : 'center',
									formatter : function(value, row, index) {
									if(row.status == 0){
										if(row.progressStatus>=1 && row.progressStatus<5){
											return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
											+ row.taskId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>'
											+' &nbsp;<a href="javascript:void(0);" id="btnStop" class="fltrt"><img src =" '+path+'/style/images/icon/icon_pause.png"  title="暂停" /></a>'
											+' &nbsp;<a href="javascript:doResend('+row.taskId+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png"  title="重发" /></a>'
											+' &nbsp;<a href="javascript:toAppointCollector('
											+row.taskId+","+row.moClassId+','+row.serverId+',&quot;'+row.serverName+'&quot;,&quot;'+row.deviceIp
											+'&quot;);" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
											
										}else{
											return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
											+ row.taskId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
											+ row.taskId+','+row.serverId+',&quot;'+row.serverName
											+ '&quot;);"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
											+ row.taskId+','+row.operateStatus+','+row.progressStatus
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>'
											+' &nbsp;<a href="javascript:doStop('+row.taskId+');" id="btnStop" class="fltrt"><img src =" '+path+'/style/images/icon/icon_pause.png"  title="暂停" /></a>'
											+' &nbsp;<a href="javascript:toAppointCollector('
											+row.taskId+","+row.moClassId+','+row.serverId+',&quot;'+row.serverName+'&quot;,&quot;'+row.deviceIp
											+'&quot;);" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
										}
										
									}else if(row.status == 1){
										if(row.progressStatus >= 1 && row.progressStatus < 5){
											return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
											+ row.taskId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>'
											+' &nbsp;<a href="javascript:void(0);" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_star.png"  title="恢复" /></a>'
											+' &nbsp;<a href="javascript:doResend('+row.taskId+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png"  title="重发" /></a>'
											+' &nbsp;<a href="javascript:toAppointCollector('
											+row.taskId+","+row.moClassId+','+row.serverId+',&quot;'+row.serverName+'&quot;,&quot;'+row.deviceIp
											+'&quot;);" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
											
										}else{
											return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
											+ row.taskId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
											+ row.taskId+','+row.serverId+',&quot;'+row.serverName+'&quot;'
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
											+ row.taskId+','+row.operateStatus+','+row.progressStatus
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>'
											+' &nbsp;<a href="javascript:doStart('+row.taskId+');" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_star.png"  title="恢复" /></a>'
											+' &nbsp;<a href="javascript:toAppointCollector('
											+row.taskId+","+row.moClassId+','+row.serverId+',&quot;'+row.serverName+'&quot;,&quot;'+row.deviceIp
											+'&quot;);" id="btnStart" class="fltrt"><img src =" '+path+'/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
										}
										
									}
								}
								},
								{
									field : 'progressStatus',
									title : '任务下发进度',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										if(value == 1){
											return '<img src =" '+path+'/style/images/icon/progress1.png" title="等待分发" />';
										}else if(value == 2){ 
											return '<img src =" '+path+'/style/images/icon/progress2.png" title="正在分发" />';
										}else if(value == 3){
											return '<img src =" '+path+'/style/images/icon/progress3.png" title="已分发" />';
										}else if(value == 4){
											return '<img src =" '+path+'/style/images/icon/progress4.png" title="已接收" />';
										}else{
											return '已完成';
										}  
									},
									sortable : true
								},
								{
									field : 'errorInfo',
									title : '错误信息',
									width : 180,
									align : 'center',
									sortable : true
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblPerfTask').resizeDataGrid(0, 0, 0, 0);
    });
}


/**
 * 查找设备及snmp信息
 */
function findDeviceInfo(){
		var authType =$("#authType").val();
		var deviceId = $("#ipt_deviceId").val();
		var path = getRootName();
		if(authType == "SNMP" || authType == "Conditons" || authType=="Ups"){
			var uri=path+"/monitor/perfTask/findDeviceInfo";
		}else if(authType == "Vmware"){
			var uri=path+"/monitor/perfTask/findVmwareDeviceInfo";
		}else if(authType == "unKnown"){
			var uri=path+"/monitor/perfTask/findUnknownDeviceInfo";
		}
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
				$("#ipt_templateID").combobox('setValue', data.templateID);
//				console.log("templateID="+$("#templateID").val());
				var manufacturerID = data.manufacturerID;
				if(manufacturerID == 1){
					$("#authType").val("unKnown");
				}
				var authType =$("#authType").val();
				if(authType == "SNMP" || authType == "Conditons" || authType == "Ups"){
					var moClassName = $("#ipt_moClassId").val();
					iterObj(data,"ipt");
					$("#ipt_moClassId").val(moClassName);
//					var snmpVersion=$("#ipt_snmpVersion").val();
//					$("input:radio[name='version']").eq(snmpVersion).attr("checked",'checked');
//					console.log("snmpVersion======"+data.snmpVersion);
					if(data.snmpVersion == "0" || data.snmpVersion == 0){
						$("#ipt_snmpVersion1").val("V1");
						$("#V1_snmp").show();
						$("#V1_readCommunity").show();
						$("#V3_snmp").hide();
						$("#V3_readCommunity").hide();
						$("#V3_usm").hide();
						$("#V3_auth").hide();
						$("#V3_encryption").hide();
					}else if(data.snmpVersion == "1"  || data.snmpVersion == 1){
						$("#ipt_snmpVersion1").val("V2");
						$("#V1_snmp").show();
						$("#V1_readCommunity1").show();
						$("#V3_snmp").hide();
						$("#V3_readCommunity").hide();
						$("#V3_usm").hide();
						$("#V3_auth").hide();
						$("#V3_encryption").hide();
					}else if(data.snmpVersion == "3"  || data.snmpVersion == 3){
						$("#ipt_snmpVersion1").val("V3");
						$("#V1_snmp").hide();
						$("#V1_readCommunity").hide();
						$("#V3_snmp").show();
						$("#V3_readCommunity").show();
						$("#V3_usm").show();
						$("#V3_auth").show();
						$("#V3_encryption").show();
					}
//					edit(data.snmpVersion);
					
					var moName=$("#ipt_moName").val();
					var readCommunity=$("#ipt_readCommunity").val();
					var writeCommunity=$("#ipt_writeCommunity").val();
					var snmpPort=$("#ipt_snmpPort").val();
//					if(snmpPort==0 || snmpPort==""){
//						$("#ipt_snmpPort").val('161');
//					}
//					if(readCommunity==''){
//						$("#ipt_readCommunity").val('public');
//					}
					if(snmpPort==0 || snmpPort==""){
						$("#ipt_snmpPort").val("");
					}
					snmpPort=$("#ipt_snmpPort").val();
					readCommunity=data.readCommunity;
					$("#ipt_moNameV1").val(moName);
					$("#ipt_readCommunityV1").val(readCommunity);
					$("#ipt_writeCommunityV1").val(writeCommunity);
					$("#ipt_snmpPortV1").val(snmpPort);
					$("#ipt_moNameV3").val(moName);
					$("#ipt_readCommunityV3").val(readCommunity);
					$("#ipt_writeCommunityV3").val(writeCommunity);
					$("#ipt_snmpPortV3").val(snmpPort);
				}else if(authType == "Vmware"){
					var moClassName = $("#ipt_moClassId").val();
					iterObj(data,"ipt");
					$("#ipt_moClassId").val(moClassName);
				}else if(authType == "unKnown"){
					$("#tblSNMPCommunityInfo").hide();
					var moClassName = $("#ipt_moClassId").val();
					iterObj(data,"ipt");
					$("#ipt_moClassId").val(moClassName);
				}
					doInitMoList();
//				edit();
			}
		};
		ajax_(ajax_param);		
}

function checkKey(){
	var authKey= $("#ipt_authKey").val();
	var encryptionAlogrithm = $("#ipt_encryptionAlogrithm").combobox('getValue');
	if(authKey!="" && authKey!=null && encryptionAlogrithm !="-1" && encryptionAlogrithm !=-1){
		return true;
	}else{
		if(authKey=="" || authKey==null){
			$.messager.alert('提示', '请输入认证KEY!', 'info');
		}
		if(encryptionAlogrithm=="-1" || encryptionAlogrithm==-1 || encryptionAlogrithm=="请选择"){
			$.messager.alert('提示', '请选择加密算法!', 'info');
		}
		return false;
	}
}
function urlClick(){
	var url = $("#ipt_url").val();
//	console.log("url==="+url);
	if(url == null ||url == ""){
//		console.log(">>>>>");
		var ip = $("#ipt_deviceIp").val();
		if(ip != null && ip !=""){
			var middleWareType =  $("#ipt_middleWareType").val();
			if(middleWareType =='Tomcat' || middleWareType =='tomcat'){
				$("#ipt_url").val("http://"+ip+":"+"8080");
			}else if(middleWareType=='Websphere' || middleWareType=='websphere' ){
				$("#ipt_url").val("http://"+ip+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
			}else if(middleWareType=='Weblogic' || middleWareType=='weblogic' ){
				$("#ipt_url").val("http://"+ip+":"+"7001"+"/console/login/LoginForm.jsp");
			}
		}else{
			$.messager.alert('提示', '请选择设备IP!', 'info');
		}
	}
}

/**
 * 新增任务
 * @return
 */
function doAddTask(){
	$("#btnSave").attr("disabled", true);
	var moClassId = $('#ipt_moClassId').attr("alt");
	var authType = $("#authType").val();
	var snmpVersion =$("#ipt_snmpVersion").val();
	var middleWareType = $("#ipt_middleWareType").val();
//	console.log("snmpVersion===="+snmpVersion);
	var checkFormRS = true;
	var checkKeyRS = true;
	var checkIPFlag = 1;
	var version=3;
	if(authType == "Vmware"){
		checkFormRS = checkInfo('#tblAuthCommunityInfo');
		checkIPFlag = 2;
	}else if(authType == "dbms"){
		checkFormRS = checkInfo('#tblDBMSCommunity');
		checkIPFlag = 3;
	}else if(authType == "MiddleWare"){
		checkFormRS = checkInfo('#tblMiddleWareCommunity');
		checkIPFlag = 4;
	}else if(authType == "room"){
		checkFormRS = checkInfo('#tblRoomCommunity');
		checkIPFlag = 5;
	}else if(authType == "site"){
		if(moClassId == 92){
			var isAuth = $("#ipt_isAuth").val();
//			console.info("isAuth=="+isAuth);
			if(isAuth == 2){
				checkFormRS = checkInfo('#tblFtpCommunity');
//				console.info("checkFormRS=="+checkFormRS);
			}
		}
		checkIPFlag = 6;
	}
	else if(authType == "SNMP" || authType == "Conditons" || authType == "Ups"){
		if(snmpVersion ==0 ||snmpVersion==1){
			checkFormRS = checkInfo('#V1_snmp')&& checkInfo('#V1_readCommunity');
			checkIPFlag = 1;
			version=1;
		}else{
			checkIPFlag = 1;
			version=3;
			checkFormRS = checkInfo('#V3_snmp')&& checkInfo('#V3_readCommunity')&& checkInfo('#V3_usm');
//			var authAlogrithm= $("#ipt_authAlogrithm").combobox('getValue');
//			console.log("authAlogrithm == "+authAlogrithm);
			var authAlogrithm= $("#ipt_authAlogrithm").val();
			if(authAlogrithm != -1 && authAlogrithm != "-1"){
				checkKeyRS = checkKey();
			}
		}
	} 
	
	
//	console.log("checkFormRS===="+checkFormRS+"  ,checkKeyRS=="+checkKeyRS);
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	var motimeunit='';
	
	var dbName = "";
	if(moClassId == 54){
		dbName = $("#ipt_dbName").val();
	}
	$('input:checked[name=moType]').each(function() { 
		arrChk.push($(this).val());
	});
	if(arrChk.length==0){
		$.messager.alert("提示","至少选择一个监测器！","error");
	}else if(moClassId == 9){
		$.messager.alert("提示","对象类型为虚拟机的设备不能创建任务！","error");
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
		
		var path=getRootName();
		var templateID=$("#ipt_templateID").combobox('getValue');
		if(templateID == -1 || templateID == "-1"){
			var uri=path+"/monitor/perfTask/addPerfTask?templateID=-1";
		}else{
			var moTypeLstJson =$("#moTypeLstJson").val();
			var uri=path+"/monitor/perfTask/addPerfTask?moTypeLstJson="+moTypeLstJson+"&templateID="+templateID+"&moClassId="+moClassId;
		}
		if(authType == "SNMP" || authType == "Conditons" || authType == "Ups"){
			var version=$("#ipt_snmpVersion").val();
			if(version==0 || version==1){
				var moNameV1=$("#ipt_moNameV1").val();
				var readCommunityV1=$("#ipt_readCommunityV1").val();
				var writeCommunityV1 =$("#ipt_writeCommunityV1").val();
				var snmpPortV1=$("#ipt_snmpPortV1").val();
				$("#ipt_moName").val(moNameV1);
				$("#ipt_readCommunity").val(readCommunityV1);
				$("#ipt_writeCommunity").val(writeCommunityV1);
				$("#ipt_snmpPort").val(snmpPortV1);
				$('.input2').val(""); 
			}else{
				var moNameV3=$("#ipt_moNameV3").val();
				var readCommunityV3=$("#ipt_readCommunityV3").val();
				var writeCommunityV3 =$("#ipt_writeCommunityV3").val();
				var snmpPortV3=$("#ipt_snmpPortV3").val();
				$("#ipt_moName").val(moNameV3);
				$("#ipt_readCommunity").val(readCommunityV3);
				$("#ipt_writeCommunity").val(writeCommunityV3);
				$("#ipt_snmpPort").val(snmpPortV3);
			}
		}else{
			$("#ipt_snmpVersion").val(0);
			$("#ipt_snmpPort").val(0);
		}
		var domainId = $("#ipt_domainId").val();
		if(domainId == null || domainId==""){
			domainId =-1;
		}
		
		var middleWareType = $("#ipt_middleWareType").val();
		if(middleWareType == "weblogic" || middleWareType == "Weblogic"){
			var middleUserName = $("#ipt_middleUserName").val();
			var middlePassWord = $("#ipt_middlePassword").val();
		}else{
			var middleUserName = "";
			var middlePassWord = "";
		}
		var ftpPort = $("#ipt_ftpPort").val();
		var isAuth =  $("#ipt_isAuth").val();
		var monitorCheck = checkInfo('#monitor');
//		console.log("monitorCheck===="+ monitorCheck);
		if(monitorCheck == true){
//		console.log("domainId===="+ domainId);
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
				"moId":$("#ipt_deviceId").val(),
				"moClassId":moClassId,
				"deviceIp" : $("#ipt_deviceIp").val(),
				"domainId":domainId,
				"domainName" : $("#ipt_domainName").val(),
				"deviceManufacture" : $("#ipt_deviceManufacture").val(),
				"deviceType" : $("#ipt_deviceType").val(),
				"snmpVersion" :$("#ipt_snmpVersion").val(),
				"moName" : $("#ipt_moName").val(),
				"snmpPort" : $("#ipt_snmpPort").val(),
				"readCommunity" : $("#ipt_readCommunity").val(),
				"writeCommunity" : $("#ipt_writeCommunity").val(),
				"status" : 1,
				"moList" : molist,
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				"encryptionKey" : $("#ipt_encryptionKey").val(),
				"usmUser" : $("#ipt_usmUser").val(),
				"securityLevel" : $("#ipt_securityLevel").val(),
//					"authAlogrithm" : $("#ipt_authAlogrithm").combobox('getText'),
				"authKey" : $("#ipt_authKey").val(),
//					"encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").combobox('getText'),
				"contexName" : $("#ipt_contexName").val(),
				"dbName" : dbName,
				
				"vmIfCommunityBean.authType":3,
				"vmIfCommunityBean.deviceIP":$("#ipt_deviceIp").val(),
				"vmIfCommunityBean.domainID":$("#ipt_domainId").val(),
				"vmIfCommunityBean.moID":$("#ipt_deviceId").val(),
				"vmIfCommunityBean.port":$("#ipt_port").val(),
				"vmIfCommunityBean.userName":$("#ipt_userName").val(),
				"vmIfCommunityBean.password":$("#ipt_password").val(),
				
				"dbmsCommunityBean.ip" : $("#ipt_deviceIp").val(),
				"dbmsCommunityBean.domainID":$("#ipt_domainId").val(),
				"dbmsCommunityBean.dbName" : $("#ipt_dbName").val(),
				"dbmsCommunityBean.dbmsType" : $("#ipt_dbmsType").val(),
				"dbmsCommunityBean.userName" : $("#ipt_dbUserName").val(),
				"dbmsCommunityBean.password" : $("#ipt_dbPassword").val(),
				"dbmsCommunityBean.port" : $("#ipt_dbPort").val(),
				
				"middleWareCommunityBean.middleWareName":$("#ipt_middleWareName").attr("alt"),
				"middleWareCommunityBean.middleWareType":middleWareType,
				"middleWareCommunityBean.ipAddress":$("#ipt_deviceIp").val(),
				"middleWareCommunityBean.domainName":$("#ipt_domainName").val(),
				"middleWareCommunityBean.userName":middleUserName,
				"middleWareCommunityBean.passWord":middlePassWord,
				"middleWareCommunityBean.port":$("#ipt_middlePort").val(),
				"middleWareCommunityBean.domainID":$("#ipt_domainId").val(),
				"middleWareCommunityBean.url":$("#ipt_url").val(),
				
				"roomCommunityBean.ipAddress" :$("#ipt_deviceIp").val(),
				"roomCommunityBean.port" : $("#ipt_roomPort").val(),
				"roomCommunityBean.userName" : $("#ipt_roomUserName").val(),
				"roomCommunityBean.passWord" : $("#ipt_roomPassWord").val(),
				"roomCommunityBean.domainID":$("#ipt_domainId").val(),
				
				"webSite.moID":$("#ipt_deviceId").val(),
				"webSite.moClassID":moClassId,
				"webSite.siteHttp.requestMethod" : $('input[name="requestMethod"]:checked').val(),
				"webSite.siteHttp.httpUrl" : $('#ipt_httpUrl').val(),
				
				"webSite.siteFtp.ipAddr" : $('#ipt_ftpIPAddr').val(),
				"webSite.siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
				"webSite.siteFtp.userName" : $("#ipt_ftpUserName").val(),
				"webSite.siteFtp.password" : $("#ipt_ftpPassWord").val(),
				"webSite.siteFtp.isAuth" : isAuth == '' ? 0:isAuth,
				
				"authType":$("#authType").val(),
				"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data != -4){
					if(data != -3){
						if(data==1){
							$.messager.alert("提示","任务新增成功！","info");
							$("#btnSave").removeAttr("disabled");
							$("#divEditTask").dialog('close');
							reloadTable();
						}else if(data==0){
							$.messager.alert("提示","此设备已分发任务！","info");
						}else if(data==-2){
							$.messager.alert("提示","此设备已通过vCenter方式分发任务！","info");
						}else{
							$.messager.alert("提示","任务新增失败！","error");
						}
					}else{
						$.messager.alert("提示","没有属于选择设备的监测器！","error");
					}
				}else{
					$.messager.alert("提示","绑定模板失败！","error");
				}
			}
			};
			if(checkFormRS == true){
				if(checkKeyRS == true){
					var checkIPRS = checkIP(checkIPFlag,version);
					if(checkIPRS==true){
						ajax_(ajax_param);
					}
				}
			}
		}
	}

}

/**
 * 修改任务
 * @param taskId
 * @return
 */
function doUpdateTask(taskId) {
	$("#btnSave").attr("disabled", true);
	var authType = $("#authType").val();
	var checkFormRS = true;
	var checkKeyRS = true;
	var snmpVersion =$("#ipt_snmpVersion").val();
	var checkIPFlag = 1;
	var version=3;
	if(authType == "Vmware"){
		checkFormRS = checkInfo('#tblAuthCommunityInfo');
		checkIPFlag = 2;
	}else if(authType == "dbms"){
		checkFormRS = checkInfo('#tblDBMSCommunity');
		checkIPFlag = 3;
	}else if(authType == "MiddleWare"){
		checkFormRS = checkInfo('#tblMiddleWareCommunity');
		checkIPFlag = 4;
	}else if(authType == "room"){
		checkFormRS = checkInfo('#tblRoomCommunity');
		checkIPFlag = 5;
	}else if(authType == "site"){
		if(moClassId == 92){
			var isAuth = $("#ipt_isAuth").val();
			if(isAuth == 2){
				checkFormRS = checkInfo('#tblFtpCommunity');
			}
		}
		checkIPFlag = 6;
	}else if(authType == "SNMP" || authType == "Conditons" || authType == "Ups"){ 
		if(snmpVersion ==0 ||snmpVersion==1){
			checkFormRS = checkInfo('#V1_snmp')&& checkInfo('#V1_readCommunity');
			checkIPFlag = 1;
			version=1;
		}else{
			checkIPFlag = 1;
			version=3;
			checkFormRS = checkInfo('#V3_snmp')&& checkInfo('#V3_readCommunity')&& checkInfo('#V3_usm');
			var authAlogrithm= $("#ipt_authAlogrithm").combobox('getValue');
//			console.log("authAlogrithm == "+authAlogrithm);
			if(authAlogrithm != -1 && authAlogrithm != "-1"  && authAlogrithm != "请选择"){
				checkKeyRS = checkKey();
			}
		}
	}
	var arrChk= [];
	var selectList= [];
	var moClassId = $('#ipt_moClassId').attr("alt");
	var dbName = "";
	if(moClassId == 54){
		dbName = $("#ipt_dbName").val();
	}
	var molist='';
	var mointerval='';
	var motimeunit='';
	$('input:checked[name=moType]').each(function() { 
		arrChk.push($(this).val());
	});
	if(arrChk.length==0){
		$.messager.alert("提示","至少选择一个监测器！","error");
	}else{
		for ( var int = 0; int < arrChk.length; int++) {
//		alert(arrChk[int]);
			molist+=arrChk[int]+",";
			mointerval+=$("#ipt_doIntervals"+arrChk[int]).val()+",";
			var select=$("#ipt_timeUnit"+arrChk[int]).val();
			selectList.push(select);
		}
		for ( var int = 0; int < selectList.length; int++) {
			motimeunit+=selectList[int]+",";
		}
		
//	alert(mointerval);
//		console.log("molist=="+molist);
//		console.log("mointerval=="+mointerval);
//		console.log("motimeunit=="+motimeunit);
		var path = getRootName();
		var templateID=$("#ipt_templateID").combobox('getValue');
		if(templateID == -1 || templateID == "-1"){
			var uri=path+"/monitor/perfTask/updateTask?templateID=-1";
		}else{
			var moTypeLstJson =$("#moTypeLstJson").val();
			var uri=path+"/monitor/perfTask/updateTask?moTypeLstJson="+moTypeLstJson+"&templateID="+templateID+"&moClassId="+moClassId;
		}
		var version=$("#ipt_snmpVersion").val();
		if(version==0 || version==1){
			var moNameV1=$("#ipt_moNameV1").val();
			var readCommunityV1=$("#ipt_readCommunityV1").val();
			var writeCommunityV1 =$("#ipt_writeCommunityV1").val();
			var snmpPortV1=$("#ipt_snmpPortV1").val();
			$("#ipt_moName").val(moNameV1);
			$("#ipt_readCommunity").val(readCommunityV1);
			$("#ipt_writeCommunity").val(writeCommunityV1);
			$("#ipt_snmpPort").val(snmpPortV1);
			$('.input2').val(""); 
		}else{
			var moNameV3=$("#ipt_moNameV3").val();
			var readCommunityV3=$("#ipt_readCommunityV3").val();
			var writeCommunityV3 =$("#ipt_writeCommunityV3").val();
			var snmpPortV3=$("#ipt_snmpPortV3").val();
			$("#ipt_moName").val(moNameV3);
			$("#ipt_readCommunity").val(readCommunityV3);
			$("#ipt_writeCommunity").val(writeCommunityV3);
			$("#ipt_snmpPort").val(snmpPortV3);
		}
		
		var serverId = $("#ipt_serverId").attr("alt");
		if(serverId == null || serverId == "" || serverId == ''){
			serverId = -1;
		}
//	console.log("serverId=="+serverId);
		var ftpPort = $("#ipt_ftpPort").val();
		var isAuth = $("#ipt_isAuth").val();
		var monitorCheck = checkInfo('#monitor');
		if(monitorCheck == true){
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
				"taskId":taskId, 
				"moId":$("#ipt_moId").val(),
				"moClassId":moClassId,
				"deviceIp" : $("#ipt_deviceIp").val(),
				"domainId":$("#ipt_domainId").val(),
				"domainName" : $("#ipt_domainName").val(),
				"deviceManufacture" : $("#ipt_deviceManufacture").val(),
				"deviceType" : $("#ipt_deviceType").val(),
				"snmpVersion" :$("#ipt_snmpVersion").val(),
				"moName" : $("#ipt_moName").val(),
				"snmpPort" : $("#ipt_snmpPort").val(),
				"readCommunity" : $("#ipt_readCommunity").val(),
				"writeCommunity" : $("#ipt_writeCommunity").val(),
				"status" : $("#ipt_status").val(),
				"collectorId" : serverId,
				"oldCollectorId" : $("#ipt_oldCollectorId").val(),
				"moList" : molist,
				"moIntervalList":mointerval,
				"moTimeUnitList":motimeunit,
				"encryptionKey" : $("#ipt_encryptionKey").val(),
				"usmUser" : $("#ipt_usmUser").val(),
				"securityLevel" : $("#ipt_securityLevel").val(),
//			"authAlogrithm" : $("#ipt_authAlogrithm").combobox('getText'),
				"authKey" : $("#ipt_authKey").val(),
//			"encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").combobox('getText'),
				"contexName" : $("#ipt_contexName").val(),
				"dbName" :dbName,
				
				"vmIfCommunityBean.authType":3,
				"vmIfCommunityBean.deviceIP":$("#ipt_deviceIp").val(),
				"vmIfCommunityBean.domainID":$("#ipt_domainId").val(),
				"vmIfCommunityBean.moID":$("#ipt_deviceId").val(),
				"vmIfCommunityBean.port":$("#ipt_port").val(),
				"vmIfCommunityBean.userName":$("#ipt_userName").val(),
				"vmIfCommunityBean.password":$("#ipt_password").val(),
				
				"dbmsCommunityBean.ip" : $("#ipt_deviceIp").val(),
				"dbmsCommunityBean.domainID":$("#ipt_domainId").val(),
				"dbmsCommunityBean.dbName" : $("#ipt_dbName").val(),
				"dbmsCommunityBean.dbmsType" : $("#ipt_dbmsType").val(),
				"dbmsCommunityBean.userName" : $("#ipt_dbUserName").val(),
				"dbmsCommunityBean.password" : $("#ipt_dbPassword").val(),
				"dbmsCommunityBean.port" : $("#ipt_dbPort").val(),
				
				"middleWareCommunityBean.middleWareName":$("#ipt_middleWareName").attr("alt"),
				"middleWareCommunityBean.middleWareType":$("#ipt_middleWareType").val(),
				"middleWareCommunityBean.ipAddress":$("#ipt_deviceIp").val(),
				"middleWareCommunityBean.domainName":$("#ipt_domainName").val(),
				"middleWareCommunityBean.userName":$("#ipt_middleUserName").val(),
				"middleWareCommunityBean.passWord":$("#ipt_middlePassword").val(),
				"middleWareCommunityBean.port":$("#ipt_middlePort").val(),
				"middleWareCommunityBean.domainID":$("#ipt_domainId").val(),
				"middleWareCommunityBean.url":$("#ipt_url").val(),
				
				"roomCommunityBean.ipAddress" :$("#ipt_deviceIp").val(),
				"roomCommunityBean.port" : $("#ipt_roomPort").val(),
				"roomCommunityBean.userName" : $("#ipt_roomUserName").val(),
				"roomCommunityBean.passWord" : $("#ipt_roomPassWord").val(),
				"roomCommunityBean.domainID":$("#ipt_domainId").val(),
				
				"webSite.moID":$("#ipt_moId").val(),
				"webSite.moClassID":moClassId,
				"webSite.siteHttp.requestMethod" : $('input[name="requestMethod"]:checked').val(),
				"webSite.siteHttp.httpUrl" : $('#ipt_httpUrl').val(),
				
				"webSite.siteFtp.ipAddr" : $('#ipt_ftpIPAddr').val(),
				"webSite.siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
				"webSite.siteFtp.userName" : $("#ipt_ftpUserName").val(),
				"webSite.siteFtp.password" : $("#ipt_ftpPassWord").val(),
				"webSite.siteFtp.isAuth" : isAuth == '' ? 0:isAuth,
				
				"authType":$("#authType").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data) {
				if(data.setTemplateFlag == false || data.setTemplateFlag == "false"){
					$.messager.alert("提示", "绑定模板失败！", "error");
				}else{
					if(data.isNoMonitor == false || data.isNoMonitor == "false"){
						$.messager.alert("提示", "没有属于该设备的监测器！", "error");
					}else{
						if (true == data.updateFlag || "true" == data.updateFlag) {
							$.messager.alert("提示", "任务修改成功！", "info");
							$("#btnSave").removeAttr("disabled");
							$("#divEditTask").dialog('close');
							reloadTable();
						} else {
							$.messager.alert("提示", "任务修改失败！", "error");
							reloadTable();
						}
					}
				}
			}
			};
			if(checkFormRS == true){
				var checkIPRS = checkIP(checkIPFlag,version);
				if(checkIPRS==true){
					if(checkKeyRS == true){
						ajax_(ajax_param);
					}
				}
			}
		}
	}
}


/**
 * 修改任务信息
 * @param userId
 * @return
 */
function toUpdate(taskId,serverId,serverName){
//	var length=$("#monitor").find("tr").length-1;
//	if(length>0){
//		var trs=length/2+length%2+1;
//		for(var i=1;i<trs;i++){
//			$("#monitor tr:last").remove();
//		}
//	}
	$("#monitor  tr:not(:first)").remove();
	$('.input').val("");
	$('.inputV1').val("");
	$('.inputV3').val(""); 
	$("#ipt_readCommunity").val("");
	$("#ipt_writeCommunity").val("");
	
	$("#flag").val("edit");
	$("#classFlag").hide();
//	$("#hostFlag").show();
	$("#hostFlag").hide()
	$("#btnChose").hide();
	$("#btnChose2").hide();
	var docDb = document.getElementById('ipt_dbPort');
	var docMiddle = document.getElementById('ipt_middlePort');
	var docRoom = document.getElementById('ipt_roomPort');
	docDb.readOnly = true;
	docMiddle.readOnly = true;
	docRoom.readOnly = true;
	
	//初始化采集机
	$("#ipt_serverId").val(serverName);
	$("#ipt_serverId").attr("alt",serverId);
	if(serverName != null && serverName != ""){
		$("#btnUnChose").show();
	}
	getAuthType(taskId);
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdateTask(taskId);
	});
	$("#btnBack").unbind();
	$("#btnBack").bind("click", function() {
		$('#divEditTask').dialog('close');
	});
	$('#divEditTask').dialog('open');
	
}

/**
 * 判断是哪一种类型
 * @param taskId
 * @return
 */
function getAuthType(taskId){
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
				$("#taskId").val(data.taskId);
//				console.log("init taskid="+$("#taskId").val());
				var showId = data.moClassId;
				var className = data.className;
				//获得模板下拉框的值
				$('#ipt_templateID').combobox({
					panelHeight : '120',
					url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId='+showId,
					valueField : 'templateID',
					textField : 'templateName',
					editable : false,
					onSelect:function(record){  
//						console.log("templateID = "+record.templateID);
						chooseTemplate();
					},
					onLoadSuccess:function(){
						//获得采集周期默认值
						getCollectPeriodVal2(className,showId,taskId);
					}
				});
				$("#ipt_templateID").combobox('setValue',data.templateID);
//				console.log("更新初始采集周期："+$("#templateID").val());
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（DBMS）
 * @param taskId
 * @return
 */
function initDBVal(taskId){
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
				iterObj(data,"ipt");
				$("#deviceInfo").hide();
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
				if(data.dbmsCommunityBean!=null){
					$("#ipt_dbName").val(data.dbmsCommunityBean.dbName);
					$("#ipt_dbmsType").val(data.dbmsCommunityBean.dbmsType);
					$("#ipt_dbUserName").val(data.dbmsCommunityBean.userName);
					$("#ipt_dbPassword").val(data.dbmsCommunityBean.password);
					$("#ipt_dbPort").val(data.dbmsCommunityBean.port);
					$("#ipt_deviceIp").val(data.dbmsCommunityBean.ip);
				}
				if(data.domainId == -1){
					$("#ipt_domainName").val("所有");
				}
				doInitDBMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化DB2信息（DBMS）
 * @param taskId
 * @return
 */
function initDB2Val(taskId){
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
				iterObj(data,"ipt");
				$("#deviceInfo").hide();
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
				if(data.dbmsCommunityBean!=null){
					$("#ipt_dbName").val(data.dbmsCommunityBean.dbName);
					$("#ipt_dbmsType").val(data.dbmsCommunityBean.dbmsType);
					$("#ipt_dbUserName").val(data.dbmsCommunityBean.userName);
					$("#ipt_dbPassword").val(data.dbmsCommunityBean.password);
					$("#ipt_dbPort").val(data.dbmsCommunityBean.port);
					$("#ipt_deviceIp").val(data.dbmsCommunityBean.ip);
				}
				if(data.domainId == -1){
					$("#ipt_domainName").val("所有");
				}
				var docDBName = document.getElementById('ipt_dbName');
				docDBName.readOnly = true;
				doInitDBMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（VMware）
 * @param taskId
 * @return
 */
function initVmwareVal(taskId){
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
				iterObj(data,"ipt");
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
				if(data.vmIfCommunityBean!=null){
					$("#ipt_port").val(data.vmIfCommunityBean.port);
					$("#ipt_userName").val(data.vmIfCommunityBean.userName);
					$("#ipt_password").val(data.vmIfCommunityBean.password);
				}
				doInitMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化未知设备信息
 * @param taskId
 * @return
 */
function initUnknownDeviceVal(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/initUnknownDeviceVal";
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
				iterObj(data,"ipt");
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
				doInitMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（中间件）
 * @param taskId
 * @return
 */
function initMiddleWareVal(taskId){
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
				iterObj(data,"ipt");
				$("#deviceInfo").hide();
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
//				$("#ipt_deviceIp").val(data.dbmsCommunityBean.ip);
				if(data.middleWareCommunityBean!=null){
					$("#ipt_middlePort").val(data.middleWareCommunityBean.port);
					$("#ipt_middleUserName").val(data.middleWareCommunityBean.userName);
					$("#ipt_middlePassword").val(data.middleWareCommunityBean.passWord);
					$("#ipt_middleWareType").val(data.middleWareCommunityBean.middleWareType);
					var middleWareName = data.middleWareCommunityBean.middleWareName;
					$("#ipt_middleWareName").attr("alt",middleWareName);
					if(middleWareName==1){
						$("#ipt_middleWareName").val("weblogic");
						$("#isShowUserAndPwd").show();
					}else if(middleWareName==2){
						$("#ipt_middleWareName").val("tomcat");
						$("#isShowUserAndPwd").hide();
					}else if(middleWareName==3){
						$("#ipt_middleWareName").val("websphere");
						$("#isShowUserAndPwd").hide();
					}
					$("#ipt_url").val(data.middleWareCommunityBean.url);
				}
				doInitDBMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 初始化信息（机房环境监控）
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
				iterObj(data,"ipt");
				$("#deviceInfo").hide();
				$("#ipt_oldCollectorId").val(data.collectorId);
				$("#ipt_serverId").val(data.collectorName);
				$("#ipt_serverId").attr("alt", data.collectorId);
//				$("#ipt_deviceIp").val(data.dbmsCommunityBean.ip);
				if(data.roomCommunityBean!=null){
					$("#ipt_roomUserName").val(data.roomCommunityBean.userName);
					$("#ipt_roomPassWord").val(data.roomCommunityBean.passWord);
					$("#ipt_roomPort").val(data.roomCommunityBean.port);
				}
				doInitDBMoList();
			}
		};
	ajax_(ajax_param);
}


/**
 * 根据taskId获取任务信息
 * @param taskId
 * @return
 */
function initUpdateVal(taskId,moClassId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/findDeviceByTaskId";
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
				iterObj(data,"ipt");
				$("#ipt_oldCollectorId").val(data.collectorId);
				var manufacturerID = data.manufacturerID;
				if(manufacturerID == 1){
					$("#tblSNMPCommunityInfo").hide();
				}else{
					var snmpVersion=$("#ipt_snmpVersion").val();
					var moName=$("#ipt_moName").val();
					var snmpPort=$("#ipt_snmpPort").val();
					var readCommunity=$("#ipt_readCommunity").val();
					snmpPort=$("#ipt_snmpPort").val();
					readCommunity=$("#ipt_readCommunity").val();
					writeCommunity=$("#ipt_writeCommunity").val();
					$("#ipt_moNameV1").val(moName);
					$("#ipt_readCommunityV1").val(readCommunity);
					$("#ipt_writeCommunityV1").val(writeCommunity);
					$("#ipt_snmpPortV1").val(snmpPort);
					$("#ipt_moNameV3").val(moName);
					$("#ipt_readCommunityV3").val(readCommunity);
					$("#ipt_writeCommunityV3").val(writeCommunity);
					$("#ipt_snmpPortV3").val(snmpPort);
					var authAlogrithm = data.authAlogrithm;
					var encryptionAlogrithm = data.encryptionAlogrithm;
					edit(snmpVersion);
				}
					doInitMoList();
			}
		};
	ajax_(ajax_param);
}

/*
 * 更新表格
 */
function reloadTable() {
	var taskId=$("#txtTaskId").val();
	if(taskId != null && taskId != ""){
		if (!(/^[0-9]*[1-9][0-9]*$/.test(taskId))) {
			$.messager.alert("提示", "任务ID只能输入正整数！", "info", function(e) {
				$("#txtTaskId").focus();
			});
			return ;
		}
	}
	var deviceIp=$("#txtDeviceIp").val();
	var serverName=$("#txtServerName").val();
	var taskStatus=$("#txtTaskStatus").combobox('getValue');
	if(taskId==''){
		taskId=0;
	}
	if(taskStatus==''){
		taskStatus=-2;
	}
	$('#tblPerfTask').datagrid('options').queryParams = {
		"taskId" : taskId,
		"deviceIp" : deviceIp,
		"serverName" : serverName,
		"status" : taskStatus
	};
	
	reloadTableCommon_1('tblPerfTask');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function toAdd(){
	$("#monitorTitle").hide();
	$("#tblChooseTemplate").hide();
	$("#monitor  tr:not(:first)").remove();
	$("#classFlag").show();
	$("#hostFlag").hide();
	$("#btnChose").show();
	$("#btnChose2").show();
	$("#deviceInfo").show();
	$("#tblSNMPCommunityInfo").hide();
	$("#tblAuthCommunityInfo").hide();
	$("#tblDBMSCommunity").hide();
	$("#tblMiddleWareCommunity").hide();
	$("#tblRoomCommunity").hide();
	
	$("#tblRoomCommunity").hide();
	$("#tblFtpCommunity").hide();
	$("#tblHttpCommunity").hide();
	$("#isShowSiteTr").hide();
	$("#isShowSiteTr2").hide();
	var docDb = document.getElementById('ipt_dbPort');
	var docMiddle = document.getElementById('ipt_middlePort');
	var docRoom = document.getElementById('ipt_roomPort');
	var docDBName = document.getElementById('ipt_dbName');
	docDb.readOnly = false;
	docMiddle.readOnly = false;
	docRoom.readOnly = false;
	docDBName.readOnly = false;
	var oTable = document.getElementById('tblSNMPCommunityInfo');
	setHiddenRow(oTable,1);
	setHiddenRow(oTable,2);
	setHiddenRow(oTable,3);
	setHiddenRow(oTable,4);
	setHiddenRow(oTable,5);
	setHiddenRow(oTable,6);
	setHiddenRow(oTable,7);
	$('.input').val("");
	$('.inputV1').val("");
	$('.inputV3').val("");
	$('.x2').val("");
	$("#ipt_moId").val(0);
	$("#ipt_readCommunityV1").val("public");
	$("#ipt_readCommunityV3").val("public");
	$("#flag").val("add");  
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doAddTask();
	});
	$("#btnBack").unbind();
	$("#btnBack").bind("click", function() {
		$('#divEditTask').dialog('close');
	});
	_auType = 1;
	$('#divEditTask').dialog('open');
	
	edit();
	
	var vmwarePort = $("#ipt_port").val();
	if (vmwarePort == 0) {
		$("#ipt_port").val('443');
	}
	
	showDefaultPort();
}

/*
 * 删除任务 
 */
function doDel(taskId,operateStatus,progressStatus) {
	$.messager.confirm("提示","确定删除此任务？",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/perfTask/delTask";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"taskId" : taskId,
				//	"operateStatus":3,
					"operateStatus" : operateStatus,
					"progressStatus":progressStatus,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success:function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "任务删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "任务删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
		}
	});
	
}

/**
 * 批量删除
 * @return
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/delTasks?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						if(data == "") {
							$.messager.alert("提示", "任务删除成功！", "info");
							reloadTable();
						} else {
							data=data.substring(0,data.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+data+"的数据不能删除！", "error");
							reloadTable();
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}


/**
 * 显示根据任务ID列表出来的监测器
 * @param taskId
 * @return
 */
function toShowMo(taskId){
	$('#divEditTask').dialog('open');
	doInitMoList();
	toCheckMo(taskId);
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
					}else if(interval >=60){
						doIntervals = interval /60;
						timeUnit = 1;
					}else{
						timeUnit = 0;
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
 * 根据所选SNMP版本不一样，显示对应的SNMP编辑页面
 * @return
 */
function edit(snmpVersion){
	if(snmpVersion=='0' || snmpVersion==0){
		$('#ipt_snmpVersion1').val("V1");
		$("#V1_snmp").show();
		$("#V1_readCommunity").show();
		$("#V3_snmp").hide();
		$("#V3_readCommunity").hide();
		$("#V3_usm").hide();
		$("#V3_auth").hide();
		$("#V3_encryption").hide();
	}else if(snmpVersion=='1'  || snmpVersion==1){
		$('#ipt_snmpVersion1').val("V2");
		$("#V1_snmp").show();
		$("#V1_readCommunity").show();
		$("#V3_snmp").hide();
		$("#V3_readCommunity").hide();
		$("#V3_usm").hide();
		$("#V3_auth").hide();
		$("#V3_encryption").hide();
	}else{
		$('#ipt_snmpVersion1').val("V3");
		$("#V1_snmp").hide();
		$("#V1_readCommunity").hide();
		$("#V3_snmp").show();
		$("#V3_readCommunity").show();
		$("#V3_usm").show();
		$("#V3_auth").show();
		$("#V3_encryption").show();
	}
	
}


function toShowSnmp(){
	$("#btnUpdate2").unbind();
	$("#btnUpdate2").bind("click", function() {
		$('.inputV1').val(""); 
		
	});
	$('#divSnmpInfo').dialog('open');
}

function toShowSnmpV3(){
	$("#btnUpdate3").unbind();
	$("#btnUpdate3").bind("click", function() {
		$('.inputV3').val(""); 
		$('.input2').val(""); 
	});
	$('#divSnmpInfoV3').dialog('open');
}

function hiddenDiv(divId){
	$('#'+divId).dialog('close');
}

/**
 * 加载设备信息列表页面
 * @return
 */
function loadDeviceInfo(){
	var path=getRootName();
	var moClassId = $("#ipt_moClassId").attr("alt");
	if(moClassId==null ||moClassId==""){
		$.messager.alert("提示", "请先选择设备类型！", "info");
	}else if(moClassId==15 ||moClassId==16 ||moClassId==54 ||moClassId==81 ||moClassId==86 ){
		var uri=path+"/monitor/perfTask/toDBMSServerList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(moClassId==19 ||moClassId==20 ||moClassId==53){
		var uri=path+"/monitor/perfTask/toMiddleWareList?flag=choose&jmxType="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(moClassId==44){
		var uri=path+"/monitor/perfTask/toZoneManageList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	} else{
		var uri=path+"/monitor/discover/toDiscoverDeviceList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	
}

/**
 * 选择站点
 * @return
 */
function loadSiteInfo(){
	var path=getRootName();
	var moClassId = $("#ipt_moClassId").attr("alt");
	if(moClassId==null ||moClassId==""){
		$.messager.alert("提示", "请先选择设备类型！", "info");
	}
	//dns
	else if(moClassId==91 ){
		var uri=path+"/monitor/webSite/toWebSiteDnsInfo?flag=1";
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	//ftp
	else if(moClassId==92){
		var uri=path+"/monitor/webSite/toWebSiteFtpInfo?flag=1";
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	//http
	else if(moClassId==93){
		var uri=path+"/monitor/webSite/toWebSiteHttpInfo?flag=1";
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	//tcp
	else if(moClassId==94){
		var uri=path+"/monitor/webSite/toWebSitePortInfo?flag=1";
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
}

/**
 * 暂停任务
 * @param taskId
 * @return
 */
function doStop(taskId){
		var path = getRootName();
		var uri = path + "/monitor/perfTask/stopOrRunTask";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"taskId" : taskId,
				"operateStatus" : 4,
				"progressStatus":1,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "任务已暂停！", "info");
					reloadTable();
				} else {
					$.messager.alert("提示", "任务暂停失败！", "error");
				}
			}
		}
		ajax_(ajax_param);
}

/**
 * 运行任务
 * @param taskId
 * @return
 */
function doStart(taskId){
	var path = getRootName();
	var uri = path + "/monitor/perfTask/stopOrRunTask";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"taskId" : taskId,
			"operateStatus" : 5,
			"progressStatus":1,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "任务已启动！", "info");
				reloadTable();
			} else {
				$.messager.alert("提示", "任务启动失败！", "error");
			}
		}
	}
	ajax_(ajax_param);
}

//查看详情
function toShowInfo(taskId){
	$('.input').val("");
	$('.inputV1').val("");
	$('.inputV3').val(""); 
	$("#flag").val("edit");
//	setRead(taskId);
//	getAuthTypeForDetail(taskId);
	var isPartentTabs = parent.document.getElementById("popWin");
	if(isPartentTabs != null ){
		parent.$('#popWin').window({
			title:'采集任务详情',
			width:800,
			height : 480,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			modal:true,
			href: getRootName() + '/monitor/perfTask/toShowPerfTaskDetail?taskId='+taskId
		});
	}else{
		parent.parent.$('#popWin').window({
			title:'采集任务详情',
			width:800,
			height : 460,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			modal:true,
			href: getRootName() + '/monitor/perfTask/toShowPerfTaskDetail?taskId='+taskId
		});
	}
	
	
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
				$("#lbl_dbName").text(data.dbmsCommunityBean.dbName);
				$("#lbl_dbmsType").text(data.dbmsCommunityBean.dbmsType);
				$("#lbl_dbUserName").text(data.dbmsCommunityBean.userName);
				$("#lbl_dbPassword").text(data.dbmsCommunityBean.password);
				$("#lbl_dbPort").text(data.dbmsCommunityBean.port);
				$("#lbl_deviceIp").text(data.dbmsCommunityBean.ip);
				$("#lbl_domainName").text(data.domainName);
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
				$("#lbl_port").text(data.vmIfCommunityBean.port);
				$("#lbl_userName").text(data.vmIfCommunityBean.userName);
				$("#lbl_password").text(data.vmIfCommunityBean.password);
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
					$("#lbl_url").text(data.middleWareCommunityBean.url);
				}
				doInitDBMoInfo(taskId,moClassId);
			}
		};
	ajax_(ajax_param);
}

//初始化详情信息
function setRead(taskId){
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
				if(snmpPort==0 || snmpPort==""){
					$("#lbl_snmpPort").text('161');
					$("#lbl_snmpPortV3").text('161');
				}
				if(readCommunity==''){
					$("#lbl_readCommunity").text('public');
					$("#lbl_readCommunityV3").text('public');
				}
//				snmpPort=$("#lbl_snmpPort").val();
//				readCommunity=$("#lbl_readCommunity").val();
				var moName=$('#lbl_moName').val();
				var moPort=$('#lbl_snmpPort').val();
				var snmpVersion=$("#lbl_snmpVersion").val();
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
				doInitMoInfo(taskId,moId);
			}
		};
	ajax_(ajax_param);
}


function doInitMoInfo(taskId,moId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
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
//				$("#tblTaskInfo tr:eq("+(7+i)+")").remove();
				html+="<td><b class='title'><label id='lbl_mo"+data[i].split(",")[0]+"'>"+data[i].split(",")[1]+"：</label></b>" 
				+"<label class='inputs' id='lbl_doIntervals"+data[i].split(",")[0]+"'>0</label>&nbsp;分</td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitorView').append(html);
			toShowMoInfo(taskId);
			
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
			
			for (var i=0;i<data.length;i++){ 
				var moTypeName=$('#lbl_mo'+data[i].split(",")[0]).html();
				var interval=data[i].split(",")[1];
				$('#lbl_doIntervals'+data[i].split(",")[0]).html(interval/60);
				}
		}
	};	
	ajax_(ajax_param);	
}


function getOpProcess(){
	var grid = $('#tblPerfTask');  
	var options = grid.datagrid('getPager').data("pagination").options;  
	//当前第几页
	var curr = options.pageNumber;  
//	console.log("curr==="+curr);
	//数据总数
	var total = options.total;  
//	console.log("total==="+total);
	//总共几页
	var max = Math.ceil(total/options.pageSize); 
//	console.log("max==="+max);
	//当前页的行数
	var rows=10;
	if(curr < max){
		rows = 10;
	}else{
		rows = total-(curr-1)*10;
	}
//	console.log("rows===="+rows);
	
	var taskIds='';
	for ( var i = 0; i < rows; i++) {
//		console.log("i====="+i+",  id====="+$('#tblPerfTask').datagrid('getRows')[i]['taskId']+" 	,progressStatus======"+$('#tblPerfTask').datagrid('getRows')[i]['progressStatus']);
		var processValue=$('#tblPerfTask').datagrid('getRows')[i]['progressStatus'];
		
		if(processValue < 5){
			var taskId=$('#tblPerfTask').datagrid('getRows')[i]['taskId'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/perfTask/getProcessByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskIds":taskIds,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			var errorInfoLst = data.errorInfoLst;
			var processLst = data.processLst;
			var statusLst = data.statusLst;
			// 更新操作进度,错误信息,任务状态
			for ( var i = 0; i < processLst.length; i++) { 
				var indexValue = + processLst[i].split(":")[1];
				var process = (processLst[i].split(":")[0]);
				var errorInfo = "";
				var status = (statusLst[i].split(":")[0]);
				var errorInfo2 = (errorInfoLst[i].split(":")[0]);
				if(errorInfo2!=null && errorInfo2!="" && errorInfo2!="null"){
					errorInfo = (errorInfoLst[i].split(":")[0]);
				}
//				console.log("status=="+status+",process=="+process+" , indexValue=="+indexValue);
				$('#tblPerfTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
						progressStatus:process,
						errorInfo:errorInfo,
						status:status
					}
				} );
			}
			
		}
	};	
	ajax_(ajax_param);	

}

/**
 * 更新采集机
 * @return
 */
function getOpCollector(){
	var grid = $('#tblPerfTask');  
	var options = grid.datagrid('getPager').data("pagination").options;  
	//当前第几页
	var curr = options.pageNumber;  
//	console.log("curr==="+curr);
	//数据总数
	var total = options.total;  
//	console.log("total==="+total);
	//总共几页
	var max = Math.ceil(total/options.pageSize); 
//	console.log("max==="+max);
	//当前页的行数
	var rows=10;
	if(curr < max){
		rows = 10;
	}else{
		rows = total-(curr-1)*10;
	}
//	console.log("rows===="+rows);
	var taskIds='';
	for ( var i = 0; i < rows; i++) {
		var serverName=$('#tblPerfTask').datagrid('getRows')[i]['serverName'];
//		console.log(i+"====="+serverName);
		if(serverName=="" || serverName==null){
			var taskId=$('#tblPerfTask').datagrid('getRows')[i]['taskId'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	if(taskIds != null && taskIds != ""){
		var path=getRootName();
		var uri=path+"/monitor/perfTask/getServerNameByTaskId";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				data:{
			"taskIds":taskIds,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			for ( var j = 0; j < data.length; j++) { 
				var indexValue = + data[j].split(":")[1];
				var serverName="";
				var name = (data[j].split(":")[0]);
				if(name!=null &&name!=""&&name!="null"){
					serverName = name;
				}
				$('#tblPerfTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
					serverName:serverName
				}
				} );
			}
		}
		};	
		ajax_(ajax_param);	
	}
}

/*
 * 选择对象类型
 */
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/perfTask/initTree";
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
				dataTreeOrg.add(0, -1, "监测对象", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
//					console.log("id="+_id+",_parent="+_parent+",classLable="+_nameTemp);
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_moClassId','"
							+ _id + "','"+ className +  "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}

function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, className ,showVal) {
	if(showId == 1){
		showId = -1;
	}
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
					//获得模板下拉框的值
					$('#ipt_templateID').combobox({
						panelHeight : '120',
						url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId='+showId,
						valueField : 'templateID',
						textField : 'templateName',
						editable : false,
						onSelect:function(record){  
//							console.log("templateID = "+record.templateID);
							chooseTemplate();
						},
						onLoadSuccess:function(){
							//获得采集周期默认值
							getCollectPeriodVal(className);
							$("#isShowSiteTr").hide();
							$("#isShowSiteTr2").hide();
							$("#isShowIPTr").show();
							if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
								$("#snmpAuth").hide();
								$("#dbAuth").show();
								$("#vmwareAuth").hide();
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").show();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
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
								$("#deviceInfo").hide();
							}else if(showId == 8 || showId == 75){
								$("#snmpAuth").hide();
								$("#dbAuth").hide();
								$("#vmwareAuth").show();
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").show();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								$("#authType").val("Vmware");
								$("#deviceInfo").show();
							}else if(showId == 19 || showId==20 || showId==53){
								var ip = $("#ipt_deviceIp").val();
								$("#snmpAuth").hide();
								$("#dbAuth").hide();
								$("#vmwareAuth").show();
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").show();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								$("#authType").val("MiddleWare");
								$("#deviceInfo").hide();
								$("#ipt_middleWareName").val(showVal);
								if(showId == 19){
									$("#isShowUserAndPwd").hide();
									$("#ipt_middleWareName").attr("alt",3);
									$("#ipt_middlePort").val("8880");
//									$("#ipt_url").val("http://"+ip+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
								}else if(showId == 20){
									$("#isShowUserAndPwd").hide();
									$("#ipt_middleWareName").attr("alt",2);
									$("#ipt_middlePort").val("8999");
//									$("#ipt_url").val("http://"+ip+":"+"8080");
								}else if(showId == 53){
									$("#isShowUserAndPwd").show();
									$("#ipt_middleWareName").attr("alt",1);
									$("#ipt_middlePort").val("7001");
//									$("#ipt_url").val("http://"+ip+":"+"7001"+"/console/login/LoginForm.jsp");
								}
								$("#ipt_middleWareType").val(showVal);
							}else if(showId == 44){
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").show();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								$("#authType").val("room");
								$("#deviceInfo").hide();
								$("#ipt_roomPort").val('6580');
							}else if(showId == 91 || showId == 92 || showId == 93 || showId == 94){
								$("#isShowIPTr").hide();
								$("#isShowSiteTr").show();
								$("#isShowSiteTr2").show();
								$("#authType").val("site");
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								$("#deviceInfo").hide();
								if(showId == 92){
									$("#tblFtpCommunity").show();
									$("#ipt_ftpPort").val('21');
								}else if(showId == 93){
									$("#tblHttpCommunity").show();
								}
							}else if(showId == -1){
								$("#tblSNMPCommunityInfo").hide();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								$("#authType").val("unKnown");
								$("#deviceInfo").show();
							}else{
								$("#snmpAuth").show();
								$("#dbAuth").hide();
								$("#vmwareAuth").hide();
								$("#tblSNMPCommunityInfo").show();
								$("#tblAuthCommunityInfo").hide();
								$("#tblDBMSCommunity").hide();
								$("#tblMiddleWareCommunity").hide();
								$("#tblRoomCommunity").hide();
								$("#tblFtpCommunity").hide();
								$("#tblHttpCommunity").hide();
								if(showId ==  96){
									$("#authType").val("Conditons");
								}else if(showId ==73){
									$("#authType").val("Ups");
								} else{
									$("#authType").val("SNMP");
								}
								$("#deviceInfo").show();
								$('#ipt_snmpVersion').val("V1");
								$('#ipt_snmpVersion1').val("V1");
								$("#V1_snmp").show();
								$("#V1_readCommunity").show();
								$("#V3_snmp").hide();
								$("#V3_readCommunity").hide();
								$("#V3_usm").hide();
								$("#V3_auth").hide();
								$("#V3_encryption").hide();
							}
							$("#ipt_siteName").val("");
							$("#ipt_deviceIp").val("");
							$("#ipt_domainName").val("");
							$("#ipt_deviceManufacture").val("");
							$("#ipt_deviceType").val("");
						}
					});
					
				}
			}
		};
		ajax_(ajax_param);	
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

/**
 * 获得数据库服务的信息
 * @return
 */
function findDBMSServerInfo(){
	var moClassId = $("#ipt_moClassId").attr("alt");
	
		var moID = $("#ipt_dbmsServerId").val();
		var path = getRootName();
		var uri=path+"/monitor/perfTask/findDBMSServerInfo";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
			"moid" : moID,
			"moClassId":moClassId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_templateID").combobox('setValue', data.templateID);
			if(moClassId != 54){
				$("#ipt_deviceIp").val(data.ip);
				$("#ipt_domainName").val(data.domainName);
				$("#ipt_domainId").val(data.domainid);
				$("#ipt_dbName").val(data.dbname);
				if(data.dbmstype!=null){
					$("#ipt_dbmsType").val(data.dbmstype);
				}else{
					$("#ipt_dbmsType").val("DB2");
				}
				$("#ipt_dbUserName").val(data.username);
				$("#ipt_dbPassword").val(data.password);
				$("#ipt_deviceId").val(data.moid);
				$("#ipt_dbPort").val(data.port);
			}else{
//				console.log("moClassId=="+moClassId);
				var moid = $("#ipt_dbmsServerId").val();
				$("#ipt_deviceId").val(moid);
			}
			doInitDBMoList();
		}
		};
		ajax_(ajax_param);
	
}

function initDB2Community(){
	var moClassId = $("#ipt_moClassId").attr("alt");
	if(moClassId == 54){
		var deviceIP = $("#ipt_deviceIp").val();
		if(deviceIP != null && deviceIP != ""){
			var dbmsType = $("#ipt_dbmsType").val();
			var dbName= $("#ipt_dbName").val();
//			console.log("dbName="+$("#ipt_dbName").val());
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
					$("#ipt_deviceIp").val(deviceIP);
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
	}
}

/**
 * 获得中间件的信息
 * @return
 */
function findMiddleWareInfo(){
	var moID = $("#ipt_middleWareId").val();
	var moClassId = $("#ipt_moClassId").attr("alt");
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findMiddleWareInfo?moClassId="+moClassId;
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
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$("#ipt_deviceIp").val(data.ip);
			$("#ipt_domainName").val(data.domainName);
			$("#ipt_domainId").val(data.domainId);
			$("#ipt_middleUserName").val(data.userName);
			$("#ipt_middlePassword").val(data.passWord);
			$("#ipt_middlePort").val(data.port);
			$("#ipt_url").val(data.url);
			$("#ipt_deviceId").val(data.moId);
			doInitDBMoList();
		}
	};
	ajax_(ajax_param);
}

//选择模板
function chooseTemplate(){
	var moClassId = $('#ipt_moClassId').attr("alt");
	if(moClassId == 15 || moClassId ==16 || moClassId ==54 || moClassId ==81 || moClassId ==86 || moClassId == 19 || moClassId == 20 || moClassId ==53 || moClassId ==44 || moClassId == 91 || moClassId ==92 || moClassId ==93 || moClassId ==94){
		doInitDBMoList();
	}else{
		doInitMoList();
	}
}

/**
 * 根据任务ID获取监测器信息
 * @param taskId
 * @return
 */
function doInitMoList(){
	var taskId = $("#taskId").val();
	if(taskId == null || taskId == ""){
		taskId = 0;
	}
	var templateID=$("#ipt_templateID").combobox('getValue');
	if(templateID == null || templateID == ""){
		templateID = -1;
		$("#ipt_templateID").combobox('setValue', templateID);
	}
	$("#monitor  tr:not(:first)").remove();
	var deviceId=$("#ipt_moId").val();
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
				"moId":deviceId,
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
				
				var flag=$("#flag").val();
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
 * 获取监测器（数据库）
 * @return
 */
function doInitDBMoList(){
	var taskId = $("#taskId").val();
	if(taskId == null || taskId == ""){
		taskId = 0;
	}
	var templateID=$("#ipt_templateID").combobox('getValue');
	if(templateID == null || templateID == ""){
		templateID = -1;
	}
//	var length=$("#monitor").find("tr").length-1;
//	console.log("length="+length);
//	if(length>0){
//		var trs=length/2+length%2+1;
//		console.log("trs="+trs);
//		for(var i=1;i<trs;i++){
//			$("#monitor tr:last").remove();
//		}
//	}
	$("#monitor  tr:not(:first)").remove();
	var moClassId=$("#ipt_moClassId").attr("alt");
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
					+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>"; 
					if(data[i].split(",")[0]=="53"){
						html+= "<option value='0'>秒</option><option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
						"</select></td>";
					}else{
						html+= "<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
						"</select></td>";
					}
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
				var flag=$("#flag").val();
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
			if(timeUnit == 0){
				timeUnitVal = "秒";
			} else if(timeUnit == 1){
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
		$('#monitor').append(html);
		
	}
	};	
	ajax_(ajax_param);
}

function doInitDBMoInfo(taskId,moClassId){
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
			$("#monitorView  tr:not(:first)").remove()
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
//				$("#tblTaskInfo tr:eq("+(7+i)+")").remove();
				html+="<td><b class='title'><label id='lbl_mo"+data[i].split(",")[0]+"'>"+data[i].split(",")[1]+"：</label></b>" 
				+"<label class='inputs' id='lbl_doIntervals"+data[i].split(",")[0]+"'>0</label>&nbsp;分</td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitorView').append(html);
			toShowMoInfo(taskId);
			
		}
	};	
	ajax_(ajax_param);	
}

/**
 * 任务重发
 * @param taskId
 * @return
 */
function doResend(taskId){
	var path = getRootName();
	var uri = path + "/monitor/perfTask/resendTask";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"progressStatus":1,
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "任务已重发！", "info");
				reloadTable();
			} else {
				$.messager.alert("提示", "任务重发失败！", "error");
			}
		}
	}
	ajax_(ajax_param);
}

/*
 * 验证IP格式
 */
function checkIP(flag,version) {
	if(flag != 6){
		var deviceIP = $("#ipt_deviceIp").val();
		if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
				.test(deviceIP))) {
			$.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e) {
				$("#ipt_deviceIp").focus();
			});
			return false;
		}
		var port =null;
		var message = "";
		if(flag == 1){
			if(version=1){
				port = $("#ipt_snmpPortV1").val();
			}else{
				port = $("#ipt_snmpPortV3").val();
			}
			message = "SNMP端口只能输入正整数！";
		}else if(flag == 2){
			port = $("#ipt_port").val();
			message = "VMware登录端口只能输入正整数！";
		}else if(flag == 3){
			port = $("#ipt_dbPort").val();
			message = "数据库凭证端口只能输入正整数！";
		}else if(flag == 4){
			port = $("#ipt_middlePort").val();
			message = "JMX凭证端口只能输入正整数！";
		}else if(flag == 5){
			port = $("#ipt_roomPort").val();
			message = "JMX凭证端口只能输入正整数！";
		}
		if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
			$.messager.alert("提示", message, "info", function(e) {
				if(flag == 1){
					if(version=1){
						$("#ipt_snmpPortV1").focus();
					}else{
						$("#ipt_snmpPortV3").focus();
					}
				}else if(flag == 2){
					$("#ipt_port").focus();
				}else if(flag == 3){
					$("#ipt_dbPort").focus();
				}else if(flag == 4){
					$("#ipt_middlePort").focus();
				}else if(flag == 5){
					$("#ipt_roomPort").focus();
				}
				
			});
			return false;
		}
		return true;
	}else{
		var siteName = $("#ipt_siteName").val();
		if(siteName == "" || siteName == null){
			$.messager.alert("提示", "站点名称不能为空，请选择！", "info");
			return false;
		}else{
			return true;
		}
	}
}

/**
 * 获得zoneManager信息
 */
function findZoneManagerInfo(){
	var moClassId = $("#ipt_moClassId").attr("alt");
	var moID = $("#ipt_zoneManagerId").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findZoneManagerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moID" : moID,
			"moClassId":moClassId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$("#ipt_deviceIp").val(data.ipAddress);
			$("#ipt_domainName").val(data.domainName);
			$("#ipt_domainId").val(data.domainID);
			$("#ipt_roomUserName").val(data.userName);
			$("#ipt_roomPassWord").val(data.passWord);
			$("#ipt_roomPort").val(data.port);
			$("#ipt_deviceId").val(data.moID);
			doInitDBMoList();
		}
	};
	ajax_(ajax_param);
}

/**
 * 获得采集周期的默认值
 */
function getCollectPeriodVal(className){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initCollectPeriodVal?className="+className;
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
		}
	};
	ajax_(ajax_param);
}

/**
 * 获得采集周期的默认值
 */
function getCollectPeriodVal2(className,showId,taskId){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initCollectPeriodVal?className="+className;
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
			$("#ipt_moClassId").val(data.moClassName);
			$("#ipt_moClassId").attr("alt", showId);
			$("#isShowSiteTr").hide();
			$("#isShowSiteTr2").hide();
			$("#isShowIPTr").show();
			if(showId == 15 || showId ==16 || showId ==54 || showId ==81 || showId ==86){
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").show();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").hide();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				$("#authType").val("dbms");
				if(showId == 15 ){
					$("#ipt_dbmsType").val("oracle");
					$("#ipt_dbPort").val("1521");
					initDBVal(taskId);
				}else if(showId == 16 ){
					$("#ipt_dbmsType").val("mysql");
					$("#ipt_dbPort").val("3306");
					initDBVal(taskId);
				}else if(showId == 54 ){
					$("#ipt_dbmsType").val("db2");
					$("#ipt_dbPort").val("50000");
					initDB2Val(taskId);
				}else if(showId == 81 ){
					$("#ipt_dbmsType").val("sybase");
					$("#ipt_dbPort").val("5000");
					initDBVal(taskId);
				}else if(showId == 86 ){
					$("#ipt_dbmsType").val("mssql");
					$("#ipt_dbPort").val("1433");
					initDBVal(taskId);
				}
			}else if(showId == 8 || showId == 75){
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").show();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").hide();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				$("#authType").val("Vmware");
				initVmwareVal(taskId);
			}else if(showId == 19 || showId==20 || showId==53){
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").show();
				$("#tblRoomCommunity").hide();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				$("#authType").val("MiddleWare");
				initMiddleWareVal(taskId);
			}else if(showId == 44){
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").show();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				$("#authType").val("room");
				initRoomDetail(taskId);  
			}else if(showId == 91 || showId == 92 || showId == 93 || showId == 94){
				$("#isShowIPTr").hide();
				$("#isShowSiteTr").show();
				$("#isShowSiteTr2").show();
				$("#authType").val("site");
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").hide();
				$("#deviceInfo").hide();
				$("#tblHttpCommunity").hide();
				if(showId == 92){
					$("#tblFtpCommunity").show();
					$("#ipt_ftpPort").val('21');
					initFtpDetail(taskId);
				}else if(showId == 93){
					$("#tblHttpCommunity").show();
					initHttpDetail(taskId);
				}else if(showId == 91){
					initDnsDetail(taskId);
				}else if(showId == 94){
					initTcpDetail(taskId);
				}
			}else if(showId == -1){
				$("#tblSNMPCommunityInfo").hide();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").hide();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				$("#authType").val("unKnown");
				initUnknownDeviceVal(taskId);  
			}else{
				$("#tblSNMPCommunityInfo").show();
				$("#tblAuthCommunityInfo").hide();
				$("#tblDBMSCommunity").hide();
				$("#tblMiddleWareCommunity").hide();
				$("#tblRoomCommunity").hide();
				$("#tblFtpCommunity").hide();
				$("#tblHttpCommunity").hide();
				if(showId == 96){
					$("#authType").val("Conditons");
				}else if(showId==73){
					$("#authType").val("Ups");
				} else{
					$("#authType").val("SNMP");
				}
				initUpdateVal(taskId,showId);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 获得zoneManager信息
 */
function getStatus(){
	var grid = $('#tblPerfTask');  
	var options = grid.datagrid('getPager').data("pagination").options;  
	var curr = options.pageNumber;  
	//数据总数
	var total = options.total;  
	
	//总共几页
	var max = Math.ceil(total/options.pageSize); 
	
	//当前页的行数
	var rows=10;
	if(curr < max){
		rows = 10;
	}else{
		rows = total-(curr-1)*10;
	}
	
	var taskIds='';
	for ( var i = 0; i < rows; i++) {
		var processValue=$('#tblPerfTask').datagrid('getRows')[i]['progressStatus'];
		
		if(processValue = 5){
			var taskId=$('#tblPerfTask').datagrid('getRows')[i]['taskId'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/perfTask/getProcessByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskIds":taskIds,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			var statusLst = data.statusLst;
			// 更新操作进度,错误信息,任务状态
			for ( var i = 0; i < statusLst.length; i++) { 
				var status = (statusLst[i].split(":")[0]);
//				console.log("status=="+status);
				
				//如果状态是已删除，操作进度已完成，刷新表格使其不展示
				if(status == -1){
//					console.log(">>>");
					reloadTable();
				}
			}
			
		}
	};	
	ajax_(ajax_param);	
}

/**
 * 批量重发
 * @return
 */
function doBatchResend(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定重发所选中的任务？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/doBatchResend?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						if(data == "") {
							$.messager.alert("提示", "任务重发成功！", "info");
							reloadTable();
						} else {
							data=data.substring(0,data.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+data+"的任务已完成，不能执行重发操作！", "error");
							reloadTable();
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

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
//				console.log("data=="+data)
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
	$("#ipt_serverId").attr("alt", 0);
	$("#btnUnChose").hide();
}


/**
 * 指定采集机
 */
function toAppointCollector(taskId,moClassId,serverId,serverName,deviceIp){
	parent.$('#popWin').window({
		title:'指定采集机',
		width:600,
		height : 200,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		modal:true,
		href: getRootName() + '/monitor/perfTask/toAppointCollector?taskId='+taskId+'&serverId='+serverId+'&serverName='+serverName+'&deviceIp='+deviceIp+'&moClassId='+moClassId
	});
}


/**
 * 查找dns信息
 * @return
 */
function findSiteDnsInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findSiteDnsInfo";
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
			$("#ipt_deviceId").val(data.moID);
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$('#ipt_siteName').val(data.siteName);
			$('#ipt_siteUrl').val(data.domainName);
			doInitDBMoList();
		}
	};
	ajax_(ajax_param);		
}

/**
 * 查找ftp信息
 */
function findSiteFtpInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findSiteFtpnfo";
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
			$("#ipt_deviceId").val(data.moID);
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$('#ipt_siteName').val(data.siteName);
			$('#ipt_siteUrl').val(data.ipAddr);
			var isAuth = data.isAuth ;
			$('#ipt_isAuth').val(data.isAuth);
			$('#ipt_ftpIPAddr').val(data.ipAddr);
			$('ipt_ftpPort').val(data.port);
			if(isAuth == 2){
				$("#tblFtpCommunity").show();
				$('#ipt_ftpUserName').val(data.userName);
				$('#ipt_ftpPassWord').val(data.password);
			}else{
				$("#tblFtpCommunity").hide();
			}
			doInitDBMoList();
		}
	};
	ajax_(ajax_param);		

}

/**
 * 查找HTTP信息
 */
function findSiteHttpInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findSiteHttpnfo";
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
			$("#ipt_deviceId").val(data.moID);
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$('#ipt_siteName').val(data.siteName);
			$('#ipt_siteUrl').val(data.httpUrl);
			$('#ipt_httpUrl').val(data.httpUrl);
			var flag = data.requestMethod;
			if(flag ===1){
				$("input:radio[name='requestMethod'][value=1]").attr("checked",'checked');
			}else if(flag ===2){
				$("input:radio[name='requestMethod'][value=2]").attr("checked",'checked');
			}else if(flag ===3){
				$("input:radio[name='requestMethod'][value=3]").attr("checked",'checked');
			}
			doInitDBMoList();
		}
	};
	ajax_(ajax_param);		
}

/**
 * 查找tcp信息
 * @return
 */
function findSitePortInfo(){
	var moId = $("#ipt_webSiteMoID").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findSitePortInfo";
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
			$("#ipt_deviceId").val(data.moID);
			$("#ipt_templateID").combobox('setValue', data.templateID);
			$('#ipt_siteName').val(data.siteName);
			$('#ipt_siteUrl').val(data.ipAddr);
			doInitDBMoList();
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
				iterObj(data,"ipt");
				$("#deviceInfoView").hide();
//				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#ipt_siteName").val(data.deviceIp);
				$('#ipt_siteUrl').val(data.ipAddr);
				
				doInitDBMoList();
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
				iterObj(data,"ipt");
				$("#deviceInfoView").hide();
//				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#ipt_siteName").val(data.deviceIp);
				$('#ipt_siteUrl').val(data.ipAddr);
				$('#ipt_isAuth').val(data.isAuth);
				$('#ipt_ftpIPAddr').val(data.ipAddr);
				if(data.isAuth == 2){
					$("#tblFtpCommunity").show();
					if(data.siteCommunityBean != null){
						$("#ipt_ftpUserName").val(data.siteCommunityBean.userName);
						$("#ipt_ftpPassWord").val(data.siteCommunityBean.password);
						$("#ipt_ftpPort").val(data.port);
					}
				}else{
					$("#tblFtpCommunity").hide();
				}
				
				doInitDBMoList();
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
				iterObj(data,"ipt");
				$("#deviceInfoView").hide();
				var moClassId = data.moClassId;
				$("#ipt_siteName").val(data.deviceIp);
				$('#ipt_siteUrl').val(data.ipAddr);
				$('#ipt_httpUrl').val(data.ipAddr);
				if(data.siteCommunityBean != null){
					var requestMethod = data.siteCommunityBean.requestMethod;
					if(requestMethod ===1){
						$("input:radio[name='requestMethod'][value=1]").attr("checked",'checked');
					}else if(requestMethod ===2){
						$("input:radio[name='requestMethod'][value=2]").attr("checked",'checked');
					}else if(requestMethod ===3){
						$("input:radio[name='requestMethod'][value=3]").attr("checked",'checked');
					}
				}
				doInitDBMoList();
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
				iterObj(data,"ipt");
				$("#deviceInfoView").hide();
//				iterObj(data,"lbl");
				var moClassId = data.moClassId;
				$("#ipt_siteName").val(data.deviceIp);
				$('#ipt_siteUrl').val(data.ipAddr);
				
				doInitDBMoList();
			}
		};
	ajax_(ajax_param);
}

/**
 * 批量暂停
 * @return
 */
function doBatchStop(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定暂停所选中的任务？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/doBatchStop?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						var notFinishIds = data.notFinishIds;
						var stopStatusIds = data.stopStatusIds;
//						console.log("notFinishIds=="+notFinishIds);
//						console.log("stopStatusIds=="+stopStatusIds);
						if(notFinishIds == "" && stopStatusIds == "") {
							$.messager.alert("提示", "批量暂停任务成功！", "info");
						} else if(notFinishIds != "" && stopStatusIds != ""){
							notFinishIds = notFinishIds.substring(0,notFinishIds.lastIndexOf(","));
							stopStatusIds = stopStatusIds.substring(0,stopStatusIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+notFinishIds+"的任务未完成；任务ID为"+stopStatusIds+"的任务已停止，不能执行暂停操作！", "error");
						} else if(notFinishIds != "" && stopStatusIds == ""){
							notFinishIds = notFinishIds.substring(0,notFinishIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+notFinishIds+"的任务未完成，不能执行暂停操作！", "error");
						} else if(notFinishIds == "" && stopStatusIds != ""){
							stopStatusIds = stopStatusIds.substring(0,stopStatusIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+stopStatusIds+"的任务已停止，不能执行暂停操作！", "error");
						}
						reloadTable();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 批量恢复
 */
function doBatchStart(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定恢复所选中的任务？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/doBatchStart?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						var notFinishIds = data.notFinishIds;
						var startStatusIds = data.startStatusIds;
//						console.log("notFinishIds=="+notFinishIds);
//						console.log("startStatusIds=="+startStatusIds);
						if(notFinishIds == "" && startStatusIds == "") {
							$.messager.alert("提示", "批量恢复任务成功！", "info");
						} else if(notFinishIds != "" && startStatusIds != ""){
							notFinishIds = notFinishIds.substring(0,notFinishIds.lastIndexOf(","));
							startStatusIds = startStatusIds.substring(0,startStatusIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+notFinishIds+"的任务未完成；任务ID为"+startStatusIds+"的任务运行中，不能执行恢复操作！", "error");
						} else if(notFinishIds != "" && startStatusIds == ""){
							notFinishIds = notFinishIds.substring(0,notFinishIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+notFinishIds+"的任务未完成，不能执行恢复操作！", "error");
						} else if(notFinishIds == "" && startStatusIds != ""){
							startStatusIds = startStatusIds.substring(0,startStatusIds.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+startStatusIds+"的任务运行中，不能执行恢复操作！", "error");
						}
						reloadTable();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}
