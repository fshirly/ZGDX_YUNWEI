<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'><select id="selLeft"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
							<option alt="userName">签到人姓名</option>
							<option alt="attDate">签到日期</option>
							<option alt="signTimeSlot">签到时间段</option>
							<option alt="statistics">统计</option>
					</select></td>
					<td style="width: 30px; text-align: center;">
						<button id="img_L_AllTo_R" type="button" style="width: 50px">>>></button>
						<button id="img_L_To_R" type="button" style="width: 50px">></button> <br />
						<button type="button" onclick="upSelectedOption()" style="width: 50px">上移</button><br /> 
						<button type="button" onclick="downSelectedOption()" style="width: 50px">下移</button><br /> 
						<button id="img_R_To_L" type="button" style="width: 50px"><</button> <br />
						<button id="img_R_AllTo_L" type="button" style="width: 50px"><<<</button> 
					</td>
					<td style="vertical-align: bottom;"><select id="selRight"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
					</select></td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<button onclick="doExport()" type="button">导出</button>
			</div>
	</div>
	
	<script>
	function doInitLRselect() {
		$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
				"img_R_To_L", "img_R_AllTo_L");
		$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
		LRSelect.moveAll( "selLeft", "selRight", true);  
	}
	
	function doExport() {
		var selAttPlanName = $('#signPlan').combobox('getText');
		$('#selAttPlanName').val(selAttPlanName);
		var exportInfoObj = {
			"selId" : "selRight",
			"colName" : "iptColName",
			"titleName" : "iptTitleName"
		};
		
		var expObject = doExportCommon(exportInfoObj);
		$("#frmExport").submit();
		$('#popWin').window('close');
	}

	function doExportCommon(exportInfoObj) {
		var expColValueAttr = '';
		var expColTitleAttr = '';
		$("#" + exportInfoObj.selId + " option").each(function() {
			expColValueAttr += $(this).attr('alt') + ',';
			expColTitleAttr += $(this).val() + ',';
		});
		var expObject = {
			"valAttr" : expColValueAttr,
			"titleAttr" : expColTitleAttr
		};
		$("#" + exportInfoObj.colName).val(expColValueAttr);
		$("#" + exportInfoObj.titleName).val(expColTitleAttr);
		return expObject;
	}

	/**
	 * 向上移动选中的option
	 */
	function upSelectedOption() {
		if (null == $('#selRight').val()) {
			$.messager.alert('提示','请选择一项','info');
			return false;
		}
		// 选中的索引,从0开始
		var optionIndex = $('#selRight').get(0).selectedIndex;
		// 如果选中的不在最上面,表示可以移动
		if (optionIndex > 0) {
			$('#selRight option:selected').insertBefore(
					$('#selRight option:selected').prev('option'));
		}
	}

	/**
	 * 向下移动选中的option
	 */
	function downSelectedOption() {
		if (null == $('#selRight').val()) {
			$.messager.alert('请选择一项');
			return false;
		}
		// 索引的长度,从1开始
		var optionLength = $('#selRight')[0].options.length;
		// 选中的索引,从0开始
		var optionIndex = $('#selRight').get(0).selectedIndex;
		// 如果选择的不在最下面,表示可以向下
		if (optionIndex < (optionLength - 1)) {
			$('#selRight option:selected').insertAfter(
					$('#selRight option:selected').next('option'));
		}
	}
	
	$(function() {
		doInitLRselect();
	});
	</script>

</body>
</html>