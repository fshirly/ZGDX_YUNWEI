
//listview插件提供的全局方法变量
var listviewMethod = {};

//获取datagrid方法,参数为listview插件的唯一标识id，
//此id是为了如果有同一个页面需要用相同的listviewName的插件多次。
//如果id为空，则id=listviewName
listviewMethod["getDatagridId"] = function(id){
	return id + "_datagridId";
};

//获取查询的方法名
listviewMethod["getQueryMethod"] = function(id){
	return id + "_query_method";
};

//获取查询按钮的方法名
listviewMethod["getQueryMethodButtonTrigger"] = function(id){
	return id + "_query_method_button_trigger";
};

//刷新datagrid数据
listviewMethod["refresh"] = function(id , reset){
	try{
		var queryMethod = "";
		if(reset){
			queryMethod = listviewMethod["getQueryMethodButtonTrigger"](id);
		}else{
			queryMethod = listviewMethod["getQueryMethod"](id);
		}
		listviewMethod[queryMethod]();
	}catch(e){
		alert( "[id: " + id + " ]传入错误！");
	}
};

(function($) {
	
	$.fn.listview = function(options) {// options 经常用这个表示有许多个参数。
		var defaultVal = {
			id : '',//控件Id
			listviewName : '', //listview英文名
			//exportExcel : false, //是否可以导出excel
			initParams : {}, //默认查询条件
			//datagridId : '',//生成的datagridId
			preview : false,//是否预览
			previewData : {},//预览数据
			/**从这里开始所有的datagrid属性和方法*/
			//面板属性
			//id : null,
			title : null,
			iconCls : null,
			width : 'auto',
			height : 'auto',
			left : null,
			top : null,
			cls : null,
			headerCls : null,
			bodyCls : null,
			style : {},
			fit : false,
			border : true,
			doSize : true,
			noheader : false,
			content : null,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false,
			tools : [],
			collapsed : false,
			minimized : false,
			maximized : false,
			closed : false,
			href : null,
			cache : true,
			//loadingMessage : "Loading…",
			extractor :  function(){},
			//method : 'get',
			//queryParams : {},
			//loader : function(){},
			//datagrid属性
			columns : undefined,
			frozenColumns : undefined,
			fitColumns : false,
			resizeHandle : 'right',
			autoRowHeight : true,
			toolbar : undefined,
			striped : false,
			//method : 'post',
			nowrap : true,
			idField : null,
			//url : '',
			//data : null,
			loadMsg : 'Processing, please wait …',
			//pagination : false,
			rownumbers : false,
			singleSelect : false,
			ctrlSelect : false,
			checkOnSelect : true,
			selectOnCheck : true,
			pagePosition : 'bottom',
			//pageNumber : 1,
			//pageSize : 10,
			//pageList : [10,20,30,40,50],
			sortName : null,
			sortOrder : 'asc',
			multiSort : false,
			remoteSort : true,
			showHeader : true,
			showFooter : false,
			scrollbarSize : 18,
			rowStyler : function(){},
			//loadFilter : function(){}//,
			//editors : {},
			//view : {},
			callBack : function(){}
		};
		var obj = $.extend(defaultVal, options);
		
		//预览时，随机生成listvieName，不去后台查询listview配置
		if(obj.preview){
			obj.listviewName = 'listview_' + Math.random().toString(36).substr(2);
		}
		
		//控件Id
		if(obj.id == ''){
			obj.id = obj.listviewName;
		}
		
		//预览时，通过previewData查询listview配置信息，否则通过listviewName
		var listviewConfig = null;
		if(obj.preview){
			listviewConfig = getConfigByPreviewData(obj.previewData);
		}else{
			listviewConfig = getConfigByListviewName(obj.listviewName ,  obj.initParams);
		}
		
		//是否分页
		var pageable = true;
		if(listviewConfig.pageSize == 0){
			pageable = false;
		}
		
		// id
		var formId = obj.id + "_formId";
		var conditionId = obj.id + "_conditionId";
		//var tableId = obj.id + "_tableId";
		var datagridId = obj.id + '_datagridId';
//		if(obj.datagridId != ''){
//			datagridId = obj.datagridId;
//		}
		
		// 方法
		var queryMethod = obj.id + "_query_method";
		var queryButtonId = queryMethod + "Id";
		var queryMethodButtonTrigger = obj.id + "_query_method_button_trigger";
		
		var resetButtonId = obj.id + "_resetButtonId";
		
		listviewMethod[queryMethodButtonTrigger] = function(){
			if(pageable){
				$($('#'+datagridId).datagrid('getPager')).pagination('refresh', {
					pageNumber : 1
				});
			}
			listviewMethod[queryMethod]();
		};
		
		//预览时,分析下是否有带冒号的可变参数,如(:name),需要传值进去才能进行查询
		var previewColonParameterArray = [];
		if(obj.preview){
			var url = rootPath + "tag/listviewQuery/previewAnalysisSql";
			$.ajax({
				url : url,
				dataType : "json",
				contentType : "application/json",
				type : "post",
				data : JSON.stringify(obj.previewData),
				async : false,
				success : function(result) {
					//alert(JSON.stringify(result.data));
					previewColonParameterArray = result.data;
				},
				error : function(result){
					$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
				}
			});
		}
		
		//预览时，查询通过previewData，否则通过listviewName
		if(obj.preview){
			listviewMethod[queryMethod] = function(){
				
				var pageSize = 0;
				var pageNum = 1;
				
				if(pageable){
					var pager = $('#'+datagridId+'').datagrid('getPager');
					pagerOpts = $(pager).pagination('options');
					pageSize = pagerOpts.pageSize;
					pageNum = pagerOpts.pageNumber;
				}
				
				pageNum = pageNum == 0 ? 1 : pageNum;
				$('#'+datagridId+'').datagrid('options').pageNumber = pageNum;
				$('#'+datagridId+'').datagrid('options').pageSize = pageSize;
				
				var formJson = formTojsonNotToLowerCase(formId);
				var params = {
				  "condition": formJson,
				  "pageSize": pageSize,
				  "pageNum": pageNum,
				  "previewData": obj.previewData
				};
				
				var url = rootPath + "tag/listviewQuery/previewForPage";
				
				$.ajax({
					url : url,
					dataType : "json",
					contentType : "application/json",
					type : "post",
					data : JSON.stringify(params),
					//async : false,
					success : function(result) {
						var easyUiJson = {
											"total" : result.total,
											"rows" : result.data
										 };
						$('#'+datagridId).datagrid('loadData', easyUiJson);
						
						$($('#'+datagridId).datagrid('getPager')).pagination('refresh', {
							pageNumber : pageNum,
							pageSize : pageSize
						});
					},
					error : function(result){
						$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
					}
				});
				
				obj.callBack();
				
			};
		}else{
			listviewMethod[queryMethod] = function(){
				
				var pageSize = 0;
				var pageNum = 1;
				
				if(pageable){
					var pager = $('#'+datagridId+'').datagrid('getPager');
					pagerOpts = $(pager).pagination('options');
					pageSize = pagerOpts.pageSize;
					pageNum = pagerOpts.pageNumber;
				}

				pageNum = pageNum == 0 ? 1 : pageNum;
				$('#'+datagridId+'').datagrid('options').pageNumber = pageNum;
				$('#'+datagridId+'').datagrid('options').pageSize = pageSize;
				
				var formJson = formTojsonNotToLowerCase(formId);
				var params = {
				  "condition": formJson,
				  "pageSize": pageSize,
				  "pageNum": pageNum,
				  "listviewName": obj.listviewName
				};
				
				var url = rootPath + "tag/listviewQuery/datasForPage";
				
				$.ajax({
					url : url,
					dataType : "json",
					contentType : "application/json",
					type : "post",
					data : JSON.stringify(params),
					//async : false,
					success : function(result) {
						var easyUiJson = {
											"total" : result.total,
											"rows" : result.data
										 };
						$('#'+datagridId).datagrid('loadData', easyUiJson);
						
						$($('#'+datagridId).datagrid('getPager')).pagination('refresh', {
							pageNumber : pageNum,
							pageSize : pageSize
						});
					},
					error : function(result){
						$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
					}
				});
				
				obj.callBack();
				
			};
		}
		
		//method[queryMethod]();
		
		//初始化html
		var formHtml = '<form id="'+formId+'" ></form>';
		var conditionsHtml = '<div id="'+conditionId+'" class="conditions listviewConditions"></div>';
		var buttonHtml = '<div align="center" >'
								+' <a href="javascript:void(0)" class="btn"  id="'+queryButtonId+'"  ' 
								+' >查询</a>'
								+' <a href="javascript:void(0)" class="btn"  id="'+resetButtonId+'" '
								+' onclick=\'$("#'+formId+'").form("reset"); \'>重置</a>'
						+'</div>';
		var tableHtml = '<div class="toptreesearch"><table id="'+datagridId+'"></table></div>';
		this.append(formHtml).append(tableHtml);
		$("#"+formId).append(conditionsHtml);
		
		//条件
		var conditionIndex = 1;
		var condition = listviewConfig.condition;
		var htmlFragment = "";
		$.each(condition,function(i,el){
			var controlType = el.controlType;
			var propertyName = el.propertyName;
			var displayTitle = el.displayTitle;
			var values = el.values == null ? [] : el.values;
			var value = el.value == null ? '' : el.value;
			var defaultValue = el.defaultValue == null ? '' : el.defaultValue;
			var url = el.url == null ? '' : el.url;
			var type = el.type == null ? '' : el.type;
			var childId = el.childId == null ? '' : el.childId;
			var multiple = el.multiple;
			
			if (conditionIndex % listviewConfig.colCount == 1) {
				htmlFragment += "<div>";
			}
			
			if (controlType == 'select') {
				if(isTree(values)){
					htmlFragment += '<b class="title">' + displayTitle + '：</b>'
			 		 +'<input id="'+propertyName+'" name="'+propertyName+'" class="easyui-combotree" style="width:15%"  value="'+defaultValue+'" />';
				}else{
					htmlFragment += '<b class="title">' + displayTitle + '：</b>'
			 		 +'<input id="'+propertyName+'" name="'+propertyName+'" class="easyui-combobox"  style="width:15%" childId="'+childId+'" value="'+defaultValue+'" />';
				}
				
			}else if (controlType == 'text') {
				htmlFragment += '<b class="title">' + displayTitle + '：</b>'
							   +'<input  id="'+propertyName+'" name="'+propertyName+'"  type="text" value="'+defaultValue+'"  />';
				
			} else if (controlType == 'hidden') {
				htmlFragment += '<b style="display:none" class="title">' + displayTitle + '：</b>'
   							 +'<input id="'+propertyName+'" name="'+propertyName+'" type="hidden"  value="'+defaultValue+'" />';
				
			} else if (controlType == 'date') {
				htmlFragment += '<b class="title">' + displayTitle + '：</b>'
				   				+'<input id="'+propertyName+'" name="'+propertyName+'" class="easyui-datebox"  data-options="panelWidth:\'15%\'" style="width:15%;"  value="'+defaultValue+'" />';
				
			} else if (controlType == 'datetime') {
				htmlFragment += '<b class="title">' + displayTitle + '：</b>'
   							 +'<input id="'+propertyName+'" name="'+propertyName+'" class="easyui-datetimebox"  data-options="panelWidth:\'15%\'" style="width:15%;" value="'+defaultValue+'" />';
			} else if (controlType == 'tree'){
				htmlFragment += '<b class="title">' + displayTitle + '：</b>'
							 +'<span style="margin-top:-5px;" id="'+propertyName+'_orgTree'+'"></span>';
			}
			
			if (controlType == 'hidden') {
				conditionIndex--;
			}
			if (conditionIndex % listviewConfig.colCount == 0) {
				htmlFragment += "</div>";
			}
			conditionIndex++;
		});
		
		$("#"+conditionId).append(htmlFragment).append(buttonHtml);
		
		$.parser.parse(); 
		
		//调整conditions高度
		hei=parseInt($(".conditions").css("height"))+11;
		$(".toptreesearch").css("top",hei);
		
		//循环处理数据
		conditionIndex = 1;
		$.each(condition,function(i,el){
			var controlType = el.controlType;
			var propertyName = el.propertyName;
			var displayTitle = el.displayTitle;
			var values = el.values == null ? [] : el.values;
			var value = el.value == null ? '' : el.value;
			var defaultValue = el.defaultValue == null ? '' : el.defaultValue;
			var url = el.url == null ? '' : el.url;
			var type = el.type == null ? '' : el.type;
			var childId = el.childId == null ? '' : el.childId;
			var multiple = el.multiple;
			var mul = multiple == "true" ? true : false;
			var opts = el.opts;
			
			if (controlType == 'select') {
				
				//如果配置的是url，则先查询出values
				if (url != '' && values.length == 0) {
					var method = el.method;
					var params = el.params;
					$.ajax({
							url : rootPath + url,
							dataType : "json",
							contentType : "application/json",
							type : method,
							data : JSON.stringify(params),
							async : false,
							success : function(res) {
								//console.log(JSON.stringify(res.data))
								values = res.data;
							},
							error:function(result){
								$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
							}
					});
				}
				
				//下拉框展示用的数据
				var datas = [];
				//多选不增加请选择...
				if(!mul){
					datas.push({"code":"", "name":"请选择..."});
				}
				if(values != undefined && values != null && values.length >0){
					datas = datas.concat(values);
				}
				
				//判断是否有子节点
				if(childId == ""){
					
					if(isTree(values)){
						
						//combotree默认扩展属性
//						var defaultOpts = {
//							cascadeCheck : true,
//							onlyLeafCheck : false
//						};
						//combotree自定义扩展属性
						var mixOpts = $.extend({}, opts);
						
						var fValues = formatJson(values , {"id":"cid" , "name" : "text" , "code" : "id"});
						var tValues = transData(fValues, "cid", "pid", "children");
						$("#"+propertyName).combotree({
							data : tValues,
							//onlyLeafCheck : true,
							multiple:mul,
//							onBeforeSelect: function(node){
//								if(!$("#"+propertyName).tree('isLeaf',node.target)){
//									//$("#"+propertyName).tree('collapse',node.target);
//									return false;
//								}
//							}
						});
						$("#"+propertyName).combotree(mixOpts);
						
					}else{//下拉框
						//alert(propertyName + "=" + mul)
						$("#"+propertyName).combobox({
							//data:datas ,
							valueField:"code",
							textField:"name",
							multiple:mul
						});
						//values有值就加载，没有就取出来再加载，这是为了解决默认不选中的bug
						if(values != undefined && values != null && values.length >0){
							$("#"+propertyName).combobox('loadData', datas);
						}else{
							var getData = $("#"+propertyName).combobox('getData');
							if(getData == null || getData.length == 0){
								$("#"+propertyName).combobox('loadData', datas);
							}else{
								$("#"+propertyName).combobox('loadData', getData);
							}
						}
						if(mul){
							//$("#"+propertyName).combobox('setValues',['']);
						}
					}

				}else{
					//先不加载，有可能是父节点初始化的时候加载过了
					$("#"+propertyName).combobox({
						//data:datas ,
						valueField:"code",
						textField:"name",
						onChange : function(newValue, oldValue) {
							var url = rootPath + "tag/dictionaryQuery/dictionarys";
							var params = {type : type , pid : '' , code : newValue , listviewName : obj.listviewName };
							$.ajax({
								url : url,
								dataType : "json",
								contentType : "application/json",
								type : "post",
								data : JSON.stringify(params),
								async : false,
								success : function(res) {
									 var children = [];
									 children.push({"code":"", "name":"请选择..."});
									 if(res.data != undefined && res.data != null && res.data.length >0){
										 children = children.concat(res.data);
									 }
									 $("#"+childId).combobox('loadData', children).combobox('setValue', '');
								},
								error : function(result){
									$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
								}
							});
						}
					});
					//values有值就加载，没有就取出来再加载，这是为了解决默认不选中的bug
					if(values != undefined && values != null && values.length >0){
						$("#"+propertyName).combobox('loadData', datas);
					}else{
						var getData = $("#"+propertyName).combobox('getData');
						if(getData == null || getData.length == 0){
							$("#"+propertyName).combobox('loadData', datas);
						}else{
							$("#"+propertyName).combobox('loadData', getData);
						}
					}
					//如果有子节点，先定义combobox
					$("#"+childId).combobox({
						//data:datas ,
						valueField:"code",
						textField:"name"
					});
					//父节点有默认值的话，加载子节点数据，当循环到子节点时就不需要加载了，否则会清空
					if(defaultValue != ""){
						var url = rootPath + "tag/dictionaryQuery/dictionarys";
						var params = {type : type , pid : '' , code : defaultValue , listviewName : obj.listviewName };
						$.ajax({
							url : url,
							dataType : "json",
							contentType : "application/json",
							type : "post",
							data : JSON.stringify(params),
							async : false,
							success : function(res) {
								 var children = [];
								 children.push({"code":"", "name":"请选择..."});
								 if(res.data != undefined && res.data != null && res.data.length >0){
									 children = children.concat(res.data);
									}
								 $("#"+childId).combobox('loadData', children);
							},
							error : function(result){
								$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
							}
						});
					}
				}
			}else if (controlType == 'text') {
			} else if (controlType == 'hidden') {
			} else if (controlType == 'date') {
			} else if (controlType == 'datetime') {
			}else if(controlType == 'tree'){
				//console.log(opts);
				//console.log(JSON.stringify(opts));
				//console.log(opts.width);
				$("#"+propertyName+'_orgTree').systemTree({
					dialogOpts : opts.dialogOpts,
					treeOpts: opts.treeOpts
				});
				$("#"+propertyName+'_orgTree').children(":first").css("width",'15%');
			}
			
			conditionIndex++;
		});
		
		$("#"+queryButtonId).click(listviewMethod[queryMethodButtonTrigger]);
		
		
		//合并frozenColumns
		var configColumns = formatJson(listviewConfig.cols , {"id":"field","name":"title"});
		var frozenColumns = extendFrozenColumns(obj.frozenColumns,configColumns,"field");
		
		//合并columns,如果在columns中没有配置，且在frozenColimns中配置了，则不显示，否则显示默认。
		var columns = extendColumns(obj.frozenColumns,obj.columns,configColumns,"field"); 
		
		//格式化数据列
//		var cols = listviewConfig.cols;
//		var columns1 = formatJson(cols,{"id":"field","name":"title"});//后台listview配置列(一维数组)
//		var columns11 = new Array();
//		columns11.push(columns1);
//		
//		var columns2 = obj.columns;//前台自定义columns
//		var columns = new Array();
//		if(columns2 == undefined){
//			columns = columns11;
//		}else{
//			columns = extendColumns(columns11,columns2,"field");//根据field合并
//		}
		
		//在toolbar中增加导出excel按钮
		if(listviewConfig.isExport == "1"){
			var downloadIframeHtml = '<iframe id="__listviewDownload__" src="" width="0" height="0" style="width: 0px; height: 0px; display: none"></iframe>';
			this.append(downloadIframeHtml);
			var excelButton = {
					text:"导出excel",
					iconCls: 'icon-export',
					handler: function(){
						var url = rootPath + "tag/listviewQuery/excel";
						var formJson = formTojsonNotToLowerCase(formId);
						var frozenColumns = $('#'+datagridId).datagrid('options').frozenColumns;
						var columns = $('#'+datagridId).datagrid('options').columns;
						var cols = [];
						if(frozenColumns){
							var frozenCols = frozenColumns[0];
							if(frozenCols){
								for (var i = 0; i < frozenCols.length; i++) {
									if ((frozenCols[i]['title']) && (frozenCols[i]['field'])) {
										if ((frozenCols[i]['checkbox']) || (frozenCols[i]['hidden'])) {
										} else {
											cols.push({"id":frozenCols[i]['field'] , "name" : frozenCols[i]['title']});
										}
									}
								}
							}
						}
						if (columns) {
							var colsm = columns[0];
							if (colsm) {
								for (var i = 0; i < colsm.length; i++) {
									if ((colsm[i]['title']) && (colsm[i]['field'])) {
										if ((colsm[i]['checkbox']) || (colsm[i]['hidden'])) {
										} else {
											cols.push({"id":colsm[i]['field'] , "name" : colsm[i]['title']});
										}
									}
								}
							}
						}
						var params = {
						  "condition": formJson,
						  "cols" :cols,
						  "listviewName": obj.listviewName
						};
						$.ajax({
							url : url,
							dataType : "json",
							contentType : "application/json",
							type : "post",
							data : JSON.stringify(params),
							success : function(res) {
								var filePath = encodeURIComponent(encodeURIComponent(res.filePath));
								$("#__listviewDownload__").attr("src",rootPath + "tag/listviewQuery/exportExcel/" + filePath);
								//document.getElementById("download").src = rootPath + "tag/listviewQuery/exportExcel/" + filePath;
							},
							error : function(result){
								$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
							}
						});
					}
				};
			 if(obj.toolbar == undefined){
				obj.toolbar = [];
			 }
			 obj.toolbar.push(excelButton);
		}
		
		//计算height
//		if(obj.height == 'auto' ){
//			obj.height = $("body").height() - $("#"+formId).height();
//		}
		
		$('#'+datagridId).datagrid({
			toolbar : obj.toolbar,
			columns : columns,
		   	pagination:pageable,
		   	width : obj.width,
		   	height : obj.height,
		    //面板属性
			//id : obj.id,
			title : obj.title,
			iconCls : obj.iconCls,
			left : obj.left,
			top : obj.top,
			cls : obj.cls,
			headerCls : obj.headerCls,
			bodyCls : obj.bodyCls,
			style : obj.style,
			fit : obj.fit,
			border : obj.border,
			doSize : obj.doSize,
			noheader : obj.noheader,
			content : obj.content,
			collapsible : obj.collapsible,
			minimizable : obj.minimizable,
			maximizable : obj.maximizable,
			closable : obj.closable,
			collapsed : obj.collapsed,
			minimized : obj.minimized,
			maximized : obj.maximized,
			closed : obj.closed,
			href : obj.href,
			cache : obj.cache,
			//loadingMessage : obj.loadingMessage,
			extractor : obj.extractor,
			//method : 'get',
//			queryParams : obj.queryParams,
			//loader : obj.loader,
			//datagrid属性
			frozenColumns : frozenColumns,
			fitColumns : obj.fitColumns,
			resizeHandle : obj.resizeHandle,
			autoRowHeight : obj.autoRowHeight,
			striped : obj.striped,
//			method : obj.method,
			nowrap : obj.nowrap,
			idField : obj.idField,
			//url : '',
			//data : [],
			loadMsg : obj.loadMsg,
			//pagination : false,
			rownumbers : obj.rownumbers,
			singleSelect : obj.singleSelect,
			ctrlSelect : obj.ctrlSelect,
			checkOnSelect : obj.checkOnSelect,
			selectOnCheck : obj.selectOnCheck,
			pagePosition : obj.pagePosition,
			//pageNumber : 1,
			//pageSize : 10,
			//pageList : [10,20,30,40,50],
			sortName : obj.sortName,
			sortOrder : obj.sortOrder,
			multiSort : obj.multiSort,
			remoteSort : obj.remoteSort,
			showHeader : obj.showHeader,
			showFooter : obj.showFooter,
			scrollbarSize : obj.scrollbarSize,
			rowStyler : obj.rowStyler,
			//pageNumber : 1,
			//pageSize : listviewConfig.pageSize,//每页显示的记录条数，默认为10  
			//pageList : JSON.parse("["+listviewConfig.pageSizeList+"]"),//可以设置每页记录条数的列表  

			//loadFilter : obj.loadFilter//,
			loadFilter: function (data) {//是否会影响性能?
				for (var i = 0; i < data.rows.length; i++) {
					for (var att in data.rows[i]) {
						if (typeof (data.rows[i][att]) == "string") {
							data.rows[i][att] = data.rows[i][att].replace(/</g, "&lt;").replace(/>/g, "&gt;");
						}
					}
				}
				return data;
			},
			//editors : {},
			//view : {}
			onLoadSuccess : function(){
				$(this).datagrid("fixRownumber");
			}
		}); 
		
		var p = $('#'+datagridId+'').datagrid('getPager');
		$(p).pagination({
			//pageNumber : 1,
			pageSize : listviewConfig.pageSize,//每页显示的记录条数，默认为10  
			pageList : JSON.parse("["+(listviewConfig.pageSizeList || "10,20,50,100" )+"]"),//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			},
			onChangePageSize : function(pageSize){
				listviewMethod[queryMethodButtonTrigger]();
			},
			onSelectPage : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				//query(pageNumber, pageSize);
				//alert(pageSize);
				listviewMethod[queryMethod]();
				$(this).pagination('loaded');
			}
		});
		
		//预览时，如果有可变参数，则弹出填写可变参数的窗口，确定之后调用查询方法
		if(obj.preview){
			if(previewColonParameterArray.length > 0){
				var content = "<p>请填写以下可变参数:</p>";
				$.each(previewColonParameterArray,function(i,el){
					content += '<span style="min-width:82px;">' + el + '：</span>'
					   		+'<input id="colon_'+el+'" name="colon_'+el+'" type="text" /></br>';
				});
				$('#colon').dialog({    
				    title: '可变参数窗口',    
				    width: 400,    
				    height: 200,    
				    closed: false,    
				    cache: false,    
				    //href: 'get_content.php',  
				    content : content,
				    modal: true,
				    buttons : [{
						text:'确定',
						iconCls:'icon-edit',
						handler:function(){ 
							var hiddenStr = "";
							$.each(previewColonParameterArray,function(i,el){
								var elVal = $("#colon_" + el).val();
								hiddenStr += '<input id="'+el+'" name="'+el+'" type="hidden" value="'+elVal+'" /></br>';
							});
							$("#"+conditionId).append(hiddenStr);
							
							$('#colon').window('close'); 
							if(listviewConfig.isAutoBind == "1"){//默认加载数据
								listviewMethod[queryMethodButtonTrigger]();
							}
						}
					}]
				});   
			}else{
				if(listviewConfig.isAutoBind == "1"){//默认加载数据
					listviewMethod[queryMethodButtonTrigger]();
				}
			}
		}else{
			if(listviewConfig.isAutoBind == "1"){//默认加载数据
				listviewMethod[queryMethodButtonTrigger]();
			}
		}
		return this;
	};
})(jQuery);

