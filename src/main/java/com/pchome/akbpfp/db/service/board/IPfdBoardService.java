package com.pchome.akbpfp.db.service.board;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfdBoard;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfdBoardService extends IBaseService<PfdBoard,String> {

	public List<PfdBoard> findPfdBoard(Map<String, String> conditionsMap) throws Exception;
	
	public void deletePfdBoardByDeleteId(String deleteId) throws Exception;
	
}
