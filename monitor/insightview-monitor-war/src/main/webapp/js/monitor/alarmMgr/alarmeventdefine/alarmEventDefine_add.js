var defineId = $("#defineId").val();
$(document).ready(function() {
	$('#ipt_categoryID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmcategory/getAllAlarmGategory',
		valueField : 'categoryID',
		textField : 'categoryName',
//		value : '请选择',
		editable : false,
		onSelect:function(record){    
        setDefualt(0,record.categoryID);
		}
	});
	
	$('#ipt_recoverCategoryID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmcategory/getAllAlarmGategory',
		valueField : 'categoryID',
		textField : 'categoryName',
//		value : '请选择',
		editable : false,
		onSelect:function(record){   
		setDefualt(1,record.categoryID);
		}
	});
	
	$('#ipt_alarmTypeID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmtype/getAllAlarmType',
		valueField : 'alarmTypeID',
		textField : 'alarmTypeName',
//		value : '请选择',
		editable : false
	});
	
	$('#ipt_recoverAlarmTypeID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmtype/getAllAlarmType',
		valueField : 'alarmTypeID',
		textField : 'alarmTypeName',
//		value : '请选择',
		editable : false
	});
	
	$('#ipt_alarmLevelID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmlevel/getAllAlarmLevel',
		valueField : 'alarmLevelID',
		textField : 'alarmLevelName',
//		value : '请选择',
		editable : false
	});
	
	$('#ipt_recoverAlarmLevelID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmlevel/getAllAlarmLevel',
		valueField : 'alarmLevelID',
		textField : 'alarmLevelName',
//		value : '请选择',
		editable : false
	});
	
	initAlarmFilter();
	initAlarmRepeat();
	$("#divOfflineCollector").hide();
});

/*
 * 初始化过滤策略
 */
