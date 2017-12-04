package com.fable.insightview.platform.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wanghairui on 2017/10/10.
 */
public class XmlGenerator {

    private static Map<String, Element> rootRequestMap = new HashMap<>();

    /**
     *
     * @param param
     * @return XML形式参数
     * @description 登陆
     */
    public static String generateLoginParam(Map<String,String> param) {

        Element root = rootRequestMap.get(enhanceKey(param));
        removeRedundantElement(root);
        Element loginReq = root.addElement("LoginReq");
        loginReq.setText("");
        return root.asXML();
    }

    /**
     *
     * @param param
     * @return XML形式参数
     * @description 心跳
     */
    public static String generateKeepHeartBeatParam(Map<String,String> param) {
        Element root = rootRequestMap.get(enhanceKey(param));
        removeRedundantElement(root);
        Element StateReq = root.addElement("StateReq");
        Element CfgMod = StateReq.addElement("CfgMod");
        Element SysStatus = StateReq.addElement("SysStatus");
        Element DiskChg = StateReq.addElement("DiskChg");
        CfgMod.setText("TRUE");
        SysStatus.setText("TRUE");
        DiskChg.setText("TRUE");
        return root.asXML();
    }

    /**
     *
     * @param param
     * @return XML形式参数
     * @description 无参形式
     */
    public static String generateHaveNotParam(Map<String,String> param) {
        Element root = rootRequestMap.get(enhanceKey(param));
        removeRedundantElement(root);
        return root.asXML();
    }

    public static String generateGetMpuVidEncParam(Map<String,String> param){
        Element root = rootRequestMap.get(enhanceKey(param));
        removeRedundantElement(root);
        Element GetMpuVidEncParamReq = root.addElement("GetMpuVidEncParamReq");
        Element MpuID = GetMpuVidEncParamReq.addElement("MpuID");
        MpuID.setText("2");
        return root.asXML();
    }

    /**
     *
     * @param param
     * @return XML形式参数
     * @description 生成请求根
     */
    public static void generateRequestRoot(Map<String,String> param) {
        Document doc = DocumentHelper.createDocument();
        Element contentroot = doc.addElement("contentroot");
        //增加子元素
        Element authenticationinfoNode = contentroot.addElement("authenticationinfo");
        authenticationinfoNode.addAttribute("type", "7.0");

        Element usernameNode = authenticationinfoNode.addElement("username");
        Element passwordNode = authenticationinfoNode.addElement("password");
        Element authenticationidNode = authenticationinfoNode.addElement("authenticationid");
        usernameNode.setText(param.get("username"));
        passwordNode.setText(encode(param));
        authenticationidNode.setText(param.get("authenticationid"));
        rootRequestMap.put(param.get("hospitalId")+param.get("port")+param.get("ip"), contentroot);
    }

    /**
     *
     * @param root
     * @return XML形式参数
     * @description 优化根参数
     */
    private static void removeRedundantElement(Element root) {
        List<Element> list = root.elements();
        if (list.size() == 2) {
            root.remove(list.get(1));
        }
    }

    /**
     *
     * @param param
     * @return XML形式参数
     * @description 生成key
     */
    private static String enhanceKey(Map<String,String> param){
        return param.get("hospitalId")+param.get("port") + param.get("ip");
    }

    /**
     *
     * @param login
     * @return XML形式参数
     * @description 加密
     */
    private static String encode(Map<String,String> login) {
        String hexMd5String = "";
        try {
            hexMd5String = DigestUtils.md5Hex((login.get("username") + "," + login.get("password") + "," + login.get("authenticationid")).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(hexMd5String.getBytes());
    }
}
