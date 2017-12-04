var path = getRootName();
var time;
$(document).ready(function() {
	time = window.setInterval(falsh, 60000);
	 $.each($("#run td input:hidden"), function(i,val){ 
		 if(this.name=="ipt_indoorTemperature"){
			 $(val).after($('<lable style="margin-left: 13px;font-size:17px;font-weight:bold;">'+this.value+'℃</lable>'));
		 }else if(this.name=="ipt_indoorHumidity"){
			 $(val).after($('<lable style="margin-left: 13px;font-size:17px;font-weight:bold;">'+this.value+'%RH</lable>'));
		 }else{
			 if(this.value=="0"){
				 $(val).after($('<img title="开启" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
			 }else if(this.value=="1"){
				 $(val).after( $('<img title="关闭" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
			 }else{
				 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
			 }
		 }
	 });  
	 
	 $.each($("#runStatus td input:hidden"), function(i,val){  
	     if(this.value=="1"){
	    	 $(val).after($('<img title="关闭" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="0"){
	    	$(val).after( $('<img title="开启" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	 
	 $.each($("#system td input:hidden"), function(i,val){  
	     if(this.value=="1" || this.value=="2"){
	    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="0"){
	    	$(val).after( $('<img title="正常" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	 
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