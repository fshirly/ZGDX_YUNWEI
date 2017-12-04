$(function(){
	$('#module').combobox({
		panelHeight : '120',
		panelWidth : '178',	
		valueField : 'key',
		textField : 'val',
		data: [
		{
			key: '1',
			val: '故障'
		},{
			key: '2',
			val: '问题'
		},{
			key: '3',
			val: '变更'
		},{
			key: '4',
			val: '资源'
		},{
			key: '5',
			val: '任务'
		},{
			key: '6',
			val: '知识'
		},{
			key: '7',
			val: '已知错误库'
		},{
			key: '8',
			val: '典型案例'
		}],
		editable : false,
		onSelect : function(rec) {
			if(rec.key == '1') {
				$("#t").html("标题：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "";
			} else if(rec.key == '2') {
				$("#t").html("标题：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "";
			} else if(rec.key == '3') {
				$("#t").html("标题：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "";
			} else if(rec.key == '4') {
				$("#t").html("标题/关键字：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "none";
			} else if(rec.key == '5') {
				$("#t").html("标题/关键字：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "none";
			} else if(rec.key == '6') {
				$("#t").html("标题/关键字：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "none";
			} else if(rec.key == '7') {
				$("#t").html("标题/关键字：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "none";
			} else if(rec.key == '8') {
				$("#t").html("标题/关键字：");
				document.getElementById("serialNo").parentNode.parentNode.style.display = "none";
			}
		}
	});
	$('#module').combobox('setValue',1);
	
    $("#searchBtn").click(function(){
        if($('#module').combobox('getValue') == '1') { //事件
        	if ($('#title').val() != '' && $('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventManageView?title=' + encodeURI(encodeURI($('#title').val())) + '&serialNo=' + $('#serialNo').val(),'事件','noiframe');
        	} else if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventManageView?title=' + encodeURI(encodeURI($('#title').val())),'事件','noiframe');
        	} else if($('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventManageView?serialNo=' + $('#serialNo').val(),'事件','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventManageView','故障','noiframe');
        	}
        } else if($('#module').combobox('getValue') == '2') {//问题
        	if ($('#title').val() != '' && $('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/problemBillManageView?title=' + encodeURI(encodeURI($('#title').val())) + '&serialNo=' + $('#serialNo').val(),'问题','noiframe');
        	} else if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/problemBillManageView?title=' + encodeURI(encodeURI($('#title').val())),'问题','noiframe');
        	} else if($('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/problemBillManageView?serialNo=' + $('#serialNo').val(),'问题','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/problemBillManageView','问题','noiframe');
        	}
        }  else if($('#module').combobox('getValue') == '3') {//变更
        	if ($('#title').val() != '' && $('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/changeManageView?title=' + encodeURI(encodeURI($('#title').val())) + '&serialNo=' + $('#serialNo').val(),'变更','noiframe');
        	} else if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/changeManageView?title=' + encodeURI(encodeURI($('#title').val())),'变更','noiframe');
        	} else if($('#serialNo').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/changeManageView?serialNo=' + $('#serialNo').val(),'变更','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/changeManageView','变更','noiframe');
        	}
        }  else if($('#module').combobox('getValue') == '4') {//资源
        	if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/resciList?title=' + encodeURI(encodeURI($('#title').val())),'资源','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/resciList','资源','noiframe');
        	}
        }  else if($('#module').combobox('getValue') == '5') {//任务
        	if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/taskManageView?title=' + encodeURI(encodeURI($('#title').val())),'任务','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/taskManageView','任务','noiframe');
        	}
        } else if($('#module').combobox('getValue') == '6') {//知识
        	if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/knowledgeManageView?title=' + encodeURI(encodeURI($('#title').val())),'知识','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/knowledgeManageView','知识','noiframe');
        	}
        }  else if($('#module').combobox('getValue') == '7') {//已知错误库
        	if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/knownErrorManageView?title=' + encodeURI(encodeURI($('#title').val())),'已知错误库','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/knownErrorManageView','已知错误库','noiframe');
        	}
        } else if($('#module').combobox('getValue') == '8') {//典型案例
        	if($('#title').val() != '') {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventCaseManageView?title=' + encodeURI(encodeURI($('#title').val())),'典型案例','noiframe');
        	} else {
        		parent.parent.toShowTabsInfo( '/dashboardPageManage/eventCaseManageView','典型案例','noiframe');
        	}
        }
    });
});
