package com.fable.insightview.monitor.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.gridstertabs.Grid;
import com.fable.insightview.platform.core.gridstertabs.Grid.GridSterWidget;
import com.fable.insightview.platform.core.gridstertabs.container.Vbox;
import com.fable.insightview.platform.core.gridstertabs.widget.DataTables;
import com.fable.insightview.platform.core.gridstertabs.widget.EmbedWidget;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.BarChart;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.ChartMark;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.ChartSeries;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.GaugeChart;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.GaugeChartSeries;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.LineChart;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.PieChart;
import com.fable.insightview.platform.core.gridstertabs.widget.chart.PieChartSeries;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.database.service.impl.OracleServiceImpl;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.mobject.service.IMobjectInfoService;
import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;
import com.fable.insightview.monitor.portal.service.IEditInfoService;
import com.fable.insightview.monitor.portal.service.IWidgetInfoService;
import com.fable.insightview.monitor.topo.entity.TopoBean;

public class DrawPortalWidgetUtil {
	
	private final Logger logger = LoggerFactory.getLogger(DrawPortalWidgetUtil.class);
	
//	EditInfoController editContr = new EditInfoController();
	
	IWidgetInfoService service = (IWidgetInfoService) BeanLoader.getBean("widgetInfoService");
	IMobjectInfoService mobjectInfoService = (IMobjectInfoService)BeanLoader.getBean("mobjectInfoServiceImpl");
    IEditInfoService editService = (IEditInfoService)BeanLoader.getBean("editInfoServiceImpl");
    IOracleService orclService = (IOracleService)BeanLoader.getBean("oracleServiceImpl");
    IMiddlewareService middlewareService = (IMiddlewareService)BeanLoader.getBean("middlewareServiceImpl");
    
