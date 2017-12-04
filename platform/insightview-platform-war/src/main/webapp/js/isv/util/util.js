/**
 * 通用工具模块
 * 提供公共的方法
 * 
 * 郑自辉
 */
(function(){
	define(function(){
		var util = {
			/**
			 * 获取项目的根路径
			 */
			getContextPath : function(){
				// 取得主机地址后的目录
				var pathName = window.document.location.pathname;
				// 取得项目名
				var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
				return name;
			}
		};
		return util;
	});
})();