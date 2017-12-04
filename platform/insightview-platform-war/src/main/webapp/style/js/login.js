


	function $(id){
		return document.getElementById(id);
	}
	function trim(v)
	{
		return v.replace(/^\s|\s$/g,"");
	}
	
	
	/*~~重置~~*/
	function doReset(){
				$('inputs2').value="";
				$('inputs1').value="";			
	}
	
	function  doSubmit(){
		if(checkAll()){
			
			$('myForm').submit();	
		}
		
		
	}
	
	function checkUserName(){
		var userName = $("inputs1").value;
		if(trim(userName).length==0){
			alert("用户名不能为空！");
			return false;
		}
		return true;
		
	}
	
	
	function checkPwd(){
		var pwd = $("inputs2").value;
		if(trim(pwd).length==0){
			alert("密码不能为空！");
			return false;
		}
		return true;
		
	}
	
	
	
	function checkAll(){
		if(checkUserName() &&
		 	 checkPwd() 
		  )
		  {
			  
			  	return true;
		  }
		  return false;
	}
	
	function doChange(obj){
		obj.style.backgroundColor="";
		obj.style.backgroundColor="#06F";

	}
	
	function doBack(obj){
		obj.style.backgroundColor="";
		obj.style.backgroundColor="#3DB2F5";
	}
	