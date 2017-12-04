package com.fable.insightview.platform.common.util;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		String path = "";
		// 判断提交过来的表单是否为文件上传菜单
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 构造一个文件上传处理对象
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");

			Iterator items;
			try {
				// 解析表单中提交的所有文件内容
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// 取出上传文件的文件名称
						String name = item.getName();
						path = request.getRealPath("uploadFiles")
								+ File.separatorChar + fileName;

						String suffix = "";
						int splitIndex = name.lastIndexOf(".");
						if (splitIndex > 0) {
							suffix = name.substring(name.lastIndexOf(".") + 1);
						}
						// 上传文件
						File uploaderFile = new File(path);
						item.write(uploaderFile);
						return fileName;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
}
