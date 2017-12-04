package com.fable.insightview.platform.importdata.example;

import org.hibernate.validator.constraints.NotBlank;

import com.fable.insightview.platform.importdata.resolver.Mapping;
import com.fable.insightview.platform.importdata.resolver.metadata.Processor;

public class ExampleChild {
	
	@Mapping(columnName="country")
	@NotBlank(message = "国家名称不能为空!") 
	@Processor(methodName="countryHandle")
	private String country;
	
	@Mapping(columnName="city")
	private String city;

	public final String getCity() {
		return city;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final String getCountry() {
		return country;
	}

	public final void setCountry(String country) {
		this.country = country;
	}
	
	public String countryHandle(String country, ExampleChild entity){
		entity.setCity(entity.getCity()+"///////");
		return country + "???";
	}
}
