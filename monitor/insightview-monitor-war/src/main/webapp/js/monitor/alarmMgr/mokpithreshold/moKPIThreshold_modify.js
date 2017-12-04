var path = getRootName();
var ruleID = $('#ruleID').val();
$(document).ready(function(){
    initThresholdDetail();
});

/*
 * 初始化阈值规则定义信息
 */
function initThresholdDetail(){
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/initThresholdDetail";
    var ajax_param = {
        url: uri,
        type: "post",
        
        datdType: "json",
        data: {
            "ruleID": ruleID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            //console.log("编辑：data.classID=="+data.classID);
            iterObj(data, "ipt");
            var kpiID = data.kpiID;
            $('#ipt_classID').combobox({
                panelHeight: '120',
                url: getRootName() + '/monitor/alarmmgr/moKPIThreshold/initObject?kpiID=' + kpiID,
                valueField: 'classId',
                textField: 'classLable',
                //		value : '请选择',
                editable: false,
                async: false,
                onSelect: function(record){
                    choseMObject();
                }
            });
            var showId = data.kpiClassID;
            if (showId == 14) {
                $("#isShow1").hide();
            } else {
                $("#isShow1").show();
            }
            if (showId < 10) {
                $("#isShow").hide();
            } else if (showId == 22 || showId == 26 || showId == 28 || showId == 14 || showId == 15 || showId == 16 || showId == 54 || showId == 81 || showId == 82 || showId == 86 || showId == 87 || showId == 55 || showId == 17 || showId == 18 || showId == 19 || showId == 20 || showId == 53 || showId == 44 || showId == 45 || showId == 59 || showId == 60 || showId == 90 || showId == 91 || showId == 92 || showId == 93) {
                $("#isShow").hide();
            } else {
                $("#isShow").show();
            }
            var classID = data.classID;
            $("#ipt_classID ").combobox("select", classID);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/*
 * 加载指标
 */
function loadPerfKPIDef(){
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/toSelectPerfKPIDef?flag=choose";
    window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

/*
 * 指标详情
 */
function findPerfKPIDefInfo(){
    var kpiID = $("#ipt_kpiID").val();
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findPerfKPIDef";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "kpiID": kpiID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_kpiName").val(data.name);
            $("#ipt_kpiClassID").val(data.classID);
			$("#ipt_descr").val(data.quantifier);
            isShowClearBtn();
            initObject(kpiID);
            
        }
    };
    ajax_(ajax_param);
}

/**
 * 初始化指标所配置的对象类型
 */
function initObject(kpiID){
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    $("#ipt_sourceMOID").val("");
    $("#ipt_sourceMOName").val("");
    //    console.log("kpiID===" + kpiID);
    $('#ipt_classID').combobox('clear');
    $('#ipt_classID').combobox({
        panelHeight: '176',
        url: getRootName() + '/monitor/alarmmgr/moKPIThreshold/initObject?kpiID=' + kpiID,
        valueField: 'classId',
        textField: 'classLable',
        //		value : '请选择',
        editable: false,
        onSelect: function(record){
            choseMObject();
        }
    });
}


/*
 * 选择对象类型
 */
function choseMObject(){
    isShowClearBtn();
    var showId = $("#ipt_kpiClassID").val();
    var kpiName = $("#ipt_kpiName").val();
    if (kpiName == "") {
        $.messager.alert('提示', '请先选择指标！', 'info');
    } else {
        //如果选择数据库，源对象不展示
        if (showId == 14) {
            $("#isShow1").hide();
        } else {
            $("#isShow1").show();
        }
        if (showId < 10) {
            $("#isShow").hide();
        } else if (showId == 22 || showId == 26 || showId == 28 || showId == 14 || showId == 15 || showId == 16 || showId == 54 || showId == 55 || showId == 81 || showId == 82 || showId == 86 || showId == 87 || showId == 17 || showId == 18 || showId == 19 || showId == 20 || showId == 53 || showId == 44 || showId == 45 || showId == 59 || showId == 60 || showId == 90 || showId == 91 || showId == 92 || showId == 93 || showId == 96) {
            $("#isShow").hide();
        } else {
            $("#isShow").show();
        }
        
    }
}

/**
 * 判断是否展示清空按钮
 */
function isShowClearBtn(){
    var kpiName = $("#ipt_kpiName").val();
    if (kpiName == "" || kpiName == null) {
        $("#btnChoseKpi").show();
        $("#btnClearKpi").hide();
    } else {
        $("#btnChoseKpi").hide();
        $("#btnClearKpi").show();
    }
    var sourceName = $("#ipt_sourceMOName").val();
    if (sourceName == "" || sourceName == null) {
        $("#btnChoseSource").show();
        $("#btnClearSource").hide();
    } else {
        $("#btnChoseSource").hide();
        $("#btnClearSource").show();
    }
    var moName = $("#ipt_moName").val();
    if (moName == "" || moName == null) {
        $("#btnChoseMo").show();
        $("#btnClearMo").hide();
    } else {
        $("#btnChoseMo").hide();
        $("#btnClearMo").show();
    }
}

/**
 * 清空指标
 */
function clearPerfKPIDef(){
    $("#ipt_kpiID").val("");
    $("#ipt_kpiName").val("");
    $("#ipt_sourceMOID").val("");
    $("#ipt_sourceMOName").val("");
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    $("#btnChoseKpi").show();
    $("#btnClearKpi").hide();
    $('#ipt_classID').combobox('clear');
    var defultlist = [{
        "classId": "",
        "classLable": ""
    }];
    $('#ipt_classID').combobox('loadData', defultlist);
    isShowClearBtn();
}

/**
 * 清空源对象
 */
function clearSource(){
    $("#ipt_sourceMOID").val("");
    $("#ipt_sourceMOName").val("");
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    $("#btnChoseSource").show();
    $("#btnClearSource").hide();
    isShowClearBtn();
}

/**
 * 清空管理对象
 */
function clearMoRescource(){
    $("#ipt_moID").val("");
    $("#ipt_moName").val("");
    $("#btnChoseMo").show();
    $("#btnClearMo").hide();
    isShowClearBtn();
}


/*
 * 加载宿主设备
 */
function loadSource(){
    var classID = $("#ipt_classID").combobox("getValue");
    var kpiClassID = $("#ipt_kpiClassID").val();
    if (null != classID && "" != classID) {
        var path = getRootName();
        if (classID == 5 || classID == 59 || classID == 60 || classID == 7 || classID == 8 || classID == 9) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=" + classID;
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 2) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=2,5,6,59,60";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 3) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=3,7,8,9";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 4) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=4,8";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 6) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=6,59,60";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 15 || classID == 22 || classID == 24 || classID == 25 || classID == 26 || classID == 27) {
            var uri = path + '/monitor/orclDbManage/toOrclInstanceList?flag=choose';
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 16 || classID == 28 || classID == 29 || classID == 30 || classID == 31) {
            var uri = path + '/monitor/dbObjMgr/toMysqlServer?flag=choose';
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 81 || classID == 82 || classID == 83 || classID == 84) { //sybase
            var uri = path + '/monitor/sybaseDbManage/toSybaseServerList?flag=chooseForThreshold';
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 86 || classID == 87 || classID == 88 || classID == 80) { //mssql
            var uri = path + '/monitor/msDbManage/toMsServerList?flag=choose';
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 17 || classID == 18 || classID == 19 || classID == 53 || classID == 20) {
            var uri = path + '/monitor/DeviceTomcatManage/toMiddlewareList?jmxType=' + classID + '&flag=choose';
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 54 || classID == 55 || classID == 56) {
			if(kpiClassID == 23 || kpiClassID == 57){
				var uri = path + '/monitor/db2Manage/toDb2InfoList?flag=choose';
			}else{
	            var uri = path + '/monitor/db2Manage/toDb2InstanceList?flag=choose';
			}
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 44 || classID == 45 || classID == 52) {//动环系统、阅读器、电子标签
            var uri = path + "/monitor/envManager/toReaderList?flag=choose";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 46 || classID == 47 || classID == 48 || classID == 49 || classID == 50 || classID == 51 || classID == 52) {//电子标签
            var uri = path + "/monitor/envManager/toTagList?flag=choose&moClassId=" + classID;
        } else if (classID == 90) {
            var uri = path + "/monitor/webSite/toWebSiteInfo?flag=choose&includeType=1,2,3";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 91) {
            var uri = path + "/monitor/webSite/toWebSiteDnsInfo?flag=1";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 92) {
            var uri = path + "/monitor/webSite/toWebSiteFtpInfo?flag=1";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 93) {
            var uri = path + "/monitor/webSite/toWebSiteHttpInfo?flag=1";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (classID == 94) {
            var uri = path + "/monitor/webSite/toWebSitePortInfo?flag=1";
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else {
//            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&moClassId=" + parentClassID;
        	var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=0&deviceType1=" + classID;
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        }
    } else {
        $.messager.alert('提示', '请先选择对象类型', 'info');
    }
}


