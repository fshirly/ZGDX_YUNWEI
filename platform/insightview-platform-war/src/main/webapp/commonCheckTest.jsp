<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<meta charset="utf-8" />
<title>公共校验</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/common/ValidFrom.css" />
</head>

<body>  
<div class="main">
    <div class="wraper">
        <form class="demoForm" id="demoForm">
            <table width="100%" style="table-layout:fixed;">
                <tr>
                    <td class="need" style="width:10px;">*</td>
                    <td style="width:70px;">昵称：</td>
                    <td style="width:205px;"><input type="text" value="" name="name" class="inputxt" datatype="s6-18" maxlength="18" nullmsg="请输入昵称！" errormsg="昵称至少6个字符,最多18个字符！" /></td>
                    <td></td>
                </tr>
                   <tr>
                    <td class="need">*</td>
                    <td>手机号码：</td>
                    <td><input type="text" value="" name="phoneNum" class="inputxt" datatype="moble" maxlength="11" nullmsg="请输入您的手机号码！" errormsg="手机号码输入不正确" /></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>密码：</td>
                    <td><input type="password" value="" name="userpassword" class="inputxt" datatype="*6-16" maxlength="16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" /></td>
                   
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td style="width:205px;">确认密码：</td>
                    <td><input type="password" value="" name="userpassword2" class="inputxt" datatype="*" recheck="userpassword" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>只输数字：</td>
                    <td><input type="text" value="" name="tel" class="inputxt" datatype="num" nullmsg="请输入数字！" errormsg="只能输入数字！"  /></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>只输中文：</td>
                    <td><input type="text" value="" name="tel" class="inputxt" datatype="chnStr" nullmsg="请输入中文" errormsg="只能输入中文字符！"  /></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>字母和数字：</td>
                    <td><input type="text" value="" name="tel" class="inputxt" datatype="enAndNum" nullmsg="请输入信息！" errormsg="只能输入字母和数字！"  /></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>所在城市：</td>
                    <td><select name="province" datatype="*" nullmsg="请选择所在城市！" errormsg="请选择所在城市！"><option value="">请选择城市</option><option value="1">瑞金市</option></select></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>性别：</td>
                    <td><input type="radio" value="1" name="gender" id="male" class="pr1" datatype="*" errormsg="请选择性别！" /><label for="male">男</label> <input type="radio" value="2" name="gender" id="female" class="pr1" /><label for="female">女</label></td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>动作：</td>
                    <td>
                        <input name="shoppingsite2" class="rt2" id="shoppingsite21" type="checkbox"  value="1" datatype="*" errormsg="请选择您常去的购物网站！" /><label for="shoppingsite21">走</label>
                        <input name="shoppingsite2" class="rt2" id="shoppingsite22" type="checkbox"  value="2" /><label for="shoppingsite22">跑</label>
                        <input name="shoppingsite2" class="rt2" id="shoppingsite23" type="checkbox"  value="3" /><label for="shoppingsite23">爬</label>
                    </td>
                    
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>输入非特殊字符：</td>
                    <td><input type="text" value="" name="specialCharacters" class="inputxt" datatype="specialCharacters" nullmsg="请输入非特殊字符" errormsg="含有特殊字符！"  /></td>
                    
                </tr>
                 <tr>
                    <td class="need"></td>
                    <td></td>
                    <td colspan="2" style="padding:10px 0 18px 0;">
                        <input type="submit" value="提 交" /> <input type="reset" value="重 置" /><span id="msgdemo2" style="margin-left:30px;"></span>
                    </td>
                </tr>
                
            </table>
        </form>
        
         
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/Validform_v5.3.2.js"></script>

<script type="text/javascript">
$(function(){
	$.Tipmsg.r=null;
    var showmsg=function(msg){//假定你的信息提示方法为showmsg， 在方法里可以接收参数msg，当然也可以接收到o及cssctl;
    	$.messager.alert("提示消息",msg,"error");
	}
	$("#demoForm").Validform({
		tiptype:function(msg){
			showmsg(msg);
		},
		tipSweep:true,     //是否选择在input框失去焦点时弹出框，false表示弹出，true表示不弹出
		ajaxPost:true
	});
})
</script>
</body>
</html>