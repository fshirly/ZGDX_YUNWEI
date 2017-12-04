package com.fable.insightview.platform.notices;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PfFileNameUtil {

	/*获取上传文件名*/
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		// 获取文件名称
		int bIndex = filePath.indexOf("//", 2);
		String fileName = filePath.substring(bIndex + 2, filePath.length());
		return fileName.substring(0, fileName.indexOf(".") - 24) + fileName.substring(fileName.indexOf("."));
	}
	
	/*获取上传文件远程路径*/
	public static String getRemoteFilePath (String filePath) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String path = request.getContextPath() + "/commonFileUpload/CosDownload?fileDir=";
		filePath = filePath.replaceAll("/", "\\\\");
		return path + filePath;
	}
	
}
