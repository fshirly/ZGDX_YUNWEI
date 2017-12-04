<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<html>
	<head>
		<title>real-time alarm</title>
		<style>
			html,body {
				padding: 0px;
				margin: 0px;
			}
			.main {
				margin: 0px;
				padding: 0px;
				position: absolute;
				top: 0px;
				bottom: 0px;
				left: 0px;
				right: 0px;
			}
		</style> 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmPanel/ht.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmPanel/ajax-pushlet-client.js"></script>
			
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/Validdiv.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script> 
		<script><!--              
         function init(){
		 	var map = { 
                 1: { name: '紧急', color: '#ff0000'},
                 2: { name: '严重', color: '#ff9900'},
                 3: { name: '一般', color: '#ffff00'},
                 4: { name: '提示', color: '#0000ff'},
                 5: { name: '清除', color: '#009900'},
                 6: { name: '未确定',color:'#c0c0c0'}
	      };
	 
		 //添加工具条               s
	     var toolbar = new ht.widget.Toolbar([
                {
                    element: document.getElementById('emergency') //紧急
                },
				{
	               	element: document.getElementById('severity') //严重
	           	},
				{
	              	element: document.getElementById('ordinary') //一般
	           	},
				{
	               	element: document.getElementById('prompt') //提示
	            },
				{
	              	element: document.getElementById('clear') //清除
	            },
	            {
	              	element: document.getElementById('uncertain') //未确定
	            },
				{
	              	element: document.getElementById('hour') //前一个小时
	          	},
				{
	               	element: document.getElementById('look') //查看
	          	},
				{
	              	element: document.getElementById('confirm') //确认
	          	},
				{
	              	element: document.getElementById('cConfirm') //取消确认
	         	},
				{
	               	element: document.getElementById('distributed') //派发
	           	},
				{
	              	element: document.getElementById('bClear') //清除
	          	},
				{
	              	element: document.getElementById('delete') //删除
	          	},
				{
	               	element: document.getElementById('cView') //自定义视图
	          	},
				{
	            	element: document.getElementById('sel') //选择
	         	},
				{
	             	element: document.getElementById('mView') //我的视图
	            }
	        ]);		
	
	        toolbar.setStickToRight(true);
			
			//创建布局对象
	        borderPane = new ht.widget.BorderPane();
			
			//创建数据模型
			dataModel = new ht.DataModel();  
			
			//创建表格面板
			tablePane = new ht.widget.TablePane(dataModel);
			
			//创建列模型
			columnModel = tablePane.getColumnModel();
			
			//将工具条添加到顶部
	        borderPane.setTopView(toolbar);
			
			//将表格面板添加到中部
			borderPane.setCenterView(tablePane);
	
			//获取视图并添加到window中
            view = borderPane.getView();  
            view.className = 'main';
            document.body.appendChild(view);
            window.addEventListener('resize', function (e) {
                borderPane.invalidate();
            }, false); 
					
			/*************创建表格列*****************/
			//告警状态
			var  column = new ht.Column();
	             column.setName("alarmID");		//列名称
			column.setDisplayName("告警序号");	//列显示名称
			column.setAccessType('attr');		//以什么方式获取值
			column.setWidth(80);				//设置列宽度
			column.setTag('alarmID');           //标签
			column.setVisible(false);           //默认不可见
	     	columnModel.add(column);			//将列添加到列模型中
			
			//告警标识
			column = new ht.Column();
	        column.setName("alarmOID");
			column.setDisplayName("告警标识");
			column.setAccessType('attr'); 
			column.setTag('alarmOID');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
			//告警标题
			column = new ht.Column();
	        column.setName("alarmTitle");
			column.setDisplayName("告警标题");
			column.setAccessType('attr'); 
			column.setTag('alarmTitle');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
			//告警级别
			column = new ht.Column();
	       	column.setName("alarmLevel");
			column.setDisplayName("告警级别");
			column.setAccessType('attr');
			column.setTag('alarmLevel');
			//column.setOrder(false);
			column.setVisible(false);
			//对列进行排序
			column.setSortFunc(function(v1, v2, d1, d2){
			    if(v1 === v2) {
	           		return 0;
                }
                // keep 'Cleared' on top
                if(v1 === 0) {
                    return -1;
                }
	
                if(v2 === 0) { 
                    return 1;
                }
                
                // compare value
                if(v1 > v2){
                    return 1;
                } else { 
                    return -1; 
                }
			});
			
			//渲染列
			column.drawCell = function (g, data, selected, column, x, y, w, h, view) {
			  	var color =  map[data.getAttr("alarmLevel")].color
			  	var value  =  map[data.getAttr("alarmLevel")].name;
			  
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
			
			//告警源名称
			column = new ht.Column();
	        column.setName("sourceMOName");
			column.setDisplayName("告警源名称");
			column.setAccessType('attr'); 
			column.setTag('sourceMOName');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
		    //告警源对象IP
			column = new ht.Column();
	        column.setName("sourceMOIPAddress");
			column.setDisplayName("告警源对象IP");
			column.setAccessType('attr');
			column.setWidth(80);
	        column.setTag('sourceMOIPAddress');
			column.setVisible(false);
			columnModel.add(column);
			
			//告警对象名称
			column = new ht.Column();
	        column.setName("moName");
			column.setDisplayName("告警对象名称");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('moName');
			column.setVisible(false);
			 columnModel.add(column);
			
		 	//告警对象类型
			column = new ht.Column();
	        column.setName("moClassName");
			column.setDisplayName("告警对象类型");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('moClassName');
			column.setVisible(false);
			 columnModel.add(column);
		    //告警类型
			column = new ht.Column();
	        column.setName("alarmType");
			column.setDisplayName("告警类型");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('alarmType');
			column.setVisible(false);
	        columnModel.add(column);
			
			//首次发生的时间
			column = new ht.Column();
	        column.setName("startTime");
			column.setDisplayName("首次发生的时间");
			column.setAccessType('attr');
			column.setWidth(120);
			column.setTag('startTime');
			column.setVisible(false);
	        columnModel.add(column); 
			
			//告警更新时间
			column = new ht.Column();
	        column.setName("lastTime");
			column.setDisplayName("告警更新时间");
			column.setAccessType('attr');
			column.setWidth(120);
			column.setTag('lastTime');
			column.setVisible(false);
	        columnModel.add(column); 
	        
		    //重复次数
			column = new ht.Column();
	        column.setName("repeatCount");
			column.setDisplayName("重复次数");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('repeatCount');
			column.setVisible(false);
	        columnModel.add(column);
			 
			//升级次数
			column = new ht.Column();
	        column.setName("upgradeCount");
			column.setDisplayName("升级次数");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('upgradeCount');
			column.setVisible(false);
	        columnModel.add(column);
	        
	        //告警状态
			column = new ht.Column();
	        column.setName("alarmStatus");
			column.setDisplayName("告警状态");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('alarmStatus');
			column.setVisible(false);
	    	columnModel.add(column);
	    
			/**************以下是模拟数据*********************/
			var data = new ht.Data();
			/**
			data.a("alarmStatus","未确认");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev01");
			data.a("alarmOID","11");
			data.a("alarmTitle","默认阀值事件");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","设备告警");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data);
	
			var data = new ht.Data();
			data.a("alarmStatus","未确认");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev02");
			data.a("alarmOID","11");
			data.a("alarmTitle","默认阀值事件");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","设备告警");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data);
			
		  	var data = new ht.Data();
			data.a("alarmStatus","未确认");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev02");
			data.a("alarmOID","11");
			data.a("alarmTitle","默认阀值事件");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","设备告警");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data); */
		  	PL.userId = Date.parse(new Date());  
      		PL.joinListen('alarmNotify');
      
      		//columnModel.getDataByTag('moClassName').setWidth(10); 

			//使用JQuery从后台获取JSON格式的数据 
		  	var uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/getViewColumn?userID="+$("#userID").val();;
			 $.ajax( {
				type : "post",
				url : uri,//发送请求地址
				contentType : "json",
				dataType : "json",
				success : function(data) {
					eval(data.evalStr);
				},
				//请求出错的处理
				error : function(XMLHttpRequest, textStatus, errorThrown) { 
					alert("请求出错1");
				}
			});
			
			//使用JQuery从后台获取JSON格式的数据 
			 
		  	 uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/loadActiveAlarmList?userID="+$("#userID").val();;
			 $.ajax( {
				type : "post",
				url : uri,//发送请求地址
				contentType : "json",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, item) {  
						dataModel.deserialize(item);
					}); 
				},
				//请求出错的处理
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown.toString());
					alert("请求出错2");
				}
			}); 
			//p_join_listen('/alarmNotify'); 
	 }
 	/**
	 function strToJson(){ 
		return JSON.parse('({"v":"2.9","d":[{"c":"ht.Data","i":123321,"a":{"alarmID":123321,"alarmDefineID":0,"alarmOID":"0.0.0.0.0.1.1.1","alarmTitle":"alarm dispatcher Test","sourceMOID":1,"sourceMOName":"Alarm Test","sourceMOIPAddress":"192.168.1.35","moclassID":1,"moid":1,"moName":"192.168.1.35","alarmLevel":10,"alarmLevelDescr":"严重","alarmType":2,"alarmTypeDescr":"性能告警","startTime":1405878585000,"lastTime":1405950585000,"repeatCount":2,"upgradeCount":0,"alarmStatus":3,"confirmer":null,"confirmTime":null,"confirmInfo":null,"cleaner":null,"cleanTime":null,"cleanInfo":null,"dispatchUser":null,"dispatchID":null,"dispatchTime":null,"dispatchInfo":null,"alarmContent":null}}]})'); 
	 }
	 function strToJson2(){ 
		var json = eval('{"v":"2.9","d":[{"c":"ht.Data","i":123321,"a":{"alarmOID":"0.0.0.0.0.1.1.1","alarmTitle":"alarm dispatcher Test","sourceMOName":"Alarm Test","sourceMOIPAddress":"192.168.1.35","alarmLevel":10,"alarmType":2,"startTime":1405878585000,"lastTime":1405950585000,"repeatCount":2,"alarmStatus":3}}]}'); 
		return json; 
	} 
	*/      	
	 function getId() {
	 	tablePane.getTableView().getSelectionModel()
		  		.getSelection().each(function(id){
					alert(id);
		});
	}
 
	function look() {
		//dataModel.remove(dataModel.getDataById(15));
		//tablePane.getTableView().getSelectionModel().getSelection().remove();
	  	 var doc = window.open().document,
         json = JSON.stringify(dataModel.toJSON(), null, 2);
         doc.open();
         doc.write("<pre>" + json + "</pre>");
         doc.close();
         doc.body.style.margin = '0';
         doc.title = 'Export JSON at ' + new Date();
	}
	
	function onData(event) {
		//alert(event.toString());  
		//var jsonData=event.get("alarmdetail1")+event.get("alarmdetail2")+event.get("alarmdetail3")+event.get("alarmdetail4"); 
		//alert(jsonData);
		var jsonData='';
		for(iia = 1; iia <= event.get("section"); iia++){
			if(iia!=''){
				jsonData += event.get("alarmdetail"+iia);
			}
		}
		//alert(strToJson2().d[0]['i']);
		dataModel.deserialize(jsonData);
	}
	                                                           </script>
</head>

<body onload="init();">
	<input type="hidden" id="userID" name="userID" value="${userID}" />
	<span id="emergency">紧急10</span>
	<span id="severity">严重11</span>
	<span id="ordinary">一般12</span>
	<span id="prompt">提示13</span>
	<span id="clear">清除11</span>
	<span id="uncertain">未确定11</span>
 
	<select id="hour">
		<option>
			前一小时
		</option>
		<option>
			前两小时
		</option>
		<option>
			前一天
		</option>
	</select>

	<button id="look" onclick="look();">
		查看
	</button>
	<button id="confirm">
		确认
	</button>
	<button id="cConfirm">
		取消确认
	</button>
	<button id="distributed">
		派发
	</button>
	<button id="bClear">
		清除
	</button>
	<button id="delete">
		删除
	</button>
	<button id="cView">
		自定义视图
	</button>

	<label id="sel">
		选择:
	</label>
	<select id="mView">
		<option>
			我的视图1
		</option>
		<option>
			我的视图2
		</option>
	</select>
  </body>
</html>