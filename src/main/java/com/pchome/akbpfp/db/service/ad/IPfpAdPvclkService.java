package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpAdPvclk;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.AdLayerVO;
import com.pchome.enumerate.ad.EnumAdLayer;


public interface IPfpAdPvclkService extends IBaseService<PfpAdPvclk,String>{
	
	public List<Float> totalPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception;
	
	public Map<Date,Float> chartPvclkCost(String customerInfoId, String chartType, Date startDate, Date endDate) throws Exception;
	
	/**
	 * 查詢帳戶總覽中廣告成效摘要
	 * 依帳戶編號、層級、時間查詢廣告成效
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AdLayerVO> detailResultCost(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate, int page, int pageSize) throws Exception;
		
	/**
	 * 查詢帳戶總覽中全部的廣告成效摘要
	 * 依帳戶編號、層級、時間查詢廣告成效
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AdLayerVO> detailResultCost(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢帳戶總覽中廣告成效筆數
	 * 依帳戶編號、層級、時間查詢廣告成效筆數
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public long detailResultCount(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢帳戶總覽中廣告成效筆數
	 * 依帳戶編號、層級、時間查詢廣告成效筆數
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long detailResultCount(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate, int page, int pageSize) throws Exception;
}