	public void drawWidget(Grid grid,NodeList widgetNodes,HttpServletRequest request,Vbox vbox,Vbox vboxGauge,Grid gridGauge,String moType,Map<String,String> urlMaps){
		
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		List<MObjectDefBean> mobLst=mobjectInfoService.getMObjectForEdit();
		if(mobLst!=null){
			for (int i = 0; i < mobLst.size(); i++) {
				mapping.put(mobLst.get(i).getClassName(), mobLst.get(i).getClassId());
			}
		}
		//循环部件
		for (int k = 0; k < widgetNodes.getLength(); k++) {
			GridSterWidget gridSterWidget = grid.new GridSterWidget();
			GridSterWidget gridSterWidget2 = gridGauge.new GridSterWidget();
			Element widgetElm = (Element) widgetNodes.item(k);
			if (widgetElm.getAttribute("colspan") == "") {
				gridSterWidget.setWidth("30");
			} else {
				gridSterWidget.setWidth(widgetElm.getAttribute("colspan"));
			}
			if (widgetElm.getAttribute("rowspan") == "") {
				gridSterWidget.setHeight("8");
			} else {
				gridSterWidget.setHeight(widgetElm.getAttribute("rowspan"));
			}
			if (widgetElm.getAttribute("col") == "") {
				gridSterWidget.setCol("1");
			} else {
				gridSterWidget.setCol(widgetElm.getAttribute("col"));
			}
			if (widgetElm.getAttribute("row") == "") {
				gridSterWidget.setRow("1");
			} else {
				gridSterWidget.setRow(widgetElm.getAttribute("row"));
			}
			String widgetNameAndId = widgetElm.getAttribute("name");
			String widgetName = widgetNameAndId.split("#")[0]+widgetNameAndId.split("#")[2];
			String widgetId = widgetNameAndId.split("#")[1];
			String editUrl = service.getWidgetByWidgetId(widgetId).getEditUrl();
			gridSterWidget.setLiName("L" + widgetName);
			NodeList chartNodes = widgetElm.getElementsByTagName("chart");
			NodeList embedNodes = widgetElm.getElementsByTagName("embed");
			
			for (int j = 0; j < chartNodes.getLength(); j++) {
				Element chartNode = (Element) chartNodes.item(j);
				if (chartNode.getNodeName().equals("chart")) {
					NodeList itemNodes = chartNode.getElementsByTagName("item");
					//柱状图
					if ("bar".equals(chartNode.getAttribute("type"))) {
						BarChart barChart = new BarChart();
//						if("platform".equals(moType)){
//							barChart.setIsEdit("true");
//						}
						barChart.setChartType("bar");
						String dataSourceUrl = chartNode.getAttribute("dataSourceUrl");
						if (dataSourceUrl.contains("?") == true) {
							String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
							String moId = request.getParameter("moID");
							String moClass = request.getParameter("moClass");
							String ifMoId = request.getParameter("IfMOID");
							if (moId == null && moClass == null) {
								WidgetInfoBean widBean = this.service.getWidgetByWidgetId(widgetId);
								String filter = widBean.getWidgetFilter();
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("moClassID", mapping.get(filter));
								if ("Host".equals(filter) || "Router".equals(filter)  || "Switcher".equals(filter)  || "PHost".equals(filter) 
									 || "VHost".equals(filter)  || "VM".equals(filter)  || "Interface".equals(filter) ) {
									List<MODevice> deviceLst = editService.getDeviceName(params);
									if (deviceLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + deviceLst.get(0).getMoid() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + deviceLst.get(0).getMoid();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								} else if ("Oracle".equals(filter) || "Mysql".equals(filter)  || "DB2_DB".equals(filter)  || "DB2_INSTANCE".equals(filter)) {
									List<MODBMSServerBean> moLst= orclService.queryAll();
									if (moLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + moLst.get(0).getMoid() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + moLst.get(0).getMoid();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								} else {
									List<MOMiddleWareJMXBean> moLst= middlewareService.queryAll();
									if (moLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + moLst.get(0).getMoId() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + moLst.get(0).getMoId();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								}
							} else {
								if (oldUrlParams.contains("&") == true) {
									if (oldUrlParams.contains("IfMOID") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass
												+ "&IfMOID=" + ifMoId;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									} else {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}
								} else {
									String newUrlParams = "?moID=" + moId;
									dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
								}
							}
						}
						urlMaps.put(widgetName, dataSourceUrl);
						barChart.setDataSourceUrl(dataSourceUrl);
						barChart.setWidgetName(widgetName);
						barChart.setWidgetId(widgetId);
						barChart.setEditUrl(editUrl);
						barChart.setWidgetTitle(widgetElm.getAttribute("title"));
						if(widgetElm.hasAttribute("isEdit") == true){
							barChart.setIsEdit(widgetElm.getAttribute("isEdit"));
						}
						barChart.setWidth(widgetElm.getAttribute("width"));
						for (int l = 0; l < itemNodes.getLength(); l++) {
							ChartSeries chartSeries = new ChartSeries();
							Element itemNode = (Element) itemNodes.item(l);
							chartSeries.setFieldName(itemNode.getAttribute("field"));
							barChart.setFieldName(itemNode.getAttribute("field"));
							chartSeries.setLabel(itemNode.getAttribute("label"));
							chartSeries.setType("bar");
							List<ChartMark> chartMarkPointLst = new ArrayList<ChartMark>();
							List<ChartMark> chartMarkLineLst = new ArrayList<ChartMark>();
							NodeList markPoints = itemNode.getElementsByTagName("markPoint");
							NodeList markLines = itemNode.getElementsByTagName("markLine");
							for (int m = 0; m < markPoints.getLength(); m++) {
								ChartMark chartMark = new ChartMark();
								chartMark.setName(((Element) markPoints.item(m)).getAttribute("label"));
								chartMark.setType(((Element) markPoints.item(m)).getAttribute("type"));
								chartMarkPointLst.add(chartMark);
							}
							for (int m = 0; m < markLines.getLength(); m++) {
								ChartMark chartMark = new ChartMark();
								chartMark.setName(((Element) markLines.item(m)).getAttribute("label"));
								chartMark.setType(((Element) markLines.item(m)).getAttribute("type"));
								chartMarkLineLst.add(chartMark);
							}
							ChartMark[] chartMarkPoint = chartMarkPointLst
									.toArray(new ChartMark[chartMarkPointLst.size()]);
							ChartMark[] chartMarkLine = chartMarkLineLst
									.toArray(new ChartMark[chartMarkLineLst.size()]);
							chartSeries.setChartMarkLine(chartMarkLine);
							chartSeries.setChartMarkPoint(chartMarkPoint);
							barChart.addChartItem(chartSeries);
						}
						barChart.setWidgetIndex(String.valueOf(k));
						barChart.setWidgetWidth(gridSterWidget.getWidth());
						vbox.addComponent(barChart);
						vboxGauge.addComponent(barChart);
					}
					//饼图
					else if ("pie".equals(chartNode.getAttribute("type"))) {
						PieChart pieChart = new PieChart();
//						if("platform".equals(moType)){
//							pieChart.setIsEdit("true");
//						}
						pieChart.setChartType("pie");
						pieChart.setDataSourceUrl(chartNode.getAttribute("dataSource"));
						pieChart.setWidgetName(widgetName);
						pieChart.setWidgetId(widgetId);
						pieChart.setEditUrl(editUrl);
						pieChart.setWidgetTitle(widgetElm.getAttribute("title"));
						if(widgetElm.hasAttribute("isEdit") == true){
							pieChart.setIsEdit(widgetElm.getAttribute("isEdit"));
						}
						pieChart.setWidth(widgetElm.getAttribute("width"));
						for (int l = 0; l < itemNodes.getLength(); l++) {
							PieChartSeries pieChartSeries = new PieChartSeries();
							Element itemNode = (Element) itemNodes.item(l);
							pieChartSeries.setFieldName(itemNode.getAttribute("field"));
							pieChartSeries.setLabel(itemNode.getAttribute("label"));
							pieChart.addChartItem(pieChartSeries);
						}
						pieChart.setWidgetIndex(String.valueOf(k));
						pieChart.setWidgetWidth(gridSterWidget.getWidth());
						vbox.addComponent(pieChart);
						vboxGauge.addComponent(pieChart);
					}
					//曲线图
					else if ("line".equals(chartNode.getAttribute("type"))) {
						LineChart lineChart = new LineChart();
//						if("platform".equals(moType)){
//							lineChart.setIsEdit("true");
//						}
						lineChart.setChartType("line");
						lineChart.setDataSourceUrl(chartNode.getAttribute("dataSource"));
						lineChart.setWidgetName(widgetName);
						lineChart.setWidgetId(widgetId);
						lineChart.setEditUrl(editUrl);
						lineChart.setWidgetTitle(widgetElm.getAttribute("title"));
						if(widgetElm.hasAttribute("isEdit") == true){
							lineChart.setIsEdit(widgetElm.getAttribute("isEdit"));
						}
						lineChart.setWidth(widgetElm.getAttribute("width"));
						for (int l = 0; l < itemNodes.getLength(); l++) {
							ChartSeries chartSeries = new ChartSeries();
							Element itemNode = (Element) itemNodes.item(l);
							chartSeries.setFieldName(itemNode.getAttribute("field"));
							chartSeries.setLabel(itemNode.getAttribute("label"));
							List<ChartMark> chartMarkPointLst = new ArrayList<ChartMark>();
							List<ChartMark> chartMarkLineLst = new ArrayList<ChartMark>();
							NodeList markPoints = itemNode.getElementsByTagName("markPoint");
							NodeList markLines = itemNode.getElementsByTagName("markLine");
							for (int m = 0; m < markPoints.getLength(); m++) {
								ChartMark chartMark = new ChartMark();
								chartMark.setName(((Element) markPoints.item(m)).getAttribute("label"));
								chartMark.setType(((Element) markPoints.item(m)).getAttribute("type"));
								chartMarkPointLst.add(chartMark);
							}
							for (int m = 0; m < markLines.getLength(); m++) {
								ChartMark chartMark = new ChartMark();
								chartMark.setName(((Element) markLines.item(m)).getAttribute("label"));
								chartMark.setType(((Element) markLines.item(m)).getAttribute("type"));
								chartMarkLineLst.add(chartMark);
							}
							ChartMark[] chartMarkPoint = chartMarkPointLst
									.toArray(new ChartMark[chartMarkPointLst.size()]);
							ChartMark[] chartMarkLine = chartMarkLineLst
									.toArray(new ChartMark[chartMarkLineLst.size()]);
							chartSeries.setChartMarkLine(chartMarkLine);
							chartSeries.setChartMarkPoint(chartMarkPoint);
							lineChart.addChartItem(chartSeries);
						}
						lineChart.setWidgetIndex(String.valueOf(k));
						lineChart.setWidgetWidth(gridSterWidget.getWidth());
						vbox.addComponent(lineChart);
						vboxGauge.addComponent(lineChart);
					} 
					//仪表盘
					else if ("gauge".equals(chartNode.getAttribute("type"))) {
						GaugeChart gaugeChart = new GaugeChart();
//						if("platform".equals(moType)){
//							gaugeChart.setIsEdit("true");
//						}
						gaugeChart.setChartType("gauge");
						String dataSourceUrl = chartNode
								.getAttribute("dataSourceUrl");
						if (dataSourceUrl.contains("?") == true) {
							String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
							String moId = request.getParameter("moID");
							String moClass = request.getParameter("moClass");
							String ifMoId = request.getParameter("IfMOID");
							if (moId == null && moClass == null) {

								WidgetInfoBean widBean = this.service.getWidgetByWidgetId(widgetId);
								String filter = widBean.getWidgetFilter();
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("moClassID", mapping.get(filter));
								if ("Host".equals(filter) || "Router".equals(filter)  || "Switcher".equals(filter)  || "PHost".equals(filter) 
									 || "VHost".equals(filter)  || "VM".equals(filter)  || "Interface".equals(filter) ) {
									List<MODevice> deviceLst = editService.getDeviceName(params);
									if (deviceLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + deviceLst.get(0).getMoid() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + deviceLst.get(0).getMoid();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								} else if ("Oracle".equals(filter) || "Mysql".equals(filter)  || "DB2_DB".equals(filter)  || "DB2_INSTANCE".equals(filter)) {
									List<MODBMSServerBean> moLst= orclService.queryAll();
									if (moLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + moLst.get(0).getMoid() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + moLst.get(0).getMoid();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								} else {
									List<MOMiddleWareJMXBean> moLst= middlewareService.queryAll();
									if (moLst.size()>0) {
										if (oldUrlParams.contains("&") == true) {
											String newUrlParams = "?moID=" + moLst.get(0).getMoId() + "&moClass=" + filter.toLowerCase();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										} else {
											String newUrlParams = "?moID=" + moLst.get(0).getMoId();
											dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
										}
									}
								}
							
							} else {
								if (oldUrlParams.contains("&") == true) {
									if (oldUrlParams.contains("IfMOID") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass
												+ "&IfMOID=" + ifMoId;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									} else {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}
								} else {
									String newUrlParams = "?moID=" + moId;
									dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
								}
							}

						}
						urlMaps.put(widgetName, dataSourceUrl);
						gaugeChart.setDataSourceUrl(dataSourceUrl);
						gaugeChart.setWidgetName(widgetName);
						gaugeChart.setWidgetId(widgetId);
						gaugeChart.setEditUrl(editUrl);
						gaugeChart.setWidgetTitle(widgetElm.getAttribute("title"));
						if(widgetElm.hasAttribute("isEdit") == true){
							gaugeChart.setIsEdit(widgetElm.getAttribute("isEdit"));
						}
						gaugeChart.setWidth(widgetElm.getAttribute("width"));
						for (int l = 0; l < itemNodes.getLength(); l++) {
							GaugeChartSeries gaugeChartSeries = new GaugeChartSeries();
							Element itemNode = (Element) itemNodes.item(l);
							gaugeChartSeries.setFieldName(itemNode.getAttribute("field"));
							gaugeChartSeries.setLabel(itemNode.getAttribute("label"));
							gaugeChart.setFieldName(itemNode.getAttribute("field"));
							gaugeChart.addChartItem(gaugeChartSeries);
						}
						gaugeChart.setWidgetIndex(String.valueOf(k));
						gaugeChart.setWidgetWidth(gridSterWidget.getWidth());
						vbox.addComponent(gaugeChart);
//						if(gaugeChart.getWidgetName().contains("Availability") == true){
							vboxGauge.addComponent(gaugeChart);
//						}
						
					} else {
						// 其他类型图表待开发
					}
				}
				//表格
				else if (chartNode.getNodeName().equals("dataTables")) {
					DataTables dataTable = new DataTables();
//					if("platform".equals(moType)){
//						dataTable.setIsEdit("true");
//					}
					dataTable.setChartType("dataTables");
					dataTable.setWidgetName(widgetName);
					dataTable.setWidgetId(widgetId);
					dataTable.setEditUrl(editUrl);
					dataTable.setWidgetTitle(widgetElm.getAttribute("title"));
					if(widgetElm.hasAttribute("isEdit") == true){
						dataTable.setIsEdit(widgetElm.getAttribute("isEdit"));
					}
					dataTable.setWidth(widgetElm.getAttribute("width"));
					dataTable.setDataSourceUrl(chartNode.getAttribute("dataSource"));
					dataTable.setWidgetIndex(String.valueOf(k));
					dataTable.setWidgetWidth(gridSterWidget.getWidth());
					vbox.addComponent(dataTable);
					vboxGauge.addComponent(dataTable);
				}

			}
			
			//嵌入页面处理
			for (int j = 0; j < embedNodes.getLength(); j++) {
				Element embedNode = (Element) embedNodes.item(j);
				EmbedWidget embedWidget = new EmbedWidget();
//				if("platform".equals(moType)){
//					embedWidget.setIsEdit("true");
//				}
				embedWidget.setWidgetName(widgetName);
				embedWidget.setWidgetId(widgetId);
				embedWidget.setEditUrl(editUrl);
				embedWidget.setWidgetTitle(widgetElm.getAttribute("title"));
				if(widgetElm.hasAttribute("isEdit") == true){
					embedWidget.setIsEdit(widgetElm.getAttribute("isEdit"));
				}
				embedWidget.setWidth(widgetElm.getAttribute("width"));
				String url = embedNode.getAttribute("url");
				if (url.contains("?") == true) {
					String oldUrlParams = url.substring(url.indexOf("?"));
					String moId = request.getParameter("moID");
					String moClass = request.getParameter("moClass");
					String ifMoId = request.getParameter("IfMOID");
					
					if (moId == null && moClass == null) {
						if (url.contains("topoId") == true) {
							if (url.contains("{") == true) {
								String newUrlParams = "?topoId=-1";
								url = url.replace(oldUrlParams, newUrlParams);
							} else {
								List<TopoBean> topoLst = editService.getAllTopo();
								List<Integer> topoIds = new ArrayList<Integer>(); 
								if (topoLst.size()>0) {
									for (int i = 0; i < topoLst.size(); i++) {
										topoIds.add(topoLst.get(i).getId());
									}
									if (topoIds.contains(Integer.parseInt(url.split("=")[1])) == true) {
										String newUrlParams = "?topoId="+url.split("=")[1];
										url = url.replace(oldUrlParams, newUrlParams);
									} else {
										String newUrlParams = "?topoId=-1";
										url = url.replace(oldUrlParams, newUrlParams);
									}
								}
								
							}
						} /*else if (url.contains("roomId") == true) {
							if (url.contains("{") == true) {
								try {

									String restUrl = SystemParamInit.getValueByKey("rest.room3d.url");
									String username = SystemParamInit.getValueByKey("rest.username");
									String password = SystemParamInit.getValueByKey("rest.password");

									// 拼接获取单板接口URL
									StringBuffer basePath = new StringBuffer();
									basePath.append(restUrl);
									basePath.append(DataConstant.GETROOM_URL);

									// 设置请求头信息
									HttpHeaders requestHeaders = RestHepler.createHeaders(username,
											password);

									HttpEntity<Object> requestEntity = new HttpEntity<Object>(null,
											requestHeaders);
									RestTemplate rest = new RestTemplate();

									// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
									ResponseEntity<String> rssResponse = rest.exchange(
											basePath.toString(), HttpMethod.POST, requestEntity,
											String.class);
									
									List<Map<String,Object>> roomList = null;
									
									if (null != rssResponse) {
										String datas = editContr.openFilter(request,rssResponse.getBody());
										roomList =  JsonUtil.toList(datas);
									}
									if (roomList.size() > 0) {
										Iterator iter = roomList.get(0).entrySet().iterator();
										Map.Entry entry = (Map.Entry) iter.next();
										Object key = entry.getKey();
										String newUrlParams = "?roomId=" + key + "&viewType=alarmView&drivceType=null";
										url = url.replace(oldUrlParams, newUrlParams);
										logger.info(">>>>>>>>>3dRoomUrl="+url);
									}
								} catch (RestClientException e) {
									logger.error("The request failed.",e);
								} catch (Exception e){
									logger.error("The request failed.",e);
								}
							}
//							else {
//								String newUrlParams = "?roomId="+url.split("=")[1];
//								url = url.replace(oldUrlParams, newUrlParams);
//							}
						} */else {
							url = embedNode.getAttribute("url");
						}
						
					} else {
						if (oldUrlParams.contains("num") == true) {
							String newUrlParams = oldUrlParams.split("&")[0] + "&moClass=" + moClass;
							url = url.replace(oldUrlParams, newUrlParams);
						} else if (oldUrlParams.contains("perfKind") == true) {
							String newUrlParams = "?moID=" + moId + "&moClass=" + moClass + "&"
									+ oldUrlParams.split("&")[1];
							url = url.replace(oldUrlParams, newUrlParams);
						} else if (oldUrlParams.contains("varClass") == true) {
							String newUrlParams = "?moID=" + moId + "&moClass=" + moClass + "&"
									+ oldUrlParams.split("&")[1];
							url = url.replace(oldUrlParams, newUrlParams);
						} else {
							if (oldUrlParams.contains("IfMOID") == true) {
								String newUrlParams = "?moID=" + moId + "&moClass=" + moClass + "&IfMOID="
										+ ifMoId;
								url = url.replace(oldUrlParams, newUrlParams);
							} else {
								String newUrlParams = "?moID=" + moId + "&moClass=" + moClass;
								url = url.replace(oldUrlParams, newUrlParams);
							}
						}
					}
				}
				urlMaps.put(widgetName, url);
				embedWidget.setUrl(url);
				embedWidget.setWidgetIndex(String.valueOf(k));
				embedWidget.setWidgetWidth(gridSterWidget.getWidth());
				vbox.addComponent(embedWidget);
				vboxGauge.addComponent(embedWidget);
			}
			gridSterWidget.addComponent(vbox);
			gridSterWidget2.addComponent(vboxGauge);
			grid.addGridSterWidget(gridSterWidget);
			gridGauge.addGridSterWidget(gridSterWidget2);
		}
		
	
	}
	
}
