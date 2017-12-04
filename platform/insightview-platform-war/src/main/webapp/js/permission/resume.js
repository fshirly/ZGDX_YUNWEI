$(function() {
	 $('#gender').combobox({
        editable : false,
        panelHeight : '120',
        textField: 'label',
        valueField: 'value',
        data: [{
            label: '男',
            value: '1'
        },{
            label: '女',
            value: '2'
        }]
    });
    
	$('#birthday').datebox({
        editable : false
    });
    
    $('#tenure').datebox({
        editable : false
    });
    
    $('#duringTime').datebox({
        editable : false
    });
    
    $('#beOnTheJob').datebox({
        editable : false
    });
    
    $('#engageTime').datebox({
        editable : false
    });
    
    $("#saveUserResumeBtn").click(function(){
        if (!checkInfo('#resumeForm')){
            return false;
        }
    
        var birthday = $('#birthday').datebox('getValue');
        var start = new Date(birthday.replace("-", "/").replace("-", "/"));
        if (start > new Date()) {
            $.messager.alert('提示', '出生日期不得晚于当前时间!', 'info');
            return false;
        }
    
        var path = getRootName();
        var uri = path + "/userResume/updateResume";
        var ajax_param = {
            url : uri,
            type : "post",
            datdType : "json",
            data : $("#resumeForm").serialize(),
            error : function() {
                $.messager.alert('提示', '提交失败!', 'error');
            },
            success : function(data) {
                if ("OK" == data) {
                	$.messager.alert('提示', '保存成功!', 'info');
                	//$('#resumeTabs').tabs('select', '学习经历');
                } else {
                    $.messager.alert('提示', '提交失败!', 'error');
                }
            }
        };
        ajax_(ajax_param); 
    });
    
    initUserLearningExpTable();
    initUserTrainingExpTable();
    initUserWorkingExpTable();
    initUserProjectExpTable();
    
    
    $("#userIcon").f_fileupload({
    	imgWidth:90,
        imgHeight:110,
        filesize: 300,
        fileFormat: "['jpg','gif','png','bmp','jpeg']",
    	viewImgSrcId:"scanImg"
    });
});

//////////////////////////////////////////////////
function addUserLearningExp() {
    $('#popWin3').window({
        title:'添加学习经历',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toAddUserLearningExp?resumeID=' + $('#resumeID').val()
    });	
}


function editUserLearningExp(id) {
    $('#popWin3').window({
        title:'编辑学习经历',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toEditUserLearningExp?learningExpId=' + id
    });
}

function delUserLearningExp(id) {
    $.messager.confirm("提示","确定删除此条？",function(r){
        if (r == true) {
            var uri = "../userResume/deleteUserLearningExp";
            var ajax_param = {
                url : uri,
                type : "post",
                datdType : "json",
                data : {
                    "learningExpId" : id,
                    "t" : Math.random()
                },
                error : function() {
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success : function(data) {
                	reloadUserLearningExpTable();
                }
            };
            ajax_(ajax_param);
        }
    });
}

function reloadUserLearningExpTable() {
    $('#tblUserLearningExp').datagrid('options').queryParams = {
        "resumeID" : $('#resumeID').val()
    };
    $('#tblUserLearningExp').datagrid('load');
    $('#tblUserLearningExp').datagrid('unselectAll');
    $('#tblUserLearningExp').datagrid('uncheckAll');	
	
}

/*
 * 页面加载初始化表格
 */
function initUserLearningExpTable() {
    var path = getRootName();
    $('#tblUserLearningExp').datagrid({
        iconCls : 'icon-edit',// 图标
        width : 'auto',
        height : 'auto',
        nowrap : false,
        striped : true,
        border : true,
        collapsible : true,// 是否可折叠的
//        fit : true,// 自动大小
        fitColumns:true,
        url : '../userResume/queryUserLearningExp?resumeID=' + $('#resumeID').val(),
        remoteSort : false,
        idField : 'learningExpId',
        singleSelect : true,// 是否单选
        checkOnSelect : false,
        selectOnCheck : false,
        pagination : false,// 分页控件
        toolbar : [ {
            text : '新建',
            iconCls : 'icon-add',
            handler : function() {
                addUserLearningExp();
            }
        }],
        columns : [ [
        {
            field : 'title',
            title : '起止年月',
            align : 'center',
            width : 60,
            formatter : function(value, row, index) {
            	if(row.endTime == null || row.endTime == '') {
            	   return row.startTime+ "至今";
            	} else {
                    return row.startTime+ "至" + row.endTime;
            	}
            }
        },
        {
            field : 'eduAndMajor',
            title : '院校及系、专业',
            align : 'center',
            width : 60
        },
        {
            field : 'graduationInfo',
            title : '毕（结、肄）业',
            align : 'center',
            width : 40
        },
        {
            field : 'reference',
            title : '证明人',
            align : 'center',
            width : 40
        },
        {
            field : 'id',
            title : '操作',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                    return "<a style='cursor:pointer' title='编辑' onclick=javascript:editUserLearningExp('" + row.learningExpId + "');><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='删除' onclick=javascript:delUserLearningExp('" + row.learningExpId + "');><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
            }
        } ] ]
    });
}
    
