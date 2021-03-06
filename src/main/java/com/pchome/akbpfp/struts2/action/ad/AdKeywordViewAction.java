package com.pchome.akbpfp.struts2.action.ad;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.IPfpAdKeywordService;
import com.pchome.akbpfp.db.service.board.IPfpBoardService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;
import com.pchome.soft.util.DateValueUtil;

public class AdKeywordViewAction extends BaseCookieAction{

	private IPfpAdGroupService pfpAdGroupService;
	private IPfpAdKeywordService pfpAdKeywordService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfpBoardService pfpBoardService;
	private IAdmAccesslogService admAccesslogService;
	
	private LinkedHashMap<String,String> dateSelectMap; // 查詢日期頁面顯示
	private String startDate;							// 開始日期
	private String endDate;								// 結束日期
	private float definePrice;
	private PfpBoard board;
	private EnumBoardType[] enumBoardType;
	
	private String adGroupSeq;
	private PfpAdGroup adGroup;
	private EnumAdType[] searchAdType;
	private String adKeywordSeq;
	private String status;
	private float userPrice;
	private String groupMaxPrice;
	private String adKeywordType;
	
	private String adKeywordOpen;			//廣泛比對
	private String adKeywordPhraseOpen;		//詞組比對
	private String adKeywordPrecisionOpen;	//精準比對
	private float suggestPrice;
	
	public String execute() throws Exception{
		
		searchAdType = EnumAdType.values();
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();
		
		enumBoardType = EnumBoardType.values();
		board = pfpBoardService.findAccountRemainBoard(EnumBoardType.FINANCE, 
														super.getCustomer_info_id(), 
														EnumCategory.REMAIN_NOT_ENOUGH);
		
		return SUCCESS;
	}
	
	public String updateAdKeywordStatusAction() throws Exception{
		
		String[] adKeywordSeqs = adKeywordSeq.split(",");
		int statusId = Integer.parseInt(status);
		String adType = "0";
		
		for(int i=0;i<adKeywordSeqs.length;i++){
			
			//log.info(" adKeywordSeqs[i] = "+adKeywordSeqs[i]);
			
			if(status.equals(String.valueOf(EnumStatus.Close.getStatusId()))){
				//更新系統價
				log.info("===============");
				log.info(" close keyword ---> update sys price ");
				syspriceOperaterAPI.addKeywordSysprice(adKeywordSeqs[i], userPrice);
			}
			
			PfpAdKeyword adKeyword = pfpAdKeywordService.findAdKeyword(adKeywordSeqs[i]);
			
			//log.info(" adGroup = "+adGroup);
			
			if(adKeyword != null && StringUtils.isNotBlank(status)){
				StringBuffer msg = new StringBuffer();
				msg.append("狀態異動：");
				msg.append("檢視廣告 ==> ");
				msg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
				msg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
				msg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
				
				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == adKeyword.getAdKeywordStatus()){
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
				
				adKeyword.setAdKeywordStatus(statusId);
				adKeyword.setAdKeywordCreateTime(new Date());
				pfpAdKeywordService.saveOrUpdateWithCommit(adKeyword);
				
				adGroupSeq = adKeyword.getPfpAdGroup().getAdGroupSeq();
				adType = adKeyword.getPfpAdGroup().getPfpAdAction().getAdType().toString();
			}
		}	
		
		// adGroup(adAction 為 搜尋廣告 時) 下層關鍵字或播放明細都被關閉, 此分類改為未完成
		if("0".equals(adType) || "1".equals(adType)){
			if(StringUtils.isNotBlank(adGroupSeq)){
				List<PfpAdKeyword> adKeyword = pfpAdKeywordService.validAdKeyword(adGroupSeq);
				//List<PfpAd> adAd = pfpAdService.validAdAd(adGroupSeq);
				
				if(adKeyword.size() <= 0 ){
					PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
					adGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());
					adGroup.setAdGroupUpdateTime(new Date());
					pfpAdGroupService.saveOrUpdate(adGroup);
				}
			}
		}
		
