var _fileServerPath = "http://192.168.1.200:8089/FileBank/FileDir";

var _imgFileType = [ 'jpg', 'png', 'icon', 'gif' ];
var _docFileType = [ 'doc', 'docx', 'icon' ];
var _exlFileType = [ 'xls', 'xlsx' ];

/**
 * 上传图片验证
 * 
 * @author wul
 * @param file
 *            上传的图片
 * @param passfixArr
 *            支持的格式数组
 * @param fileSize
 *            允许上传文件 的最大KB数
 */
function commonChkFile(file, fileObj) {
	var passfixArr = fileObj.passfixArr;
	var fileSize = fileObj.fileSize;
	// 获取文件后缀
	var passfix = file.value.substring(file.value.lastIndexOf(".") + 1,
			file.value.length);

	var path = "../style/images/initupload.gif";
	// 判断文件后缀是否在允许的上传数组之内
	if (!passfix.wlIn(passfixArr + "")) {
		$("#" + fileObj.uploadBtnId).attr('disabled', true);
		$.messager.alert("提示", "对不起，系统仅支持" + passfixArr
				+ "的文件，请您调整格式后重新上传，谢谢 !", "info");
	}else{
		
		$("#" + fileObj.uploadBtnId).attr('disabled', false);
		if(fileObj.isPreview){
			commonPreviewImage(file, fileObj);
		}
	}
}

/**
 * 图片上传预览
 * 
 * @author wul
 * @param imgFile
 *            上传的图片
 * @param imgFile
 *            验证信息
 */
function commonPreviewImage(imgFile, fileObj) {
	var path = "../style/images/initupload.gif";
	var previewConId = fileObj.previewControlId;
	
	if (document.all)// IE
	{
		imgFile.select();
		path = document.selection.createRange().text;
		document.getElementById(previewConId).innerHTML = "";
		document.getElementById(previewConId).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
				+ path + "\")";// 使用滤镜效果
	} else// FF
	{
		path = window.URL.createObjectURL(imgFile.files[0]);
		document.getElementById(previewConId).innerHTML = "<img id='img1' width='120px' height='100px' src='"
				+ path + "'/>";
	}
}



var _filePathCommon;



function updateFileCommon(uploadObject) {
	var path = getRootName();
	var url = path + "/commonFileUpload/uploadFile";
	$.ajaxFileUpload({
		url : url,
		secureuri : false,
		fileElementId : uploadObject.elementId,
		dataType : 'json',
		success : function(data, status) {
			$("#" + uploadObject.uploadBtnId).attr('disabled', true);
			
			var dataJson = data.msg;
			dataJson = eval("(" + dataJson + ")");

			var fileName = dataJson.fileName;
			var fileDir = dataJson.fileDir;

			var filePathCommon = "//" + fileDir + "//" + fileName;
			
			var fileObj = new Object();
			fileObj.filePath = filePathCommon;
			fileObj.uploadTagId = uploadObject.uploadTagId;
			uploadCallBack(fileObj);
		},
		error : function(data, status, e) {
			return false;
		}
	});
}

function initDownLinkTag(tagId, fileHref) {
	if('' == fileHref || null == fileHref)
	{
		$("#" + tagId).attr("href", '#');
		$("#" + tagId).text('');
	}else{
		var path = getRootName();
		var relHref = path + '/commonFileUpload/CosDownload?fileDir=' + fileHref;
		var relFileName = construFileName(fileHref);
		relHref = relHref.replace(/['\/\/']/g, "\\");
		$("#" + tagId).attr("href", relHref);
		$("#" + tagId).text(relFileName);
	}
	
}

function construFileName(originalFileName) {
	var allFileName = originalFileName.split("//")[2],
		fileNamePrefix = allFileName.substring(0, allFileName.indexOf('.')),
		fileNameSuffix = allFileName.substring(allFileName.indexOf('.'));
	return fileNamePrefix.substring(0, fileNamePrefix.length - 24) + fileNameSuffix;
}

function commonPreviewFile(imgFile, fileObj) {

	if (!commonChkFileType(imgFile, fileObj)) {

	} else {

	}
}

function reUpdateFile(url, eleId, fileDir) {
	// alert("reUpdateFile");
	url = url + "?fileDir=" + fileDir + "";
	$.ajaxFileUpload({
		url : url,
		secureuri : false,
		fileElementId : eleId,
		dataType : 'json',
		success : function(data, status) {
			var dataJson = data.msg;
			dataJson = eval("(" + dataJson + ")");
			_fileName = dataJson.fileName;
			filepath = dataJson.filePath;
			// alert(dataJson.fileName);
			doUpdate();
		},
		error : function(data, status, e) {
			console.log(e);
		}
	});
}
