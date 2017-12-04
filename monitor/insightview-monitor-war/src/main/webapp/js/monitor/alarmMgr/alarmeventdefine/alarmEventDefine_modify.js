var path = getRootName();
var defineId=$('#defineId').val();
var recoverId=$('#recoverId').val();
var flag=$('#index').val();
$(document).ready(function() {
	$('#ipt_categoryID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmcategory/getAllAlarmGategory',
		valueField : 'categoryID',
		textField : 'categoryName',
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
		editable : false
	});
	
	$('#ipt_recoverAlarmTypeID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmtype/getAllAlarmType',
		valueField : 'alarmTypeID',
		textField : 'alarmTypeName',
		editable : false
	});
	
	$('#ipt_alarmLevelID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmlevel/getAllAlarmLevel',
		valueField : 'alarmLevelID',
		textField : 'alarmLevelName',
		editable : false
	});
	
	$('#ipt_recoverAlarmLevelID').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/alarmMgr/alarmlevel/getAllAlarmLevel',
		valueField : 'alarmLevelID',
		textField : 'alarmLevelName',
		editable : false
	});
	if(recoverId == -1){
		$("#divRecoverAlarm").css('display','none'); 
	}
	initUpdateVal();
});

var _data = "" ;

/*
 * 初始化编辑页面
 */
function initUpdateVal(){
	toShowAlarmDefineInfo(defineId);
	toShowAlarmPairInfo(recoverId);
//	toShowRecoverFilter(recoverId);
	toShowRepeatePolicy(defineId);
	getAlarmFilterCondition(defineId);
	toShoFilterPolicy(defineId);
	toGetAllFilters();
	initOfflineCollector();
	if(flag==0){
		var selectedVal = 0;
	}else{
		var selectedVal = 1;
	}
	  jQuery('#modifyAlarm').tabs({
		  selected:selectedVal
    });
	  
}

/*
 * 初始化告警事件
 */
