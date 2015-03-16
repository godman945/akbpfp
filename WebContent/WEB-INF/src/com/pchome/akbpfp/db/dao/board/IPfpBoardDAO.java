package com.pchome.akbpfp.db.dao.board;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpBoard;

public interface IPfpBoardDAO extends IBaseDAO<PfpBoard,String>{
		
	public List<PfpBoard> findLatestBoard(String boardType, String customerInfoId, String today) throws Exception;
	
	public List<PfpBoard> findAccountRemainBoard(String boardType, String customerInfoId, String category) throws Exception ; 
}
