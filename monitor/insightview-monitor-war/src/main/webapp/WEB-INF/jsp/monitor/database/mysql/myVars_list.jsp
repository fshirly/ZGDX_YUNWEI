<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/database/mysql/myVarsList.js" ></script>
    <%
		String moId = (String)request.getAttribute("moId");
	 %>
  </head>
  
  <body>
  <input id="liInfo" value="${liInfo}" type="hidden"/>
  <div class="easyui-tabs viewtabs"  fit="true" >
  <input type="hidden" id="moId" value="<%=moId %>"/>
  <input type="hidden" id="selName" />
  <input type="hidden" id="opera" />
  <input type="hidden" id="txtValue" />
  	<div title="InnoDB启动选项" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterInnoDB">
			<table>
				<tr>
					<td>
						<select id="selNameInnoDB" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaInnoDB" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueInnoDB" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('InnoDB');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterInnoDB');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablInnoDBVars">
			
			</table>
		</div>
	</div>
  	<div title="MyISAM配置" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterMyIsam">
			<table>
				<tr>
					<td>
						<select id="selNameMyIsam" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaMyIsam" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueMyIsam" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('MyIsam');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterMyIsam');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablMyISAMConfig">
	
			</table>
		</div>
	</div>
  	<div title="字符集设置" >
  	<div class="rightContent">
  		<div class="conditions" id="divFilterCharset">
			<table>
				<tr>
					<td>
						<select id="selNameCharset" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaCharset" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueCharset" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('Charset');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterCharset');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablCharsetConfig">
	
			</table>
		</div>
	</div>
  	<div title="性能参数" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterPerfParam">
			<table>
				<tr>
					<td>
						<select id="selNamePerfParam" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaPerfParam" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValuePerfParam" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('PerfParam');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterPerfParam');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablPerfParameter">
	
			</table>
		</div>
	</div>
  	<div title="数据库安装信息" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterInstall">
			<table>
				<tr>
					<td>
						<select id="selNameInstall" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaInstall" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueInstall" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('Install');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterInstall');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablInstallInfo">
	
			</table>
		</div>
	</div>
  	<div title="数据库开关" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterSwitch">
			<table>
				<tr>
					<td>
						<select id="selNameSwitch" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaSwitch" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueSwitch" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('Switch');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterSwitch');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablDbSwitch">
	
			</table>
		</div>
	</div>
  	<div title="数据库配置" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterDbConfig">
			<table>
				<tr>
					<td>
						<select id="selNameDbConfig" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaDbConfig" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueDbConfig" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('DbConfig');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterDbConfig');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablDbConfig">
	
			</table>
		</div>
	</div>
  	<div title="所有" >
  		<div class="rightContent">
  		<div class="conditions" id="divFilterAll">
			<table>
				<tr>
					<td>
						<select id="selNameAll" class="inputs">
								<option value="varName">
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td>
						<select id="operaAll" class="inputs">
								<option value="%">
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td><input type="text" class="inputs" id="txtValueAll" /></td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable('All');">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilterAll');">重置 </a>
					</td>
				</tr>			
			</table>
			</div>
		</div>
  		<div class="datas tops2">
			<table id="tablAll">
	
			</table>
		</div>
	</div>	
  </div>
  	
		
  </body>
</html>
