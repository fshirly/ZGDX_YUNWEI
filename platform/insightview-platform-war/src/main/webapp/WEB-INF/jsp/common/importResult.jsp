<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                      
<html xmlns="http://www.w3.org/1999/xhtml">
	<head profile="http://www.w3.org/2005/10/profile">
		<title></title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
	</head>

	<body>
	<script type="text/javascript">
		function closeWin(){
			var type = $("#importType").val();
			$('#popWin').window('close');
			window.frames['component_2'].reloadTable();
			if (type == "userImportor") {
				window.frames['component_2'].initOrgTree();
			} else {
				window.frames['component_2'].initTree();
				window.frames['component_2']._initDeptCount = 0;
				window.frames['component_2'].initDeptTree();
			}
			
		}
	</script>
	<p>
		${resultInfo}
	</p>
		<input id="importType" type="hidden" value="${importType}"/>
		<br><input type="button" value="关闭" onclick="javascript:closeWin();"/> 	
	</body>
</html>
