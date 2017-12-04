package com.fable.insightview.platform.demo.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.demo.entity.TestUser;



public interface ITestUserService {
	List<TestUser> getUser(String userName);
	void addUser(TestUser user);
	List<TestUser> getAllUsers(Page<TestUser> page);
	int getTotalCount(TestUser user);
	boolean delUser(TestUser user);
	List<TestUser> getAllUsersByConditions(Page<TestUser> page);
	TestUser getUserByUserId(int userId);
	boolean updateUser(TestUser user);
}
