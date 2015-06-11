package com.pchome.akbpfp.struts2.ajax.ad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.opensymphony.xwork2.inject.util.Strings;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.soft.depot.utils.HttpUtil;
public class AdUtilAjax extends BaseCookieAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5195311542239203862L;
	
	// get data
	private String url;
	private String q;
	
	// return data
	private InputStream msg;
	private int urlState;
	private String result;
	
	public String checkAdUrl() throws Exception{
	    	log.info("checkAdUrl");
		Boolean noError = false;
		
		//檢查url 是否危險網址API
		HttpGet request = new HttpGet();
		request.setURI(new URI("http://pseapi.mypchome.com.tw/api/security/safeBrowsingLookup.html?url="+url));
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		InputStream urlContent = response.getEntity().getContent();
		BufferedReader buf = new BufferedReader(new InputStreamReader(urlContent,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String jsonStr;
		while (true) {
		    jsonStr = buf.readLine();
		    if (jsonStr == null)
			break;
		    sb.append(jsonStr);
		}
		buf.close();
		urlContent.close();
		log.info(">>>>>>>>>>>>>>"+sb);
		JSONObject jsonObj = new JSONObject(sb.toString());
		JSONObject jsonObjMsg = new JSONObject(jsonObj.get("success").toString());
		if(jsonObjMsg.get("msg").toString().equals("malware")){
		    this.msg = new ByteArrayInputStream(jsonObjMsg.get("msg").toString().getBytes());
		    return SUCCESS;
		}else{
			try {
			    if(StringUtils.isEmpty(url) || url.length()<1){
				return null;
			    }else{
				if(url.indexOf("http")<0){
				    url= "http://"+url;
				}
			    }
			    url = HttpUtil.getInstance().getRealUrl(url);
			    log.info("url = " + url);
			    urlState = HttpUtil.getInstance().getStatusCode(url);
			    msg = new ByteArrayInputStream("".getBytes());
			    log.info("urlState>>>"+urlState);
			    if(urlState >= 200 && urlState < 300){
				noError = true;
				log.info("urlState = " + urlState);
			    }
			
    			} catch(Exception ex) {
    			    System.out.println("Exception(AdUtilAjax.checkUrl) : " + ex.toString());
    			}
			if(url != null && !url.trim().equals("")) {
			    log.info("noError = " + noError);
			    msg = new ByteArrayInputStream(noError.toString().getBytes());
			} else {
			msg = new ByteArrayInputStream("".getBytes());
			}
		}
		return SUCCESS;
	}

	public String getSuggestKW() throws Exception{
		log.info(">>>getSuggestKW");
		try {
			if(q != null && !q.trim().equals("")) {
				String kwApi = "http://search.pchome.com.tw/suggest/keyword/search.html?q=" +java.net.URLEncoder.encode(q, "UTF-8");
				log.info("kwApi = " + kwApi);
				result = HttpUtil.getInstance().getResult(kwApi, "UTF-8");
				log.info("result = " + result);
			}
		} catch(Exception ex) {
		    log.info("Exception(AdUtilAjax.getSuggestKW) : " + ex.toString());
		}
		msg = new ByteArrayInputStream(result.getBytes());
		return SUCCESS;
	}

	// get data
	public void setUrl(String url) {
		this.url = url;
	}

	public void setQ(String q) {
		this.q = q;
	}

	// return data
	public InputStream getMsg() {
		return msg;
	}

	public int getUrlState() {
		return urlState;
	}

	public String getResult() {
		return result;
	}

	public void setMsg(InputStream msg) {
	    this.msg = msg;
	}


	
}
