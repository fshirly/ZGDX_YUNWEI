package com.fable.insightview.platform.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 随机生成文件命名策略
 * 
 * @author wul
 * @date 2014-03-18
 */
public class RenamePolicyCos implements FileRenamePolicy {

	public File rename(File uploadFile) {
		File renameFile = new File(uploadFile.getParent(), "test.jpg");
		return renameFile;
	}
}
