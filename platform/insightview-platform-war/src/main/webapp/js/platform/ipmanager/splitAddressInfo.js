$(document).ready(
	function() {
		doPreviewSplitSubnet();
	}
);

function doPreviewSplitSubnet() {
	var splitNum = $("#splitNum").val();
	var ipAddress = $("#ipAddress").val();
	var subNetMark = $("#subNetMark").val();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/doPreviewSplitSubnet?splitNum="+splitNum+"&ipAddress="+ipAddress+"&subNetMark="+subNetMark;
	var path = getRootName();
	$('#tblSplitIpList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 280,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : uri,
						remoteSort : false,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
//						pagination : true,// 分页控件
//						rownumbers : true,// 行号
						columns : [ [
								
								{
									field : 'netAddress',
									title : '网络地址',
									align : 'center',
									width : 100,
								},
								{
									field : 'subNetMark',
									title : '子网掩码',
									align : 'center',
									width : 100,
								},
								
//								{
//									field : 'isNetAddressFreeStr',
//									title : '是否空闲',
//									align : 'center',
//									width : 40,
//									formatter:function(value,row){
//										var rtn = "是";
//										if(value==1 || value=="1"){
//											rtn = "是";
//										}else{
//											rtn = "否";
//										}
//										return rtn;
//									}  
//								},
								{
									field : 'broadCast',
									title : '广播地址',
									align : 'center',
									width : 100,
								},
//								{
//									field : 'isBroadCastFreeStr',
//									title : '是否空闲',
//									align : 'center',
//									width : 40,
//									formatter:function(value,row){
//									var rtn = "是";
//									if(value==1 || value=="1"){
//										rtn = "是";
//									}else{
//										rtn = "否";
//									}
//									return rtn;
//								}  
//								}
					] ]
					});
//	 $(window).resize(function() {
//	        $('#tblSplitIpList').resizeDataGrid(0, 0, 0, 0);
//	    });
}

/**
 * 更新表格
 */
function reloadTable(){
	$('#tblSplitIpList').datagrid('load');
	$('#tblSplitIpList').datagrid('uncheckAll');
}

/**
 * 取消
 */
function cancle(){
	$('#popWin2').window('close');
	$('#popWin').window('close');
}

/**
 * 验证是否可以拆分
 */
function checkIsSplit(){
	var item = $('#tblSplitIpList').datagrid('getRows');
	var length= item.length;
//	console.log(item);
//	console.log(length);
	for ( var i = 0; i < length; i++) {
		var netAddress=item[i]['netAddress'];
		var isNetAddressFree=item[i]['isNetAddressFree'];
		var broadCast=item[i]['broadCast'];
		var isBroadCastFree	=item[i]['isBroadCastFree'];
//		console.log(">>>"+i);
		if(i != 0){
//			console.log(i+"---"+netAddress+"--"+isNetAddressFree);
			if(isNetAddressFree == 3 || isNetAddressFree == "3"){
				return false;
			}
		}
		if(i != length - 1){
//			console.log(i+"---"+broadCast+"--"+isBroadCastFree);
			if(isBroadCastFree == 3 || isBroadCastFree == "3"){
				return false;
			}
		}
	}
	return true;
}

function toSplit(){
	var isDisabled = document.getElementById('btnSplit').disabled;
//	console.log("isDisabled="+isDisabled);
	if(isDisabled == false || isDisabled ==undefined){
		var result = checkIsSplit();
		document.getElementById('btnSplit').disabled=true;
//		console.log(">>>>"+document.getElementById('btnSplit').disabled);
//		console.log("result ==="+result);
		if(result == true){
			var subNetId = $("#subNetId").val();
			var splitNum = $("#splitNum").val();
			var ipAddress = $("#ipAddress").val();
			var subNetMark = $("#subNetMark").val();
			var path = getRootName();
			var uri = path + "/platform/subnetViewManager/doSplit?splitNum="+splitNum+"&ipAddress="+ipAddress+"&subNetId="+subNetId+"&subNetMark="+subNetMark;
			var ajax_param = {
					url : uri,
					type : "post",
//			async : true,
					datdType : "json",
					data : {
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
				document.getElementById('btnSplit').disabled=false;
			},
			success : function(data) {
				if (data == true) {
					$.messager.alert("提示", "拆分子网成功！", "info");
					$('#popWin').window('close');
					$('#popWin2').window('close');
					window.frames['component_2'].frames['component_2'].reloadTable();
					window.frames['component_2'].initTree();
//					document.getElementById('btnSplit').disabled=true;
				} else {
					$.messager.alert("提示", "拆分子网失败！", "error");
					document.getElementById('btnSplit').disabled=false;
				}
			}
			};
			ajax_(ajax_param);
		}else{
			$.messager.alert("提示", "新产生的网络地址或广播地址中存在非空闲地址，请空出再进行拆分！", "info");
			document.getElementById('btnSplit').disabled=false;
		}
	}
}


function toCopy(){
	var subNetId = $("#subNetId").val();
	var splitNum = $("#splitNum").val();
	var ipAddress = $("#ipAddress").val();
	var subNetMark = $("#subNetMark").val();
	$("#iptColName").val("netAddress"+ ","+"subNetMark"+ ","+"broadCast"+ ",");
	$("#iptTitleName").val("网络地址"+ ","+"子网掩码"+ ","+"广播地址"+ ",");
	$("#condition").val(subNetId+";"+splitNum+";"+ipAddress+";"+subNetMark);
	$("#frmExport").submit();
}