/**
 非表单提交校验
 作者：zyp 
*/

var datatype = {
			"*":/[\w\W]+/,                             //校验输入是否为空
			"*6-16":/^[\w\W]{6,16}$/,                  //字符长度为6-16
			"num":/^\d+$/,                             //校验是否为数字
			"n6-16":/^\d{6,16}$/,
			"str":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]+$/, //字符串类型
			"s6-18":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/,// 字符串长度
			"postNum":/^[0-9]{6}$/, //邮政编码
			"phoneNum":/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/,//手机号码长度
			"specialCharacters":/^[\u4E00-\u9FA5a-zA-Z0-9_]{3,20}$/,
			"email":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,//邮箱格式
			"url":/^(\w+:\/\/)?\w+(\.\w+)+.*$/,
			"chnStr":/^[^\x00-\xff]+$/,      //只能输入汉字     
			"enAndNum":/[a-zA-Z0-9]{6,16}/,  //只能输入字母和数字
			"money":/^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/,
			"ipAddr":/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
			"pwd":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/,
			"time":/^(\d{4})(-|\/)(\d{2})\2(\d{2}) (\d{2}):(\d{2}):(\d{2})$/
	};
var errorMsg = {
	       "*":"输入不能为空！",
		   "*6-16":"输入的字符长度为6-16位！",
		   "postNum":"邮编号码不正确！",
		   "email":"邮箱格式不正确！",
		   "phoneNum":"手机号码不正确！",
		   "money":"金额只能输入数字！",
		   "ipAddr":"ip地址错误，请填写正确的地址！",
		   "specialCharacters":"输入只能是下划线、汉字、字母、数字\n不能含有特殊字符！",
		   "pwd":"密码长度6-18位！",
		   "enAndNum":"只能输入数字和字母！",
		   "chnStr":"只能输入汉字！",
		   "url":"网址链接格式不正确！",
		   "time":"时间格式错误"
	};
function checkInfo(divId){
	var flag = true;
	$(divId+" input").each(function() {
		$(this).attr("title","");
        var datatypeValue = $(this).attr("datatype");
		for(var attribute in datatype){
			if(datatypeValue == attribute){
				var inputValue = $(this).val();
				//alert(inputValue.length);
				//判断为空值
				if(inputValue ==""){
					flag = false;
					$(this).css("border-color","red");
					$(this).focus();
					$(this).attr("title",errorMsg["*"]);
					//$.messager.alert("提示",errorMsg["*"],"error");
					$(this).keyup(function(){
						$(this).css("border-color","");
						$(this).attr("title","");
						});
					return false;
				} else if(!datatype[datatypeValue].test(inputValue)){ //判断值是否匹配
					        flag = false; 
							$(this).css("border-color","red");
							$(this).focus();
							$(this).attr("value","");
							$(this).attr("title",errorMsg[datatypeValue]);
							//$.messager.alert("提示信息",errorMsg[datatypeValue],"error");
					        $(this).keyup(function(){
						         $(this).css("border-color","");
								 $(this).attr("title","");
						         });
					        return false;
				          }
		    }
		}
    });
	//alert(flag);
    return flag;
	
}
