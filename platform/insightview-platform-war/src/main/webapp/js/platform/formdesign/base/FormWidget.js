/**
* 表单设计器控件基类
* 定义控件的基本属性和共有操作
* 所有具体控件均需继承此类
* 郑自辉
*/
f.namespace('ISV.pf.fd.base');
ISV.pf.fd.base.FormWidget = function(){
	/**
	 * 定义基类中的属性
	 * 这些属性定义为私有变量，外部不可直接访问，只能通过get和set方法访问
	 */
	var _id = new Date().getTime(),
		_isNew = false,
		_container = null,
		_widgetTypeData = {};
	/**
	 * 模板窗口的URL
	 * 用于展示控件属性
	 * 大部分控件属性窗口采用此模板，若采用不同的模板，子类需重写此属性
	 */
	this.windowUrl = '/platform/form/prop/window';
	
	/**
	 * 控件对应HTML的生成方法
	 * 由子类实现，只创建对应的控件元素，不包含<label/>
	 */
	this.createWidget = function(data){
		throw new Error("This method must be override by sub widget!");
	};
	
	this.customRender = function(dom){
		//子类有选择性的实现此函数
	};
	
	/**
	 * 属性界面窗口加载完后的初始化
	 * 由子类实现
	 */
	this.customOnReady = function(){
		//子类有选择性的实现此函数
	};
	
	/**
	 * 通用的fillData()函数之外，需要额外处理的
	 * 填充逻辑由子类实现
	 */
	this.customFillData = function(data) {
		//子类有选择性的实现此函数
	};
	
	/**
	 * 通用的formJSON()函数之外，需要额外处理的
	 * 组装JSON逻辑由子类实现
	 */
	this.customFormJSON = function(json) {
		//子类有选择性的实现此函数
		return json;
	}
	
	/**
	* 控件设计的入口程序
	* 该方法可看做为一个模板方法
	* 包含控件元素的创建和属性窗口的弹出
	*/
	this.design = function(){
		this.popup();
	};
	
	/**
	* 创建控件元素
	*/
	this.create = function(data){
		var $container = this.getContainer();
		var id = this.getId();
		var widgetType = this.getWidgetType();
		var isNew = this.getIsNew();
		var imgEditId = data.id + '_img_edit';
		var imgDelId = data.id + '_img_del';
		
		var $div = $('<div/>');
		var $label = $('<label class="label"/>');
		var $span = $('<span style="display:inline" class="imgSpan"></span>');
		var $imgEdit = $('<img onclick="ISV.pf.fd.base.FormDesigner.edit_click(\''+ id +'\')" id='+ imgEditId +' class="opImg" style="cursor:pointer;visibility:hidden;" src='+f.contextPath+'/style/images/icon/icon_modify.png />');
		var $imgDel = $('<img onclick="ISV.pf.fd.base.FormDesigner.del_click(\''+ id +'\')" id='+ imgDelId +' class="opImg" style="cursor:pointer;visibility:hidden;" src='+f.contextPath+'/style/images/icon/icon_delete.png />');
		$label.text(data.attributesLabel + ':');
		$div.append($label);
		
		var $dom = this.createWidget(data);
		$div.append($dom);
		$span.append($div);
		$div.append($imgEdit);
		$div.append($imgDel);
		$container.append($span);
		this.customRender($dom,data);
		
		var widget = ISV.pf.fd.base.FormWidgetFactory.newWidget(widgetType);
		widget.setId(id);
		widget.setIsNew(isNew);
		widget.setContainer($container);
		widget.setWidgetTypeData(data);
		ISV.pf.fd.base.FormDesigner.cache[id] = widget;
	};
	
	/**
	 * 从前台数据缓存池中获取数据填充控件属性
	 * 通用的填充表单属性实现
	 */
	this.fillData = function(){
		var id = this.getId();
		var isNew = this.getIsNew();
		var json = ISV.pf.fd.base.FormDataModel.getAttr(id,isNew);
		if (!json) {
			return;
		}
		var formElements = $('#propWindow_Form .element');
		$.each(formElements,function(i,e){
			var name = $(e).attr('name');
			var elementType = $(e).attr('elementType');
			switch(elementType) {
				case 'normal':
					$('#' + name).val(json[name]);
					break;
				case 'combobox':
					name = $(e).attr('comboname');
					$('#' + name).combobox('setValue',json[name] )
					break;
				case 'select2':
					$('#' + name).select2('val',json[name] )
					break;
				default:
					$('#' + name).val(json[name]);
			}
		});
		this.customFillData(json);
	};
	
	/**
	 * 组装属性界面表单对应的JSON格式数据
	 * 不同的控件类型JSON可能也不同
	 * 通用的JSON组装实现
	 */
	this.formJSON = function(){
		var formElements = $('#propWindow_Form .element');
		var that = this;
		var json = {
				id: that.getId(),
				widgetType: that.getWidgetType(),
				dataType: that.getWidgetTypeData()['dataType'],
				datalength: that.getWidgetTypeData()['datalength'],
				editUrl: that.getWidgetTypeData()['editUrl'],
				viewUrl: that.getWidgetTypeData()['viewUrl'],
				seq: that.getContainer().attr('seq')
		};
		$.each(formElements,function(i,e){
			var name = $(e).attr('name');
			var elementType = $(e).attr('elementType');
			if (!elementType){
				return;
			}
			switch(elementType) {
				case 'normal':
					json[name] = $('#' + name).val();
					break;
				case 'combobox':
					name = $(e).attr('comboname');
					json[name] = $('#' + name).combobox('getValue');
					break;
				case 'select2':
					json[name] = $('#' + name).select2('val');
					break;
				default:
					json[name] = $('#' + name).val();
			}
		});
		return this.customFormJSON(json);
	};
	
	/**
	 * 填充控件属性
	 * 一般在控件属性加载完后执行
	 * 包括easyui控件的渲染、属性值的初始化等
	 */
	this.fillForm = function(){
		this.customOnReady();
		this.fillData();
	};
	
	/**
	* 弹出属性编辑窗口
	* 根据控件的propUrl加载相应的属性界面
	*/
	
	this.popup = function(){
		var that = this,
			attrId = that.getIsNew() === true ? 0 : that.getId(),
			widgetType = that.getWidgetType(),
			formId = $('#formId').val() == '' ? 0 : $('#formId').val();
		$('#propWin').window({
				title:'控件属性编辑窗口',
				width:470,
				height:420,
				minimizable:false,
				draggable : false,
				maximizable:false,
				collapsible:false,
				inline : true,
				modal:true,
				href: f.contextPath + that.windowUrl + '?attrId=' + attrId + '&widgetType=' + widgetType + '&formId=' + formId,
				onLoad: function() {
					that.fillForm();
				}
			});
	};
	/**
	* 属性保存之后的控件刷新
	* 每种控件都需要重写此方法
	* 处理属性保存之后控件的界面刷新
	* 比如属性ID、控件类型、name属性的更新等
	*/
	this.propChange = function(data){
		//先清空原来渲染的元素
		this.getContainer().html('');
		//根据属性数据重新创建
		this.create(data);
	};
	
	/**
	* 设置控件元素的ID
	* 拖拽新增时可用当前时间戳表示
	*/
	this.setId = function(id){
		_id = id;
		return this;
	};
	/**
	 * 返回控件ID
	 */
	this.getId = function(){
		return _id;
	};
	/**
	* 是否为新增加的属性
	* 如果该属性是由拖拽控件生成，则以后的操作isNew均为true
	* 如果该属性之前已经存在，是从库中读取渲染，则之后的操作均为false
	*/
	this.setIsNew = function(isNew){
		_isNew = isNew;
		return this;
	};
	/**
	 * 返回是否为新元素标识
	 */
	this.getIsNew = function()
	{
		return _isNew;
	};
	/**
	* 设置控件元素的容器元素
	* 该容器用于存放生成的控件元素
	*/
	this.setContainer = function(container){
		_container = container;
		return this;
	};
	/**
	 * 返回控件容器
	 */
	this.getContainer = function(){
		return _container;
	};
	
	/**
	* 获取控件类型
	*/
	this.getWidgetType = function(){
		return this.widgetType;
	};
	
	/**
	 * 返回控件类型数据
	 */
	this.getWidgetTypeData = function() {
		return _widgetTypeData;
	};
	
	/**
	 * 保存控件类型数据
	 */
	this.setWidgetTypeData = function(widgetTypeData) {
		_widgetTypeData = widgetTypeData;
		return this;
	}
};