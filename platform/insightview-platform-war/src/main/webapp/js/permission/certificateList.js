$(function() {
    initCertificateTable();
});

//////////////////////////////////////////////////
function addUserCertificate() {
    parent.$('#popWin2').window({
        title:'添加证书',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/personalInfo/toAddUserCertificate'
    });	
}


function editUserCertificate(id) {
    parent.$('#popWin2').window({
        title:'编辑证书',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/personalInfo/toEditUserCertificate?certificateId=' + id
    });
}

function viewUserCertificate(id) {
    parent.$('#popWin2').window({
        title:'查看证书',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/personalInfo/toViewUserCertificate?certificateId=' + id
    });
}

function delUserCertificate(id) {
    $.messager.confirm("提示","确定删除此条？",function(r){
        if (r == true) {
            var uri = "../personalInfo/deleteUserCertificate";
            var ajax_param = {
                url : uri,
                type : "post",
                datdType : "json",
                data : {
                    "certificateId" : id,
                    "t" : Math.random()
                },
                error : function() {
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success : function(data) {
                	reloadCertificateTable();
                }
            };
            ajax_(ajax_param);
        }
    });
}

function reloadCertificateTable() {
    $('#tblCertificateList').datagrid('options').queryParams = {
        "userId" : $('userId').val()
    };
    $('#tblCertificateList').datagrid('load');
    $('#tblCertificateList').datagrid('unselectAll');
    $('#tblCertificateList').datagrid('uncheckAll');	
	
}

/*
 * 页面加载初始化表格
 */
function initCertificateTable() {
    var path = getRootName();
    $('#tblCertificateList').datagrid({
        iconCls : 'icon-edit',// 图标
        width : 'auto',
        height : '250',
        nowrap : false,
        striped : true,
        border : true,
        collapsible : true,// 是否可折叠的
//        fit : true,// 自动大小
        fitColumns:true,
        url : '../personalInfo/queryUserCertificate',
        remoteSort : false,
        idField : 'certificateId',
        singleSelect : true,// 是否单选
        checkOnSelect : false,
        selectOnCheck : false,
        pagination : false,// 分页控件
        toolbar : [ {
            text : '新建',
            iconCls : 'icon-add',
            handler : function() {
                addUserCertificate();
            }
        }],
        columns : [ [
        {
            field : 'certificateNo',
            title : '证书编号',
            align : 'center',
            width : 60,
            styler: function(value,row,index){
                return 'word-break:break-all;';
            }
        },
        {
            field : 'certificateName',
            title : '证书名称',
            align : 'center',
            width : 60,
            styler: function(value,row,index){
                return 'word-break:break-all;';
            }
        },
        {
            field : 'dateOfIssue',
            title : '颁发时间',
            align : 'center',
            width : 40,
            formatter : function(value, row, index) {
            	if(row.dateOfIssue === null) {
            		return '';
            	}
                return formatDate(new Date(row.dateOfIssue),"yyyy-MM-dd");
            }
        },
        {
            field : 'effectiveTime',
            title : '有效时间',
            align : 'center',
            width : 40,
            formatter : function(value, row, index) {
            	if (row.effectiveTime === null) {
            		return '';
            	}
                return formatDate(new Date(row.effectiveTime),"yyyy-MM-dd");
            }
        }, 
        {
            field : 'accessoryUrl',
            title : '相关附件',
            align : 'center',
            width : 40,
            formatter : function(value, row, index) {
            	if(row.accessoryUrl != null && row.accessoryUrl != '') {
                	var relFileName = construFileName(row.accessoryUrl);
                    return relFileName;
            	} else {
            		return '';
            	}
            }
        },
        {
            field : 'certificateId',
            title : '操作',
            width : 40,
            align : 'center',
            formatter : function(value, row, index) {
                    return "<a style='cursor:pointer' title='查看' onclick=javascript:viewUserCertificate('" + row.certificateId + "');><img src='" + path + "/style/images/icon/icon_view.png' alt='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='编辑' onclick=javascript:editUserCertificate('" + row.certificateId + "');><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='cursor:pointer' title='删除' onclick=javascript:delUserCertificate('" + row.certificateId + "');><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
            }
        } ] ]
    });
}