/*
 * 加载管理对象
 */
function loadMoRescource(){
    var classID = $("#ipt_kpiClassID").val();
    var sourceMOID = $("#ipt_sourceMOID").val();
    var index = 1;
    var parentClassID = $("#ipt_classID").combobox("getValue");
    //	console.log("加载对象classID=="+classID);
    
    if (null != classID && "" != classID) {
        var path = getRootName();
        if (classID == 2 || classID == 3 || classID == 4 || classID == 5 || classID == 6 || classID == 59 || classID == 60 || classID == 7 || classID == 8 || classID == 9) {
            var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose&index=" + index;
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        } else if (null != sourceMOID && "" != sourceMOID) {
            if (classID == 10) {
                var uri = path + "/monitor/alarmmgr/moKPIThreshold/toNetworkIf?flag=choose&deviceMOID=" + sourceMOID;
            } else if (classID == 11) {
                var uri = path + "/monitor/alarmmgr/moKPIThreshold/toVolumeList?flag=choose&deviceMOID=" + sourceMOID;
            } else if (classID == 12) {
                var uri = path + "/monitor/alarmmgr/moKPIThreshold/toCPUsList?flag=choose&deviceMOID=" + sourceMOID;
            } else if (classID == 13) {
                var uri = path + "/monitor/alarmmgr/moKPIThreshold/toMemoriesList?flag=choose&deviceMOID=" + sourceMOID;
            } else if (classID == 23) {
                //oracle表空间
                if (parentClassID == 15 || parentClassID == 22) {
                    var uri = path + "/monitor/orclDbManage/toShowOrclTbsInfo?flag=choose&instanceMOID=" + sourceMOID;
                } else if (parentClassID == 54 || parentClassID == 56) {
                    //DB2表空间资源
                    var uri = path + "/monitor/db2Manage/toShowDb2TbsInfo?flag=choose&dbMoId=" + sourceMOID;
                }
            } else if (classID == 24) {//oracle回滚段
                var uri = path + "/monitor/orclDbManage/toOrclRollSEGList?flag=choose&instanceMOID=" + sourceMOID;
            } else if (classID == 25) {//oracle数据文件
                var uri = path + "/monitor/orclDbManage/toOrclDataFileList?flag=choose&instanceMOID=" + sourceMOID;
            } else if (classID == 27) {//SGA
                var uri = path + "/monitor/orclDbManage/toOrclSgaList?flag=choose&instanceMOID=" + sourceMOID;
            } else if (classID == 29) {//数据库=========================
                var uri = path + "/monitor/dbObjMgr/toMysqlDB?flag=choose&sqlServerMOID=" + sourceMOID;
            } else if (classID == 30) {//运行对象
                var uri = path + "/monitor/alarmmgr/moKPIThreshold/toMysqlRunObj?flag=choose&index=chooseForThreshold&sqlServerMOID=" + sourceMOID;
            } else if (classID == 31) {//系统变量=========================
                var uri = path + "/monitor/dbObjMgr/toMysqlSysVar?flag=choose&sqlServerMOID=" + sourceMOID;
            } else if (classID == 32) {//J2EE应用========
                var uri = path + "/monitor/DeviceTomcatManage/toJ2eeAppList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 33) {//WebModule
                var relationPath = $("#relationPath").val();
                var jmx = relationPath.split("/")[3];
                var uri = path + '/monitor/DeviceTomcatManage/toWebModuleList?flag=choose&parentMoId=' + sourceMOID;
            } else if (classID == 34) {//Servlet
                var relationPath = $("#relationPath").val();
                var jmx = relationPath.split("/")[3];
                var uri = path + '/monitor/DeviceTomcatManage/toServletList?flag=choose&parentMoId=' + sourceMOID;
            } else if (classID == 36) {//线程池
                var uri = path + "/monitor/DeviceTomcatManage/toThreadPoolList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 37) {//类加载信息
                var uri = path + "/monitor/DeviceTomcatManage/toMiddleClassLoadList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 38) {//JDBC连接池
                var uri = path + "/monitor/DeviceTomcatManage/toMiddleWareJdbcPoolList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 39) {//JMS信息==========================
                var uri = path + "/monitor/DeviceTomcatManage/toMiddleWareJMSList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 40) {//JTA信息
                var uri = path + "/monitor/DeviceTomcatManage/toMiddleWareJTAList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 41) {//中间件内存池
                var uri = path + "/monitor/DeviceTomcatManage/toMiddlewareMemPool?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 42) {//中间件Java虚拟机
                var uri = path + "/monitor/DeviceTomcatManage/toMiddlewareJvm?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 43) {//JDBC数据源
                var uri = path + "/monitor/DeviceTomcatManage/toJDBCDSList?flag=choose&parentMoId=" + sourceMOID;
            } else if (classID == 45) {//阅读器
                var uri = path + "/monitor/envManager/toReaderList?flag=choose&moClassId=" + classID;
            } else if (classID == 46 || classID == 47 || classID == 48 || classID == 49 || classID == 50 || classID == 51 || classID == 52) {
                var uri = path + "/monitor/envManager/toTagList?flag=choose&moClassId=" + classID;
            } else if (classID == 56) {//DB2数据库资源
                var uri = path + "/monitor/db2Manage/toDb2InfoList?flag=choose&isMoid=true&instanceMOID=" + sourceMOID;
            } else if (classID == 57) {//DB2缓冲池资源
                var uri = path + "/monitor/db2Manage/toShowDb2BufferPoolInfo?flag=choose&dbMoId=" + sourceMOID;
            } else if (classID == 58) {//DB2表空间资源
                var uri = path + "/monitor/db2Manage/toShowDb2TbsInfo?flag=choose&dbMoId=" + sourceMOID;
            } else if (classID == 83) {//SyBase数据库设备
                var uri = path + "/monitor/sybaseDbManage/toSybaseDeviceList?flag=choose&serverMoId=" + sourceMOID;
            } else if (classID == 84) {//SyBase数据库
                var uri = path + "/monitor/sybaseDbManage/toSybaseDBList?flag=choose&serverMoId=" + sourceMOID;
            } else if (classID == 88) {//MySql数据库设备
                var uri = path + "/monitor/msDbManage/toMsDeviceList?flag=choose&serverMoId=" + sourceMOID;
            } else if (classID == 80) {//MySql数据库
                var uri = path + "/monitor/msDbManage/toMsDBList?flag=choose&serverMoId=" + sourceMOID;
            }
            window.open(uri, "", "height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
            
        } else {
            $.messager.alert('提示', '请先选择源对象', 'info');
        }
    } else {
        $.messager.alert('提示', '请先选择对象类型', 'info');
    }
}

/*
 * 查找接口信息
 */
function findNetworkIfInfo(){
    var moID = $("#ipt_networkIfId").val();
    //	console.log("#ipt_networkIfId"+moID);
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findNetworkIfInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.ifName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/*
 * 查找磁盘信息
 */
function findVolumesInfo(){
    var moID = $("#ipt_volumnsId").val();
    //	console.log("#ipt_volumnsId"+moID);
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findVolumesInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.moName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/*
 * 查找CPU信息
 */
function findCPUsInfo(){
    var moID = $("#ipt_cpusId").val();
    //	console.log("#ipt_cpusId"+moID);
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findCPUsInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.moName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/*
 * 查找内存信息
 */
function findMemoriesInfo(){
    var moID = $("#ipt_memoriesId").val();
    //	console.log("#ipt_memoriesId"+moID);
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMemoriesInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.moName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}


/*
 * 查找设备信息
 */
function findDeviceInfo(index){
    //	console.log("index==="+index);
    var moID = 0;
    if (index == 0) {
        moID = $("#ipt_deviceId").val();
        $("#ipt_sourceMOID").val(moID);
    } else {
        var moID = $("#ipt_deviceId").val();
        //	console.log("moID=="+moID);
        $("#ipt_moID").val(moID);
    }
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findDeviceInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (index == 0) {
                $("#ipt_sourceMOName").val(data.moname);
            } else {
                $("#ipt_moName").val(data.moname);
            }
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

function toUpdate(){
    checkBeforeUpdate();
}

/*
 * 验证编辑的阈值规则定义的唯一性
 */
function checkBeforeUpdate(){
    var classID = $("#ipt_classID").combobox("getValue");
    //	alert("classId=="+classID);
    var sourceMOID = $("#ipt_sourceMOID").val();
    var moID = $("#ipt_moID").val();
    var kpiID = $("#ipt_kpiID").val();
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/checkBeforeUpdate";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "ruleID": ruleID,
            "classID": classID,
            "sourceMOID": sourceMOID,
            "moID": moID,
            "kpiID": kpiID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (false == data || "false" == data) {
                $.messager.alert("提示", "该阈值规则定义已存在！", "info");
            } else {
                doUpdate();
                return;
            }
        }
    };
    ajax_(ajax_param);
}

/*
 * 验证源对象与管理对象
 */
function checkMOID(){
    var path = getRootName();
    var classID = $("#ipt_classID").combobox("getValue");
    var moID = $("#ipt_moID").val();
    var sourceMOID = $("#ipt_sourceMOID").val();
    if (classID == 10) {
        var uri = path + "/monitor/alarmmgr/moKPIThreshold/checkNetworkIfMOID?deviceMOID=" + sourceMOID + "&moID=" + moID;
    } else if (classID == 11) {
        var uri = path + "/monitor/alarmmgr/moKPIThreshold/checkVolMOID?deviceMOID=" + sourceMOID + "&moID=" + moID;
    } else if (classID == 12) {
        var uri = path + "/monitor/alarmmgr/moKPIThreshold/checkCPUtMOID?deviceMOID=" + sourceMOID + "&moID=" + moID;
    } else if (classID == 13) {
        var uri = path + "/monitor/alarmmgr/moKPIThreshold/checkMemMOID?deviceMOID=" + sourceMOID + "&moID=" + moID;
    }
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (false == data || "false" == data) {
                $.messager.alert("提示", "该源对象下没有此管理对象！", "info");
            } else {
                doUpdate();
                return;
            }
        }
    };
    ajax_(ajax_param);
}

/*
 * 编辑
 */
function doUpdate(){
    var classID = $("#ipt_classID").combobox("getValue");
    var sourceMOID = $("#ipt_sourceMOID").val();
    var moID = $("#ipt_moID").val();
    if (moID == "") {
        moID = -1;
    }
//    if (classID == 2 || classID == 3 || classID == 4 || classID == 5 || classID == 6 || classID == 59 || classID == 60 || classID == 7 || classID == 8 || classID == 9) {
//        moID = -1;
//    } else {
//        moID = $("#ipt_moID").val();
//    }
    var kpiID = $("#ipt_kpiID").val();
    var upThreshold = $("#ipt_upThreshold").val();
    var upRecursiveThreshold = $("#ipt_upRecursiveThreshold").val();
    var downThreshold = $("#ipt_downThreshold").val();
    var downRecursiveThreshold = $("#ipt_downRecursiveThreshold").val();
    var descr = $("#ipt_descr").val();
    var result = checkInfo('#divThresholdModify');
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/updateThreshold";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "ruleID": ruleID,
            "classID": classID,
            "moID": moID,
            "sourceMOID": sourceMOID,
            "kpiID": kpiID,
            "upThreshold": upThreshold,
            "upRecursiveThreshold": upRecursiveThreshold,
            "downThreshold": downThreshold,
            "downRecursiveThreshold": downRecursiveThreshold,
            "descr": descr,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data == true) {
                $.messager.alert("提示", "阈值规则定义更新成功！", "info");
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            } else {
                $.messager.alert("提示", "阈值规则定义更新失败！", "error");
            }
        }
    };
    if (result == true) {
        var isThresholdRS = isThreshold();
        if (isThresholdRS == true) {
            ajax_(ajax_param);
        }
    }
}

