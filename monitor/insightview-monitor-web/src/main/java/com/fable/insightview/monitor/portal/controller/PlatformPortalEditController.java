package com.fable.insightview.monitor.portal.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.portal.entity.PortalInfoBean;
import com.fable.insightview.monitor.portal.service.DateUtil;
import com.fable.insightview.platform.portal.service.IPortalInfoService;
import com.fable.insightview.monitor.portal.service.IWidgetInfoService;

@Controller
@RequestMapping("/platform/platformPortalEdit")
public class PlatformPortalEditController {
	
	private final Logger logger = LoggerFactory.getLogger(PlatformPortalEditController.class);
	DateUtil dateUtil = new DateUtil();
	
	@Autowired
	IPortalInfoService portalInfoService;
	@Autowired
	IWidgetInfoService widgetInfoService;
	
	String portalContent = null;
	String widgetFilter = null;


	/**
	 * 加载平台portal编辑页面
	 */
	@RequestMapping("/toShowPlatformPortalEdit")
	public ModelAndView toShowPortalEdit(String navigationBar) {

		logger.info("编辑视图页面。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String portalDefine = "<?xml version='1.0' encoding='UTF-8'?>";
		request.setAttribute("portalDefine", portalDefine);
		request.setAttribute("navigationBar", navigationBar);
		return new ModelAndView("platform/portal/platformPortalEdit");
	}
	
	/**
	 * 初始化树
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/initPortalTree")
	@ResponseBody
	public Map<String, Object> initPortalTree() throws Exception {
		
		logger.info("初始化Portal树");
		List<PortalInfoBean> menuLst = portalInfoService.queryPortalInfos();
		String menuLstJson = JsonUtil.toString(menuLst);
		
		logger.info("menuLstJson:" + menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	/**
	 * 新增portal树节点
	 * @param portalInfoBean
	 * @return
	 */
	@RequestMapping("/addPortalTree")
	@ResponseBody
	public boolean addPortalTree(PortalInfoBean portalInfoBean) {

		logger.info("新增PortalTree ...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		portalInfoBean.setCreator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setCreateTime(dateUtil.getCurrDate());
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
		int insertFlag = portalInfoService.insertPortal(portalInfoBean);
		
		logger.info("新增PortalTree over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 加载portalTree节点信息
	 */
	@RequestMapping("/showPortalTreeInfo")
	@ResponseBody
	public PortalInfoBean showPortalTreeInfo(PortalInfoBean bean,HttpServletRequest request) throws Exception {
		
		PortalInfoBean portalBean = portalInfoService.getPortalByName(bean.getPortalName());
		return portalBean;
	}
	
	/**
	 * 修改portal树节点
	 * @param portalInfoBean
	 * @return
	 */
	@RequestMapping("/modifyPortalTree")
	@ResponseBody
	public boolean modifyPortalTree(PortalInfoBean portalInfoBean) {

		logger.info("更新PortalTree ...");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		portalInfoBean.setUpdator(sysUserInfoBeanTemp.getId().intValue());
		portalInfoBean.setLastUpdateTime(dateUtil.getCurrDate());
		portalInfoBean.setOwnerUserId(sysUserInfoBeanTemp.getId().intValue());
		int insertFlag = portalInfoService.updatePortalTree(portalInfoBean);
		
		logger.info("更新PortalTree over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除portal树节点
	 * @param portalInfoBean
	 * @return
	 */
	@RequestMapping("/deletePortalTree")
	@ResponseBody
	public boolean deletePortalTree(PortalInfoBean portalInfoBean) {

		logger.info("删除PortalTree ...");
		int insertFlag = portalInfoService.deletePortal(portalInfoBean);
		
		logger.info("删除PortalTree over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 加载widget页面
	 */
	@RequestMapping("/toShowWidget")
	public ModelAndView toShowWidget() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String tabsIndex = request.getParameter("tabsIndex");
		widgetFilter = request.getParameter("widgetFilter");
		String portalId = request.getParameter("portalId");
		String portalName = request.getParameter("portalName");
		request.setAttribute("widgetFilter", widgetFilter);
		request.setAttribute("tabsIndex", tabsIndex);
		request.setAttribute("portalId", portalId);
		request.setAttribute("portalName", portalName);
		return new ModelAndView("platform/portal/platformWidgetInfo");
	}
	
	/**
	 * 改变部件位置
	 */
	@RequestMapping("/toChangePortalStyle")
	@ResponseBody
	public PortalInfoBean toChangePortalStyle(String portalContent,String widgetIndex,String urlMaps,String widgetMaps,String widgetNames) {
		//widgetIndex存放的是点击保存视图时，gridster自动获取的最新的所有部件的大小和位置信息
		//portalContent是原始从表获取的视图xml，我们要做的就是把改变后的部件大小位置信息，替换到portalContent中部件的大小信息
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String flag = request.getParameter("flag");
		//替换xml中的&amp;字符串
		if(portalContent.contains("&amp;") == false){
			portalContent = portalContent.replace("&", "&amp;");
		}
		
		//获取注释内容
		String portalContentOld = portalContent;
		StringBuffer contentSbuff = new StringBuffer();
		while (portalContentOld.contains("<!--") == true) {
			String str = portalContentOld.substring(portalContentOld.indexOf("<!--"),portalContentOld.indexOf("-->")+4);
			portalContentOld = portalContentOld.replace(str, "");
			str = str.substring(str.indexOf("<!--")+4, str.indexOf("-->"));
			contentSbuff.append(str);
			
		}
		
		//去掉注释内容
		portalContent = portalContent.replaceAll("(?s)<!--.*?-->","");
		
		Map<String,String> urlMap = new HashMap<String,String>();
		Map<String,String> widgetTitleMap = new HashMap<String,String>();
		widgetIndex = widgetIndex.replace("]||[", ",");
		//{"HostMemoryChart_":"/monitor/gaugeChartManage/findChartMemory?moID=108065;moClass=8","HostAvailabilityChart_":"/monitor/gaugeChartManage/findChartAvailability?moID=107532;moClass="}
		//urlMaps存放的是部件名称和数据源url
		urlMaps = urlMaps.substring(1,urlMaps.length()-1);
		urlMaps = urlMaps.replace("\"", "");
		urlMaps = urlMaps.replace("_", "");
		if(urlMaps.contains(",") == true){
			for (int i = 0; i < urlMaps.split(",").length; i++) {
				urlMap.put(urlMaps.split(",")[i].split(":")[0], urlMaps.split(",")[i].split(":")[1]);
			}
		}else{
			urlMap.put(urlMaps.split(":")[0], urlMaps.split(":")[1]);
		}
		PortalInfoBean portalBean = new PortalInfoBean();
		//widgetMaps
		if ("[]".equals(widgetIndex)) {
			String contentPre = portalContent.substring(0,portalContent.indexOf("<widget"));
			String contentEnd = portalContent.substring(portalContent.indexOf("</grid>"));
			portalBean.setPortalContent(contentPre+contentEnd);
		} else {
			widgetMaps = widgetMaps.substring(1,widgetMaps.length()-1);
			widgetMaps = widgetMaps.replace("\"", "");
			widgetMaps = widgetMaps.replace("_", "");
			if(widgetMaps.contains(",") == true){
				for (int i = 0; i < widgetMaps.split(",").length; i++) {
					widgetTitleMap.put(widgetMaps.split(",")[i].split(":")[0], widgetMaps.split(",")[i].split(":")[1]);
				}
			}else{
				if(widgetMaps.contains(":") == true){
					widgetTitleMap.put(widgetMaps.split(":")[0], widgetMaps.split(":")[1]);
				}
			}
			
			logger.info("改变部件布局位置");
			
			try {
				//将视图xml写入文件，然后通过文件一行行读取并根据部件名称替换位置大小
				SAXReader reader = new SAXReader();
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
				int count = 0;
				String rowsValue = "";
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
						}
						sb = sbuffWidget;
					}
					String[] widgetInfoNew = sb.toString().split("</widget>");
					StringBuffer widgetInfoNewSb = new StringBuffer();
					for (int k = 0; k < widgetInfoNew.length; k++) {
						String temp = widgetInfoNew[k];
						if (k != widgetInfoNew.length-1) {
							String widgetTmp = temp.split("<widget")[1];
							//更新部件title
							String nameVal = widgetTmp.substring(widgetTmp.indexOf("name"), widgetTmp.indexOf("title"));
							String widgetId = nameVal.split("\"")[1].split("#")[1];
							String widgetTitle = widgetTitleMap.get(widgetId);
							if (widgetTitle != null) {
								String titleValNew = "title=\""+widgetTitle+"\" ";
								String titleVal = widgetTmp.substring(widgetTmp.indexOf("title"), widgetTmp.indexOf("isEdit"));
								widgetTmp = widgetTmp.replace(titleVal, titleValNew);
								temp = temp.split("<widget")[0]+"<widget"+widgetTmp;
							}
							//重置部件大小
							String rowspan = widgetMap.get(line).get("size_y");
							String colspan = widgetMap.get(line).get("size_x");
							String col = widgetMap.get(line).get("col");
							String row = widgetMap.get(line).get("row");
							if(!rowsValue.equals(row) == true){
								count = 0;
								rowsValue = row;
							}else{
								count ++ ;
							}
							String indexNew = "rowspan='" + rowspan + "' colspan='"
							+ colspan + "' col='" + col + "' row='" + row + "'>";
							int colspanValue =  Integer.parseInt(colspan);
							if ("homepage".equals(flag)) {
								if(!"1".equals(col)){
									if(colspanValue <= 20 && colspanValue > 11){
										col = String.valueOf(Integer.parseInt(col)-2);
									}else if(colspanValue <= 12){
										if(count < 2){
											col = String.valueOf(Integer.parseInt(col)-1);
										}else{
											col = String.valueOf(Integer.parseInt(col)-2);
										}
									}else{
										col = String.valueOf(Integer.parseInt(col)-4);
									}
									
								}
								//Integer.parseInt(colspan) <= 17 && Integer.parseInt(colspan) > 11
								if(Integer.parseInt(colspan) <= 20 && Integer.parseInt(colspan) > 11){
									indexNew = "rowspan='" + rowspan + "' colspan='"
									+ String.valueOf(Integer.parseInt(colspan)-2) + "' col='" + col + "' row='" + row + "'>";
								}else if(Integer.parseInt(colspan) <= 12){
									if(count < 2){
										indexNew = "rowspan='" + rowspan + "' colspan='"
										+ String.valueOf(Integer.parseInt(colspan)-1) + "' col='" + col + "' row='" + row + "'>";
									}else{
										indexNew = "rowspan='" + rowspan + "' colspan='"
										+ String.valueOf(Integer.parseInt(colspan)-2) + "' col='" + col + "' row='" + row + "'>";
									}
									
								}else{
									indexNew = "rowspan='" + rowspan + "' colspan='"
									+ String.valueOf(Integer.parseInt(colspan)-4) + "' col='" + col + "' row='" + row + "'>";
								}
								
							}
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
							//更新url
							if (temp.contains("<chart") == true) {
								String idValue = temp.substring(temp.indexOf("id"), temp.indexOf("type")).split("\"")[1];
								String urlValue = urlMap.get(idValue);
								if(urlValue != null){
									String chartStr = temp.substring(0,temp.indexOf("dataSourceUrl"));
									String chartNew = chartStr+" dataSourceUrl=\""+urlValue+"\">";
									chartNew = chartNew.replace("&", "&amp;");
									temp = chartNew;
								}
							}
							if (temp.contains("<embed") == true) {
								String idValue = temp.substring(temp.indexOf("id"), temp.indexOf("url")).split("\"")[1];
								String urlValue = urlMap.get(idValue);
								if(urlValue != null){
									String chartStr = temp.substring(0,temp.indexOf("url"));
									String chartNew = chartStr+" url=\""+urlValue+"\" />";
									chartNew = chartNew.replace("&", "&amp;");
									temp = chartNew;
								}
							}
							
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
							String nameVal = tempString.substring(tempString.indexOf("name"), tempString.indexOf("title"));
							String widgetId = nameVal.split("\"")[1].split("#")[1];
							String widgetTitle = widgetTitleMap.get(widgetId);
							if (widgetTitle != null) {
								String titleValNew = "title=\""+widgetTitle+"\" ";
								String titleVal = tempString.substring(tempString.indexOf("title"), tempString.indexOf("isEdit"));
								tempString = tempString.replace(titleVal, titleValNew);
							}
							String indexNew = "";
							String rowspan = widgetMap.get(line).get("size_y");
							String colspan = widgetMap.get(line).get("size_x");
							String row = widgetMap.get(line).get("row");
							String col = widgetMap.get(line).get("col");
							if(!rowsValue.equals(row) == true){
								count = 0;
								rowsValue = row;
							}else{
								count ++ ;
							}
							int colspanValue =  Integer.parseInt(colspan);
							if ("homepage".equals(flag)) {
								if(!"1".equals(col)){
									if(colspanValue <= 20 && colspanValue > 10){
										col = String.valueOf(Integer.parseInt(col)-2);
									}else if(colspanValue <= 12){
										if(count < 2){
											col = String.valueOf(Integer.parseInt(col)-1);
										}else{
											col = String.valueOf(Integer.parseInt(col)-2);
										}
									}else{
										col = String.valueOf(Integer.parseInt(col)-4);
									}
									
								}
								//Integer.parseInt(colspan) <= 17 && Integer.parseInt(colspan) > 11
								if(Integer.parseInt(colspan) <= 20 && Integer.parseInt(colspan) > 10){
									indexNew = "rowspan='" + rowspan + "' colspan='"
									+ String.valueOf(Integer.parseInt(colspan)-2) + "' col='" + col + "' row='" + row + "'";
								}else if(Integer.parseInt(colspan) <= 12){
									if(count < 2){
										indexNew = "rowspan='" + rowspan + "' colspan='"
										+ String.valueOf(Integer.parseInt(colspan)-1) + "' col='" + col + "' row='" + row + "'";
									}else{
										indexNew = "rowspan='" + rowspan + "' colspan='"
										+ String.valueOf(Integer.parseInt(colspan)-2) + "' col='" + col + "' row='" + row + "'";
									}
									
								}else{
									indexNew = "rowspan='" + rowspan + "' colspan='"
									+ String.valueOf(Integer.parseInt(colspan)-4) + "' col='" + col + "' row='" + row + "'";
								}
								
							} else {
								indexNew = "rowspan='" + rowspan + "' colspan='"
								+ colspan + "' col='" + col + "' row='" + row + "'";
							}
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
						if (tempString.contains("<chart") == true) {
							String idValue = tempString.substring(tempString.indexOf("id"), tempString.indexOf("type")).split("\"")[1];
							String urlValue = urlMap.get(idValue);
							if(urlValue != null){
								String chartStr = tempString.substring(0,tempString.indexOf("dataSourceUrl"));
								String chartNew = chartStr+" dataSourceUrl=\""+urlValue+"\">";
								chartNew = chartNew.replace("&", "&amp;");
								tempString = chartNew;
							}
						}
						if (tempString.contains("<embed") == true) {
							String idValue = tempString.substring(tempString.indexOf("id"), tempString.indexOf("url")).split("\"")[1];
							String urlValue = urlMap.get(idValue);
							if(urlValue != null){
								String chartStr = tempString.substring(0,tempString.indexOf("url"));
								String chartNew = chartStr+" url=\""+urlValue+"\" />";
								chartNew = chartNew.replace("&", "&amp;");
								tempString = chartNew;
							}
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
				formater2.setEncoding("utf-8");
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
		}
		
		return portalBean;
	}
	
	
	/**
	 * 查找节点ID
	 * 
	 * @param constantTypeCName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public PortalInfoBean searchTreeNodes(PortalInfoBean portalBean) {
		PortalInfoBean Bean = new PortalInfoBean();
		List<PortalInfoBean> portalBeans = portalInfoService.queryPortalInfosByDesc(portalBean);
		if(portalBeans != null){
			String portalIds = "";
			for (int i = 0; i < portalBeans.size(); i++) {
				portalIds = portalBeans.get(i).getPortalId()+",";
			}
			if (portalIds.contains(",") == true) {
				portalIds = portalIds.substring(0, portalIds.lastIndexOf(","));
			}
			Bean.setPortalIds(portalIds);
		}
		
		return Bean;
	}
	
	/**
	 * 打开新增页面
	 */
	@RequestMapping("/toShowAdd")
	@ResponseBody
	public ModelAndView toShowAdd(HttpServletRequest request) {
		String tabsIndex = request.getParameter("tabsIndex");
		request.setAttribute("tabsIndex", tabsIndex);
		return new ModelAndView("platform/portal/platformPortal_add");
	}
	
	/**
	 * 删除portal树节点
	 * @param portalInfoBean
	 * @return
	 */
	@RequestMapping("/deletePortalView")
	@ResponseBody
	public boolean deletePortalView(PortalInfoBean portalInfoBean) {

		logger.info("删除PortalView ...");
		int insertFlag = portalInfoService.deletePortalByName(portalInfoBean);
		
		logger.info("删除PortalView over ...");
		if (insertFlag > 0) {
			return true;
		}
		return false;
	}
}
