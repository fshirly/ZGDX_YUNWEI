/**
 * 2d饼图
 * 配置参数
 * {
 * 		id:DOM元素ID，用于生成渲染仪表盘，一般为<div>
 * 		title:仪表盘的名称
 * 		url:生成仪表盘数据的后台链接
 * 		legend:{
 * 			show:是否显示图例
 * 			location:图例的位置,nw, n, ne, e, se, s, sw, w
 * 		}
 * 		dataLabel:数据展示方式,percent按百分比展示，这时传入的数据为原始数据，图表组件自动计算百分比，value展示原始数据
 * 		dataLabelFormat:数据展示格式化，如'%.2f%'保存两位小数的百分比
 * }
 * 
 * 郑自辉
 */
(function(){
	define(['jquery','util/util','jqPlot','pieRender'],function($,util){
		var pie = function(config){
			var id = config.id;
			var url = util.getContextPath() + config.url;
			var legend = config.legend || {show : true,location : 's'};
			var dataLabel = config.dataLabel || 'percent';
			var dataLabelFormat = config.dataLabelFormat || '%.2f%';
			$.get(url,function(data){
				var pieModel = '[';
				var series = data.series;
				$.each(series,function(i,s){
					var sModel = '[';
					var points = s.points;
					$.each(points,function(j,p){
						var pModel = '[';
						pModel += p.xValue === null ? '""' : '"' + p.xValue + '"';
						pModel += ',';
						pModel += p.yValue;
						pModel += ']';
						sModel += pModel;
						if (j != points.length - 1)
						{
							sModel += ',';
						}
					});
					sModel += ']';
					pieModel += sModel;
					if (i != series.length - 1)
					{
						pieModel += ',';
					}
					pieModel += ']';
				});
				if(pieModel != "["){
					$.jqplot(id, JSON.parse(pieModel), {
						gridPadding: {top:0, bottom:38, left:0, right:0},
						seriesDefaults:{
							renderer:$.jqplot.PieRenderer, 
							trendline:{ show:false }, 
							rendererOptions: { 
								padding: 8, 
								showDataLabels: true,
								dataLabels : dataLabel,
								dataLabelFormatString : dataLabelFormat
							}
						},
						legend:{
							show:legend.show,
							rendererOptions: {
								numberRows: 1
							}, 
							location:legend.location,
							marginTop: '15px'
						},
						grid:{
							background : 'white',
							borderWidth : 0,
							shadow : false
						}
					});
				}
			},'json');
		};
		return pie;
	});
})();