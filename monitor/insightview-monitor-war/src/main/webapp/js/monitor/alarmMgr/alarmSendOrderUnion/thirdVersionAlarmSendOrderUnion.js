$(document).ready(function() {
	$.ajax({
		url : getRootName() + "/materialApply/isSendMessage",
		success : function(data) {
			if (data == "false") {
				$("#isSendMessage").attr("checked", false);
			} else {
				$("#isSendMessage").attr("checked", true);
			}
		},
		error : function() {
			$("#isSendMessage").attr("checked", true);
		}

	});
	initWorkOrderForm();
	var resourceWorkingGroupId;
	var resourceDefaultHandlerId;
});

function clearResource(){
	$('#incidentConfigurationItemName').val('');
}


//点击关联资源跳转页面
function toAddCatelog4Incident(){

	$('#ipt_name').val('');
	$('#ipt_ip').val('');
    $('#ipt_resType').combobox({
        panelHeight : '120',
        url : getRootName() + '/eventManage/queryTypeDefine',
        valueField : 'resTypeId',
        textField : 'resTypeName',
        editable : false
    });
    
	initIncidentResDept();
	
	resetForm('tblAddCateDef');
	
	doInitTable();
    $('#tblResCiList4Incident').datagrid('unselectAll');
    
	$('#divResCi4Incident').dialog('open');

	
}

/**
 * 打开告警详情页面
 * 
 * @return
 */

function toView(id) {
	parent.parent.$('#popWin').window(
			{
				title : '告警详情',
				width : 800,
				height : 550,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/alarmActive/toAlarmActiveDetail?alarmID='
						+ id
			});
}


function initIncidentResDept(){

    var ajax_param = {
        url : getRootName() + "/permissionDepartment/findOrgAndDeptVal",
        type : "post",
        datdType : "json",
        data : {
            "t" : Math.random()
        },
        error : function() {
            $.messager.alert("错误", "ajax_error", "error");
        },
        success : function(data) {
            // 得到树的json数据源
            var jsonData = eval('(' + data.menuLstJson + ')');
            $("#ipt_dept").f_combotree({
                rootName : "部门",
                height : 200,
                data : jsonData,
                titleField : 'name',
                idField : 'id',
                onBeforeSelect : function(node) {
                    if (node.id.substring(0, 1) == 'D') {
                        return true;
                    }
                    return false;
                },
                onSelect : function(node) {
                    $(this).next().val(node.id.substring(1));
                }
            });
            $("#ipt_dept").next().val($("#ipt_dept").next().val().replace(/\D/g,''));
        }
    };
    ajax_(ajax_param);

}

//初始化资源列表
function doInitTable(){

	$('#tblResCiList4Incident').datagrid({
		queryParams : {
            "name" : '',
            "ip" : '',
            "ciTypeId" : '',
            "departmentId" : ''
        },
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 308,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		url :  getRootName() + '/eventManage/queryResCiList',
		remoteSort : false,
		idField : 'ciId',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [  {
            field : 'serialNo',
            title : '资源编号',
            width : 150,
            align : 'center'
        },{
            field : 'name',
            title : '资源名称',
            width : 150,
            align : 'center'
        },{
            field : 'ip',
            title : 'IP地址',
            width : 120,
            align : 'center',
            formatter : function(value, row, index){
            	if(value == "null"){
            		return "";
            	}else{
            		return value;
            	}
            }
        },{
            field : 'resTypeName',
            title : '资源类型',
            width : 120,
            align : 'center'
        },{
            field : 'departmentName',
            title : '所属部门',
            width : 120,
            align : 'center'
        },{
            field: 'mids',
            title: '操作',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                return "<a style='cursor: pointer;' onclick=javascript:dosel('" + row.ciId + "','" + row.name + "','" + row.resTypeName + "'," + row.workingGroupId + "," + row.defaultHandlerId + ");>选择</a>";
            }
        }] ]
	});

}

/**
 * 选择相关配置项
 * 
 * @author Maowei
 */
function dosel(ciId, name, resTypeName, workingGroupId, defaultHandlerId) {
	$("#incidentConfigurationItem").val(ciId); // 相关配置项隐藏域
	$("#incidentConfigurationItemName").val(name); // 相关配置项展示区
	$("#incidentConfigurationItemName").change();
	$("#incidentCiTypeName").val(resTypeName);
	$('#resourceWorkingGroupId').val(workingGroupId);
	$('#resourceDefaultHandlerId').val(defaultHandlerId);
	resourceWorkingGroupId = workingGroupId;
	resourceDefaultHandlerId = defaultHandlerId;
	$('#divResCi4Incident').dialog('close');
	
}