		return SUCCESS;
	}

	//修改關鍵字出價
	public void updateAdKeywordPriceAjax() throws Exception{
		
		PfpAdKeyword adKeyword = pfpAdKeywordService.findAdKeyword(adKeywordSeq);

		if(adKeyword != null && StringUtils.isNotBlank(String.valueOf(userPrice)) && userPrice >= definePrice){
			
			log.info(" AdKeywordSearchPrice = "+adKeyword.getAdKeywordSearchPrice());
			log.info(" userPrice = "+userPrice);
			log.info(" adKeywordType = "+adKeywordType);
			if(adKeyword.getAdKeywordSearchPrice() != userPrice){
				StringBuffer updatemsg = new StringBuffer();
				
				if("widely".equals(adKeywordType)){
					StringBuffer msg = new StringBuffer();
					
					msg.append("搜尋廣告金額異動(自行出價):");
					msg.append("檢視廣告 ==> ");
					msg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
					msg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
					msg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
					msg.append(adKeyword.getAdKeywordSearchPrice());
					msg.append(" ==> ");
					msg.append(userPrice);
					
					admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
													msg.toString(), 
													super.getId_pchome(), 
													super.getCustomer_info_id(), 
													super.getUser_id(), 
													super.request.getRemoteAddr());
					
					updatemsg.append("搜尋廣告關鍵字出價修改 ==>");
					updatemsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
					updatemsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
					updatemsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
					updatemsg.append("廣泛比對出價修改 ==> ");
					
					updatemsg.append("修改前:");
					updatemsg.append(adKeyword.getAdKeywordSearchPrice() + ";");
					updatemsg.append("修改後:");
					updatemsg.append(userPrice + "。");
					
					adKeyword.setAdKeywordSearchPrice(userPrice);
					adKeyword.setAdKeywordUpdateTime(new Date());
					pfpAdKeywordService.saveOrUpdate(adKeyword);
	
				} else if("phrase".equals(adKeywordType)){
					updatemsg.append("搜尋廣告關鍵字出價修改 ==>");
					updatemsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
					updatemsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
					updatemsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
					updatemsg.append("詞組比對出價修改 ==> ");
					
					updatemsg.append("修改前:");
					updatemsg.append(adKeyword.getAdKeywordSearchPhrasePrice() + ";");
					updatemsg.append("修改後:");
					updatemsg.append(userPrice + "。");
					
					adKeyword.setAdKeywordSearchPhrasePrice(userPrice);
					adKeyword.setAdKeywordUpdateTime(new Date());
					pfpAdKeywordService.saveOrUpdate(adKeyword);
				} else if("precision".equals(adKeywordType)){
					updatemsg.append("搜尋廣告關鍵字出價修改 ==>");
					updatemsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
					updatemsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
					updatemsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
					updatemsg.append("精準比對出價修改 ==> ");
					
					updatemsg.append("修改前:");
					updatemsg.append(adKeyword.getAdKeywordSearchPrecisionPrice() + ";");
					updatemsg.append("修改後:");
					updatemsg.append(userPrice + "。");
					
					adKeyword.setAdKeywordSearchPrecisionPrice(userPrice);
					adKeyword.setAdKeywordUpdateTime(new Date());
					pfpAdKeywordService.saveOrUpdate(adKeyword);
				}
				//更新系統價
				log.info(" modify keyword price update sys price ");
				syspriceOperaterAPI.addKeywordSysprice(adKeyword.getAdKeyword(), userPrice);
				
				//修改出價紀錄Accesslog
				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
						updatemsg.toString(), 
						super.getId_pchome(), 
						super.getCustomer_info_id(), 
						super.getUser_id(), 
						super.request.getRemoteAddr());
			}
			
		}
	}
	
	//修改比對方式
	public void updateAdKeywordOpenAjax() throws Exception{
		PfpAdKeyword adKeyword = pfpAdKeywordService.findAdKeyword(adKeywordSeq);
		
		log.info(" adKeywordType = "+adKeywordType);
		
		StringBuffer updatemsg = new StringBuffer();
		updatemsg.append("搜尋廣告關鍵字出價修改 ==>");
		updatemsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
		updatemsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
		updatemsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
		
		if("widely".equals(adKeywordType)){
			updatemsg.append("廣泛比對方式修改 ==> ");		
			if(StringUtils.isBlank(adKeywordOpen)){
				updatemsg.append("修改前:");
				updatemsg.append("比對方式  -->開啟,");
				updatemsg.append("出價  -->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式  -->關閉,");
				updatemsg.append("出價  -->" + 0 + "。");
				
				adKeyword.setAdKeywordOpen(0);
				adKeyword.setAdKeywordSearchPrice(0);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
			} else {
				updatemsg.append("修改前:");
				updatemsg.append("比對方式 -->關閉,");
				updatemsg.append("出價 -->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式  -->開啟,");
				updatemsg.append("出價 -->" + suggestPrice + "。");
				
				adKeyword.setAdKeywordOpen(1);
				adKeyword.setAdKeywordSearchPrice(suggestPrice);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
				
				//更新系統價
				syspriceOperaterAPI.addKeywordSysprice(adKeyword.getAdKeyword(), suggestPrice);
			}
		} else if("phrase".equals(adKeywordType)){
			updatemsg.append("詞組比對方式修改 ==> ");
			if(StringUtils.isBlank(adKeywordPhraseOpen)){
				updatemsg.append("修改前:");
				updatemsg.append("比對方式  -->開啟,");
				updatemsg.append("出價  -->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式  -->關閉,");
				updatemsg.append("出價 -->" + 0 + "。");
				
				adKeyword.setAdKeywordPhraseOpen(0);
				adKeyword.setAdKeywordSearchPhrasePrice(0);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
			} else {
				updatemsg.append("修改前:");
				updatemsg.append("比對方式  -->關閉,");
				updatemsg.append("出價  -->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式 -->開啟,");
				updatemsg.append("出價  -->" + suggestPrice + "。");
				
				adKeyword.setAdKeywordPhraseOpen(1);
				adKeyword.setAdKeywordSearchPhrasePrice(suggestPrice);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
				
				//更新系統價
				syspriceOperaterAPI.addKeywordSysprice(adKeyword.getAdKeyword(), suggestPrice);
			}
		} else if("precision".equals(adKeywordType)){
			updatemsg.append("精準比對方式修改 ==> ");
			if(StringUtils.isBlank(adKeywordPrecisionOpen)){
				updatemsg.append("修改前:");
				updatemsg.append("比對方式 -->開啟,");
				updatemsg.append("出價  -->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式 -->關閉,");
				updatemsg.append("出價 -->" + 0 + "。");
				
				adKeyword.setAdKeywordPrecisionOpen(0);
				adKeyword.setAdKeywordSearchPrecisionPrice(0);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
			} else {
				updatemsg.append("修改前:");
				updatemsg.append("比對方式 -->關閉,");
				updatemsg.append("出價-->" + adKeyword.getAdKeywordSearchPrice() + ";");
				updatemsg.append("修改後:");
				updatemsg.append("比對方式-->開啟,");
				updatemsg.append("出價 -->" + suggestPrice + "。");
				
				adKeyword.setAdKeywordPrecisionOpen(1);
				adKeyword.setAdKeywordSearchPrecisionPrice(suggestPrice);
				adKeyword.setAdKeywordUpdateTime(new Date());
				pfpAdKeywordService.saveOrUpdate(adKeyword);
				
				//更新系統價
				syspriceOperaterAPI.addKeywordSysprice(adKeyword.getAdKeyword(), suggestPrice);
			}
		}
		
		//修改出價紀錄Accesslog
		admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
				updatemsg.toString(), 
				super.getId_pchome(), 
				super.getCustomer_info_id(), 
				super.getUser_id(), 
				super.request.getRemoteAddr());
	}
	
	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}
	
	public void setPfpAdKeywordService(IPfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}
	
	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}
	
	public void setPfpBoardService(IPfpBoardService pfpBoardService) {
		this.pfpBoardService = pfpBoardService;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
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

	public void setDefinePrice(float definePrice) {
		this.definePrice = definePrice;
	}
	
	public PfpBoard getBoard() {
		return board;
	}

	public EnumBoardType[] getEnumBoardType() {
		return enumBoardType;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}
	
	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public EnumAdType[] getSearchAdType() {
		return searchAdType;
	}
	
	public PfpAdGroup getAdGroup() {
		return adGroup;
	}

	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserPrice(float userPrice) {
		this.userPrice = userPrice;
	}

	public String getGroupMaxPrice() {
	    return groupMaxPrice;
	}

	public void setGroupMaxPrice(String groupMaxPrice) {
	    this.groupMaxPrice = groupMaxPrice;
	}

	public String getAdKeywordType() {
		return adKeywordType;
	}

	public void setAdKeywordType(String adKeywordType) {
		this.adKeywordType = adKeywordType;
	}

	public String getAdKeywordOpen() {
		return adKeywordOpen;
	}

	public void setAdKeywordOpen(String adKeywordOpen) {
		this.adKeywordOpen = adKeywordOpen;
	}

	public String getAdKeywordPhraseOpen() {
		return adKeywordPhraseOpen;
	}

	public void setAdKeywordPhraseOpen(String adKeywordPhraseOpen) {
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
	}

	public String getAdKeywordPrecisionOpen() {
		return adKeywordPrecisionOpen;
	}

	public void setAdKeywordPrecisionOpen(String adKeywordPrecisionOpen) {
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
	}

	public void setSuggestPrice(float suggestPrice) {
		this.suggestPrice = suggestPrice;
	}
	
}
