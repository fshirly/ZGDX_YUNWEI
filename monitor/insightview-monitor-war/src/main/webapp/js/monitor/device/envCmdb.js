window.onload=initSelVal();
function initSelVal(){
	var val = $("#type").val();
	if(val != "" && val != null){
		selResName();
	}
	
}

function selResName(){
	var val = $("#type").val();
	if(val != "" && val != null){
		doInitAssetType(val);
	}	
}
/**
 * 获取资产类型列表
 * @param val
 */
function doInitAssetType(val){
	var path = getRootPatch();
	
	var uri = path + "/monitor/envManager/listAssetName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"typeId" : val
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			var obj = document.getElementById("assetType");
			obj.options.length=0;
			$("#isAsset").val(data.isAsset);
			
			if(data.isAsset=="1"){
				$("#assetTypeLst").removeAttr("style");
				// 得到树的json数据源
				var datas = eval('(' + data.assetNameLstJson + ')');
				for (var i = 0; i < datas.length; i++) {
					var _id = datas[i].id;
					var _name = datas[i].name;
					obj.options.add(new Option(_name,_id));
				}
			}else{
				$("#assetTypeLst").css("display","none");
			}
			
		}
	}
	ajax_(ajax_param);	
}

function doConfig(){
	var val = $("#type").val();
	if(val=="null" || val=="" || val==null){
		$.messager.alert("提示", "资源归类不能为空！", "info");
		return false;
	}
	var isAsset=$("#isAsset").val();
	if(isAsset=="1"){
		var assetType=$("#assetType").val();
		if(assetType=="null" || assetType=="" || assetType==null){
			$.messager.alert("提示", "请先设置资产类型和资源类型关系！", "info");
			return false;
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/envManager/doCmdbInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"typeID" : $("#type").val(),					
			"moClassID" : $("#moClassID").val(),
			"assetTypeId" : $("#assetType").val(),
			"moids":$("#moids").val()	
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "CMDB配置成功！", "info");
				$('#popWin').window('close');
			} else {
				$.messager.alert("提示", "CMDB配置失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

