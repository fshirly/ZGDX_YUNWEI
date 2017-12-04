$(function(){
	toAdd();
	var isAdmin = $("#isAdmin").val();
	//console.log("isAdmin="+isAdmin);
	if(isAdmin == false || isAdmin == "false"){
		doChoseDeptGrade();
	}
	$("#sysUserTabs").tabs({
        border:false,
        onSelect:function(title){
    /*    	var tab = $('#sysUserTabs').tabs('getSelected');
            var index = $('#sysUserTabs').tabs('getTabIndex',tab);
            
        	if(index == 1) {//岗位信息
            	if($("#ipt_deptId").val() == '') {
            	   $.messager.alert("提示", "添加岗位前请先选择所属部门", "info");
            	   $("#sysUserTabs").tabs("select","用户信息");
            	} else {
            		if($("#deptIdOfPosts").val() == '' || $("#deptIdOfPosts").val() != $("#ipt_deptId").val()) {
                		toDivAllotPost($("#ipt_deptId").val());
        
                        $.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
                                "img_R_To_L", "img_R_AllTo_L");
                        $.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
                        
                        $("#deptIdOfPosts").val($("#ipt_deptId").val());
            		}
            	}
        	}*/
        }
    }); 
	initValue();
});

/**
 * 初始化数据，带入左侧树节点选中的信息
 */
function initValue(){
	$("#ipt_deptId").val($("#deptId").val());
	$('#ipt_deptName').val();
	//$("#ipt_providerId").val($("#providerId").val());
	//$('#ipt_providerName').val($("#providerName").val());
	//$("#ipt_providerName").attr("alt",$("#providerId").val());
	var dept_id=$("#deptId").val()==-1?null:$("#deptId").val();
	$("#ipt_deptName").combotree({
		url:getRootPatch()+"/permissionSysUser/findDeptAndOrgTreeValAjax",
		lines:true,
		panelWidth:400,
		panelHeight:300,
		onClick:function(node){
			toAsycDeptPost(node.id);
			$("#ipt_deptName").combotree("showPanel");
			$("#ipt_organizationID").val(node.attributes.organizationID);
			$("#ipt_deptId").val(node.attributes.deptId);
			
		}
	});
	//initradio($("#userType").val());
	//edit();
	//doInitGrade();
}

/*function doInitGrade(){
	var organizationId = $("#organizationId").val();
	var id = $("#ipt_deptId").val();
	var path = getRootPatch();
	var uri = path + "/permissionSysUser/findDeptGradeTreeVal?deptId="+id+"&organizationId="+organizationId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			$("#ipt_gradeID").empty();
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].gradeID;
				var _name = gtmdlToolList[i].gradeName;
				var _orgId=gtmdlToolList[i].organizationID;
				$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"'>"+_name+"</option>");     
			}
		}
	}
	ajax_(ajax_param);
}*/


/**
 * 根据左侧树节点初始化用户类型
 * @param userType
 */
/*function initradio(userType){
    var rObj = document.getElementsByName("userType");
    for(var i = 0;i < rObj.length;i++){
        if(rObj[i].value == userType){
            rObj[i].checked =  'checked';
        }
    }
}*/

/*
 * 打开新增div @author caoj
 */
var accountFlag=true;
function toAdd() {
//	resetForm('tblAddUser');
	var oTable = document.getElementById('tblAddUser');
	$("#employeeInfos").hide();
	$("#employeeGrade").hide();
	$("#providerInfos").hide();
	
	$(".inputVal").val("");
	$(':radio:checked').val("1");
	
//	edit();
    $('#ipt_userType').val($(':radio:checked').val());
    $("#employeeInfos").show();
    $("#employeeGrade").show();
    $("#providerInfos").hide();
        
}


/*
 * 新增用户 @author 武林
 */
function doAdd() {
	checkSysUser();
	
}


