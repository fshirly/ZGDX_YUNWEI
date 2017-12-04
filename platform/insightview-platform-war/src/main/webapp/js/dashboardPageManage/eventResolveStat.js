/**
 * 事件一线解决率
 */
(function(){
	require(['chart/gauge'],function(gauge){
		gauge({
			id : 'firstTeamStat',
			title : '一线解决率(%)',
			max : 100,
			min : 0,
			url : '/dashboardPageManage/firstTeamResolvingRate',
		});
	});
})();