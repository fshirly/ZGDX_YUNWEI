<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
   <link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/safeDeviceClassify_list.js"></script>
  </head>
  
  <body>
   	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<input type="hidden" id="id" value="${id}"/>
	<input type="hidden" id="relationPath" value="${relationPath}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>设备IP：</b>
							<input type="text" id="deviceip" />
						</td>
						<td>
							<b>设备厂商：</b>
							<select id="nemanufacturername">
                        		<option value="-1">全部</option>
                    		 </select>
						</td>
						<td>
							<b>可用状态：</b>
							<select id="operstatus" class="easyui-combobox">
								<option value="-1">全部</option>
								<c:forEach items="${osMap}" var="entry">
									<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<b>是否管理：</b>
							<select id="ismanage" class="easyui-combobox">
								<option value="-1">全部</option>
								<c:forEach items="${imMap}" var="entry">
									<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
								</c:forEach>
							</select>
						</td>
						<td>
				<b>管理域：</b>
				 <input id="domainName" class="inputs"
								onClick='showTree(this,"addressFatherId")' readonly="readonly"
										alt="-1" />
				 <div id="combdtree" class="dtreecob">
						<div id="dataOrgTreeDiv" class="dtree"
							style="width: 100%; height: 200px; overflow: auto;"></div>
							<div class="dBottom">
								<a href="javascript:hiddenDTree();">关闭</a>
							</div>
					</div>
			</td>
				<td>
							<b>设备名称：</b>
							<input type="text" id="moname" />
						</td>		
						
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetFormFilter('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas tops2">
				<table id="tblDataList">
				</table>
			</div>
		</div>
  </body>
</html>
