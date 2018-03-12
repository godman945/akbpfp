package com.pchome.akbpfp.db.service.admanyurlsearch;

import org.json.JSONException;

import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;


public interface IPfpAdManyURLSearchService extends IBaseService<PfpAdManyURLVO,String>{

	/**
	 * 從廣告爬蟲api取得資料，及網址相關檢查，取得資料存入vo
	 * @param vo
	 */
	public void getAdCrawlerAPIData(PfpAdManyURLVO vo);

	/**
	 * 檢查api取得資料是否已存在redis，已存在不動作，未存在新增進去
	 * @param vo
	 */
	public void checkRedisData(PfpAdManyURLVO vo);

	/**
	 * 依照每頁筆數，及目前頁數取得相對應資料
	 * @param vo
	 * @throws JSONException 
	 */
	public void getRedisLimitData(PfpAdManyURLVO vo) throws JSONException;

	/**
	 * 取得redis上，輸入的客編搜尋的網址資料
	 * @param vo
	 * @throws JSONException 
	 */
	public void getRedisURLData(PfpAdManyURLVO vo) throws JSONException;
	
}