function isUpThreshold(){
    var upThreshold = $("#ipt_upThreshold").val();
    if (upThreshold == "" || upThreshold == null) {
        $.messager.alert("提示", "请先设置上限阈值！", "error");
        $("#ipt_upRecursiveThreshold").val("");
    }
}

/**
 * 是否能够设置上限阈值
 */
function isHaveUpThreshold(){
	var kpiID = $("#ipt_kpiID").val();
	if(kpiID == 247 || kpiID == 248 || kpiID == 252 || kpiID == 253 || kpiID == 254 || kpiID == 108 || kpiID == 109 || kpiID == 116){
		$.messager.alert("提示", "该指标只能设置下限阈值！", "error");
		$("#ipt_upThreshold").val("");
		return;
	}
}

function isDownThreshold(){
    var downThreshold = $("#ipt_downThreshold").val();
    if (downThreshold == "" || downThreshold == null) {
        $.messager.alert("提示", "请先设置下限阈值！", "error");
        $("#ipt_downRecursiveThreshold").val("");
    }
}

function isThreshold(){
    var upThreshold = $("#ipt_upThreshold").val();
    var downThreshold = $("#ipt_downThreshold").val();
    if (upThreshold == "" && downThreshold == "") {
        $.messager.alert("提示", "请设置上限阈值或下限阈值！", "info");
        return false;
    } else {
        return true;
    }
}

