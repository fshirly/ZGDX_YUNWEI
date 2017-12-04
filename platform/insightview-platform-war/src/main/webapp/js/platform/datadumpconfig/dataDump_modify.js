$(document).ready(function() {
	initDumpDetail();
});

/*
 * 初始化转储表信息
 */
function initDumpDetail(){
	var id=$('#id').val();
	var path=getRootName();
	var uri=path+"/platform/dataDumpConfig/initDumpDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"id":id,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
			}
		};
	ajax_(ajax_param);
}

function toUpdate(){
    var result = checkInfo("#tblEditDump")
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
			"id":$('#id').val(),
            "tableName": $("#ipt_tableName").val(),
            "timeColumnName": $("#ipt_timeColumnName").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                updateDataDump();
                return;
            } else {
                $.messager.alert("提示", "该转储表信息已存在！", "info");
            }
        }
    };
    ajax_(ajax_param);
}


/*
 * 更新
 */
function updateDataDump(){
    var path = getRootName();
    var uri = path + "/platform/dataDumpConfig/updateDataDump";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
			"id":$('#id').val(),
            "tableName": $("#ipt_tableName").val(),
            "timeColumnName": $("#ipt_timeColumnName").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $.messager.alert("提示", "更新转储表信息成功！", "info");
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            } else {
                $.messager.alert("提示", "更新转储表信息失败！", "error");
            }
            
        }
    };
    ajax_(ajax_param);
}
