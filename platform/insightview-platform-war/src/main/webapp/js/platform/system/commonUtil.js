/**
 * 产生随机数
 * @param number 是定数目以内产生随机数
 * @returns
 */
function rand(number) {
	rnd.today = new Date();
	rnd.seed = rnd.today.getTime();
	function rnd() {
		rnd.seed = (rnd.seed * 9301 + 49297) % 233280;
		return rnd.seed / (233280.0);
	}
	;
	var s = Math.ceil(rnd() * number);
	return s;
};


function updateFile(url, eleId, fileDir, callback, previousFileName) {
	var uploadfile = $("#" + eleId).val();
	if(uploadfile != '') {
		url = url + "?fileDir=" + fileDir + "";
		$.ajaxFileUpload({
			url : url,
			secureuri : false,
			fileElementId : eleId,
			dataType : 'json',
			success : function(data, status) {
				var dataJson = data.msg;
				dataJson = eval("(" + dataJson + ")");
				//var filepath = dataJson.filePath;
				var _fileName = dataJson.fileName;
				callback(_fileName);
			},
			error : function(data, status, e) {
				console.log(e);
			}
		});
	} else {
		callback($("#" + previousFileName).val());
	}
}

/**
 * 图片上传预览
 * 
 * @author wul
 * @param imgFile
 *            上传的图片
 */
var _fileType = "";
function PreviewImage(imgFile) {
	var filextension = imgFile.value.substring(imgFile.value.lastIndexOf("."),
			imgFile.value.length);
	_fileType = filextension;
	filextension = filextension.toLowerCase();
	if ((filextension != '.jpg') && (filextension != '.gif')
			&& (filextension != '.jpeg') && (filextension != '.png')
			&& (filextension != '.bmp')) {
		$.messager.alert("提示", "对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 !", "info");
		imgFile.focus();
	} else {
		var path;
		if (document.all)// IE
		{
			imgFile.select();
			path = document.selection.createRange().text;
			document.getElementById("imgPreview").innerHTML = "";
			document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
					+ path + "\")";// 使用滤镜效果
		} else// FF
		{
			path = window.URL.createObjectURL(imgFile.files[0]);// FF 7.0以上
			// path = imgFile.files[0].getAsDataURL();// FF 3.0
			document.getElementById("imgPreview").innerHTML = "<img id='img1' width='120px' height='100px' src='"
					+ path + "'/>";
			// document.getElementById("img1").src = path;
		}
	}
}

/**
 * 验证带小数点的数字
 * 
 * @author wul
 * @param num
 *            验证的数字
 */
function numberCheck(num, message) {
	var re = /^\d+(\.\d+)?$/;
	var chkInfo = re.exec(num) != null;
	if (!chkInfo) {
		$.messager.alert("提示", message, "error");
	}
	return chkInfo;
}


 /**
  * 验证输入是否为中文
  * 
  * @author zyp
  * @param wordStr
  *            输入的字符串
 
 
 function isChn(wordStr){
     var reg = /^[u4E00-u9FA5]+$/;
     if(reg.test(wordStr)){
    	 $.messager.alert("提示","只能输入汉字！！","error");
     }
     return true;
}
 
 /**
  * 验证输入是否只能为英文和数字
  * 
  * @author zyp
  * @param str
  *            输入的字符串
  */
  
/** function isEnAndNum(str){
	 var re;
     re = /[a-zA-Z0-9]{6,16}/;  
	 if (re.test(str)) {
		   return true;  //匹配
	 }else {
		 $.messager.alert("提示","只能输入英文或者数字！！","error");  //不匹配
	 }
	 
 }
 
 /**
  * 检查输入的起止日期是否正确，规则为两个日期的格式正确，且结束如期>=起始日期 
  * 
  * @author zyp
  * @param startDate：起始日期，字符串 
           endDate：结束如期，字符串  
  */
  
 
/** function checkTwoDate( startDate,endDate ) { 
	 if( !isDate(startDate) ) { 
		 $.messager.alert("提示","起始日期不正确!","error"); 
	 return false; 
	 } else if( !isDate(endDate) ) { 
		 $.messager.alert("提示","终止日期不正确!","error"); 
	 return false; 
	 } else if( startDate > endDate ) { 
		 $.messager.alert("提示","起始日期不能大于终止日期!","error"); 
	 return false; 
	 } 
	 return true; 
	 } */
 
 /** function checkValues(value){
	 var checkValue = value;
	 var email = $('email').val();
	 var phoneNum = $('phoneNum').val();
	 var wStr =  $('wStr').val();  
	 var emailRe =  /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	 var phoneNumRe = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/;
	 var wStrRe = /^[u4E00-u9FA5]+$/;
	 var nikeName = /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/; 
	 switch(checkValue){
	    case email:
	    	if(!emailRe.test(email)){
	    		$.messager.alert("提示信息","请输入正确的邮箱格式!","error");
	    		return;
	    	}break;
	    case phoneNum:
	    	if(!phoneNumRe.exec(phoneNum)){
	    		$.messager.alert("提示信息","请输入正确的手机号码!","error");
	    		return;
	    	}break;
	    case wStr:
	    	if(!wStrRe.test(wStr)){
	    		$.messager.alert("提示信息","只能输入汉字!","error");
	    		return;
	    	}break;
	 
	 }
	 
 }*/
 
 
 
/**
 * 公用验证表单方法
 * 
 * @author wul
 * @param checkControlAttr
 *            控件数组
 * @param checkControlAttr
 *            提示信息数组
 */
function checkFormCommon(checkControlAttr, checkMessagerAttr) {
	for (var i = 0; i < checkControlAttr.length; i++) {
		var ctlVal = $("#" + checkControlAttr[i]).val();
		ctlVal = ctlVal.Trim();
		if ('' == ctlVal || ctlVal.length <= 0) {
			$.messager.alert("提示", checkMessagerAttr[i], "info", function(e) {
				$("#" + checkControlAttr[i]).focus();
			});
			return false;
		}
	}
	return true;
}

/**
 * 重新加载表格
 * 
 * @author wul
 * @param dataGridId
 */
