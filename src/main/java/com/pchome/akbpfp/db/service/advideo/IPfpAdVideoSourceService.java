package com.pchome.akbpfp.db.service.advideo;

import com.pchome.akbpfp.db.pojo.PfpAdVideoSource;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpAdVideoSourceService extends IBaseService<PfpAdVideoSource,String>{
	
	public PfpAdVideoSource getVideoUrl(String videoUrl) throws Exception;

	
}
