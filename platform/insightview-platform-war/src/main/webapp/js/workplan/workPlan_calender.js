var workplan = workplan || {};

workplan.calendar = {
		basePath: getRootName(),
		toList: function() {
			window.close();
		},
		toAdd: function() {
			this.openWin("新建工作计划", 700, 350, "/workPlan/toAddCalWorkPlan");
		},
		toEdit: function(id) {
			this.openWin("修改工作计划", 750, 350, "/workPlan/toEditCalWorkPlan?id="+id);
		},
		toShow: function(id) {
			this.openWin("查看工作计划", 750, 350, "/workPlan/toShowCalWorkPlan?id="+id);
		},
		openWin: function(title, width, height, url) {
			$('#plan_calendar').window({
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
};

$(function() {
	
	var path = workplan.calendar.basePath;
	$("#workPlan_calendar").fullCalendar({
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
		events: path+'/workPlan/loadEvents',
		eventClick: function(event) {
			workplan.calendar.toShow(event.id);
		},
		eventMouseover: function(event) {
			$('div.event_'+event.id).css({"background-color": "#FF34B3"});
		},
		eventMouseout: function(event) {
			$('div.event_'+event.id).css({"background-color": "pink"});
		},
		eventRender: function(event, element) {
			element.html(event.title);
		}
	});
	$("#wspace").show().appendTo(".fc-header-left");
	$("#workPlanAddNew").show().appendTo(".fc-header-left");
	$('#workPlanAddNew').bind("click",function(){workplan.calendar.toAdd();});
});