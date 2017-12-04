package com.fable.insightview.platform.aaService.mapper;


import java.util.Map;

public interface KsccMapper {

     void deleteRole();

     void deleteMenu();

     void insertMenuOfHospital(Map<String, Object> param);

     void insertRoleMenu(Map<String, Object> param);
}
