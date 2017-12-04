package com.fable.insightview.platform.formdesign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.formdesign.dao.IBusinessFormsDao;
import com.fable.insightview.platform.formdesign.dao.IFormDao;
import com.fable.insightview.platform.formdesign.entity.FdBusinessForms;
import com.fable.insightview.platform.formdesign.entity.FdForm;
import com.fable.insightview.platform.formdesign.service.IBusinessFormsService;
import com.fable.insightview.platform.formdesign.vo.ProcessFormLstVO;
import com.fable.insightview.platform.formdesign.vo.ProcessFormVO;
import com.fable.insightview.platform.itsm.core.service.GenericServiceImpl;

@Service("fromdesign.businessFormsService")
public class BusinessFormsServiceImpl extends
		GenericServiceImpl<FdBusinessForms> implements IBusinessFormsService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private IBusinessFormsDao businessFormsDao;

	@Autowired
	private IFormDao formDao;

	@Override
	public List<ProcessFormLstVO> findProcessFormInfoLst(String businessNodeId) {
		List<ProcessFormLstVO> voLst = new ArrayList<ProcessFormLstVO>();
		List<FdBusinessForms> businessForms = businessFormsDao
				.getListByBusinessNodeId(businessNodeId);
		for (FdBusinessForms bForm : businessForms) {
			ProcessFormLstVO vo = new ProcessFormLstVO();
			FdForm form = formDao.getById(bForm.getFormId());
			if (null != form) {
				vo.setId(bForm.getFormId());
				vo.setFormName(form.getFormName());
				vo.setLayout(form.getFormLayout());

				voLst.add(vo);
			}
		}

		return voLst;
	}

	@Override
	public void insertBForm(FdForm form, ProcessFormVO processFormVO) {
		FdBusinessForms businessForms = new FdBusinessForms();
		businessForms.setBusinessNodeId(processFormVO.getBusinessNodeId());
		businessForms.setBusinessType(processFormVO.getBusinessType());
		businessForms.setFormId(form.getId());
		try {
			businessFormsDao.insert(businessForms);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
