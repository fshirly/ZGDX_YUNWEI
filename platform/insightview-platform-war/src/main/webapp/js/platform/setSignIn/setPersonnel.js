/**
 * 人员设置
 * Created By ZhaoYP
 * 2015-5-19 
 */
var Sys = Sys || {};
Sys.setPersonnel={
		path : getRootName(),
		
		/**
		 *初始化已选择人员表单 
		 */
        initPersonnelTable : function(){
        	var that = this;
        	var param = "";
        	var uri = that.path+"/attendanceConf/attendancepeopleConf_list_select?attendanceId="+attendanceId;//初始化已选择人员表url
        	$("#personnelTable").datagrid({
        		iconCls : 'icon-edit',
				width : 'auto',
				height : 'auto',
				nowrap : false,
				striped : true,
				fit:true,
				fitColumns:true,
				border : true,
				collapsible : false,
				url : uri,
				remoteSort : false,
				idField : 'id',
				singleSelect : true,
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,
				toolbar : [ {
					text : '新增',
					iconCls : 'icon-add',
					handler : function() {
						that.addPage();
					}
				}],
				onLoadSuccess: function(data) {
					console.log(data);
				},
				columns : [ [
		                       {
					  			   field : 'id',
					               checkbox : true   
					  		   },{
			                        field : 'userAccount',
			                        title : '姓名',
			                        align : 'center',
			                        width : 140
			                   },
							   {
									field : 'userName',
									title : '用户名',
									align : 'center',
									width : 140
								},
								{
									field : 'userTypeName',
									title : '用户类型',
									align : 'center',
									width : 150,
								},
								{
									field : 'provide_Org',
									title : '部门/供应商',
									align : 'center',
									width : 172
								},
								{
									field : 'ids',
									title : '操作',
									align : 'center',
									width : 190,
									formatter : function(value,row,index){
									return '<a style="cursor: pointer;" title="删除" onclick="javascript:Sys.setPersonnel.doDel('
									+ row.id
									+ ')"><img src="'
									+ that.path
									+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								}
							] ]
        	});
        },
        
        /**
         *选择增加人员弹出页 
         */
        addPage : function(){
        	 var that = this;
				parent.$("#popWin2").window({
					title : "选择人员",
					width : 700,
					height : 500,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					href : that.path + "/attendanceConf/attendancepeopleConf_add?attendanceId="+attendanceId
				});
        },
        
        /**
         *删除单行 
         */
        doDel : function(id){
        	var that = this;
        	$.messager.confirm("提示", "确定删除此信息?", function(r) {
        		if(r==true){
        	     	var uri = that.path + "/attendanceConf/deleteAttendancePeopleConf";//执行删除url
                	var ajax_param = {
        			        url : uri,
        					type : "post",
        					datdType : "json",
        					data : {
                		        "id":id,
        						"t" : Math.random()
        					},
        					error : function() {
        						$.messager.alert("错误", "ajax_error", "error");
        					},
        					success : function(data) {
                                that.reloadTable();
        					}
        			};
        			ajax_(ajax_param);
        		}
        	});
   
        },
        
        /**
         *刷新列表 
         */
        reloadTable : function(){
        	var queryParams = {
        	  		"attendanceId" :attendanceId
        	  	};
        	  	$('#personnelTable').datagrid('options').queryParams=queryParams;
        	  	$('#personnelTable').datagrid('load');
        	  	$('#personnelTable').datagrid('uncheckAll');
        	/*Sys.setPersonnel.initPersonnelTable();
        	$('#personnelTable').datagrid('unselectAll');
   	        $('#personnelTable').datagrid('uncheckAll');*/
        }
};

/**
 *使用jquery中函数初始化方法 
 */
$(function(){
	Sys.setPersonnel.initPersonnelTable();
});