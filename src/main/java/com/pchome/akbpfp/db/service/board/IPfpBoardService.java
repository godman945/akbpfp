package com.pchome.akbpfp.db.service.board;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;

public interface IPfpBoardService extends IBaseService<PfpBoard,String>{
	
	public List<PfpBoard> findLatestBoard(String boardType, String customerInfoId, String today, String activateDate) throws Exception;
	
	public PfpBoard findAccountRemainBoard(EnumBoardType enumBoardType, String customerInfoId, EnumCategory enumCategory) throws Exception;
	
}
