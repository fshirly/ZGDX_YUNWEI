<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/ajaxFileUpload.js"></script>

<title>listview导入</title>
</head>
<script type="text/javascript">

var listviewName = "listviewManager";

	function importData() {

		$.ajaxFileUpload({
			fileElementId : 'file',
			url : "../../listviews/import",
			dataType : 'json',
			//contentType : "application/json",
			secureuri : false,
			beforeSend : function(XMLHttpRequest) {
				//("loading");  
				return $("#importFrom").form('validate');
			},
			success : function(data, status) {

				if (data.success) {
					window.top.$.messager.alert("提示信息", data.message, 'success',
							function() {
								/* closeModalWinoOff();
								if(parent.query != undefined){
									parent.query(1,10);	
								}else{
									refreshListview(listviewName);
								} */
								closeModalWindow(true);
							});

				} else {
					window.top.$.messager.alert("错误信息", data.errorCode + ":"
							+ data.message,"error");
				}
			},
			error : function(data, status, e) {
				show_error(result);
			}
		});
		//document.getElementById("importFrom").submit();

	}
</script>
<body>

	<form id="importFrom" method="post" action="saveImport"
		enctype="multipart/form-data">
		<div class="content" style="height: 100px; margin: 10px0 padding: 10px;;">
				<!-- <td class="title">数据文件:</td> -->
				<input type="file" id="file" name="file"
					class="easyui-validatebox"
					data-options="required:true,validType:'EMPTY'"> 
					<dfn>*</dfn>
		</div>
	</form>
	
	<div class="dialog-button">
		<a href="javascript:void(0)" onclick="importData()">确定</a>
		<a href="javascript:void(0)" onclick="closeModalWindow()">取消</a>
	</div>

</body>


</html>