function checkFormInfo()
{	
	
/*	var radioChecked = $(':radio:checked');
	if(radioChecked.val()=='3'){
		$('#ipt_providerName').attr('validator',"{'default':'*'}");
		$('#ipt_employeeCode').removeAttr('validator');
		$('#ipt_deptName').removeAttr('validator');
	}else{
		$('#ipt_employeeCode').attr('validator',"{'default':'*'}");
		$('#ipt_deptName').attr('validator',"{'default':'*'}");
		$('#ipt_providerName').removeAttr('validator');
	}*/
	return checkInfo('#tblAddUser');
}


/**
 * 根据所选用户类型不一样，显示对应的编辑页面
 * @return
 */
/*function edit(){
	var oTable = document.getElementById('tblAddUser');
	var radioChecked = $(':radio:checked');
	if (radioChecked.val()=='3'){
		$('#ipt_userType').val(radioChecked.val());
		$("#employeeInfos").hide();
		$("#employeeGrade").hide();
		$("#providerInfos").show();
		
		$('#sysUserTabs').tabs('disableTab', 1);
	}else {
		$('#ipt_userType').val(radioChecked.val());
		$("#employeeInfos").show();
		$("#employeeGrade").show();
		$("#providerInfos").hide();
		
		$('#sysUserTabs').tabs('enableTab', 1);
	}
}*/


//修改时初始化职位等级
/*function doInitDeptGrade(){
	var deptId=$("#ipt_deptId").val();
	var path = getRootPatch();
	var uri = path + "/permissionSysUser/findDeptGradeTreeVal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"deptId":deptId, 
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].gradeID;
				var _name = gtmdlToolList[i].gradeName;
				var _orgId=gtmdlToolList[i].organizationID;
				if($("#ipt_gradeId").val()==_id){
					$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"' selected>"+_name+"</option>");  
				}else{
					$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"'>"+_name+"</option>");  
				}
				   
			}
		}
	}
	ajax_(ajax_param);
}*/


/*function doChoseProvider(){ 
	var path = getRootPatch();
	var uri = path + "/platform/provider/findProvideTreeVal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataProTree = new dTree("dataProTree", path + "/plugin/dTree/img/");
			dataProTree.add(0, -1, "供应商", "");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeDataPro = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].providerId;
				var _name = gtmdlToolList[i].providerName;
				var _parent = 0;
				dataProTree.add(_id, _parent, _name,
						"javascript:hiddenDTreeSetValEasyUi('divChoseProvider','ipt_providerName','"
						+ _id + "','" + _name + "');");
			}
			$('#dataTreeProviders').empty();
			$('#dataTreeProviders').append(dataProTree + "");
		$('#divChoseProvider').dialog('open');
		}
	}
	ajax_(ajax_param);
}*/
 
