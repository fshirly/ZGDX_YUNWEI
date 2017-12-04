<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
		  
			<div id="rollback_pie" style="height:400px;width:550px;"></div>		
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script type="text/javascript"> 
var myChart1 = echarts.init(document.getElementById('rollback_pie'));
		myChart1.setOption({
		  title : {
		    text: 'JTA事务回滚原因分类',
		    subtext: '',
		    x:'center'
		},
		tooltip : {
		    trigger: 'item',
		    formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
		    orient : 'vertical',
		    x : 'left',
		    data:[${rollName}]
		},
		calculable : true,
		series : [
		    {
		        name:'JTA事务回滚原因分类',
		        type:'pie',
		        radius : '55%',
		        center: ['50%', '60%'],
		        data:[
		           ${rollLstNumJson}
		        ]
		    }
		]
		});
</script>
</html>