//根据填写的listview预览
function getConfigByPreviewData(previewData){
	var url = rootPath + "/tag/listviewQuery/previewConfig";
	var config = {};
	$.ajax({
		url : url,
		dataType : "json",
		contentType : "application/json",
		type : "post",
		data : JSON.stringify(previewData),
		async : false,
		success : function(res) {
			 config = res;
		},
		error : function(result){
			$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
		}
	});
	return config;
}


//根据listviewName查询listview的配置信息(包括basic、condition、cols)
function getConfigByListviewName(listviewName,initParams){
	var url = rootPath + "tag/listviewQuery/listviewConfig";
	var data = {"listviewName" : listviewName , "initParams" : initParams};
	
	var config = {};
	
	$.ajax({
		url : url,
		dataType : "json",
		contentType : "application/json",
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		success : function(res) {
			 config = res;
		},
		error : function(result){
			$.messager.alert("错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message);
		}
	});
	return config;
}

//将formObj对象转成json对象
//function formTojson(formObj) {
//	var paramObject = new Object();
//	if (formObj) {
//		var elementsObj = formObj.elements;
//		var obj;
//		if (elementsObj) {
//			// 将表单中所有参数转换为json对象
//			// [javascript]
//			for (var i = 0; i < elementsObj.length; i += 1) {
//				obj = elementsObj[i];
//				if (obj.name != undefined && obj.name != "") {
//					if(paramObject[obj.name] != undefined){//多选以，分割
//						paramObject[obj.name] = paramObject[obj.name] + "," + obj.value;
//					}else{
//						paramObject[obj.name] = obj.value;
//					}
//				}
//			}
//			//console.log(JSON.stringify(paramObject));
//			return paramObject;
//			// submitParams(paramObject);
//		} else {
//			alert("没有elements对象!");
//			return;
//		}
//	} else {
//		alert("form不存在!");
//		return;
//	}
//}

//判断是否层级数据
function isTree(rows){
	if(rows == undefined || rows == null || rows.length == 0){
		return false;
	}
	var tmpPid = null;
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		pid = row.pid;
		if(tmpPid == null){
			tmpPid = pid;
		}
		if(tmpPid != pid){
			return true;
		}
	}
	return false;
}


