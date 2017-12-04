var opt = null;
;(function () {
    var _default_config = {
        title: "选择人员",
        width: 700,
        height: 520,
        queryId: "queryId",
        url : getRootName() + '/permissionSysUser/queryUserByconditions'
    };
    $.fn.f_selectPerson = function (options) {
    	$(this).bind('click',function(){
    		opt = options;
    		if(typeof options.getUserIds==='function'){
    			options.userIds = options.getUserIds();
    		}else{
    			options.userIds = '';
    		}
    		if (typeof options === "object") {
                _createPop.call(this, options);
                if(options.selectType=="more"){
                	getData_more.call(this,options);
                }else{
                	getData.call(this, options);
                }
            } else {
                $.messager.alert('提示', '参数为object', 'info');
            }
    	});
        
    };
    var _createPop = function (options) {
    	if(options.orgId==undefined){
    		options.orgId = "";
    	}
    	if(options.userId==undefined){
    		options.userId = "";
    	}
    	if(options.userIds == undefined){
    		options.userIds = "";
    	}
    	var tableId = '';
    	if(options.selectType=='more'){
    		tableId = 'selUser_datagrid_more';
    	}else{
    		tableId = 'selUser_datagrid';
    	}
        var config = $.extend(true, {}, _default_config, options || {}),
            self = this,
            popWinDiv = $("<div id='popWinDiv'></div>"),
            orgIds = config.orgId==''?-1:config.orgId,
            sureBtn = $("<a href='javascript:void(0)' onclick='checkMoreUser()'>确定</a>");
        	insertDiv = "<div><div  class='conditions'><table><tr>" +
            "<td><b>员工编码：</b> <input type='text' id='user_no' name='userNo' /></td>" +
            "<td style='width:250px'><b style='margin-left:9px'>姓名：</b><input type='text' id='user_name_select' name='name'></td>" +
            "</tr><tr><td><b>用户类型：</b>" +
            "<select id='user_Type_select' name='userType'>" +
            "<option value='1'>内部员工</option>" +
            "<option value='2'>供应商用户</option>" +
            "</select></td>" +
            "<td><b>部门/供应商：</b><span id='dept_show'><input type='text' name='dept' id='user_department_select' style='height:24px;line-height:24px;width:134px'></span>" +
            "<span style='display:none' id='provider_show'><input type='text' name='user_provider' id='user_provider_select'></span></td>" +
            "<td class='btntd'><a onclick='select_query(\""+tableId+"\",\""+config.userIds+"\","+orgIds+");' id='" + config.queryId + "'>查询</a>" +
            "<a onclick='select_reset();'>重置</a></td>" +
            "</tr></table></div>" +
            "<div class='datas tops2'><table  id='"+tableId+"'></table></div>" +
            "<div class='conditionsBtn' id='conditionsBtn'><a href='javascript:void(0);' onclick='javascript:$(\"#popWinDiv\").window(\"close\");'>关闭</a></div></div><div id='userPop'></div>";
        $('#popWinDiv').remove();
        popWinDiv.insertAfter(self);
        popWinDiv.html(insertDiv);
        var openPopWin = $('#popWinDiv').window({
            title: config.title,
            width: config.width,
            height: config.height,
            minimizable: false,
            maximizable: false,
            collapsible: false,
            resizable: false,
            onClose : function(){
            	//$(this).window('destory');
            },
            onOpen : function () {
            	$('#user_Type_select').combobox({
            		editable : false,
            		onChange : function(){
            			if($(this).combobox("getValue")==1){
            				$("#dept_show").css({'display':'inline'});
            				$("#provider_show").css({'display':'none'});
            				$('#user_provider_select').combobox('setValue','');
            			}else if($(this).combobox("getValue")==2){
            				$("#dept_show").css({'display':'none'});
            				$("#provider_show").css({'display':'inline'});
            				$('#user_department_select').val('').f_combotree('setValue','');
            			}
            		}
            	});
            	$('#user_department_select').f_combotree({
         	        rootName : '部门',
         	        height : 200,
         	        url : getRootName() + '/permissionDepartment/findDeptByOrgId?organizationId='+config.orgId ,
         	        titleField : 'deptName',
         	        idField : 'deptId',
         	        parentIdField : 'parentDeptID'
         	    });
            	f('#user_provider_select').combobox({
					url: getRootName() + '/platform/provider/queryProsByUserId?userId=' + config.userId ,
					valueField : 'providerId',
					textField : 'providerName',
					editable : false
				});
            },
            modal: true,
        });
        if(options.selectType=='more'){
        	sureBtn.prependTo($("#conditionsBtn"));
        };
    };
    
   
   
    /**
     * 获取用户列表--单个用户
     */
    var getData = function (options) {
    	var url = "";
    	if(options.url){
    		url = options.url;
    	}else{
    		url = _default_config.url;
    	}
        $('#selUser_datagrid').datagrid({
            iconCls: 'icon-edit',
            width: 'auto',
            height: 330,
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            url: url,
            remoteSort: false,
            rownumbers: true,
            idField: 'UserID',
            singleSelect: true,
            queryParams: {
                userType: '1',
                orgId: options.orgId,
                userIds : options.userIds
            },
            pagination: true,
            columns: [[{
                    field: 'EmployeeCode',
                    title: '员工编码',
                    align: 'center',
                    width: 190
                    },{
                    field: 'UserName',
                    title: '姓名',
                    align: 'center',
                    width: 180,
                    formatter: function (value, row, index) {
                        return '<a style=\'cursor:pointer\' title="人员详情" class="easyui-tooltip" onclick="userDetail(' + row.UserID + ')">' + value + '</a>';
                    } }, {
                    field: 'userType',
                    title: '用户类型',
                    align: 'center',
                    width: 180,
                    formatter : function (value,row,index){
                    	var userType ;
    					if(value=='0'){
    						userType = '系统管理员';
    					}else if(value=='1'){
    						userType = '企业内部IT部门用户';
    					}else if(value=='2'){
    						userType = '企业内部业务部门用户';
    					}else {
    						userType = '外部供应商用户';
    					};
    					return userType;
                    }
    				}, {
                    field: 'ids',
                    title: '操作',
                    align: 'center',
                    width: 100,
                    formatter: function (value, row, index) {
                        return "<a style='cursor:pointer' title='选择' class='easyui-tooltip' onclick='checkUser("+row.UserID+",\""+row.UserName+"\")'>选择</a>";
                    }}]]
        });
    };
    /**
     * 获取用户列表--多个用户
     */
    var getData_more = function (options) {
    	var url = "";
    	if(options.url){
    		url = options.url;
    	}else{
    		url = _default_config.url;
    	}
    	$('#selUser_datagrid_more').datagrid({
    		iconCls: 'icon-edit',
    		width: 'auto',
    		height: 330,
    		nowrap: false,
    		striped: true,
    		border: true,
    		collapsible: false,
    		url: url,
    		remoteSort: false,
    		rownumbers: true,
    		idField: 'UserID',
    		singleSelect: false,
    		queryParams: {
    			userType: '1',
    			orgId: options.orgId,
    			userIds : options.userIds
    		},
    		pagination: true,
    		columns: [[{
            	field : 'UserID',
            	checkbox : true,
            	aligh : 'center',
            	width : 80
             },{
    			field: 'EmployeeCode',
    			title: '员工编码',
    			align: 'center',
    			width: 200
    		},{
    			field: 'UserName',
    			title: '姓名',
    			align: 'center',
    			width: 200,
    			formatter: function (value, row, index) {
    				return '<a style=\'cursor:pointer\' title="人员详情" class="easyui-tooltip" onclick="userDetail(' + row.UserID + ')">' + value + '</a>';
    			} }, {
    				field: 'userType',
    				title: '用户类型',
    				align: 'center',
    				width: 200,
    				formatter : function (value,row,index) {
    					var userType ;
    					if(value=='0'){
    						userType = '系统管理员';
    					}else if(value=='1'){
    						userType = '企业内部IT部门用户';
    					}else if(value=='2'){
    						userType = '企业内部业务部门用户';
    					}else {
    						userType = '外部供应商用户';
    					};
    					return userType;
    				}
    			}]]
    	});
    };
})();