function reloadTableCommon(dataGridId) {
	$('#' + dataGridId).datagrid('reload');
	$('#' + dataGridId).datagrid('clearSelections');
}

/**
 * 去除用户选择的lable和img
 * 
 * @author wul
 * @param obj
 *            控件obj
 * @param prefixname
 *            控件id
 */
function removebnlables(obj, controlId) {
	// 去除控件
	$("[name='" + obj.name + "']").remove();
	_u_sel_auto_val = _u_sel_auto_val.replace(obj.name + ",", "");
}

/**
 * 模糊匹配单选效果
 * 
 * @author wul
 * @param uSelValTemp
 *            用户选择的数值
 * @param $input
 *            控件
 */
function autoEventClickCommonRadio(uSelValTemp, $input) {
	var uSelValArr = uSelValTemp.split(":");
	var uSelVal = uSelValArr[0];
	var uSelId = uSelValArr[1];
	$input.val(uSelVal);
	_u_sel_auto_val = uSelId;
}

/**
 * 模糊匹配多选效果
 * 
 * @author wul
 * @param uSelValTemp
 *            用户选择的数值
 * @param $input
 *            控件
 */
function autoEventClickCommonMultiple(uSelValTemp, controlId, $input) {
	$input.val('');
	var path = getRootPath();
	var uSelValArr = uSelValTemp.split(":");
	var uSelVal = uSelValArr[0];
	var uSelId = uSelValArr[1];
	if (_u_sel_auto_val.indexOf(uSelId) == -1) {
		var lbl_bn = $("<label name='" + uSelId + "'>  " + uSelVal
				+ "  </label>");

		var _imgclose = $("<img></img>");
		$(_imgclose).attr({
			"title" : uSelVal,
			"name" : uSelId,
			"src" : "" + path + "/style/images/close.gif"
		});

		$(_imgclose).click(function() {
			removebnlables(this);
		});
		$("#" + controlId).append(lbl_bn);
		$("#" + controlId).append(_imgclose);
		_u_sel_auto_val += uSelId + ",";
	}
}

/**
 * 模糊匹配点击事件
 * 
 * @author wul
 * @param uSelValTemp
 *            用户选择的数值
 * @param controlId
 *            控件ID
 */
function autoEventClickCommon(uSelValTemp, controlId) {
	var path = getRootPath();
	var uSelValArr = uSelValTemp.split(":");
	var uSelVal = uSelValArr[0];
	var uSelId = uSelValArr[1];
	if (_u_sel_auto_val.indexOf(uSelId) == -1) {
		var lbl_bn = $("<label name='" + uSelId + "'>  " + uSelVal
				+ "  </label>");

		var _imgclose = $("<img></img>");
		$(_imgclose).attr({
			"title" : uSelVal,
			"name" : uSelId,
			"src" : "" + path + "/style/images/close.gif"
		});

		$(_imgclose).click(function() {
			removebnlables(this);
		});
		$("#" + controlId).append(lbl_bn);
		$("#" + controlId).append(_imgclose);
		_u_sel_auto_val += uSelId + ",";
	}
}

function doEditChkbox(control) {
	var controlChk = $(control).is(':checked');
	var controlId = $(control).attr('id');
	var controlAlt = $(control).attr('alt');

	$("[alt=" + controlId + "]").click();
	$("[alt=" + controlId + "]").attr("checked", controlChk);
	if (controlChk) {
		$("#" + controlAlt + "").attr("checked", controlChk);
	} else {
		var controlStatus = false;
		$("[alt=" + controlAlt + "]").each(function(i, val) {
			var controlChkTemp = $(val).is(':checked');
			if (controlChkTemp) {
				controlStatus = true;
			}
		});
		$("#" + controlAlt + "").attr("checked", controlStatus);
	}
}

