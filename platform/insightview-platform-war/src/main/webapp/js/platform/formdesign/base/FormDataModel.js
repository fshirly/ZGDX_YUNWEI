/**
* 表单设计器前台数据缓存
* 在表单真正保存之前表单的属性数据需要在此缓存
* 后续可方便处理撤销、重做等功能
* 真正保存表单后再把这些数据发送到后台持久化
* 郑自辉
*/
f.namespace('ISV.pf.fd.base');
ISV.pf.fd.base.FormDataModel = (function(){
	
	/**
	 * 私有函数，命名规则约定
	 */
	var _removeAttr = function(attr,attrArray) {
		for (var i = 0,len = attrArray.length;i < len;i++) {
			var _attr = attrArray[i];
			if (_attr['id'] === attr['id']) {
				attrArray.splice(i,1);
				break;
			}
		}
	};
	
	/**
	 * 从缓存池中获取指定属性的JSON数据
	 */
	var _getAttrFromArray = function(id,array) {
		for (var i = 0,len = array.length;i < len;i++) {
			var _attr = array[i];
			if (_attr['id'] === id) {
				return _attr;
			}
		}
	};
	
	/**
	* 新增加的属性
	*/
	var newAttrs = [];
	
	/**
	* 更新的属性
	* 只针对原来已有的属性
	*/
	var updateAttrs = [];
	
	/**
	* 删除的属性
	* 只针对原来已有的属性
	*/
	var deleteAttrs = [];
	
	/**
	* 控件位置更新
	*/
	var positionAttrs = {};
	
	/**
	* 当前控件所在行显示的列数
	*/
	return {
		
		/**
		* 添加新增的属性
		*/
		addNew: function(attr){
			//如果之前已经存在，则先删除
			this.removeNew(attr);
			newAttrs.push(attr);
		},
		
		/**
		* 删除新增的属性
		*/
		removeNew: function(attr){
			_removeAttr(attr, newAttrs);
		},
		
		/**
		* 添加更新的属性
		*/
		addUpdate: function(attr){
			this.removeUpdate(attr);
			updateAttrs.push(attr);
		},
		
		/**
		 * 删除要更新的属性
		 */
		removeUpdate: function(attr) {
			_removeAttr(attr, updateAttrs);
		},
		
		/**
		* 更新控件位置互换后的信息 
		*/
		addUpdatePosition : function(attr){
			if(positionAttrs !={}){
				for(var p in attr){
					positionAttrs[p] = attr[p]; 
				}
			}else{
				positionAttrs = attr;
			}
		},
		
		/**
		* 添加删除的属性
		*/
		addDelete: function(attr){
			deleteAttrs.push(attr);
			/**
			 * 要删除的属性如果之前在updateAttrs中记录的话，就删除，不需要更新了
			 */
			this.removeUpdate(attr);
		},
		
		/**
		* 去除之前要删除的属性
		*/
		removeDelete: function(attr){
			_removeAttr(attr, deleteAttrs);
		},
		
		/**
		* 获取属性数据
		*/
		getAttr: function(id,isNew){
			if (isNew === true) {
				return _getAttrFromArray(id, newAttrs);
			}
			else {
				return _getAttrFromArray(id, updateAttrs);
			}
		},
		
		/**
		 * 返回新增的属性JSON
		 */
		getNewAttrs: function() {
			return newAttrs;
		},
		
		/**
		 * 返回需要更新的属性JSON
		 */
		getUpdateAttrs: function() {
			return updateAttrs;
		},
		
		/**
		 * 返回需要更新的属性JSON
		 */
		getPositionAttrs: function() {
			return positionAttrs;
		},
		/**
		 * 返回需要删除的属性JSON
		 */
		getDeleteAttrs: function() {
			return deleteAttrs;
		},
		
		/**
		 * 清空缓存池
		 */
		clear: function() {
			newAttrs = [];
			updateAttrs = [];
			deleteAttrs = [];
			positionAttrs = {};
		}
	};
})();