package com.fable.insightview.platform.common.excel.file;

import java.io.File;

/*��
 * ����˾��
 * �ļ���ModelToExcelMessage.java
 * �����ߣ�YJM
 * �汾�ţ�1.0
 * ʱ���䣺2007-11-3����10:35:42
 */

public interface ModelToExcelMessage {
  public File getErrorFile();
  public File getSuccessFile();
  public File getFile();
  public void setStartTitleRow(int startTitleRow);
  public void setIntSheet(int intSheet);
}
