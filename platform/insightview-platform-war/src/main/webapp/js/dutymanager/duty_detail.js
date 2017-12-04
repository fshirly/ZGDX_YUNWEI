f(function(){
	(function(){
		f('#orders').datagrid({
			iconCls : 'icon-edit',// 图标
			url : getRootName() + '/platform/dutymanager/duty/ordersOfDuty',
			fit : true,// 自动大小
			fitColumns : true,
			remoteSort: false,
			striped : true,
			idField : 'ID',
			rownumbers : true,// 行号
			scrollbarSize:0,
			sortName : 'BeginPoint',
			sortOrder : 'asc',
			queryParams : {dutyId : f('[name="id"]').val()},
			columns : [ [
			             {
			            	 field : 'Title',
			            	 title : '班次名称',
			            	 align : 'center',
			            	 width : 120
			             },
			             {
			            	 field : 'BeginPoint',
			            	 title : '开始时间',
			            	 align : 'center',
			            	 width : 80
			             },
			             {
			            	 field : 'EndPoint',
			            	 title : '结束时间',
			            	 align : 'center',
			            	 width : 80
			             },
			             {
			            	 field : 'Dutyers',
			            	 title : '值班人',
			            	 align : 'center',
			            	 width : 120,
			            	 formatter : function(value,row,index){
			            		 if (value) {
			            			 value = row.Principal + ',' + value;
			            			 var names = value.split(',');
			            			 if (names.length > 2) {
			            				 return '<span title="' + value + '">' + names.slice(0,2).join(',') + '...' + '</span>';
			            			 } 
			            			 return value;
			            		 }
			            		 return row.Principal;
			            	 }
			             },
			             {
			            	 field : 'Principal',
			            	 title : '值班负责人',
			            	 align : 'center',
			            	 width : 100
			             }] ]
		});
	})();
});