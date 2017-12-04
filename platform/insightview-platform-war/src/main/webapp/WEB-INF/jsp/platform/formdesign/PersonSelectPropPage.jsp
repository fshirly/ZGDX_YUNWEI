<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员选择控件弹出页面</title>
</head>
<body>
<style>
.divbox{overflow: auto;
        border: 1px solid #ccc;
        padding: 5px;
        height:230px;
        width:272px;}
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
	<div id="divRelatedEvent" title="人员选择弹出层页面">
		<!--配置项类型查询条件-->
		<div class="winbox">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>展示方式：</b>
							<select  id="showWays" name="title" style="width:150px;">
							  <option value="0">按组织架构</option>
							  <option value="1">按工作组</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			
			<!--配置项类型信息表 -->
			<div class="datas" style="height:250px;">
			<table>
			  		<tr>
					  <td>
					    <div id="departmentTree" class="divbox"></div>
						<div id="groupList" class="divbox" style="display: none;"></div>
					   </td>
					   <td>
					    <div id="departmentMan" class="divbox"></div>
					   </td>
					</tr>
			</table>
			</div>
			<div class="conditionsBtn">
		      <a onclick="getSelectValue();">确认</a>
		      <a onclick="javascript:$('#propWin').window('close');">取消</a>
		    </div>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$("#showWays").combobox({
			onSelect : function(record){
				if(record.value == 0){
					$("#departmentTree").css("display","block");
					$("#groupList").css("display","none");
				}else if(record.value == 1){
					$("#departmentTree").css("display","none");
					$("#groupList").css("display","block");
				}
			}
		});

        $('#departmentTree').tree({
			url:f.contextPath +"/eventManage/findTreeDepartments?parentId=0",
			onBeforeExpand:function(node){
				$('#departmentTree').tree('options').url=f.contextPath +"/eventManage/findTreeDepartments?parentId="+node.id;
			},
			onClick:showDepartmentUsers
		});
		
        $("#groupList").tree({
			url:f.contextPath +"/eventManage/findAllGroups",
			onBeforeExpand:function(node){
				$('#groupList').tree('options').url=f.contextPath +"/eventManage/findAllGroups";
			},
			onClick:showGroupsUser
		});

/*         $("#selectCondition input[type='radio']").each(function(){
			  $(this).bind("click",conditionSelect);
		}); */
	});
	function showDepartmentUsers(node){
	    if(node.state!='closed'){
		        $.post(f.contextPath +"/eventManage/findStaffInfo",
		        		{deptId:node.id},
		        		function(data){
		        			$("#departmentMan li").remove();
		        			$.each(data,function(k,v){				        				
		        				var listaff=$("<li></li>");
		        				var checkboxName=$("<input type='checkbox'>");
		        				$.each(v,function(id,value){
		        					if(id!='selected'){
			        					if(id=='val'){
			        						listaff.html(value);			        						
			        					}
			        					checkboxName.attr(id,value);
		        					}
		        				});	
		        				listaff.prepend(checkboxName);
		        				checkboxName.bind("click",userSelect);
		        				listaff.appendTo("#departmentMan");
		        			});
		        		});
	        }       
	}
	function showGroupsUser(node){
	    if(node.state!='closed'){
		    $.post(f.contextPath +"/eventManage/findStaffInfo2",
		        {groupId:node.id},
		        function(data){
		        	$("#departmentMan li").remove();
		        	$.each(data,function(k,v){				        				
		        		var listaff=$("<li></li>");
		        		var checkboxName=$("<input type='checkbox' >");
		        		$.each(v,function(id,value){
		        			if(id!='selected'){
			        			if(id=='val'){
			        				listaff.html(value);			        						
			        			}
			        			checkboxName.attr(id,value);
		        			}
		        		});	
		        		listaff.prepend(checkboxName);
		        		checkboxName.bind("click",userSelect);
		        		listaff.appendTo("#departmentMan");
		        	});
		       });
	   }       
	}
	var selectMessages=[];
	function userSelect(){	
		var selectMan=$(this).attr("val"),
		    selectId=$(this).attr("key"),
		    isChecked = $(this).is(":checked"), 
		    selectMsg = {};
		if(isChecked){
			selectMsg[selectId] = selectMan;
			selectMessages.push(selectMsg);
		}else if(!isChecked && selectMessages.length > 0){
			for(var i=0;i<selectMessages.length;i++){
				var json = selectMessages[i];
				if(selectId in json){
					selectMessages.splice(i,1);
				}
			}
		}
     }
	
	function getSelectValue(){
		$("#${id}").val("");
		$("#${id}_id").val("");
        for(var i=0; i < selectMessages.length; i++){
        	for(var p in selectMessages[i]){
        		var parentTextarea = $("#${id}").val();
        		var id = $("#${id}_id").val();
        		var json = selectMessages[i];
        		if(parentTextarea !="" ){
        		   var textareaValue = parentTextarea +","+ json[p];
        		   var selectId = id +","+ p;
        		   $("#${id}").val(textareaValue);
        		   $("#${id}_id").val(selectId);
        		}else {
        			var textareaValue = json[p];
        			$("#${id}").val(textareaValue);
        			$("#${id}_id").val(p);
        		}
        	}
        }
        $('#propWin').window('close');
	}
	</script>
</body>
</html>