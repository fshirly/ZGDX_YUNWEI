$(document).ready(
	function() {
	//附件
    $("#accessoryUrl").f_fileupload({
    	imgWidth:10000,
        imgHeight:10000,
        filePreview : true
    });
	
	$('#dateOfIssue').datebox({
        editable : false
    });
    
    $('#effectiveTime').datebox({
        editable : false
    });
    
	$("#saveCertificateBtn").click(function(){
		if (!checkInfo('#form1')){
            return false;
        }
            
        var strStartTime = $('#dateOfIssue').datebox('getValue');
        var strEndTime = $('#effectiveTime').datebox('getValue');
        var startTime = new Date(strStartTime.replace("-", "/").replace("-", "/"));
        var endTime = new Date(strEndTime.replace("-", "/").replace("-", "/"));
        if (startTime > endTime) {
            $.messager.alert('提示', '有效时间不得早于颁发时间', 'error');
            return false;
        }
        
        var dateOfIssue = $('#dateOfIssue').datebox('getValue');
        var start = new Date(dateOfIssue.replace("-", "/").replace("-", "/"));
        if (start > new Date()) {
            $.messager.alert('提示', '颁发时间不得晚于当前时间', 'error');
            return false;
        }
        
        var path = getRootName();
        var uri = path + "/personalInfo/saveUserCertificate";
        var ajax_param = {
            url : uri,
            type : "post",
            datdType : "json",
            data : {
            	"certificateId":$('#certificateId').val(),
                "certificateNo":$('#certificateNo').val(),
                "certificateName":$('#certificateName').val(),
                "dateOfIssue":$('#dateOfIssue').datebox('getValue'),
                "effectiveTime":$("#effectiveTime").datebox('getValue'),
                "description" : $('#description').val(),
                "accessoryUrl":$('#iconFileName').val(),
                "t" : Math.random()
            },
            error : function() {
                $.messager.alert('提示', '提交失败', 'error');
            },
            success : function(data) {
                parent.$('#popWin2').window('close');
                reloadCertificateTable();
            }
        };
        ajax_(ajax_param); 
	});
});
