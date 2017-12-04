//短信模板列表数据
var smsArray = [];
//邮件模板列表数据
var emailArray = [];
//电话语音模板列表数据
var phoneArray = [];
//通知用户列表数据
var userArray = [];

$(document).ready(function(){
    doInitPolicyCfg();
});

/**
 * 初始化通知策略
 */
function doInitPolicyCfg(){
    var policyId = $("#policyId").val();
    var path = getRootName();
    var uri = path + "/platform/notifyPolicyCfg/doInitPolicyCfg";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "policyId": policyId,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            var policyCfg = data.policyCfg;
            $("#lbl_policyName").text(policyCfg.policyName);
            if(policyCfg.policyType != -1){
            	$("#lbl_typeName").text(policyCfg.typeName);
            }
            else{
            	$("#lbl_typeName").text("通用");
            }
            $("#lbl_maxTimes").text(policyCfg.maxTimes);
            $("#lbl_descr").text(policyCfg.descr);
            if (policyCfg.isSms == 0) {
                $("#lbl_isSms").text("否");
            }
            else if (policyCfg.isSms == 1) {
                $("#lbl_isSms").text("是");
				smsArray = data.smsContentLst;
				doInitSmsTable();
            }
            if (policyCfg.isEmail == 0) {
                $("#lbl_isEmail").text("否");
            }
            else if (policyCfg.isEmail == 1) {
                $("#lbl_isEmail").text("是");
				emailArray = data.emailContentLst;
				doInitEmailTable();
            }
            if (policyCfg.isPhone == 0) {
                $("#lbl_isPhone").text("否");
            }
            else if (policyCfg.isPhone == 1) {
                $("#lbl_isPhone").text("是");
				phoneArray = data.phoneContentLst;
				doInitPhoneTable();
            }
            
			//初始化用户
            userArray = data.userLst;
            doInitNotifyToUsers();
        }
    };
    ajax_(ajax_param);
}

/**
 * 缓存分页
 */
function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function') { // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage: function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber: pageNum,
                pageSize: pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

/**
 * 短信通知模板
 */
function doInitSmsTable(){
    $("#tblSmsList").datagrid({
        iconCls: 'icon-edit',// 图标
        width: 580,
        height: 200,
        nowrap: false,
        striped: true,
        scrollbarSize: 0,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        onLoadSuccess: function(index, field, value){
            $("#tblSmsList").closest(".datagrid").css({
                'margin-left': '70px'
            });
        },
        columns: [[{
            field: 'name',
            title: '模板名称',
            width: 50,
            align: 'center',
        }, {
            field: 'content',
            title: '内容',
            width: 150,
            align: 'center',
            formatter: function(value, row, index){
                if (value && value.length > 30) {
                    var value2 = value.substring(0, 30) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>'
                }
                return value;
            }
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": smsArray.length,
        "rows": smsArray
    });
}

/**
 * 邮件通知模板
 */
function doInitEmailTable(){
    $("#tblEmailList").datagrid({
        iconCls: 'icon-edit',// 图标
        width: 580,
        height: 200,
        nowrap: false,
        striped: true,
        scrollbarSize: 0,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        onLoadSuccess: function(index, field, value){
            $("#tblEmailList").closest(".datagrid").css({
                'margin-left': '70px'
            });
            
        },
        columns: [[{
            field: 'name',
            title: '模板名称',
            width: 50,
            align: 'center',
        }, {
            field: 'content',
            title: '内容',
            width: 150,
            align: 'center',
            formatter: function(value, row, index){
                if (value && value.length > 30) {
                    var value2 = value.substring(0, 30) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>'
                }
                return value;
            }
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": emailArray.length,
        "rows": emailArray
    });
}

/**
 * 电话通知模板
 */
function doInitPhoneTable(){
    $("#tblPhoneList").datagrid({
        iconCls: 'icon-edit',// 图标
        width: 580,
        height: 200,
        nowrap: false,
        striped: true,
        scrollbarSize: 0,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        onLoadSuccess: function(index, field, value){
            $("#tblPhoneList").closest(".datagrid").css({
                'margin-left': '70px'
            });
        },
        columns: [[{
            field: 'voiceMessageType',
            title: '语音通知类型',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                if (value == 1) {
                    return "文字模板";
                }
                else if (value == 2) {
                    return "录音";
                }
            }
        }, {
            field: 'name',
            title: '模板名称',
            width: 80,
            align: 'center',
        }, {
            field: 'content',
            title: '模板内容',
            width: 150,
            align: 'center',
            formatter: function(value, row, index){
                if (row.voiceMessageType == 1) {
                    if (value && value.length > 30) {
                        var value2 = value.substring(0, 30) + "...";
                        return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>'
                    }
                    return value;
                }
                else if (row.voiceMessageType == 2) {
                    return "-";
                }
            }
        }, {
            field: 'voiceName',
            title: '录音',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                if (row.voiceMessageType == 1) {
                    return "-";
                }
                else if (row.voiceMessageType == 2) {
                    return value;
                }
            }
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": phoneArray.length,
        "rows": phoneArray
    });
}

/**
 * 加载通知策略通知人表格数据
 */
function doInitNotifyToUsers(){
    $('#tblNotifyToUser').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 370,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        singleSelect: true,// 是否单选
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        columns: [[{
            field: 'userName',
            title: '用户姓名',
            width: 130,
            align: 'center',
            formatter: function(value, row, index){
                if (row.userId == -100) {
                    return "值班负责人";
                } else if (row.userId == -101) {
                    return "值班带班领导";
                } else if (row.userId == -102) {
                    return "值班成员";
                } else {
                    return value;
                }
            }
        }, {
            field: 'mobileCode',
            title: '手机号码',
            width: 180,
            align: 'center'
        }, {
            field: 'email',
            title: '邮箱',
            width: 180,
            align: 'center'
        }]]
    }).datagrid('loadData', {
        "total": userArray.length,
        "rows": userArray
    });
}
