$(function() {
	$("#timeBegin").datebox({
		formatter : function(date){
			return formatDate(date ,"yyyy-MM-dd");
		} ,
		editable :false
	});
	$("#timeEnd").datebox({
		formatter : function(date){
			return formatDate(date ,"yyyy-MM-dd");
		} ,
		editable :false
	});
	$("#timeBegin").datebox("setValue" ,formatDate(new Date() ,"yyyy-MM-dd"));
	$("#timeEnd").datebox("setValue" ,formatDate(new Date() ,"yyyy-MM-dd"));
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * 
 * @return
 */
function doInitTable() {
	var uri = getRootName() + "/monitor/alarmOriginalEvent/originalEventList";
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns : true,
						url : uri,
						remoteSort : true,
						idField : '',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						queryParams : {
							sourceMoName : $("#sourceMoName").val(),
							timeBegin : $("#timeBegin").datebox("getValue"),
							timeEnd : $("#timeEnd").datebox("getValue"),
							moName : $("#moName").val() ,
							ipAddr : $("#ipAddr").val()
						},
						onLoadSuccess: function(data){
				            mergeCells('tblDataList', 'sourceMOName,ipAddr,eventName');
				        },
						toolbar : [ 
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								toBathClear();
							}
						}],
						columns : [ [
								{
									field : 'eventId',
									checkbox : true
								},
								{
									field : 'sourceMOName',
									title : '事件设备',
									width : 100,
									align : 'center'
								},
								{
									field : 'ipAddr',
									title : '设备IP',
									width : 100,
									align : 'center' ,
									formatter : function(value ,row ,index){
										if(value.length > 20){
											var result = '';
											for(var i=0;i<value.length / 20;i++){
												var end = (i+1) * 20 > value.length ? value.length : (i+1) * 20;
												var br = end % 20 == 0 ? "<br>" : "";
												result += value.substring(i*20 ,end) + br;
											}
											return result;
										}
										return value;
									}
								},
								{
									field : 'moName',
									title : '设备对象',
									width : 100 ,
									align : 'center'
								},
								{
									field : 'eventName',
									title : '事件名称',
									width : 150,
									align : 'center'
								},
								{
									field : 'eventTime',
									title : '最近发生时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'times',
									title : '事件发生次数',
									width : 70,
									align : 'center'
								},
								{
									field : 'eventContent',
									title : '事件内容',
									width : 150,
									align : 'center'
								},
								{
									field : 'operate' ,
									title : '操作' ,
									width : 40 ,
									align : 'center' ,
									formatter : function(value ,row ,index){
										return '<a style="cursor: pointer;" onclick="javascript:toDelete(\''+row.eventOid+'\','+row.moId+','+row.sourceMOID+');">'+
											'<img src="/insightview/style/images/icon/icon_delete.png" title="删除"></a>'
									}
								}
						] ]
	});
	$(window).resize(function() {
		$('#tblDataList').resizeDataGrid(0, 0, 0, 0);
	});
}

