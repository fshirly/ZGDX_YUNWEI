package com.fable.insightview.monitor.harvester.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.ServerState;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.encrypt.SymmetricCoder;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.harvester.service.IHarvesterService;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.util.SSHUtil;
import com.fable.insightview.monitor.util.TelnetUtil;
import com.fable.insightview.platform.snmpcommunity.dao.ISysAuthCommunityDao;
import com.fable.insightview.platform.snmpcommunity.entity.SysAuthCommunityBean;

@Service
public class HarvesterServiceImpl implements IHarvesterService {
	private static final Logger logger = LoggerFactory
			.getLogger(HarvesterServiceImpl.class);

	@Autowired
	ISysAuthCommunityDao authCommunityDao;
	@Autowired
	HarvesterMapper harvesterMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;

	/**
	 * 强制重启采集程序
	 */
	@Override
	public Map<String, Object> resartProgram(
			SysServerInstallService sysServerInstallService) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// -1：执行指令失败 0:没有凭证 ，1：成功，2：Telnet登录失败，3：SSH登录失败
		int result = -1;
		String fileName = "";
		
		//默认返回成功
		resultMap.put("result","1");
		
		String processName = sysServerInstallService.getProcessName();
		String ip = sysServerInstallService.getIpaddress();
		String installdir = sysServerInstallService.getInstalldir();
		Date oldLastchangeDate = sysServerInstallService.getLastChangeDate();
		if ("CollectorMaster".equals(processName)) {
			fileName = "collectorMaster.sh";
		} else if ("Discovery".equals(processName)) {
			fileName = "discovery.sh";
		} else if ("PerfPoll".equals(processName)) {
			fileName = "perfPoll.sh";
		} else if ("TaskDispatcher".equals(processName)) {
			fileName = "taskDispatcher.sh";
		} else if ("TrapServer".equals(processName)) {
			fileName = "trap.sh";
		}else if ("SyslogServer".equals(processName)) {
			fileName = "syslog.sh";
		}else if ("DataTransfer".equals(processName)) {
			fileName = "datatransfer.sh";
		}else if ("OfflineMaster".equals(processName)) {
			fileName = "offlineMaster.sh";
		}
		
		// String stopDir = installdir + "bin/" + fileName + " stop";
		// String startDir = installdir + "bin/" + fileName + " start";
		String dirDir = "cd " + installdir + "bin/ ";
		String stopDir = " ./" + fileName + " stop";
		String startDir = " ./" + fileName + " start";

		// 获得凭证信息
		SysAuthCommunityBean community = new SysAuthCommunityBean();
		community.setDeviceIP(ip);
		List<SysAuthCommunityBean> authCommunityList = authCommunityDao
				.getSysAuthCommunityByConditions(community);
		SysAuthCommunityBean authCommunityBean = null;
		if (authCommunityList.size() > 0) {
			authCommunityBean = authCommunityList.get(0);
		}

