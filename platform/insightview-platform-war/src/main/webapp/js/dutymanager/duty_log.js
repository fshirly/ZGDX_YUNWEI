var dutyLog = dutyLog || {};

dutyLog.leaderImageEle = function (event) {/*获取带班领导元素*/
	var user, rootPath = getRootName();
	var edit = rootPath + '/style/images/duty_log.png', leaderImage;
	if (event.UserIcon && event.Leader) {
		user = event.fileUrl + event.UserIcon;/*用户头像*/
	} else if (event.Leader) {
		user = rootPath + '/js/fullcalendar/images/photo.gif';/*默认用户头像*/
	}
	if (user && event.checkIn) {/*验证当前用户与值班领导为同一个人即添加编辑按钮*/
		leaderImage = '<div class="fc-event-inner">带班领导：<span style="position:absolute;">' + event.Leader + '</span></div>';
		leaderImage += '<div style="position: relative;"><img width="40px" height="40px" src="' + user + '"/><img title="登记日志" onclick="dutyLog.leaderLog(\'' + event.DutyDate +'\')" class="edit_duty_log" src="' + edit + '"/></div>';
	} else if (user) {/*当前用户不是值班领导,则可以查看该值班记录的相关信息*/
		leaderImage = '<div class="fc-event-inner">带班领导：<span style="position:absolute;">' + event.Leader + '</span></div>';
		leaderImage += '<div><img width="40px" height="40px" src="' + user + '"/></div>';
	}
	return leaderImage;
};
dutyLog.dutyerImageEle = function (event) {/*获取值班负责人元素*/
	var dutyer, dutyName, userNames, checkIn, order = event.order, rootPath = getRootName();
	var edit = rootPath + '/style/images/duty_log.png', dutyImage;
	if (order) {
		userNames = order.userNames;
		for (var j = 0, len = userNames.length; j < len; j++) {
			if (userNames[j].UserType === 1) {
				checkIn = userNames[j].checkIn;
				dutyer = userNames[j].UserIcon;
				dutyName = userNames[j].UserName;
				break;
			}
		}
	}
	if (!dutyer) {
		dutyer = rootPath + '/js/fullcalendar/images/photo.gif';
	} else {
		dutyer = event.fileUrl + dutyer;
	}
	if (dutyName && checkIn) {/*验证当前用户与班次负责人为同一个人即添加编辑按钮*/
		dutyImage = '<div class="fc-event-inner">值班人：<span style="position:absolute;">' + dutyName + '</span></div>';
		dutyImage += '<div style="position: relative;"><img width="40px" height="40px" src="' + dutyer + '"/><img title="登记日志" onclick="dutyLog.dutyLog(\'' + dutyName +  '\',\'' + event.DutyDate +'\')" class="edit_duty_log" src="' + edit + '"/></div>';
	} else if (dutyName) {/*当前用户不是值班中的班次负责人,则可以查看该值班记录的相关信息*/
		dutyImage = '<div class="fc-event-inner">值班人：<span style="position:absolute;">' + dutyName + '</span></div>';
		dutyImage += '<div><img width="40px" height="40px" src="' + dutyer + '"/></div>';
	}
	return dutyImage;
};
dutyLog.dutyTip = function ($ele, content) {/*带班领导、值班负责人生成tooltip*/
	if (!$ele) {
		return;
	}
	$ele.tooltip({
		position : 'right',
		content : content,
		onShow : function() {
			$(this).tooltip('tip').css({
				backgroundColor : '#a2e4fa',
				borderColor : '#a2e4ff'
			});
		}
	});
};
dutyLog.getValue = function (val) {/*获取val值*/
	return val === undefined ? '' : val;
};

dutyLog.leaderLog = function (duty) {/*带班领导登记日志*/
	var options = {
		title : '带班记录',
		width : 700,
		height : 450,
		href : getRootName() + '/platform/dutylog/toLeaderLog' + '?duty=' + duty,
	};
	dutyLog.dialog(options);
};

dutyLog.dutyLog = function (dutyer, duty) {/*值班负责人登记日志*/
	var options = {
		title : '值班日志',
		width : 750,
		height : 600,
		href : getRootName() + '/platform/dutylog/toDutyerLog' + '?duty=' + duty + '&dutyer=' + dutyer,
	};
	dutyLog.dialog(options);
};

/*弹出窗口*/
dutyLog.dialog = function (options) {
	var baseO = {
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			onOpen : function () {
				$.fn.window.defaults.onOpen.call(this);
			},
			onClose : function () {
				f(this).dialog('destroy');
			}
	};
	f.extend(baseO, options);
	f('<div id="dutyDialog"/>').dialog(baseO).dialog('open');
};

dutyLog.handleLog = function () {/*登记带班领导的值班日志*/
	if (checkInfo('#duty_form')) {
		f.ajax({
			url : getRootName() + '/platform/dutylog/handle',
			data : f('#duty_form').serialize(),
			success : function (data) {
				if (data) {
					f('#dutyDialog').dialog('close');
				} else {
					f.messager.alert('提示', '登记值班日志异常,请检查！', 'info');
				}
				$("#duty_calendar").fullCalendar("refetchEvents");
			}
		});
	}
};

f(function() {
	f("#duty_calendar").fullCalendar({
				header : {
					left : 'today prev,next',
					center : 'title',
					right : '',
				},
				buttonText : {
					prev : "<上个月",
					next : "下个月>"
				},
				editable : false,
				theme : false,
				events : getRootName() + '/platform/dutylog/queryEvents',// 修改成后台的URL
				eventRender : function(event, element) {
					var leaderImage , dutyerImage, isLead = false, isDuty = false;
					leaderImage = dutyLog.leaderImageEle(event);
					dutyerImage = dutyLog.dutyerImageEle(event);
					if (leaderImage && leaderImage.indexOf('edit_duty_log') > -1) {
						isLead = true;
					}
					if (dutyerImage && dutyerImage.indexOf('edit_duty_log') > -1) {
						isDuty = true;
					}
					var $lead , $duty = f(dutyerImage);
					if (leaderImage) {
						$lead = f(leaderImage);
						$lead.appendTo(f(element));
						f('<div class="line_sp"></div>').appendTo(f(element));
					}
					$duty.appendTo(f(element));
					if (isLead || isDuty) {
						toolcons = '领导交办工作：' + dutyLog.getValue(event.NoticePoints) + '<br/>';
						toolcons += '领导带班日志：' + dutyLog.getValue(event.LeaderLog);
						dutyLog.dutyTip ($lead, toolcons);
					}
					if (isDuty || isLead) {
						toolcons = '&nbsp;&nbsp;&nbsp;交班记录：' + dutyLog.getValue(event.Advices) + '<br/>';
						toolcons += '值班人日志：' + dutyLog.getValue(event.DutyLog);
						dutyLog.dutyTip ($duty, toolcons);
					}
					if (!isLead && !isDuty) {
						toolcons = '领导交办工作：' + dutyLog.getValue(event.NoticePoints) + '<br/>';
						toolcons += '领导带班日志：' + dutyLog.getValue(event.LeaderLog) + '<br/>';
						toolcons += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交班记录：' + dutyLog.getValue(event.Advices) + '<br/>';
						toolcons += '&nbsp;&nbsp;&nbsp;值班人日志：' + dutyLog.getValue(event.DutyLog);
						dutyLog.dutyTip (f(element), toolcons);
					}
				},
				eventDataTransform : function(data) {
					/*if (data.Leader) {
						data.title = data.Leader;
					}*/
					data.start = data.DutyDate;
					return data;
				}
			});
});