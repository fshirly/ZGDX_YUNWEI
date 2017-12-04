$(function(){
	
	$('#requestMode').combobox({
		panelHeight : '120',
		url : '../dict/readItems?id=3001',
		valueField : 'key',
		textField : 'val',
		editable : false
	});
	
	$('#requester').createSelect2({
   		uri : '/permissionSysUser/querySysUserInfo',//获取数据
   		name : 'userName',//显示名称
   		id : 'userID', //对应值值
   		initVal :{requester:$("#requester").attr("value")}
	});
	
	// 发布人默认为当前登录人
	if ($('#requester').select2("val") == -1) {
		$('#requester').select2("val",$('#userId').val());
	}
	
    $("#commitBtn").click(function(){
        if (!checkInfo('#eventForm')){
           return false;
        }
        
        var path = getRootName();
        var uri = path + "/dashboardPageManage/saveEvent";
        var ajax_param = {
            url : uri,
            type : "post",
            datdType : "json",
            data : {
                "requester" : $("#requester").select2("val"),
                "title" : $("#title").val(),
                "description" : $("#description").val(),
                "requestMode" : $('#requestMode').combobox('getValue')
            },
            error : function() {
                 $("#errorText").html("提交失败!");
            },
            success : function(data) {
                if ("OK" == data) {
                	//parent.$('#LtoBeAllocatedEvent77 .sDashboard-icon.sDashboard-refresh-icon').click();
                    $("#errorText").html("提交成功!");
                    $("#requester").select2("val","-1");
                    $('#title').val('');
                    $('#description').val('');
                    $('#requestMode').combobox('setValue','');
                    
                    if(window.parent.frames[1].document.getElementById("pageID").value=="allocateEventList"){
                        window.parent.frames[1].doInitTable();
                   }
                  
                } else {
                     $("#errorText").html("提交失败!");
                }
            }
        };
        ajax_(ajax_param); 
    });
});
