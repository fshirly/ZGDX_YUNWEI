package com.fable.insightview.platform.util;

import com.fable.insightview.platform.common.util.StringUtil;

/**
 * @author zhouwei 单位机构树等
 */
public enum SysOrgTreeEnum {

	// 单位机构
	ORG(Constants.SOURCE_TYPE.ORG, Constants.SYS_ORG_TYPE.ORG),

	// 部门
	DEPARTMENT(Constants.SOURCE_TYPE.DEPARTMENT, Constants.SYS_ORG_TYPE.DEPARTMENT),

	// 岗位
	POST(Constants.SOURCE_TYPE.POST, Constants.SYS_ORG_TYPE.POST),

	// 人员带岗位
	USER(Constants.SOURCE_TYPE.USER, Constants.SYS_ORG_TYPE.USER),

	// 人员不带岗位
	USER_NO_POST(Constants.SOURCE_TYPE.USER_NO_POST, Constants.SYS_ORG_TYPE.USER_NO_POST),

	// 角色
	ROLE(Constants.SOURCE_TYPE.ROLE, Constants.SYS_ORG_TYPE.ROLE),

	//角色人员
	ROLEUSER(Constants.SOURCE_TYPE.ROLE_USER, Constants.SYS_ORG_TYPE.ROLE_USER);

	private String name;

	private String value;

	SysOrgTreeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static String getValueByName(String name) {
		for (SysOrgTreeEnum operator : SysOrgTreeEnum.class.getEnumConstants()) {
			if (StringUtil.equals(name, operator.getName())) {
				return operator.getValue();
			}
		}
		return null;
	}
}
