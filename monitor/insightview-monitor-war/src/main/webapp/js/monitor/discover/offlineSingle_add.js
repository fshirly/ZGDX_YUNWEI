$(document).ready(function(){
    $("#viewSnmp").hide();
    
    //snmp凭证加载完成，展示表格
    $('#tblSNMPCommunity').datagrid({
        onLoadSuccess: function(data){
            //console.log("加载完成。。。。。");
            $("#viewSnmp").show();
            
        }
    })
    var snmpVersion = $("#ipt_snmpVersion").val();
    var readCommunity = $("#ipt_readCommunity").val();
    var writeCommunity = $("#ipt_writeCommunity").val();
    var snmpPort = $("#ipt_snmpPort").val();
    if (snmpPort == 0) {
        $("#ipt_snmpPort").val('161');
    }
    if (readCommunity == '') {
        $("#ipt_readCommunity").val('public');
    }
    if (writeCommunity == '') {
        $("#ipt_writeCommunity").val('public');
    }
    snmpPort = $("#ipt_snmpPort").val();
    readCommunity = $("#ipt_readCommunity").val();
    writeCommunity = $("#ipt_writeCommunity").val();
    $("#ipt_readCommunity").val(readCommunity);
    $("#ipt_writeCommunity").val(writeCommunity);
    $("#ipt_snmpPort").val(snmpPort);
    
    var vmwarePort = $("#ipt_port").val();
    if (vmwarePort == 0) {
        $("#ipt_port").val('443');
    }
    
    if ($("#ipt_dbmsType").val() == "mysql") {
        $("#ipt_dbPort").val(3306);
        $("#isMysql").show();
        $("#isOracle").hide();
    }
    else if ($("#ipt_dbmsType").val() == "oracle") {
        $("#ipt_dbPort").val(1521);
        $("#isMysql").hide();
        $("#isOracle").show();
    }
    else if ($("#ipt_dbmsType").val() == "db2") {
        $("#ipt_dbPort").val(50000);
        $("#isMysql").hide();
        $("#isOracle").show();
    }
    
    var roomPort = $("#roomPort").val('6580');
    if (roomPort == 0) {
        $("#roomPort").val('6580');
    }
    
});

/*
 * 选择对象类型
 */
function choseMObjectTree(){
    var path = getRootPatch();
    var uri = path + "/monitor/addDevice/initTree";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "trmnlBrandNm": "",
            "qyType": "brandName",
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            dataTreeOrg = new dTree("dataTreeOrg", path +
            "/plugin/dTree/img/");
            dataTreeOrg.add(0, -1, "选择对象类型", "");
            
            // 得到树的json数据源
            var datas = eval('(' + data.menuLstJson + ')');
            // 遍历数据
            var gtmdlToolList = datas;
            for (var i = 0; i < gtmdlToolList.length; i++) {
                var _id = gtmdlToolList[i].classId;
                var _nameTemp = gtmdlToolList[i].classLable;
                var _parent = gtmdlToolList[i].parentClassId;
                var className = gtmdlToolList[i].className;
                //					console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
                //97-机房监控   96-空调  73-ups 90-站点 91-DNS 92-FTP 93-HTTP 94-TCP
                if (_id != 97 && _id != 96 && _id != 73 && _id != 90 && _id != 92 && _id != 92 && _id != 93 && _id != 94) {
                    dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_classID','" +
                    _id +
                    "','" +
                    className +
                    "','" +
                    _nameTemp +
                    "');");
                }
            }
            $('#dataMObjectTreeDiv').empty();
            $('#dataMObjectTreeDiv').append(dataTreeOrg + "");
            $('#divMObject').dialog('open');
        }
    }
    ajax_(ajax_param);
}

