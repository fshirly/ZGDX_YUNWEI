$(document).ready(function() {
	var policyID=$('#policyID').val();
	var smsFlag=$('#smsFlag').val();
	var emailFlag=$('#emailFlag').val();
//	console.log("policyID=="+policyID+";smsFlag=="+smsFlag+";emailFlag==="+emailFlag);
	  jQuery('#alarmNotifyTabs').tabs({
        onSelect: function(title, index){
           if(index == 0) {//通知策略定义
        	   toShowAlarmNotifyCfg(policyID);
           } else if(index == 1) {//通知用户
        	   toShowNotifyToUsers(policyID);
           } else {//通知过滤
        	   toShowNotifyFilter(policyID);
           }
        }
    });
    if(smsFlag == 0){
		$("#sms1").hide(); 
		$("#sms2").hide(); 
		$("#sms3").hide(); 
	}else{
		$("#sms1").show(); 
		$("#sms2").show(); 
		$("#sms3").show(); 
	}
    
    if(emailFlag == 0){
		$("#email1").hide(); 
		$("#email2").hide(); 
		$("#email3").hide(); 
	}else{
		$("#email1").show(); 
		$("#email2").show(); 
		$("#email3").show();
	}
});

/*
 * 通知策略定义
 */
function toShowAlarmNotifyCfg(policyID){
	var path=getRootName();
	var uri=path+"/monitor/alarmNotifyCfg/viewAlarmNotifyCfg";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"policyID":policyID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
				if(data.isSms == 0){
					$("#lbl_isSms").text("否");
				}else if(data.isSms == 1){
					$("#lbl_isSms").text("是");
				}
				if(data.isEmail == 0){
					$("#lbl_isEmail").text("否");
				}else if(data.isEmail == 1){
					$("#lbl_isEmail").text("是");
				}
				var isPhone = data.isPhone;
				var voiceMessageType = data.voiceMessageType;
				if(isPhone == 0){
					$("#lbl_isPhone").text("否");
					$("#voiceType").hide();
					$("#voiceTd").hide();
					$('#phone1').hide();
					$('#phone2').hide();
					$('#phone3').hide();
					$('#voice1').hide();
					$('#voice2').hide();
				}else if(voiceMessageType == 1){
					$("#lbl_isPhone").text("是");
					$("#lbl_voiceMessageType").text("文字模板"); 
					$("input:radio[name='voiceMessageType'][value=1]").attr("checked",'checked');
					$("#voiceType").show();
					$("#voiceTd").show();
					$('#phone1').show();
					$('#phone2').show();
					$('#phone3').show();
					$('#voice1').hide();
					$('#voice2').hide();
				}else{
					$("#lbl_isPhone").text("是");
					$("#lbl_voiceMessageType").text("录音"); 
					$("#voiceType").show();
					$("#voiceTd").show();
					$('#phone1').hide();
					$('#phone2').hide();
					$('#phone3').hide();
					$('#voice1').show();
					$('#voice2').show();
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 通知用户
 */
function toShowNotifyToUsers(policyID){
	var path = getRootName();
	$('#viewNotifyToUsers')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : '380',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/alarmNotifyToUsers/viewNotifyToUsers',
						queryParams : {
							"policyID":policyID,
						},
						remoteSort : false,
						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'userName',
									title : '用户姓名',
									width : 100,
									formatter : function(value, row, index){
										if(row.userID == -100){
											return "值班负责人";
										}else{
											return value;
										}
									}
								},
								{
									field : 'mobileCode',
									title : '手机',
									width : 130
								},
								{
									field : 'email',
									title : '邮箱',
									width : 150,
								},
								 ] ]
					});
}

/*
 * 通知过滤详情
 */
function toShowNotifyFilter(policyID){
	var path = getRootName();
	$('#viewNotifyFilter')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : '380',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/alarmNotifyFilter/viewAlarmNotifyFilter',
						queryParams : {
							"policyID":policyID,
						},
						remoteSort : false,
						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'filterKey',
									title : '过滤关键字',
									width : 200,
									align : 'left',
									formatter : function(value, row, index) {
										var rtnVal = "";
										if(value=="AlarmLevel"){
											rtnVal = "告警等级";
										}else if(value=="AlarmType"){
											rtnVal = "告警类型";
										}else if(value=="AlarmSourceMOID"){ 
											rtnVal = "告警源对象";
										}else if(value=="AlarmDefineID"){
											rtnVal = "告警事件";
										}
										return rtnVal;
									}
								},
								{
									field : 'filterValeName',
									title : '过滤值',
									width : 150,
								}
								 ] ]
					});
}