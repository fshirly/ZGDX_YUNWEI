define(function(){
	var option ={
		color:["#ff8808","#00489d"],
		title:{
			text:'天翼存储云使用情况：',
			 x:'left',
			 textStyle:{
			 	fontSize:16,
			 	color:'#00479d'
			 }
		},
		tooltip:{
			trigger:'item',
            formatter: function (a,b,c,d) {
                var name  = a.name.split("(")[0];
                var  html = "<div>"+a.seriesName+"</div>"
                    +name+"：<span>"+a.value+"T</span><span>("+a.percent+"%)</span>";
                return html;
            }
		},
        stillShowZeroSum:false,
		legend:{
			orient : 'vertical',
            right:0,
            bottom:0,
            itemWidth:15,
            itemHeight:15,
        	textStyle:{
        		color:'#666',
        		fontSize:12
        	},
        	data:[
        		{
        			"name":"已使用空间",
        			"icon":"<span style='width:10px;height:10px;float:left!important'></span>"
        		},
        		{
        			"name":"未使用空间",
        			"icon":"<span style='width:10px;height:10px;float:left!important'></span>"
        		}
        	]
		},
		series:[
			{
				name:"天翼云使用情况",
				type:'pie',
	            radius : '55%',
	            center: ['50%', '50%'],
	            data:[
	            	{value:300,name:"已使用空间"},
	            	{value:600,name:"未使用空间"}
	            ]
			}
		]
	};
	return option;
});