function initAlarmFilter(){
	var alarmDefineID = -1;
	if(defineId!="" && defineId !=null){
		alarmDefineID = defineId;
	}
	var path = getRootName();
	var editRow = undefined; // 定义全局变量：当前编辑的行
	 var $dg = $("#addFilter");
     $dg = $('#addFilter')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : '640',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						fitColumns:true,
						url : path + '/monitor/alarmOriginalEventFilter/viewAlarmOriginalEventFilter',
						queryParams : {
							"alarmDefineID" : alarmDefineID
						},
						remoteSort : false,
						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						scrollbarSize : 0,
						toolbar : [
						 			{
										text : '添加',
										iconCls : 'icon-add',
										handler : function() {
						             				// 添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
						             					if (editRow != undefined) {
						             						/* $dg.datagrid("endEdit", editRow);
						             						editRow = undefined; */
						             					}
						             					// 添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
						             					if (editRow == undefined) {
						             						$dg.datagrid("insertRow", {
						             							index : 0, // index start with 0
						             							row : {

						             							}
						             						});
						             						// 将新插入的那一行开户编辑状态
						             						$dg.datagrid("beginEdit", 0);
						             						// 给当前编辑的行赋值
						             						editRow = 0;
						             					}
						                         },
									}, '-', {
										text : '修改',
										iconCls : 'icon-edit',
										handler : function() {
											// 修改时要获取选择到的行
											var rows = $dg.datagrid("getSelections");
											// 如果只选择了一行则可以进行修改，否则不操作
											if (rows.length == 1) {
												// 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
												if (editRow != undefined) {
													$dg.datagrid("endEdit", editRow);
												}
												// 当无编辑行时
												if (editRow == undefined) {
													// 获取到当前选择行的下标
													var index = $dg.datagrid("getRowIndex", rows[0]);
													// 开启编辑
													$dg.datagrid("beginEdit", index);
													// 把当前开启编辑的行赋值给全局变量editRow
													editRow = index;
													// 当开启了当前选择行的编辑状态之后，
													// 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
													$dg.datagrid("unselectAll");
												}
											}
										}
									}, '-', {
										text : '删除',
										iconCls : 'icon-remove',
										handler : function() {
											// 删除时先获取选择行
											var rows = $dg.datagrid("getSelections");
											// 选择要删除的行
											if (rows.length > 0) {
												$.messager.confirm("提示", "您确定要删除吗?", function(r) {
													if (r) {
														var ids = [];
														for (var i = 0; i < rows.length; i++) {
//															console.log("删除rows[i].filterID===="+rows[i].filterID);
															ids.push(rows[i].filterID);
														}
														// 将选择到的行存入数组并用,分隔转换成字符串，
														// 本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
														doDelTblCommon(ids.join(','  ), $dg);
													}
												});
											} else {
												$.messager.alert("提示", "请选择要删除的行", "error");
											}
										}
									}, {
										text : '保存',
										iconCls : 'icon-save',
										handler : function() {
											var isValid = $dg.datagrid('validateRow',editRow);
											if (!isValid){
												return;
											}
											endEdit($dg);
											/************************/

											var updated = $dg.datagrid('getChanges');

											var effectRow = new Object();
											if (updated.length) {
												effectRow["updated"] = JSON.stringify(updated);
											}
											 if ($dg.datagrid('getChanges').length) {
												 checkKeyWord(effectRow["updated"], $dg);
											 }
											 /************************/
											// 保存后，复原到一般状态
											editRow = undefined;
											$dg.datagrid("unselectAll");
										}
									}, {
										text : '取消编辑',
										iconCls : 'icon-redo',
										handler : function() {
											// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
											editRow = undefined;
											$dg.datagrid("rejectChanges");
											$dg.datagrid("unselectAll");
										}
									} ],
						onBeforeEdit : function(index, row) {
							row.editing = true;
							$dg.datagrid('refreshRow', index);
						},
						onAfterEdit : function(index, row) {
							row.editing = false;
							$dg.datagrid('refreshRow', index);
						},
						onCancelEdit : function(index, row) {
							row.editing = false;
							$dg.datagrid('refreshRow', index);
						},
						columns : [ [
								{
									field : 'keyWord',
									title : '变量',
									width : 100,
									 editor: { type: 'combobox',
										   options: { required: true,editable:false,
													 data:[{'id':1,'text':'第1个绑定变量'},
									                         {'id':2,'text':'第2个绑定变量'},
									                         {'id':3,'text':'第3个绑定变量'},
									                         {'id':4,'text':'第4个绑定变量'},
									                         {'id':5,'text':'第5个绑定变量'},
									                         {'id':6,'text':'第6个绑定变量'},
									                         {'id':7,'text':'第7个绑定变量'},
									                         {'id':8,'text':'第8个绑定变量'},
									                         {'id':9,'text':'第9个绑定变量'},
									                         {'id':10,'text':'第10个绑定变量'},
									                         {'id':11,'text':'第11个绑定变量'},
									                         {'id':12,'text':'第12个绑定变量'},
									                         {'id':13,'text':'第13个绑定变量'},
									                         {'id':14,'text':'第14个绑定变量'},
									                         {'id':15,'text':'第15个绑定变量'},
									                         {'id':16,'text':'第16个绑定变量'},
									                         {'id':17,'text':'第17个绑定变量'},
									                         {'id':18,'text':'第18个绑定变量'},
									                         {'id':19,'text':'第19个绑定变量'},
									                         {'id':20,'text':'第20个绑定变量'}],
											          valueField:'text',
											          textField:'text'
													}
								}

								},
								{
									field : 'keyOperator',
									title : '运算符',
									width : 80,
									
//									formatter : unitFormatter, 
									editor: { type: 'combobox',
											  editable:false, 
											  options: { required: true,editable:false, 
													   data:[{'id':1,'text':'等于'},
									                         {'id':2,'text':'不等于'},
									                         {'id':3,'text':'包含'},
									                         {'id':4,'text':'不包含'}],
									          valueField:'text',
									          textField:'text'} }
								},
								{
									field : 'keyValue',
									title : '过滤值',
									width : 130,
									editor: { type: 'validatebox', options: { required: true} }
								},
								 ] ]
					});
}

/*
 * 更新过滤策略表格
 */
function reloadTbl(){
	var alarmDefineID = -1;
	var defineId = $("#defineId").val();
//	alert("defineId=="+defineId);
	if(defineId!=""&&null!=defineId){
		alarmDefineID = defineId;
	}
	$('#addFilter').datagrid('options').queryParams = {
		"alarmDefineID" : alarmDefineID
	};
	reloadTableCommon_1('addFilter');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function endEdit($dg) {
	var rows = $dg.datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$dg.datagrid('endEdit', i);
	}
}

/*
 * 删除过滤策略
 */
