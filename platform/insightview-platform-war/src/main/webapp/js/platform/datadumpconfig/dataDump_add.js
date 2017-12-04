function toAdd(){
    var result = checkInfo("#tblAddDump")
    if (result == true) {
        isExist();
    }
}

/*
 * 验证是否存在
 */
function isExist(){
    var path = getRootName();
    var uri = path + "/platform/dataDumpConfig/isExist";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "tableName": $("#ipt_tableName").val(),
            "timeColumnName": $("#ipt_timeColumnName").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                addDataDump();
                return;
            } else {
                $.messager.alert("提示", "该转储表信息已存在！", "info");
            }
        }
    };
    ajax_(ajax_param);
}


/*
 * 新增
 */
function addDataDump(){
    var path = getRootName();
    var uri = path + "/platform/dataDumpConfig/addDataDump";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "tableName": $("#ipt_tableName").val(),
            "timeColumnName": $("#ipt_timeColumnName").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $.messager.alert("提示", "新增转储表信息成功！", "info");
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            } else {
                $.messager.alert("提示", "新增转储表信息失败！", "error");
            }
            
        }
    };
    ajax_(ajax_param);
}
