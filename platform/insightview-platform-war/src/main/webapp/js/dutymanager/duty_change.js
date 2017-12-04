$(function() {
	
	//初始化值班日期
	$("#happen").datebox({
		editable: false,
		panelWidth: 135,
		onSelect: function(date) {
			$("#tblDutyChange").datagrid("reload", {dutyDate: $("#happen").datebox("getValue")});
			$("#fromUser").empty();
			$("#toUser").empty();
			doSelect(date);
		}
	});
	
	var now = new Date();
	var yy = now.getFullYear();
	var mm = now.getMonth()+1;
	var dd = now.getDate();
	var nowStr = yy+"-"+(mm<10?("0"+mm):mm)+"-"+(dd<10?("0"+dd):dd);
	$("#happen").datebox("setValue", nowStr);
	
	doSelect(now);
	
	//调班操作
	$("#dutychange_submit").click(function() {
		if(checkInfo("#table_dutychange")) {
			var happen = $("#happen").datebox("getValue");
			if(happen == "") {
				happen = nowStr;
			}
			var fromUser = $("#fromUser").val();
			var toUser = $("#toUser").val();
			$.ajax({
				url: getRootName()+"/platform/dutymanager/duty/changeDuty",
				data: "happen="+happen+"&fromUser="+fromUser+"&toUser="+toUser,
				success: function(data) {
					if(data == "success") {
						//清空值班日期
						$("#happen").datebox("clear");
						//清空带班领导
						$("#leader").html("");
						//重新初始化fromUser与toUser列表
						$("#fromUser").empty();
						$("#toUser").empty();
						$("#fromUser").createSelect2({
							id: "id",
							name: "value",
							uri: "/platform/dutymanager/duty/loadFromUserList?dutyDate=",
						});
						$("#toUser").createSelect2({
							id: "id",
							name: "value",
							uri: "/platform/dutymanager/duty/loadToUserList?id=",
						});
						$("#fromUser").val("-1");
						$("#toUser").val("-1");
						//刷新调班记录列表
						$("#tblDutyChange").datagrid("reload", {dutyDate: happen});
						//刷新值班列表
						window.frames['component_2'].$("#tblDutyDataList").datagrid("load");
					}
				},
				error: function() {
					$.messager.alert("错误", "ajax_error", "error");
				}
			});
		}
	});
	
	doInitTable();
});

function doInitTable() {
	$("#tblDutyChange").datagrid({
		width: 'auto',
        height: 216,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        url: getRootName()+"/platform/dutymanager/duty/loadDutyChangeList",
        queryParams: {dutyDate: $("#happen").datebox("getValue")},
        remoteSort: false,
        idField: 'id',
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: false,
        rownumbers: true,
        //pagination: true,
        columns: [[{
        	field: "fromUser",
        	title: "原值班人",
        	align: "center",
        	width: 120
        }, {
        	field: "toUser",
        	title: "调班给",
        	align: "center",
        	width: 120
        }, {
        	field: "handler",
        	title: "变更人",
        	align: "center",
        	width: 120
        }, {
        	field: "happen",
        	title: "变更时间",
        	align: "center",
        	width: 120,
        	formatter: function(value, row, index) {
        		var date = new Date(value);
        		var y = date.getFullYear();
        		var m = date.getMonth()+1;
        		var d = date.getDate();
        		return y+"-"+m+"-"+d;
        	}
        }]],
	});
}

function doSelect(date) {
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var happen = y+"-"+m+"-"+d;
	$.ajax({
		url: getRootName() + "/platform/dutymanager/duty/loadLeaderName",
		type: "post",
		data: "dutyDate=" + happen,
		success: function(data) {
			//初始化带班领导
			$("#leader").html(data);
			//初始化值班人列表
			$("#fromUser").createSelect2({
				id: "id",
				name: "value",
				uri: "/platform/dutymanager/duty/loadFromUserList?dutyDate="+happen,
			});
			//初始化调班给列表
			$("#toUser").createSelect2({
				id: "id",
				name: "value",
				uri: "/platform/dutymanager/duty/loadToUserList?id=",
			});
		},
		error: function() {
			$.messager.alert("错误", "ajax_error", "error");
		}
	});

}

function initToUser() {
	//当选择一个值班人时，先清空调班给列表
	$("#toUser").empty();
	var id = $("#fromUser").val();
	//初始化调班给列表，排除已选中的值班人
	$("#toUser").createSelect2({
		id: "id",
		name: "value",
		uri: "/platform/dutymanager/duty/loadToUserList?id="+id,
	});
}


