package com.pchome.akbpfp.struts2.action.board;

import java.util.Date;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.soft.util.DateValueUtil;

public class BoardAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6593611457033681402L;

	private EnumBoardType[] boardType;
	private String startDate;
	private String endDate;
	
	public String execute() throws Exception{
		
		// 公告類別
		boardType = EnumBoardType.values();	
		// 公告開始日期
		startDate = DateValueUtil.getInstance().getDateValue(-60, DateValueUtil.DBPATH);			
		// 公告結束日期
		endDate = DateValueUtil.getInstance().dateToString(new Date());							
		
		return SUCCESS;
	}

	public EnumBoardType[] getBoardType() {
		return boardType;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	
	
}
