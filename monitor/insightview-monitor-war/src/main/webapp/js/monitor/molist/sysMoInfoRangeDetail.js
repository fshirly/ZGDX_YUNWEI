var path = getRootName();
$(document).ready(function() {
	initRangeInfo();
	$("#deviceConfigRangeDiv").hide();
	$("#othersConfigRangeDiv").hide();
});


//初始化配置数据
function initRangeInfo(){
	var mid = $("#ipt_mid").val();
//	console.log(mid);
	var uri=path+"/monitor/sysMo/getMoBaseInfoByMid";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"mid":mid,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data != null){
			if(data.monitorProperty == 0){
				$("#deviceConfigRangeDiv").show();
				doInitTable($("#ipt_mid").val());
				$("#othersConfigRangeDiv").hide();
			}else{
				$("#deviceConfigRangeDiv").hide();
				$("#othersConfigRangeDiv").show();
				var uri=path+"/monitor/sysMo/getMoClassRelationInfoByMid";
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"json",
						data:{
							"mid":mid,
							"t" : Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data != null){
						if(data.moClassLable == null){
							$("#lbl_moClassID").text("");
						}else{
							$("#lbl_moClassID").text(data.moClassLable);
						}
					}
				}
				};
				ajax_(ajax_param);
			}
		}
	}
	};
	ajax_(ajax_param);
}

function doInitTable(mid){
	var path = getRootName();
	$('#tblSysMoManufactureList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 250,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/sysMo/listSysManufactureByMid?mid='+mid,
						remoteSort : false,
						idField : 'id',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						rownumbers : true,// 行号
						
						columns : [ [
								{
									field : 'resManufacturerName',
									title : '厂商',
									width : 80,
									align : 'center',
								},
								{
									field : 'resCategoryName',
									title : '型号',
									width : 80,
									align : 'center'
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysMoManufactureList').resizeDataGrid(0, 0, 0, 0);
    });

}


