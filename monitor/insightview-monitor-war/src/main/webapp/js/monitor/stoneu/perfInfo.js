var path = getRootName();
var time;
$(document).ready(function() {
	time = window.setInterval(falsh, 60000);
	 $.each($("#tbltemList tr td input:hidden"), function(i,val){ 
	 	if(this.name=="temp"){
			 $(val).after($('<lable style="margin-left: 13px;font-size:14px;">'+this.value+'℃</lable>'));
		 }else if(this.name=="humidity"){
			 $(val).after($('<lable style="margin-left: 13px;font-size:14px;">'+this.value+'%RH</lable>'));
		 }else{
			 if(this.value=="1"){
		    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
		     }else if(this.value=="0"){
		    	$(val).after( $('<img title="正常" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
		     }else{
		    	 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
		     }
		 }
	 });  
	 
	 $.each($("#smokeStatus td input"), function(i,val){  
	     if(this.value=="1"){
	    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="0"){
	    	$(val).after( $('<img title="正常" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	 
	 $.each($("#soundStatus td input"), function(i,val){ 
	     if(this.value=="1"){
	    	 $(val).after($('<img title="报警" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/down1.png">'));
	     }else if(this.value=="0"){
	    	$(val).after( $('<img title="正常" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/up2.png">'));
	     }else{
	    	 $(val).after( $('<img title="未知" style="margin-left: 13px;" src="'+path+'/style/images/levelIcon/unkonwn3.png">'));
	     }
	 }); 
	 
	 $.each($("#contStatus td input"), function(i,val){  
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
	var currTab =  $('#tabs_window_stoneu').tabs('getSelected'); //获得当前tab
	var title =  currTab.panel('options').title;
	if(title !="设备列表"){
		$('#tabs_window_stoneu').tabs('update', {
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