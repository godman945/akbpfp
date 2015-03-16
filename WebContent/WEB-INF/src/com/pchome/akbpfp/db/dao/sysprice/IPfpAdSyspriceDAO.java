package com.pchome.akbpfp.db.dao.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdSysprice;

public interface IPfpAdSyspriceDAO extends IBaseDAO<PfpAdSysprice, String>{
	
	public PfpAdSysprice getAdSysprice(String adPoolSeq) throws Exception;
	
	//取得group by 聯撥出價  ,取得廣告的聯撥出價 價格帶
	public List<PfpAdGroup> getAdChannelPriceRange() throws Exception;
}
