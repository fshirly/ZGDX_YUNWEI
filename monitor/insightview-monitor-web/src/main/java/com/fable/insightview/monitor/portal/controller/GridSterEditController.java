package com.fable.insightview.monitor.portal.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.mapper.MiddlewareMapper;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.monetworkIif.entity.MONetworkIfBean;
import com.fable.insightview.monitor.monetworkIif.mapper.MONetworkIfMapper;
import com.fable.insightview.monitor.portal.entity.WidgetGroupInfoBean;
import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;
import com.fable.insightview.monitor.portal.exception.ParsePortalException;
import com.fable.insightview.monitor.portal.mapper.WidgetGroupInfoMapper;
import com.fable.insightview.monitor.portal.service.DateUtil;
import com.fable.insightview.monitor.portal.service.IWidgetInfoService;
import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.platform.portal.mapper.PortalInfoMapper;
import com.fable.insightview.platform.portal.service.IPortalInfoService;
import com.fable.insightview.platform.service.ISysUserInGroupsService;

@Controller
@RequestMapping("/monitor/gridsterEdit")
public class GridSterEditController {
	
	private final Logger logger = LoggerFactory.getLogger(GridSterEditController.class);
	DateUtil dateUtil = new DateUtil();
	@Autowired
	PortalInfoMapper portalInfoMapper;
	@Autowired
	IWidgetInfoService widgetInfoService;
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
	@Autowired
	ISysUserInGroupsService sysUserInGroupsService;
	@Autowired
	IPortalInfoService portalInfoService;
	
	String portalContent = null;
	String widgetFilter = null;


