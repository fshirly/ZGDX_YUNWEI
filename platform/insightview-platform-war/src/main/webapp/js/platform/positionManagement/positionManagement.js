/**
 * create by zhaoyp
 */

/**
 *定义空间名称为System.pf.func 
 */
var System = System || {};
System.PostMgm = {
		//服务器地址前缀
		path : getRootName(),
		currentNodeId : "-1",
		currentNodeName :"",
		currentLevel : "",
	    
		/**
	     *初始化树函数
	     */
		
	    initTree : function(){
	    	var that  = this,
		        url = that.path +"/permissionOrganization/findOrganizationList";
		    $("#dataTreeDiv").f_stree({
		    	url : url,
		    	rootName : "单位组织",
		    	onSelect : function(node){
		    		that.treeClickAction(node.id, node.title,node.parentId);
		    	},
		    	idField : "organizationID",
		    	titleField : "organizationName",
		    	parentIdField : 'parentOrgID'
		    });
	    },
	    
	    /**
	     *初始化datagrid表格函数
	     */
	    
	    doInitTable : function(){
	    	var that = this;
		    $("#positionMessageTable").datagrid({
		    	iconCls : 'icon-edit',// 图标
				width : 'auto',
				height : 'auto',
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,// 是否可折叠的
				fit : true,// 自动大小dd
				fitColumns : true,
				url :that.path + '/platform/positionManagementList',
				remoteSort : false,
//				idField : 'fldId',
				singleSelect : true,// 是否单选
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,// 分页控件
				rownumbers : true,// 行号
				toolbar : [ {
					text : '新建',
					iconCls : 'icon-add',
					handler : function() {
						that.newPost();
					}
				}, {
					text : '删除',
					iconCls : 'icon-cancel',
					handler : function() {
						that.doBatchDel();
					}
				} ],
				columns : [ [
						{
							field : 'postID',
							checkbox : true
						},
						{
							field : 'postName',
							title : '岗位名称',
							align : 'center',
							width : 90
						},
						{
							field : 'organizationName',
							title : '所属组织',
							align : 'center',
							width : 90
						},
						{
							field : 'isImportant',
							title : '重要岗位',
							align : 'center',
							width : 90,
							formatter : function(value){
								if(value === 1){
									return "是";
								}else if(value === 2){
									return "否";
								}
							}
						},
						{
							field : 'postPersonalNum',
							title : '人数',
							align : 'center',
							width : 90,
							formatter : function(value){
								if(value ===null || "null"===value){
									return "0";
								}else{
									return value;
								}
							}
						},
						{
							field : 'option',
							title : '操作',
							width : 130,
							align : 'center',
							formatter : function(value, row, index) {
								return '<a  style=\'cursor:pointer\' title="查看" class="easyui-tooltip" onclick="javascript:System.PostMgm.toLook('
										+ row.postID
										+ ');"><img src="'
										+ that.path
										+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style=\'cursor:pointer\' title="修改" class="easyui-tooltip" onclick="javascript:System.PostMgm.doUpdate('
										+ row.postID
										+ ');"><img src="'
										+ that.path
										+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style=\'cursor:pointer\' title="刪除" class="easyui-tooltip" onclick="javascript:System.PostMgm.doDel('
										+ row.postID
										+ ');"><img src="'
										+ that.path
										+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
							}
						} ] ]
		    });
			$(window).resize(function() {
				$('#positionMessageTable').resizeDataGrid(0, 0, 0, 0);
			});
	    },
	    
	    /**
	     *点击树节点产生的动作反应 
	     */
	    
	    treeClickAction : function(id,name,level){
	    	var nodeSel_id = "";
	        if($(".nodeSel")){
	            nodeSel_id = $(".nodeSel").attr("id");
	        }
	    	this.currentNodeId = id;
	    	this.currentNodeName = name;
	    	this.currentLevel = level;
	    	this.reloadTable();
	    },
	    
	    /**
	     *新建弹出事件 
	     */
	    newPost :　function(){
	    	var that = this;
	    	if (that.currentNodeId == "-1") {
	    		$.messager.alert('提示', '请先选择服务目录节点', 'info');
	    	} else if (that.currentLevel === -1) {
	    		$.messager.alert('提示', '根目录节点下无法创建新节点', 'info');
	    	} else {
                parent.$('#popWin').window({
	    							   title : '新建岗位信息',
	    							   width : 700,
	    							   height : 350,
	    							   minimizable : false,
	    							   maximizable : false,
	    							   collapsible : false,
	    							   modal : true,
	    							   href : that.path + '/platform/positionManagementPopPage?condition=newAdd&organizationID='+that.currentNodeId 
	    		});
	    	}
	    },
	    
	    /**
	     *删除功能 
	     */
	    doBatchDel : function(){
	    	var checkedItems = $('[name=postID]:checked');
	        var postIDs = null;
	        var that = this;
	        $.each(checkedItems, function(index, item) {
		        if (null == postIDs) {
		        	postIDs = $(item).val();
		        } else {
		        	postIDs += ',' + $(item).val();
		        } 
	        });
            if (null != postIDs) {
		        $.messager.confirm("提示","确定删除所选中项？",function(r) {
					if (r == true) {
						var uri = that.path+"/platform/doBatchDel?postIDs=" + postIDs;
						var ajax_param = {
							        url : uri,
									type : "post",
									datdType : "json",
									data : {
										"t" : Math.random()
									},
									success : function(data) {
										if (data == false) {
											that.reloadTable();
											$.messager.alert("错误","存在岗位信息被用户引用，不允许删除","error");
										} else {
											that.reloadTable();
										}
									}
								};
						ajax_(ajax_param);
				     }
				});
	          }else {
		          $.messager.alert('提示', '没有任何选中项', 'info');
	         }
	     },
	     
	     /**
	      * 重载表单
	      */
	     reloadTable : function(){
	    		var positionName_main = $("#positionName_main").val();
	    		var isImportantPosition = $("#isImportantPosition").combobox("getValue");
	    		$('#positionMessageTable').datagrid('options').queryParams = {
	    			"organizationID" : this.currentNodeId,
	    			"postName" : positionName_main,
	    			"isImportant" : isImportantPosition
	    		};
	    		$('#positionMessageTable').datagrid('load');
	    		$('#positionMessageTable').datagrid('unselectAll');
	    	    $('#positionMessageTable').datagrid('uncheckAll');
	     },
	     
	     /**
	      * 查看
	      */
	     toLook : function(id){
	    	    var that = this; 
	    		parent.$('#popWin').window({
	    			title : '查看岗位信息',
	    			width : 700,
	    			height : 350,
	    			minimizable : false,
	    			maximizable : false,
	    			collapsible : false,
	    			modal : true,
	    			href : that.path + '/platform/positionManagementPopPage?id='+id +'&condition=look'
	    		});
	     },
	     
	     /**
	      *编辑修改 
	      */
	     doUpdate : function(id){
	    	 var that = this;
	    	 parent.$('#popWin').window({
	    			title : '编辑岗位信息',
	    			width : 700,
	    			height : 350,
	    			minimizable : false,
	    			maximizable : false,
	    			collapsible : false,
	    			modal : true,
	    			href : that.path + '/platform/positionManagementPopPage?id='+id +'&condition=edit'
	    		});
	     },
	     
	     /**
	      *删除单条信息 
	      */
	     doDel : function(id){
	    	 var that = this;
	    	 $.messager.confirm("提示","确认删除此条信息？",function(r){
	    		 if (r == true) {
	    			var uri =that.path + "/platform/doDelete?id="+id;
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
	    							if (data == false) {
	    								$.messager.alert("错误", "岗位信息被用户引用，不允许删除","error");
	    							}else if(data == "init"){
	    								$.messager.alert("提示", "系统初始化数据，不能删除", "info");
	    							}else {
	    								that.reloadTable();
	    							}
	    						}
	    					};
	    			ajax_(ajax_param);
	    		 }
	         });
	     },
	   
	   /**
	    *重置查询条件
	    */
	   resetQueryCondition : function(){
		   $("#positionName_main").val("");
		   $("#isImportantPosition").combobox("setValue","");
	   }
	      
};

/**
 *使用jquery中函数初始化方法 
 */
$(function(){
	System.PostMgm.initTree();//初始化树
	System.PostMgm.doInitTable();//初始化datagrid表格
	$("#isImportantPosition").combobox();
});