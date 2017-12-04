$(function(){
	
	/**
	 * 初始化列表
	 */
	doInitTable();
	
	/**
	 * 初始化类别列表
	 */
	$("#typeId").combobox({
		url : getRootName() + '/dict/readItems?id=100115',
		valueField : 'key',
		textField : 'val',
		editable : false,
		panelWidth : 130
	});
	
	/**
	 * 查询点击事件
	 */
	$('#queryBtn').click(function(){
		var userName = $("#userName").val();
		var clientNo = $("#clientNo").val();
		var typeId = $("#typeId").combobox('getValue');
		var firstTime = $("#firstTime").datebox('getValue');
		var lastTime = $("#lastTime").datebox('getValue');
		$('#tblIllegalEventList').datagrid('options').queryParams = {
			"userName" : userName,
			"firstTime" : firstTime,
			"lastTime" : lastTime,
			"clientNo" : clientNo,
			"typeId" : typeId
		};
		tableReload();
	})

	/**
	 * 重置点击事件
	 */
	$('#resetBtn').click(function(){
		$("#userName").val('');
		$("#clientNo").val('');
		$("#typeId").combobox('setValue','');
		$("#firstTime").datebox('setValue','');
		$("#lastTime").datebox('setValue','');
	})
	
	/**
	 * 时间初始化
	 */
	$("#firstTime").datebox({
		formatter : function(date){
			return formatDate(date ,"yyyy-MM-dd");
		} ,
		editable :false
	});
	
	$("#lastTime").datebox({
		formatter : function(date){
			return formatDate(date ,"yyyy-MM-dd");
		} ,
		editable :false
	});
	
})


/**
 * 初始化加载列表
 */
function doInitTable() {
	var path = getRootName();
	$('#tblIllegalEventList').datagrid(
			{
				iconCls : 'icon-edit',// 图标
				width : 'auto',
				height : 'auto',
				fitColumns:true,
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,// 是否可折叠的
				fit : true,// 自动大小
				url : path + '/monitor/illegalEvnet/queryIllegalEventList',
				sortOrder: 'desc',
				remoteSort : false,
				idField : 'id',
				singleSelect : true,// 是否单选
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,// 分页控件
				rownumbers : true,
				columns : [ [
				             {
				            	 field : 'userName',
				            	 title : '用户名称',
				            	 align : 'center',
				            	 width : 120,
				             },
				             {
				            	 field : 'orgName',
				            	 title : '所属机构',
				            	 align : 'center',
				            	 width : 120,
				             },
				             {
				            	 field : 'clientNo',
				            	 title : '终端编号',
				            	 align : 'center',
				            	 width : 120,
				             },
				             {
				            	 field : 'certSerial',
				            	 title : '证书序列号',
				            	 align : 'center',
				            	 width : 120,
				             },
				             {
				            	 field : 'typeId',
				            	 title : '类别',
				            	 width : 120,
				            	 align : 'center',
				            	 formatter : function(value, row, index) {
				            		 if (value === 2002) {
				            			 return '软件主动卸载';
				            		 } else if(value === 2003){
				            			 return '非法连接互联网';
				            		 } else if(value == 2004){
				            			 return '使用非归类软件';
				            		 } else if(value == 2005){
				            			 return '外设接口控制';
				            		 } else if (value == 2008){
				            			 return '防止使用下载软件';
				            		 } else if(value == 2009){
				            			 return '防止使用聊天软件';
				            		 } else if(value == 2010){
				            			 return '防止使用炒股软件';
				            		 } else if(value == 2011){
				            			 return '防止使用游戏软件';
				            		 } else if(value == 2012){
				            			 return '防止使用音频软件';
				            		 } else if(value == 2013){
				            			 return '防止使用视频软件';
				            		 } else if(value == 2017){
				            			 return '软件白名单';
				            		 } else if(value == 2019){
				            			 return '软件红名单';
				            		 } else if(value == 5001){
				            			 return '收集流量信息';
				            		 } else if(value == 5002){
				            			 return '收集电量信息';
				            		 } else {
				            			 return '';
				            		 }
				            	 }
				             },
				             {
				            	 field : 'firstTime',
				            	 title : '发生时间',
				            	 align : 'center',
				            	 width : 120,
								 formatter : function(value, row, index) {
									 return formatDate(new Date(row.firstTime), "yyyy-MM-dd");
									 }
				             },
				             {
				            	 field : 'lastTime',
				            	 title : '更新时间',
				            	 align : 'center',
				            	 width : 120,
								 formatter : function(value, row, index) {
									 return formatDate(new Date(row.lastTime), "yyyy-MM-dd");
									 }
				             },
				             {
				            	 field : 'memo',
				            	 title : '描述',
				            	 align : 'center',
				            	 width : 120,
				             },
				             {
				            	 field : 'repeatTimes',
				            	 title : '违规次数',
				            	 align : 'center',
				            	 width : 120,
				             }
				             ] ]
			});
}


/**
 * 重载表格
 *
 */
function tableReload(){
	$('#tblIllegalEventList').datagrid('reload');
	$('#tblIllegalEventList').datagrid('uncheckAll');
	$('#tblIllegalEventList').datagrid('unselectAll');
}