/**
 * 取消选择
 * 
 * @author Maowei
 */
function doCancel4Incident() {
	$('#divResCi4Incident').dialog('close');
}

/**
 * 查询
 */
function reloadIncidentCiTable() {
	var name = $("#ipt_name").val();
	var ip = $('#ipt_ip').val();
    var resType = $('#ipt_resType').combobox('getValue');
    var deptId = $('#ipt_dept').next().val();
	$('#tblResCiList4Incident').datagrid('options').queryParams = {
		"name" : name,
		"ip" : ip,
        "ciTypeid" : resType,
        "departmentId" : deptId
	};
	$('#tblResCiList4Incident').datagrid('reload');
	$('#tblResCiList4Incident').datagrid('unselectAll');
}

function incidentConfigurationItemChanged() {
    $.ajax({
        type : "GET",
        url : '../eventManage/queryProviderName?configurationItem=' + $('#incidentConfigurationItem').val(),
        success : function(data) {
            $('#supplierName').attr('value', data);
        }
    });
}


function initWorkOrderForm(){
	var workingGroupId;
	var defaultHandlerId;

	// 故障分类
	$("#eventTypeId").f_combotree({
		url : getRootName() + "/eventTypeManage/findEventTypeTreeVal",
		rootName : "类别",
		parentIdField : 'parentTypeId',
		showNodeTitle : "故障",
		height : 200,
		onSelect : function(node) {
			getEventCaseNameList($("#eventTypeId").f_combotree('getValue'));
			workingGroupId = node.nodeData.workingGroupId;
	        defaultHandlerId = node.nodeData.defaultHandlerId;
		},
		onClear : function(node) {
			getEventCaseNameList(2);
		}
	});
	
	//点击提交跳转到下一页面
	$("#nextPage").click(function(){
		if(!checkInfo('#tableWorkOrder')) {
			return;
		}
		$('#workOrderProcessNext').window('open');
		initWorkOrderFormNext(workingGroupId, defaultHandlerId, resourceWorkingGroupId, resourceDefaultHandlerId);
	});	
}

function closeAllWindow(){
    $('#workOrderProcessNext').window('close');	
    firstWindowCancel();

}
function firstWindowCancel(){
	 parent.$('#popWin4').window('close');
	 $('.workOrderProcessNext').parent().remove();
	 $('.divResCi4Incident').parent().remove();
}

