/*
 * 导入前验证
 */
function initAndCheckImport(imgFile) {
	var fileObj = new Object();
	fileObj.passfixArr = _exlFileType;
	fileObj.fileSize = "202400";
	fileObj.isPreview = false;
	fileObj.uploadBtnId = 'btnUpload';

	commonChkFile(imgFile, fileObj);
}
/*
 * 文件上传
 */
function doImportFile() {
	var flag = checkFile();
	if (flag == true) {
		document.getElementById("tipTr").style.display = "block";
		var importType = $('#importType').val();
		var path = getRootName();
		var beanName = "userImportor";
		if (importType == "2") {
			beanName = "deptImportor";
		}
		var url = path + '/platform/dataImportor/doImportData?beanName='+ beanName;
		$.ajaxFileUpload( {
			url : url,
			secureuri : false,
			fileElementId : 'ipt_importFile',
			dataType : 'content', //请求的返回类型不应是JSON,否则一直回调error.
			success : function(data, status) {
				showFailureInfo();
				//$.messager.alert("提示", "导入成功！", "info");
			},
			error : function(data, status, e) {
				showFailureInfo();
			}
		});
	}

}

// 判断是否选择文件
function checkFile() {
	var fileName = "";
	var path = $('#ipt_importFile').val();
	if (path != '') {
		fileName = getFileName(path);
		var ext = fileName.substring(fileName.lastIndexOf("."), fileName.length).toUpperCase();// 后缀名
		if (ext != '.XLS' && ext != '.XLSX') {
			$.messager.alert("提示", "对不起，系统仅支持xls,xlsx"
					+ "的文件，请您调整格式后重新上传，谢谢 !", "info");
			return false;
		} else {
			return true;
		}

	} else {
		$.messager.alert("提示", "请选择导入文件！", "info");
		return false;
	}
}

// 获取文件名
function getFileName(path) {
	var pos1 = path.lastIndexOf('/');
	var pos2 = path.lastIndexOf('\\');
	var pos = Math.max(pos1, pos2);
	if (pos < 0)
		return path;
	else
		return path.substring(pos + 1);
}

// 展示导入结果
function showFailureInfo() {
	//在门户中打开本页面时，它是属于跨域iframe的内容。
	if(~self.name.indexOf("dialog-")){
		self.$('#popWin').window( {
			title : '导入结果',
			width : 600,
			height : 300,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			href : getRootName() + '/platform/dataImportor/toResultInfo'
		});
	}else{
		parent.$('#popWin').window( {
			title : '导入结果',
			width : 600,
			height : 300,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			href : getRootName() + '/platform/dataImportor/toResultInfo'
		});
	}
}

function downloadTemplate() {
	var fileName = $('#templateName').val();
	var path = getRootName();
	var s = path + "/js/templatefile/" + fileName;
	window.location.href = s;

}