/**
 * 数据库信息
 * @return
 */
function findOracleInfo(){
    var deviceId = $("#ipt_oracleId").val();
    $("#ipt_sourceMOID").val(deviceId);
    //	console.log("ipt_sourceMOID===="+$("#ipt_sourceMOID").val());
    var path = getRootName();
    var uri = path + "/monitor/deviceManager/findOracleInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moid": deviceId,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            //			console.log(data.moname)
            $("#ipt_sourceMOName").val(data.moname);
	        isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 获得中间件的信息
 * @return
 */
function findMiddleWareInfo(){
    var moID = $("#ipt_middleWareId").val();
    $("#ipt_sourceMOID").val(moID);
    //	console.log("ipt_sourceMOID===="+$("#ipt_sourceMOID").val());
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findMiddleWareInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            //			console.log(data.ip)
            $("#ipt_sourceMOName").val(data.ip);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 线程池信息
 * @return
 */
function findThreadPoolInfo(){
    var moID = $("#ipt_threadPoolId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findThreadPoolInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.threadName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 类加载信息
 * @return
 */
function findClassLoadInfo(){
    var moID = $("#ipt_classLoadId").val();
    $("#ipt_moID").val(moID);
    //	console.log("moID=="+moID)
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findClassLoadInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.serverName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * JDBC数据源
 * @return
 */
function findJdbcDSInfo(){
    var moID = $("#ipt_jdbcDSId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findJdbcDSInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.dSName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * JDBC连接池
 * @return
 */
function findJdbcPoolInfo(){
    var moID = $("#ipt_jdbcPoolId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findJdbcPoolInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.dsName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 中间件内存池
 * @return
 */
function findMemPoolInfo(){
    var moID = $("#ipt_memPoolId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMemPoolInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.memName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * JTA信息
 * @return
 */
function findMiddleWareJTAInfo(){
    var moID = $("#ipt_middleJTAId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMiddleWareJTAInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.name);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * Java虚拟机信息
 * @return
 */
function findMiddlewareJvmInfo(){
    var moID = $("#ipt_middlewareJvmId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMiddlewareJvmInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.jvmName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询运行对象信息
 * @return
 */
function findMysqlRunObjInfo(){
    var moID = $("#ipt_runObjId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMysqlRunObjInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.moName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询表空间信息
 * @return
 */
function findOracleTBSInfo(){
    var moID = $("#ipt_oracleTbsMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleTBSInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moid": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.tbsname);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询oracle回滚段信息
 * @return
 */
function findOracleRollSegInfo(){
    var moID = $("#ipt_oracleRollSegMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleRollSegInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.segName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询数据文件信息
 * @return
 */
function findOracleDataFileInfo(){
    var moID = $("#ipt_oracleDataFileMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleDataFileInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.dataFileName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询SGA信息
 * @return
 */
function findOracleSgaInfo(){
    var moID = $("#ipt_oracleSgaMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleSgaInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.instanceName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询Oracle下面的数据库信息
 * @return
 */
function findOracleDbInfo(){
    var moID = $("#ipt_oracleDbMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleDbInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.dbName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询数据库实例信息
 * @return
 */
function findOracleInsInfo(){
    var sourceMOID = $("#ipt_oracleInsMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findOracleInsInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moid": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.instancename);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询MysqlServer信息
 * @return
 */
function findMysqlServerInfo(){
    var sourceMOID = $("#ipt_mysqlServerId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMysqlServerInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.ip);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询Mysql下面的数据库信息
 * @return
 */
function findMysqlDBInfo(){
    var moID = $("#ipt_mysqlDBId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMysqlDBInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.dbName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询系统变量信息
 * @return
 */
function findMysqlSysVarInfo(){
    var moID = $("#ipt_mysqlSysVarId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMysqlSysVarInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.varName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询j2ee应用信息
 * @return
 */
function findJ2eeAppInfo(){
    var moID = $("#ipt_j2eeAppId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findJ2eeAppInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.appName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询JMS信息
 * @return
 */
function findMiddleJMSInfo(){
    var moID = $("#ipt_middleJMSId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findMiddleJMSInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.name);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询WebModule信息
 * @return
 */
function findWebModuleInfo(){
    var moID = $("#ipt_webModuleId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findWebModuleInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.warName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询Servlet信息
 * @return
 */
function findServletInfo(){
    var moID = $("#ipt_servletId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/alarmmgr/moKPIThreshold/findServletInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.servletName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}



/**
 * 获得zoneManager信息
 */
function findZoneManagerInfo(){
    var sourceMOID = $("#ipt_zoneManagerId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findZoneManagerInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.ipAddress);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询阅读器信息
 */
function findMOReadInfo(){
    var sourceMOID = $("#ipt_moReadMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/envManager/findMOReaderInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.readerLabel);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查询电子标签
 */
function findMOTagInfo(){
    var moID = $("#ipt_moTagMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/envManager/findMOTagInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.tagID);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * DB2数据库实例资源
 * @return
 */
function findDB2InsInfo(){
    var sourceMOID = $("#ipt_db2InsMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/db2Manage/findDB2InsInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moid": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.instancename);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * DB2数据库资源（作为源对象）
 * @return
 */
function findDB2DbInfo(){
    //	console.log("isMoid===源对象");
    var sourceMOID = $("#ipt_db2DbMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/db2Manage/findDB2DbInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.databaseName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * DB2数据库资源(作为管理对象)
 * @return
 */
function findDB2DbInfo2(){
    //	console.log("isMoid===true");
    var moID = $("#ipt_db2DbMoId2").val();
    //	console.log("moID==="+moID);
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/db2Manage/findDB2DbInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.databaseName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * db2缓冲池
 * @return
 */
function findDb2BufferPoolInfo(){
    var moID = $("#ipt_db2BufferPoolMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/db2Manage/findDb2BufferPoolInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.bufferPoolName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * db2表空间
 * @return
 */
function findDB2TBSInfo(){
    var moID = $("#ipt_db2TbsMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/db2Manage/findDB2TBSInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.tbsName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}



/**
 * msSqlServer
 */
function findMsSqlServerInfo(){
    var sourceMOID = $("#ipt_msServerMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/msDbManage/findMsSqlServerInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.serverName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * SyBaseServer
 */
function findSybaseServerDetail(){
    var sourceMOID = $("#ipt_sybaseServerMoId").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/sybaseDbManage/findSyBaseServerInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.serverName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * sybase数据库设备
 * @return
 */
function findSybaseDeviceInfo(){
    var moID = $("#ipt_sybaseDeviceMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/sybaseDbManage/findSybaseDeviceInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.deviceName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * sybase数据库
 * @return
 */
function findSybaseDatabase(){
    var moID = $("#ipt_sybaseDbMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/sybaseDbManage/findSybaseDatabase";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.databaseName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 *  msSql数据库设备信息
 * @return
 */
function findMsDeviceInfo(){
    var moID = $("#ipt_msDeviceMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/msDbManage/findMsDeviceInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.deviceName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 *  msSql数据库信息
 */
function findMsSQLDbInfo(){
    var moID = $("#ipt_msSQLDbMoId").val();
    $("#ipt_moID").val(moID);
    var path = getRootName();
    var uri = path + "/monitor/msDbManage/findMsSQLDatabaseInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moId": moID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_moName").val(data.databaseName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查找dns信息
 * @return
 */
function findSiteDnsInfo(){
    var sourceMOID = $("#ipt_webSiteMoID").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findSiteDnsInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.siteName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
    
}

/**
 * 查找ftp信息
 */
function findSiteFtpInfo(){
    var sourceMOID = $("#ipt_webSiteMoID").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findSiteFtpnfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.siteName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
    
}

/**
 * 查找HTTP信息
 */
function findSiteHttpInfo(){
    var sourceMOID = $("#ipt_webSiteMoID").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findSiteHttpnfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.siteName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查找TCP信息
 */
function findSitePortInfo() {
	var sourceMOID = $("#ipt_webSiteMoID").val();
    $("#ipt_sourceMOID").val(sourceMOID);
    var path = getRootName();
    var uri = path + "/monitor/perfTask/findSitePortInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "moID": sourceMOID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#ipt_sourceMOName").val(data.siteName);
            isShowClearBtn();
        }
    };
    ajax_(ajax_param);
}

/**
 * 查找站点信息
 * @return
 */
function findSiteInfo(){
    var siteType = $("#ipt_siteType").val();
    if (siteType == 1) {
        findSiteFtpInfo();
    } else if (siteType == 2) {
        findSiteDnsInfo();
    } else if (siteType == 3) {
        findSiteHttpInfo();
    }
}
