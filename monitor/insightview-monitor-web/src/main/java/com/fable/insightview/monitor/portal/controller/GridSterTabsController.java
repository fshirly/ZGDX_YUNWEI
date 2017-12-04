package com.fable.insightview.monitor.portal.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.middleware.tomcat.mapper.MiddlewareMapper;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.gridstertabs.Grid;
import com.fable.insightview.platform.core.gridstertabs.GridSter;
import com.fable.insightview.platform.core.gridstertabs.GridSterTabs;
import com.fable.insightview.platform.core.gridstertabs.Grid.GridSterTabsDiv;
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
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;
import com.fable.insightview.monitor.portal.mapper.WidgetGroupInfoMapper;
import com.fable.insightview.monitor.portal.mapper.WidgetInfoMapper;
import com.fable.insightview.monitor.portal.service.DateUtil;

@Controller
@RequestMapping("/monitor/gridster")
public class GridSterTabsController {
	private final Logger logger = LoggerFactory.getLogger(GridSterTabsController.class);
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
	@Autowired
	MONetworkIfMapper moNetworkIfMapper;
	@Autowired
	MiddlewareMapper middlewareMapper;
	@Autowired
	OracleMapper oracleMapper;

	String portalContent = null;
	String widgetFilter = null;

	/**
	 * 加载视图页面
	 */
	@RequestMapping("/showPortalView")
	public ModelAndView showPortalView(HttpServletRequest request,HttpServletResponse response) {
		
		String moClassName = request.getParameter("moClassName");
		if(!"".equals(moClassName) && moClassName != null){
			viewDevicePortal(moClassName);
		}
		DrawWidgetUtil drawWidgetUtil = new DrawWidgetUtil();
		String flag = request.getParameter("flag");
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
		gridster.setGridSterName("firstgridster");
		Integer gridsterCount = 0;
		if (opPortalElm == null) {
			logger.error("缺少portal标签");
		}
		
		//获取portal下tabs外的grid标签
		NodeList gridNodes = opPortalElm.getElementsByTagName("grid");
		int gridNum = gridNodes.getLength();
		
		//获取tab下的grid标签
		NodeList tabNodes = opPortalElm.getElementsByTagName("tab");
		int tabGridNum = 0;
		for (int i = 0; i < tabNodes.getLength(); i++) {
			NodeList tabsGridNodes = ((Element) tabNodes.item(i)).getElementsByTagName("grid");
			tabGridNum += tabsGridNodes.getLength();
		}
		int num=gridNum-tabGridNum;
		StringBuilder sbPortal = new StringBuilder();
		Vbox vbox = new Vbox();
		Vbox vboxGauge = new Vbox();
		if(num > 0){
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
			gridster.setGridsterCount(gridsterCount);
//			if (gridNum == 0) {
//				logger.error("portal下缺少grid标签");
//			}
			Grid grid = new Grid();
			
			NodeList widgetNodes = ((Element) gridNodes.item(0))
					.getElementsByTagName("widget");
			
			logger.info("遍历所有部件。。。");
			drawWidgetUtil.drawWidget(grid, widgetNodes, request, vbox, vboxGauge, gridGauge);
			gridster.setGrid(grid);
			sbPortal.append(gridster.drawGridsterPre());
			sbPortal.append(gridster.draw());
			
		
		}
		
		
		// tabs
		Grid gridGaugeTab = new Grid();
		NodeList tabsNodes = opPortalElm.getElementsByTagName("tabs");
		for (int i = 0; i < tabsNodes.getLength(); i++) {
			GridSter tabsGridster = new GridSter();
			if(num == 0){
				gridsterCount = -1;
				tabsGridster.setDivId(gridster.getGridSterName());
				sbPortal.append(tabsGridster.drawGridsterPre());
			}
			GridSterTabs gridSterTabs = new GridSterTabs();
			NodeList tabsDivNodes = ((Element) tabsNodes.item(i)).getElementsByTagName("tab");
			Grid tabsgrid = new Grid();
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				GridSterTabsDiv gridSterTabsDiv = tabsgrid.new GridSterTabsDiv();
				gridSterTabsDiv.setDivId(((Element) tabsDivNodes.item(j)).getAttribute("name"));
				gridSterTabsDiv.setTabsDivTitle(((Element) tabsDivNodes.item(j)).getAttribute("title"));
				tabsgrid.addGridSterTabsDiv(gridSterTabsDiv);
			}
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				
				gridsterCount++;
				tabsGridster.setDivId(((Element) tabsDivNodes.item(j)).getAttribute("name"));
				tabsGridster.setDrawCount(0);
				NodeList tabsGridNodes = ((Element) tabsDivNodes.item(j)).getElementsByTagName("grid");
				tabsGridster.setOuterGridSterName(gridster.getGridSterName());
				tabsGridster.setGridSterName(((Element) tabsGridNodes.item(0)).getAttribute("name"));
				tabsGridster.setMarginsHorizontal(((Element) tabsGridNodes.item(0)).getAttribute("horizontal"));
				tabsGridster.setMarginsVertival(((Element) tabsGridNodes.item(0)).getAttribute("vertival"));
				tabsGridster.setBaseDimensionsWidth(((Element) tabsGridNodes.item(0)).getAttribute("basewidth"));
				tabsGridster.setBaseDimensionsHeight(((Element) tabsGridNodes.item(0)).getAttribute("baseheight"));
				tabsGridster.setResize(true);
				tabsGridster.setGridsterCount(gridsterCount);
				tabsGridster.addGridSterTabs(gridSterTabs);
				NodeList tabsWidgetNodes = ((Element) tabsGridNodes.item(0)).getElementsByTagName("widget");
				Vbox tabsVbox = new Vbox();
				drawWidgetUtil.drawWidget(tabsgrid, tabsWidgetNodes, request,tabsVbox,vboxGauge, gridGauge);
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
		if (!"".equals(portalName) && !"Unknow".equals(portalName)) {
			PortalInfoBean portalInfoBean = portalInfoMapper
					.getPortalByName(portalName);
			portalContent = portalInfoBean.getPortalContent();
			if (portalContent != null && !"".equals(portalContent)) {
				return true;
			}
		}

		logger.info("根据portalName获取到的portalContent === " + portalContent);
		return false;
	}

