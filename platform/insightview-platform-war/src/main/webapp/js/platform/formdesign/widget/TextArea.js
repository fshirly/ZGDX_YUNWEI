/**
* 单行文本控件实现类
* 继承控件基类ISV.formd.base.Widget
* 郑自辉
*/
f.namespace('ISV.pf.fd.widget');
(function(w){
	w.TextArea = function(){
		//控件类型
		this.widgetType = 'TextArea';
		ISV.pf.fd.base.FormWidget.apply(this,arguments);
		
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
			$('#validator').combobox({
						url : f.contextPath
								+ '/resource/fdSysValidateRule/queryAllValidate',
						valueField : 'id',
						textField : 'validatorName',
						editable : false,
						value : 1
			});
			$('#validator').combobox({value : $('#validator').val()});
		};
		
		/**
		 * 创建TextArea元素
		 */
		this.createWidget = function(data){
			var $textarea = $('<textarea rows="" cols=""></textarea>');
			$textarea.attr('id',data.id).attr('widgetType',data.widgetType);
			return $textarea;
		};
		
	};
	
	//注册此控件类型
	ISV.pf.fd.base.FormWidgetFactory.register('TextArea',w.TextArea);
})(ISV.pf.fd.widget);