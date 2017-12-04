<%@ page language="java" pageEncoding="UTF-8"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>real-time alarm</title>
		<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/style/css/base.css" />
	   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/alarm.css" />	 
	   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmPanel/ht.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmPanel/ajax-pushlet-client.js"></script>
		<script type="text/javascript">
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

		var levelCountMap = {
			1 : {
				name : 'emergency'
			},
			2 : {
				name : 'severity'
			},
			3 : {
				name : 'ordinary'
			},
			4 : {
				name : 'prompt'
			},
			5 : {
				name : 'uncertain'
			},
		};

		function initd() {
			var cfgID = ${viewCfgID};
			var levelMap = {
				1 : {
					name : '紧急',
					color : '#ff0000'
				},
				2 : {
					name : '严重',
					color : '#ff9900'
				},
				3 : {
					name : '一般',
					color : '#ffff00'
				},
				4 : {
					name : '提示',
					color : '#0000ff'
				},
				5 : {
					name : '未确定',
					color : '#c0c0c0'
				}
			};

			// 添加工具条
			var toolbar = new ht.widget.Toolbar([ {
				element : document.getElementById('emergency')
			// 紧急
			}, {
				element : document.getElementById('severity')
			// 严重
			}, {
				element : document.getElementById('ordinary')
			// 一般
			}, {
				element : document.getElementById('prompt')
			// 提示
			}, {
				element : document.getElementById('uncertain')
			// 未确定
			}, {
				element : document.getElementById('look')
			// 查看
			}, {
				element : document.getElementById('confirm')
			// 确认
			}, {
				element : document.getElementById('distributed')
			// 手动派发
			}, {
				element : document.getElementById('bClear')
			// 清除
			}, {
				element : document.getElementById('delete')
			// 删除
			}, {
				element : document.getElementById('addView')
			// 自定义视图
			}, {
				element : document.getElementById('cView')
			// 自定义视图
			}, {
				element : document.getElementById('sel')
			// 选择
			}, {
				element : document.getElementById('mView')
			// 我的视图
			} ]);

			toolbar.setStickToRight(true);

			// 创建布局对象
			borderPane = new ht.widget.BorderPane();

			// 创建数据模型
			dataModel = new ht.DataModel();

			// 创建表格面板
			tablePane = new ht.widget.TablePane(dataModel);

			tablePane.getTableView().setRowHeight(25);

			// 创建列模型
			columnModel = tablePane.getColumnModel();

			// 将工具条添加到顶部
			borderPane.setTopView(toolbar);

			// 将表格面板添加到中部
			borderPane.setCenterView(tablePane);

			// 获取视图并添加到window中
			view = borderPane.getView();
			view.className = 'main';
			document.body.appendChild(view);
			window.addEventListener('resize', function(e) {
				borderPane.invalidate();
			}, false);

			/** ***********创建表格列**************** */
			// 告警状态
			var column = new ht.Column();
			column.setName("alarmID"); // 列名称
			column.setDisplayName("告警序号"); // 列显示名称
			column.setAccessType('attr'); // 以什么方式获取值
			column.setWidth(60); // 设置列宽度
			column.setTag('alarmID'); // 标签
			column.setVisible(false); // 默认不可见
			columnModel.add(column); // 将列添加到列模型中

			// 告警级别
			column = new ht.Column();
			column.setName("alarmLevel");
			column.setDisplayName("告警级别");
			column.setAccessType('attr');
			column.setTag('alarmLevel');
			// column.setOrder(false);
			column.setVisible(false);
			// 对列进行排序
			column.setSortFunc(function(v1, v2, d1, d2) {
				if (v1 === v2) {
					return 0;
				}
				// keep 'Cleared' on top
				if (v1 === 0) {
					return -1;
				}

				if (v2 === 0) {
					return 1;
				}

				// compare value
				if (v1 > v2) {
					return 1;
				} else {
					return -1;
				}
			});

			// 渲染列
			column.drawCell = function(g, data, selected, column, x, y, w, h, view) {
				var color = levelMap[data.getAttr("alarmLevel")].color
				var value = levelMap[data.getAttr("alarmLevel")].name;
				// draw background
				g.fillStyle = selected ? ht.Default.darker(color) : color;
				g.beginPath();
				g.rect(x, y, w, h);
				g.fill();
				// draw label
				color = selected ? 'white' : 'black';
				ht.Default.drawText(g, value, null, color, x, y, w, h, 'center');
			};

			column.setWidth(80);
			columnModel.add(column);
			tablePane.getTableView().setSortColumn(column);
			tablePane.getTableView().setCheckMode(true);
			// 告警操作状态
			column = new ht.Column();
			column.setName("operateStatusName");
			column.setDisplayName("告警操作状态");
			column.setAccessType('attr');
			column.setWidth(70);
			column.setTag('operateStatusName');
			column.setVisible(false);
			columnModel.add(column);

			// 告警标题
			column = new ht.Column();
			column.setName("alarmTitle");
			column.setDisplayName("告警标题");
			column.setAccessType('attr');
			column.setTag('alarmTitle');
			column.setVisible(false);
			column.setWidth(80);
			columnModel.add(column);

			// 告警类型
			column = new ht.Column();
			column.setName("alarmTypeName");
			column.setDisplayName("告警类型");
			column.setAccessType('attr');
			column.setWidth(80);
			column.setTag('alarmType');
			column.setVisible(false);
			columnModel.add(column);
			
			// 告警源对象IP
			column = new ht.Column();
			column.setName("sourceMOIPAddress");
			column.setDisplayName("告警源对象IP");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('sourceMOIPAddress');
			column.setVisible(false);
			columnModel.add(column);
			// 告警源名称
			column = new ht.Column();
			column.setName("sourceMOName");
			column.setDisplayName("告警源名称");
			column.setAccessType('attr');
			column.setTag('sourceMOName');
			column.setVisible(false);
			column.setWidth(100);
			columnModel.add(column);
			// 告警对象名称
			column = new ht.Column();
			column.setName("moName");
			column.setDisplayName("告警对象名称");
			column.setAccessType('attr');
			column.setWidth(90);
			column.setTag('moName');
			column.setVisible(false);
			columnModel.add(column);
			
			// 首次发生的时间
			column = new ht.Column();
			column.setName("startTime");
			column.setDisplayName("首次发生的时间");
			column.setAccessType('attr');
			column.setWidth(140);
			column.setTag('startTime');
			column.setVisible(false);
			columnModel.add(column);

			// 告警更新时间
			column = new ht.Column();
			column.setName("lastTime");
			column.setDisplayName("告警更新时间");
			column.setAccessType('attr');
			column.setWidth(140);
			column.setTag('lastTime');
			column.setVisible(false);
			columnModel.add(column);

			// 重复次数
			column = new ht.Column();
			column.setName("repeatCount");
			column.setDisplayName("重复次数");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('repeatCount');
			column.setVisible(false);
			columnModel.add(column);

			// 升级次数
			column = new ht.Column();
			column.setName("upgradeCount");
			column.setDisplayName("升级次数");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('upgrageCount');
			column.setVisible(false);
			columnModel.add(column);

			// 确认人
			column = new ht.Column();
			column.setName("confirmer");
			column.setDisplayName("确认人");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('comfirmer');
			column.setVisible(false);
			columnModel.add(column);

			// 确认时间
			column = new ht.Column();
			column.setName("confirmTime");
			column.setDisplayName("确认时间");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('comfirmTime');
			column.setVisible(false);
			columnModel.add(column);

			// 确认内容
			column = new ht.Column();
			column.setName("confirmInfo");
			column.setDisplayName("确认内容");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('comfirmInfo');
			column.setVisible(false);
			columnModel.add(column);

			// 清除人
			column = new ht.Column();
			column.setName("cleaner");
			column.setDisplayName("清除人");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('cleaner');
			column.setVisible(false);
			columnModel.add(column);

			// 清除时间
			column = new ht.Column();
			column.setName("cleanTime");
			column.setDisplayName("清除时间");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('cleanTime');
			column.setVisible(false);
			columnModel.add(column);

			// 清除内容
			column = new ht.Column();
			column.setName("cleanInfo");
			column.setDisplayName("清除内容");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('cleanInfo');
			column.setVisible(false);
			columnModel.add(column);

			// 派发人
			column = new ht.Column();
			column.setName("dispatchUser");
			column.setDisplayName("派发人");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('dispatcher');
			column.setVisible(false);
			columnModel.add(column);

			// 派发时间
			column = new ht.Column();
			column.setName("dispatchTime");
			column.setDisplayName("派发时间");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('dispatcherTime');
			column.setVisible(false);
			columnModel.add(column);

			// 派发信息
			column = new ht.Column();
			column.setName("dispatchInfo");
			column.setDisplayName("派发信息");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('dispatchInfo');
			column.setVisible(false);
			columnModel.add(column);

			// 工单ID
			column = new ht.Column();
			column.setName("dispatcherID");
			column.setDisplayName("工单ID");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('dispatcherID');
			column.setVisible(false);
			columnModel.add(column);

			// 告警详情
			column = new ht.Column();
			column.setName("alarmContent");
			column.setDisplayName("告警详情");
			column.setAccessType('attr');
			column.setWidth(60);
			column.setTag('alarmContent');
			column.setVisible(false);
			columnModel.add(column);

			// 告警对象类型名
			column = new ht.Column();
			column.setName("moClassName");
			column.setDisplayName("告警对象类型名");
			column.setAccessType('attr');
			column.setWidth(90);
			column.setTag('moClassName');
			column.setVisible(false);
			columnModel.add(column);

			// 告警事件定义ID
			column = new ht.Column();
			column.setName("alarmDefineID");
			column.setDisplayName("告警事件定义ID");
			column.setAccessType('attr');
			column.setWidth(90);
			column.setTag('alarmDefineID');
			column.setVisible(false);
			columnModel.add(column);

			// 告警源对象ID
			column = new ht.Column();
			column.setName("sourceMOID");
			column.setDisplayName("告警源对象ID");
			column.setAccessType('attr');
			column.setWidth(90);
			column.setTag('sourceMOID');
			column.setVisible(false);
			columnModel.add(column);

			

		

			

			// 告警标识
			column = new ht.Column();
			column.setName("alarmOID");
			column.setDisplayName("告警标识");
			column.setAccessType('attr');
			column.setTag('alarmOID');
			column.setVisible(false);
			column.setWidth(130);
			columnModel.add(column);

			

			// 告警对象ID
			column = new ht.Column();
			column.setName("moID");
			column.setDisplayName("告警对象ID");
			column.setAccessType('attr');
			column.setWidth(80);
			column.setTag('moID');
			column.setVisible(false);
			columnModel.add(column);

			// 告警对象类型ID
			column = new ht.Column();
			column.setName("moClassID");
			column.setDisplayName("告警对象类型ID");
			column.setAccessType('attr');
			column.setWidth(90);
			column.setTag('moClassID');
			column.setVisible(false);
			columnModel.add(column);

			/** ************以下是模拟数据******************** */
			var data = new ht.Data();
			// register pushlet

			// PL.sessionId = (new Date()).valueOf();
			PL.userId = '${userID}' + "**" + cfgID;// Date.parse(new Date());
			// PL.moIds = '10599,10714';
			PL.joinListen('alarmNotify');

			// 使用JQuery从后台获取JSON格式的数据,显示列
			var uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/getViewColumn?userID=${userID}&viewCfgID="
					+ cfgID;
			$.ajax({
				type : "post",
				url : uri,// 发送请求地址
				contentType : "json",
				dataType : "json",
				success : function(data) {

					eval(data.evalStr);
				},
				// 请求出错的处理
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// alert("获取显示列出错");
				}
			});
			// 使用JQuery从后台获取JSON格式的数据 ,设置我的视图
			uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/getViewNameOption?userID=${userID}&viewCfgID="
					+ cfgID;
			$.ajax({
						type : "post",
						url : uri,// 发送请求地址
						contentType : "json",
						dataType : "json",
						success : function(data) {
							document.getElementById('mView').innerHTML = data.evalStr;
							var s = data.evalStr.substring(0, data.evalStr
									.lastIndexOf("selected"));
							var cfgIDValue = s.substring(s.lastIndexOf("value"));
							var cfgIdArray = cfgIDValue.split("=");
							cfgID = cfgIdArray[1];
							if (cfgID == undefined) {
								cfgID = 0;
							}
							// 使用JQuery从后台获取JSON格式的数据 ,设置告警数
							uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/getAlarmCount?userID=${userID}&viewCfgID="
									+ cfgID;
							$.ajax({
								type : "post",
								url : uri,// 发送请求地址
								contentType : "json",
								dataType : "json",
								success : function(data) {
									eval(data.evalStr.replace(new RegExp('bbb', 'g'),
											'"'));
								},
								// 请求出错的处理
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									// alert("按级别统计告警条数出错!");
								}
							});
							getActiveAlarm(cfgID);
						},
						// 请求出错的处理
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							// alert("获取我的视图出错!");
						}
					});

			// 加载活动告警
			dataModel.clear();
		};

		function getActiveAlarm(cfgID) {
			// 使用JQuery从后台获取JSON格式的数据
			uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/loadActiveAlarmList?userID=${userID}&viewCfgID="
					+ cfgID;
			$.ajax({
				type : "post",
				url : uri,// 发送请求地址
				contentType : "json",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, item) {
						dataModel.deserialize(item);
					});
				},
				// 请求出错的处理
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// alert("加载活动告警出错!");
				}
			});
		}

		function onData(event) {
			var alarmStatus = event.get("alarmStatus");
			var alarmOperateStatus = event.get("alarmOperateStatus");
			var alarmlevel = event.get("alarmsound");
			var alarmID = event.get("alarmID");

			// alarmStatus 11-新告警 12-自动清除 13-自动次数重复；14-自动次数重复,且级别升级
			// alarmOperateStatus 21-未确认 22-已确认 23-已派发 24-人工清除
			if ((alarmStatus == '11' || alarmStatus == '13' || alarmStatus == '14')
					&& (alarmOperateStatus == '21' || alarmOperateStatus == '22' || alarmOperateStatus == '23')) {
				var jsonData = '';
				for (num = 1; num <= event.get("section"); num++) {
					if (num != '') {
						jsonData += event.get("alarmdetail" + num);
					}
				}

				if (alarmStatus == '13' || alarmStatus == '14'
						|| alarmOperateStatus == '22' || alarmOperateStatus == '23') {
					var obj = dataModel.getDataByTag(alarmID);
					if (obj != null) {
						dataModel.remove(dataModel.getDataByTag(alarmID));
						var beforeHtml = document.getElementById(levelCountMap[obj
								.a("alarmLevel")].name).innerHTML;
						if (beforeHtml.indexOf("&nbsp;") > -1) {
							var before = parseInt(beforeHtml.substring(beforeHtml
									.indexOf("&nbsp;") + 6, beforeHtml.length));
							if (before > 0) {
								document.getElementById(levelCountMap[obj
										.a("alarmLevel")].name).innerHTML = beforeHtml
										.substring(0, beforeHtml.indexOf("&nbsp;") + 6)
										+ beforeHtml.substring(
												beforeHtml.indexOf("&nbsp;") + 6,
												beforeHtml.length).replace(before,
												before - 1);
							}
						}
					}
				}

				dataModel.deserialize(jsonData);
				var updateHtml = levelCountMap[alarmlevel].name;
				var htmlstr = document.getElementById(updateHtml).innerHTML;
				if (htmlstr.indexOf("style") > -1) {
					htmlstr = "<img src=\"${pageContext.request.contextPath}/"
							+ htmlstr.substring(htmlstr.indexOf("style"),
									htmlstr.length);
				}

				var before = parseInt(htmlstr.substring(htmlstr.indexOf("&nbsp;") + 6,
						htmlstr.length));
				document.getElementById(updateHtml).innerHTML = htmlstr.substring(0,
						htmlstr.indexOf("&nbsp;") + 6)
						+ htmlstr.substring(htmlstr.indexOf("&nbsp;") + 6,
								htmlstr.length).replace(before, before + 1);

			} else if (alarmStatus == '12' || alarmOperateStatus == '24') {
				var updateHtml = levelCountMap[alarmlevel].name;
				var htmlstr = document.getElementById(updateHtml).innerHTML;
				if (htmlstr.indexOf("style") > -1) {
					htmlstr = "<img src=\"${pageContext.request.contextPath}/"
							+ htmlstr.substring(htmlstr.indexOf("style"),
									htmlstr.length);
				}

				var obj = dataModel.getDataByTag(alarmID);
				if (obj != null) {
					dataModel.remove(dataModel.getDataByTag(alarmID));
					if (htmlstr.indexOf("&nbsp;") > -1) {
						var before = parseInt(htmlstr.substring(htmlstr
								.indexOf("&nbsp;") + 6, htmlstr.length));
						if (before > 0) {
							document.getElementById(updateHtml).innerHTML = htmlstr
									.substring(0, htmlstr.indexOf("&nbsp;") + 6)
									+ htmlstr.substring(htmlstr.indexOf("&nbsp;") + 6,
											htmlstr.length).replace(before, before - 1);
						}
					}
				}
			}
			// 声音
			playsound(alarmlevel);
			// alert(tablePane.getTableView().getRowSize());
			// alert(tablePane.getTableView().getStartRowIndex());

			tablePane.getTableView().validateImpl();

			if (dataModel.size() > 500) {
				var datas = tablePane.getTableView().getRowDatas();
				var obj = dataModel.getDataByTag(datas.get(datas.size() - 1));
				if (obj != null) {
					var beforeHtml = document.getElementById(levelCountMap[obj
							.a("alarmLevel")].name).innerHTML;
					if (beforeHtml.indexOf("&nbsp;") > -1) {
						var before = parseInt(beforeHtml.substring(beforeHtml
								.indexOf("&nbsp;") + 6, beforeHtml.length));
						if (before > 0) {
							document
									.getElementById(levelCountMap[obj.a("alarmLevel")].name).innerHTML = beforeHtml
									.substring(0, beforeHtml.indexOf("&nbsp;") + 6)
									+ beforeHtml.substring(
											beforeHtml.indexOf("&nbsp;") + 6,
											beforeHtml.length).replace(before,
											before - 1);
						}
					}
					dataModel.remove(obj);
				}

				// alert("*****"+obj);
				// dataModel.remove(dataModel.getDataByTag(datas.get(dataModel.size() -
				// 2)));
				// var tmpid = tablePane.getTableView().getEndRowIndex();
				// alert(tmpid);
				// var obj = dataModel.getDataById(10);
				// alert(obj);
				// alert(tablePane.getTableView().getChildAt(10));
				// dataModel.remove(dataModel.getDataById(tablePane.getTableView().getEndRowIndex()));
			}
		}

		function getAlarmId() {
			var ids = '';
			tablePane.getTableView().getSelectionModel().getSelection().each(
					function(id) {
						var obj = dataModel.getDataByTag(id);
						if (ids != '') {
							ids += ",";
						}
						ids += obj.getAttr("alarmID");
					});
			return ids
		}
		// 获取操作状态
		function getAlarmOperateStatus() {
			var operateStatus = '';
			tablePane.getTableView().getSelectionModel().getSelection().each(
					function(id) {
						var obj = dataModel.getDataByTag(id);
						//console.log(obj);
						if (operateStatus != '') {
							operateStatus += ",";
						}
						operateStatus += obj.getAttr("alarmOperateStatus");
					});
			return operateStatus
		}

		// 获取选中的数据对象
		function getAlarmObject() {
			var arrayObj = new Array();
			tablePane.getTableView().getSelectionModel().getSelection().each(
					function(id) {
						var obj = dataModel.getDataByTag(id);
						//console.log(obj);
						arrayObj.push(obj);
					});
			return arrayObj
		}

		function getId() {
			return tablePane.getTableView().getSelectionModel().getSelection();
		}

		function reloadTable() {
			var ids = [];
			tablePane.getTableView().getSelectionModel().getSelection().each(
					function(id) {
						ids.push(id);
					});

			for (var i = 0; i < ids.length; i++) {
				var obj = dataModel.getDataByTag(ids[i]);
				// console.log(obj);
				var alarmlevel = obj.a("alarmLevel");
				var htmlstr = document.getElementById(levelCountMap[alarmlevel].name).innerHTML;
				if (htmlstr.indexOf("&nbsp;") > -1) {
					var before = parseInt(htmlstr.substring(
							htmlstr.indexOf("&nbsp;") + 6, htmlstr.length));
					if (before > 0) {
						document.getElementById(levelCountMap[alarmlevel].name).innerHTML = htmlstr
								.substring(0, htmlstr.indexOf("&nbsp;") + 6)
								+ htmlstr.substring(htmlstr.indexOf("&nbsp;") + 6,
										htmlstr.length).replace(before, before - 1);
					}
				}
				dataModel.remove(obj);
			}
		}
		function reloadAllTable(viewCfgID) {
			window.location.href = getRootName()
					+ "/monitor/alarmMgr/alarmPanel/loadActive?viewCfgID=" + viewCfgID;
		}

		function onchangeReloadAllTable() {
			window.location.href = getRootName()
					+ "/monitor/alarmMgr/alarmPanel/loadActive?viewCfgID="
					+ document.getElementById("mView").value;
		}

		function modifyView() {
			var mView = $("#mView").val();
			if (mView == "1") {
				$.messager.alert("提示", "默认视图,不可编辑！", "info");
			} else {
				parent.$('#popWin').window(
						{
							title : '告警视图修改',
							width : 800,
							height : 530,
							minimizable : false,
							maximizable : false,
							collapsible : false,
							modal : true,
							href : getRootName()
									+ '/monitor/alarmView/toUpadteAlarmView?viewCfgID='
									+ document.getElementById("mView").value
						});
			}
		}

		function addView() {
			parent.$('#popWin').window({
				title : '新增告警视图',
				width : 800,
				height : 530,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName() + '/monitor/alarmView/toAddAlarmView'
			});
		}

		function alarmDetail() {
			// 获取id
			var ids = getAlarmId();
			if (ids != '' && ids.indexOf(",") < 0) {
				parent
						.$('#popWin')
						.window(
								{
									title : '告警详情',
									width : 800,
									height : 600,
									minimizable : false,
									maximizable : false,
									collapsible : false,
									modal : true,
									href : '${pageContext.request.contextPath}/monitor/alarmActive/toAlarmActiveDetail?alarmID='
											+ ids
								});
			} else if (ids.indexOf(",") > 0) {
				$.messager.alert('提示', '只能选中一条记录', 'error');
			} else {
				$.messager.alert('提示', '没有任何选中项', 'error');
			}
		}

		// 确认
		function toConfirm() {
			// 获取id
			var ids = getAlarmId();
			var flag = null;
			if (ids != '' && ids.split(",").length > 20) {
				$.messager.alert('提示', '确认告警一次不能大于20条', 'error');
				return;
			}
			var operateStatus = getAlarmOperateStatus();
			if (operateStatus.indexOf(",") < 0) {
				if (operateStatus != 21) {
					flag = "1";
				}
			} else {
				var operateStatusArray = operateStatus.split(",");
				for (var i = 0; i < operateStatusArray.length; i++) {
					if (operateStatusArray[i] != 21) {
						flag = "1";
					}
				}
			}
			if (ids != '') {
				if (null == flag) {
					parent
							.$('#popWin')
							.window(
									{
										title : '告警确认',
										width : 650,
										height : 400,
										minimizable : false,
										maximizable : false,
										collapsible : false,
										modal : true,
										href : '${pageContext.request.contextPath}/monitor/alarmActive/toBathAlarmActiveConfirm?id='
												+ ids
									});
				} else {
					$.messager.alert('提示', '选择的告警记录中,状态不是未确认,请重新选择!', 'info');
				}
			} else {
				$.messager.alert('提示', '没有任何选中项', 'info');
			}
		}

		// 派发告警
		function distributed() {
			// 获取id
			var ids = getAlarmId();
			if (ids != '' && ids.split(",").length > 20) {
				$.messager.alert('提示', '确认告警一次不能大于20条', 'error');
				return;
			}
			if (ids != '') {
				parent
						.$('#popWin')
						.window(
								{
									title : '告警确认',
									width : 650,
									height : 400,
									minimizable : false,
									maximizable : false,
									collapsible : false,
									modal : true,
									href : '${pageContext.request.contextPath}/monitor/alarmActive/toBathAlarmActiveConfirm?id='
											+ ids
								});
			} else {
				$.messager.alert('提示', '没有任何选中项', 'error');
			}
		}

		// 取消确认
		function doCancel() {
			var ids = getAlarmId();
			if (ids != '') {
				$.messager.confirm("提示", "确定要取消确认告警？", function(r) {
					if (r == true) {
						var uri = getRootName()
								+ "/monitor/alarmActive/bathCancelAlarmActiveConfirm";
						var ajax_param = {
							url : uri,
							type : "post",
							datdType : "json",
							data : {
								"id" : ids
							},
							success : function(data) {
								if (true == data || "true" == data) {
									$.messager.alert("提示", "取消确认告警成功！", "info");
									reloadTable();
								} else {
									$.messager.alert("提示", "取消确认告警失败！", "error");
								}
							}
						};
						ajax_(ajax_param);
					}
				});
			} else {
				$.messager.alert('提示', '没有任何选中项', 'error');
			}
		}

		/**
		 * 删除告警
		 * 
		 * @return
		 */
		function deleteAlarm() {
			var ids = getAlarmId();
			if (ids != '' && ids.split(",").length > 20) {
				$.messager.alert('提示', '删除告警一次不能大于20条', 'error');
				return;
			}
			if (ids != '') {
				$.messager
						.confirm(
								"提示",
								"确定要删除该告警？",
								function(r) {
									if (r == true) {
										var uri = "${pageContext.request.contextPath}/monitor/alarmActive/bathDeleteAlarmActive";
										var ajax_param = {
											url : uri,
											type : "post",
											datdType : "json",
											data : {
												"id" : ids
											},
											success : function(data) {
												if (true == data || "true" == data) {
													$.messager.alert("提示", "该告警删除成功！",
															"info");
													reloadTable();
												} else {
													$.messager.alert("提示", "该告警删除失败！",
															"error");
												}
											}
										};
										ajax_(ajax_param);
									}
								});
			} else {
				$.messager.alert('提示', '没有任何选中项', 'error');
			}
		}

		/**
		 * 清除告警
		 * 
		 * @return
		 */
		function bClear() {
			// 获取选中alarmid
			var ids = getAlarmId();
			if (ids != '' && ids.split(",").length > 20) {
				$.messager.alert('提示', '清除告警一次不能大于20条', 'error');
				return;
			}
			if (ids != '') {
				parent
						.$('#popWin')
						.window(
								{
									title : '告警清除',
									width : 650,
									height : 400,
									minimizable : false,
									maximizable : false,
									collapsible : false,
									modal : true,
									href : '${pageContext.request.contextPath}/monitor/alarmActive/toBathClearAlarmActive?id='
											+ ids
								});
			} else {
				$.messager.alert('提示', '没有任何选中项', 'error');
			}
		}

		// 告警手动派发
		Date.prototype.format = function(format) {
			var o = {
				"M+" : this.getMonth() + 1,
				"d+" : this.getDate(),
				"H+" : this.getHours(),
				"h+" : this.getHours(),
				"m+" : this.getMinutes(),
				"s+" : this.getSeconds(),
				"q+" : Math.floor((this.getMonth() + 3) / 3),
				"S" : this.getMilliseconds()
			};
			if (/(y+)/.test(format)) {
				format = format.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			}
			for ( var i in o) {
				var reg = new RegExp("(" + i + ")");
				if (reg.test(format)) {
					format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
							: (("00" + o[i]).substr(("" + o[i]).length)));
				}
			}
			return format;
		}

		alarmSendSingle = {};
		alarmSendSingle.selectPerson = function() {
			var names = [];
			var ids = [];
			var checked = $('#departmentMan :checkbox:checked');
			$.each(checked, function(k, v) {
				ids.push($(this).val());
				names.push($(this).attr("name"));
			});
			$("#selectPeopleId").val(ids.join());
			$("#selectPeopleName").val(names.join());
		}
		alarmSendSingle.toBathSendSingle = function() {
			var ids = [];
			var rowIDs = getAlarmId();
            if(null != rowIDs && "" != rowIDs){
            	ids = rowIDs.toString().split(",");
            }
			if (rowIDs.length === 0) {
				$.messager.alert('提示', '没有任何选中项', 'info');
				return;
			} else if (1 < ids.length && '3' === $('#alarmOrderVersion').val()) {
				$.messager.alert("提示", "请单条告警派发！", "info");
				return;
			} else {
				var object = getAlarmObject();
				var flag = false;
				var isSend = [];
				var rows2 = $.map(object, function(v) {
					if (v._attrObject.dispatchID) {
						flag = true;
						isSend.push(v._attrObject.alarmID);
						return null;
					}
					return v._attrObject;
				});
				if (flag) {
					$.messager.alert("提示", "选择的告警记录中，存在重复派单，请重新选择！");
					return;
				}
				if (rows2.length > 10) {
					$.messager.alert("提示", "最多可支持10条告警同时派发！");
					return;
				}
	

				if (1 == $('#alarmOrderVersion').val()) {
					$("<div id='alarm_Old_Version'/>").window({
						title: "登记并派单",
						width: 800,
						height: 510, 
						minimizable : false,
						maximizable : false,
						collapsible : false,
						modal : true,
						onClose : function () {
							$(this).window('destroy');
						},
						href: getRootName() + "/monitor/alarmSendOrderUnion/toOldAlarmSendOrderUnion?alarmIds="+rowIDs
				}
			    );
				
				} else if (2 == $('#alarmOrderVersion').val()) {
					$("#popWin").window({
						title: "登记并派单",
						width: 800,
						height: 510, 
						minimizable : false,
						maximizable : false,
						collapsible : false,
						modal : true,
						href: getRootName() + "/monitor/alarmSendOrderUnion/toNewAlarmSendOrderUnion?alarmIds="+rowIDs
					});
					
				}else if(3 == $('#alarmOrderVersion').val()){
					parent.$("#popWin4").window({
						title: "登记并派单",
						width: 800,
						height: 510,
						minimizable: false,
						maximizable: false,
						collapsible: false,
						modal: true,
						href: getRootName() + '/monitor/alarmSendOrderUnion/toThirdVersionSendOrderUnion?alarmIds='+rowIDs
					}
					);
				
				}
			}
		}

		/**
		 * 打开告警详情页面
		 * 
		 * @return
		 */
		function toView(id) {
			parent.parent.$('#popWin').window(
					{
						title : '告警详情',
						width : 800,
						height : 560,
						minimizable : false,
						maximizable : false,
						collapsible : false,
						modal : true,
						href : getRootName()
								+ '/monitor/alarmActive/toAlarmActiveDetail?alarmID='
								+ id
					});
		}

		function playsound(level) {
			var voice = "${pageContext.request.contextPath}/style/alarm/alarmSound/alarm"
					+ level + ".wav";
			document.getElementById("alarmsound").innerHTML = "<embed name='palyer1' id='player1' src='"
					+ voice + "' autostart='true' width='0' height='0'></embed>";
		}

		function cc() {
			$(".main div:first").css("border-bottom", "1px solid #dcdedd");
			$(".main div div:first").css("background",
					"none repeat scroll 0% 0% rgb(225,242,250)");
			$(".main div div:first").css("border-top", "1px solid #ffffff");
			$(".main div div:first").css("border-bottom", "1px solid #dcdedd");
			$(".main div:nth-child(2) div:nth-child(2)").css("", "");
		}
		setTimeout("cc()", 200);

		</script>
	</head>

	<body onLoad="initd();">
	<input type="hidden" id="alarmOrderVersion" value="${alarmOrderVersion }" />
		<span id="emergency"><img
				src="${pageContext.request.contextPath}/style/images/levelIcon/1.png"
				alt="紧急告警" />&nbsp;0</span>
		<span id="severity"><img
				src="${pageContext.request.contextPath}/style/images/levelIcon/2.png"
				alt="严重告警" />&nbsp;0</span>
		<span id="ordinary"><img
				src="${pageContext.request.contextPath}/style/images/levelIcon/3.png"
				alt="一般告警" />&nbsp;0</span>
		<span id="prompt"><img
				src="${pageContext.request.contextPath}/style/images/levelIcon/4.png"
				alt="提示告警" />&nbsp;0</span>
		<span id="uncertain"><img
				src="${pageContext.request.contextPath}/style/images/levelIcon/5.png"
				alt="未确定告警" />&nbsp;0</span>
		<span>
			<button id="look" onclick="alarmDetail();">
				查看
			</button> 
		</span>
		<span>
			<button id="confirm" onclick="toConfirm();">
				确认
			</button> </span>
		<span>
			<button id="distributed"  style="display:none" onclick="alarmSendSingle.toBathSendSingle();">
				派单
			</button></span>
			<span>
			<button id="bClear" onclick="bClear();">
				清除
			</button> </span>
		<!--<span>
			<button id="delete" onclick="deleteAlarm();">
				删除
			</button> </span>
		--><!-- <span>
			<button id="addView" onclick="addView();">
				新增视图
			</button> </span> -->
		<!-- <span>
			<button id="cView" onclick="modifyView();">
				修改当前视图
			</button> </span> -->
		<la bel id="sel" style="display:none">
			选择:
		</label>
		<select id="mView" onchange="onchangeReloadAllTable()" style="display:none">
		</select> 
		<div name="alarmsound" id="alarmsound">
			<!-- <embed name="palyer1" id="player1" width="10" height="10" src=""
			autostart="true" type="application/http-index-format"></embed> -->
		</div>
		
    <div id="popWin"></div>
	<div id="popWin2"></div>
	<div id="popWin3"></div>
	</body>
</html>