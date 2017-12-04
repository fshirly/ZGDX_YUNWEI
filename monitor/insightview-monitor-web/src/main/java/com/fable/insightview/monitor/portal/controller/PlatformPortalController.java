package com.fable.insightview.monitor.portal.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.gridstertabs.Grid;
import com.fable.insightview.platform.core.gridstertabs.GridSter;
import com.fable.insightview.platform.core.gridstertabs.GridSterTabs;
import com.fable.insightview.platform.core.gridstertabs.Grid.GridSterTabsDiv;
import com.fable.insightview.platform.core.gridstertabs.container.Vbox;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.monitor.portal.mapper.WidgetGroupInfoMapper;
import com.fable.insightview.monitor.portal.service.DateUtil;
import com.fable.insightview.platform.portal.service.IPortalInfoService;
import com.fable.insightview.monitor.portal.service.IWidgetInfoService;
import com.fable.insightview.platform.service.ISysUserInGroupsService;
import com.fable.insightview.platform.service.ISysUserService;

@Controller
@RequestMapping("/platform/platformPortal")
public class PlatformPortalController {
	private final Logger logger = LoggerFactory
			.getLogger(PlatformPortalController.class);
	DateUtil dateUtil = new DateUtil();
	@Autowired
	IPortalInfoService portalInfoService;
	@Autowired
	IWidgetInfoService widgetInfoService;
	@Autowired
	WidgetGroupInfoMapper widgetGroupInfoMapper;
	@Autowired
	ISysUserInGroupsService sysUserInGroupsService;
	@Autowired
	ISysUserService sysUserService;

	String portalContent = null;
	String widgetFilter = null;
	String widgetContent = null;

