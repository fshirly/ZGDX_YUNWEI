package com.fable.insightview.platform.importdata.example;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fable.insightview.platform.importdata.resolver.Mapping;
import com.fable.insightview.platform.importdata.resolver.metadata.Processor;
import com.fable.insightview.platform.importdata.resolver.Reloadable;

@Reloadable
@Processor
public class ExampleEntity {
	
	@Mapping(columnName="A")
	@NotBlank(message = "姓名不能为空!")  
    @Size(min = 2, max = 4, message = "姓名长度必须在{min}和{max}之间") 
    @Pattern(regexp = "[\u4e00-\u9fa5]+", message = "姓名只能输入是中文字符!")
    @Processor(methodName="nameHandle")
	private String name;
	
	@Mapping(columnName="B")
	@Min(value=22,message="年龄不能小于22岁")    
	@Max(value=60,message="年龄不能大于60岁") 
	@Processor
	private int age;
	
	private String nickName;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public final String getNickName() {
		return nickName;
	}

	public final void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Object nameHandle(String name){
		return "姓名：" + name;
	}
	
	public Object ageHandle(int age, ExampleEntity entity){
		if(age<18){
			entity.setNickName("小孩");
		}else if(age<30){
			entity.setNickName("小伙子");
		}else{
			entity.setNickName("老头");
		}
		return age;
	}
	
	public Object exampleEntityHandle(ExampleEntity entity){
		entity.setName(entity.getName()+"!!");
		return entity;
	}
}
