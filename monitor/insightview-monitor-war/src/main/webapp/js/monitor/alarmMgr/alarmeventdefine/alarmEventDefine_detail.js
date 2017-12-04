 
var path = getRootName();

$(document).ready(function() {
	var defineId=$('#defineId').val();
	var recoverId=$('#recoverId').val();
	var flag=$('#index').val();
//	console.log("flag == "+flag);
	if(flag==0){
		var selectedVal = 0;
	}else{
		var selectedVal = 1;
	}
//	console.log("selectedVal:"+selectedVal);
	jQuery('#alarmTabs').tabs({
		  selected:selectedVal,
    });
	toShowAlarmDefineInfo(defineId);
//    if(recoverId == -1){
//    	$('#alarmTabs').tabs('close', 1);
//		toShowAlarmDefineInfo(defineId);
//		toShowRepeatePolicy(defineId);
//	    checkAlarmFilter(defineId);
//	}else{
//		toShowAlarmDefineInfo(defineId);
//		toShowAlarmPairInfo(recoverId);
//		toShowRecoverFilter(recoverId);
//		toShowRepeatePolicy(defineId);
//		checkAlarmFilter(defineId);
//	}
});


/*
 * 告警事件详情信息
 */
function  toShowAlarmDefineInfo(alarmDefineID){
	var recoverId=$('#recoverId').val();
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
				iterObj(data,"lbl");
				if(data.moName!=null && data.moName!="null"){
					$("#lbl_moName").text(data.moName);
				}else if(data.alarmSourceMOID==0){
					$("#lbl_moName").text("所有");
				}else{
					$("#lbl_moName").text("");
				}
//				console.log("recoverId=="+recoverId);
				if(recoverId != -1){
					toShowAlarmPairInfo(recoverId);
				}else{
					$('#alarmTabs').tabs('close', 1);
//					toShowRecoverFilter(recoverId);
					toShowRepeatePolicy(alarmDefineID);
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 清除事件详情
 */
function  toShowAlarmPairInfo(recoverId){
	var defineId=$('#defineId').val();
	if(recoverId==''||recoverId==null || recoverId== "-1"){
		$("#lbl_recoverAlarmName").text("");
		$("#lbl_recoverAlarmTitle").text("");
		$("#lbl_recoverMoName").text("");
		$("#lbl_recoverCategoryName").text("");
		$("#lbl_recoverAlarmOID").text("");
		$("#lbl_recoverAlarmTypeName").text("");
		$("#lbl_recoverAlarmLevelName").text("");
		$("#lbl_recoverDescription").text("");
		$("#lbl_recoverAlarmManual").text("");
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
//					iterObj(data,"lbl");
					$("#lbl_recoverAlarmName").text(data.alarmName);
					$("#lbl_recoverAlarmTitle").text(data.alarmTitle);
					if(data.moName!=null && data.moName!="null"){
						$("#lbl_recoverMoName").text(data.moName);
					}else if(data.alarmSourceMOID==0){
						$("#lbl_recoverMoName").text("所有");
					}else{
						$("#lbl_recoverMoName").text("");
					}
					if(data.categoryName != null){
						$("#lbl_recoverCategoryName").text(data.categoryName);
					}
					$("#lbl_recoverAlarmOID").text(data.alarmOID);
					if(data.alarmTypeName != null){
						$("#lbl_recoverAlarmTypeName").text(data.alarmTypeName);
					}
					if(data.alarmLevelName != null){
						$("#lbl_recoverAlarmLevelName").text(data.alarmLevelName);
					}
					$("#lbl_recoverDescription").text(data.description);
					$("#lbl_recoverAlarmManual").text(data.alarmManual);
					
//					toShowRecoverFilter(recoverId);
					toShowRepeatePolicy(defineId);
					
				}
			};
		ajax_(ajax_param);
	}
}

/*
 * 清除事件的清除策略
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
//				iterObj(data,"lbl");
				$("#lbl_recoverTimeWindow").text(data.timeWindow);
				if(data.isUsed == 0){
					$("#lbl_recoverIsUsed").text("停用");
				}else if(data.isUsed == 1){
					$("#lbl_recoverIsUsed").text("启用");
				}
			}
		};
	ajax_(ajax_param);
}
/*
 * 重复策略详情页面
 */
function toShowRepeatePolicy(alarmDefineID){
	var defineId=$('#defineId').val();
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
				if(data.alarmName!=null && data.alarmName!=""){
					iterObj(data,"lbl");
					$("#lbl_repeatAlarmName").text(data.alarmName);
					if(data.isUsed == 0){
						$("#lbl_isUsed").text("停用");
					}else if(data.isUsed == 1){
						$("#lbl_isUsed").text("启用");
					}
					$("#isRepeat").val(true);
				}else{
					var recoverId=$('#recoverId').val();
					if(recoverId == -1){
						$('#alarmTabs').tabs('close', 1);
					}else{
						$('#alarmTabs').tabs('close', 2);
					}
					$("#isRepeat").val(false);
				}
				checkAlarmFilter(defineId);
			}
		};
	ajax_(ajax_param);
}

/*
 * 验证改告警事件是否与过滤策略
 */
function checkAlarmFilter(alarmDefineID){
	var path=getRootName();
	var uri=path+"/monitor/alarmOriginalEventFilter/checkAlarmFilter";
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
				if (true == data || "true" == data) {
					  getAlarmFilterCondition(alarmDefineID);
		        	  toShoFilterPolicy(alarmDefineID);
				} else {
//					$("#divAlarmFilter").css('display','none'); 
					var recoverId=$('#recoverId').val();
					var isRepeat = $("#isRepeat").val();
//					console.log("recoverId=="+recoverId+",isRepeat="+isRepeat);
					if(recoverId != -1 && (isRepeat == true || isRepeat == "true")){
						$('#alarmTabs').tabs('close',3);
					}else if(recoverId != -1 && (isRepeat == false || isRepeat == "false")){
						$('#alarmTabs').tabs('close',2);
					}else if(recoverId == -1 && (isRepeat == false || isRepeat == "false")){
						$('#alarmTabs').tabs('close',1);
					}else if(recoverId == -1 && (isRepeat == true || isRepeat == "true")){
						$('#alarmTabs').tabs('close',2);
					}
					var flag=$('#index').val();
					if(flag==0){
						var selectedVal = 0;
					}else{
						var selectedVal = 1;
					}
					jQuery('#alarmTabs').tabs({
						  selected:selectedVal,
				    });
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 过滤策略详情页面
 */
function toShoFilterPolicy(alarmDefineID){
	var path = getRootName();
	$('#viewFilter')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : '640',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
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
						onLoadSuccess :function(data){
							initOfflineCollector();
						},
						columns : [ [
								{
									field : 'keyWord',
									title : '变量',
									width : 100,
								},
								{
									field : 'keyOperator',
									title : '运算符',
									width : 80
								},
								{
									field : 'keyValue',
									title : '过滤值',
									width : 130,
								},
								 ] ]
					});
}

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
				if(data.match == "all"){
					$("#lbl_match").text("全部");
				}else if(data.match == "any"){
					$("#lbl_match").text("任何");
				}
				
				if(data.action == "0"){
					$("#lbl_action").text("丢弃事件");
				}else if(data.action == "1"){
					$("#lbl_action").text("接受上发");
				}
			}
		};
	ajax_(ajax_param);
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
                $("#lbl_isOffline").text("离线");
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
                $("#lbl_isOffline").text("在线");
            }
        }
    };
    ajax_(ajax_param);
}
