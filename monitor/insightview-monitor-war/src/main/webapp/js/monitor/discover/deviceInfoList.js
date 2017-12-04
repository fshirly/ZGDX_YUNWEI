var flag='';
$(document).ready(function() {
	flag=$('#flag').val();
	$("#divDevice").empty();
	if(flag == "chooseMore"){
		$("#chooseButton").show();
		$('#divDevice').append("<table id='tblDeviceMore'></table>");
		doInitChooseTable();
	}else{
		$("#chooseButton").hide();
		$('#divDevice').append("<table id='tblDevice'></table>");
		doInitTable();
	}
	if(flag=='choose'){ 
		$('#tblDevice').datagrid('showColumn','moid');
	}else{
		$('#tblDevice').datagrid('hideColumn','moid');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblDevice')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/discover/selectDeviceList?devicetype='+$("#devicetype").val()+'&taskId='+$("#taskId").val()+'&moClassId='+$("#moClassId").val()+'&deviceType1='+$("#deviceType1").val(),
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : 'moid',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
									field : 'moid',
									title : '',
									width : 40,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('
												+ value
												+ ');">选择</a>';
									}
						        },
								{  
									field : 'deviceip',
									title : '设备IP',  
									width : 80,
									align : 'center',
									sortable : true/*,
									formatter : function(value, row, index) {
						        		if(flag=='choose'){
						        			return value;
						        		}else{
						        			if(row.moClassId != 0){
							        			return '<a style="cursor: pointer;" onclick="javascript:toShowView('
												+ row.moid
												+ ');">'+value+'</a>'; 
							        		}else{
							        			return value;
							        		}
						        		}
						        		
										
									}*/
								},
								{
									field : 'moname',
									title : '设备名称',
									width : 80,
									align : 'center',
									sortable : true,
								},
//								{
//									field : 'moalias',
//									title : '设备别名', 
//									width : 70,
//									align : 'center',
//									sortable : true,
//								},
								{
									field : 'nemanufacturername',
									title : '设备厂商', 
									width : 50,
									align : 'center',
									sortable : true,
								},{
									field : 'necategoryname',
									title : '设备类型', 
									width : 60,
									align : 'center',
									sortable : true,
								},{
									field : 'operstatus',
									title : '可用状态',
									width : 50,
									align : 'center',
									formatter:function(value,row){
										var rtn = "";
										if(value=="1"){
											rtn = "UP";
										}else if(value=="2"){
											rtn = "DOWN";
										}else if(value=="3"){
											rtn = "未知";
										}	
										return rtn; 
									}
								},{
									field : 'ismanage',
									title : '是否管理', 
									width : 50,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
										var rtn = "";
										if(value=="0"){
											rtn = "否";
										}else if(value=="1"){
											rtn = "是";
										}else{
											rtn = "";
										}	
										return rtn; 
									}
								},{
									field : 'alermlevelInfo',
									title : '告警级别',
									width : 50,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
									if(value=="" || value == null){
										return "正常";
									}else{
										return value;
									}	
								}
								},{
									field : 'necollectoridinfo',
									title : '所属采集机', 
									width : 70,
									align : 'center',
									sortable : true,
								}
//								,{
//									field : 'domainName',
//									title : '管理域', 
//									width : 60,
//									align : 'center',
//									sortable : true,
//								}
								]]
					});
		$(window).resize(function() {
		    $('#tblDevice').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitChooseTable(){
	var height = $(document.body).height() - 150;
	var path = getRootName();
	$('#tblDeviceMore')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : height,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:false,// 自动大小
						fitColumns:true,
						url : path + '/monitor/discover/selectDeviceList?devicetype='+$("#devicetype").val()+'&taskId='+$("#taskId").val()+'&moClassId='+$("#moClassId").val()+'&deviceType1='+$("#deviceType1").val(),
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : 'moid',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'moID',
									checkbox : true
								},
								{  
									field : 'deviceip',
									title : '设备IP',  
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'moname',
									title : '设备名称',
									width : 80,
									align : 'center',
									sortable : true,
								},
								{
									field : 'nemanufacturername',
									title : '设备厂商', 
									width : 50,
									align : 'center',
									sortable : true,
								},{
									field : 'necategoryname',
									title : '设备类型', 
									width : 60,
									align : 'center',
									sortable : true,
								},{
									field : 'operstatus',
									title : '可用状态',
									width : 50,
									align : 'center',
									formatter:function(value,row){
										var rtn = "";
										if(value=="1"){
											rtn = "UP";
										}else if(value=="2"){
											rtn = "DOWN";
										}else if(value=="3"){
											rtn = "未知";
										}	
										return rtn; 
									}
								},{
									field : 'ismanage',
									title : '是否管理', 
									width : 50,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
										var rtn = "";
										if(value=="0"){
											rtn = "否";
										}else if(value=="1"){
											rtn = "是";
										}else{
											rtn = "";
										}	
										return rtn; 
									}
								},{
									field : 'alermlevelInfo',
									title : '告警级别',
									width : 50,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
									if(value=="" || value == null){
										return "正常";
									}else{
										return value;
									}	
								}
								},{
									field : 'necollectoridinfo',
									title : '所属采集机', 
									width : 70,
									align : 'center',
									sortable : true,
								}
								]]
					});
	$(window).resize(function() {
	    $('#tblDeviceMore').resizeDataGrid(130, 0, 300, 0);
	});
}


