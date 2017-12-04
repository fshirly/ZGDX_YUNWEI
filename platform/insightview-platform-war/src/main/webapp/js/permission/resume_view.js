$(function() {
    $("#closeBtn").click(function(){
      window.close();
    });
    
    initUserLearningExpTable();
    initUserTrainingExpTable();
    initUserWorkingExpTable();
    initUserProjectExpTable();
    
});

function initUserLearningExpTable() {
    var path = getRootName();
    $('#tblUserLearningExp').datagrid({
    	title:'学习经历',
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
        } ] ]
    });
}
    
function initUserTrainingExpTable() {
    var path = getRootName();
    $('#tblUserTrainingExp').datagrid({
        title:'参加培训及后续教育',
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
        }] ]
    });
}
    
function initUserWorkingExpTable() {
    var path = getRootName();
    $('#tblUserWorkingExp').datagrid({
        title:'工作经历',
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
        }] ]
    });
}    
    
function initUserProjectExpTable() {
    var path = getRootName();
    $('#tblUserProjectExp').datagrid({
        title:'参与项目建设',
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
        }] ]
    });
}
