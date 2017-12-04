$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	  doHostMemoryInitTables(liInfo);// 主机内存
});

// 主机内存
function doHostMemoryInitTables(liInfo) {
	var path = getRootName();
	$('#tblHostTopVirMemory')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 550,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/hostManage/topPerfVirMemoryInfo',
					 								
						idField : 'fldId',
						columns : [ [
								{
									field : 'moname',
									title : '设备名称',
									width : 120,
									align : 'center',
								},
								{
									field : 'deviceip',
									title : '设备IP',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(row.moClassId != 0){
						        			var to = "&quot;" + row.moid
											+ "&quot;,&quot;" + value
											+ "&quot;"
						        			return '<a style="cursor: pointer;" onclick="javascript:toShowView('
											+ to
											+ ');">'+value+'</a>'; 
						        		}else{
						        			return value;
						        		}
										
									}
								},
								{
									field : 'perfvalue',
									title : '使用率',
									width : 200,
									align : 'center',
									 formatter : function(value, row, index) {
									var color = (value <= 75) ? '#00ff00' : (value >= 90 ? '#ff0000' : '#FFFF33');
							          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
							         }
							        }
							        ] ],
							        onLoadSuccess:function(){
							 			$('.easyui-progressbar').progressbar().progressbar('setColor');
							 			//自适应部件大小
							 			window.parent.resizeWidgetByParams(liInfo);
							        }
							     });
								$(window).resize(function() {
								    $('#tblHostTopVirMemory').resizeDataGrid(0, 0, 0, 0);
								});
							 }


$.fn.progressbar.methods.setColor = function(jq, color){
	return jq.each(function(){
		var opts = $.data(this, 'progressbar').options;
		var color = color || opts.color || $(this).attr('color');
		$(this).css('border-color', color).find('div.progressbar-value .progressbar-text').css('background-color', color);
	});
}
