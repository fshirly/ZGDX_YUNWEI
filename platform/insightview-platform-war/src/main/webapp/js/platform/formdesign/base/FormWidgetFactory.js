/**
* 控件工厂
* 注册控件对象、生成控件对象
* 郑自辉
*/
f.namespace('ISV.pf.fd.base');
ISV.pf.fd.base.FormWidgetFactory = {
	//存放控件类型的容器
	//属性为控件类型，值为空间类型对应的Constructor
	widgets : {},
	
	/**
	* 注册控件类
	* wType为控件类型
	* wConstructor为控件类构造函数
	*/
	register : function(wType,wConstructor){
		this.widgets[wType] = wConstructor;
	},
	
	/**
	* 返回指定类型的控件对象
	* wType为控件类型
	* 生成的控件对象自动含有当前时间戳作为ID
	*/
	newWidget : function(wType){
		var WigetConstructor = this.widgets[wType];
		var widget = new WigetConstructor();
		return widget;
	}
};