/*
 * 选择隐藏树，对应展示需填数据
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, className, showVal){
    //判断是否为叶子节点
    var path = getRootName();
    var uri = path + "/monitor/addDevice/isLeaf";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "classID": showId,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $("#" + controlId).val(showVal);
                $("#" + controlId).attr("alt", showId);
                $("#" + divId).dialog('close');
                $("#authsTitle").show();
                $("#className").val(className);
                $("#ipt_deviceIP").val("");
                $("#ipTilte").show();
                $("#ipInput").show();
                $("#objTypeTr").removeAttr("style");
                if (showId == 15 || showId == 16 || showId == 54 || showId == 81 || showId == 86) {
                    $("#snmpAuth").hide();
                    $("#viewSnmp").hide();
                    $("#dbAuth").show();
                    $("#vmwareAuth").hide();
                    $("#middleAuth").hide();
                    $("#roomAuth").hide();
                    //						$("#tblSNMPCommunityInfo").hide();
                    $("#tblAuthCommunityInfo").hide();
                    $("#tblDBMSCommunity").show();
                    $("#tblMiddlewareCommunity").hide();
                    $("#tblRoomCommunity").hide();
                    $("#authType").val("dbms");
                    if (showId == 15) {
                        $("#ipt_dbmsType").val(showVal);
                        $("#ipt_dbPort").val("1521");
                        $("#isMysql").hide();
                        $("#isOracle").show();
                    }
                    else if (showId == 16) {
                        $("#ipt_mysqlDbmsType").val(showVal);
                        $("#ipt_dbName").val("mysql");
                        $("#ipt_dbPort").val("3306");
                        $("#isMysql").show();
                        $("#isOracle").hide();
                    }
                    else if (showId == 54) {
                        $("#ipt_dbmsType").val(showVal);
                        $("#ipt_dbPort").val("50000");
                        $("#isMysql").hide();
                        $("#isOracle").show();
                    }
                    else if (showId == 81) {
                        $("#ipt_mysqlDbmsType").val(showVal);
                        $("#ipt_dbmsType").val(showVal);
                        $("#ipt_dbName").val("sybase");
                        $("#ipt_dbPort").val("5000");
                        $("#isMysql").show();
                        $("#isOracle").hide();
                    }
                    else if (showId == 86) {
                        $("#ipt_mysqlDbmsType").val(showVal);
                        $("#ipt_dbmsType").val(showVal);
                        $("#ipt_dbName").val("mssql");
                        $("#ipt_dbPort").val("1433");
                        $("#isMysql").show();
                        $("#isOracle").hide();
                    }
                    
                }
                else if (showId == 8 || showId == 75) {
                    $("#snmpAuth").hide();
                    $("#viewSnmp").hide();
                    $("#dbAuth").hide();
                    $("#vmwareAuth").show();
                    $("#middleAuth").hide();
                    $("#roomAuth").hide();
                    //						$("#tblSNMPCommunityInfo").hide();
                    $("#tblAuthCommunityInfo").show();
                    $("#tblDBMSCommunity").hide();
                    $("#tblMiddlewareCommunity").hide();
                    $("#tblRoomCommunity").hide();
                    $("#authType").val("Vmware");
                    $("#ipt_port").val('443');
                }
                else if (showId == 19 || showId == 20 || showId == 53) {
                    $("#snmpAuth").hide();
                    $("#viewSnmp").hide();
                    $("#dbAuth").hide();
                    $("#vmwareAuth").hide();
                    $("#middleAuth").show();
                    $("#roomAuth").hide();
                    //						$("#tblSNMPCommunityInfo").hide();
                    $("#tblAuthCommunityInfo").hide();
                    $("#tblDBMSCommunity").hide();
                    $("#tblMiddlewareCommunity").show();
                    $("#tblRoomCommunity").hide();
                    $("#authType").val("middle");
                    if (showId == 19) {
                        $("#isShowUserAndPwd").hide();
                        //							$("#middlePortTr").css({'float':'left'});
                        $("#ipt_middlePort").val("8880");
                    }
                    else if (showId == 20) {
                        $("#isShowUserAndPwd").hide();
                        //							$("#middlePortTr").css({'float':'left'});
                        $("#ipt_middlePort").val("8999");
                    }
                    else if (showId == 53) {
                        $("#isShowUserAndPwd").show();
                        //							$("#middlePortTr").removeAttr("style");
                        $("#ipt_middlePort").val("7001");
                    }
                    $("#ipt_url").val("");
                }
                else if (showId == 44) {
                    $("#snmpAuth").hide();
                    $("#viewSnmp").hide();
                    $("#dbAuth").hide();
                    $("#vmwareAuth").hide();
                    $("#middleAuth").hide();
                    $("#roomAuth").show();
                    //						$("#tblSNMPCommunityInfo").hide();
                    $("#tblAuthCommunityInfo").hide();
                    $("#tblDBMSCommunity").hide();
                    $("#tblMiddlewareCommunity").hide();
                    $("#tblRoomCommunity").show();
                    $("#authType").val("room");
                    $("#ipt_roomPort").val('6580');
                }
                else {
                    $("#snmpAuth").show();
                    $("#viewSnmp").hide();
                    $("#dbAuth").hide();
                    $("#vmwareAuth").hide();
                    $("#middleAuth").hide();
                    $("#roomAuth").hide();
                    //						$("#tblSNMPCommunityInfo").show();
                    $("#tblAuthCommunityInfo").hide();
                    $("#tblDBMSCommunity").hide();
                    $("#tblMiddlewareCommunity").hide();
                    $("#tblRoomCommunity").hide();
                    $("#tblSiteHttp").hide();
                    $("#tblSiteDNS").hide();
                    $("#tblSiteFTP").hide();
                    $("#authType").val("SNMP");
                    var snmpPort = $("#ipt_snmpPort").val();
                    var readCommunity = $("#ipt_readCommunity").val();
                    var writeCommunity = $("#ipt_writeCommunity").val();
                    if (snmpPort == 0) {
                        $("#ipt_snmpPort").val('161');
                    }
                    if (readCommunity == '') {
                        $("#ipt_readCommunity").val('public');
                    }
                    if (writeCommunity == '') {
                        $("#ipt_writeCommunity").val('public');
                    }
                    initSnmpTable();
                    $("#viewSnmp").show();
                }
                $("#singleCollectorDiv").show();
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 加载设备信息列表页面
 *
 * @return
 */
