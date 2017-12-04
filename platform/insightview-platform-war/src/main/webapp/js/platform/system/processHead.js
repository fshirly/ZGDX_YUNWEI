/**
 * 引用JS和CSS头文件
 */
var rootName = getRootName(); //项目名称
var rootPath = getRootPath(); //项目路径
//alert(rootPath);

/**
 * 动态加载CSS和JS文件
 */
var dynamicLoading = {
	meta : function(){
		document.write('<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">');
	},
    css: function(path){
		if(!path || path.length === 0){
			throw new Error('argument "path" is required!');
		}
		//var head = document.getElementsByTagName('head')[0];
		//var link = document.createElement('link');
		//link.href = path;
		//link.rel = 'stylesheet';
		//link.type = 'text/css';
		//head.appendChild(link);
		document.write('<link rel="stylesheet" type="text/css" href="' + path + '">');
    },
    js: function(path, charset){
		if(!path || path.length === 0){
			throw new Error('argument "path" is required!');
		}
		//var head = document.getElementsByTagName('head')[0];
        //var script = document.createElement('script');
        //script.src = path;
        //script.type = 'text/javascript';
        //head.appendChild(script);
        document.write('<script charset="' + (charset ? charset : "utf-8") + '" src="' + path + '"></script>');
    }
};

/**
 * 取得项目名称
 * @author wul
 */
function getRootName() {
	//取得当前URL
	var path = window.document.location.href;
	//取得主机地址后的目录
	var pathName = window.document.location.pathname;
	var post = path.indexOf(pathName);
	//取得主机地址
	var hostPath = path.substring(0, post);
	//取得项目名
	var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return name + "/";
}

/**
 * 取得项目路径
 * @author wul
 */
function getRootPath() {
	//取得当前URL
	var path = window.document.location.href;
	//取得主机地址后的目录
	var pathName = window.document.location.pathname;
	var post = path.indexOf(pathName);
	//取得主机地址
	var hostPath = path.substring(0, post);
	//取得项目名
	var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return hostPath + name + "/";
}

//动态生成meta
dynamicLoading.meta();

//动态加载项目 JS文件
dynamicLoading.js(rootPath + "/js/process/center/common/jquery-2.1.3.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery.mockjax.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/bootstrap.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery.dataTables.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/bootstrap-datetimepicker.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/bootstrap-datetimepicker.zh-CN.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery-confirm.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/select2.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/blockui-master/jquery.blockUI.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/select2-zh.js", "utf-8");

dynamicLoading.js(rootPath + "/js/process/center/common/jquery-ztree/js/jquery.ztree.core-3.5.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/flow.js", "utf-8");
dynamicLoading.js(rootPath + "/js/common/commonUtil.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/jquery.easyui.min.js", "utf-8");

dynamicLoading.js(rootPath + "/js/process/center/common/jquery-validation-1.13.1/dist/jquery.metadata.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery-validation-1.13.1/dist/jquery.validate.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery-validation-1.13.1/dist/additional-methods.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/process/center/common/jquery-validation-1.13.1/dist/localization/messages_zh.js", "utf-8");

dynamicLoading.js(rootPath + "/js/metas.js", "utf-8");

dynamicLoading.js(rootPath + "/js/process/center/common/autocomplete/jquery.autocomplete.js", "utf-8");

dynamicLoading.js(rootPath + "/js/process/center/common/jquery.PrintArea.js", "utf-8");

//动态加载项目 CSS文件
dynamicLoading.css(rootPath + "/css/process/center/bootstrap.css");
dynamicLoading.css(rootPath + "/css/process/center/style.css");
dynamicLoading.css(rootPath + "/css/process/center/jquery.dataTables.css");
dynamicLoading.css(rootPath + "/css/process/center/bootstrap-datetimepicker.min.css");
dynamicLoading.css(rootPath + "/css/process/center/jquery-confirm.css");
dynamicLoading.css(rootPath + "/css/process/center/select2.min.css");
dynamicLoading.css(rootPath + "/css/process/center/flow.css");
dynamicLoading.css(rootPath + "/js/process/center/common/jquery-ztree/css/zTreeStyle/metro.css");
dynamicLoading.css(rootPath + "/js/process/center/common/autocomplete/jquery.autocomplete.css");


