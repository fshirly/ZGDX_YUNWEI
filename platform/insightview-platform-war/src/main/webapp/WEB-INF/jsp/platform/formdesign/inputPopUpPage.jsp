<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>弹出层控件弹出页面</title>
</head>
<body>
<style>
 .winbox {height:295px;border:1px solid #b1c1cc;}
 .conditions {min-height:36px; background:#f5f5f5; min-width:810px; padding:5px 0; border:solid #ffffff; border-width:1px 0;}
 .btn, .btntd a, .messager-button a, input.btn {display:inline-block; text-align:center; color:#ffffff; border:1px solid; border-color:#5cb8e6 #297ca6 #297ca6 #5cb8e6; background:#36a3d9; width:62px; height:24px; line-height:22px;}
 .btn {margin-right:6px;}
 .btntd a, .messager-button a {margin-left:6px;}
 .btn:hover, .btntd a:hover, .messager-button a:hover {background:#005580; color:#ffffff;}
 b{display: inline-block;
    min-width: 68px;
    text-align: right;
     font-weight: normal;}
</style>
	<div id="divRelatedEvent" title="input弹出层页面">
		<!--配置项类型查询条件-->
		<div class="winbox">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>标题：</b>
							<input type="text" id="title" name="title" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable()">查询</a>
							<a href="javascript:void(0);" onclick="reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!--配置项类型信息表 -->
			<div class="datas" style="height:250px;">
				<table id="dataTable">
				</table>
			</div>
			<div class="conditionsBtn">
		      <a onclick="getDatagridValue();">确认</a>
		      <a onclick="javascript:$('#propWin').window('close');">取消</a>
		    </div>
		</div>
	</div>
	<script>
	   function initTable(){
		   $("#dataTable").datagrid({
			    iconCls : 'icon-edit',// 图标
				width : 100,
				height : 100,
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,// 是否可折叠的
				fit : true,// 自动大小
				fitColumns : true,
				remoteSort : false,
				idField : 'id',
				url : f.contextPath + "/bpmconsole/formDesign/queryLinkListInfo?sql=${sql}",
				singleSelect : true,// 是否单选
				pagination : true,// 分页控件
				rownumbers : true,// 行号
			    columns : [${columnsData}]
		   });
	   }
	   //根据查询区域输入框中的条件进行重新加载表格
	   function reloadTable (){ 
		   var title = $("#title").val();
		   var condition = {};
		   condition['${titleCol}'] = title;
		   $('#dataTable').datagrid('options').queryParams = {
				"condition" : JSON.stringify(condition)
		   };
		   $('#dataTable').datagrid('load');
		   $('#dataTable').datagrid('unselectAll');
		   $('#dataTable').datagrid('uncheckAll');
	   }
	   //选取弹出层上到行，给对应的input赋值
	   function getDatagridValue(){ 
		   var filedName = "${fieldName}",
		       id = filedName.split(",")[0],
		       name = filedName.split(",")[1],
		       getRowValue = $("#dataTable").datagrid("getSelected"),
		       linkSql = '${linkSQL}';
		   if(getRowValue){
			   $('#${id}_hidden').val(getRowValue[id]);
			   $('#${id}').val(getRowValue[name]);
			   for(var p in getRowValue){
				   $("input[name="+ p +"]").val(getRowValue[p]);
			   }
			   if(linkSql !=""){
				   inputLinkValue(getRowValue[id],linkSql);
			   }
			   $('#propWin').window('close');
		   }
	   }
	   //给关联控件赋值
	   function inputLinkValue(id,linkSql){
			var id = id;
			var linkSQL = linkSql;
			$.ajax({
				url : getRootName() + "/bpmconsole/formDesign/findLinkFieldsForUser?linkSql=" + linkSQL + "&userId=" + id,
				type : "post",
				dataType : "json",
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					for (var p in data) {
						if (data.hasOwnProperty(p)) {
								$("input[name='"+ p +"']").val(data[p]);
						}
					}
				}
			});
	   }
	   //重置按钮
	   function reset(){
		   $("#title").val("");
	   }
	   //初始化datagrid
       $(document).ready(function() {
	        initTable();
       });
	</script>
</body>
</html>