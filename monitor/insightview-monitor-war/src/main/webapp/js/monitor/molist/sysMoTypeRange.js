$(document).ready(function() {
	initTree();
});


/**
 * 初始化树菜单
 */
var treeLst=[];
function initTree() {
	var path = getRootPatch();
	var uri = path + "/monitor/deviceManager/initDeviceTree";
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
			dataTree.add(0, -1, "监测对象", "");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _classId = gtmdlToolList[i].classId;
				var _classLable = gtmdlToolList[i].classLable;
				var _parentClassId = gtmdlToolList[i].parentClassId;
				var _newParentID = gtmdlToolList[i].newParentID;
				var _relationID = gtmdlToolList[i].relationID;
				var _relationPath = gtmdlToolList[i].relationPath;
				treeLst.push(_relationID);
				dataTree.add(_relationID, _newParentID, _classLable, "javascript:treeClickAction('" + _classId + "','" + _classLable + "','" + _parentClassId +"','" + _relationPath + "');");
			}	
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			toSerach(dataTree);
			//操作tree对象   
			//dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}


//菜单定位
function toSerach(treedata){   
	var treeName=$("#treeName").val();
	if(treeName==""){
		//dataTree.add(0, -1, "监测对象", "");
		treeName="网络设备";
	}
		var path=getRootName();
		var uri=path+"/monitor/deviceManager/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"classLable":treeName,    
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data != null && data != ""){	
				var classId = data.classId;
				var classLable = data.classLable;
				var parentClassId = data.parentClassId;
				var relationID = data.relationID;
				var relationPath = data.relationPath;
				treedata.openTo(relationID,true);				
				treeClickAction(classId, classLable, parentClassId,relationPath);
			}
		}
		};
		ajax_(ajax_param);
	
}

Array.prototype.in_array = function(e) {  
	 for(i=0;i<this.length;i++){  
		 if(this[i] == e){
			 return true;  
		 } 
	 }  
return false;  
}


//树数据
var _treeData = "";
var _currentNodeId = -1;
var _currentNodeName = "";
var _currentParnetId = "";
var _relationPath = "";

function treeClickAction(id, name,parentId,relationPath) {
	_currentNodeId = id;
	_currentNodeName = name;
	_currentParnetId = parentId;
	_relationPath = relationPath;
	window.$('#component_2').attr('src',getRootPatch()+'/monitor/sysMoTemplate/toShowConfigRange?moClassID='+id);
}