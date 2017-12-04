package com.fable.insightview.platform.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetRDeptMapper;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.log.entity.SysLog;
import com.fable.insightview.platform.log.service.ISysLogService;
import com.fable.insightview.platform.service.IDepartmentService;

/**
 * 部门组织Service
 * 
 * @author 武林
 * 
 */
@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService,MessageListener {//update zheng zhen

	private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	protected IDepartmentDao departmentDao;

	@Autowired
	protected ISysUserDao sysUserDao;

	@Autowired
	protected IOrganizationDao organizationDao;
	
	@Autowired
	protected IPManSubNetRDeptMapper ipManSubNetRDeptMapper;
	
	@Autowired
	private SysAsycOrganizationMapper sysAsycOrganizationMapper;
	
	
	@Autowired
	private ISysLogService sysLogService;

	@Override
	public int getTotalCount(DepartmentBean departmentBean) {
		return departmentDao.getTotalCount(departmentBean);
	}

	/*
	 * 根据条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentBeanByConditions(String paramName,
			String paramValue) {
		List<DepartmentBean> deptLst = departmentDao
				.getDepartmentBeanByConditions(paramName, paramValue);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			DepartmentBean dept = deptLst.get(i);
			String parentDeptName = "";
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());

			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		for (int i = 0; i < deptLstTemp.size(); i++) {
			String headName = "";
			if (deptLstTemp.get(i).getHeadOfDept() != null
					&& deptLstTemp.get(i).getHeadOfDept() != -1) {
				headName = departmentDao.getHeadNameByHeadID(deptLstTemp.get(i)
						.getHeadOfDept());
			}
			deptLstTemp.get(i).setHeadName(headName);
		}
		return deptLstTemp;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysUser(DepartmentBean departmentBean) {
		return departmentDao.updateDepartmentBean(departmentBean);
	}

	/*
	 * 获取部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public DepartmentBean getDepartmentById(Integer id) {
		return departmentDao.getById(id);
	}

	/*
	 * 新增部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addDepartment(DepartmentBean departmentBean) {
		return departmentDao.addDepartment(departmentBean);
	}

	/*
	 * 删除部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delDepartmentById(DepartmentBean departmentBean) {
		return departmentDao.delDepartmentById(departmentBean);
	}

	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<DepartmentBean> getDepartmentByConditions(
			DepartmentBean departmentBean, FlexiGridPageInfo flexiGridPageInfo) {
		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		// System.out.println("部门ID："+departmentBean.getDeptId()+"======上级部门ID："+departmentBean.getParentDeptID()+"=========所属单位："+departmentBean.getOrganizationBean().getOrganizationID()+"/"+departmentBean.getOrganizationBean().getOrganizationName());
		List<DepartmentBean> deptLst = departmentDao.getDepartmentByConditions(
				departmentBean, flexiGridPageInfo);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			String parentDeptName = "";
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());
			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		return deptLstTemp;
	}
	
	@Override
	public List<DepartmentBean> getDepartmentByConditions(DepartmentBean departmentBean) {
		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		// System.out.println("部门ID："+departmentBean.getDeptId()+"======上级部门ID："+departmentBean.getParentDeptID()+"=========所属单位："+departmentBean.getOrganizationBean().getOrganizationID()+"/"+departmentBean.getOrganizationBean().getOrganizationName());
		List<DepartmentBean> deptLst = departmentDao.getDepartmentByConditions(departmentBean);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			String parentDeptName = "";
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept.getParentDeptID());
			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		return deptLstTemp;
	}

	/*
	 * 根据用户Id获得部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentByUserId(Integer id) {
		return departmentDao.getDepartmentByUserID(id);
	}

	/*
	 * 根据部门获得用户信息
	 */
	@Override
	public List<SysUserInfoBean> findSysUserInfoByDeptId(int deptId) {
		return departmentDao.findSysUserInfoByDeptId(deptId);
	}

	/*
	 * 根君条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentByConditions(String paramName,
			String paramValue) {
		return departmentDao.getDepartmentByConditions(paramName, paramValue);
	}

	/*
	 * 获得部门树数据信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeVal() {
		return departmentDao.getDepartmentTreeVal();
	}
	
	/*
	 * 根据条件获得部门树数据信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeSelf(String deptId) {
		return departmentDao.getDepartmentTreeSelf(deptId);
	}

	/*
	 * 验证上级部门与所属组织是否匹配
	 */
	@Override
	public boolean getDeptByOrgIDAndParentDeptID(DepartmentBean departmentBean) {
		int count = departmentDao.getDeptBYOrgIDAndParentDeptID(departmentBean);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 验证部门名称唯一性
	 */
	@Override
	public boolean checkDeptName(DepartmentBean departmentBean) {
		return departmentDao.checkDeptName(departmentBean);
	}

	/*
	 * 获得单位的部门id
	 */
	@Override
	public List<Integer> getDeptIdsByOrdId(int orgId) {
		return departmentDao.getDeptIdsByOrdId(orgId);
	}

	/*
	 * 根据单位Id获得部门信息
	 */
	@Override
	public List<SysEmploymentBean> getEmploymentListByOrgID(
			DepartmentBean departmentBean) {
		List<SysEmploymentBean> empList = departmentDao
				.getEmploymentListByOrgID(departmentBean);
		for (int i = 0; i < empList.size(); i++) {
			String userName = sysUserDao.getUserNameByUserId(empList.get(i)
					.getUserId());
			empList.get(i).setUserName(userName);
		}
		return empList;
	}

	/*
	 * 根据部门名称获得部门信息
	 */
	@Override
	public DepartmentBean getDepartmentByDeptName(String deptName) {
		return departmentDao.getDepartmentByDeptName(deptName);
	}

	/*
	 * 验证部门是否可删除
	 */
	@Override
	public boolean checkBeforeDel(DepartmentBean departmentBean) {
		List<DepartmentBean> childsDepts = departmentDao
				.getDepartmentByParendID(departmentBean);
		List<SysEmploymentBean> employmentList = departmentDao
				.getSysEmploymentByDeptID(departmentBean);
		List<IPManSubNetRDeptBean> subnetRDeptLst = ipManSubNetRDeptMapper.getByDeptID(departmentBean.getDeptId());
		boolean flag = false;
		if ((childsDepts != null && childsDepts.size() > 0)
				|| (employmentList != null && employmentList.size() > 0)
				|| (subnetRDeptLst != null && subnetRDeptLst.size() > 0)) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public List<DepartmentBean> getDepartmentTreeList() {
		return departmentDao.getDepartmentTreeList();
	}

	@Override
	public List<DepartmentBean> querySysDept(DepartmentBean departmentBean) {

		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		List<DepartmentBean> deptLst = departmentDao
				.querySysDept(departmentBean);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());

			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				dept.setParentDeptName(deptTemp.getDeptName());
			}
			deptLstTemp.add(dept);
		}
		return deptLstTemp;

	}

	@Override
	public boolean checkDeptCode(String flag, DepartmentBean departmentBean) {
		return departmentDao.checkDeptCode(flag, departmentBean);
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgID(int organizationId) {
		// 根据organizationId获得其所有的子单位组织
		List<Integer> allOrgIDList = organizationDao
				.getAllOrgId(organizationId);
		String organizationIds = organizationId + ",";
		for (int i = 0; i < allOrgIDList.size(); i++) {
			organizationIds += allOrgIDList.get(i) + ",";
		}
		if (organizationIds.length() > 0) {
			organizationIds = organizationIds.substring(0, organizationIds
					.length() - 1);
		}
		// genju8单位组织获得所有的部门
		List<DepartmentBean> deptLst = departmentDao
				.getDepartmentByOrgIDs(organizationIds);
		return deptLst;
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgId(Integer organizationId) {
		// TODO Auto-generated method stub
		return departmentDao.getDepartmentByOrgId(organizationId);
	}

	@Override
	public List<Integer> getDepartmentByUserId(String userId) {
		return departmentDao.getDepartmentByUserId(userId);
	}

	/************************* update zheng zhen start *************************/
	@Override
	public void onMessage(Message message) {
		logger.debug("========部门同步开始");
		try {
			if (null != message && message instanceof TextMessage){
				TextMessage textMessage = (TextMessage)message;
				if (null != textMessage){
					Map<String,Object> req = (Map<String,Object>)JsonUtil.jsonToBean(textMessage.getText(),Map.class);
					if (null == req || null == req.get("data")){
						insertSysLog(textMessage.getText(),"格式转换错误！");
						return ;
					}
					
					int op = (int)req.get("op");
					switch(op){
						case 1 :
							insertDeptSyn(req,textMessage.getText());
							break;
						case 2 :
							updateDeptSyn(req,textMessage.getText());
							break;
						case 3 :
							deleteDeptSyn(req,textMessage.getText());
						    break;
						case 4:
							insertOrUpdateDept(req,textMessage.getText());
						   break;
					}
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private synchronized   void insertOrUpdateDept(Map<String,Object> req,String message){
		logger.debug("部门信息同步开始：==="+message);
		Map<String,Object> deptMap= (Map<String,Object>)req.get("data");
		//OrganizationBean dept = organizationDao.getOrganizationByCode((String)deptMap.get("code"));
		Map<String,Object> map_1=new HashMap<String,Object>();
		map_1.put("deptCode",(String)deptMap.get("code"));
		SysAsycDepartMent  sycDepartMent=sysAsycOrganizationMapper.quaryDeptInfos(map_1);
         
		/**
		 * 判断该数据是否存在,存在则插入,不存在则修改
		 */
		if (null != sycDepartMent){
			logger.debug("部门存在,进行修改");
			this.updateDeptSyn(req, message);
		}else{
			logger.debug("部门不存在,进行插入");
			this.insertDeptSyn(req, message);
		}
		
        
	}
	
	private synchronized void insertDeptSyn(Map<String,Object> req,String message){
		logger.debug("部门信息插入开始：==="+message);
		Map<String,Object> deptMap= (Map<String,Object>)req.get("data");
		Map<String,Object> parentDeptMap= (Map<String,Object>)deptMap.get("parent");
		Map<String,Object> orgMap= (Map<String,Object>)deptMap.get("org");
		String parentDeptCode = (String)parentDeptMap.get("code");
		Integer parentDeptId = 0;
		
		//监测当前部门是否存在
		//OrganizationBean dept = organizationDao.getOrganizationByCode((String)deptMap.get("code"));
		Map<String,Object> map_1=new HashMap<String,Object>();
		map_1.put("deptCode",(String)deptMap.get("code"));
		SysAsycDepartMent  sycDepartMent=sysAsycOrganizationMapper.quaryDeptInfos(map_1);
		if (null != sycDepartMent){
			logger.debug("部门已经存在,不需要同步");
			//insertSysLog(message,"该部门已存在！");
			return ;
		}else{
			logger.debug("部门不存在,待当前部门编号为:"+deptMap.get("code"));
		}
		if (null != parentDeptCode && !"".equals(parentDeptCode)){
			//DepartmentBean parentDept = departmentDao.getDepartmentByDeptCode(parentDeptCode);
			Map<String,Object> map_2=new HashMap<String,Object>();
			map_2.put("deptCode", parentDeptCode);
			SysAsycDepartMent  parentDept=sysAsycOrganizationMapper.quaryDeptInfos(map_2);
			/*if (null == parentDept){
				logger.debug(parentDeptCode+"没找到上级部门");
				insertSysLog(message,"上级部门【" + (String)parentDeptMap.get("name")+ "】不存在！");
				return ;
			}*/
			//parentDeptId = parentDept.getDeptID();
			logger.debug("上级部门的编号为:"+parentDeptId);
		}
		
		//查询所属单位
		//OrganizationBean org = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		Map<String,Object> map_org=new HashMap<String,Object>();
		map_org.put("organizationCode",(String)orgMap.get("code"));
		//SysAsycOrganization asycOrg=sysAsycOrganizationMapper.quaryOrganizationInfo(map_org);
		SysAsycDepartMent insertinfo=new SysAsycDepartMent();
		insertinfo.setDeptName((String)deptMap.get("name"));
		insertinfo.setDeptCode((String)deptMap.get("code"));
		insertinfo.setDescr((String)deptMap.get("description"));
		insertinfo.setHeadOfDept("0");
		insertinfo.setOrganizationID(null);
		insertinfo.setParentDeptID(null);
		int count=sysAsycOrganizationMapper.insertDepartmentInfo(insertinfo);
		boolean bool=false;
		if(count>0){
			bool=true;
		}
		
		/*//转换实体bean
		DepartmentBean departmentBean = new DepartmentBean();
		departmentBean.setDeptName((String)deptMap.get("name"));
		departmentBean.setDeptCode((String)deptMap.get("code"));
		departmentBean.setParentDeptID(parentDeptId);
		departmentBean.setDescr((String)deptMap.get("description"));
		OrganizationBean org=new OrganizationBean();
		org.setDescr(asycOrg.getDescr());
		org.setOrganizationCode(asycOrg.getOrganizationCode());
		org.setOrganizationID(asycOrg.getOrganizationID());
		org.setOrganizationName(asycOrg.getOrganizationName());
		org.setParentOrgID(asycOrg.getParentOrgID());
		departmentBean.setOrganizationBean(org);
		departmentBean.setHeadOfDept(0);*/
	    //boolean bool = departmentDao.addDepartment(departmentBean);
	    if (!bool){
	    	logger.debug(insertinfo.getDeptCode()+"插入数据失败!");
		    insertSysLog(message,"插入数据失败！");
	    }else{
	    	logger.debug(insertinfo.getDeptCode()+"插入数据成功!");
	    	 insertSysLog(message,"插入数据成功！");
	    }
	}
	
	
	private synchronized void updateDeptSyn(Map<String,Object> req,String message){
		logger.debug("=========修改部门信息");
		Map<String,Object> deptMap= (Map<String,Object>)req.get("data");
		Map<String,Object> parentMap= (Map<String,Object>)deptMap.get("parent");
		Map<String,Object> orgMap= (Map<String,Object>)deptMap.get("org");
		String parentDeptCode = (String)parentMap.get("code");
		String orgCode=(String)orgMap.get("code");
		String parentDeptId = "0";//上级部门ID
		if (null != parentDeptCode && !"".equals(parentDeptCode)){
			//DepartmentBean parentDept = departmentDao.getDepartmentByDeptCode(parentDeptCode);
			Map<String,Object> map_1=new HashMap<String,Object>();
			map_1.put("deptCode",parentDeptCode);
			SysAsycDepartMent  parentDept=sysAsycOrganizationMapper.quaryDeptInfos(map_1);
			if (null == parentDept){
				logger.debug("上级部门【" + (String)parentMap.get("code") + "】不存在！");
				insertSysLog(message,"上级部门【" + (String)parentMap.get("code") + "】不存在！");
				if(orgCode==null ||(orgCode!=null && !orgCode.equals(parentDeptCode)) ){
					logger.debug((String)deptMap.get("code")+"同时不是顶级节点");
					return ;
				}
				
			}else{
				logger.debug("上级部门【" + (String)parentMap.get("code") + "】存在！");
				parentDeptId = parentDept.getDeptID();
			}
			logger.debug("上级部门的编号为:"+parentDeptId);
		}
		//查询所属单位
		Map<String,Object> map_org=new HashMap<String,Object>();
		map_org.put("organizationCode",(String)orgMap.get("code"));
		SysAsycOrganization asycOrg=sysAsycOrganizationMapper.quaryOrganizationInfo(map_org);
		//获取要更新的部门信息
		Map<String,Object> map_1=new HashMap<String,Object>();
		map_1.put("deptCode",(String)deptMap.get("code"));
		SysAsycDepartMent  deptment=sysAsycOrganizationMapper.quaryDeptInfos(map_1);
		if (null == deptment){
			logger.debug("没有找到修改的数据！");
			insertSysLog(message,"没有找到修改的数据！");
			return ;
		}else{
			logger.debug("找到要修改的部门数据！");
		}
		if(asycOrg!=null){
			deptment.setOrganizationID(asycOrg.getOrganizationID());
		}else{
			logger.debug("====没找到所属单位的message:"+message);
		}
		deptment.setParentDeptID(parentDeptId);
		boolean bool=false;
		int i=sysAsycOrganizationMapper.updateDepartmentInfo(deptment);
		if(i>0){
			bool=true;
		}else{
			bool=false;
		}
		//boolean bool = departmentDao.updateDepartmentBean(updateDeptBean);
		if (!bool){
		  logger.debug("要修改的部门数据更新失败");
		  insertSysLog(message,"更新数据失败！");
		}else{
		  logger.debug("要修改的部门数据更新成功！");
		  insertSysLog(message,"更新数据成功！");
		}
	}
	
	private void deleteDeptSyn(Map<String,Object> req,String message){
		logger.debug("=========删除部门信息");
		List<Map<String,Object>> orgList = (List<Map<String,Object>>)req.get("data");
		for (Map<String,Object> map : orgList){
			DepartmentBean departmentBean = departmentDao.getDepartmentByDeptCode((String)map.get("code"));
			if (null == departmentBean){
				logger.debug("没有找到删除的部门数据！");
				insertSysLog(message,"没有找到删除的数据！");
				continue;
			}
			boolean bool = departmentDao.delDepartmentById(departmentBean);
			if (!bool){
				logger.debug("=========删除部门信息删除失败");
				insertSysLog(message,"删除失败！");
			}else{
				logger.debug("=========删除部门信息删除成功");
				insertSysLog(message,"删除成功！");
			}
		}
	}
	
	
	private void insertSysLog(String context,String result){
		SysLog sysLog = new SysLog();
		sysLog.setModule("部门管理");
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
	public int getOrgByUserId(Integer userId) {
		
		return departmentDao.getOrgByUserId(userId);
	}
	
	@Override
	public List<Integer> getExistIds(boolean isOrg) {
		return departmentDao.getExistIds(isOrg);
	}
	
	/*
	 * 根据条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartments(String paramName,
			String paramValue) {
		return departmentDao.getDepartments(paramName, paramValue);
	}
	
}
