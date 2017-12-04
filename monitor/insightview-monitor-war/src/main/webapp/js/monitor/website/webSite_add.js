$(document).ready(function() {
	$('#ipt_moClassID').combobox({
		editable : false,
        panelHeight : '120',
        textField: 'label',
        valueField: 'value',
        data: [{
            label: 'DNS监控',
            value: '91',
            "selected":true 
        },{
            label: 'FTP监控',
            value: '92'
        },{
            label: 'HTTP监控',
            value: '93'
        },{
            label: 'TCP监控',
            value: '94'
        }],
		onSelect:function(rec){
        	setDefualt(rec.value);
		}
	});
	
	$("#testDiv").show();
	$("#tblSiteHttp").hide();
	$("#tblSiteDNS").show();
	$("#tblSiteFTP").hide();
	$("#tblSiteTcp").hide();
	
	$("#monitorTitle").hide();
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
});

/**
 * 选择监控类型
 * @return
 */
function setDefualt(moClassID){
//	console.log(">>>");
	$('.input').val("");
	$('.x2').val("");
	$("#ipt_httpUrl").val("");
	$("#ipt_dnsIPAddr").val("");
	$("#ipt_ftpIPAddr").val("");
	$("#ipTilte").show();
	$("#ipInput").show();
	$("#sucessTip").hide();
	$("#errorTip").hide();
//	var moClassID = $("#ipt_moClassID").combobox("getValue");
	if(moClassID == 93){//http
		$("#testDiv").show();
		$("#snmpAuth").hide();
		$("#tblSiteHttp").show();
		$("#tblSiteDNS").hide();
		$("#tblSiteFTP").hide();
		$("#tblSiteTcp").hide();
		$("#isShowFtpcom").hide();
	}
	else if(moClassID == 91){//dns
		$("#testDiv").show();
		$("#tblSiteHttp").hide();
		$("#tblSiteDNS").show();
		$("#tblSiteFTP").hide();
		$("#tblSiteTcp").hide();
		$("#isShowFtpcom").hide();
	}
	else if(moClassID == 92){//ftp
		$("#testDiv").show();
		$("#tblSiteHttp").hide();
		$("#tblSiteDNS").hide();
		$("#tblSiteFTP").show();
		$("#tblSiteTcp").hide();
		$("#ipt_ftpPort").val("21");
		$("#isShowFtpcom").hide();
	}
	else if(moClassID == 94){//tcp
		$("#testDiv").show();
		$("#tblSiteHttp").hide();
		$("#tblSiteDNS").hide();
		$("#tblSiteFTP").hide();
		$("#tblSiteTcp").show();
		$("#isShowFtpcom").hide();
	}
	$('#ipt_templateID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/perfTask/getAllTemplate?moClassId='+moClassID,
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
		var moClassID = $("#ipt_moClassID").combobox("getValue");;
		var ftpPort = $("#ipt_ftpPort").val();
		var tcpPort =  $("#ipt_tcpPort").val();
		if(ftpPort > 2147483647){
			 $.messager.alert('提示', 'FTP端口号最大值不能超过2147483647!', 'info');
		        return false;
		}
		if(tcpPort > 2147483647){
			 $.messager.alert('提示', 'TCP端口号最大值不能超过2147483647!', 'info');
		        return false;
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
	var moClassID = $("#ipt_moClassID").combobox("getValue");;
	var checkIPResult = true;
	var result
	//dns
	if(moClassID == 91){
		var result = checkInfo("#tblSiteDNS");
	}
	//ftp
	else if(moClassID == 92){
		var isAuth = $('input[name="isAuth"]:checked').val();
		if(isAuth == 1){
			var result = checkInfo("#tblSiteFTP");
		}else{
			var result = checkInfo("#tblSiteFTP") && checkInfo("#isShowFtpcom");
		}

	}
	//http
	else if(moClassID == 93){
		var result = checkInfo("#tblSiteHttp");
	}
	//tcp
	else if(moClassID == 94){
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
		checkSiteNameAndIPAddr();
	}
}

/**
 * 校验站点名称
 * @return
 */
function checkSiteNameAndIPAddr(){
	var moClassID = $("#ipt_moClassID").combobox("getValue");;
	var siteName = "";
	var ftpPort = $("#ipt_ftpPort").val();
	var tcpPort = $("#ipt_tcpPort").val();
	//dns
	if(moClassID == 91){
		siteName = $("#ipt_dnsSiteName").val();
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
	var uri = path + "/monitor/webSite/checkSiteNameAndIPAddr";
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
	var moClassID = $("#ipt_moClassID").combobox("getValue");
	var siteType = 1;
	//dns
	if(moClassID == 91){
		siteType = 2;
		siteName = $("#ipt_dnsSiteName").val();
	}
	//ftp
	else if(moClassID == 92){
		siteType = 1;
		siteName = $("#ipt_ftpSiteName").val();
	}
	//http
	else if(moClassID == 93){
		siteType = 3;
		siteName = $("#ipt_httpSiteName").val();
	}
	//tcp
	else if(moClassID == 94){
		siteType = 4;
		siteName = $("#ipt_tcpSiteName").val();
	}
	var ftpPort = $("#ipt_ftpPort").val();
	var tcpPort = $("#ipt_tcpPort").val();
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
		
		var templateID=$("#ipt_templateID").combobox('getValue');
		var moTypeLstJson =$("#moTypeLstJson").val();
		var monitorCheck = checkInfo('#monitor');
		if(monitorCheck == true){
			var path = getRootName();
			var uri = path + "/monitor/webSite/addSite?isExistSite="+isExistSite+"&siteCommunityId="+siteCommunityId+"&templateID="+templateID+"&moTypeLstJson="+moTypeLstJson;
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
//			"siteHttp.period" : $("#ipt_httpPeriod").val(),
				"siteHttp.httpUrl" : $("#ipt_httpUrl").val(),
				"siteHttp.requestMethod" : $('input[name="requestMethod"]:checked').val(),
				"siteHttp.siteInfo" : $("#ipt_httpSiteInfo").val(),
				
				
				"siteDns.siteName" : $("#ipt_dnsSiteName").val(),
				"siteDns.domainName" : $("#ipt_domainName").val(),
//			"siteDns.period" : $("#ipt_dnsPeriod").val(),
				"siteDns.ipAddr" : $("#ipt_dnsIPAddr").val(),
				"siteDns.siteInfo" : $("#ipt_dnsSiteInfo").val(),
				
				"siteFtp.siteName" : $("#ipt_ftpSiteName").val(),
				"siteFtp.ipAddr" : $("#ipt_ftpIPAddr").val(),
				"siteFtp.port" : ftpPort == '' ? 0 : ftpPort,
//			"siteFtp.period" : $("#ipt_ftpPeriod").val(),
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
					$.messager.alert("提示", "新增站点成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					$.messager.alert("错误", "新增站点失败！", "error");
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
	var moClassID = $("#ipt_moClassID").combobox("getValue");
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