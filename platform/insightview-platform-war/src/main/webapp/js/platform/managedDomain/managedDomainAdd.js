$(function(){
	$("#ipt_parentId").val(window.frames['component_2']._currentNodeId);
	$("#ipt_parentDomainName").val(window.frames['component_2']._currentNodeName);
	$("#ipt_parentDomainName").attr("alt",window.frames['component_2']._currentNodeId);
	toAdd();
});

/**
 * 新增管理域
 * @return
 */
function toAdd(){
	$('#flag').val("add");
	$('.input').val("");
	var parentId=$("#ipt_parentId").val();
	if(parentId==""){
		parentId=0;
		$("#ipt_parentDomainName").val("根管理域");
	}
	 isShow();
}

/**
 * 新增
 * @return
 */
function doAddManagedDomain(){
	var result=checkInfo('#divManagedDomainInfo');
	var orgId=$('#ipt_organizationName').attr("alt");
	var parentId=$("#ipt_parentDomainName").attr("alt");
	if(parentId=="" || parentId==null || parentId=='-1' || parentId=='0'){
		parentId=1;
	}
	var path=getRootName();
	var uri=path+"/platform/managedDomain/addManagedDomain";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"domainName":$("#ipt_domainName").val(),
				"domainAlias" : $("#ipt_domainAlias").val(),
				"organizationId":orgId,
				"domainDescr" : $("#ipt_domainDescr").val(),
				"parentId":parentId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
//			$.messager.alert("提示","管理域新增成功！","info");
			$('#popWin').window('close');
			window.frames['component_2'].reloadTable();
			window.frames['component_2'].initTree();
		}else{
			$.messager.alert("提示","管理域新增失败！","error");
		}
	}
	};
	if(result==true){
		ajax_(ajax_param);
	}
}


/**
 * 选择上级管理域
 * @param parentId
 * @return
 */
function doChoseParentDomain(){
		var path = getRootPatch();
		dataTreeChoose = new dTree("dataTreeChoose", path + "/plugin/dTree/img/");
//		dataTreeChoose.add(0, -1, "根管理域", "");
		// 得到树的json数据源
		var datas = window.frames['component_2']._treeData;

		// 遍历数据
		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].domainId;
			var _name = gtmdlToolList[i].domainName;
			var _parent = gtmdlToolList[i].parentId;

			dataTreeChoose.add(_id, _parent, _name,
					"javascript:hiddenDomainDTreeSetValEasyUi('divChoseDomain','ipt_parentDomainName','"
							+ _id + "','" + _name + "');");
		}
		$('#dataTreeDomains').empty();
		$('#dataTreeDomains').append(dataTreeChoose + "");
	$('#divChoseDomain').dialog('open');

}

function hiddenDomainDTreeSetValEasyUi(divId,controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
//	console.log("tree.....");
	isShow();
}

/**
 * 管理域名称不能重复
 * @param domainName
 * @return
 */
function checkDomainName(){
	var flag=$('#flag').val();
	var domainName=$('#ipt_domainName').val();
	var path=getRootName();
	var uri=path+"/platform/managedDomain/checkDomainName?flag="+flag;
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"domainName":domainName,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data==true){
					$.messager.alert("提示", "该管理域名已存在！", "error");
					$('#ipt_domainName').val("");
					$('#ipt_domainName').focus();
				}else{
					return;
				}
			}
		};
	ajax_(ajax_param);

}

function doChoseParentOrg() {
	cancelRedBox("ipt_organizationName");
	var _treeOpenCount=window.frames['component_2']._treeOpenCount;
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0, -1, "选择所属单位", "");
		// 得到树的json数据源
		var datas = window.frames['component_2']._orgTreeData;

		// 遍历数据
		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].organizationID;
			var _nameTemp = gtmdlToolList[i].organizationName;
			var _parent = gtmdlToolList[i].parentOrgID;

			dataTreeTwo.add(_id, _parent, _nameTemp,
					"javascript:hiddenDTreeSetValEasyUi('divChoseOrg','ipt_organizationName','"
							+ _id + "','" + _nameTemp + "');");
		}
		$('#dataTreeDivs').empty();
		$('#dataTreeDivs').append(dataTreeTwo + "");
	}
	$('#divChoseOrg').dialog('open');
}

/**
 * 是否显示清空
 * @return
 */
function isShow(){
	var parentDomain =$("#ipt_parentDomainName").val();
//	console.log("parentDomain==="+parentDomain);
	if(parentDomain == null || parentDomain == "" || parentDomain == "-1"){
		$("#isShow").hide();
	}else{
		$("#isShow").show();
	}
}

/**
 * 清空上级部门
 * @return
 */
function clear(){
	$("#ipt_parentDomainName").val("");
	$("#ipt_parentDomainName").attr("alt", "1");
	$("#isShow").hide();
}