function createXMLHttpRequest() {
	if (window.XMLHttpRequest) {
		XMLHttpR = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		try {
			XMLHttpR = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				XMLHttpR = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
}
function sendRequest(url) {
	window.location.href = url;
}
function processResponse() {
	if (XMLHttpR.readyState == 4 && XMLHttpR.status == 200) {
		document.write(XMLHttpR.responseText);
	}
}
var _imageRoot = "/CBA_V2/images/";
var _IEVersion = "";
function cgds(val) {
	var s = val + "";
	if (10 != s.length) {
		if (null != val && "" != val) {
			var year = parseInt(val.year) + 1900;
			var month = (parseInt(val.month) + 1);
			month = month > 9 ? month : ("0" + month);
			var date = parseInt(val.date);
			date = date > 9 ? date : ("0" + date);
			var time = year + "-" + month + "-" + date;
			return time;
		}
	} else {
		return val;
	}
}

/**
 * 重置表单
 * 
 * @author wul
 */
function resetForm(pcId) {
	var iptLst = $("#" + pcId).find("input");
	$.each(iptLst, function(i, val) {
		$(val).val('');
		$(val).attr('alt', '');
	});

	var txtareaLst = $("#" + pcId).find("textarea");
	$.each(txtareaLst, function(i, val) {
		$(val).val('');
	});

	var selLst = $("#" + pcId).find("select");
	var optLst;
	$.each(selLst, function(i, val) {
		optLst = $(val).find('option');
		$.each(optLst, function(i, v) {
			if ($(v).attr('value') == -1) {
				$(v).attr("selected", "selected");
			} else {
				$(v).removeAttr("selected");
			}
		});
	});
	$('#' + pcId).find("select").prop('selectedIndex', 0);
}


 



/**
 * 判断IE版本
 * 
 * @author wul
 */
function chkIENumber() {
	var isIE = !!window.ActiveXObject;
	var isIE6 = isIE && !window.XMLHttpRequest;
	var isIE8 = isIE && !!document.documentMode;
	var isIE7 = isIE && !isIE6 && !isIE8;
	if (isIE) {
		if (isIE6) {
			_IEVersion = "IE6";
		} else {
			if (isIE7) {
				_IEVersion = "IE7";
			} else {
				if (isIE8) {
					_IEVersion = "IE8";
				}
			}
		}
	}
}

/**
 * 格式化CST日期的字串
 * 
 * @author maowei
 */
function formatCSTDate(strDate, format) {
	return formatDate(new Date(strDate), format);
}

/**
 * 格式化日期的字串 补零
 * 
 * @author maowei
 */
function formatCSTDate1(strDate, format) {
	return formatDate(new Date(new Date(strDate).toUTCString()), format);
}


/**
 * 格式化日期
 * 
 * @author maowei
 */
function formatDate(date, format) {

	var paddNum = function(num) {
		num += "";
		return num.replace(/^(\d)$/, "0$1");
	};

	// 指定格式字符
	var cfg = {
		yyyy : date.getFullYear(), // 年 : 4位
		yy : date.getFullYear().toString().substring(2), // 年 : 2位
		M : date.getMonth() + 1, // 月 : 如果1位的时候不补0
		MM : paddNum(date.getMonth() + 1), // 月 : 如果1位的时候补0
		d : date.getDate(), // 日 : 如果1位的时候不补0
		dd : paddNum(date.getDate()), // 日 : 如果1位的时候补0
		hh : paddNum(date.getHours() + 0), // 时
		mm : paddNum(date.getMinutes() + 0), // 分
		ss : paddNum(date.getSeconds() + 0)
	// 秒
	};

	format || (format = "yyyy-MM-dd hh:mm:ss");

	return format.replace(/([a-z])(\1)*/ig, function(m) {
		return cfg[m];
	});
}

function formatDateText(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var h = date.getHours();
	var M = date.getMinutes();
	var s = date.getSeconds();
	return y + "-" + m + "-" + d + " " + h + ":" + M + ":" + s;
}

/**
 * 构建生成select
 * 
 * @author wul
 * @param obj
 * @param prefixname
 */
function buildselect(selectid, text, value) {
	var id = "#" + selectid;
	$(id)[0].options.add(new Option(text, value));
}
/**
 * 判断一个字符是否存在一个数组里面
 * 
 * @author wul
 * @param arrStrTemp
 *            数组参数
 */
String.prototype.wlIn = function(arrStrTemp) {
	var arr = arrStrTemp.split(",");
	var strTemp = this + "";
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] == strTemp) {
			return true;
		}
	}
	return false;
};
/**
 * 去空格 L,R,ALL
 * 
 * @author wul
 * @param obj
 */
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.LTrim = function() {
	return this.replace(/(^\s*)/g, "");
};
String.prototype.RTrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * 将数据填充到控件
 * 
 * @author wul
 * @param obj
 *            集合
 * @param prefixname
 *            判断类型
 */
function iterObj(obj, prefixname) {

	var patrnheadertime = /^.+(_time)$/;
	var patrnheader = /^.+(Time)$/;
	var patrnheaderdate = /^.+(_date)$/;
	for ( var key in obj) {
		var compentVal = obj[key];
		if ("object" != typeof (compentVal)) {
			if("string" == typeof(compentVal))
				compentVal=compentVal.replace(new RegExp('00:00:00','gm'),"");
			if ("lbl" == prefixname) {
				$("#" + prefixname + "_" + key + "").html(compentVal);
			}
			if ("lbl2" == prefixname) {
				$("#" + prefixname + "_" + key + "").html(compentVal);
			}
			if ("txt" == prefixname || "ipt" == prefixname) {
			}
			if($("#" + prefixname + "_" + key + "").type="input"){
				$("#" + prefixname + "_" + key + "").val(compentVal);
			}
			$("#" + prefixname + "_" + key + "").html(compentVal);
		}
		if ("object" == typeof (compentVal)) {
			var tt = "";
			if ((patrnheadertime.test(key + "") || patrnheader.test(key + "") || patrnheaderdate
					.test(key + ""))
					&& null != compentVal) {
				compentVal = formatetime(compentVal, 0);
				if ("lbl" == prefixname) {
					$("#" + prefixname + "_" + key + "").html(compentVal);
				}
				if ("lbl2" == prefixname) {
					$("#" + prefixname + "_" + key + "").html(compentVal);
				}
				if ("txt" == prefixname || "ipt" == prefixname) {
					$("#" + prefixname + "_" + key + "").val(compentVal);
				}
			} else {
				for ( var key2 in compentVal) {
					tt = compentVal[key2];
					if ("lbl" == prefixname) {
						$("#" + prefixname + "_" + key + "_" + key2 + "").html(
								tt);
					}
					if ("txt" == prefixname || "ipt" == prefixname) {
						$("#" + prefixname + "_" + key + "_" + key2 + "").val(
								tt);
					}
				}
			}
		}
	}
}

/**
 * 执行aAjax统一方法
 * 
 * @author wul
 * @param ajax_params
 *            ajax参数格式
 */
var ajax_sync = 0;
function ajax_(ajax_params) {
	if (ajax_sync == 0) {
		ajax_sync = 1;
		$.ajax({
			type : ajax_params.type,
			dataType : ajax_params.dataType,
			ifModified : true,
			url : ajax_params.url,
			data : ajax_params.data,
			error : function() {
				if (ajax_sync != 0) {
					ajax_sync = 0;
				}
				ajax_params.error;
			},
			success : function(data) {
				if (ajax_sync != 0) {
					ajax_sync = 0;
				}
				ajax_params.success(data);
			}
		});
	}
}
/**
 * 取得项目名称
 * 
 * @author wul
 */
function getRootName() {
	// 取得当前URL
	var path = window.document.location.href;
	// 取得主机地址后的目录
	var pathName = window.document.location.pathname;
	var post = path.indexOf(pathName);
	// 取得主机地址
	var hostPath = path.substring(0, post);
	// 取得项目名
	var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return name;
}
/**
 * 取得项目路径
 * 
 * @author wul
 */
function getRootPath() {
	// 取得当前URL
	var path = window.document.location.href;
	// 取得主机地址后的目录
	var pathName = window.document.location.pathname;
	var post = path.indexOf(pathName);
	// 取得主机地址
	var hostPath = path.substring(0, post);
	// 取得项目名
	var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return hostPath + name;
}
String.prototype.wlInArr = function(arr) {
	var strTemp = this + "";
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] == strTemp) {
			return true;
		}
	}
	return false;
};

