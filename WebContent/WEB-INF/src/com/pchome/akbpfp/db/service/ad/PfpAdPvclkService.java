package com.pchome.akbpfp.db.service.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdPvclkDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdPvclkDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdPvclk;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.AdLayerVO;
import com.pchome.enumerate.ad.EnumAdLayer;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdPvclkService extends BaseService<PfpAdPvclk,String> implements IPfpAdPvclkService{

	private IPfpAdActionDAO pfpAdActionDAO;
	private PfpAdGroupDAO pfpAdGroupDAO;
	private PfpAdKeywordDAO pfpAdKeywordDAO;
	private PfpAdDAO pfpAdDAO;

	public List<Float> totalPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception{

		List<Float> costs = new ArrayList<Float>();
		List<Object> objects = ((IPfpAdPvclkDAO)dao).totalPvclkCost(customerInfoId, startDate, endDate);

		for(Object object:objects){
			Object[] ob = (Object[])object;
			if(ob[0] != null ){	
				float pv = Float.parseFloat(ob[0].toString());
				float clk = Float.parseFloat(ob[1].toString());
				float clkCost = Float.parseFloat(ob[2].toString());
				float invalidClk = Float.parseFloat(ob[3].toString());
				float invalidClkCost = Float.parseFloat(ob[4].toString());
				
				clk = clk - invalidClk;
				clkCost = clkCost - invalidClkCost;
				
				costs.add(pv);
				costs.add(clk);
				costs.add(clkCost);
			} else {
				costs.add((float) 0);
				costs.add((float) 0);
				costs.add((float) 0);
			}
		}
		return costs;
	}
	
	public Map<Date,Float> chartPvclkCost(String customerInfoId, String chartType, Date startDate, Date endDate) throws Exception{
		Map<Date,Float> flashDataMap =  new HashMap<Date,Float>();
		List<Object> objects = ((IPfpAdPvclkDAO)dao).chartPvclkCost(customerInfoId, startDate, endDate);
		for(Object object:objects){
			Object[] ob = (Object[])object;
			if(ob[0] != null ){
				float pv = Integer.parseInt(ob[1].toString());
				float clk = Integer.parseInt(ob[2].toString());
				float clkCost = Float.parseFloat(ob[3].toString());
				float invalidClk = Integer.parseInt(ob[4].toString());
				float invalidClkCost = Float.parseFloat(ob[5].toString());

				clk = clk - invalidClk;
				clkCost = clkCost - invalidClkCost;

				float flashDataMapValue;
				if(chartType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())){
	    			flashDataMapValue = pv;	
	        		flashDataMap.put((Date)ob[0], flashDataMapValue);
	    		} else if(chartType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())){
	    			flashDataMapValue = clk;	
	        		flashDataMap.put((Date)ob[0], flashDataMapValue);
	    		} else if(chartType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())){
	    			flashDataMapValue = clkCost;	
	        		flashDataMap.put((Date)ob[0], flashDataMapValue);
	    		}
			}
		}
		return flashDataMap;
	}
	
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
	public List<AdLayerVO> detailResultCost(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		// 存放廣告成效資料
		List<AdLayerVO> adLayerVOs = new ArrayList<AdLayerVO>();

		// 查詢廣告總覽顯示的廣告成效資料
		List<Object> objects =((IPfpAdPvclkDAO)dao).getAdCost(customerInfoId, enumAdLayer.getType(), startDate, endDate, page, pageSize);

		if(objects != null && objects.size() > 0) {
			// 逐筆讀出本頁的關鍵字序號
			List<String> seqList = new ArrayList<String>();
			for(Object obj:objects) {
				Object[] result = (Object[])obj;
				seqList.add(result[0].toString());
			}
	
			// 依照讀出本頁的關鍵字序號，查詢關鍵字成效
			HashMap<String, Object> dataDetails = new HashMap<String, Object>();

			// 顯示廣告活動
	    	if(EnumAdLayer.AD_ACTION.getType().equals(enumAdLayer.getType())){
	    		// 查詢廣告成效的廣告活動資料
	    		dataDetails = pfpAdActionDAO.getPfpAdActionBySeqList(seqList);
	    	}
			// 顯示廣告分類
	    	if(EnumAdLayer.AD_GROUP.getType().equals(enumAdLayer.getType())){
	    		// 查詢廣告成效的廣告分類資料
	    		dataDetails = pfpAdGroupDAO.getPfpAdGroupBySeqList(seqList);
	    	}
	    	// 顯示關鍵字廣告
	    	if(EnumAdLayer.AD_KEYWORD.getType().equals(enumAdLayer.getType())){
	    		// 查詢廣告成效的廣告分類資料
	    		dataDetails = pfpAdKeywordDAO.getPfpAdKeywordBySeqList(seqList);
	    	}
	    	//顯示廣告明細
	    	if(EnumAdLayer.AD_AD.getType().equals(enumAdLayer.getType())){
	    		// 查詢廣告成效的廣告明細資料
	    		dataDetails = pfpAdDAO.getPfpAdBySeqList(seqList);
	    	}

			// 逐筆讀出廣告成效資料
			for(Object object:objects){
				Object[] ob = (Object[])object;
				if(ob[0] != null){
					AdLayerVO adLayerVO = new AdLayerVO();
					String seq = ob[0].toString();
					adLayerVO.setSeq(seq);

					int statusId = 4;	//預設廣告狀態為開啟
            		if(dataDetails != null) {
						// 顯示廣告活動
		            	if(EnumAdLayer.AD_ACTION.getType().equals(enumAdLayer.getType())){
		            		// 查詢廣告成效的廣告活動資料
	            			PfpAdAction pfpAdAction = (PfpAdAction)dataDetails.get(seq);
	        				// 設定廣告活動的名稱及狀態
	        				adLayerVO.setName(pfpAdAction.getAdActionName());
	        				statusId = pfpAdAction.getAdActionStatus();
		            	}
						// 顯示廣告分類
		            	if(EnumAdLayer.AD_GROUP.getType().equals(enumAdLayer.getType())){
		            		// 查詢廣告成效的廣告分類資料
		    				PfpAdGroup pfpAdGroup = (PfpAdGroup)dataDetails.get(seq);
	        				// 設定廣告活動的名稱及狀態
	        				adLayerVO.setName(pfpAdGroup.getAdGroupName());
	        				statusId = pfpAdGroup.getAdGroupStatus();
		            	}
		            	// 顯示關鍵字廣告
		            	if(EnumAdLayer.AD_KEYWORD.getType().equals(enumAdLayer.getType())){
		            		// 查詢廣告成效的關鍵字廣告資料
		            		PfpAdKeyword pfpAdKeyword = (PfpAdKeyword)dataDetails.get(seq);
		    				// 設定關鍵字廣告的名稱及狀態
		    				adLayerVO.setName(pfpAdKeyword.getAdKeyword());
		    				statusId = pfpAdKeyword.getAdKeywordStatus();
		            	}
		            	//顯示廣告明細
		            	if(EnumAdLayer.AD_AD.getType().equals(enumAdLayer.getType())){
		            		// 查詢廣告成效的廣告明細資料
		            		PfpAd pfpAd = (PfpAd)dataDetails.get(seq);
		    				// 設定廣告明細的名稱及狀態
		    				adLayerVO.setName(pfpAd.getAdSeq());
		    				statusId = pfpAd.getAdStatus();
		    				adLayerVO.setTemplateNo(pfpAd.getTemplateProductSeq());
		    				adLayerVO.setAdStyle(pfpAd.getAdStyle());
		            	}
					}
					adLayerVO.setStatusId(statusId);

					// 設定狀態名稱
					for(EnumStatus status:EnumStatus.values()){
						if(statusId == status.getStatusId()){
							adLayerVO.setStatusChName(status.getStatusRemark());
						}
					}

					// 顯示廣告成效資料
					int pv = Integer.parseInt(ob[1].toString());
					float realClk = Float.parseFloat(ob[2].toString());
					float realCost = Float.parseFloat(ob[3].toString());				
					float clkRate = 0;
					float clkCostAvg = 0;
					float invalidClk = Float.parseFloat(ob[4].toString());
					//float invalidClkCost = Float.parseFloat(ob[5].toString());

					//float realClk = ((float)clk - invalidClk);
					//float realClk = (float)clk;
					//float realCost = clkCost - invalidClkCost;
					//float realCost = Float.parseFloat(ob[5].toString());

					// 計算點擊率
					if(realClk > 0 || pv > 0){
						clkRate = realClk / (float)pv*100;
					}

					// 計算平均點擊費用
					if(realCost > 0 || realClk > 0){
						clkCostAvg = realCost / realClk;
					}

					adLayerVO.setPv(pv);
					adLayerVO.setClk(realClk);
					adLayerVO.setClkCost(realCost);
					adLayerVO.setClkRate(clkRate);
					adLayerVO.setAvgClkCost(clkCostAvg);
					adLayerVO.setInvalidClk(invalidClk);

					adLayerVOs.add(adLayerVO);
				}
			}
		}
		return adLayerVOs;
	}

	/**
	 * 查詢帳戶總覽中全部的廣告成效摘要
	 * 依帳戶編號、層級、時間查詢全部廣告成效
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<AdLayerVO> detailResultCost(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate) throws Exception{
		return detailResultCost(customerInfoId, enumAdLayer, startDate, endDate, -1, -1);
	}

	/**
	 * 查詢帳戶總覽中廣告成效摘要全部筆數
	 * 依帳戶編號、層級、時間查詢廣告成效
	 * @param customerInfoId
	 * @param enumAdLayer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public long detailResultCount(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate) throws Exception{
		return ((PfpAdPvclkDAO)dao).getAdCostCount(customerInfoId, enumAdLayer.getType(), startDate, endDate, -1, -1);
	}

	/**
	 * 查詢帳戶總覽中廣告成效摘要分頁筆數
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
	public long detailResultCount(String customerInfoId, EnumAdLayer enumAdLayer, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		return ((PfpAdPvclkDAO)dao).getAdCostCount(customerInfoId, enumAdLayer.getType(), startDate, endDate, page, pageSize);
	}

	public void setPfpAdActionDAO(PfpAdActionDAO pfpAdActionDAO) {
		this.pfpAdActionDAO = pfpAdActionDAO;
	}

	public void setPfpAdGroupDAO(PfpAdGroupDAO pfpAdGroupDAO) {
		this.pfpAdGroupDAO = pfpAdGroupDAO;
	}

	public void setPfpAdKeywordDAO(PfpAdKeywordDAO pfpAdKeywordDAO) {
		this.pfpAdKeywordDAO = pfpAdKeywordDAO;
	}

	public void setPfpAdDAO(PfpAdDAO pfpAdDAO) {
		this.pfpAdDAO = pfpAdDAO;
	}
}