function reloadTable() { 
	var deviceip = $("#deviceip").val(); 
	var flag=$('#flag').val();
	if(flag == "chooseMore"){
		$('#tblDeviceMore').datagrid('options').queryParams = {
			"deviceip" : deviceip
		};
		reloadTableCommon_1('tblDeviceMore');
	}else{
		$('#tblDevice').datagrid('options').queryParams = {
			"deviceip" : deviceip
		};
		reloadTableCommon_1('tblDevice');
	}
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**function mask(obj){
	obj.value=obj.value.replace(/[^\d]/g,'');
	key1 = event.keyCode
	if(key1 == 37 || key1 == 39){   
		obj.blur();
		nextip = parseInt(obj.name.substr(2,1))
		nextip = key1==37?nextip-1:nextip+1;
		nextip = nextip>=5?1:nextip
		nextip = nextip<=0?4:nextip
		eval("ip"+nextip+".focus()");
	}
	
	if(obj.value.length>=3)  
	if(parseInt(obj.value)>=256 || parseInt(obj.value)<=0){
		alert(parseInt(obj.value)+"IP地址错误！");
		obj.value = "";
		obj.focus();
		return false;
	} else {
		obj.blur();
		nextip = parseInt(obj.name.substr(2,1))+1
		nextip = nextip>=5?1:nextip
		nextip = nextip<=0?4:nextip
		eval("ip"+nextip+".focus()");
	}
}*/

function mask_c(obj){
	clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''));
}

function doInitMoList(){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			var interval='';
			var html='';
			for (var i=0;i<data.length;i++){ 
				html+="<input type='checkbox' id='ipt_mo"+i+"' name='moType'/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i]+"</br>"; 
				interval+="<select class='inputs' id='ipt_doIntervals"
						+i+"' style='width:80px'><option value='10'>10</option><option value='15'>15</option><option value='20'>20</option></select>"+"</br>";
			}
			document.getElementById("divEditMo").innerHTML=html;
			document.getElementById("divEditMoInterval").innerHTML=interval;
		}
	};	
	ajax_(ajax_param);
}


function toShowMo(taskId){
	resetForm('tblEditTask');
	$('#divEditTask').dialog('open')
	doInitMoList();
}

function toCheckMo(){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoListByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			for (var i=0;i<data.length;i++){ 
				var checkboxs=document.getElementsByName('moType');
//				alert(checkboxs.length);
//				alert(data[i]);
				for(var j = 0; j < checkboxs.length;j++){
					 if(checkboxs[j].value==data[i])
				     {
						 checkboxs[j].checked;
				     }

				}
				}
		}
	};	
	ajax_(ajax_param);	
}

function edit(){
	var val=$('input:radio[name="version"]:checked').val();
	if(val==null){
		$.messager.alert("错误", "什么也没选中!", "error");
		return false;
	}
	else{
		alert(val);
	}

}

function sel(moid){
	var index =$('#index').val();
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_deviceId"); 
	     fWindowText1.value = moid; 
	     if(index==0||index==1){
	    	 window.opener.findDeviceInfo(index);
	     }else{
	    	 window.opener.findDeviceInfo();
	     }
	     window.close();
	} 
}  

//查看设备视图
function toShowView(moid){
	var path = getRootPatch();
	var uri = path + "/monitor/discover/selectMoClass";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moid" : moid,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			viewDevicePortal(moid,data);
		}
	}
	ajax_(ajax_param);
	
}

function viewDevicePortal(moid,moClassName){
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : moClassName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var moClass="host";
				if(moClassName=="VHost"){
					moClass="vhost";
				}
				if(moClassName=="VM"){
					moClass="vm";
				}
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
//				var iWidth=1300; //弹出窗口的宽度;
//				var iHeight=700; //弹出窗口的高度;
//				var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
//				var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
//				window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
				window.location = uri;
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}

/**
 * 多选
 * @return
 */
function chooseMore(){
	var sourceMoIds = null;
	var sourceNames = null;
	var deviceips = null;
	var rows  = $('#tblDeviceMore').datagrid('getChecked');
	var flag = null;
	for(var i=0; i<rows.length; i++){
		if (null == sourceMoIds) {
			sourceMoIds = rows[i].moid;
			sourceNames = rows[i].moname;
			deviceips = rows[i].deviceip;
		} else {
			sourceMoIds += ',' + rows[i].moid;
			sourceNames += ',' + rows[i].moname;
			deviceips += ',' + rows[i].deviceip;
		}
    }
	
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("moIds"); 
	     fWindowText2 = window.opener.document.getElementById("deviceips");
	     fWindowText1.value = sourceMoIds;
	     fWindowText2.value = deviceips;
	     window.opener.showDeviceInfos();
	     window.close();
	} 
}