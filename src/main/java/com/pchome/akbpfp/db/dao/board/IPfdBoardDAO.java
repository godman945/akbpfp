package com.pchome.akbpfp.db.dao.board;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfdBoard;

public interface IPfdBoardDAO extends IBaseDAO<PfdBoard,String> {
	
	public List<PfdBoard> findPfdBoard(Map<String, String> conditionsMap) throws Exception;
	
	public void deletePfdBoardByDeleteId(String deleteId) throws Exception;
	
}
