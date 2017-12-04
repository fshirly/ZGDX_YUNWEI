<!-- 文件上传的绘制模版 -->
<label id="fileiploadLabel" class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input id="${attribute.id}" name="${attribute.columnName}" type="file" class="element" elementType="file" style="width: 156px"/>
	<script type="text/javascript">
	$(document).ready(function() {
			var name = $('#${attribute.id}').attr('name');
			$('#${attribute.id}').f_fileupload(
					{
						whetherPreview : false,
						fileFormat : "['jpg', 'png', 'icon', 'gif', 'doc', 'docx']",
						filesize : 1000,
					});
			$('#${attribute.id}').attr('name',name);
	});
	
	</script>