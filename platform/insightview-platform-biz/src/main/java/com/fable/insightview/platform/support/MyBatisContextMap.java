package com.fable.insightview.platform.support;

import java.util.HashMap;

import org.apache.ibatis.reflection.MetaObject;

public class MyBatisContextMap extends HashMap<String, Object>{
	
    private static final long serialVersionUID = 2977601501966151582L;

    private MetaObject parameterMetaObject;
    
    public MetaObject getParameterMetaObject() {
		return parameterMetaObject;
	}

	public void setParameterMetaObject(MetaObject parameterMetaObject) {
		this.parameterMetaObject = parameterMetaObject;
	}

	@Override
    public Object put(String key, Object value) {
      return super.put(key, value);
    }
    
    @Override
    public Object get(Object key) {
      String strKey = (String) key;
      if (super.containsKey(strKey)) {
        return super.get(strKey);
      }

      if (parameterMetaObject != null) {
        Object object = parameterMetaObject.getValue(strKey);
        return object;
      }

      return null;
    }
}
