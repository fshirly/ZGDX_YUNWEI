<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/splitAddressInfo.js"></script>
		<%
			String subNetId =(String)request.getAttribute("subNetId");
			String ipAddress =(String)request.getAttribute("ipAddress");
			String splitNum =(String)request.getAttribute("splitNum");
			String subNetMark =(String)request.getAttribute("subNetMark");
		 %>
	  <input type="hidden" id="subNetId" value="<%= subNetId%>"/>
	  <input type="hidden" id="ipAddress" value="<%= ipAddress%>"/>
	  <input type="hidden" id="splitNum" value="<%= splitNum%>"/>
	  <input type="hidden" id="subNetMark" value="<%= subNetMark%>"/>
	  <div id="divUseInfo">
			<table cellspacing="10" class="formtable">
				<tr>
					<td></td>
					<td colspan="3">
						<label class="x2">提示：拆分将会产生新的网络地址和广播地址，导致某些IP地址变为保留状态。请将可能产生冲突的IP地址空出，再进行拆分。</label>
					</td>
				</tr>
			</table>
			
			<div id="viewUserGroup"class="winbox" >
				<div class="datas tops1">
					<table id="tblSplitIpList"></table>
				</div>
			</div>
			
			<div class="conditionsBtn">
				<a href="javascript:toCopy();" class="fltrt">导出表格</a>
				<a href="javascript:toSplit();" class="fltrt" id="btnSplit">确定进行拆分</a>
				<a href="javascript:cancle();" class="fltrt">取消</a>
			</div>
		</div>
		 <form id="frmExport"
				action="${pageContext.request.contextPath}/platform/subnetViewManager/exportSplit">
		  	  <input name="colNameArrStr" id="iptColName" type="hidden" /> 
			  <input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
			  <input name="condition" id="condition" type="hidden" /> 
			  <input name="exlName" id="iptExlName" value="拆分产生的新网络地址广播地址列表.xls" type="hidden" />
		 </form>
	</body>
</html>