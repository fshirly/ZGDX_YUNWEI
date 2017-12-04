var filepath = null;
var flag = false;
var uploadPath = '';
$(document).ready(function() {
	if(!flag) {
		$("#hshowFile").hide();
	}
	$("#application_sfile").f_fileupload({
		whetherPreview : false,
		//fileFormat : "['doc', 'docx']",
		filesize : 5120,
		imgWidth:10000,
        imgHeight:10000,
		upLoadBtnId :"uploadBtn3",
		onFileUpload : function(path,name){
			//if(checkForm()) {
				//var index = objSH.id;
				//g_attachmentArrays.splice(index,1);
				initAttachmentArrays(path,name);
			//}
		}
	});
	$('#e_proxyBeginTime').datebox({
		editable : false
	});
	$('#e_proxyEndTime').datebox({
		editable : false
	});
	$('#e_productType').combobox();
	initRoomResDetail();
});

/*function beginTime() {
	return objSH.proxyBeginTime;
}

function endTime() {
	return objSH.proxyEndTime;
}*/
/*
 * 初始化信息
 */
function initRoomResDetail(){
	//console.log(objSH);
	$("#e_proxyName").val(objSH.proxyName);
	$('#e_productType').combobox('setValue', objSH.productType);
	$("#e_proxyFirmName").val(objSH.proxyFirmName);
	$('#e_proxyBeginTime').datebox('setValue',formatDateText2(new Date(objSH.proxyBeginTime)));
	$('#e_proxyEndTime').datebox('setValue',formatDateText2(new Date(objSH.proxyEndTime)));
	$("#e_description").val(objSH.description);
	//$("#e_certificateUrl").val(objSH.certificateUrl);
	if(objSH.certificateUrl) {
		$("#shfilename").html(initDownLinkTag("shfilename",objSH.certificateUrl));
		$("#hshowFile").show();
	}
}
	
function checkForm() {
	return checkInfo('#tblEdit1');
}

function toEdit() {
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
	debugger;
	var index = $('#proxy_info').datagrid('getRowIndex',objSH.id);
	var pageNum = $('#proxy_info').datagrid('options').pageNumber;
	var pageSize = $('#proxy_info').datagrid('options').pageSize;
	g_attachmentArrays.splice(index + pageSize * (pageNum - 1),1);
	if(path == '') {
		if(!checkForm()) {
			return;
		}
	}
	var proxyName = $('#e_proxyName').val();
	var productType = $('#e_productType').combobox('getValue');
	var proxyFirmName = $('#e_proxyFirmName').val();
	var proxyBeginTime = $('#e_proxyBeginTime').datebox('getValue');
	var proxyEndTime = $('#e_proxyEndTime').datebox('getValue');
	var description = $('#e_description').val();
	var start = new Date(proxyBeginTime.replace("-", "/").replace("-", "/"));
	var end = new Date(proxyEndTime.replace("-", "/").replace("-", "/"));
	if(path != '') {
		uploadPath = path;
	}
	if (end <= start) {
		$.messager.alert('提示', '代理期限结束时间不能早于或等于开始时间', 'error');
	}else{
		//g_id++;
		if(uploadPath == null || uploadPath == '') {
			uploadPath = objSH.certificateUrl;
		}
		var row={
			"id":objSH.id,
			"proxyId":objSH.proxyId,
			"proxyName":proxyName,
			"productType":productType,
			"proxyFirmName":proxyFirmName,
			"proxyBeginTime":proxyBeginTime,
			"proxyEndTime":proxyEndTime,
			"description":description,
			"certificateUrl":uploadPath
		};
		/*if(flag) {
			g_attachmentArrays.splice(-1,1);
		}*/
		if(path != '') {
			$("#shfilename").html(initDownLinkTag("shfilename",uploadPath));
			//$("#shfilename").html('<a id="downloadFile" title="下载文件" href="'+f.contextPath+'\\commonFileUpload\\CosDownload?fileDir='+uploadPath.replace(/\//g, "\\")+'">'+name+'</a>');
			$("#hshowFile").show();
			flag = true;
		}
		g_attachmentArrays.push(row);
		g_attachmentArrays.sort(function(a,b){return a.id-b.id});
		var gridDate={
			"total":g_attachmentArrays.length,
			"rows":g_attachmentArrays
		};
		if(path == '') {
			$('#proxy_info').datagrid({loadFilter:pagerFilter}).datagrid('loadData', gridDate);
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
