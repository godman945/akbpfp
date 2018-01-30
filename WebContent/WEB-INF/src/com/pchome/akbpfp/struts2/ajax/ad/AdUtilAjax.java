package com.pchome.akbpfp.struts2.ajax.ad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdVideoCondition;
import com.pchome.soft.depot.utils.HttpUtil;
public class AdUtilAjax extends BaseCookieAction{

	private static final long serialVersionUID = -5195311542239203862L;
	// get data
	private String url;
	private String q;
	// return data
	private InputStream msg;
	private int urlState;
	private String result;
	private String akbPfpServer;
	private String adVideoUrl;
	
	public String checkAdUrl() throws Exception{
	    	log.info("checkAdUrl");
	    
		Boolean noError = false;
		
		//檢查url 是否危險網址API
		HttpGet request = new HttpGet();
		URL thisUrl = new URL("http://pseapi.mypchome.com.tw/api/security/safeBrowsingLookup.html?url="+url);
		URI uri = new URI(thisUrl.getProtocol(), thisUrl.getHost(), thisUrl.getPath(), thisUrl.getQuery(), null);
		request.setURI(uri);
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
			    
			    // www.mjholly.com pass
			    String passUrl = url;
			    if(url.indexOf("www.mjholly.com") >= 0){
			    	passUrl = passUrl.replaceAll("http://", "");
			    	passUrl = passUrl.replaceAll("https://", "");
			    	passUrl = passUrl.substring(0, passUrl.indexOf(".com") + 4);
			    }
			    
			    if(url.indexOf(akbPfpServer) == 0){
			    //if(akbPfpServer.equals(url) || (akbPfpServer.substring(0, akbPfpServer.length() -1).equals(url))){
			    	urlState = 200;
			    } else if("www.mjholly.com".equals(passUrl)){
			    	urlState = 200;
			    } else {
			    	urlState = HttpUtil.getInstance().getStatusCode(url);
			    	msg = new ByteArrayInputStream("".getBytes());	
			    }
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
		HttpGet request = new HttpGet(new URI("http://search.pchome.com.tw/suggest/keyword/search.html?q="+java.net.URLDecoder.decode(q, "UTF-8")));
		//request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:28.0) Gecko/20100101 Firefox/28.0");
		HttpClient client = new DefaultHttpClient();
	    HttpResponse response = client.execute(request);
	    String theString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
	    this.result = theString;
	    return SUCCESS;
	}
	
	/**
	 * 檢查影音廣告網址
	 * 1.根據回傳時間格式轉換秒數
	 * 2.影片格式目前開放30秒以下才可通過
	 * */
	public String chkVideoUrl() throws Exception{
		
		//取得影片播放網址
		Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", "youtube-dl -f 18 -g --get-title --get-format " + adVideoUrl });
		String resultStr = IOUtils.toString(process.getInputStream(),"UTF-8").trim();
		log.info(">>>>> resultStr:"+resultStr);
		
		JSONObject json = new JSONObject();
		if(resultStr.indexOf("ERROR") >= 0 || process.waitFor() == 1){
			json.put("result", false);
			json.put("msg", "錯誤的影片連結");
			this.result = json.toString();
			this.msg = new ByteArrayInputStream(json.toString().getBytes());
			log.error(">>>>>>"+result.toString());
			return SUCCESS;
		}
		
		int seconds = 0;
		String[] videoInfoArray = resultStr.split("&");
		List<String> info = Arrays.asList(videoInfoArray);
		for (String string : info) {
			if(string.indexOf("dur=") >= 0){
				videoInfoArray = string.split("=");
				System.out.println(string);
				seconds = (int)Math.floor(Double.parseDouble(videoInfoArray[1]));
				break;
			}
		}	

		if(seconds > EnumAdVideoCondition.AD_VIDEO_TOTAL_TIME.getValue()){
			json.put("result", false);
			json.put("msg", "影片長度不得超過30秒，請重新上傳30秒以內的影片。");
			this.result = json.toString();
			this.msg = new ByteArrayInputStream(json.toString().getBytes());
			return SUCCESS;
		}
		
		String adTitle = resultStr.substring(0,resultStr.indexOf("http"));
		String previewUrl = resultStr.substring(resultStr.indexOf("http"),resultStr.length());
		
		//判斷是否直立影片
		boolean verticalAd = false;
		String videoSize = resultStr.substring(resultStr.indexOf("18 - "),resultStr.indexOf(" (small);"));
		log.info(">>>>>>>>>>>alex verticalAd:"+videoSize);
		videoSize = videoSize.replace("18 - ", "");
		String [] videoSizeArray = videoSize.toString().split("x");
		if(Integer.parseInt(videoSizeArray[1]) > Integer.parseInt(videoSizeArray[0])){
			verticalAd = true;
		}
		
		json.put("result", true);
		json.put("videoTime", seconds);
		json.put("previewUrl", previewUrl);
		json.put("adTitle", adTitle);
		json.put("verticalAd", verticalAd);
		process.destroy();
		this.result = json.toString();
		this.msg = new ByteArrayInputStream(json.toString().getBytes());
		return SUCCESS;
	}
	
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public InputStream getMsg() {
		return msg;
	}

	public int getUrlState() {
		return urlState;
	}

	public String getResult() {
		return result;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public String getAdVideoUrl() {
		return adVideoUrl;
	}

	public void setAdVideoUrl(String adVideoUrl) {
		this.adVideoUrl = adVideoUrl;
	}

}