function doDelTblCommon(filterID, dg){
	var path=getRootName();
	var uri = path + "/monitor/alarmOriginalEventFilter/delAlarmFilter";
	var ajax_param = {
		url : uri,
		type : "post",
		dataType : "json",
		data : {
			"filterID" : filterID,
			"t" : Math.random()
		}, 
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			reloadTbl();
		}
	};
	ajax_(ajax_param);
}

/*
 * 验证过滤值
 */
function checkKeyValue(tblCommon, $dg){
	tblCommon = tblCommon.replace('[', '').replace(']', '');
	var tbl = eval('(' + tblCommon + ')');
	var keyOperator = tbl.keyOperator; 
	var keyValue = tbl.keyValue;
	if(keyOperator=="等于" || keyOperator=="不等于"){
		if (!(/^[0-9]*[0-9][0-9]*$/.test(keyValue))){
			$.messager.alert("提示", "当运算符为"+keyOperator+"时,过滤值必须为整数！", "warning");
			editRow = undefined;
			$dg.datagrid("rejectChanges");
			return false;
		}else{
			return true;
		}
	}
}
/*
 * 验证过滤策略的变量名称的唯一性
 */
function checkKeyWord(tblCommon, $dg){
	var checkRS = checkKeyValue(tblCommon, $dg);
	if(checkRS == true){
		var alarmDefineID = $("#defineId").val();
		if(alarmDefineID==""||alarmDefineID==-1){
			//$.messager.alert('提示', '请先保存主表视图信息,点右下角确定', 'hint');
			doMainAdd(tblCommon,$dg);
		}else{
			var path=getRootName();
			tblCommon = tblCommon.replace('[', '').replace(']', '');
			var tbl = eval('(' + tblCommon + ')');
			var uri = path + "/monitor/alarmOriginalEventFilter/checkKeyWord?alarmDefineID="+alarmDefineID;
			var ajax_param = {
					url : uri,
					type : "post",
					dataType : "json",
					data : tbl,
					error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if(true == data || "true" == data){
					doUpdateTblCommon(tbl, $dg,alarmDefineID);
				}else {
					$.messager.alert("提示", "该过滤策略的变量名称已存在！", "error");
					editRow = undefined;
					$dg.datagrid("rejectChanges");
					$dg.datagrid("unselectAll");
				}
			}
			};
			ajax_(ajax_param);
		}
	}
}


/*
 * 保存过滤策略
 */
function doUpdateTblCommon(tblCommon, $dg){
	var alarmDefineID = $("#defineId").val();
	var path=getRootName();
	var uri = path + "/monitor/alarmOriginalEventFilter/updateAlarmFilter?alarmDefineID="+alarmDefineID;
	var ajax_param = {
			url : uri,
			type : "post",
			dataType : "json",
			data : tblCommon,
			error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		reloadTbl();
	}
	};
	ajax_(ajax_param);
}

function checkMOID(){
	var alarmOIDSplit = $("#ipt_alarmOID").val();
	var alarmOID = $("#ipt_alarmOIDDefualt").val()+alarmOIDSplit;
	if(alarmOID=="" || alarmOID ==null){
		$.messager.alert("提示", "告警事件的告警标记不能为空", "info");
		return false;
	}else{
		return true;
	}
}

function checkRecoverMOID(){
	var recoverAlarmOIDSplit = $("#ipt_recoverAlarmOID").val();
	var recoverAlarmOID = $("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOIDSplit
	if(recoverAlarmOID=="" || recoverAlarmOID ==null){
		$.messager.alert("提示", "清除事件的告警标记不能为空", "info");
		return false
	}else{
		return true;
	}
}
function doMainAdd(tblCommon,$dg){
	
	var result=checkInfo('#divAlarmDefine');
	var path = getRootName();
	var uri=path+"/monitor/alarmEventDefine/addCauseAlarm";
	var alarmOID = $("#ipt_alarmOID").val();
	if(alarmOID=="" || alarmOID ==null){
		alarmOID="";
	}
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"alarmName" :$("#ipt_alarmName").val(),
			"alarmTitle" :$("#ipt_alarmTitle").val(),
			"alarmSourceMOID" :$("#ipt_moID").val(),
			"moName" :$("#ipt_moName").val(),
			"categoryID" :$("#ipt_categoryID").combobox("getValue"),
			"alarmOID" :$("#ipt_alarmOIDDefualt").val()+alarmOID,
			"alarmTypeID" :$("#ipt_alarmTypeID").combobox("getValue"),
			"alarmLevelID" :$("#ipt_alarmLevelID").combobox("getValue"),
			"description" :$("#ipt_description").val(),
			"alarmManual" :$("#ipt_alarmManual").val(),
			
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#defineId").val(data.defineId);
			checkKeyWord(tblCommon,$dg);
		}
	};	
	if(result == true ){
		var checkRS =checkMOID();
		if(checkRS==true){
			ajax_(ajax_param);
		}
	}else{
		$.messager.alert("提示", "请先完整填写告警事件信息，再重新保存！", "error");
	}
}