//格式化json数据，源数据:rows， format比如：{"id":"key","text":"label"},表示将id->key,text->label
function formatJson(rows , format){
	var nodes = [];
    for(var i = 0; i < rows.length; i++) {
    	var row = rows[i];
    	var data = row;
    	for(var key in format){  
            //console.log("key:"+ key + ", value:" + format[key]);
            data[format[key]] = row[key];
        } 
    	nodes.push(data);
    }
    return nodes;
}

//合并冻结列
function extendFrozenColumns(frozenColumns , configColumns , key){
	var result = new Array();
	var results = new Array();
	if(frozenColumns == undefined){
		return results;
	}
	var frozenCols = frozenColumns[0];
	if(frozenCols == undefined){
		return results;
	}
	for(var i=0;i<frozenCols.length;i++){
		var frozenCol = frozenCols[i];
		var frozenColPk = frozenCol[key];
		if(frozenColPk != undefined && frozenColPk != null){
			for(var j=0;j<configColumns.length;j++){
				var configColumn = configColumns[j];
				var configColumnPk = configColumn[key];
				if(frozenColPk == configColumnPk){
					frozenCol = $.extend({},configColumn,frozenCol);
					break;
				}
			}
		}
		result.push(frozenCol);
	}
	results.push(result);
	return results;
	
}


