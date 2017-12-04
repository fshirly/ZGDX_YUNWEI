package com.fable.insightview.platform.demo.mapper;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.demo.entity.TestUser;

public interface TestUserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
    
    
    List<TestUser> selectUserInfo(Page<TestUser> page);
    
    List<TestUser> selectUserByName(String userAccount);
    
    int selectCount(TestUser user);
    
    int getMaxId();
    
    List<TestUser> selectByConditions(Page<TestUser> page);
}