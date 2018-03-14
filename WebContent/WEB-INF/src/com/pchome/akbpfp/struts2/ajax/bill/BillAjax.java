package com.pchome.akbpfp.struts2.ajax.bill;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.bill.PfpTransDetailService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.freeAction.AdmFreeRecordService;
import com.pchome.akbpfp.db.vo.bill.AdmFreeVO;
import com.pchome.akbpfp.db.vo.bill.BillVO;
import com.pchome.akbpfp.db.vo.bill.BillVOList;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumPfdAccountPayType;

public class BillAjax extends BaseCookieAction{

	private static final String FILE_TYPE = ".csv";
	
	private PfpTransDetailService pfpTransDetailService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private AdmFreeRecordService admFreeRecordService;
	
	private String startDate;
	private String endDate;
	private BillVOList billVOList;
	private List<AdmFreeVO> admFreeVOList;
	
	private String downloadFileName;								// 下載顯示名
	private InputStream downloadFileStream;							// 下載報表的 input stream	
	
	private float totalAdd = 0;										// 加值
	private float totalIncome = 0;									// 廣告支出回收
	private float totalExpense = 0;									// 廣告支出
	private float totalRemain = 0;									// 帳戶餘額
	private EnumPfdAccountPayType[] payType = EnumPfdAccountPayType.values();
	
	public String searchTransDetailAjax() throws Exception{

		billVOList = pfpTransDetailService.findPfpTransDetail(super.getCustomer_info_id(), startDate, endDate);
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		
		return SUCCESS;
	}

	public String downloadTransDetailReportAjax() throws Exception{
		
		billVOList = pfpTransDetailService.findPfpTransDetail(super.getCustomer_info_id(), startDate, endDate);
		
		if(billVOList != null){
			
			StringBuffer fileName = new StringBuffer();
			fileName.append("帳戶交易明細報表_");
			fileName.append(startDate);
			fileName.append("~");
			fileName.append(endDate);
			fileName.append(FILE_TYPE);
			
			StringBuffer content = new StringBuffer();
			PfpCustomerInfo customerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			content.append("帳戶名稱,"+customerInfo.getCustomerInfoTitle());
			content.append("\n\n");
			content.append("日期範圍,"+startDate+"~"+endDate);
			content.append("\n\n");
			content.append("日期,明細內容,加值金額,退款金額,廣告支出回收,廣告支出,帳戶餘額");
			content.append("\n");
			
			for(BillVO vo:billVOList.getBillVOs()){
				
				content.append(vo.getTransDate()).append(",");
				content.append(vo.getTransContents()).append(",");
				content.append("NT$ " + vo.getSaveMoney()).append(",");
				content.append("NT$ " + vo.getRefundMoney()).append(",");
				content.append("NT$ " + vo.getReturnMoney()).append(",");
				content.append("NT$ " + vo.getAdSpentMoney()).append(",");
				content.append("NT$ " + vo.getRemain()).append(",");
				content.append("\n");
			}
			
			content.append(",總計,");
			content.append("NT$ " + billVOList.getTotalSaveMoney()).append(",");
			content.append("NT$ " + billVOList.getTotalRefundMoney()).append(",");
			content.append("NT$ " + billVOList.getTotalReturnMoney()).append(",");
			content.append("NT$ " + billVOList.getTotalAdSpentMoney()).append(",");
			content.append("NT$ " + billVOList.getRemain());
			
			downloadFileName = URLEncoder.encode(fileName.toString(), "UTF-8");
			downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
			
		}
		
		return SUCCESS;
	}
	
	public String searchFreeAjax() throws Exception{
		
		admFreeVOList = admFreeRecordService.findAccountFree(super.getCustomer_info_id(), startDate, endDate);
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		
		return SUCCESS;
	}
	
	public void setPfpTransDetailService(PfpTransDetailService pfpTransDetailService) {
		this.pfpTransDetailService = pfpTransDetailService;
	}

	public void setPfpCustomerInfoService(
			PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setAdmFreeRecordService(AdmFreeRecordService admFreeRecordService) {
		this.admFreeRecordService = admFreeRecordService;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BillVOList getBillVOList() {
		return billVOList;
	}

	public List<AdmFreeVO> getAdmFreeVOList() {
		return admFreeVOList;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public float getTotalAdd() {
		return totalAdd;
	}

	public float getTotalIncome() {
		return totalIncome;
	}

	public float getTotalExpense() {
		return totalExpense;
	}

	public float getTotalRemain() {
		return totalRemain;
	}

	public EnumPfdAccountPayType[] getPayType() {
		return payType;
	}

}
