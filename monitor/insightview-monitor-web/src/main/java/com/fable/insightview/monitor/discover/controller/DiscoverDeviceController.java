package com.fable.insightview.monitor.discover.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.ZookeeperRegister;
import com.fable.insightview.monitor.discover.entity.Count;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.dispatcher.utils.DispatcherUtils;
import com.fable.insightview.monitor.messTool.entity.notify.DiscoveryProgressNotification;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

/**
 * 
 * 设备发现控制器,包括参数保存,发现进度获取,结果统计,设备列表等
 * 
 */
@Controller
@RequestMapping("/monitor/discover")
public class DiscoverDeviceController {
	private static final long serialVersionUID = -8697049781798812644L;

	private static final Logger log = LoggerFactory
			.getLogger(DiscoverDeviceController.class);

	public static Properties ZKPrpperty = null;

	@Autowired
	SysNetworkDiscoverTaskMapper sysNetworkDiscover;

	@Autowired
	MODeviceMapper moDeviceMapper;

	@Autowired
	IPerfTaskService taskService;

	@RequestMapping("/discoverDevice")
	public ModelAndView discoverDevice(HttpServletRequest result,
			HttpServletResponse response,String navigationBar) {
		ModelAndView mv = new ModelAndView("monitor/discover/discoverStep1");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	@RequestMapping("/saveDiscoverParam")
	public void saveDiscoverParam(HttpServletRequest request,
			HttpServletResponse response) {
		SysNetworkDiscoverTask discover = new SysNetworkDiscoverTask();
		int way = Integer.parseInt(request.getParameter("way"));
		if (way == 1) {
			discover.setIpaddress1(request.getParameter("startIP1"));
			discover.setIpaddress2(request.getParameter("endIP1"));
		} else if (way == 2) {
			discover.setIpaddress1(request.getParameter("startIP2"));
			discover.setNetmask(request.getParameter("netMask2"));
		} else if (way == 3) {
			discover.setIpaddress1(request.getParameter("startIP3"));
			discover.setHops(Integer.parseInt(request.getParameter("step3")));
		}

		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		// 1:地址段 2:子网 3:路由表
		discover.setTasktype(way);
		discover.setWebipaddress(DispatcherUtils.getLocalAddress());
		discover.setCreator(sysUserInfoBeanTemp.getId().intValue());
		discover.setCreatetime(new Date());
		discover.setProgressstatus(1);
		discover.setOperatestatus(1);
		// 保存
		try {
			sysNetworkDiscover.insert2(discover);
			log.info("保存任务结束 taskID=" + discover.getTaskid());
			response.setContentType("application/json");
			// 返回任务编号
			response.getWriter().write(
					"{\"taskID\":\"" + discover.getTaskid() + "\"}");
		} catch (Exception e) {
			try {
				response.setContentType("application/json");
				String errorInfo = "保存入库错误!";
				response.getWriter().write(
						"{\"errorInfo\":\"" + errorInfo + "\"}");
			} catch (IOException e1) {
			}
		}

		log.info("发送消息通知发现程序!");
		// 发送消息给
		sendMeg();
	}

	@RequestMapping("/startDiscover")
	public ModelAndView startDiscover(HttpServletRequest request,
			HttpServletResponse response,String navigationBar) {
		String taskID = request.getParameter("taskID");
		String way = request.getParameter("way");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/discover/discoverStep2");
		mv.addObject("taskID", taskID);
		mv.addObject("way", way);
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 获取发现进度信息返回给显示
	 * 
	 * @param 任务编号
	 */
	@RequestMapping("/getProgress2")
	public void discoverProcess2(HttpServletRequest request,
			HttpServletResponse response) {
		String taskID = request.getParameter("taskID3");
		int progressValue = 1;
		try {
			ZookeeperRegister reg = (ZookeeperRegister) BeanLoader
					.getBean("zookeeperMonitorService");
			LinkedList<DiscoveryProgressNotification> processInfo = new LinkedList<DiscoveryProgressNotification>();
			StringBuffer returnTmp = new StringBuffer();
			String progressValueString = "";
			boolean isError = false;
			try {
				if (taskID != null && taskID.compareTo("") > 0) {
					processInfo = reg.getProcess(Integer.parseInt(taskID));
				}

				if(processInfo !=null){
			
				for (DiscoveryProgressNotification discover : processInfo) {
					String progress = discover.getProgress(); // "10/30"
					// 以便日志打印，查询进度发现有时会出现卡顿现象
					log.error("发现progress=" + progress + " getIp="
							+ discover.getIp()+",status="+discover.getStatus()+",error="+discover.getErrorInfo());
					if (returnTmp.indexOf("progressValue") > 1) {
						returnTmp.append(",");
					}
					progressValue = percent(Integer.parseInt(progress
							.substring(0, progress.indexOf("/"))), Integer
							.parseInt(progress.substring(
									progress.indexOf("/") + 1, progress
											.length())));
					log.error("progressValue=="+progressValue);
					if (discover.getErrorInfo() != null
							&& !discover.getErrorInfo().equals("")) {
						log.error("发现错误。。。。");
						progressValueString = moDeviceMapper
								.getErrorMessByErrorID(discover.getErrorInfo());
						progressValue = 0;
						isError = true;
					} else {
						String statusString =  discover.getStatus();
						String status = null;
						if(!"".equals(statusString) &&statusString != null){
							if(statusString.contains("connect")){
								status = statusString;
							}else{
								status = moDeviceMapper.getErrorMessByErrorID(statusString);
							}
						}
						progressValueString = "正在发现:" + discover.getIp() + " 状态:" + status;
						if (progressValue == 100) {
							getStoreProcess(taskID, progress.substring(progress
									.indexOf("/") + 1, progress.length()));
						}
						isError = false;
					 }
					// 以便日志打印，查询进度发现有时会出现卡顿现象
					log.error("返回：progressValue="+progressValue+",progressValueString="+progressValueString+",isError="+isError);
					returnTmp.append("{\"progressValue\":\"" + progressValue
							+ "\",\"progressString\":\"" + progressValueString
							+ "\",\"isError\":\"" + isError
							+ "\"}"); 
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}

			response.setContentType("application/json");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("[" + returnTmp.toString() + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当发现进度等于100时,检查入库数据
	 * 
	 * @param taskID
	 * @param totalCount
	 */
	public int getStoreProcess(String taskID, String totalCount) {
		int taskid = Integer.parseInt(taskID);
		int totalcount = Integer.parseInt(totalCount);
		// System.out.println("taskid:"+taskid+"  totalcount:"+totalcount);
		int tmpProcess = 0;
		for (int n = 0; n < 2; n++) {
			int process = moDeviceMapper.getDiscoverStoreProcess(taskid);
			if (process > tmpProcess) {
				n--;
				tmpProcess = process;
			}

			if (totalcount - process == 0) {
				return 100;
			}

			if (totalcount > process) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return 100;
	}

	/**
	 * 以设备类型图标统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDiscoverResult")
	public ModelAndView getDiscoverResult(HttpServletRequest request,
			HttpServletResponse response,String navigationBar) {
		String taskID = request.getParameter("taskID");
		LinkedList<Count> moList = moDeviceMapper.getCountByMOClassID(Integer
				.parseInt(taskID));
		int count = 0;
		StringBuffer data = new StringBuffer();

		for (Count list : moList) {
			String type = list.getDeviceType();
			int moClassID = list.getMoClassID();
			int num = list.getCount();
			// 5代表5列，如果太多的需要换行
			if (count == 0 || count > 5) {
				count++;
				data.append("<tr style='height:40px'>");
			}

			data.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:showtr2("
							+ moClassID
							+ ")'>"
							+ "<img src='"
							+ request.getContextPath()
							+ "/style/images/snmphost.gif'></a><br>&nbsp;&nbsp;&nbsp;"
							+ "" + type + ":" + num + "</br></td>");

			if (count > 5) {
				count = 0;
				data.append("</tr>");
			}
		}

		if (data.toString().lastIndexOf("</tr>") < data.toString().length() - 4) {
			data.append("</tr>");
		}

		if (data.toString().indexOf("<td>") < 0) {
			data = new StringBuffer();
			data.append("<tr style='height:40px'><td>没有发现新设备</td>");
			data.append("</tr>");
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/discover/discoverStep3");
		mv.addObject("taskID", taskID);
		mv.addObject("result", data.toString());
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	@RequestMapping("/importDiscoverResult")
	public ModelAndView importDiscoverResult(HttpServletRequest request,
			HttpServletResponse response,String navigationBar) {
		String taskID = request.getParameter("taskID");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/discover/discoverStep4");
		mv.addObject("taskID", taskID);
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}

	/**
	 * 获取树节点
	 * 
	 * @param taskID
	 */
	@RequestMapping("/getTreeJSON")
	@ResponseBody
	public Map<String, Object> getTreeJSON(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("获取树结点");
		Integer taskID = Integer.parseInt(request.getParameter("taskID"));
		// 以前是按照操作系统来分类的
//		LinkedList<MODeviceObj> moList = moDeviceMapper.getDiscoverResultByOS(taskID);
		
		// TOPO 现在是按照设备类型来分类的 ，modify by zhuk start
		LinkedList<MODeviceObj> moList = moDeviceMapper.getDiscoverResultByMOClassID(taskID);
		// TOPO 现在是按照设备类型来分类的 ，modify by zhuk ebd
		
		
		String menuLstJson = JsonUtil.toString(moList);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// System.out.println(menuLstJson);
		result.put("treeJSON", menuLstJson);
		return result;
		// StringBuffer data = new StringBuffer();
		// data.append("(");
		// data.append("[{'id':1,'text':'所有设备','children':[");
		// if (moList.size() > 0) {
		// int parentID = 999999900;
		// String tmpOS = "";
		// for (MODeviceObj mo : moList) {
		// if (mo.getOs() == null || mo.getOs().equals("NULL") ||
		// mo.getOs().equals("")) {
		// mo.setOs("OS未知");
		// }
		// if (data.indexOf("checked")> 1 && !tmpOS.equals(mo.getOs())) {
		// // 去掉最后一位","
		// data.delete(data.length() - 1, data.length());
		// data.append("]");
		// data.append("},");
		// }
		//     
		// if (!tmpOS.equals(mo.getOs())) {
		// parentID++;
		// data.append("{");
		// data.append("'id':" + parentID + ",'text':'" + mo.getOs()
		// + "','state':'closed','children':");
		// data.append("[");
		// tmpOS = mo.getOs();
		// }
		// data.append("{'id':" + mo.getMoid() + ",'text':'"+ mo.getDeviceip() +
		// "','checked':true},");
		// }
		// }
		// if (data.indexOf("checked")> 1){
		// data.delete(data.length() - 1, data.length());
		// }
		//		
		// data.append("]");
		// data.append("}");
		// // 添加结尾标记
		// data.append("]}]");
		// data.append(")");
		// // System.out.println(data.toString().replaceAll("'", "\""));
		// try {
		// response.setContentType("application/json");
		// response.setContentType("text/html;charset=UTF-8");
		// String returnstr = data.toString();
		// response.getWriter().write("{\"treeJSON\":\"" + returnstr + "\"}");
		// // response.getWriter().write("{\"treeJSON2\":\"[" +
		// data.toString().replaceAll("'", "\"") + "\"]}");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 保存发现结果
	 * 
	 * @param request
	 *            moids
	 */
	@RequestMapping("/storeDiscoverResult")
	@ResponseBody
	public boolean storeDiscoverResult(String moids) {
		try {
			// 采集任务创建结果
//			boolean addFlag = false;

			if (moids != null && !moids.equals("null")
					&& moids.compareTo("") > 0) {
				moids = moids.replace("undefined1,", "");
				moids = moids.replace("undefined,", "");
				moids = moids.replace("undefined", "");
				if (moids.substring(moids.length() - 1, moids.length()).equals(
						",")) {
					moids = moids.substring(0, moids.length() - 1);
				}
				// 更新MODevice.IsManage

				// 生成默认采集任务
				String[] ids = moids.split(",");
				List<Integer> idlist = new ArrayList<Integer>();
				for (int i = 0; i < ids.length; i++) {
					int id = Integer.parseInt(ids[i]);
					if (id > 999999900) {
						continue;
					}
					idlist.add(id);
					moDeviceMapper.updateStateByPrimaryKey(ids[i]);
				}
//				addFlag = taskService.addPerfTasks(idlist);
			}

			// 如果创建集任务成功完成，发送通知，返回true
//			if (addFlag == true) {
//				// 发送任务消息
//				sendMeg();
//				return true;
//			} else {
//				return false;
//			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 发现设备列表
	 */
	@RequestMapping("/toDiscoverDeviceList")
	public ModelAndView toDiscoverDeviceList(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String index = request.getParameter("index");
		if (flag != null && !"".equals(flag)) {
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("flag", "doInit");
		}
		request.setAttribute("index", index);
		String devicetype = request.getParameter("devicetype");
		String taskId = request.getParameter("taskID");
		String moClassId = request.getParameter("moClassId");
		String deviceType1 = request.getParameter("deviceType1");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("monitor/discover/deviceInfoList");
		mv.addObject("devicetype", devicetype);
		mv.addObject("taskId", taskId);
		mv.addObject("moClassId", moClassId);
		mv.addObject("deviceType1", deviceType1);
		return mv;
	}

	@RequestMapping("/selectDeviceList")
	@ResponseBody
	public Map<String, Object> selectDeviceList(MODeviceObj mo) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
					.getFlexiGridPageInfo(request);
			Page<MODeviceObj> page = new Page<MODeviceObj>();
			page.setPageNo(flexiGridPageInfo.getPage());
			page.setPageSize(flexiGridPageInfo.getRp());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("taskId", mo.getTaskId());
			paramMap.put("deviceip", mo.getDeviceip());
			paramMap.put("devicetype", mo.getDevicetype());
			paramMap.put("moClassId", mo.getMoClassId());
			paramMap.put("moname", mo.getMoname());
			paramMap.put("sort", request.getParameter("sort"));
			paramMap.put("order", request.getParameter("order"));
			if (request.getParameter("deviceType1") != null && !request.getParameter("deviceType1").equals("")) {
				paramMap.put("deviceType1", "(" + request.getParameter("deviceType1") + ")");
			}
			page.setParams(paramMap);
			// 获取SNMPDeviceSysObjectID表中所有的数据
			List<MODeviceObj> SNMPDeviceCount = moDeviceMapper.getSNMPDeviceCount();
			Map<Integer,String> ResIDAndNameMap = new HashMap<Integer, String>();
			for (MODeviceObj moDeviceObj : SNMPDeviceCount) {
				ResIDAndNameMap.put(moDeviceObj.getResCategoryID(), moDeviceObj.getDevicemodelname());
			}
			 
			List<MODeviceObj> moList = moDeviceMapper.MoDeviceProInfoList2(page);
					
			for(MODeviceObj device:moList){
				if(device.getNecategoryid()!=null){
					device.setDevicemodelname(ResIDAndNameMap.get(device.getNecategoryid()));
				}
			}
			
			Map<String, Object> result = new HashMap<String, Object>();

			result.put("total", page.getTotalRecord());
			result.put("rows", moList);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 百分比
	 * 
	 * @param p1
	 * @param p2
	 * @return int
	 */
	public static int percent(int p1, int p2) {
		System.out.println("p1="+p1+",p2="+p2);
		if(p1 == 0){
			return 0;
		}
		if (p2 == 0) {
			return 100;
		}
		java.math.BigDecimal big = new java.math.BigDecimal((double) p1 / p2
				* 100);
		return big.setScale(0, java.math.BigDecimal.ROUND_HALF_UP).intValue();// +
																				// "%";
	}

	/**
	 * 发送消息给zookeeper,通知任务分发
	 */
	public static void sendMeg() {
		try {
			log.info("开始发送消息给zookeeper!");
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_DISCOVERY);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
		} catch (Exception e) {
			log.error("发送消息给zookeeper出错", e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据设备信息获取设备类型名
	 * 
	 * @param mo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectMoClass")
	@ResponseBody
	public String selectMoClass(MODeviceObj mo) throws Exception {
		return this.moDeviceMapper.getMoClassByMoId(mo);
	}

	/**
	 * 获取某一设备详细信息
	 * 
	 * @param mo
	 * @return
	 */
	@RequestMapping("/findMODeviceInfo")
	@ResponseBody
	public MODeviceObj findMODeviceInfo(MODeviceObj mo) {
		MODeviceObj moDeviceInfo = moDeviceMapper.selectByPrimaryKey(mo
				.getMoid());
		return moDeviceInfo;
	}
}