	/**
	 * 加载portal编辑页面
	 */
	@RequestMapping("/toShowPortalEdit")
	public ModelAndView toShowPortalEdit() {

		logger.info("编辑视图页面。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String portalDefine = "<?xml version='1.0' encoding='UTF-8'?>";
		request.setAttribute("portalDefine", portalDefine);
		return new ModelAndView("monitor/portal/portalEdit");
	}

	@RequestMapping("/addPortalInfo")
	@ResponseBody
	public boolean addPortalInfo(PortalInfoBean portalInfoBean) {

		logger.info("新增portalXML ...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		SysUserInGroupsBean sysUserInGroupsBean = sysUserInGroupsService.
				findGroupsByUserId(sysUserInfoBeanTemp.getId().intValue()).get(0);
		if (portalInfoBean.getPortalType() == null) {
			portalInfoBean.setPortalType(1);
		}
//		portalInfoBean.setPortalDesc("");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		//portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
//		if(sysUserInGroupsBean.getGroupId()!=1){
//			portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
//		}
		portalInfoBean.setOwnerUserId(sysUserInGroupsBean.getGroupId());
		int insertFlag = portalInfoMapper.insertPortal(portalInfoBean);
		
		logger.info("新增portalXML over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping("/modifyPortalInfo")
	@ResponseBody
	public boolean modifyPortalInfo(PortalInfoBean portalInfoBean) {

		logger.info("修改视图。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		SysUserInGroupsBean sysUserInGroupsBean = sysUserInGroupsService.
				findGroupsByUserId(sysUserInfoBeanTemp.getId().intValue()).get(0);
		int updateFlag = 0;
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(portalInfoBean.getPortalContent()
					.toString()));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			logger.error("XML格式有误");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		
		
		
		if (portalInfoBean.getPortalType() == null) {
			portalInfoBean.setPortalType(1);
		}
//		portalInfoBean.setPortalDesc("");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		
		
		
		
		
//		if(sysUserInGroupsBean.getGroupId() != 1){
			portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
//			portalInfoBean.setOwnerUserId(sysUserInGroupsBean.getGroupId());
			PortalInfoBean bean = this.portalInfoService.getPortalByNameAndUserId(portalInfoBean);
			if(bean != null){
				updateFlag = this.portalInfoService.updateByPortalNameAndUserId(portalInfoBean);
			}else{
				updateFlag = portalInfoMapper.insertPortal(portalInfoBean);
			}
//		}else{
//			updateFlag = portalInfoMapper.updatePortal(portalInfoBean);
//		}

		logger.info("视图修改结束。。。");
		logger.info("修改结果 = " + updateFlag);
		if (updateFlag > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化树
	 */
	@RequestMapping("/findWidgetTreeVal")
	@ResponseBody
	public Map<String, Object> findWidgetTreeVal(HttpServletRequest request) {
		
		logger.info("加载部件树。。。");
		List<WidgetInfoBean> menuLst = null;
		List<WidgetInfoBean> menuLstTemp = new ArrayList<WidgetInfoBean>();
		widgetFilter = request.getParameter("widgetFilter");
		if (widgetFilter == null || "null".equals(widgetFilter) || "".equals(widgetFilter)) {
			menuLst = this.widgetInfoService.getAllWidget();
		} else {
			WidgetInfoBean widgetInfoBean = new WidgetInfoBean();
			widgetInfoBean.setWidgetFilter(widgetFilter);
			menuLst = this.widgetInfoService.getWidgetByFilter(widgetInfoBean);
			
			//过滤不含监测对象类型的数据
			for (int i = 0; i < menuLst.size(); i++) {
				String[] widgetFilters = menuLst.get(i).getWidgetFilter().split(",");
				List filterList = Arrays.asList(widgetFilters);
				int b = filterList.indexOf(widgetFilter);
				if (b < 0) {
					menuLstTemp.add(menuLst.get(i));
				}
			}
		}
		menuLst.removeAll(menuLstTemp);
		List<WidgetGroupInfoBean> wGroupLst = this.widgetGroupInfoMapper.getAllWidgetGroup();
		
		//将从表WidgetGroup获取的数据widgetGroupId值加上W，以及表WidgetDef中获取数据的WidgetGroupId值也加W
		//以便区分WidgetGroupId和WidgetId的值，从而找出上下级关系
		for (int i = 0; i < menuLst.size(); i++) {
			menuLst.get(i).setWidgetGroupId("W" + menuLst.get(i).getWidgetGroupId());
		}
		for (int i = 0; i < wGroupLst.size(); i++) {
			WidgetInfoBean bean = new WidgetInfoBean();
			bean.setWidgetId("W" + wGroupLst.get(i).getWidgetGroupId());
			bean.setWidgetGroupId("0");
			bean.setWidgetName(wGroupLst.get(i).getWidgetGroupName());
			menuLst.add(bean);
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		
		String portalId = request.getParameter("portalId");
		String portalName = request.getParameter("portalName");
		PortalInfoBean portalBean = null;
		StringBuffer widgetIds = new StringBuffer();
		if (portalId != null && !"null".equals(portalId)) {
			portalBean = portalInfoService.getPortalById(portalId);
		} else if (portalName != null  && !"null".equals(portalName)) {
			portalBean = portalInfoService.getPortalByName(portalName);
		}
		if (portalBean != null) {
			String portalContent = portalBean.getPortalContent();
			if (portalContent.contains("<widget") == true) {
				portalContent = portalContent.substring(portalContent.indexOf("<widget"), portalContent.indexOf("</grid>"));
				String[] widgetInfos = portalContent.split("</widget>");
				for (int i = 0; i < widgetInfos.length; i++) {
					if(widgetInfos[i].contains("\"") == true) {
						widgetIds.append(widgetInfos[i].split("\"")[1].split("#")[1]+",");
					}
				}
				widgetIds = widgetIds.deleteCharAt(widgetIds.lastIndexOf(","));
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		result.put("defaultWidgetIds", widgetIds);
		return result;
	}

	/**
	 * 加载widget页面
	 */
	@RequestMapping("/toShowWidget")
	public ModelAndView toShowWidget() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		widgetFilter = request.getParameter("widgetFilter");
		request.setAttribute("widgetFilter", widgetFilter);
		return new ModelAndView("monitor/portal/widgetInfo");
	}

	/**
	 * 加载第一个widget
	 */
	@RequestMapping("/showOneWidget")
	@ResponseBody
	public WidgetInfoBean showOneWidget(String widgetId){
		
		logger.info("加载的第一个widgetId= " + widgetId);
		return this.widgetInfoService.getWidgetByWidgetId(widgetId);
	}

	/**
	 * 加载第一个Portal
	 */
	@RequestMapping("/showOnePortal")
	@ResponseBody
	public PortalInfoBean showOnePortal(PortalInfoBean bean,HttpServletRequest request) throws Exception {
		
		logger.info("加载第一个portalName= " + bean.getPortalName());
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		SysUserInGroupsBean sysUserInGroupsBean = sysUserInGroupsService.
				findGroupsByUserId(sysUserInfoBeanTemp.getId().intValue()).get(0);
		PortalInfoBean portalBean = null;
		//判断用户是否为系统管理员
		if (sysUserInGroupsBean.getGroupId() == 1) {
			portalBean = this.portalInfoMapper.getPortalByName(bean.getPortalName());
		} else {
			bean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
			portalBean = this.portalInfoService.getPortalByNameAndUserId(bean);
			//若根据userId和portalName没有查到数据，则默认加载管理员数据
			if(portalBean == null){
				portalBean = this.portalInfoMapper.getPortalByName(bean.getPortalName());
			}
		}
		
		if (portalBean != null) {
			SAXReader reader = new SAXReader();
			if(portalBean.getPortalContent() != null && !"".equals(portalBean.getPortalContent())){
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
			}else{
				portalBean.setPortalContent("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			}
			
		}

		return portalBean;
	}


	  @RequestMapping(value = "/uploadPortalInfo", method = RequestMethod.POST)
	    public @ResponseBody
	    MessageBean createForm(@RequestParam("file") MultipartFile file,
	            @RequestParam("portalName") String portalName) throws Exception {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
					.getSession().getAttribute("sysUserInfoBeanOfSession");
		  BufferedReader reader = null;
			StringBuffer uploadPortalContent = new StringBuffer();
			try {
				InputStreamReader isr =new InputStreamReader( file.getInputStream(),"UTF-8");
				reader = new BufferedReader(isr);
				
				String tempString = null;
				int line = 1;
				
				// 一次读入一行，直到读入null为文件结束
				logger.info("开始读取xml文件");
				while ((tempString = reader.readLine()) != null) {
					
					// 显示行号
					uploadPortalContent.append(tempString);
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
				logger.error("XML格式有误");
				throw new SAXException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new IOException(e.getMessage());
			}

			logger.info("将读取到的xml文件解析入库");
			NodeList opPortalNode = doc.getElementsByTagName("portal");
			Element opPortalElm = (Element) opPortalNode.item(0);
			PortalInfoBean portalInfoBean = new PortalInfoBean();
			portalInfoBean.setPortalContent(uploadPortalContent.toString());
			portalInfoBean.setPortalType(1);
			portalInfoBean.setPortalDesc("");
			portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
			portalInfoBean.setCreateTime(dateUtil.getCurrDate());
			portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
			portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
			int toDbFlag = 0;
			if ("".endsWith(portalName)) {
				portalName = opPortalElm.getAttribute("portalName");
				portalInfoBean.setPortalName(portalName);
				toDbFlag = portalInfoMapper.insertPortal(portalInfoBean);
			} else {
				portalInfoBean.setPortalName(portalName);
				toDbFlag = portalInfoMapper.updatePortal(portalInfoBean);
			}
			
			logger.info("上传视图xml文件结束。。。");
			logger.info("导入portalXML over ");
			if (toDbFlag > 0) {
				return MessageBean.success("上传成功！");
			}
			return MessageBean.success("上传失败！");
	    }
	
	
	

	/**
	 * 查找节点ID
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public List<MObjectDefBean> searchTreeNodes(String classLable) {
		
		logger.info("根据节点名字查找监测对象节点信息");
		try {
			return mobjectInfoMapper.getTreeIdByTreeName(classLable);
		} catch (Exception e) {
			logger.error("查找节点异常！", e.getMessage());
			return null;
		}
	}

	/**
	 * 提供默认设备
	 */
	@RequestMapping("/initDeviceName")
	@ResponseBody
	public MODeviceObj initDeviceName(HttpServletRequest request) {
		Integer moClassId = Integer.parseInt(request.getParameter("moClassId"));
		MODeviceObj moDevice = moDeviceMapper.getFirstMoDevice(moClassId);
		return moDevice;
	}

	/**
	 * 提供默认接口
	 */
	@RequestMapping("/initInterfaceName")
	@ResponseBody
	public MONetworkIfBean initInterfaceName(HttpServletRequest request) {
		Integer parentMoClassId = Integer.parseInt(request
				.getParameter("parentMoClassId"));
		MONetworkIfBean moNetIf = moNetworkIfMapper
				.getFirstInterface(parentMoClassId);
		return moNetIf;
	}

	/**
	 * 提供默认中间件
	 */
	@RequestMapping("/initMiddleName")
	@ResponseBody
	public MOMiddleWareJMXBean initMiddleName(HttpServletRequest request) {
		String jmxType = request.getParameter("jmxType");
		MOMiddleWareJMXBean midBean = middlewareMapper.getFirstMiddle(jmxType);
		return midBean;
	}

	/**
	 * 提供默认数据库
	 */
	@RequestMapping("/initDbName")
	@ResponseBody
	public MODBMSServerBean initDbName(HttpServletRequest request) {
		String dbmstype = request.getParameter("dbmstype");
		MODBMSServerBean dbBean = oracleMapper.getFirstDbInfo(dbmstype);
		return dbBean;
	}

	/**
	 * 改变部件位置
	 */
	@RequestMapping("/toChangePortalStyle")
	@ResponseBody
	public PortalInfoBean toChangePortalStyle(String portalContent,String widgetIndex,String widgetNames) {
		//这块功能类似PlatformPortalEditController中的toChangePortalStyle
		widgetIndex = widgetIndex.replace("]||[", ",");
		
		logger.info("改变部件布局位置");
		
		//获取注释内容
		String portalContentOld = portalContent;
		StringBuffer contentSbuff = new StringBuffer();
		while (portalContentOld.contains("<!--") == true) {
			String str = portalContentOld.substring(portalContentOld.indexOf("<!--"),portalContentOld.indexOf("-->")+4);
			portalContentOld = portalContentOld.replace(str, "");
			str = str.substring(str.indexOf("<!--")+4, str.indexOf("-->"));
			contentSbuff.append(str);
			
		}
		
		PortalInfoBean portalBean = new PortalInfoBean();
		try {
			SAXReader reader = new SAXReader();
			
			//去掉注释内容
			portalContent = portalContent.replaceAll("(?s)<!--.*?-->","");
			
			StringReader in = new StringReader(portalContent);
			org.dom4j.Document doc;
			doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
			FileWriter filewriter = new FileWriter("xmlDemo.txt", false);
			filewriter.write(out.toString());
			filewriter.close();
			widgetIndex = widgetIndex.replace("},{", "};{");
			widgetIndex = widgetIndex.replace("\"", "");
			widgetIndex = widgetIndex.substring(1, widgetIndex.length() - 1);
			
			logger.info("转换后的部件位置信息："+widgetIndex);
			String[] allWidgetIndex = widgetIndex.split(";");
			String[] allWidgetNames = null;
			if (!"".equals(widgetNames)) {
				allWidgetNames = widgetNames.split(",");
			}
			Map<Integer, Map<String, String>> widgetMap = new HashMap<Integer, Map<String, String>>();
			
			//获取部件位置属性
			for (int i = 0; i < allWidgetIndex.length; i++) {
				allWidgetIndex[i] = allWidgetIndex[i].substring(1,
						allWidgetIndex[i].length() - 1);
				String[] widgetIndexs = allWidgetIndex[i].split(",");
				Map<String, String> indexInfoMap = new HashMap<String, String>();
				for (int j = 0; j < widgetIndexs.length; j++) {
					indexInfoMap.put(widgetIndexs[j].split(":")[0],
							widgetIndexs[j].split(":")[1]);
				}
				widgetMap.put(i, indexInfoMap);
			}
			
			BufferedReader buffReader = new BufferedReader(new FileReader(new File("xmlDemo.txt")));
			String tempString = null;
			StringBuffer sbuff = new StringBuffer();
			int line = 0;
			if (allWidgetNames != null) {
				StringBuffer sb = new StringBuffer();
				while ((tempString = buffReader.readLine()) != null) {
					sb.append(tempString);
				}
				for (int i = 0; i < allWidgetNames.length; i++) {
					StringBuffer sbuffWidget = new StringBuffer();
					String[] widgetInfos = sb.toString().split("</widget>");
					for (int j = 0; j < widgetInfos.length; j++) {
						String widgetInfoTmp = widgetInfos[j];
						if (allWidgetNames[i].contains("#") == true) {
							if (j != widgetInfos.length-1) {
								if (widgetInfoTmp.contains(allWidgetNames[i].split("#")[0]) == true 
										&& widgetInfoTmp.contains("#"+allWidgetNames[i].split("#")[1]) == true) {
									String tempStr = widgetInfoTmp.substring(0, widgetInfoTmp.indexOf("<widget"));
									sbuffWidget.append(tempStr);
								} else {
									sbuffWidget.append(widgetInfoTmp).append("</widget>");
								}
							} else {
								sbuffWidget.append(widgetInfoTmp);
							}
						} else {
							if (j != widgetInfos.length-1) {
								if (j == 0) {
									if (widgetInfoTmp.split("\"")[19].equals(allWidgetNames[i])) {
										String tempStr = widgetInfoTmp.substring(0, widgetInfoTmp.indexOf("<widget"));
										sbuffWidget.append(tempStr);
									} else {
										sbuffWidget.append(widgetInfoTmp).append("</widget>");
									}
								} else {
									if (widgetInfoTmp.split("\"")[1].equals(allWidgetNames[i])) {
										String tempStr = widgetInfoTmp.substring(0, widgetInfoTmp.indexOf("<widget"));
										sbuffWidget.append(tempStr);
									} else {
										sbuffWidget.append(widgetInfoTmp).append("</widget>");
									}
								}
							} else {
								sbuffWidget.append(widgetInfoTmp);
							}
						}
					}
					sb = sbuffWidget;
				}
				String[] widgetInfoNew = sb.toString().split("</widget>");
				StringBuffer widgetInfoNewSb = new StringBuffer();
				for (int k = 0; k < widgetInfoNew.length; k++) {
					String temp = widgetInfoNew[k];
					if (k != widgetInfoNew.length-1) {
						String rowspan = widgetMap.get(line).get("size_y");
						String colspan = widgetMap.get(line).get("size_x");
						String col = widgetMap.get(line).get("col");
						String row = widgetMap.get(line).get("row");
						String indexNew = "rowspan='" + rowspan + "' colspan='"
								+ colspan + "' col='" + col + "' row='" + row + "'>";
						String indexOld = null;
						if (temp.contains("rowspan") == true) {
							if (temp.contains("<embed") == true) {
								indexOld = temp.substring(temp.indexOf("rowspan"), temp.indexOf("<embed"));
							} else {
								indexOld = temp.substring(temp.indexOf("rowspan"), temp.indexOf("<chart"));
							}
							
						} else {
							indexOld = ">";
						}
						temp = temp.replace(indexOld, indexNew);
						widgetInfoNewSb.append(temp).append("</widget>");
						line++;
					} else {
						widgetInfoNewSb.append(temp);
					}
				}
				sbuff = widgetInfoNewSb;
			} else {
				while ((tempString = buffReader.readLine()) != null) {
					if (tempString.contains("<widget") == true) {
						String rowspan = widgetMap.get(line).get("size_y");
						String colspan = widgetMap.get(line).get("size_x");
						String col = widgetMap.get(line).get("col");
						String row = widgetMap.get(line).get("row");
						String indexNew = "rowspan='" + rowspan + "' colspan='"
								+ colspan + "' col='" + col + "' row='" + row + "'";
						String indexOld = null;
						if (tempString.contains("rowspan") == true) {
							indexOld = tempString.substring(tempString.indexOf("rowspan"), tempString.length() - 2);
						} else {
							indexOld = ">";
							indexNew = " rowspan='" + rowspan + "' colspan='"
									+ colspan + "' col='" + col + "' row='" + row
									+ "' >";
						}
						tempString = tempString.replace(indexOld, indexNew);
						line++;
					}
					sbuff.append(tempString);
				}
			}
			//将注释内容添加到新内容后面
			String sbuffStr = sbuff.toString();
			String tempStrPre = sbuffStr.substring(0, sbuffStr.indexOf("</grid>"));
			String tempStrEnd = sbuffStr.substring(sbuffStr.indexOf("</grid>"));
			sbuffStr = tempStrPre+"<!--"+contentSbuff+"-->"+tempStrEnd;
			
			StringReader inContent = new StringReader(sbuffStr);
			org.dom4j.Document doc2 = reader.read(inContent);
			OutputFormat formater2 = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			StringWriter out2 = new StringWriter();
			XMLWriter writer2 = new XMLWriter(out2, formater2);
			writer2.write(doc2);
			writer2.close();
			portalBean.setPortalContent(out2.toString());
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("视图部件布局异常！",e.getMessage());
		}
		return portalBean;
	}

	/**
	 * 加载第一个widget
	 */
	@RequestMapping("/toGetMultWidget")
	@ResponseBody
	public String toGetMultWidget(HttpServletRequest request){
		String widgetId = request.getParameter("widgetId");
		StringBuffer sbuff = new StringBuffer();
		try {
			if(widgetId != null){
				if (widgetId.contains(",") == true) {
					String[] widgetIds = request.getParameter("widgetId").split(",");
					for (int i = 0; i < widgetIds.length; i++) {
						if (!"0".equals(widgetIds[i]) && widgetIds[i].contains("W") == false) {
							WidgetInfoBean bean = this.widgetInfoService.getWidgetByWidgetId(widgetIds[i]);
							String widgetContent = bean.getWidgetContent();
							String widgetContentTmp = widgetContent.substring(widgetContent.indexOf("title"), widgetContent.indexOf(">"));
							String widgetTitle = widgetContentTmp.split("\"")[1];
							String widgetTitleNew = "title=\""+java.net.URLEncoder.encode(widgetTitle, "UTF-8")+"\"";
							widgetContent = widgetContent.replace(widgetContentTmp, widgetTitleNew);
							if (widgetContent.contains("label") == true) {
								String lableTmp = null;
								String lableNew = null;
								if (widgetContent.contains("</item>") == true) {
									lableTmp = widgetContent.substring(widgetContent.indexOf("label"), widgetContent.indexOf("</item>"));
									String lable = null;
									if (lableTmp.contains("\"") == true) {
										lable = lableTmp.split("\"")[1];
									} else {
										lable = lableTmp.split("\'")[1];
									}
									lableNew = "label=\""+java.net.URLEncoder.encode(lable, "UTF-8")+"\">";
								} else {
									lableTmp = widgetContent.substring(widgetContent.indexOf("label"), widgetContent.indexOf("/>"));
									String lable = null;
									if (lableTmp.contains("\"") == true) {
										lable = lableTmp.split("\"")[1];
									} else {
										lable = lableTmp.split("\'")[1];
									}
									lableNew = "label=\""+java.net.URLEncoder.encode(lable, "UTF-8")+"\"";
								}
								
								widgetContent = widgetContent.replace(lableTmp, lableNew);
							}
							sbuff.append(bean.getWidgetId()+"|"+widgetContent);
						}
						
					}
				} else {
					if (!"0".equals(widgetId) && widgetId.contains("W") == false) {
						WidgetInfoBean bean = this.widgetInfoService.getWidgetByWidgetId(widgetId);
						String widgetContent = bean.getWidgetContent();
						String widgetContentTmp = widgetContent.substring(widgetContent.indexOf("title"), widgetContent.indexOf(">"));
						String widgetTitle = widgetContentTmp.split("\"")[1];
						String widgetTitleNew = "title=\""+java.net.URLEncoder.encode(widgetTitle, "UTF-8")+"\"";
						widgetContent = widgetContent.replace(widgetContentTmp, widgetTitleNew);
						if (widgetContent.contains("label") == true) {
							String lableTmp = null;
							String lableNew = null;
							if (widgetContent.contains("</item>") == true) {
								lableTmp = widgetContent.substring(widgetContent.indexOf("label"), widgetContent.indexOf("</item>"));
								String lable = null;
								if (lableTmp.contains("\"") == true) {
									lable = lableTmp.split("\"")[1];
								} else {
									lable = lableTmp.split("\'")[1];
								}
								lableNew = "label=\""+java.net.URLEncoder.encode(lable, "UTF-8")+"\">";
							} else {
								lableTmp = widgetContent.substring(widgetContent.indexOf("label"), widgetContent.indexOf("/>"));
								String lable = null;
								if (lableTmp.contains("\"") == true) {
									lable = lableTmp.split("\"")[1];
								} else {
									lable = lableTmp.split("\'")[1];
								}
								lableNew = "label=\""+java.net.URLEncoder.encode(lable, "UTF-8")+"\"";
							}
							
							widgetContent = widgetContent.replace(lableTmp, lableNew);
						}
						sbuff.append(bean.getWidgetId()+"|"+widgetContent);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return sbuff.toString();
	}
}
