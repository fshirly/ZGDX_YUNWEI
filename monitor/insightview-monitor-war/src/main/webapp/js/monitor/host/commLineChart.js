function showChartLine(chartId){
	//显示拆线图
    var myChart1 = echarts.init(document.getElementById(chartId));
    myChart1.setOption({
    	title : {
    	x:'center',
        text: dataArr[0],
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
    	y:'bottom',
        data:dataArr[1]
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : dataArr[2]
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],
    grid: {
   		x : '10%',
     	height : '30%'
     }, 
    series : dataArr[3]     
    
    });
}