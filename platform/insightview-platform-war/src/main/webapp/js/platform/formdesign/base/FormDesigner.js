/**
* 表单设计处理类
* 负责表单设计器的初始化
* 该类为表单设计器的入口程序
* 郑自辉
*/
f.namespace('ISV.pf.fd.base');
ISV.pf.fd.base.FormDesigner = {
	/**
	* 保存当前操作的控件对象
	* 初始化为一空对象
	* 当对控件进行编辑时，需从控件工厂获取控件对象
	* 待控件对象初始化完成后赋值给该属性缓存
	*/
	opWidget : {},
	
	/**
	 * 简单的缓存Map
	 */
	cache: {},
	
	/**
	* 设计器的入口程序
	* 负责控件对象拖拽等操作的初始化
	*/
	design : function(){
		this.init_accordion();
		this.init_draggable();
		this.init_droppable();
		this.bind_event();
		this.set_tdSeq();
//		this.set_newLayOut();
		var that =this,
		    layoutTdCount = $("#tdCount").val();
	   	$('#formLayout').combobox({
	   		onSelect : function() {
				that.change_layout();
			},
			editable : false
		});
	   	if(layoutTdCount && layoutTdCount !=""){
	   		$('#formLayout').combobox("setValue",layoutTdCount);
	   	}
		this.init_td();		
	},

	/*设置td中的样式及显示编辑和删除控件的图片*/
    showHandleImage:function(nodeObj){
		$(nodeObj).css("background-color", "#a2e4fa");
		$(nodeObj).find(".opImg").css("visibility","visible");
	},
	/*设置td中的样式及隐藏编辑和删除控件的图片*/
	 hiddenHandleImage : function(nodeObj){
		$(nodeObj).css("background-color", "#ffffff");
		$(nodeObj).find(".opImg").css("visibility" , "hidden");
	},
	/**
	 * input弹出层控件弹出事件
	 */
	pop_InputPage : function(id,widgetType){
		$('#propWin').window({
			title:'数据表格',
			width:800,
			height:500,
			minimizable:false,
			draggable : false,
			maximizable:false,
			collapsible:false,
			inline : true,
			modal:true,
			href: f.contextPath + '/platform/form/prop/inputPopUp'
		});
	},
	/**
	 * 控件属性编辑弹出页面
	 */
	edit_WidgetPage : function($container,attributeData){
		var widget = ISV.pf.fd.base.FormWidgetFactory.newWidget(attributeData.widgetType);
		widget.setId(attributeData.id);
		widget.setIsNew(false);
		widget.setWidgetTypeData(attributeData);
        widget.setContainer($container);
		ISV.pf.fd.base.FormDesigner.opWidget = widget;
		widget.design();
	},
	/**
	 * 创建元素后的编辑
	 */
	edit_click: function(id){
		var widget = this.cache[id];
		ISV.pf.fd.base.FormDesigner.opWidget = widget;
		widget.design();
	},
	
	/**
	 * 创建元素后的删除
	 */
	del_click: function(id){
		$.messager.confirm("提示", "如果删除该属性，将会删除该类型下已有配置项的所有此属性的数据，确定要删除？",function(r){
			if(r){
				ISV.pf.fd.base.FormDataModel.removeNew(id);
				$('#' +id).closest('td').html('');  
			}
		});
	},
	
	/**
	 * 删除更新的控件和其属性
	 */
	del_WidgetPage : function(that,attributeData){
		$.messager.confirm("提示", "如果删除该属性，将会删除该类型下所有此属性的数据，确定要删除？",function(r){
			if(r){
				ISV.pf.fd.base.FormDataModel.addDelete(attributeData);
				$(that).parents("span").remove();
			}
		})
	},
	/**
	* 控件列表可拖拽的初始化处理
	*/
	init_draggable : function(){
    	 $('.easyui-linkbutton').draggable({
             revert:true,
     		 proxy:function(source){
	 			 var n = $('<div class="draggable-model-proxy item"></div>');
	 			 n.html($(source).html()).appendTo('body');
	 			 return n;
	 		 },
     		deltaX:0,
     		deltaY:0,
     		revert:true,
     		cursor:'auto'
         });
	},
	/**
	* 拖拽区域的初始化处理
	*/
	init_droppable : function(){
		var that = this;
		$("td.drop").mouseover(function(){
			that.showHandleImage(this);
		});
		$("td.drop").mouseout(function(){
			that.hiddenHandleImage(this);
		});
		 $('.right td.drop').droppable({
			 accept:'.easyui-linkbutton',
             onDragEnter:function(){
                 $(this).addClass('over');
             },
             onDragLeave:function(){
                 $(this).removeClass('over');
             },
             onDragOver :function(e, source){
            	 if( $.trim($(this).html()) != ""){
                     $(this).droppable('disable');
            	 }
             },
             onDrop:function(e,source){
            	 var widgetData = $.data(source);
                 var widgetType = widgetData['widgetType'];
                 var widget = ISV.pf.fd.base.FormWidgetFactory.newWidget(widgetType);
                 //初始化参数
                 widget.setIsNew(true);
                 widget.setId(new Date().getTime());
                 widget.setContainer($(this));
                 widget.setWidgetTypeData(widgetData);
                 that.opWidget = widget;
                 that.opWidget.design();
             }
         });
	},
	/**
	* accordin手风琴控件初始化处理
	*/
	init_accordion : function(){
		var that = this;
		$("#accordion_root").accordion({
			onSelect : function(title,index){	
				var  category = index + 1;
				that.onSelect(category);
				that.init_draggable();
				that.init_droppable();
			}
		});
		that.onSelect(1);

	},
	/**
	* 控件位置互换拖拽事件触发
	*/
	init_td :function(flag){
		var boolFlag = flag;
		$("td.drop").dblclick(function(){
			ISV.pf.fd.base.FormDesigner.init_widget_position_change(boolFlag);
			ISV.pf.fd.base.FormDesigner.init_widget_position_droppable();
		})
	},
    /**
    *控件位置互换 
    */
	init_widget_position_change : function(boolFlag){
   	   $('.right td.drop').draggable({
   		 disabled : boolFlag,
         revert:true,
 		 proxy:function(source){
 			 var div_html = $(this).html(),	
 			     parent_td_seq = $(this).closest('td').attr('seq'),
 			     widget_id = $(this).find("input[mark='currentId']").val();
 			 var html_move_obj ={
 					 "div_html" : div_html,
 					 "td_seq" : parent_td_seq,
 					 "id" : widget_id
 			 };
 			 var n = $('<div class="draggable-model-proxy item" style="width:400px;"></div>');
 			 n.html($(source).html()).appendTo('body');
 			 $("td").data("move_obj",html_move_obj);
 			 return n;
 		 },
 		deltaX:0,
 		deltaY:0,
 		revert:true,
 		cursor:'auto'
     });
 
	},
	
	/**
	* 拖拽放置区域的初始化处理
	*/
	init_widget_position_droppable : function(){        
		 $('.right td.drop').droppable({
			 accept:'.right td.drop',
             onDragEnter:function(){
                 $(this).addClass('over');
             },
             onDragLeave:function(){
        		 var data = $("td").data("move_obj"),
        		     td_html = data.div_html,
        		     seq = data.td_seq;
                 $(this).removeClass('over');
                 $("td[seq='"+ seq +"']").html(td_html);
             },
             onDragOver :function(e, source){
            	 var html_moved = $(this).html();
            	 var data = $("td").data("move_obj"),
            	     seq = data.td_seq,
            	     td_html = data.div_html;
            	 $("td").data("moved_obj",html_moved);               	
             },
             onDrop:function(e, source){
            	 var moved_widget_seq = $(this).closest('td').attr('seq'),
            	     moved_widget_id = $(this).find("input[mark='currentId']").val(),
            	     html_moved = $("td").data("moved_obj"),
            	     flag = false,
            	     data = $("td").data("move_obj"),
            	     move_widget_seq = data.td_seq,
            	     td_html = data.div_html,
            	     move_widget_id = data.id;
            	 $("td[seq='"+ move_widget_seq +"']").html(html_moved);
            	 $(this).html(td_html); 
            	 $('.right td.drop').draggable({
            		 disabled :'true'
            	 });
            	 var positionJson = {};
            	 positionJson[move_widget_id] = moved_widget_seq;
            	 positionJson[moved_widget_id] = move_widget_seq;
            	 ISV.pf.fd.base.FormDataModel.addUpdatePosition(positionJson);
            	 ISV.pf.fd.base.FormDesigner.init_td(flag);
            	 ISV.pf.fd.base.FormDesigner.init_draggable();
            	 ISV.pf.fd.base.FormDesigner.init_droppable();
             }
         });
	},
	/**
	 * 拖拽选取初始化
	 */
	onSelect : function(index){
		var curracd = $("#accordion_root").accordion("getSelected");
		$.ajax({
			url :f.contextPath + "/platform/form/design/findWidgetTypeByCategory",//查找所有控件类型
			type:"post",
            dataType:"json",
            async:false,
            data :{
            	"category" :index,
            	 "t" :  Math.random()
            },
            error : function(){
            	throw new Error('ajax error');
            },
            success : function(data){
            	$(curracd).html("");
            	//循环生成可拖拽的控件类型选择器
            	for(var i=0;i < data.length;i++){
            		var dragItem = $("<a href='##' class='easyui-linkbutton  l-btn l-btn-plain item' plain='true' fit='true' ><span class='l-btn-left'><span class='l-btn-text start-event-icon l-btn-icon-left'>" + data[i].widgetName + "</span></span></a><br>");
            	    $(curracd).append(dragItem);
            	    $.data(dragItem[0],data[i]);
            	}
            }
		});
	},
	
//	/**
//	*列布局显示调整
//	*/
//	set_newLayOut : function(){
//		$("#layout_tab tr td").each(function(){
//			var columnNum = $(this).attr("columnNum");
//			var isShow_tdCounts = $(this).closest("tr").children("td").length;
//			if(isShow_tdCounts == 1 && columnNum != 2){
//				var next_td_columnNum = $(this).closest("tr").next("tr").children("td:first").attr("columnNum");
//				if(next_td_columnNum != 2){
//				  var moved_td_arr = $(this).closest("tr").next("tr").html(),
//				      moved_td = moved_td_arr.split("</td>")[0] + "</td>",
//				      moved_bro = moved_td_arr.split("</td>")[1] + "</td>";
//					$(this).closest("tr").next("tr").html("");
//					$(this).closest("tr").next("tr").html(moved_bro);
//					$(this).closest("tr").append(moved_td);
//				}
//			}
//		});
//	},
	/**
	 * 给td加上排序
	 */
	set_tdSeq :function(){
		var tdIndex = 0; 
		$("#layout_tab tr td").each(function(){
		    var childrenLength = $(this).children().length;
		    if(childrenLength != 0){
		    	var tdSeq = $(this).attr("seq");
		    	tdIndex = tdIndex + 1;
		    	if(tdIndex < tdSeq){
		    		tdIndex = tdSeq;
		    	}
		    }
	    });
		if(tdIndex == 0){
			$("#layout_tab tr td").first().attr("seq",0);
			$("#layout_tab tr td").last().attr("seq",1);
		}else if(tdIndex != 0 ){
			$("#layout_tab tr td").eq(tdIndex).attr("seq",tdIndex);
			$("#layout_tab tr td").last().attr("seq",tdIndex + 1);
		}
	},
	/**
	 * 布局追加一行
	 */
	add_row :function(tdCount){
		var seq = parseInt($("#layout_tab tr td").last().attr("seq"))+1,
		    seq2 = seq + 1;
		if(tdCount == 1){
			$("#layout_tab").append($("<tr><td class='drop ' style='height:35px' align='center' seq="+ seq +"></td></tr>"));
		}else{//两列布局
			$("#layout_tab").append($("<tr><td class='drop ' style='height:35px' align='center'  seq="+ seq +"></td><td class='drop ' style='height:35px' align='center' seq="+ seq2 +"></td></tr>"));
		}
        this.init_td();
		this.init_draggable();
		this.init_droppable();
	},
	/**
	 * 增加列
	 */
	change_layout : function(){
		var tdCount = $("#formLayout").combobox('getValue');
		var $tbl = $("#layout_tab");
		var td = $tbl.find('td');
		$tbl.empty();
		var tr = $("<tr>");
		var i = 0;
		$(td).each(function() {
			var columnNum = $(this).attr("columnNum");
			++i;
			if (i == tdCount) {
				i = 0;
			}
			if (i == 1 || 1 == tdCount || 2 == columnNum) {
				tr = $("<tr>").appendTo($tbl);
			}
			$(this).find('script').remove();
			$(this).appendTo(tr);
		});
		if(tdCount == 2 ){
			$("#layout_tab tr").each(function(){
				var seq = parseInt($("#layout_tab tr td").last().attr("seq"))+1;
				if($(this).children("td").length ==1 && $(this).children("td").attr("columnNum") != 2){
					$(this).append("<td class='drop ' style='height:35px' align='center' seq='"+ seq +"'></td>");
				}
			});
		}
		this.init_td();
		this.init_draggable();
		this.init_droppable();
	},
	
	/**
	* 界面初始化时为已经存在的控件元素绑定事件
	* 如点击进行编辑、删除事件
	*/
	bind_event : function(){
		$('#save_button').click(function(){//保存表单事件
			var json = {
					businessNodeId: $('#businessNodeId').val(),
					id: $('#formId').val(),//表单编号
					businessType: $('#businessType').val(),
					formName: $('#formName').val(),//表单名称
					businessId: $('#businessId').val(),
					layout: $('#formLayout').combobox('getValue'),//布局:1列或2列
					newAttrs: ISV.pf.fd.base.FormDataModel.getNewAttrs(),//新建属性
					updateAttrs: ISV.pf.fd.base.FormDataModel.getUpdateAttrs(),//更新属性
					deleteAttrs: ISV.pf.fd.base.FormDataModel.getDeleteAttrs(),//删除属性
					positionAttrs:ISV.pf.fd.base.FormDataModel.getPositionAttrs()
			};
			console.log(json);
			f.ajax({
				url: f.contextPath + '/platform/form/design/submitForm',//提交地址
				type: 'POST',
				data: json,
				dataType: 'json',
				success: function(data) {
					ISV.pf.fd.base.FormDataModel.clear();
					window.opener.reloadTable();
			        window.opener.initTree();
					//window.close();
					//刷新表单列表
				}
			});
		});
	}
};