package com.fable.insightview.platform.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.dao.IOrganizationalEntityDao;
import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.OrganizationalEntityBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.log.entity.SysLog;
import com.fable.insightview.platform.log.service.ISysLogService;
import com.fable.insightview.platform.provider.dao.IProviderDao;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.fable.insightview.platform.userAuthenticate.service.IUserInfoService;

/**
 * 部门组织Service
 * 
 * @author 武林
 * 
 */
@Service("sysUserService1")
public class SysUserServiceImpl implements ISysUserService,MessageListener //update liujinb 
{

	private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	
	@Autowired
	protected ISysUserDao sysUserDao;
	@Autowired
	protected ISysEmploymentDao sysEmploymentDao;
	@Autowired
	protected IOrganizationalEntityDao organizationalEntityDao;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IOrganizationDao organizationDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private ISysLogService sysLogService;
	
	
	@Autowired
	protected IProviderDao providerDao;
	DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public List<SysUserInfoBean> queryUserByAuto(SysUserInfoBean sysUserBean) {
		return sysUserDao.queryUserByAuto(sysUserBean);
	}
	
	/*
	 * 根据用户查找当前部门下所有人
	 */
	@Override
	public List<SysUserInfoBean> queryUserByDepartment(SysUserInfoBean sysUserBean) {
		return sysUserDao.queryUserByDepartment(sysUserBean);
	}

	@Override
	public int getTotalCount(SysUserInfoBean user) {
		return sysUserDao.getTotalCount(user);
	}

	@Override
	public int getTotalCountByGroup(SysUserInfoBean sysUserBean) {
		String userAccount = sysUserBean.getUserAccount();
		String userName = sysUserBean.getUserName();
		String mobilePhone = sysUserBean.getMobilePhone();
		if(userAccount!=null && !"".equals(userAccount)){
			if(userAccount.contains("%")){
				userAccount = userAccount.replace("%", "\\%");
			}
		}
		if(userName!=null && !"".equals(userName)){
			if(userName.contains("%")){
				userName = userName.replace("%", "\\%");
			}
		}
		if(mobilePhone!=null && !"".equals(mobilePhone)){
			if(mobilePhone.contains("%")){
				mobilePhone = mobilePhone.replace("%", "\\%");
			}
		}
		sysUserBean.setUserAccount(userAccount);
		sysUserBean.setUserName(userName);
		sysUserBean.setMobilePhone(mobilePhone);
		return sysUserDao.getTotalCountByGroup(sysUserBean);
	}