function  toShowAlarmDefineInfo(alarmDefineID){
	var path=getRootName();
	var uri=path+"/monitor/alarmEventDefine/viewAlarmEventDefine";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":alarmDefineID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				$("#alarmName").val(data.alarmName);
				$("#alarmTitle").val(data.alarmTitle);
				$("#categoryID").val(data.categoryID);
				$("#alarmOID").val(data.alarmOID);
				$("#alarmTypeID").val(data.alarmTypeID);
				$("#alarmLevelID").val(data.alarmLevelID);
				$("#description").val(data.description);
				$("#alarmManual").val(data.alarmManual);
				$("#alarmSourceMOID").val(data.alarmSourceMOID);
				if(data.alarmSourceMOID != -1 && data.alarmSourceMOID != 0){
					$("#btnUnChose").show();
				}else{
					$("#btnUnChose").hide();
				}
				
				$("#ipt_moID").val(data.alarmSourceMOID);
				$("#ipt_deviceIP").val(data.deviceIP);
				$("#trapDeviceIP").val(data.deviceIP);
				$("#trapSourceMOID").val(data.moName);
				$("#trapAlarmOID").val(data.alarmOID);
				if(data.filterExpression!=""&& data.filterExpression!=null){
					$("#filterExpression").val(data.filterExpression);
				}else{
					$("#filterExpression").val("");
				}
//				console.log("filterExpression===="+$("#filterExpression").val());
				if(data.splitAlarmOID!=null && data.splitAlarmOID!=""){
					$("#ipt_alarmOID").val(data.splitAlarmOID);
				}else{
					$("#ipt_alarmOID").val("");
				}
				if(data.categoryName!=null){
					$('#ipt_categoryID').combobox('select',data.categoryID);
				}
				if(data.alarmTypeName!=null){
					$('#ipt_alarmTypeID').combobox('select',data.alarmTypeID);
				}
				if(data.alarmLevelName!=null){
					$('#ipt_alarmLevelID').combobox('select',data.alarmLevelID);
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 初始化清除事件
 */
function  toShowAlarmPairInfo(recoverId){
	if(recoverId==''||recoverId==null || recoverId== "-1"){
		$("#ipt_recoverAlarmName").val("");
		$("#ipt_recoverAlarmTitle").val("");
		$("#ipt_recoverMoName").val("");
		$("#ipt_recoverCategoryName").val("");
		$("#ipt_recoverAlarmOID").val("");
		$("#ipt_recoverAlarmTypeName").val("");
		$("#ipt_recoverAlarmLevelName").val("");
		$("#ipt_recoverDescription").val("");
		$("#ipt_recoverAlarmManual").val("");
	}else{
		var path=getRootName();
		var uri=path+"/monitor/alarmEventDefine/viewAlarmEventDefine";
		var ajax_param={
				url:uri,
				type:"post",
				datdType:"json",
				data:{
					"alarmDefineID":recoverId,
					"t":Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					$("#recoverAlarmName").val(data.alarmName);
					$("#recoverAlarmTitle").val(data.alarmTitle);
					$("#recoverCategoryID").val(data.categoryID);
					$("#recoverAlarmOID").val(data.alarmOID);
					$("#recoverAlarmTypeID").val(data.alarmTypeID);
					$("#recoverAlarmLevelID").val(data.alarmLevelID);
					$("#recoverDescription").val(data.description);
					$("#recoverAlarmManual").val(data.alarmManual);
					$("#recoverAlarmSourceMOID").val(data.alarmSourceMOID);
					
					if(data.alarmSourceMOID != -1 && data.alarmSourceMOID != 0){
						$("#btnReUnChose").show();
					}else{
						$("#btnReUnChose").hide();
					}
					$("#ipt_recoverAlarmName").val(data.alarmName);
					$("#ipt_recoverAlarmTitle").val(data.alarmTitle);
					$("#ipt_recoverMoName").val(data.moName);
					if(data.splitAlarmOID!=null && data.splitAlarmOID!=""){
						$("#ipt_recoverAlarmOID").val(data.splitAlarmOID);
					}else{
						$("#ipt_recoverAlarmOID").val("");
					}
					$("#ipt_recoverDescription").val(data.description);
					$("#ipt_recoverAlarmManual").val(data.alarmManual);
					$("#ipt_recoverIsSystem").val(data.isSystem);
					$("#ipt_recoverMoID").val(data.alarmSourceMOID);
					if(data.categoryName!=null){
						$('#ipt_recoverCategoryID').combobox('select',data.categoryID);
					}
					if(data.alarmTypeName!=null){
						$('#ipt_recoverAlarmTypeID').combobox('select',data.alarmTypeID);
					}
					if(data.alarmLevelName!=null){
						$('#ipt_recoverAlarmLevelID').combobox('select',data.alarmLevelID);
					}
				}
			};
		ajax_(ajax_param);
	}
}

/*
 * 初始化清除事件的清除策略
 */
function toShowRecoverFilter(recoverId){
	var path=getRootName();
	var uri=path+"/monitor/alarmPairwisePolicy/viewAlarmPairwisePolicy";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":recoverId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#ipt_recoverTimeWindow").val(data.timeWindow);
				$("#recoverTimeWindow").val(data.timeWindow);
				$("#recoverIsUsed").val(data.isUsed);
				var flag = data.isUsed;
				if(flag ===0){
					$("input:radio[name='recoverIsUsed'][value=0]").attr("checked",'checked');
				}else{
					$("input:radio[name='recoverIsUsed'][value=1]").attr("checked",'checked');
				}
				
			}
		};
	ajax_(ajax_param);
}

/*
 * 初始化重复策略
 */