	/**
	 * 初始portal化树
	 */
	@RequestMapping("/initPortalTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() throws Exception {
		/**
		 * List<MObjectDefBean> menuLst = mobjectInfoMapper.getAllMObject();
		 * List<MObjectDefBean> menuLst2 = mobjectInfoMapper.getAllMObject2();
		 * for (int i = 0; i < menuLst2.size(); i++) {
		 * menuLst.add(menuLst2.get(i)); } for (int i = 0; i < menuLst.size();
		 * i++) { MObjectDefBean bean = menuLst.get(i);
		 * if(bean.getParentClassId()== null){ bean.setParentClassId(0); } }
		 * String menuLstJson = JsonUtil.toString(menuLst); Map<String, Object>
		 * result = new HashMap<String, Object>(); result.put("menuLstJson",
		 * menuLstJson); return result;
		 */
		List<MObjectDefBean> menuLst = mobjectInfoMapper.queryMObjectRelation();
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

	/**
	 * 加载视图页面
	 * 
	 * @return
	 */
	@RequestMapping("/showDevicePortal")
	public ModelAndView showDevicePortal(HttpServletRequest request,
			HttpServletResponse response) {
		DrawWidgetUtil drawWidgetUtil = new DrawWidgetUtil();
		String flag = request.getParameter("flag");
		String moType = request.getParameter("moType");
		
		logger.info(flag);
		PortalInfoBean portalInfoBean = portalInfoMapper
				.getPortalByName("Host");
		if (moType.equals("device")) {
			portalInfoBean = portalInfoMapper.getPortalByName("Device");
		} else if (moType.equals("mo")) {
			portalInfoBean = portalInfoMapper.getPortalByName("MO");
		}else if(moType!=null && moType.toUpperCase().equals("SafeDevice".toUpperCase())){
			portalInfoBean=portalInfoMapper.getPortalByName("SafeDevice");
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
		gridster.setGridSterName(((Element) gridNodes.item(0)).getAttribute("name"));
		gridster.setDivId(((Element) gridNodes.item(0)).getAttribute("name"));
		gridster.setMarginsHorizontal(((Element) gridNodes.item(0)).getAttribute("horizontal"));
		gridster.setMarginsVertival(((Element) gridNodes.item(0)).getAttribute("vertival"));
		gridster.setBaseDimensionsWidth(((Element) gridNodes.item(0)).getAttribute("basewidth"));
		gridster.setBaseDimensionsHeight(((Element) gridNodes.item(0)).getAttribute("baseheight"));
		gridster.setResize(true);
		if (moType.equals("device")) {
			gridster.setMoType("device");
		} else if (moType.equals("mo")) {
			gridster.setMoType("mo");
		}else if(moType!=null && moType.toUpperCase().equals("SafeDevice".toUpperCase())){
			gridster.setMoType("SafeDevice");
		} else {
			gridster.setMoType("host");
		}
		gridster.setGridsterCount(gridsterCount);
		int gridNum = gridNodes.getLength();
		if (gridNum == 0) {
			logger.error("portal下缺少grid标签");
		}
		Grid grid = new Grid();
		Grid gridGauge = new Grid();
		NodeList widgetNodes = ((Element) gridNodes.item(0)).getElementsByTagName("widget");
		Vbox vbox = new Vbox();
		Vbox vboxGauge = new Vbox();
		logger.info("遍历所有部件。。。");
		drawWidgetUtil.drawWidget(grid, widgetNodes, request, vbox, vboxGauge, gridGauge);;
		gridster.setGrid(grid);
		StringBuilder sbPortal = gridster.drawGridsterPre();
		sbPortal.append(gridster.draw());
		// tabs
		NodeList tabsNodes = opPortalElm.getElementsByTagName("tabs");
		for (int i = 0; i < tabsNodes.getLength(); i++) {
			GridSter tabsGridster = new GridSter();
			GridSterTabs gridSterTabs = new GridSterTabs();
			NodeList tabsDivNodes = ((Element) tabsNodes.item(i)).getElementsByTagName("tab");
			Grid tabsgrid = new Grid();
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				GridSterTabsDiv gridSterTabsDiv = tabsgrid.new GridSterTabsDiv();
				gridSterTabsDiv.setDivId(((Element) tabsDivNodes.item(j)).getAttribute("name"));
				gridSterTabsDiv
						.setTabsDivTitle(((Element) tabsDivNodes.item(j)).getAttribute("title"));
				tabsgrid.addGridSterTabsDiv(gridSterTabsDiv);
			}
			for (int j = 0; j < tabsDivNodes.getLength(); j++) {
				gridsterCount++;
				tabsGridster.setDivId(((Element) tabsDivNodes.item(j)).getAttribute("name"));
				tabsGridster.setDrawCount(0);
				NodeList tabsGridNodes = ((Element) tabsDivNodes.item(j)).getElementsByTagName("grid");
				tabsGridster.setOuterGridSterName(gridster.getGridSterName());
				tabsGridster.setGridSterName(((Element) tabsGridNodes.item(0)).getAttribute("name"));
				tabsGridster.setMarginsHorizontal(((Element) tabsGridNodes.item(0)).getAttribute("horizontal"));
				tabsGridster.setMarginsVertival(((Element) tabsGridNodes.item(0)).getAttribute("vertival"));
				tabsGridster.setBaseDimensionsWidth(((Element) tabsGridNodes.item(0)).getAttribute("basewidth"));
				tabsGridster.setBaseDimensionsHeight(((Element) tabsGridNodes.item(0)).getAttribute("baseheight"));
				tabsGridster.setResize(true);
				tabsGridster.setGridsterCount(gridsterCount);
				tabsGridster.addGridSterTabs(gridSterTabs);
				NodeList tabsWidgetNodes = ((Element) tabsGridNodes.item(0)).getElementsByTagName("widget");
				Vbox tabsVbox = new Vbox();
//				drawWidgetUtil.drawWidget(tabsgrid, tabsWidgetNodes, request,tabsVbox);
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
		sbPortal.append(gridGauge.render());
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
	
}