	@Override
	public List<SysUserInfoBean> checkUserInfo(SysUserInfoBean user) {
		List<SysUserInfoBean> userLst = sysUserDao.chkUserInfo(user);
		return userLst;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@Override
	public boolean updateSysUser(SysUserInfoBean sysUserBean) {
		try {
			sysUserBean.setUserPassword(CryptoUtil.Encrypt(sysUserBean
					.getUserPassword()));
		} catch (Exception e) {
			LogManager.getLogger(e);
		}
		return sysUserDao.updateSysUser(sysUserBean);
	}
	
	public static Timestamp getCurrDate() {
    	Date d = new Date();
    	Timestamp ts = new Timestamp(d.getTime());
    	return ts;
    }
	
	

	/*
	 * 新增部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysUser(SysUserInfoBean sysUserBean) {
		boolean addSysE=false;
		boolean addSysP=false;
		sysUserBean.setCreateTime(this.getCurrDate());
		try {
			sysUserBean.setUserPassword(sysUserBean.getUserPassword());
		} catch (Exception e) {
			LogManager.getLogger(e);
		}
		
		//插入用户信息SysUserInfo
		boolean addUser=sysUserDao.addSysUser(sysUserBean);
		
		//判断是否插入成功
		if(addUser==true){
			logger.debug("插入用户信息成功，开始流程用户的认证");
			//监测账号是否存在，流程用户的认证
			int existOrgEntity=sysUserDao.getOrganization(sysUserBean.getUserAccount());
			
			
			//如果没有，向流程用户的认证表中插入数据
			if(existOrgEntity==0){
				OrganizationalEntityBean orgE = new OrganizationalEntityBean();
				orgE.setDtype("User");
				orgE.setId(sysUserBean.getUserAccount());
				organizationalEntityDao.insert(orgE);
			}
			
//			SysEmploymentBean emp=new SysEmploymentBean();
//			emp.setDeptID(sysUserBean.getSysEmploymentBean().getDeptID());
//			emp.setEmployeeCode(sysUserBean.getSysEmploymentBean().getEmployeeCode());
//			emp.setGradeID(sysUserBean.getSysEmploymentBean().getGradeID());
//			emp.setOrganizationID(sysUserBean.getSysEmploymentBean().getOrganizationID());
//			emp.setUserId(sysUserBean.getUserID());
			
			//UserType：0:管理员,1企业内IT部门用户,2:企业业务部门用户,3:外部供应商用户
			if(sysUserBean.getUserType()==3){
				
				//将用户的ID放入供应商登录用户SysProviderUser表中
				sysUserBean.getSysProviderUserBean().setUserId(sysUserBean.getUserID());
				addSysP=providerDao.addProviderUser(sysUserBean.getSysProviderUserBean());
				
			}else{
				
				//判断员工的bean是否为空
				if (null != sysUserBean.getSysEmploymentBean()){
				
					//将用户的ID添加到员工表中
				sysUserBean.getSysEmploymentBean().setUserId(sysUserBean.getUserID());
				addSysE=sysEmploymentDao.addSysEmp(sysUserBean.getSysEmploymentBean());
				}
				
			}
		}
		
		if((addUser==true && addSysE==true) || (addUser==true && addSysP==true)){
			return true;
		}else{
			return false;
		}
		
	}

	/*
	 * 删除部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delSysUserById(SysUserInfoBean sysUserBean) {
		sysEmploymentDao.delSysEmpByUserId(sysUserBean.getUserID());
		return sysUserDao.delSysUserById(sysUserBean) == 0 ? false : true;
	}

	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysUserInfoBean> getSysUserByConditions(
			SysUserInfoBean sysUserBean, FlexiGridPageInfo flexiGridPageInfo) {
		if(sysUserBean.getTreeType()!=null && "" != sysUserBean.getTreeType()){
			if(sysUserBean.getTreeType().contains(",")==true){
				String[] treeTypes=sysUserBean.getTreeType().split(",");
				String userIds="0";
				for (int j = 0; j < treeTypes.length; j++) {
					String treeType=treeTypes[j];
					if(!"-1".equals(treeType)){
						List<Integer> userIdLst=new ArrayList<Integer>();
						if(treeType.startsWith("O")==true){
							treeType=treeType.substring(1);
							int orgId=Integer.parseInt(treeType);
							userIdLst=sysEmploymentDao.getUserIdByOrgId(orgId);
							
						}else if(treeType.startsWith("P")==true){
							treeType=treeType.substring(1);
							userIdLst=providerDao.getUserIdByProId(Integer.parseInt(treeType));
						}else if(treeType.startsWith("D")==true){
							treeType=treeType.substring(1);
							userIdLst=sysEmploymentDao.getUserIdByDeptId(treeType);
						}
						for (int i = 0; i < userIdLst.size(); i++) {
							userIds+=","+userIdLst.get(i);
						}
						
						sysUserBean.setUserIds(userIds);
					}
				}
			}else{
				if(!"-1".equals(sysUserBean.getTreeType())){
					String treeType=sysUserBean.getTreeType();
					List<Integer> userIdLst=new ArrayList<Integer>();
					String userIds="0";
					if(treeType.startsWith("O")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						int orgId=Integer.parseInt(treeType);
						userIdLst=sysEmploymentDao.getUserIdByOrgId(orgId);
						
					}else if(treeType.startsWith("P")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=providerDao.getUserIdByProId(Integer.parseInt(treeType));
					}else if("0".equals(sysUserBean.getTreeType())){
						userIdLst=sysEmploymentDao.getUserIdByDeptId("0");
					}else if(treeType.startsWith("D")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=sysEmploymentDao.getUserIdByDeptId(treeType);
					}else if("1000".equals(sysUserBean.getTreeType())){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=providerDao.getUserIdByProId(0);
					}
					for (int i = 0; i < userIdLst.size(); i++) {
						userIds+=","+userIdLst.get(i);
					}
					
					sysUserBean.setUserIds(userIds);
				}
			}
			
			
		}
		String userAccount = sysUserBean.getUserAccount();
		String userName = sysUserBean.getUserName();
		String mobilePhone = sysUserBean.getMobilePhone();
		if(userAccount!=null && !"".equals(userAccount)){
			if(userAccount.contains("%")){
				userAccount = userAccount.replace("%", "\\%");
			}
		}
		if(userName!=null && !"".equals(userName)){
			if(userName.contains("%")){
				userName = userName.replace("%", "\\%");
			}
		}
		if(mobilePhone!=null && !"".equals(mobilePhone)){
			if(mobilePhone.contains("%")){
				mobilePhone = mobilePhone.replace("%", "\\%");
			}
		}
		sysUserBean.setUserAccount(userAccount);
		sysUserBean.setUserName(userName);
		sysUserBean.setMobilePhone(mobilePhone);
		return sysUserDao
				.getSysUserByConditions(sysUserBean, flexiGridPageInfo);
	}
	
	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysUserInfoBean> getSysUserByCondition(SysUserInfoBean sysUserBean) {
		if(sysUserBean.getTreeType()!=null && "" != sysUserBean.getTreeType()){
			if(sysUserBean.getTreeType().contains(",")==true){
				String[] treeTypes=sysUserBean.getTreeType().split(",");
				String userIds="0";
				for (int j = 0; j < treeTypes.length; j++) {
					String treeType=treeTypes[j];
					if(!"-1".equals(treeType)){
						List<Integer> userIdLst=new ArrayList<Integer>();
						if(treeType.startsWith("O")==true){
							treeType=treeType.substring(1);
							int orgId=Integer.parseInt(treeType);
							userIdLst=sysEmploymentDao.getUserIdByOrgId(orgId);
							
						}else if(treeType.startsWith("P")==true){
							treeType=treeType.substring(1);
							userIdLst=providerDao.getUserIdByProId(Integer.parseInt(treeType));
						}else if(treeType.startsWith("D")==true){
							treeType=treeType.substring(1);
							userIdLst=sysEmploymentDao.getUserIdByDeptId(treeType);
						}
						for (int i = 0; i < userIdLst.size(); i++) {
							userIds+=","+userIdLst.get(i);
						}
						
						sysUserBean.setUserIds(userIds);
					}
				}
			}else{
				if(!"-1".equals(sysUserBean.getTreeType())){
					String treeType=sysUserBean.getTreeType();
					List<Integer> userIdLst=new ArrayList<Integer>();
					String userIds="0";
					if(treeType.startsWith("O")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						int orgId=Integer.parseInt(treeType);
						userIdLst=sysEmploymentDao.getUserIdByOrgId(orgId);
						
					}else if(treeType.startsWith("P")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=providerDao.getUserIdByProId(Integer.parseInt(treeType));
					}else if("0".equals(sysUserBean.getTreeType())){
						userIdLst=sysEmploymentDao.getUserIdByDeptId("0");
					}else if(treeType.startsWith("D")==true){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=sysEmploymentDao.getUserIdByDeptId(treeType);
					}else if("1000".equals(sysUserBean.getTreeType())){
						treeType=sysUserBean.getTreeType().substring(1);
						userIdLst=providerDao.getUserIdByProId(0);
					}
					for (int i = 0; i < userIdLst.size(); i++) {
						userIds+=","+userIdLst.get(i);
					}
					
					sysUserBean.setUserIds(userIds);
				}
			}
			
			
		}
		String userAccount = sysUserBean.getUserAccount();
		String userName = sysUserBean.getUserName();
		String mobilePhone = sysUserBean.getMobilePhone();
		if(userAccount!=null && !"".equals(userAccount)){
			if(userAccount.contains("%")){
				userAccount = userAccount.replace("%", "\\%");
			}
		}
		if(userName!=null && !"".equals(userName)){
			if(userName.contains("%")){
				userName = userName.replace("%", "\\%");
			}
		}
		if(mobilePhone!=null && !"".equals(mobilePhone)){
			if(mobilePhone.contains("%")){
				mobilePhone = mobilePhone.replace("%", "\\%");
			}
		}
		sysUserBean.setUserAccount(userAccount);
		sysUserBean.setUserName(userName);
		sysUserBean.setMobilePhone(mobilePhone);
		return sysUserDao.getSysUserByCondition(sysUserBean);
	}

	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysUserInfoBean> getSysUserByGroup(SysUserInfoBean sysUserBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		return sysUserDao.getSysUserByGroup(sysUserBean, flexiGridPageInfo);
	}

	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysUserInfoBean> getSysUserByConditions(String paramName,
			String paramValue) {
		return sysUserDao.getSysUserByConditions(paramName, paramValue);
	}

	/*
	 * 查询用户信息
	 * 
	 * @author sanyou
	 */
	@Override
	public SysUserInfoBean findSysUserById(int sysUserId) {
		return sysUserDao.getById(sysUserId);
	}

	/*
	 * 获取所有用户
	 * 
	 * @see com.fable.itsm.permission.service.ISysUserService#getAllSysUsers()
	 * 
	 * @author Jiuwei Li
	 */
	public List<SysUserInfoBean> getAllSysUsers() {
		return sysUserDao.getAllSysUsers();
	}

	@Override
	public List<SysUserInfoBean> getSysUserByDept(DepartmentBean departmentBean) {
		return sysUserDao.getSysUserByDept(departmentBean);
	}

	@Override
	public boolean lockSysUser(SysUserInfoBean sysUserBean) {
		//解除锁定时清空记录次数
		SystemParamInit.mapCount.put(sysUserBean.getUserAccount(), null);
		return sysUserDao.lockSysUser(sysUserBean);
	}

	@Override
	public boolean modifyPwd(SysUserInfoBean sysUserBean) {
		sysUserBean.setLastModifyTime(new Timestamp(new Date().getTime()));
		try {
			sysUserBean.setUserPassword(CryptoUtil.Encrypt(sysUserBean
					.getUserPassword()));
		} catch (Exception e) {
			LogManager.getLogger(e);
		}
		return sysUserDao.modifyPwd(sysUserBean);
	}

	@Override
	public SysUserInfoBean getTreeIdByTreeName(String treeName) {
		return sysUserDao.getTreeIdByTreeName(treeName);
	}

	@Override
	public List<SysUserInfoBean> getSysUserByConditions(
			SysUserInfoBean sysUserBean) {
		return sysUserDao.getSysUserByConditions(sysUserBean);
	}

	@Override
	public int getOrganization(String userAccount) {
		return sysUserDao.getOrganization(userAccount);
	}

	@Override
	public List<SysUserInfoBean> getSysUserByNotifyFilter(
			SysUserInfoBean sysUserBean, FlexiGridPageInfo flexiGridPageInfo) {
		return sysUserDao.getSysUserByNotifyFilter(sysUserBean, flexiGridPageInfo);
	}

	@Override
	public int getTotalCountByNotifyFilter(SysUserInfoBean sysUserBean) {
		return sysUserDao.getTotalCountByNotifyFilter(sysUserBean);
	}
	
	@Override
	public void updateStuts(String userAccount){
		this.sysUserDao.updateStuts(userAccount);
	}

	@Override
	public List<SysUserInfoBean> querySysUser(SysUserInfoBean sysUserBean) {
		return sysUserDao.querySysUser(sysUserBean);
	}

	@Override
	public boolean addOrganization(
			OrganizationalEntityBean organizationalEntityBean) {
		return organizationalEntityDao.addOrganization(organizationalEntityBean);
	}

	@Override
	public List<OrganizationalEntityBean> getAllOrganizationalEntity() {
		return organizationalEntityDao.getAllOrganizationalEntity();
	}

	@Override
	public List<SysUserInfoBean> queryUserByExact(SysUserInfoBean sysUserBean) {
		// TODO Auto-generated method stub
		return sysUserDao.queryUserByExact(sysUserBean);
	}

	@Override
	public List<Map<String, String>> queryUsers(String userType, String orgId, Map<String, String> conditions, FlexiGridPageInfo flexiGridPageInfo) {
		return sysUserDao.queryUsers(userType, orgId, conditions, flexiGridPageInfo);
	}

	@Override
	public Integer queryUsersCount(String userType, String orgId, Map<String, String> conditions) {
		return sysUserDao.queryUsersCount(userType, orgId, conditions);
	}
	
	public Integer queryOrgIdByUserInfo(Integer userId) {
		Integer orgId = this.sysUserDao.queryOrgIdByUserInfo(userId);
		return orgId;
	}

	@Override
	public String getUserNameByUserId(Integer id) {
		return sysUserDao.getUserNameByUserId(id);
	}

	@Override
	public Integer queryUserIdByIdCard(String idCard) {
		return sysUserDao.queryUserIdByIdCard(idCard);
	}
	
	
	@Override
	public List<SysUserInfoBean> findUsersWithinChildDept(DepartmentBean qryDept) {
		return this.sysUserDao.findUsersWithinChildDept(qryDept);
	}

	/************************* update liujinb start *************************/
	@Override
	public void onMessage(Message message)  {
		logger.debug("=====开始人员同步.......");
		try {
			if (null != message && message instanceof TextMessage){
				TextMessage textMessage = (TextMessage) message;
				if (null != textMessage){
					@SuppressWarnings("unchecked")
					Map<String,Object> reqMap = (Map<String,Object>)JsonUtil.jsonToBean(textMessage.getText(),Map.class);
					
					if (null == reqMap || null == reqMap.get("data")){
						insertSysLog(textMessage.getText(),"json格式转换错误！");
						return;
					}
					int op =(int)reqMap.get("op");
				    switch(op){
					    case  1 :
					    	insertUserSyn(reqMap,textMessage.getText());
					    	break;
					    case 2 :
					    	updateUserSyn(reqMap,textMessage.getText());
					    	break;
					    case 3 :
					    	deleteUserSyn(reqMap,textMessage.getText());
					    	break;
					    case 4:
					    	insertOrUpdateUserSyn(reqMap,textMessage.getText());
					    	break;
					    default :
				    		break;
				    }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertOrUpdateUserSyn(Map<String,Object> req,String message){
		logger.debug("=====判断人员信息是插入还是修改.......");
		Map<String,Object> dataMap = (Map<String,Object>)req.get("data");//获取用户信息
		List<SysUserInfoBean> userLst = getSysUserByConditions("userAccount", (String)dataMap.get("account"));
		if (null != userLst && !userLst.isEmpty()){
			logger.debug("=====该用户已存在！");
			this.updateUserSyn(req,message);
		}else{
			logger.debug("=====该用户不存在！");
			this.insertUserSyn(req, message);
		}
	}
	private void insertUserSyn(Map<String,Object> req,String message){
		logger.debug("=====开始人员插入.......");
		@SuppressWarnings("unchecked")
		Map<String,Object> dataMap = (Map<String,Object>)req.get("data");//获取用户信息
		@SuppressWarnings("unchecked")
		Map<String,Object> orgMap = (Map<String,Object>)dataMap.get("org");
		@SuppressWarnings("unchecked")
		Map<String,Object> deptMap = (Map<String,Object>)dataMap.get("dept");
		String userName = (String)dataMap.get("name");//用户名
		
		
		List<SysUserInfoBean> userLst = getSysUserByConditions("userAccount", (String)dataMap.get("account"));
		if (null != userLst && !userLst.isEmpty()){
			logger.debug("=====该用户已存在！");
			insertSysLog(message,"该用户已存在！");
			return;
		}
		
		//查询所属机构
		OrganizationBean  organizationBean = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		if (null == organizationBean){
			logger.debug("所属机构【" + (String)orgMap.get("name") +"】不存在！");
			insertSysLog(message,"所属机构【" + (String)orgMap.get("name") +"】不存在！");
			return;
		}else{
			logger.debug("所属机构【" + (String)orgMap.get("name") +"】存在！");
		}
		
		
		DepartmentBean departmentBean = departmentDao.getDepartmentByDeptCode((String)deptMap.get("code"));
		if (null == departmentBean){
			logger.debug("所属部门【" + (String)deptMap.get("name") +"】不存在！");
			insertSysLog(message,"所属部门【" + (String)deptMap.get("name") +"】不存在！");
			return ;
		}else{
			logger.debug("所属部门【" + (String)deptMap.get("name") +"】存在！");
		}
		 SysUserInfoBean sysUserBean = new SysUserInfoBean();
		 sysUserBean.setUserAccount((String)dataMap.get("account"));//账户
		 sysUserBean.setUserName(userName);//用户名
		 try {
			sysUserBean.setUserPassword(CryptoUtil.Encrypt(dataMap.get("password").toString()));//密码
		} catch (Exception e) {
			e.printStackTrace();
		}
		 sysUserBean.setMobilePhone((String)dataMap.get("telephone"));//联系电话
		 sysUserBean.setIsAutoLock(1);//
		 sysUserBean.setStatus(1);//状态
		 sysUserBean.setUserType(1);//用户类型
		 sysUserBean.setCreateTime(this.getCurrDate());
		 sysUserBean.setState(0);
		 
		 SysEmploymentBean sysEmploymentBean = new SysEmploymentBean();
		 sysEmploymentBean.setEmployeeCode((String)dataMap.get("code"));//用户编码
		 sysEmploymentBean.setOrganizationID(organizationBean.getOrganizationID());//所属机构
		 sysEmploymentBean.setDeptID(departmentBean.getDeptId());//所属部门
		 
		 sysUserBean.setSysEmploymentBean(sysEmploymentBean);
		 boolean bool = addSysUser(sysUserBean);
		 if (!bool){
			 logger.debug("插入数据失败！");
			 insertSysLog(message,"插入数据失败！");
		 }else{
			 logger.debug("插入数据成功！");
			 insertSysLog(message,"插入数据成功！");
		 }
	}
	
	
	private void updateUserSyn(Map<String,Object> req,String message){
		logger.debug("=====开始人员修改.......");
		@SuppressWarnings("unchecked")
		Map<String,Object> dataMap = (Map<String,Object>)req.get("data");//获取用户信息
		@SuppressWarnings("unchecked")
		Map<String,Object> orgMap = (Map<String,Object>)dataMap.get("org");
		@SuppressWarnings("unchecked")
		Map<String,Object> deptMap = (Map<String,Object>)dataMap.get("dept");
		String userName = (String)dataMap.get("name");//用户名
		String userAccount = (String)dataMap.get("account");
		//查询所属机构
		OrganizationBean  organizationBean = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		if (null == organizationBean){
			logger.debug("所属机构【" + (String)orgMap.get("name")+"】不存在！");
			insertSysLog(message,"所属机构【" + (String)orgMap.get("name")+"】不存在！");
			return;
		}else{
			logger.debug("所属机构【" + (String)orgMap.get("name")+"】存在！");
		     
		}
		
		DepartmentBean departmentBean = departmentDao.getDepartmentByDeptCode((String)deptMap.get("code"));
		if (null == departmentBean){
			logger.debug("所属部门【" + (String)deptMap.get("name")+"】不存在！");
			insertSysLog(message,"所属部门【" + (String)deptMap.get("name")+"】不存在！");
			return ;
		}else{
			logger.debug("所属部门【" + (String)deptMap.get("name")+"】存在！");
		}
		//TODO 查询用户信息
		List<SysUserInfoBean> userLst = getSysUserByConditions("userAccount", userAccount);
		if (null == userLst || userLst.isEmpty()){
			logger.debug("userLst没有找到修改的数据！");
			insertSysLog(message,"没有找到修改的数据！");
			return ;
		}else{
			logger.debug("用户信息存在");
		}
		SysUserInfoBean sysUserBean = userLst.get(0);
		List<SysEmploymentBean> empLst = sysEmploymentDao.getEmploymentByUserId(sysUserBean.getUserID());
		if (null == empLst || empLst.isEmpty()){
			logger.debug("SysEmploymentBean没有找到修改的数据！");
			insertSysLog(message,"没有找到修改的数据！");
			return;
		}
		SysEmploymentBean sysEmploymentBean = empLst.get(0);
		
		sysUserBean.setUserAccount(userAccount);
		sysUserBean.setUserName(userName);
		try {
			sysUserBean.setUserPassword(CryptoUtil.Encrypt(dataMap.get("password").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sysUserBean.setMobilePhone((String)dataMap.get("telephone"));
		sysUserBean.setLastModifyTime(new Timestamp(new Date().getTime()));
		
		sysEmploymentBean.setEmployeeCode((String)dataMap.get("code"));
		sysEmploymentBean.setOrganizationID(organizationBean.getOrganizationID());
		sysEmploymentBean.setDeptID(departmentBean.getDeptId());
		
		boolean empbool = sysEmploymentDao.updateSysEmp(sysEmploymentBean);
		boolean userbool = sysUserDao.updateSysUser(sysUserBean);
		if (!empbool || !userbool){
			logger.debug("更新数据失败！");
			insertSysLog(message,"更新数据失败！");
		}else{
			logger.debug("更新数据成功！");
			insertSysLog(message,"更新数据成功！");
		}
	}
	
	private void deleteUserSyn(Map<String,Object> map,String message){
		List<Map<String,Object>> userList = (List<Map<String,Object>>)map.get("data");
		for (Map<String,Object> userMap : userList){
			
			List<SysUserInfoBean> userLst = getSysUserByConditions("userAccount", (String)userMap.get("account"));
			if (null == userLst || userLst.isEmpty()){
				insertSysLog(message,"没有找到删除的数据！");
				continue;
			}
			SysUserInfoBean SysUserInfoBean = userLst.get(0);
			boolean empbool = sysEmploymentDao.delSysEmpByUserId(SysUserInfoBean.getUserID());
			int rows = sysUserDao.delSysUserById(SysUserInfoBean);
			if (!empbool || rows == 0){
				insertSysLog(message,"删除数据失败！");
			}
			else{
				insertSysLog(message,"删除数据成功！");
			}
		}
		
	}
	
	private void insertSysLog(String context,String result){
		SysLog sysLog = new SysLog();
		sysLog.setModule("用户管理");
		sysLog.setActor("admin");
		sysLog.setContent(context);
		sysLog.setResult(result);
		sysLog.setOperation("数据同步");
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sysLog.setOptionTime(sdf.format(new Date())); //操作时间
		sysLogService.insertSysLog(sysLog);
	}
	public static void main(String[] args){
		try {
			System.out.println(CryptoUtil.Encrypt("123456"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/************************* update liujinb end *************************/
}
