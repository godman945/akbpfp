package com.pchome.akbpfp.api;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisAPI {

	protected Logger log = LogManager.getRootLogger();
	
	private String environment;
	private String redisServer;
	private int redisPort;
	private int redisTimeout; //連線逾時
	private int redisMaxRedirects; // 失敗重試次數
	private int maxWait;
	private int maxTotal;
	private int maxIdle;
	private boolean testOnBorrow;
	
	//兩個變數放在此部分，為了讓記憶體位置不會改變
	JedisPoolConfig jedisConfig = new JedisPoolConfig();
	Set<HostAndPort> hostAndPortSet = new HashSet<>();
	
	JedisPoolConfig jedisPoolConfig() {
		// 空閒連接實例的最大數目，為負值時沒有限制。Idle的實例在使用前，通常會通過
		jedisConfig.setMaxIdle(maxIdle);
		jedisConfig.setTestOnBorrow(testOnBorrow);
		// 等待可用連接的最大數目，單位毫秒（million seconds)
		jedisConfig.setMaxWaitMillis(maxWait);
		jedisConfig.setMaxTotal(maxTotal);
		// 逐出掃描的時間間隔(毫秒) 如果為負數,則不運行逐出線程, 默認-1
		jedisConfig.setTimeBetweenEvictionRunsMillis(-1);
		// 在空閒時檢查有效性, 默認false
		jedisConfig.setTestWhileIdle(false);
		// 設置的逐出策略類名, 默認DefaultEvictionPolicy(當連接超過最大空閒時間,或連接數超過最大空閒連接數)
		jedisConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		return jedisConfig;
	}
	
	/**
	 * 設定正式環境IP及PORT
	 * 群集模式
	 * @return
	 */
	Set<HostAndPort> setJedisServerConfig() {
		String[] serverArray = redisServer.split(",");
		for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            hostAndPortSet.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
		return hostAndPortSet;
	}
	
	
	/**
	 * 寫值到redis,預設有效時間為1天
	 * @param key
	 * @param val
	 * @return 成功回傳OK
	 */
	public String setRedisDataDefaultTimeout(String key, String val) {
		return setRedisData(key, 24 * 60 * 60, val);
	}
	
	/**
	 * 寫入redis資料
	 * @param key
	 * @param time 有效時間(秒)
	 * @param val
	 * @return 成功回傳OK
	 */
	public String setRedisData(String key, int time, String val) {
		try {
			JedisCluster jedis = new JedisCluster(setJedisServerConfig(), redisTimeout, redisMaxRedirects, jedisPoolConfig());
			return jedis.setex(key, time, val);
		} catch (Exception e) {
			System.out.println("寫入redis資料錯誤:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 取得redis資料
	 * @param key
	 * @return
	 */
	public String getRedisData(String key) {
		JedisCluster jedis = new JedisCluster(setJedisServerConfig(), redisTimeout, redisMaxRedirects, jedisPoolConfig());
		return jedis.get(key);
	}
	
	/**
	 * 刪除redis資料
	 * @param key
	 * @return
	 */
	public Long delRedisData(String key) {
		try {
			JedisCluster jedis = new JedisCluster(setJedisServerConfig(), redisTimeout, redisMaxRedirects, jedisPoolConfig());
			return jedis.del(key);
		} catch (Exception e) {
			System.out.println("刪除redis資料錯誤:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 測試連線
	 * @param ip
	 */
	private static void testLink(String ip) {
		// 連接Redis服務
		Jedis jedis = new Jedis(ip);
		// 查看Redis服務是否運行
		System.out.println(ip + " 正在運行:" + jedis.ping());
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getRedisTimeout() {
		return redisTimeout;
	}

	public void setRedisTimeout(int redisTimeout) {
		this.redisTimeout = redisTimeout;
	}

	public int getRedisMaxRedirects() {
		return redisMaxRedirects;
	}

	public void setRedisMaxRedirects(int redisMaxRedirects) {
		this.redisMaxRedirects = redisMaxRedirects;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getRedisServer() {
		return redisServer;
	}

	public void setRedisServer(String redisServer) {
		this.redisServer = redisServer;
	}
}