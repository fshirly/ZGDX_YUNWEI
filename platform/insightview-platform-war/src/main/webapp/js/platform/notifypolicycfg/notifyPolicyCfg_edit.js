//短信模板列表数据
var smsArray = [];
//邮件模板列表数据
var emailArray = [];
//电话语音模板列表数据
var phoneArray = [];
//新增或编辑时 模板id
var editComtentId = 0;
//通知用户列表数据
var userArray = [];
//新增或编辑时 用户id
var userId = 0;
//已经选择的值班人
var selectedDutier = [];

$(document).ready(function(){
    var editFlag = $("#editFlag").val();
    $('#ipt_policyType').combobox({
        panelHeight: '120',
        panelWidth: '180',
        url: getRootName() + '/platform/notifyPolicyCfg/getAllPolicyType',
        valueField: 'typeId',
        textField: 'typeName',
        editable: false,
        onLoadSuccess: function(){
            if (editFlag == "add") {
            
                var data1 = $('#ipt_policyType').combobox('getData'); //赋默认值
                if (data1.length > 0) {
                    $("#ipt_policyType ").combobox('select', data1[0].typeId);
                }
            }
        },
        onSelect: function(){
            getContent();
        }
    });
    if (editFlag == "add") {
        $('#divSmsList').hide();
        $('#divEmailList').hide();
        $('#divPhoneList').hide();
        doInitNotifyToUsers();
    }
    else {
        doInitPolicyCfg();
    }
    
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
            $("#ipt_policyName").val(policyCfg.policyName);
            //            console.log("policyCfg.policyType==" + policyCfg.policyType)
            $("#ipt_policyType").combobox('setValue', policyCfg.policyType);
            $("#ipt_maxTimes").val(policyCfg.maxTimes);
            $("#ipt_descr").val(policyCfg.descr);
            
            if (policyCfg.isSms == 0) {
                $("input:radio[name='isSms'][value=0]").attr("checked", 'checked');
            }
            else if (policyCfg.isSms == 1) {
                $("input:radio[name='isSms'][value=1]").attr("checked", 'checked');
            }
            if (policyCfg.isEmail == 0) {
                $("input:radio[name='isEmail'][value=0]").attr("checked", 'checked');
                $("#email").hide();
            }
            else if (policyCfg.isEmail == 1) {
                $("input:radio[name='isEmail'][value=1]").attr("checked", 'checked');
            }
            if (policyCfg.isPhone == 0) {
                $("input:radio[name='isPhone'][value=0]").attr("checked", 'checked');
                $("#email").hide();
            }
            else if (policyCfg.isPhone == 1) {
                $("input:radio[name='isPhone'][value=1]").attr("checked", 'checked');
            }
            smsArray = data.smsContentLst;
            emailArray = data.emailContentLst;
            phoneArray = data.phoneContentLst;
            editSms();
            editEmail();
            editPhone();
            userArray = data.userLst;
            doInitNotifyToUsers();
            for (var i=0; i<userArray.length; i++) {
				if(userArray[i].userId == -100 || userArray[i].userId == -101 || userArray[i].userId == -102)
				selectedDutier.push(userArray[i].userId);
			};
            if (policyCfg.isSms == 0 || policyCfg.isEmail == 0 || policyCfg.isPhone == 0) {
				var typeId = $("#ipt_policyType").combobox("getValue");
				if (typeId != -1) {
	                var path = getRootName();
	                var uri = path + "/platform/notifyPolicyCfg/getTypeContent?typeId=" + typeId;
	                var ajax_param = {
	                    url: uri,
	                    type: "post",
	                    async: false,
	                    datdType: "json",
	                    data: {
	                        "t": Math.random()
	                    },
	                    error: function(){
	                        $.messager.alert("错误", "ajax_error", "error");
	                    },
	                    success: function(data){
	                        if (policyCfg.isSms == 0) {
	                            smsArray = data.smsContentLst;
	                            editSms();
	                        }
	                        if (policyCfg.isEmail == 0) {
	                            emailArray = data.emailContentLst;
	                            editEmail();
	                            
	                        }
	                        if (policyCfg.isPhone == 0) {
	                            phoneArray = data.phoneContentLst;
	                            editPhone();
	                        }
	                    }
	                }
	                ajax_(ajax_param);
				}
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 选择策略类型时，模板内容展示
 */
function getContent(){
    var typeId = $("#ipt_policyType").combobox("getValue");
    if (typeId != -1) {
        var path = getRootName();
        var uri = path + "/platform/notifyPolicyCfg/getTypeContent?typeId=" + typeId;
        //	else{
        //		var policyId = $("#policyId").val();
        //	    var uri = path + "/platform/notifyPolicyCfg/getPolicyContent?policyId="+policyId;
        //	}
        var ajax_param = {
            url: uri,
            type: "post",
            async: false,
            datdType: "json",
            data: {
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                smsArray = data.smsContentLst;
                emailArray = data.emailContentLst;
                phoneArray = data.phoneContentLst;
                editSms();
                editEmail();
                editPhone();
            }
        }
        ajax_(ajax_param);
        
    }
    else {
        smsArray = [];
        emailArray = [];
        phoneArray = [];
        editSms();
        editEmail();
        editPhone();
    }
}

var emailMarginLeft = parseInt($('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft.slice(0, 2));
var phoneMarginLeft = parseInt($('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft.slice(0, 2));

/**
 * 是否短信
 */
function editSms(){
    var isSmsChecked = $('input[name="isSms"]:checked').val();
    if (isSmsChecked == '0') {
        $("#tblSmsList").closest(".datagrid").hide();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + 10 + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + 10 + "px";
    }
    else {
        $("#tblSmsList").closest(".datagrid").show();
        doInitSmsTable();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + "px";
    }
}

/**
 * 是否邮件
 */
function editEmail(){
    var isEmailChecked = $('input[name="isEmail"]:checked').val();
    if (isEmailChecked == '0') {
        $("#tblEmailList").closest(".datagrid").hide();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + 10 + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + 10 + "px";
    }
    else {
        $("#tblEmailList").closest(".datagrid").show();
        doInitEmailTable();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + "px";
    }
}

/**
 * 是否电话语音
 */
function editPhone(){
    var isPhoneChecked = $('input[name="isPhone"]:checked').val();
    if (isPhoneChecked == '0') {
        $("#tblPhoneList").closest(".datagrid").hide();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + 10 + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + 10 + "px";
    }
    else {
        $("#tblPhoneList").closest(".datagrid").show();
        doInitPhoneTable();
        $('input[name="isPhone"]:checked').parents("table")[0].style.marginLeft = phoneMarginLeft + "px";
        $('input[name="isEmail"]:checked').parents("table")[0].style.marginLeft = emailMarginLeft + "px";
    }
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
    var typeId = $("#ipt_policyType").combobox("getValue");
    var path = getRootName();
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
            if (typeId == -1) {
                $("#tblSmsList").closest(".datagrid").children().children().eq(0).show();
            }
            else {
                $("#tblSmsList").closest(".datagrid").children().children().eq(0).hide();
            }
        },
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                doOpenAddContent(1);
            }
        }],
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
        }, {
            field: 'ids',
            title: '操作',
            width: 50,
            align: 'center',
            formatter: function(value, row, index){
                if (typeId == -1) {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(1,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDelContent(1,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" title="删除" /></a>';
                }
                else {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(1,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>';
                }
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
    var typeId = $("#ipt_policyType").combobox("getValue");
    var path = getRootName();
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
            
            if (typeId == -1) {
                $("#tblEmailList").closest(".datagrid").children().children().eq(0).show();
            }
            else {
                $("#tblEmailList").closest(".datagrid").children().children().eq(0).hide();
            }
        },
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                doOpenAddContent(2);
            }
        }],
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
        }, {
            field: 'ids',
            title: '操作',
            width: 50,
            align: 'center',
            formatter: function(value, row, index){
                if (typeId == -1) {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(2,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDelContent(2,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" title="删除" /></a>';
                }
                else {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(2,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>';
                }
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
    var path = getRootName();
    var typeId = $("#ipt_policyType").combobox("getValue");
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
            if (typeId == -1) {
                $("#tblPhoneList").closest(".datagrid").children().children().eq(0).show();
            }
            else {
                $("#tblPhoneList").closest(".datagrid").children().children().eq(0).hide();
            }
        },
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                doOpenAddContent(3);
            }
        }],
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
        }, {
            field: 'ids',
            title: '操作',
            width: 50,
            align: 'center',
            formatter: function(value, row, index){
                if (typeId == -1) {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(3,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDelContent(3,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" title="删除" /></a>';
                }
                else {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenModifyContent(3,' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a>';
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
 * 打开新增模板页面
 */
function doOpenAddContent(type){
    parent.$('#popWin2').window({
        title: '新增通知策略模板',
        width: 700,
        height: 300,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/platform/notifyPolicyCfg/toShowEditContent?editContentFlag=add&type=' + type,
    });
}

/**
 * 打开编辑模板页面
 */
function doOpenModifyContent(type, contentId){
    parent.$('#popWin2').window({
        title: '编辑通知策略模板',
        width: 700,
        height: 300,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/platform/notifyPolicyCfg/toShowEditContent?editContentFlag=update&type=' + type + "&contentId=" + contentId,
    });
}

/**
 * 删除模板
 */
function toDelContent(type, contentId){
    $.messager.confirm("提示", "确定删除所选中项？", function(r){
        if (r == true) {
            var dataArray = [];
            var gridId = "";
            if (type == 1) {
                dataArray = smsArray;
                gridId = "#tblSmsList";
            }
            else if (type == 2) {
                dataArray = emailArray;
                gridId = "#tblEmailList";
            }
            else if (type == 3) {
                dataArray = phoneArray;
                gridId = "#tblPhoneList";
            }
            var index = getDelContentIndex(dataArray, contentId);
            dataArray.splice(index, 1);
            var gridData = {
                "total": dataArray.length,
                "rows": dataArray
            };
            $(gridId).datagrid({
                loadFilter: pagerFilter
            }).datagrid('loadData', gridData);
        }
    });
}

/**
 * 获得删除模板的索引
 */
function getDelContentIndex(dataArray, contentId){
    for (var i = 0, n = dataArray.length; i < n; i++) {
        if (contentId == dataArray[i].id) {
            return i;
        }
    }
    return -1;
}

/**
 * 加载通知策略通知人表格数据
 */
function doInitNotifyToUsers(){
    var path = getRootName();
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
        }, {
            field: 'ids',
            title: '操作',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:doDelNotifyToUser(' +
                row.id +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" title="删除"/></a>';
            }
        }]]
    }).datagrid('loadData', {
        "total": userArray.length,
        "rows": userArray
    });
}

/**
 * 更新通知用户列表
 */
function reloadNotifytToUserTable(row){
    userArray.push(row);
    var gridData = {
        "total": userArray.length,
        "rows": userArray
    };
    $("#tblNotifyToUser").datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', gridData);
}

/**
 * 删除通知用户
 */
function doDelNotifyToUser(id){
    $.messager.confirm("提示", "确定删除所选中项？", function(r){
        if (r == true) {
            var index = getDelContentIndex(userArray, id);
            userArray.splice(index, 1);
            var gridData = {
                "total": userArray.length,
                "rows": userArray
            };
            $("#tblNotifyToUser").datagrid({
                loadFilter: pagerFilter
            }).datagrid('loadData', gridData);
        }
    });
}

/**
 * 从用户列表选择用户
 */
function addFromUsers(){
    resetFormFilter();
    doInitUserTable();
    $("#btnAdduser").unbind();
    $("#btnAdduser").bind("click", function(){
        addUser();
    });
    $("#btnCloseUser").unbind();
    $("#btnCloseUser").bind("click", function(){
        $('#divAddUser').dialog('close');
    });
    $('#divAddUser').dialog('open');
}

/**
 * 重置已有用户列表的查询条件
 */
function resetFormFilter(){
    $("#txtFilterUserName").val("");
}

/**
 * 用户列表
 */
function doInitUserTable(){
    var path = getRootName();
    var exceptUserIds = getSelectedUserIds();
    $('#tblSysUser').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 320,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        url: path + '/platform/notifyPolicyCfg/listSysUser',
        queryParams: {
            "status": -1,
            "isAutoLock": -1,
            "userType": -1,
            "exceptUserIds": exceptUserIds
        },
        remoteSort: false,
        idField: 'userID',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        columns: [[{
            field: 'userID',
            checkbox: true,
        }, {
            field: 'userName',
            title: '用户姓名',
            width: 130,
            align: 'center'
        }, {
            field: 'mobilePhone',
            title: '手机号码',
            width: 180,
            align: 'center'
        }, {
            field: 'email',
            title: '邮箱',
            width: 180,
            align: 'center'
        }, ]]
    });
}

/**
 * 更新用户表数据
 */
function reloadUserTable(){
    var userName = $("#txtFilterUserName").val();
    var exceptUserIds = getSelectedUserIds();
    $('#tblSysUser').datagrid('options').queryParams = {
        "userName": userName,
        "userType": -1,
        "isAutoLock": -1,
        "status": -1,
        "exceptUserIds": exceptUserIds
    };
    $('#tblSysUser').datagrid('load');
    $('#tblSysUser').datagrid('uncheckAll');
}

/**
 * 获得已经选择的用户id
 */
function getSelectedUserIds(){
    var ids = "";
    for (var i = 0; i < userArray.length; i++) {
        if (userArray[i].userId != "" && userArray[i].userId != null && userArray[i].userId != -100) 
            ids += userArray[i].userId + ",";
    };
    if (ids != "") {
        ids = ids.substring(0, ids.length - 1);
    }
    return ids;
}

/**
 * 从用户列表中添加数据添加用户
 */
function addUser(){
    var checkedItems = $('#tblSysUser').datagrid("getChecked");
    if (null != checkedItems && checkedItems.length != 0) {
        $.messager.confirm("提示", "确定添加所选中项？", function(r){
            if (r == true) {
                $.each(checkedItems, function(index, item){
                    var id = ++userId;
                    var userID = item.userID;
                    var policyId = $("#policyId").val();
                    var mobileCode = item.mobilePhone;
                    var email = item.email;
                    var userName = item.userName;
                    var row = {
                        "id": id,
                        "policyId": policyId,
                        "userId": userID,
                        "userName": userName,
                        "mobileCode": mobileCode,
                        "email": email,
                    };
                    userArray.push(row);
                });
                var gridData = {
                    "total": userArray.length,
                    "rows": userArray
                };
                $("#tblNotifyToUser").datagrid({
                    loadFilter: pagerFilter
                }).datagrid('loadData', gridData);
                
                $('#divAddUser').dialog('close');
            }
        });
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/**
 * 添加临时用户
 */
function addCasualUser(){
    resetForm('tblCasualUser');
    $("#btnAddCasualUser").unbind();
    $("#btnAddCasualUser").bind("click", function(){
        doAddCasualUser();
    });
    $("#btnColseCasualUser").unbind();
    $("#btnColseCasualUser").bind("click", function(){
        $('#divAddCasualUser').dialog('close');
    });
    $('#divAddCasualUser').dialog('open');
}

/**
 * 判断跟用户是否已经存在
 */
function isExistCasualUser(){
    var mobileCode = $("#ipt_mobileNow").val();
    var email = $("#ipt_EmailNow").val();
    for (var i = 0, n = userArray.length; i < n; i++) {
        if (userArray[i].mobileCode == mobileCode && userArray[i].email == email) {
            return true;
        }
    }
    return false;
}

/**
 * 添加临时用户
 */
function doAddCasualUser(){
    var result = checkInfo('#divAddCasualUser');
    if (result) {
        var mobileCode = $("#ipt_mobileNow").val();
        var email = $("#ipt_EmailNow").val();
        if (mobileCode == "" && email == "") {
            $.messager.alert("提示", "手机号码或邮箱至少添加一条信息！", "info");
            return;
        }
        
        if (isExistCasualUser()) {
            $.messager.alert("提示", "该临时用户已存在！", "info");
            return;
        }
        var id = ++userId;
        var policyId = $("#policyId").val();
        var row = {
            "id": id,
            "policyId": policyId,
            "mobileCode": mobileCode,
            "email": email,
        };
        reloadNotifytToUserTable(row);
        $('#divAddCasualUser').dialog('close');
    }
}


/**
 * 添加值班人
 */
function addDutier(){
	$("#director").attr("checked", false);
	$("#leader").attr("checked", false);
	$("#member").attr("checked", false);
    initDutier();
    $("#btnAddDutier").unbind();
    $("#btnAddDutier").bind("click", function(){
        doAddDutier();
    });
    $("#btnColseDutier").unbind();
    $("#btnColseDutier").bind("click", function(){
        $('#divAddDutier').dialog('close');
		selectedDutier = [];
    });
    $('#divAddDutier').dialog('open');
}

/**
 * 初始化已经选择的值班负责人
 */
function initDutier(){
	if(selectedDutier != null) {
		for(var i = 0; i < selectedDutier.length; i++) {
			if(selectedDutier[i] == '-100') {
				$("#director").attr("checked", true);
			} else if(selectedDutier[i] == '-101') {
				$("#leader").attr("checked", true);
			} else if(selectedDutier[i] == '-102') {
				$("#member").attr("checked", true);
			}
		}
	}
}

/**
 * 添加值班负责人
 */
function doAddDutier(){
    var checkbox = $("#divChooseDutier :checkbox:checked");
    if (checkbox != null) {
		//删除之前选择的值班人
		delSelectedDutier();
		//清空选择的值班人id
		selectedDutier = [];
		
		//添加现在选择的值班人
        $.each(checkbox, function(i, e){
			var dutyUserId = $(this).val();
                selectedDutier.push(dutyUserId);
                var id = ++userId;
                var policyId = $("#policyId").val();
                var row = {
                    "id": id,
                    "policyId": policyId,
                    "userId": dutyUserId,
                };
                userArray.push(row);
        });
        var gridData = {
            "total": userArray.length,
            "rows": userArray
        };
        $("#tblNotifyToUser").datagrid({
            loadFilter: pagerFilter
        }).datagrid('loadData', gridData);
		 $('#divAddDutier').dialog('close');
		 selectedDutier = [];
    }
    else {
        $.messager.alert("提示", "至少选择一种值班人类型！", "info");
    }
}

/**
 * 删除之前选中的类型
 */
function delSelectedDutier(){
	debugger;
	if (selectedDutier != []) {
		for (var j = 0; j < selectedDutier.length; j++) {
			for (var i = 0, n = userArray.length; i < n; i++) {
				if (selectedDutier[j] == userArray[i].userId) {
					userArray.splice(i, 1);
					break;
				}
			}
		}
	}	
}

/**
 * 新增或编辑通知策略
 */
function toEditPolicyCfg(){
    var result = checkInfo('#tblNotifyCfgAdd');
    if (result) {
        checkCfgName();
    }
}

/**
 * 验证通知策略名称是否存在
 */
function checkCfgName(){
    var path = getRootName();
    var uri = path + "/platform/notifyPolicyCfg/checkCfgName";
    var policyId = $("#policyId").val();
    if (editFlag == "add") {
        policyId = -1;
    }
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "policyName": $("#ipt_policyName").val(),
            "policyId": policyId,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $.messager.alert("提示", "该规则名称已存在！", "info");
            }
            else {
                doEditPolicyCfg();
                return;
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 新增或编辑通知策略
 */
function doEditPolicyCfg(){
    var isSms = $('input[name="isSms"]:checked').val();
    var isEmail = $('input[name="isEmail"]:checked').val();
    var isPhone = $('input[name="isPhone"]:checked').val();
    if (isSms == 1 || isEmail == 1 || isPhone == 1) {
        var maxTimes = $("#ipt_maxTimes").val();
        if (maxTimes == "") {
            $.messager.alert("提示", "设置短信、邮件或者电话语音通知时，最大发送次数不能为空！", "info");
            return;
        }
        if (maxTimes > 5) {
            $.messager.alert("提示", "最大发送次数不能超过5次！", "info");
            return;
        }
    };
    //短信模板数组转换为json
    var smsArrayData = JSON.stringify(smsArray);
    //邮件模板数组转换为json
    var emailArrayData = JSON.stringify(emailArray);
    //电话语音模板数组转换为json
    var phoneArrayData = JSON.stringify(phoneArray);
    //通知用户数组转换为json
    var userArrayData = JSON.stringify(userArray);
    
    var editFlag = $("#editFlag").val();
    var path = getRootName();
    var uri = path + "/platform/notifyPolicyCfg/doEditPolicyCfg?editFlag=" + editFlag;
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "policyId": $("#policyId").val(),
            "policyName": $("#ipt_policyName").val(),
            "policyType": $("#ipt_policyType").combobox("getValue"),
            "maxTimes": $("#ipt_maxTimes").val(),
            "isSms": $('input[name="isSms"]:checked').val(),
            "isEmail": $('input[name="isEmail"]:checked').val(),
            "isPhone": $('input[name="isPhone"]:checked').val(),
            "descr": $("#ipt_descr").val(),
            "smsArrayData": smsArrayData,
            "emailArrayData": emailArrayData,
            "phoneArrayData": phoneArrayData,
            "userArrayData": userArrayData,
            "mFlag": 1
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                //                $.messager.alert("提示", "通知策略保存成功！", "info");
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            }
            else {
                $.messager.alert("失败", "通知策略保存失败！", "error");
            }
        }
    };
    ajax_(ajax_param);
}