//////////////////////////////////////////////////
function addUserTrainingExp() {
    $('#popWin3').window({
        title:'添加参加培训及后续教育',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toAddUserTrainingExp?resumeID=' + $('#resumeID').val()
    }); 
}


function editUserTrainingExp(id) {
    $('#popWin3').window({
        title:'编辑参加培训及后续教育',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toEditUserTrainingExp?trainingExpId=' + id
    });
}

function delUserTrainingExp(id) {
    $.messager.confirm("提示","确定删除此条？",function(r){
        if (r == true) {
            var uri = "../userResume/deleteUserTrainingExp";
            var ajax_param = {
                url : uri,
                type : "post",
                datdType : "json",
                data : {
                    "trainingExpId" : id,
                    "t" : Math.random()
                },
                error : function() {
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success : function(data) {
                    reloadUserTrainingExpTable();
                }
            };
            ajax_(ajax_param);
        }
    });
}

function reloadUserTrainingExpTable() {
    $('#tblUserTrainingExp').datagrid('options').queryParams = {
        "resumeID" : $('#resumeID').val()
    };
    $('#tblUserTrainingExp').datagrid('load');
    $('#tblUserTrainingExp').datagrid('unselectAll');
    $('#tblUserTrainingExp').datagrid('uncheckAll');    
    
}

/*
 * 页面加载初始化表格
 */
function initUserTrainingExpTable() {
    var path = getRootName();
    $('#tblUserTrainingExp').datagrid({
        iconCls : 'icon-edit',// 图标
        width : 'auto',
        height : 'auto',
        nowrap : false,
        striped : true,
        border : true,
        collapsible : true,// 是否可折叠的
//        fit : true,// 自动大小
        fitColumns:true,
        url : '../userResume/queryUserTrainingExp?resumeID=' + $('#resumeID').val(),
        remoteSort : false,
        idField : 'trainingExpId',
        singleSelect : true,// 是否单选
        checkOnSelect : false,
        selectOnCheck : false,
        pagination : false,// 分页控件
        toolbar : [ {
            text : '新建',
            iconCls : 'icon-add',
            handler : function() {
                addUserTrainingExp();
            }
        }],
        columns : [ [
        {
            field : 'title',
            title : '起止年月',
            align : 'center',
            width : 60,
            formatter : function(value, row, index) {
                if(row.endTime == null || row.endTime == '') {
                   return row.startTime+ "至今";
                } else {
                    return row.startTime+ "至" + row.endTime;
                }
            }
        },
        {
            field : 'followUpWork',
            title : '培训及后续教育',
            align : 'center',
            width : 60
        },
        {
            field : 'reference',
            title : '证明人',
            align : 'center',
            width : 40
        },
        {
            field : 'id',
            title : '操作',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                    return "<a style='cursor:pointer' title='编辑' onclick=javascript:editUserTrainingExp('" + row.trainingExpId + "');><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='删除' onclick=javascript:delUserTrainingExp('" + row.trainingExpId + "');><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
            }
        } ] ]
    });
}
    
//////////////////////////////////////////////////
function addUserWorkingExp() {
    $('#popWin3').window({
        title:'添加工作经历',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toAddUserWorkingExp?resumeID=' + $('#resumeID').val()
    }); 
}


function editUserWorkingExp(id) {
    $('#popWin3').window({
        title:'编辑工作经历',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toEditUserWorkingExp?workingExpId=' + id
    });
}

function delUserWorkingExp(id) {
    $.messager.confirm("提示","确定删除此条？",function(r){
        if (r == true) {
            var uri = "../userResume/deleteUserWorkingExp";
            var ajax_param = {
                url : uri,
                type : "post",
                datdType : "json",
                data : {
                    "workingExpId" : id,
                    "t" : Math.random()
                },
                error : function() {
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success : function(data) {
                    reloadUserWorkingExpTable();
                }
            };
            ajax_(ajax_param);
        }
    });
}

function reloadUserWorkingExpTable() {
    $('#tblUserWorkingExp').datagrid('options').queryParams = {
        "resumeID" : $('#resumeID').val()
    };
    $('#tblUserWorkingExp').datagrid('load');
    $('#tblUserWorkingExp').datagrid('unselectAll');
    $('#tblUserWorkingExp').datagrid('uncheckAll');    
    
}

/*
 * 页面加载初始化表格
 */
function initUserWorkingExpTable() {
    var path = getRootName();
    $('#tblUserWorkingExp').datagrid({
        iconCls : 'icon-edit',// 图标
        width : 'auto',
        height : 'auto',
        nowrap : false,
        striped : true,
        border : true,
        collapsible : true,// 是否可折叠的
//        fit : true,// 自动大小
        fitColumns:true,
        url : '../userResume/queryUserWorkingExp?resumeID=' + $('#resumeID').val(),
        remoteSort : false,
        idField : 'workingExpId',
        singleSelect : true,// 是否单选
        checkOnSelect : false,
        selectOnCheck : false,
        pagination : false,// 分页控件
        toolbar : [ {
            text : '新建',
            iconCls : 'icon-add',
            handler : function() {
                addUserWorkingExp();
            }
        }],
        columns : [ [
        {
            field : 'title',
            title : '起止年月',
            align : 'center',
            width : 60,
            formatter : function(value, row, index) {
                if(row.endTime == null || row.endTime == '') {
                   return row.startTime+ "至今";
                } else {
                    return row.startTime+ "至" + row.endTime;
                }
            }
        },
        {
            field : 'unitAndPost',
            title : '单位及职务',
            align : 'center',
            width : 60
        },
        {
            field : 'reference',
            title : '证明人',
            align : 'center',
            width : 40
        },
        {
            field : 'id',
            title : '操作',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                    return "<a style='cursor:pointer' title='编辑' onclick=javascript:editUserWorkingExp('" + row.workingExpId + "');><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='删除' onclick=javascript:delUserWorkingExp('" + row.workingExpId + "');><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
            }
        } ] ]
    });
}    
    
