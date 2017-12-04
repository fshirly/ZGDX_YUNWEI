var path = getRootName();
var mid = $("#ipt_mid").val();
$(document).ready(function() {
	$('#tabs_window').tabs({
		fit:true,
		selected:0,
		onSelect: function(title,index) {
			if(index === 1){
				$("#ifr1").attr("src",path+'/monitor/sysMo/toShowMoRangeDetail?mid='+mid);
			}
		}
	});
	$("#ifr0").attr("src",path+'/monitor/sysMo/toShowMoBaseDetail?mid='+mid);
});


