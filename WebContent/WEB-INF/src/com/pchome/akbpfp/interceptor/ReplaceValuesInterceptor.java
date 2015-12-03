package com.pchome.akbpfp.interceptor;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ReplaceValuesInterceptor  extends AbstractInterceptor {

	protected final Log log = LogFactory.getLog(this.getClass());
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Map<String,Object> map =invocation.getInvocationContext().getParameters();
		for(String key:map.keySet()){
			String[] valueList = (String[]) map.get(key);
			ArrayList<String> valueArrayList = new ArrayList<String>();
			for(String value: valueList){
				//value = value.replaceAll("&", "&amp;");
				value = value.replaceAll("<", "&lt;");
				value = value.replaceAll(">", "&gt;");
				//value = value.replaceAll("'", "&apos;");
				//value = value.replaceAll("\"", "&quot;");
				valueArrayList.add(value);
			}
			
			map.put(key, valueArrayList.toArray(new String[valueArrayList.size()]));
			
			log.info("-----------------" + key + ":" + ((String[]) map.get(key))[0]);
		}
		ActionContext actionContext = invocation.getInvocationContext();
		actionContext.setParameters(map);
		
		
		return invocation.invoke();
	}

}
