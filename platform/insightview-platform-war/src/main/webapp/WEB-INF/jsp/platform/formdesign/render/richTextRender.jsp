<!-- RichText 富文本控件 -->
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<textarea id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="normal" style="width:450px" rows="5">${value }</textarea>
<script type="text/javascript">
	$('#${attribute.id}').richtext({
		height : 140
	});
</script>