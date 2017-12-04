/**
 * 选择人员
 * Created By ZhaoYP
 * 2015-5-19 
 */
var Sys = Sys || {};
var tree_data=null;
var combox_data=null;
Sys.PersonnelSelection = {
		path : getRootName(),
		selIndex : 1,
		/**
		 *初始化人员信息表
		 */
		initPersonnelTable : function(){
			var that = this,
			    uri=that.path +"/attendanceConf/attendancepeopleConf_list?attendanceId="+attendanceId;//初始化人员表的

			$("#personalMessageTable").datagrid({
				iconCls : 'icon-edit',
				width : 'auto',
				height : 320,
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,
				url : uri,
				remoteSort : false,
				rownumbers:true,
				selectOnCheck:true,
				idField : 'userID',
				checkOnSelect : false,
				pagination : true,
				columns : [ [
				             {
					  			   field : 'userID',
					               checkbox : true   
					  		   }, {
			                        field : 'userName',
			                        title : '姓名',
			                        align : 'center',
			                        width : 140
			                   },
							   {
									field : 'userAccount',
									title : '用户名',
									align : 'center',
									width : 140
								},
								{
									field : 'provide_Org',
									title : '部门/供应商',
									align : 'center',
									width : 182
								},
								{
									field : 'ids',
									title : '操作',
									align : 'center',
									width : 160,
									formatter : function(value,row,index){
										return '<a  style=\'cursor:pointer\' title="选择" class="easyui-tooltip" onclick="javascript:Sys.PersonnelSelection.personnelSelected('
										+ row.userID
										+ ');">选择</a>'
									}
								}
							] ]
			 });
		},
		
		/**
		 *初始化下拉树 
		 */
		initComboTree : function(choose){
			var that = this;
			var ajax_param = {
					url : that.path + "/permissionDepartment/findOrgAndDeptVal",
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						// 得到树的json数据源
						var jsonData = eval('(' + data.menuLstJson + ')');
						$("#department").f_combotree({
							rootName : "部门",
							height : 200,
							data : jsonData,
							titleField : 'name',
							idField : 'id',
							onBeforeSelect : function(node) {
								if (node.id.substring(0, 1) == 'D') {
									return true;
								}
								return false;
							},
							onSelect : function(node) {
								$(this).next().val(node.id.substring(1));
							}
						});
						$("#department").next().val($("#department").next().val().replace(/\D/g,''));
					}
				};
				ajax_(ajax_param);
		},
		
		/**
		 * 初始化下拉列表
		 */
		initCombobox : function(){
			var that = this;
			$.ajax({
			datdType : "json",
			url: that.path+'/attendanceConf/attendanceConfzt_providerinfo_combox',
			success : function(data){
			  $('#Supplier').combobox({
				  valueField : 'id',
				  textField : 'neir',
				  data:data,
				  editable : false,
				  panelWidth: 135
			  });
		  }
		});
		},
		
		/**
		 * 根据条件查询人员
		 */
		queryPersonnel : function(){
			var that = this;
			var userType = $("#userType").combobox('getValue');
			var provide_Org = "";
			if(that.selIndex == 1){
				provide_Org = $("#department").f_combotree('getValue');
			}else if(that.selIndex == 2){
				provide_Org = $('#Supplier').combobox("getValue");
			}
			var name = $("#name").val();
			$('#personalMessageTable').datagrid('options').queryParams = {
    			"userType" : userType,
    			"provide_Org" : provide_Org,
    			"userName" : name,
    			"attendanceId":attendanceId
    		};
    		$('#personalMessageTable').datagrid('load');
    	    $('#personalMessageTable').datagrid('uncheckAll');
			
		},
		
		/**
		 *重置查询区域
		 */
		resetQueryCondition : function(){
			$("#userType").combobox("setValue",1);
			$("#department").val("");
			$("#department").f_combotree("setValue","");
			$("#Supplier").combobox("setValue","");
			$("#name").val("");
		},
		
		/**
		 *单个被选择的人员 
		 */
		personnelSelected : function(param){
			var that = this;
			var userId = param;
			var uri = that.path+ "/attendanceConf/attendancepeopleConfInfoadd";//执行添加的url地址
			var ajax_param = {
			        url : uri,
					type : "post",
					datdType : "json",
					data : {
				       "attendanceId":attendanceId,
		               "userIdStr":param,
				       "t" : Math.random()
					},
					success : function(data) {
						if (data == false) {
							$.messager.alert("错误","添加失败","error");
						} else {
							Sys.setPersonnel.reloadTable();
							Sys.PersonnelSelection.queryPersonnel();
							//$('#popWin2').window('close');
							
						}
					}
			};
			ajax_(ajax_param);
		},
		
		/**
		 *确定按钮 
		 */
		confirmButton : function(){
			var that = this;
			var checkedPersonnelMsg = $("#personalMessageTable").datagrid('getChecked');
			var userIdStr = null;
			for(var i=0;i < checkedPersonnelMsg.length;i++){
				if(userIdStr == null){
					userIdStr = checkedPersonnelMsg[i].userID;
				}else{
					userIdStr+= ","+checkedPersonnelMsg[i].userID;
				}
			}
			if(null != userIdStr){
				$.messager.confirm("提示","确定增加所选中项？",function(r) {
					if(r === true){
						var uri = that.path + "/attendanceConf/attendancepeopleConfInfoadd";//执行添加的url地址
						var ajax_param = {
						        url : uri,
								type : "post",
								datdType : "json",
								data : {
							        "attendanceId":attendanceId,
							        "userIdStr":userIdStr,
									"t" : Math.random()
								},
								success : function(data) {
									if (data == false) {
										$.messager.alert("错误","添加失败","error");
									} else {
										console.log(Sys.setPersonnel);
										$('#popWin2').window('close');
										parent.Sys.setPersonnel.reloadTable();
										
									}
								}
						};
						ajax_(ajax_param);
					}
				});
			}else{
				$.messager.alert('提示', '没有任何选中项', 'info');
			}
		},
		
		/**
		 *取消按钮
		 */
		cancelButton : function(){
			$("#popWin2").window("close");
		}
};

/**
 *使用jquery中函数初始化方法 
 */
$(function(){
	Sys.PersonnelSelection.initPersonnelTable();
	Sys.PersonnelSelection.initComboTree();
	Sys.PersonnelSelection.initCombobox();
	$("#userType").combobox({
		editable : false,
		onSelect : function(record){
			var selIndex = record.value;
			if(selIndex == 2){
				Sys.PersonnelSelection.selIndex = 2;
				$("#Supplier").closest("td").css("display","block");
				$("#department").closest("td").css("display","none");
			}else{
				Sys.PersonnelSelection.selIndex = 1;
				$("#Supplier").closest("td").css("display","none");
				$("#department").closest("td").css("display","block");
			}
		}
	});
	$("#popWin2").css("overflow-y","hidden");
});