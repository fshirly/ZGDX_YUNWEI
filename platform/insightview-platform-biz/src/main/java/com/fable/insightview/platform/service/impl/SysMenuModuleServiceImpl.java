package com.fable.insightview.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysMenuModuleDao;
import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.ISysMenuModuleService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("sysMenuModuleService")
public class SysMenuModuleServiceImpl implements ISysMenuModuleService {
	private final Logger logger = LoggerFactory.getLogger(SysMenuModuleServiceImpl.class);
	@Autowired
	protected ISysMenuModuleDao sysMenuModuleDao;

	@Override
	public List<SysMenuModuleBean> getSysMenuByConditions(String paramName,
			String paramValue) {
		List<SysMenuModuleBean> menuLst = sysMenuModuleDao
				.getSysMenuByConditions(paramName, paramValue);
		return menuLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuModuleBean> getSysMenuTreeVal() {
		return sysMenuModuleDao.getSysMenuTreeVal();
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysUser(SysMenuModuleBean sysMenuBean) {
		return sysMenuModuleDao.updateSysMenu(sysMenuBean);
	}

	@Override
	public int getTotalCount(SysMenuModuleBean sysMenuModuleBean) {
		return sysMenuModuleDao.getTotalCount(sysMenuModuleBean);
	}

	@Override
	public List<SysMenuModuleBean> getUserSysMenuModule(
			SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean) {

		return sysMenuModuleDao.getUserSysMenuModule(sysMenuModuleBean,
				userBean);
	}
	
	@Override
	public List<MenuNode> getUserSubMenu(SysMenuModuleBean sysMenuModuleBean, SysUserInfoBean userBean){
		List<MenuNode> menus = sysMenuModuleDao.getUserSubMenu(sysMenuModuleBean,
				userBean);
		return formatTree(menus);
	}
	
	private List<MenuNode> formatTree(List<MenuNode> list) {

		MenuNode root = new MenuNode();
		MenuNode node = new MenuNode();
        //List<MenuNode> treelist = new ArrayList<MenuNode>();// 拼凑好的json格式的数据
        List<MenuNode> parentnodes = new ArrayList<MenuNode>();// parentnodes存放所有的父节点
        
        if (list != null && list.size() > 0) {
            root = list.get(0) ;
            //循环遍历树查询的所有节点
            for (int i = 1; i < list.size(); i++) {
                node = list.get(i);
                if(node.getParentMenuID() == root.getId()){
                    //为tree root 增加子节点
                    parentnodes.add(node) ;
                    root.getChildren().add(node) ;
                }else{//获取root子节点的孩子节点
                    getChildrenNodes(parentnodes, node);
                    parentnodes.add(node) ;
                }
            }    
        }
        //treelist.add(root) ;
        return root.getChildren() ;

    }
	
	private void getChildrenNodes(List<MenuNode> parentnodes, MenuNode node) {
        //循环遍历所有父节点和node进行匹配，确定父子关系
        for (int i = parentnodes.size() - 1; i >= 0; i--) {
        	MenuNode pnode = parentnodes.get(i);
            //如果是父子关系，为父节点增加子节点，退出for循环
            if (pnode.getId() == node.getParentMenuID()) {
                pnode.setState("closed") ;//关闭二级树
                pnode.getChildren().add(node) ;
                return ;
            } 
//            else {
//                //如果不是父子关系，删除父节点栈里当前的节点，
//                //继续此次循环，直到确定父子关系或不存在退出for循环
//                parentnodes.remove(i) ;
//            }
        }
    }

	/*
	 * 新增单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addSysMenuModule(SysMenuModuleBean sysMenuModuleBean) {
		return sysMenuModuleDao.addSysMenuModule(sysMenuModuleBean);
	}

	/*
	 * 删除单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delSysMenuModuleById(int menuId) {
		return sysMenuModuleDao.delSysMenuModuleById(menuId);
	}

	/*
	 * 查询单位组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<SysMenuModuleBean> getSysMenuModuleByConditions(
			SysMenuModuleBean sysMenuModuleBean,
			FlexiGridPageInfo flexiGridPageInfo) {
		List<SysMenuModuleBean> sysMenuLst = sysMenuModuleDao
				.getSysMenuModuleByConditions(sysMenuModuleBean,
						flexiGridPageInfo);

		for (SysMenuModuleBean sysMenu : sysMenuLst) {
			List<SysMenuModuleBean> pSysMenuLst = sysMenuModuleDao
					.getSysMenuByConditions("menuId", sysMenu.getParentMenuID()
							+ "");
			if (null == pSysMenuLst || pSysMenuLst.size() <= 0) {
				sysMenu.setParentSysMenu(new SysMenuModuleBean());
			} else {
				sysMenu.setParentSysMenu(pSysMenuLst.get(0));
			}
		}
		// TODO Auto-generated method stub
		return sysMenuLst;
	}

	@Override
	public SysMenuModuleBean getIdBymenuName(String menuName) {
		return sysMenuModuleDao.getIdBymenuName(menuName);
	}

	@Override
	public List<SysMenuModuleBean> getSysMenuTreeValInSysrole() {
		return sysMenuModuleDao.getSysMenuTreeValInSysrole();
	}

	@Override
	public String getparentMenuIDsByMenuID(int menuId) {
		logger.info("service层获得所有上级菜单id："+sysMenuModuleDao.getparentMenuIDsByMenuID(menuId));
		return sysMenuModuleDao.getparentMenuIDsByMenuID(menuId);
	}

	@Override
	public boolean getSysRoleMenusByMenuID(int menuId) {
		return sysMenuModuleDao.getSysRoleMenusByMenuID(menuId);
	}


}
