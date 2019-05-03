package com.pchome.akbpfp.struts2.action.ad;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.db.pojo.PfdContract;
import com.pchome.akbpfp.db.pojo.PfdCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.board.IPfpBoardService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.pfd.EnumContractStatus;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;
import com.pchome.soft.util.DateValueUtil;

public class AdActionViewAction extends BaseSSLAction{

	private static final long serialVersionUID = 1L;

	private IAdmAccesslogService admAccesslogService;
	private IPfpAdActionService pfpAdActionService;
	private IPfpBoardService pfpBoardService;
	private ControlPriceAPI controlPriceAPI;
	private PfpCustomerInfoService pfpCustomerInfoService;
	
	private LinkedHashMap<String,String> dateSelectMap; // 查詢日期頁面顯示
	private String startDate;							// 開始日期
	private String endDate;								// 結束日期
	private PfpBoard board;
	private EnumBoardType[] enumBoardType;
	
	private String adActionSeq;
	private String status;
	private int adActionMax;
	private EnumAdType[] searchAdType;
	private float minprice;
	private String addFlag = "y"; //是否顯示新增廣告子選單
	
	public String execute() throws Exception{
		log.info("***START***");
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		searchAdType = EnumAdType.values();
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();
		
		enumBoardType = EnumBoardType.values();
		board = pfpBoardService.findAccountRemainBoard(EnumBoardType.FINANCE, 
														super.getCustomer_info_id(), 
														EnumCategory.REMAIN_NOT_ENOUGH);

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());

		//判斷合約到期的綜合經銷商規則 -> 若為綜合經銷商，合約到期後不可新增廣告活動
		if (pfpCustomerInfo.getPfdUserAdAccountRefs().size()>0) {
			PfdUserAdAccountRef ref = pfpCustomerInfo.getPfdUserAdAccountRefs().iterator().next();
			PfdCustomerInfo pfdCustomerInfo = ref.getPfdCustomerInfo();
			if ("y".equals(pfdCustomerInfo.getMixFlag())) { //綜合經銷商
				//若無有效合約 -> 鎖掉新增按鈕
				if (pfdCustomerInfo.getPfdContracts().size()>0) {
					boolean haveActiveContract = false;
					Iterator<PfdContract> it = pfdCustomerInfo.getPfdContracts().iterator();
					while (it.hasNext()) {
						if (it.next().getStatus().equals(EnumContractStatus.USE.getStatusId())) {
							haveActiveContract = true;
						}
					}

					if (!haveActiveContract) {
						this.addFlag = "n";
					}
				}
			}
		}

		return SUCCESS;
	}

	@Transactional
	public String updateAdActionStatusAction() throws Exception{
		
		String[] adActionSeqs = adActionSeq.split(",");
		int statusId = Integer.parseInt(status);
		
		for(int i=0;i<adActionSeqs.length;i++){
			
			PfpAdAction adAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeqs[i]);
			
			/**
			 *  "未完成"的廣告，不能異動廣告狀態
			 */
			if(adAction != null && StringUtils.isNotBlank(status) && 
					(EnumStatus.Close.getStatusId() == statusId || 
					EnumStatus.UnDone.getStatusId() != adAction.getAdActionStatus())){
				
				
				StringBuffer msg = new StringBuffer();
				msg.append("狀態異動：");
				msg.append("檢視廣告 ==> ");
				msg.append(adAction.getAdActionName()).append("(").append(adAction.getAdActionSeq()).append(")").append("：");
				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == adAction.getAdActionStatus()){
						msg.append(" ");
						msg.append(adStatus.getStatusDesc());
						msg.append(" ==> ");
					}
				}
				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == statusId){
						msg.append(" ");
						msg.append(adStatus.getStatusDesc());
					}
				}

				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, 
												msg.toString(), 
												super.getId_pchome(), 
												super.getCustomer_info_id(), 
												super.getUser_id(), 
												super.request.getRemoteAddr());
											
				adAction.setAdActionStatus(statusId);
				adAction.setAdActionUpdateTime(new Date());
				pfpAdActionService.saveOrUpdate(adAction);		
				
				// 重算調控金額
				controlPriceAPI.countProcess(adAction.getPfpCustomerInfo());
			}
			
		}		
		return SUCCESS;
	}
	
	@Transactional
	public void updateAdActionMaxAjax() throws Exception{
		
		PfpAdAction adAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		
		log.info(" adAction = "+adAction);
		log.info(" adActionMax = "+adActionMax);
		log.info(" minprice = "+minprice);
		
		if(adAction != null && 
				StringUtils.isNotBlank(String.valueOf(adActionMax)) && 
				adActionMax >= minprice){
	
			log.info(" adAction.getAdActionMax() = "+adAction.getAdActionMax());
			
			// 確認每日上限不一樣才更新
			if(adActionMax != adAction.getAdActionMax()){		
				
				StringBuffer msg = new StringBuffer();
				msg.append("每日上限金額異動：");
				msg.append("檢視廣告 ==> ");
				msg.append(adAction.getAdActionName()).append("(").append(adAction.getAdActionSeq()).append(")").append("：");
				msg.append(adAction.getAdActionMax());
				msg.append(" ==> ");
				msg.append(adActionMax);				
				
				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
												msg.toString(), 
												super.getId_pchome(), 
												super.getCustomer_info_id(), 
												super.getUser_id(), 
												super.request.getRemoteAddr());
				
				adAction.setAdActionMax(adActionMax);
				adAction.setAdActionControlPrice(adActionMax);
				adAction.setChangeMax("Y");
				
				adAction.setAdActionUpdateTime(new Date());
				pfpAdActionService.saveOrUpdate(adAction);
				
				log.info(" AdActionControlPrice = "+adAction.getAdActionControlPrice());

				// 只有開啟的廣告才會計算調控金額
				if(adAction.getAdActionStatus() == EnumStatus.Open.getStatusId()){
					// 重算調控金額
					controlPriceAPI.countProcess(adAction.getPfpCustomerInfo());
				}
								
			}
			
		}
		
	}
	
	public void setPfpAdActionService(IPfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}
	
	public void setPfpBoardService(IPfpBoardService pfpBoardService) {
		this.pfpBoardService = pfpBoardService;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setControlPriceAPI(ControlPriceAPI controlPriceAPI) {
		this.controlPriceAPI = controlPriceAPI;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public PfpBoard getBoard() {
		return board;
	}
	
	public EnumBoardType[] getEnumBoardType() {
		return enumBoardType;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAdActionMax(int adActionMax) {
		this.adActionMax = adActionMax;
	}

	public EnumAdType[] getSearchAdType() {
		return searchAdType;
	}

	public void setMinprice(float minprice) {
		this.minprice = minprice;
	}

	public String getAddFlag() {
		return addFlag;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
}
