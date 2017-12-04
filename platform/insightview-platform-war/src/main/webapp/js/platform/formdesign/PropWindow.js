/**
 * 控件属性模板窗口
 */
f.namespace('ISV.pf.fd');
ISV.pf.fd.PropWindow = (function(){
	$(function(){
		$('#propWindow_Submit').click(function(){
			if(checkInfo('#divPopPage')==true){
			    var opWidget = ISV.pf.fd.base.FormDesigner.opWidget;
			    var formJSON = opWidget.formJSON();
			    var isNew = opWidget.getIsNew();
			    var tdCount = $("#formLayout").combobox('getValue');
			    var flag = false;
			    if (isNew) {
				    ISV.pf.fd.base.FormDataModel.addNew(formJSON);
			    }
			    else {
				    ISV.pf.fd.base.FormDataModel.addUpdate(formJSON);
			    } 
			    var newFormJSON = jQuery.extend(true, {}, formJSON);
			    opWidget.propChange(newFormJSON);
			    $('#propWin').window('close');
			    $("#layout_tab td").each(function(){
				    if($(this).children().length ==0){
					    flag = true;
				    }
			    });
			    if(flag === false){
				    ISV.pf.fd.base.FormDesigner.add_row(tdCount);
			    }
			}
		});
		$('#propWindow_Cancel').click(function(){
			$('#propWin').window('close');
		});
	});
})();