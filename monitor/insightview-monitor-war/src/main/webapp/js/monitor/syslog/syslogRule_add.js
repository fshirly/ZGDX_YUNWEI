$(document).ready(function(){
    var facilityArray = ['0,Kernel', '1,User Level', '2,Mail System', '3,System Daemon', '4,Security One', '5,Syslogd', '6,LPR', '7,NNTP', '8,UUCP', '9,Clock Daemon One', '10,Security Two', '11,FTP Daemon', '12,NTP', '13,Log Audit', '14,Log Alert', '15,Clock Daemon Two', '16,Local Use Zero','17,Local Use One','18,Local Use Two','19,Local Use Three','20,Local Use Four','21,Local Use Five','22,Local Use Six','23,Local Use Seven'];
    var facilityVals = "";
    for (var i = 0; i < facilityArray.length; i++) {
        facilityVals += "<input type='checkbox' id='ipt_f" + i +
        "' name='facilityType' value='" +
        facilityArray[i].split(",")[0] +
        "'/>&nbsp;&nbsp;&nbsp;&nbsp;" +
        facilityArray[i].split(",")[1] +
        "<br/>"
    }
    $("#ipt_facility").html(facilityVals);
    
    var severityArray = ['0,Emergency', '1,Alert', '2,Critical', '3,Error', '4,Warning', '5,Notice', '6,Informational', '7,Debug'];
    var severityVals = "";
    for (var i = 0; i < severityArray.length; i++) {
        severityVals += "<input type='checkbox' id='ipt_s" + i +
        "' name='severityType' value='" +
        severityArray[i].split(",")[0] +
        "'/>&nbsp;&nbsp;&nbsp;&nbsp;" +
        severityArray[i].split(",")[1] +
        "<br/>"
    }
    $("#ipt_severity").html(severityVals);
    
    //采集机列表
    doInitCollecterTable();
});

/**
 * 初始化采集机列表
 */
function doInitCollecterTable(){
    var path = getRootName();
	var uri = path + '/monitor/syslogRule/listSyslogCollector'
	
    $('#tblAddCollector').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: '250',
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
//        pagination: true,// 分页控件
        rownumbers: true,// 行号
        //        fit: true,// 自动大小
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
        }, {
            field: 'isOffline',
            title: '采集机类型',
            width: 150,
            align: 'center',
			formatter : function(value, row) {
				if(value == '1'){
					return "离线";
				}
				return "在线";
			}
        }]]
    });
}

/**
 * 点击单选按钮
 */
function edit(){
    var isAll = $('input[name="isAll"]:checked').val();
    if (isAll == 1) {
        $("#trServerIp").hide();
    } else {
        $("#trServerIp").show();
    }
    $("#deviceips").val("");
    $("#ipt_serverIP").val("");
}

/**
 * 选择设备
 */
function chooseServerIP(){
    var path = getRootName();
    var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=chooseMore&index=0";
    window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
    //    var obj = new Object();
    //    obj.name = "";
    //    window.showModalDialog(uri, "", "dialogWidth=900px;dialogHeight=300px");
}

/**
 * 多选设备对象信息
 */
function showDeviceInfos(){
    $("#ipt_serverIP").val("");
    var showOptions = "";
    var deviceips = $("#deviceips").val();
    //    console.log("deviceips=" + $("#deviceips").val());
    if (deviceips != null && deviceips != "") {
        var deviceipLst = deviceips.split(",");
        for (var i = 0; i < deviceipLst.length; i++) {
            showOptions += deviceipLst[i] + "\r\n";
        }
    }
    $("#ipt_serverIP").val(showOptions);
}

/**
 * 全选事件信息源
 */
function selectAllFacility(){
    var checkboxs = document.getElementsByName('facilityType');
    for (var j = 0; j < checkboxs.length; j++) {
        checkboxs[j].checked = true;
    }
}

/**
 * 全不选事件信息源
 */
function selectNoFacility(){
    var checkboxs = document.getElementsByName('facilityType');
    for (var j = 0; j < checkboxs.length; j++) {
        checkboxs[j].checked = false;
    }
}

/**
 * 全选事件级别
 */
function selectAllSeverity(){
    var checkboxs = document.getElementsByName('severityType');
    for (var j = 0; j < checkboxs.length; j++) {
        checkboxs[j].checked = true;
    }
}

/**
 * 全选事件级别
 */
function selectNoSeverity(){
    var checkboxs = document.getElementsByName('severityType');
    for (var j = 0; j < checkboxs.length; j++) {
        checkboxs[j].checked = false;
    }
}

/**
 * 点击确定按钮
 */
function toAdd(){
    checkSyslogForm();
}

