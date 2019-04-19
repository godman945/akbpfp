package com.pchome.soft.depot.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UrlToJsonUtile {
	Logger log = LogManager.getRootLogger();
	
	private static final UrlToJsonUtile urlToJsonUtile = new UrlToJsonUtile();
	
	public static UrlToJsonUtile getInstance() {
		return urlToJsonUtile;
	}
	
	public String readJsonUrl(String urlStr) throws Exception {
		
		BufferedReader reader = null;
		URL url = new URL(urlStr);
		reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		
		StringBuffer buffer = new StringBuffer();
		int read;
		char[] chars = new char[1024];
		
		while((read = reader.read(chars)) != -1){
			buffer.append(chars,0,read);
		}
		
		return buffer.toString();
	}
}
