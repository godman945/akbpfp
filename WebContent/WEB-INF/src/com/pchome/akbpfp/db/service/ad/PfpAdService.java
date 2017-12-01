package com.pchome.akbpfp.db.service.ad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.ad.PfpAdDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewVO;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class PfpAdService extends BaseService<PfpAd,String> implements IPfpAdService{
	
	public List<PfpAd> getAllPfpAds() throws Exception{
		return ((PfpAdDAO)dao).loadAll();
	}

	public List<PfpAd> getPfpAds(String adSeq, String adGroupSeq, String adClass, String adArea, String adStyle, String adStatus) throws Exception{
		return ((PfpAdDAO)dao).getPfpAds(adSeq, adGroupSeq, adClass, adArea, adStyle, adStatus);
	}

	public List<Object> findAdView(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception{
		return ((PfpAdDAO)dao).findAdView(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, page, pageSize, customerInfoId);
	}

	public String getCount(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception {
		return ((PfpAdDAO)dao).getCount(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, page, pageSize, customerInfoId);
	}

	@Override
    public PfpAd getPfpAdBySeq(String adSeq) throws Exception {
		return ((PfpAdDAO)dao).getPfpAdBySeq(adSeq);
	}

	public void insertPfpAd(PfpAd pfpAd) throws Exception {
		((PfpAdDAO)dao).insertPfpAd(pfpAd);
	}

	public void updatePfpAd(PfpAd pfpAd) throws Exception {
		((PfpAdDAO)dao).updatePfpAd(pfpAd);
	}

	public void updatePfpAdStatus(String pfpAdStatus, String adSeq) throws Exception {
		((PfpAdDAO)dao).updatePfpAdStatus(pfpAdStatus, adSeq);
	}

	public void savePfpAd(PfpAd pfpAd) throws Exception {
		((PfpAdDAO)dao).saveOrUpdatePfpAd(pfpAd);
	}

	@Override
    public void saveOrUpdateWithCommit(PfpAd adAd) throws Exception{
		((PfpAdDAO)dao).saveOrUpdateWithCommit(adAd);
	}

	/**
	 * 查詢廣告明細全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
    public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword) throws Exception {
		return ((PfpAdDAO)dao).getPfpAdCount(customerInfoId, adGroupSeq, keyword, -1, -1);
	}

	/**
	 * 查詢廣告明細分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
    public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception {
		return ((PfpAdDAO)dao).getPfpAdCount(customerInfoId, adGroupSeq, keyword, -1, -1);
	}

	/**
	 * 查詢廣告明細成效(For 檢視廣告)
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
	@Override
    public List<PfpAdAdViewVO> getAdAdView(String customerInfoId, String adGroupSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception{

		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		adGroupSeq = CommonUtils.filterSqlInjection(adGroupSeq);
		keyword = CommonUtils.filterSqlInjection(keyword);

		List<PfpAdAdViewVO> adAdViewVOs = null;
		String imgFilename = null;

		// 廣告明細詳細內容
		HashMap<String, List<PfpAdDetail>> mPfpAdDetails = ((PfpAdDAO)dao).getPfpAdDetailByAdsList(customerInfoId, adGroupSeq, "title", keyword);

		if(mPfpAdDetails.size() > 0) {
			// 廣告明細內容
			List<PfpAd> pfpAds = ((PfpAdDAO)dao).getPfpAdForView(customerInfoId, adGroupSeq, new ArrayList<String>(mPfpAdDetails.keySet()), page, pageSize);

			// 逐筆讀出本頁的廣告明細序號
			List<String> adSeqs = new ArrayList<String>();
			for(PfpAd pfpAd:pfpAds) {
				adSeqs.add(pfpAd.getAdSeq());
			}

			// 依照讀出本頁的廣告明細序號，查詢廣告明細成效
			HashMap<String, Object> pfpAdReports = ((PfpAdDAO)dao).getAdReportByAdsList(customerInfoId, adGroupSeq, adSeqs, enumAdType.getType(), startDate, endDate);

			for(PfpAd pfpAd:pfpAds) {
				if(adAdViewVOs == null){
					adAdViewVOs = new ArrayList<PfpAdAdViewVO>();
				}

				PfpAdAdViewVO pfpAdAdViewVO = new PfpAdAdViewVO();
				pfpAdAdViewVO.setAdActionSeq(pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionSeq());
				pfpAdAdViewVO.setAdActionName(pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName());
				
				System.out.println(pfpAd.getPfpAdGroup().getAdGroupPriceType());
				// 計費方式
				for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
					if(enumAdPriceType.getDbTypeName().equals(pfpAd.getPfpAdGroup().getAdGroupPriceType())){
						pfpAdAdViewVO.setAdPriceType(enumAdPriceType.getTypeName());
						break;
					}
				}
				
				// 廣告類型
				for(EnumAdType type:EnumAdType.values()){
					int adType = pfpAd.getAdStatus();
					if(type.getType() == adType){
						pfpAdAdViewVO.setAdType(type.getChName());
						break;
					}
				}

				pfpAdAdViewVO.setAdGroupSeq(pfpAd.getPfpAdGroup().getAdGroupSeq());
				pfpAdAdViewVO.setAdGroupName(pfpAd.getPfpAdGroup().getAdGroupName());
				pfpAdAdViewVO.setAdSeq(pfpAd.getAdSeq());
				pfpAdAdViewVO.setAdTemplateNo(pfpAd.getTemplateProductSeq());
				pfpAdAdViewVO.setAdStyle(pfpAd.getAdStyle());

				for(EnumStatus status:EnumStatus.values()){
					int adStatus = pfpAd.getAdStatus();

					if(status.getStatusId() == adStatus){
						pfpAdAdViewVO.setAdStatus(adStatus);
						pfpAdAdViewVO.setAdStatusDesc(status.getStatusRemark());
					}
				}

				pfpAdAdViewVO.setAdRejectReason(pfpAd.getAdVerifyRejectReason());

				// 依照廣告活動序號，讀取、設定廣告成效，沒有廣告成效的，就不用設定了
				if(pfpAdReports != null && pfpAdReports.size() > 0 && pfpAdReports.get(pfpAd.getAdSeq()) != null) {
					Object[] obj = (Object[])pfpAdReports.get(pfpAd.getAdSeq());
					if(obj[0] != null) {
						int pv = Integer.parseInt(obj[1].toString());
						int clk = Integer.parseInt(obj[2].toString());
						float clkPrice = Float.parseFloat(obj[3].toString());
						int invalidClk = Integer.parseInt(obj[4].toString());
						float thousandsCost = 0;
						
						pfpAdAdViewVO.setAdPv(pv);
						pfpAdAdViewVO.setAdClk(clk);
						pfpAdAdViewVO.setInvalidClk(invalidClk);
						pfpAdAdViewVO.setAdClkPrice(clkPrice);

						// 求點閱率
						float clkRate = 0;
						float clkPriceAvg = 0;

						if(clk > 0 || pv > 0){
							clkRate = (float)clk / (float)pv*100;
						}

						if(clkPrice > 0 || clk > 0){
							clkPriceAvg = clkPrice / clk;
						}
						//千次曝光費用
						thousandsCost = (clkPrice / ((float)pv / 1000) );
						
						pfpAdAdViewVO.setThousandsCost(thousandsCost);
						pfpAdAdViewVO.setAdClkRate(clkRate);
						pfpAdAdViewVO.setAdClkPriceAvg(clkPriceAvg);
					}
				}

				String html5Tag = "N";
				if(StringUtils.equals("c_x05_po_tad_0059", pfpAd.getAdAssignTadSeq())){
					html5Tag = "Y";
				}
				pfpAdAdViewVO.setHtml5Tag(html5Tag);
				
				for (PfpAdDetail pfpAdDetail: pfpAd.getPfpAdDetails()) {
				    if ("real_url".equals(pfpAdDetail.getAdDetailId())) {
				        pfpAdAdViewVO.setRealUrl(pfpAdDetail.getAdDetailContent());
				    }
				    if ("title".equals(pfpAdDetail.getAdDetailId())) {
				        pfpAdAdViewVO.setTitle(pfpAdDetail.getAdDetailContent());
				    }
				    
				    if(StringUtils.equals("Y", html5Tag)){
				    	if ("img".equals(pfpAdDetail.getAdDetailId())) {
				    		pfpAdAdViewVO.setImg(pfpAdDetail.getAdDetailContent());
				    		pfpAdAdViewVO.setOriginalImg(pfpAdDetail.getAdDetailContent());
				    	}
				    	if("zip".equals(pfpAdDetail.getAdDetailId())){
				    		pfpAdAdViewVO.setZipTitle(pfpAdDetail.getAdDetailContent());
				    	}
				    	if("size".equals(pfpAdDetail.getAdDetailId())){
				    		String[] sizeArray = pfpAdDetail.getAdDetailContent().split("x");
				    		pfpAdAdViewVO.setImgWidth(sizeArray[0].trim());
				    		pfpAdAdViewVO.setImgHeight(sizeArray[1].trim());
				    	}
				    } else {
				    	if ("img".equals(pfpAdDetail.getAdDetailId())) {
	                        pfpAdAdViewVO.setImg(pfpAdDetail.getAdDetailContent());
	                        if(pfpAdAdViewVO.getImg().indexOf("original") == -1){
	                        	if(pfpAdAdViewVO.getImg().lastIndexOf("/") >= 0){
	                        		imgFilename = pfpAdAdViewVO.getImg().substring(pfpAdAdViewVO.getImg().lastIndexOf("/"));
	                        		pfpAdAdViewVO.setOriginalImg(pfpAdAdViewVO.getImg().replace(imgFilename, "/original" + imgFilename));	
	                        	}
	                        	pfpAdAdViewVO.setOriginalImg(pfpAdAdViewVO.getImg());
	                        } else {
	                        	pfpAdAdViewVO.setOriginalImg(pfpAdAdViewVO.getImg());
	                        }
	                    }
				    }
				}

				adAdViewVOs.add(pfpAdAdViewVO);
			}
		}

		return adAdViewVOs;
	}

	@Override
    public List<PfpAdAdViewVO> getAllAdAdView(String customerInfoId, String adGroupSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception{
		//return ((PfpAdDAO)dao).getAdAdPvclk(customerInfoId, adGroupSeq, keyword, enumAdType.getType(), startDate, endDate, -1, -1);
		return getAdAdView(customerInfoId, adGroupSeq, keyword, enumAdType, startDate, endDate, -1, -1);
	}

	@Override
    public List<PfpAd> validAdAd(String adGroupSeq) throws Exception{
		return ((PfpAdDAO)dao).validAdAd(adGroupSeq);
	}
	
	/*
	 * 取得影音廣告明細總數
	 * */
	public AdReportVO getAdAdVideoDetailViewCount(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception {
		
		List<Object> lisObj =  ((PfpAdDAO)dao).getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO);
		AdReportVO adReportVO = new AdReportVO();
		for (Object object : lisObj) {
			Object[] objArray = (Object[]) object;
			adReportVO.setAdPvSum(objArray[0].toString());
			adReportVO.setAdClkSum(objArray[1].toString());
			adReportVO.setAdPriceSum(objArray[2].toString());
			adReportVO.setAdInvClkSum(objArray[3].toString());
			adReportVO.setAdClickRatings(objArray[4].toString());
			adReportVO.setSingleCost(objArray[5].toString());
			adReportVO.setThousandsCost(objArray[6].toString());
		}
		
		return adReportVO;
//		int totalSize = lisObj.size();
//		int sumAdView = 0;
//		int sumPv = 0;
//		double sumCost = 0;
//		for (Object object : lisObj) {
//			Object[] objArray = (Object[]) object;
//			sumPv = sumPv + Integer.valueOf(objArray[0].toString());
//			sumCost = sumCost + Double.valueOf(objArray[1].toString());
//			sumAdView = sumAdView + Integer.valueOf(objArray[2].toString());
//		}
//		
//		double adViewRatings = 0;
//		double singleAdViewCost = 0;
//		double thousandsCost = 0;
//		if(sumAdView != 0 || sumPv != 0){
//			adViewRatings =	((double)sumAdView / (double)sumPv) * 100;
//			thousandsCost = (double)sumCost / ((double)sumPv / 1000);
//			if(sumCost >0 && sumAdView >0){
//				singleAdViewCost = (double)sumCost / (double)sumAdView;
//			}
//		}
//		PfpAdAdVideoViewSumVO pfpAdAdVideoViewSumVO = new PfpAdAdVideoViewSumVO();		
//		pfpAdAdVideoViewSumVO.setTotalSize(totalSize);
//		pfpAdAdVideoViewSumVO.setAdPvSum(sumPv);
//		pfpAdAdVideoViewSumVO.setAdViewSum(sumAdView);
//		pfpAdAdVideoViewSumVO.setAdViewRatings(adViewRatings);
//		pfpAdAdVideoViewSumVO.setSingleAdViewCost(singleAdViewCost);
//		pfpAdAdVideoViewSumVO.setThousandsCost(thousandsCost);
//		pfpAdAdVideoViewSumVO.setCostSum(sumCost);
//		return pfpAdAdVideoViewSumVO;
	}
	
	
	/*
	 * 取得影音廣告明細
	 * */
	public List<AdReportVO> getAdAdVideoDetailView(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception {
		List<Object> lisObj = ((PfpAdDAO)dao).getAdAdVideoDetailView(pfpAdAdViewConditionVO);
		List<AdReportVO> adReportVOList = null;
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = true;
		long nowTime = new Date().getTime();
		for (Object object : lisObj) {
			Object[] objArray = (Object[]) object;
			
			if(flag && StringUtils.isNotBlank(objArray[7].toString())){
				adReportVOList = new ArrayList<>();
				flag = false;
			}else if(StringUtils.isBlank(objArray[7].toString())){
				break;
			}
			
			AdReportVO adReportVO = new AdReportVO();
			adReportVO.setAdPvSum(objArray[0].toString());
			adReportVO.setAdClkSum(objArray[1].toString());
			adReportVO.setAdPriceSum(objArray[2].toString());
			adReportVO.setAdInvClkSum(objArray[3].toString());
			adReportVO.setAdSeq(objArray[7].toString());
			adReportVO.setAdOperatingRule(objArray[8].toString());
			adReportVO.setAdActionName(objArray[10].toString());
			adReportVO.setCustomerInfoId(objArray[11].toString());
			adReportVO.setAdClkPriceType(objArray[12].toString());
			adReportVO.setAdClickRatings(objArray[13].toString());
			adReportVO.setSingleCost(objArray[14].toString());
			adReportVO.setThousandsCost(objArray[15].toString());
			adReportVO.setAdVideoUrl(objArray[17].toString());
			adReportVO.setRealUrl(objArray[18].toString());
			adReportVO.setImg(objArray[19].toString());
			String[] sizeArray = ((String)objArray[20]).split("_");
			if(sizeArray.length == 2){
				adReportVO.setAdWidth(sizeArray[0]);
				adReportVO.setAdHeight(sizeArray[1]);
			}
			
			String secs = objArray[16].toString();
			if(secs.length() == 2){
				adReportVO.setAdVideoSec("00:"+secs);
			}else if(secs.length() == 1){
				adReportVO.setAdVideoSec("00:0"+secs);
			}
			
			int adActionStatus = (Integer)objArray[4];
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long _startDate = (dateFormat.parse(dateFormat2.format(objArray[5]) + " 00:00:00")).getTime();
				long _endDate = (dateFormat.parse(dateFormat2.format(objArray[6]) + " 23:59:59")).getTime();
				if (nowTime < _startDate) {
					adReportVO.setAdStatusDesc(EnumStatus.Waitbroadcast.getStatusDesc());
					adReportVO.setAdActionStatus(String.valueOf(EnumStatus.Waitbroadcast.getStatusId()));
				} else if (nowTime > _endDate) {
					adReportVO.setAdStatusDesc(EnumStatus.End.getStatusDesc());
					adReportVO.setAdActionStatus(String.valueOf(EnumStatus.End.getStatusId()));
				} else {
					adReportVO.setAdStatusDesc(EnumStatus.Broadcast.getStatusDesc());
					adReportVO.setAdActionStatus(String.valueOf(EnumStatus.Broadcast.getStatusId()));
				}
			}
			String adPriceType = objArray[12].toString();
			for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
				if(adPriceType.equals(enumAdPriceType.getDbTypeName())){
					adReportVO.setAdClkPriceType(enumAdPriceType.getTypeName());
					break;
				}
			}
			adReportVOList.add(adReportVO);
		}
		
		
//		List<PfpAdAdVideoViewVO> pfpAdAdVideoViewVOList = new ArrayList<>();
//		for (Object object : lisObj) {
//			Object[] objArray = (Object[]) object;
//			PfpAdAdVideoViewVO pfpAdAdVideoViewVO = new PfpAdAdVideoViewVO();
//			pfpAdAdVideoViewVO.setAdGroupSeq(objArray[0].toString());
//			pfpAdAdVideoViewVO.setAdSeq(objArray[1].toString());
//			for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
//				if(enumAdPriceType.getDbTypeName().equals(objArray[2].toString())){
//					pfpAdAdVideoViewVO.setPriceTypeDesc(enumAdPriceType.getTypeName());
//					break;
//				}
//			}
//			pfpAdAdVideoViewVO.setAdPvSum(Integer.valueOf(objArray[3].toString()));
//			pfpAdAdVideoViewVO.setCostSum(objArray[4].toString());
//			pfpAdAdVideoViewVO.setSumAdView(objArray[5].toString());
//			pfpAdAdVideoViewVO.setAdViewRatings(objArray[6].toString());
//			pfpAdAdVideoViewVO.setSingleAdViewCost(objArray[7].toString());
//			pfpAdAdVideoViewVO.setThousandsCost(objArray[8].toString());
//			pfpAdAdVideoViewVO.setActionStatus(objArray[9].toString());
//			pfpAdAdVideoViewVO.setImgPath(objArray[10].toString());
//			if(StringUtils.isNotBlank(objArray[11].toString())){
//				String adVideoSizeArray[] = objArray[11].toString().split("_");
//				String adWidth = adVideoSizeArray[0];
//				String adHeight = adVideoSizeArray[1];
//				pfpAdAdVideoViewVO.setAdWidth(adWidth);
//				pfpAdAdVideoViewVO.setAdHeight(adHeight);
//			}
//			pfpAdAdVideoViewVO.setMp4Path(objArray[12].toString());
//			pfpAdAdVideoViewVO.setWebmPath(objArray[13].toString());
//			pfpAdAdVideoViewVO.setVideoSeconds(objArray[14].toString());
//			pfpAdAdVideoViewVO.setAdActionName(objArray[15].toString());
//			pfpAdAdVideoViewVO.setRealUrl(objArray[16].toString());
//			pfpAdAdVideoViewVO.setVideoUrl(objArray[17].toString());
//			for(EnumStatus status:EnumStatus.values()){
//				if(status.getStatusId() == Integer.valueOf(objArray[9].toString())){
//					pfpAdAdVideoViewVO.setAdStatusDesc(status.getStatusRemark());
//					break;
//				}
//			}
//			pfpAdAdVideoViewVOList.add(pfpAdAdVideoViewVO);
//		}
		return adReportVOList;
	}
}