function loadDeviceInfo(){
    var path = getRootName();
    var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose";
    window.open(uri, "", "height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
    
}


/*
 * SNMP凭证中，根据协议版本选择展示表单
 */
function isOrnoCheck(){
    if ($("#ipt_snmpVersion").val() == "0" ||
    $("#ipt_snmpVersion").val() == "1") {
        $("#usmUser").hide();
        $("#authAlogrithm").hide();
        $("#encryptionAlogrithm").hide();
        $("#readCommunity").show();
    }
    else {
        $("#readCommunity").hide();
        $("#usmUser").show();
        $("#authAlogrithm").show();
        $("#encryptionAlogrithm").show();
    }
}

/*
 * 数据库凭证，展示默认的端口号
 */
function showDefaultPort(){
    if ($("#ipt_dbmsType").val() == "mysql") {
        $("#ipt_dbPort").val(3306);
    }
    else if ($("#ipt_dbmsType").val() == "oracle") {
        $("#ipt_dbPort").val(1521);
    }
}


/* 
 * SNMP验证：验证表单信息
 *
 */
function checkSnmpForm(){
    var checkControlAttr = new Array('ipt_deviceIP', 'ipt_readCommunity', 'ipt_writeCommunity');
    var checkMessagerAttr = new Array('请先选择设备！', '请输入读Community！', '请输入写Community！');
    return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

function checkForm(){
    var checkControlAttr = new Array('ipt_deviceIP', 'ipt_readCommunity', 'ipt_writeCommunity');
    var checkMessagerAttr = new Array('请先选择设备！', '请输入读Community！', '请输入写Community！');
    return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

/**
 * 单对象发现
 */
function toAddSingle(){
    var authType = $("#authType").val();
    var result = checkInfo("#tblAddDevice");
    if (result == true) {
		var collectorid = $("#ipt_collectorid4").attr("alt");
        //判断认证信息类别
        if (authType == "SNMP") {
            addSnmp(collectorid);
        }
        else if (authType == "Vmware") {
            addVmware(collectorid);
        }
        else if (authType == "middle") {
            addMiddleCommunity(collectorid);
        }
        else if (authType == "room") {
            addRoomCommunity(collectorid);
        }
        else {
            addDBMSCommunity(collectorid);
        }
    }
}

/*
 * 验证SNMP验证中该设备是否存在
 */
function checkSnmpCommunity(){
    var readCommunity = $("#ipt_readCommunity").val();
    var snmpVersion = $("#ipt_snmpVersion").val();
    var path = getRootName();
    var uri = path + "/platform/snmpCommunity/checkAddSnmp";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "readCommunity": readCommunity,
            "snmpVersion": snmpVersion,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (false == data || "false" == data) {
                $.messager.alert("提示", "该凭证已存在！", "error");
            }
            else {
                doAddSnmp();
                return;
            }
        }
    };
    ajax_(ajax_param);
}

/*
 * 验证Vmware验证中该设备是否存在
 */
function checkAddAuthCommunity(flag){
    var result = checkInfo("#tblAuthCommunityInfo");
    var deviceIP = $("#ipt_deviceIP").val();
    var path = getRootName();
    var uri = path + "/platform/sysVMIfCommunity/checkAddSyaAuthCommunity";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "deviceIP": deviceIP,
            "flag": flag,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $.messager.alert("提示", "该设备IP在Vmware验证中已存在！", "error");
            }
            else {
                addVmware();
                return;
            }
        }
    };
    if (result == true) {
        ajax_(ajax_param);
    }
}

/*
 * 验证数据库验证中该设备是否存在
 */
function checkAddDBMSCommunity(){
    var result = checkInfo("#tblDBMSCommunity");
    var deviceIP = $("#ipt_deviceIP").val();
    var flag = 3;
    if (checkIP(flag)) {
        var classID = $("#ipt_classID").attr("alt");
        if (classID == 16) {
            var dbmsType = $("#ipt_mysqlDbmsType").val();
            var dbName = "mysql";
        }
        else {
            var dbmsType = $("#ipt_dbmsType").val();
            var dbName = $("#ipt_dbName").val();
        }
        //		console.log("dbmsType=="+dbmsType+",dbName=="+dbName);
        var path = getRootName();
        var uri = path + "/monitor/addDevice/checkAddDBMSCommunity";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "ip": deviceIP,
                "dbName": dbName,
                "dbmsType": dbmsType,
                "userName": $("#ipt_dbUserName").val(),
                "password": $("#ipt_dbPassword").val(),
                "port": $("#ipt_dbPort").val(),
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (false == data || "false" == data) {
                    $.messager.alert("提示", "该设备IP在数据库验证中已存在！", "error");
                }
                else {
                    addDBMSCommunity();
                    return;
                }
            }
        };
        if (result == true) {
            ajax_(ajax_param);
        }
    }
}

/**
 * 验证中间件凭证是否存在
 * @return
 */
function checkAddMiddleCommunity(){
    var result = checkInfo("#tblMiddlewareCommunity");
    var deviceIP = $("#ipt_deviceIP").val();
    var middleWareName = 3;
    var classID = $("#ipt_classID").attr("alt");
    if (classID == 19) {
        middleWareName = 3;
    }
    else if (classID == 20) {
        middleWareName = 2;
    }
    else if (classID == 53) {
        middleWareName = 1;
    }
    var flag = 4;
    if (result == true) {
        if (checkIP(flag)) {
            var path = getRootName();
            var uri = path + "/monitor/MiddleWareCommunity/checkCommunity?flag=add";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "ipAddress": deviceIP,
                    "middleWareName": middleWareName,
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (false == data || "false" == data) {
                        $.messager.alert("提示", "该IP在中间件凭证中已存在！", "error");
                    }
                    else {
                        addMiddleCommunity();
                        return;
                    }
                }
            };
            ajax_(ajax_param);
        }
    }
}

