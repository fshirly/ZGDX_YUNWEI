<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>值班日历表</title>
</head>
<body>
<link href='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.css' rel='stylesheet' />
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/moment.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.min.js'></script>
<script	type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/zh-cn.js'></script>
<style type="text/css">
.fc-header{margin-top:14px;}
.fc-header-title h2 {font-size:14px;}
.line_sp{height: 3px; background: #fff}
.fc-event-title {
	display: none;
	padding: 0 1px;
}
</style>
<script type="text/javascript">
	function leaderImageEle(event) {/*获取带班领导元素*/
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

	(function() {
		f("#duty_calendar").fullCalendar({
			header : {
				left : 'today prev,next',
				center : 'title',
				right : '',
			},
			buttonText : {
				prev : "<上个月",
				next: "下个月>"
			},
			editable : false,
			theme : false,
			events : getRootName() + '/platform/dutymanager/duty/queryEvents',//修改成后台的URL
			eventRender : function(event, element) {
				var user, dutyer, dutyName, userNames, path = event.fileUrl, order = event.order, toolcons = [];
				var leaderImage = leaderImageEle(event);
				if (event.UserIcon && event.Leader) {
					user = path + event.UserIcon;
				} else if (event.Leader){
					user = '${pageContext.request.contextPath}/js/fullcalendar/images/photo.gif';
				}
				/* if (user) {
					f('<img width="40px" height="40px" src="' + user + '"/>').appendTo(f(element));
					toolcons.push('带班领导：' + event.Leader + '<br/>');
				} */
				if (leaderImage) {
					var $lead = f(leaderImage);
					$lead.appendTo(f(element));
					f('<div class="line_sp"></div>').appendTo(f(element));
					toolcons.push('带班领导：' + event.Leader + '<br/>');
				}
				if (order) {
					for (var i = 0, size = order.length; i < size; i++) {
						userNames = order[i].userNames;
						toolcons.push(order[i].Title + '值班人：' + f.map(userNames, function(name){return name.UserName;}).join(',') + '<br/>');
						if (!dutyName) {
							for (var j = 0, len = userNames.length; j < len; j++) {
								if (userNames[j].UserType === 1) {
									dutyer = userNames[i].UserIcon;
									dutyName = userNames[i].UserName;
									break;
								}
							}
						}
					}
				}
				if (!dutyer) {
					dutyer = '${pageContext.request.contextPath}/js/fullcalendar/images/photo.gif';
				} else {
					dutyer = path + dutyer;
				}
				if (dutyName) {
					f('<div class="fc-event-inner">值班人：<span style="position:absolute;">' + dutyName + '</span></div>').appendTo(f(element));
					f('<img width="40px" height="40px" src="' + dutyer + '"/>').appendTo(f(element));
				}
				if (event.reserves) {
					toolcons.push('备勤人员：' + event.reserves + '<br/>');
				}
				f(element).tooltip({
				    position : 'right',
				    content : toolcons.join(''),
				    onShow: function(){
				        $(this).tooltip('tip').css({
				            backgroundColor: '#a2e4fa',
				            borderColor: '#a2e4ff'
				        });
				    }
				});
			},
			eventDataTransform : function(data) {
				if (data.Leader) {
					data.title = '带班领导：' + data.Leader;
				}
				data.start = data.DutyDate.substr(0,data.DutyDate.indexOf('('));
				return data;
			}
		});
		
	})();
</script>
	<div id='duty_calendar' style="width: 70%; margin: 0 auto"></div>
</body>
</html>