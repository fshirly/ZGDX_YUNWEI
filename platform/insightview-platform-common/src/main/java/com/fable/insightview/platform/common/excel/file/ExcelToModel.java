package com.fable.insightview.platform.common.excel.file;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.exception.NotInDataBaseException;
import com.fable.insightview.platform.common.exception.NonConformanceException;

public interface ExcelToModel {
    public boolean validateExcelFormat();

    public List getModelList() throws NonConformanceException, NotInDataBaseException;

    public List getSuccessModelList() throws NonConformanceException, NotInDataBaseException;

    public List getErrorModelList() throws NonConformanceException, NotInDataBaseException;

    public Map getConfigTitle();

    public void setUseColumn(boolean isUseColumn);

    public int getStartTitleRow();

    public void setSheet(int intSheet);
    
    public void setSheet(String sheetName);
    
    public int getIntSheet();
}