Array.prototype.remove = function(strTemp) {
	var arr = this;
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] == strTemp) {
			arr.splice(i, 1);
		}
	}
}
function formatDateCommon(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + "-" + m + "-" + d;
}
/**
 * 判断字符的长度 返回true str 检验的字符串 count 最大长度
 */
function widthCheck(str, count) {
	var w = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			w++;
		} else {
			w += 2;
		}
	}
	if (w > count) {
		return false;
	}
	return true;
}

// 拦截特殊字符
function CheckCode(t) {
	if (/[\':;*?~`!【】￥@#$%^&+={}\[\]\<\>\(\),\.]/.test(t)) {
		return true;// 含有特殊字符!
	}
	return false;// 没含有特殊字符!
}
function openwindow(url, name, iWidth, iHeight) {
	var url; // 转向网页的地址;
	var name; // 网页名称，可为空;
	var iWidth; // 弹出窗口的宽度;
	var iHeight; // 弹出窗口的高度;
	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
	window
			.open(
					url,
					name,
					'height='
							+ iHeight
							+ ',,innerHeight='
							+ iHeight
							+ ',width='
							+ iWidth
							+ ',innerWidth='
							+ iWidth
							+ ',top='
							+ iTop
							+ ',left='
							+ iLeft
							+ ',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
}

function validValue(email, phoneNum, Num, enAndNum, cnStr, strLegth, inputValue) {
	// alert(Num);
	var emailRe = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var phoneRe = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/;
	var NumRe = /^\d+$/;
	var enAndNumRe = /[a-zA-Z0-9]{6,16}/;
	var cnStrRe = /^[u4E00-u9FA5]+$/;
	var strLegthRe = /^[\w\W]{6,16}$/;
	switch (inputValue) {
	case email:
		if (email == "") {
			$.messager.alert("提示信息", "邮箱地址不能为空！", "error");
		} else if (!emailRe.test(email) && email != "") {
			$.messager.alert("提示信息", "请输入正确邮箱！", "error");
			return;
		}
		break;

	case phoneNum:
		if (phoneNum == "") {
			$.messager.alert("提示信息", "手机号码不能为空！", "error");
		} else if (!phoneRe.test(phoneNum) && phoneNum != "") {
			// alert(re.test($('#phoneNum').val());
			$.messager.alert("提示信息", "请输入正确的手机号码！", "error");
			return;
		}
		break;

	case Num:

		if (Num == "") {
			$.messager.alert("提示信息", "输入不能为空！", "error");
		} else if (!NumRe.test(Num) && Num != "") {
			$.messager.alert("提示信息", "只能英文和数字！", "error");
			return;
		}
		break;

	case enAndNum:

		if (enAndNum == "") {
			$.messager.alert("提示信息", "文字输入不能为空！", "error");
		} else if (!enAndNumRe.test(enAndNum) && enAndNum != "") {
			$.messager.alert("提示信息", "只能数字！", "error");
			return;
		}
		break;

	case cnStr:

		if (cnStr == "") {
			$.messager.alert("提示信息", "文字输入不能为空！", "error");
		} else if (!cnStrRe.test(cnStr) && cnStr != "") {
			$.messager.alert("提示信息", "只能数字！", "error");
			return;
		}
		break;

	case strLegth:

		if (strLegth == "") {
			$.messager.alert("提示信息", "文字输入不能为空！", "error");
		} else if (!strLegthRe.test(strLegth) && strLegth != "") {
			$.messager.alert("提示信息", "只能数字！", "error");
			return;
		}
		break;
	}
}




/**
 * 居中弹出窗口
 * 
 * @author Maowei
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 * @param iscrollbars
 * @param iresizable
 */
function openWindowInC(url, name, iWidth, iHeight, iscrollbars, iresizable) {
    var url; //转向网页的地址;
    var name; //网页名称，可为空;
    var iWidth; //弹出窗口的宽度;
    var iHeight; //弹出窗口的高度;
    var iscrollbars; //弹出窗口的是否有滚动条(auto/yes/no);
    var iresizable; //弹出窗口是否可调整大小(yes/no);
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=' + iscrollbars + ',resizable=' + iresizable + ',location=no,status=no');
}

/**
 * 居中弹出窗口
 * 
 * @author Maowei
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 * @param iscrollbars
 * @param iresizable
 */
function openWindowInC(url, name, iWidth, iHeight, iscrollbars, iresizable) {
    var url; //转向网页的地址;
    var name; //网页名称，可为空;
    var iWidth; //弹出窗口的宽度;
    var iHeight; //弹出窗口的高度;
    var iscrollbars; //弹出窗口的是否有滚动条(auto/yes/no);
    var iresizable; //弹出窗口是否可调整大小(yes/no);
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=' + iscrollbars + ',resizable=' + iresizable + ',location=no,status=no');
}

/**
 * 居中弹出窗口（从父页面）
 * 
 * @author Maowei
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 * @param iscrollbars
 * @param iresizable
 */
function openWindowFromFather(url, name, iWidth, iHeight, iscrollbars, iresizable) {
	var url; // 转向网页的地址;
	var name; // 网页名称，可为空;
	var iWidth; // 弹出窗口的宽度;
	var iHeight; // 弹出窗口的高度;
	var iscrollbars; // 弹出窗口的是否有滚动条(auto/yes/no);
	var iresizable; // 弹出窗口是否可调整大小(yes/no);
	var iTop = (window.opener.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.opener.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
	window.opener.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight
			+ ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop
			+ ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars='
			+ iscrollbars + ',resizable=' + iresizable
			+ ',location=no,status=no');
}

function jsonToString (obj){   
		
        var THIS = this;      
        switch(typeof(obj)){  
            case 'string':     
                return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';     
            case 'array':     
                return '[' + obj.map(THIS.jsonToString).join(',') + ']'; 
			case 'boolean':
				return obj;
            case 'object':   
                 if(obj instanceof Array){
                    var strArr = []; 
                    var len = obj.length;
                    for(var i=0; i<len; i++){
                        strArr.push(THIS.jsonToString(obj[i]));
                    }     
                    return '[' + strArr.join(',') + ']';
                }else if(obj==null){
                    return 'null';     
    
                }else{ 
                    var string = [];     
                    for (var property in obj) {
						
						string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));     
					}
                    return '{' + string.join(',') + '}';     
                }     
            case 'number':     
                return obj;
            case false:     
                return obj;
        }     
   }  

