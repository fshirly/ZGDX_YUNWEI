package com.fable.insightview.platform.aaService.service;

import com.fable.insightview.platform.aaService.mapper.KsccMapper;
import com.fable.insightview.platform.aaServiceForKscc.mapper.KsccSchemeMapper;
import com.fable.insightview.platform.aaServiceForKscc.service.WebInvoke;
import com.fable.insightview.platform.common.util.MultipleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :Hairui
 * Date :2017/11/16
 * Time :9:21
 * </p>
 * <p>
 * Department :
 * </p>
 * <p> Copyright : 江苏飞博软件股份有限公司 </p>
 */
@Service
public class KsccService{

    @Autowired
    KsccMapper mapper;

    public void deleteMenu(){
        mapper.deleteRole();
        mapper.deleteMenu();
    }

    public void insertMenu(){
        int menuId=100000;
        int showOrder=1;
        int id=114000;
        Map<String, Object> param = new HashMap<>();
        for(Map<String,Object> map: WebInvoke.getHospitalCache().get("list")){
            param.put("menuId",menuId++);
            param.put("showOrder",showOrder++);
            param.put("menuName", map.get("hospitalName"));
            param.put("hospitalId", map.get("id"));
            param.put("id",id++);
            param.put("roleId", 1);
            mapper.insertMenuOfHospital(param);
            mapper.insertRoleMenu(param);
        }
    }

}
