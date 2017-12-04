var path = getRootName();
var uri = "";
$(document).ready(function() {
	var siteType = $("#siteType").val();
	var moID = $("#moID").val();
	if (siteType == "1") {
		//FTP
		uri = "/monitor/webSiteWidget/getSiteFtpInfo?moID="+moID;
	}
	if (siteType == "2") {
		//DNS
		uri = "/monitor/webSiteWidget/getSiteDnsInfo?moID="+moID;
	}
	if (siteType == "3") {
		//HTTP
		uri = "/monitor/webSiteWidget/getSiteHttpInfo?moID="+moID;
	}
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	$('#tblWebSiteInfo')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + uri,
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						rownumbers : true,// 行号
						columns : [ [
						        {
									field : 'available',
									title : '可用/持续时间',
									width : 60,
									align : 'center',
									formatter:function(value,row){
										return '<img title="' + row.availableTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.availablePng + '"/>'+row.durationTime;
								} 
								},
								{
									field : 'siteName',
									title : '站点名称',
									width : 80,
									align : 'center',
						            formatter: function(value, row, index){
									var to = "&quot;" + row.moID +
				                    "&quot;,&quot;" +
				                    value +
				                    "&quot;,&quot;" +
				                    row.siteType +
				                    "&quot;"
									if(value && value.length > 30){
						        		value2 = value.substring(0,30) + "...";
						        		return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>'; 
						        	}else{
						        		return  value ;
									}
					            }
								},
								{
									field : 'siteAddr',
									title : '站点地址',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 70){
						        		value2 = value.substring(0,70) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
						        	}else{
										return value;
									}
						        }
								}
								 ] ]
					});
    $(window).resize(function() {
        $('#tblWebSiteInfo').resizeDataGrid(0, 0, 0, 0);
    });
}