		if (authCommunityList.size() <= 0) {
			String[] arr = ip.split("\\.");
			String newIP = "";
			for (int i = 0; i < arr.length - 1; i++) {
				newIP = newIP + arr[i] + ".";
			}
			newIP = newIP + "*";
			community.setDeviceIP(newIP);
			if (authCommunityDao.getSysAuthCommunityByConditions(community)
					.size() > 0) {
				authCommunityBean = authCommunityDao
						.getSysAuthCommunityByConditions(community).get(0);
			}
		}
		SymmetricCoder coder =ServerState.getSymmetricBuilder().newInstance();
		if (authCommunityBean != null) {
			int port = authCommunityBean.getPort();
			String user = authCommunityBean.getUserName();
			String password = authCommunityBean.getPassword();
			try{
				password=coder.decrypt(password);
			}catch(Exception e){
				logger.debug("解密失败"+"||*****||"+password);
				e.printStackTrace();
			}
			int authType = authCommunityBean.getAuthType(); // authType:1
			// Telnet,2 SSH

			// 以Telnet方式登录
			if (authType == 1) {
				logger.info("启动Telnet。。。。");
				TelnetUtil telnet = null;
				try {
					telnet = new TelnetUtil(ip, port, user, password);
				} catch (Exception e) {
					// 登录失败
					logger.error("telnet 登录异常：" + e);
					result = 2;
					resultMap.put("result", result);
					return resultMap;
				}
				if (telnet != null) {
					// 执行停止程序命令
					String dirStr = telnet.sendCommand(dirDir);
					String stopStr = telnet.sendCommand(stopDir);
					// System.out.println(stopStr);

					// System.out.println("停止时间====" + df.format(new Date()));

					// 延时5秒
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// System.out.println("延时后的时间====" + df.format(new Date()));

					// 执行启动程序命令
					String startStr = telnet.sendCommand(startDir);
					// System.out.println(startStr);
					if (dirStr == null || stopStr == null || startStr == null) {
						result = -1;
						resultMap.put("result", result);
						return resultMap;
					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// 关闭Telnet连接
					telnet.disconnect();
				}
			}

			// 以SSH方式登录
			else if (authType == 2) {
				SSHUtil sshExecutor = null;
				try {
					sshExecutor = new SSHUtil(ip, user, password);
				} catch (Exception e) {
					logger.error("ssh 登录异常：" + e);
					// 登录失败
					result = 3;
					resultMap.put("result", result);
					return resultMap;
				}
				if (sshExecutor != null)
				{
					try
					{
						//修改代码一次执行所有脚本
						sshExecutor.executeShell(dirDir + "\n" + stopDir + " \n sleep 3 \n " + startDir+" \n");
						
						//休眠15秒是等待executeShell执行结果返回
						Thread.currentThread().sleep(15000);
					} catch (Exception e)
					{
						logger.error("执行停止命令异常：" + e);
						result = -1;
						resultMap.put("result", result);
					} finally
					{
						// 关闭SSH的session连接
						sshExecutor.close();
					}

				}
			}
            if(!"1".equals(resultMap.get("result"))){
            	return resultMap;
            }
			SysServerInstallService bean = harvesterMapper
					.getByID(sysServerInstallService.getId());
			String lastchangetime = bean.getLastchangetime();
			int servicestatus = getStatus(bean.getIpaddress(), bean.getProcessName());

			// 判断时间是否更新
			boolean timUpdate = false;
			try {
				Date nowDate = df.parse(lastchangetime);
				// System.out.println("old时间：" + oldLastchangeDate
				// + "  , nowDate :" + nowDate);
				if (oldLastchangeDate.getTime() < nowDate.getTime()) {
					// System.out.println("oldDate 在nowDate前");
					timUpdate = true;
				} else if (oldLastchangeDate.getTime() >= nowDate.getTime()) {
					// System.out.println("oldDate在dnowDatet后");
					timUpdate = false;
				}
			} catch (ParseException e) {
				logger.error("时间转换异常：" + e);
			}

			// 时间更新，状态正常则重启成功
			if (timUpdate == true && servicestatus == 1) {
				result = 1;
			}
			resultMap.put("lastchangetime", lastchangetime);
			resultMap.put("servicestatus", servicestatus);

		} else {
			// 没有凭证信息
			result = 0;
			resultMap.put("result", result);
			return resultMap;
		}
		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 获得采集机状态
	 * @return 1:正常  ；2：挂起
	 */
	@Override
	public int getStatus(String ip, String processName) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		
		//获得所有的服务
		List<Server> allServers = facade.listAllActiveServers();
		
		for (Server server : allServers) {
			if(ip.equals(server.getIp()) && processName.equals(server.getProcessName())){
					return 1;
			}else{
				continue;
			}
		}
		return 2;
	}

	/**
	 * 删除
	 */
	@Override
	public Map<String, Object> delServerInstallService(SysServerInstallService bean) {
		//删除结果
		boolean delResultFlag = false;
		boolean delHostFlag = true;
		boolean isDelHost = false;
		//采集机是否正在使用，是否能删除
		boolean isUsed = true;
		if("PerfPoll".equals(bean.getProcessName())){
			int count = perfTaskInfoMapper.getByCollector(bean.getServerid());
			if(count <= 0){
				isUsed =true;
			}else{
				isUsed = false;
			}
		}
		if(isUsed == true){
			isUsed = true;
			boolean deServicelFlag = harvesterMapper.delServerInstallService(bean.getId());
			List<SysServerInstallService> serviceLst = harvesterMapper.getByServerID(bean.getServerid());
			//如果该运维主机中没有服务了，则删除对应的运维主机
			if(serviceLst.size() <=0 ){
				isDelHost = true;
//			delHostFlag = harvesterMapper.delServerHost(bean.getServerid());
			}
			if(deServicelFlag == true && delHostFlag == true){
				delResultFlag = true;
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("delResultFlag", delResultFlag);
		result.put("isDelHost", isDelHost);
		result.put("isUsed", isUsed);
		return result;
	}

	/**
	 * 1:正常  ；2：挂起
	 */
	@Override
	public List<SysServerInstallService> getharvestLsinfo(List<SysServerInstallService> list) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();
		//获得所有的服务
		List<Server> allServers = facade.listAllActiveServers();
		SysServerInstallService  SysServer=null;
		List<SysServerInstallService>  sysServerList = new ArrayList<SysServerInstallService>();
		for (int i = 0; i < list.size(); i++) {
			SysServer = list.get(i);
			String ip = SysServer.getIpaddress();
			String processName = SysServer.getProcessName();
		for (Server server : allServers) {
				 if(ip.equals(server.getIp())
						 && processName.equals(server.getProcessName())){
					 SysServer.setServicestatus(1);
					 sysServerList.add(SysServer);
					 break;
				 }
			}
			if (SysServer.getServicestatus()!=1) {
				 SysServer.setServicestatus(2);
				 sysServerList.add(SysServer);
			}
		}
		return sysServerList;
	}
}
