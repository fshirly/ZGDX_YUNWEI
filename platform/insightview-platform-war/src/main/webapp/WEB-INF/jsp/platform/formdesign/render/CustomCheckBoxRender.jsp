<div id="${attribute.id }_div"><label class="label">${attribute.attributesLabel}: </label></div>
<script type="text/javascript">
    $(function(){
        var customInitValues = JSON.parse('${attribute.customInitValues}');
        $.each(customInitValues,function(){
            var id = this.id;
            var label = this.text;
            var $chkbox = $('<input type="check" elementType="normal" value="'+ id + '"/>');
            $chkbox.attr('name','${attribute.columnName}');
            $chkbox.addClass('element');
            $('#${attribute.id}_div').append($chkbox);
            $('#${attribute.id}_div').append('<label>'+ label +'</label>');
            var value = '${value}';
            if (id == value) {
                $chkbox.attr('checked',true);
            }
        });
    });
</script>