/*
 * 验证IP格式
 */
function checkIP(flag){
    var deviceIP = $("#ipt_deviceIP").val();
    if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/.test(deviceIP))) {
        $.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e){
            $("#ipt_deviceIP").focus();
        });
        return false;
    }
    var port = null;
    var message = "";
    if (flag == 1) {
        port = $("#ipt_snmpPort").val();
        message = "SNMP端口只能输入正整数！";
    }
    else if (flag == 2) {
        port = $("#ipt_port").val();
        message = "VMware登录端口只能输入正整数！";
    }
    else if (flag == 3) {
        port = $("#ipt_dbPort").val();
        message = "数据库凭证端口只能输入正整数！";
    }
    else if (flag == 4) {
        port = $("#ipt_middlePort").val();
        message = "中间件凭证端口只能输入正整数！";
    }
    else if (flag == 5) {
        port = $("#ipt_roomPort").val();
        message = "机房环境监控凭证端口只能输入正整数！";
    }
    if (!(/^[0-9]*[1-9][0-9]*$/.test(port)) && port != "") {
        $.messager.alert("提示", message, "info", function(e){
            if (flag == 1) {
                $("#ipt_snmpPort").focus();
            }
            else if (flag == 2) {
                $("#ipt_port").focus();
            }
            else if (flag == 3) {
                $("#ipt_dbPort").focus();
            }
            else if (flag == 4) {
                $("#ipt_middlePort").focus();
            }
            else if (flag == 5) {
                $("#ipt_roomPort").focus();
            }
            
        });
        return false;
    }
    return true;
}

function checkPort(flag){
    var port = null;
    var message = "";
    if (flag == 1) {
        port = $("#ipt_snmpPort").val();
        message = "SNMP端口只能输入正整数！";
    }
    else if (flag == 2) {
        port = $("#ipt_port").val();
        message = "VMware登录端口只能输入正整数！";
    }
    else if (flag == 3) {
        port = $("#ipt_dbPort").val();
        message = "数据库凭证端口只能输入正整数！";
    }
    else if (flag == 4) {
        port = $("#ipt_middlePort").val();
        message = "中间件凭证端口只能输入正整数！";
    }
    else if (flag == 5) {
        port = $("#ipt_roomPort").val();
        message = "机房环境监控凭证端口只能输入正整数！";
    }
    else if (flag == 6) {
        port = $("#ipt_roomPort").val();
        message = "FTP端口号只能输入正整数！";
    }
    if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
        $.messager.alert("提示", message, "info", function(e){
            if (flag == 1) {
                $("#ipt_snmpPort").focus();
            }
            else if (flag == 2) {
                $("#ipt_port").focus();
            }
            else if (flag == 3) {
                $("#ipt_dbPort").focus();
            }
            else if (flag == 4) {
                $("#ipt_middlePort").focus();
            }
            else if (flag == 5) {
                $("#ipt_roomPort").focus();
            }
            else if (flag == 6) {
                $("#ipt_ftpPort").focus();
            }
            
        });
        return false;
    }
    return true;
}

function checkKey(){
    var authKey = $("#ipt_authKey").val();
    var encryptionAlogrithm = $("#ipt_encryptionAlogrithm").val();
    if (authKey != "" && authKey != null && encryptionAlogrithm != "-1" && encryptionAlogrithm != -1) {
        return true;
    }
    else {
        if (authKey == "" || authKey == null) {
            $.messager.alert('提示', '请输入认证KEY!', 'info');
        }
        if (encryptionAlogrithm == "-1" || encryptionAlogrithm == -1) {
            $.messager.alert('提示', '请选择加密算法!', 'info');
        }
        return false;
    }
}

/*			
 * 新增（SNMP认证）
 */
function addSnmp(collectorid){
    var result = checkInfo("#singleCollectorDiv");
    if (result) {
        var deviceIP = $("#ipt_deviceIP").val();
        var path = getRootName();
        var classID = $("#ipt_classID").attr("alt");
        var moClassNames = $("#className").val();
        var uri = path + "/monitor/addDevice/addSnmpCommunity?moClassNames=" + moClassNames + "&deviceIP=" + deviceIP + "&isOffline=1&collectorid="+collectorid;
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "离线发现任务保存成功!", "info");
					reset();
                }
                else {
                    $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                }
                
            }
        };
        ajax_(ajax_param);
    }
}

/*
 * 新增（Vmware认证）
 */
function addVmware(collectorid){
    var flag = 2;
    var isExsitVmware = $("#isExsitVmware").val();
    var result = checkInfo("#tblAuthCommunityInfo") && checkInfo("#singleCollectorDiv");
    if (checkIP(flag)) {
        var moClassNames = $("#className").val();
        var path = getRootName();
        var uri = path + "/monitor/addDevice/addVmware?moClassNames=" + moClassNames + "&isExsitVmware=" + isExsitVmware + "&isOffline=1&collectorid="+collectorid;
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "deviceIP": $("#ipt_deviceIP").val(),
                "authType": 3,
                "port": $("#ipt_port").val(),
                "moID": $("#ipt_deviceId").val(),
                "userName": $("#ipt_userName").val(),
                "password": $("#ipt_password").val(),
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "离线发现任务保存成功!", "info");
					reset();
                }
                else {
                    $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                }
                
            }
        };
        if (result == true) {
            ajax_(ajax_param);
        }
    }
}

