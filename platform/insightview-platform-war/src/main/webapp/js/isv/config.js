/**
 * Require JS的通用配置
 * 包含baseUrl,paths,shim等
 * 同RequireJS一起使用，并且requireJS后加载
 * 
 * 郑自辉
 */
(function(){
	function getContextPath(){
		// 取得主机地址后的目录
		var pathName = window.document.location.pathname;
		// 取得项目名
		var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
		return name;
	}
	require.config({
		/**
		 * RequireJS加载文件时的基本路径，其他路径都相对此路径
		 */
		baseUrl : getContextPath() + '/js/isv',
		/**
		 * 定义每个模块加载的文件路径，相对于baseUrl
		 */
		paths : {
			jquery : 'common/jquery-1.9.1.min',
			'sonic-gauge' : 'third/sonicgauge/jquery.sonic-gauge.min',
			raphael : 'third/raphael/raphael-min',
			jqPlot : 'third/jqplot/jquery.jqplot',
			pieRender : 'third/jqplot/plugins/jqplot.pieRenderer.min',
			css : 'common/css.min',
			excanvas : 'common/excanvas.min',
			'raphael-charts' : 'third/raphael/raphael-charts-0.2.1'
		},
		/**
		 * 对于第三方的非AMD规范的类库，可以使用此方式定义Module，然后使用RequireJS加载
		 */
		shim : {
			/**
			 * 仪表盘使用
			 */
			'sonic-gauge' : {
				deps : ['jquery','raphael'],
				exports : 'SonicGauge'
			},
			/**
			 * 仪表盘使用
			 */
			'raphael' : {
				exports : 'Raphael'
			},
			/**
			 * jqPlot图表
			 */
			'jqPlot' : {
				deps : ['jquery','excanvas','css!third/jqplot/jquery.jqplot.min.css'],
				exports : '$.jqplot'
			},
			/**
			 * jqPlot饼图
			 */
			'pieRender' : {
				deps : ['jqPlot'],
				exports : '$.jqplot.PieRenderer'
			},
			/**
			 * 3d饼图
			 */
			'raphael-charts' : {
				deps : ['raphael','css!third/raphael/raphael-charts.css']
			}
		}
	});
})();