//告警派发工具
util = {};
util.serializeForm = function(form, enablenull) {
	var o = {};
	$ && $.each($(form).serializeArray(), function(index) {
		if (!this['value']) {
			return;
		}
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};


function initWorkOrderFormNext(workingGroupId, defaultHandlerId, resourceWorkingGroupId, resourceDefaultHandlerId){
	var submiting = false;
	$('#processHandler').click();
	//点击提交进行派单
	$('#workOrderProcessNext a:eq(0)').unbind().click(function(){
		if (submiting) {
			return; 
		}
		submiting = true;
		var param = $.extend({}, util.serializeForm("#formWorkOrder"), util.serializeForm("#formProcessNext"));
		if ("" == param.selectPeopleId
				|| null == param.selectPeopleName) {
			$.messager.alert("提示", "处理人必须选择");
			return;
		}
		var ids = $('#alarmIds').val();
		param.ids = ids;
		$.ajax({
		     url: getRootName() + "/monitor/AlarmSendSingle/ajaxSendSingleAlarm?isSend="+$("#isSendMessage").prop("checked"),
		     type:"post",
		     data: param,
		     dataType: "json",
		     success: function(d){
		    	 var msg = '派发成功.';
					if (!d) {
						msg = '派发失败，请稍后再试。';
		        	} 
					$.messager.alert("提示", msg, "info");	
					closeAllWindow();
					if (typeof reloadTable === 'function') {
						reloadTable();
					} else if (window.frames['component_2'].reloadTable) {
						window.frames['component_2'].reloadTable();
					} else if(frames[1].frames['alarmHost'] != undefined){
						frames[1].frames['alarmHost'].reloadTable();
					} else if(frames[1].frames['alarmsFirst'] != undefined){
						frames[1].frames['alarmsFirst'].reloadTable();
					} else if(frames[1].frames['alarmsInfo'] != undefined) {
						frames[1].frames['alarmsInfo'].reloadTable();
					}else{
						var iframes = parent.frames['component_2'].frames['ifrhomepage'];
						var frameId;
						for (var i = 0; i < iframes.contentWindow.frames.length; i++) {
							frameId = iframes.contentWindow.frames[i].frameElement.id;
							if (frameId.indexOf("AlarmActive") === 0) {
								iframes.contentWindow.refreshIfrView("" + frameId + "");
								return;
							}
						}
					}
					submiting = false;
		     },
		      error: function(){	    	  
		    	  $.messager.alert("提示",
					"派发失败，请稍后再试。");
			submiting = false;
		      }

		});
	
	});
	//返回上一步
	$('#workOrderProcessNext a:eq(1)').unbind().click(function(){
		$('#workOrderProcessNext').window('close');
		$("#summary").val("");	
		$("#selectCondition input[type='radio']").unbind();
	});
	
	if($("input[name='selectCondition']:checked").val() == "processHandler"){
		
		$.post(getRootName() + "/eventManage/findStaffInfo2",
				{groupId:13},
		function(data){
			$("#departmentMan4WorkOrder li").remove();
			$.each(data,function(k,v){				
 				var listaff=$("<li></li>");
 				var checkboxName=$("<input type='radio'  name='selectPerson'>");
 				$.each(v,function(id,value){
 					if(id!='selected'){
        					if(id=='val'){
        						listaff.html(value);			        						
        					}
        					checkboxName.attr(id,value);
 					}
 					else {
 						checkboxName.attr('checked','checked');
 					}
 				});	
 				listaff.prepend(checkboxName);
 				checkboxName.bind("click",userSelect);
 				listaff.appendTo("#departmentMan4WorkOrder"); 			
			});
			userSelect();
		}		
		);		
	}
	 
	$("#selectCondition input[type='radio']").click(function(){
        var groupId, defualtpeople;
		var self = $(this);
		if (!self.is(':checked')) {
			return;
		}
		$("#selectPeopleId4WorkOrder").val("");
		$("#selectPeopleName4WorkOrder").val("");
		var uri = getRootName() + "/eventManage/findStaffInfo2";
		if($("input[name='selectCondition']:checked").val() == "processHandler"){
			groupId = 13;
		}
		else if($("input[name='selectCondition']:checked").val() == "faultHandler"){
			if(null != workingGroupId && "" != workingGroupId){
				groupId = workingGroupId;
				defualtpeople = defaultHandlerId;
			}
		}
		else if ($("input[name='selectCondition']:checked").val() == "resourceHandler"){
			if(null != resourceWorkingGroupId && "" != resourceWorkingGroupId){
				groupId = resourceWorkingGroupId;
				defualtpeople = resourceDefaultHandlerId;
			}
		};
		 $("#departmentMan4WorkOrder li").remove();
		 if(!groupId) {
	 		 return;
		}	
	
		//选人组件
		$.post(uri,
				{groupId:groupId},
		function(data){
			$.each(data,function(k,v){				
 				var listaff=$("<li></li>");
 				var checkboxName=$("<input type='radio'  name='selectPerson'>");
 				$.each(v,function(id,value){
 					if(id!='selected'){
        					if(id=='val'){
        						listaff.html(value);			        						
        					}
        					checkboxName.attr(id,value);
 					}
					if (id === "key" && value === defualtpeople + ""){
						checkboxName.attr('checked', true);
					}
 				});	
				if (k === 0) {
	 					checkboxName.attr('checked', true);
	 			}
 				listaff.prepend(checkboxName);
 				checkboxName.bind("click",userSelect);
 				listaff.appendTo("#departmentMan4WorkOrder");
 			
			});
			userSelect();
		}		
		);
	});
	$('#workOrderProcessNext a:eq(2)').unbind().click(function(){
		closeAllWindow();		
	})	
}

//用户选择事件
function userSelect() {
	var selectMan = "";
	var selectId = "";
	var checked = $('#departmentMan4WorkOrder :radio:checked');
	$.each(checked, function() {
		selectMan += $(this).attr('val') + ",";
		selectId += $(this).attr('key') + ",";
	});
	$("#selectPeopleId4WorkOrder").val(selectId.substr(0, selectId.length - 1));
	$("#selectPeopleName4WorkOrder").val(
			selectMan.substr(0, selectMan.length - 1));
}
function getEventCaseNameList(eventTypeId){
	eventCaseNameList = [];
    eventCaseIdList = []; 
    var uri=getRootName()+"/eventCaseManage/getEventCaseNameList?eventTypeId=" + eventTypeId;
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
            if(data != null){
                for (var i = 0; i < data.length; i++) {
                    eventCaseNameList.push(data[i].title);
                    eventCaseIdList.push(data[i].id);
                }
           
            }
        }
    };
    ajax_(ajax_param);
}