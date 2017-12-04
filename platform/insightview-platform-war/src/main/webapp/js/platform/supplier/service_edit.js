var filepath = null;
var flag = false;
var uploadPath = '';
$(document).ready(function() {
	if(!flag) {
		$("#showFile").hide();
	}
	$("#application_ffile").f_fileupload({
		whetherPreview : false,
		//fileFormat : "['doc', 'docx']",
		filesize : 5120,
		imgWidth:10000,
        imgHeight:10000,
		upLoadBtnId :"uploadBtn4",
		onFileUpload : function(path,name){
			//if(checkForm()) {
				//alert(path);
				//var index = objService.id;
				//gs_attachmentArrays.splice(index,1);
				initAttachmentArrays(path,name);
			//}
		}
	});
	$('#e_serviceBeginTime').datebox({
		editable : false
	});
	$('#e_serviceEndTime').datebox({
		editable : false
	});
	initServiceDetail();
});

/*function sbeginTime() {
	return objService.serviceBeginTime;
}

function sendTime() {
	return objService.serviceEndTime;
}*/

/*
 * 初始化信息
 */
function initServiceDetail(){
	//console.log(objService);
	$("#e_serviceName").val(objService.serviceName);
	$("#e_serviceFirmName").val(objService.serviceFirmName);
	$('#e_serviceBeginTime').datebox('setValue',formatDateText2(new Date(objService.serviceBeginTime)));
	$('#e_serviceEndTime').datebox('setValue',formatDateText2(new Date(objService.serviceEndTime)));
	$("#e_description").val(objService.description);
	//$("#e_certificateUrl").val(objService.certificateUrl);
	if(objService.certificateUrl) {
		$("#sfilename").html(initDownLinkTag("sfilename",objService.certificateUrl));
		$("#showFile").show();
	}
}

function checkForm() {
	return checkInfo('#tblEdit2');
}

function toSEdit() {
	//debugger;
	//if(checkForm()) {
		initAttachmentArrays('','');
	//} else {
	//	return;
	//}
}
/**
 * 组装附件缓存
 */
function initAttachmentArrays(path,name){
	//debugger;
	var index = $('#services_info').datagrid('getRowIndex',objService.id);
	var pageNum = $('#services_info').datagrid('options').pageNumber;
	var pageSize = $('#services_info').datagrid('options').pageSize;
	gs_attachmentArrays.splice(index + pageSize * (pageNum - 1),1);
	if(path == '') {
		if(!checkForm()) {
			return;
		}
	}
	var serviceName = $('#e_serviceName').val();
	var serviceFirmName = $('#e_serviceFirmName').val();
	var serviceBeginTime = $('#e_serviceBeginTime').datebox('getValue');
	var serviceEndTime = $('#e_serviceEndTime').datebox('getValue');
	var description = $('#e_description').val();
	var start = new Date(serviceBeginTime.replace("-", "/").replace("-", "/"));
	var end = new Date(serviceEndTime.replace("-", "/").replace("-", "/"));
	if(path != '') {
		uploadPath = path;
	}
	if (end <= start) {
		$.messager.alert('提示', '服务期限结束时间不能早于或等于开始时间', 'error');
	}else{
		//gs_id++;
		if(uploadPath == null || uploadPath == '') {
			uploadPath = objService.certificateUrl;
		}
		var row={
			"id":objService.id,
			"serviceId" : objService.serviceId,
			"serviceName":serviceName,
			"serviceFirmName":serviceFirmName,
			"serviceBeginTime":serviceBeginTime,
			"serviceEndTime":serviceEndTime,
			"description":description,
			"certificateUrl":uploadPath
		};
		if(path != '') {
			$("#sfilename").html(initDownLinkTag("sfilename",uploadPath));
			//$("#sfilename").html('<a id="downloadFile" title="下载文件" href="'+f.contextPath+'\\commonFileUpload\\CosDownload?fileDir='+uploadPath.replace(/\//g, "\\")+'">'+name+'</a>');
			$("#showFile").show();
			flag = true;
		}
		gs_attachmentArrays.push(row);
		gs_attachmentArrays.sort(function(a,b){return a.id-b.id});
		var gridDate={
			"total":gs_attachmentArrays.length,
			"rows":gs_attachmentArrays
		};
		if(path == '') {
			$('#services_info').datagrid({loadFilter:pagerFilter}).datagrid('loadData', gridDate);
			$("#downloadFile").hide();
			$('#popWin2').window('close');
		}
	}
}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}
