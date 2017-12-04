package com.fable.insightview.monitor.portal.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.portal.Grid;
import com.fable.insightview.platform.core.portal.Portal;
import com.fable.insightview.platform.core.portal.Grid.Column;
import com.fable.insightview.platform.core.portal.container.Vbox;
import com.fable.insightview.platform.core.portal.widget.Chart;
import com.fable.insightview.platform.core.portal.widget.DataTables;
import com.fable.insightview.platform.core.portal.widget.EmbedWidget;
import com.fable.insightview.platform.core.portal.widget.chart.BarChart;
import com.fable.insightview.platform.core.portal.widget.chart.ChartMark;
import com.fable.insightview.platform.core.portal.widget.chart.ChartSeries;
import com.fable.insightview.platform.core.portal.widget.chart.GaugeChart;
import com.fable.insightview.platform.core.portal.widget.chart.GaugeChartSeries;
import com.fable.insightview.platform.core.portal.widget.chart.LineChart;
import com.fable.insightview.platform.core.portal.widget.chart.PieChart;
import com.fable.insightview.platform.core.portal.widget.chart.PieChartSeries;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.monitor.portal.entity.WidgetGroupInfoBean;
import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;
import com.fable.insightview.monitor.portal.exception.ParsePortalException;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;
import com.fable.insightview.monitor.portal.mapper.WidgetGroupInfoMapper;
import com.fable.insightview.monitor.portal.mapper.WidgetInfoMapper;
import com.fable.insightview.monitor.portal.service.DateUtil;

@Controller
@RequestMapping("/monitor/portal")
public class PortalController {

	private final Logger logger = LoggerFactory
			.getLogger(PortalController.class);
	DateUtil dateUtil = new DateUtil();
	@Autowired
	PortalInfoMapper portalInfoMapper;
	@Autowired
	WidgetInfoMapper widgetInfoMapper;
	@Autowired
	WidgetGroupInfoMapper widgetGroupInfoMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	MODeviceMapper moDeviceMapper;

	String portalContent = null;
	String widgetFilter = null;

