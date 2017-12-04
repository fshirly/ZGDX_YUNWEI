/**
 * 3D饼图
 * 配置参数
 * {
 * 		id:DOM元素ID，用于生成渲染仪表盘，一般为<div>
 * 		title:仪表盘的名称
 * 		url:生成仪表盘数据的后台链接
 * 		radius:3D饼图的半径
 * }
 * 
 * 郑自辉
 */
(function(){
	define(['jquery','raphael','/util/util','raphael-charts'],function($,Raphael,util){
		var pie3d = function(config)
		{
			var id = config.id;
			var radius = config.radius || 100;
			var canvasX = 320;
			var canvasY = 200;
			var paper1 = Raphael(id, canvasX, canvasY);	
		    paper1.pie(
	        canvasX, 
	        canvasY, 
	        radius, 
	        50, 
	        [10,20,30,15], 
		    	{
	          colors: ["#c00","#0c0","#cc0", "#00c"], 
	          labels: ["First", "Second", "Third", "Forth"],
	          show3d: true,
	          animation: true,
	          legend: true,
		    	  legendContainerCssClass: "pie-legend-container", 
		    	  legendLineColor: "#AAAAAA",
	          legendTextColor: "#000",
	          legendFont: "12px Arial",  
		    	  legendLabelCssClass: "pie-legend-label",
	          legendShowValues: "percentage"
	        }
	      );
		};
		return pie3d;
	});
})();