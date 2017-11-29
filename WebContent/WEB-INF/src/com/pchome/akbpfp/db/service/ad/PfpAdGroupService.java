package com.pchome.akbpfp.db.service.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.dao.ad.PfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdGroupViewVO;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class PfpAdGroupService extends BaseService<PfpAdGroup,String> implements IPfpAdGroupService{

	private SyspriceOperaterAPI syspriceOperaterAPI;
	
	public List<PfpAdGroup> getAllPfpAdGroups() throws Exception{
		return ((PfpAdGroupDAO)dao).loadAll();
	}
	
	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupName, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception{
		return findAdGroupView(adActionSeq, adType, null, adGroupName, null, startDate, endDate, page, pageSize, customerInfoId);
		
	}
	
	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception{
		return ((PfpAdGroupDAO)dao).findAdGroupView(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, page, pageSize, customerInfoId);
	}

	public String getCount(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception {
		return ((PfpAdGroupDAO)dao).getCount(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, page, pageSize, customerInfoId);
	}

	public List<PfpAdGroup> getPfpAdGroups(String adGroupSeq, String adActionSeq, String adGroupName, String adGroupSearchPrice, String adGroupChannelPrice, String adGroupStatus) throws Exception{
		return ((PfpAdGroupDAO)dao).getPfpAdGroups(adGroupSeq, adActionSeq, adGroupName, adGroupSearchPrice, adGroupChannelPrice, adGroupStatus);
	}

	public boolean chkAdGroupNameByAdActionSeq(String adGroupName, String adGroupSeq, String adActionSeq) throws Exception {
		return ((PfpAdGroupDAO)dao).chkAdGroupNameByAdActionSeq(adGroupName, adGroupSeq, adActionSeq);
	}
	
	public PfpAdGroup getPfpAdGroupBySeq(String adGroupSeq) throws Exception {
		return ((PfpAdGroupDAO)dao).getPfpAdGroupBySeq(adGroupSeq);
	}

	public List<PfpAdGroup> getAdGroups(String adActionSeq, String orderBy, boolean desc) throws Exception {
		return ((PfpAdGroupDAO)dao).getAdGroups(adActionSeq, orderBy, desc);
	}
	
	public void insertPfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception {
		((PfpAdGroupDAO)dao).insertPfpAdGroup(pfpAdGroup);
	}

	public void updatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception {
		((PfpAdGroupDAO)dao).updatePfpAdGroup(pfpAdGroup);
	}

	public void updatePfpAdGroupStatus(String pfpAdGroupStatus, String adGroupSeq) throws Exception {
		((PfpAdGroupDAO)dao).updatePfpAdGroupStatus(pfpAdGroupStatus, adGroupSeq);
	}

	public void savePfpAdGroup(PfpAdGroup adGroup) throws Exception {
		((PfpAdGroupDAO)dao).saveOrUpdatePfpAdGroup(adGroup);
	}
	
	public void saveOrUpdateWithCommit(PfpAdGroup adGroup) throws Exception{
		((PfpAdGroupDAO)dao).saveOrUpdateWithCommit(adGroup);
	}

	/**
	 * 查詢廣告分類全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdGroupCount(String customerInfoId, String adActionSeq, String keyword) throws Exception{
		return ((PfpAdGroupDAO)dao).getPfpAdGroupCount(customerInfoId, adActionSeq, keyword, -1, -1);
	}

	/**
	 * 查詢廣告分類分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdGroupCount(String customerInfoId, String adActionSeq, String keyword, int page, int pageSize) throws Exception{
		return ((PfpAdGroupDAO)dao).getPfpAdGroupCount(customerInfoId, adActionSeq, keyword, page, pageSize);
	}

	/**
	 * 查詢廣告分類成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢廣告分類的資料。 2.依查詢出來的廣告分類，再去查詢廣告成效
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
	public List<PfpAdGroupViewVO> getAdGroupView(String customerInfoId, String adActionSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		
		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		adActionSeq = CommonUtils.filterSqlInjection(adActionSeq);
		keyword = CommonUtils.filterSqlInjection(keyword);

		List<PfpAdGroupViewVO> adGroupViewVOs = null;

		// 查詢廣告分類
		List<PfpAdGroup> pfpAdGroups = ((PfpAdGroupDAO)dao).getPfpAdGroupForView(customerInfoId, adActionSeq, keyword, page, pageSize);
		
		if(pfpAdGroups.size() > 0) {
			// 逐筆讀出本頁的廣告分類序號
			List<String> adGroupSeqs = new ArrayList<String>();
			for(PfpAdGroup pfpAdGroup:pfpAdGroups) {
				adGroupSeqs.add(pfpAdGroup.getAdGroupSeq());
			}
	
			// 依照讀出本頁的廣告分類序號，查詢廣告分類成效
			HashMap<String, Object> pfpAdGroupReports = ((PfpAdGroupDAO)dao).getAdGroupReportByAdGroupsList(customerInfoId, adActionSeq, adGroupSeqs, enumAdType.getType(), startDate, endDate);
	
			for(PfpAdGroup pfpAdGroup:pfpAdGroups) {
				if(adGroupViewVOs == null){
					adGroupViewVOs = new ArrayList<PfpAdGroupViewVO>();
				}
			
				PfpAdGroupViewVO adGroupViewVO = new PfpAdGroupViewVO();
				adGroupViewVO.setAdActionSeq(pfpAdGroup.getPfpAdAction().getAdActionSeq());
				adGroupViewVO.setAdActionName(pfpAdGroup.getPfpAdAction().getAdActionName());
				adGroupViewVO.setAdActionMax(pfpAdGroup.getPfpAdAction().getAdActionMax());
				adGroupViewVO.setAdGroupSeq(pfpAdGroup.getAdGroupSeq());						
				adGroupViewVO.setAdGroupName(pfpAdGroup.getAdGroupName());
				adGroupViewVO.setAdOperatingRule(pfpAdGroup.getPfpAdAction().getAdOperatingRule());
				for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
					if(pfpAdGroup.getAdGroupPriceType().equals(enumAdPriceType.getDbTypeName())){
						adGroupViewVO.setAdPriceTypeDesc(enumAdPriceType.getTypeName());
						break;
					}
				}
				
				//裝置
				for(EnumAdDevice adDevice:EnumAdDevice.values()){
					int devType = pfpAdGroup.getPfpAdAction().getAdDevice();
					
					if(adDevice.getDevType() == devType){
						adGroupViewVO.setAdDevice(adDevice.getDevTypeName());
					}
				}
				
				// 分類狀態
				for(EnumStatus status:EnumStatus.values()){
					int adStatus = pfpAdGroup.getAdGroupStatus();
					
					if(status.getStatusId() == adStatus){
						adGroupViewVO.setAdGroupStatus(adStatus);
						adGroupViewVO.setAdGroupStatusDesc(status.getStatusDesc());
					}
				}	
			
				adGroupViewVO.setAdGroupSearchPriceType(pfpAdGroup.getAdGroupSearchPriceType());
				adGroupViewVO.setAdGroupSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
				adGroupViewVO.setAdGroupChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
				
				// 求播出率
				float adAsideRate = syspriceOperaterAPI.getAdAsideRate(pfpAdGroup.getAdGroupChannelPrice());
				adGroupViewVO.setAdAsideRate(adAsideRate);
				
				// 依照關鍵字廣告序號，讀取、設定廣告成效，沒有廣告成效的，就不用設定了
				if(pfpAdGroupReports.size() > 0 && pfpAdGroupReports.get(pfpAdGroup.getAdGroupSeq()) != null) {
					Object[] obj = (Object[])pfpAdGroupReports.get(pfpAdGroup.getAdGroupSeq());
					if(obj != null) {
						// 求點閱率
						int pv = Integer.parseInt(obj[1].toString());
						int clk = Integer.parseInt(obj[2].toString());
						float clkPrice = Float.parseFloat(obj[3].toString());
						int invalidClk = Integer.parseInt(obj[4].toString());
						//float invalidClkPrice = Float.parseFloat(obj[5].toString());
	
						//clk = clk - invalidClk;
						//clkPrice = clkPrice - invalidClkPrice;
	
						adGroupViewVO.setAdPv(pv);
						adGroupViewVO.setAdClk(clk);
						adGroupViewVO.setAdClkPrice(clkPrice);
						adGroupViewVO.setInvalidClk(invalidClk);
	
						float clkRate = 0;
						float clkPriceAvg = 0;
						float thousandsCost = 0;
	
						// 點擊率
						if(clk > 0 || pv > 0){
							clkRate = (float)clk / (float)pv*100;
						}
						// 平均點擊費用
						if(clkPrice > 0 && clk > 0){
							clkPriceAvg = clkPrice / (float)clk;
						}
						// 千次曝光費用
						if(clkPrice > 0){
							thousandsCost = clkPrice / ( (float)pv * 1000);
						}
						adGroupViewVO.setThousandsCost(thousandsCost);
						adGroupViewVO.setAdClkRate(clkRate);
						adGroupViewVO.setAdClkPriceAvg(clkPriceAvg);
					}
				}
				adGroupViewVOs.add(adGroupViewVO);
			}

			
//			// 查詢廣告分類的成效
//			List<Object> objects = ((PfpAdGroupDAO)dao).getAdGroupReportByAGS(customerInfoId, pfpAdGroup.getPfpAdAction().getAdActionSeq(), pfpAdGroup.getAdGroupSeq(), enumAdType.getType(), startDate, endDate);
//			for(Object object:objects){
//				Object[] ob = (Object[])object;
//				if(ob[0] != null) {
//					// 求點閱率
//					int pv = Integer.parseInt(ob[0].toString());
//					int clk = Integer.parseInt(ob[1].toString());
//					float clkPrice = Float.parseFloat(ob[2].toString());
//					int invalidClk = Integer.parseInt(ob[3].toString());
//					float invalidClkPrice = Float.parseFloat(ob[4].toString());
//
//					clkPrice = clkPrice - invalidClkPrice;
//					clk = clk - invalidClk;
//
//					adGroupViewVO.setAdPv(pv);
//					adGroupViewVO.setAdClk(clk);
//					adGroupViewVO.setAdClkPrice(clkPrice);
//					adGroupViewVO.setInvalidClk(invalidClk);		
//
//					float clkRate = 0;
//					float clkPriceAvg = 0;			
//					
//					// 點擊率
//					if(clk > 0 || pv > 0){
//						clkRate = (float)clk / (float)pv*100;
//					}
//
//					// 平均點擊費用
//					if(clkPrice > 0 || clk > 0){
//						clkPriceAvg = clkPrice / (float)clk;
//					}
//
//					adGroupViewVO.setAdClkRate(clkRate);
//					adGroupViewVO.setAdClkPriceAvg(clkPriceAvg);
//
//					adGroupViewVOs.add(adGroupViewVO);
//				}
//			}

		}
		
		return adGroupViewVOs;		
	}
	
	/**
	 * 查詢全部廣告分類成效(For 檢視廣告)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdGroupViewVO> getAllAdGroupView(String customerInfoId, String adActionSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception{
		//return ((PfpAdGroupDAO)dao).getAdGroupPvclk(customerInfoId, adActionSeq, keyword, enumAdType.getType(), startDate, endDate, -1, -1);
		return getAdGroupView(customerInfoId, adActionSeq, keyword, enumAdType, startDate, endDate, -1, -1);
	}
	
	public List<PfpAdGroup> validAdGroup(String adActionSeq) throws Exception{
		return ((PfpAdGroupDAO)dao).validAdGroup(adActionSeq);
	}
	
	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}
}
