package com.pchome.akbpfp.struts2.action.codeManage.retargetingTracking;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeTrackingService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.HttpUtil;

public class RetargetingTrackingAction  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	
	
	
	private int currentPage = 1;      //第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; //每頁筆數(初始預設每頁10筆)
	private int totalCount = 0; //資料總筆數
	private int pageCount = 0; //總頁數
	private List<Object> retargetingList; 
	
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpCodeService pfpCodeService;
	private IPfpCodeTrackingService pfpCodeTrackingService;
	private ISequenceService sequenceService;
	
	private String paId;
	private String trackingSeq;
	private String returnMsg;

	public String queryRetargetingTrackingView(){		
		try{
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
			
			RetargetingTrackingVO retargetingTrackingVO = new RetargetingTrackingVO();
			retargetingTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			int retargetingTrackingCount = Integer.parseInt(pfpCodeTrackingService.getRetargetingTrackingCount(retargetingTrackingVO));
			if (retargetingTrackingCount>0){
				return SUCCESS;
			}else{
				return "input";
			}
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
	}

	
	public String addRetargetingTrackingView(){		
		try{
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
			
			PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
			log.info(" getpfpCodeDb >>> OK");
			
			//如尚未建立過paid者，打api建立
			if (pfpCode == null){
				log.info("get pfpCode is null");
				String memberId = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getMemberId();
				String apiStr = "http://showstg.pchome.com.tw:8080/paadm/api/getPaCode?memberId=" + memberId;
				log.info("get getPaCode API : "+apiStr);
				StringBuffer paIdSB= HttpUtil.getInstance().doGet(apiStr);
				log.info("getPaIdCode String : "+paIdSB.toString());
				if (paIdSB.toString().indexOf("status:200") == -1) { 
					log.error(" getPaIdCode Error >>>  "+paIdSB.toString());
					returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
					return ERROR;
				}
				String[] paIdSBAry = paIdSB.toString().split(",");
				paId = paIdSBAry[0];
			}else{
				paId = pfpCode.getPaId();
			}
			
			//取流水號
			trackingSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CODE_TRACKING);
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}


	
	public String queryRetargetingTrackingListView(){		
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> totalCount: " + totalCount);
			log.info(">>> pageCount: " + pageCount);
			
			RetargetingTrackingVO retargetingTrackingVO = new RetargetingTrackingVO();
			retargetingTrackingVO.setPage(currentPage);
			retargetingTrackingVO.setPageSize(pageSizeSelected);
			retargetingTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			
			retargetingList = pfpCodeTrackingService.getRetargetingTrackingList(retargetingTrackingVO);
			
			if(retargetingList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(pfpCodeTrackingService.getRetargetingTrackingCount(retargetingTrackingVO));
			
//			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	

	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSizeSelected() {
		return pageSizeSelected;
	}
	public void setPageSizeSelected(int pageSizeSelected) {
		this.pageSizeSelected = pageSizeSelected;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<Object> getRetargetingList() {
		return retargetingList;
	}


	public void setRetargetingList(List<Object> retargetingList) {
		this.retargetingList = retargetingList;
	}


	public String getReturnMsg() {
		return returnMsg;
	}


	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}


	public IPfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}
	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
	public IPfpCodeService getPfpCodeService() {
		return pfpCodeService;
	}
	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}
	public ISequenceService getSequenceService() {
		return sequenceService;
	}
	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}
	public String getTrackingSeq() {
		return trackingSeq;
	}
	public void setTrackingSeq(String trackingSeq) {
		this.trackingSeq = trackingSeq;
	}
	public String getPaId() {
		return paId;
	}
	public void setPaId(String paId) {
		this.paId = paId;
	}
	public IPfpCodeTrackingService getPfpCodeTrackingService() {
		return pfpCodeTrackingService;
	}
	public void setPfpCodeTrackingService(IPfpCodeTrackingService pfpCodeTrackingService) {
		this.pfpCodeTrackingService = pfpCodeTrackingService;
	}


	
	
}
