package com.pchome.akbpfp.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.Parameter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ReplaceValuesInterceptor  extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = LogManager.getRootLogger();
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		Map<String, Parameter> newParams = null;
		Map<String, String[]> data = request.getParameterMap();
		if(data.size() > 0) {
			newParams = new HashMap<String,Parameter>();
		}
		for (Entry<String, String[]> entry : data.entrySet()) {
			String[] valueList = (String[]) entry.getValue();
			ArrayList<String> valueArrayList = new ArrayList<String>();
			for(String value: valueList){
				value = value.replaceAll("<", "&lt;");
				value = value.replaceAll(">", "&gt;");
				valueArrayList.add(value);
			}
			if(valueList.length == 0) {
				newParams.put(entry.getKey(), new Parameter.Request(entry.getKey(), ""));
			}else if(valueList.length == 1){
				newParams.put(entry.getKey(), new Parameter.Request(entry.getKey(), valueList[0]));
			}else {
				newParams.put(entry.getKey(), new Parameter.Request(entry.getKey(), valueArrayList.stream().toArray()));
			}
				
		}
		HttpParameters httpParameters = invocation.getInvocationContext().getParameters();
		if(newParams != null) {
			httpParameters.appendAll(newParams);
			ActionContext actionContext = invocation.getInvocationContext();
			actionContext.setParameters(httpParameters);
		}
		return invocation.invoke();
	}

}
