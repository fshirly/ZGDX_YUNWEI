<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input id="${attribute.id}" class="element" value="${value }" name="${attribute.columnName}" elementType="combobox" style="width:156px"/>
<script type="text/javascript">
   $(function(){
         $('#${attribute.id}').combobox({
            editable : false,
            panelHeight : 120,
            data : JSON.parse('${attribute.customInitValues}'),
            valueField: 'id'
         });
    });
</script>