/*
 * 取消选择告警事件告警源
 */
function cancelChose(){
	$("#ipt_moName").val("");
	$("#ipt_moID").val("");
	$("#ipt_deviceIP").val("");
	$("#btnUnChose").hide();
}

/*
 * 取消选择清除事件告警源
 */
function cancelReChose(){
	$("#ipt_recoverMoID").val("");
	$("#ipt_recoverMoName").val("");
	$("#btnReUnChose").hide();
}

var accountFlag=true;
/*
 * 加载告警源页面
 */
function loadMoRescource(index){
	var path=getRootName();
	if(index==0){
		var uri=path+"/monitor/alarmEventDefine/toSelectMoRescource?flag=choose&dif=cause";
	}else{
		var uri=path+"/monitor/alarmEventDefine/toSelectMoRescource?flag=choose&dif=recover";
	}
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	$('#event_select_dlg').dialog({
	    title: '告警源选择',
	    width: 620,
	    height: 560,
	    modal: true,
		    onBeforeOpen : function(){
            if($(".event_select_dlg").size() > 1){
                $('.event_select_dlg:first').parent().remove();
            }
        },
	    href: uri
	});	
}
function showMOSourceInfo(flag,moID,moName,deviceIP){
//	console.log("flad=="+flag+" ,moID==="+moID+" ,moName=="+moName+" ,deviceIP=="+deviceIP);
	if(flag == "cause"){
		$("#ipt_moID").val(moID);
		$("#ipt_moName").val(moName);
		$("#ipt_deviceIP").val(deviceIP);
		$("#btnUnChose").show();
	}else{
		$("#ipt_recoverMoID").val(moID);
		$("#ipt_recoverMoName").val(moName);
		$("#btnReUnChose").show();
	}
}

/*
 * 告警源详情
 */
function findMOSourceInfo(flag){
	if(flag == "cause"){
		var moID = $("#ipt_moID").val();
	}else{
		var moID = $("#ipt_recoverMoID").val();
	}
	var path = getRootName();
	var uri=path+"/monitor/moSource/findMOSourceInfo";
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
			if(flag == "cause"){
				$("#btnUnChose").show();
				$("#ipt_moName").val(data.moName);
				$("#ipt_deviceIP").val(data.deviceIP);
			}else {
				$("#btnReUnChose").show();
				$("#ipt_recoverMoName").val(data.moName);
			}
		}
	};
	ajax_(ajax_param);		
}

/*
 * 初始化重复策略的告警名
 */
function initRepeatAlarmName(){
	 var alarmName = $("#ipt_alarmName").val();
	   $("#ipt_repeatAlarmName").val(alarmName);
}

/*
 * 设置默认的告警标记
 */
