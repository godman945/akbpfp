package com.pchome.akbpfp.db.service.board;

import java.util.List;

import com.pchome.akbpfp.db.dao.board.PfpBoardDAO;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;

public class PfpBoardService extends BaseService<PfpBoard,String> implements IPfpBoardService{

	public List<PfpBoard> findLatestBoard(String boardType, String customerInfoId, String today, String activateDate) throws Exception{		
		return ((PfpBoardDAO)dao).findLatestBoard(boardType, customerInfoId, today, activateDate);
	}
	
	public PfpBoard findAccountRemainBoard(EnumBoardType enumBoardType, String customerInfoId, EnumCategory enumCategory) throws Exception {
		
		PfpBoard board = null;
		List<PfpBoard> boards = ((PfpBoardDAO)dao).findAccountRemainBoard(enumBoardType.getType(), customerInfoId, enumCategory.getCategory());
		
		if(boards.size() > 0){
			board = boards.get(0);
		}
		
		return board;
	}
}
