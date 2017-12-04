<!-- 字典属性的绘制模版 -->
<label class="label">${attribute.attributesLabel}: </label> </div><div class="div_right">
<input id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="combobox" value="${value }" style="width:156px;"/>
<script type="text/javascript" class="script">
	$(document).ready(function() {
		$('#${attribute.id}').combobox({
			editable : false,
			url: f.contextPath + '/platform/form/prop/value/init',
			onBeforeLoad: function(param) {
				param.initSQL = '${attribute.initSQL }';
			},
			valueField: 'id'
		});
	});
</script>