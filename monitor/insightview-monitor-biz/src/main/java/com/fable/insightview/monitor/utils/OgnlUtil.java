package com.fable.insightview.monitor.utils;

import java.util.HashMap;
import java.util.Map;

import ognl.MethodFailedException;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.collections.map.LRUMap;

import com.fable.insightview.monitor.dispatcher.exception.SystemException;

public class OgnlUtil {
	static LRUMap exprCache = new LRUMap(1024);
	public static final String parseText(String in, Object root) {
		return parseText(in, null, root);
	}
	
	public static final String parseText(String in, Map context, Object root) {
		if(in == null) return null;
		
		if(context == null)
			context = Ognl.createDefaultContext(null);
		
        StringBuffer buffer = new StringBuffer(in);
        for (int pos = buffer.indexOf("$"); pos != -1;
                       pos = buffer.indexOf("$", pos + 3)) {
            int e = buffer.indexOf("}", pos);
            if (e == -1) {
                return buffer.toString();
            }
            String name = buffer.substring(pos+2, e);
            try {
            	Object value = eval(name, context, root, (Class)null);
//            	if(value instanceof Number) {
//            		value = StringUtil.getDecimalFormatter().format(value);
//            	}
				if (value != null) {
				    buffer.replace(pos, e + 1, value.toString());
				}
			} catch (Exception e1) {
				buffer.replace(pos, e + 1, "");
			} 
        }
        return buffer.toString();
    }
	
	public  static Object parseExpression(String expr) {
		try {
			Object r = exprCache.get(expr);
			if(r == null) {
				r = Ognl.parseExpression(expr);
				exprCache.put(expr, r);
			}
			return r;
		} catch (OgnlException e) {
			throw new SystemException(e);
		}
	}
	
	public static Object eval(String ognl, Map<String, Object> args, Object root) {
		return eval(ognl, args, root, null);
	}
	
	public static <T> T eval(String expression, Map<String, Object> params, Object root, 
			Class<T> returnType) {
		Object expr = parseExpression(expression);
    	Map ognlCtx = Ognl.createDefaultContext(null);
		if(params != null)
			ognlCtx.putAll(params);
    	try {
			return (T)Ognl.getValue(expr, ognlCtx, root, returnType);
    	} catch (MethodFailedException e) {
    		throw new SystemException(e.getReason());
		} catch (OgnlException e) {
			throw new SystemException(e);
		}
	}
	
	public static void main(String[] args){
		Map user = new HashMap<String, Object>();
		user.put("name", "韩露");
		user.put("age", 21);

		Map alarm = new HashMap<String, Object>();
		alarm.put("title", "192.168.1.1down了");
		
		//System.out.print(OgnlUtil.eval("#a + #b + c + d", user, root));
		
		System.out.print(parseText("尊敬的${#user.name}你好，${#alarm.title}", user, alarm));
	}
	
}
