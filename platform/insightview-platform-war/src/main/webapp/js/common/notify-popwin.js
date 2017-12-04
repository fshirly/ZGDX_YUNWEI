var fableApp = fableApp || {};

var index_div = 0;

fableApp.msgTips = {
	maxZindex: function(){
		var divs = document.getElementsByTagName("div");
		var maxZindex = 0;
	    for(var i=0, max=0; i<divs.length; i++){
	        maxZindex = Math.max( maxZindex,divs[i].style.zIndex || 0 );
	     }
		return parseInt(maxZindex);
	},
    //创建新消息提示框
	
	msgTips : function(msgData){
		index_div = index_div + 1;
		var lookName = '';
		if(0 === msgData.status){
			lookName = "任务处理&nbsp;";
		}else if(1 === msgData.status){
			lookName = "任务处理&nbsp;";
		}else{
			msgName = '';
			lookName = "查看详情&nbsp;";
		}
		var zIndex = this.maxZindex()+10;
		var msgTitle = msgData.title.length > 12?msgData.title.substring(0,11)+"...":msgData.title;
		var newMsg = msgData.content.length >69?msgData.content.substring(0,68)+"...":msgData.content;
		var msgContent = $("<div id='msgContent-"+ msgData.id +"' class='msgContent'></div>");
		var header = $("<div class='msgHeader'><div class='msgTitle'>"
		              +"<div class='msgTitleBg'>&nbsp;</div><div class='msgName'>"+msgTitle+"</div></div><div class='msgClose'>"
					  +"<div class='closeAction' id='closeBtn_"+msgData.id+"'> </div></div></div>");
		var msgMain = $("<div class='msgMain'></div>");
//		var title = $("<div class='contentTitle'>"+msgTitle+"</div>");
		var title = $("<div class='contentTitle'>&nbsp;</div>");
		var msgMainContent = $("<div class='msgMainContent'>"+ newMsg +"</div>");
		var lookMsg = $("<div class='lookMsg'><span class='msg'>目前有&nbsp;<a class='msg_num'></a>&nbsp;条未处理</span>"+lookName+"</div><div id='popWin1'></div>");
		msgMain.append(title).append(msgMainContent).append(lookMsg);
		msgContent.append(header);
		msgContent.append(msgMain);
		msgContent.insertAfter($("#content"));
		this.showNum(index_div);
		msgContent.animate({"bottom":"0em","opacity":1},"slow",function(){fableApp.msgTips.timer(msgContent);});
		this.closeMsgTips(msgContent,msgData);
		this.toOpenDialog(msgContent,lookMsg,msgData);
		this.refreshList();
	},
	showNum : function(index){
		$(".msg_num").text(index);
	},
	closeMsgTips : function(obj,msgData){
		$("#closeBtn_"+msgData.id).click(function(){
			obj.animate({"bottom":"0em","opacity":0},"slow",function(){
				obj.remove();
				index_div = index_div-1;
				$(".msg_num").text(index_div);
			});
		});
	},
	toOpenDialog : function(obj,lookMsg,msgData){
		var that = this;
		lookMsg.click(function(){
			//此处执行弹出详情页面操作
			that.popDialog(msgData);
			obj.animate({"bottom":"0em","opacity":0},"slow",function(){
				obj.remove();
				index_div = index_div -1;
				$(".msg_num").text(index_div);
			});
		});
	},
	timer : function(obj){
//		var timer = setInterval(function(){
//			    obj.animate({"bottom":"0em","opacity":0},"slow",function(){
//				    obj.remove();
//					clearInterval(timer);
//			    });
//			},10000);
	},
	popDialog : function(msgData){//执行弹出查看详情
			var title = '任务处理',
				urlPath = msgData.url;
			/*if(0 === msgData.status){
				title = "任务办理";
			}else{
				title = "任务认领";
			}*/
			var str = 'popWin';
			if(msgData.flag == 't') {
				str = 'popWin2';
			}else if(msgData.flag == 'v3'){
				str = 'popWin4';
			}
			var p_width = 900,
				p_height = 590;
			if(msgData.popWidth!=null) {
				p_width = msgData.popWidth;
			}
			if(msgData.popHeight != null) {
				p_height = msgData.popHeight;
			};
			$.ajax({
				url : getRootName() + "/itsm/processRemind/isTaskFinish?taskId=" + msgData.id,
				success : function (data) {
					if(data == "no") {
						$.messager.alert('提示','该任务已经被处理','info');
						return;
					}else{
						parent.parent.$('#'+str).window({
							title : title,
							width : p_width,
							height : p_height,
							minimizable : false,
							maximizable : false,
							collapsible : false,
							modal : false,
							href : getRootName()+urlPath,
							onClose : function(){
							}
						});
					}
				},
				error : function() {
					$.messager.alert('提示','ajax_error','info');
				}
			});
	}
	,
	refreshList : function(){
		var parentPage = null;
	    if (null != window.frames['component_2'].document.getElementById('pageID')) {
	        parentPage = window.frames['component_2'].document.getElementById('pageID').value;
	    }
	    if (parentPage ==  'backlogPage') {
	        window.frames['component_2'].doInitTable('tblEventList', 'Reserved', 0, 5); // 刷新待办事项列表
	    }
        if(window.frames['component_2'].frames[0].frames[0].document.getElementById('mainPage').value=="mainPage"){
            window.frames['component_2'].frames[0].frames[0].doInitTable('tblEventList', 'Reserved', 0, 5);
        }
	}
};
