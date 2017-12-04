<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblWebAppInfos" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> 应用名称： </b>
									<label id="lbl_appName" class="input">${appBean.appName}</label>
								</td>
								<td>
									<b class="title"> 资源位置： </b>
									<label id="lbl_uri" class="input">${appBean.uri}</label>
								</td>
							</tr>
							
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
