/**
 * 人员设置
 * Created By ZhaoYP
 * 2015-5-19 
 */
var Sys = Sys || {};
Sys.setPersonnelDetail={
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
				singleSelect : false,
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,
				columns : [ [{
			                        field : 'userAccount',
			                        title : '姓名',
			                        align : 'center',
			                        width : 150
			                   },
							   {
									field : 'userName',
									title : '用户名',
									align : 'center',
									width : 150
								},
								{
									field : 'userTypeName',
									title : '用户类型',
									align : 'center',
									width : 180,
								},
								{
									field : 'provide_Org',
									title : '部门/供应商',
									align : 'center',
									width : 180
								}
							] ]
        	});
        }
};

/**
 *使用jquery中函数初始化方法 
 */
$(function(){
	Sys.setPersonnelDetail.initPersonnelTable();
});