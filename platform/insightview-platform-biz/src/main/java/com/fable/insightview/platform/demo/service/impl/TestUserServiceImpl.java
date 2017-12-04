package com.fable.insightview.platform.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.demo.mapper.TestUserMapper;
import com.fable.insightview.platform.demo.entity.TestUser;
import com.fable.insightview.platform.demo.service.ITestUserService;
import com.fable.insightview.platform.page.Page;

@Service
public class TestUserServiceImpl implements ITestUserService {
	@Autowired
	TestUserMapper testUserMapper;
	public List<TestUser> getUser(String userName) {
		List<TestUser> userlist=testUserMapper.selectUserByName(userName);
		return userlist;
	}

	public void addUser(TestUser user) {
//		int id = Integer.valueOf(String.valueOf(IDGeneratorFactory.getInstance().getGenerator(TestUser.class).generate())).intValue();
//		user.setUserid(id);
		user.setCreatetime(new Date());
		testUserMapper.insert(user);
	}

	public List<TestUser> getAllUsers(Page<TestUser> page) {
		List<TestUser> userList=testUserMapper.selectUserInfo(page);
		return userList;
	}

	public int getTotalCount(TestUser user) {
		return testUserMapper.selectCount(user);
	}

	public boolean delUser(TestUser user) {
		int i=testUserMapper.deleteByPrimaryKey(user.getUserid());
		if(i==1){
			return true;
		}else{
			return false;
		}
		
	}


	public List<TestUser> getAllUsersByConditions(Page<TestUser> page) {
		List<TestUser> list=testUserMapper.selectByConditions(page);
		return list;
	}
	
	public TestUser getUserByUserId(int userId) {
		return testUserMapper.selectByPrimaryKey(userId);
	}

	public boolean updateUser(TestUser user) {
		int i=testUserMapper.updateByPrimaryKeySelective(user);
		if(i==1){
			return true;
		}else{
			return false;
		} 
	}


}