function toShowRepeatePolicy(alarmDefineID){
	var path=getRootName();
	var uri=path+"/monitor/alarmRepeatPolicy/viewAlarmRepeatPolicy";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":alarmDefineID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data.alarmName==null || data.alarmName==''){
					$("#repeatFlag").val("0");
				}else{
					$("#repeatFlag").val("1");
					iterObj(data,"ipt");
					if(data.alarmName!=null && data.alarmName!=''){
						$("#ipt_repeatAlarmName").val(data.alarmName);
					}else{
						$("#ipt_repeatAlarmName").val("");
					}
					if(data.timeWindow!=null){
						$("#ipt_timeWindow").val(data.timeWindow);
					}else{
						$("#ipt_timeWindow").val("");
					}
					if(data.alarmOnCount!=null && data.alarmOnCount!=''){
						$("#ipt_alarmOnCount").val(data.alarmOnCount);
					}else{
						$("#ipt_alarmOnCount").val("");
					}
					if(data.upgradeOnCount!=null && data.upgradeOnCount!=''){
						$("#ipt_upgradeOnCount").val(data.upgradeOnCount);
					}else{
						$("#ipt_upgradeOnCount").val("");
					}
					var flag = data.isUsed;
					if(flag ===0){
						$("input:radio[name='isUsed'][value=0]").attr("checked",'checked');
					}else{
						$("input:radio[type=radio][value=1]").attr("checked",'checked');
					}
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 获得所有的过滤策略
 */
function toGetAllFilters(){
	var defineId=$('#defineId').val();
	var path=getRootName();
	var uri=path+"/monitor/alarmOriginalEventFilter/getFilters";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":defineId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				var filterLst = eval('(' + data.filterLstJson + ')');
				_data = filterLst;
			}
		};
	ajax_(ajax_param);
}

/*
 * 初始化过滤策略
 */
function toShoFilterPolicy(alarmDefineID){
	var path = getRootName();
	var editRow = undefined; // 定义全局变量：当前编辑的行
	 var $dg = $("#modifyFilter");

     $dg = $('#modifyFilter')
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
	$('#modifyFilter').datagrid('options').queryParams = {
		"alarmDefineID" : defineId
	};
	reloadTableCommon_1('modifyFilter');
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
	}else{
		return true;
	}
}

/*
 * 验证过滤策略的变量名称的唯一性
 */
