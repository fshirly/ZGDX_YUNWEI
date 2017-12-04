package com.fable.insightview.platform.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.asycorganization.entity.SysAsycDepartMent;
import com.fable.insightview.platform.asycorganization.entity.SysAsycOrganization;
import com.fable.insightview.platform.asycorganization.mapper.SysAsycOrganizationMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.log.entity.SysLog;
import com.fable.insightview.platform.log.service.ISysLogService;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
import com.fable.insightview.platform.service.IOrganizationService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("organizationService")
public class OrganizationServiceImpl implements IOrganizationService,MessageListener {//update zheng zhen

	private final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	protected IOrganizationDao organizationDao;

	@Autowired 
	protected ISysEmploymentDao sysEmploymentDao;
	
	@Autowired
	private ISysLogService sysLogService;
	
	@Autowired
	private SysAsycOrganizationMapper sysAsycOrganizationMapper;
	@Override
	public int getTotalCount(OrganizationBean organizationBean) {
		return organizationDao.getTotalCount(organizationBean);
	}

	@Override
	public List<OrganizationBean> getOrganizationBeanByConditions(
			String paramName, String paramValue) {
		List<OrganizationBean> organizationBeanLst = organizationDao
				.getOrganizationBeanByConditions(paramName, paramValue);
		if (organizationBeanLst.get(0).getParentOrgID() != 0) {
			organizationBeanLst.get(0).setParentOrganizationName(
					this.organizationDao.getById(
							organizationBeanLst.get(0).getParentOrgID())
							.getOrganizationName());
		} else {
			organizationBeanLst.get(0).setParentOrganizationName("");
		}
		return organizationBeanLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOrganizationBean(OrganizationBean organizationBean) {
		return organizationDao.updateOrganizationBean(organizationBean);
	}

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addOrganization(OrganizationBean organizationBean) {
		return organizationDao.addOrganization(organizationBean);
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public String delOrganizationById(OrganizationBean organizationBean) {
		List<OrganizationBean> childsList = organizationDao
				.findByParentId(organizationBean.getOrganizationID());
		List<SysEmploymentBean> employmentList = organizationDao
				.getSysEmployByOrganizationID(organizationBean);
		List<SysEmploymentGradeBean> employmentGradeList = organizationDao
				.getSysEmployGradeByOrganizationID(organizationBean);
		List<SysUserGroupBean> userGroupList = organizationDao
				.getSysUserGroupByOrganizationID(organizationBean);
		List<ManagedDomain> managedDomainList = organizationDao
				.getSysManagedDomainByOrganizationID(organizationBean);
		List<DepartmentBean> deptList = organizationDao
				.getDepartmentByOrganizationID(organizationBean);
		// Modified By Zhang Ya at 2014年12月25日 下午12:31:17
		// Begin ....
		if (null != deptList && deptList.size() > 0) {
			return "被部门管理使用中";
		}
		if (null != childsList && childsList.size() > 0) {
			return "被其子单位使用中";
		}
		if (null != employmentList && employmentList.size() > 0) {
			return "被该单位员工使用中";
		}
		if (null != employmentGradeList && employmentGradeList.size() > 0) {
			return "被职务等级管理使用中";
		}
		if (null != userGroupList && userGroupList.size() > 0) {
			return "被用户组管理使用中";
		}
		if (null != managedDomainList && managedDomainList.size() > 0) {
			return "被管理域使用中";
		} else {
			organizationDao.delOrganizationById(organizationBean);
		}
		return "NotUsed";
		// ...... End
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<OrganizationBean> getOrganizationByConditions(
			OrganizationBean organizationBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		// TODO Auto-generated method stub
		List<OrganizationBean> orgLst = organizationDao
				.getOrganizationByConditions(organizationBean,
						flexiGridPageInfo);
		for (int i = 0; i < orgLst.size(); i++) {
			OrganizationBean org = orgLst.get(i);
			String parentName = getOrganizationBeanByConditions(
					"organizationID", org.getOrganizationID() + "").get(0)
					.getParentOrganizationName();
			org.setParentOrganizationName(parentName);
		}
		// List<Integer> ids = new ArrayList<Integer>();
		// for (OrganizationBean orgTemp : orgLst) {
		// ids.add(orgTemp.getParentOrgID());
		// }
		// if(orgLst.size()>0){
		// List<OrganizationBean> list =
		// organizationDao.getParentnameByIds(ids);
		// for(OrganizationBean orgTemp : orgLst){
		// for(OrganizationBean ot : list){
		// if(orgTemp.getParentOrgID() == ot.getOrganizationID()){
		// orgTemp.setParentOrganizationName(ot.getOrganizationName());
		// }
		// }
		// }
		// }
		return orgLst;
	}

	@Override
	public List<SysUserInfoBean> findUserByOrganizationId(int organizationId) {
		return null;
	}

	@Override
	public OrganizationBean findOrganizationById(int organizationId) {
		return organizationDao.getById(organizationId);
	}

	@Override
	public List<OrganizationBean> findOrganizationsByParentId(int parentId) {
		return organizationDao.findByParentId(parentId);
	}

	@Override
	public List<OrganizationBean> getOrganizationByConditions(String paramName,
			String paramValue) {
		return organizationDao.getOrganizationByConditions(paramName,
				paramValue);
	}

	@Override
	public List<OrganizationBean> getOrganizationTreeVal() {
		return organizationDao.getOrganizationTreeVal();
	}
	
	@Override
	public List<OrganizationBean> getOrganizationTree(String deptId) {
		return organizationDao.getOrganizationTree(deptId);
	}

	@Override
	public boolean checkOrganizationName(OrganizationBean organizationBean) {
		// TODO Auto-generated method stub
		return organizationDao.checkOrganizationName(organizationBean);
	}

	@Override
	public OrganizationBean getOrganizationBeanByOrgName(String organizationName) {
		// TODO Auto-generated method stub
		return organizationDao.getOrganizationBeanByOrgName(organizationName);
	}

	/*
	 * 删除前验证单位组织
	 * 
	 * @author hanl
	 */
	@Override
	public boolean checkBeforeDel(OrganizationBean organizationBean) {
		List<OrganizationBean> childsList = organizationDao
				.findByParentId(organizationBean.getOrganizationID());
		List<SysEmploymentBean> employmentList = organizationDao
				.getSysEmployByOrganizationID(organizationBean);
		List<SysEmploymentGradeBean> employmentGradeList = organizationDao
				.getSysEmployGradeByOrganizationID(organizationBean);
		List<SysUserGroupBean> userGroupList = organizationDao
				.getSysUserGroupByOrganizationID(organizationBean);
		List<ManagedDomain> managedDomainList = organizationDao
				.getSysManagedDomainByOrganizationID(organizationBean);
		List<DepartmentBean> deptList = organizationDao
				.getDepartmentByOrganizationID(organizationBean);
		boolean flag = (null == deptList || deptList.size() <= 0)
				&& (null == childsList || childsList.size() <= 0)
				&& (null == employmentList || employmentList.size() <= 0)
				&& (null == employmentGradeList || employmentGradeList.size() <= 0)
				&& (null == userGroupList || userGroupList.size() <= 0)
				&& (null == managedDomainList || managedDomainList.size() <= 0);
		return flag;
	}

	@Override
	public List<OrganizationBean> listOrganization() {
		return organizationDao.getOrganizationBeanByConditions("", "");
	}

	@Override
	public List<Integer> getUserAccountsByOrgId(Integer branchId) {
		return organizationDao.getUserIdsByOrgId(branchId);
	}

	@Override
	public List<OrganizationBean> listOrganizationByUserId(int currentUserId) {
		List<SysEmploymentBean> employmentBeanList = sysEmploymentDao.getEmploymentByUserId((int)currentUserId);
		List<OrganizationBean> list = new ArrayList<OrganizationBean>();
		if(employmentBeanList.size() > 0 || currentUserId<10000){
			list = organizationDao.getOrganizationBeanByConditions("", "");
		}else{
			list = organizationDao.listOrganizationByUserId((int)currentUserId);
		}
		return list;
	}

	@Override
	public boolean checkOrganizationCode(OrganizationBean organizationBean) {
		return organizationDao.checkOrganizationCode(organizationBean);
	}

	/************************* update zheng zhen start *************************/
	@Override
	public void onMessage(Message message) {
		logger.debug("=========组织机构开始同步..........");
		try {
			if (null != message && message instanceof TextMessage){
				TextMessage textMessage = (TextMessage)message;
				Map<String,Object> req = (Map<String,Object>)JsonUtil.jsonToBean(textMessage.getText(),Map.class);
				
				if (null == req || null == req.get("data")){
					insertSysLog(textMessage.getText(),"json格式转换错误");
					return ;
				}
				int op = (int)req.get("op");
				//op 1、新增  2、修改  3、删除
				switch(op){
					case 1 :
						insertOrgSyn(req,textMessage.getText());
						break;
					case 2 :
						updateOrgSyn(req,textMessage.getText());
						break;
					case 3 :
						deleteOrgSyn(req,textMessage.getText());
						break;
					case 4:
						insertOrUpdateOrganization(req,textMessage.getText());
					default:
						break;	
						
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private synchronized void insertOrUpdateOrganization(Map<String,Object> req,String message){
		Map<String,Object> orgMap = (Map<String,Object>)req.get("data");
		logger.debug("=========判断单位是修改还是插入:"+message);
		//获取父机构编码
		OrganizationBean orgBean = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		//表示机构存在
		if (null != orgBean){
		   logger.debug("=========判断单位是修改");
		   this.updateOrgSyn(req, message);
		}else{
			logger.debug("=========判断单位是插入");
			this.insertOrgSyn(req, message);	
		}
	}
	
	private synchronized void insertOrgSyn(Map<String,Object> req,String message){
		logger.debug("=========单位开始时插入");
		@SuppressWarnings("unchecked")
		Map<String,Object> orgMap = (Map<String,Object>)req.get("data");
		@SuppressWarnings("unchecked")
		Map<String,Object> parentMap = (Map<String,Object>)orgMap.get("parent");
		//获取父机构编码
		String parentOrgCode = (String)parentMap.get("code");
		Integer parentId = 0;
		
		//监测当前机构是否存在
		OrganizationBean orgBean = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		//表示机构存在
		if (null != orgBean){
			logger.debug("=========该机构已经存在");
			insertSysLog(message,"该机构已存在！");
			return ;
		}
		
		//不是根目录
		if (null != parentOrgCode && !"".equals(parentOrgCode)){
			 OrganizationBean parentOrg = organizationDao.getOrganizationByCode(parentOrgCode);//查询父机构信息
			 if (null == parentOrg){
				 insertSysLog(message,"上级机构【" + (String)parentMap.get("name") + "】不存在！");
				 return ;
			 }else{
				logger.debug("上级机构【" + (String)parentMap.get("name") + "】存在！"); 
			 }
			 parentId = parentOrg.getOrganizationID(); //获取所属单位	
		}
		
		
		OrganizationBean organizationBean = new OrganizationBean();
		organizationBean.setOrganizationName((String)orgMap.get("name"));//机构名称
		organizationBean.setOrganizationCode((String)orgMap.get("code"));//机构编码
		organizationBean.setParentOrgID(parentId);//父机构ID
		organizationBean.setDescr((String)orgMap.get("description"));//机构描述
		boolean bool = organizationDao.addOrganization(organizationBean);
		if (!bool){
			  logger.info("机构【" +(String)orgMap.get("name") +"】插入失败！");
			 insertSysLog(message,"插入失败！");
		}else{
			  logger.info("机构【" +(String)orgMap.get("name") +"】插入成功！");
			  insertSysLog(message,"插入成功！");
		}
	}
	
	private synchronized void updateOrgSyn(Map<String,Object> req,String message){
		logger.debug("=========开始时修改");
		@SuppressWarnings("unchecked")
		Map<String,Object> orgMap = (Map<String,Object>)req.get("data");//组织机构的信息
		@SuppressWarnings("unchecked")
		Map<String,Object> parentMap = (Map<String,Object>)orgMap.get("parent");//上级组织机构的信息
		
		//获取父机构编码
		String parentOrgCode = (String)parentMap.get("code");
		Integer parentId = 0;
		//不是根目录
		if (null != parentOrgCode && !"".equals(parentOrgCode)){
			 OrganizationBean parentOrg = organizationDao.getOrganizationByCode(parentOrgCode);//查询父机构信息
			 if (null == parentOrg){
				 insertSysLog(message,"上级机构【" + (String)parentMap.get("name") + "】不存在！");
				 return ;
			 }else{
				 logger.debug("上级机构【" + (String)parentMap.get("name") + "】存在！");
			 }
			 parentId = parentOrg.getOrganizationID(); 	
		}
		
		OrganizationBean updateOrgBean = organizationDao.getOrganizationByCode((String)orgMap.get("originalCode"));
		if (null == updateOrgBean ){
			logger.debug("没有找到修改的数据！");
			insertSysLog(message,"没有找到修改的数据！");
			return ;
		}
		updateOrgBean.setOrganizationName((String)orgMap.get("name"));//机构名称
		updateOrgBean.setOrganizationCode((String)orgMap.get("code"));//机构编码
		updateOrgBean.setParentOrgID(parentId);//父机构ID
		updateOrgBean.setDescr((String)orgMap.get("description"));//机构描述
		boolean bool = organizationDao.updateOrganizationBean(updateOrgBean);
		if (!bool){
			logger.debug("更新失败！");
			insertSysLog(message,"更新失败！");
		}else{
			logger.debug("更新成功！");
			insertSysLog(message,"更新成功！");
		}
	}
	
	
	private void deleteOrgSyn(Map<String,Object> req,String message){
		List<Map<String,Object>> orgList = (List<Map<String,Object>>)req.get("data");
		for (Map<String,Object> map : orgList){
			OrganizationBean organizationBean = organizationDao.getOrganizationByCode((String)map.get("code"));
			if (null == organizationBean){
				insertSysLog(message,"没有找到删除的数据！");
				continue;
			}
			boolean bool = organizationDao.delOrganizationById(organizationBean);
			if (!bool){
				insertSysLog(message,"删除失败！");
			}else{
				insertSysLog(message,"删除成功！");
			}
		}
	}
	
	private void insertSysLog(String context,String result){
		SysLog sysLog = new SysLog();
		sysLog.setModule("机构管理");
		sysLog.setActor("admin");
		sysLog.setContent(context);
		sysLog.setResult(result);
		sysLog.setOperation("数据同步");
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sysLog.setOptionTime(sdf.format(new Date())); //操作时间
		sysLogService.insertSysLog(sysLog);
	}

	/************************* update zheng zhen end *************************/
	@Override
	public List<OrganizationBean> getOrgByUserId(int userId) {
		return organizationDao.getOrgByUserId(userId);
	}

	@Override
	public List<SysAsycOrganization> findUserInsertSyncOrganizationList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<SysAsycOrganization> list=sysAsycOrganizationMapper.findUserInsertSyncOrganizationList(params);
		return list;
	}

	@Override
	public List<SysAsycDepartMent> findUserInsertSyncDeptList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<SysAsycDepartMent> list=sysAsycOrganizationMapper.findUserInsertSyncDeptList(params);
		return list;
	}

	@Override
	public SysAsycOrganization findUserAddSyncOrganizationList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return sysAsycOrganizationMapper.findUserAddSyncOrganizationList(params);
	}

	@Override
	public SysAsycDepartMent quaryDeptInfos(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysAsycOrganizationMapper.quaryDeptInfos(map);
	}

}
