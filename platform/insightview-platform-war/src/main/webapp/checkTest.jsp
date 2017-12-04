<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校验测试页面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/style_one/css/global.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
</head>
<body>
  <div>
              邮箱格式校验：<input type="text" id="mail"/>
              <button onclick="isEmail($('#mail').val());">校验邮箱</button>
    <br />
              手机号码校验：<input type="text" id="phoneNum" /> 
              <button onclick="checkPhoneNum($('#phoneNum').val())">手机号码校验</button>
     <br />
                验证输入只能为汉字：<input type="text" id="wordStr" />
                   <button onclick="isChn($('#wordStr').val())">校验</button>

      <br />
                  输入的只能是字母和汉字：<input type="text" id="Str" />
                      <button onclick="isEnAndNum($('#Str').val())" >校验</button>
      <br />
                   开始时间：<input type="text" id="startDate" />
                   结束时间：<input type="text" id="endDate" />
             <button onclick="checkTwoDate(document.getElementById('startDate').value,document.getElementById('endDate').value)" >检查时间段</button>                
      </div>
</body>
</html>