package com.fable.insightview.monitor.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

public class DrawWidgetUtil {
	
	public void drawWidget(Grid grid,NodeList widgetNodes,HttpServletRequest request,Vbox vbox,Vbox vboxGauge,Grid gridGauge){
		
		//循环部件
		for (int k = 0; k < widgetNodes.getLength(); k++) {
			GridSterWidget gridSterWidget = grid.new GridSterWidget();
			GridSterWidget gridSterWidget2 = gridGauge.new GridSterWidget();
			Element widgetElm = (Element) widgetNodes.item(k);
			if (widgetElm.getAttribute("colspan") == "") {
				gridSterWidget.setWidth("16");
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
			gridSterWidget.setLiName("L" + widgetElm.getAttribute("name"));
			NodeList chartNodes = widgetElm.getElementsByTagName("chart");
			NodeList embedNodes = widgetElm.getElementsByTagName("embed");
			
			for (int j = 0; j < chartNodes.getLength(); j++) {
				Element chartNode = (Element) chartNodes.item(j);
				if (chartNode.getNodeName().equals("chart")) {
					NodeList itemNodes = chartNode.getElementsByTagName("item");
					//柱状图
					if ("bar".equals(chartNode.getAttribute("type"))) {
						BarChart barChart = new BarChart();
						barChart.setChartType("bar");
						String dataSourceUrl = chartNode.getAttribute("dataSourceUrl");
						if (dataSourceUrl.contains("?") == true) {
							String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
							String moId = request.getParameter("moID");
							String moClass = request.getParameter("moClass");
							String ifMoId = request.getParameter("IfMOID");
							if (moId == null && moClass == null) {
								// 暂未处理
							} else {
								if (oldUrlParams.contains("&") == true) {
									if (oldUrlParams.contains("IfMOID") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass
												+ "&IfMOID=" + ifMoId;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									} else if (oldUrlParams.contains("perfKind") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass + "&"
										+ oldUrlParams.split("&")[1];
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}else {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}
								} else {
									String newUrlParams = "?moID=" + moId;
									dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
								}
							}
						}
						barChart.setDataSourceUrl(dataSourceUrl);
						barChart.setWidgetName(widgetElm.getAttribute("name"));
						barChart.setWidgetTitle(widgetElm.getAttribute("title"));
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
						pieChart.setChartType("pie");
						String dataSourceUrl = chartNode.getAttribute("dataSourceUrl");
						
						if (dataSourceUrl.contains("?") == true) {
							String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
							String moId = request.getParameter("moID");
							String moClass = request.getParameter("moClass");
							String ifMoId = request.getParameter("IfMOID");
							if (moId == null && moClass == null) {
								// 暂未处理
							} else {
								if (oldUrlParams.contains("&") == true) {
									if (oldUrlParams.contains("IfMOID") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass
												+ "&IfMOID=" + ifMoId;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									} else if (oldUrlParams.contains("perfKind") == true) {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass + "&"
										+ oldUrlParams.split("&")[1];
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}else {
										String newUrlParams = "?moID=" + moId + "&moClass=" + moClass;
										dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
									}
								} else {
									String newUrlParams = "?moID=" + moId;
									dataSourceUrl = dataSourceUrl.replace(oldUrlParams, newUrlParams);
								}
							}
						}
						pieChart.setDataSourceUrl(dataSourceUrl);
						pieChart.setWidgetName(widgetElm.getAttribute("name"));
						pieChart.setWidgetTitle(widgetElm.getAttribute("title"));
						pieChart.setWidth(widgetElm.getAttribute("width"));
						for (int l = 0; l < itemNodes.getLength(); l++) {
//							PieChartSeries pieChartSeries = new PieChartSeries();
							Element itemNode = (Element) itemNodes.item(l);
//							pieChartSeries.setFieldName(itemNode.getAttribute("field"));
							pieChart.setFieldName(itemNode.getAttribute("field"));
//							pieChartSeries.setLabel(itemNode.getAttribute("label"));
//							pieChartSeries.setType("pie");
//							pieChart.addChartItem(pieChartSeries);
							
						}
						pieChart.setWidgetIndex(String.valueOf(k));
						pieChart.setWidgetWidth(gridSterWidget.getWidth());
						vbox.addComponent(pieChart);
						vboxGauge.addComponent(pieChart);
					}
					//曲线图
					else if ("line".equals(chartNode.getAttribute("type"))) {
						LineChart lineChart = new LineChart();
						lineChart.setChartType("line");
						lineChart.setDataSourceUrl(chartNode.getAttribute("dataSource"));
						lineChart.setWidgetName(widgetElm.getAttribute("name"));
						lineChart.setWidgetTitle(widgetElm.getAttribute("title"));
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
						gaugeChart.setChartType("gauge");
						String dataSourceUrl = chartNode
								.getAttribute("dataSourceUrl");
						if (dataSourceUrl.contains("?") == true) {
							String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
							String moId = request.getParameter("moID");
							String moClass = request.getParameter("moClass");
							String ifMoId = request.getParameter("IfMOID");
							if (moId == null && moClass == null) {
							//	暂未处理
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
						gaugeChart.setDataSourceUrl(dataSourceUrl);
						gaugeChart.setWidgetName(widgetElm.getAttribute("name"));
						gaugeChart.setWidgetTitle(widgetElm.getAttribute("title"));
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
					dataTable.setChartType("dataTables");
					dataTable.setWidgetName(widgetElm.getAttribute("name"));
					dataTable.setWidgetTitle(widgetElm.getAttribute("title"));
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
				embedWidget.setWidgetName(widgetElm.getAttribute("name"));
				embedWidget.setWidgetTitle(widgetElm.getAttribute("title"));
				embedWidget.setWidth(widgetElm.getAttribute("width"));
				String url = embedNode.getAttribute("url");
				if (url.contains("?") == true) {
					String oldUrlParams = url.substring(url.indexOf("?"));
					String moId = request.getParameter("moID");
					String moClass = request.getParameter("moClass");
					String ifMoId = request.getParameter("IfMOID");
					if (moId == null && moClass == null) {
						url = embedNode.getAttribute("url");
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
						} else if (oldUrlParams.contains("siteType") == true) {
							String newUrlParams = "?moID=" + moId + "&"
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