/*
 * 新增（数据库认证）
 */
function addDBMSCommunity(collectorid){
    var result = checkInfo("#tblDBMSCommunity") && checkInfo("#singleCollectorDiv");
    var db2CommunityId = $("#db2CommunityId").val();
    if (db2CommunityId == "" || db2CommunityId == null) {
        db2CommunityId = -1;
    }
    var flag = 3;
    if (checkIP(flag)) {
        var classID = $("#ipt_classID").attr("alt");
        if (classID == 16) {
            var dbmsType = $("#ipt_mysqlDbmsType").val();
            var dbName = "mysql";
        }
        else if (classID == 81) {
            var dbmsType = $("#ipt_mysqlDbmsType").val();
            var dbName = "sybase";
        }
        else if (classID == 86) {
            var dbmsType = $("#ipt_mysqlDbmsType").val();
            var dbName = "msSql";
        }
        else {
            var dbmsType = $("#ipt_dbmsType").val();
            var dbName = $("#ipt_dbName").val();
        }
        //		console.log("dbmsType=="+dbmsType+",dbName=="+dbName);
        var path = getRootName();
        var moClassNames = $("#className").val();
        var isExistDB = $("#isExistDB").val();
        var uri = path + "/monitor/addDevice/addDBMSCommunity?moClassNames=" + moClassNames + "&isExistDB=" + isExistDB + "&db2CommunityId=" + db2CommunityId + "&isOffline=1&collectorid="+collectorid;
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "id": db2CommunityId,
                "ip": $("#ipt_deviceIP").val(),
                "dbName": dbName,
                "dbmsType": dbmsType,
                "userName": $("#ipt_dbUserName").val(),
                "password": $("#ipt_dbPassword").val(),
                "port": $("#ipt_dbPort").val(),
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "离线发现任务保存成功!", "info");
					reset();
                }
                else {
                    $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                }
                
            }
        };
        if (result == true) {
            ajax_(ajax_param);
        }
    }
}

/**
 * 新增（JMX凭证）
 * @return
 */
function addMiddleCommunity(collectorid){
    $("#btnSave").attr('disabled', true);
    var result = checkInfo("#tblMiddlewareCommunity") && checkInfo("#singleCollectorDiv");
    var userName = "";
    var passWord = "";
    var middleWareName = 3;
    var middleWareType = "websphere";
    var classID = $("#ipt_classID").attr("alt");
    if (classID == 19) {
        middleWareName = 3;
        middleWareType = "websphere";
    }
    else if (classID == 20) {
        middleWareName = 2;
        middleWareType = "tomcat";
    }
    else if (classID == 53) {
        middleWareName = 1;
        middleWareType = "weblogic";
        userName = $("#ipt_middleUserName").val();
        passWord = $("#ipt_middlePassWord").val();
    }
    var flag = 4;
    if (result == true) {
        if (checkIP(flag)) {
            var path = getRootName();
            var moClassNames = $("#className").val();
            var isExistMiddle = $("#isExistMiddle").val();
            var uri = path + "/monitor/addDevice/addMiddleCommunity?moClassNames=" + moClassNames + "&isExistMiddle=" + isExistMiddle + "&isOffline=1&collectorid="+collectorid;
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "ipAddress": $("#ipt_deviceIP").val(),
                    "middleWareName": middleWareName,
                    "middleWareType": middleWareType,
                    "port": $("#ipt_middlePort").val(),
                    "userName": userName,
                    "passWord": passWord,
                    "url": $("#ipt_url").val(),
                    "t": Math.random()
                },
                error: function(){
                    $("#btnSave").removeAttr("disabled");
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        $.messager.alert("提示", "离线发现任务保存成功!", "info");
						reset();
                    }
                    else {
                        $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                    }
                    
                }
            };
            ajax_(ajax_param);
        }
    }
}

/*
 * 填写对象IP前先选择对象类型
 */
function isClass(){
    var classID = $("#ipt_classID").attr("alt");
    if (classID == null || classID == "") {
        $.messager.alert("提示", "请先选择对象类型！", "error");
    }
}

/*
 * 对象类型为数据库时，验证对象IP是已经发现的
 */
