<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
%>
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/systest.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/systestjson.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：系统管理&gt;&gt; 安全管理&gt;&gt; <span>系统测试1</span></div>
		<div class="conditions" id="divFilter">
			<table>
			  <tr>
			    <td>
				   <b>用户名：</b>
				   <input type="text"  id="txtFilterUserAccount" />
			    </td>
			    <td>
				   <b>姓名：</b>
				   <input type="text"  id="txtFilterUserName" />
			    </td>
			    <td class="btntd">
				    <a href="javascript:void(0);" onclick="resetForm('divFilter');" >重置</a> 
				    <a href="javascript:void(0);" onclick="reloadTable();">查询</a>
			    </td>
			  </tr>
			</table>
		</div>
		<div class="datas">
			<table id="tblSysUser">

			</table>
		</div>
	</div>
	<!-- end .datas -->
	<div id="divUploadImg" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="图片上传、下载组件"
		style="width: 600px; height: 505px;">
		<table width="100%">
			<tr>
				<td align="center"><b>图片上传</b></td>
			</tr>
			<tr>
				<td>
					<!-- end .datas -->
					<div id="imgPreview" style='width: 150px; height: 80px;'>
						<img id="img1" src="../style/images/initupload.gif" width="150"
							height="100" />
					</div> <input name="ipt_imageFileNames" id="ipt_imageFileNames"type="file" onchange='initAndCheckUpload(this)' />
					</br>
					<label>文件上传地址：</label><label id="lbl_filepath"></label>
				</td>
				<td align="left"><button id="btnUpload" onclick="doUploadFile();">上传</button></td>
			</tr>
			<tr>
				<td align="center"><b>图片预览</b></td>
			</tr>
			<tr>
				<td align="center"><img id="img_filepreview" width="150" height="100" src="" alt="" /></td>
			</tr>
			<tr>
				<td align="center"><b>文件下载</b></td>
			</tr>
			<tr>
				<td><button onclick="initDownload();">初始化下载</button></td>
			</tr>
			<tr>
				<td><a id="a1" href="">下载</a>
				<a href="<%=path %>/commonFileUpload/CosDownload?fileDir=\\IMAGE\\SDF-9090-11.gif">下载1</a></td>
			</tr>
			<!-- <tr>
				<td>文件上传</td>
			</tr>
			<tr>
				<td><input name="ipt_fileupload" id="ipt_fileupload"
					type="file" onchange='initFile(this);' /></td>
			</tr>
			<tr>
				<td><button onclick="doUploadFileTwo();">上传</button></td>
			</tr> -->
		</table>
	</div>
	<script language="javascript">
		
		
	</script>
	<!-- end .datas -->
	<div id="divAddUser" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="用户信息"
		style="width: 600px; height: 275px;">
		<input id="ipt_userID" type="hidden" /><input id="ipt_isAutoLock"
			type="hidden" /><input id="ipt_status" type="hidden" /> <input
			id="ipt_createTime" type="hidden" /> <input id="ipt_lockedTime"
			type="hidden" /> <input id="ipt_lockedReason" type="hidden" />
		<table id="tblAddUser" cellspacing="13">
			<tr>
				<td>用&nbsp;户&nbsp;名：</td>
				<td><input id="ipt_userAccount" onblur="checkSysUser();"
					maxlength="20" /><span style="color: red; margin-left: 6px;">*</span></td>
				<td>用户姓名：</td>
				<td><input id="ipt_userName" /><span
					style="color: red; margin-left: 6px;">*</span></td>
			</tr>
			<tr>
				<td>用户密码：</td>
				<td><input id="ipt_userPassword" type="password" /><span
					style="color: red; margin-left: 6px;">*</span></td>
				<td>手机号码：</td>
				<td><input id="ipt_mobilePhone" /><span
					style="color: red; margin-left: 6px;">*</span></td>
			</tr>
			<tr>
				<td>邮箱地址：</td>
				<td><input id="ipt_email" /><span
					style="color: red; margin-left: 6px;">*</span></td>
				<td>电话号码：</td>
				<td><input id="ipt_telephone" /></td>
			</tr>
			<tr>
				<td>用户类型：</td>
				<td>
				<!--  <select id="ipt_userType" style="width: 155px">
						<option value="0">管理员</option>
						<option value="1">企业内IT部门用户</option>
						<option value="2">企业业务部门用户</option>
						<option value="3">外部供应商用户</option>
				</select><span style="color: red; margin-left: 6px;">*</span>
				-->
				<input type="text" id="ipt_userType" name="ipt_userType" value="${ipt_userType}"/> </td>
			</tr>
			<tr>
				<td colspan="4">
					<p class="formBtn" />
					<p class="formBtn" />
					<p class="formBtn">
						<a href="javascript:void(0);" id="btnUpdate" class="fltrt">重置</a>
						<a href="javascript:void(0);" id="btnSave" class="fltrt">保存</a>
					</p>
				</td>
			</tr>
		</table>
	</div>
	<div id="divSelect2" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="Select2代码"
		style="width: 900px; height: 500px;">
			带搜索的下拉框，支持多选
			<select multiple id="select2" style="width: 200px">
		
			</select>
			</br> 
			</br>
			带搜索的下拉框，支持多选,和请选择提示
			<select id="select2_3" multiple="multiple" style="width: 300px"
			class="populate placeholder select2-offscreen" tabindex="-1"><option></option>

			</select> 
			</br>
			</br>
			带搜索的下拉框
			<select id="select2_1" style="width: 200px">
		
			</select>
			</br>
			</br>
			带搜索的下拉框,支持请选择提示
			<select id="select2_2" style="width:200px" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
		
			</select>
			</br>
			</br>
			适用于数据量较大时候的搜索，这里使用网上的JSON数据做案例，全屏打开弹出层做测试
			<p>
                   <div class="select2-container bigdrop" id="s2id_e7" style="width: 600px;"><a href="javascript:void(0)" class="select2-choice select2-default" tabindex="-1">   <span class="select2-chosen" id="select2-chosen-6">Search for a movie</span><abbr class="select2-search-choice-close"></abbr>   <span class="select2-arrow" role="presentation"><b role="presentation"></b></span></a><label for="s2id_autogen6" class="select2-offscreen"></label><input class="select2-focusser select2-offscreen" type="text" aria-haspopup="true" role="button" aria-labelledby="select2-chosen-6" id="s2id_autogen6" tabindex="0"></div><input type="hidden" class="bigdrop select2-offscreen" id="e7" style="width:600px" tabindex="-1" title="">
               </p>
               <script id="script_e7">