//合并columns,如果在columns中没有配置，且在frozenColimns中配置了，则不显示，否则显示默认。 (反掉了，默认不输出，所以注掉此处部分代码)
function extendColumns(frozenColumns , columns , configColumns , key){
	var result = new Array();
	var results = new Array();
	if(columns == undefined && frozenColumns == undefined){
		results.push(configColumns);
		return results;
	}
	var cols = columns[0];
	//var frozenCols = frozenColumns[0];
//	if(cols == undefined && frozenCols == undefined){
//		results.push(configColumns);
//		return results;
//	}
	//先将自定义的列输出
	for(var i=0;i<cols.length;i++){
		var col = cols[i];
		var colPk = col[key];
		for(var j=0;j<configColumns.length;j++){
			var configColumn = configColumns[j];
			var configColumnPk = configColumn[key];
			if(colPk == configColumnPk){
				col = $.extend({},configColumn,col);
				break;
			}
		}
		result.push(col);
	}
	
	//如果在frozenColumns、columns在都没有配置的，默认输出 (反掉了，默认不输出，所以注掉此处代码)
//	var frozenHash = {};
//	if(frozenColumns!=undefined){
//		var frozenCols = frozenColumns[0];
//		for(var i=0; i<frozenCols.length; i++) {
//			if(frozenCols[i][key] != undefined){
//				frozenHash[frozenCols[i][key]] = frozenCols[i];
//			}
//		}
//	}
//	var hash = {};
//	for(var i=0; i<cols.length; i++) {
//		if(cols[i][key] != undefined){
//			hash[cols[i][key]] = cols[i];
//		}
//	}
//	for(var k=0;k<configColumns.length;k++){
//		var configColumn = configColumns[k];
//		var configColumnPk = configColumn[key];
//		if((frozenHash[configColumnPk]==undefined || frozenHash[configColumnPk]==null) 
//				&& (hash[configColumnPk]==undefined || hash[configColumnPk]==null)){
//			result.push(configColumn);
//		}
//	}
	results.push(result);
	return results;
}

