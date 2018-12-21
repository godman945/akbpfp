package com.pchome.akbpfp.struts2.action.codeManage.convertTracking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertRuleService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingRuleVO;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.codeManage.EnumConvertType;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.HttpUtil;

public class ConvertTrackingAction  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	
	private int currentPage = 1;      //第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; //每頁筆數(初始預設每頁10筆)
	private int totalCount = 0; //資料總筆數
	private int pageCount = 0; //總頁數
	private List<Object> convertList; 
	private ConvertTrackingVO  sumConvertCount;
	
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpCodeService pfpCodeService;
	private IPfpCodeConvertService pfpCodeConvertService;		
	private IPfpCodeConvertRuleService pfpCodeConvertRuleService;
	
	private ISequenceService sequenceService;
//	
	private String paId;
	private String convertSeq;
	private String returnMsg;
	private ConvertTrackingVO convertTrackingBean;
	private List<ConvertTrackingRuleVO> convertTrackingRuleList;

	public String queryConvertTrackingView(){		
		try{
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
			
			ConvertTrackingVO convertTrackingVO = new ConvertTrackingVO();
			convertTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			int convertTrackingCount = Integer.parseInt(pfpCodeConvertService.getConvertTrackingCount(convertTrackingVO));
			if (convertTrackingCount>0){
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
	
	public String queryConvertTrackingListView(){		
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> totalCount: " + totalCount);
			log.info(">>> pageCount: " + pageCount);
			
			ConvertTrackingVO convertTrackingVO = new ConvertTrackingVO();
			convertTrackingVO.setPage(currentPage);
			convertTrackingVO.setPageSize(pageSizeSelected);
			convertTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			
			
			//
			List<PfpCodeConvert> pfpCodeConvertList =pfpCodeConvertService.findPfpCodeConvertList(convertTrackingVO);
			convertList = new ArrayList<>();
			
			for (PfpCodeConvert pfpCodeConvert : pfpCodeConvertList) {
				convertTrackingVO.setConvertSeq(pfpCodeConvert.getConvertSeq());
				//ck
				int clickRangeDate = pfpCodeConvert.getClickRangeDate();
				convertTrackingVO.setCkStartDate(convertStartDay(clickRangeDate));
				convertTrackingVO.setCkEndDate(convertStartDay(1));
				//pv
				int impRangeDate = pfpCodeConvert.getImpRangeDate();
				convertTrackingVO.setPvStartDate(convertStartDay(impRangeDate));
				convertTrackingVO.setPvEndDate(convertStartDay(1));
				
				ConvertTrackingVO convertTransVO = pfpCodeConvertService.getConvertTrackingList(convertTrackingVO);
				convertList.add(convertTransVO);
			}
			
			
//			convertList = pfpCodeConvertService.getConvertTrackingList(convertTrackingVO);
			
			if(convertList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			
			//所有轉換動作
//			sumConvertCount = pfpCodeConvertService.getSumConvertCount(convertTrackingVO);
			int SumCKConvertCount = 0;
			int SumPVConvertCount = 0;
			int SumAllConvertCount = 0;
			
			for (int i = 0; i < convertList.size(); i++) {
				ConvertTrackingVO convertListVO = (ConvertTrackingVO) convertList.get(i);

				SumCKConvertCount = SumCKConvertCount + Integer.parseInt(convertListVO.getTransCKConvertCount());
				SumPVConvertCount = SumPVConvertCount + Integer.parseInt(convertListVO.getTransPVConvertCount());
				SumAllConvertCount = SumAllConvertCount + SumCKConvertCount + SumPVConvertCount;
			}
			
			sumConvertCount = new ConvertTrackingVO();
			sumConvertCount.setTransSumCKConvertCount(Integer.toString(SumCKConvertCount));	//加總ck點擊後轉換數
			sumConvertCount.setTransSumPVConvertCount(Integer.toString(SumPVConvertCount));	//加總pv瀏覽後轉換數
			sumConvertCount.setTransSumAllConvertCount(Integer.toString(SumAllConvertCount));//加總所有轉換(ck+pv)
		      
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(pfpCodeConvertService.getConvertTrackingCount(convertTrackingVO));
			
//			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public String convertStartDay(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -day);
		Date date = cal.getTime();
		String dateStr = sdf.format(date);
		System.out.println(dateStr);
		
		return dateStr;
	}
	
	public String convertEndDay(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -day);
		Date date = cal.getTime();
		String dateStr = sdf.format(date);
		System.out.println(dateStr);
		
		return dateStr;
	}

	
	public String addConvertTrackingView(){		
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
			convertSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CODE_CONVERT);
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public String editConvertTrackingView(){		
		try{
			log.info(">>> convertSeq: " +convertSeq);
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
			
			
			ConvertTrackingVO convertTrackingVO = new ConvertTrackingVO();
			convertTrackingVO.setConvertSeq(convertSeq);
			convertTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			
			convertTrackingBean = pfpCodeConvertService.getPfpCodeConvertByCondition(convertTrackingVO);
			
			if ( convertTrackingBean == null ){
				log.error("edit convert tracking error >>> convertSeq = " +convertSeq);
				returnMsg = "僅能編輯所屬的再行銷目錄";
				return ERROR;
			}
			
			if ( StringUtils.equals(convertTrackingBean.getConvertType(), EnumConvertType.CustomConvert.getType()) ){
				convertTrackingRuleList = pfpCodeConvertRuleService.getPfpCodeConvertRuleByCondition(convertTrackingVO);
			}
			
			
			
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

	public List<Object> getConvertList() {
		return convertList;
	}

	public void setConvertList(List<Object> convertList) {
		this.convertList = convertList;
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
	public String getConvertSeq() {
		return convertSeq;
	}
	public void setConvertSeq(String convertSeq) {
		this.convertSeq = convertSeq;
	}
	public String getPaId() {
		return paId;
	}
	public void setPaId(String paId) {
		this.paId = paId;
	}
	public IPfpCodeConvertService getPfpCodeConvertService() {
		return pfpCodeConvertService;
	}
	public void setPfpCodeConvertService(IPfpCodeConvertService pfpCodeConvertService) {
		this.pfpCodeConvertService = pfpCodeConvertService;
	}
	public IPfpCodeConvertRuleService getPfpCodeConvertRuleService() {
		return pfpCodeConvertRuleService;
	}
	public void setPfpCodeConvertRuleService(IPfpCodeConvertRuleService pfpCodeConvertRuleService) {
		this.pfpCodeConvertRuleService = pfpCodeConvertRuleService;
	}
	public ConvertTrackingVO getConvertTrackingBean() {
		return convertTrackingBean;
	}

	public void setConvertTrackingBean(ConvertTrackingVO convertTrackingBean) {
		this.convertTrackingBean = convertTrackingBean;
	}

	public List<ConvertTrackingRuleVO> getConvertTrackingRuleList() {
		return convertTrackingRuleList;
	}

	public void setConvertTrackingRuleList(List<ConvertTrackingRuleVO> convertTrackingRuleList) {
		this.convertTrackingRuleList = convertTrackingRuleList;
	}

	public ConvertTrackingVO getSumConvertCount() {
		return sumConvertCount;
	}

	public void setSumConvertCount(ConvertTrackingVO sumConvertCount) {
		this.sumConvertCount = sumConvertCount;
	}

	
	
}
