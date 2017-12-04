(function($){
	/**
	 * 对easyui-validatebox的扩展
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		    minLength : { 
		    	// 判断最小长度
		        validator : function(value, param) {
		            return value.length >= param[0];
		        },
		        message : '最少输入 {0} 个字符。'
		    },
		    
		    length:{ 
		    	// 判断长度区间
		    	validator:function(value,param){
		        var len=$.trim(value).length;
		            return len>=param[0]&&len<=param[1];
		        },
		            message:"内容长度介于{0}和{1}之间."
		        },
		        
		    phone : {
		    	// 验证电话号码
		        validator : function(value) {
		            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		        },
		        message : '格式不正确,请使用下面格式:020-88888888'
		    },
		    
		    mobile : {
		    	// 验证手机号码
		        validator : function(value) {
		            return /^(13|15|18)\d{9}$/i.test(value);
		        },
		        message : '手机号码格式不正确(正确格式如：13450774432)'
		    },
		    
		    phoneOrMobile:{
		    	//验证手机或电话
		        validator : function(value) {
		            return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		        },
		        message:'请填入手机或电话号码,如13688888888或020-8888888'
		    },
		    
		    idcard : {
		    	// 验证身份证
		        validator : function(value) {
		            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		        },
		        message : '身份证号码格式不正确'
		    },
		    
		    floatOrInt : {
		    	// 验证是否为小数或整数
		        validator : function(value) {
		            return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
		        },
		        message : '请输入数字，并保证格式正确'
		    },
		    
		    currency : {
		    	// 验证货币
		        validator : function(value) {
		            return /^\+?(:?(:?\d+\.\d+)|(:?\d+))$/.test(value);
		        },
		        message : '货币格式不正确'
		    },
		    
		    numeric : {
		    	// 验证数值
		        validator : function(value) {
		            return /^([1-9][\\d]{0,9}|0)(\\.[\\d]{1,2})?$/.test(value);
		        },
		        message : '请输入整数部分在10位以内，小数点后最多有两位的数值'
		    },
		    
		    qq : {
		    	// 验证QQ,从10000开始
		        validator : function(value) {
		            return /^[1-9]\d{4,9}$/i.test(value);
		        },
		        message : 'QQ号码格式不正确(正确如：453384319)'
		    },
		    
		    integer : {
		    	// 验证整数
		        validator : function(value) {
		            return /^[+]?[1-9]+\d*$/i.test(value);
		        },
		        message : '请输入整数'
		    },
		    
		    number: {
		    	// 验证数字
		        validator: function (value, param) {
		            return /^\d+$/.test(value);
		        },
		        message: '请输入数字'
		    },
		    
		    url: {
		    	// 验证网址
		        validator: function (value, param) {
		            return /^(\w+:\/\/)?\w+(\.\w+)+.*$/.test(value);
		        },
		        message: '请输入有效网址'
		    },
		    
		    chinese : {
		    	// 验证中文
		        validator : function(value) {
		            return /^[\u0391-\uFFE5]+$/i.test(value);
		        },
		        message : '请输入中文'
		    },
		    
		    english : {
		    	// 验证英语
		        validator : function(value) {
		            return /^[A-Za-z]+$/i.test(value);
		        },
		        message : '请输入英文'
		    },
		    
		    letter : {
		    	// 验证字母
		        validator : function(value) {
		            return /^[a-zA-Z]*$/g.test(value);
		        },
		        message : '请输入字母'
		    },
		    
		    letterOrNum : {
		    	// 验证字母或数字
		        validator : function(value) {
		            return /^[0-9a-zA-Z]*$/g.test(value);
		        },
		        message : '请输入字母或数字'
		    },
		    
		    character : {
		    	// 验证字符
		        validator : function(value) {
		            return /^(.|\\n){0,500}$/.test(value);
		        },
		        message : '请输入字符'
		    },
		    
		    unnormal : {
		    	// 验证是否包含空格和非法字符
		        validator : function(value) {
		            return /.+/i.test(value);
		        },
		        message : '输入值不能为空和包含其他非法字符'
		    },
		    
		    username : {
		    	// 验证用户名
		        validator : function(value) {
		            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		        },
		        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
		    },
		    
		    faxno : {
		    	// 验证传真
		        validator : function(value) {
		            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		        },
		        message : '传真号码不正确'
		    },
		    
		    zip : {
		    	// 验证邮政编码
		        validator : function(value) {
		            return /^[1-9]\d{5}$/i.test(value);
		        },
		        message : '邮政编码格式不正确'
		    },
		    
		    ip : {
		    	// 验证IP地址
		        validator : function(value) {
		            return /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value);
	            },
		        message : 'IP地址格式不正确'
		    },
		    
		    name : {
		    	    // 验证姓名，可以是中文或英文
		            validator : function(value) {
		                return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
		            },
		            message : '请输入姓名'
		    },
		    
		    carNo : {
		    	// 验证车牌号
		        validator : function(value){
		            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
		        },
		        message : '车牌号码无效（例：粤J12350）'
		    },
		    
		    carenergin : {
		    	// 验证发动机型号
		        validator : function(value){
		            return /^[a-zA-Z0-9]{16}$/.test(value);
		        },
		        message : '发动机型号无效(例：FG6H012345654584)'
		    },
		    
		    email : {
		    	// 验证电子邮箱
		        validator : function(value){
		            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		        },
		        message : '请输入有效的电子邮件账号(例：abc@126.com)'
		    },
		    
		    msn : {
		    	// 验证有效的msn账号
		        validator : function(value){
		            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		        },
		        message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
		    },
		    
		    department : {
		    	// 验证部门排序号
		        validator : function(value){
		            return /^[0-9]*$/.test(value);
		        },
		        message : '请输入部门排序号(例：1)'  
		    },
		    
		    samePasswd : {
		    	// 验证两次输入的密码是否一致
		        validator : function(value, param){
		            if($("#"+param[0]).val() != "" && value != ""){
		                return $("#"+param[0]).val() == value;
		            }else{
		                return true;
		            }
		        },
		        message : '两次输入的密码不一致！'
		    }
		});
	/**
	 * easyUI combobox的扩展，目的解决弹出层时combo panel的z-index的问题
	 */
	if ($.fn.combobox) {
		$.fn.combobox.defaults.onShowPanel = function(){
			var z_index = +($(this).closest('.window').css('z-index'));
			$(this)
			.combobox('panel').parent().css('z-index',z_index + 1);
		};
	}
	/**
	 * easyUI datagrid的getChecked的扩展，支持返回选中的多行数据
	 */
	if ($.fn.datagrid) {
		$.extend($.fn.datagrid.methods, {
			getChecked : function(jq) {
				var rr = [];
				var rows = jq.datagrid('getRows');
				jq.datagrid('getPanel').find(
						'div.datagrid-cell-check input:checked').each(
						function() {
							var index = $(this).parents('tr:first').attr(
									'datagrid-row-index');
							rr.push(rows[index]);
						});
				return rr;
			}
		});
	}
	/**
	 * easyUI window的默认draggable属性设置为false，不允许拖拽
	 */
	if ($.fn.window) {
		$.fn.window.defaults.draggable = false;
	}
	if ($.fn.dialog) {
		$.fn.dialog.defaults.draggable = false;
	}
	//让window居中
	var easyuiPanelOnOpen  = function () {
	    var iframeWidth = $(this).parent().parent().width();
	    var iframeHeight = $(this).parent().parent().height();

	    var windowWidth = $(this).parent().width();
	    var windowHeight = $(this).parent().height();

	    var setWidth = (iframeWidth - windowWidth) / 2;
	    var setHeight = (iframeHeight - windowHeight) / 2;
	    $(this).parent().css({/* 修正面板位置 */
	        left: setWidth,
	        top: setHeight
	    });

	    if (iframeHeight < windowHeight)
	    {
	        $(this).parent().css({/* 修正面板位置 */
	            left: setWidth,
	            top: 0
	        });
	    }
	};
	$.fn.window.defaults.onOpen = easyuiPanelOnOpen;
	
	$.fn.window.defaults.onClose = function(){
		var winPosition = window.location.href;
		var winIds = ['popWin', 'popWin2', 'popWin3', 'popWin4'];
		if (winIds.contains(this.id) && winPosition && winPosition.indexOf('commonLogin/toMain') > 0) {
			$(this).children().remove();
		}
	};
})(jQuery);