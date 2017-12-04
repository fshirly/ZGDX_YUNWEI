var worklog = worklog || {};

worklog.calendar = {
		basePath: getRootName(),
		toList: function() {
			window.close();
		},
		toAdd: function() {
			this.openWin("新建工作日志", 750, 400, "/workLog/toAddCalWorkLog");
		},
		toEdit: function(id) {
			this.openWin("修改工作日志", 750, 400, "/workLog/toEditCalWorkLog?id="+id);
		},
		toShow: function(id) {
			this.openWin("查看工作日志", 750, 400, "/workLog/toShowCalWorkLog?id="+id);
		},
		openWin: function(title, width, height, url) {
			$('#log_calendar').window({
				title: title,
				width: width,
				height: height,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				resizable: false,
				draggable: false,
				href: this.basePath + url,
			});
		},
}

$(function() {
	
	var path = worklog.calendar.basePath;
	$("#workLog_calendar").fullCalendar({
		header: {
			left: 'today prev,next',
			center: 'title',
			right: '',
		},
		buttonText: {
			prev: "<上个月",
			next: "下个月>"
		},
		editable: false,
		height: 750,
		contentHeight: 680,
		theme: false,
		events: path+'/workLog/loadEvents',
		eventClick: function(event) {
			worklog.calendar.toShow(event.id);
		},
		eventMouseover: function(event) {
			$('div.event_'+event.id).css({"background-color": "#FF34B3"});
		},
		eventMouseout: function(event) {
			$('div.event_'+event.id).css({"background-color": "pink"});
		},
	});
});