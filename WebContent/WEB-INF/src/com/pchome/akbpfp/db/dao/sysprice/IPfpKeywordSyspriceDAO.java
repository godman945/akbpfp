package com.pchome.akbpfp.db.dao.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpKeywordSysprice;

public interface IPfpKeywordSyspriceDAO extends IBaseDAO<PfpKeywordSysprice, String>{
	
	public PfpKeywordSysprice getKeywordSysprice(String keyword) throws Exception;
	//取得group by 關鍵出價  ,取得某個字系統價的價格帶
	public List<PfpAdKeyword> getKeywordSearchPriceRange(String keyword);
	
}
