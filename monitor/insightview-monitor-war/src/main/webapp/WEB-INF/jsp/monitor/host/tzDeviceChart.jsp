<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
		<link href="${pageContext.request.contextPath}/style/css/sDashboard.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/style/css/reset.css" rel="stylesheet" />
	 <input type="hidden" id="moID" value="${moID}"/>
	 <input type="hidden" id="moClass" value="${moClass}"/>
	  <input type="hidden" id="timeBegin" value="${timeBegin}"/>
	   <input type="hidden" id="timeEnd" value="${timeEnd}"/>
		   <div id="timeBar" class="databbar">
			        <a   href="javascript:void(0);" onclick="getChartJsonData('24H');" class="line"><b>24h</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Today');" class="line"><b>今天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Yes');" class="line"><b>昨天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Week');" class="line"><b>本周</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('7D');" class="line"><b>7天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Month');" class="line"><b>本月</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('LastMonth');"><b>上月</b></a>	
				
			</div>
			<div id="availability_gauge" style="height:275px;width:100%;"></div>
				<div style="width:100%;text-align: center;position: absolute;top: 240px;">
				
					<p><b>设备IP:</b><a style="cursor: pointer;" onclick="javascript:toShowView('${moID}','${deviceIP}');">${deviceIP}</a></p>
					<p><b>告警数:</b><a style="cursor: pointer;" onclick="javascript:toShowAlarmList('${deviceIP}');">${alarmCount}</a></p>
					<p><b>CPU利用率:</b>${CPUsage}</p>
					<p><b>内存利用率:</b>${MemUsage}</p>
			
			</div>	
	
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script type="text/javascript">
		function getChartJsonData(time){
		var path = getRootPatch();
		var moID=$("#moID").val();
		var moClass=$("#moClass").val();
		var param = "?moID="+moID+"&moClass="+moClass+"&time="+time+"&t="+ Math.random();
		window.location.href=path + "/monitor/tzManage/findDeviceInfo"+param;
}
var myChart1 = echarts.init(document.getElementById('availability_gauge'));
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
			                	 color: [[0.1, '#BC1717'],[0.35,'#E4B116'],[1,'#3CB371' ]], 
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
			            data:[{value: ${avaiUsage}, name: '${nameValue}'}]
			        }
			    ]
		});
function toShowAlarmList(deviceIP){
	var timeBegin=$("#timeBegin").val();
	var timeEnd=$("#timeEnd").val();
	var param = "?tzDeviceIP="+deviceIP+"&tzTimeBegin="+timeBegin+"&tzTimeEnd="+timeEnd+'&tzType=tz'+"&t="+ Math.random();
	var url=getRootPatch() +'/monitor/alarmActive/toAlarmActiveList'+param;
	//alert("告警咯： "+url);
	var content = '<iframe id="alarmHost" name="alarmHost" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	var title = '告警列表';
	var isExistTabs = parent.parent.document.getElementById("tabs_window");
	var isPartentTabs = parent.document.getElementById("tabs_window");
	if(isPartentTabs != null){
		parent.$('#tabs_window').tabs('add',{
	    title:title,
	    content:content,
	    closable:true
	       });
	}else if(isExistTabs != null){
		parent.parent.$('#tabs_window').tabs('add',{
		    title:title,
		    content:content,
		    closable:true
		    });
	}else{
		window.parent.frames.location = url;
	}
}
</script>
</html>