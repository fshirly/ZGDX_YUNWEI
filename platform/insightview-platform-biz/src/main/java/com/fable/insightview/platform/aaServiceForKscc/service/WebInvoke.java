package com.fable.insightview.platform.aaServiceForKscc.service;

import com.fable.enclosure.bussiness.entity.PageRequest;
import com.fable.enclosure.bussiness.entity.PageResponse;
import com.fable.enclosure.bussiness.entity.ResultKit;
import com.fable.enclosure.bussiness.entity.ServiceResponse;
import com.fable.insightview.platform.aaServiceForKscc.entity.FbsInterface;
import com.fable.insightview.platform.aaServiceForKscc.mapper.KsccSchemeMapper;
import com.fable.insightview.platform.aaServiceForKscc.util.MedTApi;
import com.fable.insightview.platform.common.util.BigDecimalUtil;
import com.fable.insightview.platform.common.util.MultipleDataSource;
import com.fable.insightview.platform.common.util.SysConfig;
import com.fable.insightview.platform.common.util.XmlGenerator;
import com.sun.org.apache.regexp.internal.RE;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;


/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :Hairui
 * Date :2017/11/17
 * Time :13:53
 * </p>
 * <p>
 * Department :
 * </p>
 * <p> Copyright : 江苏飞博软件股份有限公司 </p>
 */
@Service
public class WebInvoke{

    @Autowired
    KsccSchemeMapper mapper;

    private static Map<String, Object> cache = new HashMap<>();

    private static Map<String, Object> cacheForDisk = new HashMap<>();

    public static Map<String, List<Map<String, Object>>> getHospitalCache() {
        return hospitalCache;
    }

    private static Map<String, List<Map<String, Object>>> hospitalCache = new HashMap<>();

    public PageResponse getAllMedtDiskStatus(PageRequest<Map<String,String>> param){
        String hospitalId = param.getParam().get("hospitalId");
        if(hospitalId!=null){
            if(cacheForDisk.get(hospitalId)!=null){
                Long lastTime = (Long) cacheForDisk.get(hospitalId);
                Long nowTime=new Date().getTime();
                long interval=(nowTime-lastTime)/1000/60;
                if(interval<60){
                    return PageResponse.wrap((List)cacheForDisk.get(hospitalId+"result"), param.getPageNo(), param.getPageSize());
                }
                List result = getList(param);
                cacheForDisk.put(hospitalId, new Date().getTime());
                cacheForDisk.put(hospitalId+"result", result);
                return PageResponse.wrap(result, param.getPageNo(), param.getPageSize());
            }
            List result = getList(param);
            cacheForDisk.put(hospitalId, new Date().getTime());
            cacheForDisk.put(hospitalId+"result", result);
            return PageResponse.wrap(result, param.getPageNo(), param.getPageSize());
        }
        else{
            if(cacheForDisk.get("time")!=null){
                Long lastTime = (Long) cacheForDisk.get("time");
                Long nowTime=new Date().getTime();
                long interval=(nowTime-lastTime)/1000/60;
                if(interval<60){
                    return PageResponse.wrap((List)cacheForDisk.get("result"), param.getPageNo(), param.getPageSize());
                }
                List result = getList(param);
                cacheForDisk.put("time", new Date().getTime());
                cacheForDisk.put("result", result);
                return PageResponse.wrap( result, param.getPageNo(), param.getPageSize());
            }
            List result = getList(param);
            cacheForDisk.put("time", new Date().getTime());
            cacheForDisk.put("result", result);
            return PageResponse.wrap(result, param.getPageNo(), param.getPageSize());
        }
    }

