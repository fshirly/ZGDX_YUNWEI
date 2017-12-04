<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改项目任务</title>
</head>
<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/permission/sysusergroup_role.js"></script>

	<div id="divAllotRole">
				<table class="formtable1">
					<tr>
						<td>
		<input type="hidden" value="${groupID }" id="groupID">
							角色名称：
						</td>
						<td></td>
						<td>
							已有角色：
						</td>
					</tr>
					<tr>
						<td style='vertical-align: bottom;'>
							<select id="selLeft" multiple="multiple"
								style="width: 205px; height: 330px" class="dataSelect">
							</select>
						</td>
						<td style="width: 30px; text-align: center;">
							<button id="img_L_AllTo_R">
								>>>
							</button>
							<button id="img_L_To_R">
								&nbsp;&nbsp;>&nbsp;&nbsp;
							</button>
							<br />
							<button id="img_R_To_L">
								&nbsp;&nbsp;<&nbsp;&nbsp;
							</button>
							<br />
							<button id="img_R_AllTo_L">
								<<<
							</button>
							<br />
						</td>
						<td style="vertical-align: bottom;">
							<select id="selRight" multiple="multiple"
								style="width: 205px; height: 330px" class="dataSelect">
							</select>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" onclick="resetGroupRole();"
						class="fltrt">确定</a>
					<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">取消</a>
				</div>
			</div>
	
	
</body>
</html>