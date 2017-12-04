$(document).ready(
    function() {
		// 初始化下拉列表树
		initTree();
		
		$("#searchBtn").click(function(){
			if ('' == $('#title').val()) {
				$("#errorText").html("请输入搜索关键字!");
				return false;
			} 
			//search($('#title').val());
			viewMore('/dashboardPageManage/mainresults?category=' + _currentNodeId + '&keywords=' + $('#title').val());
		});
	}
);

function search(keywords) {
	window.location.href =  '../dashboardPageManage/mainresults?category=' + _currentNodeId + '&keywords=' + keywords;
}

//树数据
var _treeData = "";

var _currentNodeId = -1;
var _currentNodeName = "";

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
}

/**
 * 初始化树菜单
 */
function initTree() {
	var path = getRootPatch();
	var uri = path + "/knowledgeTypeManage/findKnowledgeTypeTreeVal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "知识类别", "javascript:treeClickAction('0','无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].id;
				var _name = gtmdlToolList[i].title;
				var _parent = gtmdlToolList[i].parentTypeId;
				
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append(dataTree + "");
			//操作tree对象
			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}