function isDiscovered(){
    var deviceIP = $("#ipt_deviceIP").val();
    if (deviceIP != null && deviceIP != "") {
        if (checkDeviceIP(deviceIP)) {
            var classID = $("#ipt_classID").attr("alt");
            if (classID == 15 || classID == 16 || classID == 54 || classID == 81 || classID == 86 || classID == 19 || classID == 20 || classID == 53 || classID == 44) {
                if (deviceIP != null && deviceIP != "") {
                    var path = getRootName();
                    var uri = path + "/monitor/addDevice/isDiscovered";
                    var ajax_param = {
                        url: uri,
                        type: "post",
                        datdType: "json",
                        data: {
                            "deviceip": $("#ipt_deviceIP").val(),
                            "t": Math.random()
                        },
                        error: function(){
                            $.messager.alert("错误", "ajax_error", "error");
                        },
                        success: function(data){
                            if (false == data || "false" == data) {
                                $.messager.alert("提示", "该对象IP还未发现,请重新填写！", "error");
                                $('#ipt_deviceIP').val("");
                                $('#ipt_deviceIP').focus();
                            }
                            else {
                                innitCommunity(classID);
                            }
                        }
                    };
                    ajax_(ajax_param);
                }
            }
            else if (classID == 8) {
                var path = getRootName();
                var uri = path + "/monitor/addDevice/isVCenter";
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    data: {
                        "deviceip": $("#ipt_deviceIP").val(),
                        "t": Math.random()
                    },
                    error: function(){
                        $.messager.alert("错误", "ajax_error", "error");
                    },
                    success: function(data){
                        if (false == data || "false" == data) {
                            $.messager.alert("提示", "该对象IP的vCenter设备已经发现,请重新填写！", "error");
                            $('#ipt_deviceIP').val("");
                            $('#ipt_deviceIP').focus();
                        }
                        else {
                            initVmwareCommunity();
                        }
                    }
                };
                ajax_(ajax_param);
            }
            else if (classID == 75) {
                initVmwareCommunity();
            }
            else {
                var deviceIP = $('#ipt_deviceIP').val();
                if (deviceIP != "") {
                    initSnmpTable();
                    $("#viewSnmp").show();
                }
            }
        }
    }
    
}

function checkDeviceIP(deviceIP){
    if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/.test(deviceIP))) {
        $.messager.alert("提示", "对象IP格式错误，请填写正确的对象IP！", "info", function(e){
            $("#ipt_deviceIP").val("");
            $("#ipt_deviceIP").focus();
        });
        return false;
    }
    return true;
}

function innitCommunity(classID){
    if (classID == 15 || classID == 16 || classID == 81 || classID == 86) {
        initDBCommunity(classID);
    }
    else if (classID == 44) {
        initRoomCommunity();
    }
    else if (classID == 19 || classID == 20 || classID == 53) {
        initMiddleCommunity();
    }
}



/**
 * 初始化VMware
 * @return
 */
function initVmwareCommunity(){
    var deviceIP = $("#ipt_deviceIP").val();
    var path = getRootName();
    var uri = path + "/monitor/addDevice/initVmwareCommunity";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "deviceIP": deviceIP,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#isExsitVmware").val(data.isExsitVmware);
            if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
                //				iterObj(data.vmwareCommunity,"ipt");
                var vmwareCommunity = data.vmwareCommunity;
                $("#ipt_deviceIP").val(deviceIP);
                $("#ipt_port").val(vmwareCommunity.port);
                $("#ipt_deviceId").val(vmwareCommunity.moID);
                $("#ipt_userName").val(vmwareCommunity.userName);
                $("#ipt_password").val(vmwareCommunity.password);
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 初始化数据库
 * @return
 */
function initDBCommunity(classID){
    var deviceIP = $("#ipt_deviceIP").val();
    if (classID == 16 || classID == 81 || classID == 86) {
        var dbmsType = $("#ipt_mysqlDbmsType").val();
    }
    else {
        var dbmsType = $("#ipt_dbmsType").val();
    }
    var path = getRootName();
    var uri = path + "/monitor/addDevice/initDBCommunity";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "ip": deviceIP,
            "dbName": $("#ipt_dbName").val(),
            "dbmsType": dbmsType,
            "userName": $("#ipt_dbUserName").val(),
            "password": $("#ipt_dbPassword").val(),
            "port": $("#ipt_dbPort").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#isExistDB").val(data.isExistDB);
            var dbCommunity = data.dbCommunity;
            var docs = document.getElementById('ipt_dbPort');
            if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
                $("#ipt_deviceIP").val(deviceIP);
                $("#ipt_dbName").val(dbCommunity.dbName);
                $("#ipt_dbmsType").val(dbCommunity.dbmsType);
                $("#ipt_mysqlDbmsType").val(dbCommunity.dbmsType);
                $("#ipt_dbUserName").val(dbCommunity.userName);
                $("#ipt_dbPassword").val(dbCommunity.password);
                $("#ipt_dbPort").val(dbCommunity.port);
                //docs.readOnly = true;
            }
            else {
                //docs.readOnly = false;
            }
        }
    };
    ajax_(ajax_param);
}

function initDB2Community(){
    var deviceIP = $("#ipt_deviceIP").val();
    if (deviceIP != null && deviceIP != "") {
        var dbmsType = $("#ipt_dbmsType").val();
        var dbName = $("#ipt_dbName").val();
        //		console.log("dbName="+$("#ipt_dbName").val());
        var path = getRootName();
        var uri = path + "/monitor/addDevice/initDB2Community?dbName=" + dbName;
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "ip": deviceIP,
                "dbmsType": dbmsType,
                "userName": $("#ipt_dbUserName").val(),
                "password": $("#ipt_dbPassword").val(),
                "port": $("#ipt_dbPort").val(),
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                $("#isExistDB").val(data.isExistDB);
                var dbCommunity = data.dbCommunity;
                var docs = document.getElementById('ipt_dbPort');
                if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
                    $("#ipt_deviceIP").val(deviceIP);
                    $("#ipt_dbName").val(dbCommunity.dbName);
                    $("#ipt_dbmsType").val(dbCommunity.dbmsType);
                    $("#ipt_mysqlDbmsType").val(dbCommunity.dbmsType);
                    $("#ipt_dbUserName").val(dbCommunity.userName);
                    $("#ipt_dbPassword").val(dbCommunity.password);
                    $("#ipt_dbPort").val(dbCommunity.port);
                    $("#db2CommunityId").val(dbCommunity.id);
                    //docs.readOnly = true;
                }
                else {
                    //docs.readOnly = false;
                }
            }
        };
        ajax_(ajax_param);
    }
}

