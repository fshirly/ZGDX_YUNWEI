$(document).ready(
	function() {    
     $('#graduationInfo').combobox({
        editable : false,
        panelHeight : '120',
        textField: 'label',
        valueField: 'value',
        data: [{
            label: '毕业',
            value: '毕业'
        },{
            label: '结业',
            value: '结业'
        },{
            label: '肄业',
            value: '肄业'
        }]
    });
    
	$("#saveBtn").click(function(){
		if (!checkInfo('#form1')){
            return false;
        }
    
        var strStartTime = $('#startTime').val();
        var strEndTime = $('#endTime').val();
        if (strStartTime == '') {
            $.messager.alert('提示', '开始年月不能为空!', 'error');
            return false;
        }

        if (strEndTime != '') {
            if (strStartTime == strEndTime) {
                $.messager.alert('提示', '结束年月要晚于开始年月!', 'error');
                return false;
            }
            
            if (strStartTime > strEndTime) {
                $.messager.alert('提示', '结束年月不得早于开始年月!', 'error');
                return false;
            }
        }
        
        var path = getRootName();
        var uri = path + "/userResume/saveUserLearningExp";
        var ajax_param = {
            url : uri,
            type : "post",
            datdType : "json",
            data : $("#form1").serialize(),
            error : function() {
                $.messager.alert('提示', '提交失败', 'error');
            },
            success : function(data) {
                $('#popWin3').window('close');
                reloadUserLearningExpTable();
            }
        };
        ajax_(ajax_param); 
	});
});
