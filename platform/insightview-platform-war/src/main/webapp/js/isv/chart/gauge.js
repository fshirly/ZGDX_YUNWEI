/**
 * 仪表盘
 * 配置参数
 * {
 * 		id:DOM元素ID，用于生成渲染仪表盘，一般为<div>
 * 		title:仪表盘的名称
 * 		min:仪表盘最小的刻度值
 * 		max:仪表盘最大的刻度值
 * 		url:生成仪表盘数据的后台链接
 * 		size:仪表盘的大小，默认210
 * }
 * 
 * 郑自辉
 */
(function(){
	define(['jquery','chart/chart','util/util','sonic-gauge'],function($,chart,util){
		var gauge = function(config)
		{
			config = config || chart.config;
			var id = config.id;
			var title = config.title;
			var min = config.min;
			var max = config.max;
			var url = util.getContextPath() + config.url;
			var size = config.size || 210;
			
			var sGauge = $('#' + id).SonicGauge ({
				label	: title,
				start	: {angle: -225, num: min},
				end		: {angle: 45, num: max},
				diameter : size,
				digital	: {
					"width"				: "16%",
					"font-size"			: 10,
					"color"				: '#333',
					"text-align"		: "center",
					"border-radius"		: 15,
					"padding"			: 5,
					"background-color"	: "#e6f0f8",
					"margin-top"		: 40
				},
				animation_speed	: 500,
				markers	: [
					{
						gap: 20,
						line: {"width": 20, "stroke": "none", "fill": "#eeeeee"},
						text: {"space": 22, "text-anchor": "middle", "fill": "#333333", "font-size": 18}
					},{
						gap: 10, 
						line: {"width": 12, "stroke": "none", "fill": "#aaaaaa"},
						text: {"space": 18, "text-anchor": "middle", "fill": "#333333", "font-size": 14}
					},{
						gap: 5, 
						line: {"width": 8, "stroke": "none", "fill": "#999999"}
					}
				],
				needles : [{ style: { height: 2} } ],
				sectors : [
					{
						start: 0,
						end: 20,
						style: {fill: "rgba(0,0,255,.5)", stroke: "blue", "stroke-width": 0}
					},{
						start: 20,
						end: 40,
						style: {fill: "rgba(90,200,220,.5)", stroke: "cyan", "stroke-width": 0}
					},{
						start: 40,
						end: 60,
						style: {fill: "rgba(50,220,50,.5)", stroke: "cyan", "stroke-width": 0}
					},{
						start: 60,
						end: 80,
						style: {fill: "rgba(250,230,70,.5)", stroke: "yellow", "stroke-width": 0}
					},{
						start: 80,
						end: 100,
						style: {fill: "rgba(255,0,0,.5)", stroke: "red", "stroke-width": 0}
					}
				],
				style	: {
					"outline"	: {"fill": "#dedede", "stroke": "#333", "stroke-width": 2},
					"center"	: {"fill": "#ae1e1e", "diameter": 8, "stroke": "#590303", "stroke-width": 1},
					"needle"	: {"fill": "#000"},
					"label"		: {"fill": "#000", "font-size": 14}
				}
			});
			$.get(url,function(data){
				sGauge.SonicGauge ('val', data.value);
			},'json');
		};
		return gauge;
	});
})();