//////////////////////////////////////////////////
function addUserProjectExp() {
    $('#popWin3').window({
        title:'添加参与项目建设',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toAddUserProjectExp?resumeID=' + $('#resumeID').val()
    }); 
}


function editUserProjectExp(id) {
    $('#popWin3').window({
        title:'编辑参与项目建设',
        width:600,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/userResume/toEditUserProjectExp?projectExpId=' + id
    });
}

function delUserProjectExp(id) {
    $.messager.confirm("提示","确定删除此条？",function(r){
        if (r == true) {
            var uri = "../userResume/deleteUserProjectExp";
            var ajax_param = {
                url : uri,
                type : "post",
                datdType : "json",
                data : {
                    "projectExpId" : id,
                    "t" : Math.random()
                },
                error : function() {
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success : function(data) {
                    reloadUserProjectExpTable();
                }
            };
            ajax_(ajax_param);
        }
    });
}

function reloadUserProjectExpTable() {
    $('#tblUserProjectExp').datagrid('options').queryParams = {
        "resumeID" : $('#resumeID').val()
    };
    $('#tblUserProjectExp').datagrid('load');
    $('#tblUserProjectExp').datagrid('unselectAll');
    $('#tblUserProjectExp').datagrid('uncheckAll');    
}

/*
 * 页面加载初始化表格
 */
function initUserProjectExpTable() {
    var path = getRootName();
    $('#tblUserProjectExp').datagrid({
        iconCls : 'icon-edit',// 图标
        width : 'auto',
        height : 'auto',
        nowrap : false,
        striped : true,
        border : true,
        collapsible : true,// 是否可折叠的
//        fit : true,// 自动大小
        fitColumns:true,
        url : '../userResume/queryUserProjectExp?resumeID=' + $('#resumeID').val(),
        remoteSort : false,
        idField : 'projectExpId',
        singleSelect : true,// 是否单选
        checkOnSelect : false,
        selectOnCheck : false,
        pagination : false,// 分页控件
        toolbar : [ {
            text : '新建',
            iconCls : 'icon-add',
            handler : function() {
                addUserProjectExp();
            }
        }],
        columns : [ [
        {
            field : 'title',
            title : '起止年月',
            align : 'center',
            width : 60,
            formatter : function(value, row, index) {
                if(row.endTime == null || row.endTime == '') {
                   return row.startTime+ "至今";
                } else {
                    return row.startTime+ "至" + row.endTime;
                }
            }
        },
        {
            field : 'projectName',
            title : '项目名称',
            align : 'center',
            width : 60
        },
        {
            field : 'reference',
            title : '证明人',
            align : 'center',
            width : 40
        },
        {
            field : 'id',
            title : '操作',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                    return "<a style='cursor:pointer' title='编辑' onclick=javascript:editUserProjectExp('" + row.projectExpId + "');><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='删除' onclick=javascript:delUserProjectExp('" + row.projectExpId + "');><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
            }
        } ] ]
    });
}

function openResume(userId) {
    var uri = getRootName() + "/userResume/isResumeAdded";
    var ajax_param = {
        url : uri,
        type : "post",
        datdType : "json",
        data : {
            "userId" : userId,
            "t" : Math.random()
        },
        error : function() {
            $.messager.alert("错误", "ajax_error", "error");
        },
        success : function(data) {
            if(data == 'false') {
                $.messager.alert('提示', '该用户尚未添加个人简历信息!', 'info');
            } else {
                var uri = getRootName() + "/userResume/toResumeView?userId=" + userId;
                var width = window.screen.width - 10;
                var height = window.screen.height - 80;
                window.open(uri,'查看个人简历','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,width=' + width + ',height=' + height);
            }
        }
    };
    ajax_(ajax_param);
}