/*
 * 选择用户并关闭窗口
 */
function checkUser(userId,userName){
	opt.onClosed.call(this, {"userName": userName,'userId': userId});
	$('#popWinDiv').window('close');
};
function checkMoreUser(){
	var checkItems = $("#selUser_datagrid_more").datagrid('getChecked');
	opt.onClosed.call(this,checkItems);
    opt = null;
	$('#popWinDiv').window('close');
}

function select_query(tableId,userIds,orgId) {
	var $user_datagrid = f('#'+tableId);
	$user_datagrid.datagrid('options').queryParams = {
		userType:f('#user_Type_select').combobox('getValue'),
		orgId:orgId==-1?"":orgId,
		deptId:f('#user_department_select').f_combotree('getValue'),
		name:f('#user_name_select').val(),
		userNo : $('#user_no').val(),
		userIds : userIds,
		provider:f('#user_provider_select').combobox('getValue')};
	$user_datagrid.datagrid('load');
	$user_datagrid.datagrid('unselectAll');
};

function select_reset() {
    $('#user_no').val('');
    $('#user_name_select').val('');
    $("#user_Type_select").combobox("setValue","1");
    $('#user_department_select').val('').f_combotree('setValue','');
};

/*
 * 查看用户详细信息
 */
function userDetail(userId) {
    var userDiv = ' <div id="divUserInfo" ><div id="sysUserTabs" class="easyui-tabs"><div title="用户信息" id="userInfoTab">' +
        '<table id="tblUserInfo" class="tableinfo" ><tr><td><b class="title">帐号：</b><label id="lbl_userAccount" class="inputVal"></label>' +
        '</td><td><b class="title">用户姓名：</b><label id="lbl_userName" class="inputVal" ></label></td></tr>' +
        '<tr><td><b class="title">手机号码：</b><label id="lbl_mobilePhone" class="inputVal" ></label></td><td><b class="title">电话号码：</b><label id="lbl_telephone" class="inputVal"></label>' +
        '</td> </tr><tr><td colspan="2"><b class="title">邮箱地址：</b><label id="lbl_email" class="inputVal" ></label></td></tr>' +
        '<tr id="isShow1"><td><b class="title">用户类型：</b><label id="lbl_userType" class="inputVal"></label></td><td><b class="title">员工编码：</b>' +
        '<label id="lbl_employeeCode" class="inputVal" ></label></td></tr><tr id="isShow2"><td><b class="title">所属部门：</b><label id="lbl_deptName" class="inputVal"></label>' +
        '</td><td><b class="title">职位级别：</b><label id="lbl_gradeName" class="inputVal"></label></td></tr><tr id="isShow3"><td><b class="title">用户类型：</b><label id="lbl_userType2" class="inputVal"></label>' +
        '</td><td><b class="title">所属供应商：</b><label id="lbl_providerName" class="inputVal"  ></label></td></tr></table></div><div class="conditionsBtn"><a href="javascript:void(0);" onclick="javascript:$(\'#userPop\').window(\'close\');"' +
        'id="btnBackInfo" >关闭</a></div></div>';
    var openPopWin = $('#userPop').window({
        title: '用户详细信息',
        width: 750,
        height: 350,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        resizable: false,
        modal: true,
    });
    openPopWin.html(userDiv);
    setRead(userId);
}

/**
 * 详情赋值
 * @param userId
 */
function setRead(userId) {
    var path = getRootName();
    var uri = path + "/permissionSysUser/findSysUser";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "userID": userId,
            "t": Math.random()
        },
        error: function () {
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function (data) {
            iterObj(data, "lbl");
            var userType = $("#lbl_userType").val();
            if (userType == 0) {
                $("#isShow1").show();
                $("#isShow2").show();
                $("#isShow3").hide();
                $("#lbl_userType").text("系统管理员");
            } else if (userType == 1) {
                $("#isShow1").show();
                $("#isShow2").show();
                $("#isShow3").hide();
                $("#lbl_userType").text("IT部门用户");
            } else if (userType == 2) {
                $("#isShow1").show();
                $("#isShow2").show();
                $("#isShow3").hide();
                $("#lbl_userType").text("业务部门用户");
            } else if (userType == 3) {
                $("#isShow1").hide();
                $("#isShow2").hide();
                $("#isShow3").show();
                $("#lbl_userType").text("外部供应商用户");
                $("#lbl_userType2").text("外部供应商用户");
            }
        }
    };
    ajax_(ajax_param);
}