function jsonToStringToLowerCase(obj){     
        var THIS = this;      
        switch(typeof(obj)){  
            case 'string':     
                return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';     
            case 'array':     
                return '[' + obj.map(THIS.jsonToString).join(',') + ']';     
            case 'object':   
                 if(obj instanceof Array){
                    var strArr = []; 
                    var len = obj.length;
                    for(var i=0; i<len; i++){
                        strArr.push(THIS.jsonToString(obj[i]));
                    }     
                    return '[' + strArr.join(',') + ']';
                }else if(obj==null){
                    return 'null';     
    
                }else{ 
                    var string = [];     
                    for (var property in obj) string.push(THIS.jsonToString(property).toLowerCase() + ':' + THIS.jsonToString(obj[property]));     
                    return '{' + string.join(',') + '}';     
                }     
            case 'number':     
                return obj;     
            case false:     
                return obj;     
        }     
   } 

function formToJsonOld(form){
	var str = $('#'+form).serialize();
	str = str.replace(/\+/g," ");
	str = decodeURIComponent(str);
	str = str.replace(/&/g,"','");
	str = str.replace(/=/g,"':'");
	str = "({'"+str +"'})";
	str = str.replace(/\r/g, '\\r').replace(/\n/g, '\\n'); 
    obj = eval(str); 
	return obj;
}

function formTojson(str)
{
	var strs="#"+str;
    var obj=new Object();  
    /*$.each($(strs).serializeArray(), function() {  
    	var namelow = 	this.name.toLowerCase();
        if (obj[namelow]) {  
            if (!obj[namelow].push) {  
            	obj[namelow] = [obj[namelow]];  
            }  
            obj[namelow].push(this.value || '');  
        } else {  
        	obj[namelow] = this.value || '';  
        }  
    });*/
   //alert(jsonToString(obj));
    $.each($(strs).serializeArray(),function(index,param){
    	var paramlow = 	param.name.toLowerCase();
    	
    	//IE10在input框使用“X”按钮时，隐藏的输入项的值没有清除掉的情况
//    	var pre = document.getElementsByName(param.name)[0].previousSibling;
//    	if(pre && pre.type == "text" && pre.value != document.getElementsByName(param.name)[0].value) {
//    		document.getElementsByName(param.name)[0].value = pre.value;
//    	}
    	
        if(!(paramlow in obj)){
	        //日期 正则
	        var reg=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/; 
	        if(param.value.match(reg)){//是 yyyy-MM-dd格式的 则变成 yyyy-MM-dd 00:00:00
	        	param.value = param.value+" 00:00:00";
	        }
	        
	        obj[paramlow]=param.value;  
        }else{
        	 obj[paramlow]= obj[paramlow] + "," +param.value;  
        }
    });  
    return obj;  
};

function formTojsonNotToLowerCase(str)
{
	var strs="#"+str;
    var obj=new Object();  
    /*$.each($(strs).serializeArray(), function() {  
    	var namelow = 	this.name.toLowerCase();
        if (obj[namelow]) {  
            if (!obj[namelow].push) {  
            	obj[namelow] = [obj[namelow]];  
            }  
            obj[namelow].push(this.value || '');  
        } else {  
        	obj[namelow] = this.value || '';  
        }  
    });*/
   //alert(jsonToString(obj));
    $.each($(strs).serializeArray(),function(index,param){
    	var paramlow = 	param.name;
    	
    	if(paramlow.indexOf("_orgTreeName") != -1){
    		return;
    	}
    	
    	if(paramlow.indexOf("_orgTreeId") != -1){
    		paramlow = paramlow.substring(0,paramlow.length-"_orgTreeId".length);
    	}
    	
    	//IE10在input框使用“X”按钮时，隐藏的输入项的值没有清除掉的情况
//    	var pre = document.getElementsByName(param.name)[0].previousSibling;
//    	if(pre && pre.type == "text" && pre.value != document.getElementsByName(param.name)[0].value) {
//    		document.getElementsByName(param.name)[0].value = pre.value;
//    	}
    	
        if(!(paramlow in obj)){
	        //日期 正则
	        var reg=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/; 
	        if(param.value.match(reg)){//是 yyyy-MM-dd格式的 则变成 yyyy-MM-dd 00:00:00
	        	param.value = param.value+" 00:00:00";
	        }
	        
	        obj[paramlow]=param.value;  
        }else{
        	 obj[paramlow]= obj[paramlow] + "," +param.value;  
        }
    });  
    
    return obj;  
};


function convertFormToJson(str){
    var obj=new Object();  
    $.each($("#"+str).serializeArray(),function(index,param){
        if(!(param.name in obj)){
	        obj[param.name]=param.value;
        }
    });  
    return obj;  
};

function strToObj(json){
    return eval("("+json+")"); 
}

/**
 * 关闭窗口
 */
function closeWindow(windowId){
	$('#'+windowId).window('close');
}

/**
 * 清空表单
 */
function resetForm(formId){
	$("#"+formId).form('clear');
}

/**
 * 提示信息
 */
function alertMsg(message, title, func) {
	if (!title || title.trim() == '') {
		title = '提示';
	}
	alertFb(message, title, 'info', func);
}
function alertMsgError(message, title, func) {
	if (!title || title.trim() == '') {
		title = '错误';
	}
	alertFb(message, title, 'error', func);
}
function alertFb(message, title, type, func) {
	window.top.$.messager.alert(title, message, type, func);
}

/**
 * 确认信息
 */
