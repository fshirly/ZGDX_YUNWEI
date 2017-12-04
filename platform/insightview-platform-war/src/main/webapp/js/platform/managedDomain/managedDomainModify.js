$(function(){
	var domainId=$('#ipt_domainId').val();
	toUpdate(domainId);
});

/**
 * 修改管理域信息
 * @param userId
 * @return
 */
function toUpdate(domainId){
	$('#flag').val("edit");
	$('.input').val("");
	initUpdateVal(domainId);
}

function initUpdateVal(domainId){

	var path=getRootName();
	var uri=path+"/platform/managedDomain/initManagedDomainInfo";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"domainId":domainId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var parentName=$('#ipt_parentDomainName').val();
//				var parentId=$('#ipt_parentDomainName').attr("alt");
				var parentId = data.parentId;
				$('#ipt_parentDomainName').attr("alt",parentId)
				var orgId=$('#ipt_organizationId').val();
				$('#ipt_organizationName').attr("alt",orgId);
				if(parentName==null || parentName==""){
					$('#ipt_parentDomainName').val("根管理域");
					$('#ipt_parentDomainName').attr("alt",0);
				}
				isShow();
			}
		};
	ajax_(ajax_param);

}


function doUpdateDomain(){
	var domainId = $('#ipt_domainId').val();
	if(domainId == 1){
		$.messager.alert("提示","该管理域为系统自定义管理域，不能修改！","info");
	}else{
		var result=checkInfo('#divManagedDomainInfo');
		var orgId=$('#ipt_organizationName').attr("alt");
		var parentId=$("#ipt_parentDomainName").attr("alt");
		if(parentId=="" || parentId=="-1" || parentId==null || parentId=='0'){
			parentId=1;
		}
		if(parentId == domainId){
			$.messager.alert("提示","上级管理域不能为本身！","info");
			return;
		}
		var path=getRootName();
		var uri=path+"/platform/managedDomain/editManagedDomainInfo";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
			"domainId":$('#ipt_domainId').val(),
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
//			$.messager.alert("提示","修改成功！","info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
				window.frames['component_2'].initTree();
			}else{
				$.messager.alert("提示","修改失败！","error");
			}
		}
		};
		if(result==true){
			ajax_(ajax_param);
		}
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
					"javascript:hiddenDeptDTreeSetValEasyUi('divChoseDomain','ipt_parentDomainName','"
							+ _id + "','" + _name + "');");
		}
		$('#dataTreeDomains').empty();
		$('#dataTreeDomains').append(dataTreeChoose + "");
	$('#divChoseDomain').dialog('open');

}

function hiddenDeptDTreeSetValEasyUi(divId,controlId, showId,showVal) {
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
	if(parentDomain == null || parentDomain == ""){
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