//function isExistsInArray(array,obj,key){
//	if(array == undefined){
//		return false;
//	}
//	var hash = {};
//	for (var i=0; i<array.length; i++) {
//        hash[array[i][key]] = array[i];
//    }
//	if(hash[obj[key]] == undefined || hash[obj[key]] == null){
//		return false;
//	}
//	return true;
//}


////合并array, target为主array ，another为辅助array，key为合并关键字
//function extendArray(targets,anothers,key){
//	
//	var target = targets[0];
//	var another= anothers[0];
//	var result = [];
//	//不存在的先放进去
//	for(var ii = 0; ii<another.length; ii++){
//		var tmpAnotherData = another[ii];
//		var tmpAnotherValue = tmpAnotherData[key];
//		var have = false;
//		for(var jj = 0; jj<target.length; jj++){
//			var tmpTargetData = target[jj];
//			var tmpTargetValue = tmpTargetData[key];
//			if(tmpAnotherValue == tmpTargetValue){
//				have = true;
//				break;
//			}
//		}
//		if(!have){
//			result.push(tmpAnotherData);
//		}
//	}
//	
//	//合并数组中对应的的元素
//	for(var i = 0; i < target.length; i++) {
//		var data = target[i];
//		var value = data[key];
//		if(another != undefined){
//			for(var j=0; j<another.length; j++){
//				var mixData = another[j];
//				var mixValue = mixData[key];
//				if(value == mixValue){
//					$.extend(data,mixData);
//					//have = true;
//					break;
//				}
//			}
//		}
//		result.push(data);
//	}
//	var results = new Array();
//	results.push(result);
//	return results;
//}

