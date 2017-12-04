<!-- 单个日期控件的绘制模版 -->
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input class="element easyui-datetimebox" id="${attribute.id}" name="${attribute.columnName}" elementType="datetimebox" value="${value }" data-options="editable : false" style="width:156px">
<script>
  $(document).ready(function() {
	  var showTime = '${attribute.showTime}';
	  if(showTime == 2){
		  $('#${attribute.id}').datetimebox("setValue","9999");
	  }
  });
</script>