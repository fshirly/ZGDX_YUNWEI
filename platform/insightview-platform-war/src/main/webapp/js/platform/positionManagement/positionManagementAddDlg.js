/**
 * create by zhaoyp
 */
var System = System || {};
System.PostMgmDlg = {
		path : getRootName(),
		count :1,
		initPersonalTable : function(){
			var that = this;
			var isLook = $("#toLookFlag").val();
			var id = $("#postId").val();
			$("#personalTable").datagrid({
				iconCls : 'icon-edit',// 图标
				width : 'auto',
				height : 222,
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,// 是否可折叠的
				url : that.path + '/platform/initPositionPersonalTab?postID='+id,
				remoteSort : false,
				idField : 'fldId',
				singleSelect : true,// 是否单选
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,// 分页控件
				columns : [ [
                       {
	                        field : 'employeeCode',
	                        title : '工号',
	                        align : 'center',
	                        width : 150
	                   },
					   {
							field : 'userName',
							title : '姓名',
							align : 'center',
							width : 150
						},
						{
							field : 'mobilePhone',
							title : '联系电话',
							align : 'center',
							width : 182
						},
						{
							field : 'email',
							title : '邮箱',
							align : 'center',
							width : 170
						}
					    ] ]
			});
			if(isLook != "look"){
				$("#personalTable").datagrid({
					toolbar : [ {
					      text : '添加',
					      iconCls : 'icon-add',
					      handler : function() {
						      that.addPersonal();
					      }
				        }],
				     columns : [ [
			                      {
				                        field : 'employeeCode',
				                        title : '工号',
				                        align : 'center',
				                        width : 120  
				                   },
								   {
										field : 'userName',
										title : '姓名',
										align : 'center',
										width : 120
									},
									{
										field : 'mobilePhone',
										title : '联系电话',
										align : 'center',
										width : 120
									},
									{
										field : 'email',
										title : '邮箱',
										align : 'center',
										width : 150
									},
									{
										field : 'id',
										title : '操作',
										align : 'center',
										width : 145,
										formatter : function(value,row,index){
											return '<a  style=\'cursor:pointer\' title="刪除" class="easyui-tooltip" onclick="javascript:System.PostMgmDlg.doDel('
											+ row.id
											+ ');"><img src="'
											+ that.path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>'
										}
									 }
								    ] ]
				     });
			 }
		},
		
		/**
		 *初始化tabs分页渲染模块 
		 */
		
		initTabs : function(){
			var that =this;
			$("#positionDlgTabs").tabs({
				border : false,
				fit : true,
				closable : false,
				onSelect : function(title,index){
					var postID = $("#postId").val();
					if(index == 1){
						
						if(!checkInfo("#positionMessageTab")){
							$("#positionDlgTabs").tabs("select",0);
						}else if(postID==""){
							that.confirmData("addNew",1);
							postID = $("#postId").val();
							if( postID ==""){
								$("#tabs2_btn").css("display","none");
								$("#tabs1_btn").css("display","block");
								$("#positionDlgTabs").tabs("select",0);
							}
						}else{
							$("#tabs2_btn").css("display","block");
	                        $("#tabs1_btn").css("display","none");
						}
					}else if(index === 0){
						$("#tabs2_btn").css("display","none");
						$("#tabs1_btn").css("display","block");
					}
				}
			});
		},
		
		 /**
	      *删除单条信息 
	      */
	     doDel : function(id){
	    	 var that = this;
	    	 $.messager.confirm("提示","删除后不能恢复，确定删除？",function(r){
	    		 if (r == true) {
	    			var uri =that.path + "/platform/deletePositionPersonalMessage?id="+id;
	    			var ajax_param = {
	    						url : uri,
	    						type : "post",
	    						datdType : "json",
	    						data : {
	    							"t" : Math.random()
	    						},
	    						error : function() {
	    							$.messager.alert("错误", "ajax_error", "error");
	    						},
	    						success : function(data) {
	    							if (data == "false") {
	    								$.messager.alert("错误", "请先删除该节点下的子节点并确定该服务目录未被使用","error");
	    							}else if(data == "init"){
	    								$.messager.alert("提示", "系统初始化数据，不能删除", "info");
	    							}else {
	    								that.reloadTable();
	    								window.frames['component_2'].System.PostMgm.reloadTable();
	    							}
	    						}
	    					};
	    			ajax_(ajax_param);
	    		 }
	         });
	     },
		
		/**
		 *增加人员弹出选择面板 
		 */
		
		addPersonal : function(){
			    var that = this;
				parent.$("#popWin2").window({
					title : "添加人员",
					width : 700,
					height : 530,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					href : that.path + "/platform/positionManagementPopPage?condition=addPersonal"
				});
		},

		/**
		 * 执行的url
		 */
		
		checkUri :function(urlParam){
			var uri = "";
			var id = $("#postId").val();
			switch(urlParam){
			  case "addNew":
				  uri = "/platform/addNew";
				  break;
			  case "edit":
				  uri = "/platform/toUpdate?id="+id;
				  break;
			}
			return uri;
		},
		
		/**
		 *编辑修改岗位信息 
		 */
		
		postEditBtn : function(){
			this.confirmData("edit");
		},
		
	     /**
	      * 重载表单
	      */
		
	     reloadTable : function(){
             this.initPersonalTable();
             $('#personalTable').datagrid('unselectAll');
    	     $('#personalTable').datagrid('uncheckAll');
	     },
	     
	     submitBtn : function(){
	    	 $('#popWin').window('close');
	    	 window.frames['component_2'].System.PostMgm.reloadTable();
	     },
	     
		/**
		 *弹出层确认功能 
		 */
		
		confirmData : function(urlParam,index){
			var that = this;
			var uri = that.checkUri(urlParam);
			if(checkInfo("#positionMessageTab")){
				var isImportantPosition = $('input[name="isImportantPosition"]:checked').val();
				var json = {
						postName : $("#positionNameTree").val(),
						organizationID : $("#organizationID").val(),
						isImportant : isImportantPosition,
						postDivision : $("#positionDistribution_Dlg").val(),
						"t" : Math.random()
				};
				//执行ajax方法
				if(that.count == 1){
					$.ajax({
						 url : that.path+uri,
						 type : "post",
						 datdType : "json",
						 data : json,
						 error:function(){
							 $.messager.alert("错误", "ajax_error", "error");
						 },
						 success : function(data){
							 that.count = 2;
							 if (null != data || "null" != data) {
								$("#postId").val(data);
								if(index !=1){
									$('#popWin').window('close');
									window.frames['component_2'].System.PostMgm.reloadTable();
								} else if(index == 1){
									$("#positionDlgTabs").tabs("select",1);
									$("#tabs2_btn").css("display","block");
									$("#tabs1_btn").css("display","none");
								}
							 } else {
								 $.messager.alert("提示", "单位新增失败！", "error");
							 }
						 }
					 });
				}else{
					window.frames['component_2'].System.PostMgm.reloadTable();
					$('#popWin').window('close');
				}	

				}

		 },
		 
		 /**
		  *删除插入表中的数据 
		  */
		 delIsertPostMsg : function(){
			 var that = this;
			 var postID = $("#postId").val();
			 var uri = "/platform/delIsertPostMsg?postID="+postID;
			 if(postID !=""){
				 $.ajax({
					 url : that.path + uri,
					 type : "post",
					 dataType : "json",
					 success : function(data){
						 if(data == "true" || true == data){
							 window.frames['component_2'].System.PostMgm.reloadTable();
							 $('#popWin').window('close');
						 } 
					 }
				 });
			 }else{
				 window.frames['component_2'].System.PostMgm.reloadTable();
				 $('#popWin').window('close');
			 }

		 },
		 
		 /**
		  *弹出窗口关闭事件 
		  */
		 
		 closeWindowEvent : function(){
			 var that = this;
			 $(".panel-tool-close").click(function(){
				 var postID = $("#postId").val();
				 if(postID !=""){
					 that.delIsertPostMsg();
				 } 
			 });
		 },
		 
		 /**
		  *addPersonalToPosition添加人员到岗位页面调用方法 
		  */
		 addPersonalPopWin : {
				
				/**
				 * 重置表单
				 */
				
				reloadTable : function(){
					var employeeCode = $("#employeeCode").val(),
					    userName = $("#userName").val();
		    		$('#positionMessage_AddPersonalTable').datagrid('options').queryParams = {
		    			"employeeCode" : employeeCode,
		    			"userName" : userName
		    		};
		    		$('#positionMessage_AddPersonalTable').datagrid('load');
		    		$('#positionMessage_AddPersonalTable').datagrid('unselectAll');
		    	    $('#positionMessage_AddPersonalTable').datagrid('uncheckAll');
				},
				
				/**
				 * 重置查询区域
				 */
				
				resetQueryCondition : function(){
					$("#employeeCode").val("");
					$("#userName").val("");
				},
				
				/**
				 *添加人员到岗位 
				 */
				addPersonalToPosition : function(){
					var postId = $("#postId").val();
					var that = this;
					var checkMsg = $('#positionMessage_AddPersonalTable').datagrid('getChecked');
					var userIDStr = null;
					for(var i=0;i < checkMsg.length;i++){
						if(userIDStr == null){
							userIDStr = checkMsg[i].userID;
							
						}else{
							userIDStr+=","+checkMsg[i].userID;
						}
					}
				   if(null != userIDStr){
					   $.messager.confirm("提示","确定增加所选中项？",function(r) {
					       if (r == true) {
							    var uri = System.PostMgmDlg.path +"/platform/addPersonalToPosition?userIDs=" + userIDStr +"&postID="+postId;
							    var ajax_param = {
								        url : uri,
										type : "post",
										datdType : "json",
										data : {
											"t" : Math.random()
										},
										success : function(data) {
											if (data == false) {
												$.messager.alert("错误","添加失败","error");
											} else {
												System.PostMgmDlg.reloadTable();
												$('#popWin2').window('close');
												window.frames['component_2'].System.PostMgm.reloadTable();
											}
										}
									};
								ajax_(ajax_param);
							}
					   });
		           } else {
			          $.messager.alert('提示', '没有任何选中项', 'info');
		           }
				},
				
				/**
				 * 
				 * 初始化人员信息表单
				 */
				initDatagrid : function(){
					var organizationID = $("#organizationID").val();
					$("#positionMessage_AddPersonalTable").datagrid({
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 350,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : true,// 是否可折叠的
						fitColumns : true,
						url : System.PostMgmDlg.path + '/platform/initPositionMessageAddPersonalTable?organizationID='+organizationID,
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						columns : [ [
								{
									field : 'ck',
									checkbox : true
								},
								{
									field : 'employeeCode',
									title : '工号',
									align : 'center',
									width : 90
								},
								{
									field : 'userName',
									title : '姓名',
									align : 'center',
									width : 120,
									formatter: function(value,row,index){
										return'<a onclick="javascript:System.PostMgmDlg.addPersonalPopWin.doOpenDetail('+ row.userID + ');">'+ value +'</a>'
									}
								},
								{
									field : 'mobilePhone',
									title : '联系电话',
									align : 'center',
									width : 120
								},
								{
									field : 'email',
									title : '邮箱',
									align : 'center',
									width : 190
								}
							    ] ]
					});
				},
				
				/**
				 * 查看员工详细信息
				 */
				doOpenDetail : function(userId){
					// 查看配置项页面
					parent.$('#popWin').window({
				    	title:'用户详情',
				        width:800,
				        height:400,
				        minimizable:false,
				        maximizable:false,
				        collapsible:false,
				        modal:true,
				        href: System.PostMgmDlg.path + '/permissionSysUser/toShowDetail?userId='+userId
				    });
				}
			}
};

/**
 *使用jquery中函数初始化方法 
 */
$(function(){
	System.PostMgmDlg.initTabs();
	System.PostMgmDlg.initPersonalTable();
});

