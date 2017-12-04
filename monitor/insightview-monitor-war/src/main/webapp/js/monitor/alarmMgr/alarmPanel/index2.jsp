<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Insert title here</title>  
<script type="text/javascript" src="ajax-pushlet-client.js"></script>  
<script type="text/javascript">  
    PL.userId = 'twoseven';  
    PL.joinListen('notifynum');
    function onData(event) {
        document.getElementById("number").innerHTML  = event.toString();  
    }  
</script>  
</head>  
<body>  
    <div id="number">123</div>  
</body>  
</html>