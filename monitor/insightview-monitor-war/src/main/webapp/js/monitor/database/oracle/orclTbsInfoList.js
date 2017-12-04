$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doTbsInitTables(moID,liInfo);//ORACLE 表空间信息列表
	$('#tblTbsInfo').datagrid('hideColumn','moId');
	});

// ORACLE 表空间信息列表
function doTbsInitTables(moID,liInfo) {
	var path = getRootName();
	$('#tblTbsInfo')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 550,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/orclManage/getOrclTbsInfo?moID='+moID,

						idField : 'fldId',
						columns : [ [
								{
									field : 'tbsname',
									title : '名称',
									width : 170,
									align : 'center',
									 formatter : function(value, row, index) {
							       	return '<a onclick="javascript:doOpenDetail('+ row.moid+ ');">'+value+"</a>";
							       }	
								},
								{
									field : 'tbsusage',
									title : '表空间使用率',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
									var color = (value <= 75) ? '#00ff00' : (value >= 90 ? '#ff0000' : '#FFFF33');
										return '<div class="easyui-progressbar" color="'
												+ color
												+ '" value="'
												+ value
												+ '" style="width:100px;height:15px;"></div>';
									}
								},
								{
									field : 'totalsize',
									title : '分配空间',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'freesize',
									title : '剩余空间',
									width : 120,
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
							//自适应部件大小
					 		window.parent.resizeWidgetByParams(liInfo);
						}
					});
	$(window).resize(function() {
	    $('#tblTbsInfo').resizeDataGrid(0, 0, 0, 0);
	});
}

$.fn.progressbar.methods.setColor = function(jq, color) {
	return jq.each(function() {
		var opts = $.data(this, 'progressbar').options;
		var color = color || opts.color || $(this).attr('color');
		$(this).css('border-color', color).find(
				'div.progressbar-value .progressbar-text').css(
				'background-color', color);
	});
}
function doOpenDetail(moId){
	//查看文件详情页面
	var isExistPop = parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.$('#popWin').window({
	    	title:'表空间详细信息',
	        width:800,
	        height:350,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclTbsDetail?moId='+moId
	    });
	}else{
		    parent.$('#popView').window({
	    	title:'表空间详细信息',
	        width:800,
	        height:350,
	        inline:true,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclTbsDetail?flag=1&moId='+moId
	    });
	}
//	var url = getRootName() + '/monitor/orclManage/toOrclTbsDetail?moId='+moId;
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}
