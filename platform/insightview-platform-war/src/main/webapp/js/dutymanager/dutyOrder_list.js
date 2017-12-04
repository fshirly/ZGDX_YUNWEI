$(function() {
	doInitTable();
});

function doInitTable() {
	$("#tblDutyOrder").datagrid({
		width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: getRootName() + "/dutyOrder/toDutyOrderList",
        remoteSort: false,
        sortName: "beginPoint",
        idField: 'id',
        scrollbarSize: 0,
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: false,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        toolbar: [{
        	text: "新建",
        	iconCls: "icon-add",
        	handler: function() {
        		toAdd();
        	}
        }, {
        	text: "<b style='cursor: default;color:#333333 !important;'>值班班次(最多可设置5个班次):</b>"
        }],
        columns: [[{
        	field: "title",
        	title: "班次名称",
        	align: "center",
        	width: 120
        }, {
        	field: "beginPoint",
        	title: "开始时间",
        	align: "center",
        	width: 120
        }, {
        	field: "endPoint",
        	title: "结束时间",
        	align: "center",
        	width: 120
        }, {
        	field: "id",
        	title: "操作",
        	align: "center",
        	width: 200,
        	formatter: function(value, row, index) {
        		return "<a style='cursor:pointer' title='查看' onclick=javascript:doShow(" + row.id + ");><img src='" + getRootName() + "/style/images/icon/icon_view.png' alt='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a style='cursor:pointer' title='修改' onclick=javascript:toEdit(" + row.id + ");><img src='" + getRootName() + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" + 
                "<a style='cursor:pointer' title='删除' onclick=javascript:doDel(" + row.id + ");><img src='" + getRootName() + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
        	}
        }]]
	});
	
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblDutyOrder').resizeDataGrid(0, 0, 0, 0);
	});
}

function toAdd() {
	var rows = $("#tblDutyOrder").datagrid("getRows");
	if(rows.length == 5) {
		$.messager.alert("提示", "最多可设置5个值班班次", "warning");
		return;
	}
	parent.$("#popWin").window({
		title: "新增值班班次",
		width: 500,
		height: 420,
		minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + "/dutyOrder/addDutyOrderPage"
	});
}

function toEdit(id) {
	parent.$("#popWin").window({
		title: "修改值班班次",
		width: 500,
		height: 420,
		minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + "/dutyOrder/editDutyOrderPage?id=" + id
	});
}

function doShow(id) {
	parent.$("#popWin").window({
		title: "查看值班班次",
		width: 530,
		height: 420,
		minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + "/dutyOrder/showDutyOrder?id=" + id
	});
}

function doDel(id) {
	$.messager.confirm("提示", "确定删除该班次吗？", function(r) {
		if(r) {
			$.ajax({
				url: getRootName() + "/dutyOrder/deleteDutyOrder",
				type: "post",
				data: {
					"id": id,
					"t": Math.random()
				},
				error: function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success: function(data) {
					if(data == "success") {
						$("#tblDutyOrder").datagrid("reload");
					}
				}
			});
		}
	});
}