function doChoseDept(node){
	
	var id=node.id;
	var path = getRootPatch();
	//var isAdmin = $("#isAdmin").val();
	//if(isAdmin == true || isAdmin == "true"){
		var uri = path + "/permissionSysUser/findDeptAndOrgTreeValAjax";
	//}else{
		//var organizationId = $("#organizationId").val();
		//var uri = path + "/permissionSysUser/findDeptAndOrgTreeVal?organizationId="+organizationId;
	//}
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		/*data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},*/
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			
			
			var res = [{
			    "id":1,
			    "text":"Folder1",
			    "iconCls":"icon-save",
			    "children":[{
					"text":"File1",
					"checked":true
			    },{
					"text":"Books",
					"state":"open",
					"attributes":{
						"url":"/demo/book/abc",
						"price":100
					},
					"children":[{
						"text":"PhotoShop",
						"checked":true
					},{
						"id": 8,
						"text":"Sub Bookds",
						"state":"closed"
					}]
			    }]
			},{
			    "text":"Languages",
			    "state":"closed",
			    "children":[{
					"text":"Java"
			    },{
					"text":"C#"
			    }]
			}];
			
			
			
			
//			console.log(data);
			$("#dataTreeDepts").tree("loadData",data);
			/*deptDataTree = new dTree("deptDataTree", path + "/plugin/dTree/img/");
			deptDataTree.add(0, -1, "单位部门", "");
   
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].id;
				var _name = gtmdlToolList[i].name;
				var _parent = gtmdlToolList[i].parentId;
				if(_id.indexOf("O") == 0) {
					deptDataTree.add(_id, _parent, _name, "javascript:void(0);");
				} else {
					deptDataTree.add(_id, _parent, _name,  "javascript:hiddenGradeDTreeSetValEasyUi('divChoseDept','ipt_deptName','"
							+ _id + "','" + _name + "');");
				}
			}
			// dom操作div元素内容
			$('#dataTreeDepts').empty();
			$('#dataTreeDepts').append(deptDataTree + "");
			$('#divChoseDept').dialog('open');*/
			
//			// 得到树的json数据源
//			var jsonData = eval('(' + data.menuLstJson + ')');
//			// 遍历数据
//			var gtmdlToolList = jsonData;
//			for (var i = 0; i < gtmdlToolList.length; i++) {
//				var _id = gtmdlToolList[i].deptId;
//				var _name = gtmdlToolList[i].deptName;
//				var _parent = gtmdlToolList[i].parentDeptID;
//				
//				deptDataTree.add(_id, _parent, _name, "javascript:hiddenGradeDTreeSetValEasyUi('divChoseDept','ipt_deptName','"
//						+ _id + "','" + _name + "');");
//			}
//			$('#dataTreeDepts').empty();
//			$('#dataTreeDepts').append(deptDataTree + "");
			$('#divChoseDept').dialog('open');
			
		}
	}
	ajax_(ajax_param);
	
}

function hiddenGradeDTreeSetValEasyUi(divId, controlId, showId, showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	var deptId=$('#ipt_deptName').attr("alt");
	$("#ipt_deptId").val(deptId.substring(1,deptId.length));
	var isAdmin = $("#isAdmin").val();
	if(isAdmin == true || isAdmin == "true"){
		doChoseDeptGrade();
	}
}

function doChoseDeptGrade(){
	var isAdmin = $("#isAdmin").val();
	var organizationId = $("#organizationId").val();
	var path = getRootPatch();
	var deptId=$('#ipt_deptName').attr("alt");
	if((isAdmin == true || isAdmin == "true") && deptId!=undefined){
		$("#ipt_deptId").val(deptId.substring(1,deptId.length));
		var id = $("#ipt_deptId").val();
	}else{
		var id = -1;
	}
	var uri = path + "/permissionSysUser/findDeptGradeTreeVal?deptId="+id+"&organizationId="+organizationId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			$("#ipt_gradeID").empty();
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].gradeID;
				var _name = gtmdlToolList[i].gradeName;
				var _orgId=gtmdlToolList[i].organizationID;
				$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"'>"+_name+"</option>");     
			}
		}
	}
	ajax_(ajax_param);
}


//检查新密码与重复密码是否一致
function checkConfirmPwd(){
	var pwd=$("#ipt_userPassword").val();
	if(pwd=="" || pwd==null){
		$.messager.alert("提示", "用户密码不能为空！", "error");
	}else{
		var confirmPwd=$("#confirmPassword").val();
		if(confirmPwd != pwd){
			$.messager.alert("提示", "两次密码输入不一致，请重新输入！", "error");
			$("#confirmPassword").val("");
		}
	}
	
}