function mergeCells(tableID, fldList){
    var fldArr = fldList.split(",");
    var dg = $('#' + tableID);
    var fldName;
    var RowCount = dg.datagrid("getRows").length;
    var span;
    var PerValue = "";
    var CurValue = "";
    var length = fldArr.length;
    
    fldName = fldArr[0];
    PerValue = "";
    span = 1;
    for (row = 0; row <= RowCount; row++) {
        if (row == RowCount) {
            CurValue = "";
        } else {
            CurValue = dg.datagrid("getRows")[row][fldName];
        }
        if (PerValue == CurValue) {
            span += 1;
        } else {
            //合并资源名称，获得合并的索引、合并的行数			
            var index = row - span;
            dg.datagrid('mergeCells', {
                index: index,
                field: fldName,
                rowspan: span,
                colspan: null
            });
            //合并其他药合并的列
            if(span > 1){
            	for (var j=1;j<length;j++) {
                    fldName2 = fldArr[j];
                    
                    if(fldName2 == 'ipAddr'){
                    	dg.datagrid('mergeCells', {
			                index: index,
			                field: fldName2,
			                rowspan: span,
			                colspan: null
			            });
			            continue;
                    }
                    
                    var pre = "";
                    var cur = "";
                    var s = 1;
                	for(var k=index;k<=row;k++){
                		if(k == row){
                			cur = "";
                			execIndex = 0;
                		}else{
                			cur = $.trim(dg.datagrid("getRows")[k][fldName2]);
                		}
                    	
                    	if(pre == cur){
                    		s ++;
                    	}
                    	else{
                			dg.datagrid('mergeCells', {
                                index: k-s,
                                field: fldName2,
                                rowspan: s,
                                colspan: null
                            });
                    		s = 1;
                    		pre = cur;
                    	}
                    }
                }
            }
            span = 1;
            PerValue = CurValue;
        }
    }
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	var timeBegin = $('#timeBegin').datetimebox('getValue');
	var timeEnd = $('#timeEnd').datetimebox('getValue');

	var start = new Date(timeBegin.replace(/\-/g, "/"));
	var end = new Date(timeEnd.replace(/\-/g, "/"));
	if(start && end){
		if (end < start) {
			$.messager.alert('提示', '事件结束时间不得早于开始时间', 'error');
			return ;
		}
	}

	$('#tblDataList').datagrid('options').queryParams = {
		sourceMoName : $("#sourceMoName").val(),
		timeBegin : $("#timeBegin").datebox("getValue"),
		timeEnd : $("#timeEnd").datebox("getValue"),
		moName : $("#moName").val() ,
		ipAddr : $("#ipAddr").val()
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}


/**
 * 打开批量告警清除页面
 * 
 * @return
 */
function toBathClear() {
	var ids = null;
	var moids = null;
	var sourceMoids = null;
	var preOid = "";
	var preSource = "";
	var preMoid = "";
	var rows = $('#tblDataList').datagrid('getChecked');
	for (var i = 0; i < rows.length; i++) {
		if (null == ids) {
			ids = "'"+rows[i].eventOid+"'";
			moids = rows[i].moId;
			sourceMoids = rows[i].sourceMOID;
			preOid = ids;
			preSource = sourceMoids;
			preMoid = moids;
			continue;
		} 
		if(preOid != rows[i].eventOid){
			ids += ",'" + rows[i].eventOid+"'";
			preOid = rows[i].eventOid;
		} 
		if(preMoid != rows[i].moId){
			moids += "," + rows[i].moId;
			preMoid = rows[i].moId;
		} 
		if(preSource != rows[i].sourceMOID){
			sourceMoids += "," + rows[i].sourceMOID;
			preSource = rows[i].sourceMOID;
		}
	}
	if(null == ids){
		$.messager.alert('提示', '您还没有选中要删除的事件，请选择', 'info');
		return ;
	}
	toDelete(ids ,moids ,sourceMoids);
}

function reset(){
	$("#sourceMoName").val("");
	$("#timeBegin").datebox("setValue" ,formatDate(new Date() ,"yyyy-MM-dd"));
	$("#timeEnd").datebox("setValue" ,formatDate(new Date() ,"yyyy-MM-dd"));
	$("#moName").val("");
	$("#ipAddr").val("");
}

function toDelete(eventOids ,moids ,sourceMoids){
	$.messager.confirm("提示" ,"您确定要执行删除操作吗？" ,function(sure){
		if(sure){
			if(eventOids.indexOf(",") < 0 && eventOids.indexOf("\'") < 0){
				eventOids = "'"+eventOids+"'";
			}
			$.ajax({
				url : getRootName() + "/monitor/alarmOriginalEvent/deleteOriginalEvent" ,
				type : "post" ,
				data : {	
					eventOids : eventOids ,
					moids : moids ,
					sourceMoids : sourceMoids
				},
				dataType : "json" ,
				success : function(data){
					if(data){
						if(data)
							$.messager.alert('提示', '事件删除成功', 'info');
						else
							$.messager.alert('提示', '事件删除失败', 'error');
							
						reloadTable();
					}
					else{
						$.messager.alert('提示', '事件删除失败', 'error');
					}
				}
			});
		}
	});
}
