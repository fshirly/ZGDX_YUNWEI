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
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/jquery.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/jquery.easyui.min.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/jquery-form.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/locale/easyui-lang-zh_CN.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/commonUtil.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/fbsmsg.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/tags/ListviewPlugin.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/tags/systemTreePlugin.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/jquery.tree.extend.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Easyui/treegrid-dnd.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/Math.uuid.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/md5.js", "utf-8");
dynamicLoading.js(rootPath + "/js/platform/system/validator.js", "utf-8");

//动态加载项目 CSS文件
dynamicLoading.css(rootPath + "/css/platform/system/css/reset.css");
dynamicLoading.css(rootPath + "/css/platform/system/css/base.css");
dynamicLoading.css(rootPath + "/js/platform/system/Easyui/themes/icon.css");
dynamicLoading.css(rootPath + "/js/platform/system/Easyui/themes/color.css");
dynamicLoading.css(rootPath + "/js/platform/system/Easyui/themes/easyui.css");
dynamicLoading.css(rootPath + "/js/platform/tags/listviewPlugin.css");