/**
 * 初始化中间件
 * @return
 */
function initMiddleCommunity(){
    var deviceIP = $("#ipt_deviceIP").val();
    var middleWareType = "websphere";
    var classID = $("#ipt_classID").attr("alt");
    if (classID == 19) {
        middleWareType = "websphere";
    }
    else if (classID == 20) {
        middleWareType = "tomcat";
    }
    else if (classID == 53) {
        middleWareType = "weblogic";
    }
    var path = getRootName();
    var uri = path + "/monitor/addDevice/initMiddleCommunity";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "ipAddress": deviceIP,
            "middleWareType": middleWareType,
            "port": $("#ipt_middlePort").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#isExistMiddle").val(data.isExistMiddle);
            var middleCommunity = data.middleCommunity;
            var docs = document.getElementById('ipt_middlePort');
            
            if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
                $("#ipt_deviceIP").val(deviceIP);
                $("#ipt_middleWareName").val(middleCommunity.middleWareName);
                $("#ipt_middleWareType").val(middleCommunity.middleWareType);
                $("#ipt_middlePort").val(middleCommunity.port);
                $("#ipt_middleUserName").val(middleCommunity.userName);
                $("#ipt_middlePassWord").val(middleCommunity.passWord);
                $("#ipt_url").val(middleCommunity.url);
                //docs.readOnly = true;
            }
            else {
                //docs.readOnly = false;
                if (classID == 19) {
                    $("#ipt_url").val("http://" + deviceIP + ":" + "9060" + "/ibm/console/unsecureLogon.jsp");
                }
                else if (classID == 20) {
                    $("#ipt_url").val("http://" + deviceIP + ":" + "8080");
                }
                else if (classID == 53) {
                    $("#ipt_url").val("http://" + deviceIP + ":" + "7001" + "/console/login/LoginForm.jsp");
                }
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 初始化机房监控
 * @return
 */
function initRoomCommunity(){
    var deviceIP = $("#ipt_deviceIP").val();
    var classID = $("#ipt_classID").attr("alt");
    var path = getRootName();
    var uri = path + "/monitor/addDevice/initRoomCommunity";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "ipAddress": deviceIP,
            "port": $("#ipt_roomPort").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#isExistRoom").val(data.isExistRoom);
            var roomCommunity = data.roomCommunity;
            var docs = document.getElementById('ipt_roomPort');
            if (true == data.isExistCommunity || "true" == data.isExistCommunity) {
                $("#ipt_deviceIP").val(deviceIP);
                $("#ipt_roomPort").val(roomCommunity.port);
                $("#ipt_roomUserName").val(roomCommunity.userName);
                $("#ipt_roomPassWord").val(roomCommunity.passWord);
                //docs.readOnly = true;
            }
            else {
                //docs.readOnly = false;
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 新增（机房监控环境凭证）
 * @return
 */
function addRoomCommunity(collectorid){
    var result = checkInfo("#tblRoomCommunity") && checkInfo("#singleCollectorDiv");
    var flag = 5;
    if (result == true) {
        if (checkIP(flag)) {
            var path = getRootName();
            var moClassNames = $("#className").val();
            var isExistRoom = $("#isExistRoom").val();
            var uri = path + "/monitor/addDevice/addRoomCommunity?moClassNames=" + moClassNames + "&isExistRoom=" + isExistRoom + "&isOffline=1&collectorid="+collectorid;
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "ipAddress": $("#ipt_deviceIP").val(),
                    "port": $("#ipt_roomPort").val(),
                    "userName": $("#ipt_roomUserName").val(),
                    "passWord": $("#ipt_roomPassWord").val(),
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        $.messager.alert("提示", "离线发现任务保存成功!", "info");
						reset();
                    }
                    else {
                        $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                    }
                    
                }
            };
            ajax_(ajax_param);
        }
    }
}

/**
 * 重置IP
 * @return
 */
function resetIP(){
    var classID = $("#ipt_classID").attr("alt");
    if (classID == 15 || classID == 16 || classID == 81 || classID == 86 || classID == 19 || classID == 20 || classID == 53 || classID == 44) {
        innitCommunity(classID);
    }
    else if (classID == 54) {
        initDB2Community();
    }
    else if (classID == 8 || classID == 75) {
        initVmwareCommunity();
    }
    else {
        var deviceIP = $('#ipt_deviceIP').val();
        if (deviceIP != "") {
            initSnmpTable();
            $("#viewSnmp").show();
        }
    }
}

/**
 * 获得snmp凭证
 * @return
 */
