<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>数据对象管理</title>
    <style type="text/css">
	  .treemenu-config {background:url('../../../../js/platform/system/Easyui/themes/icons/treemenu.png') no-repeat -350px -60px;}
    </style>
  	<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
  </head>
  <body>
	  <div id="datagrid"></div>
	  
	  <script type="text/javascript">
	      var urlPrefix=rootPath+"sys/dataSecurity/";
		  var listviewName = "dataObject";
	      
		  $(function(){
			  initDatagrid();
	      });
	      
		  function initDatagrid(){
	  		$('#datagrid').listview({
				listviewName : listviewName,
				frozenColumns : [[{field:'id',hidden:true}]],
				columns : [[{field:'ck', checkbox:true},
					        {field:'objectName',width:'15%',align:'left'},
					        {field:'type',width:'8%',align:'center',formatter:function(value,row,index){
					        	if(value == "1"){
						    		return "表";
						    	} 
					        	if(value == "2"){
						    		return "视图";
						    	} 
					        	if(value == "3"){
						    		return "自定义SQL";
						    	}
					        	return value;
					        }},
					        {field:'fbsSql',align:'left',width:'42%'},
					        {field:'note',align:'left',width:'20%'},
					        {field:'updatedTime',align:'left',width:'12%'}
					      ]],
				toolbar : [
					{text:"新增",
					iconCls: 'icon-add',
					handler: function(){
						var dataObject = {"type":3};
    					setJsonObj('dataObject', dataObject); 
						OpenWin(urlPrefix+'dataObject/create/html', "数据对象-新增", 600, 630);
					}},
					{text:"修改",
					iconCls: 'icon-modify',
					handler: function(){
				    	if($('#dataObject_datagridId').datagrid("getSelections").length !=1) {
							alertMsg("请选中一行进行修改！");
			    		 	return;
			    		}
				    	
				    	var dataObject = $('#dataObject_datagridId').datagrid('getSelected');
				    	setJsonObj('dataObject', dataObject); 
						OpenWin(urlPrefix+'dataObject/update/html', "数据对象-修改", 600, 630);
					}},
					{text:"删除",
					iconCls: 'icon-remove',
					handler: function(){
						if($('#dataObject_datagridId').datagrid("getSelections").length == 0) {
							alertMsg("请选择记录！");
				          return;
				        }
			 	        
						alertConfirm('确认', '您确定要删除选择的记录吗？',
					        function(r) {
					          if(r) {
					            var rows = $('#dataObject_datagridId').datagrid('getSelections');
					            var ids=[], types=[], type, illegalType=false;
					            $.each(rows, function(i, row) {
					              ids[i] = row.id;
					              type = row.type;
					              if(type != 3){
									  alertMsg('只能删除自定义SQL类型的记录');
					            	  illegalType = true;
					            	  return false;
					              }
					              types[i] = type;
			                    });
					            if(illegalType){
					            	return;
					            }
					            
					            $.ajax({
					              type: "DELETE",
					              url: urlPrefix+"dataObjects/"+JSON.stringify(ids),
					              data : JSON.stringify(types),
					              dataType: 'json',
					              contentType: "application/json",
					              success: function(result) {
									  alertMsg(result.message);
					                  if(result.success) {
					                    loadData();
					                  }
					              },
					              error: function(result) {
					            	  show_error(result);
					              }
					            });
					          }
					        }
				        );
					}},
					{text:"权限配置",
					iconCls: 'treemenu-config',
					handler: function(){
				   	  	if($('#dataObject_datagridId').datagrid("getSelections").length !=1) {
							alertMsg("请选中一行进行设置！");
				   		  return;
				   		}
				   	 	var dataObjectId = $('#dataObject_datagridId').datagrid('getSelected').id;
				   	 	setJsonObj('dataObjectId', dataObjectId);
						OpenWin(urlPrefix+'dataSecuritys/list/html', "数据权限设置", 1000, 500);
					}},
					{text:"更新数据对象",
					iconCls: 'icon-modify',
					handler: function(){
				   	  	alertConfirm('确定', '您确定要更新数据对象？', function(r){
				   	  		if(r){
				   	  			$.ajax({
					              type: "POST",
					              url: urlPrefix+"dataObjects/update",
					              //data : JSON.stringify(types),
					              dataType: 'json',
					              contentType: "application/json",
					              success: function(result) {
									  alertMsg(result.message, '提示', function(){
										  loadData();
									  });
					              },
					              error: function(result) {
					            	  show_error(result);
					              }
					            });
				   	  		}
				   	  	});
					}}
				],
				fitColumns : true,
				pagination : true,
				rownumbers : true,
				fit:true
			});
		  }
		  
	      function loadData(){
	    	  listviewMethod[listviewName+"_query_method"]();
	      }
	      
	      /**
	       * 页面刷新
	       */
	      function fresh() {
	    	listviewMethod["refresh"](listviewName);
	      }
	  </script>
  </body>
</html>