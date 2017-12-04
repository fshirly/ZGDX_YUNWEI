package com.fable.insightview.monitor.discover.controller;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :Hairui
 * Date :2017/11/14
 * Time :13:18
 * </p>
 * <p>
 * Department :
 * </p>
 * <p> Copyright : 江苏飞博软件股份有限公司 </p>
 */
@Controller
@RequestMapping("monitorCenter")
public class MonitorCenter {
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/alarmList")
    public ModelAndView toAlarmList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_alarm/alarmList");
        SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
                .getSession().getAttribute("sysUserInfoBeanOfSession");
        String viewCfgID = request.getParameter("viewCfgID");
        mv.addObject("viewCfgID", viewCfgID);
        mv.addObject("userID", sysUserInfoBeanTemp.getId().intValue());
        mv.addObject("username", sysUserInfoBeanTemp.getUserName());
        return mv ;
    }
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/hospital")
    public ModelAndView toHospital(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_hospital/hospital");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/hostMonitor")
    public ModelAndView toHostMonitor(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_platform/hostMonitor");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/appMonitor")
    public ModelAndView toAppMonitor(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_platform/appMonitor");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/storeMonitor")
    public ModelAndView toStoreMonitor(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_platform/storeMonitor");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }
    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/interfaceMonitor")
    public ModelAndView toInterfaceMonitor(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_platform/interfaceMonitor");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }

    /**
     * 设备列表页面跳转
     */
    @RequestMapping("/historyAlarm")
    public ModelAndView toHistoryAlarm(String navigationBar) throws Exception {
        ModelAndView mv = new ModelAndView("monitor/our_alarm/alarmHistory_list");
        mv.addObject("navigationBar", navigationBar);
        return mv ;
    }

}
