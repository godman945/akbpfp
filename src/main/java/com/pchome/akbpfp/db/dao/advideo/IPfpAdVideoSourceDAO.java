package com.pchome.akbpfp.db.dao.advideo;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoSource;

public interface IPfpAdVideoSourceDAO extends IBaseDAO<PfpAdVideoSource,String>{
	

	public PfpAdVideoSource getVideoUrl(String videoUrl) throws Exception;
	
}