var facilityArrChk = [];
var facilitylist = '';
var severityArrChk = [];
var severitylist = '';
var keyword = "";
/**
 * 新增前校验
 */
function checkSyslogForm(){
    var result = checkInfo("#divAddSyslog");
    if (result) {
        var isDevice = isSelectDevice();
        //        console.log("isDevice:" + isDevice);
        if (isDevice == false) {
            $.messager.alert("提示", "至少选择一个设备！", "info");
        } else {
            var deviceips = $("#deviceips").val();
            var deviceipArray = deviceips.split(",");
            if (deviceipArray.length > 50) {
                $.messager.alert("提示", "最多指定50个设备！", "info");
            } else {
                $('input:checked[name=facilityType]').each(function(){
                    facilityArrChk.push($(this).val());
                });
                
                $('input:checked[name=severityType]').each(function(){
                    severityArrChk.push($(this).val());
                });
                
                keyword = $("#ipt_keyword").val();
                if (keyword == "多个关键字用','分隔") {
                    keyword = "";
                }
                if (keyword == "" && facilityArrChk.length == 0 && severityArrChk.length == 0) {
                    $.messager.alert("提示", "至少选择一个过滤条件！", "info");
                } else {
                    checkRuleName();
                }
            }
            
        }
        
    }
}

/**
 * 判断指定设备时有没选择设备
 */
function isSelectDevice(){
    var isAll = $('input[name="isAll"]:checked').val();
    if (isAll == 1) {
        return true;
    } else {
        var deviceips = $("#deviceips").val();
        //        console.log("deviceips==" + deviceips);
        //        console.log(typeof(deviceips));
        if (deviceips == "" || deviceips == null) {
            return false;
        } else {
            return true;
        }
    }
}

/**
 * 检验规格名称
 */
function checkRuleName(){
    var ruleName = $("#ipt_ruleName").val();
    var path = getRootName();
    var uri = path + "/monitor/syslogRule/checkRuleName";
    var ajax_param = {
        url: uri,
        type: "post",
        dataType: "json",
        async: false,
        data: {
            "ruleName": ruleName,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data == true || data == "true") {
                doAddSyslogRule();
            } else {
                $.messager.alert("提示", "该规则名称已存在！", "info");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 新增
 */
function doAddSyslogRule(){
    for (var i = 0; i < facilityArrChk.length; i++) {
        facilitylist += facilityArrChk[i] + ",";
    }
    for (var i = 0; i < severityArrChk.length; i++) {
        severitylist += severityArrChk[i] + ",";
    }
    
    var ruleName = $("#ipt_ruleName").val();
    var isAll = $('input[name="isAll"]:checked').val();
    var serverIP = $("#deviceips").val();
    if (isAll == 1) {
        serverIP = "*";
    }
    
    //获得采集机id
    var checkedItems = $('#tblAddCollector').datagrid("getChecked");
    var collectorIds = null;
    $.each(checkedItems, function(index, item){
		var isOffline = '0';
		if(item.isOffline == '1'){
			isOffline = '1';
		}
        if (null == collectorIds) {
            collectorIds = item.serverid+":"+isOffline;
        } else {
            collectorIds += ',' + item.serverid + ":" + isOffline;
        }
    });
    //    console.log("serverIP:" + serverIP);
    var path = getRootName();
    var uri = path + "/monitor/syslogRule/doAddSyslogRule";
    var ajax_param = {
        url: uri,
        type: "post",
        dataType: "json",
        async: false,
        data: {
            "ruleName": ruleName,
            "serverIP": serverIP,
            "facilitylist": facilitylist,
            "severitylist": severitylist,
            "keyword": keyword,
            "note": $("#ipt_note").val(),
            "collectorIds": collectorIds,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            var addRuleFlag = data.addRuleFlag;
            var isHaveTask = data.isHaveTask;
            var addTaskFlag = data.addTaskFlag;
            if (addRuleFlag == true || addRuleFlag == "true") {
                if (isHaveTask == false || isHaveTask == "false") {
                    $.messager.alert("提示", "syslog监测配置新建成功！", "info");
                } else {
                    if (addTaskFlag == true || addTaskFlag == "true") {
                        $.messager.alert("提示", "syslog监测配置新建成功,并且创建syslog任务成功！", "info");
                    } else if (addTaskFlag == true || addTaskFlag == "true") {
                        $.messager.alert("错误", "syslog监测配置新建成功,创建syslog任务失败！", "error");
                    }
                }
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            } else {
                $.messager.alert("错误", "syslog监测配置新建失败！", "error");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 根据任务类型更新采集机列表
 */
function changeCollector() {
	doInitCollecterTable();
}