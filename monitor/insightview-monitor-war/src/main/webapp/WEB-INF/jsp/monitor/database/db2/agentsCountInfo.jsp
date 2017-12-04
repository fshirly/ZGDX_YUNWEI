<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	 <style>   
    .easyui-progressbar{   
        background : #383EA3;   
    }      
    .progressbar-value .progressbar-text{   
        background-color : #73484F;   
    }   
    </style>   
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblAgentsCount" class="formtable1">
							<tr>
								<td class="title">
									总连接：
								</td>
								<td>
									<label id="lbl_totalConns" class="input">
									<div class="easyui-progressbar"  value="${instanceBean.localConns}" algin="left" style="width:200px;height:17px;"></div>
									
									</label>
								</td>
							</tr>
							<tr>
								<td class="title">
									 本地连接：
								</td>
								<td>
									<label id="lbl_localConns" class="input">
									<div class="easyui-progressbar"  value="${instanceBean.localConnsExec}" text="正在执行" style="width:200px;height:17px;"></div>
									</label>
								</td>
							</tr>
							<tr>
								<td class="title">
									 异地连接：
								</td>
								<td>
									<label id="lbl_remConns" class="input">
									<div class="easyui-progressbar"  value="${instanceBean.remConnsExec}" text="正在执行" style="width:200px;height:17px;"></div>
									</label>
								</td>
							</tr>
						</table>
					</div>
				</td>
				
			</tr>

		</table>
	</body>
</html>
