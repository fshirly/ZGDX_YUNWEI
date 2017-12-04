var tabsLst = [];
$(document).ready(function() {	
	initTree();
});

//树数据
var _treeData = "";
var _currentNodeId = -1;
var _currentNodeName = "";
var _currentParnetId = "";

function treeClickAction(id, name,parentId) {
	_currentNodeId = id;
	_currentNodeName = name;
	_currentParnetId = parentId;
	if(id=="0"){//表示单位部门
		window.$('#component_2').attr('src',getRootPatch()+'/platform/deptViewManager/toOrgUseSubNetInfo?treeId=-1');
	}else if(id.indexOf("O") == 0){//点击单位
		window.$('#component_2').attr('src',getRootPatch()+'/platform/deptViewManager/toOrgUseSubNetInfo?treeId='+id);
	}else if(id.indexOf("D") == 0){//点击部门
		window.$('#component_2').attr('src',getRootPatch()+'/platform/deptViewManager/toDeptUseSubNetInfo?treeId='+id);
	}
}

/**
 * 初始化树菜单
 */
var treeLst=[];
function initTree() {
	var path = getRootPatch();
	var uri = path + "/permissionDepartment/findOrgAndDeptVal";
	var partmentTreeImgPath = path + "/plugin/dTree/img/tree_department.png";
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "单位部门",  "javascript:treeClickAction('0','无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].id;
				var _name = gtmdlToolList[i].name;
				var _parent = gtmdlToolList[i].parentId;
				if(_id.indexOf("O") == 0) {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name +  "','" + _parent +  "');","单位","",orgTreeImgPath,orgTreeImgPath);
				} else {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name +  "','" + _parent +  "');","部门","",partmentTreeImgPath,partmentTreeImgPath);
				}
			}	
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			//toSerach(dataTree);
			//操作tree对象   
			//dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}

//菜单定位
function toSerach(treedata){   
	var treeName=$("#treeName").val();
//	console.log(treeName);
	if(treeName=="" || treeName==null){
		treeClickAction("0", "单位部门","-1");
	}else{
		var firstOrgId = treedata.aNodes[1].id.substring(1)
		var path=getRootName();
		var uri=path+"/platform/deptViewManager/searchTreeNodes?treeName="+treeName+"&firstOrgId="+firstOrgId;
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
			"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data != null && data != ""){	
				var id = data.id;
				var parentId = data.parentId;
				var name = data.name;
				treedata.openTo(id,true);				
				treeClickAction(id, name,parentId);
			}
		}
		};
		ajax_(ajax_param);
	}
	
}

Array.prototype.in_array = function(e) {  
	 for(i=0;i<this.length;i++){  
		 if(this[i] == e){
			 return true;  
		 } 
	 }  
return false;  
}