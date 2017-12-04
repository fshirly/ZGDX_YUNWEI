package com.fable.insightview.platform.message.entity;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ognl.MethodFailedException;
import ognl.Ognl;
import ognl.OgnlException;

public class PfOgnlUtil {
	private Logger logger = LoggerFactory.getLogger(PfOgnlUtil.class);
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
			throw new RuntimeException(e);
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
    		throw new RuntimeException(e.getReason());
		} catch (OgnlException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param in
	 * @param context
	 * @param root
	 * @return
	 */
	public static final String parseTextLose(String in, Map context, Object root) {
		if(in == null) return null;
		
		if(context == null)
			context = Ognl.createDefaultContext(null);
		
        StringBuffer buffer = new StringBuffer(in);
        for (int pos = buffer.indexOf("$"); pos != -1;
                       pos = buffer.indexOf("$", pos)) {
            int e = buffer.indexOf("}", pos);
            if (e == -1) {
                return buffer.toString();
            }
            String name = buffer.substring(pos+2, e);
            	Object value = eval(name, context, root, (Class)null);
				if (value != null) {
				    buffer.replace(pos, e + 1, value.toString());
				}else{
					buffer.replace(pos, e + 1, "");
				}
			
        }
        return buffer.toString();
    }
	
}
