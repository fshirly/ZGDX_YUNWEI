var filepath = null;
var flag = false;
var uploadPath = '';
$(document).ready(function() {
	//alert(attachmentArrays);
	if(!flag) {
		$("#showFile").hide();
	}
	$('#serviceBeginTime').datebox({
		editable : false
	});
	$('#serviceEndTime').datebox({
		editable : false
	});
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
				initAttachmentArrays(path,name);
			//}
		}
	});
});

function checkForm() {
	return checkInfo('#tblEdit2');
}

function toSAdd() {
	initAttachmentArrays('','');
}
/**
 * 组装附件缓存
 */
function initAttachmentArrays(path,name){
	if(path == '') {
		if(!checkForm()) {
			return;
		}
	}
	var serviceName = $('#serviceName').val();
	var serviceFirmName = $('#serviceFirmName').val();
	var serviceBeginTime = $('#serviceBeginTime').datebox('getValue');
	var serviceEndTime = $('#serviceEndTime').datebox('getValue');
	var description = $('#fw_description').val();
	var start = new Date(serviceBeginTime.replace("-", "/").replace("-", "/"));
	var end = new Date(serviceEndTime.replace("-", "/").replace("-", "/"));
	if(path != '') {
		uploadPath = path;
	}
	if(flag) {
		gs_id--;
	}
	if (end <= start) {
		$.messager.alert('提示', '服务期限结束时间不能早于或等于开始时间', 'error');
	}else{	
		var row={
			"id":gs_id,
			"serviceName":serviceName,
			"serviceFirmName":serviceFirmName,
			"serviceBeginTime":serviceBeginTime,
			"serviceEndTime":serviceEndTime,
			"description":description,
			"certificateUrl":uploadPath
		};
		gs_id++;
		if(flag) {
			gs_attachmentArrays.splice(-1,1);
		}
		if(path != '') {
			$("#sfilename").html(initDownLinkTag("sfilename",uploadPath));
			//$("#sfilename").html('<a id="downloadFile" title="下载文件" href="'+f.contextPath+'\\commonFileUpload\\CosDownload?fileDir='+uploadPath.replace(/\//g, "\\")+'">'+name+'</a>');
			$("#showFile").show();
			flag = true;
		}
		gs_attachmentArrays.push(row);
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