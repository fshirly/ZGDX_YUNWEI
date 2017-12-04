package com.fable.insightview.platform.demo.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("testUser")
public class TestUser {
	@NumberGenerator(name = "userid")
    private Integer userid;

    private String useraccount;

    private String username;

    private String userpassword;

    private String mobilephone;

    private String email;

    private String telephone;

    private Integer isautolock;

    private Integer status;

    private Integer usertype;

    private Date createtime;

    private Date lastmodifytime;

    private Date lockedtime;

    private String lockedreason;

    private Byte state;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount == null ? null : useraccount.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getIsautolock() {
        return isautolock;
    }

    public void setIsautolock(Integer isautolock) {
        this.isautolock = isautolock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(Date lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public Date getLockedtime() {
        return lockedtime;
    }

    public void setLockedtime(Date lockedtime) {
        this.lockedtime = lockedtime;
    }

    public String getLockedreason() {
        return lockedreason;
    }

    public void setLockedreason(String lockedreason) {
        this.lockedreason = lockedreason == null ? null : lockedreason.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}