function setDefualt(flag,id){
	var path = getRootName();
	var uri=path+"/monitor/alarmMgr/alarmcategory/getAlarmCategoryInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"categoryID" : id,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(flag == 0){
				$("#ipt_alarmOIDDefualt").val(data.alarmOID);
			}else{
				$("#ipt_recoverAlarmOIDDefualt").val(data.alarmOID);
			}
		}
	};	
	ajax_(ajax_param);
}
function toAdd(){
	var checkRS = isRecoverEvent();
	var result = false;
	var checkMOIDRS = false;
	var timeWindow =$("#ipt_timeWindow").val();
	var alarmOnCount =$("#ipt_alarmOnCount").val();
	var upgradeOnCount =$("#ipt_upgradeOnCount").val();
//	console.log("timeWindow=="+timeWindow+",alarmOnCount==="+alarmOnCount+",upgradeOnCount==="+upgradeOnCount);
	if(checkRS==true &&(timeWindow!="" || alarmOnCount!="" || upgradeOnCount!="")){
		result=checkInfo('#addAlarm');
		checkMOIDRS =checkMOID() && checkRecoverMOID();
//		console.log("清除和重复：result===="+result);
	}else if(checkRS==true && (timeWindow=="" && alarmOnCount=="" && upgradeOnCount=="")){
		result = checkInfo('#divAlarmDefine')&&checkInfo('#divRecoverAlarm');
		checkMOIDRS =checkMOID();
//		console.log("清除、无重复：result===="+result);
	}else if(checkRS==false && (timeWindow!="" || alarmOnCount!="" || upgradeOnCount!="")){
		result = checkInfo('#divAlarmDefine')&&checkInfo('#repeatView');
		checkMOIDRS =checkMOID();
//		console.log("无清除、重复：result===="+result);
	}else{
		result = checkInfo('#divAlarmDefine');
		checkMOIDRS =checkMOID();
//		console.log("无清除、无重复：result===="+result);
	}
	//告警标识是否为空
	if(checkMOIDRS==true){
		//表单页面是否填写完整
		if(result == true ){
			//是否有清除事件
			if(checkRS==true){
				//表单校验
				var rs = checkAlarmForm();
				if(rs == true){
					checkDefineName();
				}
			}else{
				checkDefineName();
			}
		}else{
			$.messager.alert("提示", "请完善必填项信息！", "error");
			$("#btnSave").removeAttr("disabled");
		}
	}
}

/*
 * 是否新增清除事件
 */
function isRecoverEvent(){
	var recoverAlarmName = $("#ipt_recoverAlarmName").val();
	var recoverAlarmTitle = $("#ipt_recoverAlarmTitle").val();
	var recoverMoID = $("#ipt_recoverMoID").val();
	if((recoverAlarmName==null||recoverAlarmName=="") && (recoverAlarmTitle==null||recoverAlarmTitle=="") && (recoverMoID==null||recoverMoID=="")){
		return false;
	}else{
		return true;
	}
}
var filterFlag = false;
/*
 * 验证告警名称是否存在
 */
