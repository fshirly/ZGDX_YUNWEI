$(document).ready(function(){
    initTaskDetail();
});

/*
 * 初始化任务信息
 */
function initTaskDetail(){
    var taskID = $('#taskID').val();
    var path = getRootName();
    var uri = path + "/monitor/syslogTask/initTaskDetail";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "taskID": taskID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            iterObj(data, "lbl");
            var progressStatus = data.progressStatus;
            var serverIP = data.serverIP;
            if (data.createTime != null) {
                $("#lbl_createTime").text(data.createTime.split("\.")[0]);
            }
			if (data.lastStatusTime != null) {
                $("#lbl_lastStatusTime").text(data.lastStatusTime.split("\.")[0]);
            }
            if (progressStatus == 1) {
                $("#lbl_progressStatus").text("等待分发");
            } else if (progressStatus == 2) {
                $("#lbl_progressStatus").text("正在分发");
            } else if (progressStatus == 3) {
                $("#lbl_progressStatus").text("已分发");
            } else if (progressStatus == 4) {
                $("#lbl_progressStatus").text("已接收");
            } else if (progressStatus == 5) {
                $("#lbl_progressStatus").text("已完成");
            }
            if (data.lastOPResult == 0) {
                $("#lbl_lastOPResult").text("成功");
            } else if (data.lastOPResult == 1) {
                $("#lbl_lastOPResult").text("失败");
            }
        }
    };
    ajax_(ajax_param);
}
