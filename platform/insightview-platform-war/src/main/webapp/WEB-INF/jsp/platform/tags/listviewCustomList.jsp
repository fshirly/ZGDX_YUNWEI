<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>listview管理</title>
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
<script type="text/javascript">
	var listviewId = {};

	/**
	 * 查询用户
	 */
	function query(pageNum, pageSize) {

		if (pageNum == undefined || pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == undefined || pageSize == null) {
			pageSize = 10;
		}

		var conditionObj = $('#conditionForm').serializeObject();

		var datas = {
			"condition" : conditionObj,
			"pageSize" : pageSize,
			"pageNum" : pageNum,
			"listviewName" : ""
		};

		$.ajax({
			type : "POST",
			url : rootPath + "/tag/listviewManager/listviews",
			data : JSON.stringify(datas),
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				var easyUiJson = {
					"total" : result.total,
					"rows" : result.data
				};

				//console.log(JSON.stringify(easyUiJson));

				$('#dg').datagrid('loadData', easyUiJson);

				$($('#dg').datagrid('getPager')).pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
			},
			error : function(data) {
				//alert(data);
			}
		});
	}

	/**
	 * 新增
	 */
	function newListview() {

		listviewId = {};

		openModalWin(rootPath + '/tag/listviewManager/listview/create/html', "自定义列表查询-新增", 900, 800);
	}

	/**
	 *修改
	 */
	function editListview() {
		if ($("#dg").datagrid("getSelections").length != 1) {
			$.messager.alert("提示信息", "请选中一行进行修改！");
			return;
		}

		var row = $('#dg').datagrid('getSelected');

		listviewId = {
			"id" : row.id
		};

		openModalWin(rootPath + '/tag/listviewManager/listview/update/html', "自定义列表查询-修改", 900, 800);

	}

	/**
	 * 删除
	 */
	function deleteListview() {

		if ($("#dg").datagrid("getSelections").length == 0) {
			$.messager.alert("提示信息", "未选中数据！");
			return;
		}

		$.messager.confirm('确认', '您确定要删除选择的记录吗？', function(r) {
			if (r) {
				var jsonObj = [];
				var rows = $('#dg').datagrid('getSelections');
				for (var jj = 0; jj < rows.length; jj++) {
					jsonObj.push(rows[jj].id);
				}
				$.ajax({
					type : "DELETE",
					url : rootPath + '/tag/listviewManager/listview',
					data : JSON.stringify(jsonObj),
					dataType : 'json',
					contentType : "application/json",
					success : function(result) {
						if (result.success) {
							$.messager.alert("提示信息", result.message);
							//$('#dg').datagrid('reload'); //reload the user data
							query(1, 10);
						} else {
							$.messager.alert("错误信息", result.errorCode + ": "
									+ result.message);
						}
					},
					error : function(result) {
						//
					}
				});
			}
		});

	}

	//导出
	function listviewExport() {
		if ($("#dg").datagrid("getSelections").length == 0) {
			$.messager.alert("提示信息", "未选中数据！");
			return;
		}

		var jsonObj = [];
		var rows = $('#dg').datagrid('getSelections');
		for (var jj = 0; jj < rows.length; jj++) {
			jsonObj.push(rows[jj].id);
		}
		$.ajax({
					type : "POST",
					url : rootPath + '/tag/listviewManager/listviews/export',
					data : JSON.stringify(jsonObj),
					dataType : 'json',
					contentType : "application/json",
					success : function(result) {
						if (result.success) {
							var filePath = encodeURIComponent(encodeURIComponent(result.filePath));
							document.getElementById("download").src = rootPath + "/tag/listviewManager/listviews/exportByPath/"
									+ filePath;
						} else {
							$.messager.alert("错误信息", result.errorCode + ": "
									+ result.message);
						}

					},
					error : function(result) {
					}
				});
	}

	//导入
	function listviewImport() {
		openModalWin(rootPath + '/tag/listviewManager/listview/import/html', "自定义列表查询-导入", 500, 200);
	}

	//初始化
	$(function() {
		var p = $('#dg').datagrid('getPager');
		$(p).pagination({
			pageNumber : 1,
			pageSize : 10,//每页显示的记录条数，默认为10  
			pageList : [ 5, 10, 20, 50, 100 ],//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			},
			onSelectPage : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				query(pageNumber, pageSize);
				$(this).pagination('loaded');
			}
		});

	});
</script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 140px;
}

.fitem input {
	width: 160px;
}

/*--- 查询条件区域 ---*/
.conditions {
	min-height: 36px;
	min-width: 824px;
	padding: 5px 0 22px 10px;
	border: solid #ffffff;
	border-width: 1px 0;
	background-image:
		url(../../easyui/themes/images/datagrid_header_top.jpg);
	background-position: left bottom;
	background-repeat: repeat-x;
	font-size: 14px;
}

.conditions div {
	margin-top: 5px;
}

.conditions .title {
	display: inline-block;
	min-width: 100px;
	text-align: right;
	font-weight: normal;
	vertical-align: middle;
}

.treetabler .conditions {
	min-width: 99%;
}

.conditions+div {
	border-top: 1px solid #ebebeb;
}

.w13 {
	width: 15%;
	display: inline-block;
	margin-left: -160px;
}

.w12 {
	width: 15%;
	display: inline-block;
	margin-left: -154px;
	min-width: 145px;
}

.conditions input[type="text"],.conditions select {
	width: 15%;
	margin-right: 2px;
}
</style>
</head>

<body onload="query(1,10)">

	<form id="conditionForm" method="post">
		<div class="conditions">
			<div>
				<b class="title">视图名称：</b> <input class="input_text" name='name'
					type='text' /> <b class="title">视图标题：</b> <input
					class="input_text" name='title' type='text' />
			</div>
			<div style="margin-top: 10px">
				<a href="../../../../" class="easyui-linkbutton" icon="icon-back">返回首页</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					icon="icon-search" onclick="query()">查询</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					icon="icon-reload" onclick="$('#conditionForm').form('reset'); ">重置</a>
			</div>
		</div>
	</form>
	<table id="dg" title="listview配置" class="easyui-datagrid"
		style="width: 880px; height: 450px;" toolbar="#toolbar"
		pagination="true" rownumbers="true" fitColumns="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<!-- <th field="id" width="150">ID</th> -->
				<th field="name" width="150">名称</th>
				<th field="title" width="200">标题</th>
				<th field="note" width="200">备注</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newListview()">新增</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-modify" plain="true" onclick="editListview()">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="deleteListview()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-import" plain="true" onclick="listviewImport()">导入</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-export" plain="true" onclick="listviewExport()">导出</a>


	</div>

	<div id="div_info"></div>

	<iframe id="download" src="" width="0" height="0"
		style="width: 0px; height: 0px; display: none"></iframe>
</body>
</html>