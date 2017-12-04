<!-- Radio单选框控件 -->
<div id="${attribute.id }_div"><label class="label">${attribute.attributesLabel}: </label></div>
<script type="text/javascript">
	$(function(){
		var customInitValues = JSON.parse('${attribute.customInitValues}');
		$.each(customInitValues,function(){
			var id = this.id;
			var label = this.text;
			var $radio = $('<input type="radio" elementType="radio" value="'+ id + '"/>');
			$radio.attr('name','${attribute.columnName}');
			$radio.addClass('element');
			$('#${attribute.id}_div').append($radio);
			$('#${attribute.id}_div').append('<label>'+ label +'</label>');
			var value = '${value}';
			if (id == value) {
				$radio.attr('checked',true);
			} else {
				$radio.attr('checked',false);
			}
		});
	});
</script>