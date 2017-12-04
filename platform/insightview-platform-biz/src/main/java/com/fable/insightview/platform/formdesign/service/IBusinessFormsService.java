package com.fable.insightview.platform.formdesign.service;

import java.util.List;

import com.fable.insightview.platform.formdesign.entity.FdBusinessForms;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.vo.ProcessFormLstVO;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericService;

public interface IBusinessFormsService extends GenericService<FdBusinessForms> {

	List<ProcessFormLstVO> findProcessFormInfoLst(String businessNodeId);

	void insertBForm(FdForm form, ProcessFormVO processFormVO);
}
