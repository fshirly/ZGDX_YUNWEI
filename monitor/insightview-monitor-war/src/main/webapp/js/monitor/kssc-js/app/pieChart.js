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
		stillShowZeroSum:false,
		tooltip:{
			trigger:'item',
			formatter: function (a,b,c,d) {
				var  html = "<div>"+a+"</div>"
					+b+"<span>"+c+"%</span><span>("+d+"%)</span>"
				return html;
				//"{a} <br/>{b} : {c} ({d}%)"
			}
		},
		legend:{
			orient : 'vertical',
        	x:'right',
        	y:'bottom',
        	textStyle:{
        		color:'#666',
        		fontSize:12
        	},
        	data:[
        		{
        			"name":"已使用空间",
        			"icon":"<span style='width:20px;height:10px;float:left!important'></span>"
        		},
        		{
        			"name":"未使用空间",
        			"icon":"<span style='width:20px;height:10px;float:left!important'></span>"
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
	}
	return option;
})

