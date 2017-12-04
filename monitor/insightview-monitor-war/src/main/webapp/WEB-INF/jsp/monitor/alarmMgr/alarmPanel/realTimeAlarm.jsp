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
                 1: { name: '����', color: '#ff0000'},
                 2: { name: '����', color: '#ff9900'},
                 3: { name: 'һ��', color: '#ffff00'},
                 4: { name: '��ʾ', color: '#0000ff'},
                 5: { name: '���', color: '#009900'},
                 6: { name: 'δȷ��',color:'#c0c0c0'}
	      };
	 
		 //��ӹ�����               s
	     var toolbar = new ht.widget.Toolbar([
                {
                    element: document.getElementById('emergency') //����
                },
				{
	               	element: document.getElementById('severity') //����
	           	},
				{
	              	element: document.getElementById('ordinary') //һ��
	           	},
				{
	               	element: document.getElementById('prompt') //��ʾ
	            },
				{
	              	element: document.getElementById('clear') //���
	            },
	            {
	              	element: document.getElementById('uncertain') //δȷ��
	            },
				{
	              	element: document.getElementById('hour') //ǰһ��Сʱ
	          	},
				{
	               	element: document.getElementById('look') //�鿴
	          	},
				{
	              	element: document.getElementById('confirm') //ȷ��
	          	},
				{
	              	element: document.getElementById('cConfirm') //ȡ��ȷ��
	         	},
				{
	               	element: document.getElementById('distributed') //�ɷ�
	           	},
				{
	              	element: document.getElementById('bClear') //���
	          	},
				{
	              	element: document.getElementById('delete') //ɾ��
	          	},
				{
	               	element: document.getElementById('cView') //�Զ�����ͼ
	          	},
				{
	            	element: document.getElementById('sel') //ѡ��
	         	},
				{
	             	element: document.getElementById('mView') //�ҵ���ͼ
	            }
	        ]);		
	
	        toolbar.setStickToRight(true);
			
			//�������ֶ���
	        borderPane = new ht.widget.BorderPane();
			
			//��������ģ��
			dataModel = new ht.DataModel();  
			
			//����������
			tablePane = new ht.widget.TablePane(dataModel);
			
			//������ģ��
			columnModel = tablePane.getColumnModel();
			
			//����������ӵ�����
	        borderPane.setTopView(toolbar);
			
			//����������ӵ��в�
			borderPane.setCenterView(tablePane);
	
			//��ȡ��ͼ����ӵ�window��
            view = borderPane.getView();  
            view.className = 'main';
            document.body.appendChild(view);
            window.addEventListener('resize', function (e) {
                borderPane.invalidate();
            }, false); 
					
			/*************���������*****************/
			//�澯״̬
			var  column = new ht.Column();
	             column.setName("alarmID");		//������
			column.setDisplayName("�澯���");	//����ʾ����
			column.setAccessType('attr');		//��ʲô��ʽ��ȡֵ
			column.setWidth(80);				//�����п��
			column.setTag('alarmID');           //��ǩ
			column.setVisible(false);           //Ĭ�ϲ��ɼ�
	     	columnModel.add(column);			//������ӵ���ģ����
			
			//�澯��ʶ
			column = new ht.Column();
	        column.setName("alarmOID");
			column.setDisplayName("�澯��ʶ");
			column.setAccessType('attr'); 
			column.setTag('alarmOID');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
			//�澯����
			column = new ht.Column();
	        column.setName("alarmTitle");
			column.setDisplayName("�澯����");
			column.setAccessType('attr'); 
			column.setTag('alarmTitle');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
			//�澯����
			column = new ht.Column();
	       	column.setName("alarmLevel");
			column.setDisplayName("�澯����");
			column.setAccessType('attr');
			column.setTag('alarmLevel');
			//column.setOrder(false);
			column.setVisible(false);
			//���н�������
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
			
			//��Ⱦ��
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
			
			//�澯Դ����
			column = new ht.Column();
	        column.setName("sourceMOName");
			column.setDisplayName("�澯Դ����");
			column.setAccessType('attr'); 
			column.setTag('sourceMOName');
			column.setVisible(false);
			column.setWidth(100);
	        columnModel.add(column);
			
		    //�澯Դ����IP
			column = new ht.Column();
	        column.setName("sourceMOIPAddress");
			column.setDisplayName("�澯Դ����IP");
			column.setAccessType('attr');
			column.setWidth(80);
	        column.setTag('sourceMOIPAddress');
			column.setVisible(false);
			columnModel.add(column);
			
			//�澯��������
			column = new ht.Column();
	        column.setName("moName");
			column.setDisplayName("�澯��������");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('moName');
			column.setVisible(false);
			 columnModel.add(column);
			
		 	//�澯��������
			column = new ht.Column();
	        column.setName("moClassName");
			column.setDisplayName("�澯��������");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('moClassName');
			column.setVisible(false);
			 columnModel.add(column);
		    //�澯����
			column = new ht.Column();
	        column.setName("alarmType");
			column.setDisplayName("�澯����");
			column.setAccessType('attr');
			column.setWidth(100);
			column.setTag('alarmType');
			column.setVisible(false);
	        columnModel.add(column);
			
			//�״η�����ʱ��
			column = new ht.Column();
	        column.setName("startTime");
			column.setDisplayName("�״η�����ʱ��");
			column.setAccessType('attr');
			column.setWidth(120);
			column.setTag('startTime');
			column.setVisible(false);
	        columnModel.add(column); 
			
			//�澯����ʱ��
			column = new ht.Column();
	        column.setName("lastTime");
			column.setDisplayName("�澯����ʱ��");
			column.setAccessType('attr');
			column.setWidth(120);
			column.setTag('lastTime');
			column.setVisible(false);
	        columnModel.add(column); 
	        
		    //�ظ�����
			column = new ht.Column();
	        column.setName("repeatCount");
			column.setDisplayName("�ظ�����");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('repeatCount');
			column.setVisible(false);
	        columnModel.add(column);
			 
			//��������
			column = new ht.Column();
	        column.setName("upgradeCount");
			column.setDisplayName("��������");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('upgradeCount');
			column.setVisible(false);
	        columnModel.add(column);
	        
	        //�澯״̬
			column = new ht.Column();
	        column.setName("alarmStatus");
			column.setDisplayName("�澯״̬");
			column.setAccessType('attr');
			column.setWidth(50);
			column.setTag('alarmStatus');
			column.setVisible(false);
	    	columnModel.add(column);
	    
			/**************������ģ������*********************/
			var data = new ht.Data();
			/**
			data.a("alarmStatus","δȷ��");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev01");
			data.a("alarmOID","11");
			data.a("alarmTitle","Ĭ�Ϸ�ֵ�¼�");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","�豸�澯");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data);
	
			var data = new ht.Data();
			data.a("alarmStatus","δȷ��");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev02");
			data.a("alarmOID","11");
			data.a("alarmTitle","Ĭ�Ϸ�ֵ�¼�");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","�豸�澯");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data);
			
		  	var data = new ht.Data();
			data.a("alarmStatus","δȷ��");
			data.a("alarmLevel","1");
			data.a("sourceMOName","fabelDev02");
			data.a("alarmOID","11");
			data.a("alarmTitle","Ĭ�Ϸ�ֵ�¼�");
			data.a("startTime","2014-6-12 15:30:22");
			data.a("alarmType","�豸�澯");
			data.a("repeatCount","2");
			data.a("lastTime","2014-6-12 15:30:22");
			data.a("sourceMOIPAddress","192.168.1.200"); 
			dataModel.add(data); */
		  	PL.userId = Date.parse(new Date());  
      		PL.joinListen('alarmNotify');
      
      		//columnModel.getDataByTag('moClassName').setWidth(10); 

			//ʹ��JQuery�Ӻ�̨��ȡJSON��ʽ������ 
		  	var uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/getViewColumn?userID="+$("#userID").val();;
			 $.ajax( {
				type : "post",
				url : uri,//���������ַ
				contentType : "json",
				dataType : "json",
				success : function(data) {
					eval(data.evalStr);
				},
				//�������Ĵ���
				error : function(XMLHttpRequest, textStatus, errorThrown) { 
					alert("�������1");
				}
			});
			
			//ʹ��JQuery�Ӻ�̨��ȡJSON��ʽ������ 
			 
		  	 uri = "${pageContext.request.contextPath}/monitor/alarmMgr/alarmPanel/loadActiveAlarmList?userID="+$("#userID").val();;
			 $.ajax( {
				type : "post",
				url : uri,//���������ַ
				contentType : "json",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, item) {  
						dataModel.deserialize(item);
					}); 
				},
				//�������Ĵ���
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown.toString());
					alert("�������2");
				}
			}); 
			//p_join_listen('/alarmNotify'); 
	 }
 	/**
	 function strToJson(){ 
		return JSON.parse('({"v":"2.9","d":[{"c":"ht.Data","i":123321,"a":{"alarmID":123321,"alarmDefineID":0,"alarmOID":"0.0.0.0.0.1.1.1","alarmTitle":"alarm dispatcher Test","sourceMOID":1,"sourceMOName":"Alarm Test","sourceMOIPAddress":"192.168.1.35","moclassID":1,"moid":1,"moName":"192.168.1.35","alarmLevel":10,"alarmLevelDescr":"����","alarmType":2,"alarmTypeDescr":"���ܸ澯","startTime":1405878585000,"lastTime":1405950585000,"repeatCount":2,"upgradeCount":0,"alarmStatus":3,"confirmer":null,"confirmTime":null,"confirmInfo":null,"cleaner":null,"cleanTime":null,"cleanInfo":null,"dispatchUser":null,"dispatchID":null,"dispatchTime":null,"dispatchInfo":null,"alarmContent":null}}]})'); 
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
	<span id="emergency">����10</span>
	<span id="severity">����11</span>
	<span id="ordinary">һ��12</span>
	<span id="prompt">��ʾ13</span>
	<span id="clear">���11</span>
	<span id="uncertain">δȷ��11</span>
 
	<select id="hour">
		<option>
			ǰһСʱ
		</option>
		<option>
			ǰ��Сʱ
		</option>
		<option>
			ǰһ��
		</option>
	</select>

	<button id="look" onclick="look();">
		�鿴
	</button>
	<button id="confirm">
		ȷ��
	</button>
	<button id="cConfirm">
		ȡ��ȷ��
	</button>
	<button id="distributed">
		�ɷ�
	</button>
	<button id="bClear">
		���
	</button>
	<button id="delete">
		ɾ��
	</button>
	<button id="cView">
		�Զ�����ͼ
	</button>

	<label id="sel">
		ѡ��:
	</label>
	<select id="mView">
		<option>
			�ҵ���ͼ1
		</option>
		<option>
			�ҵ���ͼ2
		</option>
	</select>
  </body>
</html>