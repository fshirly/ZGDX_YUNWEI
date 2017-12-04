f(function(){	
	f('#duty_search_panel').panel({
	    href : getRootName() + '/platform/dutymanager/duty/toSearch'
	});
	(function(){
		var path = getRootName();
		f('.clc_bck').attr('title', '日历视图').bind('click', showCalendar);
		function showCalendar() {
			f('#duty_leader,#duty_watch').createSelect2({
				uri : '/platform/dutymanager/duty/queryDutyers',// 获取数据
				name : 'name',// 显示名称
				id : 'id'
			});	
			f('.clc_bck').attr('title', '列表视图').unbind().bind('click', showSearch).find('img').attr('src', path + '/style/images/duty_calendar.png');
			f('#duty_search_panel').panel('refresh', path + '/platform/dutymanager/duty/toCalendar');
		}
		function showSearch () {
			f('.clc_bck').attr('title', '日历视图').unbind().bind('click', showCalendar).find('img').attr('src', path + '/style/images/duty_search.png');
			f('#duty_search_panel').panel('refresh', path + '/platform/dutymanager/duty/toSearch');
		}
	})();
});

/**
 * 调班
 */
function toChange() {
	parent.$("#popWin").window({
		title: "调班",
		width: 650,
		height: 490,
		minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + "/platform/dutymanager/duty/changeDutyPage"
	});
}
