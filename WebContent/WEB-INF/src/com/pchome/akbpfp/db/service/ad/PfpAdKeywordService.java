package com.pchome.akbpfp.db.service.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.dao.ad.IPfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdKeywordViewVO;
import com.pchome.enumerate.ad.EnumAdKeywordType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class PfpAdKeywordService extends BaseService<PfpAdKeyword,String> implements IPfpAdKeywordService{
	
	private SyspriceOperaterAPI syspriceOperaterAPI;

	public List<PfpAdKeyword> findAdKeywords(String adGroupSeq) {
		return ((IPfpAdKeywordDAO)dao).findAdKeywords(adGroupSeq);
	}
	
	public List<PfpAdKeyword> findAdKeywords(String adKeywordSeq, String adGroupSeq, String adKeyword, String adKeywordSearchPrice, String adKeywordChannelPrice, String adKeywordStatus) throws Exception{
		return ((PfpAdKeywordDAO)dao).findAdKeywords(adKeywordSeq, adGroupSeq, adKeyword, adKeywordSearchPrice, adKeywordChannelPrice, adKeywordStatus);
	}

	public PfpAdKeyword findAdKeyword(String adKeywordSeq) throws Exception {
		
		 List<PfpAdKeyword> adKeywords = ((PfpAdKeywordDAO)dao).findAdKeyword(adKeywordSeq);
		 
		 if(adKeywords.size() > 0){
			 return adKeywords.get(0);
		 }else{
			 return null;
		 }
	}

	public void insertPfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception {
		((PfpAdKeywordDAO)dao).insertPfpAdKeyword(pfpAdKeyword);
	}

	public void updatePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception {
		((PfpAdKeywordDAO)dao).updatePfpAdKeyword(pfpAdKeyword);
	}

	public void updatePfpAdKeywordStatus(String pfpAdKeywordStatus, String adKeywordSeq) throws Exception {
		((PfpAdKeywordDAO)dao).updatePfpAdKeywordStatus(pfpAdKeywordStatus, adKeywordSeq);
	}

	public void savePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception {
		((PfpAdKeywordDAO)dao).saveOrUpdatePfpAdKeyword(pfpAdKeyword);
	}
	
	public void saveOrUpdateWithCommit(PfpAdKeyword adKeyword) throws Exception{
		((PfpAdKeywordDAO)dao).saveOrUpdateWithCommit(adKeyword);
	}

	/**
	 * 查詢廣告關鍵字全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdKeywordCount(String customerInfoId, String adGroupSeq, String keyword) throws Exception{
		return ((PfpAdKeywordDAO)dao).getPfpAdKeywordCount(customerInfoId, adGroupSeq, keyword, -1, -1);
	}

	/**
	 * 查詢廣告關鍵字分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdKeywordCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception{
		return ((PfpAdKeywordDAO)dao).getPfpAdKeywordCount(customerInfoId, adGroupSeq, keyword, page, pageSize);
	}

	/**
	 * (新版)查詢關鍵字統計全部資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdKeywordReport(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception{
		//return ((PfpAdKeywordDAO)dao).getAdKeywordPvclk(customerInfoId, adGroupSeq, keyword, startDate, endDate, -1, -1);
		return ((PfpAdKeywordDAO)dao).getAdKeywordReport(customerInfoId, adGroupSeq, keyword, startDate, endDate, -1, -1);
	}

	/**
	 * (新版)查詢關鍵字統計資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeywordViewVO> getAdKeywordReport(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		
		List<PfpAdKeywordViewVO> adKeywordViewVOs = null;
		//List<Object> pvclks = ((PfpAdKeywordDAO)dao).getAdKeywordPvclk(customerInfoId, adGroupSeq, keyword, startDate, endDate, page, pageSize);
		List<Object> pvclks = ((PfpAdKeywordDAO)dao).getAdKeywordReport(customerInfoId, adGroupSeq, keyword, startDate, endDate, page, pageSize);
		List<Object> adRanks = ((PfpAdKeywordDAO)dao).getAdRank(customerInfoId, adGroupSeq, keyword, startDate, endDate);
		
		for(Object object:pvclks){
			
			Object[] ob = (Object[])object;
			
			if(ob[0] != null){
				
				if(adKeywordViewVOs == null){
					adKeywordViewVOs = new ArrayList<PfpAdKeywordViewVO>();
				}
				
				PfpAdKeywordViewVO adKeywordViewVO = new PfpAdKeywordViewVO();
				adKeywordViewVO.setAdActionSeq(ob[0].toString());
				adKeywordViewVO.setAdActionName(ob[1].toString());
				adKeywordViewVO.setAdActionMax(Float.parseFloat(ob[2].toString()));				
				adKeywordViewVO.setAdGroupSeq(ob[3].toString());		
				adKeywordViewVO.setAdGroupName(ob[4].toString());
				adKeywordViewVO.setAdKeywordSeq(ob[5].toString());
				adKeywordViewVO.setAdKeyword(ob[6].toString());
				// 關鍵字狀態
				for(EnumStatus status:EnumStatus.values()){
					int adStatus = Integer.parseInt(ob[7].toString());
					
					if(status.getStatusId() == adStatus){
						adKeywordViewVO.setAdKeywordStatus(adStatus);
						adKeywordViewVO.setAdKeywordStatusDesc(status.getStatusRemark());
					}
				}	
				
				float searchPrice = Float.parseFloat(ob[8].toString());
				float channelPrice = Float.parseFloat(ob[9].toString());
				adKeywordViewVO.setAdKeywordSearchPrice(searchPrice);
				adKeywordViewVO.setAdKeywordChannelPrice(channelPrice);
								
				// 平均排名
				adKeywordViewVO.setAdKeywordRankAvg(0);
				
				for(Object adRank:adRanks){
					
					Object[] rank = (Object[])adRank;
					
					if(rank[0] != null && adKeywordViewVO.getAdKeywordSeq().equals(rank[0].toString())){
						adKeywordViewVO.setAdKeywordRankAvg(Float.parseFloat(rank[1].toString()));
					}
				}
					
				
				// 求點閱率
				int pv = Integer.parseInt(ob[10].toString());
				int clk = Integer.parseInt(ob[11].toString());
				int invalidClk = Integer.parseInt(ob[13].toString());
				float clkPrice = Float.parseFloat(ob[12].toString());
				
				adKeywordViewVO.setAdKeywordPv(pv);
				adKeywordViewVO.setAdKeywordClk(clk);
				adKeywordViewVO.setInvalidClk(invalidClk);
				adKeywordViewVO.setAdKeywordClkPrice(clkPrice);
								
				float clkRate = 0;
				float clkPriceAvg = 0;			
				
				if(clk > 0 || pv > 0){
					clkRate = (float)clk / (float)pv*100;
					
				}
				
				if(clkPrice > 0 || clk > 0){
					clkPriceAvg = clkPrice / (float)clk;
				}
				
				adKeywordViewVO.setAdKeywordClkRate(clkRate);
				adKeywordViewVO.setAdKeywordClkPriceAvg(clkPriceAvg);
				
				float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeywordViewVO.getAdKeyword());
				adKeywordViewVO.setSuggestPrice(suggestPrice);
				adKeywordViewVO.setAdKeywordType(ob[15].toString());
				
				adKeywordViewVOs.add(adKeywordViewVO);
			}
		}
		
		return adKeywordViewVOs;
	}

	/**
	 * 查詢關鍵字廣告成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢關鍵字廣告的資料。 2.依查詢出來的關鍵字廣告，再去查詢平均排行及成效
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeywordViewVO> findAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		
		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		adGroupSeq = CommonUtils.filterSqlInjection(adGroupSeq);
		keyword = CommonUtils.filterSqlInjection(keyword);
		
		List<PfpAdKeywordViewVO> adKeywordViewVOs = null;

		//List<Object> pvclks = ((PfpAdKeywordDAO)dao).getAdKeywordPvclk(customerInfoId, adGroupSeq, keyword, startDate, endDate, page, pageSize);
		//List<Object> pvclks = ((PfpAdKeywordDAO)dao).getAdKeywordReport(customerInfoId, adGroupSeq, keyword, startDate, endDate, page, pageSize);
		// 先查詢帳戶的所有廣告關鍵字資料
		List<PfpAdKeyword> pfpAdKeywords = ((PfpAdKeywordDAO)dao).getPfpAdKeywordForView(customerInfoId, adGroupSeq, keyword, page, pageSize);
		// 逐筆讀出
		for(PfpAdKeyword pfpAdKeyword:pfpAdKeywords) {
			if(adKeywordViewVOs == null){
				adKeywordViewVOs = new ArrayList<PfpAdKeywordViewVO>();
			}
			
			PfpAdKeywordViewVO adKeywordViewVO = new PfpAdKeywordViewVO();
			adKeywordViewVO.setAdActionSeq(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionSeq());
			adKeywordViewVO.setAdActionName(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName());
			adKeywordViewVO.setAdActionMax(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionMax());				
			adKeywordViewVO.setAdGroupSeq(pfpAdKeyword.getPfpAdGroup().getAdGroupSeq());		
			adKeywordViewVO.setAdGroupName(pfpAdKeyword.getPfpAdGroup().getAdGroupName());
			adKeywordViewVO.setAdKeywordSeq(pfpAdKeyword.getAdKeywordSeq());
			adKeywordViewVO.setAdKeyword(pfpAdKeyword.getAdKeyword());

			// 關鍵字狀態
			for(EnumStatus status:EnumStatus.values()){
				int adStatus = pfpAdKeyword.getAdKeywordStatus();
				
				if(status.getStatusId() == adStatus){
					adKeywordViewVO.setAdKeywordStatus(adStatus);
					adKeywordViewVO.setAdKeywordStatusDesc(status.getStatusRemark());
				}
			}	

			adKeywordViewVO.setAdKeywordSearchPrice(pfpAdKeyword.getAdKeywordSearchPrice());
			adKeywordViewVO.setAdKeywordChannelPrice(pfpAdKeyword.getAdKeywordChannelPrice());

			// 平均排名
			List<Object> adRanks = ((PfpAdKeywordDAO)dao).getAdRankByAGS(customerInfoId, adGroupSeq, EnumAdType.AD_SEARCH.getType(), startDate, endDate);
			adKeywordViewVO.setAdKeywordRankAvg(0);
			for(Object adRank:adRanks){
				Object[] rank = (Object[])adRank;
				
				if(rank[0] != null && adKeywordViewVO.getAdKeywordSeq().equals(rank[0].toString())){
					adKeywordViewVO.setAdKeywordRankAvg(Float.parseFloat(rank[1].toString()));
				}
			}

			// 查詢關鍵字廣告的成效
			List<Object> objects = ((PfpAdKeywordDAO)dao).getAdKeywordReportByAKS(customerInfoId, pfpAdKeyword.getPfpAdGroup().getAdGroupSeq(), pfpAdKeyword.getAdKeywordSeq(), EnumAdType.AD_SEARCH.getType(), startDate, endDate);
			for(Object object:objects){
				Object[] ob = (Object[])object;
				if(ob[0] != null) {
					// 求點閱率
					int pv = Integer.parseInt(ob[0].toString());
					int clk = Integer.parseInt(ob[1].toString());
					float clkPrice = Float.parseFloat(ob[2].toString());
					int invalidClk = Integer.parseInt(ob[3].toString());

					adKeywordViewVO.setAdKeywordPv(pv);
					adKeywordViewVO.setAdKeywordClk(clk);
					adKeywordViewVO.setInvalidClk(invalidClk);
					adKeywordViewVO.setAdKeywordClkPrice(clkPrice);

					float clkRate = 0;
					float clkPriceAvg = 0;			
					
					// 點擊率
					if(clk > 0 || pv > 0){
						clkRate = (float)clk / (float)pv*100;
						
					}
					// 平均點擊費用
					if(clkPrice > 0 || clk > 0){
						clkPriceAvg = clkPrice / (float)clk;
					}
					
					adKeywordViewVO.setAdKeywordClkRate(clkRate);
					adKeywordViewVO.setAdKeywordClkPriceAvg(clkPriceAvg);

					// 關鍵字建議價
					//float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeywordViewVO.getAdKeyword());
					//adKeywordViewVO.setSuggestPrice(suggestPrice);
					adKeywordViewVO.setAdKeywordType(ob[5].toString());
					
					adKeywordViewVOs.add(adKeywordViewVO);
				}
			}
		}

		return adKeywordViewVOs;
	}

	/**
	 * 查詢全部的關鍵字廣告成效(For 檢視廣告)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeywordViewVO> findAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception{
		return findAdKeywordView(customerInfoId, adGroupSeq, keyword, startDate, endDate, -1, -1);
	}


	/**
	 * 2014/04/21 修正查詢關鍵字廣告成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢關鍵字廣告的資料。 2.再依查詢出來的關鍵字廣告，去查詢平均排行及成效
	 * 成效與平均排名皆以 Map 回傳，Map 的 Key 皆為 adKeywordSeq
	 * 本次處理是將 DB 連線數量壓到最低來處理，不然速度會因為與 DB 連線數量過多而變得非常慢
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeywordViewVO> getAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		
		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		adGroupSeq = CommonUtils.filterSqlInjection(adGroupSeq);
		keyword = CommonUtils.filterSqlInjection(keyword);
		
		List<PfpAdKeywordViewVO> adKeywordViewVOs = null;
		// 查詢帳戶的廣告關鍵字資料
		List<PfpAdKeyword> pfpAdKeywords = ((PfpAdKeywordDAO)dao).getPfpAdKeywordForView(customerInfoId, adGroupSeq, keyword, page, pageSize);

		if(pfpAdKeywords.size() > 0) {
			// 逐筆讀出本頁的關鍵字序號
			List<String> adKeywordSeqs = new ArrayList<String>();
			for(PfpAdKeyword pfpAdKeyword:pfpAdKeywords) {
				adKeywordSeqs.add(pfpAdKeyword.getAdKeywordSeq());
			}
	
			// 依照讀出本頁的關鍵字序號，查詢關鍵字成效
			HashMap<String, Object> pfpAdKeywordReports = ((PfpAdKeywordDAO)dao).getAdKeywordReportByAdKeywordsList(customerInfoId, adGroupSeq, adKeywordSeqs, EnumAdType.AD_SEARCH.getType(), startDate, endDate);
	
			// 依照讀出本頁的關鍵字序號，查詢關鍵字平均排名
			HashMap<String, Float> adRanks = ((PfpAdKeywordDAO)dao).getAdRankByAGS(customerInfoId, adKeywordSeqs, startDate, endDate,EnumAdKeywordType.WIDELY.getStyle());
			HashMap<String, Float> adPhrRanks = ((PfpAdKeywordDAO)dao).getAdRankByAGS(customerInfoId, adKeywordSeqs, startDate, endDate,EnumAdKeywordType.PHRASE.getStyle());
			HashMap<String, Float> adPreRanks = ((PfpAdKeywordDAO)dao).getAdRankByAGS(customerInfoId, adKeywordSeqs, startDate, endDate,EnumAdKeywordType.PRECISION.getStyle());
			
			// 逐筆讀出關鍵字資料
			for(PfpAdKeyword pfpAdKeyword:pfpAdKeywords) {
				if(adKeywordViewVOs == null){
					adKeywordViewVOs = new ArrayList<PfpAdKeywordViewVO>();
				}
	
				// 開始設定關鍵字的資料進 PfpAdKeywordViewVO
				PfpAdKeywordViewVO adKeywordViewVO = new PfpAdKeywordViewVO();
				adKeywordViewVO.setAdActionSeq(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionSeq());
				adKeywordViewVO.setAdActionName(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName());
				adKeywordViewVO.setAdActionMax(pfpAdKeyword.getPfpAdGroup().getPfpAdAction().getAdActionMax());				
				adKeywordViewVO.setAdGroupSeq(pfpAdKeyword.getPfpAdGroup().getAdGroupSeq());		
				adKeywordViewVO.setAdGroupName(pfpAdKeyword.getPfpAdGroup().getAdGroupName());
				adKeywordViewVO.setAdKeywordSeq(pfpAdKeyword.getAdKeywordSeq());
				adKeywordViewVO.setAdKeyword(pfpAdKeyword.getAdKeyword());
	
				// 關鍵字狀態
				for(EnumStatus status:EnumStatus.values()){
					int adStatus = pfpAdKeyword.getAdKeywordStatus();
					if(status.getStatusId() == adStatus){
						adKeywordViewVO.setAdKeywordStatus(adStatus);
						adKeywordViewVO.setAdKeywordStatusDesc(status.getStatusRemark());
					}
				}	
				
				//比對開起狀態
				adKeywordViewVO.setAdKeywordOpen(pfpAdKeyword.getAdKeywordOpen());
				adKeywordViewVO.setAdKeywordPhraseOpen(pfpAdKeyword.getAdKeywordPhraseOpen());
				adKeywordViewVO.setAdKeywordPrecisionOpen(pfpAdKeyword.getAdKeywordPrecisionOpen());
				
				//出價
				adKeywordViewVO.setAdKeywordSearchPrice(pfpAdKeyword.getAdKeywordSearchPrice());
				adKeywordViewVO.setAdKeywordSearchPhrasePrice(pfpAdKeyword.getAdKeywordSearchPhrasePrice());
				adKeywordViewVO.setAdKeywordSearchPrecisionPrice(pfpAdKeyword.getAdKeywordSearchPrecisionPrice());
				adKeywordViewVO.setAdKeywordChannelPrice(pfpAdKeyword.getAdKeywordChannelPrice());
	
				// 依照關鍵字廣告序號，讀取、設定平均排名，沒有平均排名的，就不用設定了
				if(adRanks.get(pfpAdKeyword.getAdKeywordSeq()) != null) {
					adKeywordViewVO.setAdKeywordRankAvg(adRanks.get(pfpAdKeyword.getAdKeywordSeq()));
				}
				if(adPhrRanks.get(pfpAdKeyword.getAdKeywordSeq()) != null) {
					adKeywordViewVO.setAdKeywordPhraseRankAvg(adPhrRanks.get(pfpAdKeyword.getAdKeywordSeq()));
				}
				if(adPreRanks.get(pfpAdKeyword.getAdKeywordSeq()) != null) {
					adKeywordViewVO.setAdKeywordPhraseRankAvg(adPreRanks.get(pfpAdKeyword.getAdKeywordSeq()));
				}
	
				// 依照關鍵字廣告序號，讀取、設定廣告成效，沒有廣告成效的，就不用設定了
				if(pfpAdKeywordReports.size() > 0 && pfpAdKeywordReports.get(pfpAdKeyword.getAdKeywordSeq()) != null) {
					Object[] obj = (Object[])pfpAdKeywordReports.get(pfpAdKeyword.getAdKeywordSeq());
					if(obj != null) {
						//總計
						Integer pvSum = 0;			//曝光數
						Integer clkSum = 0;			//點選次數
						float clkRateSum = 0;		//點選率
						Integer invalidClkSum = 0;	//無效點選次數
						float clkPriceSumAvg = 0;	//平均點選費用
						float clkPriceSum = 0;		//費用
						
						//-------------------廣泛比對--------------------
						int pv = Integer.parseInt(obj[1].toString());
						int clk = Integer.parseInt(obj[2].toString());
						float clkPrice = Float.parseFloat(obj[3].toString());
						int invalidClk = Integer.parseInt(obj[4].toString());
						float clkRate = 0;
						float clkPriceAvg = 0;
						
						pvSum += pv;
						clkSum += clk;
						invalidClkSum += invalidClk;
						clkPriceSum += clkPrice;
	
						// 點擊率
						if(clk > 0 || pv > 0){
							clkRate = (float)clk / (float)pv*100;
						}
						// 平均點擊費用
						if(clkPrice > 0 || clk > 0){
							clkPriceAvg = clkPrice / (float)clk;
						}
						
						adKeywordViewVO.setAdKeywordPv(pv);
						adKeywordViewVO.setAdKeywordClk(clk);
						adKeywordViewVO.setInvalidClk(invalidClk);
						adKeywordViewVO.setAdKeywordClkPrice(clkPrice);
						adKeywordViewVO.setAdKeywordClkRate(clkRate);
						adKeywordViewVO.setAdKeywordClkPriceAvg(clkPriceAvg);
						adKeywordViewVO.setAdKeywordType(obj[6].toString());
						
						//-------------------詞組比對--------------------
						int phrasePv = Integer.parseInt(obj[7].toString());
						int phraseClk = Integer.parseInt(obj[8].toString());
						float phraseClkPrice = Float.parseFloat(obj[9].toString());
						int phraseInvalidClk = Integer.parseInt(obj[10].toString());
						float phraseClkRate = 0;
						float phraseClkPriceAvg = 0;
						
						pvSum += phrasePv;
						clkSum += phraseClk;
						invalidClkSum += phraseInvalidClk;
						clkPriceSum += phraseClkPrice;
	
						// 點擊率
						if(phraseClk > 0 || phrasePv > 0){
							phraseClkRate = (float)phraseClk / (float)phrasePv*100;
						}
						// 平均點擊費用
						if(phraseClkPrice > 0 || phraseClk > 0){
							phraseClkPriceAvg = phraseClkPrice / (float)phraseClk;
						}
						
						adKeywordViewVO.setAdKeywordPhrasePv(phrasePv);
						adKeywordViewVO.setAdKeywordPhraseClk(phraseClk);
						adKeywordViewVO.setPhraseInvalidClk(phraseInvalidClk);
						adKeywordViewVO.setAdKeywordPhraseClkPrice(phraseClkPrice);
						adKeywordViewVO.setAdKeywordPhraseClkRate(phraseClkRate);
						adKeywordViewVO.setAdKeywordPhraseClkPriceAvg(phraseClkPriceAvg);
						adKeywordViewVO.setAdKeywordPhraseType(obj[12].toString());
						
						//------------------- 精準比對--------------------
						int precisionPv = Integer.parseInt(obj[13].toString());
						int precisionClk = Integer.parseInt(obj[14].toString());
						float precisionClkPrice = Float.parseFloat(obj[15].toString());
						int precisionInvalidClk = Integer.parseInt(obj[16].toString());
						float precisionClkRate = 0;
						float precisionClkPriceAvg = 0;
						
						pvSum += precisionPv;
						clkSum += precisionClk;
						invalidClkSum += precisionInvalidClk;
						clkPriceSum += precisionClkPrice;
	
						// 點擊率
						if(precisionClk > 0 || precisionPv > 0){
							precisionClkRate = (float)precisionClk / (float)precisionPv*100;
						}
						// 平均點擊費用
						if(precisionClkPrice > 0 || precisionClk > 0){
							precisionClkPriceAvg = precisionClkPrice / (float)precisionClk;
						}
						
						adKeywordViewVO.setAdKeywordPrecisionPv(precisionPv);
						adKeywordViewVO.setAdKeywordPrecisionClk(precisionClk);
						adKeywordViewVO.setPrecisionInvalidClk(precisionInvalidClk);
						adKeywordViewVO.setAdKeywordPrecisionClkPrice(precisionClkPrice);
						adKeywordViewVO.setAdKeywordPrecisionClkRate(precisionClkRate);
						adKeywordViewVO.setAdKeywordPrecisionClkPriceAvg(precisionClkPriceAvg);
						adKeywordViewVO.setAdKeywordPrecisionType(obj[18].toString());
						
						//------------------- 總計--------------------
						// 點擊率
						if(clkSum > 0 || pvSum > 0){
							clkRateSum = (float)clkSum / (float)pvSum*100;
						}
						// 平均點擊費用
						if(clkPriceSum > 0 || clkSum > 0){
							clkPriceSumAvg = clkPriceSum / (float)clk;
						}
						
						adKeywordViewVO.setAdKeywordPvSum(pvSum);
						adKeywordViewVO.setAdKeywordClkSum(clkSum);
						adKeywordViewVO.setInvalidClkSum(invalidClkSum);
						adKeywordViewVO.setAdKeywordClkPriceSum(clkPriceSum);
						adKeywordViewVO.setAdKeywordClkRateSum(clkRateSum);
						adKeywordViewVO.setAdKeywordClkPriceSumAvg(clkPriceSumAvg);
					}
				}
	
				// 關鍵字建議價(詞組比對跟精準比對價錢為廣泛比對價錢往上加1~3,4~6)
				float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeywordViewVO.getAdKeyword(),"widely");
				float suggestPhrasePrice = ((int)(Math.random()*3+1)) + suggestPrice;
				float suggestPrecisionPrice = ((int)(Math.random()*3+4)) + suggestPrice;
				/*float suggestPhrasePrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeywordViewVO.getAdKeyword(),"phrase");
				suggestPhrasePrice = ((int)(Math.random()*3+1)) + suggestPhrasePrice;
				float suggestPrecisionPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeywordViewVO.getAdKeyword(),"precision");
				suggestPrecisionPrice = ((int)(Math.random()*3+4)) + suggestPrecisionPrice;*/
				
				adKeywordViewVO.setSuggestPrice(suggestPrice);
				adKeywordViewVO.setSuggestPhrasePrice(suggestPhrasePrice);
				adKeywordViewVO.setSuggestPrecisionPrice(suggestPrecisionPrice);
	
				adKeywordViewVOs.add(adKeywordViewVO);
			}
		}

		return adKeywordViewVOs;
	}

	
	public List<PfpAdKeyword> validAdKeyword(String adGroupSeq) throws Exception{
		return ((PfpAdKeywordDAO)dao).validAdKeyword(adGroupSeq);
	}
	
	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	//keyWord list update
	public void savePfpAdKeywordList(List<PfpAdKeyword> pfpAdKeywordList) throws Exception {
	    ((IPfpAdKeywordDAO) dao).savePfpAdKeywordList(pfpAdKeywordList);
	}

}
