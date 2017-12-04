var arr = new Array("/js/common/jquery-1.9.1.min.js", "/js/common/commonUtil.js",
		"/js/common/base64.js", "/plugin/easyui/jquery.easyui.min.js",
		"/style/style_one/js/main.js", "/plugin/dTree/dtree.js",
		"/plugin/dTree/combo/comboJS.js", "/js/permission/sysuser.js");
for (var i = 0; i < arr.length; i++) {
	LoadJS(arr[i]);
}

function LoadJS(src) {
	alert(src);
	var headObj = document.getElementsById("component_2");
	srciptObj = document.createElement("script");
	srciptObj.language = "javaScript";
	srciptObj.type = "text/JavaScript";
	srciptObj.src = "fable-itsm-bpmconsole-war"+src;
	headObj.appendChild(srciptObj);
}