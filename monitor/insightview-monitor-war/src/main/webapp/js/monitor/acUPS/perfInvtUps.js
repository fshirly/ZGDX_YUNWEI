var path = getRootName();
var time;
$(document).ready(function() {
	 time = window.setInterval(falsh, 60000);
	 $.each($("#Switch tr td input[alt='']"), function(i,val){  
	     if(this.value=="14"){
	    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="16"){
	    	$(val).after( $('<img title="正常"  style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知"  style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	
	 var batteryStatus=$("#ipt_batteryStatus").text();
	 if(batteryStatus=="4"){
		 $("#ipt_batteryStatus").text("备用电池");
	 }else if(batteryStatus =="5"){
		 $("#ipt_batteryStatus").text("异常电池");
	 }
	 
	 var chargeStatus =$("#ipt_chargeStatus").text();
	 if(chargeStatus=="6"){
		 $("#ipt_chargeStatus").text("持续充电");
	 }else if(chargeStatus =="7"){
		 $("#ipt_chargeStatus").text("浮动充电");
	 }else if(chargeStatus=="16"){
		 $("#ipt_chargeStatus").text("已充满");
	 }
	 
	 var breakerStatus =$("#ipt_breakerStatus").text();
	 if(breakerStatus=="8"){
		 $("#ipt_breakerStatus").text("关闭");
	 }else if(breakerStatus =="9"){
		 $("#ipt_breakerStatus").text("开启");
	 }
	 
	 var aCStatus =$("#ipt_aCStatus").text();
	 if(aCStatus=="10"){
		 $("#ipt_aCStatus").text("正常");
	 }else if(aCStatus =="11"){
		 $("#ipt_aCStatus").text("异常");
	 }
	 var switchMode =$("#ipt_switchMode").text();
	 if(switchMode=="12"){
		 $("#ipt_switchMode").text("反转方式");
	 }else if(switchMode =="13"){
		 $("#ipt_switchMode").text("旁路方式");
	 }
	 
	 var inAndOut =$("#ipt_inAndOut").text();
	 if(inAndOut=="2"){
		 $("#ipt_inAndOut").text("threeInOneOut");
	 }else if(inAndOut =="3"){
		 $("#ipt_inAndOut").text("threeInThreeOut");
	 }
});

function falsh(){
	var currTab =  $('#tabs_window_acups').tabs('getSelected'); //获得当前tab
	var title =  currTab.panel('options').title;
	if(title !="设备列表"){
		$('#tabs_window_acups').tabs('update', {
			tab : currTab,
			options : {
				content:'<iframe name="indextab" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>',  
				closable:true,  
				fit:true,  
				selected:true 
			}
		});
	}
}
