$(function() {
	doInitAnnouncementTable();
});

function doInitAnnouncementTable() {
	var path = getRootName();
	$('#announceList').datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns : true,
						fit : true,
						url : path + '/announcement/queryItsmAnnList',
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						pageList : [5],
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'title',
									title : '标题',
									width : 240,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a  style=\'cursor:pointer\' title="' + value +'" class="easyui-tooltip" onclick="javascript:toLook('
												+ row.id
												+ ');">' + value + '</a>';
									}
								},
								{
									field : 'createTime',
									title : '创建时间',
									width : 150,
									align : 'center'
								} ] ]
					});
}

function toLook(id){
	parent.parent.$('#popWin').window({
    	title : '通知公告查看' ,
        width : 760 ,
        height : 355 ,
        minimizable : false ,
        maximizable : false ,
        collapsible : false ,
        modal : true ,
        href: getRootName() + '/announcement/announcementDetailView?id=' + id
    });
}

function viewMore(){
	openwindow(getRootName() + '/announcement/queryItsmAnnouList','',1160,400);
}