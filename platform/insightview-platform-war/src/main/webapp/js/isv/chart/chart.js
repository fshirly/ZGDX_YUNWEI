/**
 * InsightView图表组件基类
 * 定义图表元素的基本属性
 * 
 * 郑自辉
 */
define(function(){
	/**
	 * 定义图表元素的基本属性
	 */
	var default_config = {
			id : '',
			name : 'chart-demo',
			title : '',
			url : ''
		};
	return {
		config : default_config
	};
});