<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
<head> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmstatis/alarmStatis_list.js"></script>
</head>
<body>
    <div class="rightContent">
 		 <div class="location">当前位置：${navigationBar}</div>
  		 <div class="conditions" id="divFilter">
		 <table >
			<tr>
			<td>
				<b>告警状态：</b>
				<select id="alarmStatus" name="alarmStatus" class="easyui-combobox">
					<option value="" <c:if test="${alarmVo.alarmStatus==0}">selected="selected"</c:if>>全部</option>				    				    	
					<option value="21" <c:if test="${alarmVo.alarmStatus==21}">selected="selected"</c:if>>未确认</option>					
					<option value="22" <c:if test="${alarmVo.alarmStatus==22}">selected="selected"</c:if>>已确认</option>
					<option value="23" <c:if test="${alarmVo.alarmStatus==23}">selected="selected"</c:if>>已派发</option>
				</select>
			</td>
			<td>
				<b>显示方式： </b>
				<select id="viewPic" name="viewPic" class="easyui-combobox">
					<option value="1" <c:if test="${alarmVo.viewPic==1}">selected="selected"</c:if> >饼图显示</option>
				    <option value="2" <c:if test="${alarmVo.viewPic==2}">selected="selected"</c:if> >柱状图显示</option>
				</select>
			</td>
			<td>
				<b>首次发生时间：</b>
				<input class="easyui-datetimebox" id="timeBegin" name="timeBegin" value="${alarmVo.timeBegin}"/> 
				- <input class="easyui-datetimebox" id="timeEnd" name="timeEnd" value="${alarmVo.timeEnd}"/>
			</td>
			<td class="btntd">
				<a href="javascript:reloadTable();"  >查询</a>		
				<a href="javascript:void(0);" onclick="resetFilterForm('divFilter');">重置</a>
			</td>
		</tr>
	</table>
	</div>
		
		<div class="datas" style="overflow: auto;">
    		<div>
    			<b><span>告警统计--按级别</span></b><br/>&nbsp;
    			<table style="border:1px solid #A2C0DA;text-align: center;">
    				<tr>
    					<c:forEach items="${levelLstNum}" var="levelvo">
    					<td style="width:85px;height:25px;background:
    						<c:out value='${levelvo.levelColor}' /> 
    					 	;border:1px solid white; vertical-align:top">
							<c:out value="${levelvo.alarmLevelName}" /> 
						</td>
    					</c:forEach>	
    				</tr>
    				<tr >
    					<c:forEach items="${levelLstNum}" var="levelvo">
    					<td style="height:25px">
							<a style="cursor: pointer;" onclick="javascript:toAlarm('level',${levelvo.alarmLevelID})"><c:out value="${levelvo.totalNum}" /> </a>
						</td>
    					</c:forEach>	
    				</tr>
    			</table>
    			<br/>
    			<b><span>告警统计--按类型</span></b><br/>&nbsp;
    			<table style="border:1px solid #A2C0DA;text-align: center;">
    				<tr >
    					<c:forEach items="${typeLstNum}" var="vo">
    					<td style="width:85px;height:25px;background:#CBE2FB;border:1px solid white; vertical-align:top">
							<c:out value="${vo.alarmTypeName}" /> 
						</td>
    					</c:forEach>
    				</tr>
    				<tr>
    					<c:forEach items="${typeLstNum}" var="vo">
    					<td style="height:25px">
							<a style="cursor: pointer;" onclick="javascript:toAlarm('type',${vo.alarmTypeID})"><c:out value="${vo.totalNum}" /></a> 
						</td>
    					</c:forEach>
    				</tr>
    			</table>   
    			<br/> 		
    		</div>
    		 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    		 <div id="pieId">
    		 <table>
    		 	<tr>
    		 		<td> <div id="level_pie" style="height:400px;width:420px; border:1px solid #ccc;padding:10px;"></div></td>
    		 		<td>&nbsp;</td>
    		 	    <td><div id="type_pie" style="height:400px;width:630px; border:1px solid #ccc;padding:10px;"></div></td>
    		 	</tr>
    		 </table>
    		 </div>
    		 <div id="barId" >
    		 <div id="level_bar" style="height:240px;border:1px solid #ccc;padding:10px;"></div>
    		 <div id="type_bar" style="height:240px;width:600;border:1px solid #ccc;padding:10px;"></div>
    		  </div>
		</div>	
	</div>   
</body>
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script type="text/javascript">   
    var myChart1 = echarts.init(document.getElementById('level_pie'));
    myChart1.setOption({
      title : {
        text: '告警级别统计',
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
        data:[${levelName}]
    },
    calculable : true,
    color:[${levelColor}],//颜色不协调，暂时注掉
    series : [
        {
            name:'告警级别个数统计',
            type:'pie',
            radius : '55%',
            center: ['50%', '50%'],
            data:[
               ${levelLstNumJson}
            ]
        }
    ]
    });
    
    var myChart2 = echarts.init(document.getElementById('type_pie'));
    myChart2.setOption({
      title : {
        text: '告警类型统计',
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
        data:[${typeName}]
    },
    calculable : true,
    series : [
        {
            name:'告警类型个数统计',
            type:'pie',
            radius : '55%',
            startAngle:80,
            center: ['55%', '60%'],
            data:[
                ${typeLstNumJson}
            ]
        }
    ]
    });    
    </script>
<script type="text/javascript">   
    var myChart3 = echarts.init(document.getElementById('level_bar'));
    myChart3.setOption({
    	title : {
    	text: '告警级别统计',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['告警级别个数统计']
    },    
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : [${levelName}]
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'告警级别个数统计',
            type:'bar',
            barWidth:40,
            data:[${levelVal}]            
        }
    ]
    });

    var myChart4 = echarts.init(document.getElementById('type_bar'));
    myChart4.setOption({
    	title : {
    	text: '告警类型统计',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['告警类型个数统计']
    },    
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : [${typeName}]
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'告警类型个数统计',
            type:'bar',
            barWidth:40,
            data:[${typeVal}]            
        }
    ]
    });       
    </script>    
</html>