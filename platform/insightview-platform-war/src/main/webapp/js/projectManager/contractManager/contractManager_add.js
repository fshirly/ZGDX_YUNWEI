/*
 * 增加命名空间
 */
f.namespace('platform.contractManager');
platform.contractManager.contractManagerAdd = (function(){
/*
 * 绑定事件
 */
f('#contractManageradd').click(contractMangerInfo_add);
f('#contractManageraddresert').click(contractManager_resert);

var contractManagerAdd={
    doContractFileDel:function(id){
	        doContractFileDel(id);
    }
};
var fileCachearrat=[];
var id = 1;
$(function() {
	var path = getRootName();
	 /*
	  * 合同文件上传
	  */
	 $('#contractfile_url').f_fileupload({
			whetherPreview : false,
			//fileFormat : "['doc', 'docx', 'pdf', 'jpg', 'png', 'gif', 'rar', 'zip', 'txt']",
			filesize : 100000,
			repeatUpload : true,
			imgWidth: 10000,
			imgHeight: 10000,
			showFile : false,
			onFileUpload : function(path,name){
				initAttachmentArray(path,name);
			}
		});
	 /*
	  * 关联项目下拉框
	  */
	 $('#ipt_projectId').combobox({
			valueField : 'id',
			textField : 'neir',
			url: path+'/contractmanager/projectcombox',
			editable : false,
			panelWidth:180
		});
	 /*
	  * 合同类型下来框
	  */
	 $('#ipt_contractType').combobox({
			valueField : 'id',
			textField : 'neir',
			url: path+'/contractmanager/contracttypecombox',
			editable : false,
			panelWidth:180
		});
	 contractFile();
});
/**
 * 组装附件缓存
 */
function initAttachmentArray(path,name){
	var row={
			"id":id,
			"url":path,
			"fileName":name
		};
	fileCachearrat.push(row);
	id++;
	var gridDate={
			"total":fileCachearrat.length,
			"rows":fileCachearrat
			};
	$('#contractfile').datagrid('loadData',gridDate);
	reloadTableCommon('contractfile');
}
/**
 *删除附件缓存
 * 
 */
function doContractFileDel(index){
	//var index = $('#contractfile').datagrid('getRowIndex',id);
	fileCachearrat.splice(index,1);
	var gridDate={
			"total":fileCachearrat.length,
			"rows":fileCachearrat
			};
	$('#contractfile').datagrid('loadData',gridDate);
	reloadTableCommon('contractfile');
}
/**
 * 多文件上传
 * @return
 */
function contractFile(){
	var path = getRootName();
	var uri='';
	$('#contractfile').datagrid({
		width : 'auto',
		height : 130,
		width:'auto',
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		fitColumns:true,
		url : uri,
		remoteSort : true,
		//pageSize:5,
		//pageList:[5,10,15,20],
		idField : 'id',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : false,// 分页控件
		rownumbers : true,// 行号
		columns : [ [
		   {
			 field : 'url',
		     title : '附件名称',
			 width : 50,
			 align:'center',
			 formatter : function(value, row, index) {
					var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir='+ value;
					href = href.replace(/['\/\/']/g, "\\");
					return '<a title="下载文件" class="easyui-tooltip"  href="' + href + '">' + row.fileName + '</a>';
				}
		   },{
				field : 'providerIDs',
				title : '操作',
				width : 50,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerAdd.doContractFileDel('
						+ index
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} 
	    ]]
		
	});
}
/*
 * 提交信息
 */
function contractMangerInfo_add(){
	
	var  validTimeEnd_temp=$('#ipt_validTimeEnd').datebox('getValue');
	var  signTime_temp=$('#ipt_signTime').datebox('getValue');
	var  validTimeBegin_temp=$('#ipt_validTimeBegin').datebox('getValue');
	var  validTimeEnd_date=parseISO8601(validTimeEnd_temp);
	var  signTime_date=parseISO8601(signTime_temp);
	var  validTimeBegin_date=parseISO8601(validTimeBegin_temp);
	if(checkInfo('#editcontractManager')){
		if(signTime_date>validTimeBegin_date){
			$.messager.alert("错误", "有效期开始时间不能在签订时间之前！", "error");
		    $('#ipt_validTimeBegin').datebox('setValue','');
		}else if(validTimeEnd_date<validTimeBegin_date){
			$.messager.alert("错误", "有效期结束时间不能在开始时间之前！", "error");
		    $('#ipt_validTimeEnd').datebox('setValue','');
		}else{
			var path = getRootName();
			var	uri =path+"/contractmanager/contractmanagerinfoadd";
			var	data={
				"title" : $("#ipt_title").val(),//合同标题
				"signTime" : $("#ipt_signTime").datebox("getValue") ,//签订日期
				"contractNo" : $("#ipt_contractNo").val(),//合同编号
				"validTimeBegin" : $("#ipt_validTimeBegin").datebox("getValue"),//合同有效期_开始
				"validTimeEnd":$("#ipt_validTimeEnd").datebox("getValue"),//合同有效期_结束
				"owner" : $("#ipt_owner").val(),//甲方
				"partyB" : $("#ipt_partyB").val(),//乙方
				"cashDeposit" : $("#ipt_cashDeposit").val(),//保证金
				"moneyAmount" : $("#ipt_moneyAmount").val(),//合同金额
				"projectId" : $("#ipt_projectId").combobox("getValue"),
				"contractSummary" : $("#ipt_contractSummary").val(),
				"contractType":$("#ipt_contractType").combobox("getValue"),
				"responserName" :$("#ipt_responserName").val(),
				"fileCachearrat":JSON.stringify(fileCachearrat),
				"t" : Math.random()	
				};
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data :data,
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							parent.$('#popWin').dialog('close');
							window.frames['component_2'].platform.contractManager.contractManagerList.reloadTable();
							resetForm("editcontractManager");
						} else {
							$.messager.alert("提示", "合同信息添加失败！", "error");
						}
	
					}
				};
			   ajax_(ajax_param);
		  }
	   }
	}

/*
 * 信息重置
 */
function contractManager_resert(){
	
	resetForm("editcontractManager");	
}

return contractManagerAdd;
})();