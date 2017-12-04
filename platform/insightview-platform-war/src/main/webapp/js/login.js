


	function $_(id){
		return document.getElementById(id);
	}
	function trim(v)
	{
		return v.replace(/^\s|\s$_/g,"");
	}
	
	
	/*~~重置~~*/
	function doReset(){
		$_('iptUserAccount').value="";
		$_('iptUserPassword').value="";			
	}
	
	function  doSubmit(){
		if(checkAll()){
			
			$_('myForm').submit();	
		}
		
		
	}
	
	function checkUserName(){
		var userName = $_("inputs1").value;
		if(trim(userName).length==0){
			$_.messager.alert("用户名不能为空！");
			return false;
		}
		return true;
		
	}
	
	
	function checkPwd(){
		var pwd = $_("inputs2").value;
		if(trim(pwd).length==0){
			$_.messager.alert("密码不能为空！");
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
		obj.style.backgroundColor="";

	}
	
	function doBack(obj){
		obj.style.backgroundColor="";
		obj.style.backgroundColor="";
	}
	