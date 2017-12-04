package com.fable.insightview.monitor.aaFromKscc;

import com.fable.enclosure.bussiness.entity.PageRequest;
import com.fable.enclosure.bussiness.entity.PageResponse;
import com.fable.enclosure.bussiness.entity.ServiceResponse;
import com.fable.insightview.platform.aaServiceForKscc.entity.FbsInterface;
import com.fable.insightview.platform.aaServiceForKscc.service.WebInvoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :Hairui
 * Date :2017/11/21
 * Time :10:03
 * </p>
 * <p>
 * Department :
 * </p>
 * <p> Copyright : 江苏飞博软件股份有限公司 </p>
 */
@Controller
@RequestMapping("/store")
public class ControllerForKscc {

    @Autowired
    WebInvoke webInvoke;

    @RequestMapping("/diskStatus")
    @ResponseBody
    public PageResponse<List<Map<String,String>>> getAllMedtDiskStatus(@RequestBody PageRequest param){
        return webInvoke.getAllMedtDiskStatus(param);
    }

    @RequestMapping("/cloudUsage")
    @ResponseBody
    public ServiceResponse getAllMedtDiskStatus(){
        return webInvoke.getCloudUsage();
    }

    @RequestMapping("/medtOkOrNot")
    @ResponseBody
    public ServiceResponse medtOkOrNot(){
        return webInvoke.medtOkOrNot();
    }

    @RequestMapping("/getMedInterfaceInfo")
    @ResponseBody
    public PageResponse<FbsInterface> getMedInterfaceInfo(@RequestBody PageRequest param){
        return webInvoke.getMedInterfaceInfo(param);
    }

    @RequestMapping("/appMonitor")
    @ResponseBody
    public ServiceResponse appMonitor(HttpServletRequest request){
        return webInvoke.appMonitor(request);
    }

}