function alertConfirm(title,message,func){
	//$.messager.alert(title, messager,type);
	window.top.$.messager.confirm(title, message,func);
}
/**
 * 字符串转JSON
*/
function strToJson(str){ 
	var json = (new Function("return " + str))(); 
	return json; 
}

//填充form的input值，在页面初始化完成以后
function fillFormAfter(formId,jsonData) {
	//取得所有的easyui-combobox控件
	$("#" + formId + " .fb-combobox").each(function () {
		if($(this).attr('childId')!='' && $(this).attr('childId')!=undefined){
			var name = $(this).attr('comboname');
			var valObj = jsonData[name];
			if(typeof(valObj) == 'object'){
				 $(this).combobox('setValues',valObj);
			}else{
				if(valObj!=''){
					 $(this).combobox('setValue',jsonData[name]);
				}
			}
			delete jsonData[name];
		}
	});
	$("#" + formId).form('load',jsonData);
}

//填充form的input值，页面初始化期间
function fillFormBefore(formId,jsonData) {
	//取得所有的easyui-combobox控件
	$("#" + formId + " .fb-combobox").each(function () {
		if($(this).attr('childId')!='' && $(this).attr('childId')!=undefined){
			var name = $(this).attr('name');
			var valObj = jsonData[name];
			if(typeof(valObj) == 'object'){
				//$(this).combobox('setValues',valObj);
			}else{
				if(valObj!=''){
					$(this)[0].value = valObj;
				}
			}
			delete jsonData[name];
		}
	});
	$("#" + formId).form('load',jsonData);
}
//禁用form表单中所有的input[文本框、复选框、单选框],select[下拉选],多行文本框[textarea]
function disableForm(formId,isDisabled) {
  var attr="disable";
	if(!isDisabled){
	   attr="enable";
	}
	$("form[id='"+formId+"'] :text").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] textarea").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] select").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] :radio").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled);
	//禁用jquery easyui中的下拉选（使用input生成的combox）
	$("#" + formId + " .easyui-combobox").each(function () {
		$(this).combobox(attr);
	});
	
	//禁用jquery easyui中的日期组件dataBox
	$("#" + formId + " .datebox").each(function () {
		$(this).datebox(attr);
	});
}

/**
 * 禁用退格键在readonly或者disable属性的input控件上
*/
function disableBackSpace(){
	$("input[readonly]").keydown(function(e) {
		if(e.keyCode==8){
			 e.preventDefault();
		}
	});
}

/*
 *求2个日期相差的天数 
 * */
function  DateDiff(DateOne,  DateTwo){    //sDate1和sDate2是2006-12-18格式    
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return Math.abs(cha); 
}

/*
 * 获取当前日期 格式yyyy-mm-dd
 * */

function getNowDate(){
	var now=new Date()
	y=now.getFullYear()
	m=now.getMonth()+1
	d=now.getDate()
	m=m<10?"0"+m:m
	d=d<10?"0"+d:d
	return  y+"-"+m+"-"+d ;
}




/*
 * 2个日期的大小比较
 * */
function duibi(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes >= lktimes) {
        return true;
    }
    else
        return false;

}

function loadDatagrid(iframeId,datagridId){
	parent.window.frames[iframeId].contentWindow.$('#'+datagridId).datagrid('load');
}

function convertComplexObjectToSimpleObject(data){
	for(var key in data){
		var obj=data[key];
		switch(typeof(obj)){  
			case 'object':   
			if(obj instanceof Array){
				for(var i=0;i<obj.length;i++){
					if(obj[i] instanceof Object){
						for(var objKey in obj[i]){
							data[key+"["+i+"]."+objKey]=obj[i][objKey];
						}
					}else{
							data[key+"["+i+"]"]=obj[i];
					}
				}
				delete data[key];
				}else if(obj!=null){
					for(var objKey in obj){	  
						data[key+"."+objKey]=obj[objKey];
					}
					delete data[key];
				}     
		}     
	}
	return data;
}

function rtrim_dwdm(dwdm){
	while(dwdm.substring(dwdm.length-1,dwdm.length)=="0"){
		dwdm=dwdm.substring(0,dwdm.length-1);
	}
	return dwdm;
}

