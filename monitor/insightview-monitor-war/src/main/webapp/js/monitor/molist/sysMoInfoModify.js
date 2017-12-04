var path = getRootName();
var mid = $("#ipt_mid").val();
$(document).ready(function() {
	$('#tabs_window').tabs({
		fit:true,
		selected:0,
		onSelect: function(title,index) {
			if(index === 1){
				$("#ifr1").attr("src",path+'/monitor/sysMo/toShowModifyMoRangeView?mid='+mid);
				var value =  $("#ifr0").contents().find("#monitorProperty").val();
				$("#btnSave").css("display","none");
				$("#btnClose").css("display","none");
				if(value == 1){
					$("#btnSave3").css("display","inline");
					
				}else{
					$("#btnClose1").css("display","inline");
				}
			}else{
				$("#btnSave").css("display","inline");
				$("#btnClose").css("display","inline");
				$("#btnSave3").css("display","none");
				$("#btnClose1").css("display","none");
			}
		}
	});
	$("#ifr0").attr("src",path+'/monitor/sysMo/toShowModifyMoBaseView?mid='+mid);
});


