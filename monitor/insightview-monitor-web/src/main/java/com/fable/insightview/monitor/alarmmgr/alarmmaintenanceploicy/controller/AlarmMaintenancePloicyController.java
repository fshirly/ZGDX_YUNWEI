package com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmeventdefine.mapper.AlarmEventDefineMapper;
import com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.entity.AlarmMaintenancePloicyBean;
import com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.mapper.AlarmMaintenancePloicyMapper;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.maintenance.MaintenancePeriod;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/monitor/alarmMgr/mPloicy")
public class AlarmMaintenancePloicyController {

		@Autowired
		AlarmMaintenancePloicyMapper alarmMaintenancePloicyMapper;
		
		@Autowired
		AlarmEventDefineMapper alarmEventDefineMapper;
		
		@Autowired
		MODeviceMapper moDeviceMapper;
		
	    //注意format的格式要与日期String的格式相匹配  
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		
		private static final Logger log = LoggerFactory.getLogger(AlarmMaintenancePloicyController.class);
		
		/**
		 *@Description:跳转到页面，显示列表
		 *@return 
		 *@returnLevel ModelAndView
		 *@author zhurt
		 */
		@RequestMapping("/toMaintenancePloicyList")
		public  ModelAndView toAlarmLevelList(String navigationBar){
			log.info("开始...页面跳转");
			ModelAndView mv =new ModelAndView("monitor/alarmMgr/maintenanceploicy/maintenancePloicy_list");
			mv.addObject("navigationBar", navigationBar);
			return mv;
		}
		
