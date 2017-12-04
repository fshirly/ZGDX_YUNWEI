package com.fable.insightview.platform.serviceSerialNum.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.serviceSerialNum.entity.ServiceNumRule;
import com.fable.insightview.platform.serviceSerialNum.mapper.ServiceNumRuleMapper;
import com.fable.insightview.platform.serviceSerialNum.service.IServiceNumRuleService;

/**
 * 业务编码（业务流水号）的生成规则服务类
 * 
 * @author maow
 * 
 */
@Service("serviceNumRuleService")
public class ServiceNumRuleServiceImpl implements IServiceNumRuleService {
	// 默认最终业务流水号格式定义
	private static String DEFAULT_SERVICE_RULE_NUM_PATTERN = "${Prefix}${TimePattern}${SerialNum}";
	// 默认序列号格式,4-表示0000-9999,
	private static String DEFAULT_SERIAL_NUM_RULE = "4";

	@Autowired
	private ServiceNumRuleMapper numRuleMapper;
	
	@Override
	public String generateServiceNumber(Integer numRuleId) {
		if (null == numRuleId) {
			return "";
		}
		String serviceNum = "";
		String prefix = null;
		String serialTime = null;
		String serialNum = null;
		ServiceNumRule ruleBean = numRuleMapper.getNumRuleInfo(numRuleId);
		if (null != ruleBean) {
			prefix = ruleBean.getPerfix();
			
			SimpleDateFormat fmt = new SimpleDateFormat(ruleBean.getTimePattern());
			Date date = new Date();
			serialTime = fmt.format(date);
			
			if ("day".equals(ruleBean.getSerialPattern())) {
				SimpleDateFormat dayFmt = new SimpleDateFormat("yyyyMMdd");
				if (null != ruleBean.getSerialTime() && dayFmt.format(date).equals(dayFmt.format(ruleBean.getSerialTime()))) {
					// 如果今天和表中记录的是同一天
					if (null != ruleBean.getSerialNum()) {
						// 如果表中流水号不为空，自增1
						serialNum = increase(ruleBean.getSerialNum(), ruleBean.getSerialNumRule());
					} else {
						// 如果表中流水号为空，说明当前是今天的第一次生成流水号
						serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
					}
				} else {
					// 如果今天和表中记录的不是同一天，说明当前是今天的第一次生成流水号
					serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
				}
			} else if ("month".equals(ruleBean.getSerialPattern())) {
				SimpleDateFormat monthFmt = new SimpleDateFormat("yyyyMM");
				if (null != ruleBean.getSerialTime() && monthFmt.format(date).equals(monthFmt.format(ruleBean.getSerialTime()))) {
					// 如果本月和表中记录的是同一个月
					if (null != ruleBean.getSerialNum()) {
						// 如果表中流水号不为空，自增1
						serialNum = increase(ruleBean.getSerialNum(), ruleBean.getSerialNumRule());
					} else {
						// 如果表中流水号为空，说明当前是本月的第一次生成流水号
						serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
					}
				} else {
					// 如果本月和表中记录的不是同一个月，说明当前是本月的第一次生成流水号
					serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
				}
			} else if ("year".equals(ruleBean.getSerialPattern())) {
				SimpleDateFormat yearFmt = new SimpleDateFormat("yyyy");
				if (null != ruleBean.getSerialTime() && yearFmt.format(date).equals(yearFmt.format(ruleBean.getSerialTime()))) {
					// 如果今年和表中记录的是同一年
					if (null != ruleBean.getSerialNum()) {
						// 如果表中流水号不为空，自增1
						serialNum = increase(ruleBean.getSerialNum(), ruleBean.getSerialNumRule());
					} else {
						// 如果表中流水号为空，说明当前是今年的第一次生成流水号
						serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
					}
				} else {
					// 如果今年和表中记录的是同一年，说明当前是今年的第一次生成流水号
					serialNum = this.getFirstSerialNum(ruleBean.getSerialNumRule());
				}
			}
			
			// 组合成业务编号（流水号）
			serviceNum = this.printServiceNum(prefix, serialTime, serialNum, ruleBean.getServiceNumRulePattern());
			
			// 更新业务编号生成表
			ruleBean.setSerialTime(date);
			ruleBean.setSerialNum(serialNum);
			numRuleMapper.updateNumRuleInfo(ruleBean);
		} else {
			return "";
		}
		
		return serviceNum;
	}
	
	private String printServiceNum(String prefix, String serialTime,
			String serialNum, String serialPattern) {
		StringBuffer sb = new StringBuffer();
		// 占位符的正则支持属性，匹配${xxx.xxx1},如${obj1.field1}
		String regexStr = "\\$\\{(.+?)}";
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(serialPattern);
		String newVal = null;
		Map<String, String> servNumData = new HashMap<String, String>();
		servNumData.put("Prefix", prefix);
		servNumData.put("TimePattern", serialTime);
		servNumData.put("SerialNum", serialNum);
		while(matcher.find()) {
			newVal = servNumData.get(matcher.group(1));
			if(null == newVal) {
				// 没有文本需要替换
				newVal = "";
			}
			else {
				// 处理替换的文本中包含特殊字符$
				newVal = newVal.replaceAll("\\$", "\\\\\\$"); 
			}
			matcher.appendReplacement(sb, newVal);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 获取序列号的初始值
	 * @param serialNumRule 序列号格式定义，4-表示"0001"~"9999"，3-表示"0001"~"9999"
	 * @return
	 */
	public String getFirstSerialNum(String serialNumRule) {
		if("4".equals(serialNumRule.trim())) {
			return "0001";
		}
		else if ("3".equals(serialNumRule.trim())) {
			return "001";
		}
		return "0001";
	}

	/**
	 * 序列号自增
	 * @param numRule 序列号格式定义，4-表示"0001"~"9999"，3-表示"0001"~"9999"
	 * @return
	 */
	public synchronized String increase(String value, String numRule) {
		int n = Integer.parseInt(value) + 1;  
		String newValue = String.valueOf(n);  
		// 补零位数
		int len = value.length() - newValue.length();  
		for(int i = 0; i < len; i++){  
			newValue = "0" + newValue;  
		}
		
		return newValue;
	}
	
	
}