    @NotNull
    private List getList(PageRequest<Map<String,String>> param) {
        List<Map<String,String>> codeList = mapper.findAllLiveCodecForYw(param.getParam());
        List arryList = new ArrayList<>();
        for (Map<String,String> code : codeList){
            String authenticationId=MedTApi.getAuthenticationId(code);
            if(authenticationId!=null){
                code.put("authenticationid", authenticationId);
                XmlGenerator.generateRequestRoot(code);
                MedTApi.Login(code);
                Map<String,Object> codeMap = MedTApi.getDisks(code);
                Map<String,Object> mpuVidMap = MedTApi.getMpuVidEnc(code);
                if (!"0".equals(codeMap.get("success")) || !"0".equals(mpuVidMap.get("success"))){
                    String resoultion = mpuVidMap.get("Width").toString() + "*" + mpuVidMap.get("Height").toString();
                    codeMap.put("resoultion",resoultion);
                    String frameRate = mpuVidMap.get("FrameRate").toString();
                    codeMap.put("FrameRate",frameRate);
                    Integer remainStorm = Integer.parseInt(codeMap.get("Capacity").toString()) - Integer.parseInt(codeMap.get("Free").toString());
                    codeMap.put("remainStorm",remainStorm);
                    codeMap.put("newvideoNum",code.get("newvideoNum"));
                    codeMap.put("ip",code.get("ip"));
                    codeMap.put("hospitalName",code.get("hospitalName"));
                    codeMap.put("codecOwnership",code.get("codecOwnership"));
                    arryList.add(codeMap);
                }else {
                    codeMap.put("resoultion",null);
                    codeMap.put("FrameRate",null);
                    codeMap.put("remainStorm",null);
                    codeMap.put("Free", null);
                    codeMap.put("Capacity", null);
                    codeMap.put("newvideoNum",code.get("newvideoNum"));
                    codeMap.put("ip",code.get("ip"));
                    codeMap.put("hospitalName",code.get("hospitalName"));
                    codeMap.put("codecOwnership",code.get("codeOwnership"));
                    arryList.add(codeMap);
                }
            }
            else{
                code.put("resoultion",null);
                code.put("FrameRate",null);
                code.put("remainStorm",null);
                code.put("Free", null);
                code.put("Capacity", null);
                arryList.add(code);
            }

        }
        MultipleDataSource.setDataSourceKey("mySqlDataSource");
        return arryList;
    }

    public ServiceResponse getCloudUsage(){
        String file = mapper.getFileSize();
        List<Map<String, Object>> list = mapper.getHospital();
        hospitalCache.put("list", list);
        if(file==null){
            file = "0";
        }
        String size = SysConfig.getValueByKey("skyCloud.usageSpace");
        double usage = BigDecimalUtil.add(Double.valueOf(file), Double.valueOf(size));
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        // total 6t 60000000MB
        String result= numberFormat.format(BigDecimalUtil.div(usage, Double.valueOf("60000000"))*100)+"%";
        MultipleDataSource.setDataSourceKey("mySqlDataSource");
        return ResultKit.serviceResponse(result);
    }

    public ServiceResponse medtOkOrNot(){
        Map<String, Object> response;
        if (cache.get("time") != null) {
            Long lastTime = (Long) cache.get("time");
            Long nowTime=new Date().getTime();
            long interval=(nowTime-lastTime)/1000/60;
            if(interval<=10){
                return ResultKit.serviceResponse(cache.get("result"));
            }
            response = function();
            return ResultKit.serviceResponse(response);
        }
        response = function();
        cache.put("result", response);
        MultipleDataSource.setDataSourceKey("mySqlDataSource");
        return ResultKit.serviceResponse(response);
    }

    public Map<String, Object> function(){
        Map<String, String> param = new HashMap<>();
        List<Map<String, String>> codeList = mapper.findAllLiveCodecForYw(param);
        int total = codeList.size();
        int bad = 0;
        for (Map<String, String> codec:codeList){
            param.put("ip", codec.get("ip"));
            param.put("port", codec.get("port"));
            codec.put("status", "ok");
            String statusCode= MedTApi.getAuthenticationId(param);
            if(statusCode==null){
                bad++;
                codec.put("status","bad");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("abnormal", bad);
        map.put("normal", total-bad);
        map.put("codeList", codeList);
        cache.put("time", new Date().getTime());
        MultipleDataSource.setDataSourceKey("mySqlDataSource");
        return map;
    }

    public PageResponse<FbsInterface> getMedInterfaceInfo(PageRequest param){
        List<FbsInterface> list=mapper.selectFbsInterfaceList();
        MultipleDataSource.setDataSourceKey("mySqlDataSource");
        return PageResponse.wrap(list,param.getPageNo(),param.getPageSize());
    }

    public ServiceResponse appMonitor(HttpServletRequest request){
        List<Map<String, Object>> list = new ArrayList<>();
        String ip = SysConfig.getValueByKey("kscc.ip");
        String port = SysConfig.getValueByKey("kscc.port");
        String application = SysConfig.getValueByKey("kscc.application");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("address", "http://"+ip+":"+port+"/"+application);
        map1.put("name", "kscc运营系统");
        map1.put("status", "异常");
        int status=testWsdlConnection("http://"+ip+":"+port+"/"+application);
        if(status==200){
            map1.put("status", "正常");
        }
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        String address=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        map2.put("address",address);
        map2.put("name", "kscc运维系统");
        map2.put("status", "正常");
        list.add(map2);
        return ResultKit.serviceResponse(list);
    }

    private  int testWsdlConnection(String address){
        int status = 404;
        try {
            URL urlObj = new URL(address);
            HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
            oc.setUseCaches(false);
            oc.setConnectTimeout(10000); // 设置超时时间
            status = oc.getResponseCode();// 请求状态
            if (200 == status) {
                // 200是请求地址顺利连通。。
                return status;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
