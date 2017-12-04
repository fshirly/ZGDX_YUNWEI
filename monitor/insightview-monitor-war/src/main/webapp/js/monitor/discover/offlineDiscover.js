/**
 * 选择离线采集机
 */
function choseOfflineCollector(index){
    var path = getRootPatch();
    var uri = path + "/monitor/offlineDiscover/findHostLst";
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
            //				console.log(data)
            dataTreeOrg = new dTree("dataTreeOrg", path +
            "/plugin/dTree/img/");
            dataTreeOrg.add(0, -1, "选择离线采集机", "");
            
            // 得到树的json数据源
            var datas = eval('(' + data.hostListJson + ')');
            // 遍历数据
            var gtmdlToolList = datas;
            for (var i = 0; i < gtmdlToolList.length; i++) {
                var _id = gtmdlToolList[i].serverid;
                var _nameTemp = gtmdlToolList[i].ipaddress;
                var _parent = gtmdlToolList[i].parentId;
                var collectorId = "ipt_collectorid" + index;
                dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenHostTreeSetValEasyUi('divHost','" + collectorId + "','" +
                _id +
                "','" +
                _nameTemp +
                "');");
            }
            $('#dataHostTreeDiv').empty();
            $('#dataHostTreeDiv').append(dataTreeOrg + "");
            $('#divHost').dialog('open');
        }
    }
    ajax_(ajax_param);
}

function hiddenHostTreeSetValEasyUi(divId, controlId, showId, showVal){
    $("#" + controlId).val(showVal);
    $("#" + controlId).attr("alt", showId);
    $("#" + divId).dialog('close');
}

function isNull(arg1){
    return !arg1 && arg1 !== 0 && typeof arg1 !== "boolean" ? true : false;
}

/**
 * 重置
 */
function reset(){
    var way = $("input[name='selectDiscover']:checked").val();
    if (way == 1) {
        $("#startIP1").val("");
        $("#endIP1").val("");
        $("#ipt_collectorid1").val("");
        $("#ipt_collectorid1").attr("alt", -1);
    }
    else if (way == 2) {
        $("#startIP2").val("");
        $("#ipt_collectorid2").val("");
        $("#ipt_collectorid2").attr("alt", -1);
    }
    else if (way == 3) {
        $("#startIP3").val("");
        $("#step3").val("");
        $("#ipt_collectorid3").val("");
        $("#ipt_collectorid3").attr("alt", -1);
    }
    else if (way == 4) {
        $("#ipt_classID").val("");
        $("#ipt_classID").attr("alt", -1);
        $("#ipt_deviceIP").val("");
        $("#authTr").hide();
        $("#viewSnmp").hide();
        $("#tblAuthCommunityInfo").hide();
        $("#tblDBMSCommunity").hide();
        $("#tblMiddlewareCommunity").hide();
        $("#tblRoomCommunity").hide();
        $("#ipt_collectorid4").val("");
        $("#ipt_collectorid4").attr("alt", -1);
        $("#singleCollectorDiv").hide();
    }
}

/**
 * 新增离线任务
 */
function toAddOfflineDiscover(){
    var way = $("input[name='selectDiscover']:checked").val();
    if (way != 4) {
        saveInfo(way);
    }
    //单对象发现
    else {
        toAddSingle();
    }
}

/**
 * 起始ip发现、子网发现、路由发现
 */
function saveInfo(way){
    var collectorid = $("#ipt_collectorid1").attr("alt");
    if (way == 1) {
    	var result = checkInfo("#byIPScpoe");
        var startIP1 = $("#startIP1").val();
        var endIP1 = $("#endIP1").val();
        if (judge_ip(startIP1) > judge_ip(endIP1)) {
            $.messager.alert("提示", "请输入有效的IP地址范围!", "error");
            $("#endIP1").focus();
            return;
        }
    }
    
    if (way == 2) {
		var result = checkInfo("#byStartIP");
        collectorid = $("#ipt_collectorid2").attr("alt");
        var startIP2 = $("#startIP2").val();
        var netMask2 = $("#netMask2").val();
    }
    
    if (way == 3) {
		var result = checkInfo("#byRouteStep");
        collectorid = $("#ipt_collectorid3").attr("alt");
        var startIP3 = $("#startIP3").val();
        var step3 = $("#step3").val();
		if(result){
	        if (!checkNum(step3)) {
	            $.messager.alert("提示", "请输入有效的发现跳数(1~15)", "error");
	            $("#step3").val('');
	            $("#step3").focus();
	            return;
	        }
		}
    }
    var uri = getRootName() + "/monitor/offlineDiscover/saveDiscover1";
    var params = "way=" + way + "&startIP1=" + startIP1 + "&endIP1=" + endIP1 +
    "&startIP2=" +
    startIP2 +
    "&netMask2=" +
    netMask2 +
    "&startIP3=" +
    startIP3 +
    "&step3=" +
    step3 +
    "&collectorid=" +
    collectorid;
    if (result) {
        $.ajax({
            url: uri,
            type: "post",
            dateType: "json",
            data: params,
            onSubmit: function(){
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close'); // hide progress bar while the form is invalid
                }
                return isValid; // return false will stop the form submission
            },
            error: function(data){
            },
            success: function(data){
                if (data.errorInfo != null && data.errorInfo != '') {
                    $.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error");
                }
                else {
                    $.messager.alert("提示", "离线发现任务保存成功!", "info");
					reset();
                }
            }
        });
    }
}

/**
 * 选择发现类型
 * @param {Object} way
 */
function selectDiscoverWay(way){
	reset();
    if (way == 1) {
        $("#byIPScpoe").show();
        $("#byStartIP").hide();
        $("#byRouteStep").hide();
        $("#singleDiscover").hide();
    }
    else if (way == 2) {
        $("#byIPScpoe").hide();
        $("#byStartIP").show();
        $("#byRouteStep").hide();
        $("#singleDiscover").hide();
    }
    else if (way == 3) {
        $("#byIPScpoe").hide();
        $("#byStartIP").hide();
        $("#byRouteStep").show();
        $("#singleDiscover").hide();
    }
    else if (way == 4) {
        $("#byIPScpoe").hide();
        $("#byStartIP").hide();
        $("#byRouteStep").hide();
        $("#singleDiscover").show();
    }
}

/**
 * 判断是否为合法IP
 * @param strIP
 * @return
 */
function isIP(strIP){
    if (isNull(strIP)) {
        return false;
    }
    var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
    if (re.test(strIP)) {
        if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 &&
        RegExp.$4 < 256) 
            return true;
    }
    return false;
}

/**
 * 判断是否为数字
 * @param strIP
 * @return
 */
function checkNum(step){
    if (step == null || step == '') {
        return false;
    }
    var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 
    if (!re.test(step) || step > 15) {
        return false;
    }
    return true;
}

/**
 * IP地址转换为整数
 */
function judge_ip(ip){
    var num = 0;
    ip = ip.split(".");
    num = Number(ip[0]) * 256 * 256 * 256 +
    Number(ip[1]) * 256 * 256 +
    Number(ip[2]) * 256 +
    Number(ip[3]);
    num = num >>> 0;
    return num;
}
