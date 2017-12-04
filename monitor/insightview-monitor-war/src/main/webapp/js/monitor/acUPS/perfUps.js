var path = getRootName();
var time;
$(document).ready(function() {
	time = window.setInterval(falsh, 60000);
	 $.each($("#run td label"), function(i,val){ 
		 if(this.id=="ipt_autoRestartType"){
			 if(this.textContent=="0"){
				 $("#ipt_autoRestartType").text("自动");
			 }else if(this.textContent=="100"){
				 $("#ipt_autoRestartType").text("手动");
			 } 
		 }
		 if(this.id=="ipt_shutdownType"){
			 if(this.textContent=="0"){
				 $("#ipt_shutdownType").text("只有输出关闭");
			 }else if(this.textContent=="100"){
				 $("#ipt_shutdownType").text("全部关闭");
			 } 
		 }
		 
		 if(this.id=="ipt_batCondition"){
			 if(this.textContent=="0"){
				 $("#ipt_batCondition").text("良好");
			 }else if(this.textContent=="100"){
				 $("#ipt_batCondition").text("虚弱");
			 }else if(this.textContent=="200"){
				 $("#ipt_batCondition").text("需替换");
			 }
		 }
		 if(this.id=="ipt_batStatus"){
			 if(this.textContent=="0"){
				 $("#ipt_batStatus").text("良好");
			 }else if(this.textContent=="100"){
				 $("#ipt_batStatus").text("电量低");
			 }else if(this.textContent =="200"){
				 $("#ipt_batStatus").text("电量耗尽");
			 }
		 }
		 
	 });  
	 
		 var status = $("#ipt_batChargeStatus").text();
		 if(status=="0"){
			 $("#ipt_batChargeStatus").text("浮动充电");
		 }else if(status=="100"){
			 $("#ipt_batChargeStatus").text("涓流充电");
		 }else if(status=="200"){
			 $("#ipt_batChargeStatus").text("静止充电");
		 }else if(status=="300"){
			 $("#ipt_batChargeStatus").text("放电");
		 }
	 
	 $.each($("#Switch tr td input[alt='']"), function(i,val){  
	     if(this.value=="1"){
	    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="0"){
	    	$(val).after( $('<img title="正常"  style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知"  style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	
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