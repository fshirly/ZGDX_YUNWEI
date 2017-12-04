var tabsLst = [];
$(document).ready(function() {	
	$('#tabs_window').tabs({   
		onClose:function(title){   
			tabsLst.remove(title);     
	      }   
	  });
	initTree();
});

//树数据
var _treeData = "";
var _currentNodeId = -1;
var _currentNodeName = "";
var _currentParnetId = "";
var _relationPath = "";
var _navigationBar="";

function treeClickAction(id, name,parentId,relationPath) {
	_navigationBar=$('#navigationBar').val();
	var navigationBar =_navigationBar.split(">>")[0]+">>"+_navigationBar.split(">>")[1]+">>"+this.getNavigationBar(relationPath,'/');
	_currentNodeId = id;
	_currentNodeName = name;
	_currentParnetId = parentId;
	_relationPath = relationPath;
	if(id=="2"){//表示网络设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=5,6,59,60'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="106"){//表示安全设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/safeDeviceManager/toDeviceList?mOClassID=62,117,118,132,133,135,136,137'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="132"){
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=132'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="133"){//表示移动应用代理
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=133'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="135"){//表示代理网关
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=135'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="134"){//表示负载均衡
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=134'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="137"){//表示网闸
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=137'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="136"){//表示探针
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=136'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="138"){//表示虚拟机（新增的设备）
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=138'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="62"){//防火墙
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/safeDeviceManager/toDeviceList?mOClassID=62'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="117"){//光闸
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/safeDeviceManager/toDeviceList?mOClassID=117'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="118"){//VPN
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/safeDeviceManager/toDeviceList?mOClassID=118'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="3"){//表示主机
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=7,8,9'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="4"){//表示虚拟设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toPhysicsList?mOClassID=8,75'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="8"){//表示虚拟主机
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toPhysicsList?mOClassID=8'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="44"){//44  表示机房环境Manager  
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toZoneManagerList?mOClassID=44'+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="45"){//45//表示阅读器
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toRoomList?mOClassID='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="10"){//表示接口
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toInterfaceList?mOClassID='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="11"){//表示磁盘
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDiscList?mOClassID='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="12"){//表示cpu
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toCpuList?mOClassID='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="13"){//表示内存
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toMemoryList?mOClassID='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="14" || id=="15" || id=="16" || id=="54" || id=="81" || id=="86"){	
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/dbObjMgr/toDataBaseList?dbmstype='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="17" || id=="18" || id=="19" || id=="20"  || id=="53"){//表示中间件
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddlewareList?jmxType='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="41"){//表示中间件内存池
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddlewareMemPool?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="42"){//表示中间件Java虚拟机
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddlewareJvm?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="22" ){//oracle数据库
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toOrclDbInfoList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="25" ){//oracle数据文件
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toOrclDataFileList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="23" ){//oracle表空间
		if (parentId == "56") {
			window.$('#component_2').attr('src',getRootPatch()+'/monitor/db2Manage/toShowDb2TbsInfo'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
		} else {
			window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toShowOrclTbsInfo'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
		}
	}else if( id=="24" ){//oracle回滚段
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toOrclRollSEGList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="26" ){//oracle数据库实例
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toOrclInstanceList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="27" ){//SGA
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/orclDbManage/toOrclSgaList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="28" ){//MysqlServer
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/dbObjMgr/toMysqlServer'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="29" ){//数据库 
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/dbObjMgr/toMysqlDB'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="30" ){//运行对象
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/dbObjMgr/toMysqlRunObj'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="31" ){//系统变量
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/dbObjMgr/toMysqlSysVar'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="32" ){//J2EE应用
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toJ2eeAppList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="33" ){//WebModule
		var jmx = relationPath.split("/")[3];
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toWebModuleList?jmxType='+jmx+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="34" ){//Servlet
		var jmx = relationPath.split("/")[3];
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toServletList?jmxType='+jmx+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="36" ){//线程池
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toThreadPoolList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="37"){//表示类加载信息
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddleClassLoadList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="38"){//表示JDBC连接池
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddleWareJdbcPoolList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="39"){//表示JMS信息
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddleWareJMSList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="40"){//表示JTA信息
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toMiddleWareJTAList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if( id=="43" ){//jdbc资源
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/DeviceTomcatManage/toJDBCDSList?jmxType='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if("5,7,9".indexOf(id) != -1){//表示单类型的设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="46"){//表示温度电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toTemperatureTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="47"){//表示水带电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toWaterHoseTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="48"){//表示温湿度电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toTemperatureHumidityTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="49"){//表示门磁感应电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toDoorMagneticTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="50"){//表示干节点电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toDryContactTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="52"){//表示温度电子标签
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/envManager/toTagList?moClassId='+id+'&id='+id+'&navigationBar='+encodeURI(navigationBar));	
	}else if(id=="-1"){//-1表示未知类型
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toUnknownDeviceList?mOClassID=-1'+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="55" ){//db2数据库实例
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/db2Manage/toDb2InstanceList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="56" ){//db2数据库
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/db2Manage/toDb2InfoList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}
	/*else if( id=="58" ){//db2表空间
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/db2Manage/toShowDb2TbsInfo');
	}*/
	else if( id=="57" ){//db2缓冲池
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/db2Manage/toShowDb2BufferPoolInfo'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="59" || id=="60"){//表示二层、三层交换机
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="6"){//表示交换机
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=6,59,60'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="75"){//表示vCenter
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toDeviceList?mOClassID=75'+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="87" ){//MssqlServer
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/msDbManage/toMsServerList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="88" ){//Mssql数据库设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/msDbManage/toMsDeviceList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="80" ){//Mssql数据库
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/msDbManage/toMsDBList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="82" ){//Sybase
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/sybaseDbManage/toSybaseServerList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="83" ){//Sybase数据库设备
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/sybaseDbManage/toSybaseDeviceList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if( id=="84" ){//Sybase数据库
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/sybaseDbManage/toSybaseDBList'+'?id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="85"){//表示数据存储
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/deviceManager/toStorageList?mOClassID='+parentId+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="89"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/process/toProcessInfo?'+'navigationBar='+encodeURI(navigationBar));
	}else if(id=="90"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/webSite/toWebSiteInfo?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="91"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/webSite/toWebSiteDnsInfo?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="92"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/webSite/toWebSiteFtpInfo?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="93"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/webSite/toWebSiteHttpInfo?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}else if(id=="94"){//进程
		window.$('#component_2').attr('src',getRootPatch()+'/monitor/webSite/toWebSitePortInfo?mOClassID='+id+'&relationPath='+relationPath+'&id='+id+'&navigationBar='+encodeURI(navigationBar));
	}
}

/**
 * 初始化树菜单
 */
var treeLst=[];
var nodeNameLst=[];
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
				//97-机房监控   96-空调  73-ups
				if(_classId !=97 && _classId !=96 && _classId !=73){
					treeLst.push(_relationID);
					nodeNameLst.push(_classId + '&' + _classLable);
					dataTree.add(_relationID, _newParentID, _classLable, "javascript:treeClickAction('" + _classId + "','" + _classLable + "','" + _parentClassId +"','" + _relationPath + "');");
				}
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

function getNodeName(nodeid){
    var len = nodeNameLst.length;
	if(len <= 0){
		return "";
	}
	
	for(var i = 0; i < len; i++){
	  var names = new Array();
       names = nodeNameLst[i].split('&');
	   var len1 = names.length;
	   if(len1 == 2){
	   	if(nodeid == names[0])
		{
			return names[1];
		}
	   }
	}	
	
	return "";
};
//根据路径获取导航
function getNavigationBar (relationPath, split){
    var relationPaths = new Array();
    relationPaths = relationPath.split(split);
	
	var navigationBar = "";
	var len = relationPaths.length;
	if(len <= 0){
		return navigationBar;
	}
		
	for (var i = 0; i < len; i++) {
		if(navigationBar == ""){
			navigationBar = this.getNodeName(relationPaths[i]);
		} else if( i == (len - 1))
		{
			navigationBar = navigationBar + ' >> <span>' + this.getNodeName(relationPaths[i]) + '</span>';
		} else{
			navigationBar = navigationBar + ' >> ' + this.getNodeName(relationPaths[i]);
		}
	}

   return navigationBar;
};

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