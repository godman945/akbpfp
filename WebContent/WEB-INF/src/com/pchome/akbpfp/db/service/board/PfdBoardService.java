package com.pchome.akbpfp.db.service.board;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.board.PfdBoardDAO;
import com.pchome.akbpfp.db.pojo.PfdBoard;
import com.pchome.akbpfp.db.service.BaseService;

public class PfdBoardService extends BaseService<PfdBoard,String> implements IPfdBoardService {

	public List<PfdBoard> findPfdBoard(Map<String, String> conditionsMap) throws Exception{
		return ((PfdBoardDAO)dao).findPfdBoard(conditionsMap);
	}
	
	public void deletePfdBoardByDeleteId(String deleteId) throws Exception {
		((PfdBoardDAO)dao).deletePfdBoardByDeleteId(deleteId);
	}
	
}
