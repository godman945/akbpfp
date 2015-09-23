package com.pchome.akbpfp.struts2.ajax.board;

import java.util.ArrayList;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.service.board.IPfpBoardService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.soft.util.DateValueUtil;

public class BoardAjax extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6593611457033681402L;
	
	private IPfpBoardService pfpBoardService;
	private IPfpAdService pfpAdService;
	
	private String boardType;				// 查詢公告類型
	private List<PfpBoard> boardList;
	private EnumBoardType[] enumBoardType = EnumBoardType.values();		// 公告屬性
	
	private int pageNo = 1;       			// 目前頁數
	private int pageSize = 20;     			// 每頁幾筆
	private int pageCount = 0;    			// 共幾頁
	private int totalCount = 0;   			// 共幾筆
	
	public String execute() throws Exception{
		
		return SUCCESS;
	}

	public String latestBoardAjax() throws Exception{
		
		// 最新公告 
		String today = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
		
		boardList = pfpBoardService.findLatestBoard(boardType, 
												super.getCustomer_info_id(), 
												today);	
		
		//如果廣告已關閉則不顯示公告
		List<PfpBoard> chooseBoardList = new ArrayList<PfpBoard>();
		for(PfpBoard pfpBoard:boardList){
			String adSeq = pfpBoard.getDeleteId();
			if(adSeq != null){
				int adStatus = 10;
				int adGroupStatus = 10;
				int adActionStatus = 10;
				PfpAd pfpad = pfpAdService.getPfpAdBySeq(adSeq);
				adStatus = pfpad.getAdStatus();
				adGroupStatus = pfpad.getPfpAdGroup().getAdGroupStatus();
				adActionStatus = pfpad.getPfpAdGroup().getPfpAdAction().getAdActionStatus();
				
				if(adStatus != 10 && adGroupStatus != 10 && adActionStatus != 10){
					chooseBoardList.add(pfpBoard);
				}	
			} else {
				chooseBoardList.add(pfpBoard);
			}
		}
		boardList = chooseBoardList;
		
		return SUCCESS;
	}
	
	
	public String searchBoardAjax() throws Exception{
		
		String dateStr = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
		
		List<PfpBoard> boards = pfpBoardService.findLatestBoard(boardType, 
																super.getCustomer_info_id(), 
																dateStr);
		
		int maxCount = 0;
		
		totalCount = boards.size();
		pageCount = (int) Math.ceil(((float)totalCount / pageSize));
		maxCount = Math.min(pageNo*pageSize,totalCount);
		
		if(pageSize < totalCount){
			
			boardList = new ArrayList<PfpBoard>();
			
			for(int i=(pageNo-1)*pageSize;i<maxCount;i++){
				boardList.add(boards.get(i));
			}
		}else{
			boardList = boards;
		}
		
		return SUCCESS;
	}

	public void setPfpBoardService(IPfpBoardService pfpBoardService) {
		this.pfpBoardService = pfpBoardService;
	}

	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public EnumBoardType[] getEnumBoardType() {
		return enumBoardType;
	}

	public List<PfpBoard> getBoardList() {
		return boardList;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


}
