///$(function() { 
//var myJson = eval('([{"id":1,"text":"所有设备","children":[{"id":2,"text":"Linux","state":"open","children":[{"id":"11","text":"192.168.0.1","checked":true},{"id":12,"text":"192.168.0.2"}]}]}])');
// var myJson = eval('([{"id":1,"text":"所有设备","children":[{"id":100000000,"text":"未知","state":"open","children":[{"id":35,"text":"192.168.0.8","checked":true},{"id":36,"text":"192.168.0.57","checked":true},{"id":37,"text":"192.168.0.85","checked":true}]},{"id":100000001,"text":"Linux","state":"open","children":[{"id":5,"text":"192.168.0.1","checked":true},{"id":52,"text":"192.168.1.201","checked":true}]},{"id":100000002,"text":"Windows","state":"open","children":[{"id":38,"text":"192.168.0.140","checked":true},{"id":48,"text":"192.168.0.146","checked":true}]}]}])');
// $('#deviceTree').tree({data: myJson});
//});
$(function() {
	var taskID = $("#taskID").val();
	initTree(taskID);
	// 使用JQuery从后台获取JSON格式的数据
//	$.ajax( {
//		type : "POST",
//		url : "monitor/discover/getTreeJSON?taskID=" + taskID,// 发送请求地址
//		contentType : "application/json",
//		dataType : "json",
//		success : function(data) {
//			$('#deviceTree').tree({
//				data : eval(data.treeJSON)
//			});
//		},
//		// 请求出错的处理
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//			$.messager.alert("请求出错", "ajax_error", "error");
//		}
//	});
});

/**
 * 初始化树菜单
 */
var dataTree = new dTree("dataTree",  getRootPatch() + "/plugin/dTree/img/");
dataTree.config.folderLinks=true;
dataTree.config.useCookies=false;
dataTree.config.check=true;
function initTree(taskID) {
	var path = getRootPatch(); 
	var uri = path + "/monitor/discover/getTreeJSON?taskID=" + taskID;
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
			dataTree.add(0, -1, "所有设备", "javascript:;",'');
			// 得到树的json数据源
			var jsonData = eval('(' + data.treeJSON + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			var tmpOS;
			var parentID=999999900;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var moid = gtmdlToolList[i].moid;
				var deviceip = gtmdlToolList[i].deviceip;
//				var os = gtmdlToolList[i].os; 
				var classLable = gtmdlToolList[i].classLable; 
				
				/*以前是以操作系统来分类的。
				 * if(os== null || os==''){
					parentID=999999900;
					if(i==0){
						dataTree.add(parentID, 0, '未知', "javascript:;",'');
					} 
				}
				if (os!=null && os!='') {
					if(tmpOS!=os){
						parentID++; 
						tmpOS = os;
						dataTree.add(parentID, 0, os, "javascript:;",'');
					}
					
				}*/
				
				// 现在是以设备类型来分类的  modify by zhuk start
				if (classLable!=null && classLable!='') {
					if(tmpOS!=classLable){
						parentID++; 
						tmpOS = classLable;
						dataTree.add(parentID, 0, classLable, "javascript:;",'');
					}
					
				}// 现在是以设备类型来分类的  modify by zhuk end
				
				
				dataTree.add(moid, parentID, deviceip, "javascript:;",'');
			}
			
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append(dataTree + "");
			//操作tree对象
			dataTree.closeAll();
		}
	}
	ajax_(ajax_param);
}

function store() {
//	console.log(">>>>>");
//	document.getElementById('btnSave').disabled = true;
	$("#btnSave").attr('disabled', true).css({
		                                      'background':'#AFAEAE',
		                                      'cursor':'context-menu',
		                                      'border':0,
		                                      'border-color':'none'
		                                    	  });
	var selids=dataTree.getCheckedNodes();  
	// var checked = $('#dataTreeDiv').tree('getCheckedNodes');
	// var unchecked1 = $('#tt').tree('getChecked', 'unchecked');
	var moids;
	for ( var i = 0, l = selids.length; i < l; i++) {
		// for(var key in checked1[i]){
		// alert(checked1[i][key]);
		// }
		//moids += checked[i].id + ",";
		moids +=selids[i]+","; 
	}
	if(moids==null){
		$.messager.alert("提示", "请选择要导入的设备！", "error");
		$("#btnSave").removeAttr("disabled").css({
		            'background':'#36a3d9',
		            'cursor':'pointer',
		            'border':'1px solid',
		            'border-color':'#5cb8e6 #297ca6 #297ca6 #5cb8e6'
		}).mouseover(function(){
		$(this).css('background','#005580');
		}).mouseout(function(){
		$(this).css('background','#36a3d9');
		});
		return;
	}
	if(moids!=null){
		moids = moids.replace("undefined,", "");
	}
	var path = getRootPatch(); 
	var uri = path+"/monitor/discover/storeDiscoverResult";
	$.ajax( {
		type : "POST",
		url : uri,
		async:false,
		dataType : "json",
		data : {
			"moids" : moids,
			"t" : Math.random()
		},
		success : function(data) {   
			if(data == true) { 
				$.messager.alert("提示","保存成功！","info"); 
//				document.getElementById('btnSave').disabled = false;
				$("#btnSave").removeAttr("disabled").css({
					                                      'background':'#36a3d9',
                                                          'cursor':'pointer',
                                                          'border':'1px solid',
                                                          'border-color':'#5cb8e6 #297ca6 #297ca6 #5cb8e6'
				}).mouseover(function(){
					$(this).css('background','#005580');
				}).mouseout(function(){
					$(this).css('background','#36a3d9');
				});
				//window.location.href = getRootName()+'/monitor/discover/discoverDevice';
			}else{
				$("#btnSave").removeAttr("disabled").css({
				                    'background':'#36a3d9',
				                    'cursor':'pointer',
				                    'border':'1px solid',
				                    'border-color':'#5cb8e6 #297ca6 #297ca6 #5cb8e6'
				}).mouseover(function(){
				$(this).css('background','#005580');
				}).mouseout(function(){
				$(this).css('background','#36a3d9');
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert("请求出错", "请求出错！", "error");
			$("#btnSave").removeAttr("disabled").css({
									                'background':'#36a3d9',
									                'cursor':'pointer',
									                'border':'1px solid',
									                'border-color':'#5cb8e6 #297ca6 #297ca6 #5cb8e6'
			}).mouseover(function(){
				$(this).css('background','#005580');
			}).mouseout(function(){
				$(this).css('background','#36a3d9');
			});
		}
	}); 
}

function afresh() { 
var navigationBar = $("#navigationBar").val();
	window.location.href = getRootName()+'/monitor/discover/discoverDevice?navigationBar='+navigationBar;
}