function initSnmpTable(){
    var deviceIp = $('#ipt_deviceIP').val();
    var path = getRootName();
    $('#tblSNMPCommunity').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 780,
        height: 260,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        //						fit : true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/addDevice/listSnmpCommunity?deviceIp=' + deviceIp,
        remoteSort: false,
        idField: 'fldId',
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                toAddSnmp();
            }
        }],
        columns: [[        //								{
        //									field : 'id',
        //									checkbox : true
        //								},
        {
            field: 'readCommunity',
            title: '读团体名',
            width: 350,
            align: 'center'
        }, {
            field: 'writeCommunity',
            title: '写团体名',
            width: 350,
            align: 'center'
        }, {
            field: 'alias',
            title: '别名',
            width: 280,
            align: 'center',
            sortable: true
        }, {
            field: 'snmpVersion',
            title: '协议版本',
            width: 350,
            align: 'center',
            formatter: function(value, row, index){
                if (value == 0) {
                    return 'V1';
                }
                else if (value == 1) {
                    return 'V2';
                }
                else if (value == 3) {
                    return 'V3';
                }
            }
        }, {
            field: 'snmpPort',
            title: '端口',
            width: 350,
            align: 'center'
        }]]
    });
    // 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
    $(window).resize(function(){
        $('#tblSNMPCommunity').resizeDataGrid(0, 0, 0, 0);
    });
}

function reloadSnmpTable(){
    reloadTableCommon_1('tblSNMPCommunity');
}

function reloadTableCommon_1(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开新增snmp
 * @return
 */
function toAddSnmp(){
    resetForm('tblSNMPCommunityInfo');
    $("#usmUser").hide();
    $("#authAlogrithm").hide();
    $("#encryptionAlogrithm").hide();
    $("#readCommunity").show();
    
    $("#btnAddSnmpNow").unbind();
    $("#btnAddSnmpNow").bind("click", function(){
        checkSnmp();
    });
    $("#btnColseSnmpNow").unbind();
    $("#btnColseSnmpNow").bind("click", function(){
        $('#divAddSnmp').dialog('close');
    });
    $('#divAddSnmp').dialog('open');
    var snmpVersion = $("#ipt_snmpVersion").val();
    var readCommunity = $("#ipt_readCommunity").val();
    var writeCommunity = $("#ipt_writeCommunity").val();
    var snmpPort = $("#ipt_snmpPort").val();
    if (snmpPort == 0) {
        $("#ipt_snmpPort").val('161');
    }
    if (readCommunity == '') {
        $("#ipt_readCommunity").val('public');
    }
    if (writeCommunity == '') {
        $("#ipt_writeCommunity").val('public');
    }
    snmpPort = $("#ipt_snmpPort").val();
    readCommunity = $("#ipt_readCommunity").val();
    writeCommunity = $("#ipt_writeCommunity").val();
    $("#ipt_readCommunity").val(readCommunity);
    $("#ipt_writeCommunity").val(writeCommunity);
    $("#ipt_snmpPort").val(snmpPort);
    
}

/**
 * 校验是否存在
 * @return
 */
function checkSnmp(){
    var flag = 1;
    var snmpVersion = $("#ipt_snmpVersion").val();
    //	console.log("snmpVersion=="+snmpVersion);
    var checkFormRS = true;
    var checkKeyRS = true;
    if (snmpVersion == 0 || snmpVersion == 1) {
        //		console.log("readCommunity=="+checkInfo('#readCommunity'));
        //		console.log("snmpPort=="+checkInfo('#snmpPort'));
        checkFormRS = checkInfo('#readCommunity') && checkInfo('#snmpPort');
    }
    else if (snmpVersion == 3) {
        checkFormRS = checkInfo('#usmUser') && checkInfo('#snmpPort');
        var authAlogrithm = $("#ipt_authAlogrithm").val();
        //		console.log("authAlogrithm == "+authAlogrithm);
        if (authAlogrithm != -1) {
            checkKeyRS = checkKey();
        }
    }
    
    if (checkFormRS == true) {
        if (checkKeyRS == true) {
            if (snmpVersion == 0 || snmpVersion == 1) {
                checkSnmpCommunity();
            }
            else {
                doAddSnmp();
            }
            
        }
    }
}

/**
 * 新增凭证
 * @return
 */
function doAddSnmp(){
    if (checkIP(1)) {
        var path = getRootName();
        var classID = $("#ipt_classID").attr("alt");
        var moClassNames = $("#className").val();
        var uri = path + "/platform/snmpCommunity/addSnmpCommunity"
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "alias": $("#ipt_alias").val(),
                "readCommunity": $("#ipt_readCommunity").val(),
                "writeCommunity": $("#ipt_writeCommunity").val(),
                "snmpPort": $("#ipt_snmpPort").val(),
                "snmpVersion": $("#ipt_snmpVersion").val(),
                "usmUser": $("#ipt_usmUser").val(),
                "contexName": $("#ipt_contexName").val(),
                "authAlogrithm": $("#ipt_authAlogrithm").val(),
                "authKey": $("#ipt_authKey").val(),
                "encryptionAlogrithm": $("#ipt_encryptionAlogrithm").val(),
                "encryptionKey": $("#ipt_encryptionKey").val(),
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "添加凭证成功！", "info");
                    reloadSnmpTable()
                    $('#divAddSnmp').dialog('close');
                }
                else {
                    $.messager.alert("提示", "添加凭证失败！", "error");
                }
                
            }
        };
        ajax_(ajax_param);
    }
}
