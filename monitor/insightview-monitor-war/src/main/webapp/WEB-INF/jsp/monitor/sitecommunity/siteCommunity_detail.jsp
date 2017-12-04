<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  </head>
  <body>
  <style>
      .label{
      width:500px;
        max-height: 100px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
      
      .label2{
      width:180px;
        max-height: 100px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
    </style>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/sitecommunity/siteCommunity_detail.js"></script>
	<div id="divTaskDetail">
		<input type="hidden" id="id" value="${id}"/>
		  <table id="tblFtpDetail" class="tableinfo">
		  	<tr>
		  		<td>
					<div style='float:left;'><b class="title">监控类型：</b></div>
					<div class="label2">
					<label id="lbl_siteType1"></label>
					</div>
				</td>
				<td>
					<div style='float:left;'><b class="title">地址：</b></div>
					<div class="label2">
					<label id="lbl_ftpIPAddress"></label>
					</div>
				</td>
		  	</tr>
			<tr>
				<td>
					<div style='float:left;'><b class="title">用户名：</b></div>
					<div class="label2"><label id="lbl_userName" ></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">端口：</b></div>
					<div class="label2">
					<label id="lbl_port"></label></div>
				</td>
			</tr>
		  </table>
		  
		   <table id="tblHttpDetail" class="tableinfo">
		     <tr>
		  		<td>
					<div style='float:left;'><b class="title">监控类型：</b></div>
					<div class="label2">
					<label id="lbl_siteType2"></label>
					</div>
				</td>
				<td>
					<div style='float:left;'><b class="title">HTTP请求方式：</b></div>
					<div class="label2">
					<label id="lbl_requestMethod"></label></div>
				</td>
		  	  </tr>
		   
			  <tr>
				<td colspan="2">
					<div style='float:left;'><b class="title">地址：</b></div>
					<div class="label">
					<label id="lbl_httpIPAddress"></label>
					</div>
				</td>
				
			  </tr>
		  </table>
		<div class="conditionsBtn">
			<input type="button" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
  </body>
</html>
