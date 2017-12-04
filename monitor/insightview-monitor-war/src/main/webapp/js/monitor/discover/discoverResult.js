$(window).load(function() {
	var result = $("#result").val(); 
	var table = $("<table></table>");
	table.append(result);
	$('#deviceView').append(table);
});

function next3() {
	var taskID = $("#taskID").val();
	var navigationBar = $("#navigationBar").val();
	window.location.href = getRootName()
		+ '/monitor/discover/importDiscoverResult?taskID='+ taskID+'&navigationBar='+navigationBar;
}

function deviceList() {
	var taskID = $("#taskID").val();
	window.location.href = getRootName()
			+ '/monitor/discover/toDiscoverDeviceList?taskID=' + taskID;
}

function showtr2(devicetype) {
	var taskID = $("#taskID").val();
	var uri = getRootName()
			+ "/monitor/discover/toDiscoverDeviceList?moClassId=" + devicetype
			+ "&taskID="+ taskID;
	window.open(uri, null,
			"dialogHeight=550px;dialogWidth=750px;resizable=no;menuba=no;resizable=no");
}