	/**
	 * 加载视图页面
	 */
	@RequestMapping("/showPortalView")
	public ModelAndView showPortalView(HttpServletRequest request,
			HttpServletResponse response) {

		DrawPortalWidgetUtil drawWidgetUtil = new DrawPortalWidgetUtil();
		Map<String, String> urlMaps = new HashMap<String, String>();
		String flag = request.getParameter("flag");
		String moType = request.getParameter("moType");
		Grid gridGauge = new Grid();
		logger.info("begin showPortalView....");
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
			logger.error("XML格式有误");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		NodeList opPortalNode = doc.getElementsByTagName("portal");
		Element opPortalElm = (Element) opPortalNode.item(0);
		GridSter gridster = new GridSter();
		if ("platform".equals(moType)) {
			gridster.setMoType("platform");
		}
		gridster.setGridSterName("firstgridster");
		Integer gridsterCount = 0;
		if (opPortalElm == null) {
			logger.error("缺少portal标签");
		}

		// 获取portal下tabs外的grid标签
		NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
		int gridNum = gridNodes.getLength();

		// 获取tab下的grid标签
		NodeList tabNodes = opPortalElm.getElementsByTagName("tab");
		int tabGridNum = 0;
		for (int i = 0; i < tabNodes.getLength(); i++) {
			NodeList tabsGridNodes = ((Element) tabNodes.item(i))
					.getElementsByTagName("grid");
			tabGridNum += tabsGridNodes.getLength();
		}
		int num = gridNum - tabGridNum;
		StringBuilder sbPortal = new StringBuilder();
		if (num > 0) {
			gridster.setGridSterName(((Element) gridNodes.item(0))
					.getAttribute("name"));
			gridster.setDivId(((Element) gridNodes.item(0))
					.getAttribute("name"));
			gridster.setMarginsHorizontal(((Element) gridNodes.item(0))
					.getAttribute("horizontal"));
			gridster.setMarginsVertival(((Element) gridNodes.item(0))
					.getAttribute("vertival"));
			gridster.setBaseDimensionsWidth(((Element) gridNodes.item(0))
					.getAttribute("basewidth"));
			gridster.setBaseDimensionsHeight(((Element) gridNodes.item(0))
					.getAttribute("baseheight"));
			gridster.setResize(true);
			gridster.setPortalType("1");
			gridster.setGridsterCount(gridsterCount);
			// if (gridNum == 0) {
			// logger.error("portal下缺少grid标签");
			// }
			Grid grid = new Grid();

			NodeList widgetNodes = ((Element) gridNodes.item(0))
					.getElementsByTagName("widget");
			Vbox vbox = new Vbox();
			Vbox vboxGauge = new Vbox();
			logger.info("遍历所有部件。。。");
			drawWidgetUtil.drawWidget(grid, widgetNodes, request, vbox,
					vboxGauge, gridGauge, moType, urlMaps);
			gridster.setGrid(grid);
			sbPortal.append(gridster.drawGridsterPre());
			sbPortal.append(gridster.draw());

		}

		// tabs
		NodeList tabsNodes = opPortalElm.getElementsByTagName("tabs");
		for (int i = 0; i < tabsNodes.getLength(); i++) {
			GridSter tabsGridster = new GridSter();
			if (num == 0) {
				gridsterCount = -1;
				tabsGridster.setDivId(gridster.getGridSterName());
				sbPortal.append(tabsGridster.drawGridsterPre());
			}
			GridSterTabs gridSterTabs = new GridSterTabs();
			NodeList tabsDivNodes = ((Element) tabsNodes.item(i))
					.getElementsByTagName("tab");
			Grid tabsgrid = new Grid();
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				GridSterTabsDiv gridSterTabsDiv = tabsgrid.new GridSterTabsDiv();
				gridSterTabsDiv.setDivId(((Element) tabsDivNodes.item(j))
						.getAttribute("name"));
				gridSterTabsDiv
						.setTabsDivTitle(((Element) tabsDivNodes.item(j))
								.getAttribute("title"));
				tabsgrid.addGridSterTabsDiv(gridSterTabsDiv);
			}
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				gridsterCount++;
				tabsGridster.setDivId(((Element) tabsDivNodes.item(j))
						.getAttribute("name"));
				tabsGridster.setDrawCount(0);
				NodeList tabsGridNodes = ((Element) tabsDivNodes.item(j))
						.getElementsByTagName("grid");
				tabsGridster.setOuterGridSterName(gridster.getGridSterName());
				tabsGridster.setGridSterName(((Element) tabsGridNodes.item(0))
						.getAttribute("name"));
				tabsGridster.setMarginsHorizontal(((Element) tabsGridNodes
						.item(0)).getAttribute("horizontal"));
				tabsGridster.setMarginsVertival(((Element) tabsGridNodes
						.item(0)).getAttribute("vertival"));
				tabsGridster.setBaseDimensionsWidth(((Element) tabsGridNodes
						.item(0)).getAttribute("basewidth"));
				tabsGridster.setBaseDimensionsHeight(((Element) tabsGridNodes
						.item(0)).getAttribute("baseheight"));
				tabsGridster.setResize(true);
				tabsGridster.setGridsterCount(gridsterCount);
				tabsGridster.addGridSterTabs(gridSterTabs);
				NodeList tabsWidgetNodes = ((Element) tabsGridNodes.item(0))
						.getElementsByTagName("widget");
				Vbox tabsVbox = new Vbox();
				// drawWidgetUtil.drawWidget(tabsgrid, tabsWidgetNodes,
				// request,tabsVbox);
				if (j != 0) {
					tabsgrid.delGridSterTabs();
					tabsGridster.setDrawCount(1);
				}
				tabsGridster.setGrid(tabsgrid);
				sbPortal.append(tabsGridster.draw());
				tabsgrid.delGridSterWidget();
			}
		}
		sbPortal.append(gridster.drawEnd());

		sbPortal.append(gridGauge.render());

		logger.info("xml解析生成的js === " + sbPortal.toString());
		request.setAttribute("portalJS", sbPortal.toString());
		request.setAttribute("flag", flag);
		request.setAttribute("moType", moType);
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
		}
		return false;
	}

	/**
	 * 根据设备类型获取相应portal模板
	 */
	@RequestMapping("/viewDevicePortal")
	@ResponseBody
	public boolean viewDevicePortal(String portalName) {

		logger.info("portalName == " + portalName);
		if (!"".equals(portalName)) {
			PortalInfoBean portalInfoBean = portalInfoService
					.getPortalByName(portalName);
			portalContent = portalInfoBean.getPortalContent();
		}

		logger.info("根据portalName获取到的portalContent === " + portalContent);
		if (portalContent != null && !"".equals(portalContent)) {
			return true;
		}
		return false;
	}

	/**
	 * 加载视图页面
	 * 
	 * @return
	 */
	@RequestMapping("/showDevicePortal")
	public ModelAndView showDevicePortal(HttpServletRequest request,
			HttpServletResponse response) {
		DrawPortalWidgetUtil drawWidgetUtil = new DrawPortalWidgetUtil();
		String flag = request.getParameter("flag");
		String moType = request.getParameter("moType");

		logger.info(flag);
		PortalInfoBean portalInfoBean = portalInfoService
				.getPortalByName("Host");
		if (moType.equals("device")) {
			portalInfoBean = portalInfoService.getPortalByName("Device");
		} else if (moType.equals("mo")) {
			portalInfoBean = portalInfoService.getPortalByName("MO");
		}

		String xml = portalInfoBean.getPortalContent();
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
			logger.error("XML格式有误");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		NodeList opPortalNode = doc.getElementsByTagName("portal");
		Element opPortalElm = (Element) opPortalNode.item(0);
		GridSter gridster = new GridSter();
		Integer gridsterCount = 0;
		if (opPortalElm == null) {
			logger.error("缺少portal标签");
		}
		NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
		gridster.setGridSterName(((Element) gridNodes.item(0))
				.getAttribute("name"));
		gridster.setDivId(((Element) gridNodes.item(0)).getAttribute("name"));
		gridster.setMarginsHorizontal(((Element) gridNodes.item(0))
				.getAttribute("horizontal"));
		gridster.setMarginsVertival(((Element) gridNodes.item(0))
				.getAttribute("vertival"));
		gridster.setBaseDimensionsWidth(((Element) gridNodes.item(0))
				.getAttribute("basewidth"));
		gridster.setBaseDimensionsHeight(((Element) gridNodes.item(0))
				.getAttribute("baseheight"));
		gridster.setResize(true);
		if (moType.equals("device")) {
			gridster.setMoType("device");
		} else if (moType.equals("mo")) {
			gridster.setMoType("mo");
		} else {
			gridster.setMoType("host");
		}
		gridster.setGridsterCount(gridsterCount);
		int gridNum = gridNodes.getLength();
		if (gridNum == 0) {
			logger.error("portal下缺少grid标签");
		}
		Grid grid = new Grid();
		NodeList widgetNodes = ((Element) gridNodes.item(0))
				.getElementsByTagName("widget");
		Vbox vbox = new Vbox();

		logger.info("遍历所有部件。。。");
		// drawWidgetUtil.drawWidget(grid, widgetNodes, request, vbox);
		gridster.setGrid(grid);
		StringBuilder sbPortal = gridster.drawGridsterPre();
		sbPortal.append(gridster.draw());
		// tabs
		NodeList tabsNodes = opPortalElm.getElementsByTagName("tabs");
		for (int i = 0; i < tabsNodes.getLength(); i++) {
			GridSter tabsGridster = new GridSter();
			GridSterTabs gridSterTabs = new GridSterTabs();
			NodeList tabsDivNodes = ((Element) tabsNodes.item(i))
					.getElementsByTagName("tab");
			Grid tabsgrid = new Grid();
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				GridSterTabsDiv gridSterTabsDiv = tabsgrid.new GridSterTabsDiv();
				gridSterTabsDiv.setDivId(((Element) tabsDivNodes.item(j))
						.getAttribute("name"));
				gridSterTabsDiv
						.setTabsDivTitle(((Element) tabsDivNodes.item(j))
								.getAttribute("title"));
				tabsgrid.addGridSterTabsDiv(gridSterTabsDiv);
			}
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				gridsterCount++;
				tabsGridster.setDivId(((Element) tabsDivNodes.item(j))
						.getAttribute("name"));
				tabsGridster.setDrawCount(0);
				NodeList tabsGridNodes = ((Element) tabsDivNodes.item(j))
						.getElementsByTagName("grid");
				tabsGridster.setOuterGridSterName(gridster.getGridSterName());
				tabsGridster.setGridSterName(((Element) tabsGridNodes.item(0))
						.getAttribute("name"));
				tabsGridster.setMarginsHorizontal(((Element) tabsGridNodes
						.item(0)).getAttribute("horizontal"));
				tabsGridster.setMarginsVertival(((Element) tabsGridNodes
						.item(0)).getAttribute("vertival"));
				tabsGridster.setBaseDimensionsWidth(((Element) tabsGridNodes
						.item(0)).getAttribute("basewidth"));
				tabsGridster.setBaseDimensionsHeight(((Element) tabsGridNodes
						.item(0)).getAttribute("baseheight"));
				tabsGridster.setResize(true);
				tabsGridster.setGridsterCount(gridsterCount);
				tabsGridster.addGridSterTabs(gridSterTabs);
				NodeList tabsWidgetNodes = ((Element) tabsGridNodes.item(0))
						.getElementsByTagName("widget");
				Vbox tabsVbox = new Vbox();
				// drawWidgetUtil.drawWidget(tabsgrid, tabsWidgetNodes,
				// request,tabsVbox);
				// gridSterTabsDiv.addComponent(tabsVbox);
				if (j != 0) {
					tabsgrid.delGridSterTabs();
					tabsGridster.setDrawCount(1);
				}
				tabsGridster.setGrid(tabsgrid);
				sbPortal.append(tabsGridster.draw());
				tabsgrid.delGridSterWidget();
			}
		}
		sbPortal.append(gridster.drawEnd());

		logger.info("xml解析生成的js === " + sbPortal.toString());
		request.setAttribute("portalJS", sbPortal.toString());
		request.setAttribute("flag", flag);
		/**
		 * } else { //返回304，告诉浏览器使用本地缓存
		 * System.out.println(">>>>>>>>>304>>>>>>>>>");
		 * response.setStatus(HttpServletResponse.SC_NOT_MODIFIED); }
		 */
		return new ModelAndView("monitor/portal/portalView");
	}

	/**
	 * 加载首页页面
	 * 
	 * @return
	 */
	@RequestMapping("/showHomePagePortal")
	public ModelAndView showHomePagePortal(HttpServletRequest request,
			HttpServletResponse response) {
			/*SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
					.getSession().getAttribute("sysUserInfoBeanOfSession");*/
			SysUserInGroupsBean sysUserInGroupsBean = sysUserInGroupsService
					.findGroupsByUserId(1)
					.get(0);
			String userId = "";
			//String userAccount = sysUserInfoBeanTemp.getUserAccount();
			String userAccount="admin";
			PortalInfoBean bean = new PortalInfoBean();
			bean.setCreator(1);
			bean.setOwnerUserId(sysUserInGroupsBean.getGroupId());
			PortalInfoBean portalInfoBean = null;
			PortalInfoBean portalInfoUserBean = null;
			userId ="1";
			String moType = request.getParameter("moType");
			String flag = request.getParameter("flag");
			String portalName = request.getParameter("portalName");
			if (true) {
				if ("homepage".equals(moType)) {
					portalInfoBean = portalInfoService
							.getPortalByName("homepage");
				} else if ("informationDesk".equals(moType)) {
					portalInfoBean = portalInfoService
							.getPortalByName("informationDesk");
				} else {
					portalInfoBean = portalInfoService
							.getPortalByName(portalName);
				}
				userId ="1";
			} else {
				// 根据用户ID查
				portalInfoBean = portalInfoService.getPortalByUserId(bean);
				if (portalInfoBean == null) {
					if ("homepage".equals(moType)) {
						portalInfoBean = portalInfoService
								.getPortalByOwnerUserId(bean);
						if (portalInfoBean == null) {
							portalInfoBean = portalInfoService
									.getPortalByName("homepage");
						}
						portalInfoBean.setPortalName("homepage" + userAccount);
						portalInfoUserBean = portalInfoBean;
					} else if ("informationDesk".equals(moType)) {
						portalInfoBean = portalInfoService
								.getPortalByName("informationDesk");
					} else {
						portalInfoBean = portalInfoService
								.getPortalByName(portalName);
					}
				} else {
					userId ="1";
				}
			}
			String userPortal = portalInfoBean.getPortalContent();
			String portalType = String.valueOf(portalInfoBean.getPortalType());
			DrawPortalWidgetUtil drawWidgetUtil = new DrawPortalWidgetUtil();
			Grid gridGauge = new Grid();
			logger.info("begin showPortalView....");
			SAXReader reader = null;
			String portalContentTemp = portalInfoBean.getPortalContent();
			if (widgetContent != null) {
				String strPre = portalContentTemp.substring(0,
						portalContentTemp.lastIndexOf("</grid>"));
				String strEnd = portalContentTemp.substring(portalContentTemp
						.indexOf("</grid>"));
				portalContentTemp = strPre + widgetContent + strEnd;
				widgetContent = null;
			}
			try {
				reader = new SAXReader();
				StringReader in = new StringReader(portalContentTemp);
				org.dom4j.Document doc;
				doc = reader.read(in);
				OutputFormat formater = OutputFormat.createPrettyPrint();
				formater.setEncoding("utf-8");
				StringWriter out = new StringWriter();
				XMLWriter writer = new XMLWriter(out, formater);
				writer.write(doc);
				writer.close();
				//将视图xml写到文件，作为备份
				FileWriter filewriter = new FileWriter("xmlHomePage.txt", false);
				filewriter.write(out.toString());
				filewriter.close();
				BufferedReader buffReader = new BufferedReader(new FileReader(
						new File("xmlHomePage.txt")));
				String tempString = null;
				StringBuffer sbuff = new StringBuffer();
				String rowsValue = "";
				int count = 0;
				//处理部件大小，因为首页窗口比预览窗口大，所以首页展示的时候，部件大小需要改变
				//预览的时候，一个部件最大长度为31，首页展示大概34到35左右
				while ((tempString = buffReader.readLine()) != null) {
					if (tempString.contains("<widget") == true) {
						String rowspan = tempString.substring(0, tempString
								.indexOf("colspan"));
						String colspan = tempString.substring(tempString
								.indexOf("colspan"));
						String[] colspanTemp = colspan.split("\"");
						String colspanNew = "";
						if (!rowsValue.equals(colspanTemp[5]) == true) {
							count = 0;
							rowsValue = colspanTemp[5];
						} else {
							count++;
						}
						int colspanValue = Integer.parseInt(colspanTemp[1]);
						for (int i = 0; i < colspanTemp.length; i++) {
							if (i == 1) {
								if (Integer.parseInt(colspanTemp[1]) <= 20
										&& Integer.parseInt(colspanTemp[1]) > 10) {
									colspanTemp[1] = String.valueOf(Integer
											.parseInt(colspanTemp[1]) + 2);
								} else if (Integer.parseInt(colspanTemp[1]) <= 10) {
									if (count < 2) {
										colspanTemp[1] = String.valueOf(Integer
												.parseInt(colspanTemp[1]) + 1);
									} else {
										colspanTemp[1] = String.valueOf(Integer
												.parseInt(colspanTemp[1]) + 2);
									}

								} else {
									colspanTemp[1] = String.valueOf(Integer
											.parseInt(colspanTemp[1]) + 4);
								}

							}
							if (i == 3) {
								if (colspanValue <= 20 && colspanValue > 10) {
									if (Integer.parseInt(colspanTemp[3]) != 1) {
										colspanTemp[3] = String.valueOf(Integer
												.parseInt(colspanTemp[3]) + 2);
									}
								} else if (colspanValue <= 10) {
									if (Integer.parseInt(colspanTemp[3]) != 1) {
										if (count < 2) {
											colspanTemp[3] = String
													.valueOf(Integer
															.parseInt(colspanTemp[3]) + 1);
										} else {
											colspanTemp[3] = String
													.valueOf(Integer
															.parseInt(colspanTemp[3]) + 2);
										}
									}
								} else {
									if (Integer.parseInt(colspanTemp[3]) != 1) {
										colspanTemp[3] = String.valueOf(Integer
												.parseInt(colspanTemp[3]) + 4);
									}

								}

							}
							colspanNew = colspanNew + colspanTemp[i] + "\"";
						}
						colspanNew = colspanNew.substring(0, colspanNew
								.lastIndexOf("\""));
						tempString = rowspan + colspanNew;
					}
					sbuff.append(tempString);
				}
				StringReader inContent = new StringReader(sbuff.toString());
				org.dom4j.Document doc2 = reader.read(inContent);
				OutputFormat formater2 = OutputFormat.createPrettyPrint();
				formater.setEncoding("utf-8");
				StringWriter out2 = new StringWriter();
				XMLWriter writer2 = new XMLWriter(out2, formater2);
				writer2.write(doc2);
				writer2.close();
				portalInfoBean.setPortalContent(out2.toString());
				buffReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("视图部件布局异常！", e.getMessage());
			}
			String xml = portalInfoBean.getPortalContent();
			String contentXml = portalInfoBean.getPortalContent();
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
				logger.error("XML格式有误");
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			NodeList opPortalNode = doc.getElementsByTagName("portal");
			Element opPortalElm = (Element) opPortalNode.item(0);
			GridSter gridster = new GridSter();
			if ("homepage".equals(moType)) {
				gridster.setMoType("homepage");
			}
			if ("informationDesk".equals(moType)) {
				gridster.setMoType("informationDesk");
			}
			gridster.setGridSterName("firstgridster");
			Integer gridsterCount = 0;
			if (opPortalElm == null) {
				logger.error("缺少portal标签");
			}

			// 获取portal下tabs外的grid标签
			NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
			int gridNum = gridNodes.getLength();

			// 获取tab下的grid标签
			NodeList tabNodes = opPortalElm.getElementsByTagName("tab");
			int tabGridNum = 0;
			for (int i = 0; i < tabNodes.getLength(); i++) {
				NodeList tabsGridNodes = ((Element) tabNodes.item(i))
						.getElementsByTagName("grid");
				tabGridNum += tabsGridNodes.getLength();
			}
			int num = gridNum - tabGridNum;
			StringBuilder sbPortal = new StringBuilder();
			Map<String, String> urlMaps = new HashMap<String, String>();
			if (num > 0) {
				gridster.setGridSterName(((Element) gridNodes.item(0))
						.getAttribute("name"));
				gridster.setDivId(((Element) gridNodes.item(0))
						.getAttribute("name"));
				gridster.setMarginsHorizontal(((Element) gridNodes.item(0))
						.getAttribute("horizontal"));
				gridster.setMarginsVertival(((Element) gridNodes.item(0))
						.getAttribute("vertival"));
				gridster.setBaseDimensionsWidth(((Element) gridNodes.item(0))
						.getAttribute("basewidth"));
				gridster.setBaseDimensionsHeight(((Element) gridNodes.item(0))
						.getAttribute("baseheight"));
				gridster.setResize(true);
				gridster.setPortalType(portalType);
				gridster.setGridsterCount(gridsterCount);
				// if (gridNum == 0) {
				// logger.error("portal下缺少grid标签");
				// }
				Grid grid = new Grid();

				NodeList widgetNodes = ((Element) gridNodes.item(0))
						.getElementsByTagName("widget");
				Vbox vbox = new Vbox();
				Vbox vboxGauge = new Vbox();
				logger.info("遍历所有部件。。。");
				drawWidgetUtil.drawWidget(grid, widgetNodes, request, vbox,
						vboxGauge, gridGauge, moType, urlMaps);
				gridster.setGrid(grid);
				sbPortal.append(gridster.drawGridsterPre());
				sbPortal.append(gridster.draw());

			}

			// tabs，主要处理视图xml里面包含tab标签
			NodeList tabsNodes = opPortalElm.getElementsByTagName("tabs");
			for (int i = 0; i < tabsNodes.getLength(); i++) {
				GridSter tabsGridster = new GridSter();
				if (num == 0) {
					gridsterCount = -1;
					tabsGridster.setDivId(gridster.getGridSterName());
					sbPortal.append(tabsGridster.drawGridsterPre());
				}
				GridSterTabs gridSterTabs = new GridSterTabs();
				NodeList tabsDivNodes = ((Element) tabsNodes.item(i))
						.getElementsByTagName("tab");
				Grid tabsgrid = new Grid();
				for (int j = 0; j < tabsDivNodes.getLength(); j++) {
					GridSterTabsDiv gridSterTabsDiv = tabsgrid.new GridSterTabsDiv();
					gridSterTabsDiv.setDivId(((Element) tabsDivNodes.item(j))
							.getAttribute("name"));
					gridSterTabsDiv.setTabsDivTitle(((Element) tabsDivNodes
							.item(j)).getAttribute("title"));
					tabsgrid.addGridSterTabsDiv(gridSterTabsDiv);
				}
				for (int j = 0; j < tabsDivNodes.getLength(); j++) {
					gridsterCount++;
					tabsGridster.setDivId(((Element) tabsDivNodes.item(j))
							.getAttribute("name"));
					tabsGridster.setDrawCount(0);
					NodeList tabsGridNodes = ((Element) tabsDivNodes.item(j))
							.getElementsByTagName("grid");
					tabsGridster.setOuterGridSterName(gridster
							.getGridSterName());
					tabsGridster.setGridSterName(((Element) tabsGridNodes
							.item(0)).getAttribute("name"));
					tabsGridster.setMarginsHorizontal(((Element) tabsGridNodes
							.item(0)).getAttribute("horizontal"));
					tabsGridster.setMarginsVertival(((Element) tabsGridNodes
							.item(0)).getAttribute("vertival"));
					tabsGridster
							.setBaseDimensionsWidth(((Element) tabsGridNodes
									.item(0)).getAttribute("basewidth"));
					tabsGridster
							.setBaseDimensionsHeight(((Element) tabsGridNodes
									.item(0)).getAttribute("baseheight"));
					tabsGridster.setResize(true);
					tabsGridster.setGridsterCount(gridsterCount);
					tabsGridster.addGridSterTabs(gridSterTabs);
					NodeList tabsWidgetNodes = ((Element) tabsGridNodes.item(0))
							.getElementsByTagName("widget");
					Vbox tabsVbox = new Vbox();
					// drawWidgetUtil.drawWidget(tabsgrid, tabsWidgetNodes,
					// request,tabsVbox);
					if (j != 0) {
						tabsgrid.delGridSterTabs();
						tabsGridster.setDrawCount(1);
					}
					tabsGridster.setGrid(tabsgrid);
					sbPortal.append(tabsGridster.draw());
					tabsgrid.delGridSterWidget();
				}
			}
			sbPortal.append(gridster.drawEnd());

			sbPortal.append(gridGauge.render());

			// 替换用户首页视图部件url，每个用户登录后，会将展示的首页视图保存一份私有的，内容从刚刚备份的文件中获取
			//并且将部件url替换成目前展示的数据获取的url，保存到表中，以后展示的就是现在的数据，而不是最初没有数据的空图表
			if (portalInfoUserBean != null) {
				try {
					BufferedReader buffReader = new BufferedReader(
							new FileReader(new File("xmlHomePage.txt")));
					StringBuffer sb = new StringBuffer();
					String tmpStr = null;
					while ((tmpStr = buffReader.readLine()) != null) {
						if (tmpStr.contains("<chart") == true) {
							String idValue = tmpStr.substring(
									tmpStr.indexOf("id"),
									tmpStr.indexOf("type")).split("\"")[1];
							String urlValue = urlMaps.get(idValue);
							if (urlValue != null) {
								String chartStr = tmpStr.substring(0, tmpStr
										.indexOf("dataSourceUrl"));
								String chartNew = chartStr
										+ " dataSourceUrl=\"" + urlValue
										+ "\">";
								chartNew = chartNew.replace("&", "&amp;");
								tmpStr = chartNew;
							}
						}
						if (tmpStr.contains("<embed") == true) {
							String idValue = tmpStr
									.substring(tmpStr.indexOf("id"),
											tmpStr.indexOf("url")).split("\"")[1];
							String urlValue = urlMaps.get(idValue);
							if (urlValue != null) {
								String chartStr = tmpStr.substring(0, tmpStr
										.indexOf("url"));
								String chartNew = chartStr + " url=\""
										+ urlValue + "\" />";
								chartNew = chartNew.replace("&", "&amp;");
								tmpStr = chartNew;
							}
						}
						sb.append(tmpStr);
					}
					StringReader inContent = new StringReader(sb.toString());
					org.dom4j.Document doc3 = reader.read(inContent);
					OutputFormat formater3 = OutputFormat.createPrettyPrint();
					formater3.setEncoding("utf-8");
					StringWriter out3 = new StringWriter();
					XMLWriter writer3 = new XMLWriter(out3, formater3);
					writer3.write(doc3);
					writer3.close();
					portalInfoUserBean.setPortalContent(out3.toString());
					buffReader.close();
					portalInfoUserBean.setPortalDesc("首页");
					boolean saveHomepage = this
							.addPortalInfo(portalInfoUserBean);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

			}

			logger.info("xml解析生成的js === " + sbPortal.toString());
			request.setAttribute("portalJS", sbPortal.toString());
			request.setAttribute("moType", moType);
			request.setAttribute("flag", flag);
			request.setAttribute("contentXml", contentXml);
			request.setAttribute("portalName", portalInfoBean.getPortalName());
			request.setAttribute("userId", userId);
			request.setAttribute("userAccount", userAccount);
			request.setAttribute("portalDesc", portalInfoBean.getPortalDesc());


		return new ModelAndView("monitor/portal/portalView");
	}

	public boolean addPortalInfo(PortalInfoBean portalInfoBean) {

		logger.info("新增portalXML ...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		SysUserInGroupsBean sysUserInGroupsBean = sysUserInGroupsService
				.findGroupsByUserId(sysUserInfoBeanTemp.getId().intValue())
				.get(0);
		if (portalInfoBean.getPortalType() == null) {
			portalInfoBean.setPortalType(1);
		}
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		// if(sysUserInGroupsBean.getGroupId()!=1){
		// portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
		// }
		portalInfoBean.setOwnerUserId(sysUserInGroupsBean.getGroupId());
		int insertFlag = portalInfoService.insertPortal(portalInfoBean);

		logger.info("新增portalXML over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 首页新增部件
	 * 
	 * @return
	 */
	@RequestMapping("/initWidgetContent")
	@ResponseBody
	public boolean initWidgetContent(HttpServletRequest request,
			HttpServletResponse response) {

		widgetContent = request.getParameter("widgetContent");
		if (widgetContent != null) {
			return true;
		}
		return false;
	}

	/**
	 * 首页新增部件
	 * 
	 * @return
	 */
	@RequestMapping("/toGetAllHomepages")
	@ResponseBody
	public String toGetAllHomepages(PortalInfoBean portalBean) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		portalBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		List<PortalInfoBean> portalList = portalInfoService.queryPortalInfosByType(portalBean);
		if (portalList.size() == 0) {
			Integer adminId = sysUserService.getSysUserByConditions("UserAccount", "admin").get(0).getUserID();
			portalBean.setCreator(adminId);
			portalList = portalInfoService.queryPortalInfosByType(portalBean);
		}
		StringBuffer sbuff = new StringBuffer();
		try {
			if (portalList.size() > 0) {
				for (int i = 0; i < portalList.size(); i++) {
					sbuff.append(portalList.get(i).getPortalName()).append(";")
							.append(
									java.net.URLEncoder.encode(portalList
											.get(i).getPortalDesc(), "UTF-8"))
							.append(",");
				}
				sbuff.deleteCharAt(sbuff.lastIndexOf(","));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return sbuff.toString();
	}
}