$(document).ready(function() {
$("#e7").select2({
    placeholder: "Search for a movie",
    minimumInputLength: 3,//输入多少字符开始响应触发查询
    ajax: {
        url: "http://api.rottentomatoes.com/api/public/v1.0/movies.json",
        dataType: 'jsonp',
        quietMillis: 100,//超时限制
        data: function (term, page) { 
            return {
                q: term, 
                page_limit: 10, //每页显示最大数量
                page: page,
                apikey: "ju6z9mjyajq2djue3gbvv26t"
            };
        },
        results: function (data, page) {
            var more = (page * 10) < data.total; 

            return {results: data.movies, more: more};
        }
    },
    formatResult: movieFormatResult, 
    formatSelection: movieFormatSelection, 
    dropdownCssClass: "bigdrop", 
    escapeMarkup: function (m) { return m; } 
});
});

</script>
<script>

    function movieFormatResult(movie) {
        var markup = "<table class='movie-result'><tr>";
        if (movie.posters !== undefined && movie.posters.thumbnail !== undefined) {
            markup += "<td class='movie-image'><img src='" + movie.posters.thumbnail + "'/></td>";
        }
        markup += "<td class='movie-info'><div class='movie-title'>" + movie.title + "</div>";
        if (movie.critics_consensus !== undefined) {
            markup += "<div class='movie-synopsis'>" + movie.critics_consensus + "</div>";
        }
        else if (movie.synopsis !== undefined) {
            markup += "<div class='movie-synopsis'>" + movie.synopsis + "</div>";
        }
        markup += "</td></tr></table>";
        return markup;
    }

    function movieFormatSelection(movie) {
        return movie.title;
    }

</script>
	</div>
</body>
</html>