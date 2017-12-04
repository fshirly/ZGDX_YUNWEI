package com.fable.insightview.platform.aaServiceForKscc.util;

import com.fable.insightview.platform.common.util.HttpUtils;
import com.fable.insightview.platform.common.util.XmlGenerator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wanghairui on 2017/10/10.
 */
public class MedTApi {

    public static Logger logger = LoggerFactory.getLogger(MedTApi.class);

    /**
     *
     * @param param
     * @return 鉴权id
     * @description 鉴权
     */
    public static String getAuthenticationId(Map<String,String> param){
        String response= HttpUtils.httpPostByNullXml(getSocketAddress(param)+Constants.MedT100.authentication);
        logger.info(param.get("hospitalName")+"鉴权：：："+response);
        try {
            if(response!=null){
                Document doc = DocumentHelper.parseText(response);
                return doc.getRootElement().elementText("Authenticationid");
            }
           return null;
        } catch (DocumentException e) {
            logger.error(param.get("hospitalName")+"的编解码器鉴权发生错误",e);
            return null;
        }
    }

    /**
     *
     * @param param
     * @description 登陆
     */
    public static boolean Login(Map<String,String> param){

        String params = XmlGenerator.generateLoginParam(param);
        String xmlResponse = HttpUtils.httpPostByXml(getSocketAddress(param) + Constants.MedT100.login, params);
        try {
            Document doc = DocumentHelper.parseText(xmlResponse);
            String response=doc.getRootElement().elementText("statusstring");

            String statusString = doc.getRootElement().elementText("substatusstring");
            if (!"success".equals(response)) {
                logger.info(param.get("hospitalName")+"的编解码器登陆失败："+statusString);
                return false;
            }
            return true;
        } catch (DocumentException e) {
            logger.error(param.get("hospitalName")+"的编解码器登陆发生错误",e);
            return false;
        }
    }

    /**
     *
     * @param param
     * @return string
     * @description   运维部分，获取编解码参数
     */
    public static Map<String,Object> getMpuVidEnc(Map<String,String> param){
        Map<String,Object> map = new HashMap<>();
        try {
            String params = XmlGenerator.generateGetMpuVidEncParam(param);
            String xmlResponse = HttpUtils.httpPostByXml(getSocketAddress(param) + Constants.MedT100.getMpuVidEncParam, params);
            Element root = null;
            Document document = DocumentHelper.parseText(xmlResponse);
            root = document.getRootElement();
            String width = root.element("Resolution").elementText("Width");
            String height = root.element("Resolution").elementText("Height");
            map.put("success", "1");
            map.put("Width",width);
            map.put("Height",height);
            //帧率
            String frame = root.elementText("FrameRate");
            map.put("FrameRate",frame);
            System.out.println("FrameRate" +frame );
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String,Object>(){{put("success", "0");}};
        }
        return map;
    }
    /**
     *
     * @param param
     * @return
     * @description 运维部分，获取当前硬盘信息
     */
    public static Map<String,Object> getDisks(Map<String,String> param){
        Map<String,Object> map = new HashMap<>();
        try {
            String params = XmlGenerator.generateHaveNotParam(param);
            String xmlResponse = HttpUtils.httpPostByXml(getSocketAddress(param)+Constants.MedT100.getDisks,params);
            Element element = (Element)(DocumentHelper.parseText(xmlResponse).getRootElement().element("DiskList").elements().get(0));
            map.put("success", "1");
            map.put("Id",element.elementText("Id"));
            map.put("Capacity",element.elementText("Capacity"));
            map.put("Status",element.elementText("Status"));
            map.put("Attr",element.elementText("Attr"));
            map.put("Type",element.elementText("Type"));
            map.put("Free",element.elementText("Free"));
            map.put("GroupID",element.elementText("GroupID"));
            map.put("SmartState",element.elementText("SmartState"));
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String,Object>(){{put("success", "0");}};
        }
        return map;
    }

    /**
     *
     * @param param
     * @return string
     * @description   获取地址
     */
    private static String getSocketAddress(Map<String,String> param){
        return String.format("http://%s:%s/", param.get("ip"), param.get("port"));
    }
}
