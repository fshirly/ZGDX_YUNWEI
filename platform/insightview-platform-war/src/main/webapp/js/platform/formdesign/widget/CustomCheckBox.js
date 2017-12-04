/**
* 单行文本控件实现类
* 继承控件基类ISV.formd.base.Widget
* 郑自辉
*/
f.namespace('ISV.pf.fd.widget');
(function(w){
	w.CustomCheckBox = function(){
		//控件类型
		this.widgetType = 'CustomCheckBox';
        ISV.pf.fd.base.FormWidget.apply(this,arguments);
        
        this.customFillData = function(data) {
            $("#customInitValue option").remove();
             var customInitValues = JSON.parse(data.customInitValues);
             $.each(customInitValues,function(){
                    var id = this.id;
                    var label = this.text;
                    $("#customInitValue").append("<option value='" + id + "'>" + label + "</option>");
             });
        }
        
        this.customFormJSON = function(json) {
            var options = $('#customInitValue option');
            var customInitValues = [];
            options.each(function(){
                customInitValues.push({
                    id: this.value,
                    text: this.label
                });
            });
            json.customInitValues = JSON.stringify(customInitValues);
            return json;
        }
        
        /**
         * 属性界面渲染处理
         * 主要包含对特殊控件的渲染
         * 如easyui的控件
         */
        this.customOnReady = function(){
            $('#required').combobox({
                data: [
                        {
                            id: 1,
                            text: '是'
                        },
                        {
                            id: 0,
                            text: '否'
                        }
                ],
                valueField: 'id',
                value: 1
            });
            $('#isTableDisplay').combobox({
                data: [
                        {
                            id: 1,
                            text: '是'
                        },
                        {
                            id: 0,
                            text: '否'
                        }
                ],
                valueField: 'id',
                value: 1
            });
            $('#columnNum').combobox({
				data : [
				        {
				        	id : 1,
				        	text : '一列显示'
				        },
				        {
				        	id : 2,
				        	text : '两列显示'
				        }],
				        valueField: 'id',
						value: 1
			});
            $('#columnNum').combobox({value : $('#columnNum').val()});
            if ($('#required').val() != '') {
                $('#required').combobox('setValue',$('#required').val());
            }
            if ($('#isTableDisplay').val() != '') {
                $('#isTableDisplay').combobox('setValue',$('#isTableDisplay').val());
            }
        };
	
    	/**
    	 * 创建CustomCheckBox元素
    	 */
    	this.createWidget = function(data){
            var customInitValues = JSON.parse(data.customInitValues);
            var checkBoxes = '';
            for(var i=0; i<customInitValues.length; i++){
                checkBoxes += '<input type="checkbox">' + customInitValues[i].text + '</check>';
            }
            return $(checkBoxes);
    	};
	};
	//注册此控件类型
	ISV.pf.fd.base.FormWidgetFactory.register('CustomCheckBox',w.CustomCheckBox);
})(ISV.pf.fd.widget);