	/**
	 * 加载视图页面
	 * 
	 * @return
	 */
	@RequestMapping("/showPortalView")
	public ModelAndView showPortalView(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("begin showPortalView....");
		/**
		 * response.setContentType("application/xml");
		 * response.setHeader("Cache-Control", "public");
		 * response.setHeader("Pragma", "Pragma"); //本地缓存10分钟过期 //
		 * response.setDateHeader("Expires",
		 * System.currentTimeMillis()+10*60*1000); //设置Last-Modified
		 * response.setDateHeader("Last-Modified", System.currentTimeMillis());
		 * //判断请求中的If-Modified-Since头信息
		 * if(request.getHeader("If-Modified-Since") == null){
		 */
		// 首次访问或者用户按Ctl+F5，从文件系统重新读取图片
		String xml = portalContent;
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			logger.error("XML格式有误!",e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		NodeList opPortalNode = doc.getElementsByTagName("portal");
		Element opPortalElm = (Element) opPortalNode.item(0);
		String portalName = opPortalElm.getAttribute("name");
		Portal portal = new Portal();
		portal.setPortalName(portalName);
		if (opPortalElm == null) {
			logger.error("缺少portal标签");
		}
		NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
		int gridNum = gridNodes.getLength();
		if (gridNum == 0) {
			logger.error("portal下缺少grid标签");
		}
		Grid grid = new Grid();
		NodeList columnNodes = ((Element) gridNodes.item(0)).getElementsByTagName("column");
		for (int i = 0; i < columnNodes.getLength(); i++) {
			Vbox vbox = new Vbox();
			String width = ((Element) columnNodes.item(i)).getAttribute("width");
			Column column = grid.new Column();
			column.setWidth(width);
			NodeList widgetNodes = ((Element) columnNodes.item(i)).getElementsByTagName("widget");
			for (int k = 0; k < widgetNodes.getLength(); k++) {
				Element widgetElm = (Element) widgetNodes.item(k);
				NodeList chartNodes = widgetElm.getElementsByTagName("chart");
				NodeList embedNodes = widgetElm.getElementsByTagName("embed");
				for (int j = 0; j < chartNodes.getLength(); j++) {
					Element chartNode = (Element) chartNodes.item(j);
					if (chartNode.getNodeName().equals("chart")) {
						NodeList itemNodes = chartNode.getElementsByTagName("item");
						if ("bar".equals(chartNode.getAttribute("type"))) {
							BarChart barChart = new BarChart();
							String dataSourceUrl = chartNode.getAttribute("dataSourceUrl");
							if (dataSourceUrl.contains("?") == true) {
								String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
								String moId = request.getParameter("moID");
								
								logger.info("moId= " + moId);
								String moClass = request.getParameter("moClass");
								
								logger.info("moClass= " + moClass);
								String ifMoId = request.getParameter("IfMOID");
								
								logger.info("ifName= " + ifMoId);
								if (moId == null && moClass == null) {
									logger.info("暂未处理！");
								} else {
									
									if (oldUrlParams.contains("&") == true) {
										if (oldUrlParams.contains("IfMOID") == true) {
											String newUrlParams = "?moID="
													+ moId + "&moClass="
													+ moClass + "&IfMOID="
													+ ifMoId;
											dataSourceUrl = dataSourceUrl
													.replace(oldUrlParams,
															newUrlParams);
										} else {
											String newUrlParams = "?moID="
													+ moId + "&moClass="
													+ moClass;
											dataSourceUrl = dataSourceUrl
													.replace(oldUrlParams,
															newUrlParams);
										}
									} else {
										String newUrlParams = "?moID=" + moId;
										dataSourceUrl = dataSourceUrl.replace(
												oldUrlParams, newUrlParams);
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
							vbox.addComponent(barChart);
						} else if ("pie".equals(chartNode.getAttribute("type"))) {
							PieChart pieChart = new PieChart();
							pieChart.setDataSourceUrl(chartNode.getAttribute("dataSource"));
							pieChart.setWidgetName(widgetElm.getAttribute("name"));
							pieChart.setWidgetTitle(widgetElm.getAttribute("title"));
							pieChart.setWidth(widgetElm.getAttribute("width"));
							for (int l = 0; l < itemNodes.getLength(); l++) {
								PieChartSeries pieChartSeries = new PieChartSeries();
								Element itemNode = (Element) itemNodes.item(l);
								pieChartSeries.setFieldName(itemNode.getAttribute("field"));
								pieChartSeries.setLabel(itemNode.getAttribute("label"));
								pieChart.addChartItem(pieChartSeries);
							}
							vbox.addComponent(pieChart);
						} else if ("line".equals(chartNode.getAttribute("type"))) {
							LineChart lineChart = new LineChart();
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
							vbox.addComponent(lineChart);
						} else if ("gauge".equals(chartNode.getAttribute("type"))) {
							
							GaugeChart gaugeChart = new GaugeChart();
							String dataSourceUrl = chartNode.getAttribute("dataSourceUrl");
							if (dataSourceUrl.contains("?") == true) {
								String oldUrlParams = dataSourceUrl.substring(dataSourceUrl.indexOf("?"));
								String moId = request.getParameter("moID");
								
								logger.info("moId= " + moId);
								String moClass = request.getParameter("moClass");
								
								logger.info("moClass= " + moClass);
								String ifMoId = request.getParameter("IfMOID");
								
								logger.info("ifName= " + ifMoId);
								if (moId == null && moClass == null) {
									logger.info("暂未处理！");
								} else {
									if (oldUrlParams.contains("&") == true) {
										if (oldUrlParams.contains("IfMOID") == true) {
											String newUrlParams = "?moID="
													+ moId + "&moClass="
													+ moClass + "&IfMOID="
													+ ifMoId;
											dataSourceUrl = dataSourceUrl
													.replace(oldUrlParams,
															newUrlParams);
										} else {
											String newUrlParams = "?moID="
													+ moId + "&moClass="
													+ moClass;
											dataSourceUrl = dataSourceUrl
													.replace(oldUrlParams,
															newUrlParams);
										}

									} else {
										String newUrlParams = "?moID=" + moId;
										dataSourceUrl = dataSourceUrl.replace(
												oldUrlParams, newUrlParams);
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
								gaugeChart.addChartItem(gaugeChartSeries);
							}
							vbox.addComponent(gaugeChart);
						} else {
							logger.info("暂未处理！");
						}
					} else if (chartNode.getNodeName().equals("dataTables")) {
						DataTables dataTable = new DataTables();
						dataTable.setWidgetName(widgetElm.getAttribute("name"));
						dataTable.setWidgetTitle(widgetElm.getAttribute("title"));
						dataTable.setWidth(widgetElm.getAttribute("width"));
						dataTable.setDataSourceUrl(chartNode.getAttribute("dataSource"));
						vbox.addComponent(dataTable);
					}

				}
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
						
						logger.info("moId= " + moId);
						String moClass = request.getParameter("moClass");
						
						logger.info("moClass= " + moClass);
						String ifMoId = request.getParameter("IfMOID");
						
						logger.info("ifName= " + ifMoId);
						if (moId == null && moClass == null) {
							logger.info("暂未处理！");
						} else {
							
							if (oldUrlParams.contains("num") == true) {
								String newUrlParams = oldUrlParams.split("&")[0]
										+ "&moClass=" + moClass;
								url = url.replace(oldUrlParams, newUrlParams);
							} else {
								if (oldUrlParams.contains("IfMOID") == true) {
									String newUrlParams = "?moID=" + moId
											+ "&moClass=" + moClass
											+ "&IfMOID=" + ifMoId;
									url = url.replace(oldUrlParams,
											newUrlParams);
								} else {
									String newUrlParams = "?moID=" + moId
											+ "&moClass=" + moClass;
									url = url.replace(oldUrlParams,
											newUrlParams);
								}

							}
						}
					}
					embedWidget.setUrl(url);
					vbox.addComponent(embedWidget);
				}
			}
			column.addComponent(vbox);
			grid.addColumn(column);
		}
		portal.setGrid(grid);
		StringBuilder sbPortal = portal.draw();
		
		logger.info("xml解析生成的js >>>>>>>>> " + sbPortal.toString());
		request.setAttribute("portalJS", sbPortal.toString());
		/**
		 * } else { //返回304，告诉浏览器使用本地缓存
		 * System.out.println(">>>>>>>>>304>>>>>>>>>");
		 * response.setStatus(HttpServletResponse.SC_NOT_MODIFIED); }
		 */
		return new ModelAndView("monitor/portal/portalView");
	}

	@RequestMapping("/initPortalContent")
	@ResponseBody
	public boolean initPortalContent(PortalInfoBean portalInfoBean) {
		
		if (portalInfoBean.getPortalContent() != null) {
			portalContent = portalInfoBean.getPortalContent();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 加载portal编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toShowPortalEdit")
	public ModelAndView toShowPortalEdit(String navigationBar) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String portalDefine = "<?xml version='1.0' encoding='UTF-8'?>";
		request.setAttribute("portalDefine", portalDefine);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("monitor/portal/portalEdit");
	}

	@RequestMapping("/addPortalInfo")
	@ResponseBody
	public boolean addPortalInfo(PortalInfoBean portalInfoBean) {
		
		logger.info(">>>>>>>>>>新增portalXML");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		DocumentBuilder db = null;
		Document doc = null;
		portalInfoBean.setPortalType(1);
		portalInfoBean.setPortalDesc("");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		int i = portalInfoMapper.insertPortal(portalInfoBean);
		
		logger.info(">>>>>>>>>>新增portalXML over ");
		if (i > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping("/modifyPortalInfo")
	@ResponseBody
	public boolean modifyPortalInfo(PortalInfoBean portalInfoBean) {
		
		logger.info("进入修改方法");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		portalInfoBean.setPortalType(1);
		portalInfoBean.setPortalDesc("");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		int i = portalInfoMapper.updatePortal(portalInfoBean);
		
		logger.info("修改结束");
		logger.info("修改结果 = " + i);
		if (i > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findWidgetTreeVal")
	@ResponseBody
	public Map<String, Object> findWidgetTreeVal() throws Exception {
		
		List<WidgetInfoBean> menuLst = null;
		menuLst = this.widgetInfoMapper.getAllWidget();
		// if(widgetFilter == null){
		// menuLst = this.widgetInfoMapper.getAllWidget();
		// }else{
		// WidgetInfoBean widgetInfoBean = new WidgetInfoBean();
		// widgetInfoBean.setWidgetFilter(widgetFilter);
		// menuLst = this.widgetInfoMapper.getWidgetByFilter(widgetInfoBean);
		// }

		List<WidgetGroupInfoBean> wGroupLst = this.widgetGroupInfoMapper
				.getAllWidgetGroup();
		for (int i = 0; i < menuLst.size(); i++) {
			menuLst.get(i).setWidgetGroupId(
					"W" + menuLst.get(i).getWidgetGroupId());
		}
		for (int i = 0; i < wGroupLst.size(); i++) {
			WidgetInfoBean bean = new WidgetInfoBean();
			bean.setWidgetId("W" + wGroupLst.get(i).getWidgetGroupId());
			bean.setWidgetGroupId("0");
			bean.setWidgetName(wGroupLst.get(i).getWidgetGroupName());
			menuLst.add(bean);
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 加载widget页面
	 * 
	 * @return
	 */
	@RequestMapping("/toShowWidget")
	public ModelAndView toShowWidget() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		widgetFilter = request.getParameter("widgetFilter");
		return new ModelAndView("monitor/portal/widgetInfo");
	}

	/**
	 * 加载第一个widget
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showOneWidget")
	@ResponseBody
	public WidgetInfoBean showOneWidget(String widgetId) throws Exception {
		logger.info(">>>>>>>>>widgetId= " + widgetId);
		return this.widgetInfoMapper.getWidgetByWidgetId(widgetId);
	}

	/**
	 * 加载第一个Portal
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showOnePortal")
	@ResponseBody
	public PortalInfoBean showOnePortal(String portalName) throws Exception {
		logger.info(">>>>>>>>>portalName= " + portalName);
		PortalInfoBean portalBean = this.portalInfoMapper
				.getPortalByName(portalName);
		if (portalBean != null) {
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(portalBean.getPortalContent());
			org.dom4j.Document doc = null;
			doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
			portalBean.setPortalContent(out.toString());
		}

		return portalBean;
	}

	/**
	 * 根据设备类型获取相应portal模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDevicePortal")
	@ResponseBody
	public boolean viewDevicePortal(String portalName) throws Exception {
		logger.info("portalName == " + portalName);
		if (!"".equals(portalName)) {
			PortalInfoBean portalInfoBean = portalInfoMapper
					.getPortalByName(portalName);
			portalContent = portalInfoBean.getPortalContent();
		}
		logger.info("根据portalName获取到的portalContent === " + portalContent);
		if (portalContent != null && !"".equals(portalContent)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始portal化树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initPortalTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() throws Exception {
		List<MObjectDefBean> menuLst = mobjectInfoMapper.getAllMObject();
		List<MObjectDefBean> menuLst2 = mobjectInfoMapper.getAllMObject2();
		for (int i = 0; i < menuLst2.size(); i++) {
			menuLst.add(menuLst2.get(i));
		}
		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	@RequestMapping("/uploadPortalInfo")
	@ResponseBody
	public boolean uploadPortalInfo(String filePath, String portalName) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		File file = new File(filePath);
		BufferedReader reader = null;
		StringBuffer uploadPortalContent = new StringBuffer();
		try {
			// reader = new BufferedReader(new FileReader(file));
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "GBK");
			reader = new BufferedReader(isr);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				uploadPortalContent.append(tempString);
				// line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(uploadPortalContent
					.toString()));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new ParsePortalException(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ParsePortalException("XML格式有误");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsePortalException(e.getMessage());
		}

		NodeList opPortalNode = doc.getElementsByTagName("portal");
		Element opPortalElm = (Element) opPortalNode.item(0);
		// String portalName=opPortalElm.getAttribute("name");
		PortalInfoBean portalInfoBean = new PortalInfoBean();
		portalInfoBean.setPortalContent(uploadPortalContent.toString());
		portalInfoBean.setPortalType(1);
		portalInfoBean.setPortalDesc("");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		int i = 0;
		if ("".endsWith(portalName)) {
			portalName = opPortalElm.getAttribute("name");
			portalInfoBean.setPortalName(portalName);
			i = portalInfoMapper.insertPortal(portalInfoBean);
		} else {
			portalInfoBean.setPortalName(portalName);
			i = portalInfoMapper.updatePortal(portalInfoBean);
		}
		logger.info(">>>>>>>>>>导入portalXML over ");
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找节点ID
	 * 
	 * @param constantTypeCName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public List<MObjectDefBean> searchTreeNodes(String classLable) {
		try {
			return mobjectInfoMapper.getTreeIdByTreeName(classLable);
		} catch (Exception e) {
			logger.error("查找节点异常！", e.getMessage());
			return null;
		}
	}

	/**
	 * 提供默认设备
	 * 
	 * @return
	 */
	@RequestMapping("/initDeviceName")
	@ResponseBody
	public MODeviceObj initDeviceName(HttpServletRequest request) {
		Integer moClassId = Integer.parseInt(request.getParameter("moClassId"));
		MODeviceObj moDevice = moDeviceMapper.getFirstMoDevice(moClassId);
		return moDevice;
	}

	/**
	 * 概要视图
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showDevicePortal")
	@ResponseBody
	public ModelAndView showDevicePortal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PortalInfoBean portalInfoBean = portalInfoMapper
				.getPortalByName("Device");
		String xml = portalInfoBean.getPortalContent();
		logger.info("根据portalName获取到的xml === " + xml);

		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new ParsePortalException(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ParsePortalException("XML格式有误");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsePortalException(e.getMessage());
		}
		NodeList opPortalNode = doc.getElementsByTagName("portal");
		Element opPortalElm = (Element) opPortalNode.item(0);
		String portalName = opPortalElm.getAttribute("name");
		Portal portal = new Portal();
		portal.setPortalName(portalName);
		if (opPortalElm == null) {
			throw new ParsePortalException("缺少portal标签");
		}
		NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
		int gridNum = gridNodes.getLength();
		if (gridNum == 0) {
			throw new ParsePortalException("portal下缺少grid标签");
		}
		Grid grid = new Grid();
		NodeList columnNodes = ((Element) gridNodes.item(0))
				.getElementsByTagName("column");
		for (int i = 0; i < columnNodes.getLength(); i++) {
			Vbox vbox = new Vbox();
			String width = ((Element) columnNodes.item(i))
					.getAttribute("width");
			Column column = grid.new Column();
			column.setWidth(width);
			NodeList widgetNodes = ((Element) columnNodes.item(i))
					.getElementsByTagName("widget");
			for (int k = 0; k < widgetNodes.getLength(); k++) {
				Element widgetElm = (Element) widgetNodes.item(k);
				NodeList embedNodes = widgetElm.getElementsByTagName("embed");
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
						logger.info("moId= " + moId);
						String moClass = request.getParameter("moClass");
						logger.info("moClass= " + moClass);
						String ifMoId = request.getParameter("IfMOID");
						logger.info("ifName= " + ifMoId);
						if (moId == null && moClass == null) {

						} else {
							if (oldUrlParams.contains("num") == true) {
								String newUrlParams = oldUrlParams.split("&")[0]
										+ "&moClass=" + moClass;
								url = url.replace(oldUrlParams, newUrlParams);
							} else {
								if (oldUrlParams.contains("IfMOID") == true) {
									String newUrlParams = "?moID=" + moId
											+ "&moClass=" + moClass
											+ "&IfMOID=" + ifMoId;
									url = url.replace(oldUrlParams,
											newUrlParams);
								} else {
									String newUrlParams = "?moID=" + moId
											+ "&moClass=" + moClass;
									url = url.replace(oldUrlParams,
											newUrlParams);
								}

							}
						}
					}
					embedWidget.setUrl(url);
					vbox.addComponent(embedWidget);
				}
			}
			column.addComponent(vbox);
			grid.addColumn(column);
		}
		portal.setGrid(grid);
		StringBuilder sbPortal = portal.draw();
		logger.info("xml解析生成的js >>>>>>>>> " + sbPortal.toString());
		request.setAttribute("portalJS", sbPortal.toString());
		/**
		 * } else { //返回304，告诉浏览器使用本地缓存
		 * System.out.println(">>>>>>>>>304>>>>>>>>>");
		 * response.setStatus(HttpServletResponse.SC_NOT_MODIFIED); }
		 */
		return new ModelAndView("monitor/portal/portalView");

	}
}