//判断用户名是否存在
function checkSysUser() {
		//var radioChecked = $(':radio:checked');
		var userAccount = $("#ipt_userAccount").val();
		var path = getRootName();
		var uri = path + "/permissionSysUser/checkSysUser";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"userAccount" : userAccount,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					accountFlag=true;
					$.messager.alert("提示", "该用户名已存在！", "error");
				} else {
					/*var valArr = "";
                    $("#selRight option").each(function() {
                        var val = $(this).val(); // 获取单个value
                        valArr += val + ",";
                    });*/
    
					accountFlag=false;
					var result = checkFormInfo();
					//var providerId=$("#ipt_providerName").attr("alt");
					//var gradeInfo=$("#ipt_gradeID").val();
					/*if(gradeInfo != null){
						var gradeId=gradeInfo.split("O")[0];
						$("#ipt_organizationID").val(gradeInfo.split("O")[1]);
					}*/
					
					var path = getRootName();
					var uri = path + "/permissionSysUser/addSysUser";
					var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
							"status" : 1,
							"userAccount" : $("#ipt_userAccount").val(),
							"userName" : $("#ipt_userName").val(),
							"userPassword" : $("#ipt_userPassword").val(),
							"mobilePhone" : $("#ipt_mobilePhone").val(),
							"email" : $("#ipt_email").val(),
							"iDCard": $("#ipt_idcard").val(),
							"telephone" : $("#ipt_telephone").val(),
							//"userType" : $("#ipt_userType").val(),
							"isAutoLock" : "1",
							"sysEmploymentBean.employeeCode":$("#ipt_employeeCode").val(),
							"sysEmploymentBean.organizationID":$("#ipt_organizationID").val(),
							"sysEmploymentBean.deptID":$("#ipt_deptId").val(),
							//"sysEmploymentBean.gradeID":gradeId,
							//"sysProviderUserBean.providerId":providerId,
							//"strPostList" : valArr,
							"t" : Math.random()
						},
						error : function() {
							$.messager.alert("错误", "ajax_error", "error");
						},
						success : function(data) {
							if (true == data || "true" == data) {
//								$.messager.alert("提示", "用户新增成功！", "info");
								$('#popWin').window('close');
								window.frames['component_2'].reloadTable();
								//window.frames['component_2'].initOrgTree();
							} else {
								$.messager.alert("提示", "用户新增失败！", "error");
							}
						}
					};
					if (result == true && accountFlag==false) {
						
						/*if($('#ipt_email').val() == ""){
							$.messager.alert("提示", "邮箱不能为空！", "info");
						}else *//*if($('#ipt_gradeID').val() == null && radioChecked.val()!='3'){
							$.messager.alert("提示", "职位级别不能为空！", "info");
						}else{*/
							ajax_(ajax_param);
						/*}*/
					}
				}
			}
		};
		ajax_(ajax_param);
}
/**
 * 异步加载子节点
 */
function toAsycDeptPost(id){
	var path = getRootName();
	var uri = path + "/permissionSysUser/findDeptAndOrgTreeValAjax";
	var ajax_param = {
	        url : uri,
	        type : "get",
	        datdType : "json",
	        data : {
	            "organizationId" : id,
	            "t" : Math.random()
	        },
	        error : function() {
	            $.messager.alert("错误", "ajax_error", "error");
	        },
	        success : function(bb) {
	        	var t = $('#ipt_deptName').combotree('tree');	// get the tree object
	        	var n = t.tree('getSelected');
	            t.tree("append",{'parent': n.target, 'data':bb})
	        }
	    };
	    ajax_(ajax_param);
}

/*
 * 分配岗位
 */
/*function toDivAllotPost(deptId) {
    $("#selLeft").empty();
    $("#selRight").empty();
    var path = getRootName();
    var uri = path + "/permissionSysUser/queryAllPost";
    var ajax_param = {
        url : uri,
        type : "post",
        datdType : "json",
        data : {
            "deptId" : deptId,
            "t" : Math.random()
        },
        error : function() {
            $.messager.alert("错误", "ajax_error", "error");
        },
        success : function(data) {
            for (var i = 0; i < data.allPostList.length; i++) {
                $("#selLeft").append(
                        "<option value='" + data.allPostList[i].postID + "'>"
                                + data.allPostList[i].postName + "</option>");
            }
            $('#divAllotRole').dialog('open');
        }
    };
    ajax_(ajax_param);
}*/