function checkKeyWord(tblCommon, $dg){
	var checkRS = checkKeyValue(tblCommon, $dg);
	if(checkRS == true){
		tblCommon = tblCommon.replace('[', '').replace(']', '');
		var tblCommon = eval('(' + tblCommon + ')');
		var uri = path + "/monitor/alarmOriginalEventFilter/checkKeyWord?alarmDefineID="+defineId;
		var ajax_param = {
				url : uri,
				type : "post",
				dataType : "json",
				data : tblCommon,
				error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if(true == data || "true" == data){
				doUpdateTblCommon(tblCommon, $dg);
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

/*
 * 保存过滤策略
 */
function doUpdateTblCommon(tblCommon, $dg){
	var uri = path + "/monitor/alarmOriginalEventFilter/updateAlarmFilter?alarmDefineID="+defineId;
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

/*
 * 初始化过滤的match、action
 */
function getAlarmFilterCondition(alarmDefineID){
	var path=getRootName();
	var uri=path+"/monitor/alarmOriginalEventFilter/viewAlarmFilterCondition";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":alarmDefineID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				 $("#ipt_match option").each(function(){          
			          if($(this).val()==data.match){
			           $(this).attr("selected","selected");              
			          }          
			         });
				 $("#ipt_action option").each(function(){          
			          if($(this).val()== data.action){
			           $(this).attr("selected","selected");              
			          }          
			         });
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
//	alert("flag==="+flag+"catagoryID==="+id);
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

/*
 * 加载告警源页面
 */
function loadMoRescource(index){
	var path=getRootName();
//	alert(path);
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
 * 取消选择告警事件告警源
 */
function cancelChose(){
	$("#ipt_moName").val("");
	$("#ipt_moID").val(0);
	$("#ipt_deviceIP").val("");
	$("#btnUnChose").hide();
}

/*
 * 取消选择清除事件告警源
 */
function cancelReChose(){
	$("#ipt_recoverMoID").val(0);
	$("#ipt_recoverMoName").val("");
	$("#btnReUnChose").hide();
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

function toUpdate(){
	var defineId=$('#defineId').val();
	var recoverId=$('#recoverId').val();
	var flag=$('#index').val();
	var result = false;
	var checkRS = checkISsystem();
	var checkRecoverRS = checkRecoverIsSystem();
	var checkMOIDRS = false;
	var timeWindow =$("#ipt_timeWindow").val();
	var alarmOnCount =$("#ipt_alarmOnCount").val();
	var upgradeOnCount =$("#ipt_upgradeOnCount").val();
	if(recoverId != -1 &&(timeWindow!="" || alarmOnCount!="" || upgradeOnCount!="")){
		result=checkInfo('#modifyAlarm');
		checkMOIDRS =checkMOID() && checkRecoverMOID();
//		console.log("清除和重复：result===="+result);
	}else if(recoverId != -1 && (timeWindow=="" && alarmOnCount=="" && upgradeOnCount=="")){
		result = checkInfo('#divAlarmDefine')&&checkInfo('#divRecoverAlarm');
		checkMOIDRS =checkMOID();
//		console.log("清除、无重复：result===="+result);
	}else if(recoverId == -1 && (timeWindow!="" || alarmOnCount!="" || upgradeOnCount!="")){
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
			//告警事件是否为系统自定义事件
			if(checkRS == true){
				//告警事件是否为系统自定义事件
				if(checkRecoverRS == true){
					if(recoverId!=-1){
						var rs = checkAlarmForm();
						if(rs == true){
							checkDefineName();
						}
					}else{
						checkDefineName();
					}
				}else{
					$.messager.alert("提示", "该清除事件为系统自定义事件，不能修改！", "error");
					$("#btnSave").removeAttr("disabled");
				}
			}else{
				$.messager.alert("提示", "该告警事件为系统自定义事件，不能修改！", "error");
				$("#btnSave").removeAttr("disabled");
			}
		}else{
			$.messager.alert("提示", "请完善必填项信息！", "error");
			$("#btnSave").removeAttr("disabled");
		}
	}
}

/*
 * 验证名称的唯一性
 */
function checkDefineName(){
//	var alarmName =$("#ipt_alarmName").val()
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkNameBeforeUpdate";
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
		} else {
			checkAlarmOID();
			return;
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 验证清除事件的名称是否存在
 */
function checkRecoverName(){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkNameBeforeUpdate";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"alarmName" :$("#ipt_recoverAlarmName").val(),
		"alarmDefineID" :recoverId,
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
			return;
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 验证告警事件的告警标识唯一性
 */
function checkAlarmOID(){
	var recoverId=$('#recoverId').val();
	var alarmOID = $("#ipt_alarmOID").val();
	if(alarmOID=="" || alarmOID ==null){
		alarmOID="";
	}
	var path = getRootName();
	var defineId = $("#defineId").val();
	var uri =  path + "/monitor/alarmEventDefine/checkAlarmOIDForUpdate";
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
		}else if(recoverId!=-1){
			checkRecoverName();
		}
		else{
			doUpdateAlarmEvent();
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
	var uri =  path + "/monitor/alarmEventDefine/checkAlarmOIDForUpdate";
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
			doUpdateAlarmEvent();
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
 * 告警事件是否为系统自定义
 * @return
 */
function checkISsystem(){
	var isSystem = $("#ipt_isSystem").val();
	if(isSystem==1){
		var alarmOID = $("#ipt_alarmOID").val();
		if(alarmOID=="" || alarmOID ==null){
			alarmOID="";
		}
		var alarmName = $("#ipt_alarmName").val();
		var alarmTitle=$("#ipt_alarmTitle").val();
		var categoryID=$("#ipt_categoryID").combobox("getValue");
		var alarmOID=$("#ipt_alarmOIDDefualt").val()+alarmOID;
		var alarmTypeID=$("#ipt_alarmTypeID").combobox("getValue");
		var alarmLevelID=$("#ipt_alarmLevelID").combobox("getValue");
		var description=$("#ipt_description").val();
		var alarmManual=$("#ipt_alarmManual").val();
		var alarmSourceMOID=$("#ipt_moID").val();
		
		var defaultAlarmName = $("#alarmName").val();
		var defaultAlarmTitle = $("#alarmTitle").val();
		var defaultCategoryID = $("#categoryID").val();
		var defaultAlarmOID = $("#alarmOID").val();
		var defaultAlarmTypeID = $("#alarmTypeID").val();
		var defaultAlarmLevelID = $("#alarmLevelID").val();
		var defaultDescription = $("#description").val();
		var defaultAlarmManual = $("#alarmManual").val();
		var defaultAlarmSourceMOID = $("#alarmSourceMOID").val();
		
		if(alarmName==defaultAlarmName && alarmTitle==defaultAlarmTitle && categoryID==defaultCategoryID && alarmOID==defaultAlarmOID
				&& alarmTypeID==defaultAlarmTypeID && alarmLevelID==defaultAlarmLevelID && description==defaultDescription
				&& alarmManual==defaultAlarmManual && alarmSourceMOID==defaultAlarmSourceMOID){
			return true;
		}else{
			$("#btnSave").removeAttr("disabled");
			return false;
		}
	}else{
		//console.log(">>>>>");
		return true;
	}
}

/*
 * 清除事件是否为系统自定义
 * @return
 */
function checkRecoverIsSystem(){
	var recoverIsSystem = $("#ipt_recoverIsSystem").val();
	if(recoverIsSystem==1){
		var recoverAlarmOID = $("#ipt_recoverAlarmOID").val();
		if(recoverAlarmOID=="" || recoverAlarmOID ==null){
			recoverAlarmOID="";
		}
		var alarmName=$("#ipt_recoverAlarmName").val();
		var alarmTitle=$("#ipt_recoverAlarmTitle").val();
		var categoryID=$("#ipt_recoverCategoryID").combobox("getValue");
		var alarmOID=$("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOID;
		var alarmTypeID=$("#ipt_recoverAlarmTypeID").combobox("getValue");
		var alarmLevelID=$("#ipt_recoverAlarmLevelID").combobox("getValue");
		var description=$("#ipt_recoverDescription").val();
		var alarmManual=$("#ipt_recoverDlarmManual").val();
		var alarmSourceMOID=$("#ipt_recoverMoID").val();
		var timeWindow=$("#ipt_recoverTimeWindow").val();
		var isUsed=$('input[name="recoverIsUsed"]:checked').val();
		
		var defaultAlarmName = $("#recoverAlarmName").val();
		var defaultAlarmTitle = $("#recoverAlarmTitle").val();
		var defaultCategoryID = $("#recoverCategoryID").val();
		var defaultAlarmOID = $("#recoverAlarmOID").val();
		var defaultAlarmTypeID = $("#recoverAlarmTypeID").val();
		var defaultAlarmLevelID = $("#recoverAlarmLevelID").val();
		var defaultDescription = $("#recoverDescription").val();
		var defaultAlarmManual = $("#recoverAlarmManual").val();
		var defaultAlarmSourceMOID = $("#recoverAlarmSourceMOID").val();
		var defaultTimeWindow = $("#recoverTimeWindow").val();
		var defaultIsUsed = $("#recoverIsUsed").val();
//		console.log(alarmName+"=="+defaultAlarmName);
//		console.log(alarmTitle+"=="+defaultAlarmTitle);
//		console.log(categoryID+"=="+defaultCategoryID);
//		console.log(alarmOID+"=="+defaultAlarmOID);
//		console.log(alarmManual+"=="+defaultAlarmManual);
//		console.log(alarmSourceMOID+"=="+defaultAlarmSourceMOID);
//		console.log(timeWindow+"=="+defaultTimeWindow);
//		console.log(isUsed+"=="+defaultIsUsed);
		if(alarmName==defaultAlarmName && alarmTitle==defaultAlarmTitle && categoryID==defaultCategoryID && alarmOID==defaultAlarmOID
				&& alarmTypeID==defaultAlarmTypeID && alarmLevelID==defaultAlarmLevelID && description==defaultDescription
				&& alarmManual==defaultAlarmManual && alarmSourceMOID==defaultAlarmSourceMOID){
			return true;
		}else{
			$("#btnSave").removeAttr("disabled");
			return false;
		}
	}else{
		return true;
	}
}

/*
 * 告警事件告警标识不能为空
 */
function checkMOID(){
	var alarmOIDSplit = $("#ipt_alarmOID").val();
	var alarmOID = $("#ipt_alarmOIDDefualt").val()+alarmOIDSplit;
	if(alarmOID=="" || alarmOID ==null){
		$.messager.alert("提示", "告警事件的告警标记不能为空", "info");
		$("#btnSave").removeAttr("disabled");
		return false
	}else{
		return true;
	}
}

/*
 * 清除事件的告警标识不能为空
 */
function checkRecoverMOID(){
	var recoverAlarmOIDSplit = $("#ipt_recoverAlarmOID").val();
	var recoverAlarmOID = $("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOIDSplit
	if(recoverAlarmOID=="" || recoverAlarmOID ==null){
		$.messager.alert("提示", "清除事件的告警标记不能为空", "info");
		$("#btnSave").removeAttr("disabled");
		return false
	}else{
		return true;
	}
}

/*
 * 编辑告警事件
 */
function doUpdateAlarmEvent(){
	$("#btnSave").attr("disabled", true);
	var repeatFlag = $("#repeatFlag").val();
	var defineId=$('#defineId').val();
	var recoverId=$('#recoverId').val();
	var flag=$('#index').val();
//	console.log("flag==="+flag);
	var trapAlarmOID = $("#trapAlarmOID").val();
	var path = getRootName();
	var uri=path+"/monitor/alarmEventDefine/updateAlarm?repeatFlag="+repeatFlag;
	var alarmOID = $("#ipt_alarmOID").val();
	if(alarmOID=="" || alarmOID ==null){
		alarmOID="";
	}
	var recoverAlarmOID = $("#ipt_recoverAlarmOID").val();
	if(recoverAlarmOID=="" || recoverAlarmOID ==null){
		recoverAlarmOID="";
	}
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"causeAlarmEvent.alarmDefineID" :defineId,
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
			"causeAlarmEvent.isSystem" :$("#ipt_isSystem").val(),
			"causeAlarmEvent.filterExpression" :$("#filterExpression").val(),
			"causeAlarmEvent.deviceIP" :$("#ipt_deviceIP").val(),
			"causeAlarmEvent.trapDeviceIP" :$("#trapDeviceIP").val(),
			
			"recoverAlamEvent.alarmDefineID" :recoverId,
			"recoverAlamEvent.alarmName" :$("#ipt_recoverAlarmName").val(),
			"recoverAlamEvent.alarmTitle" :$("#ipt_recoverAlarmTitle").val(),
			"recoverAlamEvent.alarmSourceMOID" :$("#ipt_recoverMoID").val(),
			"recoverAlamEvent.categoryID" :$("#ipt_recoverCategoryID").combobox("getValue"),
			"recoverAlamEvent.alarmOID" :$("#ipt_recoverAlarmOIDDefualt").val()+recoverAlarmOID,
			"recoverAlamEvent.alarmTypeID" :$("#ipt_recoverAlarmTypeID").combobox("getValue"),
			"recoverAlamEvent.alarmLevelID" :$("#ipt_recoverAlarmLevelID").combobox("getValue"),
			"recoverAlamEvent.description" :$("#ipt_recoverDescription").val(),
			"recoverAlamEvent.alarmManual" :$("#ipt_recoverDlarmManual").val(),
			"recoverAlamEvent.isSystem" :$("#ipt_recoverIsSystem").val(),
			
			"alarmPairwisePolicy.causeAlarmDefineID" :defineId,
			"alarmPairwisePolicy.recoverAlarmDefineID" :recoverId,
//			"alarmPairwisePolicy.timeWindow" :$("#ipt_recoverTimeWindow").val(),
//			"alarmPairwisePolicy.isUsed" :$('input[name="recoverIsUsed"]:checked').val(),
			"alarmPairwisePolicy.timeWindow" :300,
			"alarmPairwisePolicy.isUsed" :1,
			
			"alarmRepeat.alarmDefineID" :defineId,
			"alarmRepeat.timeWindow" :$("#ipt_timeWindow").val(),
			"alarmRepeat.alarmOnCount" :$("#ipt_alarmOnCount").val(),
			"alarmRepeat.upgradeOnCount" :$("#ipt_upgradeOnCount").val(),
			"alarmRepeat.isUsed" :$('input[name="isUsed"]:checked').val(),
			
			"alarmFilter.alarmDefineID" :defineId,
			"alarmFilter.match" : $("#ipt_match").val(),
			"alarmFilter.action" : $("#ipt_action").val(),
			
			"trapTask.alarmOID" :trapAlarmOID,
			
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == 1) {
				$.messager.alert("提示", "告警事件更新成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "告警事件更新失败！", "error");
			}
		}
	};	
	ajax_(ajax_param);
}

/*
 * 取消更新
 */
function toCancle(){
	var defineId=$('#defineId').val();
	//初始化的过滤策略
	var defaultFilterLst = JSON.stringify(_data);
	var path=getRootName();
	var uri=path+"/monitor/alarmOriginalEventFilter/getFilters";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"alarmDefineID":defineId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				var filterLst = eval('(' + data.filterLstJson + ')');
				_data = filterLst;
				doCancle(defaultFilterLst)
			}
		};
	ajax_(ajax_param);
}

function doCancle(filterLst){
	var defineId=$('#defineId').val();
	var currentFilter = JSON.stringify(_data);
	var path=getRootName();
	var uri = path + "/monitor/alarmEventDefine/toCancleUpdate?alarmDefineID="+defineId+"&filterLst="+filterLst+"&currentFilter="+currentFilter;
	var ajax_param = {
			url : uri,
			type : "post",
			dataType : "json",
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if(true == data || "true" == data){
					$('#popWin').window('close');
				}
			}
	};
	ajax_(ajax_param);
}


function setAlarmtitle(){
	 var alarmName = $("#ipt_alarmName").val();
	  $("#ipt_repeatAlarmName").val(alarmName);
}

/**
 * 初始化trap离线采集机
 */
function initOfflineCollector(){
    var alarmDefineID = $("#defineId").val();
    var path = getRootName();
    var uri = path + '/monitor/alarmEventDefine/listSelectedCollector?alarmDefineID=' + alarmDefineID
    var ajax_param = {
        url: uri,
        type: "post",
        dataType: "json",
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            var isOffline = data.isOffline;
            if (isOffline == "1") {
                $("input[name='isOffline'][value=1]").attr("checked", true);
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
                        field: 'ipaddress',
                        title: '采集机IP',
                        width: 150,
                        align: 'center',
                        sortable: true
                    }]],
                }).datagrid('loadData',data.rows);
            }
            else {
                $("input[name='isOffline'][value=0]").attr("checked", true);
            }
        }
    };
    ajax_(ajax_param);
}