if(typeof $ != "undefined" && typeof $.fn != "undefined"){
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a,
			function() {
				if(o[this.name]) {
					if(!o[this.name].push) {
						o[this.name] = [o[this.name]];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
		return o;
	};
}
  
  /**
   * obj是否为空对象，即没有任何属性
   * @param obj
   * @returns {Boolean}
   */
  function isEmpty(obj) {
	// null and undefined are "empty"
    if(obj == null) return true;
    
    // Assume if it has a length property with a non-zero value
    // that that property is correct.
    if(obj.length > 0)    return false;
    if(obj.length === 0)  return true;
    
    // Otherwise, does it have any properties of its own?
    // Note that this doesn't handle
    // toString and valueOf enumeration bugs in IE < 9
    for(var key in obj) {
      if(hasOwnProperty.call(obj, key)) return false;
    }
    
    return true;
	//return Object.keys(obj).length === 0;
  }
  
  /**
   * 将一般的JSON格式转为EasyUI Tree树控件的JSON格式
   * @param rows:json数据对象
   * @param idFieldName:表id的字段名
   * @param pidFieldName:表父级id的字段名
   * @param textFieldName:节点文本显示字段名
   * @param fileds:要显示的字段,多个字段用逗号分隔
   */
   function ConvertToTreeJson(rows, idFieldName, pidFieldName, textFieldName, fileds) {
	   if(rows == null || isEmpty(rows)) {
		   return {};
	   }
	   
       function exists(rows, ParentId) {
           for(var i = 0; i < rows.length; i++) {
               if(rows[i][idFieldName] == ParentId)
                   return true;
           }
           return false;
       }
       
       var nodes = [];
       //get the top level nodes
       for(var i = 0; i < rows.length; i++) {
           var row = rows[i];
           if(!exists(rows, row[pidFieldName])) {
               var data = {
                   id: row[idFieldName]
               }
               var arrFiled = fileds.split(",");
               for(var j = 0; j < arrFiled.length; j++)
               {
                   if(arrFiled[j] != idFieldName)
                       data[arrFiled[j]] = row[arrFiled[j]];
               }
               data["text"] = row[textFieldName];
               //data["checked"] = true;
               nodes.push(data);
           }
       }

       var toDo = [];
       for(var i = 0; i < nodes.length; i++) {
           toDo.push(nodes[i]);
       }
       while(toDo.length) {
           var node = toDo.shift(); //the parent node
           //get the children nodes
           for(var i = 0; i < rows.length; i++) {
               var row = rows[i];
               if(row[pidFieldName] == node.id) {
                   var child = {
                       id: row[idFieldName]
                   };
                   var arrFiled = fileds.split(",");
                   for(var j = 0; j < arrFiled.length; j++) {
                       if(arrFiled[j] != idFieldName) {
                           child[arrFiled[j]] = row[arrFiled[j]];
                       }
                   }
                   child["text"] = row[textFieldName];
                   //child["checked"] = true;
                   if(node.children) {
                       node.children.push(child);
                   } else {
                       node.children = [child];
                   }
                   toDo.push(child);
               }
           }
       }
       return nodes;
   };
  
/**
 * 将一般的JSON格式转为EasyUI TreeGrid树控件的JSON格式
 * @param rows:json数据对象
 * @param idFieldName:表id的字段名
 * @param pidFieldName:表父级id的字段名
 * @param fileds:要显示的字段,多个字段用逗号分隔
 */
 function ConvertToTreeGridJson(rows, idFieldName, pidFieldName, fileds) {
	 if(rows == null || isEmpty(rows)) {
		 return {};
	 }
	 
     function exists(rows, ParentId) {
         for(var i = 0; i < rows.length; i++) {
             if(rows[i][idFieldName] == ParentId)
                 return true;
         }
         return false;
     }
     var nodes = [];
     //get the top level nodes
     for(var i = 0; i < rows.length; i++) {
         var row = rows[i];
         if(!exists(rows, row[pidFieldName])) {
             var data = {
                 id: row[idFieldName]
             }
             var arrFiled = fileds.split(",");
             for (var j = 0; j < arrFiled.length; j++)
             {
                 if (arrFiled[j] != idFieldName)
                     data[arrFiled[j]] = row[arrFiled[j]];
             }
             nodes.push(data);
         }
     }

     var toDo = [];
     for (var i = 0; i < nodes.length; i++) {
         toDo.push(nodes[i]);
     }
     while (toDo.length) {
         var node = toDo.shift(); //the parent node
         //get the children nodes
         for (var i = 0; i < rows.length; i++) {
             var row = rows[i];
             if (row[pidFieldName] == node.id) {
                 var child = {
                     id: row[idFieldName]
                 };
                 var arrFiled = fileds.split(",");
                 for (var j = 0; j < arrFiled.length; j++) {
                     if (arrFiled[j] != idFieldName) {
                         child[arrFiled[j]] = row[arrFiled[j]];
                     }
                 }
                 if (node.children) {
                     node.children.push(child);
                 } else {
                     node.children = [child];
                 }
                 toDo.push(child);
             }
         }
     }
     return nodes;
 };

var popupWindowIds = {};
var jsonObjs = {}; //存放所有页面的json对象
var winObjs = {}; //存放所有页面的Window对象

if(typeof(window.top.popupWindowIds) == "undefined") {
  window.top.popupWindowIds = {};
}

if(typeof(window.top.jsonObjs) == "undefined") {
	  window.top.jsonObjs = {};
}

if(typeof(window.top.winObjs) == "undefined") {
	  window.top.winObjs = {};
}
/**
 * 打开窗口,参数width和height不传递时,窗口最大化
 * @param url 页面URL
 * @param title 窗口标题
 * @param width 窗口宽度
 * @param height 窗口高度
 * @param scrolling 是否显示滚动条
 */
function OpenWin(url, title, width, height, scrolling) {
  //补全请求地址
  if(url.indexOf("http") != 0) {
	url = window.location.protocol + "//" + window.location.host + url;
  }
  //解决一个页面被多个父页面打开导致传递参数失效的问题
  if(url.indexOf("?") != -1) {
	url = url + "&" + getPageId();
  } else {
	url = url + "?" + getPageId();
  }
  
  if(!scrolling) {
    scrolling = 'no';
  }
  var content = "<iframe src='" + url + "' style='width:100%;height:100%' scrolling='" + scrolling + "' frameborder='0'></iframe>";
  
  //获取div_info对象
  var divId = encodePage(url);
  var $div_info = window.top.$("#div_info_" + divId);
  if($div_info.length == 0) {
	$div_info = window.top.$('<div id="div_info_' + divId + '"></div>').appendTo(window.top.$("body"));
  }
  
  if(width && height) {
    $div_info.dialog({
      title: title,
      width: width,
      height: height,
      modal: true,
      closed: true,
      draggable: true,
      content: content
    });
  } else {
    $div_info.dialog({
      title: title,
      maximized: true,
      modal: true,
      closed: true,
      draggable: true,
      content: content
    });
  }
  $div_info.css("display", "block");
  $div_info.window('open');
  
  setPage(url, window.document.location.href);
}

/**
 * 关闭Default页面弹出窗口
 * @param isFresh 是否刷新页面
 */
function closeModalWindow(isFresh) {
  //获取div_info对象
  var divId = getPageId();
  var $div_info = window.top.$("#div_info_" + divId);
  $div_info.window('close', true);
  
  //刷新页面(主页面需提供fresh方法)
  if(isFresh) {
	window.top.winObjs[getMainPageId()].fresh();
  }
}

/**
 * 设置页面信息
 * @param pageUrl 页面URL
 * @param prePageUrl 前页URL
 */
function setPage(pageUrl, prePageUrl) {
  var pageId = encodePage(pageUrl);
  var prePageId = encodePage(prePageUrl);
  
  var page = window.top.popupWindowIds[pageId];
  if(!page) {
	page = {"pageId": pageId, "prePage": null};
	window.top.popupWindowIds[pageId] = page;
  }
  
  if(!window.top.popupWindowIds[prePageId]) {
	var prePage = {"pageId": prePageId, "prePage": null};
	window.top.popupWindowIds[prePageId] = prePage;
  }
  page.prePage = window.top.popupWindowIds[prePageId];
  
  if(!window.top.winObjs) {
    window.top.winObjs = {};
  }
  window.top.winObjs[getPageId()] = window;
}

/**
 * 获取当前页面的标识ID
 * @author fangang
 */
function getPageId() {
  return encodePage(window.document.location.href);
}

/**
 * 对页面地址进行编码
 * @param url
 */
function encodePage(url) {
  //页面使用href="#"时,url的最后会添加#
  while(url.endsWith("#")) {
    url = url.substring(0, url.length - 1);
  }
  
  return hex_md5(url);
}

/* Here for node 10.x support */
if(!String.prototype.endsWith) {
  String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
  };
}

/**
 * 获取window.top下面第一级页面的标识ID(系统框架右下角页面或称主页面)
 * @author fangang
 */
function getMainPageId() {
  var pageId = getPageId();
  var pageObj = window.top.popupWindowIds[pageId];
  if(!pageObj) {
    var page = {"pageId": pageId, "prePage": null};
    window.top.popupWindowIds[pageId] = page;
    return pageId;
  }
  
  var mainPageId = pageObj.pageId;
  while(pageObj.prePage) {
	mainPageId = pageObj.prePage.pageId;
	pageObj = pageObj.prePage;
  }
  return mainPageId;
}

/**
 * 设置Json对象
 * @param key
 * @param obj
 */
function setJsonObj(key, obj) {
  var mainPageId = getMainPageId(); //主页面ID
  if(!window.top.jsonObjs[mainPageId]) {
	window.top.jsonObjs[mainPageId] = {};
  }
  window.top.jsonObjs[mainPageId][key] = obj;
}

/**
 * 获取Json对象
 * @param key
 */
function getJsonObj(key) {
  var mainPageId = getMainPageId(); //主页面ID
  if(window.top.jsonObjs[mainPageId]) {
    return window.top.jsonObjs[mainPageId][key];
  } else {
	return null;
  }
}

/**
 * 获取父页面对象(当前页面的上一级页面对象,即打开当前页面的那一个页面对象)
 */
function getParentWindow() {
  var pageObj =	window.top.popupWindowIds[getPageId()];
  if(pageObj && pageObj.prePage) {
	return window.top.winObjs[pageObj.prePage.pageId];
  }
  return null;
}

/**
 * 获取window.top下面第一级页面对象(主页面对象)
 */
function getMainWindow() {
  return window.top.winObjs[getMainPageId()];
}

/**
 * 获取地址栏里面的参数
 */
function getParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 改变数组中某元素位置
 * @param arr 数组
 * @param pos
 * @param toPos
 * @returns
 */
function changeArrayItemPos(arr, pos, toPos){
  //目标索引溢出修复下
  toPos = Math.min(Math.max(0, toPos), arr.length - 1);
  
  //待换索引溢出或与目标索引相同，则不做处理
  if(pos === toPos || pos < 0 || pos > arr.length - 1){ 
    return [].concat(arr);
  }
  
  var _arr = [], after = pos > toPos;
  for(var i = 0, len = arr.length; i < len; i++) {
    //待换索引直接pass
    if(i === pos) {
      continue;
    } else {
      if(i === toPos) {
        //目标索引与待换索引前后位置有关系
        if(after) {
          _arr.push(arr[pos]);
          _arr.push(arr[i]);
        } else {
          _arr.push(arr[i]);
          _arr.push(arr[pos]);
        }   
      } else {
        _arr.push(arr[i]);
      }
    }
  }
  return _arr;
}

/**
 * 显示错误信息
 * @param result 错误信息
 * @param title 标题
 */
function show_error(result, title) {
  if(result.responseJSON) {
	window.top.$.messager.alert(title ? title : "错误信息", result.responseJSON.errorCode + ": " + result.responseJSON.message, "error");
  } else if(result.responseText){
	  var responseJSON = eval('(' + result.responseText+ ')'); 
	  window.top.$.messager.alert(title ? title : "错误信息", responseJSON.errorCode + ": " + responseJSON.message, "error");
  } else {
	window.top.$.messager.alert(title ? title : "错误信息", "未知错误信息!");
  }
}

/**
 * 显示json对象信息
 * @param obj json对象
 */
function show_json(obj) {
  var $div_json = window.top.$("#pre_json_result");
  if($div_json.length == 0) {
	$div_json = window.top.$('<pre id="pre_json_result" style="display:block;outline:1px solid #ccc;padding:3px;margin:5px;overflow:auto">').appendTo(window.top.$("body"));
  }
  
  $div_json.dialog({
    title: "JSon对象查看",
    width: 600,
    height: 450,
    modal: true,
    closed: true,
    resizable: true,
    draggable: true,
    content: syntaxHighlight(obj)
  });
  $div_json.css("display", "block");
  $div_json.window('open');
}

/**
 * json格式化
 * @param json
 * @returns
 */
function syntaxHighlight(json) {
  if(typeof json != 'string') {
    json = JSON.stringify(json, null, 2);
  }
  
  json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
  return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
    var cls = 'number';
    if(/^"/.test(match)) {
      if(/:$/.test(match)) {
        cls = 'key';
      } else {
        cls = 'string';
      }
    } else if(/true|false/.test(match)) {
      cls = 'boolean';
    } else if(/null/.test(match)) {
      cls = 'null';
    }
    return '<span class="' + cls + '">' + match + '</span>';
  });
}

try{
	$(document).ready(disableBackSpace);
} catch(e){
}

/** 获取浏览器类型（不考虑版本）*/
function getBrowserType(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera";
    }
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } 
    if (userAgent.indexOf("Chrome") > -1){
    	return "Chrome";
	}
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } 
}

/**
 * 判断是否IE浏览器
 */
function isIEBrowser(){
	return getBrowserType() == "IE";
}