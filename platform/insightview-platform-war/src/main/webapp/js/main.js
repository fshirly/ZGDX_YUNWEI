/*~~获取当前时间~~*/
/*function getCurDate()
{ 
	   var d = new Date();
	   var years = d.getYear();
	   var month = add_zero(d.getMonth()+1);
	   var days = add_zero(d.getDate());
//     var hours = add_zero(d.getHours()); 
//     var minutes = add_zero(d.getMinutes());
//     var seconds=add_zero(d.getSeconds());
	   var ndate =" &nbsp;"+ years+"."+month+"."+days ;
	   document.getElementById('dateArea').innerHTML= ndate;
	   document.getElementById('dateArea').style.fontFamily="宋体";
	   document.getElementById('dateArea').style.fontSize="12px";		   
}
function add_zero(temp){ 
	if(temp<10)
		return "0"+temp; 
	else 
		return temp;
}
setInterval("getCurDate()",100);*/
var Main = Main || {};		 
Main.tools = {
		//path : getRootName(),
		resumePop : function(userId){
			var that = this;
			$('#popWin').window({
    			title : '个人中心',
    			width : 800,
    			height : 350,
    			minimizable : false,
    			maximizable : false,
    			collapsible : false,
    			modal : true,
    			href :getRootName()+ '/personalInfo/toPersonalInfo'
    		});
		},
		changePwd : function(userId){
			var that = this;
			$('#popWin').window({
    			title : '修改密码',
    			width : 400,
    			height : 270,
    			minimizable : false,
    			maximizable : false,
    			collapsible : false,
    			modal : true,
    			href :getRootName()+ '/personalInfo/toChangePwd'
    		});
		}
}
	
	  
	  