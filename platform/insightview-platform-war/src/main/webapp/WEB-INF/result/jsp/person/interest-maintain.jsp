<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
<title>兴趣信息</title>
<script type="text/javascript">
	$(function() {
		grid = $('#person-table')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/interest/listInterest',
							title : '兴趣资料',
							singleSelect : true,
							fitColumns : true,
							autoRowHeight : true,
							loadMsg : '兴趣资料正在加载,请稍后 !',
							striped : true,
							autoRowHeight : true,
							pagination : true,
							rownumbers : true,
							resizeHandle : 'both',
							pagePosition : 'both',
							scrollbarSize : 10,
							queryParams : {
								name1 : 'easyui',
								subject1 : 'datagrid'
							},
							frozenColumns : [ [ {
								field : 'id',
								checkbox : true
							}, ] ],
							columns : [ [ {
								field : 'name',
								title : '兴趣',
								width : 80,
								align : 'center'
							}, {
								field : 'createdBy',
								title : '创建人',
								width : 80,
								align : 'center'
							}, {
								field : 'createdTime',
								title : '创建时间',
								width : 80,
								align : 'center',
							}, {
								field : 'lastUpdatedBy',
								title : '最后修改人',
								width : 80,
								align : 'center'
							}, {
								field : 'lastUpdatedTime',
								title : '最后修改时间',
								align : 'center',
								width : 80,
							} ] ],
							toolbar : [ {
								text : '新增兴趣',
								iconCls : 'icon-add',
								handler : newPerson
							}, '-', {
								text : '修改兴趣',
								iconCls : 'icon-edit',
								handler : editPerson
							}, '-', {
								text : '删除兴趣',
								iconCls : 'icon-remove',
								handler : removePerson
							} ]
						});
		$('#btn-save,#btn-cancel').linkbutton();
		win = $('#person-window').window({
			closed : true
		});
		form = win.find('form');

		//设置分页控件  
		var p = $('#person-table').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10  
			pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});

	var grid;
	var win;
	var form;
	function newPerson() {
		win.window('open');
		form.form('clear');
		form.url = '${pageContext.request.contextPath}/interest/saveInterest';
	}
	function editPerson() {
		var row = grid.datagrid('getSelected');
		if (row) {
			win.window('open');
			form.form('load',
					'${pageContext.request.contextPath}/interest/getInterestInfo?id='
							+ row.id);
			form.url = '${pageContext.request.contextPath}/interest/updateInterest?id='
					+ row.id;

		} else {
			$.messager.show({
				title : '警告',
				msg : '请先选择兴趣。'
			});
		}
	}
	function savePerson() {
		form.form('submit', {
			url : form.url,
			success : function(data) {
				eval('data=' + data);
				if (data.dealFlag) {
					grid.datagrid('reload');
					win.window('close');
					$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		});
	}

	function removePerson() {
		var selected = grid.datagrid('getSelected');
		if (selected) {
			$.messager
					.confirm(
							'warning',
							'确认删除么?',
							function(id) {
								if (id) {
									id = selected.id;
									$
											.ajax({
												type : "POST",
												url : "${pageContext.request.contextPath}/interest/deleteInterest",
												data : "id=" + id,
												dataType : "json",
												success : function callback(
														data) {
													if (!data.dealFlag) {
														$.messager.show({
															title : '警告',
															msg : '删除失败。'
														});
													} else {
														$.messager.show({
															title : '提示',
															msg : '删除成功'
														});
													}
												}
											});
									grid.datagrid('reload');
								}
							});
		} else {
			$.messager.show({
				title : '警告',
				msg : '请先选择兴趣。'
			});
		}
	}
	function closeWindow() {
		win.window('close');
	}
</script>
</head>
<body>
	<table id="person-table" style="height: 500px"></table>

	<div id="person-window" title="兴趣信息维护"
		style="width: 420px; height: 300px;">
		<div style="padding: 20px 20px 40px 80px;">
			<form method="post">
				<table>
					<tr>
						<td>兴趣：</td>
						<td><input name="name"></input></td>
					</tr>
					<tr>
						<td>创建人：</td>
						<td><input name="createdBy" readonly="readonly"></input></td>
					</tr>
					<tr>
						<td>创建时间：</td>
						<td><input name="createdTime" readonly="readonly"></input></td>
					</tr>

					<tr>
						<td>最后修改人：</td>
						<td><input name="lastUpdatedBy" readonly="readonly"></input></td>
					</tr>
					<tr>
						<td>最后修改时间：</td>
						<td><input name="lastUpdatedTime" readonly="readonly"></input></td>
					</tr>
				</table>
			</form>
		</div>
		<div style="text-align: center; padding: 5px;">
			<a href="javascript:void(0)" onclick="savePerson()" id="btn-save"
				icon="icon-save">保存</a> <a href="javascript:void(0)"
				onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>
		</div>
	</div>
</body>
</html>