/**
 * json格式转树状结构
 * @param   {json}      json数据
 * @param   {String}    id的字符串
 * @param   {String}    父id的字符串
 * @param   {String}    children的字符串
 * @return  {Array}     数组
 */
function transData(a, idStr, pidStr, chindrenStr) {
    var r = [], hash = {}, id = idStr, pid = pidStr, children = chindrenStr, i = 0, j = 0, len = a.length;
    for (; i < len; i++) {
        hash[a[i][id]] = a[i];
    }
    for (; j < len; j++) {
        var aVal = a[j], hashVP = hash[aVal[pid]];
        if (hashVP) {
            !hashVP[children] && (hashVP[children] = []);
            hashVP[children].push(aVal);
        } else {
            r.push(aVal);
        }
    }
    return r;
}


//刷新父窗口数据 
function refreshListview(listviewName , reset){
	parent.listviewMethod["refresh"](listviewName,reset);
}

/**
 * 解决Easyui Datagrid行号四位、五位显示不完全的扩展方法
 * 在onLoadSuccess调用,例如
 * $("#grid").datagrid({
 *   ...
 *   onLoadSuccess: function() {
 *     $(this).datagrid("fixRownumber");
 *   }
 * });
 */
$.extend($.fn.datagrid.methods, {
  fixRownumber: function(jq) {
    return jq.each(function() {
      var panel = $(this).datagrid("getPanel");
      var clone = $(".datagrid-cell-rownumber", panel).last().clone();
      clone.css({
        "position": "absolute",
        left: -1000
      }).appendTo("body");
      
      var width = clone.width("auto").width();
      //默认宽度是25,所以只有大于25的时候才进行fix
      if(width > 25) {
        //多加几个像素,保持一点边距
        $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 10);
        
        //修改了宽度之后,需要对容器进行重新计算
        $(this).datagrid("resize");
        
        clone.remove();
        clone = null;
      } else {
        //还原成默认状态
        $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
      }
    });
  }
});

