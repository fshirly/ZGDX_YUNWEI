<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
		  
			<div id="currMemory_gauge" style="height:300px;width:100%;"></div>
			<div style="width:100%;text-align: center;position: absolute;top: 240px;">总内存:${heapSize}<br>使用内存:${heapUsed}</div>		
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script type="text/javascript"> 
var myChart1 = echarts.init(document.getElementById('currMemory_gauge'));
		myChart1.setOption({
			 tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    series : [
			        {
			            name:'性能指标',
			            type:'gauge',
			            splitNumber: 10,       // 分割段数，默认为5
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                	 color: [[0.75, '#3CB371'],[0.9,'#E4B116'],[1, '#BC1717']], 
			                    width: 8
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                splitNumber: 10,   // 每份split细分多少段
			                length :12,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                show: true,        // 默认显示，属性show控制显示与否
			                length :30,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            pointer : {
			                width : 5
			            },
			            title : {
			                show : true,
			                offsetCenter: [0, '-40%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder'
			                }
			            },
			            detail : {
			                formatter:'{value}%',
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto',
			                    fontWeight: 'bolder'
			                }
			            },
			            data:[{value: ${heapUsage}, name: '内存使用率'}]
			        }
			    ]
		});
</script>
</html>