function checkDefineName(){
	var checkRS = isRecoverEvent();
	var path = getRootName();
	var defineId = $("#defineId").val();
	var uri="";
	if(defineId==""||null==defineId){
		uri = path + "/monitor/alarmEventDefine/checkAlarmName";
	}else{
		uri =  path + "/monitor/alarmEventDefine/checkNameBeforeUpdate";
	}
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"alarmName" :$("#ipt_alarmName").val(),
		"alarmDefineID" :defineId,
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if (false == data || "false" == data) {
			$.messager.alert("提示", "该告警事件的名称已存在！", "info");
			$("#btnSave").removeAttr("disabled");
		}else{
			checkAlarmOID()
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 验证清除事件的名称是否存在
 */
function checkRecoverName(){
//	var alarmName =$("#ipt_alarmName").val()
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkAlarmName";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"alarmName" :$("#ipt_recoverAlarmName").val(),
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if (false == data || "false" == data) {
			$.messager.alert("提示", "该清除事件的名称已存在！", "info");
			$("#btnSave").removeAttr("disabled");
		} else {
			checkRecoverAlarmOID();
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 验证告警事件的告警标识唯一性
 */
function checkAlarmOID(){
	var checkRS = isRecoverEvent();
	var alarmOID = $("#ipt_alarmOID").val();
	if(alarmOID=="" || alarmOID ==null){
		alarmOID="";
	}
	var path = getRootName();
	var defineId = $("#defineId").val();
	var uri="";
	if(defineId==""||null==defineId){
		uri = path + "/monitor/alarmEventDefine/checkAlarmOID";
	}else{
		uri =  path + "/monitor/alarmEventDefine/checkAlarmOIDForUpdate";
	}
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"alarmOID" : $("#ipt_alarmOIDDefualt").val()+alarmOID,
		"alarmDefineID" :defineId,
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if (false == data || "false" == data) {
			$.messager.alert("提示", "该告警事件的告警标识已存在！", "info");
			$("#btnSave").removeAttr("disabled");
		}else if(checkRS==true){
			checkRecoverName();
		}
		else{
			doAddAlarmEvent();
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 验证清除事件的告警标识唯一性
 */
function checkRecoverAlarmOID(){
	var recoverAlarmOID = $("#ipt_recoverAlarmOID").val();
	if(recoverAlarmOID=="" || recoverAlarmOID ==null){
		recoverAlarmOID="";
	}
	var path = getRootName();
	var uri =  path + "/monitor/alarmEventDefine/checkAlarmOID";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"alarmOID" : $("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOID,
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if (false == data || "false" == data) {
			$.messager.alert("提示", "该清除事件的告警标识已存在！", "info");
			$("#btnSave").removeAttr("disabled");
		}else {
			doAddAlarmEvent();
		}
	}
	};
	ajax_(ajax_param);
}

/*
 *表单校验 
 */
function checkAlarmForm(){
	var flag = true;
	var alarmName =$("#ipt_alarmName").val();
	var recoverAlarmName =$("#ipt_recoverAlarmName").val();
	var alarmSourceMOID = $("#ipt_moID").val();
	var recoverAlarmSource = $("#ipt_recoverMoID").val();
	var categoryID = $("#ipt_categoryID").combobox("getValue");
	var recoverCategoryID = $("#ipt_recoverCategoryID").combobox("getValue");
	var alarmTypeID = $("#ipt_alarmTypeID").combobox("getValue");
	var recoveralArmTypeID = $("#ipt_recoverAlarmTypeID").combobox("getValue");
	var alarmOID = $("#ipt_alarmOID").val();
	
	var mOID = $("#ipt_alarmOID").val();
	if(mOID=="" || mOID ==null){
		mOID="";
	}
	var alarmOID = $("#ipt_alarmOIDDefualt").val()+mOID;
	
	var reAlarmOID = $("#ipt_recoverAlarmOID").val();
	if(reAlarmOID=="" || reAlarmOID ==null){
		reAlarmOID="";
	}
	var recoverAlarmOID = $("#ipt_recoverAlarmOIDDefualt").val()+reAlarmOID;
	
	if(alarmName == recoverAlarmName ){
		$.messager.alert("提示", "告警事件名称与清除事件名称不能重复！", "info");
		flag = false;
	}
	if(alarmOID == recoverAlarmOID ){
		$.messager.alert("提示", "告警事件的告警标识与清除事件的告警标识不能重复！", "info");
		flag = false;
	}
	if(alarmSourceMOID != recoverAlarmSource ){
		$.messager.alert("提示", "告警事件与清除事件的告警源必须一致！", "info");
		flag = false;
	}
	if(categoryID != recoverCategoryID){
		$.messager.alert("提示", "告警事件与清除事件的告警分类必须一致！", "info");
		flag = false;
	}
	if(alarmTypeID != recoveralArmTypeID){
		$.messager.alert("提示", "告警事件与清除事件的告警类型必须一致！", "info");
		flag = false;
	}
	if(flag == false){
		$("#btnSave").removeAttr("disabled");
	}
	return flag;
}

/*
 * 新增
 */
function doAddAlarmEvent(){
	$("#btnSave").attr("disabled", true);
	var path = getRootName();
	var uri=path+"/monitor/alarmEventDefine/addAlarm";
	var alarmOID = $("#ipt_alarmOID").val();
	if(alarmOID=="" || alarmOID ==null){
		alarmOID="";
	}
	var recoverAlarmOID = $("#ipt_recoverAlarmOID").val();
	if(recoverAlarmOID=="" || recoverAlarmOID ==null){
		recoverAlarmOID="";
	}
	
	var isOffline = $('input[name="isOffline"]:checked').val();
	//获得采集机id
    var checkedItems = $('[name=serverid]:checked');
    var collectorIds = null;
    $.each(checkedItems, function(index, item){
        if (null == collectorIds) {
            collectorIds = $(item).val();
        } else {
            collectorIds += ',' + $(item).val();
        }
    });
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		async:false,
		data:{
			"causeAlarmEvent.alarmDefineID" :$("#defineId").val(),
			"causeAlarmEvent.alarmName" :$("#ipt_alarmName").val(),
			"causeAlarmEvent.alarmTitle" :$("#ipt_alarmTitle").val(),
			"causeAlarmEvent.alarmSourceMOID" :$("#ipt_moID").val(),
			"causeAlarmEvent.moName" :$("#ipt_moName").val(),
			"causeAlarmEvent.categoryID" :$("#ipt_categoryID").combobox("getValue"),
			"causeAlarmEvent.alarmOID" :$("#ipt_alarmOIDDefualt").val()+alarmOID,
			"causeAlarmEvent.alarmTypeID" :$("#ipt_alarmTypeID").combobox("getValue"),
			"causeAlarmEvent.alarmLevelID" :$("#ipt_alarmLevelID").combobox("getValue"),
			"causeAlarmEvent.description" :$("#ipt_description").val(),
			"causeAlarmEvent.alarmManual" :$("#ipt_alarmManual").val(),
			"causeAlarmEvent.deviceIP" :$("#ipt_deviceIP").val(),
			
			"recoverAlamEvent.alarmName" :$("#ipt_recoverAlarmName").val(),
			"recoverAlamEvent.alarmTitle" :$("#ipt_recoverAlarmTitle").val(),
			"recoverAlamEvent.alarmSourceMOID" :$("#ipt_recoverMoID").val(),
			"recoverAlamEvent.categoryID" :$("#ipt_recoverCategoryID").combobox("getValue"),
			"recoverAlamEvent.alarmOID" :$("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOID,
			"recoverAlamEvent.alarmTypeID" :$("#ipt_recoverAlarmTypeID").combobox("getValue"),
			"recoverAlamEvent.alarmLevelID" :$("#ipt_recoverAlarmLevelID").combobox("getValue"),
			"recoverAlamEvent.description" :$("#ipt_recoverDescription").val(),
			"recoverAlamEvent.alarmManual" :$("#ipt_recoverDlarmManual").val(),
			
//			"alarmPairwisePolicy.timeWindow" :$("#ipt_recoverTimeWindow").val(),
//			"alarmPairwisePolicy.isUsed" :$('input[name="recoverIsUsed"]:checked').val(),
			
			"alarmPairwisePolicy.timeWindow" :300,
			"alarmPairwisePolicy.isUsed" :1,
			
			"alarmRepeat.timeWindow" :$("#ipt_timeWindow").val(),
			"alarmRepeat.alarmOnCount" :$("#ipt_alarmOnCount").val(),
			"alarmRepeat.upgradeOnCount" :$("#ipt_upgradeOnCount").val(),
			"alarmRepeat.isUsed" :$('input[name="isUsed"]:checked').val(),
			
			"alarmFilter.match" : $("#ipt_match").val(),
			"alarmFilter.action" : $("#ipt_action").val(),
			
			"trapTask.isOffline":isOffline,
			"trapTask.collectorIds":collectorIds,
			
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "告警事件新增成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "告警事件新增失败！", "error");
			}
		}
	};	
	ajax_(ajax_param);
}

function toCancle(){
	$('#popWin').window('close');
	window.frames['component_2'].reloadTable();
}

/**
 * 展示重复策略的默认值
 * @return
 */
function initAlarmRepeat(){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initAlarmRuleVal";
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
			$("#ipt_timeWindow").val(data.timeWindow);
			$("#ipt_alarmOnCount").val(data.repeatNum);
			$("#ipt_upgradeOnCount").val(data.upgradeNum);
		}
	};
	ajax_(ajax_param);
}

function changeCollector(){
	var isOffline =  $('input[name="isOffline"]:checked').val();
	if(isOffline == 0){
		$("#divOfflineCollector").hide();
	}else{
		$("#divOfflineCollector").show();
		getOfflineCollector();
	}
}


/**
 * trap离线采集机列表
 */
function getOfflineCollector(){
	var path = getRootName();
	var uri = path + '/monitor/alarmEventDefine/listOfflineTrapCollector'
	
    $('#tblOfflineCollector').datagrid({
        iconCls: 'icon-edit',// 图标
        width: '640',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: uri,
        remoteSort: true,
        idField: 'serverid',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        rownumbers: true,// 行号
        fitColumns: true,
        checkOnSelect: false,
		scrollbarSize : 0,
        columns: [[{
            field: 'serverid',
            checkbox: true
        }, {
            field: 'ipaddress',
            title: '采集机IP',
            width: 150,
            align: 'center',
            sortable: true
        }]],
		onLoadSuccess: function(index,field,value){
			$('#tblOfflineCollector').datagrid('uncheckAll');
		}

    });
}
