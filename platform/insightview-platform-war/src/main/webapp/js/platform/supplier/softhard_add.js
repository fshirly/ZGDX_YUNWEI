var filepath = null;
var flag = false;
var uploadPath = '';
$(document).ready(function() {
	//alert(attachmentArrays);
	if(!flag) {
		$("#hshowFile").hide();
	}
	$('#proxyBeginTime').datebox({
		editable : false
	});
	$('#proxyEndTime').datebox({
		editable : false
	});
	$("#application_sfile").f_fileupload({
		whetherPreview : false,
		//fileFormat : "['doc', 'docx']",
		filesize : 5120,
		imgWidth:10000,
        imgHeight:10000,
		upLoadBtnId :"uploadBtn3",
		onFileUpload : function(path,name){
			//if(checkForm()) {
				initAttachmentArrays(path,name);
			//}
		}
	});
});

function checkForm() {
	return checkInfo('#tblEdit1');
}

function toAdd() {
	initAttachmentArrays('','');
}
/**
 * 组装附件缓存
 */
function initAttachmentArrays(path,name){
	//debugger;
	if(path == '') {
		if(!checkForm()) {
			return;
		}
	}
	var proxyName = $('#proxyName').val();
	var productType = $('#productType').combobox('getValue');
	var proxyFirmName = $('#proxyFirmName').val();
	var proxyBeginTime = $('#proxyBeginTime').datebox('getValue');
	var proxyEndTime = $('#proxyEndTime').datebox('getValue');
	var description = $('#description').val();
	var start = new Date(proxyBeginTime.replace("-", "/").replace("-", "/"));
	var end = new Date(proxyEndTime.replace("-", "/").replace("-", "/"));
	if(path != '') {
		uploadPath = path;
	}
	if(flag) {
		g_id--;
	}
	if (end <= start) {
		$.messager.alert('提示', '代理期限结束时间不能早于或等于开始时间', 'error');
	}else{
		var row={
			"id":g_id,
			"proxyName":proxyName,
			"productType":productType,
			"proxyFirmName":proxyFirmName,
			"proxyBeginTime":proxyBeginTime,
			"proxyEndTime":proxyEndTime,
			"description":description,
			"certificateUrl":uploadPath
		};
		g_id++;
		if(flag) {
			g_attachmentArrays.splice(-1,1);
		}
		if(path != '') {
			$("#shfilename").html(initDownLinkTag("shfilename",uploadPath));
			//$("#shfilename").html('<a id="downloadFile" title="下载文件" href="'+f.contextPath+'\\commonFileUpload\\CosDownload?fileDir='+uploadPath.replace(/\//g, "\\")+'">'+name+'</a>');
			$("#hshowFile").show();
			flag = true;
		}
		g_attachmentArrays.push(row);
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

