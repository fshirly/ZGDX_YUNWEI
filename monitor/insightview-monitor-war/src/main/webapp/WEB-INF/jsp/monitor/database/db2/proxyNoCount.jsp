<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
<head> 
</head>
<body>
	<div>
		<table style="border:1px solid #A2C0DA;text-align: center;">
   				<tr >
   					<c:forEach items="${proxyName_jsp}" var="vo">
   					<td style="width:60px;height:25px;background:#CBE2FB;border:1px solid white; vertical-align:top">
						<c:out value="${vo}" /> 
					</td>
   					</c:forEach>
   				</tr>
   				<tr>
	   				<c:forEach items="${proxyData}" var="vo">
	   					<td style="height:25px">
							<c:out value="${vo}" />
						</td>
	   				</c:forEach>
   				</tr>
   			</table>    		
	</div>
    <div id="proxy_pie" style="height:200px;width:550px; border:1px solid #ccc;padding:10px;margin-top:2px;"></div> 
</body>
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script type="text/javascript">   
    var myChart = echarts.init(document.getElementById('proxy_pie'));
     myChart.setOption({
      title : {
        text: '代理数统计',
        subtext: '',
        x:'right'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:[${proxyName}]
    },
    calculable : true,
    //color:[${levelColor}],//颜色不协调，暂时注掉
    series : [
        {
            name:'代理数统计',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
               ${proxyValue}
            ]
        }
    ]
    });
</script>
</html>