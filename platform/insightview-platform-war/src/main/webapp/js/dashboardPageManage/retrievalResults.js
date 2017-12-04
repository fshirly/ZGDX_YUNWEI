/**
 * 首页检索跳转至检索结果页面
 */
//树数据
var _currentNodeId = -1;
var _currentNodeName = "";

$(document).ready(
    function() {
    	// 初始化下拉列表树 
    	initTree();
    	$('#title').searchbox({
    	    searcher:function(value,name){
    	    	//parent.toShowTabsInfo('/dashboardPageManage/retrievalResults?category=' + _currentNodeId + '&keywords=' + value);
    	    	doInitRetrievalTable(_currentNodeId ,value);
    	    },
    	    prompt:'输入关键字后按回车'
    	});
		doInitRetrievalTable(_currentNodeId);
	}
);

function treeClickAction(id, name) {
	if(parseInt(id)===0){
		_currentNodeId = -1;
	}else{
		_currentNodeId = id;
	}
	_currentNodeName = name;
	doInitRetrievalTable(_currentNodeId);
	reloadTableCommon("retrievalList");
}

/**
 * 初始化树菜单
 */
function initTree() {
	var path = getRootPatch();
	var url = path + "/knowledgeTypeManage/findKnowledgeTypeTreeVal";
	$("#retrievalTreeDiv").f_tree({
		url:url,
		onSelect: function(node) {
			treeClickAction(node.id,node.title);
		},
		parentIdField: 'parentTypeId'
	});
}

function doInitRetrievalTable(_currentNodeId ,keywords) {
	var path = getRootName();
	var keywords = keywords == null || keywords ==""?$('#title').val():keywords;
	var height = $("#getHeightDiv").height()-15; 
	var width = $(".treetabler").width();
	$('#retrievalList').datagrid(
					{
						width : width,
						height : height,
						nowrap : false,
						striped : true,
						border : false,
						collapsible : false,// 是否可折叠的
						url : path + '/dashboardPageManage/queryRetrievalResults?keywords=' + keywords + "&category=" + _currentNodeId,
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						columns : [ [
								{
									field : 'title' ,
									width : width-10,
									align : 'left',
									formatter : function(value, row, index) {
										var content = row.content;
										content = content.replace(keywords,'<em style="color:red;font-style:normal">' + keywords + '</em>');
										return '<a title="点击查看详情：' + value + '" class="easyui-tooltip" style=\'cursor:pointer\' onclick="javascript:toLook('
												+ row.id
												+ ');"><font face="verdana" size="3">' + value.replace(keywords,'<em style="color:red;font-style:normal">' + keywords + '</em>') + '</font></a></br>' + content + '<br></br>';
									}
								} ] ]
					});
	$(".datagrid-header").css("display","none");
	/*$(window).resize(function() {
		$('#retrievalList').resizeDataGrid(0, 0, 0, 0);
	});*/
}

/**
 * 点击标题查看标题和内容
 * @param id
 */
function toLook(id){
	parent.$('#popWin').window({
    	title : '查看知识检索详细信息' ,
        width : 760 ,
        height : 355 ,
        minimizable : false ,
        maximizable : false ,
        collapsible : false ,
        modal : true ,
        href: getRootName() + '/dashboardPageManage/retrievalResultsView?id=' + id
    });
}