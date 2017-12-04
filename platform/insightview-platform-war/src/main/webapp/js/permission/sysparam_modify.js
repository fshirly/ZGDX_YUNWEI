$(function(){
	toUpdate();
})

/**
 * 初始化修改参数
 * 
 * @param roleId
 * @return
 */
function initUpdateVal(paramId) {
	resetForm('tblSysparamInfo');
	var path = getRootName();
	var uri = path + "/permissionSystemParam/findDepartmentByParamId?paramId="
			+ paramId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async : false,
		data : {
			"paramId" : paramId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
		}
	}
	ajax_(ajax_param);
}

/**
 * 去修改
 * 
 * @param paramId
 * @return
 */
function toUpdate() {
	var paramId = $('#paramId').val();
	initUpdateVal(paramId);
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		//console.log($('#iconFileName').val());
		//如果打开的是图片并且图片未被修改过，直接关闭，不保存。
		if(($("#ipt_paramName").val().indexOf("Icon")!=-1) && $("#iconFileName").val()==""){
			parent.$('#popWin').window('close');
		}else{
			doUpdate();
		}
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		//console.log('close');
		parent.$('#popWin').window('close');
		});
	var name = $('#ipt_paramName').val();
	if(name.indexOf("Icon")!=-1){
		$('#paramValue').hide();
		$('#uploadImg').show();
		$('#selectFile').show();
		if(name=="LoginIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/style/images/logo.png'");
			$("#userIcon").f_fileupload({
				imgWidth:250,
				imgHeight:50,
				filesize: 300,
				fileFormat: "['jpg','gif','png','bmp','jpeg']",
				viewImgSrcId:"scanImg"
			});
		}else if(name=="ProductSmallIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/style/images/logos.png'");
			$("#userIcon").f_fileupload({
				imgWidth:200,
				imgHeight:40,
				filesize: 300,
				fileFormat: "['jpg','gif','png','bmp','jpeg']",
				viewImgSrcId:"scanImg"
			});
		}else if(name=="LogoIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/favicon.ico'");
			$("#scanImg").attr("style","width:16px; height:16px;");
			$("#userIcon").f_fileupload({
				imgWidth:16,
				imgHeight:16,
				filesize: 300,
				fileFormat: "['ico']",
				viewImgSrcId:"scanImg"
			});
		}
	}else{
		$('#paramValue').show();
		$('#uploadImg').hide();
		$('#selectFile').hide();
	}
}

function doUpdate() {
	if (!checkInfo('#parForm')) {
			return false;
	}else{
		var path = getRootName();
		var uri = path + "/permissionSystemParam/updateSysParam";
		// alert($("#ipt_paramID").val()+" doupdate
		// "+$("#ipt_paramValue").val());
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"paramID" : $("#ipt_paramID").val(),
				"paramValue" : imgJudge(),
				"paramName" : $("#ipt_paramName").val() ,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					window.frames['component_2'].reloadTable();
					parent.$('#popWin').window('close');
				} else {
					$.messager.alert("提示", "系统运行参数修改失败！", "error");
					window.frames['component_2'].reloadTable();
				}
			}
		}
		ajax_(ajax_param);
	}
}

/**
 * 判断修改的条目是否是图片，如果是图片，返回上传后图片的路径值。
 */
function imgJudge(){
	var name = $("#ipt_paramName").val();
	if(name.indexOf("Icon")!=-1){
		return $('#iconFileName').val();
	}else{
		return $("#ipt_paramValue").val();
	}
}

