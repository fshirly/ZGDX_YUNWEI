/**
* 表单控件实现类
* 继承控件基类ISV.formd.base.Widget
* 赵元鹏
*/
f.namespace('ISV.pf.fd.widget');
(function(w){
	w.PersonSelect = function(){
		//控件类型
	    this.widgetType = 'PersonSelect';
		
		ISV.pf.fd.base.FormWidget.apply(this,arguments);
		
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
			$('#validator').combobox({
						url : f.contextPath
								+ '/resource/fdSysValidateRule/queryAllValidate',
						valueField : 'id',
						textField : 'validatorName',
						editable : false,
						value : 1
			});
			$('#validator').combobox({value : $('#validator').val()});
			$('#initSQL').combobox(
					{
						url : getRootName()
								+ '/platform/form/readItems?widgetType=System_Combotree',
						valueField : 'initSQL',
						width: 182,
						textField : 'attributeLabel',
						editable : false,
					});
		};
		/**
		 * 系统属性（下拉树）的initSQL
		 */
		this.customFormJSON = function(json){
			json.initSQL = $('#initSQL').combobox('getValue');
			return json;
		};

		this.createWidget = function(data){
			var $PersonSelect = $('<textarea onclick=""></textarea>');
			$PersonSelect.attr('id',data.id).attr('widgetType',data.widgetType);
			return $PersonSelect;
		};
	};
	//注册此控件类型
	ISV.pf.fd.base.FormWidgetFactory.register('PersonSelect',w.PersonSelect);
})(ISV.pf.fd.widget);