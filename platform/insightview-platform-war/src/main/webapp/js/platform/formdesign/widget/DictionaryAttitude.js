/**
* 单行文本控件实现类
* 继承控件基类ISV.formd.base.Widget
* 郑自辉
*/
f.namespace('ISV.pf.fd.widget');
(function(w){
	w.DictionaryAttitude = function(){
		//控件类型
		this.widgetType = 'DictionaryAttitude';
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
			
			$('#item').createSelect2({
				uri : '/dict/getSysConstantList',
				name : 'constantTypeCName',
				id : 'constantTypeId',
				initVal :{item:$("#item").attr("value")}
			});
		};
		
		/**
		 * 自定义字典属性的initSQL
		 */
		this.customFormJSON = function(json){
			json.initSQL = 'select constantitemid id,constantitemname text from SysConstantItemDef where constanttypeid=' + json.item;
			return json;
		};
		
		/**
		 * 创建DictionaryAttitude元素
		 */
		this.createWidget = function(data){
			var $dictionaryattitude = $('<input/>');
			$dictionaryattitude.attr('id',data.id).attr('widgetType',data.widgetType);
			return $dictionaryattitude;
		};
		
		this.customRender = function($dom,data){
			$dom.combobox({
				editable : false,
				url: f.contextPath + '/platform/form/prop/value/init',
				onBeforeLoad: function(param) {
					param.initSQL = data.initSQL;
				},
				valueField: 'id'
			});
	    };
	};
	
	//注册此控件类型
	ISV.pf.fd.base.FormWidgetFactory.register('DictionaryAttitude',w.DictionaryAttitude);
})(ISV.pf.fd.widget);