		/**
		 *@Description:获得页面显示数据
		 *@param vo
		 *@return 
		 *@returnType Map<String,Object>
		 */
		@RequestMapping("/listMaintenancePloicy")
		@ResponseBody
		public Map<String,Object> listMaintenancePloicy(AlarmMaintenancePloicyBean vo) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
					.getRequestAttributes()).getRequest();
			FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
			.getFlexiGridPageInfo(request);
			
			Page<AlarmMaintenancePloicyBean> page = new Page<AlarmMaintenancePloicyBean>(); 
			page.setPageNo(flexiGridPageInfo.getPage());
			page.setPageSize(flexiGridPageInfo.getRp());
			
			Map<String,Object>  paramMap = new HashMap<String,Object>();
			System.out.println("maintainTitle:"+vo.getMaintainTitle()+" , moName:"+vo.getMoname());;
			paramMap.put("maintainTitle",vo.getMaintainTitle() );
			paramMap.put("moname", vo.getMoname());
			page.setParams(paramMap);
			
			List<AlarmMaintenancePloicyBean> levelList =  alarmMaintenancePloicyMapper.queryInfoList(page);
			
			int total = page.getTotalRecord();
			log.info("***数据记录数***："+total);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("total", total);
			result.put("rows", levelList);
			return result;
		}
		
		/**
		 *@Description:跳转到新增页面
		 *@param request
		 *@returnType ModelAndView
		 */
		@RequestMapping("/toMaintenancePloicyAdd")
		public  ModelAndView toMaintenancePloicyAdd(HttpServletRequest request,ModelMap map){
			log.info("跳转到新增页面alarmMaintenancePloicyAdd.jsp");
			return new ModelAndView("monitor/alarmMgr/maintenanceploicy/maintenancePloicyAdd");
		}
		
		/**
		 *@Description:新增维护期
		 *@param vo
		 *@param request
		 *@returnType int
		 */
		@RequestMapping("/addMaintenancePloicy")
		@ResponseBody
		public int addMaintenancePloicy(AlarmMaintenancePloicyBean vo,HttpServletRequest request) throws ParseException{
			log.info("开始。。。增加");
			String id = request.getParameter("sourceMOID");
			int sourceMOID = Integer.parseInt(id);
			
			//获取当前用户名
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession"); 
			String creatUser = sysUserInfoBeanTemp.getUserName();
			vo.setCreateUser(creatUser);//设置创建者
			vo.setModifyUser("");
			vo.setCreateTime(new Timestamp(System.currentTimeMillis()));	//设置创建时间
			vo.setModifyTime(new Timestamp(System.currentTimeMillis()));
			vo.setmFlag(0);
			//维护期是否启用判断
			int isUsed = Integer.parseInt(request.getParameter("isUsed"));
			log.info("是否启用："+request.getParameter("isUsed"));
			
			try {
				Date startTime = sdf.parse(request.getParameter("startTime"));
				Date endTime = sdf.parse(request.getParameter("endTime"));
				
				MODeviceObj  moinfo = moDeviceMapper.selectByPrimaryKey(sourceMOID);
				String deviceip = moinfo.getDeviceip();
				
			if("no IP".equals(deviceip)== false && isUsed == 1 ){
				log.info("调用维护期接口");
				MaintenancePeriod period = new MaintenancePeriod();
				period.setServerIP(deviceip);
				period.setBeginTime(startTime);
				period.setEndTime(endTime);
				Dispatcher.getInstance().getManageFacade().addMaintenancePeriod(period);
				log.info("维护期接口调用结束");
				
				alarmMaintenancePloicyMapper.insertSelective(vo);
				return 1;
			}else{
				alarmMaintenancePloicyMapper.insertSelective(vo);
				return 1;
				}
			} catch(Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		
		/**
		 *@Description:跳转到修改页面
		 *@param request
		 *@returnLevel ModelAndView
		 */
		@RequestMapping("/toMaintenancePloicyModify")
		public  ModelAndView toMaintenancePloicyModify(ModelMap map,AlarmMaintenancePloicyBean vo){
			log.info("跳转到修改页面alarmMaintenancePloicyModify.jsp");
			vo = alarmMaintenancePloicyMapper.queryByID(vo.getPloicyID());
			map.put("alarmVo", vo);
			return new ModelAndView("monitor/alarmMgr/maintenanceploicy/maintenancePloicyModify");
		} 
		
		@RequestMapping("/updateMaintenancePloicy")
		@ResponseBody
		public boolean updateMaintenancePloicy(AlarmMaintenancePloicyBean vo,HttpServletRequest request) throws Exception{
			//获取当前用户名
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession"); 
			String modifyUser = sysUserInfoBeanTemp.getUserName();
			vo.setModifyUser(modifyUser);//设置修改人
			vo.setModifyTime(new Timestamp(System.currentTimeMillis()));//设置修改时间
			//日志记录修改时间和修改人
			log.info("修改时间："+new Timestamp(System.currentTimeMillis())+"修改人："+modifyUser);
			vo.setmFlag(0);
			
			AlarmMaintenancePloicyBean ploicyinfo = alarmMaintenancePloicyMapper.queryByID(vo.getPloicyID());
			String deviceIP = ploicyinfo.getDeviceip();
			log.info("deviceIP:"+deviceIP);
			
			int isUsed = ploicyinfo.getIsUsed();//数据库中的isUsed的值
			int isUsedFore = Integer.parseInt(request.getParameter("isUsed"));//前台输入的isUsed值
			Date startTime = sdf.parse(request.getParameter("startTime"));
			Date endTime = sdf.parse(request.getParameter("endTime"));
			
			if(isUsed == 0 && isUsedFore == 0){
				alarmMaintenancePloicyMapper.updateByPrimaryKey(vo);
				return true;
			}else if(isUsed == 0 && isUsedFore == 1 && "no IP".equals(deviceIP)==false ){
				log.info("维护期接口调用开始。。。");
				MaintenancePeriod period = new MaintenancePeriod();
				period.setServerIP(deviceIP);
				period.setBeginTime(startTime);
				period.setEndTime(endTime);
				Dispatcher.getInstance().getManageFacade().addMaintenancePeriod(period);
				log.info("维护期接口调用结束");
				alarmMaintenancePloicyMapper.updateByPrimaryKey(vo);
				return true;
			}else if(isUsed == 1 && isUsedFore == 1 && "no IP".equals(deviceIP)==false ){
				log.info("维护期接口调用开始。。。");
				MaintenancePeriod period = new MaintenancePeriod();
				period.setServerIP(deviceIP);
				period.setBeginTime(startTime);
				period.setEndTime(endTime);
				Dispatcher.getInstance().getManageFacade().updateMaintenancePeriod(period);
				log.info("维护期接口调用结束");
				alarmMaintenancePloicyMapper.updateByPrimaryKey(vo);
				return true;
			}else if(isUsed == 1 && isUsedFore == 0 && "no IP".equals(deviceIP)==false){
				log.info("维护期接口调用开始。。。");
				MaintenancePeriod period = new MaintenancePeriod();
				period.setServerIP(deviceIP);
				period.setBeginTime(ploicyinfo.getStartTime());
				period.setEndTime(ploicyinfo.getEndTime());
				Dispatcher.getInstance().getManageFacade().removeMaintenancePeriod(deviceIP);
				log.info("维护期接口调用结束");
				alarmMaintenancePloicyMapper.updateByPrimaryKey(vo);
				return true;
			}else{
				alarmMaintenancePloicyMapper.updateByPrimaryKey(vo);
				return true;
			}
}
		
		/**
		 *@Description:删除维护期策略
		 *@param vo
		 *@return
		 *@returnLevel boolean
		 */
		@RequestMapping("/delMaintenancePloicy")
		@ResponseBody
		public boolean delAlarmLevel(AlarmMaintenancePloicyBean vo)throws Exception{
			log.info("********删除ID："+vo.getPloicyID());
			AlarmMaintenancePloicyBean ploicyinfo = alarmMaintenancePloicyMapper.queryByID(vo.getPloicyID());
			String deviceIP = ploicyinfo.getDeviceip();
			int isUsed = ploicyinfo.getIsUsed();
			
			if("no IP".equals(deviceIP)==false && isUsed == 1 ){
				log.info("调用维护期接口");
				MaintenancePeriod period = new MaintenancePeriod();
				period.setServerIP(deviceIP);
				period.setBeginTime(ploicyinfo.getStartTime());
				period.setEndTime(ploicyinfo.getEndTime());
				Dispatcher.getInstance().getManageFacade().removeMaintenancePeriod(deviceIP);
				log.info("维护期接口调用结束");
				
				//删除维护期策略	
				alarmMaintenancePloicyMapper.deleteByPrimaryKey(vo.getPloicyID());
				return true;
			}else{
				alarmMaintenancePloicyMapper.deleteByPrimaryKey(vo.getPloicyID());
				return true;
			}
		}
		
		/**
		 *@Description:批量删除
		 *@param request
		 *@returnType Map<String,Object>
		 */
		@RequestMapping("/doBatchDel")
		@ResponseBody
		public boolean doBatchDel(HttpServletRequest request){
			String ploicyIDs =request.getParameter("ploicyIDs");
			//log.info("选中要批量删除的ID:"+ploicyIDs);
			//获取单个ID
			String[] splitIds = ploicyIDs.split(",");
			//能够被删的ID数组
			//List<Integer> delDefine = new ArrayList<Integer>();
			//不能被删保留的ID数组 
			//List<Integer> reserveDefine = new ArrayList<Integer>();
			try {
				for(String strID : splitIds){
					//遍历获取选项的ID
					int ploicyID = Integer.parseInt(strID);
					AlarmMaintenancePloicyBean ploicyinfo = alarmMaintenancePloicyMapper.queryByID(ploicyID);
					String deviceIP = ploicyinfo.getDeviceip();
					int isUsed = ploicyinfo.getIsUsed();
					if("no IP".equals(deviceIP)==false && isUsed == 1 ){
						log.info("调用维护期接口");
						MaintenancePeriod period = new MaintenancePeriod();
						period.setServerIP(deviceIP);
						period.setBeginTime(ploicyinfo.getStartTime());
						period.setEndTime(ploicyinfo.getEndTime());
						Dispatcher.getInstance().getManageFacade().removeMaintenancePeriod(deviceIP);
						log.info("维护期接口调用结束");
						
						alarmMaintenancePloicyMapper.deleteByPrimaryKey(ploicyID);//删除
					}else{
						alarmMaintenancePloicyMapper.deleteByPrimaryKey(ploicyID);
					}
				}
			} catch (Exception e) {
					log.error("删除异常："+e.getMessage());
					return false;
				}
				return true;
	}
		/**
		 *@Description:检查维护期策略名称是否存在(增加前)
		 *@param vo
		 *@return 
		 */
		@RequestMapping("/checkNameUnique")
		@ResponseBody
		public boolean checkNameUnique(AlarmMaintenancePloicyBean vo){
			log.info("检查策略名是否存在！");
			log.info("要检查的名称maintainTitle:"+vo.getMaintainTitle());
			int i= alarmMaintenancePloicyMapper.checkName(vo.getMaintainTitle());
			log.info("*****存在数量:i="+i);
			return ( i==0 )?true:false;
		}
		
		/**
		 *@Description:检查维护期策略名称是否存在(修改)
		 *@param vo
		 *@return 
		 */
		@RequestMapping("/checkNameUnique2")
		@ResponseBody
		public boolean checkNameUnique2(AlarmMaintenancePloicyBean vo){
			log.info("检查策略名是否存在！");
			log.info("要检查的名称maintainTitle:"+vo.getMaintainTitle());
			int i= alarmMaintenancePloicyMapper.checkName2(vo);
			log.info("*****存在数量:i="+i);
			return ( i==0  )?true:false;
		}
		
		/**
		 *@Description:检查事件源对象是否唯一
		 *@param vo
		 *@return 
		 */
		@RequestMapping("/checkMONameUnique")
		@ResponseBody
		public boolean checkMONameUnique(AlarmMaintenancePloicyBean vo,HttpServletRequest request){
			log.info("检查事件源对象是否存在！");
			String id = request.getParameter("sourceMOID");
			int sourceMOID = Integer.parseInt(id);
			log.info("SourceMOID："+vo.getSourceMOID());
			int i= alarmMaintenancePloicyMapper.checkMOName(sourceMOID);
			log.info("*****:"+i);
			return ( i==0  )?true:false;
		}
		
		/**
		 * 维护期策略详情页面跳转
		 */
		@RequestMapping("/toMaintenancePloicyDetail")
		public ModelAndView toAlarmLevelDetail(ModelMap map,AlarmMaintenancePloicyBean vo)throws Exception {		
			log.info("维护期id:"+vo.getPloicyID());
			vo = alarmMaintenancePloicyMapper.queryByID(vo.getPloicyID());
			map.put("alarmVo", vo);
			return new ModelAndView("monitor/alarmMgr/maintenanceploicy/maintenancePloicyDetail");
		}

		/**
		 * 打开事件源页面
		 * @return
		 */
		@RequestMapping("/toSelectMoRescource")
		@ResponseBody
		public ModelAndView toSelectMoRescource(HttpServletRequest request,	HttpServletResponse response){
			log.info("打开事件源页面");
			String flag=request.getParameter("flag");
			String dif = request.getParameter("dif");
			if(flag !=null && !"".equals(flag)){
				request.setAttribute("flag", flag);
			}else{
				request.setAttribute("flag", "doInit");
			}
			request.setAttribute("dif", dif);
			ModelAndView mv = new ModelAndView();
			log.info("跳转到moDevice");
			mv.setViewName("monitor/alarmMgr/maintenanceploicy/moDevice_list");
			return mv;
		}
}		