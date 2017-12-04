
$(document).ready(function(){
});

/**
 * 选择语音通知类型
 */
function editVoice(){
    var voiceMessageType = $('input[name="voiceMessageType"]:checked').val();
    if (voiceMessageType == 1) {
        $('#textRr').show();
        $('#voiceRr').hide();
        //        $("#ipt_voiceId").combobox('setValue', '-1');
    }
    else {
        $('#textRr').hide();
        $('#voiceRr').show();
        //        $("#ipt_content").val("");
    }
}

/**
 * 添加模板
 */
function toEditContent(){
    var editContentFlag = $("#editContentFlag").val();
    checkContent(editContentFlag);
}

/**
 * 检验模板
 */
function checkContent(editContentFlag){
    var type = $("#type").val();
    //语音类型
    var voiceMessageType = $('input[name="voiceMessageType"]:checked').val();
    if (type == 3 && voiceMessageType == 2) {
        var checkInfoResult = checkInfo("#nameTr");
    }
    else {
        var checkInfoResult = checkInfo("#divContentEdit");
    }
    if (checkInfoResult) {
        var contentName = $("#ipt_name").val();
        var checkArray = [];
        //短信
        if (type == 1) {
            var content = $("#ipt_content").val();
            checkArray = smsArray;
        }
        //邮件
        else if (type == 2) {
            var content = f('#ipt_content').richtext('getValue');
            if (content == "" || content == "<br>") {
                $.messager.alert("提示", "邮件模板内容不能为空！", "info");
                return;
            }
            else if (content.length > 2000) {
                $.messager.alert("提示", "邮件模板内容过长，请修改！", "info");
                return;
            }
            checkArray = emailArray;
        }
        //电话语音
        else if (type == 3) {
            //录音
            if (voiceMessageType == 2) {
                var voiceId = $("#ipt_voiceId").combobox('getValue');
                if (voiceId == -1) {
                    $.messager.alert("提示", "请选择录音！", "info");
                    return;
                }
            }
            //文字模板
            else {
                var content = $("#ipt_content").val();
            }
            checkArray = phoneArray;
        }
        if (isContentNameRepeat(checkArray, contentName, editContentFlag, type)) {
            $.messager.alert("提示", "模板名称不能重复！", "info");
            return;
        }
        doEditContent(checkArray, contentName, content, type, editContentFlag);
    }
}

/**
 * 检查模板名称是否重复
 */
function isContentNameRepeat(checkArray, contentName, editContentFlag, type){
    var voiceMessageType = $('input[name="voiceMessageType"]:checked').val();
    //新增模板
    if (editContentFlag == "add") {
        if (type != 3) {
            for (var i = 0; i < checkArray.length; i++) {
                if (contentName == checkArray[i].name) {
                    return true;
                }
            }
        }
        else {
            for (var i = 0; i < checkArray.length; i++) {
                if (voiceMessageType == checkArray[i].voiceMessageType && contentName == checkArray[i].name) {
                    return true;
                }
            }
        }
    }
    //编辑模板
    else {
        var contentId = $("#contentId").val();
        if (type != 3) {
            for (var i = 0; i < checkArray.length; i++) {
                if (contentName == checkArray[i].name && contentId != checkArray[i].id) {
                    return true;
                }
            }
        }
        else {
            for (var i = 0; i < checkArray.length; i++) {
                if (voiceMessageType == checkArray[i].voiceMessageType && contentName == checkArray[i].name && contentId != checkArray[i].id) {
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * 新增或编辑模板
 */
function doEditContent(checkArray, name, content, type, editContentFlag){
    var contentId = ++editComtentId;
    if (editContentFlag == "update") {
        contentId = $("#contentId").val();
    }
    var policyId = $("#policyId").val();
    var voiceMessageType = $('input[name="voiceMessageType"]:checked').val();
    //组装电话插入数据
    if (type == 3) {
        //文字模板
        voiceMessageType = parseInt(voiceMessageType);
        if (voiceMessageType == 1) {
            var row = {
                "id": contentId,
                "policyId": policyId,
				"notifyType":type,
                "voiceMessageType": voiceMessageType,
                "name": name,
                "content": content,
            };
        }
        //录音
        else {
            var voiceId = $("#ipt_voiceId").combobox('getValue');
            var voiceName = $("#ipt_voiceId").combobox('getText');
            var row = {
                "id": contentId,
                "policyId": policyId,
				"notifyType":type,
                "voiceMessageType": voiceMessageType,
                "name": name,
                "voiceId": voiceId,
                "voiceName": voiceName,
            };
        }
    }
    //组装短信/邮件插入数据
    else {
        var row = {
            "id": contentId,
            "policyId": policyId,
			"notifyType":type,
            "name": name,
            "content": content,
        };
    }
	
    //更新列表数据
    if (editContentFlag == "add") {
        checkArray.push(row);
    }
    else {
        var index = getDelContentIndex(checkArray, contentId);
        checkArray.splice(index, 1, row);
    }
    var gridData = {
        "total": checkArray.length,
        "rows": checkArray
    };
    var gridId = "";
    if (type == 1) {
        gridId = "#tblSmsList";
    }
    else if (type == 2) {
        gridId = "#tblEmailList";
    }
    else if (type == 3) {
        gridId = "#tblPhoneList";
    }
    $(gridId).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', gridData);
    $('#popWin2').window('close');
}

/**
 * 编辑时初始化模板
 */
function initContent(type, contentId){
    var dataArray = [];
    if (type == 1) {
        dataArray = smsArray;
    }
    else if (type == 2) {
        dataArray = emailArray;
    }
    else if (type == 3) {
        dataArray = phoneArray;
    }
    //获得模板对象
    for (var i = 0; i < dataArray.length; i++) {
        if (contentId == dataArray[i].id) {
            var name = dataArray[i].name;
            $("#ipt_name").val(name);
            if (type == 1) {
                $("#ipt_content").val(dataArray[i].content);
            }
            else if (type == 2) {
                $('#ipt_content').richtext('setValue', dataArray[i].content);
            }
            else if (type == 3) {
                var voiceMessageType = dataArray[i].voiceMessageType;
                if (voiceMessageType == 1) {
                    $("input[type='radio'][name='voiceMessageType']").get(0).checked = true;
                    $("#ipt_content").val(dataArray[i].content);
                }
                else if (voiceMessageType == 2) {
                    $("input[type='radio'][name='voiceMessageType']").get(1).checked = true;
                    $("#ipt_voiceId").combobox('setValue', dataArray[i].voiceId);
                }
                editVoice();
            }
        }
    };
    }
